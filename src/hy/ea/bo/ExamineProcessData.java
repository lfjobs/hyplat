package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 审核流程数据
 * @author wangshuangni
 *
 */
public class ExamineProcessData implements BaseBean{
	/**
	 * 主键
	 */
	private String examineProcessKey;
	/**
	 * id
	 */
	private String examineProcessId;
	/**
	 * 流程类型
	 */
	private String processType;
	/**
	 * 关联id
	 */
	private String processAssociationId;
	/**
	 * 审核状态（1：通过 2：未通过）
	 */
	private String examineStatus;
	/**
	 * 审核人id
	 */
	private String examineStaffId;
	/**
	 * 审核人姓名
	 */
	private String examineStaffName;
	/**
	 * 审核时间
	 */
    private Date examineDate;
	/**
	 * 审核意见
	 */
	private String examineRemark;
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 *
	 * @return
	 */

	public String getExamineProcessKey() {
		return examineProcessKey;
	}

	public void setExamineProcessKey(String examineProcessKey) {
		this.examineProcessKey = examineProcessKey;
	}

	public String getExamineProcessId() {
		return examineProcessId;
	}

	public void setExamineProcessId(String examineProcessId) {
		this.examineProcessId = examineProcessId;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessAssociationId() {
		return processAssociationId;
	}

	public void setProcessAssociationId(String processAssociationId) {
		this.processAssociationId = processAssociationId;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getExamineStaffId() {
		return examineStaffId;
	}

	public void setExamineStaffId(String examineStaffId) {
		this.examineStaffId = examineStaffId;
	}

	public String getExamineStaffName() {
		return examineStaffName;
	}

	public void setExamineStaffName(String examineStaffName) {
		this.examineStaffName = examineStaffName;
	}

	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	public String getExamineRemark() {
		return examineRemark;
	}

	public void setExamineRemark(String examineRemark) {
		this.examineRemark = examineRemark;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
