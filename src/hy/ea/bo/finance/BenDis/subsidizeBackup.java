package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;

/**
 * Created by Administrator on 2018-01-17.
 */
@Entity
@Table(name = "DT_SUBSIDIZE_BACKUP", schema = "NEWS", catalog = "")
public class subsidizeBackup implements BaseBean{
    private String sbKey;
    private String sbId;
    private String cashId;//订单id
    private String cashJum;//订单编号
    private String mType;//帐号类别
    private String mId;//帐号id
    private String mZh;//帐号
    private String staffId;//人员id
    private String staffName;//人员姓名
    private String mStatus;//判断公司个人状态 0：公司 1：个人
    private String jbNum;//金币数量
    private String jbBl;//分配金币比例
    private String status;//状态 0：正常 1：作废
    private String sbType; //分配类型 0:消费红包 1:消费补充红包 2:粉丝红包 3:粉丝补充红包

    @Id
    @Column(name = "SB_KEY", nullable = false, length = 50)
    public String getSbKey() {
        return sbKey;
    }

    public void setSbKey(String sbKey) {
        this.sbKey = sbKey;
    }

    @Basic
    @Column(name = "SB_ID", nullable = true, length = 50)
    public String getSbId() {
        return sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    @Basic
    @Column(name = "CASH_ID", nullable = true, length = 50)
    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    @Basic
    @Column(name = "CASH_JUM", nullable = true, length = 50)
    public String getCashJum() {
        return cashJum;
    }

    public void setCashJum(String cashJum) {
        this.cashJum = cashJum;
    }

    @Basic
    @Column(name = "M_TYPE", nullable = true, length = 50)
    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Basic
    @Column(name = "M_ID", nullable = true, length = 50)
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Basic
    @Column(name = "M_ZH", nullable = true, length = 50)
    public String getmZh() {
        return mZh;
    }

    public void setmZh(String mZh) {
        this.mZh = mZh;
    }

    @Basic
    @Column(name = "STAFF_ID", nullable = true, length = 50)
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    @Basic
    @Column(name = "STAFF_NAME", nullable = true, length = 50)
    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Basic
    @Column(name = "M_STATUS", nullable = true, length = 50)
    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    @Basic
    @Column(name = "JB_NUM", nullable = true, length = 50)
    public String getJbNum() {
        return jbNum;
    }

    public void setJbNum(String jbNum) {
        this.jbNum = jbNum;
    }

    @Basic
    @Column(name = "JB_BL", nullable = true, length = 50)
    public String getJbBl() {
        return jbBl;
    }

    public void setJbBl(String jbBl) {
        this.jbBl = jbBl;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSbType() {
        return sbType;
    }

    public void setSbType(String sbType) {
        this.sbType = sbType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        subsidizeBackup that = (subsidizeBackup) o;

        if (sbKey != null ? !sbKey.equals(that.sbKey) : that.sbKey != null) return false;
        if (sbId != null ? !sbId.equals(that.sbId) : that.sbId != null) return false;
        if (cashId != null ? !cashId.equals(that.cashId) : that.cashId != null) return false;
        if (cashJum != null ? !cashJum.equals(that.cashJum) : that.cashJum != null) return false;
        if (mType != null ? !mType.equals(that.mType) : that.mType != null) return false;
        if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
        if (mZh != null ? !mZh.equals(that.mZh) : that.mZh != null) return false;
        if (staffId != null ? !staffId.equals(that.staffId) : that.staffId != null) return false;
        if (staffName != null ? !staffName.equals(that.staffName) : that.staffName != null) return false;
        if (mStatus != null ? !mStatus.equals(that.mStatus) : that.mStatus != null) return false;
        if (jbNum != null ? !jbNum.equals(that.jbNum) : that.jbNum != null) return false;
        if (jbBl != null ? !jbBl.equals(that.jbBl) : that.jbBl != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sbKey != null ? sbKey.hashCode() : 0;
        result = 31 * result + (sbId != null ? sbId.hashCode() : 0);
        result = 31 * result + (cashId != null ? cashId.hashCode() : 0);
        result = 31 * result + (cashJum != null ? cashJum.hashCode() : 0);
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        result = 31 * result + (mZh != null ? mZh.hashCode() : 0);
        result = 31 * result + (staffId != null ? staffId.hashCode() : 0);
        result = 31 * result + (staffName != null ? staffName.hashCode() : 0);
        result = 31 * result + (mStatus != null ? mStatus.hashCode() : 0);
        result = 31 * result + (jbNum != null ? jbNum.hashCode() : 0);
        result = 31 * result + (jbBl != null ? jbBl.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
