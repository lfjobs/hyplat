package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 进销存单据表
 *
 */
public class FinancialBill implements BaseBean,Cloneable,ExcelBean,java.io.Serializable{

/***************************出纳单据子表字段开始****************************************/
	private String financialbillKey;        //主键
	private String financialbillID;         //业务主键
	private String cashierBillsID;          //出纳单单据外键
	private String groupCompanySn;     		//集团公司的标识  外键
	private String companyID;          		//公司  外键
	private String organizationID;     		//单据所在部门  外键
	private String departmentID;       		//子部门ID(限定部门单据) 外键
	private String ccompanyID;         		//往来单位ID 外键（供货商，调出机构）
	private String ccompanyName;       		//往来单位名称（供货商，调出机构）
	private String ccompanyTel;        		//往来单位电话（供货商，调出机构）
	private String drccompanyID;            //调入机构ID 外键
	private String drccompanyName;       	//调入机构名称
	private String drccompanyTel;        	//调入机构电话
    /**
    * xyz添加 采购信息
    */
	private String frontMoney;			//预付定金
	private String moneyType;			//定金类型
	private String staffPhone;          //采购员电话
	private String purchaseDate;        //采购日期(入库日期)
	private String examinegoodsDate;    //验货日期
	private String goodsReceiptDate;		//收货日期
	private String subjectName;         //对方科目名称
	private String subjectID;           //对方科目ID
	private String paymentType;         //支付方式
	private String logisticsType;       //物流方式
	private String goodsmoney;          //采购合计
	private String depotName;           //仓库name
	private String depotID;             //仓库id
	private String drdepotName;         //调入仓库name
	private String drdepotID;           //调入仓库id
	private String staffID;             //限定部门内的责任人ID  外键
	private String staffName;           //采购员
	private String staffsID;            //限定部门内的接收人ID  外键  
	private String staffsName;			//接收人姓名（验货人）
	private String billsType;           //单据类型
	private String journalNum;          //凭证号（单据编号）
	private Date billsDate;           //制单日期
	private String billStaffID;         //制单人ID  外键
	private String billStaffName;       //制单人名称
	private String billStatus;         	//单据状态  22:草稿状态 12：未收货  13：已收货 14：已验货 15：已入库 16：已出库  17：盘库 18：移库 19:销售出库 23:入库失败24:报损
/***************************************************************************/	
	//未使用字段
	private String companyBankNum;     		//公司银行账号
	private String cstaffID;            	//往来个人ID  外键
	private String cstaffName;          	//往来个人名称
	private String cstaffRelationship;      //个人往来关系
	private String userAccountNum;			//往来个人银行帐号
	private String ccompanyRelationship;    //往来单位关系
	private String accountNum;				//往来公司银行账号
	private String accessoriesUrl;			//附件(路径)
	private File photo;						//附件文件
	private String PhotoFileName;			//附件名称
	
	private String consigneeID;			//收货人ID
	private String consigneeName;	//收货人名称
  
	private String category;			//产品类型  00：单产品  01：组装产品
	private String fiveClear;			//组织机构


	private String area;                   //区外键(从库房中拿)
	private String frame;                  //架外键(从库房中拿)
	private String position;               //位外键(从库房中拿)

	private String areaName;               //区名称
	private String frameName;              //架名称
	private String positionName;           //位名称
	
	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneFinancialBill() throws CloneNotSupportedException{
		return this.clone();
	}
	
	@Override
	public String[] properties() {
		return null;
	}
	
	/**
	 * 主键
	 * @return
	 */
	public String getFinancialbillKey() {
		return financialbillKey;
	}
	/**
	 * 主键
	 * @param financialbillKey
	 */
	public void setFinancialbillKey(String financialbillKey) {
		this.financialbillKey = financialbillKey;
	}
	
	/**
	 * 业务主键
	 * @return
	 */
	public String getFinancialbillID() {
		return financialbillID;
	}
	/**
	 * 业务主键
	 * @param financialbillID
	 */
	public void setFinancialbillID(String financialbillID) {
		this.financialbillID = financialbillID;
	}
	
	/**
	 * 集团公司的标识  外键
	 * @return
	 */
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	/**
	 * 集团公司的标识  外键
	 * @param groupCompanySn
	 */
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	
	/**
	 * 公司  外键
	 * @return
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * 公司  外键
	 * @param companyID
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
	 * 单据所在部门  外键
	 * @return
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * 单据所在部门  外键
	 * @param organizationID
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	
	/**
	 * 子部门ID(限定部门单据) 外键
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}
	/**
	 * 子部门ID(限定部门单据) 外键
	 * @param departmentID
	 */
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	
	/**
	 * 公司银行账号
	 * @return
	 */
	public String getCompanyBankNum() {
		return companyBankNum;
	}
	/**
	 * 公司银行账号
	 * @param companyBankNum
	 */
	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}
	
	/**
	 * 单据状态  00：未收货  01：已收货 02：已入库 03：已出库 04：市场调查 05:盘库单
	 * @return
	 */
	public String getBillStatus() {
		return billStatus;
	}
	/**
	 * 单据状态  00：未收货  01：已收货 02：已入库 03：已出库 04：市场调查 05:盘库单
	 * @param billStatus
	 */
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	
	/**
	 * 往来个人ID  外键
	 * @return
	 */
	public String getCstaffID() {
		return cstaffID;
	}
	/**
	 * 往来个人ID  外键
	 * @param cstaffID
	 */
	public void setCstaffID(String cstaffID) {
		this.cstaffID = cstaffID;
	}
	
	/**
	 * 往来个人名称
	 * @return
	 */
	public String getCstaffName() {
		return cstaffName;
	}
	/**
	 * 往来个人名称
	 * @param cstaffName
	 */
	public void setCstaffName(String cstaffName) {
		this.cstaffName = cstaffName;
	}
	
	/**
	 * 个人往来关系
	 * @return
	 */
	public String getCstaffRelationship() {
		return cstaffRelationship;
	}
	/**
	 * 个人往来关系
	 * @param cstaffRelationship
	 */
	public void setCstaffRelationship(String cstaffRelationship) {
		this.cstaffRelationship = cstaffRelationship;
	}
	
	/**
	 * 往来单位ID  外键
	 * @return
	 */
	public String getCcompanyID() {
		return ccompanyID;
	}
	/**
	 * 往来单位ID  外键
	 * @param ccompanyID
	 */
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	
	/**
	 * 往来单位名称
	 * @return
	 */
	public String getCcompanyName() {
		return ccompanyName;
	}
	/**
	 * 往来单位名称
	 * @param ccompanyName
	 */
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	
	/**
	 * 往来单位关系
	 * @return
	 */
	public String getCcompanyRelationship() {
		return ccompanyRelationship;
	}
	/**
	 * 往来单位关系
	 * @param ccompanyRelationship
	 */
	public void setCcompanyRelationship(String ccompanyRelationship) {
		this.ccompanyRelationship = ccompanyRelationship;
	}
	
	/**
	 * 往来单位电话
	 * @return
	 */
	public String getCcompanyTel() {
		return ccompanyTel;
	}
	/**
	 * 往来单位电话
	 * @param ccompanyTel
	 */
	public void setCcompanyTel(String ccompanyTel) {
		this.ccompanyTel = ccompanyTel;
	}
	/**
	 * 调入机构ID
	 * @return
	 */
	public String getDrccompanyID() {
		return drccompanyID;
	}
	/**
	 * 调入机构ID
	 * @param drccompanyID
	 */
	public void setDrccompanyID(String drccompanyID) {
		this.drccompanyID = drccompanyID;
	}
	/**
	 * 调入机构名称
	 * @return
	 */
	public String getDrccompanyName() {
		return drccompanyName;
	}
	/**
	 * 调入机构名称
	 * @param drccompanyName
	 */
	public void setDrccompanyName(String drccompanyName) {
		this.drccompanyName = drccompanyName;
	}
	/**
	 * 调入机构电话
	 * @return
	 */
	public String getDrccompanyTel() {
		return drccompanyTel;
	}
	/**
	 * 调入机构电话
	 * @param drccompanyTel
	 */
	public void setDrccompanyTel(String drccompanyTel) {
		this.drccompanyTel = drccompanyTel;
	}

	/**
	 * 限定部门内德责任人ID  外键
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * 限定部门内德责任人ID  外键
	 * @param staffID
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	/**
	 * 单据类型
	 * @return
	 */
	public String getBillsType() {
		return billsType;
	}
	/**
	 * 单据类型
	 * @param billsType
	 */
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	
	/**
	 * 凭证号
	 * @return
	 */
	public String getJournalNum() {
		return journalNum;
	}
	/**
	 * 凭证号
	 * @param journalNum
	 */
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	
	/**
	 * 制单日期
	 * @return
	 */
	public Date getBillsDate() {
		return billsDate;
	}

	public void setBillsDate(Date billsDate) {
		this.billsDate = billsDate;
	}

	/**
	 * 制单人ID  外键
	 * @return
	 */
	public String getBillStaffID() {
		return billStaffID;
	}
	/**
	 * 制单人ID  外键
	 * @param billStaffID
	 */
	public void setBillStaffID(String billStaffID) {
		this.billStaffID = billStaffID;
	}
	
	/**
	 * 制单人名称
	 * @return
	 */
	public String getBillStaffName() {
		return billStaffName;
	}
	/**
	 * 制单人名称
	 * @param billStaffName
	 */
	public void setBillStaffName(String billStaffName) {
		this.billStaffName = billStaffName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
	}
	/**
	 * 责任人名称
	 * @return
	 */

	public String getStaffName() {
		return staffName;
	}
	/**
	 * 责任人名称
	 * @return
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * 附件(路径)
	 * @return
	 */
	public String getAccessoriesUrl() {
		return accessoriesUrl;
	}
	/**
	 * 附件(路径)
	 * @param accessoriesUrl
	 */
	public void setAccessoriesUrl(String accessoriesUrl) {
		this.accessoriesUrl = accessoriesUrl;
	}

	/**
	 * 附件文件
	 * @return
	 */
	public File getPhoto() {
		return photo;
	}
	/**
	 * 附件文件
	 * @param photo
	 */
	public void setPhoto(File photo) {
		this.photo = photo;
	}

	/**
	 * 附件名称
	 * @return
	 */
	public String getPhotoFileName() {
		return PhotoFileName;
	}
	/**
	 * 附件名称
	 * @param photoFileName
	 */
	public void setPhotoFileName(String photoFileName) {
		PhotoFileName = photoFileName;
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

	public String getFrontMoney() {
		return frontMoney;
	}

	public void setFrontMoney(String frontMoney) {
		this.frontMoney = frontMoney;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getGoodsmoney() {
		return goodsmoney;
	}

	public void setGoodsmoney(String goodsmoney) {
		this.goodsmoney = goodsmoney;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDepotID() {
		return depotID;
	}

	public void setDepotID(String depotID) {
		this.depotID = depotID;
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

	public String getExaminegoodsDate() {
		return examinegoodsDate;
	}

	public void setExaminegoodsDate(String examinegoodsDate) {
		this.examinegoodsDate = examinegoodsDate;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getGoodsReceiptDate() {
		return goodsReceiptDate;
	}

	public void setGoodsReceiptDate(String goodsReceiptDate) {
		this.goodsReceiptDate = goodsReceiptDate;
	}

	public String getConsigneeID() {
		return consigneeID;
	}

	public void setConsigneeID(String consigneeID) {
		this.consigneeID = consigneeID;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getFrameName() {
		return frameName;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
