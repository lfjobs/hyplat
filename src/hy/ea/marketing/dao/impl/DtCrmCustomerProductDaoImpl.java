package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomerProduct;
import hy.ea.marketing.dao.DtCrmCustomerProductDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * DtCrmCustomerProduct entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomerProduct
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerProductDaoImpl implements DtCrmCustomerProductDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerProductDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	// property constants
	public static final String PRODUCTID = "productid";
	public static final String PPKEY = "ppkey";
	public static final String GOODSCODING = "goodscoding";
	public static final String GOODSVERSION = "goodsversion";
	public static final String GOODSDESC = "goodsdesc";
	public static final String REQUIREMENT = "requirement";
	public static final String NREQUIREMENT = "nrequirement";
	public static final String PPKEYONE = "ppkeyone";
	public static final String PPKEYTWO = "ppkeytwo";
	public static final String PPKEYTHREE = "ppkeythree";
	public static final String COMPETITIVE = "competitive";

	public void save(DtCrmCustomerProduct transientInstance) {
		log.debug("saving DtCrmCustomerProduct instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DtCrmCustomerProduct persistentInstance) {
		log.debug("deleting DtCrmCustomerProduct instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DtCrmCustomerProduct findById(java.lang.String id) {
		log.debug("getting DtCrmCustomerProduct instance with id: " + id);
		try {
			DtCrmCustomerProduct instance = (DtCrmCustomerProduct) getSession()
					.get("hy.ea.marketing.bo.DtCrmCustomerProduct", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DtCrmCustomerProduct instance) {
		log.debug("finding DtCrmCustomerProduct instance by example");
		try {
			List results = getSession()
					.createCriteria("hy.ea.marketing.bo.DtCrmCustomerProduct")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding DtCrmCustomerProduct instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomerProduct as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProductid(Object productid) {
		return findByProperty(PRODUCTID, productid);
	}

	public List findByPpkey(Object ppkey) {
		return findByProperty(PPKEY, ppkey);
	}

	public List findByGoodscoding(Object goodscoding) {
		return findByProperty(GOODSCODING, goodscoding);
	}

	public List findByGoodsversion(Object goodsversion) {
		return findByProperty(GOODSVERSION, goodsversion);
	}

	public List findByGoodsdesc(Object goodsdesc) {
		return findByProperty(GOODSDESC, goodsdesc);
	}

	public List findByRequirement(Object requirement) {
		return findByProperty(REQUIREMENT, requirement);
	}

	public List findByNrequirement(Object nrequirement) {
		return findByProperty(NREQUIREMENT, nrequirement);
	}

	public List findByPpkeyone(Object ppkeyone) {
		return findByProperty(PPKEYONE, ppkeyone);
	}

	public List findByPpkeytwo(Object ppkeytwo) {
		return findByProperty(PPKEYTWO, ppkeytwo);
	}

	public List findByPpkeythree(Object ppkeythree) {
		return findByProperty(PPKEYTHREE, ppkeythree);
	}

	public List findByCompetitive(Object competitive) {
		return findByProperty(COMPETITIVE, competitive);
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomerProduct instances");
		try {
			String queryString = "from DtCrmCustomerProduct";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomerProduct merge(DtCrmCustomerProduct detachedInstance) {
		log.debug("merging DtCrmCustomerProduct instance");
		try {
			DtCrmCustomerProduct result = (DtCrmCustomerProduct) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomerProduct instance) {
		log.debug("attaching dirty DtCrmCustomerProduct instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomerProduct instance) {
		log.debug("attaching clean DtCrmCustomerProduct instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	private Session getSession() {		
		return this.sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}