package com.tiantai.telrec.bean;

public class AccessInfo {
	private String id;
	private String userId;
	private String customerId;
	private String customerRelaId;
	private String accessTime;
	private String accessType;
	private String accessContent;
	private String memo;
	private String insertTime;
	private String customerName;
	private String companyid;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerRelaId() {
		return customerRelaId;
	}

	public void setCustomerRelaId(String customerRelaId) {
		this.customerRelaId = customerRelaId;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAccessContent() {
		return accessContent;
	}

	public void setAccessContent(String accessContent) {
		this.accessContent = accessContent;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
