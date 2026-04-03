package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCheckplan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckPlan implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4863173896998949970L;
	private String checkplankey;
	private String checkplanid;
	private String checkplanname;
	private String companyid;
	private Date createdatetime;
	private String creator;
	private String creatorname;
	private String principal;
	private String principalname;
	private String plantype;
	private Date starttime;
	private Date endtime;
	private String remark;

	
	// Property accessors

	public String getCheckplankey() {
		return this.checkplankey;
	}

	public void setCheckplankey(String checkplankey) {
		this.checkplankey = checkplankey;
	}

	public String getCheckplanid() {
		return this.checkplanid;
	}

	public void setCheckplanid(String checkplanid) {
		this.checkplanid = checkplanid;
	}

	public String getCheckplanname() {
		return this.checkplanname;
	}

	public void setCheckplanname(String checkplanname) {
		this.checkplanname = checkplanname;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPlantype() {
		return this.plantype;
	}

	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorname() {
		return creatorname;
	}

	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}

	public String getPrincipalname() {
		return principalname;
	}

	public void setPrincipalname(String principalname) {
		this.principalname = principalname;
	}

}