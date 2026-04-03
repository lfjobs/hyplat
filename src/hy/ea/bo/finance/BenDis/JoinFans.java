package hy.ea.bo.finance.BenDis;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 * 粉丝加入关系表
 * 
 * @author mz
 * 
 */
public class JoinFans implements BaseBean{
    
	private String jfKey;
	private String jfID;
	private String state;//00:正常
	private String staffid;//粉丝人员id
	private String source;//来源
	private String zaccount;//谁的粉丝
	private String faccount;//粉丝 
	private String zsccId;//谁的粉丝（根据sccid区别）
	private String fsccId;//粉丝（根据sccid区别）
	private Date fansDate; //添加粉丝的时间
	private String company;//关注的公司名、称

	public String getZaccount() {
		return zaccount;
	}
	public void setZaccount(String zaccount) {
		this.zaccount = zaccount;
	}
	public String getFaccount() {
		return faccount;
	}
	public void setFaccount(String faccount) {
		this.faccount = faccount;
	}
	public String getJfKey() {
		return jfKey;
	}
	public void setJfKey(String jfKey) {
		this.jfKey = jfKey;
	}
	public String getJfID() {
		return jfID;
	}
	public void setJfID(String jfID) {
		this.jfID = jfID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public String getZsccId() {
		return zsccId;
	}
	public void setZsccId(String zsccId) {
		this.zsccId = zsccId;
	}
	public String getFsccId() {
		return fsccId;
	}
	public void setFsccId(String fsccId) {
		this.fsccId = fsccId;
	}
	public Date getFansDate() {
		return fansDate;
	}
	public void setFansDate(Date fansDate) {
		this.fansDate = fansDate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	 
    
}
