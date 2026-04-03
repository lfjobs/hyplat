package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;


public class WfjTask implements BaseBean {

	private String  wfjTaskKey;
	private String  wfjTaskId;
	private String  staffId;//人员Id
	private String  wfjGuizeId;//规则外键
	private Date  wfjTaskDate;//日期
	public WfjTask() {
		super();
	}
	public WfjTask(String wfjTaskKey, String wfjTaskId, String staffId, String wfjGuizeId,
			Date wfjTaskDate) {
		super();
		this.wfjTaskKey = wfjTaskKey;
		this.wfjTaskId = wfjTaskId;
		this.staffId = staffId;
		this.wfjGuizeId = wfjGuizeId;
		this.wfjTaskDate = wfjTaskDate;
	}

	public String getWfjTaskKey() {
		return wfjTaskKey;
	}
	public void setWfjTaskKey(String wfjTaskKey) {
		this.wfjTaskKey = wfjTaskKey;
	}
	public String getWfjTaskId() {
		return wfjTaskId;
	}
	public void setWfjTaskId(String wfjTaskId) {
		this.wfjTaskId = wfjTaskId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getWfjGuizeId() {
		return wfjGuizeId;
	}
	public void setWfjGuizeId(String wfjGuizeId) {
		this.wfjGuizeId = wfjGuizeId;
	}
	public Date getWfjTaskDate() {
		return wfjTaskDate;
	}
	public void setWfjTaskDate(Date wfjTaskDate) {
		this.wfjTaskDate = wfjTaskDate;
	}
}
