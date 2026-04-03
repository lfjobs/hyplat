package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * DtCrmCustomerCompetitive entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomerCompetitive implements java.io.Serializable,BaseBean {

	// Fields

	private String competitivekey;//数据库主键
	private DtCrmCustomer dtCrmCustomer;
	private String competitiveid;//业务主键
	private String ppkey;//产品ID
	private String pname;//产品名称
	private String goodscoding;//产品编号
	private String companyname;//公司
	private String address;//产地
	private String saletoarea;//主销地区
	private String specification;//规格
	private String model;//型号
	private BigDecimal price;//单价
	private BigDecimal cprice;//会员价
	private BigDecimal vprice;//VIP价
	
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
	public DtCrmCustomerCompetitive() {
	}

	/** minimal constructor */
	public DtCrmCustomerCompetitive(String competitiveid) {
		this.competitiveid = competitiveid;
	}

	/** full constructor */
	public DtCrmCustomerCompetitive(DtCrmCustomer dtCrmCustomer,
			String competitiveid, String companyname, String address,
			String saletoarea, String specification, String model,
			BigDecimal price, BigDecimal cprice, BigDecimal vprice) {
		this.dtCrmCustomer = dtCrmCustomer;
		this.competitiveid = competitiveid;		
		this.companyname = companyname;
		this.address = address;
		this.saletoarea = saletoarea;
		this.specification = specification;
		this.model = model;
		this.price = price;
		this.cprice = cprice;
		this.vprice = vprice;
	}

	// Property accessors

	public String getCompetitivekey() {
		return this.competitivekey;
	}

	public void setCompetitivekey(String competitivekey) {
		this.competitivekey = competitivekey;
	}

	public DtCrmCustomer getDtCrmCustomer() {
		return this.dtCrmCustomer;
	}

	public void setDtCrmCustomer(DtCrmCustomer dtCrmCustomer) {
		this.dtCrmCustomer = dtCrmCustomer;
	}

	public String getCompetitiveid() {
		return this.competitiveid;
	}

	public void setCompetitiveid(String competitiveid) {
		this.competitiveid = competitiveid;
	}	

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSaletoarea() {
		return this.saletoarea;
	}

	public void setSaletoarea(String saletoarea) {
		this.saletoarea = saletoarea;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCprice() {
		return this.cprice;
	}

	public void setCprice(BigDecimal cprice) {
		this.cprice = cprice;
	}

	public BigDecimal getVprice() {
		return this.vprice;
	}

	public void setVprice(BigDecimal vprice) {
		this.vprice = vprice;
	}

	public String getPpkey() {
		return ppkey;
	}

	public void setPpkey(String ppkey) {
		this.ppkey = ppkey;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getGoodscoding() {
		return goodscoding;
	}

	public void setGoodscoding(String goodscoding) {
		this.goodscoding = goodscoding;
	}

}