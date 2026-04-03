package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 户内户外宣传管理Bean
 * @author Administrator
 *
 */
public class InOutPropagate implements BaseBean,ExcelBean,java.io.Serializable{

	private static Map<String,String> oMap;
	public String getOName(){
		String sName = "";
		if(null != oMap){
			sName = oMap.get(corganizationID);
		}
		return sName;
	}
	public static String[] columnHeadings(){
		String[] titles = {"序号","编号","宣传部门","宣传承办人","宣传主题","宣传内容","开始日期","结束日期","预算费用"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {serialNumber,getOName(),executePerson,subject,content,String.format("%1$tF", startDate),String.format("%1$tF", endDate),cost};
		return properties;
	}
	private String propagateKey;
	private String propagateID;			
	private String companyID;
	private String organizationID;
	
	private String  serialNumber;		//编号
	private String corganizationID;		//宣传部门
	private String executePerson;		//承办人
	private Date  startDate;			//开始日期
	private Date  endDate;				//结束日期
	private String subject;				//宣传主题
	private String content;				//宣传内容
	private String cost;				//预算费用 
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
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
	public String getCorganizationID() {
		return corganizationID;
	}
	public void setCorganizationID(String corganizationID) {
		this.corganizationID = corganizationID;
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
}
