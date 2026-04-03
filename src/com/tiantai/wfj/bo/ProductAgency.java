package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 代理产品表
 * @author TY
 *
 */
public class ProductAgency implements BaseBean{
	private String key; //自动生成id
	private String agencyID; //唯一表示id
	private String productID; //代理产品id
	private String agencyCompanyID; //代理公司id
	private String agencyPartnerID; //代理合伙人id	
	private String agencyShopID; //代理微分金店id
	private String agentID; //代理商id
	private String createTime; //创建时间
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAgencyID() {
		return agencyID;
	}
	public void setAgencyID(String agencyID) {
		this.agencyID = agencyID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getAgencyCompanyID() {
		return agencyCompanyID;
	}
	public void setAgencyCompanyID(String agencyCompanyID) {
		this.agencyCompanyID = agencyCompanyID;
	}
	public String getAgencyPartnerID() {
		return agencyPartnerID;
	}
	public void setAgencyPartnerID(String agencyPartnerID) {
		this.agencyPartnerID = agencyPartnerID;
	}
	public String getAgencyShopID() {
		return agencyShopID;
	}
	public void setAgencyShopID(String agencyShopID) {
		this.agencyShopID = agencyShopID;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}
