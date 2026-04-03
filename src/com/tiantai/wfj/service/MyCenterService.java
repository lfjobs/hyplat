package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCard;

import java.util.List;


public interface MyCenterService {
	
	
	public TEshopCusCom getCusCom(String user);


	public TEshopCustomer getCustomer(String user);
	
	
    public  Object getUserInfo(String user);


	public void editInfo(Staff staff,String sccid);


	public  String updatePsw(String account,String pswtype,String yspsw,String newpsw);

	public List<Object> getMenuInfo(String account, String staffid, Integer rNumber);

	void editSatffCardInfo(StaffCard staffCard);
}
