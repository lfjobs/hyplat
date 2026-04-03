package com.tiantai.importdata.entity;

import java.util.List;

//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\Entity.java
/**
 * 对应每个导入meta xml文件的entity
 * @author zhb
 *
 */
public class Entity {
	private String type;// 导入的业务数据的类型定义(自定义,以字母组合的方式)
	private String table;// 导入数据对应的数据库表(先考虑数据导入单表的情况)
	private String className;// 导入数据对应的class(和table属性比较，className更优先。)
	private List<TemplateField> templateFields;// 每个Entity都包含若干个Field
	private List<TableField> tableField;// 每个Entity都包含若干个TableField

	/**
	 * @return java.util.List
	 * @roseuid 52D4F3210086
	 */
	public java.util.List<TemplateField> getFields() {
		return templateFields;
	}

	/**
	 * @param list
	 * @roseuid 52D4F3730265
	 */
	public void setFields(java.util.List<TemplateField> templateFields) {
		this.templateFields = templateFields;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public List<TableField> getTableField() {
		return tableField;
	}

	public void setTableField(List<TableField> tableField) {
		this.tableField = tableField;
	}

}
