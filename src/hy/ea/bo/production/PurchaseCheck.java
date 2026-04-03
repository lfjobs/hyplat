package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class PurchaseCheck implements BaseBean, ExcelBean ,java.io.Serializable{

	private String pckey; // 主键
	private String pcId;
	private String cashierBillsID;//单据ID
	private String journalNum;//凭证号
	private String billsType;//单据类型
	private Date applyTime;//提交申请时间
	private String applyor; // 申请人
	private String applyId; // 申请人id
	private String comapplyId; // 申请人公司id
	private String comapplyName; // 申请人公司
	private String orgapplyId; // 申请人部门id
	private String orgapplyName; // 申请人部门
	private Date dcheckTime; // 考核时间
	private String auditor; // 审核人
	private String auditorId; // 审核人id
	private String companyId; // 审核人公司id
	private String companyName; // 审核人公司
	private String organizationId; // 审核人部门id
	private String organizationName; // 审核人部门
	private String auditoroption; // 审核人意见
	private String status; // 考核状态 00待审核  01通过 02驳回
	private String fiveClear;//1人事2办公室3财务4生产5营销
	private String type;	// 生产类别   00：订单  01：计划

	public static String[] columnHeadings() {
		String[] titles = { "序号", "凭证号", "单据类型", "申请公司", "申请部门","申请人",
				"申请时间", "审核公司", "审核部门","审核人","审核时间","审核状态", "意见"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { journalNum, 
				billsType, comapplyName, orgapplyName, applyor,String.format("%1$tF",applyTime), companyName,
				organizationName,auditor, String.format("%1$tF",dcheckTime),  "00".equals(status)?"待审核":("01".equals(status)?"通过":"驳回") };
		return properties;
	}
	public String getPckey() {
		return pckey;
	}
	public void setPckey(String pckey) {
		this.pckey = pckey;
	}
	public String getPcId() {
		return pcId;
	}
	public void setPcId(String pcId) {
		this.pcId = pcId;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getAuditoroption() {
		return auditoroption;
	}
	public void setAuditoroption(String auditoroption) {
		this.auditoroption = auditoroption;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBillsType() {
		return billsType;
	}
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getDcheckTime() {
		return dcheckTime;
	}
	public void setDcheckTime(Date dcheckTime) {
		this.dcheckTime = dcheckTime;
	}
	public String getApplyor() {
		return applyor;
	}
	public void setApplyor(String applyor) {
		this.applyor = applyor;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getComapplyId() {
		return comapplyId;
	}
	public void setComapplyId(String comapplyId) {
		this.comapplyId = comapplyId;
	}
	public String getComapplyName() {
		return comapplyName;
	}
	public void setComapplyName(String comapplyName) {
		this.comapplyName = comapplyName;
	}
	public String getOrgapplyId() {
		return orgapplyId;
	}
	public void setOrgapplyId(String orgapplyId) {
		this.orgapplyId = orgapplyId;
	}
	public String getOrgapplyName() {
		return orgapplyName;
	}
	public void setOrgapplyName(String orgapplyName) {
		this.orgapplyName = orgapplyName;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	
    
}