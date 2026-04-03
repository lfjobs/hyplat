/**
 * Responsibilities
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * ZC
 * @author  TransactionService  客户成交服务
 */
public class TransactionService implements BaseBean,ExcelBean,java.io.Serializable{
  private String transactionServiceKey;
  private String transactionServiceID;
  
  private String companyID;
  private String organizationID;
  
  private String transactionServiceCode;//客户成交序号
  private String serviceCode;//服务ID
  private String customerCode ;//客户编号
  private String customerName;//客户名称
  private String customerContact;//客户联系人
  private String serviceType;//客户服务类型
  private String serviceMode;//客户服务方式
  private String ServiceDepartment;//公司服务部门
  private String ServiceExecutor;//公司服务执行人
  
  public static String[] columnHeadings() {
		String[] titles = { "序号", "客户成交序号", "服务ID", "客户编号", "客户名称", "客户联系人", "客户服务类型",
				"客户服务方式", "公司服务部门" ,"公司服务执行人"};
		return titles;
	}
	public String[] properties() {
		String[] properties = { transactionServiceCode, serviceCode, customerCode,customerName, customerContact,
				serviceType, serviceMode, ServiceDepartment,ServiceExecutor };
		return properties ;
	}
	public String getTransactionServiceKey() {
		return transactionServiceKey;
	}
	public void setTransactionServiceKey(String transactionServiceKey) {
		this.transactionServiceKey = transactionServiceKey;
	}
	public String getTransactionServiceID() {
		return transactionServiceID;
	}
	public void setTransactionServiceID(String transactionServiceID) {
		this.transactionServiceID = transactionServiceID;
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
	public String getTransactionServiceCode() {
		return transactionServiceCode;
	}
	public void setTransactionServiceCode(String transactionServiceCode) {
		this.transactionServiceCode = transactionServiceCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceMode() {
		return serviceMode;
	}
	public void setServiceMode(String serviceMode) {
		this.serviceMode = serviceMode;
	}
	public String getServiceDepartment() {
		return ServiceDepartment;
	}
	public void setServiceDepartment(String serviceDepartment) {
		ServiceDepartment = serviceDepartment;
	}
	public String getServiceExecutor() {
		return ServiceExecutor;
	}
	public void setServiceExecutor(String serviceExecutor) {
		ServiceExecutor = serviceExecutor;
	}
	
}
