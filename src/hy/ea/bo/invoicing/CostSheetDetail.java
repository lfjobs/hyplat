package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class CostSheetDetail implements BaseBean,ExcelBean,Cloneable,java.io.Serializable{

	private String csdKey;					//主键
	private String csdID;					//逻辑主键
	private String csbID;       			//主单据外键
	private String goodsNum;              	//品名编号
	private String goodsName;             	//品名名称
	private String csdType;					//产品类别    公司内部（00）  市场外部（01） 
	private String unitPrice;          	//单价
	private String quantity;              	//数量
	private String amount;                	//总金额
	
	//按照需求填写新字段
	private Date startDate;// 款源日期
	private Date endDate;// 报账日期
	private String batchNumber;// 批号或期号
	private Date periodDate;// 有效日期
	private String subjectsID;// 科目管理ID
	private String subjectsName;// 科目管理名称
	private String reasonThing;//事由
	private String costType;// 费用类别
	private String weight;// 重量
	private String boxStandard;// 箱规格
	private String priceManage;// 单价管理
	private String depotType;// 库房类型
	private String depotID;// 物品所在库房
	private String depotName;// 库房名称
	private String loan;// 借方金额
	private String forLoan;// 贷方金额
	private String direction;// 方向
	private String balance;// 余额
	private String typeID;
	private String mnemonicCode;
	private String model;
	private String standard;
	private String unit;
	private String goodsID;					//物品外键
	private String ccompanyName;//往来单位
	private String ccompanyID;//往来单位ID
	private String connectID;//往来个人ID
	private String connectName;//往来个人name 
	
	private String isSelected;//比价后是否被选中 空为没比的，00未选中的，01选中的
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "产品名称","产品编号", "部门", "责任人", "产品类型","产品预算单价","产品数量","产品预算总价","项目编号","制单日期","实际工作时间","产品类别","项目状态"};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneCostSheetDetail() throws CloneNotSupportedException{
		
		return this.clone();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((batchNumber == null) ? 0 : batchNumber.hashCode());
		result = prime * result
				+ ((boxStandard == null) ? 0 : boxStandard.hashCode());
		result = prime * result
				+ ((ccompanyID == null) ? 0 : ccompanyID.hashCode());
		result = prime * result
				+ ((ccompanyName == null) ? 0 : ccompanyName.hashCode());
		result = prime * result
				+ ((connectID == null) ? 0 : connectID.hashCode());
		result = prime * result
				+ ((connectName == null) ? 0 : connectName.hashCode());
		result = prime * result
				+ ((costType == null) ? 0 : costType.hashCode());
		result = prime * result + ((csbID == null) ? 0 : csbID.hashCode());
		result = prime * result + ((csdID == null) ? 0 : csdID.hashCode());
		result = prime * result + ((csdKey == null) ? 0 : csdKey.hashCode());
		result = prime * result + ((csdType == null) ? 0 : csdType.hashCode());
		result = prime * result + ((depotID == null) ? 0 : depotID.hashCode());
		result = prime * result
				+ ((depotName == null) ? 0 : depotName.hashCode());
		result = prime * result
				+ ((depotType == null) ? 0 : depotType.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((forLoan == null) ? 0 : forLoan.hashCode());
		result = prime * result + ((goodsID == null) ? 0 : goodsID.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result
				+ ((goodsNum == null) ? 0 : goodsNum.hashCode());
		result = prime * result + ((loan == null) ? 0 : loan.hashCode());
		result = prime * result
				+ ((mnemonicCode == null) ? 0 : mnemonicCode.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result
				+ ((periodDate == null) ? 0 : periodDate.hashCode());
		result = prime * result
				+ ((priceManage == null) ? 0 : priceManage.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((reasonThing == null) ? 0 : reasonThing.hashCode());
		result = prime * result
				+ ((standard == null) ? 0 : standard.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((subjectsID == null) ? 0 : subjectsID.hashCode());
		result = prime * result
				+ ((subjectsName == null) ? 0 : subjectsName.hashCode());
		result = prime * result + ((typeID == null) ? 0 : typeID.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result
				+ ((unitPrice == null) ? 0 : unitPrice.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CostSheetDetail other = (CostSheetDetail) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (batchNumber == null) {
			if (other.batchNumber != null)
				return false;
		} else if (!batchNumber.equals(other.batchNumber))
			return false;
		if (boxStandard == null) {
			if (other.boxStandard != null)
				return false;
		} else if (!boxStandard.equals(other.boxStandard))
			return false;
		if (ccompanyID == null) {
			if (other.ccompanyID != null)
				return false;
		} else if (!ccompanyID.equals(other.ccompanyID))
			return false;
		if (ccompanyName == null) {
			if (other.ccompanyName != null)
				return false;
		} else if (!ccompanyName.equals(other.ccompanyName))
			return false;
		if (connectID == null) {
			if (other.connectID != null)
				return false;
		} else if (!connectID.equals(other.connectID))
			return false;
		if (connectName == null) {
			if (other.connectName != null)
				return false;
		} else if (!connectName.equals(other.connectName))
			return false;
		if (costType == null) {
			if (other.costType != null)
				return false;
		} else if (!costType.equals(other.costType))
			return false;
		if (csbID == null) {
			if (other.csbID != null)
				return false;
		} else if (!csbID.equals(other.csbID))
			return false;
		if (csdID == null) {
			if (other.csdID != null)
				return false;
		} else if (!csdID.equals(other.csdID))
			return false;
		if (csdKey == null) {
			if (other.csdKey != null)
				return false;
		} else if (!csdKey.equals(other.csdKey))
			return false;
		if (csdType == null) {
			if (other.csdType != null)
				return false;
		} else if (!csdType.equals(other.csdType))
			return false;
		if (depotID == null) {
			if (other.depotID != null)
				return false;
		} else if (!depotID.equals(other.depotID))
			return false;
		if (depotName == null) {
			if (other.depotName != null)
				return false;
		} else if (!depotName.equals(other.depotName))
			return false;
		if (depotType == null) {
			if (other.depotType != null)
				return false;
		} else if (!depotType.equals(other.depotType))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (forLoan == null) {
			if (other.forLoan != null)
				return false;
		} else if (!forLoan.equals(other.forLoan))
			return false;
		if (goodsID == null) {
			if (other.goodsID != null)
				return false;
		} else if (!goodsID.equals(other.goodsID))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (goodsNum == null) {
			if (other.goodsNum != null)
				return false;
		} else if (!goodsNum.equals(other.goodsNum))
			return false;
		if (loan == null) {
			if (other.loan != null)
				return false;
		} else if (!loan.equals(other.loan))
			return false;
		if (mnemonicCode == null) {
			if (other.mnemonicCode != null)
				return false;
		} else if (!mnemonicCode.equals(other.mnemonicCode))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (periodDate == null) {
			if (other.periodDate != null)
				return false;
		} else if (!periodDate.equals(other.periodDate))
			return false;
		if (priceManage == null) {
			if (other.priceManage != null)
				return false;
		} else if (!priceManage.equals(other.priceManage))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (reasonThing == null) {
			if (other.reasonThing != null)
				return false;
		} else if (!reasonThing.equals(other.reasonThing))
			return false;
		if (standard == null) {
			if (other.standard != null)
				return false;
		} else if (!standard.equals(other.standard))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (subjectsID == null) {
			if (other.subjectsID != null)
				return false;
		} else if (!subjectsID.equals(other.subjectsID))
			return false;
		if (subjectsName == null) {
			if (other.subjectsName != null)
				return false;
		} else if (!subjectsName.equals(other.subjectsName))
			return false;
		if (typeID == null) {
			if (other.typeID != null)
				return false;
		} else if (!typeID.equals(other.typeID))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	/**
	 * 主键
	 * @return
	 */
	public String getCsdKey() {
		return csdKey;
	}
	/**
	 * 主键
	 */
	public void setCsdKey(String csdKey) {
		this.csdKey = csdKey;
	}
	/**
	 * 逻辑主键
	 * @return
	 */
	public String getCsdID() {
		return csdID;
	}
	/**
	 * 逻辑主键
	 */
	public void setCsdID(String csdID) {
		this.csdID = csdID;
	}
	/**
	 * 主单据外键
	 * @return
	 */
	public String getCsbID() {
		return csbID;
	}
	/**
	 * 主单据外键
	 */
	public void setCsbID(String csbID) {
		this.csbID = csbID;
	}
	/**
	 * 产品数量
	 * @return
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * 产品数量
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * 产品单价
	 * @return
	 */
	public String getUnitPrice() {
		return unitPrice;
	}
	/**
	 * 产品单价
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * 产品总金额
	 * @return
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 产品总金额
	 */
	public void setAmount(String amount) {
		this.amount = amount;
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
	 */
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	/**
	 * 产品编号
	 * @return
	 */
	public String getGoodsNum() {
		return goodsNum;
	}
	/**
	 * 产品编号
	 * @param goodsNum
	 */
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	/**
	 * 产品名称
	 * @return
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 产品名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 产品类别
	 * @return
	 */
	public String getCsdType() {
		return csdType;
	}
	/**
	 * 产品类别
	 * @param csdType
	 */
	public void setCsdType(String csdType) {
		this.csdType = csdType;
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
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Date getPeriodDate() {
		return periodDate;
	}
	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getReasonThing() {
		return reasonThing;
	}
	public void setReasonThing(String reasonThing) {
		this.reasonThing = reasonThing;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBoxStandard() {
		return boxStandard;
	}
	public void setBoxStandard(String boxStandard) {
		this.boxStandard = boxStandard;
	}
	public String getPriceManage() {
		return priceManage;
	}
	public void setPriceManage(String priceManage) {
		this.priceManage = priceManage;
	}
	public String getDepotType() {
		return depotType;
	}
	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}
	public String getDepotID() {
		return depotID;
	}
	public void setDepotID(String depotID) {
		this.depotID = depotID;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
	public String getLoan() {
		return loan;
	}
	public void setLoan(String loan) {
		this.loan = loan;
	}
	public String getForLoan() {
		return forLoan;
	}
	public void setForLoan(String forLoan) {
		this.forLoan = forLoan;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getMnemonicCode() {
		return mnemonicCode;
	}
	public void setMnemonicCode(String mnemonicCode) {
		this.mnemonicCode = mnemonicCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCcompanyName() {
		return ccompanyName;
	}
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getConnectID() {
		return connectID;
	}
	public void setConnectID(String connectID) {
		this.connectID = connectID;
	}
	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
}
