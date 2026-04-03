package com.tiantai.importdata.pattern;

import com.tiantai.importdata.exception.NotMatchedException;

//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\Pattern.java

/**
 * 
 * 定义每个field需要匹配的模式
 */
public interface Pattern 
{
   
   /**
    * @return boolean
    * @roseuid 52D5010B01C7
    */
   public boolean match(String value, int rowNum, int colNum) throws NotMatchedException;
}
