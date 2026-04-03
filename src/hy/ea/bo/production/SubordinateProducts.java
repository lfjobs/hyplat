package hy.ea.bo.production;

/**
 * 下级所属产品
 * @author zj
 */
public class SubordinateProducts {
	private String subProductKey;
	private String subProductID;
	private String fieldConID;						//信息字段ID
	private String fieldPpID;							//所属字段ID
	private String fieldPpName;					//所属字段名称
	private String textID;								//内容所属ID
	private String content;								//内容
	private String primaryField;					//一级格式
	private String twoLevelField;				//二级格式
	public String getSubProductKey() {
		return subProductKey;
	}
	public void setSubProductKey(String subProductKey) {
		this.subProductKey = subProductKey;
	}
	public String getSubProductID() {
		return subProductID;
	}
	public void setSubProductID(String subProductID) {
		this.subProductID = subProductID;
	}
	public String getFieldConID() {
		return fieldConID;
	}
	public void setFieldConID(String fieldConID) {
		this.fieldConID = fieldConID;
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
	public String getTextID() {
		return textID;
	}
	public void setTextID(String textID) {
		this.textID = textID;
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
	
	
}
