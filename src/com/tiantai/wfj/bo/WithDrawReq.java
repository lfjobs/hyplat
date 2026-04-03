package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2019/6/4.
 */
public class WithDrawReq implements BaseBean {
    private String withDrawKey;
    private String withDrawID;
    private String sccID;      //提现发起账号
    private String withDrawWay; //提现方式 01:支付宝 02:微信 03: 银行卡
    private Date withDrawDate; //提现时间
    private String jNumOrder; //提现订单号
    private String amount;//提现金额
    private String message;//提现信息
    private String state; //提现状态  0:初始化  00 :提现成功  01:提现成功(生成订单失败)  10:提现失败(正常情况:如企业账户余额不足) 11:提现失败(非正常情况:报错进入catch)  12：项目内错误

    public WithDrawReq() {}

    public WithDrawReq(String withDrawID,String sccID,String withDrawWay,Date withDrawDate,String jNumOrder,String amount){
        this.withDrawID = withDrawID;
        this.sccID = sccID;
        this.withDrawWay = withDrawWay;
        this.withDrawDate = withDrawDate;
        this.jNumOrder = jNumOrder;
        this.amount = amount;
    }

    public String getWithDrawKey() {
        return withDrawKey;
    }

    public void setWithDrawKey(String withDrawKey) {
        this.withDrawKey = withDrawKey;
    }

    public String getWithDrawID() {
        return withDrawID;
    }

    public void setWithDrawID(String withDrawID) {
        this.withDrawID = withDrawID;
    }

    public String getSccID() {
        return sccID;
    }

    public void setSccID(String sccID) {
        this.sccID = sccID;
    }

    public String getWithDrawWay() {
        return withDrawWay;
    }

    public void setWithDrawWay(String withDrawWay) {
        this.withDrawWay = withDrawWay;
    }

    public Date getWithDrawDate() {
        return withDrawDate;
    }

    public void setWithDrawDate(Date withDrawDate) {
        this.withDrawDate = withDrawDate;
    }

    public String getjNumOrder() {
        return jNumOrder;
    }

    public void setjNumOrder(String jNumOrder) {
        this.jNumOrder = jNumOrder;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
