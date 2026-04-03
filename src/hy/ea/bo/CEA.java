
package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * Company System Enterprise Application
 */
public class CEA implements BaseBean{
	private String ceaKey;
	private String ceaID;
	private String companyID;
	private String eaID;
	private String ceaType; 
	private Date   ceaTryDate;		
	private Date   ceaBuyDate;
	private Date   ceaInvalidDate;    
	private String ceaStatus;
	public String getCeaKey() {
		return ceaKey;
	}
	public void setCeaKey(String ceaKey) {
		this.ceaKey = ceaKey;
	}
	public String getCeaID() {
		return ceaID;
	}
	public void setCeaID(String ceaID) {
		this.ceaID = ceaID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getEaID() {
		return eaID;
	}
	public void setEaID(String eaID) {
		this.eaID = eaID;
	}
	public String getCeaType() {
		return ceaType;
	}
	public void setCeaType(String ceaType) {
		this.ceaType = ceaType;
	}
	public Date getCeaTryDate() {
		return ceaTryDate;
	}
	public void setCeaTryDate(Date ceaTryDate) {
		this.ceaTryDate = ceaTryDate;
	}
	public Date getCeaBuyDate() {
		return ceaBuyDate;
	}
	public void setCeaBuyDate(Date ceaBuyDate) {
		this.ceaBuyDate = ceaBuyDate;
	}
	public Date getCeaInvalidDate() {
		return ceaInvalidDate;
	}
	public void setCeaInvalidDate(Date ceaInvalidDate) {
		this.ceaInvalidDate = ceaInvalidDate;
	}
	public String getCeaStatus() {
		return ceaStatus;
	}
	public void setCeaStatus(String ceaStatus) {
		this.ceaStatus = ceaStatus;
	}
}
