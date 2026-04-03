package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 审核人员选择
 * @author wangshuangni
 *
 */
public class ExaminePersonData implements BaseBean{
	/**
	 * 主键
	 */
	private String examinePersonKey;
	/**
	 * id
	 */
	private String examinePersonId;
	/**
	 * 关联id
	 */
	private String processAssociationId;
	/**
	 * 关联流程id
	 */
	private String examineProcessId;
	/**
	 * 需审核人员id
	 */
	private String examineDataId;
	/**
	 * 需审核人员姓名
	 */
	private String examineDataName;

	/**
	 * 公司id
	 */
	private String companyId;

	public String getExaminePersonKey() {
		return examinePersonKey;
	}

	public void setExaminePersonKey(String examinePersonKey) {
		this.examinePersonKey = examinePersonKey;
	}

	public String getExaminePersonId() {
		return examinePersonId;
	}

	public void setExaminePersonId(String examinePersonId) {
		this.examinePersonId = examinePersonId;
	}

	public String getProcessAssociationId() {
		return processAssociationId;
	}

	public void setProcessAssociationId(String processAssociationId) {
		this.processAssociationId = processAssociationId;
	}

	public String getExamineDataId() {
		return examineDataId;
	}

	public void setExamineDataId(String examineDataId) {
		this.examineDataId = examineDataId;
	}

	public String getExamineDataName() {
		return examineDataName;
	}

	public void setExamineDataName(String examineDataName) {
		this.examineDataName = examineDataName;
	}

	public String getExamineProcessId() {
		return examineProcessId;
	}

	public void setExamineProcessId(String examineProcessId) {
		this.examineProcessId = examineProcessId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
