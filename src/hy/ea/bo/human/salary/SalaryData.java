package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 工资数据
 * @author wangshuangni
 *
 */
public class SalaryData implements BaseBean {
	/**
	 * 主键
	 */
	private String salaryKey;
	/**
	 * id
	 */
	private String salaryId;
	/**
	 * 级别id
	 */
	private String salaryLevelId;
	/**
	 * 序号
	 */
	private String salaryLevelSerial;
	/**
	 * 级别编码
	 */
	private String salaryLevelNum;
	/**
	 * 基本工资
	 */
	private String baseSalary;
	/**
	 * 职能工资
	 */
	private String roleSalary;
	/**
	 * 职责工资
	 */
	private String dutySalary;
	/**
	 * 竞职金
	 */
	private String competeSalary;
	/**
	 * 保密工资
	 */
	private String secrecySalary;
	/**
	 * 调平工资
	 */
	private String levelSalary;
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 * 保障工资
	 */
	private String guaranteeSalary;
	/**
	 * 福利工资
	 */
	private String welfareSalary;
	/**
	 * 总金额（保障工资 + 福利津贴）
	 */
	private String totalSalary;
	/**
	 * 周六加班
	 */
	private String overtimeSalary;

	/**
	 * 汇总工资（保障工资+福利津贴 + 周六加班）
	 */
	private String summarySalary;

	public String getSalaryKey() {
		return salaryKey;
	}

	public void setSalaryKey(String salaryKey) {
		this.salaryKey = salaryKey;
	}

	public String getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getSalaryLevelSerial() {
		return salaryLevelSerial;
	}

	public void setSalaryLevelSerial(String salaryLevelSerial) {
		this.salaryLevelSerial = salaryLevelSerial;
	}

	public String getSalaryLevelNum() {
		return salaryLevelNum;
	}

	public void setSalaryLevelNum(String salaryLevelNum) {
		this.salaryLevelNum = salaryLevelNum;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getOvertimeSalary() {
		return overtimeSalary;
	}

	public void setOvertimeSalary(String overtimeSalary) {
		this.overtimeSalary = overtimeSalary;
	}

	public String getSummarySalary() {
		return summarySalary;
	}

	public void setSummarySalary(String summarySalary) {
		this.summarySalary = summarySalary;
	}
}
