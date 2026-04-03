package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 
 * 人员分配
 * 
 * @author mz
 *
 */
public class MemberAllot implements BaseBean,ExcelBean,java.io.Serializable {

	
    private String makey;
    private String maid;
    private String productCode;//产品编号
    private String productName;//产品名称
    private String productID;//产品ID
    private Date startDate;//项目开始时间
    private Date endDate;//项目结束时间
    private String allotorID;//分配人ID
    private String allotorName;//分配人Name
    private String transferID;//交接人ID
    private String transferName;//交接人Name
    private String receiverID;//接收人id
    private String receiverName;//接收人name
    private String duty;//职责
    private Date allotDate;//分配时间
    private String remark;//备注
    private String companyID;//公司ID
    private String type;	//类别   00：订单生产  01：计划生产
    private String category;  //产品类型 00：单产品  02：组装
    private String fiveClear;	//组织机构
    public static String[] columnHeadings() {
		String[] titles = { "序号", "项目产品编号", "项目产品名称", "开始时间", "结束时间","分配负责人","交接人","接班人","分配时间","职责","备注"};
		return titles;
	}
    @Override
	public String[] properties() {
		String[] properties = { productCode,productName,
				String.format("%1$tF", startDate) ,String.format("%1$tF", endDate), allotorName,transferName,receiverName,String.format("%1$tF", allotDate),duty,remark };
		return properties;
	}
    
    
	public String getMakey() {
		return makey;
	}
	public void setMakey(String makey) {
		this.makey = makey;
	}
	public String getMaid() {
		return maid;
	}
	public void setMaid(String maid) {
		this.maid = maid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAllotorID() {
		return allotorID;
	}
	public void setAllotorID(String allotorID) {
		this.allotorID = allotorID;
	}
	public String getAllotorName() {
		return allotorName;
	}
	public void setAllotorName(String allotorName) {
		this.allotorName = allotorName;
	}
	public String getTransferID() {
		return transferID;
	}
	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}
	public String getTransferName() {
		return transferName;
	}
	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Date getAllotDate() {
		return allotDate;
	}
	public void setAllotDate(Date allotDate) {
		this.allotDate = allotDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
    
}
