package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Dtorganizationdesc 部门机构职责. 
 * @author lwz
 */

public class Organizationdesc implements BaseBean ,ExcelBean,Serializable {

	// Fields

	private String organizationdesckey;
	private String organizationdescid;
	private String companyid;      //公司
	private String organizationid;	//部门
	private String datework;		//日工作
	private String weekwork;		//周工作
	private String monthwork;		//月工作
	private String seasonwork;		//季工作
	private String yearwork;		//年工作
	private String jobwork;			//本职工作
	private String twicework;		//兼职工作
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","日工作","周工作", "月工作","季工作","年工作", "本职工作", "兼职工作" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {datework,weekwork,monthwork,seasonwork,yearwork, jobwork,twicework};
		return properties;
	}
	// Constructors

	/** default constructor */
	public Organizationdesc() {
	}

	/** full constructor */
	public Organizationdesc(String organizationdescid, String companyid,
			String organizationid, String datework, String weekwork,
			String monthwork, String seasonwork, String yearwork,
			String jobwork, String twicework, String cname, Date ctime,
			String uname, Date utime) {
		this.organizationdescid = organizationdescid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.datework = datework;
		this.weekwork = weekwork;
		this.monthwork = monthwork;
		this.seasonwork = seasonwork;
		this.yearwork = yearwork;
		this.jobwork = jobwork;
		this.twicework = twicework;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}
	

	// Property accessors

	public String getOrganizationdesckey() {
		return this.organizationdesckey;
	}

	public void setOrganizationdesckey(String organizationdesckey) {
		this.organizationdesckey = organizationdesckey;
	}

	public String getOrganizationdescid() {
		return this.organizationdescid;
	}

	public void setOrganizationdescid(String organizationdescid) {
		this.organizationdescid = organizationdescid;
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

	public String getDatework() {
		return this.datework;
	}

	public void setDatework(String datework) {
		this.datework = datework;
	}

	public String getWeekwork() {
		return this.weekwork;
	}

	public void setWeekwork(String weekwork) {
		this.weekwork = weekwork;
	}

	public String getMonthwork() {
		return this.monthwork;
	}

	public void setMonthwork(String monthwork) {
		this.monthwork = monthwork;
	}

	public String getSeasonwork() {
		return this.seasonwork;
	}

	public void setSeasonwork(String seasonwork) {
		this.seasonwork = seasonwork;
	}

	public String getYearwork() {
		return this.yearwork;
	}

	public void setYearwork(String yearwork) {
		this.yearwork = yearwork;
	}

	public String getJobwork() {
		return this.jobwork;
	}

	public void setJobwork(String jobwork) {
		this.jobwork = jobwork;
	}

	public String getTwicework() {
		return this.twicework;
	}

	public void setTwicework(String twicework) {
		this.twicework = twicework;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}