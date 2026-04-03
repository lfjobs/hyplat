package hy.ea.office.service;

import hy.ea.bo.office.vo.ViewCarcheckresults;
import hy.plat.CoreManager;
import hy.plat.bo.PageForm;

import java.util.List;

public interface CheckResultsService extends CoreManager<ViewCarcheckresults> {

	
	/**
     * 分页
     * @param pageNo
     * @param pageSize
     * @param plan
     * @return
     */
	public PageForm getPageForm(int pageNo, int pageSize, ViewCarcheckresults results);
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<ViewCarcheckresults> findByProperty(String property, Object value);
}
