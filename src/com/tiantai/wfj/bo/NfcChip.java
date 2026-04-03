package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * NFC芯片绑定安全点
 */
public class NfcChip implements BaseBean, ExcelBean,java.io.Serializable  {
    private String nfckey;
    private String nfcID;
    private String sn;  //nfc序列号
    private String model; //nfc标签类型
    private String companyID;//绑定公司ID
    private String companyName;//绑定公司name
    private String staffID;//负责人
    private String staffName;//负责人姓名
    private int ln;//安全点编号 （每个公司从1排序 n+1）
    private String oaskId;//安全类别id （OASafeKind类id）
    private String oaskName;//安全类别名称
    private String en;//设备编号 如：车牌号
    private String bindLocation;//芯片绑定公司地点 如：厨房、仓库、下水道、车间、财务处等
    private Date bindDate;//绑定日期
    private Date onBindDate;//解绑日期
    private String bindState;//绑定状态  00：绑定  01：解绑  默认绑定

    public NfcChip() {
    }

    public NfcChip(String nfckey, String nfcID, String sn, String model, String companyID, String companyName, String staffID, String staffName, int ln, String oaskId, String oaskName, String en, String bindLocation, Date bindDate, Date onBindDate, String bindState) {
        this.nfckey = nfckey;
        this.nfcID = nfcID;
        this.sn = sn;
        this.model = model;
        this.companyID = companyID;
        this.companyName = companyName;
        this.staffID = staffID;
        this.staffName = staffName;
        this.ln = ln;
        this.oaskId = oaskId;
        this.oaskName = oaskName;
        this.en = en;
        this.bindLocation = bindLocation;
        this.bindDate = bindDate;
        this.onBindDate = onBindDate;
        this.bindState = bindState;
    }



    public String getNfckey() {
        return nfckey;
    }

    public void setNfckey(String nfckey) {
        this.nfckey = nfckey;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getLn() {
        return ln;
    }

    public void setLn(int ln) {
        this.ln = ln;
    }

    public String getOaskId() {
        return oaskId;
    }

    public void setOaskId(String oaskId) {
        this.oaskId = oaskId;
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

    public Date getOnBindDate() {
        return onBindDate;
    }

    public void setOnBindDate(Date onBindDate) {
        this.onBindDate = onBindDate;
    }

    public String getBindState() {
        return bindState;
    }

    public void setBindState(String bindState) {
        this.bindState = bindState;
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
