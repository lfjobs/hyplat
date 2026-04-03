package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Map;

/**
 * 进销存子类
 *
 */
public class FinancialBillsGood implements BaseBean,ExcelBean,java.io.Serializable{
/***************************出纳物品子表字段开始****************************************/	
	private String financialgoodKey;      //主键
	private String financialgoodID;       //业务主键
	private String financialbillID;       //采购单据外键
	private String financialbillIDyh;     //验货单据外键
	private String financialbillIDrk;     //入库单据外键
	private String goodsBillsID;          //GoodsBills外键
	private String numbers;               //序号
	private String inventoryID;			  //库房外键
	private String inventoryName;         //库房名称
	private String goodsID;               //物品外键
	private String goodsName;             //费用或品名名称
	private String goodsNum;              //品名编号
	private String modelNumber;           //型号
	private String brand;                 //品牌
	private String type;                  //类型
	private String sortCode;              //统一分类条码
	private String defaultStorage;        //芯片号
	private String quantity;              //数量
	private String unitPrice;             //单价
	private String amount;                //金额
	private String unit;                  //单位
	private String remindContent;         //提醒内容（备注）
	private String status;				  //状态	22:草稿状态 12：未收货  13：已收货 14：已验货 15：已入库 16：已出库  17：盘库 18：移库 19:销售出库 23:入库失败24:报损
	private String goodstatus;            //状态00正常 01维修02报废
	private String storageQuantity;		  //未入库数量
	private String reQuantity;            //收货数量
	private String isQualify;          	  //物品合格数量
	private String subjectsID;            //科目ID
	private String subjectsName;          //科目名称
/***************************未使用字段****************************************/		
	private String paymentType;                //付款方式
	private String logisticsType;              //物流方式
	private String purchasemode;               //采购方式 1 拨款 0 未拨款
	private String stocktakingQuantity;        //盘库数量
	private String stocktakingresults;	       //盘库结果
	private String invenQuantity;		       //盘库前库存量
	private String surveyAmount;               //调查金额
	private String errorAmount;                //误差金额
	private String processOpinion;             //处理意见
	/**
	 * 库
	 */
	private String pware;  
	private String ku;
	/**
	 * 区
	 */
	private String parea;
	private String qu;
	/**
	 * 架
	 */
	private String pframe;
	private String jia;
	/**
	 * 位
	 */
	private String place;
	private String wei;
	
	private String shiftQuantity; 			//移库数量
	
	private String weizhi;
	
	
	/**
	 * 添加字段
	 * @return
	 */
	private String appropriationmoney;		       //拨款金额
	private String cashApplyBillsID;			   //拨款id
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","凭证号","单据类型","部门","负责人","品名编号","统一分类条码","品名名称","物品类别","品牌","型号","单位","合格数量","收货数量","采购数量","采购单价","采购金额","付款方式","物流方式","提醒内容","往来单位","单位往来关系","往来个人","个人往来关系"};
		return titles;
	}
	
	@Override
	public String[] properties() { 
		return null;
	}
	
	public static Map<String, String> orgMap;
	/**
	 * 主键
	 * @return
	 */
	public String getFinancialgoodKey() {
		return financialgoodKey;
	}
	/**
	 * 主键
	 * @param financialgoodKey
	 */
	public void setFinancialgoodKey(String financialgoodKey) {
		this.financialgoodKey = financialgoodKey;
	}
	
	/**
	 * 业务主键
	 * @return
	 */
	public String getFinancialgoodID() {
		return financialgoodID;
	}
	/**
	 * 业务主键
	 * @param financialgoodID
	 */
	public void setFinancialgoodID(String financialgoodID) {
		this.financialgoodID = financialgoodID;
	}
	
	/**
	 * 主单据外键
	 * @return
	 */
	public String getFinancialbillID() {
		return financialbillID;
	}
	/**
	 * 主单据外键
	 * @param financialbillID
	 */
	public void setFinancialbillID(String financialbillID) {
		this.financialbillID = financialbillID;
	}
	
	/**
	 * 序号
	 * @return
	 */
	public String getNumbers() {
		return numbers;
	}
	/**
	 * 序号
	 * @param number
	 */
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	/**
	 * 物品外键
	 * @return
	 */
	public String getGoodsID() {
		return goodsID;
	}
	/**
	 * 物品外键
	 * @param goodsID
	 */
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	
	/**
	 * 品名编号
	 * @return
	 */
	public String getGoodsNum() {
		return goodsNum;
	}
	/**
	 * 品名编号
	 * @param goodsNum
	 */
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	/**
	 * 统一分类条码
	 * @return
	 */
	public String getSortCode() {
		return sortCode;
	}
	/**
	 * 统一分类条码
	 * @param sortCode
	 */
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	
	/**
	 * 费用或品名名称
	 * @return
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 费用或品名名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 类型
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * 类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 品牌
	 * @return
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * 品牌
	 * @param brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * 型号
	 * @return
	 */
	public String getModelNumber() {
		return modelNumber;
	}
	/**
	 * 型号
	 * @param modelNumber
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	/**
	 * 物品是否合格
	 * @return
	 */
	public String getIsQualify() {
		return isQualify;
	}
	/**
	 * 物品是否合格
	 * @param isQualify
	 */
	public void setIsQualify(String isQualify) {
		this.isQualify = isQualify;
	}
	
	/**
	 * 单价
	 * @return
	 */
	public String getUnitPrice() {
		return unitPrice;
	}
	/**
	 * 单价
	 * @param unitPrice
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	/**
	 * 数量
	 * @return
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * 数量
	 * @param quantity
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 收货数量
	 * @return
	 */
	public String getReQuantity() {
		return reQuantity;
	}
	/**
	 * 收货数量
	 * @param reQuantity
	 */
	public void setReQuantity(String reQuantity) {
		this.reQuantity = reQuantity;
	}

	/**
	 * 单位
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 单位
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * 金额
	 * @return
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 金额
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * 付费方式
	 * @return
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * 付费方式
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	/**
	 * 物流方式
	 * @return
	 */
	public String getLogisticsType() {
		return logisticsType;
	}
	/**
	 * 物流方式
	 * @param logisticsType
	 */
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	
	/**
	 * 提醒内容
	 * @return
	 */
	public String getRemindContent() {
		return remindContent;
	}
	/**
	 * 提醒内容
	 * @param remindContent
	 */
	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}
	
	/**
	 * 库
	 * @return
	 */
	public String getPware() {
		return orgMap==null?pware:orgMap.get(pware);
	}
	/**
	 * 库
	 * @param pware
	 */
	public void setPware(String pware) {
		this.pware = pware;
	}

	/**
	 * 区
	 * @return
	 */
	public String getParea() {
		return orgMap==null?parea:orgMap.get(parea);
	}
	/**
	 * 区
	 * @param parea
	 */
	public void setParea(String parea) {
		this.parea = parea;
	}

	/**
	 * 架
	 * @return
	 */
	public String getPframe() {
		return orgMap==null?pframe:orgMap.get(pframe);
	}
	/**
	 * 架
	 * @param pframe
	 */
	public void setPframe(String pframe) {
		this.pframe = pframe;
	}

	/**
	 * 位
	 * @return
	 */
	public String getPlace() {
		return orgMap==null?place:orgMap.get(place);
	}
	/**
	 * 位
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	public static Map<String, String> getOrgMap() {
		return orgMap;
	}
	public static void setOrgMap(Map<String, String> orgMap) {
		FinancialBillsGood.orgMap = orgMap;
	}
	
	/**
	 * 库房外键
	 * @return
	 */
	public String getInventoryID() {
		return inventoryID;
	}
	/**
	 * 库房外键
	 * @param inventoryID
	 */
	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}
	
	/**
	 * 状态	00 未收货 01 已收货 02 已验货 03 已入库 04：市场调查 05:盘库
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 状态	00 未收货 01 已收货 02 已验货 03 已入库 04：市场调查 05:盘库
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 未入库数量
	 * @return
	 */
	public String getStorageQuantity() {
		return storageQuantity;
	}
	/**
	 * 未入库数量
	 * @param storageQuantity
	 */
	public void setStorageQuantity(String storageQuantity) {
		this.storageQuantity = storageQuantity;
	}
	
	/**
	 * 调查金额
	 * @return
	 */
	public String getSurveyAmount() {
		return surveyAmount;
	}
	/**
	 * 调查金额
	 * @param surveyAmount
	 */
	public void setSurveyAmount(String surveyAmount) {
		this.surveyAmount = surveyAmount;
	}
	
	/**
	 * 误差金额
	 * @return
	 */
	public String getErrorAmount() {
		return errorAmount;
	}
	/**
	 * 误差金额
	 * @param errorAmount
	 */
	public void setErrorAmount(String errorAmount) {
		this.errorAmount = errorAmount;
	}
	
	/**
	 * 处理意见
	 * @return
	 */
	public String getProcessOpinion() {
		return processOpinion;
	}
	/**
	 * 处理意见
	 * @param processOpinion
	 */
	public void setProcessOpinion(String processOpinion) {
		this.processOpinion = processOpinion;
	}
	/**
	 * 盘库数量
	 * @return
	 */
	public String getStocktakingQuantity() {
		return stocktakingQuantity;
	}
	/**
	 * 盘库数量
	 */
	public void setStocktakingQuantity(String stocktakingQuantity) {
		this.stocktakingQuantity = stocktakingQuantity;
	}
	/**
	 * 盘库结果
	 * @return
	 */
	public String getStocktakingresults() {
		return stocktakingresults;
	}
	/**
	 * 盘库结果
	 */
	public void setStocktakingresults(String stocktakingresults) {
		this.stocktakingresults = stocktakingresults;
	}
	
	/**
	 * 盘库前库存量
	 * @return
	 */
	public String getInvenQuantity() {
		return invenQuantity;
	}
	/**
	 * 盘库前库存量
	 */
	public void setInvenQuantity(String invenQuantity) {
		this.invenQuantity = invenQuantity;
	}
	/**
	 * 库
	 * @return
	 */
	public String getKu() {
		return ku;
	}
	/**
	 * 库
	 */
	public void setKu(String ku) {
		this.ku = ku;
	}
	/**
	 * 区
	 * @return
	 */
	public String getQu() {
		return qu;
	}
	/**
	 * 区
	 */
	public void setQu(String qu) {
		this.qu = qu;
	}
	/**
	 * 架
	 * @return
	 */
	public String getJia() {
		return jia;
	}
	/**
	 * 架
	 */
	public void setJia(String jia) {
		this.jia = jia;
	}
	/**
	 * 位
	 * @return
	 */
	public String getWei() {
		return wei;
	}
	/**
	 * 位
	 */
	public void setWei(String wei) {
		this.wei = wei;
	}
	/**
	 * 移库数量
	 * @return
	 */
	public String getShiftQuantity() {
		return shiftQuantity;
	}
	/**
	 * 移库数量
	 */
	public void setShiftQuantity(String shiftQuantity) {
		this.shiftQuantity = shiftQuantity;
	}

	public String getGoodstatus() {
		return goodstatus;
	}

	public void setGoodstatus(String goodstatus) {
		this.goodstatus = goodstatus;
	}

	public String getPurchasemode() {
		return purchasemode;
	}

	public void setPurchasemode(String purchasemode) {
		this.purchasemode = purchasemode;
	}

	public String getWeizhi() {
		return weizhi;
	}

	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}

	public String getSubjectsName() {
		return subjectsName;
	}

	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}

	public String getSubjectsID() {
		return subjectsID;
	}

	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}

	public String getDefaultStorage() {
		return defaultStorage;
	}

	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	public String getAppropriationmoney() {
		return appropriationmoney;
	}

	public void setAppropriationmoney(String appropriationmoney) {
		this.appropriationmoney = appropriationmoney;
	}

	public String getCashApplyBillsID() {
		return cashApplyBillsID;
	}

	public void setCashApplyBillsID(String cashApplyBillsID) {
		this.cashApplyBillsID = cashApplyBillsID;
	}
	public String getFinancialbillIDyh() {
		return financialbillIDyh;
	}

	public void setFinancialbillIDyh(String financialbillIDyh) {
		this.financialbillIDyh = financialbillIDyh;
	}

	public String getFinancialbillIDrk() {
		return financialbillIDrk;
	}

	public void setFinancialbillIDrk(String financialbillIDrk) {
		this.financialbillIDrk = financialbillIDrk;
	}

	public String getGoodsBillsID() {
		return goodsBillsID;
	}

	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	
	
}