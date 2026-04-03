package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomerOffer;
import hy.ea.marketing.dao.DtCrmCustomerOfferDao;

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
 * DtCrmCustomerOffer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomerOffer
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerOfferDaoImpl implements DtCrmCustomerOfferDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerOfferDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	// property constants
	public static final String OFFERID = "offerid";
	public static final String PPKEY = "ppkey";
	public static final String GOODSCODING = "goodscoding";
	public static final String GOODSVERSION = "goodsversion";
	public static final String STAFFID = "staffid";
	public static final String OFFERCUSTOMERKEY = "offercustomerkey";
	public static final String OFFERCUSTOMERTITLE = "offercustomertitle";
	public static final String ISTRAIN = "istrain";
	public static final String ISASSEMBLY = "isassembly";
	public static final String ISREMOTE = "isremote";
	public static final String ISVISIT = "isvisit";
	public static final String ISUPDATE = "isupdate";
	public static final String ISCALL = "iscall";
	public static final String ISOTHER = "isother";
	public static final String CONTENT = "content";

	public void save(DtCrmCustomerOffer transientInstance) {
		log.debug("saving DtCrmCustomerOffer instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DtCrmCustomerOffer persistentInstance) {
		log.debug("deleting DtCrmCustomerOffer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DtCrmCustomerOffer findById(java.lang.String id) {
		log.debug("getting DtCrmCustomerOffer instance with id: " + id);
		try {
			DtCrmCustomerOffer instance = (DtCrmCustomerOffer) getSession()
					.get("hy.ea.marketing.bo.DtCrmCustomerOffer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DtCrmCustomerOffer instance) {
		log.debug("finding DtCrmCustomerOffer instance by example");
		try {
			List results = getSession()
					.createCriteria("hy.ea.marketing.bo.DtCrmCustomerOffer")
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
		log.debug("finding DtCrmCustomerOffer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomerOffer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOfferid(Object offerid) {
		return findByProperty(OFFERID, offerid);
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

	public List findByStaffid(Object staffid) {
		return findByProperty(STAFFID, staffid);
	}

	public List findByOffercustomerkey(Object offercustomerkey) {
		return findByProperty(OFFERCUSTOMERKEY, offercustomerkey);
	}

	public List findByOffercustomertitle(Object offercustomertitle) {
		return findByProperty(OFFERCUSTOMERTITLE, offercustomertitle);
	}

	public List findByIstrain(Object istrain) {
		return findByProperty(ISTRAIN, istrain);
	}

	public List findByIsassembly(Object isassembly) {
		return findByProperty(ISASSEMBLY, isassembly);
	}

	public List findByIsremote(Object isremote) {
		return findByProperty(ISREMOTE, isremote);
	}

	public List findByIsvisit(Object isvisit) {
		return findByProperty(ISVISIT, isvisit);
	}

	public List findByIsupdate(Object isupdate) {
		return findByProperty(ISUPDATE, isupdate);
	}

	public List findByIscall(Object iscall) {
		return findByProperty(ISCALL, iscall);
	}

	public List findByIsother(Object isother) {
		return findByProperty(ISOTHER, isother);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomerOffer instances");
		try {
			String queryString = "from DtCrmCustomerOffer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomerOffer merge(DtCrmCustomerOffer detachedInstance) {
		log.debug("merging DtCrmCustomerOffer instance");
		try {
			DtCrmCustomerOffer result = (DtCrmCustomerOffer) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomerOffer instance) {
		log.debug("attaching dirty DtCrmCustomerOffer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomerOffer instance) {
		log.debug("attaching clean DtCrmCustomerOffer instance");
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