package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 每道题的答题情况
 */
public class ExamAnswer implements BaseBean {
    private String eaKey;
    private String eaId;
    private String erId;//关联的考试
    private String qreID;
    private String qrID;//关联的题目
    private String answer;//考生答案 多选用逗号隔开
    private int score;//总得分
    private String isCorrect;//是正确00 正确 01 错误
    private String staffID;//考生ID
    private String auditID;//审批人员
    private String auditName;//审批人姓名
    private Date auditDate;//审核时间

    public String getEaKey() {
        return eaKey;
    }

    public void setEaKey(String eaKey) {
        this.eaKey = eaKey;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getErId() {
        return erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
    }

    public String getQrID() {
        return qrID;
    }

    public void setQrID(String qrID) {
        this.qrID = qrID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getQreID() {
        return qreID;
    }

    public void setQreID(String qreID) {
        this.qreID = qreID;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAuditID() {
        return auditID;
    }

    public void setAuditID(String auditID) {
        this.auditID = auditID;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
}