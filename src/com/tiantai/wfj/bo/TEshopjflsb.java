package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 *  新增积分表
 * @author ttst_wk
 * 2015年6月29日17:04:44
 *
 */
public class TEshopjflsb implements BaseBean {
	private String jfbkey;
	private String jfbid;//积分表ID
	private String jfblai;//积分来源/去向
	private String jfbms;//积分来源描述
	private String jfbsl;//多少积分
	private String jfbye;//剩余
	private Date   jfbdate;//使用积分时间
	public String getJfbkey() {
		return jfbkey;
	}
	public void setJfbkey(String jfbkey) {
		this.jfbkey = jfbkey;
	}
	public String getJfbid() {
		return jfbid;
	}
	public void setJfbid(String jfbid) {
		this.jfbid = jfbid;
	}
	public String getJfblai() {
		return jfblai;
	}
	public void setJfblai(String jfblai) {
		this.jfblai = jfblai;
	}
	public String getJfbms() {
		return jfbms;
	}
	public void setJfbms(String jfbms) {
		this.jfbms = jfbms;
	}
	public String getJfbsl() {
		return jfbsl;
	}
	public void setJfbsl(String jfbsl) {
		this.jfbsl = jfbsl;
	}
	public String getJfbye() {
		return jfbye;
	}
	public void setJfbye(String jfbye) {
		this.jfbye = jfbye;
	}
	public Date getJfbdate() {
		return jfbdate;
	}
	public void setJfbdate(Date jfbdate) {
		this.jfbdate = jfbdate;
	}
	

}
