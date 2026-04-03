package hy.ea.office.service;

import java.util.List;

import hy.ea.bo.office.CarCheckPlan;
import hy.plat.CoreManager;
import hy.plat.bo.PageForm;

public interface CheckPlanService extends CoreManager<CarCheckPlan>{
	
    /**
     * 分页
     * @param pageNo
     * @param pageSize
     * @param plan
     * @return
     */
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckPlan plan);
	

	
	/**
	 * 按属性查找唯一对象.
	 */
	public CarCheckPlan findUniqueBeanById(Object value);
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckPlan> findByProperty(String property, Object value);

}
