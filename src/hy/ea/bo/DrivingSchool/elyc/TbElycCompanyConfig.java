package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 约车配置
 * mz
 */
public class TbElycCompanyConfig implements BaseBean,Serializable {
    private String ccKey;
    private String ccId;
    private String companyId;//驾校ID
    private String genday;//驾校提前生成预约记录天数
    private String studentay;//学员提前预约天数
    private String startTime;//学员每天可约车时段开始时间
    private String endTime;//学员每天可约车时段结束时间
    private String cancelDetail;//提前 N小时取消
    private String studentCanBookingToday;//学员是否可约当天*
    private String isUsed;//是否启用
    private String createperson;
    private String updateperson;
    private Date createdate;
    private Date updatedate;

    private Double studentLeaveTime;//学员每天可练车小时数限制*


    public String getCcKey() {
        return ccKey;
    }

    public void setCcKey(String ccKey) {
        this.ccKey = ccKey;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStudentay() {
        return studentay;
    }
    public void setStudentay(String studentay) {
        this.studentay = studentay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCancelDetail() {
        return cancelDetail;
    }

    public void setCancelDetail(String cancelDetail) {
        this.cancelDetail = cancelDetail;
    }

    public String getStudentCanBookingToday() {
        return studentCanBookingToday;
    }

    public void setStudentCanBookingToday(String studentCanBookingToday) {
        this.studentCanBookingToday = studentCanBookingToday;
    }
    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getUpdateperson() {
        return updateperson;
    }

    public void setUpdateperson(String updateperson) {
        this.updateperson = updateperson;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Double getStudentLeaveTime() {
        return studentLeaveTime;
    }

    public void setStudentLeaveTime(Double studentLeaveTime) {
        this.studentLeaveTime = studentLeaveTime;
    }

    public String getGenday() {
        return genday;
    }

    public void setGenday(String genday) {
        this.genday = genday;
    }
}
