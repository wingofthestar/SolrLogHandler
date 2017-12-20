package site.yourdiary.loghandle.controller.rest;

import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.service.SolrLogInfoIndexService;

/**
 * Solr索引的增删改查
 */
@RestController
@RequestMapping("/solr")
public class SolrIndexController {
    private static final Logger logger = LoggerFactory.getLogger(SolrIndexController.class);

    private SolrClient solrClient;
    private SolrLogInfoIndexService solrLogInfoIndexService;

    @Autowired
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }
    @Autowired
    public void setLogInfoSolrIndexService(SolrLogInfoIndexService solrLogInfoIndexService) {
        this.solrLogInfoIndexService = solrLogInfoIndexService;
    }

    //从数据库中更新索引到Solr
    @RequestMapping("/updateAllIndex")
    public ResponseInfo SolrIndexUpdateAll() throws SolrCurdException {
        solrLogInfoIndexService.updateSolrIndexFromDateBase();
        return new ResponseInfo(ResponseInfo.OK,"Solr更新索引成功");
    }

    //删除Solr中的所有索引
    @RequestMapping("/deleteAllIndex")
    public ResponseInfo SolrIndexDeleteAll() throws SolrCurdException {
        solrLogInfoIndexService.deleteAllSolrIndex();
        return new ResponseInfo(ResponseInfo.OK, "Solr删除索引成功");
    }

    //为Solr添加某个索引
    @RequestMapping("/addIndex")
    public ResponseInfo SolrIndexAdd(@RequestParam("timestamp")  String timestamp, @RequestParam("pid") String pid,
                               @RequestParam("level") String level, @RequestParam("content") String content) throws SolrCurdException {
        LogInfo logInfo = new LogInfo();
        logInfo.setTimestamp(timestamp);
        logInfo.setPid(pid);
        logInfo.setLevel(level);
        logInfo.setContent(content);
        solrLogInfoIndexService.addIndex(logInfo);
        return new ResponseInfo(ResponseInfo.OK,"Solr添加索引成功");
    }

    //根据Id删除索引
    @RequestMapping("/deleteIndex")
    public ResponseInfo SolrIndexDelete(@RequestParam("id") String id) throws SolrCurdException {
        solrLogInfoIndexService.deleteIndex(id);
        return new ResponseInfo(ResponseInfo.OK,"Solr删除索引成功");
    }
}
