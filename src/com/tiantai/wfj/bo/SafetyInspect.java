package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 安全巡查
 */
public class SafetyInspect implements BaseBean, ExcelBean, java.io.Serializable {
    private String sikey;
    private String siID;
    private String nfcID;  //NFC芯片绑定安全点id  NfcChip
    private Date siDate;  //巡逻时间
    private String illustrate;  //说明
    private String siType; //类型  00：红  01：黄  02：绿
    private String companyID;//公司ID
    private String companyName;//公司name
    private String staffID;//负责人id
    private String staffName;//负责人姓名

    public SafetyInspect(String sikey, String siID, String nfcID, Date siDate, String illustrate, String siType, String companyID, String companyName, String staffID, String staffName) {
        this.sikey = sikey;
        this.siID = siID;
        this.nfcID = nfcID;
        this.siDate = siDate;
        this.illustrate = illustrate;
        this.siType = siType;
        this.companyID = companyID;
        this.companyName = companyName;
        this.staffID = staffID;
        this.staffName = staffName;
    }

    public SafetyInspect() {

    }


    public String getSikey() {
        return sikey;
    }

    public void setSikey(String sikey) {
        this.sikey = sikey;
    }

    public String getSiID() {
        return siID;
    }

    public void setSiID(String siID) {
        this.siID = siID;
    }

    public String getNfcID() {
        return nfcID;
    }

    public void setNfcID(String nfcID) {
        this.nfcID = nfcID;
    }

    public Date getSiDate() {
        return siDate;
    }

    public void setSiDate(Date siDate) {
        this.siDate = siDate;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public void setIllustrate(String illustrate) {
        this.illustrate = illustrate;
    }

    public String getSiType() {
        return siType;
    }

    public void setSiType(String siType) {
        this.siType = siType;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    @Override
    public String[] properties() {
        return new String[0];
    }
}
