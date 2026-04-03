package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * ViewJobplan entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class ViewJobplan implements java.io.Serializable,BaseBean {

	// Fields

	private String jobplankey;
	private String projectcode;
	private String jobname;
	private String jobcontent;
	private String entry;
	private Date startdate;
	private String entrydate;
	private Date enddate;
	private String staffid;
	private String staffname;
	private String companyid;
	private String staffids;
	private String staffnames;
	private String companyids;
	private String organizationid;
	private String jobplanid;
	private String codeid;
	private String codevalue;
	private String jobstatus;
	private String supervisor;
	private String humansupervisor;
	private String manager;

	public String getJobplankey() {
		return this.jobplankey;
	}

	public void setJobplankey(String jobplankey) {
		this.jobplankey = jobplankey;
	}

	public String getProjectcode() {
		return this.projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}

	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getJobcontent() {
		return this.jobcontent;
	}

	public void setJobcontent(String jobcontent) {
		this.jobcontent = jobcontent;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getEntrydate() {
		return this.entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getStaffids() {
		return this.staffids;
	}

	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	public String getStaffnames() {
		return this.staffnames;
	}

	public void setStaffnames(String staffnames) {
		this.staffnames = staffnames;
	}

	public String getCompanyids() {
		return this.companyids;
	}

	public void setCompanyids(String companyids) {
		this.companyids = companyids;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getJobplanid() {
		return this.jobplanid;
	}

	public void setJobplanid(String jobplanid) {
		this.jobplanid = jobplanid;
	}

	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public String getCodevalue() {
		return this.codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	public String getJobstatus() {
		return this.jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getHumansupervisor() {
		return this.humansupervisor;
	}

	public void setHumansupervisor(String humansupervisor) {
		this.humansupervisor = humansupervisor;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}