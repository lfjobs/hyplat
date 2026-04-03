package com.tiantai.importdata.entity;

import java.util.List;

import com.tiantai.importdata.validator.Validator;
/**
 * 
 * meta文件中每个field,是需要导入的(由needImport属性值决定)
 *
 */
public class TemplateField {
	
	private int order;//表示该field对应在excel文件中的第几列
	private String name;//表示导入数据文件(excel)中的每列的title
	private String column;//如果Entity中table起效，那么column表示这一列对应到数据库表中的字段名
	private String property;//如果Entity中className起效，那么property表示该class的属性名
	private String comment;//对该列导入数据的注解
	private boolean needImport;//是否要导入这一列数据
	private List<Validator> validators;//每个Field都是由若干个Validator组成
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public List<Validator> getValidators() {
		return validators;
	}
	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public boolean getNeedImport() {
		return needImport;
	}
	public void setNeedImport(boolean needImport) {
		this.needImport = needImport;
	}	
}
