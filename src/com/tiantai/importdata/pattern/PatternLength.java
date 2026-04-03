package com.tiantai.importdata.pattern;

import com.tiantai.importdata.exception.NotMatchedException;

import hy.ea.util.StringUtil;
//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\PatternLength.java
/**
 * 
 * 长度限制模式
 */
public class PatternLength extends PatternImpl {
	
	private int maxLength;

	/**
	 * @roseuid 52D50434006A
	 */
	public PatternLength() {}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	@Override
	public boolean match(String value, int rowNum, int colNum) throws NotMatchedException {		
		super.match(value,rowNum,colNum);		
		if (StringUtil.length(value)<=this.maxLength){
			return true;
		}else{
			throw new NotMatchedException(new Throwable(),"the value of " + value + " in Cell[" + (rowNum+1) + "," + (colNum+1) 
					+ "] is not match with maxlength=\"" + maxLength + "\".");			
		}
		
	}
}
