package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 员工角色
 * @author wsn
 *
 */
@SuppressWarnings("serial")
public class StaffRole implements BaseBean {
	/**
	 * 员工角色主键
	 */
	private String staffRoleKey;

	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 公司id
	 */
	private String companyId;

	public String getStaffRoleKey() {
		return staffRoleKey;
	}

	public void setStaffRoleKey(String staffRoleKey) {
		this.staffRoleKey = staffRoleKey;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
