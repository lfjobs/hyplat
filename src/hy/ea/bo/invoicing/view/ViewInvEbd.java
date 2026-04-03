package hy.ea.bo.invoicing.view;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 
 * @author mz
 */

public class ViewInvEbd implements BaseBean,ExcelBean{

	// Fields

	private String ebdkey;
	private String ebdid;
	private String productnum;
	private String productname;
	private String month;
	private String productunit;
	private String productstandard;
	private Long bunitprice;
	private Long bdquantity;
	private Long bdamount;
	private Long bwamount;
	private Long bmamount;
	private Long bsamount;
	private Long byamount;
	private Long tunitprice;
	private Long tdquantity;
	private Long tdamount;
	private Long twamount;
	private Long tmamount;
	private Long tsamount;
	private Long tyamount;
	private String year;
	private String companyname;
	private String companyid;
	private String organizationid;
	private String organizationname;
	private String budgetname;
	private String groupcompanysn;
	private String billstatus;
	private String billnum;
	private String staffname;
	private String staffid;
	private String sdate;
	private String edate;
	private String billstype;
	private Date billsdate;
	private String sztype;
	private String delstatus;

	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司名称", "黏贴单编号", "单据类型", "部门名称", "责任人",
				"制单日期", "产品编号", "产品名称", "单价", "月预算数量", "月预算金额", "季度预算金额",
				"年预算金额", "月调整数量", "月调整金额", "季度调整金额",
				"年调整金额","是否确认预算"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {
				companyname,
				billnum,
				billstype,
				organizationname,
				staffname,
				String.format("%1$tF", billsdate),

				productnum,
				productname,
				String.format("%d", bunitprice),
				String.format("%d", bdquantity),
				String.format("%d", bmamount),
				String.format("%d", bsamount),

				String.format("%d", byamount),
				String.format("%d", tdquantity),
				String.format("%d",tmamount),
				String.format("%d", tsamount),
				String.format("%d", tyamount),
				billstatus.equals("00") ? "草稿" : "已确认预算"};
		return properties;
	}

	public String getEbdkey() {
		return this.ebdkey;
	}

	public void setEbdkey(String ebdkey) {
		this.ebdkey = ebdkey;
	}

	public String getEbdid() {
		return this.ebdid;
	}

	public void setEbdid(String ebdid) {
		this.ebdid = ebdid;
	}

	public String getProductnum() {
		return this.productnum;
	}

	public void setProductnum(String productnum) {
		this.productnum = productnum;
	}

	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getProductunit() {
		return this.productunit;
	}

	public void setProductunit(String productunit) {
		this.productunit = productunit;
	}

	public String getProductstandard() {
		return this.productstandard;
	}

	public void setProductstandard(String productstandard) {
		this.productstandard = productstandard;
	}

	public Long getBunitprice() {
		return this.bunitprice;
	}

	public void setBunitprice(Long bunitprice) {
		this.bunitprice = bunitprice;
	}

	public Long getBdquantity() {
		return this.bdquantity;
	}

	public void setBdquantity(Long bdquantity) {
		this.bdquantity = bdquantity;
	}

	public Long getBdamount() {
		return this.bdamount;
	}

	public void setBdamount(Long bdamount) {
		this.bdamount = bdamount;
	}

	public Long getBwamount() {
		return this.bwamount;
	}

	public void setBwamount(Long bwamount) {
		this.bwamount = bwamount;
	}

	public Long getBmamount() {
		return this.bmamount;
	}

	public void setBmamount(Long bmamount) {
		this.bmamount = bmamount;
	}

	public Long getBsamount() {
		return this.bsamount;
	}

	public void setBsamount(Long bsamount) {
		this.bsamount = bsamount;
	}

	public Long getByamount() {
		return this.byamount;
	}

	public void setByamount(Long byamount) {
		this.byamount = byamount;
	}

	public Long getTunitprice() {
		return this.tunitprice;
	}

	public void setTunitprice(Long tunitprice) {
		this.tunitprice = tunitprice;
	}

	public Long getTdquantity() {
		return this.tdquantity;
	}

	public void setTdquantity(Long tdquantity) {
		this.tdquantity = tdquantity;
	}

	public Long getTdamount() {
		return this.tdamount;
	}

	public void setTdamount(Long tdamount) {
		this.tdamount = tdamount;
	}

	public Long getTwamount() {
		return this.twamount;
	}

	public void setTwamount(Long twamount) {
		this.twamount = twamount;
	}

	public Long getTmamount() {
		return this.tmamount;
	}

	public void setTmamount(Long tmamount) {
		this.tmamount = tmamount;
	}

	public Long getTsamount() {
		return this.tsamount;
	}

	public void setTsamount(Long tsamount) {
		this.tsamount = tsamount;
	}

	public Long getTyamount() {
		return this.tyamount;
	}

	public void setTyamount(Long tyamount) {
		this.tyamount = tyamount;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationname() {
		return this.organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getBudgetname() {
		return this.budgetname;
	}

	public void setBudgetname(String budgetname) {
		this.budgetname = budgetname;
	}

	public String getGroupcompanysn() {
		return this.groupcompanysn;
	}

	public void setGroupcompanysn(String groupcompanysn) {
		this.groupcompanysn = groupcompanysn;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getBillnum() {
		return billnum;
	}

	public void setBillnum(String billnum) {
		this.billnum = billnum;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getBillstype() {
		return billstype;
	}

	public void setBillstype(String billstype) {
		this.billstype = billstype;
	}

	public Date getBillsdate() {
		return billsdate;
	}

	public void setBillsdate(Date billsdate) {
		this.billsdate = billsdate;
	}

	public String getSztype() {
		return sztype;
	}

	public void setSztype(String sztype) {
		this.sztype = sztype;
	}

	public String getDelstatus() {
		return delstatus;
	}

	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	
    
}