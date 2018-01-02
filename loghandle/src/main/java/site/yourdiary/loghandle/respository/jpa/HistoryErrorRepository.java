package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.HistoryError;

public interface HistoryErrorRepository extends JpaRepository<HistoryError, String> {
}
