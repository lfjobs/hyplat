package com.tiantai.importdata.util;

import java.text.SimpleDateFormat;
/**
 * excel文件的读取配置定义
 * @author zhb
 *
 */
public class ExcelConfig {
	
	private int beginRowNum;
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	
	public ExcelConfig(int beginRowNum) {
		this.beginRowNum = beginRowNum;
	}

	public int getBeginRowNum() {
		return beginRowNum;
	}

	public void setBeginRowNum(int beginRowNum) {
		this.beginRowNum = beginRowNum;
	}

	public SimpleDateFormat getFormater() {
		return formater;
	}

	public void setFormater(SimpleDateFormat formater) {
		this.formater = formater;
	}  

}
