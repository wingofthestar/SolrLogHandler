package site.yourdiary.loghandle.controller.rest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.service.SolrLogInfoQueryService;

import java.io.IOException;
import java.util.Map;

/**
 * Solr中的搜索操作
 */
@RestController
@RequestMapping("/solrQuery")
public class SolrQueryController {
    private static final Logger logger = LoggerFactory.getLogger(SolrQueryController.class);

    private SolrClient solrClient;
    private SolrLogInfoQueryService solrLogInfoQueryService;

    @Autowired
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    @Autowired
    public void setLogInfoQueryService(SolrLogInfoQueryService solrLogInfoQueryService) {
        this.solrLogInfoQueryService = solrLogInfoQueryService;
    }

    @RequestMapping("/queryIndexById")
    public LogInfo solrQueryById(@Param("id") String id) throws IOException, SolrServerException, SolrCurdException {
        LogInfo logInfo = solrLogInfoQueryService.queryById(id);
        return logInfo;
//        SolrDocument document = solrClient.getById(id);
//        return document.toString();
    }

    /**
     * 搜索内容并高亮显示搜索的关键字(未分页)
     *
     * @return
     */
    @RequestMapping("/queryIndexByContentWithColor")
    public Map solrQueryByContentWithColor(@RequestParam("content") String content) throws SolrCurdException {
       Map resultMap =  solrLogInfoQueryService.queryContentWithColor(content);
       return resultMap;
    }
    /**
     * 使用Spring Data Solr提供的突出显示搜索并分页并排序
     *
     */
    @RequestMapping("/queryIndexByContentWithHightPage")
    public HighlightPage<LogInfo> solrQueryByContentWithHightLightPage(@RequestParam("content") String content,
                                                                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        HighlightPage<LogInfo> logInfoHighlightPage= solrLogInfoQueryService.ContentWithHightLightPage(pageNumber, pageSize, content);
        return logInfoHighlightPage;
    }


    /**
     * 搜索内容并显示(未分页)
     *
     * @return
     */
    @RequestMapping("/queryIndexByContent")
    public Map solrQueryByContent(@RequestParam("content") String content) throws SolrCurdException {
        Map resultMap = solrLogInfoQueryService.queryContent(content);
        return resultMap;
    }
    /**
     * 搜索内容并显示（分页并按id排序）
     */
    @RequestMapping("/queryIndexByContentPage")
    public Page<LogInfo> solrQueryByContentPage(@RequestParam("content") String content,
                                                @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) throws SolrCurdException {
        Page<LogInfo> logInfoPage = solrLogInfoQueryService.queryContentPage(pageNumber, pageSize, content);
        return logInfoPage;
    }

    /**
     *对级别进行统计
     */
    @RequestMapping("/queryLevelCount")
    public Map<String, Long> solrQueryLevelNumber() throws SolrCurdException {
        Map<String, Long> resultMap = solrLogInfoQueryService.queryLevelCount();
        return resultMap;
    }

    /**
     *
     * @return 统计返回日志信息的百分比
     */
    @RequestMapping("/queryLevelPercent")
    public Map<String, String> solrQueryLevelPercent(){
        Map<String, String> resultMap = solrLogInfoQueryService.queryLevelPercent();
        return resultMap;
    }

    /**
     * 查询某一个日志级别的内容(未分页)
     * @param level
     * @return
     * @throws SolrCurdException
     */
    @RequestMapping("/queryByLevel")
    public Map solrQueryByLevel(@RequestParam("level") String level) throws SolrCurdException {
        Map resultMap = solrLogInfoQueryService.queryByLevel(level);
        return resultMap;
    }

    /**
     * 查询某一个日志级别的内容（分页并按id排序）
     * @param level
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/queryByLevelPage")
    public Page<LogInfo> solrQueryByLevelPage(@RequestParam(value = "level") String level,
                                              @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) throws SolrCurdException {
        //没有传入pageNumber和PageSize的话，pageNumber默认为0显示第一页，pageSize默认显示2条数据
        Page<LogInfo> logInfoPage = solrLogInfoQueryService.queryByLevelPage(pageNumber, pageSize, level);
        return logInfoPage;
    }





}
