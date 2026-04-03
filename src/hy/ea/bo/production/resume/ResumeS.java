package hy.ea.bo.production.resume;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * @author zzl 个人简历
 * */
public class ResumeS implements BaseBean {

	private String resumeKey;// 主键
	private String resumeID;// id
	private String staffID;// 人员id
	private String evaluate;// 自我评价
	private String privacy;// 是否隐私00可见 01不可见
	private String ompletionDegree;// 自我评价的完成度01
	private Date creationTime;// 创建时间
	private String isDefault;//01默认
	private String resumeName;//简历名称
	private String isNon;//简历是否发布  默认否00   是01
	private String state;//状态00 草稿未编辑状态；
	public String getResumeKey() {
		return resumeKey;
	}

	public void setResumeKey(String resumeKey) {
		this.resumeKey = resumeKey;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getOmpletionDegree() {
		return ompletionDegree;
	}

	public void setOmpletionDegree(String ompletionDegree) {
		this.ompletionDegree = ompletionDegree;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getIsNon() {
		return isNon;
	}

	public void setIsNon(String isNon) {
		this.isNon = isNon;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
