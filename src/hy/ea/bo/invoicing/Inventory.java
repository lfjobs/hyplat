package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 库存表
 *
 */
public class Inventory implements BaseBean ,ExcelBean,java.io.Serializable{
	private String inventoryKey;           //主键
	private String inventoryID;            //业务主键
	private String companyID;          	   //公司  外键
	private String groupCompanySn;     	   //集团公司  外键
	private String organizationID;		   //组织机构  外键
	private String departmentID;           //部门ID
	private String staffID;				   //责任人  外键
	private String staffName;			   //责任人名称
	private String warehouse;              //库房外键
	private String warehouseName;		   //库房名称
	private String goodsID;                //物品外键
	private String goodsType;              //物品类别
	private String goodsName;              //物品名称
	private String barcode;                //条码
	private String standard;               //规格
	private String goodsCoding;            //品名编号
	private String defaultStorage;         //芯片号
	private String unit;                   //单位
	private String unitPrice;              //单价
	private String purPrice;			   //进货价
	private String invenQuantity;          //库存数量
	private String preQuantity;  //预扣库存数量 收银
	private String badQuantity;            //报损数量
	private String goodstatus;             //状态00正常 01维修02报废
	private String sumPrice;               //总价
	private String subjectsName;           //科目名称
	private String subjectsID;             //科目ID
//--------------------------------------------------------------------------------------------
	private String invenOnline;            //库存上限
	private String invenUnderline;         //库存下限
	private String status;				   //状态	00 未 收货 01 已收货 02 已验货 03 已入库 04：市场调查 05:盘库 06:移库07出库 08入库失败 09出库审核10报损
	private String inventoryNum;           //序号
	private String weizhi;				   //库存位置
	private String area;                   //区外键(从库房中拿)
	private String frame;                  //架外键(从库房中拿)
	private String position;               //位外键(从库房中拿)
	
	private String areaName;               //区名称
	private String frameName;              //架名称
	private String positionName;           //位名称
	private String panduan;				   //用于判断是单选按钮还是图片
	private String productId;   			   //产品ID
	private String category;					//产品类别
	private String fiveClear;					//组织机构
	private String startDate;				//生产日期
	private String endDate;					//到期日期
	/*
	 * 财务库存模块
	 */
	private String source; 					//来源途径
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","库","区","架","位","物品类别","物品名称","条码","规格","单位","单价","库存数量","总价"};
		return titles;
	}

	public Inventory(String inventoryKey, String inventoryID, String companyID, String groupCompanySn, String organizationID, String departmentID, String staffID, String staffName, String warehouse, String warehouseName, String goodsID, String goodsType, String goodsName, String barcode, String standard, String goodsCoding, String defaultStorage, String unit, String unitPrice, String purPrice, String invenQuantity, String badQuantity, String goodstatus, String sumPrice, String subjectsName, String subjectsID, String invenOnline, String invenUnderline, String status, String inventoryNum, String weizhi, String area, String frame, String position, String areaName, String frameName, String positionName, String panduan, String productId, String category, String fiveClear, String startDate, String endDate, String source) {
		this.inventoryKey = inventoryKey;
		this.inventoryID = inventoryID;
		this.companyID = companyID;
		this.groupCompanySn = groupCompanySn;
		this.organizationID = organizationID;
		this.departmentID = departmentID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.warehouse = warehouse;
		this.warehouseName = warehouseName;
		this.goodsID = goodsID;
		this.goodsType = goodsType;
		this.goodsName = goodsName;
		this.barcode = barcode;
		this.standard = standard;
		this.goodsCoding = goodsCoding;
		this.defaultStorage = defaultStorage;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.purPrice = purPrice;
		this.invenQuantity = invenQuantity;
		this.badQuantity = badQuantity;
		this.goodstatus = goodstatus;
		this.sumPrice = sumPrice;
		this.subjectsName = subjectsName;
		this.subjectsID = subjectsID;
		this.invenOnline = invenOnline;
		this.invenUnderline = invenUnderline;
		this.status = status;
		this.inventoryNum = inventoryNum;
		this.weizhi = weizhi;
		this.area = area;
		this.frame = frame;
		this.position = position;
		this.areaName = areaName;
		this.frameName = frameName;
		this.positionName = positionName;
		this.panduan = panduan;
		this.productId = productId;
		this.category = category;
		this.fiveClear = fiveClear;
		this.startDate = startDate;
		this.endDate = endDate;
		this.source = source;
	}

	public Inventory(){
		super();
	}

	/**
	 * 主键
	 * @return
	 */
	public String getInventoryKey() {
		return inventoryKey;
	}
	/**
	 * 主键
	 * @param inventoryKey
	 */
	public void setInventoryKey(String inventoryKey) {
		this.inventoryKey = inventoryKey;
	}
	
	/**
	 * 业务主键
	 * @return
	 */
	public String getInventoryID() {
		return inventoryID;
	}
	/**
	 * 业务主键
	 * @param inventoryID
	 */
	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
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
	 * 集团公司  外键
	 * @return
	 */
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	/**
	 * 集团公司  外键
	 * @param groupCompanySn
	 */
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	
	/**
	 * 序号
	 * @return
	 */
	public String getInventoryNum() {
		return inventoryNum;
	}
	/**
	 * 序号
	 * @param inventoryNum
	 */
	public void setInventoryNum(String inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	
	/**
	 * 库房(从库房中拿)
	 * @return
	 */
	public String getWarehouse() {
		return warehouse;
	}
	/**
	 * 库房(从库房中拿)
	 * @param warehouse
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	/**
	 * 区(从库房中拿)
	 * @return
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 区(从库房中拿)
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * 架(从库房中拿)
	 * @return
	 */
	public String getFrame() {
		return frame;
	}
	/**
	 * 架(从库房中拿)
	 * @param frame
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}
	
	/**
	 * 位(从库房中拿)
	 * @return
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * 位(从库房中拿)
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * 物品类别
	 * @return
	 */
	public String getGoodsType() {
		return goodsType;
	}
	/**
	 * 物品类别
	 * @param goodsType
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * 物品名称
	 * @return
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 物品名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 条码
	 * @return
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * 条码
	 * @param barcode
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	/**
	 * 规格
	 * @return
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * 规格
	 * @param standard
	 */
	public void setStandard(String standard) {
		this.standard = standard;
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
	 * 库存数量
	 * @return
	 */
	public String getInvenQuantity() {
		return invenQuantity;
	}
	/**
	 * 库存数量
	 * @param invenQuantity
	 */
	public void setInvenQuantity(String invenQuantity) {
		this.invenQuantity = invenQuantity;
	}
	
	/**
	 * 总价
	 * @return
	 */
	public String getSumPrice() {
		return sumPrice;
	}
	/**
	 * 总价
	 * @param sumPrice
	 */
	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	/**
	 * 库存上线
	 * @return
	 */
	public String getInvenOnline() {
		return invenOnline;
	}
	/**
	 * 库存上线
	 * @param invenOnline
	 */
	public void setInvenOnline(String invenOnline) {
		this.invenOnline = invenOnline;
	}
	
	/**
	 * 库存下线
	 * @return
	 */
	public String getInvenUnderline() {
		return invenUnderline;
	}
	/**
	 * 库存下线
	 * @param invenUnderline
	 */
	public void setInvenUnderline(String invenUnderline) {
		this.invenUnderline = invenUnderline;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
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

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getBadQuantity() {
		return badQuantity;
	}

	public void setBadQuantity(String badQuantity) {
		this.badQuantity = badQuantity;
	}

	public String getGoodstatus() {
		return goodstatus;
	}

	public void setGoodstatus(String goodstatus) {
		this.goodstatus = goodstatus;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPanduan() {
		return panduan;
	}

	public void setPanduan(String panduan) {
		this.panduan = panduan;
	}

	public String getWeizhi() {
		return weizhi;
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

	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}

	public String getDefaultStorage() {
		return defaultStorage;
	}

	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	public String getGoodsCoding() {
		return goodsCoding;
	}

	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getPurPrice() {
		return purPrice;
	}

	public void setPurPrice(String purPrice) {
		this.purPrice = purPrice;
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

	public String getPreQuantity() {
		return preQuantity;
	}

	public void setPreQuantity(String preQuantity) {
		this.preQuantity = preQuantity;
	}

	@Override
	public String toString() {
		return "\"Inventory\":{" +
				"\"inventoryKey\":\"" +inventoryKey+ '\"' +
				", \"inventoryID\":\"" +inventoryID+ '\"' +
				", \"companyID\":\"" +companyID+ '\"' +
				", \"groupCompanySn\":\"" +groupCompanySn+ '\"' +
				", \"organizationID\":\"" +organizationID+ '\"' +
				", \"departmentID\":\"" +departmentID+ '\"' +
				", \"staffID\":\"" +staffID+ '\"' +
				", \"staffName\":\"" +staffName+ '\"' +
				", \"warehouse\":\"" +warehouse+ '\"' +
				", \"warehouseName\":\"" +warehouseName+ '\"' +
				", \"goodsID\":\"" +goodsID+ '\"' +
				", \"goodsType\":\"" +goodsType+ '\"' +
				", \"goodsName\":\"" +goodsName+ '\"' +
				", \"barcode\":\"" +barcode+ '\"' +
				", \"standard\":\"" +standard+ '\"' +
				", \"goodsCoding\":\"" +goodsCoding+ '\"' +
				", \"defaultStorage\":\"" +defaultStorage+ '\"' +
				", \"unit\":\"" +unit+ '\"' +
				", \"unitPrice\":\"" +unitPrice+ '\"' +
				", \"purPrice\":\"" +purPrice+ '\"' +
				", \"invenQuantity\":\"" +invenQuantity+ '\"' +
				", \"preQuantity\":\"" +preQuantity+ '\"' +
				", \"badQuantity\":\"" +badQuantity+ '\"' +
				", \"goodstatus\":\"" +goodstatus+ '\"' +
				", \"sumPrice\":\"" +sumPrice+ '\"' +
				", \"subjectsName\":\"" +subjectsName+ '\"' +
				", \"subjectsID\":\"" +subjectsID+ '\"' +
				", \"invenOnline\":\"" +invenOnline+ '\"' +
				", \"invenUnderline\":\"" +invenUnderline+ '\"' +
				", \"status\":\"" +status+ '\"' +
				", \"inventoryNum\":\"" +inventoryNum+ '\"' +
				", \"weizhi\":\"" +weizhi+ '\"' +
				", \"area\":\"" +area+ '\"' +
				", \"frame\":\"" +frame+ '\"' +
				", \"position\":\"" +position+ '\"' +
				", \"areaName\":\"" +areaName+ '\"' +
				", \"frameName\":\"" +frameName+ '\"' +
				", \"positionName\":\"" +positionName+ '\"' +
				", \"panduan\":\"" +panduan+ '\"' +
				", \"productId\":\"" +productId+ '\"' +
				", \"category\":\"" +category+ '\"' +
				", \"fiveClear\":\"" +fiveClear+ '\"' +
				", \"startDate\":\"" +startDate+ '\"' +
				", \"endDate\":\"" +endDate+ '\"' +
				", \"source\":\"" +source+ '\"' +
				"},";
	}
}
