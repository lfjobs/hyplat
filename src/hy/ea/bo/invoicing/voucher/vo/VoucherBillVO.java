package hy.ea.bo.invoicing.voucher.vo;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 凭证主表视图（用于传参）
 * @author lou
 *
 */
public class VoucherBillVO implements BaseBean {
	
	private String voucherkey;  
	private String voucherid;
	private String companyid;//公司id
	private String orgId;//部门id
	private String journalnum;//凭证号码
	private String companyname;//公司名称
	private String orgName;//部门名称
	private String makepeople;//制单人
	private String makedate;//制单时间
	private String auditingpeo;//审核人
	private String auditingdate;//审核时间
	private String tallypeople;//记账人
	private String tallydate;//记账时间
	private BigDecimal vouchercategory;//审核注记
	private String makepeopleid;//制单人id
	private String auditingpeoid;//审核人id
	private String tallypeopleid;//记账人id
	private String voucherdate;//凭证日期
	private String voucherorigin;//凭证来源
	
	
	
	public VoucherBillVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VoucherBillVO(String voucherkey, String voucherid, String companyid,
			String orgId, String journalnum, String companyname,
			String orgName, String makepeople, String makedate,
			String auditingpeo, String auditingdate, String tallypeople,
			String tallydate, BigDecimal vouchercategory, String makepeopleid,
			String auditingpeoid, String tallypeopleid, String voucherdate,
			String voucherorigin) {
		super();
		this.voucherkey = voucherkey;
		this.voucherid = voucherid;
		this.companyid = companyid;
		this.orgId = orgId;
		this.journalnum = journalnum;
		this.companyname = companyname;
		this.orgName = orgName;
		this.makepeople = makepeople;
		this.makedate = makedate;
		this.auditingpeo = auditingpeo;
		this.auditingdate = auditingdate;
		this.tallypeople = tallypeople;
		this.tallydate = tallydate;
		this.vouchercategory = vouchercategory;
		this.makepeopleid = makepeopleid;
		this.auditingpeoid = auditingpeoid;
		this.tallypeopleid = tallypeopleid;
		this.voucherdate = voucherdate;
		this.voucherorigin = voucherorigin;
	}
	public String getVoucherkey() {
		return voucherkey;
	}
	public void setVoucherkey(String voucherkey) {
		this.voucherkey = voucherkey;
	}
	public String getVoucherid() {
		return voucherid;
	}
	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getJournalnum() {
		return journalnum;
	}
	public void setJournalnum(String journalnum) {
		this.journalnum = journalnum;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMakepeople() {
		return makepeople;
	}
	public void setMakepeople(String makepeople) {
		this.makepeople = makepeople;
	}
	public String getMakedate() {
		return makedate;
	}
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}
	public String getAuditingpeo() {
		return auditingpeo;
	}
	public void setAuditingpeo(String auditingpeo) {
		this.auditingpeo = auditingpeo;
	}
	public String getAuditingdate() {
		return auditingdate;
	}
	public void setAuditingdate(String auditingdate) {
		this.auditingdate = auditingdate;
	}
	public String getTallypeople() {
		return tallypeople;
	}
	public void setTallypeople(String tallypeople) {
		this.tallypeople = tallypeople;
	}
	public String getTallydate() {
		return tallydate;
	}
	public void setTallydate(String tallydate) {
		this.tallydate = tallydate;
	}
	public BigDecimal getVouchercategory() {
		return vouchercategory;
	}
	public void setVouchercategory(BigDecimal vouchercategory) {
		this.vouchercategory = vouchercategory;
	}
	public String getMakepeopleid() {
		return makepeopleid;
	}
	public void setMakepeopleid(String makepeopleid) {
		this.makepeopleid = makepeopleid;
	}
	public String getAuditingpeoid() {
		return auditingpeoid;
	}
	public void setAuditingpeoid(String auditingpeoid) {
		this.auditingpeoid = auditingpeoid;
	}
	public String getTallypeopleid() {
		return tallypeopleid;
	}
	public void setTallypeopleid(String tallypeopleid) {
		this.tallypeopleid = tallypeopleid;
	}
	public String getVoucherdate() {
		return voucherdate;
	}
	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}
	public String getVoucherorigin() {
		return voucherorigin;
	}
	public void setVoucherorigin(String voucherorigin) {
		this.voucherorigin = voucherorigin;
	}
}
