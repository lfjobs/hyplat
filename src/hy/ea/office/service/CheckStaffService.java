package hy.ea.office.service;

import hy.ea.bo.human.Staff;
import hy.plat.CoreManager;



public interface CheckStaffService extends CoreManager<Staff> {
	
	/**
	 * 按属性查找唯一对象.
	 */
	public Staff findUniqueBeanById(Object value);
	

	
}
