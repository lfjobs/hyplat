package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz 班次和时间段模式关联
 */
public class TbElycSchedulingDetail implements BaseBean,Serializable {
    private String scdkey;
    private String scdId;
    private String classId;//百次ID
    private String rotateMode;//
    private String reservationModeId;
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;

    public String getScdkey() {
        return scdkey;
    }

    public void setScdkey(String scdkey) {
        this.scdkey = scdkey;
    }

    public String getScdId() {
        return scdId;
    }

    public void setScdId(String scdId) {
        this.scdId = scdId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getRotateMode() {
        return rotateMode;
    }

    public void setRotateMode(String rotateMode) {
        this.rotateMode = rotateMode;
    }



    public String getReservationModeId() {
        return reservationModeId;
    }

    public void setReservationModeId(String reservationModeId) {
        this.reservationModeId = reservationModeId;
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
