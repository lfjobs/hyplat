package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 项目预算
 */
public class Budget implements BaseBean{

	private String budgetKey;
	private String budgetId;
	private String ppname;//产品名称
	private String goodsBillsID;//单据id
	private String price;//产品价格
	private String ppId;
	  
	
	
	public String getBudgetKey() {
		return budgetKey;
	}
	public void setBudgetKey(String budgetKey) {
		this.budgetKey = budgetKey;
	}
	public String getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}
	public String getPpname() {
		return ppname;
	}
	public void setPpname(String ppname) {
		this.ppname = ppname;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}

	
}
