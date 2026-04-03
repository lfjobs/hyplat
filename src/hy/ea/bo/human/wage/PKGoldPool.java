package hy.ea.bo.human.wage;

import hy.plat.bo.BaseBean;
/**
 * pkGoldPool
 * @author Administrator
 *
 */
public class PKGoldPool implements BaseBean,java.io.Serializable{
	
	private String pkgoldpoolKey;			//主键
	private String pkgoldpoolID;			//逻辑主键
	private String companyID;       //公司ID
	private String companyName;
	private String organizationID;		//部门
	private String organizationName;
	private String goldpool;              //PK金月总额
	private String goldpaypool;			//支出总额	
	private String goldbalpool;			//余额
	private String pkDate;		  //时间
	
	
	public String getPkgoldpoolKey() {
		return pkgoldpoolKey;
	}
	public void setPkgoldpoolKey(String pkgoldpoolKey) {
		this.pkgoldpoolKey = pkgoldpoolKey;
	}
	public String getPkgoldpoolID() {
		return pkgoldpoolID;
	}
	public void setPkgoldpoolID(String pkgoldpoolID) {
		this.pkgoldpoolID = pkgoldpoolID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getGoldpool() {
		return goldpool;
	}
	public void setGoldpool(String goldpool) {
		this.goldpool = goldpool;
	}
	public String getGoldpaypool() {
		return goldpaypool;
	}
	public void setGoldpaypool(String goldpaypool) {
		this.goldpaypool = goldpaypool;
	}
	public String getGoldbalpool() {
		return goldbalpool;
	}
	public void setGoldbalpool(String goldbalpool) {
		this.goldbalpool = goldbalpool;
	}
	public String getPkDate() {
		return pkDate;
	}
	public void setPkDate(String pkDate) {
		this.pkDate = pkDate;
	}
	
}