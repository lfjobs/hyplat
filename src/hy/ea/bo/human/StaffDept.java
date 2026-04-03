package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 员工部门
 * @author wsn
 *
 */
@SuppressWarnings("serial")
public class StaffDept implements BaseBean {
	/**
	 * 员工部门主键
	 */
	private String staffDeptKey;

	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 部门id
	 */
	private String deptId;

	/**
	 * 公司
	 * @return
	 */
	private String companyId;

	public String getStaffDeptKey() {
		return staffDeptKey;
	}

	public void setStaffDeptKey(String staffDeptKey) {
		this.staffDeptKey = staffDeptKey;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
