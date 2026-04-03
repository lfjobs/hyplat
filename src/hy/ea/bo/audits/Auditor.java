package hy.ea.bo.audits;

import hy.plat.bo.BaseBean;

/**
 * 审核 - 审核人
 */
public class Auditor implements BaseBean {
	private String auditKey;
	private String auditId;
	private String auditorName;// 审核人姓名记录形式
	//张晨(NO29236)[人事处-北京天太世统科技有限责任公司]
	
    private String auditorID;//审核人ID
    
    private String post;//审核人职务
    private String phaseId;//阶段ID
    private String proId;//流程ID
    private Integer orderNum;//审核人审核先审后审序号
	
    private String companyID;// 审核人公司ID
	private String organizationID;//审核人部门ID
    
	public String getAuditKey() {
		return auditKey;
	}
	public void setAuditKey(String auditKey) {
		this.auditKey = auditKey;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public String getAuditorID() {
		return auditorID;
	}
	public void setAuditorID(String auditorID) {
		this.auditorID = auditorID;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
    
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	

	
}
