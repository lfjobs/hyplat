package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 考试相关
 */
public class ExamRelate implements BaseBean {

    private String erKey;
    private String erId;
    private String tqeID;//题库考试备份
    private String tqID;//题库
    private String staffID;//考试人
    private String staffName;//考试人
    private String telphone;
    private String card;
    private Date startDate;
    private Date endDate;
    private String isHg;//是否合格
    private int tscore;//总得分
    private String status;//00尚未考试，01 正在考试 02 已经考完 03 退出后，没有再考，进去时已经超时

    public String getErKey() {
        return erKey;
    }

    public void setErKey(String erKey) {
        this.erKey = erKey;
    }

    public String getErId() {
        return erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
    }



    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIsHg() {
        return isHg;
    }

    public void setIsHg(String isHg) {
        this.isHg = isHg;
    }

    public int getTscore() {
        return tscore;
    }

    public void setTscore(int tscore) {
        this.tscore = tscore;
    }

    public String getTqID() {
        return tqID;
    }

    public void setTqID(String tqID) {
        this.tqID = tqID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTqeID() {
        return tqeID;
    }

    public void setTqeID(String tqeID) {
        this.tqeID = tqeID;
    }
}