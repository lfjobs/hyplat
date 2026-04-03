package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;


/**
 * 工资月结
 * @author maochang
 *
 */
public class SalarySettlementMonth implements BaseBean {
	private String salarySettlementMonthId;
	private String staffId;
	private String companyId;
	private String settlementMonth;
	private String salaryLevelId;
	private String salaryLevelNum;
	private String baseSalary;
	private String roleSalary;
	private String dutySalary;
	private String competeSalary;
	private String secrecySalary;
	private String levelSalary;
	private String guaranteeSalary;
	private String welfareSalary;
	private String totalSalary;
	private String lateDiscountMoney;
	private String leaveEarlyDiscountMoney;
	private String absenteeDiscountMoney;
	private String leaveDiscountMoney;
	private String weekendOvertimeMoney;
	private String eightHourOvertimeMoney;
	private String holidayOvertimeMoney;
	private String realSalary;
	private String socialSecurityLevel;//社保缴纳基数
	private String elderlyCareDiscountMoney;//养老扣费
	private String medicalDiscountMoney;//医保扣费
	private String unemploymentDiscountMoney;//失业金扣费

	public String getSalarySettlementMonthId() {
		return salarySettlementMonthId;
	}

	public void setSalarySettlementMonthId(String salarySettlementMonthId) {
		this.salarySettlementMonthId = salarySettlementMonthId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(String settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(String baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getRoleSalary() {
		return roleSalary;
	}

	public void setRoleSalary(String roleSalary) {
		this.roleSalary = roleSalary;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}

	public String getCompeteSalary() {
		return competeSalary;
	}

	public void setCompeteSalary(String competeSalary) {
		this.competeSalary = competeSalary;
	}

	public String getSecrecySalary() {
		return secrecySalary;
	}

	public void setSecrecySalary(String secrecySalary) {
		this.secrecySalary = secrecySalary;
	}

	public String getLevelSalary() {
		return levelSalary;
	}

	public void setLevelSalary(String levelSalary) {
		this.levelSalary = levelSalary;
	}

	public String getGuaranteeSalary() {
		return guaranteeSalary;
	}

	public void setGuaranteeSalary(String guaranteeSalary) {
		this.guaranteeSalary = guaranteeSalary;
	}

	public String getWelfareSalary() {
		return welfareSalary;
	}

	public void setWelfareSalary(String welfareSalary) {
		this.welfareSalary = welfareSalary;
	}

	public String getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(String totalSalary) {
		this.totalSalary = totalSalary;
	}

	public String getLateDiscountMoney() {
		return lateDiscountMoney;
	}

	public void setLateDiscountMoney(String lateDiscountMoney) {
		this.lateDiscountMoney = lateDiscountMoney;
	}

	public String getLeaveEarlyDiscountMoney() {
		return leaveEarlyDiscountMoney;
	}

	public void setLeaveEarlyDiscountMoney(String leaveEarlyDiscountMoney) {
		this.leaveEarlyDiscountMoney = leaveEarlyDiscountMoney;
	}

	public String getAbsenteeDiscountMoney() {
		return absenteeDiscountMoney;
	}

	public void setAbsenteeDiscountMoney(String absenteeDiscountMoney) {
		this.absenteeDiscountMoney = absenteeDiscountMoney;
	}

	public String getLeaveDiscountMoney() {
		return leaveDiscountMoney;
	}

	public void setLeaveDiscountMoney(String leaveDiscountMoney) {
		this.leaveDiscountMoney = leaveDiscountMoney;
	}

	public String getRealSalary() {
		return realSalary;
	}

	public void setRealSalary(String realSalary) {
		this.realSalary = realSalary;
	}

	public String getSalaryLevelNum() {
		return salaryLevelNum;
	}

	public void setSalaryLevelNum(String salaryLevelNum) {
		this.salaryLevelNum = salaryLevelNum;
	}

	public String getWeekendOvertimeMoney() {
		return weekendOvertimeMoney;
	}

	public void setWeekendOvertimeMoney(String weekendOvertimeMoney) {
		this.weekendOvertimeMoney = weekendOvertimeMoney;
	}

	public String getEightHourOvertimeMoney() {
		return eightHourOvertimeMoney;
	}

	public void setEightHourOvertimeMoney(String eightHourOvertimeMoney) {
		this.eightHourOvertimeMoney = eightHourOvertimeMoney;
	}

	public String getHolidayOvertimeMoney() {
		return holidayOvertimeMoney;
	}

	public void setHolidayOvertimeMoney(String holidayOvertimeMoney) {
		this.holidayOvertimeMoney = holidayOvertimeMoney;
	}

	public String getSocialSecurityLevel() {
		return socialSecurityLevel;
	}

	public void setSocialSecurityLevel(String socialSecurityLevel) {
		this.socialSecurityLevel = socialSecurityLevel;
	}

	public String getElderlyCareDiscountMoney() {
		return elderlyCareDiscountMoney;
	}

	public void setElderlyCareDiscountMoney(String elderlyCareDiscountMoney) {
		this.elderlyCareDiscountMoney = elderlyCareDiscountMoney;
	}

	public String getMedicalDiscountMoney() {
		return medicalDiscountMoney;
	}

	public void setMedicalDiscountMoney(String medicalDiscountMoney) {
		this.medicalDiscountMoney = medicalDiscountMoney;
	}

	public String getUnemploymentDiscountMoney() {
		return unemploymentDiscountMoney;
	}

	public void setUnemploymentDiscountMoney(String unemploymentDiscountMoney) {
		this.unemploymentDiscountMoney = unemploymentDiscountMoney;
	}
}
