package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 网络宣传管理Bean
 * @author Administrator
 *
 */
public class Propagate implements BaseBean,ExcelBean,java.io.Serializable{

	public static String[] columnHeadings(){
		String[] titles = {"序号","编号","开始日期","结束日期","网络名称","宣传主题","宣传内容","预算费用","宣传人员","批准人","承办人"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {serialNumber,String.format("%1$tF", startDate),String.format("%1$tF", endDate),netName,subject,content,cost,propagatePerson,approvePerson,executePerson};
		return properties;
	}
	private String propagateKey;
	private String propagateID;			
	private String companyID;
	private String organizationID;
	
	private String  serialNumber;		//编号
	private Date  startDate;				//开始日期
	private Date  endDate;					//结束日期
	private String netName;				//网络名称
	private String subject;				//宣传主题
	private String content;				//宣传内容
	private String cost;				//预算费用
	private String propagatePerson;		//宣传人员
	private String approvePerson;		//批准人
	private String executePerson;		//承办人
	public String getPropagateKey() {
		return propagateKey;
	}
	public void setPropagateKey(String propagateKey) {
		this.propagateKey = propagateKey;
	}
	public String getPropagateID() {
		return propagateID;
	}
	public void setPropagateID(String propagateID) {
		this.propagateID = propagateID;
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getPropagatePerson() {
		return propagatePerson;
	}
	public void setPropagatePerson(String propagatePerson) {
		this.propagatePerson = propagatePerson;
	}
	public String getApprovePerson() {
		return approvePerson;
	}
	public void setApprovePerson(String approvePerson) {
		this.approvePerson = approvePerson;
	}
	public String getExecutePerson() {
		return executePerson;
	}
	public void setExecutePerson(String executePerson) {
		this.executePerson = executePerson;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
