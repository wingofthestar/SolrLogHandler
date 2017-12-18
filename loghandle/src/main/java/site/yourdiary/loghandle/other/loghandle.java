package site.yourdiary.loghandle.other;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loghandle {
    public static void main(String[] args) throws IOException {
        File srcFile = new File("D:\\TNEPS201001_D0922_nB03p2122.log");
        InputStreamReader isr = new InputStreamReader(new FileInputStream(srcFile), "GB2312");
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        List<Map<String, Object>> logMapList = new ArrayList();
        while (line != null) {
            String regex = "^\\[(.*)\\]\\[(\\w{13})\\]\\s+--\\s+\\[(\\w)\\]\\[(.*)\\]$";
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(line);
            Map<String, Object> logMap = new HashMap<>();
            if (m.matches()) {
                logMap.put("timestamp", m.group(1));
                //pid:进程号
                logMap.put("pid", m.group(2));
                logMap.put("level", m.group(3));
                logMap.put("content", m.group(4));
            }
            logMapList.add(logMap);
            line = br.readLine();
        }

        System.out.println("end");
    }
}
