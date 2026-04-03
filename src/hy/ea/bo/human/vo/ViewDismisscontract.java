package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;


public class ViewDismisscontract implements BaseBean {

	// Fields

	private String dismisscontractkey;
	private String contractid;
	private String staffid;
	private String staffname;
	private String sex;
	private String staffidentitycard;
	private Date registerdate;
	private Date contractsigndate;
	private String contractcode;
	private Date startvalidity;
	private Date endvalidity;
	private Date renewaldate;
	private Date dimissiondate;
	private String dimissionstatus;
	private String companyid;

	public String getDismisscontractkey() {
		return this.dismisscontractkey;
	}

	public void setDismisscontractkey(String dismisscontractkey) {
		this.dismisscontractkey = dismisscontractkey;
	}

	public String getContractid() {
		return this.contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStaffidentitycard() {
		return this.staffidentitycard;
	}

	public void setStaffidentitycard(String staffidentitycard) {
		this.staffidentitycard = staffidentitycard;
	}

	public Date getRegisterdate() {
		return this.registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public Date getContractsigndate() {
		return this.contractsigndate;
	}

	public void setContractsigndate(Date contractsigndate) {
		this.contractsigndate = contractsigndate;
	}

	public String getContractcode() {
		return this.contractcode;
	}

	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
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

	public Date getRenewaldate() {
		return this.renewaldate;
	}

	public void setRenewaldate(Date renewaldate) {
		this.renewaldate = renewaldate;
	}

	public Date getDimissiondate() {
		return this.dimissiondate;
	}

	public void setDimissiondate(Date dimissiondate) {
		this.dimissiondate = dimissiondate;
	}

	public String getDimissionstatus() {
		return this.dimissionstatus;
	}

	public void setDimissionstatus(String dimissionstatus) {
		this.dimissionstatus = dimissionstatus;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

}