package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;


public class WfjJifen implements BaseBean {

	private String  wfjJifenKey;
	private String  wfjJifenId;
	private String  staffId;//人员Id
	private String  wfjJifenScore;//积分汇总
	private Integer  wfjJifenState;//0:正常(default)
	private String sccid;//会员id
	private String envelopescore;//红包汇总
	private String staffName;//人员姓名（传值字段，不存数据库）
	private String headimage;//人员姓名（传值字段，不存数据库）
	public WfjJifen() {
		super();
	}
	public WfjJifen(String wfjJifenKey, String wfjJifenId,String staffId, String wfjJifenScore,
			Integer wfjJifenState) {
		super();
		this.wfjJifenKey = wfjJifenKey;
		this.wfjJifenId = wfjJifenId;
		this.staffId = staffId;
		this.wfjJifenScore = wfjJifenScore;
		this.wfjJifenState = wfjJifenState;
	}
	
	
	public WfjJifen(String wfjJifenKey, String wfjJifenId,
			String wfjJifenScore, Integer wfjJifenState, String staffName,String headimage) {
		super();
		this.wfjJifenKey = wfjJifenKey;
		this.wfjJifenId = wfjJifenId;
		this.wfjJifenScore = wfjJifenScore;
		this.wfjJifenState = wfjJifenState;
		this.staffName = staffName;
		this.headimage=headimage;
	}
	
	public String getWfjJifenKey() {
		return wfjJifenKey;
	}
	public void setWfjJifenKey(String wfjJifenKey) {
		this.wfjJifenKey = wfjJifenKey;
	}
	public String getWfjJifenId() {
		return wfjJifenId;
	}
	public void setWfjJifenId(String wfjJifenId) {
		this.wfjJifenId = wfjJifenId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getWfjJifenScore() {
		return wfjJifenScore;
	}
	public void setWfjJifenScore(String wfjJifenScore) {
		this.wfjJifenScore = wfjJifenScore;
	}
	public Integer getWfjJifenState() {
		return wfjJifenState;
	}
	public void setWfjJifenState(Integer wfjJifenState) {
		this.wfjJifenState = wfjJifenState;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
	/**
	 * @return the sccid
	 */
	public String getSccid() {
		return sccid;
	}
	/**
	 * @param sccid the sccid to set
	 */
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public String getEnvelopescore() {
		return envelopescore;
	}

	public void setEnvelopescore(String envelopescore) {
		this.envelopescore = envelopescore;
	}
}
