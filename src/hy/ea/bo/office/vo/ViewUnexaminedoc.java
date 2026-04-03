package hy.ea.bo.office.vo;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;


public class ViewUnexaminedoc implements java.io.Serializable,BaseBean,ExcelBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651220623474122086L;
	private String key;
    private String docid;
	private String docnum;
	private String title;
	private String theme;
	private String doctype;
	private String emergencytype;
	private Date startvalidity;
	private Date endvalidity;
	private Date updatetime;
	private String assignee;
	private String frommember;
	private String fromname;
	private String module;
	private String activityname;
	private String deptidofsubscriber;
	private String drafterid;
	private String draftername;
	private String organizationid;
	private String deptnameofdraft;
	private String companyid;
	private String companyname;
	private String postname;
	//用于企业合同
	private String partya;//甲方可以为公司和人
	private String partyb;//乙方可以为公司和人
	private String partyaname;//甲方可以为公司和人
	private String partybname;//乙方可以为公司和人
	
	private String partyastaff;
	private String partybstaff;
	private String staffidentitycarda;
	private String staffidentitycardb;
	private String partyastaffnames;
	private String partybstaffnames;
	private String status;
	private String companyidofsubscriber;

	public static String[] columnHeadings() { 
		String[] titles = { "序号", "公文编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称","发件人","报送时间" ,"合同生效日期", "合同截止日期","甲方公司","甲方责任人","乙方公司","乙方责任人"};
		return titles;
	}
	
	public static String[] columnHeadings1() { 
		String[] titles = { "序号", "公文编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称", "发件人","报送时间"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {
				docnum,
				title,
				theme,
				doctype,
				emergencytype,
				draftername,
				deptnameofdraft,
				companyname,
				fromname,
				String.format("%1$tF", updatetime),
				String.format("%1$tF", startvalidity),
				String.format("%1$tF", endvalidity),
				partyaname,
				partyastaffnames,
				partyaname,
				partybstaffnames
				};
		return properties;
	}
	public String getPartyastaff() {
		return partyastaff;
	}

	public void setPartyastaff(String partyastaff) {
		this.partyastaff = partyastaff;
	}

	public String getPartybstaff() {
		return partybstaff;
	}

	public void setPartybstaff(String partybstaff) {
		this.partybstaff = partybstaff;
	}


	public String getPartyastaffnames() {
		return partyastaffnames;
	}

	public void setPartyastaffnames(String partyastaffnames) {
		this.partyastaffnames = partyastaffnames;
	}

	public String getPartybstaffnames() {
		return partybstaffnames;
	}

	public void setPartybstaffnames(String partybstaffnames) {
		this.partybstaffnames = partybstaffnames;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDocid() {
		return this.docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDocnum() {
		return this.docnum;
	}

	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getFrommember() {
		return this.frommember;
	}

	public void setFrommember(String frommember) {
		this.frommember = frommember;
	}

	public String getFromname() {
		return this.fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getActivityname() {
		return this.activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getDeptidofsubscriber() {
		return this.deptidofsubscriber;
	}

	public void setDeptidofsubscriber(String deptidofsubscriber) {
		this.deptidofsubscriber = deptidofsubscriber;
	}

	public String getDrafterid() {
		return this.drafterid;
	}

	public void setDrafterid(String drafterid) {
		this.drafterid = drafterid;
	}

	public String getDraftername() {
		return this.draftername;
	}

	public void setDraftername(String draftername) {
		this.draftername = draftername;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getDeptnameofdraft() {
		return this.deptnameofdraft;
	}

	public void setDeptnameofdraft(String deptnameofdraft) {
		this.deptnameofdraft = deptnameofdraft;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getPostname() {
		return this.postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getEmergencytype() {
		return emergencytype;
	}

	public void setEmergencytype(String emergencytype) {
		this.emergencytype = emergencytype;
	}

    

	public Date getStartvalidity() {
		return startvalidity;
	}

	public void setStartvalidity(Date startvalidity) {
		this.startvalidity = startvalidity;
	}

	public Date getEndvalidity() {
		return endvalidity;
	}

	public void setEndvalidity(Date endvalidity) {
		this.endvalidity = endvalidity;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getPartybname() {
		return partybname;
	}

	public void setPartybname(String partybname) {
		this.partybname = partybname;
	}

	public String getPartya() {
		return partya;
	}

	public void setPartya(String partya) {
		this.partya = partya;
	}

	public String getPartyb() {
		return partyb;
	}

	public void setPartyb(String partyb) {
		this.partyb = partyb;
	}

	public String getPartyaname() {
		return partyaname;
	}

	public void setPartyaname(String partyaname) {
		this.partyaname = partyaname;
	}

	public String getStaffidentitycarda() {
		return staffidentitycarda;
	}

	public void setStaffidentitycarda(String staffidentitycarda) {
		this.staffidentitycarda = staffidentitycarda;
	}

	public String getStaffidentitycardb() {
		return staffidentitycardb;
	}

	public void setStaffidentitycardb(String staffidentitycardb) {
		this.staffidentitycardb = staffidentitycardb;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getCompanyidofsubscriber() {
		return companyidofsubscriber;
	}

	public void setCompanyidofsubscriber(String companyidofsubscriber) {
		this.companyidofsubscriber = companyidofsubscriber;
	}
}