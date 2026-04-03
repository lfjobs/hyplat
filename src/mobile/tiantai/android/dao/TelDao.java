package mobile.tiantai.android.dao;

import hy.ea.bo.human.StaffContact;

import com.tiantai.wfj.bo.TEshopCusCom;

public interface TelDao {
	/**
	 * @param account手机号
	 * @param tel 选择的联系人
	 */
	void updateTel (StaffContact sc);
	
	/**
	 * @param account手机号
	 * @return TEshopCustomer对象
	 */
	TEshopCusCom getCompanyID(String account);
	
	/**
	 * @param account 手机号
	 * @param staffID 
	 * @return StaffContact对象
	 */
	StaffContact getAccount(String account,String staffID);
}
