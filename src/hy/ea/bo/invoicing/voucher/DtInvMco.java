package hy.ea.bo.invoicing.voucher;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 凭证月结主表
 * @author lou
 *
 */
public class DtInvMco implements BaseBean {

	// Fields

	private String mcokey;
	private String mcoid;
	private String mcodate;//月结日期
	private String companyid;//公司id
	private String groupcsn;//集团组织机构id
	private String mcostaffid;//月结人员id
	private BigDecimal glmoney;//损益金额
	private String yearmonth;//月年

	// Constructors

	/** default constructor */
	public DtInvMco() {
	}

	/** full constructor */
	public DtInvMco(String mcoid, String mcodate, String companyid,
			String groupcsn, String mcostaffid,
			BigDecimal glmoney, String yearmonth) {
		this.mcoid = mcoid;
		this.mcodate = mcodate;
		this.companyid = companyid;
		this.groupcsn = groupcsn;
		this.mcostaffid = mcostaffid;
		this.glmoney = glmoney;
		this.yearmonth = yearmonth;
	}

	// Property accessors

	public String getMcokey() {
		return this.mcokey;
	}

	public void setMcokey(String mcokey) {
		this.mcokey = mcokey;
	}

	public String getMcoid() {
		return this.mcoid;
	}

	public void setMcoid(String mcoid) {
		this.mcoid = mcoid;
	}

	public String getMcodate() {
		return this.mcodate;
	}

	public void setMcodate(String mcodate) {
		this.mcodate = mcodate;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getGroupcsn() {
		return this.groupcsn;
	}

	public void setGroupcsn(String groupcsn) {
		this.groupcsn = groupcsn;
	}

	public String getMcostaffid() {
		return this.mcostaffid;
	}

	public void setMcostaffid(String mcostaffid) {
		this.mcostaffid = mcostaffid;
	}

	public BigDecimal getGlmoney() {
		return this.glmoney;
	}

	public void setGlmoney(BigDecimal glmoney) {
		this.glmoney = glmoney;
	}

	public String getYearmonth() {
		return this.yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

}