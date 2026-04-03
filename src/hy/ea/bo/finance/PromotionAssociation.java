package hy.ea.bo.finance;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 促销品，订单关联
 * 
 */
public class PromotionAssociation implements BaseBean {
	private String paKey;
	private String paId;
	private String cashierBillsID;//主产品订单id
	private String ptcashierBillsID;//促销品id
	private String ppId;//促销产品Id
	private String ppName;//促销产品名称
	private String companyId;//促销公司Id
	private String price;//促销产品价格
	private String state;//0未付款1已付款
	private Date creatDate;
	private String standard;//促销品规格
	private Integer count;//促销品的数量
	public String getPaKey() {
		return paKey;
	}
	public void setPaKey(String paKey) {
		this.paKey = paKey;
	}
	public String getPaId() {
		return paId;
	}
	public void setPaId(String paId) {
		this.paId = paId;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getPpName() {
		return ppName;
	}
	public void setPpName(String ppName) {
		this.ppName = ppName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public String getPtcashierBillsID() {
		return ptcashierBillsID;
	}
	public void setPtcashierBillsID(String ptcashierBillsID) {
		this.ptcashierBillsID = ptcashierBillsID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
