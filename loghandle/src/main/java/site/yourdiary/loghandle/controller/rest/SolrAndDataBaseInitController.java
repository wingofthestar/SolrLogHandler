package site.yourdiary.loghandle.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.exception.SolrAndDataBaseInitException;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.service.InitSolrAndDataBaseService;

@RestController
public class SolrAndDataBaseInitController {

    private InitSolrAndDataBaseService initSolrAndDataBaseService;
    @Autowired
    public void setInitSolrAndDataBaseService(InitSolrAndDataBaseService initSolrAndDataBaseService) {
        this.initSolrAndDataBaseService = initSolrAndDataBaseService;
    }

    @RequestMapping("/initSolrAndDataBase")
    public ResponseInfo initSolrAndDataBase() throws SolrAndDataBaseInitException {
        initSolrAndDataBaseService.InitSolrAndDataBase();
        return new ResponseInfo(ResponseInfo.OK,"同步初始化数据库和Solr索引成功");
    }
}
