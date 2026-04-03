package com.tiantai.importdata.factory;

import com.tiantai.importdata.pattern.Pattern;
import com.tiantai.importdata.pattern.PatternEnum;
import com.tiantai.importdata.pattern.PatternLength;
import com.tiantai.importdata.pattern.PatternMatch;
import com.tiantai.importdata.pattern.PatternScope;
import com.tiantai.importdata.validator.Validator;
import com.tiantai.importdata.validator.ValidatorBoolean;
import com.tiantai.importdata.validator.ValidatorDecimal;
import com.tiantai.importdata.validator.ValidatorEnum;
import com.tiantai.importdata.validator.ValidatorInt;
import com.tiantai.importdata.validator.ValidatorRequired;
import com.tiantai.importdata.validator.ValidatorString;
/**
 * 创建 Validator和Pattern的工厂实现类
 * @author zhb
 *
 */
public class FactoryImpl extends Factory {

	@Override
	public Pattern createPattern(String type) {
		Pattern pattern = null;
		if (type!=null && !"".equals(type.trim())){
			if ("length".equals(type.trim())) pattern = new PatternLength();
			if ("scope".equals(type.trim())) pattern = new PatternScope();
			if ("match".equals(type.trim())) pattern = new PatternMatch();
			if ("Enum".equals(type.trim())) pattern = new PatternEnum();
		}
		return pattern;
	}
	
	@Override
	public Validator createValidator(String name) {
		Validator validator = null;
		if (name!=null && !"".equals(name.trim())){
			if ("Required".equals(name.trim())) validator = new ValidatorRequired();
			if ("String".equals(name.trim())) validator = new ValidatorString();
			if ("Int".equals(name.trim())) validator = new ValidatorInt();
			if ("Decimal".equals(name.trim())) validator = new ValidatorDecimal();
			if ("Enum".equals(name.trim())) validator = new ValidatorEnum();
			if ("Boolean".equals(name.trim())) validator = new ValidatorBoolean();
		}		
		return validator;
	}

}
