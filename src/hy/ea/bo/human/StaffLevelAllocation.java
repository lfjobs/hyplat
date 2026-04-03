package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 员工级别分配
 * @author wangshuangni
 *
 */
public class StaffLevelAllocation implements BaseBean{
	/**
	 * 主键
	 */
	private String levelAllocationKey;
	/**
	 * id
	 */
	private String levelAllocationId;
	/**
	 * 人员id
	 */
	private String staffId;
	/**
	 * 人员姓名
	 */
	private String staffName;
	/**
	 * 原级别id
	 */
	private String salaryLevelIdOld;
	/**
	 * 现级别id
	 */
	private String salaryLevelId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人id
	 */
	private String createStaffId;
	/**
	 * 创建人姓名
	 */
	private String createStaffName;
	/**
	 * 创建时间
	 */
    private Date createDate;
	/**
	 * 状态（0：待审核  1：已通过  2：未通过  3:已完成（级别生效） 9：删除）
	 */
	private String status;
	/**
	 * 审核流程id
	 */
	private String examineProcessId;

	/**
	 * 公司id
	 */
	private String companyId;

	public String getLevelAllocationKey() {
		return levelAllocationKey;
	}

	public void setLevelAllocationKey(String levelAllocationKey) {
		this.levelAllocationKey = levelAllocationKey;
	}

	public String getLevelAllocationId() {
		return levelAllocationId;
	}

	public void setLevelAllocationId(String levelAllocationId) {
		this.levelAllocationId = levelAllocationId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getSalaryLevelIdOld() {
		return salaryLevelIdOld;
	}

	public void setSalaryLevelIdOld(String salaryLevelIdOld) {
		this.salaryLevelIdOld = salaryLevelIdOld;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public String getCreateStaffName() {
		return createStaffName;
	}

	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
