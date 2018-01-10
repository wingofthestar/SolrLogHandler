package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;

import java.util.Date;
import java.util.List;

public interface HistoryLogReportRepository extends JpaRepository<HistoryLogReport, String> {
    Long countByErrorNumberGreaterThanAndHistoryLogDateBetween(Long number, Date startDate, Date endDate);

    List<HistoryLogReport> findByErrorNumberGreaterThanAndHistoryLogDateBetween(Long number, Date startDate, Date endDate);

    HistoryLogReport findByHistoryLogReportId(String historyLogReportId);
}
