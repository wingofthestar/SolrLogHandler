package site.yourdiary.loghandle.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.exception.SolrAndDataBaseInitException;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.service.FlushInfoService;
import site.yourdiary.loghandle.service.InitSolrAndDataBaseService;

import java.util.Date;

@RestController
public class SolrAndDataBaseInitController {

    private InitSolrAndDataBaseService initSolrAndDataBaseService;
    private FlushInfoService flushInfoService;
    @Autowired
    public void setInitSolrAndDataBaseService(InitSolrAndDataBaseService initSolrAndDataBaseService) {
        this.initSolrAndDataBaseService = initSolrAndDataBaseService;
    }
    @Autowired
    public void setFlushInfoService(FlushInfoService flushInfoService) {
        this.flushInfoService = flushInfoService;
    }

    /**
     * 初始化数据库，并同步日志文件到Solr索引库，记录操作的时间节点
     * @param timestamp 前端传来操作时刻的时间戳
     * @return
     * @throws SolrAndDataBaseInitException
     */
    @RequestMapping("/initSolrAndDataBase")
    public ResponseInfo initSolrAndDataBase(@RequestParam("time") Long timestamp) throws SolrAndDataBaseInitException {
        initSolrAndDataBaseService.InitSolrAndDataBase();
        Date date = new Date(timestamp);
        flushInfoService.saveFlushInfo(date);
        return new ResponseInfo(ResponseInfo.OK,"同步初始化数据库和Solr索引成功");
    }
}
