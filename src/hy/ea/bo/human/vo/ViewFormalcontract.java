package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * ViewFormalcontractId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ViewFormalcontract implements BaseBean {

	// Fields

	private String formalcontractkey;
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
	private String companyid;

	public String getFormalcontractkey() {
		return this.formalcontractkey;
	}

	public void setFormalcontractkey(String formalcontractkey) {
		this.formalcontractkey = formalcontractkey;
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

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

}