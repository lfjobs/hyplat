package hy.ea.util.bean;
 
import hy.ea.util.MobileMessage;
import hy.ea.util.URLConnIO;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @类名称:SecondSMS
 * @类描述:发送短信;  
 * @author yaloo
 * @desc 请参见\\192.168.0.100\扩展硬盘\共享\八优短信HTTP网接口文档.doc
 * @技术支持 
 */
@Component
public class SendSMS {
	
	/**
	 * 发送短信
	 * @return
	 */
	public Map<String, String> sendSMS(){
		Logger log = LoggerFactory.getLogger(SendSMS.class);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;
		
		Map<String,String> result = new LinkedHashMap<String, String>();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = URLConnIO.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestData().toString());
			doc = db.parse(is);
			result.put("errorcode", doc.getElementsByTagName("errorcode").item(0).getFirstChild().getNodeValue());
			result.put("errordescription", doc.getElementsByTagName("errordescription").item(0).getFirstChild().getNodeValue());
			result.put("time", doc.getElementsByTagName("time").item(0).getFirstChild().getNodeValue());
			result.put("msgcount", doc.getElementsByTagName("msgcount").item(0).getFirstChild().getNodeValue()); 
			is.close();
		} catch (Exception e) {
			log.error("发送短信失败!---{}",e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 构建提交信息参数
	 * @return
	 */
	private StringBuffer getRequestData(){
		StringBuffer buf = new StringBuffer();
		buf.append("func=sendsms");
		buf.append("&username=").append(this.username);
		buf.append("&password=").append(this.password);
		buf.append("&smstype=").append(this.smstype);
		buf.append("&timerflag=").append(this.timerflag);
		if(this.timerflag != 0){
			buf.append("&timervalue=").append(this.timervalue);
		}
		buf.append("&extno=").append(this.extno);
		buf.append("&timertype=").append(this.timertype);
		buf.append("&timerid=").append(this.timerid);
		buf.append("&mobiles=").append(this.mobiles);
		buf.append("&message=").append(this.message);
		
		return buf;
	}
	
	/**
	 * 发送短信编码方式
	 */ 
	private String encode = "GB2312";
	
	//服务器请求地址
    private String servicesRequestAddRess = "";
    
	//登录的用户名
    private String username;
    
    //登录的密码
    private String password;
    
    //发送的手机号码
    private String mobiles;
    
    //短信内容,发送时要在GB2312编码下两次URL
    private String message;
    
    /**
     * 106行业用户发送短信时，指定一个扩展号码。
     * 如果不需要指定扩张号码，则传空。如果为非空，
     * 提交的扩展号，会覆盖掉用户在管理后台设置的扩展号码，
     * 所以如果需要在连接中提交了非空的扩展号，
     * 就需要针对各个运营商分别设置  
     * （扩展号必须为数字，否则忽略掉）
     */
    private String extno = "";   
    
    /*
     * 短信发送方式
     *  0：表示 一个或多个号码对应一个短信内容
     *  1：表示 号码和内容一一对应，也就是多个号码对应多个内容
     */
    
    private int smstype = 0;
	
    /**
	 * 1 表示 本次提交的是定时信息
	 * 0 标识 本次提交的是非定时信息
	 * 如果设置 为0 那么和定时相关的所有的参数都会被忽略掉
	 * （发送定时信息时，必须提供）
	 */
    private int timerflag = 0;
    
    /**
     * 短信发送定时时间;
     * 格式：yyyy-mm-dd hh:mm:ss
     */
    private String timervalue;
    
    /**
     * 发送定时短信时需要提供定时的类型（发送定时时，必须提供此参数）
     * 1 只发一次 
     * 2 每天一次
     * 3 每周一次 
     * 4 每月一次 
     * 5 每年一次 
     */
    private int timertype = 1;
    
    /**
     * 每次定时提交的标识，定时发送时必须提供，可以默认为0，可重复
     * 此标识主要用来 调用接口删除定时
     * 当要删除本次提交的定时时，只要提供该ID就可以删除了
     * 
     */
    private String timerid;
    
	/**
	 * 
	 * <p>default value is<b> GB2312</b><p>
	 * @param encode 设备发送短信编码方式 
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	/**
	 * @return the encode
	 * <p>default value is<b> GB2312</b><p>
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * @return 服务器请求地址
	 * <p><b>required</b><p>
	 */
	public String getServicesRequestAddRess() {
		return servicesRequestAddRess;
	}
	
	/**
	 * @param servicesRequestAddRess 服务器请求地址
	 * <p><b>required</b><p>
	 */
	public void setServicesRequestAddRess(String servicesRequestAddRess) {
		this.servicesRequestAddRess = servicesRequestAddRess;
	}
	
	/**
	 * @return 用户名
	 * <p><b>required</b><p>
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username 用户名 
	 * <p><b>required</b><p>
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * <p><b>required</b><p>
	 * @return 加密密码
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * <p><b>required</b><p>
	 * @param password 加密密码 
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return 要发送的号码，多个号码用 <b><i>英文逗号',' </i> </b> 隔开
	 * <p><b>required</b><p>
	 */
	public String getMobiles() {
		return mobiles;
	}
	
	/**
	 * @param mobiles 要发送的号码，多个号码用英文逗号隔开
	 * <p><b>required</b><p> 
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	
	/**
	 * @return 短信内容,发送时要在GB2312编码下两次URL
	 * <p><b>required</b><p>
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message 短信内容,发送时要在GB2312编码下两次URL 
	 *  <p><b>required</b><p>
	 */
	public void setMessage(String message) {
		try {
			this.message = URLEncoder.encode(message, this.getEncode());
		} catch (UnsupportedEncodingException e) { 
			Log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * @return 106行业用户发送短信时，指定一个扩展号码
	 */
	public String getExtno() {
		return extno;
	}
	
	/**
	 * @param extno 106行业用户发送短信时，指定一个扩展号码
	 *  <p><b>not required</b><p>
	 */
	public void setExtno(String extno) {
		this.extno = extno;
	}
	
	/**
	 * @return 指定短信和号码的对应方式
	 *  0：表示 一个或多个号码对应一个短信内容
     *  1：表示 号码和内容一一对应，也就是多个号码对应多个内容
	 */
	public int getSmstype() {
		return smstype;
	}
	
	/**
	 * @param smstype 指定短信和号码的对应方式
	 *  <p><b>not required</b><p>
	 *  0：表示 一个或多个号码对应一个短信内容<br/>
     *  1：表示 号码和内容一一对应，也就是多个号码对应多个内容
     *  <p>default value <b>0</b></p>
	 */
	public void setSmstype(int smstype) {
		this.smstype = smstype;
	}
	
	/**
	 * @return the timerflag
	 * 
	 * 1 表示 本次提交的是定时信息
	 * 0 标识 本次提交的是非定时信息
	 * 如果设置 为0 那么和定时相关的所有的参数都会被忽略掉
	 * （发送定时信息时，必须提供）
	 * <p>default value <b>0</b></p>
	 */
	public int getTimerflag() {
		return timerflag;
	}
	
	/**
	 * @param timerflag 
	 *  <p><b>not required</b><p>
	 * 1 表示 本次提交的是定时信息
	 * 0 标识 本次提交的是非定时信息
	 * 如果设置 为0 那么和定时相关的所有的参数都会被忽略掉
	 * （发送定时信息时，必须提供）
	 */
	public void setTimerflag(int timerflag) {
		this.timerflag = timerflag;
	}
	
	/**
	 * @return the timervalue 
	 * 短信发送定时时间
	 */
	public String getTimervalue() {
		return timervalue;
	}
	
	/**
	 * @param timervalue the timervalue
	 * 
	 * 短信发送定时时间<br/>
     * 格式：yyyy-mm-dd hh:mm:ss
	 * 
	 */
	public void setTimervalue(String timervalue) {
		this.timervalue = timervalue;
	}
	
	/**
	 * @return the timertype
	 */
	public int getTimertype() {
		return timertype;
	}
	
	/**
	 * @param timertype the timertype
	 * <p><b>not required</b><p> 
	 * 发送定时短信时需要提供定时的类型（发送定时时，必须提供此参数）<br/>
     * 1 只发一次<br/> 
     * 2 每天一次<br/>
     * 3 每周一次<br/> 
     * 4 每月一次<br/> 
     * 5 每年一次
     * <p>default value <b>1</b></p>
	 */
	public void setTimertype(int timertype) {
		this.timertype = timertype;
	}
	/**
	 * @return the timerid
	 */
	public String getTimerid() {
		return timerid;
	}
	/**
	 * @param timerid the timerid
	 * <p><b>not required</b><p>
	 * <p>每次定时提交的标识，定时发送时必须提供，可以默认为0，可重复</p>
     * <p>此标识主要用来 调用接口删除定时</p>
     * <p>当要删除本次提交的定时时，只要提供该ID就可以删除了</p>
	 */
	public void setTimerid(String timerid) {
		this.timerid = timerid;
	}
	/*----------升级版----------*/
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}

		return result;
	}

	public static void main(String[] args)  {

//        MobileMessage mm = new MobileMessage();
//        mm.setMessage("测试");
//        mm.setMobiles("15041528152");
//        try {
//           String s = mm.sendMsg();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //发送 GET 请求
//		String url="http://211.149.174.186:16668/SendSms.asp";
//		String account="15810799888";
//		String password="799888";
//		String phones="15041528152";
//		String content=URLEncoder.encode("【微分金平台】你好吗？", "gb2312");
//		String channel="1";
//		String request="Account="+account+"&Password="+password+"&Phones="+phones+"&Content="+content+"&Channel="+channel;
//		String ret1=SendSMS.sendGet(url, request);
//		System.out.println(ret1);
//		//发送 POST 请求
//		String ret2=SendSMS.sendPost(url, request);
//		System.out.println(ret2);
	}
}
