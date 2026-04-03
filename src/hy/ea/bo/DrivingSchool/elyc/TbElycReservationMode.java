package hy.ea.bo.DrivingSchool.elyc;


import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 约车时间段模式
 */
public class TbElycReservationMode implements BaseBean,Serializable {
    private  String rmKey;
    private  String reservationModeId;
    private String modeName;//模式名称
    private String status;//模式状态；00未启用，01：启用
    private String companyId;//驾校公司ID
    private Date createdate;
    private Date updatedate;
    private String createperson;
    private String updateperson;

    public String getRmKey() {
        return rmKey;
    }

    public void setRmKey(String rmKey) {
        this.rmKey = rmKey;
    }

    public String getReservationModeId() {
        return reservationModeId;
    }

    public void setReservationModeId(String reservationModeId) {
        this.reservationModeId = reservationModeId;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
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
