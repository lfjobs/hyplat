package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class ComplaintSchool implements BaseBean, Serializable {
    private String clKey;
    private String clId;
    private String companyId;//所属驾校
    private String teacherId;//教练
    private String studentId;//学员
    private String content;//投诉内容
    private String staffId;//学员的staffId
    private String companyReply;//驾校回复
    private Date complaintDate;//投诉时间
    private String studentName;//学员姓名

    public String getClKey() {
        return clKey;
    }

    public void setClKey(String clKey) {
        this.clKey = clKey;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyReply() {
        return companyReply;
    }

    public void setCompanyReply(String companyReply) {
        this.companyReply = companyReply;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
