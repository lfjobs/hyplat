package hy.ea.office.service;

import java.util.List;

import hy.ea.bo.office.CarCheckTask;
import hy.plat.CoreManager;

import hy.plat.bo.PageForm;



public interface CheckTaskService extends CoreManager<CarCheckTask> {
	
	
	public PageForm getPageForm(int pageNo, int pageSize,CarCheckTask task);
	
	/**
	 * 按ID查找唯一对象.
	 */
	public CarCheckTask findUniqueBeanById(Object value);
	
	
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckTask> findByProperty(String property, Object value);
	
	/**
	 * 获取未巡查的任务
	 * @param Principal 负责人
	 * @param CompanyId 负责人所属公司
	 * @return
	 */
	List<CarCheckTask> getNotCheckTask(String principal,String companyId);
}
