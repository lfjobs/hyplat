package com.tiantai.importdata.pattern;

import com.tiantai.importdata.exception.NotMatchedException;

//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\PatternImpl.java

public class PatternImpl implements Pattern {

	private String type;
	private String value;//从validator传入的Field值
	private int rowNum;
	private int colNum;

	/**
	 * @roseuid 52D5043303D5
	 */
	public PatternImpl() {
	}

	/**
	 * @return boolean
	 * @roseuid 52D50434001C
	 */
	public boolean match(String value, int rowNum, int colNum)  throws NotMatchedException{
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	
	
}
