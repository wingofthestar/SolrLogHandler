package site.yourdiary.loghandle.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class logHandleTest {

    public static void main(String[] args) {
        String line2 = "[14:55:59.755][0000103345667] -- [D][[P_BAM_DBMap:225]===报文字段映射==========" +
                "OK源[bizbigtyp      ]目标[biztyp         ]值[2]]";
        String line1= "[14:55:59.755][0000103345667] -- [D][[P_BAM_DBMap:225]===报文字段映射==========]";
        String regex = "^\\[(.*)\\]\\[(\\w{13})\\]\\s+--\\s+\\[(\\w)\\]\\[(.*)\\]";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(line1);
        if(m.matches()){
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
        }
    }


}
