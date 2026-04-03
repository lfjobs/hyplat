package com.tiantai.telrec.tool;

import java.util.HashMap;

public class Parameters {
	@SuppressWarnings("rawtypes")
	private static HashMap customerMap = new HashMap();

	@SuppressWarnings("rawtypes")
	public static HashMap getCustomerMap() {
		return customerMap;
	}
}
