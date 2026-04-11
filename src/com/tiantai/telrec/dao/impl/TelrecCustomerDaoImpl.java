package com.tiantai.telrec.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.tiantai.telrec.bo.TelrecCustomerInfo;
import com.tiantai.telrec.dao.TelrecCustomerDao;

public class TelrecCustomerDaoImpl implements TelrecCustomerDao {
	private org.hibernate.SessionFactory sessionFactory;
	// private final static String INSERT_CUSTOMER_INFO = "insert into
	// customer_info(customer_name,customer_tel,customer_mobile,customer_type,customer_unit,customer_address,customer_fax,customer_email,customer_hometel,customer_birthday,customer_title,customer_postcode,customer_memo,rela_companyid)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	@SuppressWarnings("unused")
	private final static String INSERT_CUSTOMER_INFO = "insert into customer_info(customer_name,customer_tel,customer_type,customer_address,customer_fax,customer_email,customer_hometel,customer_postcode,customer_memo,rela_companyid,spell)values(?,?,?,?,?,?,?,?,?,?,?,?)";
	@SuppressWarnings("unused")
	private final static String QUERY_CUSTOMER_BY_TELNO = "select * from customer_info where customer_tel=? or customer_mobile=? or customer_hometel=?";
	@SuppressWarnings("unused")
	private final static String QUERY_CUSTOMER_LIST = "select customer_name,customer_tel,customer_mobile,customer_type,customer_unit,customer_address,customer_fax,customer_email,customer_hometel,date_format(customer_birthday,'%Y-%m-%d') as customer_birthday,customer_title,customer_postcode,customer_memo from customer_info where  1=1";
	@SuppressWarnings("unused")
	private final static String QUERY_CUSTOMER_LIST_FOR_COMPANYID = "select * from customer_info where rela_companyid=?";
	@SuppressWarnings("unused")
	private final static String QUERY_CUSTOMER_TOTAL = "select count(*) as cnt ,customer_name,sum(char_length(content)) as sum_content,avg(char_length(content)) as avg_content,sum(end_time-start_time) as sum_time,avg(end_time-start_time)as avg_time from view_tel_rec where company_id=?  and  (start_time between ?  and ?) ";
	@SuppressWarnings("unused")
	private final static String INSERT_CUSTOMER = "insert into customer_info(customer_name,customer_tel,customer_type,customer_address,customer_fax,customer_email,customer_postcode,customer_memo,rela_companyid,spell) values(?,?,?,?,?,?,?,?,?,?)";
	@SuppressWarnings("unused")
	private final static String QUERY_CUSTOMER_BY_ID = "select * from customer_info where id=?";
	@SuppressWarnings("unused")
	private final static String UPDATE_CUSTOMER = "update customer_info set customer_name=?,customer_tel=?,customer_type=?,customer_address=?,customer_fax=?,customer_email=?,customer_postcode=?,customer_memo=?,rela_companyid=?,spell=? where id=?";

	// 保存一个客户信息
	public void insertCustomerInfo1(TelrecCustomerInfo customerInfo) {
		this.getSessionFactory().getCurrentSession().save(customerInfo);
	}

	// 根据电话号码查询客户
	@SuppressWarnings("rawtypes")
	public List getCustomerInfo(String telno) {
		String hql = "from TelrecCustomerInfo where customer_tel=? or customer_mobile=? or customer_hometel=?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				hql);
		query.setString(0, telno);
		query.setString(1, telno);
		query.setString(2, telno);
		return query.list();
	}

	// 根据特定查找条件检索客户
	@SuppressWarnings("rawtypes")
	public List getCustomerList(TelrecCustomerInfo customer) {
		Example example = Example.create(customer).ignoreCase().enableLike();
		List list = this.getSessionFactory().getCurrentSession()
				.createCriteria(TelrecCustomerInfo.class).add(example).list();
		return list;
	}

	// 根据公司查找客户
	@SuppressWarnings("rawtypes")
	public List queryCustomerList(String companyid) {
		// TODO Auto-generated method stub
		return this.getSessionFactory().getCurrentSession().createCriteria(
				TelrecCustomerInfo.class).add(
				Restrictions.eq("rela_companyid", companyid)).list();

	}
	@SuppressWarnings("rawtypes")
	public List QueryCustomerTotalList(String starttime, String endtime,
			String companyid) {
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				"");
		query.setString(1, starttime);
		query.setString(2, endtime);
		query.setString(3, companyid);
		/*
		 * List list = new ArrayList(); try { list =
		 * this.jdbcTemplate.queryForList(QUERY_CUSTOMER_TOTAL, new Object[] {
		 * companyid, starttime, endtime }); } catch (Exception e) {
		 * logger.error("操作异常", e); }
		 */

		return query.list();
	}

	public void insertCustomerInfo(TelrecCustomerInfo customerInfo) {
		// TODO Auto-generated method stub
		this.getSessionFactory().getCurrentSession().save(customerInfo);

	}

	public TelrecCustomerInfo getCustomerInfoByid(String id) {
		// TelrecCustomerInfo customerInfo = null;
		String hql = "from TelrecCustomerInfo where id=? ";
		Query query = this.sessionFactory.openSession().createQuery(hql);
		query.setString(0, id);

		return (TelrecCustomerInfo) query.uniqueResult();
		/*
		 * List list = jdbcTemplate.queryForList(QUERY_CUSTOMER_BY_ID, new
		 * Object[] { id }); if (list.size() > 0) { customerInfo = new
		 * TelrecCustomerInfo(); Map map = (Map) list.get(0);
		 * customerInfo.setId(((Long) map.get("id")).intValue());
		 * customerInfo.setCustomer_name((String) map.get("customer_name"));
		 * customerInfo.setCustomer_tel((String) map.get("customer_tel"));
		 * customerInfo.setCustomer_type((String) map.get("customer_type"));
		 * customerInfo.setCustomer_address((String) map
		 * .get("customer_address")); customerInfo.setCustomer_fax((String)
		 * map.get("customer_fax")); customerInfo.setCustomer_email((String)
		 * map.get("customer_email"));
		 * 
		 * customerInfo.setCustomer_postcode((String) map
		 * .get("customer_postcode")); customerInfo.setCustomer_memo((String)
		 * map.get("customer_memo")); customerInfo.setCustomer_companyid(((Long)
		 * map .get("rela_companyid")).intValue()); }
		 */

	}

	public void updateCustomer(TelrecCustomerInfo c) {
		/*
		 * int flag = this.getJdbcTemplate().update( UPDATE_CUSTOMER, new
		 * Object[] { c.getCustomer_name(), c.getCustomer_tel(),
		 * c.getCustomer_type(), c.getCustomer_address(), c.getCustomer_fax(),
		 * c.getCustomer_email(), c.getCustomer_postcode(),
		 * c.getCustomer_memo(), c.getCustomer_companyid(), c.getSpell(),
		 * c.getId() }); return flag > -1 ? true : false;
		 */
		this.getSessionFactory().getCurrentSession().update(c);
	}

	private static final String QUERY_CUSTOMER_FOR_SPELL = "select id,concat( substr(spell,1,1),'-',customer_name) as customer_name from customer_info where rela_companyid=? order by customer_name";
	@SuppressWarnings("rawtypes")
	public List getCustomerNameByIdForSpell(String companyid) {
		// TODO Auto-generated method stub
		/*
		 * List list = this.getJdbcTemplate().queryForList(
		 * QUERY_CUSTOMER_FOR_SPELL, new Object[] { companyid });
		 */
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				QUERY_CUSTOMER_FOR_SPELL);
		return query.list();
	}

	public org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@SuppressWarnings("rawtypes")
	public List queryTelForDate(Integer type, Date date) {
		List list = new ArrayList();
		return list;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Object getCustomerInfoForId(String customerid) {
		// TODO Auto-generated method stub
		String hql = "from TelrecCustomerInfo wher id=?";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString(0, customerid);
		List list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// 根据公司id分页查询
	@SuppressWarnings("rawtypes")
	public List getCustomerForSplit(int pageNum, int recNum, String companyid) {
		// TODO Auto-generated method stub
		String hql = " from TelrecCustomerInfo where rela_companyid=? ";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setMaxResults(recNum);
		if (pageNum == 0) {

		} else {
			pageNum = (pageNum - 1) * recNum;
		}
		query.setFirstResult(pageNum);

		query.setString(0, companyid);

		List list = query.list();
		return list;
	}

	public int getTotal(String companyid, int rp) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from TelrecCustomerInfo where rela_companyid=? ";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString(0, companyid);
		Long l = (Long) query.uniqueResult();
		return l.intValue();
	}

	// 判断是否存在该客户
	public boolean hasCustomer(TelrecCustomerInfo customerInfo) {
		// TODO Auto-generated method stub
		String hql = "from TelrecCustomerInfo where customer_mobile=? and customer_tel=?  and customer_hometel=?";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString(0, customerInfo.getCustomer_mobile());
		query.setString(1, customerInfo.getCustomer_tel());
		query.setString(2, customerInfo.getCustomer_hometel());
		if (query.list().size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
