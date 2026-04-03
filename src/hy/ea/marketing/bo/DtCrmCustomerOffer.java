package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DtCrmCustomerOffer entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomerOffer implements java.io.Serializable,BaseBean {

	// Fields

	private String offerkey;//数据库主键
	private DtCrmCustomer dtCrmCustomer;//
	private String offerid;//业务主键
	private String ppkey;//报价产品ID
	private String pname;//报价产品名称
	private String goodscoding;//报价产品编号
	private String goodsversion;//报价产品版本
	private BigDecimal goodsprice;//报价产品价格
	private BigDecimal offerprice;//报价金额
	private BigDecimal discount;//折扣比率
	private BigDecimal gapprice;//差价
	private String staffid;//报价人ID
	private String staffname;//报价人姓名
	private Date offerdate;//报价日期
	private String offercustomerkey;//报价客户ID
	private String offercustomertitle;//客户职务
	private String istrain;//培训
	private String isassembly;//安装实施
	private String isremote;//远程
	private String isvisit;//上门服务
	private String isupdate;//产品升级
	private String iscall;//电话解决问题
	private String isother;//其他
	private String content;//内容1
	
	
	public static String[] columnHeadings(){
		String[] titles = {};
		return titles;
	}
	public String[] properties() {
		String[] properties = {};
		return properties;
	}

	// Constructors

	/** default constructor */
	public DtCrmCustomerOffer() {
	}

	/** minimal constructor */
	public DtCrmCustomerOffer(String offerid) {
		this.offerid = offerid;
	}

	/** full constructor */
	public DtCrmCustomerOffer(DtCrmCustomer dtCrmCustomer, String offerid,
			String ppkey, String goodscoding, String goodsversion,
			BigDecimal goodsprice, BigDecimal offerprice, BigDecimal discount,
			BigDecimal gapprice, String staffid, Date offerdate,
			String offercustomerkey, String offercustomertitle, String istrain,
			String isassembly, String isremote, String isvisit,
			String isupdate, String iscall, String isother, String content) {
		this.dtCrmCustomer = dtCrmCustomer;
		this.offerid = offerid;
		this.ppkey = ppkey;
		this.goodscoding = goodscoding;
		this.goodsversion = goodsversion;
		this.goodsprice = goodsprice;
		this.offerprice = offerprice;
		this.discount = discount;
		this.gapprice = gapprice;
		this.staffid = staffid;
		this.offerdate = offerdate;
		this.offercustomerkey = offercustomerkey;
		this.offercustomertitle = offercustomertitle;
		this.istrain = istrain;
		this.isassembly = isassembly;
		this.isremote = isremote;
		this.isvisit = isvisit;
		this.isupdate = isupdate;
		this.iscall = iscall;
		this.isother = isother;
		this.content = content;
	}

	// Property accessors

	public String getOfferkey() {
		return this.offerkey;
	}

	public void setOfferkey(String offerkey) {
		this.offerkey = offerkey;
	}

	public DtCrmCustomer getDtCrmCustomer() {
		return this.dtCrmCustomer;
	}

	public void setDtCrmCustomer(DtCrmCustomer dtCrmCustomer) {
		this.dtCrmCustomer = dtCrmCustomer;
	}

	public String getOfferid() {
		return this.offerid;
	}

	public void setOfferid(String offerid) {
		this.offerid = offerid;
	}

	public String getPpkey() {
		return this.ppkey;
	}

	public void setPpkey(String ppkey) {
		this.ppkey = ppkey;
	}

	public String getGoodscoding() {
		return this.goodscoding;
	}

	public void setGoodscoding(String goodscoding) {
		this.goodscoding = goodscoding;
	}

	public String getGoodsversion() {
		return this.goodsversion;
	}

	public void setGoodsversion(String goodsversion) {
		this.goodsversion = goodsversion;
	}

	public BigDecimal getGoodsprice() {
		return this.goodsprice;
	}

	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	public BigDecimal getOfferprice() {
		return this.offerprice;
	}

	public void setOfferprice(BigDecimal offerprice) {
		this.offerprice = offerprice;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getGapprice() {
		return this.gapprice;
	}

	public void setGapprice(BigDecimal gapprice) {
		this.gapprice = gapprice;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public Date getOfferdate() {
		return this.offerdate;
	}

	public void setOfferdate(Date offerdate) {
		this.offerdate = offerdate;
	}

	public String getOffercustomerkey() {
		return this.offercustomerkey;
	}

	public void setOffercustomerkey(String offercustomerkey) {
		this.offercustomerkey = offercustomerkey;
	}

	public String getOffercustomertitle() {
		return this.offercustomertitle;
	}

	public void setOffercustomertitle(String offercustomertitle) {
		this.offercustomertitle = offercustomertitle;
	}

	public String getIstrain() {
		return this.istrain;
	}

	public void setIstrain(String istrain) {
		this.istrain = istrain;
	}

	public String getIsassembly() {
		return this.isassembly;
	}

	public void setIsassembly(String isassembly) {
		this.isassembly = isassembly;
	}

	public String getIsremote() {
		return this.isremote;
	}

	public void setIsremote(String isremote) {
		this.isremote = isremote;
	}

	public String getIsvisit() {
		return this.isvisit;
	}

	public void setIsvisit(String isvisit) {
		this.isvisit = isvisit;
	}

	public String getIsupdate() {
		return this.isupdate;
	}

	public void setIsupdate(String isupdate) {
		this.isupdate = isupdate;
	}

	public String getIscall() {
		return this.iscall;
	}

	public void setIscall(String iscall) {
		this.iscall = iscall;
	}

	public String getIsother() {
		return this.isother;
	}

	public void setIsother(String isother) {
		this.isother = isother;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

}