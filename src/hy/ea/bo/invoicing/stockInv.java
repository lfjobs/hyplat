package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 库存盘点表
 *
 */
public class stockInv implements BaseBean ,ExcelBean,java.io.Serializable{
	private String stockinvKey;           //主键
	private String stockinvID;            //业务主键
	private String companyID;          	   //公司  外键
	private String groupCompanySn;     	   //集团公司  外键
	
	private String goodsBillsId;			//物品单据ID      微分金流程下 现金、金币关联时 存储的可能是收支单据ID
	private String goodsID;               //物品外键
	private String goodsType;              //物品类别
	private String goodsName;              //物品名称
	private String invenQuantity;          //库存数量
	private String summoney;               //库存金额
	private String invenOnline;            //库存上线
	private String invenUnderline;         //库存下线
	private Date intime;				  //入库时间
	//private Date inDate;					//入库日期
	private String type;				//入库00 出库01 借出02 作废10
	private String staffID;				//物品使用人ID
	private String staffName;           //物品使用人姓名
	//2015-01-09 xyz新加
	private String warehouse;              //库房外键
	private String warehouseName;		   //库房名称
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","物品名称","物品类别","上月库存量","本月入库量","本月出库量","当前库存量","标准库存范围","库存预警","库存总金额"};
		return titles;
	}
	public static String[] columnHeading() {
		String[] titles = {"序号","物品名称","物品类别","日期","业务类型","数量","总金额"};
		return titles;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStockinvKey() {
		return stockinvKey;
	}
	public void setStockinvKey(String stockinvKey) {
		this.stockinvKey = stockinvKey;
	}
	public String getStockinvID() {
		return stockinvID;
	}
	public void setStockinvID(String stockinvID) {
		this.stockinvID = stockinvID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getInvenQuantity() {
		return invenQuantity;
	}
	public void setInvenQuantity(String invenQuantity) {
		this.invenQuantity = invenQuantity;
	}
	public String getSummoney() {
		return summoney;
	}
	public void setSummoney(String summoney) {
		this.summoney = summoney;
	}
	public String getInvenOnline() {
		return invenOnline;
	}
	public void setInvenOnline(String invenOnline) {
		this.invenOnline = invenOnline;
	}
	public String getInvenUnderline() {
		return invenUnderline;
	}
	public void setInvenUnderline(String invenUnderline) {
		this.invenUnderline = invenUnderline;
	}
	public Date getIntime() {
		return intime;
	}
	public void setIntime(Date intime) {
		this.intime = intime;
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

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getGoodsBillsId() {
		return goodsBillsId;
	}

	public void setGoodsBillsId(String goodsBillsId) {
		this.goodsBillsId = goodsBillsId;
	}

	/*public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}*/
}
