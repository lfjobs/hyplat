package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤设置
 * @author
 *
 */
public class CheckOnSetup implements BaseBean, Serializable {
	private static final long serialVersionUID = -1388822093830151969L;
	private String checkOnSetupId;
	private String companyId;
	private String rankId;//工资级别ID
	private String checkOnTypeId;//考勤类别ID
	private String referenceBaseSalary;//引用基本工资:Y/N
	private String referenceGuaranteeSalary;//引用保障工资:Y/N
	private String referenceWelfareSalary;//引用福利工资:Y/N
	private Double discountRate;//折扣倍数
	private String amountType;//奖折方式
	private Double checkOnAmount;//奖励金额
	private String meetingRecord;
	private String transferReviewerId;
	private String reviewerComments;
	private String reviewerId;
	private String reviewerName;
	private Date setupDate;//设置时间
	private String rankNo;
	private String checkOnTypeName;
	private String staffId;
	private String staffName;
	private String reviewStatus;
	private String rewardDeductType;
	private Double rewardDeductAmount;

	public CheckOnSetup() {
	}

	public CheckOnSetup(String checkOnSetupId, String companyId, String rankId, String checkOnTypeId, String referenceBaseSalary, String referenceGuaranteeSalary,
						String referenceWelfareSalary, String amountType, Double checkOnAmount, Double discountRate, String rankNo, String checkOnTypeName) {
		this.transferReviewerId = transferReviewerId;
		this.setupDate = setupDate;
		this.reviewerId = reviewerId;
		this.reviewerComments = reviewerComments;
		this.referenceWelfareSalary = referenceWelfareSalary;
		this.referenceGuaranteeSalary = referenceGuaranteeSalary;
		this.referenceBaseSalary = referenceBaseSalary;
		this.rankId = rankId;
		this.meetingRecord = meetingRecord;
		this.discountRate = discountRate;
		this.companyId = companyId;
		this.checkOnTypeId = checkOnTypeId;
		this.checkOnSetupId = checkOnSetupId;
		this.checkOnAmount = checkOnAmount;
		this.amountType = amountType;
		this.rankNo = rankNo;
		this.checkOnTypeName = checkOnTypeName;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Double getCheckOnAmount() {
		return checkOnAmount;
	}

	public void setCheckOnAmount(Double checkOnAmount) {
		this.checkOnAmount = checkOnAmount;
	}

	public String getCheckOnSetupId() {
		return checkOnSetupId;
	}

	public void setCheckOnSetupId(String checkOnSetupId) {
		this.checkOnSetupId = checkOnSetupId;
	}

	public String getCheckOnTypeId() {
		return checkOnTypeId;
	}

	public void setCheckOnTypeId(String checkOnTypeId) {
		this.checkOnTypeId = checkOnTypeId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public String getMeetingRecord() {
		return meetingRecord;
	}

	public void setMeetingRecord(String meetingRecord) {
		this.meetingRecord = meetingRecord;
	}

	public String getRankId() {
		return rankId;
	}

	public void setRankId(String rankId) {
		this.rankId = rankId;
	}

	public String getReferenceBaseSalary() {
		return referenceBaseSalary;
	}

	public void setReferenceBaseSalary(String referenceBaseSalary) {
		this.referenceBaseSalary = referenceBaseSalary;
	}

	public String getReferenceGuaranteeSalary() {
		return referenceGuaranteeSalary;
	}

	public void setReferenceGuaranteeSalary(String referenceGuaranteeSalary) {
		this.referenceGuaranteeSalary = referenceGuaranteeSalary;
	}

	public String getReferenceWelfareSalary() {
		return referenceWelfareSalary;
	}

	public void setReferenceWelfareSalary(String referenceWelfareSalary) {
		this.referenceWelfareSalary = referenceWelfareSalary;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getTransferReviewerId() {
		return transferReviewerId;
	}

	public void setTransferReviewerId(String transferReviewerId) {
		this.transferReviewerId = transferReviewerId;
	}

	public String getCheckOnTypeName() {
		return checkOnTypeName;
	}

	public void setCheckOnTypeName(String checkOnTypeName) {
		this.checkOnTypeName = checkOnTypeName;
	}

	public String getRankNo() {
		return rankNo;
	}

	public void setRankNo(String rankNo) {
		this.rankNo = rankNo;
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

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getRewardDeductType() {
		return rewardDeductType;
	}

	public void setRewardDeductType(String rewardDeductType) {
		this.rewardDeductType = rewardDeductType;
	}

	public Double getRewardDeductAmount() {
		return rewardDeductAmount;
	}

	public void setRewardDeductAmount(Double rewardDeductAmount) {
		this.rewardDeductAmount = rewardDeductAmount;
	}
}
