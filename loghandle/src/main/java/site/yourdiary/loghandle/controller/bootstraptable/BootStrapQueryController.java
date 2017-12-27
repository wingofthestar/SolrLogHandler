package site.yourdiary.loghandle.controller.bootstraptable;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.pojo.BootstrapResponseInfo;
import site.yourdiary.loghandle.service.SolrLogInfoQueryService;

@RestController
@RequestMapping("/bootstrap")
public class BootStrapQueryController {

    private SolrClient solrClient;
    private SolrLogInfoQueryService solrLogInfoQueryService;
    @Autowired
    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }
    @Autowired
    public void setSolrLogInfoQueryService(SolrLogInfoQueryService solrLogInfoQueryService) {
        this.solrLogInfoQueryService = solrLogInfoQueryService;
    }

    /**
     * 按照BootstrapTable的接口格式按等级分页查询
     * @param level 等级
     * @param pageNumber 页码
     * @param pageSize 每页条数
     * @return
     */
    @RequestMapping("/queryByLevel")
    public BootstrapResponseInfo BootstrapSolrQueryByLevel(@RequestParam("level") String level,
                                                           @RequestParam("offset") int pageNumber,
                                                           @RequestParam("limit") int pageSize){
        BootstrapResponseInfo bootstrapResponseInfo =
                solrLogInfoQueryService.bootstrapSolrQueryByLevel(pageNumber, pageSize, level);
        return bootstrapResponseInfo;
    }

    /**
     * 按照BootstrapTable的接口格式突出显示搜索并分页并排序
     */
    @RequestMapping("/queryIndexByContentWithHightPage")
    public BootstrapResponseInfo queryIndexByContentWithHightPage(@RequestParam("content") String content,
                                                                  @RequestParam("offset") int pageNumber,
                                                                  @RequestParam("limit") int pageSize){
        BootstrapResponseInfo bootstrapResponseInfo = solrLogInfoQueryService.bootstrapContentWithHightLightPage(pageNumber, pageSize, content);
        return bootstrapResponseInfo;
    }


    /**
     * 按照BootstrapTable的接口根据pid分页查询
     * @param pid
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/queryIndexByPid")
    public BootstrapResponseInfo queryIndexByPid(@RequestParam("pid") String pid,
                                                 @RequestParam("offset") int pageNumber,
                                                 @RequestParam("limit") int pageSize){
        BootstrapResponseInfo bootstrapResponseInfo = solrLogInfoQueryService.bootstrapQueryIndexByPid(pageNumber, pageSize, pid);
        return bootstrapResponseInfo;
    }

    /**
     * 组合查询
     */
    @RequestMapping("/queryIndexByGroup")
    public BootstrapResponseInfo queryIndexByGroup(@RequestParam("offset") int pageNumber,
                                                   @RequestParam("limit") int pageSize,
                                                   @RequestParam(value = "pid", required = false) String pid,
                                                   @RequestParam(value = "level", required = false) String level,
                                                   @RequestParam(value = "content", required = false) String content,
                                                   @RequestParam(value = "timestamp", required = false) String timestamp){

        BootstrapResponseInfo bootstrapResponseInfo = solrLogInfoQueryService.queryGroup(pageNumber, pageSize, pid, level, content, timestamp);
        return bootstrapResponseInfo;
    }


}
