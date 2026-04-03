package com.tiantai.importdata.validator;

import java.util.List;

import com.tiantai.importdata.exception.ValidateFaultException;
import com.tiantai.importdata.pattern.Pattern;

//Source file: D:\\MyEclipse 8.5\\Workspace\\Test\\src\\com\\tiantai\\ValidatorImpl.java

public class ValidatorImpl implements Validator {

	private List<Pattern> patterns;
	private String name;
	private Validator next;

	/**
	 * @roseuid 52D5043300B9
	 */
	public ValidatorImpl() {

	}

	/**
	 * @return Validator
	 * @roseuid 52D5043300D8
	 */
	public Validator setNext(Validator next) {
		this.next = next;
		return next;
	}	
	
	/**
	    * @param value
	    * @param rowNum
	    * @param colNum
	    * @throws ValidateFaultException 
	    * @roseuid 52D4FA03020E
	    */
	   public void chainAfterDeal(String value, int rowNum, int colNum) throws ValidateFaultException{
		   validator(value,rowNum,colNum);
		   if (next != null)
		   {
			   next.chainAfterDeal(value, rowNum, colNum);
		   }
	   }

	/**
	 * @param value
	 * @param rowNum
	 * @param colNum
	 * @throws ValidateFaultException 
	 * @roseuid 52D504330220
	 */
	public void validator(String value, int rowNum, int colNum) throws ValidateFaultException {
		for (Pattern pattern : getPatterns()) {			
			pattern.match(value,rowNum,colNum);
		}
	}

	public void setPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
	}

	public List<Pattern> getPatterns() {
		return patterns;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
