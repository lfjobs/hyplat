package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * 资产负债损益表印表结果表
 * @author lou
 *
 */
public class DtInvResult implements BaseBean {

	// Fields

	private String reKey;
	private String reId;
	private String startdate;//起始时间
	private String enddate;//终止时间
	private String tabType;//报表类别（A:资产负债表，B：损益表）
	private String comId;//公司id
	private String tabSymbol;//报表代号
	private BigDecimal reMoney;//金额
	private BigDecimal rePet;//百分比
	private String reFm;//项次 

	// Constructors

	/** default constructor */
	public DtInvResult() {
	}

	/** full constructor */
	public DtInvResult(String reId, String startdate, String enddate,
			String tabType, String comId, String tabSymbol, BigDecimal reMoney,
			BigDecimal rePet, String reFm) {
		this.reId = reId;
		this.startdate = startdate;
		this.enddate = enddate;
		this.tabType = tabType;
		this.comId = comId;
		this.tabSymbol = tabSymbol;
		this.reMoney = reMoney;
		this.rePet = rePet;
		this.reFm = reFm;
	}

	// Property accessors

	public String getReKey() {
		return this.reKey;
	}

	public void setReKey(String reKey) {
		this.reKey = reKey;
	}

	public String getReId() {
		return this.reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getTabType() {
		return this.tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getTabSymbol() {
		return this.tabSymbol;
	}

	public void setTabSymbol(String tabSymbol) {
		this.tabSymbol = tabSymbol;
	}

	public BigDecimal getReMoney() {
		return this.reMoney;
	}

	public void setReMoney(BigDecimal reMoney) {
		this.reMoney = reMoney;
	}

	public BigDecimal getRePet() {
		return this.rePet;
	}

	public void setRePet(BigDecimal rePet) {
		this.rePet = rePet;
	}

	public String getReFm() {
		return this.reFm;
	}

	public void setReFm(String reFm) {
		this.reFm = reFm;
	}

}