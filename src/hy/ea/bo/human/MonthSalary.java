package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * Dtmonthsalary entity. @author MyEclipse Persistence Tools
 */

public class MonthSalary implements BaseBean,java.io.Serializable{

	// Fields

	private String monthsalarykey;
	private String monthsalaryid;
	/**
	 * 公司id
	 */
	private String companyid;
	/**
	 * 公司名称
	 */
	private String companyname;
	/**
	 * 人员id
	 */
	private String staffid;
	/**
	 * 人员姓名
	 */
	private String staffname;
	/**
	 * 工种类别名称
	 */
	private String categoryname;
	/**
	 * 岗位名称
	 */
	private String postname;
	/**
	 * 月份
	 */
	private String months;
	
	/**
	 * 基本积分
	 */
	private String basicintegral;
	/**
	 * 职务职责得分
	 */
	private String funzioneintegral;
	/**
	 * 周末加班
	 */
	private String weekendintegral;
	/**
	 * 晚上加班
	 */
	private String worknightintegral;
	/**
	 * 节假日加班
	 */
	private String workholidaysintegral;
	/**
	 * 目标任务考核积分
	 */
	private String targettaskintegral;
	/**
	 * 月考评
	 */
	private String appraisalintegral;
	/**
	 * 任务得分
	 */
	private String taskintegral;
	/**
	 * 奖励得分
	 */
	private String rewardintegral;
	/**
	 * 安全得分
	 */
	private String safetyintegral;
	/**
	 * 单位承担
	 */
	private String holidaysintegral;
	/**
	 * 个人所得税
	 */
	private String personaldiscount;
	/**
	 * 特殊人才
	 */
	private String stpay;
	/**
	 * 保密工资
	 */
	private String secrecypay;
	/**
	 * 任务折扣
	 */
	private String taskdiscount;
	/**
	 * 暂扣安全积分
	 */
	private String safetydiscount;
	/**
	 * 考勤折扣
	 */
	private String attendancediscount;
	/**
	 * 个人应交社保
	 */
	private String personalsocialsecurity;
	/**
	 * 个人应交公积金
	 */
	private String personalreservedfunds;
	/**
	 * 违规折扣
	 */
	private String violationdiscount;
	
	/**
	 * 自定义加分项字段名
	 */
	private String wagefiledadd;
	/**
	 * 自定义加分项分数
	 */
	private String customwageadd;
	/**
	 * 自定义减分项字段名
	 */
	private String wagefiledcut;
	/**
	 * 自定义减分项分数
	 */
	private String customwagecut;
	
	/**
	 * 应得积分
	 */
	private String dueintegral;
	/**
	 * 实得积分
	 */
	private String obtainedintegral;
	/**
	 * 实得工资
	 */
	private String obtainedmenoy;
	
	/**
	 * 状态 00：解锁状态  01：加锁状态
	 */
	private String status;
	/**
	 * 加锁责任人
	 */
	private String lockperson;
	/**
	 * 解锁责任人
	 */
	private String deblockperson;
	/**
	 * 加锁时间
	 */
	private String lockedtime;
	/**
	 * 解锁时间
	 */
	private String deblocktime;
	
	// Constructors

	/** default constructor */
	public MonthSalary() {
	}

	/** minimal constructor */
	public MonthSalary(String monthsalaryid, String companyid,
			String companyname, String staffid, String staffname) {
		this.monthsalaryid = monthsalaryid;
		this.companyid = companyid;
		this.companyname = companyname;
		this.staffid = staffid;
		this.staffname = staffname;
	}

	/** full constructor */
	public MonthSalary(String monthsalaryid, String companyid,
			String companyname, String staffid, String staffname,
			String categoryname, String postname,String months, 
			String basicintegral, String funzioneintegral,
			String weekendintegral, String worknightintegral,
			String workholidaysintegral, String targettaskintegral,
			String appraisalintegral, String taskintegral,
			String rewardintegral, String safetyintegral,
			String holidaysintegral, String personaldiscount, String stpay,
			String secrecypay, String taskdiscount, String safetydiscount,
			String attendancediscount, String personalsocialsecurity,
			String personalreservedfunds, String violationdiscount,
			String wagefiledadd, String customwageadd, String wagefiledcut,
			String customwagecut, String dueintegral, String obtainedintegral,
			String obtainedmenoy, String status, String lockperson, String deblockperson,
			String lockedtime, String deblocktime) {
		this.monthsalaryid = monthsalaryid;
		this.companyid = companyid;
		this.companyname = companyname;
		this.staffid = staffid;
		this.staffname = staffname;
		this.categoryname = categoryname;
		this.postname = postname;
		this.months = months;
		this.basicintegral = basicintegral;
		this.funzioneintegral = funzioneintegral;
		this.weekendintegral = weekendintegral;
		this.worknightintegral = worknightintegral;
		this.workholidaysintegral = workholidaysintegral;
		this.targettaskintegral = targettaskintegral;
		this.appraisalintegral = appraisalintegral;
		this.taskintegral = taskintegral;
		this.rewardintegral = rewardintegral;
		this.safetyintegral = safetyintegral;
		this.holidaysintegral = holidaysintegral;
		this.personaldiscount = personaldiscount;
		this.stpay = stpay;
		this.secrecypay = secrecypay;
		this.taskdiscount = taskdiscount;
		this.safetydiscount = safetydiscount;
		this.attendancediscount = attendancediscount;
		this.personalsocialsecurity = personalsocialsecurity;
		this.personalreservedfunds = personalreservedfunds;
		this.violationdiscount = violationdiscount;
		this.wagefiledadd = wagefiledadd;
		this.customwageadd = customwageadd;
		this.wagefiledcut = wagefiledcut;
		this.customwagecut = customwagecut;
		this.dueintegral = dueintegral;
		this.obtainedintegral = obtainedintegral;
		this.obtainedmenoy = obtainedmenoy;
		this.status = status;
		this.lockperson = lockperson;
		this.deblockperson = deblockperson;
		this.lockedtime = lockedtime;
		this.deblocktime = deblocktime;
	}

	// Property accessors

	public String getMonthsalarykey() {
		return this.monthsalarykey;
	}

	public void setMonthsalarykey(String monthsalarykey) {
		this.monthsalarykey = monthsalarykey;
	}

	public String getMonthsalaryid() {
		return this.monthsalaryid;
	}

	public void setMonthsalaryid(String monthsalaryid) {
		this.monthsalaryid = monthsalaryid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getCategoryname() {
		return this.categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getPostname() {
		return this.postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getMonths() {
		return this.months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getBasicintegral() {
		return this.basicintegral;
	}

	public void setBasicintegral(String basicintegral) {
		this.basicintegral = basicintegral;
	}

	public String getFunzioneintegral() {
		return this.funzioneintegral;
	}

	public void setFunzioneintegral(String funzioneintegral) {
		this.funzioneintegral = funzioneintegral;
	}

	public String getWeekendintegral() {
		return this.weekendintegral;
	}

	public void setWeekendintegral(String weekendintegral) {
		this.weekendintegral = weekendintegral;
	}

	public String getWorknightintegral() {
		return this.worknightintegral;
	}

	public void setWorknightintegral(String worknightintegral) {
		this.worknightintegral = worknightintegral;
	}

	public String getWorkholidaysintegral() {
		return this.workholidaysintegral;
	}

	public void setWorkholidaysintegral(String workholidaysintegral) {
		this.workholidaysintegral = workholidaysintegral;
	}

	public String getTargettaskintegral() {
		return this.targettaskintegral;
	}

	public void setTargettaskintegral(String targettaskintegral) {
		this.targettaskintegral = targettaskintegral;
	}

	public String getAppraisalintegral() {
		return this.appraisalintegral;
	}

	public void setAppraisalintegral(String appraisalintegral) {
		this.appraisalintegral = appraisalintegral;
	}

	public String getTaskintegral() {
		return this.taskintegral;
	}

	public void setTaskintegral(String taskintegral) {
		this.taskintegral = taskintegral;
	}

	public String getRewardintegral() {
		return this.rewardintegral;
	}

	public void setRewardintegral(String rewardintegral) {
		this.rewardintegral = rewardintegral;
	}

	public String getSafetyintegral() {
		return this.safetyintegral;
	}

	public void setSafetyintegral(String safetyintegral) {
		this.safetyintegral = safetyintegral;
	}

	public String getHolidaysintegral() {
		return this.holidaysintegral;
	}

	public void setHolidaysintegral(String holidaysintegral) {
		this.holidaysintegral = holidaysintegral;
	}

	public String getPersonaldiscount() {
		return this.personaldiscount;
	}

	public void setPersonaldiscount(String personaldiscount) {
		this.personaldiscount = personaldiscount;
	}

	public String getStpay() {
		return this.stpay;
	}

	public void setStpay(String stpay) {
		this.stpay = stpay;
	}

	public String getSecrecypay() {
		return this.secrecypay;
	}

	public void setSecrecypay(String secrecypay) {
		this.secrecypay = secrecypay;
	}

	public String getTaskdiscount() {
		return this.taskdiscount;
	}

	public void setTaskdiscount(String taskdiscount) {
		this.taskdiscount = taskdiscount;
	}

	public String getSafetydiscount() {
		return this.safetydiscount;
	}

	public void setSafetydiscount(String safetydiscount) {
		this.safetydiscount = safetydiscount;
	}

	public String getAttendancediscount() {
		return this.attendancediscount;
	}

	public void setAttendancediscount(String attendancediscount) {
		this.attendancediscount = attendancediscount;
	}

	public String getPersonalsocialsecurity() {
		return this.personalsocialsecurity;
	}

	public void setPersonalsocialsecurity(String personalsocialsecurity) {
		this.personalsocialsecurity = personalsocialsecurity;
	}

	public String getPersonalreservedfunds() {
		return this.personalreservedfunds;
	}

	public void setPersonalreservedfunds(String personalreservedfunds) {
		this.personalreservedfunds = personalreservedfunds;
	}

	public String getViolationdiscount() {
		return this.violationdiscount;
	}

	public void setViolationdiscount(String violationdiscount) {
		this.violationdiscount = violationdiscount;
	}

	public String getWagefiledadd() {
		return wagefiledadd;
	}

	public void setWagefiledadd(String wagefiledadd) {
		this.wagefiledadd = wagefiledadd;
	}

	public String getCustomwageadd() {
		return customwageadd;
	}

	public void setCustomwageadd(String customwageadd) {
		this.customwageadd = customwageadd;
	}

	public String getWagefiledcut() {
		return wagefiledcut;
	}

	public void setWagefiledcut(String wagefiledcut) {
		this.wagefiledcut = wagefiledcut;
	}

	public String getCustomwagecut() {
		return customwagecut;
	}

	public void setCustomwagecut(String customwagecut) {
		this.customwagecut = customwagecut;
	}

	public String getDueintegral() {
		return dueintegral;
	}

	public void setDueintegral(String dueintegral) {
		this.dueintegral = dueintegral;
	}

	public String getObtainedintegral() {
		return obtainedintegral;
	}

	public void setObtainedintegral(String obtainedintegral) {
		this.obtainedintegral = obtainedintegral;
	}

	public String getObtainedmenoy() {
		return obtainedmenoy;
	}

	public void setObtainedmenoy(String obtainedmenoy) {
		this.obtainedmenoy = obtainedmenoy;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLockperson() {
		return lockperson;
	}

	public void setLockperson(String lockperson) {
		this.lockperson = lockperson;
	}

	public String getDeblockperson() {
		return this.deblockperson;
	}

	public void setDeblockperson(String deblockperson) {
		this.deblockperson = deblockperson;
	}

	public String getLockedtime() {
		return this.lockedtime;
	}

	public void setLockedtime(String lockedtime) {
		this.lockedtime = lockedtime;
	}

	public String getDeblocktime() {
		return this.deblocktime;
	}

	public void setDeblocktime(String deblocktime) {
		this.deblocktime = deblocktime;
	}
}