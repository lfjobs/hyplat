package hy.tel.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * PhoneRecode entity. @author MyEclipse Persistence Tools
 */

public class PhoneRecode implements BaseBean{

	// Fields

	private String phoneRecodeKey;
	private String phoneRecodeId;
	private String companyId;
	private String staffId;
	private Long callingNumber;
	private Long calledNumber;
	private Date recodeStartTime;
	private Date recodeEndTime;

	// Constructors

	/** default constructor */
	public PhoneRecode() {
	}

	/** full constructor */
	public PhoneRecode(String phoneRecodeId, String companyId, String staffId,
			Long callingNumber, Long calledNumber, Date recodeStartTime,
			Date recodeEndTime) {
		this.phoneRecodeId = phoneRecodeId;
		this.companyId = companyId;
		this.staffId = staffId;
		this.callingNumber = callingNumber;
		this.calledNumber = calledNumber;
		this.recodeStartTime = recodeStartTime;
		this.recodeEndTime = recodeEndTime;
	}

	// Property accessors

	public String getPhoneRecodeKey() {
		return this.phoneRecodeKey;
	}

	public void setPhoneRecodeKey(String phoneRecodeKey) {
		this.phoneRecodeKey = phoneRecodeKey;
	}

	public String getPhoneRecodeId() {
		return this.phoneRecodeId;
	}

	public void setPhoneRecodeId(String phoneRecodeId) {
		this.phoneRecodeId = phoneRecodeId;
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

	public Long getCallingNumber() {
		return this.callingNumber;
	}

	public void setCallingNumber(Long callingNumber) {
		this.callingNumber = callingNumber;
	}

	public Long getCalledNumber() {
		return this.calledNumber;
	}

	public void setCalledNumber(Long calledNumber) {
		this.calledNumber = calledNumber;
	}

	public Date getRecodeStartTime() {
		return this.recodeStartTime;
	}

	public void setRecodeStartTime(Date recodeStartTime) {
		this.recodeStartTime = recodeStartTime;
	}

	public Date getRecodeEndTime() {
		return this.recodeEndTime;
	}

	public void setRecodeEndTime(Date recodeEndTime) {
		this.recodeEndTime = recodeEndTime;
	}

}