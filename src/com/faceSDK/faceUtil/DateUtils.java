package com.faceSDK.faceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    public static Map<String, Date> determineTimeNumber(String timeNumber){
        String startTime = null;
        String endTime= null;
        Map<String,Date> map=new HashMap<>();
        // 定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // 获取当前日期时间
        Date now=new Date();
        if(timeNumber!=null){
            int number = Integer.parseInt(timeNumber);
            switch (number){
                case 1:
                    //今天,开始时间和结束时间
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    startTime = sdf.format(calendar.getTime());
                    endTime = sdf.format(now);
                    break;
                case 2:
                    //昨天
                    calendar.add(Calendar.DATE, -1); // 将日期设置为昨天
                    // 获取昨天的开始时间（0点）
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    startTime=sdf.format(calendar.getTime());

                    // 获取昨天的结束时间（23:59:59.999）
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endTime=sdf.format(calendar.getTime());
                    break;
                case 3:
                    //本周,开始时间和结束时间
                    // 获取本周的开始时间（星期一）
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date startOfWeek = calendar.getTime();
                    startTime = sdf.format(startOfWeek);
                    // 获取当前时间
                    endTime = sdf.format(now);
                    break;
                case 4:

                    calendar.add(Calendar.WEEK_OF_YEAR, -1);

                    // 上周的开始时间（周一）
                    calendar.set(Calendar.DAY_OF_WEEK, 7);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endTime=sdf.format(calendar.getTime());

                    calendar.set(Calendar.DAY_OF_WEEK, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    startTime=sdf.format(calendar.getTime());
                    break;
                case 5:
                    //本月,开始时间和结束时间
                    // 设置为本月的第一天
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    // 本月开始时间
                    startTime = sdf.format(calendar.getTimeInMillis());
                    // 当前时间
                    endTime = sdf.format(now);
                    break;
                case 6:
                    //上月,开始时间和结束时间
                    // 设置为上个月的第一天
                    calendar.add(Calendar.MONTH, -1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    startTime = sdf.format(calendar.getTime());

                    // 设置为上个月的最后一天
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endTime = sdf.format(calendar.getTime());
                    break;
                case 7:
                    //本季度,开始时间和结束时间
                    int month1 = (calendar.get(Calendar.MONTH) / 3) * 3 + 1; // 计算当前月份所在的季度
                    calendar.set(Calendar.MONTH, month1 - 1); // 设置为该季度的第一个月
                    calendar.set(Calendar.DAY_OF_MONTH, 1); // 将日期设置为每个月的第一天
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    startTime=sdf.format(calendar.getTime());
                    // 当前时间
                    endTime = sdf.format(now);
                    break;
                case 8:
                    //上季度,开始时间和结束时间
                    int month2 = (calendar.get(Calendar.MONTH) / 3) * 3 + 1; // 计算当前月份所在的季度
                    calendar.set(Calendar.MONTH, month2 - 4); // 设置为该季度的第一个月
                    calendar.set(Calendar.DAY_OF_MONTH, 1); // 将日期设置为每个月的第一天
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    startTime=sdf.format(calendar.getTime());
                    calendar.set(Calendar.MONTH, month2-2); // 设置为该季度的第一个月
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endTime=sdf.format(calendar.getTime());
                    break;
                case 9:
                    //本年,开始时间和结束时间
                    calendar.set(Calendar.DAY_OF_YEAR, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    startTime=sdf.format(calendar.getTime());
                    endTime=sdf.format(now);
                    break;
                case 10:
                    //去年,开始时间和结束时间
                    calendar.add(Calendar.YEAR, -1); // 将当前时间回退一年

                    // 设置到当年的第一天
                    calendar.set(Calendar.MONTH, Calendar.JANUARY);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    startTime=sdf.format(calendar.getTimeInMillis());

                    // 设置到当年的最后一天
                    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                    calendar.set(Calendar.DAY_OF_MONTH, 31);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    calendar.set(Calendar.MILLISECOND, 999);
                    endTime=sdf.format(calendar.getTimeInMillis());
                    break;
                case 100:
                    startTime="1994-01-01 00:00:00";
                    endTime=sdf.format(now);
                    break;
            }
        }
        try {
            map.put("startTime",sdf.parse(startTime));
            map.put("endTime",sdf.parse(endTime));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
