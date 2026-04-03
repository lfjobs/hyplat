package hy.ea.office.service;

import java.util.List;

import hy.ea.bo.office.CarCheckPoint;
import hy.ea.bo.office.CarCheckTask;
import hy.plat.CoreManager;
import hy.plat.bo.PageForm;

public interface CheckPointService  extends CoreManager<CarCheckPoint>{

	
	/**
     * 分页
     * @param pageNo
     * @param pageSize
     * @param plan
     * @return
     */
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckPoint point);
	
	
	/**
	 * 
	 * 根据sql分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param params
	 * @return
	 */
	public PageForm getPageForm(int pageNo, int pageSize, String sql,Object[] params,CarCheckPoint point);
	

	/**
	 * 按属性查找唯一对象.
	 */
	public CarCheckPoint findUniqueBeanById(Object value);
	
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckPoint> findByProperty(String property, Object value);
	
	
	/**
	 * 
	 * 根据任务项查询检查点里的信息
	 */
	
	public List<CarCheckPoint> getPointListByTask(CarCheckTask task);
}
