package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * TEshopCustomer entity. @author MyEclipse Persistence Tools
 */

public class TEshopCustomer implements BaseBean ,java.io.Serializable{

	// Fields

	private String custkey;
	private String custid;
	private String staffid;
	private String account;//登录账号
	private String password;//密码
	private String paymentCode;//支付密码
	private String status;//金币账号状态 1为冻结 否则为空

	private String openId;//微信公众行号微信用户的openId
	private String qqId;//qqId授权登陆
	private String appOpenId;//微信elkcAPP应用ID
	private String weiboId;//微博id

	private String userId; //授权支付宝唯一标识
	private String nickName;//授权支付宝昵称
	private String weNickName;//授权微信昵称

	private String eqId;//e路快车QQ登录ID
	private String eOpenId;//e路快车微信登录ID
	private String eBlogId;//e路快车微博登录ID
	private String logOff="0";//是否注销

	//没用了
	private String recoveryquestion;//密码提示问题
	private String answer;//问题答案
	private String applogoned;//APP登录状态 --0下线 --1上线
	private String phoneweblogoned;//手机网页登录状态
	private String pcweblogoned;//PC网页登录状态
	private String wxlogoned;//微信登录状态
	private String jiguangNo; //极光推送的设备id
	private String source;  //注册时判别是否来自单车
	private String vendingMachineAdminLogoned;//自助贩卖机管理员登录状态
	// Constructors

	/** default constructor */
	public TEshopCustomer() {
	}

	public TEshopCustomer(String custkey, String custid, String staffid, String account, String password, String paymentCode,
						  String status, String recoveryquestion, String answer, String applogoned, String phoneweblogoned, String pcweblogoned,
						  String wxlogoned, String jiguangNo, String source,String logOff) {
		this.custkey = custkey;
		this.custid = custid;
		this.staffid = staffid;
		this.account = account;
		this.password = password;
		this.paymentCode = paymentCode;
		this.status = status;
		this.recoveryquestion = recoveryquestion;
		this.answer = answer;
		this.applogoned = applogoned;
		this.phoneweblogoned = phoneweblogoned;
		this.pcweblogoned = pcweblogoned;
		this.wxlogoned = wxlogoned;
		this.jiguangNo = jiguangNo;
		this.source = source;
		this.logOff=logOff;
	}



	/** full constructor */


	// Property accessors
	public String getLogOff() {
		return logOff;
	}

	public void setLogOff(String logOff) {
		this.logOff = logOff;
	}
	public String getCustkey() {
		return this.custkey;
	}

	public void setCustkey(String custkey) {
		this.custkey = custkey;
	}


	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecoveryquestion() {
		return this.recoveryquestion;
	}

	public void setRecoveryquestion(String recoveryquestion) {
		this.recoveryquestion = recoveryquestion;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getApplogoned() {
		return this.applogoned;
	}

	public void setApplogoned(String applogoned) {
		this.applogoned = applogoned;
	}

	public String getPhoneweblogoned() {
		return this.phoneweblogoned;
	}

	public void setPhoneweblogoned(String phoneweblogoned) {
		this.phoneweblogoned = phoneweblogoned;
	}

	public String getPcweblogoned() {
		return this.pcweblogoned;
	}

	public void setPcweblogoned(String pcweblogoned) {
		this.pcweblogoned = pcweblogoned;
	}

	public String getWxlogoned() {
		return this.wxlogoned;
	}

	public void setWxlogoned(String wxlogoned) {
		this.wxlogoned = wxlogoned;
	}
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJiguangNo() {
		return jiguangNo;
	}

	public void setJiguangNo(String jiguangNo) {
		this.jiguangNo = jiguangNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getAppOpenId() {
		return appOpenId;
	}

	public void setAppOpenId(String appOpenId) {
		this.appOpenId = appOpenId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWeNickName() {
		return weNickName;
	}

	public void setWeNickName(String weNickName) {
		this.weNickName = weNickName;
	}

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public String geteOpenId() {
		return eOpenId;
	}

	public void seteOpenId(String eOpenId) {
		this.eOpenId = eOpenId;
	}

	public String geteBlogId() {
		return eBlogId;
	}

	public void seteBlogId(String eBlogId) {
		this.eBlogId = eBlogId;
	}

	public String getVendingMachineAdminLogoned() {
		return vendingMachineAdminLogoned;
	}

	public void setVendingMachineAdminLogoned(String vendingMachineAdminLogoned) {
		this.vendingMachineAdminLogoned = vendingMachineAdminLogoned;
	}
}