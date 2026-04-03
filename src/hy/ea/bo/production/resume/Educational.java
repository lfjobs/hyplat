package hy.ea.bo.production.resume;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * @author zzl 添加教育背景
 * */
public class Educational implements BaseBean {

	private String educationKey;// 主键
	private String educationID;// id
	private String staffID;// 人员id
	private String resumeID;// 个人简历id
	private String name;// 学校名称
	private Date admissionTime;// 入学时间
	private Date graduationTime;// 毕业时间
	private String professionalName;// 专业名称
	private String education;// 学历学位
	private String degree;//进度
	public String getEducationKey() {
		return educationKey;
	}

	public void setEducationKey(String educationKey) {
		this.educationKey = educationKey;
	}

	public String getEducationID() {
		return educationID;
	}

	public void setEducationID(String educationID) {
		this.educationID = educationID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(Date admissionTime) {
		this.admissionTime = admissionTime;
	}

	public Date getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(Date graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getProfessionalName() {
		return professionalName;
	}

	public void setProfessionalName(String professionalName) {
		this.professionalName = professionalName;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
