package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 现场会议管理
 * 
 * @author 李伟志
 */

public class Dtconference implements BaseBean,ExcelBean {

	// Fields

	private String conferencekey;
	private String conferenceid;
	private String companyid;
	private String organizationid;
	private String serNum;	//序号
	private String hisdata; //历史数据状态 00：使用 01 历史
	private String condate;			//日期
	private String startdate;		//开始时间
	private String enddate;			//终止时间
	private String state;			//00 ：准备会议阶段01：正式会议阶段02：会议闭幕阶段
	private String orgname;			//部门名称
	private String postname;		//岗位名称
	private String jobname;			//职务名称
	private String responsible;		//责任人
	private String flowname;		//会议流程名称
	private String tcontent;		//会议内容
	private String annexid;			//附件编号
	private String annexname;		//附件名称
	private String annexurl;		//附件路径
	private String createname;		//创建人
	private String createdate;		//创建时间
	private String updatename;		//最后修改人
	private String updatedate;		//最后修改时间

	
	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","日期", "起时间", "止时间","部门", "岗位名称", "职务名称", "责任人",
				"会议流程名称", "会议内容", "附件编号", "附件名称", "附件Word流程"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {condate, startdate, enddate, orgname,postname,
				jobname, responsible, flowname, tcontent, annexid,annexname, annexurl};
		return properties;
	}

	
	// Constructors

	/** default constructor */
	public Dtconference() {
	}

	/** full constructor */
	public Dtconference(String conferenceid, String companyid,
			String organizationid, String serNum, String condate, String startdate,
			String enddate, String state, String orgname, String postname,
			String jobname, String responsible, String flowname,
			String tcontent, String annexid, String annexname, String annexurl,
			String createname, String createdate, String updatename, String hisdata,
			String updatedate) {
		this.conferenceid = conferenceid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.serNum = serNum;
		this.condate = condate;
		this.startdate = startdate;
		this.enddate = enddate;
		this.state = state;
		this.orgname = orgname;
		this.postname = postname;
		this.jobname = jobname;
		this.responsible = responsible;
		this.flowname = flowname;
		this.tcontent = tcontent;
		this.annexid = annexid;
		this.annexname = annexname;
		this.annexurl = annexurl;
		this.createname = createname;
		this.createdate = createdate;
		this.updatename = updatename;
		this.updatedate = updatedate;
		this.hisdata = hisdata;
		}

	// Property accessors

	public String getConferencekey() {
		return this.conferencekey;
	}

	public void setConferencekey(String conferencekey) {
		this.conferencekey = conferencekey;
	}

	public String getConferenceid() {
		return this.conferenceid;
	}

	public void setConferenceid(String conferenceid) {
		this.conferenceid = conferenceid;
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

	
	public String getSerNum() {
		return serNum;
	}

	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getPostname() {
		return this.postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getResponsible() {
		return this.responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getFlowname() {
		return this.flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}

	public String getTcontent() {
		return this.tcontent;
	}

	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	public String getAnnexid() {
		return this.annexid;
	}

	public void setAnnexid(String annexid) {
		this.annexid = annexid;
	}

	public String getAnnexname() {
		return this.annexname;
	}

	public void setAnnexname(String annexname) {
		this.annexname = annexname;
	}

	public String getAnnexurl() {
		return this.annexurl;
	}

	public void setAnnexurl(String annexurl) {
		this.annexurl = annexurl;
	}

	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpdatename() {
		return this.updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

	public String getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getCondate() {
		return condate;
	}

	public void setCondate(String condate) {
		this.condate = condate;
	}

	public String getHisdata() {
		return hisdata;
	}

	public void setHisdata(String hisdata) {
		this.hisdata = hisdata;
	}
	
}