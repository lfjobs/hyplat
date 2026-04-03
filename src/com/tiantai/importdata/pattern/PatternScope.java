package com.tiantai.importdata.pattern;

import com.tiantai.importdata.exception.NotMatchedException;
//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\PatternScope.java

/**
 * 
 * 范围限定模式
 *
 */
public class PatternScope extends PatternImpl 
{
   
   /**
    * @roseuid 52D5043400B8
    */
	@Override
	public boolean match(String value, int rowNum, int colNum) throws NotMatchedException {
		super.match(value, rowNum, colNum);
		return false; //TODO
	}
}
