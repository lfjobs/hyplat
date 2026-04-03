package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 签到考勤汇总
 * @author
 *
 */
public class SignInCheckOn implements BaseBean, Serializable {
	private static final long serialVersionUID = 502704448151102264L;
	private String staffId;
	private String account;
	private String sccId;
	private String companyId;
	private String staffName;
	private String startTime;
	private String endTime;
	private String checkOnTimeType;//天，周，月，季，年
	private String salaryLevelId;
	private String salaryLevelNum;
	private Double baseSalary;
	/**
	 * 职能工资
	 */
	private Double roleSalary;
	/**
	 * 职责工资
	 */
	private Double dutySalary;
	/**
	 * 竞职金
	 */
	private Double competeSalary;
	/**
	 * 保密工资
	 */
	private Double secrecySalary;
	/**
	 * 调平工资
	 */
	private Double levelSalary;
	private Double guaranteeSalary;//保障工资
	private Double welfareSalary;//福利工资
	private Double checkOnDayCount;//月考勤天数
	private BigDecimal salaryPerDay;//每天工资
	private BigDecimal salaryPerMinute;//每分钟工资
	private BigDecimal salaryPerSeconds;//每秒钟工资
	private BigDecimal guaranteeSalaryPerDay;//每天保障工资
	private BigDecimal welfareSalaryPerDay;//每天福利工资

	private Integer lateCount;//迟到多少次
	private Double lateMinutes;//迟到多少分钟
	private Integer lateSeconds;//迟到多少秒钟
	private BigDecimal lateDiscountMoney;//迟到应扣多少工资

	private Integer leaveEarlyCount;//早退多少次
	private Double leaveEarlyMinutes;//早退多少分钟
	private Integer leaveEarlySeconds;//早退多少秒钟
	private BigDecimal leaveEarlyDiscountMoney;//早退应扣多少工资

	private Double leaveAbsenceDayCount;//请事假多少天
	private Double leaveAbsenceHours;
	private Integer leaveAbsenceMinutes;
	private Integer leaveAbsenceSeconds;
	private BigDecimal leaveAbsenceDiscountMoney;

	private Double leaveSickDayCount;//请病假多少天
	private Double leaveSickHours;
	private Integer leaveSickMinutes;
	private Integer leaveSickSeconds;
	private BigDecimal leaveSickDiscountMoney;

	private Double leaveMarriageDayCount;
	private Double leaveMarriageHours;
	private Integer leaveMarriageMinutes;
	private Integer leaveMarriageSeconds;
	private BigDecimal leaveMarriageDiscountMoney;

	private Double leaveAnnualDayCount;
	private Double leaveAnnualHours;
	private Integer leaveAnnualMinutes;
	private Integer leaveAnnualSeconds;
	private BigDecimal leaveAnnualDiscountMoney;

	private Double leaveMaternityDayCount;
	private Double leaveMaternityHours;
	private Integer leaveMaternityMinutes;
	private Integer leaveMaternitySeconds;
	private BigDecimal leaveMaternityDiscountMoney;

	private Double leaveFuneralDayCount;
	private Double leaveFuneralHours;
	private Integer leaveFuneralMinutes;
	private Integer leaveFuneralSeconds;
	private BigDecimal leaveFuneralDiscountMoney;

	private Double leaveWorkInjuryDayCount;
	private Double leaveWorkInjuryHours;
	private Integer leaveWorkInjuryMinutes;
	private Integer leaveWorkInjurySeconds;
	private BigDecimal leaveWorkInjuryDiscountMoney;

	private Double leaveRotationRestDayCount;
	private Double leaveRotationRestHours;
	private Integer leaveRotationRestMinutes;
	private Integer leaveRotationRestSeconds;
	private BigDecimal leaveRotationRestDiscountMoney;

	private Double absenteeDayCount;//旷工多少天
	private Double absenteeHours;
	private Integer absenteeMinutes;
	private Integer absenteeSeconds;
	private BigDecimal absenteeDiscountMoney;

	private BigDecimal totalDiscountMoney;//总共应扣多少工资

	private Double weekendOverTimeDayCount;//加班多少天
	private Double weekendOverTimeHours;
	private Integer weekendOverTimeMinutes;
	private Integer weekendOverTimeSeconds;
	private BigDecimal weekendOverTimeMoney;

	private Double eightHourOverTimeDayCount;//加班多少天
	private Double eightHourOverTimeHours;
	private Integer eightHourOverTimeMinutes;
	private Integer eightHourOverTimeSeconds;
	private BigDecimal eightHourOverTimeMoney;

	private Double holidayOverTimeDayCount;//加班多少天
	private Double holidayOverTimeHours;
	private Integer holidayOverTimeMinutes;
	private Integer holidayOverTimeSeconds;
	private BigDecimal holidayOverTimeMoney;

	private Double outworkDayCount;//外勤多少天
	private Double outworkHours;
	private Integer outworkMinutes;
	private Integer outworkSeconds;

	private List<LeaveData> leaveDataList;
	private List<LeaveData> outworkList;
	private List<OvertimeData> overtimeDataList;//节假日，平时加班
	private List<SignInResult> signInResultList;//签到数据
	private List<OvertimeData> weekendOvertimeDataList;

	public SignInCheckOn() {
	}

	public SignInCheckOn(String staffId, String account, String sccId, String companyId, String staffName, String startTime, String endTime, String checkOnTimeType) {
		this.staffId = staffId;
		this.account = account;
		this.sccId = sccId;
		this.companyId = companyId;
		this.staffName = staffName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.checkOnTimeType = checkOnTimeType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCheckOnTimeType() {
		return checkOnTimeType;
	}

	public void setCheckOnTimeType(String checkOnTimeType) {
		this.checkOnTimeType = checkOnTimeType;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getGuaranteeSalary() {
		return guaranteeSalary;
	}

	public void setGuaranteeSalary(Double guaranteeSalary) {
		this.guaranteeSalary = guaranteeSalary;
	}

	public Double getWelfareSalary() {
		return welfareSalary;
	}

	public void setWelfareSalary(Double welfareSalary) {
		this.welfareSalary = welfareSalary;
	}

	public Double getCheckOnDayCount() {
		return checkOnDayCount;
	}

	public void setCheckOnDayCount(Double checkOnDayCount) {
		this.checkOnDayCount = checkOnDayCount;
	}

	public BigDecimal getSalaryPerMinute() {
		return salaryPerMinute;
	}

	public void setSalaryPerMinute(BigDecimal salaryPerMinute) {
		this.salaryPerMinute = salaryPerMinute;
	}

	public Integer getLateCount() {
		return lateCount;
	}

	public void setLateCount(Integer lateCount) {
		this.lateCount = lateCount;
	}

	public Double getLateMinutes() {
		return lateMinutes;
	}

	public void setLateMinutes(Double lateMinutes) {
		this.lateMinutes = lateMinutes;
	}

	public BigDecimal getLateDiscountMoney() {
		return lateDiscountMoney;
	}

	public void setLateDiscountMoney(BigDecimal lateDiscountMoney) {
		this.lateDiscountMoney = lateDiscountMoney;
	}

	public Integer getLeaveEarlyCount() {
		return leaveEarlyCount;
	}

	public void setLeaveEarlyCount(Integer leaveEarlyCount) {
		this.leaveEarlyCount = leaveEarlyCount;
	}

	public Double getLeaveEarlyMinutes() {
		return leaveEarlyMinutes;
	}

	public void setLeaveEarlyMinutes(Double leaveEarlyMinutes) {
		this.leaveEarlyMinutes = leaveEarlyMinutes;
	}

	public BigDecimal getLeaveEarlyDiscountMoney() {
		return leaveEarlyDiscountMoney;
	}

	public void setLeaveEarlyDiscountMoney(BigDecimal leaveEarlyDiscountMoney) {
		this.leaveEarlyDiscountMoney = leaveEarlyDiscountMoney;
	}

	public BigDecimal getTotalDiscountMoney() {
		return totalDiscountMoney;
	}

	public void setTotalDiscountMoney(BigDecimal totalDiscountMoney) {
		this.totalDiscountMoney = totalDiscountMoney;
	}

	public BigDecimal getSalaryPerSeconds() {
		return salaryPerSeconds;
	}

	public void setSalaryPerSeconds(BigDecimal salaryPerSeconds) {
		this.salaryPerSeconds = salaryPerSeconds;
	}

	public Integer getLateSeconds() {
		return lateSeconds;
	}

	public void setLateSeconds(Integer lateSeconds) {
		this.lateSeconds = lateSeconds;
	}

	public Integer getLeaveEarlySeconds() {
		return leaveEarlySeconds;
	}

	public void setLeaveEarlySeconds(Integer leaveEarlySeconds) {
		this.leaveEarlySeconds = leaveEarlySeconds;
	}

	public Double getAbsenteeDayCount() {
		return absenteeDayCount;
	}

	public void setAbsenteeDayCount(Double absenteeDayCount) {
		this.absenteeDayCount = absenteeDayCount;
	}

	public Integer getAbsenteeMinutes() {
		return absenteeMinutes;
	}

	public void setAbsenteeMinutes(Integer absenteeMinutes) {
		this.absenteeMinutes = absenteeMinutes;
	}

	public Integer getAbsenteeSeconds() {
		return absenteeSeconds;
	}

	public void setAbsenteeSeconds(Integer absenteeSeconds) {
		this.absenteeSeconds = absenteeSeconds;
	}

	public BigDecimal getAbsenteeDiscountMoney() {
		return absenteeDiscountMoney;
	}

	public void setAbsenteeDiscountMoney(BigDecimal absenteeDiscountMoney) {
		this.absenteeDiscountMoney = absenteeDiscountMoney;
	}

	public Double getAbsenteeHours() {
		return absenteeHours;
	}

	public void setAbsenteeHours(Double absenteeHours) {
		this.absenteeHours = absenteeHours;
	}

	public Double getLeaveAbsenceDayCount() {
		return leaveAbsenceDayCount;
	}

	public void setLeaveAbsenceDayCount(Double leaveAbsenceDayCount) {
		this.leaveAbsenceDayCount = leaveAbsenceDayCount;
	}

	public Double getLeaveAbsenceHours() {
		return leaveAbsenceHours;
	}

	public void setLeaveAbsenceHours(Double leaveAbsenceHours) {
		this.leaveAbsenceHours = leaveAbsenceHours;
	}

	public Integer getLeaveAbsenceMinutes() {
		return leaveAbsenceMinutes;
	}

	public void setLeaveAbsenceMinutes(Integer leaveAbsenceMinutes) {
		this.leaveAbsenceMinutes = leaveAbsenceMinutes;
	}

	public Integer getLeaveAbsenceSeconds() {
		return leaveAbsenceSeconds;
	}

	public void setLeaveAbsenceSeconds(Integer leaveAbsenceSeconds) {
		this.leaveAbsenceSeconds = leaveAbsenceSeconds;
	}

	public BigDecimal getLeaveAbsenceDiscountMoney() {
		return leaveAbsenceDiscountMoney;
	}

	public void setLeaveAbsenceDiscountMoney(BigDecimal leaveAbsenceDiscountMoney) {
		this.leaveAbsenceDiscountMoney = leaveAbsenceDiscountMoney;
	}

	public Double getLeaveSickDayCount() {
		return leaveSickDayCount;
	}

	public void setLeaveSickDayCount(Double leaveSickDayCount) {
		this.leaveSickDayCount = leaveSickDayCount;
	}

	public Double getLeaveSickHours() {
		return leaveSickHours;
	}

	public void setLeaveSickHours(Double leaveSickHours) {
		this.leaveSickHours = leaveSickHours;
	}

	public Integer getLeaveSickMinutes() {
		return leaveSickMinutes;
	}

	public void setLeaveSickMinutes(Integer leaveSickMinutes) {
		this.leaveSickMinutes = leaveSickMinutes;
	}

	public Integer getLeaveSickSeconds() {
		return leaveSickSeconds;
	}

	public void setLeaveSickSeconds(Integer leaveSickSeconds) {
		this.leaveSickSeconds = leaveSickSeconds;
	}

	public BigDecimal getLeaveSickDiscountMoney() {
		return leaveSickDiscountMoney;
	}

	public void setLeaveSickDiscountMoney(BigDecimal leaveSickDiscountMoney) {
		this.leaveSickDiscountMoney = leaveSickDiscountMoney;
	}

	public Double getLeaveMarriageDayCount() {
		return leaveMarriageDayCount;
	}

	public void setLeaveMarriageDayCount(Double leaveMarriageDayCount) {
		this.leaveMarriageDayCount = leaveMarriageDayCount;
	}

	public Double getLeaveMarriageHours() {
		return leaveMarriageHours;
	}

	public void setLeaveMarriageHours(Double leaveMarriageHours) {
		this.leaveMarriageHours = leaveMarriageHours;
	}

	public Integer getLeaveMarriageMinutes() {
		return leaveMarriageMinutes;
	}

	public void setLeaveMarriageMinutes(Integer leaveMarriageMinutes) {
		this.leaveMarriageMinutes = leaveMarriageMinutes;
	}

	public Integer getLeaveMarriageSeconds() {
		return leaveMarriageSeconds;
	}

	public void setLeaveMarriageSeconds(Integer leaveMarriageSeconds) {
		this.leaveMarriageSeconds = leaveMarriageSeconds;
	}

	public BigDecimal getLeaveMarriageDiscountMoney() {
		return leaveMarriageDiscountMoney;
	}

	public void setLeaveMarriageDiscountMoney(BigDecimal leaveMarriageDiscountMoney) {
		this.leaveMarriageDiscountMoney = leaveMarriageDiscountMoney;
	}

	public Double getLeaveAnnualDayCount() {
		return leaveAnnualDayCount;
	}

	public void setLeaveAnnualDayCount(Double leaveAnnualDayCount) {
		this.leaveAnnualDayCount = leaveAnnualDayCount;
	}

	public Double getLeaveAnnualHours() {
		return leaveAnnualHours;
	}

	public void setLeaveAnnualHours(Double leaveAnnualHours) {
		this.leaveAnnualHours = leaveAnnualHours;
	}

	public Integer getLeaveAnnualMinutes() {
		return leaveAnnualMinutes;
	}

	public void setLeaveAnnualMinutes(Integer leaveAnnualMinutes) {
		this.leaveAnnualMinutes = leaveAnnualMinutes;
	}

	public Integer getLeaveAnnualSeconds() {
		return leaveAnnualSeconds;
	}

	public void setLeaveAnnualSeconds(Integer leaveAnnualSeconds) {
		this.leaveAnnualSeconds = leaveAnnualSeconds;
	}

	public BigDecimal getLeaveAnnualDiscountMoney() {
		return leaveAnnualDiscountMoney;
	}

	public void setLeaveAnnualDiscountMoney(BigDecimal leaveAnnualDiscountMoney) {
		this.leaveAnnualDiscountMoney = leaveAnnualDiscountMoney;
	}

	public Double getLeaveMaternityDayCount() {
		return leaveMaternityDayCount;
	}

	public void setLeaveMaternityDayCount(Double leaveMaternityDayCount) {
		this.leaveMaternityDayCount = leaveMaternityDayCount;
	}

	public Double getLeaveMaternityHours() {
		return leaveMaternityHours;
	}

	public void setLeaveMaternityHours(Double leaveMaternityHours) {
		this.leaveMaternityHours = leaveMaternityHours;
	}

	public Integer getLeaveMaternityMinutes() {
		return leaveMaternityMinutes;
	}

	public void setLeaveMaternityMinutes(Integer leaveMaternityMinutes) {
		this.leaveMaternityMinutes = leaveMaternityMinutes;
	}

	public Integer getLeaveMaternitySeconds() {
		return leaveMaternitySeconds;
	}

	public void setLeaveMaternitySeconds(Integer leaveMaternitySeconds) {
		this.leaveMaternitySeconds = leaveMaternitySeconds;
	}

	public BigDecimal getLeaveMaternityDiscountMoney() {
		return leaveMaternityDiscountMoney;
	}

	public void setLeaveMaternityDiscountMoney(BigDecimal leaveMaternityDiscountMoney) {
		this.leaveMaternityDiscountMoney = leaveMaternityDiscountMoney;
	}

	public Double getLeaveFuneralDayCount() {
		return leaveFuneralDayCount;
	}

	public void setLeaveFuneralDayCount(Double leaveFuneralDayCount) {
		this.leaveFuneralDayCount = leaveFuneralDayCount;
	}

	public Double getLeaveFuneralHours() {
		return leaveFuneralHours;
	}

	public void setLeaveFuneralHours(Double leaveFuneralHours) {
		this.leaveFuneralHours = leaveFuneralHours;
	}

	public Integer getLeaveFuneralMinutes() {
		return leaveFuneralMinutes;
	}

	public void setLeaveFuneralMinutes(Integer leaveFuneralMinutes) {
		this.leaveFuneralMinutes = leaveFuneralMinutes;
	}

	public Integer getLeaveFuneralSeconds() {
		return leaveFuneralSeconds;
	}

	public void setLeaveFuneralSeconds(Integer leaveFuneralSeconds) {
		this.leaveFuneralSeconds = leaveFuneralSeconds;
	}

	public BigDecimal getLeaveFuneralDiscountMoney() {
		return leaveFuneralDiscountMoney;
	}

	public void setLeaveFuneralDiscountMoney(BigDecimal leaveFuneralDiscountMoney) {
		this.leaveFuneralDiscountMoney = leaveFuneralDiscountMoney;
	}

	public Double getLeaveWorkInjuryDayCount() {
		return leaveWorkInjuryDayCount;
	}

	public void setLeaveWorkInjuryDayCount(Double leaveWorkInjuryDayCount) {
		this.leaveWorkInjuryDayCount = leaveWorkInjuryDayCount;
	}

	public Double getLeaveWorkInjuryHours() {
		return leaveWorkInjuryHours;
	}

	public void setLeaveWorkInjuryHours(Double leaveWorkInjuryHours) {
		this.leaveWorkInjuryHours = leaveWorkInjuryHours;
	}

	public Integer getLeaveWorkInjuryMinutes() {
		return leaveWorkInjuryMinutes;
	}

	public void setLeaveWorkInjuryMinutes(Integer leaveWorkInjuryMinutes) {
		this.leaveWorkInjuryMinutes = leaveWorkInjuryMinutes;
	}

	public Integer getLeaveWorkInjurySeconds() {
		return leaveWorkInjurySeconds;
	}

	public void setLeaveWorkInjurySeconds(Integer leaveWorkInjurySeconds) {
		this.leaveWorkInjurySeconds = leaveWorkInjurySeconds;
	}

	public BigDecimal getLeaveWorkInjuryDiscountMoney() {
		return leaveWorkInjuryDiscountMoney;
	}

	public void setLeaveWorkInjuryDiscountMoney(BigDecimal leaveWorkInjuryDiscountMoney) {
		this.leaveWorkInjuryDiscountMoney = leaveWorkInjuryDiscountMoney;
	}

	public Double getLeaveRotationRestDayCount() {
		return leaveRotationRestDayCount;
	}

	public void setLeaveRotationRestDayCount(Double leaveRotationRestDayCount) {
		this.leaveRotationRestDayCount = leaveRotationRestDayCount;
	}

	public Double getLeaveRotationRestHours() {
		return leaveRotationRestHours;
	}

	public void setLeaveRotationRestHours(Double leaveRotationRestHours) {
		this.leaveRotationRestHours = leaveRotationRestHours;
	}

	public Integer getLeaveRotationRestMinutes() {
		return leaveRotationRestMinutes;
	}

	public void setLeaveRotationRestMinutes(Integer leaveRotationRestMinutes) {
		this.leaveRotationRestMinutes = leaveRotationRestMinutes;
	}

	public Integer getLeaveRotationRestSeconds() {
		return leaveRotationRestSeconds;
	}

	public void setLeaveRotationRestSeconds(Integer leaveRotationRestSeconds) {
		this.leaveRotationRestSeconds = leaveRotationRestSeconds;
	}

	public BigDecimal getLeaveRotationRestDiscountMoney() {
		return leaveRotationRestDiscountMoney;
	}

	public void setLeaveRotationRestDiscountMoney(BigDecimal leaveRotationRestDiscountMoney) {
		this.leaveRotationRestDiscountMoney = leaveRotationRestDiscountMoney;
	}

	public String getSalaryLevelNum() {
		return salaryLevelNum;
	}

	public void setSalaryLevelNum(String salaryLevelNum) {
		this.salaryLevelNum = salaryLevelNum;
	}

	public Double getWeekendOverTimeDayCount() {
		return weekendOverTimeDayCount;
	}

	public void setWeekendOverTimeDayCount(Double weekendOverTimeDayCount) {
		this.weekendOverTimeDayCount = weekendOverTimeDayCount;
	}

	public Double getWeekendOverTimeHours() {
		return weekendOverTimeHours;
	}

	public void setWeekendOverTimeHours(Double weekendOverTimeHours) {
		this.weekendOverTimeHours = weekendOverTimeHours;
	}

	public Integer getWeekendOverTimeMinutes() {
		return weekendOverTimeMinutes;
	}

	public void setWeekendOverTimeMinutes(Integer weekendOverTimeMinutes) {
		this.weekendOverTimeMinutes = weekendOverTimeMinutes;
	}

	public Integer getWeekendOverTimeSeconds() {
		return weekendOverTimeSeconds;
	}

	public void setWeekendOverTimeSeconds(Integer weekendOverTimeSeconds) {
		this.weekendOverTimeSeconds = weekendOverTimeSeconds;
	}

	public BigDecimal getWeekendOverTimeMoney() {
		return weekendOverTimeMoney;
	}

	public void setWeekendOverTimeMoney(BigDecimal weekendOverTimeMoney) {
		this.weekendOverTimeMoney = weekendOverTimeMoney;
	}

	public Double getOutworkDayCount() {
		return outworkDayCount;
	}

	public void setOutworkDayCount(Double outworkDayCount) {
		this.outworkDayCount = outworkDayCount;
	}

	public Double getOutworkHours() {
		return outworkHours;
	}

	public void setOutworkHours(Double outworkHours) {
		this.outworkHours = outworkHours;
	}

	public Integer getOutworkMinutes() {
		return outworkMinutes;
	}

	public void setOutworkMinutes(Integer outworkMinutes) {
		this.outworkMinutes = outworkMinutes;
	}

	public Integer getOutworkSeconds() {
		return outworkSeconds;
	}

	public void setOutworkSeconds(Integer outworkSeconds) {
		this.outworkSeconds = outworkSeconds;
	}

	public List<LeaveData> getLeaveDataList() {
		return leaveDataList;
	}

	public void setLeaveDataList(List<LeaveData> leaveDataList) {
		this.leaveDataList = leaveDataList;
	}

	public List<LeaveData> getOutworkList() {
		return outworkList;
	}

	public void setOutworkList(List<LeaveData> outworkList) {
		this.outworkList = outworkList;
	}

	public Double getRoleSalary() {
		return roleSalary;
	}

	public void setRoleSalary(Double roleSalary) {
		this.roleSalary = roleSalary;
	}

	public Double getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(Double dutySalary) {
		this.dutySalary = dutySalary;
	}

	public Double getCompeteSalary() {
		return competeSalary;
	}

	public void setCompeteSalary(Double competeSalary) {
		this.competeSalary = competeSalary;
	}

	public Double getSecrecySalary() {
		return secrecySalary;
	}

	public void setSecrecySalary(Double secrecySalary) {
		this.secrecySalary = secrecySalary;
	}

	public Double getLevelSalary() {
		return levelSalary;
	}

	public void setLevelSalary(Double levelSalary) {
		this.levelSalary = levelSalary;
	}

	public List<OvertimeData> getOvertimeDataList() {
		return overtimeDataList;
	}

	public void setOvertimeDataList(List<OvertimeData> overtimeDataList) {
		this.overtimeDataList = overtimeDataList;
	}

	public Double getEightHourOverTimeDayCount() {
		return eightHourOverTimeDayCount;
	}

	public void setEightHourOverTimeDayCount(Double eightHourOverTimeDayCount) {
		this.eightHourOverTimeDayCount = eightHourOverTimeDayCount;
	}

	public Double getEightHourOverTimeHours() {
		return eightHourOverTimeHours;
	}

	public void setEightHourOverTimeHours(Double eightHourOverTimeHours) {
		this.eightHourOverTimeHours = eightHourOverTimeHours;
	}

	public Integer getEightHourOverTimeMinutes() {
		return eightHourOverTimeMinutes;
	}

	public void setEightHourOverTimeMinutes(Integer eightHourOverTimeMinutes) {
		this.eightHourOverTimeMinutes = eightHourOverTimeMinutes;
	}

	public Integer getEightHourOverTimeSeconds() {
		return eightHourOverTimeSeconds;
	}

	public void setEightHourOverTimeSeconds(Integer eightHourOverTimeSeconds) {
		this.eightHourOverTimeSeconds = eightHourOverTimeSeconds;
	}

	public BigDecimal getEightHourOverTimeMoney() {
		return eightHourOverTimeMoney;
	}

	public void setEightHourOverTimeMoney(BigDecimal eightHourOverTimeMoney) {
		this.eightHourOverTimeMoney = eightHourOverTimeMoney;
	}

	public Double getHolidayOverTimeDayCount() {
		return holidayOverTimeDayCount;
	}

	public void setHolidayOverTimeDayCount(Double holidayOverTimeDayCount) {
		this.holidayOverTimeDayCount = holidayOverTimeDayCount;
	}

	public Double getHolidayOverTimeHours() {
		return holidayOverTimeHours;
	}

	public void setHolidayOverTimeHours(Double holidayOverTimeHours) {
		this.holidayOverTimeHours = holidayOverTimeHours;
	}

	public Integer getHolidayOverTimeMinutes() {
		return holidayOverTimeMinutes;
	}

	public void setHolidayOverTimeMinutes(Integer holidayOverTimeMinutes) {
		this.holidayOverTimeMinutes = holidayOverTimeMinutes;
	}

	public Integer getHolidayOverTimeSeconds() {
		return holidayOverTimeSeconds;
	}

	public void setHolidayOverTimeSeconds(Integer holidayOverTimeSeconds) {
		this.holidayOverTimeSeconds = holidayOverTimeSeconds;
	}

	public BigDecimal getHolidayOverTimeMoney() {
		return holidayOverTimeMoney;
	}

	public void setHolidayOverTimeMoney(BigDecimal holidayOverTimeMoney) {
		this.holidayOverTimeMoney = holidayOverTimeMoney;
	}

	public List<SignInResult> getSignInResultList() {
		return signInResultList;
	}

	public void setSignInResultList(List<SignInResult> signInResultList) {
		this.signInResultList = signInResultList;
	}

	public BigDecimal getSalaryPerDay() {
		return salaryPerDay;
	}

	public void setSalaryPerDay(BigDecimal salaryPerDay) {
		this.salaryPerDay = salaryPerDay;
	}

	public BigDecimal getGuaranteeSalaryPerDay() {
		return guaranteeSalaryPerDay;
	}

	public void setGuaranteeSalaryPerDay(BigDecimal guaranteeSalaryPerDay) {
		this.guaranteeSalaryPerDay = guaranteeSalaryPerDay;
	}

	public BigDecimal getWelfareSalaryPerDay() {
		return welfareSalaryPerDay;
	}

	public void setWelfareSalaryPerDay(BigDecimal welfareSalaryPerDay) {
		this.welfareSalaryPerDay = welfareSalaryPerDay;
	}

	public List<OvertimeData> getWeekendOvertimeDataList() {
		return weekendOvertimeDataList;
	}

	public void setWeekendOvertimeDataList(List<OvertimeData> weekendOvertimeDataList) {
		this.weekendOvertimeDataList = weekendOvertimeDataList;
	}
}
