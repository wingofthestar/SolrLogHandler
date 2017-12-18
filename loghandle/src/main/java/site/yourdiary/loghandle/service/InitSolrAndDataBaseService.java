package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.exception.SolrAndDataBaseInitException;

@Service
public class InitSolrAndDataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(InitSolrAndDataBaseService.class);
    private DataBaseLogInfoService dataBaseLogInfoService;
    private SolrLogInfoIndexService solrLogInfoIndexService;
    @Autowired
    public void setDataBaseLogInfoService(DataBaseLogInfoService dataBaseLogInfoService) {
        this.dataBaseLogInfoService = dataBaseLogInfoService;
    }
    @Autowired
    public void setSolrLogInfoIndexService(SolrLogInfoIndexService solrLogInfoIndexService) {
        this.solrLogInfoIndexService = solrLogInfoIndexService;
    }

    @Transactional
    public void InitSolrAndDataBase() throws SolrAndDataBaseInitException {
        logger.info("开始同步初始化数据库和Solr索引");
        try {
            dataBaseLogInfoService.updateDataBaseLogInfo();
            solrLogInfoIndexService.updateSolrIndexFromDateBase();
        }catch (Exception e){
            logger.error("同步初始化数据库和Solr索引失败" + e);
            throw new SolrAndDataBaseInitException("同步初始化数据库和Solr索引失败" + e);
        }
        logger.info("同步初始化数据库和Solr索引成功");
    }

}
