package com.tiantai.nwa.tbank.bo;

import hy.plat.bo.BaseBean;
/**
 * 农业银行字典Bean
 * @author zhb
 *
 */
public class AbcDictBean implements BaseBean {
	private String name;	
	private String code;
	
	public AbcDictBean(String name, String code)
	{
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
