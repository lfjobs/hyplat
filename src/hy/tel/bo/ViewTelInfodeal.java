/*
 *北京天太世统科技有限公司 010-64164005 
 *author：zg。email：longsky_03@sina.com
 */

package hy.tel.bo;

import java.io.Serializable;

import hy.plat.bo.BaseBean;

/**
 信息处理中心 打入打出合并处理 视图Bean
 * @author zg longsky_03@sina.com
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class ViewTelInfodeal implements Serializable,BaseBean {
    
	/** id */
	private java.lang.String id;
	/** userid */
	private java.lang.String userid;
	private java.lang.String recordid;
	/** staffname */
	private java.lang.String staffname;
	/** reference */
	private java.lang.String reference;
	/** telnumber */
	private java.lang.String telnumber;
	/** customname */
	private java.lang.String customname;
	/** recordcontent */
	private java.lang.String recordcontent;
	/** recordtype */
	private java.lang.String recordtype;
	/** isdeal */
	private Long isdeal;
	/** company */
	private java.lang.String company;
	/** companyname 处理人所在公司 */
	private java.lang.String companyname;
	/** recodedate */
	private java.util.Date recodedate;
	/** dealuser */
	private java.lang.String dealuser;
	/** dealdate */
	private java.util.Date dealdate;
	/** dealcontent */
	private java.lang.String dealcontent;
	
	private String telcodetype;//用于区分座机手机
	
	public String getTelcodetype() {
		return telcodetype;
	}

	public void setTelcodetype(String telcodetype) {
		this.telcodetype = telcodetype;
	}

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getTelnumber() {
		return this.telnumber;
	}
	
	public void setTelnumber(java.lang.String value) {
		this.telnumber = value;
	}
	
	public java.lang.String getCustomname() {
		return this.customname;
	}
	
	public void setCustomname(java.lang.String value) {
		this.customname = value;
	}
	
	public java.lang.String getRecordcontent() {
		return this.recordcontent;
	}
	
	public void setRecordcontent(java.lang.String value) {
		this.recordcontent = value;
	}
	
	public Long getIsdeal() {
		return this.isdeal;
	}
	
	public void setIsdeal(Long value) {
		this.isdeal = value;
	}
	
	public java.lang.String getCompany() {
		return this.company;
	}
	
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	
	public java.lang.String getDealuser() {
		return this.dealuser;
	}
	
	public void setDealuser(java.lang.String value) {
		this.dealuser = value;
	}
	 
	
	public java.lang.String getDealcontent() {
		return this.dealcontent;
	}
	
	public void setDealcontent(java.lang.String value) {
		this.dealcontent = value;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getStaffname() {
		return staffname;
	}

	public void setStaffname(java.lang.String staffname) {
		this.staffname = staffname;
	}

	public java.util.Date getRecodedate() {
		return recodedate;
	}

	public void setRecodedate(java.util.Date recodedate) {
		this.recodedate = recodedate;
	}

	public java.util.Date getDealdate() {
		return dealdate;
	}

	public void setDealdate(java.util.Date dealdate) {
		this.dealdate = dealdate;
	}

	public java.lang.String getReference() {
		return reference;
	}

	public void setReference(java.lang.String reference) {
		this.reference = reference;
	}

	public java.lang.String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(java.lang.String companyname) {
		this.companyname = companyname;
	}

	public java.lang.String getRecordtype() {
		return recordtype;
	}

	public void setRecordtype(java.lang.String recordtype) {
		this.recordtype = recordtype;
	}

	public java.lang.String getRecordid() {
		return recordid;
	}

	public void setRecordid(java.lang.String recordid) {
		this.recordid = recordid;
	}

	
}

