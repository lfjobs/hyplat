package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 商品收藏
 * @author mz
 *
 */
public class GoodsCollect implements BaseBean{

	private String gcKey;
	private String gcId;
	private String staffId;// 用户iD 
	private String pid;// 商品ID
	private String pricetype;//0零售价，1：批发价
	private Date createDate;
	public String getGcKey() {
		return gcKey;
	}
	public void setGcKey(String gcKey) {
		this.gcKey = gcKey;
	}
	public String getGcId() {
		return gcId;
	}
	public void setGcId(String gcId) {
		this.gcId = gcId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPricetype() {
		return pricetype;
	}
	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
