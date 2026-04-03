package hy.plat.service.impl;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBean4websiteDao;
import hy.plat.service.BaseBean4websiteService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseBean4websiteServiceImpl implements BaseBean4websiteService {

	@Resource
	private BaseBean4websiteDao baseBean4websiteDao;

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteBeanByKey(Class clasz, String key) {
		baseBean4websiteDao.deleteBeanByKey(clasz, key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseBean getBeanByKey(Class clasz, String key) {
		return baseBean4websiteDao.getBeanByKey(clasz, key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<BaseBean> list(Class clasz) {
		return baseBean4websiteDao.list(clasz);
	}

	@Override
	public void save(BaseBean baseBean) {
		baseBean4websiteDao.save(baseBean);
	}

	@Override
	public void update(BaseBean baseBean) {
		baseBean4websiteDao.update(baseBean);
	}

	@Override
	public PageForm getPageForm(int pageNumber, int pageSize, String hql,
			Object[] params) {

		int count = baseBean4websiteDao.getConutByByHqlAndParams("select count(*) "
				+ hql, params); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBean4websiteDao
				.getConutByByHqlAndParamsAndPage(hql, params, firstResult,
						maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	@Override
	public List<BaseBean> getListBeanByHqlAndParams(String hql, Object[] params) {
		return baseBean4websiteDao.getListBeanByHqlAndParams(hql, params);
	}

	@Override
	public int getConutByByHqlAndParams(String hql, Object[] params) {
		return baseBean4websiteDao.getConutByByHqlAndParams(hql, params);
	}
	@Override
	public int getConutByBySqlAndParams(String sql, Object[] params) {		
		return baseBean4websiteDao.getConutByBySqlAndParams(sql, params);
	}
	@Override
	public BaseBean getBeanByHqlAndParams(String hql, Object[] params) {
		return baseBean4websiteDao.getBeanByHqlAndParams(hql, params);
	}

	@Override
	public Object getObjectByHqlAndParams(String hql, Object[] params) {
		return baseBean4websiteDao.getObjectByHqlAndParams(hql, params);
	}
	
	@Override
	public Object getObjectBySqlAndParams(String sql, Object[] params) {
		return baseBean4websiteDao.getObjectBySqlAndParams(sql, params);
	}
	@Override
	public List<BaseBean> getListByDC(DetachedCriteria dc) {
		return baseBean4websiteDao.getListByDC(dc);
	}

	/**
	 * 执行同一参数的多条hql语句
	 * 
	 * @param hql
	 * @param params
	 */

	@Override
	public PageForm getPageFormByDC(int pageNumber, int pageSize,
			DetachedCriteria dc) {
		int count = baseBean4websiteDao.getConutByDC(dc); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBean4websiteDao.getListFormByDC(dc,
				firstResult, maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	@Override
	public PageForm getPageForm(int pageNumber, int pageSize, String hql,
			String hqlcount, Object[] params) {
		int count = baseBean4websiteDao.getConutByByHqlAndParams(hqlcount, params); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBean4websiteDao
				.getConutByByHqlAndParamsAndPage(hql, params, firstResult,
						maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	@Override
	public void saveBeansListAndexecuteHqlsByParams(
			List<BaseBean> baseBeansList, String[] hqls, Object[] params) {		
		baseBean4websiteDao.saveBeansListAndexecuteHqlsByParams(baseBeansList, hqls,
				params);
	}

	@Override
	public void executeHqlsByParamsList(List<BaseBean> beans, String[] hqls,
			List<Object[]> parmsList) {		
		baseBean4websiteDao.executeHqlsByParmsList(beans, hqls, parmsList);
	}

	@Override
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
			String sqlcount, Object[] params) {
		int count = baseBean4websiteDao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBean4websiteDao
				.getConutByBySqlAndParamsAndPage(sql, params, firstResult,
						maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListBeanBySqlAndParams(String sql, Object[] params) {		
		return baseBean4websiteDao.getListBeanBySqlAndParams(sql, params);
	}
	@Override
	public void executeSqlsByParmsList(List<BaseBean> beans,String[] sqls,List<Object[]> parmsList){
		baseBean4websiteDao.executeSqlsByParmsList(beans, sqls, parmsList);
	}
	@Override
	public void saveBeansListAndexecuteSqlsByParams(List<BaseBean> baseBeansList,
			String[] sqls, Object[] params) {
		baseBean4websiteDao.saveBeansListAndexecuteSqlsByParams(baseBeansList, sqls, params);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public PageForm getListByNormal(int pageNow, int pageSize, String dt_table,String dt_column,
			String where_condition,Class clazz){
		return baseBean4websiteDao.getListByNormal(pageNow,pageSize,dt_table,dt_column,where_condition,clazz);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public PageForm getListByComplex(int pageNow, int pageSize, String select_sql,String count_sql,
			Class clazz){
		return baseBean4websiteDao.getListByComplex(pageNow,pageSize,select_sql,count_sql,clazz);
	}
}
