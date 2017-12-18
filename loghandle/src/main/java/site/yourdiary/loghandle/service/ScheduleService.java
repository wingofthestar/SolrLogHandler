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
    @Autowired
    public void setInitSolrAndDataBaseService(InitSolrAndDataBaseService initSolrAndDataBaseService) {
        this.initSolrAndDataBaseService = initSolrAndDataBaseService;
    }

    /**
     * 每天定时同步初始化数据库和Solr索引失败
     */
    @Scheduled(cron = "0 0 6 * * ?") //每天凌晨6点执行一次
    public void scheduler(){
        try {
            initSolrAndDataBaseService.InitSolrAndDataBase();
        } catch (SolrAndDataBaseInitException e) {
            logger.error("定时同步初始化数据库和Solr索引失败" + "time:" + new Date() + e.toString());
        }
    }
}