package com.faceSDK.faceVO;

import java.util.Arrays;

public class PersonGrantDeviceVO {
    private String personId;
    private String[] deviceIds;
    private Integer passNum=0;
    private Integer passMode=5;
    private Boolean passMonday=true;
    private Boolean passTuesday=true;
    private Boolean passWednesday=true;
    private Boolean passThursday=true;
    private Boolean passFriday=true;
    private Boolean passSaturday=true;
    private Boolean passSunday=true;
    private String startTime="2019-01-01 00:00:00";
    private String expireTime="2099-02-01 00:00:00";

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Integer getPassMode() {
        return passMode;
    }

    public void setPassMode(Integer passMode) {
        this.passMode = passMode;
    }

    public Boolean getPassMonday() {
        return passMonday;
    }

    public void setPassMonday(Boolean passMonday) {
        this.passMonday = passMonday;
    }

    public Boolean getPassTuesday() {
        return passTuesday;
    }

    public void setPassTuesday(Boolean passTuesday) {
        this.passTuesday = passTuesday;
    }

    public Boolean getPassWednesday() {
        return passWednesday;
    }

    public void setPassWednesday(Boolean passWednesday) {
        this.passWednesday = passWednesday;
    }

    public Boolean getPassThursday() {
        return passThursday;
    }

    public void setPassThursday(Boolean passThursday) {
        this.passThursday = passThursday;
    }

    public Boolean getPassFriday() {
        return passFriday;
    }

    public void setPassFriday(Boolean passFriday) {
        this.passFriday = passFriday;
    }

    public Boolean getPassSaturday() {
        return passSaturday;
    }

    public void setPassSaturday(Boolean passSaturday) {
        this.passSaturday = passSaturday;
    }

    public Boolean getPassSunday() {
        return passSunday;
    }

    public void setPassSunday(Boolean passSunday) {
        this.passSunday = passSunday;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "PersonGrantDeviceVO{" +
                "personId='" + personId + '\'' +
                ", deviceIds=" + Arrays.toString(deviceIds) +
                ", passNum=" + passNum +
                ", passMode=" + passMode +
                ", passMonday=" + passMonday +
                ", passTuesday=" + passTuesday +
                ", passWednesday=" + passWednesday +
                ", passThursday=" + passThursday +
                ", passFriday=" + passFriday +
                ", passSaturday=" + passSaturday +
                ", passSunday=" + passSunday +
                ", startTime='" + startTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                '}';
    }
}
