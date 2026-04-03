package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomerWaitor;
import hy.ea.marketing.dao.DtCrmCustomerWaitorDao;

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
 * DtCrmCustomerWaitor entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomerWaitor
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerWaitorDaoImpl implements DtCrmCustomerWaitorDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerWaitorDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;

	// property constants

	public void save(DtCrmCustomerWaitor transientInstance) {
		log.debug("saving DtCrmCustomerWaitor instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DtCrmCustomerWaitor persistentInstance) {
		log.debug("deleting DtCrmCustomerWaitor instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}	

	public List findByExample(DtCrmCustomerWaitor instance) {
		log.debug("finding DtCrmCustomerWaitor instance by example");
		try {
			List results = getSession()
					.createCriteria("hy.ea.marketing.bo.DtCrmCustomerWaitor")
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
		log.debug("finding DtCrmCustomerWaitor instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomerWaitor as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomerWaitor instances");
		try {
			String queryString = "from DtCrmCustomerWaitor";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomerWaitor merge(DtCrmCustomerWaitor detachedInstance) {
		log.debug("merging DtCrmCustomerWaitor instance");
		try {
			DtCrmCustomerWaitor result = (DtCrmCustomerWaitor) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomerWaitor instance) {
		log.debug("attaching dirty DtCrmCustomerWaitor instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomerWaitor instance) {
		log.debug("attaching clean DtCrmCustomerWaitor instance");
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