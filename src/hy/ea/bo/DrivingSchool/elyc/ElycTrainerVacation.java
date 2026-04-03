package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练休班
 */
public class ElycTrainerVacation implements BaseBean,Serializable {
    private String etvKey;
    private String etvId;
    private String trainer_id;//教练员ID
    private String status;//用于存放是否已经实施了休班,00:未生效,01:已生效
    private Date start_time;//休假开始时间
    private Date end_time;//休假结束时间
    private String company_id;//驾校ID
    private String createperson;
    private String updateperson;
    private Date createdate;
    private Date updatedate;

    public String getEtvKey() {
        return etvKey;
    }

    public void setEtvKey(String etvKey) {
        this.etvKey = etvKey;
    }

    public String getEtvId() {
        return etvId;
    }

    public void setEtvId(String etvId) {
        this.etvId = etvId;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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
}
