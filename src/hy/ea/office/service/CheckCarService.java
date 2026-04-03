package hy.ea.office.service;

import hy.ea.bo.office.CarInformation;
import hy.plat.CoreManager;
import hy.plat.bo.PageForm;

import java.util.List;



public interface CheckCarService extends CoreManager<CarInformation> {
	
	
	public PageForm getPageForm(int pageNo, int pageSize,CarInformation car);
	
	
	public PageForm getPageFormByPoint(int pageNo, int pageSize, CarInformation car);

	
	
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarInformation> findByProperty(String property, Object value);
	
}
