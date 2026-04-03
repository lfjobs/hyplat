package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 员工工作计划
 */
public class COSJobPlan implements BaseBean, ExcelBean,java.io.Serializable {
	private String jobPlanKey;
	private String jobPlanID;    //        **
	private String companyID;//责任人公司      ** **************************** 
	private String staffID; //责任人ID      ** **************************** 
	private String organizationID; //      **
	private Date startDate;// 任务起始日期    ** **************************** 
	private Date endDate;// 任务结束日期   ** **************************** 
	private String projectpercentage ;// 所占项目百分比
	private String plancheck ;// 任务计划检查人
	private String postmanage;//岗位情况管理 
	private String taskName;//任务名称
	private String supervisor;// 主管签字
	private String humanSupervisor;// 人事主管理
	private String manager;// 公司经理
	
	private String entryDate;//录入日期  **
	
	
	private String strStartDate;  //任务详细开始时间
	private String strEndDate;   //任务详细结束时间

	private String projectcode;          //项目编号
	private String jobName;              //项目内容   ** **************************** 
	private String jobContent;           // 项目明细内容 ** **************************** 
	private String entry;                // 完成情况  待办 已办  完成   未完成  
	
	private String projectDetailsCode;   //项目明细编号
	private String projectRequirements ; //项目要求
	private String fraction;             //应得分数
	private String detailsfraction;      //任务详细应得分数
	private String actualdetailsScore;   //任务详细实际得分分数 
	private String points;               //扣分
	private String actualScore;          //实际得分
	
	
	private String codeID ;		  //           **
	private String codeValue ; //任务类别   **
	private String jobstatus; //日周月季年     ****************************  00 01 02 03 04
	private String staffIDS; //发起人ID     **
	private String staffNameS ; //发起人姓名 **
	private String staffName; //责任人姓名 **
	private String companyIDS; //发起人公司 **
	
	
	
	
	public String getCompanyIDS() {
		return companyIDS;
	}

	public void setCompanyIDS(String companyIDS) {
		this.companyIDS = companyIDS;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNameS() {
		return staffNameS;
	}

	public void setStaffNameS(String staffNameS) {
		this.staffNameS = staffNameS;
	}

	public String getStaffIDS() {
		return staffIDS;
	}

	public void setStaffIDS(String staffIDS) {
		this.staffIDS = staffIDS;
	}

	public String getJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
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

	public static String[] columnHeadings() {
		String[] titles = { "序号","录入日期", "起始日期","结束日期","项目编码","项目名称","项目类别","项目内容","项目工作状态","发起人", "主管签字", "人事主管理",
				"公司经理" };
		return titles;
	}

	public static String[] columnHeadingsSummary() {
		String[] titles = { "序号","公司","部门","员工编号","员工姓名","起始日期","结束日期","项目编码","项目名称","项目类别","项目内容","完成情况","发起人","主管签字", "人事主管理",
				"公司经理" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { entryDate,String.format("%tF", startDate)+" "+String.format("%tT", startDate), String.format("%tF", endDate)+" "+String.format("%tT", endDate),
				projectcode,jobName,codeValue, jobContent,entry,staffNameS,supervisor, humanSupervisor,manager};
		return properties;
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

	public String getDetailsfraction() {
		return detailsfraction;
	}

	public void setDetailsfraction(String detailsfraction) {
		this.detailsfraction = detailsfraction;
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

	public String getStrStartDate() {
		return strStartDate;
	}

	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
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

	public String getProjectpercentage() {
		return projectpercentage;
	}

	public void setProjectpercentage(String projectpercentage) {
		this.projectpercentage = projectpercentage;
	}

	public String getPlancheck() {
		return plancheck;
	}

	public String getActualdetailsScore() {
		return actualdetailsScore;
	}

	public void setActualdetailsScore(String actualdetailsScore) {
		this.actualdetailsScore = actualdetailsScore;
	}

	public void setPlancheck(String plancheck) {
		this.plancheck = plancheck;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	


}
