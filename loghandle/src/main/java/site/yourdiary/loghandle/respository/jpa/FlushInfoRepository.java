package site.yourdiary.loghandle.respository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yourdiary.loghandle.entity.jpa.FlushInfo;

public interface FlushInfoRepository extends JpaRepository<FlushInfo, String>{
}
