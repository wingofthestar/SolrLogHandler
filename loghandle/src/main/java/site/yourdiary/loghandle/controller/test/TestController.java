package site.yourdiary.loghandle.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.service.HistoryLogReportSaveService;

@RestController
@RequestMapping("/test")
public class TestController {
    private HistoryLogReportSaveService historyLogReportSaveService;
    @Autowired
    public void setHistoryLogReportSaveService(HistoryLogReportSaveService historyLogReportSaveService) {
        this.historyLogReportSaveService = historyLogReportSaveService;
    }

    @RequestMapping("/historyReportSave")
    public void test_historyLogReport(){
        historyLogReportSaveService.saveHistoryLogReport();
    }
}
