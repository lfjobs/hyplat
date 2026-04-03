package hy.ea.bo.production;

import hy.plat.bo.BaseBean;
/**
 * 场地分配表
 * @author zj
 *
 */
public class FieldDistribution implements BaseBean {
	
	private String fieldDistributionKey;
	private String fieldDistributionId;
	private String proDocumentsID;   //生产批次ID
	private String companyId;		//公司Id
	private String startTime;		// 开始时间
	private String endTime;			// 结束时间
	private String staffId;			// 场地责任人Id
	private String staffName;		// 场地责任人名称
	private String siteAddress;		//场地地址
	private String ppId;			//产品表Id
	private String ppName;		//产品表Name
	private String productCode;//项目编号
	private String duty;			//职责
	private String distributionTime; //分配时间
	private String remarks;			//备注
	private String status; 			//00：正常     01：删除
	private String type;			//生产类别  00：订单  01：计划
	private String category;			//产品类型  00：单产品 01：组装产品
	private String fiveClear;			//组织机构
	public String getFieldDistributionKey() {
		return fieldDistributionKey;
	}
	public void setFieldDistributionKey(String fieldDistributionKey) {
		this.fieldDistributionKey = fieldDistributionKey;
	}
	public String getFieldDistributionId() {
		return fieldDistributionId;
	}
	public void setFieldDistributionId(String fieldDistributionId) {
		this.fieldDistributionId = fieldDistributionId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPpName() {
		return ppName;
	}
	public void setPpName(String ppName) {
		this.ppName = ppName;
	}
	public String getDistributionTime() {
		return distributionTime;
	}
	public void setDistributionTime(String distributionTime) {
		this.distributionTime = distributionTime;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProDocumentsID() {
		return proDocumentsID;
	}
	public void setProDocumentsID(String proDocumentsID) {
		this.proDocumentsID = proDocumentsID;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
}