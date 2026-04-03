package hy.ea.office.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 工资级别
 */
public class HrSalaryLevel implements BaseBean, Serializable {
	private static final long serialVersionUID = 4147132325751072820L;
	/**
	 * 主键
	 */
	private String salaryLevelKey;

	/**
	 * 级别id
	 */
	private String salaryLevelId;

	/**
	 * 序号
	 */
	private String salaryLevelSerial;

	/**
	 * 级别编码
	 */
	private String salaryLevelNum;

	/**
	 * 公司id
	 */
	private String companyId;

	/**
	 * 设置人id
	 */
	private String createStaffId;

	/**
	 * 设置时间
	 */
	private Date createDate;

	public HrSalaryLevel() {
	}

	public HrSalaryLevel(String companyId, String salaryLevelKey, String salaryLevelNum, String salaryLevelSerial) {
		this.companyId = companyId;
		this.salaryLevelKey = salaryLevelKey;
		this.salaryLevelNum = salaryLevelNum;
		this.salaryLevelSerial = salaryLevelSerial;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSalaryLevelKey() {
		return salaryLevelKey;
	}

	public void setSalaryLevelKey(String salaryLevelKey) {
		this.salaryLevelKey = salaryLevelKey;
	}

	public String getSalaryLevelNum() {
		return salaryLevelNum;
	}

	public void setSalaryLevelNum(String salaryLevelNum) {
		this.salaryLevelNum = salaryLevelNum;
	}

	public String getSalaryLevelSerial() {
		return salaryLevelSerial;
	}

	public void setSalaryLevelSerial(String salaryLevelSerial) {
		this.salaryLevelSerial = salaryLevelSerial;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
