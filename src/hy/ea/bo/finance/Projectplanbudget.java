package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * Dtprojectplanbudget entity. @author MyEclipse Persistence Tools
 */

public class Projectplanbudget implements BaseBean {

	// Fields

	private String projectplanbudgetkey;
	private String projectplanbudgetid;
	private String groupcompanysn;
	private String companyid;
	private String organizationid;
	private String csbid;
	private String projectname;
	private String staffid;

	// Constructors

	/** default constructor */
	public Projectplanbudget() {
	}

	/** full constructor */
	public Projectplanbudget(String projectplanbudgetid,
			String groupcompanysn, String companyid, String organizationid,
			String csbid, String staffid) {
		this.projectplanbudgetid = projectplanbudgetid;
		this.groupcompanysn = groupcompanysn;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.csbid = csbid;
		this.staffid = staffid;
	}

	// Property accessors

	public String getProjectplanbudgetkey() {
		return this.projectplanbudgetkey;
	}

	public void setProjectplanbudgetkey(String projectplanbudgetkey) {
		this.projectplanbudgetkey = projectplanbudgetkey;
	}

	public String getProjectplanbudgetid() {
		return this.projectplanbudgetid;
	}

	public void setProjectplanbudgetid(String projectplanbudgetid) {
		this.projectplanbudgetid = projectplanbudgetid;
	}

	public String getGroupcompanysn() {
		return this.groupcompanysn;
	}

	public void setGroupcompanysn(String groupcompanysn) {
		this.groupcompanysn = groupcompanysn;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getCsbid() {
		return this.csbid;
	}

	public void setCsbid(String csbid) {
		this.csbid = csbid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
     
}