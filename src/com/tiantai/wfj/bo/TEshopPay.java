package com.tiantai.wfj.bo;

import hy.ea.bo.human.COrganization;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TEshopPay entity. @author MyEclipse Persistence Tools
 */

public class TEshopPay implements BaseBean {

	// Fields

	private String paykey;
	private COrganization dtcorganization;
	private String payid;
	private Date paytime;
	private BigDecimal paycount;
	private String memo;

	// Constructors

	/** default constructor */
	public TEshopPay() {
	}

	/** minimal constructor */
	public TEshopPay(COrganization dtcorganization, String payid) {
		this.dtcorganization = dtcorganization;
		this.payid = payid;
	}

	/** full constructor */
	public TEshopPay(COrganization dtcorganization, String payid,
			Date paytime, BigDecimal paycount, String memo) {
		this.dtcorganization = dtcorganization;
		this.payid = payid;
		this.paytime = paytime;
		this.paycount = paycount;
		this.memo = memo;
	}

	// Property accessors

	public String getPaykey() {
		return this.paykey;
	}

	public void setPaykey(String paykey) {
		this.paykey = paykey;
	}

	public COrganization getDtcorganization() {
		return this.dtcorganization;
	}

	public void setDtcorganization(COrganization dtcorganization) {
		this.dtcorganization = dtcorganization;
	}

	public String getPayid() {
		return this.payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public Date getPaytime() {
		return this.paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public BigDecimal getPaycount() {
		return this.paycount;
	}

	public void setPaycount(BigDecimal paycount) {
		this.paycount = paycount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}