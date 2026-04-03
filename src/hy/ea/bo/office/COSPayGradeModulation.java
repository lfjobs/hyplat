package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

public final class COSPayGradeModulation implements BaseBean, ExcelBean,java.io.Serializable {
	private String pgmKey;
	private String pgmID;
	private String companyID;
	private String organizationID;
	private String voucherNomber;// 凭证号
	private String deptID;
	private String affix;// 附件
	private String dutyOfficer;// 工资级别升降级责任人
	private Date addTime;// 工资级别升降级日期
	private String staffCode;// 人员编号
	private String currentLevel;// 升降级别
	private String primaryLevel;// 原级别名细
	private Date startDate;// 起时间
	private Date endDate;// 止时间
	private String levelDetail;// 现级别名细
	private String selfRating;// 自我评定
	private String classes0;// 调薪类别
	private String reason;// 调薪原因
	private String suggestion;// 意见
	private String managerName;// 公司经理
	private String supervisor;// 部门主管
	private String auditer;// 审核
	private String accountant;// 会计
	private String casher;// 出纳
	private String headManager;// 总部总经理
	private String headSupervisor;// 总部主管
	private String headAuditer;// 财务部审核
	private String headAccountant;// 总会计
	private String headCasher;// 总出纳

	@Override
	public String[] properties() {
		String[] properties = { voucherNomber, oMap.get(companyID),oMap.get(deptID),
				dutyOfficer, String.format("%1$tF", addTime), staffCode,
				currentLevel, primaryLevel, String.format("%1$tF", startDate),
				String.format("%1$tF", endDate), levelDetail, selfRating,
				classes0, reason, suggestion, managerName, supervisor, auditer,
				accountant, casher, headManager, headSupervisor, headAuditer,
				headAccountant, headCasher };
		return properties;
	}

	public static String[] columnHeadings() {
		String[] titles = { "序号", "凭证号", "公司", "部门", "工资级别升降级责任人", "工资级别升降级日期",
				"人员编号", "升降级别", "原级别名细", "起时间", "止时间", "现级别名细", "自我评定", "调薪类别",
				"调薪原因", "意见", "公司经理", "部门主管", "审核", "会计", "出纳", "总部总经理",
				"总部主管", "财务部审核", "总会计", "总出纳" };
		return titles;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public final String getPgmKey() {
		return pgmKey;
	}

	public final void setPgmKey(String pgmKey) {
		this.pgmKey = pgmKey;
	}

	public final String getPgmID() {
		return pgmID;
	}

	public final void setPgmID(String pgmID) {
		this.pgmID = pgmID;
	}

	public final String getCompanyID() {
		return companyID;
	}

	public final void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public final String getVoucherNomber() {
		return voucherNomber;
	}

	public final void setVoucherNomber(String voucherNomber) {
		this.voucherNomber = voucherNomber;
	}

	public final String getAffix() {
		return affix;
	}

	public final void setAffix(String affix) {
		this.affix = affix;
	}

	

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public final String getDutyOfficer() {
		return dutyOfficer;
	}

	public final void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public final Date getAddTime() {
		return addTime;
	}

	public final void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public final String getStaffCode() {
		return staffCode;
	}

	public final void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public final String getCurrentLevel() {
		return currentLevel;
	}

	public final void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

	public final String getPrimaryLevel() {
		return primaryLevel;
	}

	public final void setPrimaryLevel(String primaryLevel) {
		this.primaryLevel = primaryLevel;
	}

	public final Date getStartDate() {
		return startDate;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final Date getEndDate() {
		return endDate;
	}

	public final void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public final String getLevelDetail() {
		return levelDetail;
	}

	public final void setLevelDetail(String levelDetail) {
		this.levelDetail = levelDetail;
	}

	public final String getSelfRating() {
		return selfRating;
	}

	public final void setSelfRating(String selfRating) {
		this.selfRating = selfRating;
	}

	public final String getClasses0() {
		return classes0;
	}

	public final void setClasses0(String classes0) {
		this.classes0 = classes0;
	}


	public final String getReason() {
		return reason;
	}

	public final void setReason(String reason) {
		this.reason = reason;
	}

	public final String getSuggestion() {
		return suggestion;
	}

	public final void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public final String getManagerName() {
		return managerName;
	}

	public final void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public final String getSupervisor() {
		return supervisor;
	}

	public final void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public final String getAccountant() {
		return accountant;
	}

	public final void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	public final String getCasher() {
		return casher;
	}

	public final void setCasher(String casher) {
		this.casher = casher;
	}

	public final String getHeadManager() {
		return headManager;
	}

	public final void setHeadManager(String headManager) {
		this.headManager = headManager;
	}

	public final String getHeadSupervisor() {
		return headSupervisor;
	}

	public final void setHeadSupervisor(String headSupervisor) {
		this.headSupervisor = headSupervisor;
	}

	public final String getAuditer() {
		return auditer;
	}

	public final void setAuditer(String auditer) {
		this.auditer = auditer;
	}

	public final String getHeadAuditer() {
		return headAuditer;
	}

	public final void setHeadAuditer(String headAuditer) {
		this.headAuditer = headAuditer;
	}

	public final String getHeadAccountant() {
		return headAccountant;
	}

	public final void setHeadAccountant(String headAccountant) {
		this.headAccountant = headAccountant;
	}

	public final String getHeadCasher() {
		return headCasher;
	}

	public final void setHeadCasher(String headCasher) {
		this.headCasher = headCasher;
	}

	public final String getOrganizationID() {
		return organizationID;
	}

	public final void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

}
