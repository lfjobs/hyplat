package com.mysl.bo;

import hy.plat.bo.BaseBean;

/**
 * DtMystaff entity. @author MyEclipse Persistence Tools
 */

public class DtMystaff implements java.io.Serializable,BaseBean {

	// Fields

	private String mystaffkey;//主键key
	private String mystaffid;//逻辑ID
	private String proid;//项目ID
	private String staffid;//5l5C人员ID
	private String staffname;//5L5C人员名称
	private String identity;//身份(项目负责人00,任务执行人01)
	private String projectname;//项目名称
	private String companyName;//公司名称
	private String companyID;//公司ID
	private String organizationName;//部门名称
	private String organizationID;//部门ID

	// Constructors

	/** default constructor */
	public DtMystaff() {
	}

	/** full constructor */
	public DtMystaff(String mystaffkey, String mystaffid, String proid,
			String staffid, String staffname, String identity,
			String projectname, String companyName, String companyID,
			String organizationName, String organizationID) {
		super();
		this.mystaffkey = mystaffkey;
		this.mystaffid = mystaffid;
		this.proid = proid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.identity = identity;
		this.projectname = projectname;
		this.companyName = companyName;
		this.companyID = companyID;
		this.organizationName = organizationName;
		this.organizationID = organizationID;
	}
	// Property accessors
	public String getMystaffkey() {
		return this.mystaffkey;
	}

	public void setMystaffkey(String mystaffkey) {
		this.mystaffkey = mystaffkey;
	}

	public String getMystaffid() {
		return this.mystaffid;
	}

	public void setMystaffid(String mystaffid) {
		this.mystaffid = mystaffid;
	}

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

}