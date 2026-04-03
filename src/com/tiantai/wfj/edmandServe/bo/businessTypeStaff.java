package com.tiantai.wfj.edmandServe.bo;

import hy.plat.bo.BaseBean;

/**
 * 行业人员关联表
 */
public class businessTypeStaff implements BaseBean {

    private String bskey;
    private String bsid;
    private String bsStaffid;  //人员id
    private String btypeId;  //行业id
    private Integer clickNum;  //点击量

    public String getBskey() {
        return bskey;
    }

    public void setBskey(String bskey) {
        this.bskey = bskey;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public String getBsStaffid() {
        return bsStaffid;
    }

    public void setBsStaffid(String bsStaffid) {
        this.bsStaffid = bsStaffid;
    }

    public String getBtypeId() {
        return btypeId;
    }

    public void setBtypeId(String btypeId) {
        this.btypeId = btypeId;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}
