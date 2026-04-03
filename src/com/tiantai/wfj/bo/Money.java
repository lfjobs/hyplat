package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 单车金钱记录相关
 */
public class Money implements BaseBean,java.io.Serializable {

	private String  wfjMoneyKey;
	private String  wfjMoneyId;
	private String  staffId;//人员Id
	private String  sccid ;  //人员sccid帐号
	private String  wfjyajin;//单车押金金额
	private String  wfjJifenState;//0:正常(default)   1为钱包没有押金
    private String  wfjyingli ; //代理单车的盈利金额
	private String staffName;//人员姓名（传值字段，不存数据库）
	private String headimage;//人员头像（传值字段，不存数据库）



    public String getWfjMoneyKey() {
        return wfjMoneyKey;
    }

    public void setWfjMoneyKey(String wfjMoneyKey) {
        this.wfjMoneyKey = wfjMoneyKey;
    }

    public String getWfjMoneyId() {
        return wfjMoneyId;
    }

    public void setWfjMoneyId(String wfjMoneyId) {
        this.wfjMoneyId = wfjMoneyId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getWfjyajin() {
        return wfjyajin;
    }

    public void setWfjyajin(String wfjyajin) {
        this.wfjyajin = wfjyajin;
    }

    public String getWfjJifenState() {
        return wfjJifenState;
    }

    public void setWfjJifenState(String wfjJifenState) {
        this.wfjJifenState = wfjJifenState;
    }

    public String getWfjyingli() {
        return wfjyingli;
    }

    public void setWfjyingli(String wfjyingli) {
        this.wfjyingli = wfjyingli;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }
}
