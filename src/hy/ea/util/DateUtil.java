package hy.ea.util;

import com.tiantai.wfj.service.impl.GoldOrderServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期时间常用操作
 *
 * @author yaloo
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String C_DATE_DIVISION = "-";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String C_TIME_PATTON_DEFAULT_ = "yyyy-MM-dd HH:mm:ss FFF";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd
     */
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String C_DATA_PATTON_YYYYMMDD = "yyyyMMdd";

    /**
     * HH:mm:ss
     */
    public static final String C_TIME_PATTON_HHMMSS = "HH:mm:ss";

    public static final int C_ONE_SECOND = 1000;
    public static final int C_ONE_MINUTE = 60 * C_ONE_SECOND;
    public static final int C_ONE_HOUR = 60 * C_ONE_MINUTE;
    public static final long C_ONE_DAY = 24 * C_ONE_HOUR;

    /**
     * Parse a string and return the date value in the specified format
     * DateUtil.C_TIME_PATTON_DEFAULT will be choose,if strFormat is
     * <code>null</code>
     *
     * @param strFormat
     * @param dateValue
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date parseDate(String strFormat, String dateValue) {
        if (dateValue == null)
            return null;

        if (strFormat == null)
            strFormat = C_TIME_PATTON_DEFAULT;

        SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateValue);
        } catch (ParseException pe) {
            newDate = null;
        }

        return newDate;
    }

    /**
     * 获得当前时间，格式yyyy-MM-dd HH:mm:ss
     *
     * @param format
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * util.Date型日期转化转化成Calendar日期
     *
     * @param p_utilDate Date
     * @return Calendar
     * @Author: zhuqx
     * @Date: 2006-10-31
     */
    public static Calendar toCalendarFromUtilDate(java.util.Date p_utilDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(p_utilDate);
        return c;
    }

    /**
     * 获取两日期相减后的月份数 date2 > date1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonthNum(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal2.get(1) - cal1.get(1)) * 12
                + (cal2.get(2) - cal1.get(2) + 1);
    }

    /**
     * 获得当前时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @param format
     * @return
     */
    public static Date getCurrDate() {
        return parseDate(C_TIME_PATTON_DEFAULT, getCurrentDate());
    }

    /**
     * 获得当前时间，格式自定义
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }

    public static String toPaseDate(String format, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(d.getTime());
        return date;
    }

    /**
     * 获得昨天时间，格式自定义
     *
     * @param format
     * @return
     */
    public static String getYesterdayDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(day.getTime());
        return date;
    }


    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
     * @param date2 被比较的时间 为空(null)则为当前时间
     * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
     * @return 举例： compareDate("2009-09-12", null, 0);//比较天
     * compareDate("2009-09-12", null, 1);//比较月
     * compareDate("2009-09-12", null, 2);//比较年
     */
    public static int compareDate(String startDay, String endDay, int stype) {
        int n = 0;
        @SuppressWarnings("unused")
        String[] u = {"天", "月", "年"};
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        endDay = endDay == null ? getCurrentDate("yyyy-MM-dd") : endDay;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception e3) {
            logger.info("wrong occured");
        }
        // List list = new ArrayList();
        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            // list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }
        n = n - 1;
        if (stype == 2) {
            n = (int) n / 365;
        }
        // logger.info("调试信息");
        return n;
    }

    /**
     * 判断时间是否符合时间格式
     */
    public static boolean isDate(String date, String dateFormat) {
        if (date != null) {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                    dateFormat);
            format.setLenient(false);
            try {
                format.format(format.parse(date));
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 实现给定某日期，判断是星期几 date:必须yyyy-MM-dd格式
     */
    public static String getWeekday(String date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        return sdw.format(d);
    }

    /**
     * 用来全局控制 上一周，本周，下一周的周数变化
     */
    private static int weeks = 0;

    /**
     * 获得当前日期与本周一相差的天数
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获得本周星期一的日期
     */
    public static String getCurrentMonday(String format) {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        logger.info("值：{}", mondayPlus);
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, mondayPlus);
        logger.info("调试信息");
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(currentDate.getTime());
        return date;
    }

    /**
     * 获得上周星期一的日期
     */
    public static String getPreviousMonday(String format) {
        weeks--;
        int mondayPlus = getMondayPlus();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(currentDate.getTime());
        return date;
    }

    /**
     * 获得下周星期一的日期
     */
    public static String getNextMonday(String format) {
        weeks++;
        int mondayPlus = getMondayPlus();
        // GregorianCalendar currentDate = new GregorianCalendar();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(currentDate.getTime());
        return date;
    }

    /**
     * 获得相应周的周日的日期 此方法必须写在getCurrentMonday，getPreviousMonday或getNextMonday方法之后
     */
    public static String getSunday(String format) {
        int mondayPlus = getMondayPlus();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, mondayPlus + 7 * weeks + 6);
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(currentDate.getTime());
        return date;
    }

    /**
     * method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     *
     * @param dateString 需要转换为timestamp的字符串
     * @return dataTime timestamp
     */
    public final static java.sql.Timestamp string2Time(String dateString) {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);// 设定格式
        dateFormat.setLenient(false);
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        // java.sql.Timestamp dateTime = new java.sql.Timestamp(date.getTime());
        return new java.sql.Timestamp(date.getTime());// Timestamp类型,timeDate.getTime()返回一个long型
    }

    /**
     * method 将字符串类型的日期转换为一个Date（java.sql.Date）
     *
     * @param dateString 需要转换为Date的字符串
     * @return dataTime Date
     */
    public final static java.sql.Date string2Date(String dateString) {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        // java.sql.Date dateTime = new java.sql.Date(date.getTime());// sql类型
        return new java.sql.Date(date.getTime());
    }

    // 记录考勤， 记录迟到、早退时间
    public static String getState() {
        String state = "正常";
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();
        try {
            Date d1 = df.parse("08:00:00");
            Date d2 = df.parse(df.format(d));
            Date d3 = df.parse("18:00:00");

            int t1 = (int) d1.getTime();
            int t2 = (int) d2.getTime();
            int t3 = (int) d3.getTime();
            if (t2 < t1) {

                long between = (t1 - t2) / 1000;// 除以1000是为了转换成秒
                long hour1 = between % (24 * 3600) / 3600;
                long minute1 = between % 3600 / 60;

                state = "迟到 ：" + hour1 + "时" + minute1 + "分";

            } else if (t2 < t3) {
                long between = (t3 - t2) / 1000;// 除以1000是为了转换成秒
                long hour1 = between % (24 * 3600) / 3600;
                long minute1 = between % 3600 / 60;
                state = "早退 ：" + hour1 + "时" + minute1 + "分";
            }
            return state;
        } catch (Exception e) {
            return state;
        }

    }

    // -----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------

    /**
     * 获取指定日期的年份
     *
     * @param p_date util.Date日期
     * @return int 年份
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getYearOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.YEAR);
    }

    /**
     * 获取指定日期的月份
     *
     * @param p_date util.Date日期
     * @return int 月份
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getMonthOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     * 获取指定日期的日份
     *
     * @param p_date util.Date日期
     * @return int 日份
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getDayOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期的小时
     *
     * @param p_date util.Date日期
     * @return int 日份
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getHourOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的分钟
     *
     * @param p_date util.Date日期
     * @return int 分钟
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getMinuteOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.MINUTE);
    }

    /**
     * 获取指定日期的秒钟
     *
     * @param p_date util.Date日期
     * @return int 秒钟
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static int getSecondOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.get(java.util.Calendar.SECOND);
    }

    /**
     * 获取指定日期的毫秒
     *
     * @param p_date util.Date日期
     * @return long 毫秒
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static long getMillisOfDate(java.util.Date p_date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(p_date);
        return c.getTimeInMillis();
    }

    /**
     * 获取当前季度日期
     * @param datestr 当前日期字符串,默认为当前日期
     * @return string[]{季度起始日期，李度结束日期)
     */
    public static String[] getseasonDay(String datestr,String p_format) {
        SimpleDateFormat sdf = new SimpleDateFormat(p_format);
        Date date;
        if (StringUtils.isNotEmpty(datestr)) {
            try {
                date = sdf.parse(datestr);
            } catch (ParseException e) {
                logger.error("转换字符串:{}为日期失败，错误信息:}", datestr, e);
                return null;
            }
        } else {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //数学原理:
        // moth属性=月份-1，3个月=1季度
        // 那么季度的最大月份:3 =季度,(月份([month属性+1))÷3向上取整 =季度
        // 当前李度最大月份=季度*3，而最大月month属性=最大月份-1
        int month = c.get(Calendar.MONTH);
        int seasonEndMonthNum = (int) (Math.ceil((month + 1) / 3.0) * 3) - 1;
        Calendar endC = Calendar.getInstance();
        endC.setTime(date);
        endC.set(Calendar.MONTH, seasonEndMonthNum);
        endC.set(Calendar.DAY_OF_MONTH, endC.getActualMaximum(Calendar.DAY_OF_MONTH));
        Calendar startC = Calendar.getInstance();
        startC.setTime(date);
        //李度最小月份=李度最大月份-2，
        int seasonstartMonthNum = seasonEndMonthNum - 2;
        startC.set(Calendar.MONTH, seasonstartMonthNum);
        startC.set(Calendar.DAY_OF_MONTH, 1);
        String seasonstartDatestr = sdf.format(startC.getTime());
        String seasonEndDatestr = sdf.format(endC.getTime());
        return new String[]{seasonstartDatestr, seasonEndDatestr};
    }
    // -----------------获取指定月份的第一天,最后一天
    // ---------------------------------------------------------------------------

    /**
     * 获取指定月份的第一天
     *
     * @param p_strDate 指定月份
     * @param p_format 日期格式
     * @return String 时间字符串
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static String getDateOfMonthBegin(String p_strDate, String p_format)
            throws ParseException {
        java.util.Date date = parseDate(p_format, p_strDate);
        return toStrDateFromUtilDateByFormat(date, "yyyy-MM") + "-01";
    }

    /**
     * util.Date型日期转化指定格式的字符串型日期
     *
     * @param p_date   Date
     * @param p_format String 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
     *                 格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
     * @return String
     * @Date: 2006-10-31
     */
    public static String toStrDateFromUtilDateByFormat(
            java.util.Date p_utilDate, String p_format) throws ParseException {
        String l_result = "";
        if (p_utilDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(p_format);
            l_result = sdf.format(p_utilDate);
        }
        return l_result;
    }

    /**
     * 获取指定月份的最后一天
     *
     * @param p_strDate 指定月份
     * @param p_formate 日期格式
     * @return String 时间字符串
     */
    public static String getDateOfMonthEnd(String p_strDate, String p_format)
            throws ParseException {
        java.util.Date date = parseDate(p_format, getDateOfMonthBegin(p_strDate, p_format));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return toStrDateFromUtilDateByFormat(calendar.getTime(), p_format);
    }

}