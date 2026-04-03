package hy.ea.bo.audits;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 审核-表单
 */
public class Form implements BaseBean {
	private String formKey;
	private String formId;
	private String formName;// 表单名称
	private String tag;// 表单标识符
	private String proID;//表单所属流程ID
	private String organizationID;//表单所属部门
	private String companyID;//所属公司
	private Date createDate;// 表单创建时间
	private String creatorID;// 表单创建人ID
	
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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
	public String getProID() {
		return proID;
	}
	public void setProID(String proID) {
		this.proID = proID;
	}
    

	
}
