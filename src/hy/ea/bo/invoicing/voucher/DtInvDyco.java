package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * 年度结转表
 * @author lou
 *
 */
public class DtInvDyco implements BaseBean ,java.io.Serializable{

	// Fields

	private String dycokey;
	private String dycoid;
	private String groupcsn;//集团标识
	private String companyid;//公司id
	private String dycoYear;//年度(年月)
	private String subjectsid;//科目id
	private String subjectscode;//科目编号
	private String subjectsname;//科目名称
	private String currencyid;//币别id
	private String currencyname;//币别名称
	private String orgid;//部门id
	private String clientid;//（客户/供应商）id
	private String clientname;//（客户/供应商）name
	private String reserved1;//预留一
	private String reserved2;//预留二
	private BigDecimal sbdmoney;//本位币期初借方金额
	private BigDecimal sbcmoney;//本位币期初贷方金额
	private BigDecimal scdmoney;//本位币本期借方金额
	private BigDecimal sccmoney;//本位币本期贷方金额
	private BigDecimal scldmoney;//本位币期末借方金额
	private BigDecimal sclcmoney;//本位币期末贷方金额
	private BigDecimal abdmoney;//记账期初借方金额
	private BigDecimal abcmoney;//记账期初贷方金额
	private BigDecimal acdmoney;//记账本期借方金额
	private BigDecimal accmoney;//记账本期贷方金额
	private BigDecimal acldmoney;//记账期末借方金额
	private BigDecimal aclcmoney;//记账期末贷方金额

	// Constructors

	/** default constructor */
	public DtInvDyco() {
	}

	/** full constructor */
	public DtInvDyco(String dycoid, String groupcsn, String companyid,
			String dycoYear, String subjectsid,String subjectscode, String subjectsname,
			String currencyid, String currencyname, String orgid,
			String clientid,String clientname, String reserved1, String reserved2,
			BigDecimal sbdmoney, BigDecimal sbcmoney, BigDecimal scdmoney, BigDecimal sccmoney,
			BigDecimal scldmoney, BigDecimal sclcmoney, BigDecimal abdmoney,
			BigDecimal abcmoney, BigDecimal acdmoney, BigDecimal accmoney,
			BigDecimal acldmoney, BigDecimal aclcmoney) {
		this.dycoid = dycoid;
		this.groupcsn = groupcsn;
		this.companyid = companyid;
		this.dycoYear = dycoYear;
		this.subjectsid = subjectsid;
		this.subjectsname = subjectsname;
		this.currencyid = currencyid;
		this.currencyname = currencyname;
		this.orgid = orgid;
		this.clientid = clientid;
		this.clientname = clientname;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.sbdmoney = sbdmoney;
		this.sbcmoney = sbcmoney;
		this.scdmoney = scdmoney;
		this.sccmoney = sccmoney;
		this.scldmoney = scldmoney;
		this.sclcmoney = sclcmoney;
		this.abdmoney = abdmoney;
		this.abcmoney = abcmoney;
		this.acdmoney = acdmoney;
		this.accmoney = accmoney;
		this.acldmoney = acldmoney;
		this.aclcmoney = aclcmoney;
		this.subjectscode=subjectscode;
	}

	// Property accessors

	public String getDycokey() {
		return this.dycokey;
	}

	public void setDycokey(String dycokey) {
		this.dycokey = dycokey;
	}

	public String getDycoid() {
		return this.dycoid;
	}

	public void setDycoid(String dycoid) {
		this.dycoid = dycoid;
	}

	public String getGroupcsn() {
		return this.groupcsn;
	}

	public void setGroupcsn(String groupcsn) {
		this.groupcsn = groupcsn;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDycoYear() {
		return this.dycoYear;
	}

	public void setDycoYear(String dycoYear) {
		this.dycoYear = dycoYear;
	}

	public String getSubjectsid() {
		return this.subjectsid;
	}

	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}

	public String getSubjectsname() {
		return this.subjectsname;
	}

	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}

	public String getCurrencyid() {
		return this.currencyid;
	}

	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}

	public String getCurrencyname() {
		return this.currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getClientid() {
		return this.clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public BigDecimal getSbdmoney() {
		return this.sbdmoney;
	}

	public void setSbdmoney(BigDecimal sbdmoney) {
		this.sbdmoney = sbdmoney;
	}

	public BigDecimal getSbcmoney() {
		return this.sbcmoney;
	}

	public void setSbcmoney(BigDecimal sbcmoney) {
		this.sbcmoney = sbcmoney;
	}

	public BigDecimal getScdmoney() {
		return this.scdmoney;
	}

	public void setScdmoney(BigDecimal scdmoney) {
		this.scdmoney = scdmoney;
	}

	public BigDecimal getSccmoney() {
		return this.sccmoney;
	}

	public void setSccmoney(BigDecimal sccmoney) {
		this.sccmoney = sccmoney;
	}

	public BigDecimal getScldmoney() {
		return this.scldmoney;
	}

	public void setScldmoney(BigDecimal scldmoney) {
		this.scldmoney = scldmoney;
	}

	public BigDecimal getSclcmoney() {
		return this.sclcmoney;
	}

	public void setSclcmoney(BigDecimal sclcmoney) {
		this.sclcmoney = sclcmoney;
	}

	public BigDecimal getAbdmoney() {
		return this.abdmoney;
	}

	public void setAbdmoney(BigDecimal abdmoney) {
		this.abdmoney = abdmoney;
	}

	public BigDecimal getAbcmoney() {
		return this.abcmoney;
	}

	public void setAbcmoney(BigDecimal abcmoney) {
		this.abcmoney = abcmoney;
	}

	public BigDecimal getAcdmoney() {
		return this.acdmoney;
	}

	public void setAcdmoney(BigDecimal acdmoney) {
		this.acdmoney = acdmoney;
	}

	public BigDecimal getAccmoney() {
		return this.accmoney;
	}

	public void setAccmoney(BigDecimal accmoney) {
		this.accmoney = accmoney;
	}

	public BigDecimal getAcldmoney() {
		return this.acldmoney;
	}

	public void setAcldmoney(BigDecimal acldmoney) {
		this.acldmoney = acldmoney;
	}

	public BigDecimal getAclcmoney() {
		return this.aclcmoney;
	}

	public void setAclcmoney(BigDecimal aclcmoney) {
		this.aclcmoney = aclcmoney;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getSubjectscode() {
		return subjectscode;
	}

	public void setSubjectscode(String subjectscode) {
		this.subjectscode = subjectscode;
	}
	
}