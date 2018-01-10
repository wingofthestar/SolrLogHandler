package site.yourdiary.loghandle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yourdiary.loghandle.entity.solr.LogInfo;
import site.yourdiary.loghandle.respository.solr.SolrLogInfoRepository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportService {
    private static final String error1 = "数据库状态控制更新异常";
    private static final String error2 = "执行detach失败";
    private static final String error3 = "TSP服务异常";
    private static final String error4 = "未知异常";

    private SolrLogInfoRepository solrLogInfoRepository;
    private FlushInfoService flushInfoService;
    @Autowired
    public void setSolrLogInfoRepository(SolrLogInfoRepository solrLogInfoRepository) {
        this.solrLogInfoRepository = solrLogInfoRepository;
    }
    @Autowired
    public void setFlushInfoService(FlushInfoService flushInfoService) {
        this.flushInfoService = flushInfoService;
    }

    /**
     * @return 存在异常的数量
     */
    public Long errorNumberReport(){
       Long errorNumber = solrLogInfoRepository.countByLevel("E");
       return errorNumber;
    }

    /**
     * 数据库状态控制更新异常：
     * 数据库表执行失败异常
     * TSP服务异常
     * 未知的异常
     * @return 异常列表
     */
    public List errorListReport(){
        Map<String, Integer> errorMap = new HashMap<>();
        errorMap.put(error1, 0);
        errorMap.put(error2, 0);
        errorMap.put(error3, 0);
        errorMap.put(error4, 0);
        List<String> errorReportList = new ArrayList<>();
        List<LogInfo> logInfoList =  solrLogInfoRepository.findByLevel("E");
        for (LogInfo logInfo:logInfoList) {
            if(logInfo.getContent().contains("数据库状态控制更新异常")){
                errorMap.put(error1, errorMap.get(error1) + 1);
            }else if(logInfo.getContent().contains("执行detach失败")){
                errorMap.put(error2, errorMap.get(error2) + 1);
            }else if(logInfo.getContent().contains("TSP服务异常")){
                errorMap.put(error3, errorMap.get(error3) + 1);
            }else {
                errorMap.put(error4, errorMap.get(error4) + 1);
            }
        }

        if(errorMap.get(error1) > 0){
            errorReportList.add("存在" + errorMap.get(error1) + "处数据库状态控制更新异常");
        }
        if(errorMap.get(error2) > 0){
            errorReportList.add("存在" + errorMap.get(error2) + "处数据库表执行detach失败异常");
        }
        if(errorMap.get(error3) > 0){
            errorReportList.add("存在" + errorMap.get(error3) + "处TSP服务异常");
        }
        if(errorMap.get(error4) > 0){
            errorReportList.add("存在" + errorMap.get(error4) + "处其他未知异常");
        }

        return errorReportList;
    }

    public Map<String, Integer> errorMessageAndErrorNumberMap(){
        Map<String, Integer> errorMap = new HashMap<>();
        errorMap.put(error1, 0);
        errorMap.put(error2, 0);
        errorMap.put(error3, 0);
        errorMap.put(error4, 0);
        List<String> errorReportList = new ArrayList<>();
        List<LogInfo> logInfoList =  solrLogInfoRepository.findByLevel("E");
        for (LogInfo logInfo:logInfoList) {
            if(logInfo.getContent().contains("数据库状态控制更新异常")){
                errorMap.put(error1, errorMap.get(error1) + 1);
            }else if(logInfo.getContent().contains("执行detach失败")){
                errorMap.put(error2, errorMap.get(error2) + 1);
            }else if(logInfo.getContent().contains("TSP服务异常")){
                errorMap.put(error3, errorMap.get(error3) + 1);
            }else {
                errorMap.put(error4, errorMap.get(error4) + 1);
            }
        }

        Map<String, Integer> errorMessageAndErrorNumberMap = new HashMap<>();

        if(errorMap.get(error1) > 0){
            errorReportList.add("存在" + errorMap.get(error1) + "处数据库状态控制更新异常");
            errorMessageAndErrorNumberMap.put("存在" + errorMap.get(error1) + "处数据库状态控制更新异常", errorMap.get(error1));
        }
        if(errorMap.get(error2) > 0){
            errorReportList.add("存在" + errorMap.get(error2) + "处数据库表执行detach失败异常");
            errorMessageAndErrorNumberMap.put("存在" + errorMap.get(error2) + "处数据库表执行detach失败异常", errorMap.get(error2));
        }
        if(errorMap.get(error3) > 0){
            errorReportList.add("存在" + errorMap.get(error3) + "处TSP服务异常");
            errorMessageAndErrorNumberMap.put("存在" + errorMap.get(error3) + "处TSP服务异常", errorMap.get(error3));
        }
        if(errorMap.get(error4) > 0){
            errorReportList.add("存在" + errorMap.get(error4) + "处其他未知异常");
            errorMessageAndErrorNumberMap.put("存在" + errorMap.get(error4) + "处其他未知异常", errorMap.get(error4));
        }

        return errorMessageAndErrorNumberMap;
    }



    /**
     *
     * @return 清算超时的数量
     */
    public Long timeOutWarningNumber(){
        Long timeOutWarningNumber = solrLogInfoRepository.countLogInfoByLevelAndContent("D", "超时");
        return timeOutWarningNumber;
    }

    /**
     *
     * @return 所有的日志条数
     */
    public Long allLogInfoNumber(){
        Long allLogInfoNumber = solrLogInfoRepository.count();
        return allLogInfoNumber;
    }

    /**
     *
     * @return 日志文件数
     */
    public long logFileNumber(){
        File file = new File("C:\\solrlog");
        File[] fileList = file.listFiles();
        long logFileNumber = fileList.length;
        return logFileNumber;
    }

    /**
     *
     * @return 前端页面渲染所需要的日期数组格式
     */
    public String[] getDateStr(){
        Date date = flushInfoService.gainFlushInfoTime();
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = sdfTime.format(date);
        String strDate = sdfDate.format(date);
        return new String[]{strTime, strDate};
    }
}
