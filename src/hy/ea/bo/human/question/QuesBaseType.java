package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 题库类别
 */
public class QuesBaseType implements BaseBean{

    private String qbtId;
    private String qbtKey;
    private String typeName;
    private String staffID;
    private String staffName;
    private String companyID;
    private Date createDate;
    private String status;//99删除 00 正常

    public String getQbtId() {
        return qbtId;
    }

    public void setQbtId(String qbtId) {
        this.qbtId = qbtId;
    }

    public String getQbtKey() {
        return qbtKey;
    }

    public void setQbtKey(String qbtKey) {
        this.qbtKey = qbtKey;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
