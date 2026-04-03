package com.tiantai.importdata.validator;

import java.util.List;

import com.tiantai.importdata.exception.ValidateFaultException;
import com.tiantai.importdata.pattern.Pattern;
//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\Validator.java

/**
 * 每个Field字段可能需要若干数据教养的校验器(即Validator),每个Validator实际上Pattern(可能是多个)的容器
 * @author zhb
 *
 */
public interface Validator 
{
   
   /**
    * @return Validator
    * @roseuid 52D4F90F01BE
    */
   public Validator setNext(Validator next);
   
   /**
    * @param value
    * @param rowNum
    * @param colNum
    * @roseuid 52D4FA03020E
    */
   public void chainAfterDeal(String value, int rowNum, int colNum) throws ValidateFaultException;
   
   /**
    * @param value
    * @param rowNum
    * @param colNum
    * @throws ValidateFaultException 
    * @roseuid 52D4FC4003E7
    */
   public void validator(String value, int rowNum, int colNum) throws ValidateFaultException;
   
   public void setPatterns(List<Pattern> patterns);   
   
   public List<Pattern> getPatterns();
   
   public void setName(String name);
}
