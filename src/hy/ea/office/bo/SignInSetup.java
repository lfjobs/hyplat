package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到设置
 * @author
 *
 */
public class SignInSetup implements BaseBean, Serializable {
	private static final long serialVersionUID = 7351502464614680661L;
	private String signInSetupId;
	private String companyId;
	private String signInTypeId;//签到类别
	private String signInTypeName;
	private String startTime;//签到时间
	private String endTime;//签到结束时间
	private String signInAddress;//签到地址
	private Date setupDate;
	private String staffId;
	private String staffName;
	private String reviewerId;
	private String reviewerName;
	private String reviewStatus;

	public SignInSetup() {
	}

	public SignInSetup(String signInSetupId, String companyId, String signInTypeId, String startTime, String endTime,
					   String signInAddress, Date setupDate, String signInTypeName,String staffId) {
		this.companyId = companyId;
		this.endTime = endTime;
		this.setupDate = setupDate;
		this.signInAddress = signInAddress;
		this.signInSetupId = signInSetupId;
		this.signInTypeId = signInTypeId;
		this.signInTypeName = signInTypeName;
		this.startTime = startTime;
		this.staffId = staffId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSignInAddress() {
		return signInAddress;
	}

	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}

	public String getSignInSetupId() {
		return signInSetupId;
	}

	public void setSignInSetupId(String signInSetupId) {
		this.signInSetupId = signInSetupId;
	}

	public String getSignInTypeId() {
		return signInTypeId;
	}

	public void setSignInTypeId(String signInTypeId) {
		this.signInTypeId = signInTypeId;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getSignInTypeName() {
		return signInTypeName;
	}

	public void setSignInTypeName(String signInTypeName) {
		this.signInTypeName = signInTypeName;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}
