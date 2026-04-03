package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtArchivesCheckvouchs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesCheckvouchs implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5189951792151186818L;
	private String checkkey;
	private String checkid;
	private String companyid;
	private String departmentid;
	private String createuser;
	private String checklocation;
	private String checkuser;
	private Date checktime;
	private String results;
	private String categoryid;
	
	
	
	private String checkusername;
	private String cataloguename;
	private String departmentname;
	
	
	
	private String startDate;
	private String endDate;

	// Constructors

	/** default constructor */
	public DtArchivesCheckvouchs() {
	}

	/** minimal constructor */
	public DtArchivesCheckvouchs(String checkkey, String checkid,
			String companyid, String departmentid, String createuser) {
		this.checkkey = checkkey;
		this.checkid = checkid;
		this.companyid = companyid;
		this.departmentid = departmentid;
		this.createuser = createuser;
	}

	/** full constructor */
	public DtArchivesCheckvouchs(String checkkey, String checkid,
			String companyid, String departmentid, String createuser,
			String checklocation, String checkuser, Date checktime,
			String results, String categoryid) {
		this.checkkey = checkkey;
		this.checkid = checkid;
		this.companyid = companyid;
		this.departmentid = departmentid;
		this.createuser = createuser;
		this.checklocation = checklocation;
		this.checkuser = checkuser;
		this.checktime = checktime;
		this.results = results;
		this.categoryid = categoryid;
	}

	// Property accessors

	public String getCheckkey() {
		return this.checkkey;
	}

	public void setCheckkey(String checkkey) {
		this.checkkey = checkkey;
	}

	public String getCheckid() {
		return this.checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDepartmentid() {
		return this.departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getChecklocation() {
		return this.checklocation;
	}

	public void setChecklocation(String checklocation) {
		this.checklocation = checklocation;
	}

	public String getCheckuser() {
		return this.checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public Date getChecktime() {
		return this.checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	public String getResults() {
		return this.results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}

	public String getCataloguename() {
		return cataloguename;
	}

	public void setCataloguename(String cataloguename) {
		this.cataloguename = cataloguename;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

}