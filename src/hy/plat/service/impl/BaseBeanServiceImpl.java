package hy.plat.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.tiantai.wfj.bo.TEshopCustomer;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;

@Service
@Transactional
public class BaseBeanServiceImpl implements BaseBeanService {

	@Resource
	private BaseBeanDao baseBeanDao;

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteBeanByKey(Class clasz, String key) {
		baseBeanDao.deleteBeanByKey(clasz, key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseBean getBeanByKey(Class clasz, String key) {
		return baseBeanDao.getBeanByKey(clasz, key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<BaseBean> list(Class clasz) {
		return baseBeanDao.list(clasz);
	}

	@Override
	public void save(BaseBean baseBean) {
		baseBeanDao.save(baseBean);
	}
	@Override
	public void updateAndExecute(BaseBean baseBean) {
		baseBeanDao.updateAndExecute(baseBean);
	}

	@Override
	public void update(BaseBean baseBean) {
		baseBeanDao.update(baseBean);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getPageForm(int pageNumber, int pageSize, String hql,
			Object[] params) {

		int count = baseBeanDao.getConutByByHqlAndParams("select count(*) "+hql, params); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBeanDao
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

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListBeanByHqlAndParams(String hql, Object[] params) {
		return baseBeanDao.getListBeanByHqlAndParams(hql, params);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int getConutByByHqlAndParams(String hql, Object[] params) {
		return baseBeanDao.getConutByByHqlAndParams(hql, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int getConutByBySqlAndParams(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return baseBeanDao.getConutByBySqlAndParams(sql, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public BigDecimal getCountByBDSqlAndParams(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return baseBeanDao.getCountByBDSqlAndParams(sql, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public BaseBean getBeanByHqlAndParams(String hql, Object[] params) {
		return baseBeanDao.getBeanByHqlAndParams(hql, params);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Object getObjectByHqlAndParams(String hql, Object[] params) {
		return baseBeanDao.getObjectByHqlAndParams(hql, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Object getObjectBySqlAndParams(String sql, Object[] params) {
		return baseBeanDao.getObjectBySqlAndParams(sql, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListByDC(DetachedCriteria dc) {

		return baseBeanDao.getListByDC(dc);
	}

	/**
	 * 执行同一参数的多条hql语句
	 * 
	 * @param hql
	 * @param params
	 */

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getPageFormByDC(int pageNumber, int pageSize,
			DetachedCriteria dc) {
		int count = baseBeanDao.getConutByDC(dc); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBeanDao.getListFormByDC(dc,
				firstResult, maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getPageForm(int pageNumber, int pageSize, String hql,
			String hqlcount, Object[] params) {
		int count = baseBeanDao.getConutByByHqlAndParams(hqlcount, params); // 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBeanDao
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
		baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeansList, hqls,
				params);
	}

	@Override
	public void executeHqlsByParamsList(List<BaseBean> beans, String[] hqls,
			List<Object[]> parmsList) {
		baseBeanDao.executeHqlsByParmsList(beans, hqls, parmsList);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
			String sqlcount, Object[] params) {
		int count = baseBeanDao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = baseBeanDao
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

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List getListBeanBySqlAndParams(String sql, Object[] params) {
		return baseBeanDao.getListBeanBySqlAndParams(sql, params);
	}
	@Override
	public void executeSqlsByParmsList(List<BaseBean> beans,String[] sqls,List<Object[]> parmsList){
		 baseBeanDao.executeSqlsByParmsList(beans, sqls, parmsList);
	}
	@Override
	public void saveBeansListAndexecuteSqlsByParams(List<BaseBean> baseBeansList,
			String[] sqls, Object[] params) {
		baseBeanDao.saveBeansListAndexecuteSqlsByParams(baseBeansList, sqls, params);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getListByNormal(int pageNow, int pageSize, String dt_table,String dt_column,
			String where_condition,Class clazz){
		return baseBeanDao.getListByNormal(pageNow,pageSize,dt_table,dt_column,where_condition,clazz);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public PageForm getListByComplex(int pageNow, int pageSize, String select_sql,String count_sql,
			Class clazz){
		return baseBeanDao.getListByComplex(pageNow,pageSize,select_sql,count_sql,clazz);
	}


	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int getConutByDC(DetachedCriteria dc) {
		return baseBeanDao.getConutByDC(dc);
	}
	
	@Override
	public void saveOrUpdate(BaseBean baseBean) {
		baseBeanDao.saveOrUpdate(baseBean);
	}
}
