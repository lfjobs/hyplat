package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 预算招标单生成中间表（用于保存项目名称字段）
 * 
 * @author ct
 * 
 */
public class projectBills implements BaseBean {
	private String projectID;			//业务主键
	private String projectKey;			//数据库主键
	private String csbID;				//项目单据表id
	private String groupCompanySn;     	//集团公司的标识  外键
	private String companyID;          	//公司  外键
	private String projectName;			//项目名称
	private Date startTime;				//起时间
	private Date endTime;				//止时间
	private String costdescription;		//项目内容
	private String cashierBillsID;		//生成三审单
	private String type;//收款单中间表还是支出单中间表；
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	public String getCsbID() {
		return csbID;
	}
	public void setCsbID(String csbID) {
		this.csbID = csbID;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCostdescription() {
		return costdescription;
	}
	public void setCostdescription(String costdescription) {
		this.costdescription = costdescription;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
