package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * company_staff 社会单位人员表
 * @author zgzg
 *
 */
public class CS implements BaseBean ,java.io.Serializable{
	
	/**
	 * 主键
	 */
	private String csKEY;
	/**
	 * 业务主键
	 */
	private String csID;
	/**
	 * 社会单位ID
	 */
	private String companyID;
	
	/**
	 * 社会人员ID
	 */
	private String staffID;

	public String getCsKEY() {
		return csKEY;
	}

	public void setCsKEY(String csKEY) {
		this.csKEY = csKEY;
	}

	public String getCsID() {
		return csID;
	}

	public void setCsID(String csID) {
		this.csID = csID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
}
