package com.faceSDK.faceVO;

public class FaceDeviceVO {
    //设备类型1：未知，2：人脸识别，3：人证比对
    private Integer type;
    //设备序列号
    private String serial;
    //设备名称
    private String name;
    //出入标识：1:入，0:出
    private byte inoutFlag;

    //设备在表中的id
    private String id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getInoutFlag() {
        return inoutFlag;
    }

    public void setInoutFlag(byte inoutFlag) {
        this.inoutFlag = inoutFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FaceDeviceVO{" +
                "type=" + type +
                ", serial='" + serial + '\'' +
                ", name='" + name + '\'' +
                ", inoutFlag=" + inoutFlag +
                '}';
    }
}
