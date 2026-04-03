package com.tiantai.importdata.util;

public class FunctionUtil {
	
	/***************************************
	 * 将指定英文字符串首字母大写
	 * 
	 * @param filed
	 * @return 首字母大写后的字符串
	 */
	public static String A2UpperCase(String filed) {
		return filed.substring(0, 1).toUpperCase()
				+ filed.substring(1, filed.length());
	}

}
