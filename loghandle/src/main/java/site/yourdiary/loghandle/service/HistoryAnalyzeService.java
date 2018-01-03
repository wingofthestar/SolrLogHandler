package site.yourdiary.loghandle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;
import site.yourdiary.loghandle.respository.jpa.HistoryErrorRepository;
import site.yourdiary.loghandle.respository.jpa.HistoryLogReportRepository;

import java.util.List;

@Service
public class HistoryAnalyzeService {

    private HistoryLogReportRepository historyLogReportRepository;
    private HistoryErrorRepository historyErrorRepository;
    @Autowired
    public void setHistoryLogReportRepository(HistoryLogReportRepository historyLogReportRepository) {
        this.historyLogReportRepository = historyLogReportRepository;
    }
    @Autowired
    public void setHistoryErrorRepository(HistoryErrorRepository historyErrorRepository) {
        this.historyErrorRepository = historyErrorRepository;
    }

    public List<HistoryLogReport> getHistoryLogReport(){
        List<HistoryLogReport> historyLogReports = historyLogReportRepository.findAll();
        return historyLogReports;
    }
}
