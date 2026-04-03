package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 学员预约记录
 */
public class TbElycOrderRecord implements BaseBean,Serializable {
    private String etKey;
    private String etoId;
    private String studentId;//学员ID
    private String studentName;//学员姓名
    private String studentPhone;//学员电话
    private String studentNum;//学员身份证号
    private String staffId;//人员id
    private String teacherId;//教练ID
    private String teacherName;//教练姓名
    private String teacherNum;//教练身份证号
    private String teacherPhone;//教练电话

    private String detailId;//预约时间段ID
    private Date lessionStartTime;//课程开始时间
    private Date lessionEndTime;//课程结束时间
    private Double hours;//时长

    private String status;//1正常已约 2已取消   3已签到  4已签退  5已评价 6超时关闭

    private Date cancelTime;//取消时间
    private Date actualCheckInTime;//实际签到时间
    private Date actualCheckOutTime;//实际签退时间

    private String source;//预约来源：00;微分金app 01：e路快车app 02:微信
    private String subject;//预约科目
    private  String companyId;//预约驾校
    private  String companyName;//驾校名称
    private  Date orderTime;//预约时间
    private String replaceTeacherId;//替班教练
    private String replaceTeacherName;//教练姓名


    public String getEtKey() {
        return etKey;
    }

    public void setEtKey(String etKey) {
        this.etKey = etKey;
    }

    public String getEtoId() {
        return etoId;
    }

    public void setEtoId(String etoId) {
        this.etoId = etoId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(String teacherNum) {
        this.teacherNum = teacherNum;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Date getLessionStartTime() {
        return lessionStartTime;
    }

    public void setLessionStartTime(Date lessionStartTime) {
        this.lessionStartTime = lessionStartTime;
    }

    public Date getLessionEndTime() {
        return lessionEndTime;
    }

    public void setLessionEndTime(Date lessionEndTime) {
        this.lessionEndTime = lessionEndTime;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReplaceTeacherId() {
        return replaceTeacherId;
    }

    public void setReplaceTeacherId(String replaceTeacherId) {
        this.replaceTeacherId = replaceTeacherId;
    }

    public String getReplaceTeacherName() {
        return replaceTeacherName;
    }

    public void setReplaceTeacherName(String replaceTeacherName) {
        this.replaceTeacherName = replaceTeacherName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getActualCheckInTime() {
        return actualCheckInTime;
    }

    public void setActualCheckInTime(Date actualCheckInTime) {
        this.actualCheckInTime = actualCheckInTime;
    }

    public Date getActualCheckOutTime() {
        return actualCheckOutTime;
    }

    public void setActualCheckOutTime(Date actualCheckOutTime) {
        this.actualCheckOutTime = actualCheckOutTime;
    }
}
