package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Dtunitservicemanager entity. @author MyEclipse Persistence Tools
 */

public class Dtunitservicemanager implements BaseBean {

	// 单位服务办 （显示隐藏菜单）
	private String humanresourcekey;
	private String humanresourceid;
	private String companyid;
	private String personlist;
	private String bankcount;
	private String contactinformation;
	private String unitaddress;
	private String unitcontact;
	private String clientunitperson;
	private String unitcertificate;
	private String businessscope;
	private String annualvalueproduction;
	private String stateoperation;
	private String hobbysurvey;
	private String enterpriseindustry;
	private String productresource;
	private String clientlevelmember;
	private String clienttrackservice;
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;
	private String clientdemand;
	private String clientunitphotorecord;

	// Constructors

	/** default constructor */
	public Dtunitservicemanager() {
	}

	/** full constructor */
	public Dtunitservicemanager(String humanresourceid, String companyid,
			String personlist, String bankcount, String contactinformation,
			String unitaddress, String unitcontact, String clientunitperson,
			String unitcertificate, String businessscope,
			String annualvalueproduction, String stateoperation,
			String hobbysurvey, String enterpriseindustry,
			String productresource, String clientlevelmember,
			String clienttrackservice, String cname, Date ctime, String uname,
			Date utime, String clientdemand, String clientunitphotorecord) {
		this.humanresourceid = humanresourceid;
		this.companyid = companyid;
		this.personlist = personlist;
		this.bankcount = bankcount;
		this.contactinformation = contactinformation;
		this.unitaddress = unitaddress;
		this.unitcontact = unitcontact;
		this.clientunitperson = clientunitperson;
		this.unitcertificate = unitcertificate;
		this.businessscope = businessscope;
		this.annualvalueproduction = annualvalueproduction;
		this.stateoperation = stateoperation;
		this.hobbysurvey = hobbysurvey;
		this.enterpriseindustry = enterpriseindustry;
		this.productresource = productresource;
		this.clientlevelmember = clientlevelmember;
		this.clienttrackservice = clienttrackservice;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
		this.clientdemand = clientdemand;
		this.clientunitphotorecord = clientunitphotorecord;
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

	public String getPersonlist() {
		return this.personlist;
	}

	public void setPersonlist(String personlist) {
		this.personlist = personlist;
	}

	public String getBankcount() {
		return this.bankcount;
	}

	public void setBankcount(String bankcount) {
		this.bankcount = bankcount;
	}

	public String getContactinformation() {
		return this.contactinformation;
	}

	public void setContactinformation(String contactinformation) {
		this.contactinformation = contactinformation;
	}

	public String getUnitaddress() {
		return this.unitaddress;
	}

	public void setUnitaddress(String unitaddress) {
		this.unitaddress = unitaddress;
	}

	public String getUnitcontact() {
		return this.unitcontact;
	}

	public void setUnitcontact(String unitcontact) {
		this.unitcontact = unitcontact;
	}

	public String getClientunitperson() {
		return this.clientunitperson;
	}

	public void setClientunitperson(String clientunitperson) {
		this.clientunitperson = clientunitperson;
	}

	public String getUnitcertificate() {
		return this.unitcertificate;
	}

	public void setUnitcertificate(String unitcertificate) {
		this.unitcertificate = unitcertificate;
	}

	public String getBusinessscope() {
		return this.businessscope;
	}

	public void setBusinessscope(String businessscope) {
		this.businessscope = businessscope;
	}

	public String getAnnualvalueproduction() {
		return this.annualvalueproduction;
	}

	public void setAnnualvalueproduction(String annualvalueproduction) {
		this.annualvalueproduction = annualvalueproduction;
	}

	public String getStateoperation() {
		return this.stateoperation;
	}

	public void setStateoperation(String stateoperation) {
		this.stateoperation = stateoperation;
	}

	public String getHobbysurvey() {
		return this.hobbysurvey;
	}

	public void setHobbysurvey(String hobbysurvey) {
		this.hobbysurvey = hobbysurvey;
	}

	public String getEnterpriseindustry() {
		return this.enterpriseindustry;
	}

	public void setEnterpriseindustry(String enterpriseindustry) {
		this.enterpriseindustry = enterpriseindustry;
	}

	public String getProductresource() {
		return this.productresource;
	}

	public void setProductresource(String productresource) {
		this.productresource = productresource;
	}

	public String getClientlevelmember() {
		return this.clientlevelmember;
	}

	public void setClientlevelmember(String clientlevelmember) {
		this.clientlevelmember = clientlevelmember;
	}

	public String getClienttrackservice() {
		return this.clienttrackservice;
	}

	public void setClienttrackservice(String clienttrackservice) {
		this.clienttrackservice = clienttrackservice;
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

	public String getClientdemand() {
		return this.clientdemand;
	}

	public void setClientdemand(String clientdemand) {
		this.clientdemand = clientdemand;
	}

	public String getClientunitphotorecord() {
		return this.clientunitphotorecord;
	}

	public void setClientunitphotorecord(String clientunitphotorecord) {
		this.clientunitphotorecord = clientunitphotorecord;
	}

}