package hy.ea.bo.audits;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 审核 - 阶段（比如校对）
 */
public class Phase implements BaseBean {
	private String phaseKey;
	private String phaseId;
	private Integer orderNum;//阶段序号
	private String phaseName;// 阶段名称
	private String auditType;//审核类型；单人审核，多人审核
	private String proID;//所属流程ID
	private Date createDate;// 阶段创建时间
	private String creatorID;// 阶段创建人ID
	private String companyID;// 阶段所属公司
	private String organizationID;//阶段所属部门
	private String terminal;//是否是最终审核阶段
	
	public String getPhaseKey() {
		return phaseKey;
	}
	public void setPhaseKey(String phaseKey) {
		this.phaseKey = phaseKey;
	}
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getProID() {
		return proID;
	}
	public void setProID(String proID) {
		this.proID = proID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
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
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
    
	
}
