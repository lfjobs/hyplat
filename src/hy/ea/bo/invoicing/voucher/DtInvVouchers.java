package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * 凭证明细表
 * @author lou
 *
 */
public class DtInvVouchers implements BaseBean ,java.io.Serializable{

	// Fields

	private String voucherskey;
	private String vouchersid;
	private String voucherid;//凭证主表主键
	private String groupcsn;//集团组织机构id
	private BigDecimal vouchersnum;//序号
	private String subjectsid;//科目id
	private String subjectscode;//科目编号
	private String subjectsname;//科目名称
	private String casId;//单据id
	private String goodsid;//物品id
	private String goodsbillsid;//物品单据id
	private String reasonthing;//摘要
	private String currencyid;//币别id
	private String currencyname;//币别名称
	private String direction;//借贷方向（D / C ，D：表示借，C：表示贷）
	private BigDecimal standardmoney;//本位币金额
	private BigDecimal accountingmoney;//记账金额
	private BigDecimal exchangerate;//汇率
	private String mdannotation;//乘除注记（M / D ，M：表示乘，D：表示除）
	private BigDecimal quantity;//数量
	private String clientid;//（客户\供应商）ID
	private String ccompanyName;//供货商name
	private String organizationName;   //部门名称
	private String organizationId; 	   //部门ID
	
	private String reserved1;//预留一
	private String reserved2;//预留二
	


	// Constructors

	/** default constructor */
	public DtInvVouchers() {
	}

	/** full constructor */
	public DtInvVouchers(String vouchersid, String voucherid,
			String subjectsid, String casId, String goodsid,
			String subjectsname, String reasonthing, String goodsbillsid,
			String direction, String currencyname, String currencyid,
			BigDecimal vouchersnum, BigDecimal exchangerate, String mdannotation,
			BigDecimal standardmoney, BigDecimal accountingmoney, BigDecimal quantity,
			String clientid, String ccompanyName,String reserved1, String reserved2,
			String journalnum, String voucherdate, String groupcsn,
			String companyid,String subjectscode) {
		this.vouchersid = vouchersid;
		this.voucherid = voucherid;
		this.subjectsid = subjectsid;
		this.casId = casId;
		this.goodsid = goodsid;
		this.subjectsname = subjectsname;
		this.reasonthing = reasonthing;
		this.goodsbillsid = goodsbillsid;
		this.direction = direction;
		this.currencyname = currencyname;
		this.currencyid = currencyid;
		this.vouchersnum = vouchersnum;
		this.exchangerate = exchangerate;
		this.mdannotation = mdannotation;
		this.standardmoney = standardmoney;
		this.accountingmoney = accountingmoney;
		this.quantity = quantity;
		this.clientid = clientid;
		this.ccompanyName = ccompanyName;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.groupcsn = groupcsn;
		this.subjectscode=subjectscode;
	}

	// Property accessors

	public String getVoucherskey() {
		return this.voucherskey;
	}

	public void setVoucherskey(String voucherskey) {
		this.voucherskey = voucherskey;
	}

	public String getVouchersid() {
		return this.vouchersid;
	}

	public void setVouchersid(String vouchersid) {
		this.vouchersid = vouchersid;
	}

	public String getVoucherid() {
		return this.voucherid;
	}

	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}

	public String getSubjectsid() {
		return this.subjectsid;
	}

	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}

	public String getCasId() {
		return this.casId;
	}

	public void setCasId(String casId) {
		this.casId = casId;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getSubjectsname() {
		return this.subjectsname;
	}

	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}

	public String getReasonthing() {
		return this.reasonthing;
	}

	public void setReasonthing(String reasonthing) {
		this.reasonthing = reasonthing;
	}

	public String getGoodsbillsid() {
		return this.goodsbillsid;
	}

	public void setGoodsbillsid(String goodsbillsid) {
		this.goodsbillsid = goodsbillsid;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getCurrencyname() {
		return this.currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	public String getCurrencyid() {
		return this.currencyid;
	}

	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}

	public BigDecimal getVouchersnum() {
		return vouchersnum;
	}

	public void setVouchersnum(BigDecimal vouchersnum) {
		this.vouchersnum = vouchersnum;
	}

	public BigDecimal getExchangerate() {
		return this.exchangerate;
	}

	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}

	public String getMdannotation() {
		return this.mdannotation;
	}

	public void setMdannotation(String mdannotation) {
		this.mdannotation = mdannotation;
	}

	public BigDecimal getStandardmoney() {
		return this.standardmoney;
	}

	public void setStandardmoney(BigDecimal standardmoney) {
		this.standardmoney = standardmoney;
	}

	public BigDecimal getAccountingmoney() {
		return this.accountingmoney;
	}

	public void setAccountingmoney(BigDecimal accountingmoney) {
		this.accountingmoney = accountingmoney;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getClientid() {
		return this.clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	public String getCcompanyName() {
		return ccompanyName;
	}

	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
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

	public String getGroupcsn() {
		return this.groupcsn;
	}

	public void setGroupcsn(String groupcsn) {
		this.groupcsn = groupcsn;
	}

	public String getSubjectscode() {
		return subjectscode;
	}

	public void setSubjectscode(String subjectscode) {
		this.subjectscode = subjectscode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
}