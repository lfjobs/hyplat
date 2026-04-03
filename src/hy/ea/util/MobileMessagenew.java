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
 * @author Administrator 广告类
 * 
 */
@Component
public class MobileMessagenew {
	// 短信平台登录地址http://112.124.10.199:8888/sms.aspx
	
	private static final String basePath = "http://sms.37037.com/sms.aspx";
	private static final String account = "ttst";
	private static final String password = "meng@123456";
	private static final String userid="10369";
	Logger log = LoggerFactory.getLogger(MobileMessagenew.class);

	private String mobile = "";
	private String content = "";
	@Autowired
    private SendSMSnew sendSMS;
	/**
	 * 设置电话号mobiles
	 * 
	 * @param
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 设置短信内容message
	 * 
	 * @param
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 外部调用发送短信
	 * 
	 * @return
	 * @throws IOException
	 */
	String status = "";


	public String sendMsg() throws IOException {
		Map<String, String> map = null;
		List<String> sbtel= new ArrayList<String>();
		sbtel.add("15201234567");sbtel.add("18382085269");sbtel.add("18080891109");sbtel.add("15022369129");sbtel.add("17012345678");
		String[] tel = mobile.split(",");
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

		try{
			sendSMS.setContent("【数字地球】"+content+"拒收请回复R");
			sendSMS.setMobile(mobile);
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
	 *
	 * 获取余下额
	 * @return
	 * @throws IOException
	 */
	public String queryOverage() throws IOException {
		Map<String, String> map = null;
		try{
			sendSMS.setPassword(password);
			sendSMS.setAccount(account);
			sendSMS.setAction("overage");
			sendSMS.setUserid(userid);
			sendSMS.setServicesRequestAddRess(basePath);
			map = sendSMS.overage();
		}catch(Exception e){
			e.printStackTrace();
		}

		return map.get("overage");
	}


	/**
	 *
	 * 非法关键词查询
	 * @return
	 * @throws IOException
	 */
	public String checkkeyword() throws IOException {
		Map<String, String> map = null;
		try{
			sendSMS.setPassword(password);
			sendSMS.setAccount(account);
			sendSMS.setAction("checkkeyword");
			sendSMS.setUserid(userid);
			sendSMS.setContent(content);
			sendSMS.setServicesRequestAddRess(basePath);
			map = sendSMS.checkkeyword();
		}catch(Exception e){
			e.printStackTrace();
		}

		return map.get("message");
	}





	/**
	 * 
	 * @param status
	 * @return
	 */
	public static String getStatusMSG(String status) {

//		if (status.equalsIgnoreCase("0")) {
//			reStr = "成功";
//		} else if (status.equalsIgnoreCase("1")) {
//			reStr = "用户名不存在或密码错误！";
//		} else if (status.equalsIgnoreCase("2")) {
//			reStr = "连接写入的功能名称错误！";
//		} else {
//			reStr = "失败(错误代码:" + status + ")";
//		}
              
		return status;
	}

}
