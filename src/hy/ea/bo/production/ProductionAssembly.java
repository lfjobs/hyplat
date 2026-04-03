package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

/*
 * 组装生产表
 */
public class ProductionAssembly  implements BaseBean{
	private String proAssemblyKey;						//主键
	private String proAssemblyID;							//伪键
	private String productPID;							//主产品ID
	private String productID;							//产品ID
	private String cashierBillsID;						//生产单据
	private String goodsName;						//产品名称
	private String structureQuantity;			//结构数量
	private String outgoingQuantity;			//生产数量
	private String startTime;								//开始时间
	private String endTime;								//结束时间
	private String journal;									//生产日志
	private String teachingSupervisor;			//责任人
	private String teachingSupervisorID;	//责任人ID
	private String status;										//状态    00：组装中   01：组装完成    02:检验失败，重新组装
	private String remarks;								//备注
	

	public String getProAssemblyKey() {
		return proAssemblyKey;
	}
	public void setProAssemblyKey(String proAssemblyKey) {
		this.proAssemblyKey = proAssemblyKey;
	}
	public String getProAssemblyID() {
		return proAssemblyID;
	}
	public void setProAssemblyID(String proAssemblyID) {
		this.proAssemblyID = proAssemblyID;
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
	public String getStructureQuantity() {
		return structureQuantity;
	}
	public void setStructureQuantity(String structureQuantity) {
		this.structureQuantity = structureQuantity;
	}
	public String getOutgoingQuantity() {
		return outgoingQuantity;
	}
	public void setOutgoingQuantity(String outgoingQuantity) {
		this.outgoingQuantity = outgoingQuantity;
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
	public String getTeachingSupervisor() {
		return teachingSupervisor;
	}
	public void setTeachingSupervisor(String teachingSupervisor) {
		this.teachingSupervisor = teachingSupervisor;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductPID() {
		return productPID;
	}
	public void setProductPID(String productPID) {
		this.productPID = productPID;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getTeachingSupervisorID() {
		return teachingSupervisorID;
	}
	public void setTeachingSupervisorID(String teachingSupervisorID) {
		this.teachingSupervisorID = teachingSupervisorID;
	}
	
}
