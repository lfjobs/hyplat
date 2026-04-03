package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 订单产品表
 * @author TY
 *
 */
public class OrderProduct implements BaseBean{

	private String key; //主键ID 系统自动生成
	private String orderProductID; //唯一标识ID
	private String orderID; //订单号
	private String productID; //产品ID
	private String productMoney; //生成订单时产品单价
	
	private String wlCompany; //物流公司
	private String wlOrderNumber; //物流单号
	private Date sendProductTime; //发货时间
	
	
	private String buyNum; //产品订购数量
	private String type; //状态（0正常  1退货  2换货）
	private String compID; //公司ID
	
	public String getCompID() {
		return compID;
	}
	public void setCompID(String compID) {
		this.compID = compID;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductMoney() {
		return productMoney;
	}
	public void setProductMoney(String productMoney) {
		this.productMoney = productMoney;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderProductID() {
		return orderProductID;
	}
	public void setOrderProductID(String orderProductID) {
		this.orderProductID = orderProductID;
	}
	public String getWlCompany() {
		return wlCompany;
	}
	public void setWlCompany(String wlCompany) {
		this.wlCompany = wlCompany;
	}
	public String getWlOrderNumber() {
		return wlOrderNumber;
	}
	public void setWlOrderNumber(String wlOrderNumber) {
		this.wlOrderNumber = wlOrderNumber;
	}
	public Date getSendProductTime() {
		return sendProductTime;
	}
	public void setSendProductTime(Date sendProductTime) {
		this.sendProductTime = sendProductTime;
	}
	
	
	
}
