package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class CashierBillsExt implements BaseBean, java.io.Serializable {
    private String cashierBillsExtKey; //主键id
    private String cashierBillsID; //预算单id
    private String receiverID; //传阅接收人id
    private String receiverDeptID; //传阅接收部门
    private String receiverCompanyID; //传阅接收公司
    private Date updateTime; //传阅时间
    private String fromMember; //传阅人

    private String isComplete;//是否完成
    private String completeTime;//完成时间
    private String completeQuality;//完成质量
    private String reviewerId;//审核人Id
    private String reviewerName;//审核人名
    private String reviewTime;//审核时间
    private String reviewStatus;//审核状态
    private String isSend;//是否发送


    public String getCashierBillsExtKey() {
        return cashierBillsExtKey;
    }

    public void setCashierBillsExtKey(String cashierBillsExtKey) {
        this.cashierBillsExtKey = cashierBillsExtKey;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverDeptID() {
        return receiverDeptID;
    }

    public void setReceiverDeptID(String receiverDeptID) {
        this.receiverDeptID = receiverDeptID;
    }

    public String getReceiverCompanyID() {
        return receiverCompanyID;
    }

    public void setReceiverCompanyID(String receiverCompanyID) {
        this.receiverCompanyID = receiverCompanyID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFromMember() {
        return fromMember;
    }

    public void setFromMember(String fromMember) {
        this.fromMember = fromMember;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getCompleteQuality() {
        return completeQuality;
    }

    public void setCompleteQuality(String completeQuality) {
        this.completeQuality = completeQuality;
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

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
    public String getIsSend() {
        return isSend;
    }
    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }
}