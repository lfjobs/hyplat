package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 *
 * 约车时间段模式详细表
 */
public class TbElycReservationDetail implements BaseBean,Serializable{
    private String rdKey;
    private String rdId;
    private String reservationModeId;//约车时间段模式表外键
    private String startTime;//课时开始时间
    private String endTime;//课时开始时间
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;
    private Double hours;//时长

    public String getRdKey() {
        return rdKey;
    }

    public void setRdKey(String rdKey) {
        this.rdKey = rdKey;
    }

    public String getRdId() {
        return rdId;
    }

    public void setRdId(String rdId) {
        this.rdId = rdId;
    }

    public String getReservationModeId() {
        return reservationModeId;
    }

    public void setReservationModeId(String reservationModeId) {
        this.reservationModeId = reservationModeId;
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
