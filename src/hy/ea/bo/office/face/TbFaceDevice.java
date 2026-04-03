package hy.ea.bo.office.face;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class TbFaceDevice implements java.io.Serializable, BaseBean{
    private String id;

    private String faceDeviceId;

    private String companyId;

    private String deviceName;

    private String deviceSN;

    private String notes;

    private String companyName;

    private Date createTime=new Date();

    private Date updateTime=new Date();

    private Integer deleteState;

    private String siteId;//场所id

    private String inoutFlag;


    public String getInoutFlag() {
        return inoutFlag;
    }

    public void setInoutFlag(String inoutFlag) {
        this.inoutFlag = inoutFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaceDeviceId() {
        return faceDeviceId;
    }

    public void setFaceDeviceId(String faceDeviceId) {
        this.faceDeviceId = faceDeviceId;
    }



    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "TbFaceDevice{" +
                "id='" + id + '\'' +
                ", faceDeviceId='" + faceDeviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceSN='" + deviceSN + '\'' +
                ", notes='" + notes + '\'' +
                ", companyName='" + companyName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteState=" + deleteState +
                '}';
    }
}
