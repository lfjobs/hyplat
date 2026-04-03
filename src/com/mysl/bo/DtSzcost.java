package com.mysl.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMytask entity. @author MyEclipse Persistence Tools mz
 * 收支管理表
 */

public class DtSzcost implements java.io.Serializable,BaseBean{

	// Fields

	private String szkey;
	private String szid;
	private String proid;//项目ID
	private String costtype;//收款类型;00：应收，01已收
	private float money;//费用金额
	private String moneystr;
	private Date happenDate;//发生日期
	private String  payer;//付款人
	private String payee;//收款人
	private String purpose;//用途
	private String remark;//备注
	private Date createtime;
	
	
	

	// Constructors
	
	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	/** default constructor */
	public DtSzcost() {
	}


	public DtSzcost(String szkey, String szid, String proid, String costtype,
			float money, Date happenDate, String payer, String payee,
			String purpose, String remark) {
		super();
		this.szkey = szkey;
		this.szid = szid;
		this.proid = proid;
		this.costtype = costtype;
		this.money = money;
		this.happenDate = happenDate;
		this.payer = payer;
		this.payee = payee;
		this.purpose = purpose;
		this.remark = remark;
	}


	public String getSzkey() {
		return szkey;
	}


	public void setSzkey(String szkey) {
		this.szkey = szkey;
	}


	public String getSzid() {
		return szid;
	}


	public void setSzid(String szid) {
		this.szid = szid;
	}


	public String getProid() {
		return proid;
	}


	public void setProid(String proid) {
		this.proid = proid;
	}


	public String getCosttype() {
		return costtype;
	}


	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}


  


	public float getMoney() {
		return money;
	}


	public void setMoney(float money) {
		this.money = money;
	}


	public Date getHappenDate() {
		return happenDate;
	}


	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}


	public String getPayer() {
		return payer;
	}


	public void setPayer(String payer) {
		this.payer = payer;
	}


	public String getPayee() {
		return payee;
	}


	public void setPayee(String payee) {
		this.payee = payee;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getMoneystr() {
		return moneystr;
	}


	public void setMoneystr(String moneystr) {
		this.moneystr = moneystr;
	}



	
}