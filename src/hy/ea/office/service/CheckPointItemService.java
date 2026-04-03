package hy.ea.office.service;

import java.util.List;

import hy.ea.bo.office.CarCheckPointItem;
import hy.plat.CoreManager;
import hy.plat.bo.PageForm;

public interface CheckPointItemService extends CoreManager<CarCheckPointItem>{

	
	
	/**
     * 分页
     * @param pageNo
     * @param pageSize
     * @param plan
     * @return
     */
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckPointItem pointitem);
	
	/**
	 * 按属性查找唯一对象.
	 */
	public CarCheckPointItem findUniqueBeanById(Object value);
	
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckPointItem> findByProperty(String property, Object value);
	
	public List<CarCheckPointItem> findBeanByPointId(Object value);
}
