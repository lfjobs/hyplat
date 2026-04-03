package com.tiantai.wfj.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class InspectVo implements BaseBean, ExcelBean,java.io.Serializable  {
    private String sikey;
    private String siID;
    private String nfcID;  //NFC芯片绑定安全点id  NfcChip
    private String sn;  //nfc序列号
    private int ln;//安全点编号 （每个公司从1排序 n+1）
    private String model; //nfc标签类型
    private String oaskName;//安全类别名称
    private String en;//设备编号 如：车牌号
    private String bindLocation;//芯片绑定公司地点 如：厨房、仓库、下水道、车间、财务处等
    private Date bindDate;//绑定日期
    private Date siDate;  //巡逻时间
    private String illustrate;  //说明
    private String siType; //类型  00：红  01：黄  02：绿
    private String companyID;//公司ID
    private String companyName;//公司name
    private String staffID;//负责人id
    private String staffName;//负责人姓

    public InspectVo(String sikey, String siID, String nfcID, String sn, int ln, String model, String oaskName, String en, String bindLocation, Date bindDate, Date siDate, String illustrate, String siType, String companyID, String companyName, String staffID, String staffName) {
        this.sikey = sikey;
        this.siID = siID;
        this.nfcID = nfcID;
        this.sn = sn;
        this.ln = ln;
        this.model = model;
        this.oaskName = oaskName;
        this.en = en;
        this.bindLocation = bindLocation;
        this.bindDate = bindDate;
        this.siDate = siDate;
        this.illustrate = illustrate;
        this.siType = siType;
        this.companyID = companyID;
        this.companyName = companyName;
        this.staffID = staffID;
        this.staffName = staffName;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getLn() {
        return ln;
    }

    public void setLn(int ln) {
        this.ln = ln;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOaskName() {
        return oaskName;
    }

    public void setOaskName(String oaskName) {
        this.oaskName = oaskName;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getBindLocation() {
        return bindLocation;
    }

    public void setBindLocation(String bindLocation) {
        this.bindLocation = bindLocation;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
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
