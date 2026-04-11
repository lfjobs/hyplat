package hy.ea.util.elkc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtilElkc {
	private static final Logger logger = LoggerFactory.getLogger(DateUtilElkc.class);
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DateUtilElkc.class);
	public static String DatePattern = "yyyy-MM-dd";
	public static String TimePattern = "HH:mm:ss";
	public static String DateTimePattern = "yyyy-MM-dd HH:mm:ss";

	public DateUtilElkc() {
	}

	public static String getDateTimePattern() {
		return getDatePattern() + " " + getTimePattern();
	}

	public static Date getCurrentDateTime() {
		try {
			SimpleDateFormat e = new SimpleDateFormat(DateTimePattern);
			String strDate = e.format(new Date());
			logger.info("值：{}", strDate);
			return convertStringToDate(DateTimePattern, strDate);
		} catch (Exception e) {
			logger.error("操作异常", e);
			return null;
		}
	}

	public static Date getCurrentDate() {
		try {
			SimpleDateFormat e = new SimpleDateFormat(DatePattern);
			String strDate = e.format(new Date());
			logger.info("值：{}", strDate);
			return convertStringToDate(DatePattern, strDate);
		} catch (Exception e) {
			logger.error("操作异常", e);
			return null;
		}
	}

	public static String getTimePattern() {
		return TimePattern;
	}

	public static synchronized String getDatePattern() {
		return DatePattern;
	}

	public static String toString(Date date) {
		return date != null?DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).format(date):null;
	}

	public static String getYear(Date date) {
		String strDate = getDate(date);
		return strDate.split("-").length > 0?strDate.split("-")[0]:"";
	}

	public static int getIntYear(Date date) {
		String year = getYear(date);
		return year.length() == 4?Integer.parseInt(year):1900;
	}

	public static String getMonth(Date date) {
		String strDate = getDate(date);
		return strDate.split("-").length > 1?strDate.split("-")[1]:"";
	}

	public static String getDay(Date date) {
		String strDate = getDate(date);
		return strDate.split("-").length > 2?strDate.split("-")[2]:"";
	}

	public static Date toDate(String str) {
		if(str != null) {
			try {
				return DateFormat.getDateInstance(2, Locale.CHINESE).parse(str);
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}

		return null;
	}

	public static Date toDateTime(String str) {
		if(str != null) {
			try {
				return DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).parse(str);
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}

		return null;
	}

	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if(aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return returnValue;
	}

	public static final String generateDateString(Date date) {
		return generateDateString(date, "yyyyMMddHHmmssSSSS");
	}

	public static final String generateDateString(Date date, String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if(date != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}

		return returnValue;
	}

	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if(log.isDebugEnabled()) {
			log.debug("converting \'" + strDate + "\' to date with mask \'" + aMask + "\'");
		}

		if(StringUtils.isBlank(strDate)) {
			return null;
		} else {
			try {
				date = df.parse(strDate);
				return date;
			} catch (ParseException e) {
				logger.error("操作异常", e);
				throw new ParseException(e.getMessage(), e.getErrorOffset());
			}
		}
	}

	public static String getTimeNow(Date theTime) {
		return getDateTime(TimePattern, theTime);
	}

	public static String getDateTime(Date theTime) {
		return getDateTime(DateTimePattern, theTime);
	}

	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = df.format(today);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if(aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return returnValue;
	}

	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if(log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
			return aDate;
		} catch (ParseException e) {
			log.error("Could not convert \'" + strDate + "\' to a date, throwing exception");
			logger.error("操作异常", e);
			throw new ParseException(e.getMessage(), e.getErrorOffset());
		}
	}

	public static String getDateStr4Log() {
		return "[" + getDateTime(getDateTimePattern(), new Date()) + "] ";
	}

	public static final Date parseNewTime(Date startTime, int field, int difference) {
		if(startTime != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(startTime);
			c1.add(field, difference);
			return c1.getTime();
		} else {
			return null;
		}
	}

	public static final Date getDateMinusFourYear(Date startTime, int field) {
		if(startTime != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(startTime);
			c1.add(field, 4);
			c1.add(5, -1);
			return c1.getTime();
		} else {
			return null;
		}
	}

	public static int getDay(Date firstDate, Date secondDate) {
		int day = (int)((firstDate.getTime() - secondDate.getTime()) / 86400000L);
		return day;
	}

	public static long getDifftime(Date date1, Date date2, String diffType) {
		long dd = 86400000L;
		long hh = 3600000L;
		long mi = 60000L;
		long diff = date1.getTime() - date2.getTime();
		if(StringUtils.isNotBlank(diffType)) {
			if("dd".equals(diffType)) {
				return diff / dd;
			}

			if("hh".equals(diffType)) {
				return diff / hh;
			}

			if("mi".equals(diffType)) {
				return diff / mi;
			}
		}

		return diff;
	}

	public static String getDistanceTime(Date date1, Date date2) {
		long day = 0L;
		long hour = 0L;
		long min = 0L;
		String rDiff = "";
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long diff;
		if(time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}

		day = diff / 86400000L;
		hour = diff / 3600000L - day * 24L;
		min = diff / 60000L - day * 24L * 60L - hour * 60L;
		if(day > 0L) {
			rDiff = day + "天";
		} else if(hour > 0L) {
			rDiff = hour + "小时";
		} else if(min > 0L) {
			rDiff = min + "分钟";
		}

		return rDiff;
	}

	public static Date getLastDayOfMonth(Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		int lastDay = cDay1.getActualMaximum(5);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}

	public static Date getMonthFirstDay(Date sDate1) {
		Calendar cal = Calendar.getInstance();
		Calendar f = (Calendar)cal.clone();
		f.clear();
		cal.setTime(sDate1);
		f.set(1, cal.get(1));
		f.set(2, cal.get(2));
		return f.getTime();
	}

	public static Date getYearFirstDay(Date date) throws Exception {
		String year = getYear(date);
		year = year + "-01-01";
		return convertStringToDate(year);
	}

	public static Date getyearEndDay(Date date) throws Exception {
		String year = getYear(date);
		year = year + "-12-31";
		return convertStringToDate(year);
	}

	public static Date getdaystart() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(13, 0);
		cal.set(12, 0);
		Date date = new Date(cal.getTimeInMillis());
		return date;
	}

	public static Date getdayend() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 23);
		cal.set(13, 59);
		cal.set(12, 59);
		Date date = new Date(cal.getTimeInMillis());
		return date;
	}

	public static Date getdaystart(Date date, int hours, int second, int minute) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, hours);
		cal.set(13, second);
		cal.set(12, minute);
		Date newDate = new Date(cal.getTimeInMillis());
		return newDate;
	}

	public static Date addmonth(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(2, num);
		return c.getTime();
	}

	public static Date addminute(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(12, num);
		return c.getTime();
	}

	public static Date addday(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(5, num);
		return c.getTime();
	}

	public static Date addyear(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(1, num);
		return c.getTime();
	}

	public static String getDateTimeByPattern(Date theTime, String pattern) {
		return getDateTime(pattern, theTime);
	}

	public static Date getDateTimeByPattern(String datestr, String pattern) {
		try {
			SimpleDateFormat e = new SimpleDateFormat(pattern);
			String strDate = e.format(datestr);
			logger.info("值：{}", strDate);
			return convertStringToDate(pattern, strDate);
		} catch (Exception e) {
			logger.error("操作异常", e);
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		logger.info("调试信息");
	}
}
