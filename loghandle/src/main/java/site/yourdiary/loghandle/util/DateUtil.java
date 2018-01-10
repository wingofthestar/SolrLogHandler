package site.yourdiary.loghandle.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换工具类
 */
public class DateUtil {

    public static Date[] weekStartToYesterday(){
        Calendar calendar = Calendar.getInstance();
        //设置获得本周的第一天的日期
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date weekStart = calendar.getTime();
        //设置获得昨天的日期
        Date yesterday = DateUtil.yesterday();
        return new Date[]{weekStart, yesterday};
    }

    public static Date[] monthStartToYesterday(){
        Calendar calendar = Calendar.getInstance();
        //设置获得本月初的天数
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date monthStart = calendar.getTime();

        //设置获得昨天的日期
        Date yesterday = DateUtil.yesterday();
        return new Date[]{monthStart, yesterday};

    }

    public static Date[] yearStartToToday(){
        Calendar calendar = Calendar.getInstance();
        //设置获得年初的天数
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date yearStart = calendar.getTime();
        //设置获得昨天的日期
        Date yesterday = DateUtil.yesterday();
        return new Date[]{yearStart, yesterday};
    }

    private static Date yesterday(){
        //设置获得昨天的日期
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.add(Calendar.DATE, -1);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        return todayCalendar.getTime();
    }
}
