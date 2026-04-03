package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 卫生管理
 */
public class COSHealthAdministration implements BaseBean, ExcelBean,java.io.Serializable {
	private String healthAdministrationKey;
	private String healthAdministrationID;
	private String companyID;
	private String organizationID;
	private String scope;// 管理范围
	private String scopeName;// 范围名称
	private String principal;// 卫生负责人
	private String examiner;// 卫生检查人
	private String assessment;// 卫生评估
	private String remark;// 备注

	public static String[] columnHeadings() {
		String[] titles = { "序号", "管理范围", "范围名称", "卫生负责人", "卫生检查人", "卫生评估",
				"备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {scope,scopeName,principal,examiner,assessment,remark};
		return properties;
	}

	public String getHealthAdministrationKey() {
		return healthAdministrationKey;
	}

	public void setHealthAdministrationKey(String healthAdministrationKey) {
		this.healthAdministrationKey = healthAdministrationKey;
	}

	public String getHealthAdministrationID() {
		return healthAdministrationID;
	}

	public void setHealthAdministrationID(String healthAdministrationID) {
		this.healthAdministrationID = healthAdministrationID;
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}




}
