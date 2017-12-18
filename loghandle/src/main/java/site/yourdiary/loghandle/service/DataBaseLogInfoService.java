package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;
import site.yourdiary.loghandle.exception.DataBaseCurdException;
import site.yourdiary.loghandle.respository.jpa.DataBaseLogInfoRepository;

import java.util.List;

/**
 * 数据库中日志信息的CURD操作
 */
@Service
public class DataBaseLogInfoService {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseLogInfoService.class);
    private LogHandleService logHandleService;
    private DataBaseLogInfoRepository dataBaseLogInfoRepository;
    @Autowired
    public void setLogHandleService(LogHandleService logHandleService) {
        this.logHandleService = logHandleService;
    }
    @Autowired
    public void setLogInfoRepository(DataBaseLogInfoRepository dataBaseLogInfoRepository) {
        this.dataBaseLogInfoRepository = dataBaseLogInfoRepository;
    }

    @Transactional
    public void saveLogInfoToDataBase() throws DataBaseCurdException {
        logger.info("开始处理日志，并保存至数据库");
        try {
            List<LogInfoData> logInfoDataList = logHandleService.logHandle();
            dataBaseLogInfoRepository.save(logInfoDataList);
        }catch (Exception e){
            logger.error("保存日志信息失败" + e);
            throw new DataBaseCurdException("处理日志，并保存信息到数据库失败" + e);
        }
        logger.info("保存日志成功");
    }

    @Transactional
    public void deleteDataBaseLogInfo() throws DataBaseCurdException {
        logger.info("开始删除数据库日志信息");
        try {
            dataBaseLogInfoRepository.deleteAll();
        }catch (Exception e){
            logger.error("删除数据库日志失败" + e);
            throw new DataBaseCurdException("删除数据库日志信息失败" + e);
        }
        logger.info("删除数据库日志成功");
    }

    @Transactional
    public void updateDataBaseLogInfo() throws DataBaseCurdException {
        logger.info("开始更新数据库日志信息");
        try {
            dataBaseLogInfoRepository.deleteAll();
            List<LogInfoData> logInfoDataList = logHandleService.logHandle();
            dataBaseLogInfoRepository.save(logInfoDataList);
        }catch (Exception e){
            logger.error("更新数据库日志失败" + e);
            throw new DataBaseCurdException("更新数据库日志失败" + e);
        }
        logger.info("更新数据库日志成功");
    }
}
