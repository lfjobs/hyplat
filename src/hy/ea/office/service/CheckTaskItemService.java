package hy.ea.office.service;

import hy.ea.bo.office.CarCheckTaskItem;
import hy.plat.CoreManager;

import java.util.List;

public interface CheckTaskItemService extends CoreManager<CarCheckTaskItem>{
	/**
	 * 根据任务id获取任务项
	 * @return
	 */
    List<CarCheckTaskItem> getListByTaskId(String taskId);
    
    
    /**
	 * 按属性查找唯一对象.
	 */
	public CarCheckTaskItem findUniqueBeanById(Object value);
	
   
    
    
    /**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckTaskItem> findByProperty(String property, Object value);
	
	
	public CarCheckTaskItem findUnique(String hql, Object... values);
}
