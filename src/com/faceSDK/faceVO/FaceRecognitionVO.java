package com.faceSDK.faceVO;

public class FaceRecognitionVO {
    private Integer type;
    private String code;
    private String name;
    private String faces;
    private String faceUrl;
    private String deviceId;
    private Integer empowerType;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaces() {
        return faces;
    }

    public void setFaces(String faces) {
        this.faces = faces;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getEmpowerType() {
        return empowerType;
    }

    public void setEmpowerType(Integer empowerType) {
        this.empowerType = empowerType;
    }

    @Override
    public String toString() {
        return "FaceRecognitionVO{" +
                "type=" + type +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", faces='" + faces + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", empowerType=" + empowerType +
                '}';
    }
}
