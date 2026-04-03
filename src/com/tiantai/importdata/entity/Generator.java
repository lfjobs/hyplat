package com.tiantai.importdata.entity;
/**
 * 
 * meta文件中每个tableField中的generator
 *
 */
public class Generator {
	
	private String type;
	private String prefix;	
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
