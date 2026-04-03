package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomerActivity;
import hy.ea.marketing.bo.DtCrmCustomerProduct;
import hy.ea.marketing.dao.DtCrmCustomerActivityDao;

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
 * DtCrmCustomerActivity entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomerActivity
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerActivityDaoImpl implements DtCrmCustomerActivityDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerActivityDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	
	// property constants
	public static final String ACTIVITYID = "activityid";
	public static final String ACTIVITYTITLE = "activitytitle";
	public static final String STATUS = "status";
	public static final String ISMANUAL = "ismanual";
	public static final String ISBROCHURE = "isbrochure";
	public static final String ISDEMO = "isdemo";
	public static final String ISOTHER = "isother";
	public static final String CONTENT1 = "content1";
	public static final String ISSMS = "issms";
	public static final String ISSMS2 = "issms2";
	public static final String ISWEIXIN = "isweixin";
	public static final String ISVISIT = "isvisit";
	public static final String ISMEETING = "ismeeting";
	public static final String ISACTIVITY = "isactivity";
	public static final String CONTENT2 = "content2";
	public static final String DDESC = "ddesc";

	

	public void delete(DtCrmCustomerActivity persistentInstance) {
		log.debug("deleting DtCrmCustomerActivity instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DtCrmCustomerActivity findById(java.lang.String id) {
		log.debug("getting DtCrmCustomerActivity instance with id: " + id);
		try {
			DtCrmCustomerActivity instance = (DtCrmCustomerActivity) getSession()
					.get("hy.ea.marketing.bo.DtCrmCustomerActivity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DtCrmCustomerActivity instance) {
		log.debug("finding DtCrmCustomerActivity instance by example");
		try {
			List results = getSession()
					.createCriteria("hy.ea.marketing.bo.DtCrmCustomerActivity")
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
		log.debug("finding DtCrmCustomerActivity instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomerActivity as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActivityid(Object activityid) {
		return findByProperty(ACTIVITYID, activityid);
	}

	public List findByActivitytitle(Object activitytitle) {
		return findByProperty(ACTIVITYTITLE, activitytitle);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByIsmanual(Object ismanual) {
		return findByProperty(ISMANUAL, ismanual);
	}

	public List findByIsbrochure(Object isbrochure) {
		return findByProperty(ISBROCHURE, isbrochure);
	}

	public List findByIsdemo(Object isdemo) {
		return findByProperty(ISDEMO, isdemo);
	}

	public List findByIsother(Object isother) {
		return findByProperty(ISOTHER, isother);
	}

	public List findByContent1(Object content1) {
		return findByProperty(CONTENT1, content1);
	}

	public List findByIssms(Object issms) {
		return findByProperty(ISSMS, issms);
	}

	public List findByIssms2(Object issms2) {
		return findByProperty(ISSMS2, issms2);
	}

	public List findByIsweixin(Object isweixin) {
		return findByProperty(ISWEIXIN, isweixin);
	}

	public List findByIsvisit(Object isvisit) {
		return findByProperty(ISVISIT, isvisit);
	}

	public List findByIsmeeting(Object ismeeting) {
		return findByProperty(ISMEETING, ismeeting);
	}

	public List findByIsactivity(Object isactivity) {
		return findByProperty(ISACTIVITY, isactivity);
	}

	public List findByContent2(Object content2) {
		return findByProperty(CONTENT2, content2);
	}

	public List findByDdesc(Object ddesc) {
		return findByProperty(DDESC, ddesc);
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomerActivity instances");
		try {
			String queryString = "from DtCrmCustomerActivity";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomerActivity merge(DtCrmCustomerActivity detachedInstance) {
		log.debug("merging DtCrmCustomerActivity instance");
		try {
			DtCrmCustomerActivity result = (DtCrmCustomerActivity) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomerActivity instance) {
		log.debug("attaching dirty DtCrmCustomerActivity instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomerActivity instance) {
		log.debug("attaching clean DtCrmCustomerActivity instance");
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

	public void save(DtCrmCustomerActivity atty) {		
		log.debug("saving DtCrmCustomerActivity instance");
		try {
			getSession().save(atty);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}		
	}

	
}