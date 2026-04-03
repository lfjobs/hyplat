package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 *	社会人力表（显示隐藏作用）
 * @author lwz 2013.4.2
 */

public class Humanresource  implements BaseBean{

	// Fields

	private String humanresourcekey; 
	private String humanresourceid;
	private String companyid;    //公司
	private String staffaddress; //地址管理
	private String staffcontact; //联系方式
	private String staffeducation; //学历学位
	private String staffresume; //个人履历
	private String stafffamilymember; //家庭成员
	private String staffphysicalcondition; //健康状况
	private String staffpoliticalstatus; //政治面貌
	private String staffencourage; //奖励情况
	private String staffpunishment; //处分情况
	private String staffinsurance; //社会保险
	private String staffinvestigation; //调查情况
	private String staffcertificate; //证件管理
	private String staffdocumentation; //资料列表
	private String staffpersonalfile; //人事档案
	private String staffbankaccount; //银行账号
	private String staffagreement; //合同管理
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	// Constructors

	/** default constructor */
	public Humanresource() {
	}

	/** full constructor */
	public Humanresource(String humanresourceid, String companyid,
			String staffaddress, String staffcontact, String staffeducation,
			String staffresume, String stafffamilymember,
			String staffphysicalcondition, String staffpoliticalstatus,
			String staffencourage, String staffpunishment,
			String staffinsurance, String staffinvestigation,
			String staffcertificate, String staffdocumentation,
			String staffpersonalfile, String staffbankaccount,String staffagreement, String cname,
			Date ctime, String uname, Date utime) {
		this.humanresourceid = humanresourceid;
		this.companyid = companyid;
		this.staffaddress = staffaddress;
		this.staffcontact = staffcontact;
		this.staffeducation = staffeducation;
		this.staffresume = staffresume;
		this.stafffamilymember = stafffamilymember;
		this.staffphysicalcondition = staffphysicalcondition;
		this.staffpoliticalstatus = staffpoliticalstatus;
		this.staffencourage = staffencourage;
		this.staffpunishment = staffpunishment;
		this.staffinsurance = staffinsurance;
		this.staffinvestigation = staffinvestigation;
		this.staffcertificate = staffcertificate;
		this.staffdocumentation = staffdocumentation;
		this.staffpersonalfile = staffpersonalfile;
		this.staffbankaccount = staffbankaccount;
		this.staffagreement = staffagreement;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}

	// Property accessors

	public String getHumanresourcekey() {
		return this.humanresourcekey;
	}

	public void setHumanresourcekey(String humanresourcekey) {
		this.humanresourcekey = humanresourcekey;
	}

	public String getHumanresourceid() {
		return this.humanresourceid;
	}

	public void setHumanresourceid(String humanresourceid) {
		this.humanresourceid = humanresourceid;
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

	public String getStaffeducation() {
		return this.staffeducation;
	}

	public void setStaffeducation(String staffeducation) {
		this.staffeducation = staffeducation;
	}

	public String getStaffresume() {
		return this.staffresume;
	}

	public void setStaffresume(String staffresume) {
		this.staffresume = staffresume;
	}

	public String getStafffamilymember() {
		return this.stafffamilymember;
	}

	public void setStafffamilymember(String stafffamilymember) {
		this.stafffamilymember = stafffamilymember;
	}

	public String getStaffphysicalcondition() {
		return this.staffphysicalcondition;
	}

	public void setStaffphysicalcondition(String staffphysicalcondition) {
		this.staffphysicalcondition = staffphysicalcondition;
	}

	public String getStaffpoliticalstatus() {
		return this.staffpoliticalstatus;
	}

	public void setStaffpoliticalstatus(String staffpoliticalstatus) {
		this.staffpoliticalstatus = staffpoliticalstatus;
	}

	public String getStaffencourage() {
		return this.staffencourage;
	}

	public void setStaffencourage(String staffencourage) {
		this.staffencourage = staffencourage;
	}

	public String getStaffpunishment() {
		return this.staffpunishment;
	}

	public void setStaffpunishment(String staffpunishment) {
		this.staffpunishment = staffpunishment;
	}

	public String getStaffinsurance() {
		return this.staffinsurance;
	}

	public void setStaffinsurance(String staffinsurance) {
		this.staffinsurance = staffinsurance;
	}

	public String getStaffinvestigation() {
		return this.staffinvestigation;
	}

	public void setStaffinvestigation(String staffinvestigation) {
		this.staffinvestigation = staffinvestigation;
	}

	public String getStaffcertificate() {
		return this.staffcertificate;
	}

	public void setStaffcertificate(String staffcertificate) {
		this.staffcertificate = staffcertificate;
	}

	public String getStaffdocumentation() {
		return this.staffdocumentation;
	}

	public void setStaffdocumentation(String staffdocumentation) {
		this.staffdocumentation = staffdocumentation;
	}

	public String getStaffpersonalfile() {
		return this.staffpersonalfile;
	}

	public void setStaffpersonalfile(String staffpersonalfile) {
		this.staffpersonalfile = staffpersonalfile;
	}

	public String getStaffbankaccount() {
		return this.staffbankaccount;
	}

	public void setStaffbankaccount(String staffbankaccount) {
		this.staffbankaccount = staffbankaccount;
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

	public String getStaffagreement() {
		return staffagreement;
	}

	public void setStaffagreement(String staffagreement) {
		this.staffagreement = staffagreement;
	}

}