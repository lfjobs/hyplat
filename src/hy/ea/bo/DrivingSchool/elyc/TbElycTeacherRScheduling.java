package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练关联班次
 */
public class TbElycTeacherRScheduling implements BaseBean,Serializable {
    private String trsKey;
    private String trsId;
    private String teacherId;
    private String teacherName;//数据库不存
    private String className;//数据库不存
    private String idcard;//身份证ID
    private String classId;//百次外键
    private String companyId;//驾校公司ID
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;
    private String status;//00未启用，01启用

    public String getTrsKey() {
        return trsKey;
    }

    public void setTrsKey(String trsKey) {
        this.trsKey = trsKey;
    }

    public String getTrsId() {
        return trsId;
    }

    public void setTrsId(String trsId) {
        this.trsId = trsId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
