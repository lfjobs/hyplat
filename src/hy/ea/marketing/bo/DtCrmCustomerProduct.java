package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * DtCrmCustomerProduct entity. @author MyEclipse Persistence Tools
 */

public class DtCrmCustomerProduct implements java.io.Serializable,BaseBean {

	// Fields

	private String productkey;//数据库主键
	private DtCrmCustomer dtCrmCustomer;
	private String productid;//业务主键
	private String ppkey;//意向产品ID
	private String pname;//意向产品名称
	private String goodscoding;//意向产品编号
	private String goodsversion;//意向产品版本
	private BigDecimal goodsprice;//意向产品价格
	private String goodsdesc;//产品描述
	private String requirement;//功能性需求
	private String nrequirement;//非功能性需求
	private String ppkeyone;//竞品一
	private String ppkeytwo;//竞品二
	private String ppkeythree;//竞品三
	private String competitive;//竞品功能点
	
	public static String[] columnHeadings(){
		String[] titles = {"活动日期","主题","客户阶段"};
		return titles;
	}
	public String[] properties() {
		String[] properties = {"activitydate"};
		return properties;
	}

	// Constructors

	/** default constructor */
	public DtCrmCustomerProduct() {
	}

	/** minimal constructor */
	public DtCrmCustomerProduct(String productid) {
		this.productid = productid;
	}

	/** full constructor */
	public DtCrmCustomerProduct(DtCrmCustomer dtCrmCustomer, String productid,
			String ppkey, String goodscoding, String goodsversion,
			BigDecimal goodsprice, String goodsdesc, String requirement,
			String nrequirement, String ppkeyone, String ppkeytwo,
			String ppkeythree, String competitive) {
		this.dtCrmCustomer = dtCrmCustomer;
		this.productid = productid;
		this.ppkey = ppkey;
		this.goodscoding = goodscoding;
		this.goodsversion = goodsversion;
		this.goodsprice = goodsprice;
		this.goodsdesc = goodsdesc;
		this.requirement = requirement;
		this.nrequirement = nrequirement;
		this.ppkeyone = ppkeyone;
		this.ppkeytwo = ppkeytwo;
		this.ppkeythree = ppkeythree;
		this.competitive = competitive;
	}

	// Property accessors

	public String getProductkey() {
		return this.productkey;
	}

	public void setProductkey(String productkey) {
		this.productkey = productkey;
	}

	public DtCrmCustomer getDtCrmCustomer() {
		return this.dtCrmCustomer;
	}

	public void setDtCrmCustomer(DtCrmCustomer dtCrmCustomer) {
		this.dtCrmCustomer = dtCrmCustomer;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
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

	public String getGoodsdesc() {
		return this.goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
	}

	public String getRequirement() {
		return this.requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getNrequirement() {
		return this.nrequirement;
	}

	public void setNrequirement(String nrequirement) {
		this.nrequirement = nrequirement;
	}

	public String getPpkeyone() {
		return this.ppkeyone;
	}

	public void setPpkeyone(String ppkeyone) {
		this.ppkeyone = ppkeyone;
	}

	public String getPpkeytwo() {
		return this.ppkeytwo;
	}

	public void setPpkeytwo(String ppkeytwo) {
		this.ppkeytwo = ppkeytwo;
	}

	public String getPpkeythree() {
		return this.ppkeythree;
	}

	public void setPpkeythree(String ppkeythree) {
		this.ppkeythree = ppkeythree;
	}

	public String getCompetitive() {
		return this.competitive;
	}

	public void setCompetitive(String competitive) {
		this.competitive = competitive;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}