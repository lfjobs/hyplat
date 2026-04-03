package hy.ea.bo.human;

import hy.plat.bo.BaseBean;
/**
 * 往来个人实体
 * @author 陈小丰
 */
public class CustomerType implements BaseBean{
	private String customerKey;
	private String customerID;
	private String staffID;
	private String companyID;
	private String customerrelation;
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCustomerrelation() {
		return customerrelation;
	}
	public void setCustomerrelation(String customerrelation) {
		this.customerrelation = customerrelation;
	}
}
