
package hy.ea.bo;

import java.io.Serializable;

import hy.plat.bo.BaseBean;
/**
 * Company Menu Interface
 * 用于取得给菜单分配的BO的Interface
 */
public class COA implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String coaKey;
	private String coaID;
	private String companyID;
	private String organizationID;
	private String accountID;
	public String getCoaKey() {
		return coaKey;
	}
	public void setCoaKey(String coaKey) {
		this.coaKey = coaKey;
	}
	public String getCoaID() {
		return coaID;
	}
	public void setCoaID(String coaID) {
		this.coaID = coaID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
}
