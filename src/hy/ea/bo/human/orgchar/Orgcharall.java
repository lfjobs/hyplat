package hy.ea.bo.human.orgchar;

import hy.plat.bo.BaseBean;

/**
 * 
 * 机构图
 * 
 */

public class Orgcharall implements BaseBean {

	// Fields

	private String orgcharallkey;// 
	private String orgcharallid; // 
	private String companyID; // 公司ID
	private String organizationID ;//部门
	
	private String orgcharUrl; //保存路径

	public String getOrgcharallkey() {
		return orgcharallkey;
	}

	public void setOrgcharallkey(String orgcharallkey) {
		this.orgcharallkey = orgcharallkey;
	}

	public String getOrgcharallid() {
		return orgcharallid;
	}

	public void setOrgcharallid(String orgcharallid) {
		this.orgcharallid = orgcharallid;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrgcharUrl() {
		return orgcharUrl;
	}

	public void setOrgcharUrl(String orgcharUrl) {
		this.orgcharUrl = orgcharUrl;
	}

	
	
}