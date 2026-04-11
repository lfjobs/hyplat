package com.tiantai.nwa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 有关时间和字符串(日期时间格式)之间的转换函数功能类
 * @author zhanghaibin
 * @version 1.0
 * <br/>2011-1-17 下午02:25:54
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	private static final SimpleDateFormat SIMPLEDATEFORMAT = (SimpleDateFormat)DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat SIMPLEDATEFORMAT_WITH_FORMAT = (SimpleDateFormat)DateFormat.getDateTimeInstance();
	private static final SimpleDateFormat SIMPLEDATEFORMAT_CHINA = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINESE);
	private static final SimpleDateFormat SIMPLEDATEFORMAT_ENGLISH = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.ENGLISH);
	
	public static final String Date_Format_YYYYMMDD = "yyyy-MM-dd"; 
	public static final String Date_Format_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String Date_Format_HHMMSS = "HH:mm:ss";
	
	public static final String Date_Parse_Format_YYYYMMDD = "yyyyMMdd";
	public static final String Date_Parse_Format_YYYYMMDD_HHMMSS = "yyyy-mm-dd hh:mm:ss.ffffff";
	/**
	 * 
	 * @return
	 */
	private static String getDateTimeWithFormat()
	{
		return SIMPLEDATEFORMAT_WITH_FORMAT.format(Calendar.getInstance().getTime());
	}
	
	private static String getDateTimeWithFormat(Date date)
	{
		return SIMPLEDATEFORMAT_WITH_FORMAT.format(date);
	}
	
	/**
	 * 
	 * @param format
	 */
	private static void setFormat(String format)
	{
		SIMPLEDATEFORMAT_WITH_FORMAT.applyPattern(format);
	}
	/**
	 * 
	 * @return
	 */
	public static String getDefaultDateTime()
	{
		return SIMPLEDATEFORMAT.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getChinaDateTime()
	{
		return SIMPLEDATEFORMAT_CHINA.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getEnglishDateTime()
	{
		return SIMPLEDATEFORMAT_ENGLISH.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateTimeByFormat(String format)
	{
		setFormat(format);
		return getDateTimeWithFormat();
	}
	
	/**
	 * 
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static String formatDateTime(String dateTime,String format)
	{
		setFormat(format);	
		try {
			return getDateTimeWithFormat(SIMPLEDATEFORMAT_WITH_FORMAT.parse(dateTime));
		} catch (ParseException e) {			
			logger.error("操作异常", e);
			return null;
		}
	}
	/**
	 * 
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static Date convertDateTime(String dateTime,String format)
	{
		if (null==dateTime || "".equals(dateTime.trim())) return null;
		setFormat(format);	
		try {
			return SIMPLEDATEFORMAT_WITH_FORMAT.parse(dateTime);
		} catch (ParseException e) {			
			logger.error("操作异常", e);
			return null;
		}
	}	
	
	public static void main(String[] args) {		
//		logger.info("调试信息");
//		logger.info("调试信息");
//		logger.info("调试信息");
//		logger.info("调试信息");		
//		logger.info("调试信息");
		
		String a = "22|735001040016552";
		String[] arr  = a.split("\\|");		
		logger.info("调试信息");
		for (int i = 0; i < arr.length; i++) {
			logger.info("调试信息");			
		}
	}
}
