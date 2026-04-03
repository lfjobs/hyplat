package hy.ea.bo.office.face;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

public class OfficeFaceEmpower implements BaseBean,Cloneable, ExcelBean,java.io.Serializable{
    private String staffKey;//主键
    private String staffId;//人员的关系id（DT_HR_STAFF的staffkey）,当personType=1时才会有

    private String companyId;//人员所属场所（授权场所下面的所有设备）

    private String empowerStaffId;//授权操作人id

    private String empowerStaffName;//授权操作人名称
    private Integer empowerState;//授权状态

    private Date empowerTime;//授权时间

    private Date createTime=new Date();//创建时间

    private Integer deleteState=0;//删除状态
    private String personId;//第三方平台返回的全局id
    private BigDecimal payMoney;//缴费金额
    private Integer personType;//人员类型，1系统人员，2临时人员

    private String staffName;//人员的名称（DT_HR_STAFF的staffName或临时人员的名称）
    private String personImage;//人员照片路径
    private Integer payStatus;//缴费状态
    private String notes;//备注

    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }




    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public String getStaffKey() {
        return staffKey;
    }

    public void setStaffKey(String staffKey) {
        this.staffKey = staffKey;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Date getEmpowerTime() {
        return empowerTime;
    }

    public void setEmpowerTime(Date empowerTime) {
        this.empowerTime = empowerTime;
    }

    public String getEmpowerStaffId() {
        return empowerStaffId;
    }

    public void setEmpowerStaffId(String empowerStaffId) {
        this.empowerStaffId = empowerStaffId;
    }

    public String getEmpowerStaffName() {
        return empowerStaffName;
    }

    public void setEmpowerStaffName(String empowerStaffName) {
        this.empowerStaffName = empowerStaffName;
    }

    public Integer getEmpowerState() {
        return empowerState;
    }

    public void setEmpowerState(Integer empowerState) {
        this.empowerState = empowerState;
    }

    @Override
    public String toString() {
        return "OfficeFaceEmpower{" +
                "staffKey='" + staffKey + '\'' +
                ", staffId='" + staffId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", empowerStaffId='" + empowerStaffId + '\'' +
                ", empowerStaffName='" + empowerStaffName + '\'' +
                ", empowerState=" + empowerState +
                ", empowerTime=" + empowerTime +
                ", createTime=" + createTime +
                ", deleteState=" + deleteState +
                ", personId='" + personId + '\'' +
                ", payMoney=" + payMoney +
                ", personType=" + personType +
                ", staffName='" + staffName + '\'' +
                ", personImage='" + personImage + '\'' +
                '}';
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
