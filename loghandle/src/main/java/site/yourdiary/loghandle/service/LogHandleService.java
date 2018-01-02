package site.yourdiary.loghandle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import site.yourdiary.loghandle.entity.jpa.LogInfoData;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
//@Service
public class LogHandleService {

    private static final Logger logger = LoggerFactory.getLogger(LogHandleService.class);

    public List<LogInfoData> logHandle() throws IOException, ParseException {

        logger.info("开始处理日志文件");

        List<LogInfoData> logMapList = new ArrayList<>();
        File file = new File("C:\\solrlog");
        File[] fileList = file.listFiles();
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        if(fileList != null){
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
                            Date dateTime = sdfTime.parse(m.group(1));
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DATE);
                            calendar.setTime(dateTime);
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DATE, day);
                            Date logDate = calendar.getTime();
                            logInfo.setTimestamp(logDate);
                            logInfo.setPid(m.group(2));
                            logInfo.setLevel(m.group(3));
                            logInfo.setContent(m.group(4));
                            logMapList.add(logInfo);
                        }
                        line = br.readLine();
                    }

                }
            }
        }else{
            logger.info("日志目录下没有日志文件");
        }

        logger.info("处理日志文件结束");
        return logMapList;
    }

    public static void main(String[] args) throws IOException, ParseException {
        LogHandleService logHandleService = new LogHandleService();
        logHandleService.logHandle();
        System.out.println("OK");
    }
}
