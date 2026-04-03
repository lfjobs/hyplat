package hy.ea.bo.finance.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 咨询跟踪单打印：ServiceBills
 * 
 */
public class ServiceBillsPrintVO implements BaseBean{
	private String ServiceBillsPrintID;
	private String billsType; // 单据类型
	
	private String companyname; // 公司名称
	private String departmentname; // 部门名称
	private String staffname; // 负责人姓名
	private String recordcode; // 负责人档案号
	private Date cashierDate; // 单据日期
	private String companyBankNum; // 公司银行账号
	private String journalNum; // 凭证号
	
	private String ccompanyname; // 往来单位名称
	private String companyTel; // 公司电话
	private String cresponsible; // 负责人
	private String companyAddr; // 具体地址
	private String contactConnections; // 公司往来关系
	
	private String contactUserName; // 往来个人姓名
	private String tel; // 往来个人电话
	private String staffIdentityCard; // 往来个人身份证
	private String userQq; // 往来个人qq
	private String email; // 往来个人邮箱
	private String phone; // 个人往来关系
	private String userAddr; // 往来个人具体地址
	
	private String status; // 单据状态 '00'草稿 '04'已审核 '11'待营销审核 '12'待人事审核 '10' 驳回
	
	private List<BaseBean> goodsList; // 咨询跟踪单打印List


	public String getServiceBillsPrintID() {
		return ServiceBillsPrintID;
	}

	public void setServiceBillsPrintID(String serviceBillsPrintID) {
		ServiceBillsPrintID = serviceBillsPrintID;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getRecordcode() {
		return recordcode;
	}

	public void setRecordcode(String recordcode) {
		this.recordcode = recordcode;
	}

	public Date getCashierDate() {
		return cashierDate;
	}

	public void setCashierDate(Date cashierDate) {
		this.cashierDate = cashierDate;
	}

	public String getCompanyBankNum() {
		return companyBankNum;
	}

	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getCcompanyname() {
		return ccompanyname;
	}

	public void setCcompanyname(String ccompanyname) {
		this.ccompanyname = ccompanyname;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCresponsible() {
		return cresponsible;
	}

	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getContactConnections() {
		return contactConnections;
	}

	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BaseBean> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<BaseBean> goodsList) {
		this.goodsList = goodsList;
	}
}