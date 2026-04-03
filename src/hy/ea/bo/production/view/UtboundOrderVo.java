package hy.ea.bo.production.view;

import hy.plat.bo.BaseBean;

public class UtboundOrderVo implements BaseBean {
	private String cashierbillskey;   
	private String cashierbillsid; 
	private String staffID;				//负责人Id   责任人Id   制单人Id
	private String staffName;			//负责人名称	责任人名称  制单人名称
	private String cashierDate;		//单据时间
	private String journalnum;		//单据编号
	private String statusbill;           //判断单据来源
	private String goodsbillsid;			//物品单据表ID
	private String inventoryID;		//库存表ID
	private String stockinvID;			//盘点表ID
	private String goodsId;				//物品ID
	private String typeId;				//物品分类
	private String goodsnum;			//品名编号
	private String goodsname;			//物品名称
	private String standard;			//物品规格
	private String quantity;			//数量
	private String price;				//单价
	private String money;				//总价
	private String financialbillid;		//进销存单据表Id
	private String depotname;			//出库仓库名称
	private String depotid;				//出库仓库ID
	private String drdepotName;		//入库仓库名称
	private String drdepotID;			//入库仓库ID
	private String staffsID;				//验收人ID
	private String staffsName;			//验收人名称
	private String examinegoodsDate;//验收时间
	private String billstype;			//单据类别
	private String status;				//单据状态
	private String companyid;			//公司ID
	private String organizationid;		//部门Id
	private String ppId;  				//产品Id
	private String profitMargin;   //利润率
	private String profitAmount;  //利润金额
	private String pretium;       	//销售价
	private String purchaseDate;	//出库日期  、入库日期
	private String subjectName;	//科目名称
	private String subjectID;		//科目Id
	private String logisticsType;	//物流方式
	private String contactUserID;//往来个人Id   出入库人员Id
	private String ctUserName;	//往来个人名称 出入库人员名称
	private String reference; 		//往来个人电话 出库人员电话
	private String proInspectionID;	//生产时考核检验ID
	private String jNumOrder;				//订单号
	private String category;			//产品类型  00：单产品   01：组装产品
	private String fiveClear;			//组织机构
	public String getCashierbillskey() {
		return cashierbillskey;
	}
	public void setCashierbillskey(String cashierbillskey) {
		this.cashierbillskey = cashierbillskey;
	}
	public String getCashierbillsid() {
		return cashierbillsid;
	}
	public void setCashierbillsid(String cashierbillsid) {
		this.cashierbillsid = cashierbillsid;
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
	public String getCashierDate() {
		return cashierDate;
	}
	public void setCashierDate(String cashierDate) {
		this.cashierDate = cashierDate;
	}
	public String getJournalnum() {
		return journalnum;
	}
	public void setJournalnum(String journalnum) {
		this.journalnum = journalnum;
	}
	public String getGoodsbillsid() {
		return goodsbillsid;
	}
	public void setGoodsbillsid(String goodsbillsid) {
		this.goodsbillsid = goodsbillsid;
	}
	public String getInventoryID() {
		return inventoryID;
	}
	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}
	public String getStockinvID() {
		return stockinvID;
	}
	public void setStockinvID(String stockinvID) {
		this.stockinvID = stockinvID;
	}
	public String getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(String goodsnum) {
		this.goodsnum = goodsnum;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getFinancialbillid() {
		return financialbillid;
	}
	public void setFinancialbillid(String financialbillid) {
		this.financialbillid = financialbillid;
	}

	public String getDepotname() {
		return depotname;
	}
	public void setDepotname(String depotname) {
		this.depotname = depotname;
	}
	public String getDepotid() {
		return depotid;
	}
	public void setDepotid(String depotid) {
		this.depotid = depotid;
	}
	public String getDrdepotName() {
		return drdepotName;
	}
	public void setDrdepotName(String drdepotName) {
		this.drdepotName = drdepotName;
	}
	public String getDrdepotID() {
		return drdepotID;
	}
	public void setDrdepotID(String drdepotID) {
		this.drdepotID = drdepotID;
	}
	public String getBillstype() {
		return billstype;
	}
	public void setBillstype(String billstype) {
		this.billstype = billstype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getOrganizationid() {
		return organizationid;
	}
	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}
	public String getStatusbill() {
		return statusbill;
	}
	public void setStatusbill(String statusbill) {
		this.statusbill = statusbill;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getStaffsID() {
		return staffsID;
	}
	public void setStaffsID(String staffsID) {
		this.staffsID = staffsID;
	}
	public String getStaffsName() {
		return staffsName;
	}
	public void setStaffsName(String staffsName) {
		this.staffsName = staffsName;
	}
	public String getExaminegoodsDate() {
		return examinegoodsDate;
	}
	public void setExaminegoodsDate(String examinegoodsDate) {
		this.examinegoodsDate = examinegoodsDate;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getProfitMargin() {
		return profitMargin;
	}
	public void setProfitMargin(String profitMargin) {
		this.profitMargin = profitMargin;
	}
	public String getProfitAmount() {
		return profitAmount;
	}
	public void setProfitAmount(String profitAmount) {
		this.profitAmount = profitAmount;
	}
	public String getPretium() {
		return pretium;
	}
	public void setPretium(String pretium) {
		this.pretium = pretium;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}
	public String getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	public String getContactUserID() {
		return contactUserID;
	}
	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}
	public String getCtUserName() {
		return ctUserName;
	}
	public void setCtUserName(String ctUserName) {
		this.ctUserName = ctUserName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getProInspectionID() {
		return proInspectionID;
	}
	public void setProInspectionID(String proInspectionID) {
		this.proInspectionID = proInspectionID;
	}
	public String getjNumOrder() {
		return jNumOrder;
	}
	public void setjNumOrder(String jNumOrder) {
		this.jNumOrder = jNumOrder;
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
