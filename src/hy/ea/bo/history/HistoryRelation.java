package hy.ea.bo.history;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class HistoryRelation implements BaseBean {
	private String ID;
	private String Key;
	private String organizationID;
	private String companyID;
	private String cashierBillsID;//单据ID
	private String archivesNum;//公司归档号
	private Date archivesDate;//归档日期
	private String deparchivesNum;//部门归档号
	private Date deparchivesDate;//部门归档日期
	
	
	/**
	 * 部门归档号
	 * @return
	 */
	public String getDeparchivesNum() {
		return deparchivesNum;
	}
	/**
	 * 部门归档号
	 * @return
	 */
	public void setDeparchivesNum(String deparchivesNum) {
		this.deparchivesNum = deparchivesNum;
	}
	/**
	 * 部门归档日期
	 * @return
	 */
	public Date getDeparchivesDate() {
		return deparchivesDate;
	}
	/**
	 * 部门归档日期
	 * @return
	 */
	public void setDeparchivesDate(Date deparchivesDate) {
		this.deparchivesDate = deparchivesDate;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	/**
	 * 归档号
	 * @return
	 */
	public String getArchivesNum() {
		return archivesNum;
	}
	/**
	 * 归档号
	 */
	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}
	/**
	 * 归档日期
	 * @return
	 */
	public Date getArchivesDate() {
		return archivesDate;
	}
	/**
	 * 归档日期
	 */
	public void setArchivesDate(Date archivesDate) {
		this.archivesDate = archivesDate;
	}
	/**
	 * 单据ID
	 * @return
	 */
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	/**
	 * 单据ID
	 */
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
}
