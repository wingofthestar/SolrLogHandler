package site.yourdiary.loghandle.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.pojo.BootstrapResponseInfo;
import site.yourdiary.loghandle.pojo.LayuiTableResponseInfo;
import site.yourdiary.loghandle.respository.solr.SolrLogInfoRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolrLogInfoQueryService {
    private static final Logger logger = LoggerFactory.getLogger(SolrLogInfoQueryService.class);
    private SolrLogInfoRepository solrLogInfoRepository;
    private SolrClient solrClient;
    private SolrTemplate solrTemplate;

    @Autowired
    public void setLogInfoSolrRepository(SolrLogInfoRepository solrLogInfoRepository) {
        this.solrLogInfoRepository = solrLogInfoRepository;
    }
    @Autowired
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }
    @Autowired
    public void setSolrLogInfoRepository(SolrLogInfoRepository solrLogInfoRepository) {
        this.solrLogInfoRepository = solrLogInfoRepository;
    }

    private PageRequest buildPageRequest(int pageNumber, int pageSize, Sort sort){
        return new PageRequest(pageNumber-1, pageSize, sort);
    }

    public LogInfo queryById(String id) throws SolrCurdException {
        LogInfo logInfo = solrLogInfoRepository.findById(id);
        if (logInfo != null) {
            return logInfo;
        } else {
            throw new SolrCurdException("不存在" + id + "对应的索引项");
        }
    }

    /**
     * 这里是用原生的操作solr的方式写的，用Spring Data Solr封装的方法可以更简便
     * @param content
     * @return
     * @throws SolrCurdException
     */
    public Map queryContentWithColor(String content) throws SolrCurdException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "content:" + content);
        solrQuery.addSort("id", SolrQuery.ORDER.asc);

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("content");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

        List<LogInfo> logInfoList = new ArrayList<>();

        //执行查询
        try {
            QueryResponse response = solrClient.query(solrQuery);
            //文档结果集
            SolrDocumentList docs = response.getResults();
            //查询到的结果数量
            long resultNumber = docs.getNumFound();
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (int i = 0; i < docs.size(); i++) {
                LogInfo logInfo = new LogInfo();
                logInfo.setId(docs.get(i).getFieldValue("id").toString());
                logInfo.setPid(docs.get(i).getFieldValue("pid").toString());
                logInfo.setTimestamp(docs.get(i).getFieldValue("timestamp").toString());
                logInfo.setLevel(docs.get(i).getFieldValue("level").toString());
                String key = docs.get(i).getFieldValue("id").toString();
//                Map map = highlighting.get(key);
//                List<String> list = highlighting.get(key).get("content");
//                String str = highlighting.get(key).get("content").get(0);
                logInfo.setContent(highlighting.get(key).get("content").get(0));
                logInfoList.add(logInfo);
            }
            Map resultMap = new HashMap();
            resultMap.put("resultNumber", resultNumber);
            resultMap.put("logInfoList", logInfoList);
//            String result = new Gson().toJson(highlighting);
            return resultMap;
        } catch (Exception e) {
            logger.error("查询出错");
            throw new SolrCurdException("查询出错" + e);
        }

    }

    public Map queryContent(String content) throws SolrCurdException {
        try {
            List<LogInfo> logInfoList = solrLogInfoRepository.findByContent(content);
            long resultNumber = logInfoList.size();
            Map resultMap = new HashMap();
            resultMap.put("resultNumber", resultNumber);
            resultMap.put("logInfoList", logInfoList);
            return resultMap;
        } catch (Exception e) {
            logger.error("查询content出错");
            throw new SolrCurdException("查询出错" + e);
        }
    }

    public HighlightPage<LogInfo> ContentWithHightLightPage(int pageNumber, int pageSize, String content){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        HighlightPage<LogInfo> logInfoHighlightPage = solrLogInfoRepository.findLogInfoHighlightPageByContent(content, request);
        return logInfoHighlightPage;
    }

    public Map<String, Long> queryLevelCount() throws SolrCurdException {
        try {
            Map<String, Long> levelCount = new HashMap();
            levelCount.put("D", solrLogInfoRepository.countByLevel("D"));
            levelCount.put("I", solrLogInfoRepository.countByLevel("I"));
            levelCount.put("T", solrLogInfoRepository.countByLevel("T"));
            levelCount.put("E", solrLogInfoRepository.countByLevel("E"));
            return levelCount;
        }catch (Exception e){
            logger.error("按日志级别统计出错");
            throw new SolrCurdException("按日志级别统计出错" + e);
        }
    }

    public Map queryByLevel(String level) throws SolrCurdException {
        try {
            List<LogInfo> logInfoList = solrLogInfoRepository.findByLevel(level);
            int resultNumber = logInfoList.size();
            Map resultMap = new HashMap();
            resultMap.put("resultNumber", resultNumber);
            resultMap.put("logInfoList", logInfoList);
            return resultMap;
        }catch (Exception e){
            logger.error("查询日志级别为" + level + "的内容出错" + e);
            throw new SolrCurdException("查询日志级别为" + level + "的内容出错" + e);
        }
    }

    public Page<LogInfo> queryByLevelPage(int pageNumber, int pageSize, String level) throws SolrCurdException {
        try {
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByLevel(level, request);
            return logInfoPage;
        }catch (Exception e){
            logger.error("通过Level分页查询日志信息出错" + e);
            throw new SolrCurdException("通过Level分页查询日志信息出错" + e);
        }
    }

    public Page<LogInfo> queryContentPage(int pageNumber, int pageSize, String content) throws SolrCurdException {
        try {
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);

            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByContent(content, request);
            return logInfoPage;
        }catch (Exception e){
            logger.error("通过content分页查询日志信息出错" + e);
            throw new SolrCurdException("通过content分页查询日志信息出错" + e);
        }
    }

    public LayuiTableResponseInfo layuiQueryByLevelPage(int pageNumber, int pageSize, String level){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByLevel(level, request);
        Long total = solrLogInfoRepository.countByLevel(level);
        String message = "查询level:"+level+"的日志信息成功";
        LayuiTableResponseInfo layuiTableResponseInfo = new LayuiTableResponseInfo(LayuiTableResponseInfo.Ok, message,
                total, logInfoPage.getContent());
        return layuiTableResponseInfo;
    }

    /**
     * 按照BootstrapTable的格式根据level分页查询日志信息
     * @param pageNumber
     * @param pageSize
     * @param level
     * @return
     */
    public BootstrapResponseInfo bootstrapSolrQueryByLevel(int pageNumber, int pageSize, String level) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByLevel(level, request);
        Long total = logInfoPage.getTotalElements();
        BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, logInfoPage.getContent());
        return bootstrapResponseInfo;
    }

    /**
     * 按照BootstrapTable的格式根据Content分页高亮查询
     * @param pageNumber
     * @param pageSize
     * @param content
     * @return
     */
    public BootstrapResponseInfo bootstrapContentWithHightLightPage(int pageNumber, int pageSize, String content) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        HighlightPage<LogInfo> logInfoHighlightPage = solrLogInfoRepository.findLogInfoHighlightPageByContent(content, request);
        List<HighlightEntry<LogInfo>> logInfoHighlightPageHighlighted = logInfoHighlightPage.getHighlighted();
        List<LogInfo> logInfos = new ArrayList<>();
        for (HighlightEntry<LogInfo> logInfoHighlightEntry : logInfoHighlightPageHighlighted) {
            LogInfo hightLightedLogInfo = logInfoHighlightEntry.getEntity();
            hightLightedLogInfo.setContent(logInfoHighlightEntry.getHighlights().get(0).getSnipplets().get(0));
            logInfos.add(hightLightedLogInfo);
        }
        Long total = logInfoHighlightPage.getTotalElements();
        BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, logInfos);
        return bootstrapResponseInfo;
    }

    /**
     * 按照BootstrapTable的格式根据id分页查询信息
     * @param pageNumber
     * @param pageSize
     * @param pid
     * @return
     */
    public BootstrapResponseInfo bootstrapQueryIndexByPid(int pageNumber, int pageSize, String pid) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByPid(pid, request);
        List<LogInfo> logInfoList = logInfoPage.getContent();
        Long total = logInfoPage.getTotalElements();
        BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, logInfoList);
        return bootstrapResponseInfo;
    }

    /**
     * 按照BootstrapTable的格式，多种条件组合查询,共16种情况
     * @param timestamp
     * @param pid
     * @param level
     * @param content
     */
    public BootstrapResponseInfo queryGroup(int pageNumber, int pageSize,String timestamp, String pid, String level, String content){
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
        if(StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
           Page<LogInfo> logInfoPage =  solrLogInfoRepository.findLogInfoByTimestamp(timestamp, request);
//           List<LogInfo> logInfoList = logInfoPage.getContent();
//           Long total = logInfoPage.getTotalElements();
//           BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, logInfoList);
           return queryGroupUtil(logInfoPage);
        }else if (!StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByPid(pid, request);
            return queryGroupUtil(logInfoPage);
        }else if(!StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByLevel(level, request);
            return queryGroupUtil(logInfoPage);
        }else if(!StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByContent(content, request);
            return queryGroupUtil(logInfoPage);
        }else if(StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndPid(timestamp, pid, request);
            return queryGroupUtil(logInfoPage);
        }else if(StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndLevel(timestamp, level, request);
            return queryGroupUtil(logInfoPage);
        }else if(StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndContent(timestamp, content, request);
            return queryGroupUtil(logInfoPage);
        }else if(!StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByPidAndLevel(pid, level, request);
            return queryGroupUtil(logInfoPage);
        }else if(!StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByPidAndContent(pid, content, request);
            return queryGroupUtil(logInfoPage);
        }else if(!StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByLevelAndContent(level, content, request);
            return queryGroupUtil(logInfoPage);
        }else if(StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && StringUtils.hasLength(level) && !StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndPidAndLevel(timestamp, pid, level, request);
            return queryGroupUtil(logInfoPage);
        }else if(StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && !StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndPidAndContent(timestamp, pid, content, request);
            return queryGroupUtil(logInfoPage);
        }else if (StringUtils.hasLength(timestamp) && !StringUtils.hasLength(pid) && StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndLevelAndContent(timestamp, level, content, request);
            return queryGroupUtil(logInfoPage);
        }else if (!StringUtils.hasLength(timestamp) && StringUtils.hasLength(pid) && StringUtils.hasLength(level) && StringUtils.hasLength(content)){
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByPidAndLevelAndContent(pid, level, content, request);
            return queryGroupUtil(logInfoPage);
        }else{
            Page<LogInfo> logInfoPage = solrLogInfoRepository.findLogInfoPageByTimestampAndPidAndLevelAndContent(timestamp, pid, level, content, request);
            return  queryGroupUtil(logInfoPage);
        }

    }

    public Map<String,String> queryLevelPercent() {
        Map<String, String> levelPercent = new HashMap<>();
        long logNumberCount = solrLogInfoRepository.count();
        int DLevel = (int) (solrLogInfoRepository.countByLevel("D")*100.0/logNumberCount);
        int ILevel = (int) (solrLogInfoRepository.countByLevel("I")*100.0/logNumberCount);
        int TLevel = (int) (solrLogInfoRepository.countByLevel("T")*100.0/logNumberCount);
        int ELevel = 100-DLevel-ILevel-TLevel;

        String DLevelPercent =  DLevel + "%";
        String ILevelPercent =  ILevel + "%";
        String TLevelPercent =  TLevel + "%";
        String ELevelPercent =  ELevel + "%";

        levelPercent.put("D", DLevelPercent);
        levelPercent.put("I", ILevelPercent);
        levelPercent.put("T", TLevelPercent);
        levelPercent.put("E", ELevelPercent);

        return levelPercent;
    }


    private BootstrapResponseInfo queryGroupUtil(Page<LogInfo> logInfoPage){
        List<LogInfo> logInfoList = logInfoPage.getContent();
        Long total = logInfoPage.getTotalElements();
        BootstrapResponseInfo bootstrapResponseInfo = new BootstrapResponseInfo(total, logInfoList);
        return bootstrapResponseInfo;
    }
}


