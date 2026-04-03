package com.tiantai.telrec.service;

import java.util.List;

public interface ClientMissCalledService {
	@SuppressWarnings("rawtypes")
	public List getMissedCallForUserId(String date1, String date2, String userid);
	@SuppressWarnings("rawtypes")
	public List getMissedCallForAll(String date1, String date2, String companyid);
}
