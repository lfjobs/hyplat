package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * mz 班次
 */
public class TbElycScheduling implements BaseBean,Serializable {
    private String slKey;
    private String classId;
    private String className;//班次名称
    private String rotateMode;//轮询方式，日，周，目前只做日的
    private String status;//启用状态00未启用；01启用
    private String companyId;//驾校公司ID
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;

    public String getSlKey() {
        return slKey;
    }

    public void setSlKey(String slKey) {
        this.slKey = slKey;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getRotateMode() {
        return rotateMode;
    }

    public void setRotateMode(String rotateMode) {
        this.rotateMode = rotateMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
