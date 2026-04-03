package hy.ea.bo.office;
import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 员工会议参加人员
 * @author Administrator
 *
 */
public class SMParticipant implements BaseBean{
	private String partiID;
	private String partiKey;
	private String meetingID;//会议ID
	private String companyID;//参会人公司
	private String organizationID;//参会人部门
	private String organizationName;//部门名称
    private String post;//职务
	private String staffID;//参会人ID
	private String staffName;//参会人姓名
	private String isAttend;//是否出席会议
	private String unAttendCause;//未出席原因
	private String isLate;//是否迟到
	private String lateCause;//迟到原因
	private String isSpeak;//是否发言
	private String speakContent;//发言内容
	private String phoneNumber;
	private String email;
	private String qq;
	private Date recordDate;//录入时间
	public String getPartiID() {
		return partiID;
	}
	public void setPartiID(String partiID) {
		this.partiID = partiID;
	}
	public String getPartiKey() {
		return partiKey;
	}
	public void setPartiKey(String partiKey) {
		this.partiKey = partiKey;
	}
	public String getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getIsAttend() {
		return isAttend;
	}
	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
	}
	public String getIsLate() {
		return isLate;
	}
	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}
	public String getLateCause() {
		return lateCause;
	}
	public void setLateCause(String lateCause) {
		this.lateCause = lateCause;
	}
	public String getIsSpeak() {
		return isSpeak;
	}
	public void setIsSpeak(String isSpeak) {
		this.isSpeak = isSpeak;
	}
	public String getSpeakContent() {
		return speakContent;
	}
	public void setSpeakContent(String speakContent) {
		this.speakContent = speakContent;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getUnAttendCause() {
		return unAttendCause;
	}
	public void setUnAttendCause(String unAttendCause) {
		this.unAttendCause = unAttendCause;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	
	  

	
	
	
}
