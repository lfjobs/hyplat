package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

/**
 * 生产量设置
 * @author zj
 *
 */
public class ProductionAmount implements BaseBean{
	private String productionAmountKey;
	private String productionAmountID;
	private String companyID;								//公司ID
	private String companyName;						//公司名称
	private String productID;								//产品ID
	private String goodsName;							//产品名称
	private String setDate;										//设置日期
	private String batchNumber;						//生产批次号
	private String staffID;										//责任人ID
	private String staffName;								//责任人名称	
	private String singleID;									//制单人ID
	private String singleName;								//制单人名称
	private String singleDate;								//制单时间
	private String amount;										//生产量
	private String status;											//状态    00：草稿     01：提交
	private String category;									// 00：单产品    01：组装产品
	private String fiveClear;									//组织机构
	public String getProductionAmountKey() {
		return productionAmountKey;
	}
	public void setProductionAmountKey(String productionAmountKey) {
		this.productionAmountKey = productionAmountKey;
	}
	public String getProductionAmountID() {
		return productionAmountID;
	}
	public void setProductionAmountID(String productionAmountID) {
		this.productionAmountID = productionAmountID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSetDate() {
		return setDate;
	}
	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getSingleID() {
		return singleID;
	}
	public void setSingleID(String singleID) {
		this.singleID = singleID;
	}
	public String getSingleName() {
		return singleName;
	}
	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSingleDate() {
		return singleDate;
	}
	public void setSingleDate(String singleDate) {
		this.singleDate = singleDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	
}
