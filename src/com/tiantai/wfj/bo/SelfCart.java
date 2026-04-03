package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 自助收银购物车
 * @author Administrator
 *
 */
public class SelfCart implements BaseBean{

	private String cartKey;
	private String cartId;
	private String comID;//超市
	private String posNum;// 收银机编号/
	private String pid;// 商品ID
	private String price;// 商品价格  最终价格
	private String pritype;///价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
	private String activityID;//活动价格佣金表的ID
	private String oripri;//原价
	private String costmoney;//单价成本
	private String companyId;// 商品对应的公司ID
	private String itemNum;// 商品的数量
	private String sendNum;//需要配送的数量
	private String goodsName;//商品名称
	private String journalNum;//订单号
	private String payjournalNum;//多公司多个订单，共有付款订单号
	private String barCode;//条码
	private String specNum;//更多规格条码数量
	private String ssprice;//实收
	private String zlprice;//找零
	private String cardNum;//购物卡号
	private String stardard;//规格
	private String relateID;//关联扫描记录
	private  String sgrId;//记录ID
	private String variableID;//单位
	private String deptId;//库房ID
	private String deptName;//库房名称


	public String getCartKey() {
		return cartKey;
	}

	public void setCartKey(String cartKey) {
		this.cartKey = cartKey;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getPosNum() {
		return posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getComID() {
		return comID;
	}

	public void setComID(String comID) {
		this.comID = comID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSpecNum() {
		return specNum;
	}

	public void setSpecNum(String specNum) {
		this.specNum = specNum;
	}

	public String getSsprice() {
		return ssprice;
	}

	public void setSsprice(String ssprice) {
		this.ssprice = ssprice;
	}

	public String getZlprice() {
		return zlprice;
	}

	public void setZlprice(String zlprice) {
		this.zlprice = zlprice;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getStardard() {
		return stardard;
	}

	public void setStardard(String stardard) {
		this.stardard = stardard;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getPayjournalNum() {
		return payjournalNum;
	}

	public void setPayjournalNum(String payjournalNum) {
		this.payjournalNum = payjournalNum;
	}

	public String getPritype() {
		return pritype;
	}

	public void setPritype(String pritype) {
		this.pritype = pritype;
	}

	public String getCostmoney() {
		return costmoney;
	}

	public void setCostmoney(String costmoney) {
		this.costmoney = costmoney;
	}

	public String getOripri() {
		return oripri;
	}

	public void setOripri(String oripri) {
		this.oripri = oripri;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getRelateID() {
		return relateID;
	}

	public void setRelateID(String relateID) {
		this.relateID = relateID;
	}

	public String getSgrId() {
		return sgrId;
	}

	public void setSgrId(String sgrId) {
		this.sgrId = sgrId;
	}

	public String getVariableID() {
		return variableID;
	}

	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
