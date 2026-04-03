package com.tiantai.nwa.tbank.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
/**
 * 银行账户对象
 * @author zhb
 *
 */
public class BankAccount implements Serializable,BaseBean, ExcelBean {
	
	private String pkey; //自主主键，没有实际意义 //32
	private String accid; //账户ID //50
	private String account;//账户号 //50
	private String banksx;//银行缩写 //10
	private String provcode;//省份代码(农行定义) //4
	private String currency;//币种代码(农行定义) //4
	private String bindto;//绑定到部门或公司 //1
	private String orgid;//绑定部门id //50
	private String orgname;//绑定部门名称 //50
	private String companyid;//绑定公司id //50
	private String companyname;//绑定公司名称 //100
	private String responser;//责任人//50
	private String accounttype;//账户性质//10
	private String accountowner;//户主//50
	private String relation;//往来关系//20
	private Date cdate;//创建日期
	private Date mdate;//修改日期   
	
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBanksx() {
		return banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getProvcode() {
		return provcode;
	}

	public void setProvcode(String provcode) {
		this.provcode = provcode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBindto() {
		return bindto;
	}

	public void setBindto(String bindto) {
		this.bindto = bindto;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getResponser() {
		return responser;
	}

	public void setResponser(String responser) {
		this.responser = responser;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getAccountowner() {
		return accountowner;
	}

	public void setAccountowner(String accountowner) {
		this.accountowner = accountowner;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}	

}
