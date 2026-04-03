package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2019/7/17.
 */
public class OperatorInfo implements BaseBean {
    private String operatorKey;
    private String operatorID;
    private String staffID;
    private String sccID;
    private String journalNum;
    private String payWay;//00支付宝 01微信 02现金
    private String status;//00未付款，01已付款

    public OperatorInfo(){};

    public OperatorInfo(String operatorID,String staffID,String sccID,String journalNum,String payWay,String status){
        this.operatorID = operatorID;
        this.staffID = staffID;
        this.sccID = sccID;
        this.journalNum = journalNum;
        this.payWay = payWay;
        this.status = status;
    }

    public String getOperatorKey() {
        return operatorKey;
    }

    public void setOperatorKey(String operatorKey) {
        this.operatorKey = operatorKey;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getSccID() {
        return sccID;
    }

    public void setSccID(String sccID) {
        this.sccID = sccID;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
