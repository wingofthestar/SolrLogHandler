package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
//@Service
public class LogHandleService {

    private static final Logger logger = LoggerFactory.getLogger(LogHandleService.class);

    public List<LogInfoData> logHandle() throws IOException {

        logger.info("开始处理日志文件");

        List<LogInfoData> logMapList = new ArrayList();
        List<File> logFileList = new ArrayList<>();
        File file = new File("C:\\solrlog");
        File[] fileList = file.listFiles();
        for (File logFile : fileList) {
            if (logFile.isFile() && logFile.exists()) {
               InputStreamReader reader = new InputStreamReader(new FileInputStream(logFile), "GB2312");
               BufferedReader br = new BufferedReader(reader);
               String line = br.readLine();

               while(line != null){
                   String regex = "^\\[(.*)\\]\\[(\\w{13})\\]\\s+--\\s+\\[(\\w)\\]\\[(.*)\\]$";
                   Pattern r = Pattern.compile(regex);
                   Matcher m = r.matcher(line);
                   if (m.matches()){



                       LogInfoData logInfo = new LogInfoData();
                       logInfo.setTimestamp(m.group(1));
                       logInfo.setPid(m.group(2));
                       logInfo.setLevel(m.group(3));
                       logInfo.setContent(m.group(4));
                       logMapList.add(logInfo);
                   }
                   line = br.readLine();
               }

            }
        }

        logger.info("处理日志文件结束");
        return logMapList;
    }

    public static void main(String[] args) throws IOException {
        LogHandleService logHandleService = new LogHandleService();
        logHandleService.logHandle();
        System.out.println("OK");
    }
}
