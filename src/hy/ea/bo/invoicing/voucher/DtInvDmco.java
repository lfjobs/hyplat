package hy.ea.bo.invoicing.voucher;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 凭证月结明细资料表
 * @author lou
 *
 */
public class DtInvDmco implements BaseBean {

	// Fields

	private String dmcokey;
	private String dmcoid;
	private String groupcsn;//集团标识
	private String companyid;//公司id
	private String yearmonth;//年月
	private String subjectsid;//科目id
	private String subjectscode;//科目编号
	private String subjectsname;//科目名称
	private String currencyid;//币别id
	private String currencyname;//币别名称
	private String orgId;//部门id
	private String clientid;//（客户/供应商）id
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

	@Override
	public String toString() {
		return "DtInvDmco [dmcokey=" + dmcokey + ", dmcoid=" + dmcoid
				+ ", groupcsn=" + groupcsn + ", companyid=" + companyid
				+ ", yearmonth=" + yearmonth + ", subjectsid=" + subjectsid
				+ ", subjectsname=" + subjectsname + ", currencyid="
				+ currencyid + ", currencyname=" + currencyname + ", orgId="
				+ orgId + ", clientid=" + clientid + ", reserved1=" + reserved1
				+ ", reserved2=" + reserved2 + ", sbdmoney=" + sbdmoney
				+ ", sbcmoney=" + sbcmoney + ", scdmoney=" + scdmoney
				+ ", sccmoney=" + sccmoney + ", scldmoney=" + scldmoney
				+ ", sclcmoney=" + sclcmoney + ", abdmoney=" + abdmoney
				+ ", abcmoney=" + abcmoney + ", acdmoney=" + acdmoney
				+ ", accmoney=" + accmoney + ", acldmoney=" + acldmoney
				+ ", aclcmoney=" + aclcmoney + ", getDmcokey()=" + getDmcokey()
				+ ", getDmcoid()=" + getDmcoid() + ", getGroupcsn()="
				+ getGroupcsn() + ", getCompanyid()=" + getCompanyid()
				+ ", getYearmonth()=" + getYearmonth() + ", getSubjectsid()="
				+ getSubjectsid() + ", getSubjectsname()=" + getSubjectsname()
				+ ", getCurrencyid()=" + getCurrencyid()
				+ ", getCurrencyname()=" + getCurrencyname() + ", getOrgId()="
				+ getOrgId() + ", getClientid()=" + getClientid()
				+ ", getReserved1()=" + getReserved1() + ", getReserved2()="
				+ getReserved2() + ", getSbdmoney()=" + getSbdmoney()
				+ ", getSbcmoney()=" + getSbcmoney() + ", getScdmoney()="
				+ getScdmoney() + ", getSccmoney()=" + getSccmoney()
				+ ", getScldmoney()=" + getScldmoney() + ", getSclcmoney()="
				+ getSclcmoney() + ", getAbdmoney()=" + getAbdmoney()
				+ ", getAbcmoney()=" + getAbcmoney() + ", getAcdmoney()="
				+ getAcdmoney() + ", getAccmoney()=" + getAccmoney()
				+ ", getAcldmoney()=" + getAcldmoney() + ", getAclcmoney()="
				+ getAclcmoney() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	/** default constructor */
	public DtInvDmco() {
	}

	/** full constructor */
	public DtInvDmco(String dmcoid, String groupcsn, String companyid,
			String yearmonth, String subjectsid, String subjectsname,String subjectscode,
			String currencyid, String currencyname, String orgId,
			String clientid, String reserved1, String reserved2,
			BigDecimal sbdmoney, BigDecimal sbcmoney, BigDecimal scdmoney, BigDecimal sccmoney,
			BigDecimal scldmoney, BigDecimal sclcmoney, BigDecimal abdmoney,
			BigDecimal abcmoney, BigDecimal acdmoney, BigDecimal accmoney,
			BigDecimal acldmoney, BigDecimal aclcmoney) {
		this.dmcoid = dmcoid;
		this.groupcsn = groupcsn;
		this.companyid = companyid;
		this.yearmonth = yearmonth;
		this.subjectsid = subjectsid;
		this.subjectsname = subjectsname;
		this.currencyid = currencyid;
		this.currencyname = currencyname;
		this.orgId = orgId;
		this.clientid = clientid;
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

	public String getDmcokey() {
		return this.dmcokey;
	}

	public void setDmcokey(String dmcokey) {
		this.dmcokey = dmcokey;
	}

	public String getDmcoid() {
		return this.dmcoid;
	}

	public void setDmcoid(String dmcoid) {
		this.dmcoid = dmcoid;
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

	public String getYearmonth() {
		return this.yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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


	public String getSubjectscode() {
		return subjectscode;
	}


	public void setSubjectscode(String subjectscode) {
		this.subjectscode = subjectscode;
	}

}