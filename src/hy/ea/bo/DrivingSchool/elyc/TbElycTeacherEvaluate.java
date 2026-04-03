package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练评价
 */
public class TbElycTeacherEvaluate implements BaseBean,Serializable {
    private String etKey;
    private String etId;
    private String studentId;//学员ID
    private String teacherId;//教练ID
    private int totalScore;//平均星星数
    private int serviceScore;//服务态度星星数
    private int teachleveScore;//教学水平星星数
    private String  evaluateType;//评价类型 0 1 2;//好评 ，中评，差评，通过星星数计算出来的1-4差评；5-7中评；8-10，好评
    private String  evaluateContent;//评价文字内容
    private Date evaluateDate;//评价时间
    private String orderRecordId;//学员预约记录ID也就是教练课程ID


    public String getEtKey() {
        return etKey;
    }

    public void setEtKey(String etKey) {
        this.etKey = etKey;
    }

    public String getEtId() {
        return etId;
    }

    public void setEtId(String etId) {
        this.etId = etId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public int getTeachleveScore() {
        return teachleveScore;
    }

    public void setTeachleveScore(int teachleveScore) {
        this.teachleveScore = teachleveScore;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public Date getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getOrderRecordId() {
        return orderRecordId;
    }

    public void setOrderRecordId(String orderRecordId) {
        this.orderRecordId = orderRecordId;
    }
}
