package hy.plat.dao.impl;

import hy.ea.bo.company.DepotManage;
import hy.ea.util.Converter;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

import javax.annotation.Resource;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseBeanDao {


	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 保存BaseBean子类对象
	 *
	 * @param baseBean
	 */
	public void save(BaseBean baseBean) {
		sessionFactory.getCurrentSession().persist(baseBean);
	}


	/**
	 * 更新BaseBean子类对象
	 *
	 * @param baseBean
	 */
	public void update(BaseBean baseBean) {
		sessionFactory.getCurrentSession().merge(baseBean);

	}
	/**
	 * 更新BaseBean子类对象
	 *
	 * @param baseBean
	 */
	@Transactional
	public void updateAndExecute(BaseBean baseBean) {
		sessionFactory.getCurrentSession().saveOrUpdate(baseBean);
		sessionFactory.getCurrentSession().flush();  // 强制执行 SQL
	}

	/**
	 * 保存或更新BaseBean子类对象
	 *
	 * @param baseBean
	 */
	public void saveOrUpdate(BaseBean baseBean) {
		sessionFactory.getCurrentSession().saveOrUpdate(baseBean);
	}

	/**
	 * 根据主键Key查询BaseBean子类对象
	 *
	 * @param clasz
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public BaseBean getBeanByKey(Class clasz, String key) {
		BaseBean baseBean =(BaseBean) sessionFactory.getCurrentSession().get(clasz, key);
		return baseBean;

	}

	/**
	 * 根据主键Key删除BaseBean子类对象
	 * @param clasz
	 * @param key
	 */
	@SuppressWarnings("rawtypes")
	public void deleteBeanByKey(Class clasz, String key) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(clasz, key));
	}

	/**
	 * 查询
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> list(Class clasz) {

		List<BaseBean> list =sessionFactory.getCurrentSession().createQuery(
				" from " + clasz.getSimpleName() + " ").list();
		return list;

	}

	/**
	 * 通过hql及条件查询
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListBeanByHqlAndParams(String hql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		return query.list();
	}
	/**
	 * 通过SQL及条件查询
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListBeanBySqlAndParams(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toUpperCase());
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		return query.list();
	}

	/**
	 * 通过HQL及条件查询分页
	 * @param hql
	 * @param params
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getConutByByHqlAndParamsAndPage(String hql,
														  Object[] params, int firstResult, int maxResult) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.list();
	}

	/**
	 * 通过SQL及条件查询分页
	 * @param sql
	 * @param params
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getConutByBySqlAndParamsAndPage(String sql,
														  Object[] params, int firstResult, int maxResult) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toUpperCase());
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.list();
	}


	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int getConutByByHqlAndParams(String hql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		Object o = query.uniqueResult();
		return Integer.parseInt(o.toString());

	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int getConutByBySqlAndParams(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toUpperCase());
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		Object o = query.uniqueResult();
		return Integer.parseInt(o.toString());
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public BigDecimal getCountByBDSqlAndParams(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toUpperCase());
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		Object o = query.uniqueResult();

		return new BigDecimal(o.toString());
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public BaseBean getBeanByHqlAndParams(String hql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		query.setMaxResults(1);
		BaseBean baseBean = (BaseBean) query.uniqueResult();
		return baseBean;
	}

	/**
	 * 返回的对象可能包含多个bo的字段
	 * @param hql
	 * @param params
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object getObjectByHqlAndParams(String hql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		//query.setMaxResults(1);
		Object object = query.uniqueResult();
		return object;
	}

	/**
	 * 返回的对象可能包含多个bo的字段
	 * @param hql
	 * @param params
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object getObjectBySqlAndParams(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		//query.setMaxResults(1);
		Object object = query.uniqueResult();
		return object;
	}

	/**
	 * 通过SQL及条件查询字段的集合
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> getListObjectBySqlAndParams(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toUpperCase());
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListByDC(DetachedCriteria dc) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getListFormByDC(DetachedCriteria dc,int firstResult, int maxResult) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		criteria.setProjection(null);
		dc.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int getConutByDC(DetachedCriteria dc) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = dc.getExecutableCriteria(session);
		Integer coun = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		return coun;

	}

    /**
     * 增删改的事物方法（执行hql语句的参数必须一致）
     *
     * @param baseBeansList 实体的集合
     * @param hqls          hql语句的集合
     * @param params        参数据的数组（所有语句均使用相同参数）
     */
    public void saveBeansListAndexecuteHqlsByParams(List<BaseBean> baseBeansList,
                                                    String[] hqls, Object[] params) {
        if (hqls != null && hqls.length > 0) {
            for (String hql : hqls) {
                if (hql != null && !hql.equals("")) {
                    Query query = sessionFactory.getCurrentSession().createQuery(hql);
                    if (params != null && params.length > 0) {
                        int i = 0;
                        for (Object param : params) {
                            query.setParameter(i++, param);
                        }
                    }
                    query.executeUpdate();
                }
            }
        }
        if (baseBeansList != null && baseBeansList.size() != 0) {

            for (BaseBean baseBean : baseBeansList) {
                if (null != baseBean) {
                    sessionFactory.getCurrentSession().merge(baseBean);
                }
            }
        }
    }

    /**
     * 增删改的事物方法（执行sql语句的参数必须一致）
     *
     * @param baseBeansList 实体的集合
     * @param sqls          sql语句的集合
     * @param params        参数据的数组（所有语句均使用相同参数）
     */
    public void saveBeansListAndexecuteSqlsByParams(List<BaseBean> baseBeansList,
                                                    String[] sqls, Object[] params) {
        if (sqls != null && sqls.length > 0) {
            for (String sql : sqls) {
                Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
                if (params != null && params.length > 0) {
                    int i = 0;
                    for (Object param : params) {
                        query.setParameter(i++, param);
                    }
                }
                query.executeUpdate();
            }
        }
        if (baseBeansList != null && baseBeansList.size() != 0) {

            for (BaseBean baseBean : baseBeansList) {
                if (null != baseBean) {
                    sessionFactory.getCurrentSession().merge(baseBean);
                }
            }
        }
    }

    /**
     * 增删改的事物方法(适用于不同hql语句有不同参数的情况)
     *
     * @param beans     添加的实体集合
     * @param hqls      hql语句集合
     * @param parmsList hql语句参数集合(与hql语句对应)
     */
    @SuppressWarnings("unused")
    public void executeHqlsByParmsList(List<BaseBean> beans, String[] hqls, List<Object[]> parmsList) {

        if (hqls != null && hqls.length > 0) {

            for (int i = 0; i < hqls.length; i++) {
                String hql = hqls[i];
                if (hql != null && !hql.equals("")) {
                    Query query = sessionFactory.getCurrentSession().createQuery(hql);

                    if (parmsList != null && parmsList.size() != 0) {

                        for (int j = i; j < parmsList.size(); j++) {

							Object[] parms = parmsList.get(j);

							int index = 0;

							for (Object parm : parms) {

								query.setParameter(index++, parm);
							}
							break;

						}
					}
					query.executeUpdate();
				}else{
					continue;
				}
			}
		}

		if(beans != null && beans.size() != 0){

			for (BaseBean bean : beans){

				if( bean != null){

					sessionFactory.getCurrentSession().merge(bean);
				}
			}
		}
	}
	/**
	 * 增删改的事物方法(适用于不同sql语句有不同参数的情况)
	 * @param beans 添加的实体集合
	 * @param sqls  sql语句集合
	 * @param parmsList sql语句参数集合(与sql语句对应)
	 */
	@SuppressWarnings("unused")
	public void executeSqlsByParmsList(List<BaseBean> beans,String[] sqls,List<Object[]> parmsList){

		if(sqls != null && sqls.length > 0){

			for (int i = 0; i < sqls.length; i++) {

				String sql = sqls[i];

				if(sql!=null&&!sql.equals("")){

					Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

					if(parmsList != null && parmsList.size() != 0){

						for (int j = i; j < parmsList.size(); j++) {

							Object[] parms = parmsList.get(j);

							int index = 0;

							for (Object parm : parms) {

								query.setParameter(index++, parm);
							}
							break;

						}
					}
					query.executeUpdate();
				}else{
					continue;
				}
			}
		}

		if(beans != null && beans.size() != 0){

			for (BaseBean bean : beans){

				if( bean != null){

					sessionFactory.getCurrentSession().merge(bean);
				}
			}
		}
	}
	/**
	 * 查询一个表 简单存储过程分页
	 * @param pageNow  当前页
	 * @param pageSize 每页显示的条数
	 * @param dt_table 表名
	 * @param dt_column 查询的列
	 * @param where_condition 查询条件
	 * @param clazz 操作的实体类
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes"})
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getListByNormal(int pageNow, int pageSize, String dt_table,String dt_column,
									String where_condition,Class clazz) {
		PageForm page = new PageForm();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call normalfenye(?,?,?,?,?,?,?,?)}");
			cs.setString(1, dt_table); //
			cs.setString(2, dt_column); //
			cs.setString(3, where_condition); //
			cs.setInt(4, pageSize); // 每页显示记录数
			cs.setInt(5, pageNow);// 当前页数
			// 注册总记录数
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER); // 总记录数
			// 注册总页数
			cs.registerOutParameter(7, oracle.jdbc.OracleTypes.INTEGER); // 总页数
			// 注册返回的结果集
			cs.registerOutParameter(8, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
			// 3、执行
			cs.execute();
			// 得到结果集
			// 取出总记录数 /这里要注意，getInt(5)中5，是由该参数的位置决定的
			page.setRecordCount(cs.getInt(6));//总记录数
			page.setPageCount(cs.getInt(7));//总页数
			page.setPageNumber(pageNow);//当前页
			page.setPageSize(pageSize);//总页数

			ResultSet rs = (ResultSet) cs.getObject(8);
			Converter c = new Converter();
			Collection jihe=c.get(rs, clazz);
			List<BaseBean> list=new ArrayList<BaseBean>();
			for (Iterator it = jihe.iterator(); it.hasNext();) {
				BaseBean ltest = (BaseBean) it.next();
				list.add(ltest);
			}
			page.setList(list);
			list=null;
			// 4、关闭
			rs.close();
			cs.close();
			ct.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 复杂分页存储过程
	 * @param pageNow
	 * @param pageSize
	 * @param count_sql
	 * @param select_sql
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes"})
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getListByComplex(int pageNow, int pageSize, String select_sql,String count_sql,
									 Class clazz) {
		PageForm page = new PageForm();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call complexfenye(?,?,?,?,?,?,?)}");
			cs.setString(1, count_sql); //
			cs.setString(2, select_sql); //
			cs.setInt(3, pageSize); // 每页显示记录数
			cs.setInt(4, pageNow);// 当前页数
			// 注册总记录数
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER); // 总记录数
			// 注册总页数
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER); // 总页数
			// 注册返回的结果集
			cs.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
			// 3、执行
			cs.execute();
			// 得到结果集
			// 取出总记录数 /这里要注意，getInt(5)中5，是由该参数的位置决定的
			page.setRecordCount(cs.getInt(5));//总记录数
			page.setPageCount(cs.getInt(6));//总页数
			page.setPageNumber(pageNow);//当前页
			page.setPageSize(pageSize);//总页数

			ResultSet rs = (ResultSet) cs.getObject(7);
			Converter c = new Converter();
			Collection jihe=c.get(rs, clazz);
			List<BaseBean> list=new ArrayList<BaseBean>();
			for (Iterator it = jihe.iterator(); it.hasNext();) {
				BaseBean ltest = (BaseBean) it.next();
				list.add(ltest);
			}
			page.setList(list);
			list=null;
			// 4、关闭
			rs.close();
			cs.close();
			ct.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 返回的对象可能包含多个bo的字段
	 * @param
	 * @param params
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object getDepotBySqlAndParams(String sql, Object[] params) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(DepotManage.class);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		List list = query.list();
		//query.setMaxResults(1);
		Object object = query.uniqueResult();
		return object;
	}
}
