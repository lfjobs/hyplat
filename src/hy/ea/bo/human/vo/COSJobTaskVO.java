package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作目标任务
 */
public class COSJobTaskVO implements BaseBean, ExcelBean,Serializable {
	private String organizationName;
	private String companyName;
	private String staffName;
	private String staffCode;
	private String jobTaskKey;
	private String jobTaskID;
	private String companyID;
	private String staffID;
	private String organizationID;
	private String neededTime;// 任务时间
	private String taskNumber;// 任务编号
	private String taskName;// 任务名称
	private Date startDate;// 任务完成起时间
	private Date endDate;// 任务完成止时间
	private String bonusPoint;// 完成奖分设定
	private String penaltyPoint;// 未完成扣分设定
	private Date checkUpTime;// 检查任务时间
	private String actualperformance;// 检查任务完成情况
	private String actualBonusPoint;// 完成奖分
	private String actualPenaltyPoint;// 未完成扣分
	private String status;// 状态 00 未完成 01 完成
	private String codeID ;		
	private String codeValue ; //任务类别
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司","部门", "姓名","编号","任务时间", "任务编号", "任务名称", "任务完成起时间", "任务完成止时间", "任务类别",
				"完成奖分设定", "未完成扣分设定", "检查任务时间", "检查任务完成情况", "完成奖分", "未完成扣分" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {companyName,organizationName,staffName,staffCode,neededTime, taskNumber,taskName,String.format("%1$tF", startDate),
				String.format("%1$tF", endDate),codeValue, bonusPoint, penaltyPoint,String.format("%1$tF", checkUpTime),
				actualperformance, actualBonusPoint ,actualPenaltyPoint};
		return properties;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJobTaskKey() {
		return jobTaskKey;
	}

	public void setJobTaskKey(String jobTaskKey) {
		this.jobTaskKey = jobTaskKey;
	}

	public String getJobTaskID() {
		return jobTaskID;
	}

	public void setJobTaskID(String jobTaskID) {
		this.jobTaskID = jobTaskID;
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

	public String getNeededTime() {
		return neededTime;
	}

	public void setNeededTime(String neededTime) {
		this.neededTime = neededTime;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getBonusPoint() {
		return bonusPoint;
	}

	public void setBonusPoint(String bonusPoint) {
		this.bonusPoint = bonusPoint;
	}

	public String getPenaltyPoint() {
		return penaltyPoint;
	}

	public void setPenaltyPoint(String penaltyPoint) {
		this.penaltyPoint = penaltyPoint;
	}

	public Date getCheckUpTime() {
		return checkUpTime;
	}

	public void setCheckUpTime(Date checkUpTime) {
		this.checkUpTime = checkUpTime;
	}

	public String getActualperformance() {
		return actualperformance;
	}

	public void setActualperformance(String actualperformance) {
		this.actualperformance = actualperformance;
	}

	public String getActualBonusPoint() {
		return actualBonusPoint;
	}

	public void setActualBonusPoint(String actualBonusPoint) {
		this.actualBonusPoint = actualBonusPoint;
	}

	public String getActualPenaltyPoint() {
		return actualPenaltyPoint;
	}

	public void setActualPenaltyPoint(String actualPenaltyPoint) {
		this.actualPenaltyPoint = actualPenaltyPoint;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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


}
