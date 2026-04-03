package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

/**
 * 工作计划记录状态
 * lwz
 */

public class Jobplanrecord implements BaseBean {

	// Fields

	private String jobplanrecordkey;
	private String jobplanrecordid;
	private String jobplanid;   //工作计划id
	private Date caupdate;	//时间
	private String entry;		//状态
	private String staffid;		//责任人id
	private String staffids;	//操作人id
	private String staffname;	//责任人姓名
	private String staffnames;	//操作人姓名
	private String companyid;	//责任人公司
	private String companyids;	//操作人公司

	
	private static Map<String, String> cMap;//公司名称
	public static void setCMap(Map<String, String> map) {
		cMap = map;
	}
	public String getCompanyName() {
		String cName="";
		if(null!=cMap)
		{
			cName=cMap.get(companyid);
		}
		return cName;
	}
	
	public String getCompanyNames() {
		String cName="";
		if(null!=cMap)
		{
			cName=cMap.get(companyids);
		}
		return cName;
	}


	public String getJobplanrecordkey() {
		return this.jobplanrecordkey;
	}

	public void setJobplanrecordkey(String jobplanrecordkey) {
		this.jobplanrecordkey = jobplanrecordkey;
	}

	public String getJobplanrecordid() {
		return this.jobplanrecordid;
	}

	public void setJobplanrecordid(String jobplanrecordid) {
		this.jobplanrecordid = jobplanrecordid;
	}

	public String getJobplanid() {
		return this.jobplanid;
	}

	public void setJobplanid(String jobplanid) {
		this.jobplanid = jobplanid;
	}

	public Date getCaupdate() {
		return caupdate;
	}

	public void setCaupdate(Date caupdate) {
		this.caupdate = caupdate;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffids() {
		return this.staffids;
	}

	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getStaffnames() {
		return this.staffnames;
	}

	public void setStaffnames(String staffnames) {
		this.staffnames = staffnames;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyids() {
		return this.companyids;
	}

	public void setCompanyids(String companyids) {
		this.companyids = companyids;
	}


	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}