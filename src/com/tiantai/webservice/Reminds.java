package com.tiantai.webservice;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
 

@WebService(targetNamespace = "http://service.impf2010.com", name = "Reminds") 
public interface Reminds {

	/**
	 * 消息提醒
	 * @param remindType 提醒状态
	 * @param staffID 接收人id
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	String getRemind(@WebParam(name = "remindType", targetNamespace = "http://service.impf2010.com") String remindType,
			@WebParam(name = "staffID", targetNamespace = "http://service.impf2010.com") String staffID);
	/**
	 * 修改状态
	 * @param remindID 提醒消息id
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	String upRemind(@WebParam(name = "remindID", targetNamespace = "http://service.impf2010.com") String remindID);
	
	/**
	 * 登录
	 * @param userName 用户名
	 * @param loginPwd 密码
	 * @param companyIdentity 组织结构
	 * @return
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	String getLoginUser(@WebParam(name = "userName", targetNamespace = "http://service.impf2010.com") String userName,
			@WebParam(name = "loginPwd", targetNamespace = "http://service.impf2010.com") String loginPwd,
			@WebParam(name = "companyIdentity", targetNamespace = "http://service.impf2010.com") String companyIdentity);
	
	/**
	 * 历史数据
	 * @param staffID 责任人ID
	 * @return
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	String getReminds(@WebParam(name = "staffID", targetNamespace = "http://service.impf2010.com") String staffID);
}
