package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */

public class WageResultVO implements BaseBean {

	private String resultKey;
	private String resultId;
	private String groupCompanySn;
	private String companyId;
	private String staffId;
	private String staffCode;
	private String staffName;
	private String deptName;
	private Date moneyDate;
	private BigDecimal deserveMoney;
	private BigDecimal realMoney;
	private BigDecimal addTimeMoney;
	private BigDecimal miTimeMoney;
	private BigDecimal addCMoney;
	private BigDecimal miCMoney;
	private BigDecimal taxMoney;

	
	public BigDecimal getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(BigDecimal taxMoney) {
		this.taxMoney = taxMoney;
	}

	public String getResultKey() {
		return this.resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getResultId() {
		return this.resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffCode() {
		return this.staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getMoneyDate() {
		return this.moneyDate;
	}

	public void setMoneyDate(Date moneyDate) {
		this.moneyDate = moneyDate;
	}

	public BigDecimal getDeserveMoney() {
		return this.deserveMoney;
	}

	public void setDeserveMoney(BigDecimal deserveMoney) {
		this.deserveMoney = deserveMoney;
	}

	public BigDecimal getRealMoney() {
		return this.realMoney;
	}

	public void setRealMoney(BigDecimal realMoney) {
		this.realMoney = realMoney;
	}

	public BigDecimal getAddTimeMoney() {
		return this.addTimeMoney;
	}

	public void setAddTimeMoney(BigDecimal addTimeMoney) {
		this.addTimeMoney = addTimeMoney;
	}

	public BigDecimal getMiTimeMoney() {
		return this.miTimeMoney;
	}

	public void setMiTimeMoney(BigDecimal miTimeMoney) {
		this.miTimeMoney = miTimeMoney;
	}

	public BigDecimal getAddCMoney() {
		return this.addCMoney;
	}

	public void setAddCMoney(BigDecimal addCMoney) {
		this.addCMoney = addCMoney;
	}

	public BigDecimal getMiCMoney() {
		return this.miCMoney;
	}

	public void setMiCMoney(BigDecimal miCMoney) {
		this.miCMoney = miCMoney;
	}

}