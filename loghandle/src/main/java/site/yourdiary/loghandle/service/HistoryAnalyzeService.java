package site.yourdiary.loghandle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.entity.jpa.HistoryError;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;
import site.yourdiary.loghandle.pojo.BootstrapResponseInfo;
import site.yourdiary.loghandle.pojo.ErrorType;
import site.yourdiary.loghandle.respository.jpa.HistoryErrorRepository;
import site.yourdiary.loghandle.respository.jpa.HistoryLogReportRepository;
import site.yourdiary.loghandle.util.DateUtil;

import java.util.*;

@Service
public class HistoryAnalyzeService {

    private HistoryLogReportRepository historyLogReportRepository;
    private HistoryErrorRepository historyErrorRepository;
    private ReportService reportService;
    private PageRequest buildPageRequest(int pageNumber, int pageSize, Sort sort){
        return new PageRequest(pageNumber-1, pageSize, sort);
    }
    @Autowired
    public void setHistoryLogReportRepository(HistoryLogReportRepository historyLogReportRepository) {
        this.historyLogReportRepository = historyLogReportRepository;
    }
    @Autowired
    public void setHistoryErrorRepository(HistoryErrorRepository historyErrorRepository) {
        this.historyErrorRepository = historyErrorRepository;
    }
    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public BootstrapResponseInfo getHistoryLogReport(int pageNumber, int pageSize){
        Sort sort = new Sort(Sort.Direction.ASC, "historyLogReportId");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        Page<HistoryLogReport> historyLogReportPage = historyLogReportRepository.findAll(request);
        List<HistoryLogReport> historyLogReportList = historyLogReportPage.getContent();
        Long total = historyLogReportPage.getTotalElements();
        BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, historyLogReportList);
        return bootstrapResponseInfo;
    }

    @Transactional
    public Map<String, Long> getErrorDays(){
        Map<String, Long> errorDayMap = new HashMap<>();
        Date[] weekStartToYesterday = DateUtil.weekStartToYesterday();
        Long weekErrorDay = historyLogReportRepository.countByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, weekStartToYesterday[0], weekStartToYesterday[1]);
        Date[] monthStartToYesterday = DateUtil.monthStartToYesterday();
        Long monthErrorDay = historyLogReportRepository.countByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, monthStartToYesterday[0], monthStartToYesterday[1]);
        Date[] yearStartToToday = DateUtil.yearStartToToday();
        Long yearErrorDay = historyLogReportRepository.countByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, yearStartToToday[0], yearStartToToday[1]);
        errorDayMap.put("weekErrorDay", weekErrorDay);
        errorDayMap.put("monthErrorDay", monthErrorDay);
        errorDayMap.put("yearErrorDay", yearErrorDay);
        return errorDayMap;
    }

    @Transactional
    public Map<String, String> ErrorPercent(Character C){
        Date[] weekStartToYesterday = DateUtil.weekStartToYesterday();
        Date[] monthStartToYesterday = DateUtil.monthStartToYesterday();
        Date[] yearStartToYesterday = DateUtil.yearStartToToday();
        List<HistoryLogReport> historyLogReportList = new ArrayList<>();
        if(C == 'W'){
            historyLogReportList = historyLogReportRepository.findByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, weekStartToYesterday[0], weekStartToYesterday[1]);
        }else if(C == 'M'){
            historyLogReportList = historyLogReportRepository.findByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, monthStartToYesterday[0], monthStartToYesterday[1]);
        }else if (C == 'Y'){
            historyLogReportList = historyLogReportRepository.findByErrorNumberGreaterThanAndHistoryLogDateBetween(0L, yearStartToYesterday[0], yearStartToYesterday[1]);
        }else {
            throw new Error();
        }
        Map<String, Integer> ErrorTypeMap = new HashMap<>();
        ErrorTypeMap.put("ControlError", 0);
        ErrorTypeMap.put("DetachError", 0);
        ErrorTypeMap.put("TSPError", 0);
        ErrorTypeMap.put("OtherError", 0);
        for (HistoryLogReport historyLogReport : historyLogReportList) {
            List<HistoryError> historyErrorList = (List<HistoryError>) historyLogReport.getHistoryErrors();
            for (HistoryError historyError : historyErrorList) {
                if(historyError.getErrorType() == 0){
                    ErrorTypeMap.put("ControlError", ErrorTypeMap.get("ControlError")+historyError.getErrorNumber());
                }else if (historyError.getErrorType() == 1){
                    ErrorTypeMap.put("DetachError", ErrorTypeMap.get("DetachError")+historyError.getErrorNumber());
                }else if (historyError.getErrorType() == 2){
                    ErrorTypeMap.put("TSPError", ErrorTypeMap.get("TSPError")+historyError.getErrorNumber());
                }else {
                    ErrorTypeMap.put("OtherError", ErrorTypeMap.get("OtherError")+historyError.getErrorNumber());
                }
            }
        }
        Map<String, String> errorTypePercentMap = new HashMap<>();
        int controlError = ErrorTypeMap.get("ControlError");
        int detachError = ErrorTypeMap.get("DetachError");
        int tSPError = ErrorTypeMap.get("TSPError");
        int otherError = ErrorTypeMap.get("OtherError");
        int errorTypeCount = controlError + detachError + tSPError + otherError;

        int controlErrorP = (int) (controlError*100.0/errorTypeCount);
        int detachErrorP = (int) (detachError*100.0/errorTypeCount);
        int tSPErrorP = (int) (tSPError*100.0/errorTypeCount);
        int otherErrorP;
        if (otherError != 0){
           otherErrorP = (int) (otherError*100.0/errorTypeCount);
        }else {
            otherErrorP = 0;
        }

        String controlErrorPercent = controlErrorP + "%";
        String detachErrorPercent = detachErrorP + "%";
        String tSPErrorPercent = tSPErrorP + "%";
        String otherErrorPercent = otherErrorP + "%";

        errorTypePercentMap.put("controlErrorPercent", controlErrorPercent);
        errorTypePercentMap.put("detachErrorPercent", detachErrorPercent);
        errorTypePercentMap.put("tSPErrorPercent", tSPErrorPercent);
        errorTypePercentMap.put("otherErrorPercent", otherErrorPercent);

        return errorTypePercentMap;
    }

    public Map<String,Long> gainHistoryErrorAndWarningNumber() {
        Map<String,Long> errorAndWarningNumberMap = new HashMap<>();
        errorAndWarningNumberMap.put("errorNumber" ,reportService.errorNumberReport());
        errorAndWarningNumberMap.put("timeOutWarningNumber",reportService.timeOutWarningNumber());
        return errorAndWarningNumberMap;
    }

    public Map<String, String> errorMap() {
        List<String> errorList =  reportService.errorListReport();
        Map<String, String> errorMap = new LinkedHashMap<>();
        int i = 0;
        for (String errorMessage:errorList) {
            i++;
            errorMap.put("list" + i, errorMessage);
        }
        return errorMap;
    }

    public HistoryLogReport historyErrorReport(String historyLogReportId){
         HistoryLogReport historyLogReport = historyLogReportRepository.findByHistoryLogReportId(historyLogReportId);
         return historyLogReport;
    }

//    public Long historyTimeOutWarningNumber(String historyLogReportId){
//
//    }
//
//    public List errorListReport(String historyLogReportId){
//
//    }
}
