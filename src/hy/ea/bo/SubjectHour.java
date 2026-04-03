package hy.ea.bo;

import hy.plat.bo.BaseBean;


/**
 * Created by Administrator on 2017/9/20.
 */
public class SubjectHour implements BaseBean {

    private String subjectHourKey;

    private String subjectHourId;

    private String staffId; //人员ID

    private String licenseType; //培训车型

    private Integer p1Time; //科一学时

    private Integer p2Time;   //科二学时

    private Integer p3Time; //科三学时

    private Integer p4Time;  //科四学时

    private Integer totalTime;   //总学时

    private Integer p1NetworkTime; //已学科一学时

    private Integer p2NetworkTime; //已学科二学时

    private Integer p3NetworkTime; //已学科三学时

    private Integer p4NetworkTime; //已学科四学时

    private Integer hasTime;//拥有学时

    private String reserved1;//预留字段

    private String reserved2;

    public String getSubjectHourKey() {
        return subjectHourKey;
    }

    public void setSubjectHourKey(String subjectHourKey) {
        this.subjectHourKey = subjectHourKey;
    }

    public String getSubjectHourId() {
        return subjectHourId;
    }

    public void setSubjectHourId(String subjectHourId) {
        this.subjectHourId = subjectHourId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public Integer getP1Time() {
        return p1Time;
    }

    public void setP1Time(Integer p1Time) {
        this.p1Time = p1Time;
    }

    public Integer getP2Time() {
        return p2Time;
    }

    public void setP2Time(Integer p2Time) {
        this.p2Time = p2Time;
    }

    public Integer getP3Time() {
        return p3Time;
    }

    public void setP3Time(Integer p3Time) {
        this.p3Time = p3Time;
    }

    public Integer getP4Time() {
        return p4Time;
    }

    public void setP4Time(Integer p4Time) {
        this.p4Time = p4Time;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getP1NetworkTime() {
        return p1NetworkTime;
    }

    public void setP1NetworkTime(Integer p1NetworkTime) {
        this.p1NetworkTime = p1NetworkTime;
    }

    public Integer getP2NetworkTime() {
        return p2NetworkTime;
    }

    public void setP2NetworkTime(Integer p2NetworkTime) {
        this.p2NetworkTime = p2NetworkTime;
    }

    public Integer getP3NetworkTime() {
        return p3NetworkTime;
    }

    public void setP3NetworkTime(Integer p3NetworkTime) {
        this.p3NetworkTime = p3NetworkTime;
    }

    public Integer getP4NetworkTime() {
        return p4NetworkTime;
    }

    public void setP4NetworkTime(Integer p4NetworkTime) {
        this.p4NetworkTime = p4NetworkTime;
    }

    public Integer getHasTime() {
        return hasTime;
    }

    public void setHasTime(Integer hasTime) {
        this.hasTime = hasTime;
    }
}
