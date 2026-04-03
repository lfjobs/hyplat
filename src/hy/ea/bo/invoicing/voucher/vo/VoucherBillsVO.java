package hy.ea.bo.invoicing.voucher.vo;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 凭证子表单据
 * @author lou
 */
public class VoucherBillsVO implements BaseBean{
	private String voucherskey;
	private String vouchersid;
	private String voucherid;//凭证主表主键
	private String subjectsid;//科目id
	private String casId;//单据id
	private String goodsid;//物品id
	private String subjectsname;//凭证名称
	private String reasonthing;//摘要
	private String goodsbillsid;//物品单据id
	private String direction;//借贷方向（D / C ，D：表示借，C：表示贷）
	private String currencyname;//币别名称
	private String currencyid;//币别id
	private String vouchersnum;//序号
	private BigDecimal exchangerate;//汇率
	private String mdannotation;//乘除注记（M / D ，M：表示乘，D：表示除）
	private BigDecimal standardmoney;//本位币金额
	private BigDecimal accountingmoney;//记账金额
	private BigDecimal quantity;//数量
	private String clientid;//（客户\供应商）ID
	private String reserved1;//预留一
	private String reserved2;//预留二
	private String groupcsn;//集团组织机构id
	
	
	public VoucherBillsVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VoucherBillsVO(String voucherskey, String vouchersid,
			String voucherid, String subjectsid, String casId, String goodsid,
			String subjectsname, String reasonthing, String goodsbillsid,
			String direction, String currencyname, String currencyid,
			String vouchersnum, BigDecimal exchangerate, String mdannotation,
			BigDecimal standardmoney, BigDecimal accountingmoney,
			BigDecimal quantity, String clientid, String reserved1,
			String reserved2, String groupcsn) {
		super();
		this.voucherskey = voucherskey;
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
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.groupcsn = groupcsn;
	}

	public String getVoucherskey() {
		return voucherskey;
	}
	public void setVoucherskey(String voucherskey) {
		this.voucherskey = voucherskey;
	}
	public String getVouchersid() {
		return vouchersid;
	}
	public void setVouchersid(String vouchersid) {
		this.vouchersid = vouchersid;
	}
	public String getVoucherid() {
		return voucherid;
	}
	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}
	public String getSubjectsid() {
		return subjectsid;
	}
	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}
	public String getCasId() {
		return casId;
	}
	public void setCasId(String casId) {
		this.casId = casId;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getSubjectsname() {
		return subjectsname;
	}
	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}
	public String getReasonthing() {
		return reasonthing;
	}
	public void setReasonthing(String reasonthing) {
		this.reasonthing = reasonthing;
	}
	public String getGoodsbillsid() {
		return goodsbillsid;
	}
	public void setGoodsbillsid(String goodsbillsid) {
		this.goodsbillsid = goodsbillsid;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCurrencyname() {
		return currencyname;
	}
	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}
	public String getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}
	public String getVouchersnum() {
		return vouchersnum;
	}
	public void setVouchersnum(String vouchersnum) {
		this.vouchersnum = vouchersnum;
	}
	public BigDecimal getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}
	public String getMdannotation() {
		return mdannotation;
	}
	public void setMdannotation(String mdannotation) {
		this.mdannotation = mdannotation;
	}
	public BigDecimal getStandardmoney() {
		return standardmoney;
	}
	public void setStandardmoney(BigDecimal standardmoney) {
		this.standardmoney = standardmoney;
	}
	public BigDecimal getAccountingmoney() {
		return accountingmoney;
	}
	public void setAccountingmoney(BigDecimal accountingmoney) {
		this.accountingmoney = accountingmoney;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getGroupcsn() {
		return groupcsn;
	}
	public void setGroupcsn(String groupcsn) {
		this.groupcsn = groupcsn;
	}
}
