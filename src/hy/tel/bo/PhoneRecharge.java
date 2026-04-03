package hy.tel.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PhoneRecharge entity. @author MyEclipse Persistence Tools
 */

public class PhoneRecharge implements BaseBean {

	// Fields

	private String phoneRechargeKey;
	private String phoneRechargeId;
	private String companyId;
	private String staffId;
	private Date phoneRechargeTime;
	private BigDecimal phoneRechargeMoney;
	private BigDecimal longTalkTime;

	// Constructors

	/** default constructor */
	public PhoneRecharge() {
	}

	/** full constructor */
	public PhoneRecharge(String phoneRechargeId, String companyId,
			String staffId, Date phoneRechargeTime,
			BigDecimal phoneRechargeMoney, BigDecimal longTalkTime) {
		this.phoneRechargeId = phoneRechargeId;
		this.companyId = companyId;
		this.staffId = staffId;
		this.phoneRechargeTime = phoneRechargeTime;
		this.phoneRechargeMoney = phoneRechargeMoney;
		this.longTalkTime = longTalkTime;
	}

	// Property accessors

	public String getPhoneRechargeKey() {
		return this.phoneRechargeKey;
	}

	public void setPhoneRechargeKey(String phoneRechargeKey) {
		this.phoneRechargeKey = phoneRechargeKey;
	}

	public String getPhoneRechargeId() {
		return this.phoneRechargeId;
	}

	public void setPhoneRechargeId(String phoneRechargeId) {
		this.phoneRechargeId = phoneRechargeId;
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

	public Date getPhoneRechargeTime() {
		return this.phoneRechargeTime;
	}

	public void setPhoneRechargeTime(Date phoneRechargeTime) {
		this.phoneRechargeTime = phoneRechargeTime;
	}

	public BigDecimal getPhoneRechargeMoney() {
		return this.phoneRechargeMoney;
	}

	public void setPhoneRechargeMoney(BigDecimal phoneRechargeMoney) {
		this.phoneRechargeMoney = phoneRechargeMoney;
	}

	public BigDecimal getLongTalkTime() {
		return this.longTalkTime;
	}

	public void setLongTalkTime(BigDecimal longTalkTime) {
		this.longTalkTime = longTalkTime;
	}

}