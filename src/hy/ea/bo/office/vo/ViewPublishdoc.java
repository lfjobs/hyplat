package hy.ea.bo.office.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * ViewPublishdocId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ViewPublishdoc implements java.io.Serializable,BaseBean,ExcelBean{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 656323749092488668L;
	private String key;
	private String docid;
	private String title;
	private String theme;
	private String doctype;
	private String emergencytype;
	private Date startvalidity;
	private Date endvalidity;
	private Date subscribetime;
	private String companyidofpublisher;
	private String deptidofpublisher;
	private String docnum;
	private String formalnum;
	private String assignee;
	private String outcome;
	private Date publishtime;
	private String module;
	private String sealerid;
	private String sealername;
	private String deptnameofsealer;
	private String comnameofsealer;
	private String companyidofsubscriber;
	private String subscribername;
	private String deptnameofsub;
	private String comnameofsub;
	private String drafterid;
	private String draftername;
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
    
	
	public static String[] columnHeadings() { 
		String[] titles = { "序号", "公文编号", "正式编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称","签发（审批）单位","签发（审批）部门","签发（审批）人","盖章人公司","盖章人部门","盖章人","分发时间", "合同生效日期", "合同截止日期","甲方公司","甲方责任人","乙方公司","乙方责任人"};
		return titles;
	}
	
	public static String[] columnHeadings1() { 
		String[] titles = { "序号", "公文编号", "正式编号", "文件标题", "主题词", "公文类型","缓急","申报人","申报人部门", 
				"申报单位名称","签发（审批）单位","签发（审批）部门","签发（审批）人","盖章人公司","盖章人部门","盖章人", "分发时间"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {
				docnum,
				formalnum,
				title,
				theme,
				doctype,
				emergencytype,

				draftername,
				deptnameofdraft,
				companyname,

				comnameofsub,
				deptnameofsub,
				subscribername,
				comnameofsealer,
				deptnameofsealer,
				sealername,
				String.format("%1$tF",publishtime),
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
	public String getPartybname() {
		return partybname;
	}
	public void setPartybname(String partybname) {
		this.partybname = partybname;
	}
	public String getPostname(){
    	return this.postname;
    }
    public void setPostname(String postname){
    	this.postname = postname;
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

	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getEmergencytype() {
		return this.emergencytype;
	}

	public void setEmergencytype(String emergencytype) {
		this.emergencytype = emergencytype;
	}

	public Date getStartvalidity() {
		return this.startvalidity;
	}

	public void setStartvalidity(Date startvalidity) {
		this.startvalidity = startvalidity;
	}

	public Date getEndvalidity() {
		return this.endvalidity;
	}

	public void setEndvalidity(Date endvalidity) {
		this.endvalidity = endvalidity;
	}

	public Date getSubscribetime() {
		return this.subscribetime;
	}

	public void setSubscribetime(Date subscribetime) {
		this.subscribetime = subscribetime;
	}

	public String getDeptidofpublisher() {
		return this.deptidofpublisher;
	}

	public void setDeptidofpublisher(String deptidofpublisher) {
		this.deptidofpublisher = deptidofpublisher;
	}

	public String getDocnum() {
		return this.docnum;
	}

	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getOutcome() {
		return this.outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Date getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSealerid() {
		return this.sealerid;
	}

	public void setSealerid(String sealerid) {
		this.sealerid = sealerid;
	}

	public String getCompanyidofsubscriber() {
		return this.companyidofsubscriber;
	}

	public void setCompanyidofsubscriber(String companyidofsubscriber) {
		this.companyidofsubscriber = companyidofsubscriber;
	}

	public String getSubscribername() {
		return this.subscribername;
	}

	public void setSubscribername(String subscribername) {
		this.subscribername = subscribername;
	}

	public String getDeptnameofsub() {
		return this.deptnameofsub;
	}

	public void setDeptnameofsub(String deptnameofsub) {
		this.deptnameofsub = deptnameofsub;
	}

	public String getComnameofsub() {
		return this.comnameofsub;
	}

	public void setComnameofsub(String comnameofsub) {
		this.comnameofsub = comnameofsub;
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
	public String getSealername() {
		return sealername;
	}
	public void setSealername(String sealername) {
		this.sealername = sealername;
	}
	public String getDeptnameofsealer() {
		return deptnameofsealer;
	}
	public void setDeptnameofsealer(String deptnameofsealer) {
		this.deptnameofsealer = deptnameofsealer;
	}
	public String getComnameofsealer() {
		return comnameofsealer;
	}
	public void setComnameofsealer(String comnameofsealer) {
		this.comnameofsealer = comnameofsealer;
	}
	public String getFormalnum() {
		return formalnum;
	}
	public void setFormalnum(String formalnum) {
		this.formalnum = formalnum;
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

	public String getCompanyidofpublisher() {
		return companyidofpublisher;
	}

	public void setCompanyidofpublisher(String companyidofpublisher) {
		this.companyidofpublisher = companyidofpublisher;
	}
}