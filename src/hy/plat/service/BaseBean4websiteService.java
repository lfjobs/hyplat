package hy.plat.service;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
public interface BaseBean4websiteService {
	/**
	 * 保存BaseBean子类对象
	 * @param baseBean
	 */
	void save(BaseBean baseBean);
	
	/**
	 * 更新BaseBean子类对象
	 * @param baseBean
	 */
	void update(BaseBean baseBean);
	
	/**
	 * 根据主键查询BeseBean子类对象
	 * @param clasz
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	BaseBean getBeanByKey(Class clasz,String key);
	
	/**
	 * 根据主键删除BaseBean子类对象
	 * @param clasz
	 * @param key
	 */
	@SuppressWarnings("rawtypes")
	void deleteBeanByKey(Class clasz,String key);
	
	/**
	 * 查询
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<BaseBean> list(Class clasz);
	
	/**
	 * 一般分页查询HQL
	 * @param pageNumber
	 * @param pageSize
	 * @param hql
	 * @param params
	 * @return
	 */
	PageForm getPageForm(int pageNumber,int pageSize,String hql, Object[] params);
	/**
	 * 复杂分页查询HQL
	 * @param pageNumber
	 * @param pageSize
	 * @param hql
	 * @param params
	 * @return
	 */
	PageForm getPageForm(int pageNumber,int pageSize,String hql,String hqlcount, Object[] params);
	/**
	 * SQL分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sql
	 * @param params
	 * @return
	 */
	PageForm getPageFormBySQL(int pageNumber,int pageSize,String sql,String sqlcount, Object[] params);
	/**
	 * 离线查询分页
	 * @param pageNumber
	 * @param pageSize
	 * @param listBaseBean 用于分布显示的列表
	 * @return
	 */
	PageForm getPageFormByDC(int pageNumber,int pageSize,DetachedCriteria dc);
	/**
	 * 跟据条件和语句查询例表HQL
	 * @param hql
	 * @param params
	 * @return
	 */
	List<BaseBean> getListBeanByHqlAndParams(String hql, Object[] params);
	/**
	 * 跟据条件和语句查询例表SQL
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List getListBeanBySqlAndParams(String sql, Object[] params);
	/**
	 * 跟据条件和语句查询纪录总数
	 * @param hql
	 * @param params
	 * @return
	 */
	int getConutByByHqlAndParams(String hql, Object[] params);
	/**
	 * 跟据条件和语句查询纪录总数
	 * @param sql
	 * @param params
	 * @return
	 */
	int getConutByBySqlAndParams(String sql, Object[] params);
	/**
	 * 根据Hql语句和参数查找单一对象
	 * @param hql
	 * @param params
	 */
	BaseBean getBeanByHqlAndParams(String hql, Object[] params);
	/**
	 * 根据Hql语句和参数查找单一对象(可能包含多个bo的字段)
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getObjectByHqlAndParams(String hql, Object[] params);
	/**
	 * 根据Hql语句和参数查找单一对象(可能包含多个bo的字段)
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getObjectBySqlAndParams(String sql, Object[] params);
	/**
	 * 离线查询
	 * @param dc
	 * @return
	 */
	List<BaseBean> getListByDC(DetachedCriteria dc);
	/**
	 * 增删改的事物方法（执行hql语句的参数必须一致）
	 * @param baseBeans
	 */
	void saveBeansListAndexecuteHqlsByParams(List<BaseBean> baseBeansList,String[] hqls, Object[] params);
	/**
	 * 增删改的事物方法
	 * @param beans 实体集合
	 * @param hqls	hql语句集合
	 * @param parmsList 参数集合（与hql语句对应）
	 */
	void executeHqlsByParamsList(List<BaseBean> beans , String[] hqls , List<Object[]> parmsList);
	/**
	 * 增删改的事物方法(适用于不同sql语句有不同参数的情况)
	 * @param beans 添加的实体集合
	 * @param sqls  sql语句集合
	 * @param parmsList sql语句参数集合(与sql语句对应)
	 */
	public void executeSqlsByParmsList(List<BaseBean> beans,String[] sqls,List<Object[]> parmsList);
	
	/**
	 * 增删改的事物方法（执行sql语句的参数必须一致）
	 * @param baseBeansList 实体的集合
	 * @param sqls sql语句的集合
	 * @param params 参数据的数组（所有语句均使用相同参数）
	 */
	public void saveBeansListAndexecuteSqlsByParams(List<BaseBean> baseBeansList,
			String[] sqls, Object[] params);
	/**
	 * 查询一个表 简单存储过程分页
	 * @param dt_table  表名  
	 * @param dt_column 要查询的列明
	 * @param where_condition  条件 不用加 where 格式如下（column1 = '张灿' and column2='喽飞' ....） 
	 * @param pageNow  当前页
	 * @param pageSize 每页显示的条数
	 * @param clazz 操作的实体类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageForm getListByNormal(int pageNow, int pageSize, String dt_table,String dt_column,
			String where_condition,Class clazz);
	/**
	 * 复杂存储过程分页
	 * @param count_sql 查询总数
	 * @param select_sql 查询语句
	 * @param pageNow 当期页
	 * @param pageSize 每页显示条数
	 * @param clazz 转换的类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageForm getListByComplex(int pageNow, int pageSize, String select_sql,String count_sql,
			Class clazz);
}
