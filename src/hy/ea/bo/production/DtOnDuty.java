package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtOnDuty entity. @author MyEclipse Persistence Tools
 */

public class DtOnDuty implements BaseBean,ExcelBean{

	// Fields

	private String dutykey;
	private String dutyid;
	private String staffid;
	private String staffname;
	private String companyid;
	private String companyname;
	private String orgid;
	private String orgname;
	private String productid;
	private String productcode;
	private String productname;
	private Date startdate;
	private Date enddate;
	private Date allotdate;
	private String duty;
	private String dutyType;
	private String comments;
	private String type;
	private String category;
	private String fiveClear;
	// Constructors
	public static String[] columnHeadings() {
		String[] titles = { "序号","项目产品编号","项目产品名称","开始时间","结束时间","职责","班值类型","分配时间","分配责任人","备注"};
		return titles;
	}
	
    @Override
    public String[] properties() {
	
	 String[] properties = {productcode,productname,String.format("%1$tF",startdate),String.format("%1$tF",enddate),duty,dutyType,String.format("%1$tF", allotdate),staffname,comments};
		return properties;
    }
    
	/** default constructor */
	public DtOnDuty() {
	}
	
	/** minimal constructor */
	public DtOnDuty(String dutyid, String staffid, String companyid,
			String productid) {
		this.dutyid = dutyid;
		this.staffid = staffid;
		this.companyid = companyid;
		this.productid = productid;
	}

	/** full constructor */
	public DtOnDuty(String dutyid, String staffid, String staffname,
			String companyid, String companyname, String orgid, String orgname,
			String productid, String productcode, String productname,
			Date startdate, Date enddate, Date allotdate, String duty,
			String dutyType, String comments) {
		this.dutyid = dutyid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.companyid = companyid;
		this.companyname = companyname;
		this.orgid = orgid;
		this.orgname = orgname;
		this.productid = productid;
		this.productcode = productcode;
		this.productname = productname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.allotdate = allotdate;
		this.duty = duty;
		this.dutyType = dutyType;
		this.comments = comments;
	}

	// Property accessors

	public String getDutykey() {
		return this.dutykey;
	}

	public void setDutykey(String dutykey) {
		this.dutykey = dutykey;
	}

	public String getDutyid() {
		return this.dutyid;
	}

	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getAllotdate() {
		return this.allotdate;
	}

	public void setAllotdate(Date allotdate) {
		this.allotdate = allotdate;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDutyType() {
		return this.dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

}