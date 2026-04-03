/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * cxf
 * 客户增值服务办
 * @author Administrator
 */
public class ClientIncrement implements BaseBean,ExcelBean,java.io.Serializable{
	private String  clientIncrementKey;
	private String  clientIncrementID;
	private String  companyID;
	private String 	organizationID;
	private String  clientCode;             //客户编号
	private String 	clientName;             //客户名称
	private String 	clientService;          //客户服务
	private String 	clientComplaint;        //客户投诉
	private String 	clientAppraise;         //客户评价
	private String 	clientFeedback;         //客户服务反馈
	private String 	tack;                   //提供附加值
	private String 	remark;                 //备注
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "客户编号", "客户名称", "客户服务", "客户投诉", "客户评价",
				 "客户服务反馈", "提供附加值","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { clientCode,clientName,clientService, 
				clientComplaint, clientAppraise, clientFeedback,tack,remark};
		return properties;
	}
	public String getClientIncrementKey() {
		return clientIncrementKey;
	}
	public void setClientIncrementKey(String clientIncrementKey) {
		this.clientIncrementKey = clientIncrementKey;
	}
	public String getClientIncrementID() {
		return clientIncrementID;
	}
	public void setClientIncrementID(String clientIncrementID) {
		this.clientIncrementID = clientIncrementID;
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
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientService() {
		return clientService;
	}
	public void setClientService(String clientService) {
		this.clientService = clientService;
	}
	public String getClientComplaint() {
		return clientComplaint;
	}
	public void setClientComplaint(String clientComplaint) {
		this.clientComplaint = clientComplaint;
	}
	public String getClientAppraise() {
		return clientAppraise;
	}
	public void setClientAppraise(String clientAppraise) {
		this.clientAppraise = clientAppraise;
	}
	public String getClientFeedback() {
		return clientFeedback;
	}
	public void setClientFeedback(String clientFeedback) {
		this.clientFeedback = clientFeedback;
	}
	public String getTack() {
		return tack;
	}
	public void setTack(String tack) {
		this.tack = tack;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
