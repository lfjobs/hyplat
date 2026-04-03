package hy.ea.bo.invoicing.voucher;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 资产负债损表科目明细表
 * @author lou
 *
 */

public class DtInvResdetails implements BaseBean {

	// Fields

	private String rdKey;
	private String rdId;
	private String reId;//印表结果id
	private String subjectsid;//科目id
	private String subjectsname;//科目名称
	private BigDecimal moneyD;//借方金额
	private BigDecimal moneyC;//贷方金额

	// Constructors

	/** default constructor */
	public DtInvResdetails() {
	}

	/** full constructor */
	public DtInvResdetails(String rdId, String reId, String subjectsid,
			String subjectsname, BigDecimal moneyD, BigDecimal moneyC) {
		this.rdId = rdId;
		this.reId = reId;
		this.subjectsid = subjectsid;
		this.subjectsname=subjectsname;
		this.moneyD = moneyD;
		this.moneyC = moneyC;
	}

	// Property accessors

	public String getRdKey() {
		return this.rdKey;
	}

	public void setRdKey(String rdKey) {
		this.rdKey = rdKey;
	}

	public String getRdId() {
		return this.rdId;
	}

	public void setRdId(String rdId) {
		this.rdId = rdId;
	}

	public String getReId() {
		return this.reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}
	
	public BigDecimal getMoneyD() {
		return this.moneyD;
	}

	public void setMoneyD(BigDecimal moneyD) {
		this.moneyD = moneyD;
	}

	public BigDecimal getMoneyC() {
		return this.moneyC;
	}

	public void setMoneyC(BigDecimal moneyC) {
		this.moneyC = moneyC;
	}

	public String getSubjectsid() {
		return subjectsid;
	}

	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}

	public String getSubjectsname() {
		return subjectsname;
	}

	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}
	
}