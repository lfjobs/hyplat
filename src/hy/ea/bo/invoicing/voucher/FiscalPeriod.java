package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

/**
 * 会计期间
 * 
 * @author mz
 */

public class FiscalPeriod implements BaseBean,java.io.Serializable{
   private String fpKey;
   private String fpID;
   private String companyID;   
   private String companyName;
   private String groupCompanyID;
   private String year;  //会计年度
   private String startDate; //会计年度起始月份
   private String endDate;//会计年度终止月份
   private String creatorID;//新增人员ID
   private String creatorName;//新增人员Name
   private String updatorID;//修改人员ID
   private String updatorName;//修改人员Name
   private String createDate;//新增时间
   private String updateDate;//修改时间
   
   
public String getFpKey() {
	return fpKey;
}
public void setFpKey(String fpKey) {
	this.fpKey = fpKey;
}
public String getFpID() {
	return fpID;
}
public void setFpID(String fpID) {
	this.fpID = fpID;
}
public String getCompanyID() {
	return companyID;
}
public void setCompanyID(String companyID) {
	this.companyID = companyID;
}
public String getGroupCompanyID() {
	return groupCompanyID;
}
public void setGroupCompanyID(String groupCompanyID) {
	this.groupCompanyID = groupCompanyID;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getCreatorID() {
	return creatorID;
}
public void setCreatorID(String creatorID) {
	this.creatorID = creatorID;
}
public String getCreatorName() {
	return creatorName;
}
public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
}
public String getUpdatorID() {
	return updatorID;
}
public void setUpdatorID(String updatorID) {
	this.updatorID = updatorID;
}
public String getUpdatorName() {
	return updatorName;
}
public void setUpdatorName(String updatorName) {
	this.updatorName = updatorName;
}
public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}
public String getUpdateDate() {
	return updateDate;
}
public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
}

   
	
}
