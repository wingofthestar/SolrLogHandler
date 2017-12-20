package site.yourdiary.loghandle.controller.layui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.exception.SolrCurdException;
import site.yourdiary.loghandle.pojo.LayuiTableResponseInfo;
import site.yourdiary.loghandle.service.SolrLogInfoQueryService;

@RestController
@RequestMapping("/layui")
public class LayuiSolrQueryController {

    private SolrLogInfoQueryService solrLogInfoQueryService;
    @Autowired
    public void setSolrLogInfoQueryService(SolrLogInfoQueryService solrLogInfoQueryService) {
        this.solrLogInfoQueryService = solrLogInfoQueryService;
    }

    @RequestMapping("/queryByLevel")
    public LayuiTableResponseInfo solrQueryByLevel2(@RequestParam("level") String level,
                                                    @RequestParam("page") int pageNumber,
                                                    @RequestParam("limit") int pageSize) throws SolrCurdException {
        LayuiTableResponseInfo layuiTableResponseInfo =
                solrLogInfoQueryService.layuiQueryByLevelPage(pageNumber, pageSize, level);
        return layuiTableResponseInfo;
    }

}
