package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDutyBranch entity. @author MyEclipse Persistence Tools
 */

public class DtDutyBranch implements BaseBean {

	// Fields

	private String branchkey;
	private String branchid;
	private String dutyid;
	private String staffid;
	private String staffname;
	private String dutyType;
	private Date sdate;
	private Date edate;
	private String strdate;
	private String enddate;

	// Constructors

	/** default constructor */
	public DtDutyBranch() {
	}

	/** minimal constructor */
	public DtDutyBranch(String branchid, String dutyid, String staffid) {
		this.branchid = branchid;
		this.dutyid = dutyid;
		this.staffid = staffid;
	}

	/** full constructor */
	public DtDutyBranch(String branchid, String dutyid, String staffid,
			String staffname, String dutyType, Date sdate, Date edate) {
		this.branchid = branchid;
		this.dutyid = dutyid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.dutyType = dutyType;
		this.sdate = sdate;
		this.edate = edate;
	}

	// Property accessors

	public String getBranchkey() {
		return this.branchkey;
	}

	public void setBranchkey(String branchkey) {
		this.branchkey = branchkey;
	}

	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getDutyid() {
		return this.dutyid;
	}

	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
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

	public String getDutyType() {
		return this.dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	public Date getSdate() {
		return this.sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public Date getEdate() {
		return this.edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	/**
	 * @return the strdate
	 */
	public String getStrdate() {
		return strdate;
	}

	/**
	 * @param strdate the strdate to set
	 */
	public void setStrdate(String strdate) {
		this.strdate = strdate;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
}