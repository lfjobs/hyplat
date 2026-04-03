package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 往来单位关系
 * @author cxf
 */
public class ContactConnection implements BaseBean,ExcelBean{
	private String contactConnectionKey;
	private String contactConnectionID;
	private String companyID;
	private String ccompanyID;      
	private String contactConnections;  //往来单位关系
	
	@Override
	public String[] properties() {
		return null;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getContactConnectionKey() {
		return contactConnectionKey;
	}
	public void setContactConnectionKey(String contactConnectionKey) {
		this.contactConnectionKey = contactConnectionKey;
	}
	public String getContactConnectionID() {
		return contactConnectionID;
	}
	public void setContactConnectionID(String contactConnectionID) {
		this.contactConnectionID = contactConnectionID;
	}
	public String getContactConnections() {
		return contactConnections;
	}
	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}
	
	
}
