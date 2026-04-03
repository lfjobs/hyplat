package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;


public interface  MessageComService {
	
	
	public TEshopCusCom getCusCom(String user);


	public TEshopCustomer getCustomer(String user);
}
