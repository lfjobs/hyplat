package com.tiantai.importdata.entity;

/**
 * 
 * meta文件中每个tableField,即这些字段不导入，或不能预先赋值，需要在导入其他数据的同时，获取或生成一些数据值
 *
 */
public class TableField {
	
	private String name;
	private String column;
	private String property;
	private String comment;
	private Generator generator;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	
}
