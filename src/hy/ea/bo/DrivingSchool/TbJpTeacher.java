package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 教练员信息
 * Created by Administrator on 2017/8/15 0015.
 */
public class TbJpTeacher implements BaseBean,Serializable {
    private String teacherKey;
    private String teacherId;  //教练ID
    private String coachnum;  //教练员全国唯一编号
    private String companyId; //驾校ID
    private String name;  //姓名
    private String sex;  //性别
    private String idcard; //身份证号
    private String mobile;  //手机
    private String address; //地 址
    private String photo; //照片文件ID
    private String fingerprint; //指纹图片ID
    private String drilicence;  //驾驶证号
    private Date fstdrilicdate;  //驾驶证初领日期
    private String occupationno; //职业资格证号
    private String occupationlevel;  //职业资格等级
    private String dripermitted;  //准驾车型
    private String teachpermitted;  //准 教车型
    private String employstatus;  //供职状态
    private Date hiredate;  //入职时间
    private Date leavedate;  //离职时间
    private String syncType;  //同步类型
    private String syncStatus;  //同步状态
    private String delFlag;  //删除标志
    private String checkStatus;  //审核状态
    private String remark;  //备注
    private Date createdate;  //创建时间
    private Date updatedate;  //更新时间
    private String createperson;  //创建人
    private String updateperson;  //更新人
    private String faceId;  //人脸识别ID
    private String faceCode;  //人脸识别图像返回code
    private String syncXlycStatus;  //同步状态 0待同步1同步成功2同步失败
    private String staffId;     //人员的staffid
    private String colorCar;   //车辆颜色
    private String ranyou ;  //燃油
    private String zaike; //载客量
    private String changjia; //生产厂家
    private String xinghao ;  //厂牌型号
    private String subject;//准教科目


    public TbJpTeacher() {
    }

    public TbJpTeacher(String teacherKey, String teacherId, String coachnum, String companyId, String name, String sex, String idcard, String mobile, String address, String photo, String fingerprint, String drilicence, Date fstdrilicdate, String occupationno, String occupationlevel, String dripermitted, String teachpermitted, String employstatus, Date hiredate, Date leavedate, String syncType, String syncStatus, String delFlag, String checkStatus, String remark, Date createdate, Date updatedate, String createperson, String updateperson, String faceId, String faceCode, String syncXlycStatus, String staffId, String colorCar, String ranyou, String zaike, String changjia, String xinghao, String id) {
        this.teacherKey = teacherKey;
        this.teacherId = teacherId;
        this.coachnum = coachnum;
        this.companyId = companyId;
        this.name = name;
        this.sex = sex;
        this.idcard = idcard;
        this.mobile = mobile;
        this.address = address;
        this.photo = photo;
        this.fingerprint = fingerprint;
        this.drilicence = drilicence;
        this.fstdrilicdate = fstdrilicdate;
        this.occupationno = occupationno;
        this.occupationlevel = occupationlevel;
        this.dripermitted = dripermitted;
        this.teachpermitted = teachpermitted;
        this.employstatus = employstatus;
        this.hiredate = hiredate;
        this.leavedate = leavedate;
        this.syncType = syncType;
        this.syncStatus = syncStatus;
        this.delFlag = delFlag;
        this.checkStatus = checkStatus;
        this.remark = remark;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.createperson = createperson;
        this.updateperson = updateperson;
        this.faceId = faceId;
        this.faceCode = faceCode;
        this.syncXlycStatus = syncXlycStatus;
        this.staffId = staffId;
        this.colorCar = colorCar;
        this.ranyou = ranyou;
        this.zaike = zaike;
        this.changjia = changjia;
        this.xinghao = xinghao;
        this.id = id;
    }


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public String getRanyou() {
        return ranyou;
    }

    public void setRanyou(String ranyou) {
        this.ranyou = ranyou;
    }

    public String getZaike() {
        return zaike;
    }

    public void setZaike(String zaike) {
        this.zaike = zaike;
    }

    public String getChangjia() {
        return changjia;
    }

    public void setChangjia(String changjia) {
        this.changjia = changjia;
    }

    public String getXinghao() {
        return xinghao;
    }

    public void setXinghao(String xinghao) {
        this.xinghao = xinghao;
    }

    public String getTeacherKey() {
        return teacherKey;
    }

    public void setTeacherKey(String teacherKey) {
        this.teacherKey = teacherKey;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCoachnum() {
        return coachnum;
    }

    public void setCoachnum(String coachnum) {
        this.coachnum = coachnum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getDrilicence() {
        return drilicence;
    }

    public void setDrilicence(String drilicence) {
        this.drilicence = drilicence;
    }

    public Date getFstdrilicdate() {
        return fstdrilicdate;
    }

    public void setFstdrilicdate(Date fstdrilicdate) {
        this.fstdrilicdate = fstdrilicdate;
    }

    public String getOccupationno() {
        return occupationno;
    }

    public void setOccupationno(String occupationno) {
        this.occupationno = occupationno;
    }

    public String getOccupationlevel() {
        return occupationlevel;
    }

    public void setOccupationlevel(String occupationlevel) {
        this.occupationlevel = occupationlevel;
    }

    public String getDripermitted() {
        return dripermitted;
    }

    public void setDripermitted(String dripermitted) {
        this.dripermitted = dripermitted;
    }

    public String getTeachpermitted() {
        return teachpermitted;
    }

    public void setTeachpermitted(String teachpermitted) {
        this.teachpermitted = teachpermitted;
    }

    public String getEmploystatus() {
        return employstatus;
    }

    public void setEmploystatus(String employstatus) {
        this.employstatus = employstatus;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Date getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(Date leavedate) {
        this.leavedate = leavedate;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceCode() {
        return faceCode;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public String getSyncXlycStatus() {
        return syncXlycStatus;
    }

    public void setSyncXlycStatus(String syncXlycStatus) {
        this.syncXlycStatus = syncXlycStatus;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
