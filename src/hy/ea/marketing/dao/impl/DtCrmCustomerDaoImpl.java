package hy.ea.marketing.dao.impl;

import hy.ea.marketing.bo.DtCrmCustomer;
import hy.ea.marketing.bo.DtCrmCustomermenu;
import hy.ea.marketing.dao.DtCrmCustomerDao;

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
 * DtCrmCustomer entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see hy.ea.marketing.bo.DtCrmCustomer
 * @author MyEclipse Persistence Tools
 */
@Service
@Transactional
public class DtCrmCustomerDaoImpl implements DtCrmCustomerDao{
	private static final Logger log = LoggerFactory
			.getLogger(DtCrmCustomerDaoImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	// property constants
	public static final String CUSTOMERID = "customerid";
	public static final String CUSTOMERCODE = "customercode";
	public static final String CUSTOMERNAME = "customername";
	public static final String STATUS = "status";
	public static final String USEDNMAE = "usednmae";
	public static final String SEX = "sex";
	public static final String IDTYPE = "idtype";
	public static final String IDENTITYCARD = "identitycard";
	public static final String REFERENCE = "reference";
	public static final String DDESC = "ddesc";
	public static final String BIRTHDAY = "birthday";
	public static final String ADDRESS = "address";
	public static final String AREA = "area";
	public static final String PHOTO = "photo";
	public static final String PRODUCTID = "productid";
	public static final String STAFFID = "staffid";
	public static final String GROUPCOMPANYSN = "groupcompanysn";
	public static final String VERIFYTIME = "verifytime";
	
	public static final String DTCRMCUSTOMERMENU_COMPANYID = "companyid";
	

	public void save(DtCrmCustomer transientInstance) {
		log.debug("saving DtCrmCustomer instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DtCrmCustomer persistentInstance) {
		log.debug("deleting DtCrmCustomer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DtCrmCustomer findById(java.lang.String id) {
		log.debug("getting DtCrmCustomer instance with id: " + id);
		try {
			DtCrmCustomer instance = (DtCrmCustomer) getSession().get(
					"hy.ea.marketing.bo.DtCrmCustomer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DtCrmCustomer instance) {
		log.debug("finding DtCrmCustomer instance by example");
		try {
			List results = getSession()
					.createCriteria("hy.ea.marketing.bo.DtCrmCustomer")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	private List findByProperty(String propertyName, Object value) {
		log.debug("finding DtCrmCustomer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	private DtCrmCustomer getOnlyOneByProperty(String propertyName, Object value) {
		log.debug("getting only one DtCrmCustomer instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DtCrmCustomer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setMaxResults(1);
			return (DtCrmCustomer)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("get only one DtCrmCustomer by property name failed", re);
			throw re;
		}
	}

	public List findByCustomerid(Object customerid) {
		return findByProperty(CUSTOMERID, customerid);
	}

	public List findByCustomercode(Object customercode) {
		return findByProperty(CUSTOMERCODE, customercode);
	}

	public List findByCustomername(Object customername) {
		return findByProperty(CUSTOMERNAME, customername);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByUsednmae(Object usednmae) {
		return findByProperty(USEDNMAE, usednmae);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByIdtype(Object idtype) {
		return findByProperty(IDTYPE, idtype);
	}

	public List findByIdentitycard(Object identitycard) {
		return findByProperty(IDENTITYCARD, identitycard);
	}

	public List findByReference(Object reference) {
		return findByProperty(REFERENCE, reference);
	}

	public List findByDdesc(Object ddesc) {
		return findByProperty(DDESC, ddesc);
	}

	public List findByBirthday(Object birthday) {
		return findByProperty(BIRTHDAY, birthday);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByArea(Object area) {
		return findByProperty(AREA, area);
	}

	public List findByPhoto(Object photo) {
		return findByProperty(PHOTO, photo);
	}

	public List findByProductid(Object productid) {
		return findByProperty(PRODUCTID, productid);
	}

	public List findByStaffid(Object staffid) {
		return findByProperty(STAFFID, staffid);
	}

	public List findByGroupcompanysn(Object groupcompanysn) {
		return findByProperty(GROUPCOMPANYSN, groupcompanysn);
	}

	public List findAll() {
		log.debug("finding all DtCrmCustomer instances");
		try {
			String queryString = "from DtCrmCustomer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DtCrmCustomer merge(DtCrmCustomer detachedInstance) {
		log.debug("merging DtCrmCustomer instance");
		try {
			DtCrmCustomer result = (DtCrmCustomer) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DtCrmCustomer instance) {
		log.debug("attaching dirty DtCrmCustomer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DtCrmCustomer instance) {
		log.debug("attaching clean DtCrmCustomer instance");
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

	@Override
	public List getList(String groupsn) {
		
		log.debug("finding DtCrmCustomer instance with property: "
				+ "groupcompanysn " + ", value: " + groupsn);
		try {
			String queryString = "from DtCrmCustomer as model where model."
					+ GROUPCOMPANYSN + "= ?" + " order by model." + VERIFYTIME + " desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, groupsn);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property groupcompanysn failed", re);
			throw re;
		}
		
	}

	@Override
	public DtCrmCustomermenu getCustMenuByCompID(String companyId) {
		log.debug("finding DtCrmCustomerMenu instance with property: "
				+ "companyId " + ", value: " + companyId);
		try {
			String queryString = "from DtCrmCustomermenu as model where model."
					+ DTCRMCUSTOMERMENU_COMPANYID + "= ?" ;
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, companyId);
			queryObject.setMaxResults(1);
			return (DtCrmCustomermenu)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find by property companyId failed", re);
			throw re;
		}		
	}

	@Override
	public DtCrmCustomer getCustomerById(String customerId) {		
		return getOnlyOneByProperty(CUSTOMERID, customerId);
	}

	@Override
	public void delCustomerById(String customerId) {		
		getSession().delete(getCustomerById(customerId));//TODO
	}
}