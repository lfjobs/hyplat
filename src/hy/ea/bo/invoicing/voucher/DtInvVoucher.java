package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * 凭证主表
 * @author lou
 *
 */
public class DtInvVoucher implements BaseBean {

	// Fields

	private String voucherkey;  
	private String voucherid;
	private String companyid;//公司id
	private String orgId;//部门id
	private String journalnum;//凭证号码
	private String companyname;//公司名称
	private String orgName;//部门名称
	private String makepeople;//制单人
	private String makedate;//制单时间
	private BigDecimal vouchercategory;//审核注记（初始1,1: 未审核,2：退审核,3：已审核,4：退记帐,5：记帐）
	private String makepeopleid;//制单人id
	private String voucherdate;//凭证日期
	private String voucherorigin;//凭证来源
	private String VTId;//凭证类别id
	private String VTJc;//凭证类别简称
	private String VTName;//凭证类别名称
	
	private String auditingpeo;//审核人
	private String auditingdate;//审核时间
	private String tallypeople;//记账人
	private String tallydate;//记账时间
	private String auditingpeoid;//审核人id
	private String tallypeopleid;//记账人id
	private String singular;     //附单数
	
	

	// Constructors

	/** default constructor */
	public DtInvVoucher() {
	}

	/** full constructor */
	public DtInvVoucher(String voucherid, String companyid, String orgId,
			String journalnum, String companyname, String orgName,
			String makepeople, String makedate, String auditingpeo,
			String auditingdate, String tallypeople, String tallydate,
			BigDecimal vouchercategory, String makepeopleid, String auditingpeoid,
			String tallypeopleid, String voucherdate, String voucherorigin, String VTId) {
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
		this.VTId=VTId;
	}

	// Property accessors

	public String getVoucherkey() {
		return this.voucherkey;
	}

	public void setVoucherkey(String voucherkey) {
		this.voucherkey = voucherkey;
	}

	public String getVoucherid() {
		return this.voucherid;
	}

	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getJournalnum() {
		return this.journalnum;
	}

	public void setJournalnum(String journalnum) {
		this.journalnum = journalnum;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMakepeople() {
		return this.makepeople;
	}

	public void setMakepeople(String makepeople) {
		this.makepeople = makepeople;
	}

	public String getMakedate() {
		return this.makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getAuditingpeo() {
		return this.auditingpeo;
	}

	public void setAuditingpeo(String auditingpeo) {
		this.auditingpeo = auditingpeo;
	}

	public String getAuditingdate() {
		return this.auditingdate;
	}

	public void setAuditingdate(String auditingdate) {
		this.auditingdate = auditingdate;
	}

	public String getTallypeople() {
		return this.tallypeople;
	}

	public void setTallypeople(String tallypeople) {
		this.tallypeople = tallypeople;
	}

	public String getTallydate() {
		return this.tallydate;
	}

	public void setTallydate(String tallydate) {
		this.tallydate = tallydate;
	}

	public BigDecimal getVouchercategory() {
		return this.vouchercategory;
	}

	public void setVouchercategory(BigDecimal vouchercategory) {
		this.vouchercategory = vouchercategory;
	}

	public String getMakepeopleid() {
		return this.makepeopleid;
	}

	public void setMakepeopleid(String makepeopleid) {
		this.makepeopleid = makepeopleid;
	}

	public String getAuditingpeoid() {
		return this.auditingpeoid;
	}

	public void setAuditingpeoid(String auditingpeoid) {
		this.auditingpeoid = auditingpeoid;
	}

	public String getTallypeopleid() {
		return this.tallypeopleid;
	}

	public void setTallypeopleid(String tallypeopleid) {
		this.tallypeopleid = tallypeopleid;
	}

	public String getVoucherdate() {
		return this.voucherdate;
	}

	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}

	public String getVoucherorigin() {
		return this.voucherorigin;
	}

	public void setVoucherorigin(String voucherorigin) {
		this.voucherorigin = voucherorigin;
	}

	public String getVTId() {
		return VTId;
	}

	public void setVTId(String vTId) {
		VTId = vTId;
	}

	public String getVTJc() {
		return VTJc;
	}

	public void setVTJc(String vTJc) {
		VTJc = vTJc;
	}

	public String getVTName() {
		return VTName;
	}

	public void setVTName(String vTName) {
		VTName = vTName;
	}

	public String getSingular() {
		return singular;
	}

	public void setSingular(String singular) {
		this.singular = singular;
	}
	
}