package hy.ea.bo.production.recruit;



import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 
 * 人才简历库即用户投过的简历
 * @author Administrator
 *
 */
public class TalentPool implements BaseBean{

	private String tpkey; // 主键
	private String tpId;
	private String staffID;//人员ID  谁投递的，谁抢的
	private String riId;//招聘信息ID   投递的招聘信息
	private String resID;//用哪个简历投的
	private Date postDate;//投递时间
	private String state;//状态  00:未发送面试通知 /不合适   01：已发送面试通知 /邀请面试 02:被查看 03：有意向
	private String operateID;//不合适/面试邀请人
	private String resumeID;//抢的简历ID
	private String type;//00 投递 01 邀请
	private String companyId;//公司ID  投向哪个公司，哪个公司邀请
	//面试通知信息
	private String content;	//内容
	private Date interviewDate;//面试时间
	private String interviewPlace;//面试地点
	private String contactor;//联系人
	private String contactTel;//联系人电话
	private String remark;//备注
	private String noticeDate;//通知时间
	private String isSMS;//是否短信通知 00 否  01 是

	
   
	public String getTpkey() {
		return tpkey;
	}
	public void setTpkey(String tpkey) {
		this.tpkey = tpkey;
	}
	public String getTpId() {
		return tpId;
	}
	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getRiId() {
		return riId;
	}
	public void setRiId(String riId) {
		this.riId = riId;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResumeID() {
		return resumeID;
	}
	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewPlace() {
		return interviewPlace;
	}
	public void setInterviewPlace(String interviewPlace) {
		this.interviewPlace = interviewPlace;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getIsSMS() {
		return isSMS;
	}
	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResID() {
		return resID;
	}
	public void setResID(String resID) {
		this.resID = resID;
	}

	public String getOperateID() {
		return operateID;
	}

	public void setOperateID(String operateID) {
		this.operateID = operateID;
	}
}