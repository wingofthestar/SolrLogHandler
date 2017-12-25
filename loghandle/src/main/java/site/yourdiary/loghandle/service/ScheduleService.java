package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.yourdiary.loghandle.exception.SolrAndDataBaseInitException;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(Scheduled.class);
    private InitSolrAndDataBaseService initSolrAndDataBaseService;
    private FlushInfoService flushInfoService;
    @Autowired
    public void setInitSolrAndDataBaseService(InitSolrAndDataBaseService initSolrAndDataBaseService) {
        this.initSolrAndDataBaseService = initSolrAndDataBaseService;
    }
    @Autowired
    public void setFlushInfoService(FlushInfoService flushInfoService) {
        this.flushInfoService = flushInfoService;
    }

    /**
     * 每天定时同步初始化数据库和Solr索引库
     */
    @Scheduled(cron = "0 0 0 * * ?") //每天凌晨6点执行一次
    public void scheduler(){
        try {
            initSolrAndDataBaseService.InitSolrAndDataBase();
            flushInfoService.saveFlushInfo(new Date());
        } catch (SolrAndDataBaseInitException e) {
            logger.error("定时同步初始化数据库和Solr索引失败" + "time:" + new Date() + e.toString());
        }
    }
}