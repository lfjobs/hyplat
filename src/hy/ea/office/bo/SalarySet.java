package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 级别工资
 */
public class SalarySet implements BaseBean, Serializable {
	private static final long serialVersionUID = 6863609770373605345L;
	private String salaryKey;
	private String salaryLevelKey;
	private String salaryType;
	private String companyId;
	private Double baseSalary;
	private Double guaranteeSalary;
	private Double welfareSalary;

	public SalarySet() {
	}

	public SalarySet(String salaryKey, String salaryLevelKey, String salaryType, String companyId, Double baseSalary, Double guaranteeSalary, Double welfareSalary) {
		this.baseSalary = baseSalary;
		this.companyId = companyId;
		this.guaranteeSalary = guaranteeSalary;
		this.salaryKey = salaryKey;
		this.salaryLevelKey = salaryLevelKey;
		this.salaryType = salaryType;
		this.welfareSalary = welfareSalary;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Double getGuaranteeSalary() {
		return guaranteeSalary;
	}

	public void setGuaranteeSalary(Double guaranteeSalary) {
		this.guaranteeSalary = guaranteeSalary;
	}

	public String getSalaryKey() {
		return salaryKey;
	}

	public void setSalaryKey(String salaryKey) {
		this.salaryKey = salaryKey;
	}

	public String getSalaryLevelKey() {
		return salaryLevelKey;
	}

	public void setSalaryLevelKey(String salaryLevelKey) {
		this.salaryLevelKey = salaryLevelKey;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public Double getWelfareSalary() {
		return welfareSalary;
	}

	public void setWelfareSalary(Double welfareSalary) {
		this.welfareSalary = welfareSalary;
	}
}
