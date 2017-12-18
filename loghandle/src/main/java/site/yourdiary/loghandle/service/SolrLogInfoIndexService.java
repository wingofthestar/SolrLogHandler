package site.yourdiary.loghandle.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.respository.solr.SolrLogInfoRepository;

@Service
public class SolrLogInfoIndexService {

    private static final Logger logger = LoggerFactory.getLogger(SolrLogInfoIndexService.class);
    private SolrClient solrClient;
    private SolrLogInfoRepository solrLogInfoRepository;
    private SolrLogInfoQueryService solrLogInfoQueryService;

    @Autowired
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }
    @Autowired
    public void setLogInfoSolrRepository(SolrLogInfoRepository solrLogInfoRepository) {
        this.solrLogInfoRepository = solrLogInfoRepository;
    }
    @Autowired
    public void setLogInfoQueryService(SolrLogInfoQueryService solrLogInfoQueryService) {
        this.solrLogInfoQueryService = solrLogInfoQueryService;
    }

    /**
     * 从数据库中将日志信息导入到Solr中建立索引
     * @throws SolrCurdException
     */
    @Transactional
    public void updateSolrIndexFromDateBase() throws SolrCurdException {
        logger.info("Solr开始更新索引");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("command", "full-import");
        solrQuery.setRequestHandler("/dataimport");
        QueryResponse response = null;
        try {
            response = solrClient.query(solrQuery);
        } catch (Exception e) {
            logger.error("Solr更新索引失败" +  e.toString());
            throw new SolrCurdException("Solr更新索引失败" + e);
        }
        logger.info("Solr更新索引成功:" + response.toString());
    }

    /**
     * 删除所有Solr索引
     * @throws SolrCurdException
     */
    @Transactional
    public void deleteAllSolrIndex() throws SolrCurdException {

        logger.info("Solr开始删除索引");
        try {
            solrClient.deleteByQuery("*");
            solrClient.commit();
        }catch (Exception e){
            logger.error("Solr删除索引失败" + e.toString());
            throw new SolrCurdException("Solr删除索引失败" +e);
        }
        logger.info("Solr删除索引成功");
    }

    /**
     * 向Solr中添加索引
     * @param logInfo
     * @throws SolrCurdException
     */
    @Transactional
    public void addIndex(LogInfo logInfo) throws SolrCurdException {
        logger.info("开始向Solr中添加索引");
        try {
            solrLogInfoRepository.save(logInfo);
            logger.info("向Solr中添加索引成功");
        }catch (Exception e){
            logger.error("向Solr中添加索引失败");
            throw new SolrCurdException("向Solr中添加索引失败");
        }
    }

    /**
     * 从Solr中删除索引
     * @param id
     * @throws SolrCurdException
     */
    @Transactional
    public void deleteIndex(String id) throws SolrCurdException{
        logger.info("开始从Solr中删除索引");
        try {
            solrLogInfoRepository.deleteById(id);
            logger.info("从Solr中删除索引成功");
        }catch (Exception e){
            logger.error("从Solr中删除索引失败" + e);
            throw new SolrCurdException("从Solr中删除索引失败");
        }
    }

}
