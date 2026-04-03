package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 帐号表公司关联表
 */
public class TEshopCusComCompany implements BaseBean, Serializable {

    private String sccckey;
    private String sccId; // 账号ID
    private String companyId;//公司ID

    public TEshopCusComCompany() {
    }

    public TEshopCusComCompany(String sccId, String companyId) {
        this.sccId = sccId;
        this.companyId = companyId;
    }

    public String getSccckey() {
        return sccckey;
    }

    public void setSccckey(String sccckey) {
        this.sccckey = sccckey;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}