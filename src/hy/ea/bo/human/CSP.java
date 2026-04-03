package hy.ea.bo.human;

import hy.plat.bo.BaseBean;
/**
 * 员工级别确定
 * @author Administrator
 *
 */
public class CSP implements BaseBean,java.io.Serializable {
	private String cspKey;
	private String cspID;
	private String companyID;
	private String payScaleID;
	private String staffID;
	public String getCspKey() {
		return cspKey;
	}
	public void setCspKey(String cspKey) {
		this.cspKey = cspKey;
	}
	
	public String getCspID() {
		return cspID;
	}
	public void setCspID(String cspID) {
		this.cspID = cspID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getPayScaleID() {
		return payScaleID;
	}
	public void setPayScaleID(String payScaleID) {
		this.payScaleID = payScaleID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}


}
