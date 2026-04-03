package hy.ea.bo.office.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * ViewArchiveId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ViewArchive implements java.io.Serializable,BaseBean{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4430210731413255224L;
	private String historykey;
	private String historyid;
	private String inuser;
	private String inusername;
	private Date intime;
	private String locationid;
	private String locationname;
	private String outusername;
	private Date outtime;
	private Long state;
	private String archivesid;
	private String archivecode;
	private String companyid;
	private String barcode;
	private String chipid;
	private String name;
	private String securitylevel;
	private String obsoletestatus;
	private Date startvalidity;
	private Date endvalidity;
	private String staffid;
	private String catemodule;
	private String categroyid;
	private String parent;
	private String categroyname;
	private Date renewaldate;
	private String member;
	private String attach;
	//数据库没有
	private String intimestr;
	private String outtimestr;
	private String companyname;
	private String org;
	

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getIntimestr() {
		return intimestr;
	}

	public void setIntimestr(String intimestr) {
		this.intimestr = intimestr;
	}

	public String getOuttimestr() {
		return outtimestr;
	}

	public void setOuttimestr(String outtimestr) {
		this.outtimestr = outtimestr;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Date getRenewaldate() {
		return renewaldate;
	}

	public void setRenewaldate(Date renewaldate) {
		this.renewaldate = renewaldate;
	}

	public String getHistorykey() {
		return this.historykey;
	}

	public void setHistorykey(String historykey) {
		this.historykey = historykey;
	}

	public String getHistoryid() {
		return this.historyid;
	}

	public void setHistoryid(String historyid) {
		this.historyid = historyid;
	}

	public String getInuser() {
		return this.inuser;
	}

	public void setInuser(String inuser) {
		this.inuser = inuser;
	}

	public String getInusername() {
		return this.inusername;
	}

	public void setInusername(String inusername) {
		this.inusername = inusername;
	}

	public Date getIntime() {
		return this.intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public String getLocationid() {
		return this.locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getOutusername() {
		return this.outusername;
	}

	public void setOutusername(String outusername) {
		this.outusername = outusername;
	}

	public Date getOuttime() {
		return this.outtime;
	}

	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}

	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getArchivesid() {
		return this.archivesid;
	}

	public void setArchivesid(String archivesid) {
		this.archivesid = archivesid;
	}

	public String getArchivecode() {
		return this.archivecode;
	}

	public void setArchivecode(String archivecode) {
		this.archivecode = archivecode;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getChipid() {
		return this.chipid;
	}

	public void setChipid(String chipid) {
		this.chipid = chipid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecuritylevel() {
		return this.securitylevel;
	}

	public void setSecuritylevel(String securitylevel) {
		this.securitylevel = securitylevel;
	}

	public String getObsoletestatus() {
		return this.obsoletestatus;
	}

	public void setObsoletestatus(String obsoletestatus) {
		this.obsoletestatus = obsoletestatus;
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

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getCatemodule() {
		return this.catemodule;
	}

	public void setCatemodule(String catemodule) {
		this.catemodule = catemodule;
	}

	public String getCategroyid() {
		return this.categroyid;
	}

	public void setCategroyid(String categroyid) {
		this.categroyid = categroyid;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getCategroyname() {
		return this.categroyname;
	}

	public void setCategroyname(String categroyname) {
		this.categroyname = categroyname;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

}