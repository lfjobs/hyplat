package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomerCompetitive;
import hy.ea.marketing.dao.DtCrmCustomerCompetitiveDao;

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
 * DtCrmCustomerCompetitive entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomerCompetitive
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerCompetitiveDaoImpl implements DtCrmCustomerCompetitiveDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerCompetitiveDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	// property constants
	public static final String COMPETITIVEID = "competitiveid";
	public static final String COMPETITIVENAME = "competitivename";
	public static final String COMPETITIVECODE = "competitivecode";
	public static final String COMPANYNAME = "companyname";
	public static final String ADDRESS = "address";
	public static final String SALETOAREA = "saletoarea";
	public static final String SPECIFICATION = "specification";
	public static final String MODEL = "model";

	public void save(DtCrmCustomerCompetitive transientInstance) {
		log.debug("saving DtCrmCustomerCompetitive instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DtCrmCustomerCompetitive persistentInstance) {
		log.debug("deleting DtCrmCustomerCompetitive instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DtCrmCustomerCompetitive findById(java.lang.String id) {
		log.debug("getting DtCrmCustomerCompetitive instance with id: " + id);
		try {
			DtCrmCustomerCompetitive instance = (DtCrmCustomerCompetitive) getSession()
					.get("hy.ea.marketing.bo.DtCrmCustomerCompetitive", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DtCrmCustomerCompetitive instance) {
		log.debug("finding DtCrmCustomerCompetitive instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"hy.ea.marketing.bo.DtCrmCustomerCompetitive")
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
		log.debug("finding DtCrmCustomerCompetitive instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomerCompetitive as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCompetitiveid(Object competitiveid) {
		return findByProperty(COMPETITIVEID, competitiveid);
	}

	public List findByCompetitivename(Object competitivename) {
		return findByProperty(COMPETITIVENAME, competitivename);
	}

	public List findByCompetitivecode(Object competitivecode) {
		return findByProperty(COMPETITIVECODE, competitivecode);
	}

	public List findByCompanyname(Object companyname) {
		return findByProperty(COMPANYNAME, companyname);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findBySaletoarea(Object saletoarea) {
		return findByProperty(SALETOAREA, saletoarea);
	}

	public List findBySpecification(Object specification) {
		return findByProperty(SPECIFICATION, specification);
	}

	public List findByModel(Object model) {
		return findByProperty(MODEL, model);
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomerCompetitive instances");
		try {
			String queryString = "from DtCrmCustomerCompetitive";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomerCompetitive merge(
			DtCrmCustomerCompetitive detachedInstance) {
		log.debug("merging DtCrmCustomerCompetitive instance");
		try {
			DtCrmCustomerCompetitive result = (DtCrmCustomerCompetitive) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomerCompetitive instance) {
		log.debug("attaching dirty DtCrmCustomerCompetitive instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomerCompetitive instance) {
		log.debug("attaching clean DtCrmCustomerCompetitive instance");
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