package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练预约时间段
 */
public class TbElycOrderDetailTime implements BaseBean,Serializable {
    private String odtKey;
    private String odtId;
    private String teacherId;//关联教练ID
    private Date lessionStartTime;//课程开始时间
    private Date lessionEndTime;//课程结束时间
    private String status;//1.已约 2.未约 3.教练员请假
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;
    private String companyId;//驾校ID
    private Double hours;//时长


    public String getOdtKey() {
        return odtKey;
    }

    public void setOdtKey(String odtKey) {
        this.odtKey = odtKey;
    }

    public String getOdtId() {
        return odtId;
    }

    public void setOdtId(String odtId) {
        this.odtId = odtId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Date getLessionStartTime() {
        return lessionStartTime;
    }

    public void setLessionStartTime(Date lessionStartTime) {
        this.lessionStartTime = lessionStartTime;
    }

    public Date getLessionEndTime() {
        return lessionEndTime;
    }

    public void setLessionEndTime(Date lessionEndTime) {
        this.lessionEndTime = lessionEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
