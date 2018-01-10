package site.yourdiary.loghandle.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.pojo.BootstrapResponseInfo;
import site.yourdiary.loghandle.service.HistoryAnalyzeService;
import site.yourdiary.loghandle.service.ReportService;

import java.util.Map;

@RestController
public class HistoryReportController {

    private HistoryAnalyzeService historyAnalyzeService;
    private ReportService reportService;
    @Autowired
    public void setHistoryAnalyzeService(HistoryAnalyzeService historyAnalyzeService) {
        this.historyAnalyzeService = historyAnalyzeService;
    }
    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * 获取历史日志报告列表
     * @return
     */
    @RequestMapping("/historyLogList")
    public BootstrapResponseInfo showHistoryLogReport(@RequestParam("offset") int pageNumber,
                                                      @RequestParam("limit") int pageSize){
        return historyAnalyzeService.getHistoryLogReport(pageNumber, pageSize);
    }

    /**
     * 获取历史报告列表哪天的日志报告
     */

    /**
     * 获取周、月、年出错天数
     */
    @RequestMapping("/showErrorDays")
    public Map<String, Long> showErrorDays(){
        return historyAnalyzeService.getErrorDays();
    }

    /**
     * 获取月出错情况比例统计
     */
    @RequestMapping("/errorPercent")
    public Map<String, String> MonthErrorPercent(@RequestParam("type") Character C){
        Map<String, String> resultMap = historyAnalyzeService.ErrorPercent(C);
        return resultMap;
    }


    @RequestMapping("/historyLogErroList")
    @ResponseBody
    public Map<String, String> historyLogReport(){
       return historyAnalyzeService.errorMap();
    }

    @RequestMapping("/historyErrorAndWarningNumber")
    @ResponseBody
    public Map<String, Long> historyErrorAndWarningNumber(){
       return historyAnalyzeService.gainHistoryErrorAndWarningNumber();
    }

}
