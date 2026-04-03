package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarutilitybill entity. @author MyEclipse Persistence Tools
 */

public class DtCarutilitybill implements BaseBean {

	// Fields
//报车管费用单
	private String carutilitybillkey;
	private String carutilitybillid;
	private String billsid;   //单据ID
	private String staffid;   //人员ID
	private String batchnumber;   //批次号
	private Date proposerdate;   //申请时间
	private String companyid;
	private String staffids;   //责任人ID
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	// Constructors

	/** default constructor */
	public DtCarutilitybill() {
	}

	/** full constructor */
	public DtCarutilitybill(String carutilitybillid, String billsid,
			String staffid, String batchnumber, Date proposerdate,
			String companyid, String staffids, String cname, Date ctime,
			String uname, Date utime) {
		this.carutilitybillid = carutilitybillid;
		this.billsid = billsid;
		this.staffid = staffid;
		this.batchnumber = batchnumber;
		this.proposerdate = proposerdate;
		this.companyid = companyid;
		this.staffids = staffids;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}

	// Property accessors

	public String getCarutilitybillkey() {
		return this.carutilitybillkey;
	}

	public void setCarutilitybillkey(String carutilitybillkey) {
		this.carutilitybillkey = carutilitybillkey;
	}

	public String getCarutilitybillid() {
		return this.carutilitybillid;
	}

	public void setCarutilitybillid(String carutilitybillid) {
		this.carutilitybillid = carutilitybillid;
	}

	public String getBillsid() {
		return this.billsid;
	}

	public void setBillsid(String billsid) {
		this.billsid = billsid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getBatchnumber() {
		return this.batchnumber;
	}

	public void setBatchnumber(String batchnumber) {
		this.batchnumber = batchnumber;
	}

	public Date getProposerdate() {
		return this.proposerdate;
	}

	public void setProposerdate(Date proposerdate) {
		this.proposerdate = proposerdate;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getStaffids() {
		return this.staffids;
	}

	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}