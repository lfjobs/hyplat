package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 考评
 * @author zgzg
 *
 */
public class StaffAppraisalSummary implements BaseBean, ExcelBean,Serializable {
	private String appraisalKey;
	private String appraisalID;
	
	private String  companyID;
	private String  companyName;
		
	private String 	staffID;
	private String staffName;//员工姓名
	
	private String staffCode;//员工编号
	private String payScaleID;		//工资级别ID

	private String checkPerson; // 参会考评人

	private Date appraisalDate; // 考评时间
	private String workDateSaturation;  //工作日饱和度
	/**
	 * 职责得分
	 */
	private String responsibility1; // 遵守法律
	private String responsibility2; // 责任心
	private String responsibility3; // 原则性
	/**
	 * 业绩得分
	 */
	private String achievements1; // 工作完成率
	private String achievements2; // 工作量是否饱和
	private String achievements3; // 工作质量
	/**
	 * 任务得分
	 */
	private String task1; // 任务完成率
	private String task2; // 目标是否明确
	private String task3; // 任务完成主动性
	/**
	 * 能力得分
	 */
	private String ability1; // 专业技术能力
	private String ability2; // 管理能力
	private String ability3; // 综合素质能力
	/**
	 * 态度得分
	 */
	private String manner1; // 出勤率
	private String manner2; // 工作主动性
	private String manner3; // 文明礼貌素质

	private String status;// 状态

	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","员工编号","员工姓名" ,"考评时间","参会考评人", "工作日饱和度", "遵守法律", "责任心", "原则性","工作完成率", "工作量是否饱和", "工作质量","任务完成率","目标是否明确","任务完成主动性","专业技术能力","管理能力","综合素质能力","出勤率","工作主动性","文明礼貌素质"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {companyName,staffCode,staffName,String.format("%1$tF", appraisalDate),checkPerson,workDateSaturation,responsibility1,responsibility2,responsibility3,achievements1,achievements2,achievements3,task1,task2,task3,ability1,ability2,ability3,manner1,manner2,manner3};
		return properties;
	}

	public String getAppraisalKey() {
		return appraisalKey;
	}

	public void setAppraisalKey(String appraisalKey) {
		this.appraisalKey = appraisalKey;
	}

	public String getAppraisalID() {
		return appraisalID;
	}

	public void setAppraisalID(String appraisalID) {
		this.appraisalID = appraisalID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPayScaleID() {
		return payScaleID;
	}

	public void setPayScaleID(String payScaleID) {
		this.payScaleID = payScaleID;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getAppraisalDate() {
		return appraisalDate;
	}

	public void setAppraisalDate(Date appraisalDate) {
		this.appraisalDate = appraisalDate;
	}

	public String getWorkDateSaturation() {
		return workDateSaturation;
	}

	public void setWorkDateSaturation(String workDateSaturation) {
		this.workDateSaturation = workDateSaturation;
	}

	public String getResponsibility1() {
		return responsibility1;
	}

	public void setResponsibility1(String responsibility1) {
		this.responsibility1 = responsibility1;
	}

	public String getResponsibility2() {
		return responsibility2;
	}

	public void setResponsibility2(String responsibility2) {
		this.responsibility2 = responsibility2;
	}

	public String getResponsibility3() {
		return responsibility3;
	}

	public void setResponsibility3(String responsibility3) {
		this.responsibility3 = responsibility3;
	}

	public String getAchievements1() {
		return achievements1;
	}

	public void setAchievements1(String achievements1) {
		this.achievements1 = achievements1;
	}

	public String getAchievements2() {
		return achievements2;
	}

	public void setAchievements2(String achievements2) {
		this.achievements2 = achievements2;
	}

	public String getAchievements3() {
		return achievements3;
	}

	public void setAchievements3(String achievements3) {
		this.achievements3 = achievements3;
	}

	public String getTask1() {
		return task1;
	}

	public void setTask1(String task1) {
		this.task1 = task1;
	}

	public String getTask2() {
		return task2;
	}

	public void setTask2(String task2) {
		this.task2 = task2;
	}

	public String getTask3() {
		return task3;
	}

	public void setTask3(String task3) {
		this.task3 = task3;
	}

	public String getAbility1() {
		return ability1;
	}

	public void setAbility1(String ability1) {
		this.ability1 = ability1;
	}

	public String getAbility2() {
		return ability2;
	}

	public void setAbility2(String ability2) {
		this.ability2 = ability2;
	}

	public String getAbility3() {
		return ability3;
	}

	public void setAbility3(String ability3) {
		this.ability3 = ability3;
	}

	public String getManner1() {
		return manner1;
	}

	public void setManner1(String manner1) {
		this.manner1 = manner1;
	}

	public String getManner2() {
		return manner2;
	}

	public void setManner2(String manner2) {
		this.manner2 = manner2;
	}

	public String getManner3() {
		return manner3;
	}

	public void setManner3(String manner3) {
		this.manner3 = manner3;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	
	
}
