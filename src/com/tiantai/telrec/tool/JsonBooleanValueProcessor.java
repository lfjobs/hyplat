package com.tiantai.telrec.tool;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonBooleanValueProcessor implements JsonValueProcessor {

	private String trueValue = "是";
	private String falseValue = "否";

	public String getTrueValue() {
		return trueValue;
	}

	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	public String getFalseValue() {
		return falseValue;
	}

	public void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		return null;
	}

	@SuppressWarnings("unused")
	private Object process(Object value, JsonConfig jsonConfig) {
		boolean val = (Boolean) value;
		if (val) {
			return this.trueValue;
		} else {
			return this.falseValue;
		}
	}

	public JsonBooleanValueProcessor() {

	}

	public JsonBooleanValueProcessor(String trueValue, String falseValue) {
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}
}
