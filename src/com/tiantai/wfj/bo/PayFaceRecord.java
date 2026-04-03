package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 刷脸记录
 */
public class PayFaceRecord implements BaseBean {

    private String pftrkey;
    private String pftrID;
    private String sn;  //设备序列号
	private String journalNum;
	private Date tradeDate;
	private String  tradeMoney;//交易金额
    private String clerkSccid; //交易时设备绑定的业务员
	private String subCompanyID;//当时使用商户
	private String appID;//当时微信APPID
	private String storeRate;//费率
	private String source;//来源 scancode 扫码   face:刷脸
	private String openID;//微信用户ID
	private String  nickname;//微信昵称


	public String getPftrkey() {
		return pftrkey;
	}

	public void setPftrkey(String pftrkey) {
		this.pftrkey = pftrkey;
	}

	public String getPftrID() {
		return pftrID;
	}

	public void setPftrID(String pftrID) {
		this.pftrID = pftrID;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getClerkSccid() {
		return clerkSccid;
	}

	public void setClerkSccid(String clerkSccid) {
		this.clerkSccid = clerkSccid;
	}

	public String getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(String subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getStoreRate() {
		return storeRate;
	}

	public void setStoreRate(String storeRate) {
		this.storeRate = storeRate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
