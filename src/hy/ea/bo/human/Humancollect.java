package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 *	个人服务办表（显示隐藏作用）
 * @author lwz 2013.5.6
 */

public class Humancollect implements BaseBean {

	// Fields

	private String humancollectkey;
	private String humancollectid;
	private String companyid;    //公司
	private String staffaddress; //地址管理
	private String staffcontact;//联系方式
	private String staffpersonl;//电话记录
	private String stafftrack;//咨询跟踪
	private String stafftrackl;//咨询报表
	
	private String classification;//客户分类	
	private String subordinate ;//所属单位
	private String callCenter;//呼叫中心
	private String documents;//个人证件
	private String interested ;//客户兴趣
	private String potential;//潜在客户
	private String source;//客户来源
	private String individual;//个人客户档案
	private String branchCompany;//报名单位
	
	
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	// Constructors

	/** default constructor */
	public Humancollect() {
	}

	/** full constructor */
	
	
	// Property accessors
	
	public String getClassification() {
		return classification;
	}

	public Humancollect(String humancollectkey, String humancollectid,
			String companyid, String staffaddress, String staffcontact,
			String staffpersonl, String stafftrack, String stafftrackl,
			String classification, String subordinate, String callCenter,
			String documents, String interested, String potential,
			String source, String individual, String cname, Date ctime,
			String uname, Date utime) {
		super();
		this.humancollectkey = humancollectkey;
		this.humancollectid = humancollectid;
		this.companyid = companyid;
		this.staffaddress = staffaddress;
		this.staffcontact = staffcontact;
		this.staffpersonl = staffpersonl;
		this.stafftrack = stafftrack;
		this.stafftrackl = stafftrackl;
		this.classification = classification;
		this.subordinate = subordinate;
		this.callCenter = callCenter;
		this.documents = documents;
		this.interested = interested;
		this.potential = potential;
		this.source = source;
		this.individual = individual;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

	public String getCallCenter() {
		return callCenter;
	}

	public void setCallCenter(String callCenter) {
		this.callCenter = callCenter;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}

	public String getInterested() {
		return interested;
	}

	public void setInterested(String interested) {
		this.interested = interested;
	}

	public String getPotential() {
		return potential;
	}

	public void setPotential(String potential) {
		this.potential = potential;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public String getHumancollectkey() {
		return this.humancollectkey;
	}

	public void setHumancollectkey(String humancollectkey) {
		this.humancollectkey = humancollectkey;
	}

	public String getHumancollectid() {
		return this.humancollectid;
	}

	public void setHumancollectid(String humancollectid) {
		this.humancollectid = humancollectid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getStaffaddress() {
		return this.staffaddress;
	}

	public void setStaffaddress(String staffaddress) {
		this.staffaddress = staffaddress;
	}

	public String getStaffcontact() {
		return this.staffcontact;
	}

	public void setStaffcontact(String staffcontact) {
		this.staffcontact = staffcontact;
	}

	public String getStaffpersonl() {
		return this.staffpersonl;
	}

	public void setStaffpersonl(String staffpersonl) {
		this.staffpersonl = staffpersonl;
	}

	public String getStafftrack() {
		return this.stafftrack;
	}

	public void setStafftrack(String stafftrack) {
		this.stafftrack = stafftrack;
	}

	public String getStafftrackl() {
		return this.stafftrackl;
	}

	public void setStafftrackl(String stafftrackl) {
		this.stafftrackl = stafftrackl;
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

	public String getBranchCompany() {
		return branchCompany;
	}

	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}

}