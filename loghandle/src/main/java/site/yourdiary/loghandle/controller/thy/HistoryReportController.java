package site.yourdiary.loghandle.controller.thy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;
import site.yourdiary.loghandle.service.HistoryAnalyzeService;

import java.util.List;

@Controller
public class HistoryReportController {

    private HistoryAnalyzeService historyAnalyzeService;
    @Autowired
    public void setHistoryAnalyzeService(HistoryAnalyzeService historyAnalyzeService) {
        this.historyAnalyzeService = historyAnalyzeService;
    }

    /**
     * 获取历史日志报告列表
     * @return
     */
    @RequestMapping("/historyLogList")
    @ResponseBody
    public List<HistoryLogReport> showHistoryLogReport(){
        return historyAnalyzeService.getHistoryLogReport();
    }

    /**
     * 获取上周历史出错条数
     */


}
