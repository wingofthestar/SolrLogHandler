package site.yourdiary.loghandle.controller.thy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.yourdiary.loghandle.entity.jpa.HistoryError;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;
import site.yourdiary.loghandle.service.HistoryAnalyzeService;
import site.yourdiary.loghandle.service.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private ReportService reportService;
    private HistoryAnalyzeService historyAnalyzeService;
    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    @Autowired
    public void setHistoryAnalyzeService(HistoryAnalyzeService historyAnalyzeService) {
        this.historyAnalyzeService = historyAnalyzeService;
    }

    @RequestMapping("/")
    public String defaultHomepage(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        if (reportService.errorNumberReport() > 0) {
            model.addAttribute("message", "系统出现异常");
            model.addAttribute("style", "color:red");
        } else {
            model.addAttribute("message", "系统正常运行");
            model.addAttribute("style", "color:green");
        }
        model.addAttribute("errorNumber", reportService.errorNumberReport());
        model.addAttribute("errorList", reportService.errorListReport());
        model.addAttribute("timeOutWarningNumber", reportService.timeOutWarningNumber());
        model.addAttribute("allLogInfoNumber", reportService.allLogInfoNumber());
        model.addAttribute("logFileNumber", reportService.logFileNumber());
        String[] dateStr = reportService.getDateStr();
        model.addAttribute("time", dateStr[0]);
        model.addAttribute("date", dateStr[1]);
        return "index";
    }

    @RequestMapping("/level")
    public String logLevelStatics(HttpServletRequest request){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        return "statistics";
    }

    @RequestMapping("/search")
    public String logSearch(HttpServletRequest request){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        return "search";
    }

    @RequestMapping("/index")
    public String homepage(HttpServletRequest request){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        return "forward:/";
    }

    @RequestMapping("/multiSearch")
    public String logMultiSearch(HttpServletRequest request){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        return "multiSearch";
    }

    @RequestMapping("/history")
    public String logHistory(HttpServletRequest request){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        return "history";
    }

    @RequestMapping("/errorReport")
    public String logHistoryErrorReport(Model model, HttpServletRequest request,
                                        @RequestParam("id") String historyLogReportId){
        if (request.getSession().getAttribute("userId") == null){
            return "forward:/login";
        }
        HistoryLogReport historyLogReport = historyAnalyzeService.historyErrorReport(historyLogReportId);
        model.addAttribute("errorNumber",historyLogReport.getErrorNumber());
        model.addAttribute("timeOutWarningNumber",historyLogReport.getTimeoutWarningNumber());
        List<HistoryError> historyErrorList = (List<HistoryError>) historyLogReport.getHistoryErrors();
        List<String> errorMessageList = new ArrayList<>();
        for (HistoryError historyError:historyErrorList) {
            errorMessageList.add(historyError.getHistoryErrorMessage());
        }
        model.addAttribute("errorList",errorMessageList);
        return "errorReport";
    }

}
