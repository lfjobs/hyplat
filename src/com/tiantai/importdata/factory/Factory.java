package com.tiantai.importdata.factory;

import com.tiantai.importdata.pattern.Pattern;
import com.tiantai.importdata.validator.Validator;
/**
 * 创建 Validator和Pattern的工厂
 * @author zhb
 * @see com.tiantai.importdata.validator.Validator 
 * @see com.tiantai.importdata.pattern.Pattern
 */
public abstract class Factory {
	
	public abstract  Validator createValidator(String name);
	public abstract Pattern createPattern(String type);

}
