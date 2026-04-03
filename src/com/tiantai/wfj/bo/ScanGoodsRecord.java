package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 扫描商品记录
 * @author mz
 *
 */
public class ScanGoodsRecord implements BaseBean{

	private String sgrKey;
	private String sgrId;
	private String posNum;// 收银机编号
	private String pid;// 商品ID
	private String companyId;// 商品对应的公司ID
	private String itemNum;// 商品的数量 增加数量
	private String joinNum;//加入的数量
	private String barCode;//条码
	private String stardard;//规格
	private String price; //价格
	private String goodsName;//商品名称
	private Date joinDate;
	private String  staffID;//收银员ID
	private String  reason;//未结算理由
	private String  relateID;//关联订单号的统一编号
	private String state;//00未处理，01 已处理  09 删除

	public String getSgrKey() {
		return sgrKey;
	}

	public void setSgrKey(String sgrKey) {
		this.sgrKey = sgrKey;
	}

	public String getSgrId() {
		return sgrId;
	}

	public void setSgrId(String sgrId) {
		this.sgrId = sgrId;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getStardard() {
		return stardard;
	}

	public void setStardard(String stardard) {
		this.stardard = stardard;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRelateID() {
		return relateID;
	}

	public void setRelateID(String relateID) {
		this.relateID = relateID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}
}
