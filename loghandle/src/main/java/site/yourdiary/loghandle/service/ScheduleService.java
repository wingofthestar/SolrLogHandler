package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.yourdiary.loghandle.exception.SolrAndDataBaseInitException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(Scheduled.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private InitSolrAndDataBaseService initSolrAndDataBaseService;
    private FlushInfoService flushInfoService;
    private HistoryLogReportSaveService historyLogReportSaveService;
    @Autowired
    public void setInitSolrAndDataBaseService(InitSolrAndDataBaseService initSolrAndDataBaseService) {
        this.initSolrAndDataBaseService = initSolrAndDataBaseService;
    }
    @Autowired
    public void setFlushInfoService(FlushInfoService flushInfoService) {
        this.flushInfoService = flushInfoService;
    }
    @Autowired
    public void setHistoryLogReportSaveService(HistoryLogReportSaveService historyLogReportSaveService) {
        this.historyLogReportSaveService = historyLogReportSaveService;
    }

    /**
     * 每天定时同步初始化数据库和Solr索引库
     */
    @Scheduled(cron = "1 0 0 * * ?") //每天00:00:01的时候执行一次
    public void initScheduler(){
        try {
            logger.info("开始执行schedule操作{}" + dateFormat.format(new Date()));
            initSolrAndDataBaseService.InitSolrAndDataBase();
            flushInfoService.saveFlushInfo(new Date());
        } catch (SolrAndDataBaseInitException e) {
            logger.error("定时同步初始化数据库和Solr索引失败" + dateFormat.format(new Date()) + e.toString());
        }
    }

    /**
     * 每天定时存储历史日志报告信息
     */
    @Scheduled(cron = "59 59 23 * * ?") //每天23:59:59的时候执行一次
    public void historyLogSaveScheduler(){
        try {
            logger.info("开始执行schedule操作{}" + dateFormat.format(new Date()));
            historyLogReportSaveService.saveHistoryLogReport();
        }catch (Exception e){
            logger.error("定时存储历史日志报告信息失败{}", dateFormat.format(new Date()));
        }
    }
}