package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 购物卡
 */
public class GiftCards implements java.io.Serializable, BaseBean {
    private String cardKey;
    private String cardId;
    private String staffID;// 人员ID
    private String companyID;//公司ID
    private String cardNumber;// 购物卡号(办卡)
    private String oldCardNumber;// 购物卡号(补卡之后的旧卡)
    private Date applyCardDate;// 办卡时间
    private Date reissueCardDate;// 补卡时间
    private String state;//0挂失 1：正常
    private String operator; //操作人

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOldCardNumber() {
        return oldCardNumber;
    }

    public void setOldCardNumber(String oldCardNumber) {
        this.oldCardNumber = oldCardNumber;
    }

    public Date getApplyCardDate() {
        return applyCardDate;
    }

    public void setApplyCardDate(Date applyCardDate) {
        this.applyCardDate = applyCardDate;
    }

    public Date getReissueCardDate() {
        return reissueCardDate;
    }

    public void setReissueCardDate(Date reissueCardDate) {
        this.reissueCardDate = reissueCardDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}


