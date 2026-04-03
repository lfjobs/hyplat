package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 刷脸支付设备
 */
public class PayFaceDevice implements BaseBean,ExcelBean,java.io.Serializable {

   private String pfdkey;
   private String pfdID;
   private String sn;  //设备序列号
   private String model; //设备型号
	 private String bindID; //绑定设备人ID
	private String bindName;//绑定设备姓名
	private Date bindDate;//绑定日期

   private String factoryID;//厂家ID
   private String factoryName;//厂家名称

   private String source; //设备来源 00：采购 01自研
   private String bindState; //和商户绑定状态
	private String activeState;//激活状态
   private String  tradeMoney;//这台设备总交易额
   private String  rakeMoney;//这台设备总返佣
   private String deviceType;//00 微信刷脸设备  01：支付宝刷脸设备

	private String companyID;//维护所有设备公司ID

	private String staffID;//负责人
	private String staffName;//负责人姓名

	private String inputID;//录入人员
	private String inputName;//录入人员姓名
	private Date createDate;  //录入时间

	private String clerkID; //业务员ID
	private String clerkName;//业务员姓名
	private String clerkSccid;//业务员微分金账号ID
	private String clearkAccount;//业务员微分金账号手机号




	public static String[] columnHeadings() {
		String[] titles = { "序号","设备序列号","设备型号","厂家名称","设备来源","和商户绑定状态","激活状态","累计交易额","累计返佣","设备类型","负责人","制定日期"};
		return titles;
	}

	public String[] properties() {

		String[] properties = {sn,model,factoryName,(source=="00"?"采购":"自研"),(bindState.equals("00")?"未绑定":"已绑定"),(activeState.equals("00")?"未激活":"已激活"),tradeMoney,rakeMoney,(deviceType.equals("00")?"微信设备":"支付宝设备"),staffName,String.format("%1$tF", createDate)};
		return properties;
	}
	public String getPfdkey() {
		return pfdkey;
	}

	public void setPfdkey(String pfdkey) {
		this.pfdkey = pfdkey;
	}

	public String getPfdID() {
		return pfdID;
	}

	public void setPfdID(String pfdID) {
		this.pfdID = pfdID;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFactoryID() {
		return factoryID;
	}

	public void setFactoryID(String factoryID) {
		this.factoryID = factoryID;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBindState() {
		return bindState;
	}

	public void setBindState(String bindState) {
		this.bindState = bindState;
	}

	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getRakeMoney() {
		return rakeMoney;
	}

	public void setRakeMoney(String rakeMoney) {
		this.rakeMoney = rakeMoney;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getInputID() {
		return inputID;
	}

	public void setInputID(String inputID) {
		this.inputID = inputID;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getClerkID() {
		return clerkID;
	}

	public void setClerkID(String clerkID) {
		this.clerkID = clerkID;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getClerkSccid() {
		return clerkSccid;
	}

	public void setClerkSccid(String clerkSccid) {
		this.clerkSccid = clerkSccid;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public String getClearkAccount() {
		return clearkAccount;
	}

	public void setClearkAccount(String clearkAccount) {
		this.clearkAccount = clearkAccount;
	}

	public String getBindID() {
		return bindID;
	}

	public void setBindID(String bindID) {
		this.bindID = bindID;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
}
