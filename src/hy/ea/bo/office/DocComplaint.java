package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocComplaint implements BaseBean ,java.io.Serializable{

	private String key;
	private String Id;
	private String docId;
	private String code;//投诉编号
	private String complaintCompany;
	private String responsibilitor;
	private String telphone;
	private String userName;
	private Date complaintTime;//投诉时间
	private String docPath;//附件路径
	private String statusPic;//处理状态，图片路径
	private String status;//处理状态 reject:驳回；auditing:审批中，dealed已处理；pass:通过，waritaudit:待审批；waitdeal：待处理
	private String suggestion;//处理意见
	private String reply;//回复意见
	
	
	private String delstatus;//删除状态；00:正常,01;已删除
	
	private Date auditTime;//审批时间；用来显示发送至审批的时间；以及审批人来文显示时间；
	
	
	private String startDate;
	private String endDate;
	
	private String realName;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getComplaintTime() {
		return complaintTime;
	}

	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getComplaintCompany() {
		return complaintCompany;
	}

	public void setComplaintCompany(String complaintCompany) {
		this.complaintCompany = complaintCompany;
	}

	public String getResponsibilitor() {
		return responsibilitor;
	}

	public void setResponsibilitor(String responsibilitor) {
		this.responsibilitor = responsibilitor;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getStatusPic() {
		return statusPic;
	}

	public void setStatusPic(String statusPic) {
		this.statusPic = statusPic;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDelstatus() {
		return delstatus;
	}

	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}
