package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;

public interface DataBaseLogInfoRepository extends JpaRepository<LogInfoData, String> {

}
