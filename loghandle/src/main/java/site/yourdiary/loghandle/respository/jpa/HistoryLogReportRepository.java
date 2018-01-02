package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.HistoryLogReport;

public interface HistoryLogReportRepository extends JpaRepository<HistoryLogReport, String> {
}
