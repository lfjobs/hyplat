package hy.ea.bo.office.face;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class OfficeFaceResult implements java.io.Serializable, BaseBean{
    private String id;

    private String personId;

    private String deviceSN;

    private byte inoutFlag;

    private String snapImage;

    private Date snapTime;

    private Date createTime;

    private String personName;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public byte getInoutFlag() {
        return inoutFlag;
    }

    public void setInoutFlag(byte inoutFlag) {
        this.inoutFlag = inoutFlag;
    }

    public String getSnapImage() {
        return snapImage;
    }

    public void setSnapImage(String snapImage) {
        this.snapImage = snapImage;
    }

    public Date getSnapTime() {
        return snapTime;
    }

    public void setSnapTime(Date snapTime) {
        this.snapTime = snapTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "OfficeFaceResult{" +
                "id='" + id + '\'' +
                ", personId='" + personId + '\'' +
                ", deviceSN='" + deviceSN + '\'' +
                ", inoutFlag='" + inoutFlag + '\'' +
                ", snapImage='" + snapImage + '\'' +
                ", snapTime=" + snapTime +
                ", createTime=" + createTime +
                ", personName='" + personName + '\'' +
                '}';
    }
}
