package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 *wk
 * 新增佣金流水表
 * 2015年6月29日17:05:14
 * 
 */
public class TEshopyjlsb implements BaseBean {
	private String yjbKey;
	private String yjbID;// 流水表ID
	private String yjbly;//流水表来源或者去向
	private String yjbms;//来源描述
	private String yjbqian;//金额
	private String yjbye;//余额
	private Date   yjbdate;//时间 
	public String getYjbKey() {
		return yjbKey;
	}
	public void setYjbKey(String yjbKey) {
		this.yjbKey = yjbKey;
	}
	public String getYjbID() {
		return yjbID;
	}
	public void setYjbID(String yjbID) {
		this.yjbID = yjbID;
	}
	public String getYjbly() {
		return yjbly;
	}
	public void setYjbly(String yjbly) {
		this.yjbly = yjbly;
	}
	public String getYjbms() {
		return yjbms;
	}
	public void setYjbms(String yjbms) {
		this.yjbms = yjbms;
	}
	public String getYjbqian() {
		return yjbqian;
	}
	public void setYjbqian(String yjbqian) {
		this.yjbqian = yjbqian;
	}
	public String getYjbye() {
		return yjbye;
	}
	public void setYjbye(String yjbye) {
		this.yjbye = yjbye;
	}
	public Date getYjbdate() {
		return yjbdate;
	}
	public void setYjbdate(Date yjbdate) {
		this.yjbdate = yjbdate;
	}
	
	
}
