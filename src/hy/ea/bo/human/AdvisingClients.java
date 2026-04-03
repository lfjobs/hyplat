/**
 * Responsibilities
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * ZC
 * @author  AdvisingClients  指导客户办
 */
public class AdvisingClients implements BaseBean,ExcelBean,java.io.Serializable{
  private String advisingClientsKey;
  private String advisingClientsID;
  
  private String companyID;
  private String organizationID;
  
  private String companyCode;//公司编号
  private String companyName;//公司名称
  private String department;//部门
  private String contactPeople;//联系人
  private String contactPhone;//联系电话
  private String customerServiceContent;//客户服务内容
  private String note;//备注
  
  public static String[] columnHeadings() {
		String[] titles = { "序号", "公司编号", "公司名称 ", "部门", "联系人", "联系电话", "客户服务内容",
				"备注"};
		return titles;
	}
	public String[] properties() {
		String[] properties = { companyCode, companyName, department,contactPeople, contactPhone,
				customerServiceContent, note };
		return properties ;
	}
	
	public String getAdvisingClientsKey() {
		return advisingClientsKey;
	}
	public void setAdvisingClientsKey(String advisingClientsKey) {
		this.advisingClientsKey = advisingClientsKey;
	}
	public String getAdvisingClientsID() {
		return advisingClientsID;
	}
	public void setAdvisingClientsID(String advisingClientsID) {
		this.advisingClientsID = advisingClientsID;
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
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getContactPeople() {
		return contactPeople;
	}
	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCustomerServiceContent() {
		return customerServiceContent;
	}
	public void setCustomerServiceContent(String customerServiceContent) {
		this.customerServiceContent = customerServiceContent;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
