package com.tiantai.webservice.bo;

import javax.xml.bind.annotation.XmlRootElement;

import hy.ea.bo.CAccount;

/**
 * 用户业务类
 * @author Administrator
 *
 */ 
@XmlRootElement
public class LoginUser extends CAccount{
	//所属公司组织机构码
	private String companyIdentity;
	//部门Id
	private String organization;
	
	public String getCompanyIdentity() {
		return companyIdentity;
	}
	public void setCompanyIdentity(String companyIdentity) {
		this.companyIdentity = companyIdentity;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	} 
	
}
