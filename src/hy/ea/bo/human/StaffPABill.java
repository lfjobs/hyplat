package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

public class StaffPABill implements java.io.Serializable,BaseBean{



	/**
	 * 
	 */
	private static final long serialVersionUID = 6370184809803226549L;
	private String spaKey;
	private String spaId;
	private String staffID;
	private String paBillID;
	public String getSpaKey() {
		return spaKey;
	}
	public void setSpaKey(String spaKey) {
		this.spaKey = spaKey;
	}
	public String getSpaId() {
		return spaId;
	}
	public void setSpaId(String spaId) {
		this.spaId = spaId;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getPaBillID() {
		return paBillID;
	}
	public void setPaBillID(String paBillID) {
		this.paBillID = paBillID;
	}
	
}