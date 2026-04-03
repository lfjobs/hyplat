package hy.ea.util.bean;

import hy.ea.util.URLConnIO;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class SendSMSnew {
	
	/**
	 * 发送短信
	 * @return
	 */
	public Map<String, String> sendSMS(){
		Logger log = LoggerFactory.getLogger(SendSMSnew.class);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;

		Map<String,String> result = new LinkedHashMap<String, String>();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = URLConnIO.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestData().toString());
			doc = db.parse(is);
			// returnstatus 返回状态值：成功返回Success 失败返回：Faild
			result.put("returnstatus", doc.getElementsByTagName("returnstatus").item(0).getFirstChild().getNodeValue());
			//返回信息e.g.用户名或密码不能为空
			result.put("message", doc.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
			//返余额
			result.put("remainpoint", doc.getElementsByTagName("remainpoint").item(0).getFirstChild().getNodeValue());
			//返回成功短信数
			result.put("successCounts", doc.getElementsByTagName("successCounts").item(0).getFirstChild().getNodeValue());
			//返回本次任务的序列ID;
			result.put("taskID", doc.getElementsByTagName("taskID").item(0).getFirstChild().getNodeValue());
			is.close();
		} catch (Exception e) {
			log.error("发送短信失败!---{}",e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}



	/**
	 *
	 * 获取平台短信在第三方剩余金额
	 * @return
	 */
	public Map<String, String> overage(){
		Logger log = LoggerFactory.getLogger(SendSMSnew.class);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;

		Map<String,String> result = new LinkedHashMap<String, String>();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputStream is = URLConnIO.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestOverageData().toString());
			doc = db.parse(is);
			// returnstatus 返回状态值：成功返回Success 失败返回：Faild
			result.put("returnstatus", doc.getElementsByTagName("returnstatus").item(0).getFirstChild().getNodeValue());
			//返余额
			result.put("payinfo", doc.getElementsByTagName("payinfo").item(0).getFirstChild().getNodeValue());
			//返回成功短信数
			result.put("overage", doc.getElementsByTagName("overage").item(0).getFirstChild().getNodeValue());
			//返回本次任务的序列ID;
			result.put("sendTotal", doc.getElementsByTagName("sendTotal").item(0).getFirstChild().getNodeValue());
			is.close();
		} catch (Exception e) {
			//log.error("获取余额失败!---{}",e.getMessage());
			e.printStackTrace();
		}

		return result;
	}




	/**
	 *
	 * 非法关键词验证
	 * @return
	 */
	public Map<String, String> checkkeyword(){
		Logger log = LoggerFactory.getLogger(SendSMSnew.class);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;

		Map<String,String> result = new LinkedHashMap<String, String>();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputStream is = URLConnIO.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestheckkeywordData().toString());
			doc = db.parse(is);

			//返结果
			result.put("message", doc.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());


			is.close();
		} catch (Exception e) {
			//log.error("获取余额失败!---{}",e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 构建提交信息参数
	 * @return
	 */
	private StringBuffer getRequestOverageData(){
		StringBuffer buf = new StringBuffer();
		buf.append("action=").append(this.action);
		buf.append("&userid=").append(this.userid);
		buf.append("&account=").append(this.account);
		buf.append("&password=").append(this.password);
		return buf;
	}


	/**
	 * 构建提交信息参数
	 * @return
	 */
	private StringBuffer getRequestheckkeywordData(){
		StringBuffer buf = new StringBuffer();
		buf.append("action=").append(this.action);
		buf.append("&userid=").append(this.userid);
		buf.append("&account=").append(this.account);
		buf.append("&password=").append(this.password);
		buf.append("&content=").append(this.content);

		return buf;
	}


	/**
	 * 获取余额
	 * @return
	 */
	private StringBuffer getRequestData(){
		StringBuffer buf = new StringBuffer();
		buf.append("action=").append(this.action);
		buf.append("&userid=").append(this.userid);
		buf.append("&account=").append(this.account);
		buf.append("&password=").append(this.password);
		buf.append("&mobile=").append(this.mobile);
		buf.append("&content=").append(this.content);
		buf.append("&sendTime=").append(this.sendTime);
		buf.append("&extno=").append(this.extno);


		return buf;
	}
	/**
	 * 发送短信编码方式
	 */ 
	private String encode = "utf-8";
	
	//服务器请求地址
    private String servicesRequestAddRess = "";
    
	//登录的用户名
    private String account;
    
    //登录的密码
    private String password;
    
    //发送的手机号码
    private String mobile;
    

    private String content;
    
    /**
     * 106行业用户发送短信时，指定一个扩展号码。
     * 如果不需要指定扩张号码，则传空。如果为非空，
     * 提交的扩展号，会覆盖掉用户在管理后台设置的扩展号码，
     * 所以如果需要在连接中提交了非空的扩展号，
     * 就需要针对各个运营商分别设置  
     * （扩展号必须为数字，否则忽略掉）
     */
    private String extno = "";   
    
    
    private String sendTime = "";
    
    
    //发送任务命令	设置为固定的:send
    private String action = "send";
    
    
    
    private String userid="10342";
    
   
    
	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 
	 * 
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
	 * @return 用户名
	 * <p><b>required</b><p>
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * @param account
	 * <p><b>required</b><p>
	 */
	public void setAccount(String account) {
		this.account = account;
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
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * @param mobile 要发送的号码，多个号码用英文逗号隔开
	 * <p><b>required</b><p> 
	 */
	public void setMobiles(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * @return 短信内容,发送时要在GB2312编码下两次URL
	 * <p><b>required</b><p>
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 
	 *  <p><b>required</b><p>
	 */
	public void setContent(String Content) {
		try {
			this.content = URLEncoder.encode(Content, this.getEncode());
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
	

	

}
