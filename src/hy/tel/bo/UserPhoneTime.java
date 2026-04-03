package hy.tel.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * UserPhoneTime entity. @author MyEclipse Persistence Tools
 */

public class UserPhoneTime implements BaseBean {

	// Fields

	private String userPhoneTimeKey;
	private String companyId;
	private String userPhoneTimeId;
	private String staffId;
	private BigDecimal userTime;

	// Constructors

	/** default constructor */
	public UserPhoneTime() {
	}
	public UserPhoneTime(BigDecimal userTime) {
		this.userTime = userTime;
	}

	/** full constructor */
	public UserPhoneTime(String companyId, String userPhoneTimeId,
			String staffId, BigDecimal userTime) {
		this.companyId = companyId;
		this.userPhoneTimeId = userPhoneTimeId;
		this.staffId = staffId;
		this.userTime = userTime;
	}

	// Property accessors

	public String getUserPhoneTimeKey() {
		return this.userPhoneTimeKey;
	}

	public void setUserPhoneTimeKey(String userPhoneTimeKey) {
		this.userPhoneTimeKey = userPhoneTimeKey;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserPhoneTimeId() {
		return this.userPhoneTimeId;
	}

	public void setUserPhoneTimeId(String userPhoneTimeId) {
		this.userPhoneTimeId = userPhoneTimeId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public BigDecimal getUserTime() {
		return this.userTime;
	}

	public void setUserTime(BigDecimal userTime) {
		this.userTime = userTime;
	}

}