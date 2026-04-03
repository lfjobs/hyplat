package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * 促销品
 */
public class Promotion implements BaseBean {

	private String promotionKey;
	private String promotionId;
	private String ppId;//产品Id
	private String ptppId;//促销产品Id
	private String companyId;//产品的公司Id
	private String ptcompanyId;//促销产品公司Id
	private Date promotionDate;//提交时间
	private int comboGenre;//套餐类型
	public String getPromotionKey() {
		return promotionKey;
	}
	public void setPromotionKey(String promotionKey) {
		this.promotionKey = promotionKey;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getPtppId() {
		return ptppId;
	}
	public void setPtppId(String ptppId) {
		this.ptppId = ptppId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPtcompanyId() {
		return ptcompanyId;
	}
	public void setPtcompanyId(String ptcompanyId) {
		this.ptcompanyId = ptcompanyId;
	}
	public Date getPromotionDate() {
		return promotionDate;
	}
	public void setPromotionDate(Date promotionDate) {
		this.promotionDate = promotionDate;
	}
	public int getComboGenre() {
		return comboGenre;
	}
	public void setComboGenre(int comboGenre) {
		this.comboGenre = comboGenre;
	}
	
	
}
