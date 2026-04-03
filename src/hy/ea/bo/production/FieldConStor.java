package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

public class FieldConStor implements BaseBean{
	private String fieldConKey;
	private String fieldConID;
	private String ppID;									//产品ID
	private String goodsName;					//产品名称
	private String fieldPpID;							//产品下字段ID
	private String fieldPpName;					//产品下字段名称
	private String textID;								//内容所属ID
	private String content;								//内容
	private String primaryField;					//一级格式
	private String twoLevelField;				//二级格式
	private String cashierBillsID;					//单据ID
	public String getFieldConKey() {
		return fieldConKey;
	}
	public void setFieldConKey(String fieldConKey) {
		this.fieldConKey = fieldConKey;
	}
	public String getFieldConID() {
		return fieldConID;
	}
	public void setFieldConID(String fieldConID) {
		this.fieldConID = fieldConID;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public String getFieldPpID() {
		return fieldPpID;
	}
	public void setFieldPpID(String fieldPpID) {
		this.fieldPpID = fieldPpID;
	}
	public String getFieldPpName() {
		return fieldPpName;
	}
	public void setFieldPpName(String fieldPpName) {
		this.fieldPpName = fieldPpName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPrimaryField() {
		return primaryField;
	}
	public void setPrimaryField(String primaryField) {
		this.primaryField = primaryField;
	}
	public String getTwoLevelField() {
		return twoLevelField;
	}
	public void setTwoLevelField(String twoLevelField) {
		this.twoLevelField = twoLevelField;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTextID() {
		return textID;
	}
	public void setTextID(String textID) {
		this.textID = textID;
	}
	
}
