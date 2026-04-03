package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * Dtconferenceorg  会议组织机构
 * 
 * @author 李伟志
 */

public class Dtconferenceorg implements BaseBean,ExcelBean {

	// Fields

	private String conferenceorgekey;
	private String conferenceorgid;
	private String companyid;
	private String organizationid;
	private String conorgname;  //机构名称
	private String jobname;		//职务
	private String remarks;		//备注
	private String ctname;
	private String ctdate;
	private String updates;
	private String upname;
	private String responsible; //责任人

	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","组织机构", "责任人", "职务","电话号码", "备注"};
		return titles;
	}
	
	
	
	// Constructors

	/** default constructor */
	public Dtconferenceorg() {
	}

	/** full constructor */
	public Dtconferenceorg(String conferenceorgid, String companyid,
			String organizationid, String conorgname, String jobname,
			String remarks, String ctname, String ctdate,
			String updates, String upname, String responsible) {
		this.conferenceorgid = conferenceorgid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.conorgname = conorgname;
		this.jobname = jobname;
		this.remarks = remarks;
		this.ctname = ctname;
		this.ctdate = ctdate;
		this.updates = updates;
		this.upname = upname;
		this.responsible = responsible;
	}

	// Property accessors

	public String getConferenceorgekey() {
		return this.conferenceorgekey;
	}

	public void setConferenceorgekey(String conferenceorgekey) {
		this.conferenceorgekey = conferenceorgekey;
	}

	public String getConferenceorgid() {
		return this.conferenceorgid;
	}

	public void setConferenceorgid(String conferenceorgid) {
		this.conferenceorgid = conferenceorgid;
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

	public String getConorgname() {
		return this.conorgname;
	}

	public void setConorgname(String conorgname) {
		this.conorgname = conorgname;
	}

	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}


	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCtname() {
		return this.ctname;
	}

	public void setCtname(String ctname) {
		this.ctname = ctname;
	}

	public String getCtdate() {
		return this.ctdate;
	}

	public void setCtdate(String ctdate) {
		this.ctdate = ctdate;
	}

	public String getUpdates() {
		return this.updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public String getUpname() {
		return this.upname;
	}

	public void setUpname(String upname) {
		this.upname = upname;
	}

	public String getResponsible() {
		return this.responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

}