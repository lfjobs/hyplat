package hy.ea.bo.audits;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 审核 - 流程表
 */
public class Procedure implements BaseBean ,java.io.Serializable{
	private String proKey;
	private String proId;
	private String proName;// 流程名称
	private Date startDate;// 该流程的使用的开始时间
	private Date endDate;// 截止时间（也就是有效期）
	private String status;// 00 使用中；01禁用；
	private Date createDate;// 流程创建时间
	private String creatorID;// 流程创建人ID
	private String companyID;// 流程所属公司
	private String organizationID;//流程所属部门
	private String forms;//审核的表单

   
	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}


	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}
    
}
