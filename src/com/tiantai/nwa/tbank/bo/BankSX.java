package com.tiantai.nwa.tbank.bo;

import hy.plat.bo.BaseBean;

/**
 * 银行缩写
 * @author zhb
 *
 */
public class BankSX implements BaseBean {
	
	private String name;
	private String esx;
	
	public BankSX()
	{}
	
	public BankSX(String name)
	{
		this.name = name;		
	}
	
	public BankSX(String name,String esx)
	{
		this.name = name;
		this.esx = esx;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEsx() {
		return esx;
	}
	public void setEsx(String esx) {
		this.esx = esx;
	}

}
