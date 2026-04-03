package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练请假
 */
public class ElycTrainerLeave implements BaseBean,Serializable {
    private String etlKey;
    private String etlId;
    private String trainer_id;//教练ID
    private Date leave_date;//申请时间
    private Date start_time;//请假开始时间
    private Date end_time;//请假结束时间
    private String status;//状态:00:未生效,01:已生效
    private String relay_trainer_id;//代班教练iD
    private String company_id;//驾校ID
    private String createperson;
    private String updateperson;
    private Date createdate;
    private Date updatedate;


    public String getEtlKey() {
        return etlKey;
    }

    public void setEtlKey(String etlKey) {
        this.etlKey = etlKey;
    }

    public String getEtlId() {
        return etlId;
    }

    public void setEtlId(String etlId) {
        this.etlId = etlId;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public Date getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(Date leave_date) {
        this.leave_date = leave_date;
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

    public String getRelay_trainer_id() {
        return relay_trainer_id;
    }

    public void setRelay_trainer_id(String relay_trainer_id) {
        this.relay_trainer_id = relay_trainer_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
