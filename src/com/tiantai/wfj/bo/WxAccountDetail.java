package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 微信商户号明细
	*/
	public class WxAccountDetail implements BaseBean,java.io.Serializable{

		private String  wxdKey;
		private String  wxdId;
		private String  companyID;//公司ID
		private String money;
		private Date createDate;//入账出账时间
		private String sccid;//操作人
	    private String staffName;//人名
		private String  type;//供应商成本，业务佣金
		private String sztype;//支出还是收入
		private String rate;//
	    private String journalNum;//订单号
	    private String status;//状态，提现的时候有状态

	public String getWxdKey() {
		return wxdKey;
	}

	public void setWxdKey(String wxdKey) {
		this.wxdKey = wxdKey;
	}

	public String getWxdId() {
		return wxdId;
	}

	public void setWxdId(String wxdId) {
		this.wxdId = wxdId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getSztype() {
		return sztype;
	}

	public void setSztype(String sztype) {
		this.sztype = sztype;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}
