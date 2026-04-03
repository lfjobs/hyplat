package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 	社会往来单位显示隐藏
 * @author l  2013.5.10
 */
public class Contactresource implements BaseBean{

	// Fields

	private String contactresourcekey;
	private String contactresourceid;
	
	private String ccomconf;//公司主页ljc
	private String companyid;   //公司
	private String certificate;	//证件管理
	private String contacttype;	//联系方式
	private String registration;//银行帐号
	private String contactcom;	//人员列表
	private String contactimg;//图片列表
	
	private String contactinout; //呼入呼出
	private String contactzxgztj;//咨询跟踪统计
	private String contactzxgz;//咨询跟踪
	private String contactscope; //经营范围
	private String contactannual; //年产值
	private String contactoperation;//经营状况调查
	private String contactglzah;//管理着爱好调查
	private String contactqysshy;//企业所属行业
	private String contactkhcpzy; //客户产品资源
	private String contactkhjbhy;//客户级别会员
	private String contactkhgzfw;//客户跟踪服务
	private String contactkhxqdy;//客户需求调研
	private String contactphone; //电话记录
	
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	public String getContactresourcekey() {
		return this.contactresourcekey;
	}

	public void setContactresourcekey(String contactresourcekey) {
		this.contactresourcekey = contactresourcekey;
	}

	public String getContactresourceid() {
		return this.contactresourceid;
	}

	public void setContactresourceid(String contactresourceid) {
		this.contactresourceid = contactresourceid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getContacttype() {
		return this.contacttype;
	}

	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}

	public String getRegistration() {
		return this.registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getContactcom() {
		return this.contactcom;
	}

	public void setContactcom(String contactcom) {
		this.contactcom = contactcom;
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

	public String getContactimg() {
		return contactimg;
	}

	public void setContactimg(String contactimg) {
		this.contactimg = contactimg;
	}

	public String getContactinout() {
		return contactinout;
	}

	public void setContactinout(String contactinout) {
		this.contactinout = contactinout;
	}

	public String getContactzxgztj() {
		return contactzxgztj;
	}

	public void setContactzxgztj(String contactzxgztj) {
		this.contactzxgztj = contactzxgztj;
	}

	public String getContactzxgz() {
		return contactzxgz;
	}

	public void setContactzxgz(String contactzxgz) {
		this.contactzxgz = contactzxgz;
	}

	public String getContactscope() {
		return contactscope;
	}

	public void setContactscope(String contactscope) {
		this.contactscope = contactscope;
	}

	public String getContactannual() {
		return contactannual;
	}

	public void setContactannual(String contactannual) {
		this.contactannual = contactannual;
	}

	public String getContactoperation() {
		return contactoperation;
	}

	public void setContactoperation(String contactoperation) {
		this.contactoperation = contactoperation;
	}

	public String getContactglzah() {
		return contactglzah;
	}

	public void setContactglzah(String contactglzah) {
		this.contactglzah = contactglzah;
	}

	public String getContactqysshy() {
		return contactqysshy;
	}

	public void setContactqysshy(String contactqysshy) {
		this.contactqysshy = contactqysshy;
	}

	public String getContactkhcpzy() {
		return contactkhcpzy;
	}

	public void setContactkhcpzy(String contactkhcpzy) {
		this.contactkhcpzy = contactkhcpzy;
	}

	public String getContactkhjbhy() {
		return contactkhjbhy;
	}

	public void setContactkhjbhy(String contactkhjbhy) {
		this.contactkhjbhy = contactkhjbhy;
	}

	public String getContactkhgzfw() {
		return contactkhgzfw;
	}

	public void setContactkhgzfw(String contactkhgzfw) {
		this.contactkhgzfw = contactkhgzfw;
	}

	public String getContactkhxqdy() {
		return contactkhxqdy;
	}

	public void setContactkhxqdy(String contactkhxqdy) {
		this.contactkhxqdy = contactkhxqdy;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public String getCcomconf() {
		return ccomconf;
	}

	public void setCcomconf(String ccomconf) {
		this.ccomconf = ccomconf;
	}

}