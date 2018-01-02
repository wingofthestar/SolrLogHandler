package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.entity.jpa.HistoryError;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;
import site.yourdiary.loghandle.respository.jpa.HistoryLogReportRepository;
import site.yourdiary.loghandle.respository.jpa.HistoryErrorRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoryLogReportSaveService {
    private static final Logger log = LoggerFactory.getLogger(HistoryLogReportSaveService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ReportService reportService;
    private HistoryLogReportRepository historyLogReportRepository;
    private HistoryErrorRepository historyErrorRepository;
    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    @Autowired
    public void setHistoryLogReportRepository(HistoryLogReportRepository historyLogReportRepository) {
        this.historyLogReportRepository = historyLogReportRepository;
    }
    @Autowired
    public void setHistoryErrorRepository(HistoryErrorRepository historyErrorRepository) {
        this.historyErrorRepository = historyErrorRepository;
    }

    /**
     * 将历史日志信息存储到数据库中以便以后进一步分析统计
     */
    @Transactional
    public void saveHistoryLogReport() {
        try {
            log.info("开始存储历史日志报告");
            HistoryLogReport historyLogReport = new HistoryLogReport();
            List<HistoryError> historyErrorList = new ArrayList<>();
            historyLogReport.setErrorNumber(reportService.errorNumberReport());
            historyLogReport.setLogFileNumber(reportService.logFileNumber());
            historyLogReport.setAllLogInfoNumber(reportService.allLogInfoNumber());
            historyLogReport.setHistoryLogDate(new Date());
            historyLogReport.setTimeoutWarningNumber(reportService.timeOutWarningNumber());
            List<String> errorListReport = reportService.errorListReport();
            for (String errorMessage : errorListReport) {
                HistoryError historyError = new HistoryError();
                historyError.setHistoryErrorMessage(errorMessage);
                historyError.setErrorType(HistoryLogReportSaveService.errorTypeCheck(errorMessage));
                historyError.setHistoryLogReport(historyLogReport);
                historyErrorList.add(historyError);
            }
            historyLogReport.setHistoryErrors(historyErrorList);
            historyErrorRepository.save(historyErrorList);
            historyLogReportRepository.save(historyLogReport);
            log.info("存储历史日志报告信息成功");
        }catch (Exception e){
            log.error("存储历史日志报告信息失败");
        }
    }

    private static int errorTypeCheck(String content) {
        if (content.contains("数据库状态控制更新异常")) {
            return 0;
        } else if (content.contains("执行detach失败")) {
            return 1;
        } else if (content.contains("TSP服务异常")) {
            return 2;
        } else {
            return 3;
        }
    }
}