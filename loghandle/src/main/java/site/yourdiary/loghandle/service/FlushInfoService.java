package site.yourdiary.loghandle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yourdiary.loghandle.entity.jpa.FlushInfo;
import site.yourdiary.loghandle.respository.jpa.FlushInfoRepository;

import java.util.Date;

@Service
public class FlushInfoService {
    private FlushInfoRepository flushInfoRepository;
    @Autowired
    public void setFlushInfoRepository(FlushInfoRepository flushInfoRepository) {
        this.flushInfoRepository = flushInfoRepository;
    }

    public void saveFlushInfo(Date date){
        FlushInfo flushInfo = new FlushInfo();
        flushInfo.setFlushId("1");
        flushInfo.setTimestamp(date);
        flushInfoRepository.save(flushInfo);
    }

    public Date gainFlushInfoTime(){
       FlushInfo flushInfo =  flushInfoRepository.getOne("1");
       return flushInfo.getTimestamp();
    }
}
