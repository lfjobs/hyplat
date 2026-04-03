package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 *搜索历史ljc 
 */
public class SearchHistory implements BaseBean {
	private String shkey;
	private String shId;
	private String staffId;//人员staffid
	private String shname;//搜索词语
	private Date shdate;
	private String ccomIDPlatform;//行业平台对应的往来单位ID；
	public String getShkey() {
		return shkey;
	}
	public void setShkey(String shkey) {
		this.shkey = shkey;
	}
	public String getShId() {
		return shId;
	}
	public void setShId(String shId) {
		this.shId = shId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getShname() {
		return shname;
	}
	public void setShname(String shname) {
		this.shname = shname;
	}
	public Date getShdate() {
		return shdate;
	}
	public void setShdate(Date shdate) {
		this.shdate = shdate;
	}

	public String getCcomIDPlatform() {
		return ccomIDPlatform;
	}

	public void setCcomIDPlatform(String ccomIDPlatform) {
		this.ccomIDPlatform = ccomIDPlatform;
	}
}
