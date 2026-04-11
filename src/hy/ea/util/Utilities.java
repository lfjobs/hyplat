package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.util.bean.MailConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Utilities {
	private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
	
	private static Utilities utilities;
	private JavaMailSenderImpl jmail;
	private Properties pp;
	MimeMessageHelper messageHelper;

	/**
	 * 将String转换成int
	 * 
	 * @param sNumber
	 * @return
	 */
	public static int getIntFormString(String sNumber) {
		int rNumber = 0;
		try {
			rNumber = Integer.parseInt(sNumber);
		} catch (Exception e) {

		}
		return rNumber;
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		int begin = 0;
		boolean once = true;
		if (str == null || str.trim().equals("")) {
			return false;
		}
		str = str.trim();
		if (str.startsWith("+") || str.startsWith("-")) {
			if (str.length() == 1) {
				// "+" "-"
				return false;
			}
			begin = 1;
		}
		for (int i = begin; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				if (str.charAt(i) == '.' && once) {
					// '.' can only once
					once = false;
				} else {
					return false;
				}
			}
		}
		if (str.length() == (begin + 1) && !once) {
			// "." "+." "-."
			return false;
		}
		return true;
	}

	/**
	 * MD5加密
	 * 
	 * @return
	 */
	public final static String MD5(String s) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			return compute(mdTemp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 2次加密
	 * 
	 * @param mad5
	 * @return
	 */
	public static String compute(MessageDigest mad5) {
		String inStr = "ycqazwsxv";
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = mad5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String getConfigFilesRootPath() {
		String rootPath = "";
		rootPath = Utilities.class.getClassLoader().getResource("hy/plat/bo")
				.getPath();
		try {
			rootPath = java.net.URLDecoder.decode(rootPath, "utf-8");
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return rootPath;
	}

	/**
	 * 时间的Date变成String
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateString(Date date, String format) {
		String result = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			result = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return result;
	}

	/**
	 * 时间的String变成Date
	 * 
	 * @param formatDate
	 *            日期
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date getDateFromString(String formatDate, String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format,
				java.util.Locale.US);
		Date date = new Date();
		try {
			date = sdFormat.parse(formatDate);
		} catch (ParseException e) {
			logger.error("操作异常", e);
		}
		return date;
	}

	/**
	 * 时间格式字符串 去 0  
	 * 2013-03-06 11:49:35   转换 2013-3-6 11:49:35
	 * @author lwz
	 * @return
	 */
	public static String getStrDateStr(String oldDateStr){
		String newDateStr = "";
		try{
			if(oldDateStr.substring(5, 6).equals("0")){
				newDateStr +=  oldDateStr.substring(0, 5) + oldDateStr.substring(6, 8);
			}else{
				newDateStr +=  oldDateStr.substring(0, 8);
			}
			if(oldDateStr.substring(8, 9).equals("0")){
				newDateStr +=  oldDateStr.substring(9);
			}else{
				newDateStr +=  oldDateStr.substring(8);
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return newDateStr;
	}
	/**
	 * 时间格式字符串 加 0
	 * 2013-3-6  转换  2013-03-06 
	 * @param oldDate
	 * @author lwz
	 * @return
	 */
	public static String getStrDateStrs(String oldDate){
		String newDate = "";
		try {
			if(oldDate.lastIndexOf("-") != 7){
				newDate = oldDate.substring(0, 5)+"0"+oldDate.substring(5, 6);
				if(oldDate.substring(7).length() != 2){
					newDate += "-0"+oldDate.substring(7);
				}else{
					newDate += oldDate.substring(6);
				}
			}else{
				newDate = oldDate.substring(0, 7);
				if(oldDate.substring(8).length() != 2){
					newDate += "-0"+oldDate.substring(8);
				}else{
					newDate += oldDate.substring(7);
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		
		
		return newDate;
	}
	
	/**
	 * 拿去days后的时间
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAfterDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
	
	
	/**
	 * 返回每个月的天数
	 * @param year
	 * @param mouth
	 * @return
	 */
	public static int getDayMouth(int year,int mouth){
	        int num=0;
	        switch(mouth){
	            case 1:
	            case 3:
	            case 5:
	            case 7:
	            case 8:
	            case 10:
	            case 12:
	                num=31;
	                break;
	            case 4:
	            case 6:
	            case 9:
	            case 11:
	                num=30;
	                break;
	            case 2:
	                num = isLeapYear(year)?29:28;
	                break;
	            default:
	                logger.info("非法月份");
	                break;
	            }
	        return num;
	        }
	    
	      /**
	       * 方法判断指定的年份是否为闰年
	       * @param year
	       * @return
	       */
	     public static boolean isLeapYear(int year){  
	        if((year%4==0)&&(year%100!=0)||(year%400==0))
	        return true;
	        else
	        return false;
	     }
	     
	     
	     /**
	      * 判断是时间是不是在这个时间段内(String类型)
	      * @param ntime 当前时间
	      * @param stime 时间段起时间
	      * @param etime 时间段终止时间
	      * @param format 时间格式
	      * @return true：在时间段内，false：不在时间段内
	      */
	     public static boolean isPeriodTime(String ntime,String stime,String etime,String format){
		   	  Calendar cal1 = Calendar.getInstance();
		   	  Calendar cal2 = Calendar.getInstance();
		   	  Calendar cal3 = Calendar.getInstance();
		   	  SimpleDateFormat sdf = new SimpleDateFormat(format);
		   	  try {
		   		cal1.setTime(sdf.parse(ntime));
		   		cal2.setTime(sdf.parse(stime));
		   		cal3.setTime(sdf.parse(etime));
		   	  } catch (ParseException e) {
		   		// TODO Auto-generated catch block
		   		logger.error("操作异常", e);
		   	  }
		   	  
		   	  if((cal2.before(cal1)&& cal3.after(cal1))||ntime.equals(stime)||ntime.equals(etime))
		   	    //在时间段内　
		   		return true;
		   	  else
		   		return false;
	     }



	public static int sendMail(MailConfiguration mailConfiguration) {
		int result = 0;
		try {

			Properties properties = new Properties();
			properties.put("mail.transport.protocol", mailConfiguration
					.getProtocol());
			properties.put("mail.smtp.host", mailConfiguration.getHost());
			properties.put("mail.smtp.port", mailConfiguration.getPort());
			properties.put("mail.smtp.auth", mailConfiguration.getAuth());

			Session session = Session.getInstance(properties, null);

			Message message = new MimeMessage(session);
			message
					.setFrom(new InternetAddress(mailConfiguration
							.getMailFrom()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(mailConfiguration.getMailTo()));
			message.setSentDate(new Date());
			message.setSubject(mailConfiguration.getMsgSubject());

			message.setText(mailConfiguration.getMsgText());

			Transport transport = session.getTransport(mailConfiguration
					.getProtocol());
			transport.connect(mailConfiguration.getHost(), mailConfiguration
					.getAuthUser(), mailConfiguration.getAuthPassword());
			transport.sendMessage(message, message
					.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (Exception e) {
			result = 1;
			logger.error("操作异常", e);
		}

		return result;
	}

	public static MailConfiguration getMailConfiguration() {

		XMLReader xmlreader = new XMLReader(getConfigFilesRootPath()
				+ "jmail.xml");
		MailConfiguration mailConfiguration = new MailConfiguration();

		mailConfiguration.setProtocol(xmlreader.getElementvalue(
				"Configuration", "protocol"));
		mailConfiguration.setHost(xmlreader.getElementvalue("Configuration",
				"host"));
		mailConfiguration.setPort(xmlreader.getElementvalue("Configuration",
				"port"));
		mailConfiguration.setAuth(xmlreader.getElementvalue("Configuration",
				"auth"));
		mailConfiguration.setAuthUser(xmlreader.getElementvalue(
				"Configuration", "authUser"));
		mailConfiguration.setAuthPassword(xmlreader.getElementvalue(
				"Configuration", "authPassword"));
		mailConfiguration.setMailFrom(xmlreader.getElementvalue(
				"Configuration", "mailFrom"));
		mailConfiguration.setMailTo(xmlreader.getElementvalue("Configuration",
				"mailTo"));
		mailConfiguration.setCopytTo(xmlreader.getElementvalue("Configuration",
				"copyTo"));
		mailConfiguration.setMsgSubject(xmlreader.getElementvalue(
				"Configuration", "msgSubject"));
		mailConfiguration.setMsgText(xmlreader.getElementvalue("Configuration",
				"msgText"));
		mailConfiguration.setReplyMail(xmlreader.getElementvalue(
				"Configuration", "replyMail"));
		mailConfiguration.setReplyPhone(xmlreader.getElementvalue(
				"Configuration", "replyPhone"));
		mailConfiguration.setPetSystemPassword(xmlreader.getElementvalue(
				"Configuration", "petSystemPassword"));

		return mailConfiguration;
	}

	/**
	 * 反射对象值
	 * 
	 * @param newObject
	 * @param oldObject
	 */
	public static void copy(Object newObject, Object oldObject) {

		try {
			BeanUtils.copyProperties(newObject, oldObject);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
	}
	
	/**  
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零  
     * 要用到正则表达式  
    */ 
    public static String digitUppercase(Double n){  
        String fraction[] = {"角", "分"};  
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };  
        String unit[][] = {{"元", "万", "亿"},  
                     {"", "拾", "佰", "仟"}};  
        String head = n < 0? "负": "";  
        n = Math.abs(n);  
       String s = "";  
        for (int i = 0; i < fraction.length; i++) {  
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");  
        }  
        if(s.length()<1){  
           s = "整";      
        }  
        int integerPart = (int)Math.floor(n);  

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {  
            String p ="";  
           for (int j = 0; j < unit[1].length && n > 0; j++) {  
               p = digit[integerPart%10]+unit[1][j] + p;  
               integerPart = integerPart/10;  
            }  
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;  
       }  
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");  
   }  

	/**
	 * 计算器
	 * 
	 * @param f
	 * @return
	 */
	public static double Calculator(String f)// 科学计算
	{
		int i = 0, j = 0;
		char c;
		StringBuffer s = new StringBuffer();
		s.append(f);
		s.append('=');
		String formula = s.toString();
		char[] anArray;
		anArray = new char[50];
		Stack<Character> mystack = new Stack<Character>();
		while (formula.charAt(i) != '=') {
			c = formula.charAt(i);
			switch (c) {
			case '(':
				mystack.push(new Character(c));
				i++;
				break;
			case ')':
				while (mystack.peek().charValue() != '(') {
					anArray[j++] = mystack.pop().charValue();
				}
				mystack.pop();
				i++;
				break;
			case '+':
			case '-':
				while (!mystack.empty() && mystack.peek().charValue() != '(') {
					anArray[j++] = mystack.pop().charValue();

				}
				mystack.push(new Character(c));
				i++;
				break;
			case '*':
			case '/':
				while (!mystack.empty()
						&& (mystack.peek().charValue() == '*' || mystack.peek()
								.charValue() == '/')) {
					anArray[j++] = mystack.pop().charValue();
				}
				mystack.push(new Character(c));
				i++;
				break;
			case ' ':
				i++;
				break;
			default:
				while ((c >= '0' && c <= '9') || c == '.') {
					anArray[j++] = c;
					i++;
					c = formula.charAt(i);
				}
				anArray[j++] = '#';
				break;
			}
		}
		while (!(mystack.empty()))
			anArray[j++] = mystack.pop().charValue();
		i = 0;
		int count;
		double a, b, d;
		Stack<Double> mystack1 = new Stack<Double>();
		while (i < j) {
			c = anArray[i];
			switch (c) {
			case '+':
				a = mystack1.pop().doubleValue();
				b = mystack1.pop().doubleValue();
				d = b + a;
				mystack1.push(new Double(d));
				i++;
				break;
			case '-':
				a = mystack1.pop().doubleValue();
				b = mystack1.pop().doubleValue();
				d = b - a;
				mystack1.push(new Double(d));
				i++;
				break;
			case '*':
				a = mystack1.pop().doubleValue();
				b = mystack1.pop().doubleValue();
				d = b * a;
				mystack1.push(new Double(d));
				i++;
				break;
			case '/':
				a = mystack1.pop().doubleValue();
				b = mystack1.pop().doubleValue();
				if (a != 0) {
					d = b / a;
					mystack1.push(new Double(d));
					i++;
				} else {
					logger.info("Error!");
				}
				break;
			default:
				d = 0;
				count = 0;
				while ((c >= '0' && c <= '9')) {
					d = 10 * d + c - '0';
					i++;
					c = anArray[i];
				}
				if (c == '.') {
					i++;
					c = anArray[i];
					while ((c >= '0' && c <= '9')) {
						count++;
						d = d + (c - '0') / Math.pow(10, count);
						i++;
						c = anArray[i];
					}
				}
				if (c == '#')
					mystack1.push(new Double(d));
				i++;
				break;
			}
		}
		return (mystack1.peek().doubleValue());
	}

	/**
	 * unicode 转换成 中文
	 *
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len; ) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
										"Malformed  encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 发送邮件 title ：邮件标题 content:邮件内容 toMail:对方邮件EMail
	 */
	private Boolean sendMail(String title, String content, String toMail) {
		try {
			String username = ConfigUtil.getInstance(Constant.SYS_CONFIG_PATH)
					.getKeyValue("mail_Name");
			String password = ConfigUtil.getInstance(Constant.SYS_CONFIG_PATH)
					.getKeyValue("mail_Pass");
			String mailServer = ConfigUtil
					.getInstance(Constant.SYS_CONFIG_PATH).getKeyValue(
							"mail_Server");
			logger.info("调试信息");
			jmail = new JavaMailSenderImpl();
			jmail.setHost(mailServer);
			jmail.setUsername(username);
			jmail.setPassword(password);
			pp = new Properties();
			pp.setProperty("mail.smtp.auth", "true");
			jmail.setJavaMailProperties(pp);
			MimeMessage mailMessage = jmail.createMimeMessage();
			messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
			messageHelper.setTo(toMail);
			messageHelper.setText("<html><head></head><body>" + content
					+ "</body></html>", true);
			String from = username + "@" + mailServer.replace("smtp.", "");
			messageHelper.setFrom(from);
			messageHelper.setSubject(title);
			jmail.send(mailMessage);
			return true;
		} catch (Exception e) {
			logger.error("操作异常", e);
			return false;
		}

	}

	/*
	 * 调用邮件发送
	 */
	public static Boolean getReport(String title, String content, String toMail) {
		if (utilities == null)
			utilities = new Utilities();
		return utilities.sendMail(title, content, toMail);

	}
	/**
	 * 单据类型维护类
	 * @return
	 */
	public static List<String> getBillTypes() {
		List<String> list = new ArrayList<String>();
		list.add("订货单");
		list.add("费用报销（出/入库）单");
		list.add("收货单");
		list.add("异动单");
		list.add("客户跟踪单");
		list.add("固定资产低易品其他物品出入库异动责任清单");
		list.add("实物采购市场调查误差表");
		list.add("合同管理");
		list.add("预收款");
		list.add("税务票据单");
		list.add("应付工资");
		list.add("现金申请明细审批单");
		list.add("退（费、货）单");
		list.add("费用报销单");
		list.add("现金申请单");
		list.add("应付款单");
		list.add("应收款单");
		list.add("公司内部缴款单");
		list.add("代收款单");
		list.add("报名单");
		list.add("实物采购市场调查厂商统计表");
		list.add("出入库存异动责任清单");
		list.add("缴款单");
		list.add("收款单");
		list.add("咨询跟踪单");
		return list;
	}
    public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
        
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

       return htmlStr.trim(); //返回文本字符串 
    } 

}
