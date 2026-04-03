package hy.ea.bo.invoicing.voucher;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 凭证父表单据
 * 陈婷
 */
public class VoucherBill implements BaseBean,ExcelBean{
	private String voucherKey;        		//凭证号主键
	private String voucherID;         		//凭证号业务主键
	private String voucherNum;				//凭证号
	private String companyID;          		//公司  外键
	private String companyName;				//公司名称
	private String organizationID;     		//单据所在部门  外键
	private String organizationName;		//部门名称
	private String appendixNum;				//附件数
	private String makePeople;				//制单人
	private Date makeDate;				//制单日期
	private String auditingPeo;				//审核人
	private Date auditingDate;			//审核日期
	private String tallyPeople;				//记账人
	private Date tallyDate;				//记账日期
	private String sum;						//金额
	private String status;					//状态 00 未审核 01已审核 10已记账
	private String remarks;					//备注
	
	/**
	 * 为记以前账日期
	 * @return
	 */
	private Date openDate;					//业务日期（指的是当前系统时间）
	public String getVoucherKey() {
		return voucherKey;
	}
	public void setVoucherKey(String voucherKey) {
		this.voucherKey = voucherKey;
	}
	public String getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(String voucherID) {
		this.voucherID = voucherID;
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
	public String getAppendixNum() {
		return appendixNum;
	}
	public void setAppendixNum(String appendixNum) {
		this.appendixNum = appendixNum;
	}
	public String getMakePeople() {
		return makePeople;
	}
	public void setMakePeople(String makePeople) {
		this.makePeople = makePeople;
	}
	
	public String getAuditingPeo() {
		return auditingPeo;
	}
	public void setAuditingPeo(String auditingPeo) {
		this.auditingPeo = auditingPeo;
	}
	
	public String getTallyPeople() {
		return tallyPeople;
	}
	public void setTallyPeople(String tallyPeople) {
		this.tallyPeople = tallyPeople;
	}
	
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Date getAuditingDate() {
		return auditingDate;
	}
	public void setAuditingDate(Date auditingDate) {
		this.auditingDate = auditingDate;
	}
	public Date getTallyDate() {
		return tallyDate;
	}
	public void setTallyDate(Date tallyDate) {
		this.tallyDate = tallyDate;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getVoucherNum() {
		return voucherNum;
	}
	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司","部门","凭证编号","金额","制作人","制作日期","审核人","审核日期","记账人","记账日期","状态","附件数" };
		return titles;
	}
	public static String[] columnHeading() {
		String[] titles = { "序号", "时间","公司","凭证编号","科目","摘要","借方金额","贷方金额"};
		return titles;
	}
	public static String[] columnHeading1() {
		String[] titles = { "序号", "公司","记账日期","凭证编号","科目名称","科目编号","摘要","借方金额","贷方金额","制单人"};
		return titles;
	}
	@SuppressWarnings("unused")
	@Override
	public String[] properties() {
		String[] titles={companyName,organizationName,voucherNum,sum,makePeople,makeDate.toString(),auditingPeo,auditingDate.toString(),tallyPeople,tallyDate.toString(),status.equals("00") ? "未审核" : (status.equals("01") ? "已审核" : "已记账"),appendixNum};
		return null;
	}
	
}
