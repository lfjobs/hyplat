package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class Bidsinformation implements BaseBean {

	private String bfkey; // 主键
	private String bfId;
	private String cashierBillsID;//单据ID
	private String mainpicurl;//主图
	private String tgoodsName;//投标的物品名称
	private String tgoodsID;//投标的物品ID
	private String tgoodsNum;//投标的物品编号
	private String ppID;//产品ID
	private String tprice;//投标价格
	private String tquantity;//数量
	private String tmoney;//金额
	private String boxStandard;
	private String goodsVariableID;//单位
	private String bidperson;//投标人名称
	private String bidtype;//投标人类型 00公司  01个人
	private String companyID;//如果是公司公司ID
	private String staffID;//如果是个人个人ID
	private Date bidDate;//投标时间

	private String bidremark;//投标说明
	private String goodsID;//投标的是哪个物品
	//选标情况
	private String iszbid;//是否中标00未中标 01中标
	private Date zbDate;//中标时间
	private String selectID;//选标人ID
	private String selectName;//选标人姓名
	public String getBfkey() {
		return bfkey;
	}
	public void setBfkey(String bfkey) {
		this.bfkey = bfkey;
	}
	public String getBfId() {
		return bfId;
	}
	public void setBfId(String bfId) {
		this.bfId = bfId;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getMainpicurl() {
		return mainpicurl;
	}
	public void setMainpicurl(String mainpicurl) {
		this.mainpicurl = mainpicurl;
	}
	public String getTgoodsName() {
		return tgoodsName;
	}
	public void setTgoodsName(String tgoodsName) {
		this.tgoodsName = tgoodsName;
	}
	public String getTgoodsID() {
		return tgoodsID;
	}
	public void setTgoodsID(String tgoodsID) {
		this.tgoodsID = tgoodsID;
	}
	public String getTprice() {
		return tprice;
	}
	public void setTprice(String tprice) {
		this.tprice = tprice;
	}
	public String getBidperson() {
		return bidperson;
	}
	public void setBidperson(String bidperson) {
		this.bidperson = bidperson;
	}
	public String getBidtype() {
		return bidtype;
	}
	public void setBidtype(String bidtype) {
		this.bidtype = bidtype;
	}
	public Date getBidDate() {
		return bidDate;
	}
	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}
	public String getIszbid() {
		return iszbid;
	}
	public void setIszbid(String iszbid) {
		this.iszbid = iszbid;
	}
	public String getBidremark() {
		return bidremark;
	}
	public void setBidremark(String bidremark) {
		this.bidremark = bidremark;
	}
	
	public String getTquantity() {
		return tquantity;
	}
	public void setTquantity(String tquantity) {
		this.tquantity = tquantity;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getTmoney() {
		return tmoney;
	}
	public void setTmoney(String tmoney) {
		this.tmoney = tmoney;
	}
	public String getBoxStandard() {
		return boxStandard;
	}
	public void setBoxStandard(String boxStandard) {
		this.boxStandard = boxStandard;
	}
	public String getGoodsVariableID() {
		return goodsVariableID;
	}
	public void setGoodsVariableID(String goodsVariableID) {
		this.goodsVariableID = goodsVariableID;
	}
	
	public Date getZbDate() {
		return zbDate;
	}
	public void setZbDate(Date zbDate) {
		this.zbDate = zbDate;
	}
	public String getSelectID() {
		return selectID;
	}
	public void setSelectID(String selectID) {
		this.selectID = selectID;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getTgoodsNum() {
		return tgoodsNum;
	}
	public void setTgoodsNum(String tgoodsNum) {
		this.tgoodsNum = tgoodsNum;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	

    
}