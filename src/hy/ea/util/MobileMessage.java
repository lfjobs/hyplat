package hy.ea.util;

import hy.ea.util.bean.SendSMSnew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 手机短消息工具类
 *
 * @author Administrator   通知类
 *
 */
@Component
public class MobileMessage {
	// 短信平台登录地址http://112.124.10.199:8888/sms.aspx

	private static final String basePath = "http://sms.37037.com/sms.aspx";
	private static final String account = "ttsw";
	private static final String password = "meng@123456";
	private static final String userid="10342";


	Logger log = LoggerFactory.getLogger(MobileMessage.class);

	//发送手机号码，多个号码之间以英文逗号分隔（get最多50个,post最多20000个）
	private String mobiles = "";

	//发送内容（需要UrlEncode）需加签名【签名】+内容
	private String message = "";

	@Autowired
	private SendSMSnew sendSMS;
	/**
	 * 设置电话号
	 *
	 * @param mobiles
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	/**
	 * 设置短信内容
	 *
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 外部调用发送短信
	 *
	 * @return
	 * @throws IOException
	 */
	String status = "";

//	public String sendMsg() throws IOException {
//		sendSMS.setMessage(message+"【胜威集团】");
//		sendSMS.setMobiles(mobiles);
//		sendSMS.setPassword(password);
//		sendSMS.setUsername(username);
//		sendSMS.setServicesRequestAddRess(basePath);
//		Map<String, String> map = sendSMS.sendSMS();
//
//        return map.get("errorcode");
//	}

//	public String sendMsg(String str) throws IOException {
//		sendSMS.setMessage(message+str);
//		sendSMS.setMobiles(mobiles);
//		sendSMS.setPassword(password);
//		sendSMS.setUsername(username);
//		sendSMS.setServicesRequestAddRess(basePath);
//		Map<String, String> map = sendSMS.sendSMS();
//
//        return map.get("errorcode");
//	}

	/**
	 * 升级短信接口
	 * @param str 签名 必填可自定义
	 * @return
	 * @throws IOException
	 */
	public String sendMsg(String str) throws IOException {
		List<String> sbtel= new ArrayList<String>();
		sbtel.add("15201234567");sbtel.add("18382085269");sbtel.add("18080891109");sbtel.add("15022369129");sbtel.add("17012345678");
		String[] tel = mobiles.split(",");
		String tels = "";
		for(int i = 0 ;i<tel.length;i++){
			if(!sbtel.contains(tel[i])){
				tels+=tel[i]+",";
			}

		}

		if(tels.endsWith(",")){
			tels = tels.substring(0,tels.length()-1);
		}
		if("".equals(tels)){
			return "no";
		}
		Map<String, String> map = null;
		try{

			sendSMS.setContent(str+message);
			sendSMS.setMobiles(tels);
			sendSMS.setPassword(password);
			sendSMS.setAccount(account);
			sendSMS.setAction("send");
			sendSMS.setUserid(userid);
			sendSMS.setServicesRequestAddRess(basePath);
			map = sendSMS.sendSMS();
		}catch(Exception e){
			e.printStackTrace();
		}

		return map.get("message");
	}
	public String sendMsg() throws IOException {
		Map<String, String> map = null;
		try{
			sendSMS.setContent("【数字地球】"+message);
			sendSMS.setMobiles(mobiles);
			sendSMS.setPassword(password);
			sendSMS.setAccount(account);
			sendSMS.setAction("send");
			sendSMS.setUserid(userid);
			sendSMS.setServicesRequestAddRess(basePath);
			map = sendSMS.sendSMS();
		}catch(Exception e){
			e.printStackTrace();
		}

		return map.get("message");
	}
	/**
	 * 构建短信内容，调用第三方api，发送短信
	 *
	 * @param status
	 * @return
	 * @throws IOException
	 */
	/*private boolean httpSend(String status) {
		Boolean isSend = false;
		GetMethod getMethod = null;

		StringBuffer url = new StringBuffer();
		// String md5Password = MD5Util.md5s(password);
		url.append(basePath);
		url.append(userName);
		url.append("&password=");
		url.append(password);
		url.append("&smstype=0&timerflag=0");
		url.append("&mobiles=" + mobiles);
		try {
			url.append("&message=" + URLEncoder.encode(message, "GB2312"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		HttpClient httpClient = new HttpClient();
		getMethod = new GetMethod(url.toString());

		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {

			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			System.out.println("httpClient.executeMethod返回状态" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				log.warn("MobileMessage -> Method failed:  "
						+ getMethod.getStatusLine());
			} else {
				this.status = getMethod.getResponseBodyAsString();
				isSend = true;
			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return isSend;
	}*/

	/**
	 *
	 * @param status
	 * @return
	 */
	public static String getStatusMSG(String status) {
		String reStr = "";
		if (status.equalsIgnoreCase("0")) {
			reStr = "成功";
		} else if (status.equalsIgnoreCase("1")) {
			reStr = "用户名不存在或密码错误！";
		} else if (status.equalsIgnoreCase("2")) {
			reStr = "连接写入的功能名称错误！";
		} else {
			reStr = "失败(错误代码:" + status + ")";
		}

		return reStr;
	}

	/**
	 * 根据发送命令返回的code,查找对应的desc节点
	 *
	 * @param xml
	 * @param name
	 * @return
	 */
	/*private String getXMLText(String xml, String name) {
		try {
			System.out.println("根据发送命令返回的code,查找对应的desc节点-------------" + xml);
			System.out.println("根据发送命令返回的code,查找对应的desc节点-------------" + name);
			Document document = null;
			document = (Document) DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			String xmlContent = root.elementText(name);
			return xmlContent;
		} catch (DocumentException e) {
			e.printStackTrace();
			return "解析异常";
		}
	}*/
}
