package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 员工工作计划VO
 */
public class COSJobPlanVO implements BaseBean, ExcelBean ,Serializable {
	private String organizationName;
	private String companyName;
	private String staffName;
	private String staffCode;
	private String jobPlanKey;
	private String jobPlanID;
	private String companyID;
	private String staffID;
	private String organizationID;
	
	private Date startDate;// 起始日期
	
	private String postmanage;//岗位情况管理
	private Date endDate;// 结束日期

	private String supervisor;// 主管签字
	private String humanSupervisor;// 人事主管理
	private String manager;// 公司经理
	
	private String entryDate;//录入日期
	

	private String jobName;              //项目内容
	private String jobContent;           // 项目明细内容
	private String entry;                // 完成情况    
	
	private String projectcode;          //项目编号
	private String projectDetailsCode;   //项目明细编号
	private String projectRequirements ; //项目要求
	private String fraction;             //应得分数
	private String points;               //扣分
	private String actualScore;          //实际得分
	private String codeID ;		
	private String codeValue ; //工作，项目类别
	private String jobstatus; //日周月季年     ****************************  00 01 02 03 04
	

	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司","部门","员工姓名","员工编号","录入日期", "起始日期","结束日期","项目编码","项目名称","项目类别","项目内容","完成情况","项目分类","应得分数","扣分","实际得分", "主管签字", "人事主管理",
				"公司经理" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {companyName,organizationName,staffName,staffCode, entryDate,String.format("%tF", startDate)+" "+String.format("%tT", startDate), String.format("%tF", endDate)+" "+String.format("%tT", endDate),
				projectcode,jobName,codeValue,jobContent,entry,getJobstatusN(),fraction,points,actualScore,supervisor, humanSupervisor,manager};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	/**
	 * 通过jobstatus得到名称
	 * @return
	 */
	public String getJobstatusN(){
		String jobstatusN = "";
		if(oMap != null){
			jobstatusN = oMap.get(jobstatus);
		}
		return jobstatusN;
	}
	
	
	public String getPostmanage() {
		return postmanage;
	}

	public void setPostmanage(String postmanage) {
		this.postmanage = postmanage;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getHumanSupervisor() {
		return humanSupervisor;
	}

	public void setHumanSupervisor(String humanSupervisor) {
		this.humanSupervisor = humanSupervisor;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getJobPlanKey() {
		return jobPlanKey;
	}

	public void setJobPlanKey(String jobPlanKey) {
		this.jobPlanKey = jobPlanKey;
	}

	public String getJobPlanID() {
		return jobPlanID;
	}

	public void setJobPlanID(String jobPlanID) {
		this.jobPlanID = jobPlanID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}


	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}

	public String getProjectDetailsCode() {
		return projectDetailsCode;
	}

	public void setProjectDetailsCode(String projectDetailsCode) {
		this.projectDetailsCode = projectDetailsCode;
	}

	public String getProjectRequirements() {
		return projectRequirements;
	}

	public void setProjectRequirements(String projectRequirements) {
		this.projectRequirements = projectRequirements;
	}

	public String getFraction() {
		return fraction;
	}

	public void setFraction(String fraction) {
		this.fraction = fraction;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getActualScore() {
		return actualScore;
	}

	public void setActualScore(String actualScore) {
		this.actualScore = actualScore;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

}
