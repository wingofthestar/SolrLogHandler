package site.yourdiary.loghandle.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.yourdiary.loghandle.exception.DataBaseCurdException;
import site.yourdiary.loghandle.pojo.ResponseInfo;
import site.yourdiary.loghandle.service.DataBaseLogInfoService;


@RestController
@RequestMapping("/dataBase")
public class DataBaseOperatorController {

    private DataBaseLogInfoService dataBaseLogInfoService;

    @Autowired
    public void setLogInfoSaveService(DataBaseLogInfoService dataBaseLogInfoService) {
        this.dataBaseLogInfoService = dataBaseLogInfoService;
    }

    @RequestMapping("/handleLogAndaddToDataBase")
    public ResponseInfo handlogAndAddToDataBase() throws DataBaseCurdException {
        dataBaseLogInfoService.saveLogInfoToDataBase();
        return new ResponseInfo(ResponseInfo.OK, "解析日志文件并保存到数据库成功");
    }

    @RequestMapping("/deleteDataBaseLogInfo")
    public ResponseInfo deleteDataBaseLogInfo() throws DataBaseCurdException {
        dataBaseLogInfoService.deleteDataBaseLogInfo();
        return new ResponseInfo(ResponseInfo.OK, "删除数据库日志文件信息成功");
    }

    @RequestMapping("/updateDataBaseLogInfo")
    public ResponseInfo updateDataBaseLogInfo() throws DataBaseCurdException {
        dataBaseLogInfoService.updateDataBaseLogInfo();
        return new ResponseInfo(ResponseInfo.OK, "更新数据库日志信息成功");
    }

}
