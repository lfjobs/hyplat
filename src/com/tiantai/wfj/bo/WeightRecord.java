package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *  重量记录
 * @author mz
 *
 */
public class WeightRecord implements BaseBean{

	private String wrKey;
	private String wrd;
	private String weightB;
	private String weightA;
	private Date createDate;
	private String hgcode;
	private String staffID;
	private String journalNum;

	public String getWrKey() {
		return wrKey;
	}

	public void setWrKey(String wrKey) {
		this.wrKey = wrKey;
	}

	public String getWrd() {
		return wrd;
	}

	public void setWrd(String wrd) {
		this.wrd = wrd;
	}

	public String getWeightB() {
		return weightB;
	}

	public void setWeightB(String weightB) {
		this.weightB = weightB;
	}

	public String getWeightA() {
		return weightA;
	}

	public void setWeightA(String weightA) {
		this.weightA = weightA;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHgcode() {
		return hgcode;
	}

	public void setHgcode(String hgcode) {
		this.hgcode = hgcode;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
}
