package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class BalanceChange implements BaseBean{
	private String bcID;
	private String bcKey;
	private String cashierBillsID;
	private String goodsBillsID;
	private String pcompanyID;
	private String companyID;
	private String organizationID;
	private String staffID;
	private String staffCode;
	private String subjectsID;             // 科目管理ID
	private String subjectsName;           // 科目管理名称
	private String costType;               // 费用类别
	private Date startDate;				   // 款源日期
	private Date bcDate;
	private String gdirection;			   //物品方向
	private String gbalance;			   //物品余额
	private String direction;              //方向（人员）
	private String balance;                //余额（人员）
	private String direction1;             //方向（部门）
	private String balance1;               //余额（部门）
	private String direction2;             //方向（公司）
	private String balance2;               //余额（公司）
	private String direction3;             //方向（集团）
	private String balance3;               //余额（集团）
	public String getBcID() {
		return bcID;
	}
	public void setBcID(String bcID) {
		this.bcID = bcID;
	}
	public String getBcKey() {
		return bcKey;
	}
	public void setBcKey(String bcKey) {
		this.bcKey = bcKey;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public Date getBcDate() {
		return bcDate;
	}
	public void setBcDate(Date bcDate) {
		this.bcDate = bcDate;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDirection1() {
		return direction1;
	}
	public void setDirection1(String direction1) {
		this.direction1 = direction1;
	}
	public String getBalance1() {
		return balance1;
	}
	public void setBalance1(String balance1) {
		this.balance1 = balance1;
	}
	public String getDirection2() {
		return direction2;
	}
	public void setDirection2(String direction2) {
		this.direction2 = direction2;
	}
	public String getBalance2() {
		return balance2;
	}
	public void setBalance2(String balance2) {
		this.balance2 = balance2;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getPcompanyID() {
		return pcompanyID;
	}
	public void setPcompanyID(String pcompanyID) {
		this.pcompanyID = pcompanyID;
	}
	public String getDirection3() {
		return direction3;
	}
	public void setDirection3(String direction3) {
		this.direction3 = direction3;
	}
	public String getBalance3() {
		return balance3;
	}
	public void setBalance3(String balance3) {
		this.balance3 = balance3;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getGdirection() {
		return gdirection;
	}
	public void setGdirection(String gdirection) {
		this.gdirection = gdirection;
	}
	public String getGbalance() {
		return gbalance;
	}
	public void setGbalance(String gbalance) {
		this.gbalance = gbalance;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
