package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 
 * 
 * @author ttst_wk
 *新增退货跟换货表
 *2015年6月29日17:04:59thwldate
 */
public class TEshoptuihuan implements BaseBean {
	
	private String thKey;
    private String odID;//订单号
	private String thID;//退货ID
	private String thddID;//退货产品id
	private String ppjg;//退货产品价格  换货时没有价格
	private String ppsl;//产品数量
	private String thtqian;//产品金额
	private Date   thdate;//申请时间
	private String thtype;//状态
	private String thsm;//退货或者换货说明
	private String thstatus;//表示次信息是换货还是退货
	private String thuserID;//处理人ID
	private String thsqID;//申请人ID
	private String thwlID;//物流订单
	private Date   thwldate;//发货时间
	private Date   thcldate;//处理时间
	
	
	public String getThstatus() {
		return thstatus;
	}
	public void setThstatus(String thstatus) {
		this.thstatus = thstatus;
	}
	public Date getThcldate() {
		return thcldate;
	}
	public void setThcldate(Date thcldate) {
		this.thcldate = thcldate;
	}
	public String getOdID() {
		return odID;
	}
	public void setOdID(String odID) {
		this.odID = odID;
	}
	public String getThKey() {
		return thKey;
	}
	public void setThKey(String thKey) {
		this.thKey = thKey;
	}
	public String getThID() {
		return thID;
	}
	public void setThID(String thID) {
		this.thID = thID;
	}
	public String getThddID() {
		return thddID;
	}
	public void setThddID(String thddID) {
		this.thddID = thddID;
	}
	public String getPpjg() {
		return ppjg;
	}
	public void setPpjg(String ppjg) {
		this.ppjg = ppjg;
	}
	public String getPpsl() {
		return ppsl;
	}
	public void setPpsl(String ppsl) {
		this.ppsl = ppsl;
	}
	public String getThtqian() {
		return thtqian;
	}
	public void setThtqian(String thtqian) {
		this.thtqian = thtqian;
	}
	public Date getThdate() {
		return thdate;
	}
	public void setThdate(Date thdate) {
		this.thdate = thdate;
	}
	public String getThtype() {
		return thtype;
	}
	public void setThtype(String thtype) {
		this.thtype = thtype;
	}
	public String getThsm() {
		return thsm;
	}
	public void setThsm(String thsm) {
		this.thsm = thsm;
	}
	public String getThuserID() {
		return thuserID;
	}
	public void setThuserID(String thuserID) {
		this.thuserID = thuserID;
	}
	public String getThsqID() {
		return thsqID;
	}
	public void setThsqID(String thsqID) {
		this.thsqID = thsqID;
	}
	public String getThwlID() {
		return thwlID;
	}
	public void setThwlID(String thwlID) {
		this.thwlID = thwlID;
	}
	public Date getThwldate() {
		return thwldate;
	}
	public void setThwldate(Date thwldate) {
		this.thwldate = thwldate;
	}
	
	
	
	

}
