package com.tiantai.telrec.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.tiantai.telrec.bo.TelrecTelInfo;
import com.tiantai.telrec.dao.TelrecTelInfoDao; 
import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelOutRecord;

public class TelrecTelInfoDaoImpl implements TelrecTelInfoDao {
	private static final long ONEDAYCOUNT = 1000 * 60 * 60 * 24;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insertTelInfo(TelrecTelInfo telInfo) { 
		this.getSessionFactory().getCurrentSession().save(telInfo);
	}
	
	/**
	 * 电话打入5条记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TelInRecord> queryCustomerTelRecordIn(String telno,String company) { 
		String hql = "select * from ( select tt.customname,tt.recordtype,to_char(tt.recodeDate,'yyyy-MM-dd hh:mm:ss') as recodeDate,tt.recordcontent from dt_tel_telinrecord tt where telNumber=? and company=? order by tt.recodeDate desc ) where ROWNUM < 6";
		 
		Query query = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(hql);
		query.setString(0, telno);
		query.setString(1, company);
		return query.list();
	}
	/**
	 * 电话打出5条记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TelOutRecord> queryCustomerTelRecordOut(String telno,String company) { 
		String hql = "select * from ( select tt.visiteduser as customname,tt.teltype as recordtype,to_char(tt.visitedtime,'yyyy-MM-dd hh:mm:ss') as recodeDate,tt.visitedcontent as recordcontent from dt_tel_teloutrecord tt where telNumber=? and company=? order by tt.visitedtime desc ) where ROWNUM < 6";
		 
		Query query = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(hql);
		query.setString(0, telno);
		query.setString(1, company);
		return query.list();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelForDate(int telType, String date) { 
		return null;
	}
	@Override
	public boolean updateTelInfo(String id, String path) { 

		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				"update  TelrecTelInfo set path=? where id=?");
		query.setString(0, path);
		query.setString(1, id);
		return query.executeUpdate() > 0 ? true : false;
	}
	@SuppressWarnings("rawtypes")
	public List queryTelForDate(Integer type, Date date) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForUserid(String userid, String starttime,
			String endtime) { 
		String hql = "from TelrecTelInfo where user_id=? and starttime between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd')";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				hql);
		return query.list();
	}
	@SuppressWarnings({ "rawtypes", "finally" })
	public List queryTelForDate(String id, String time) { 
		List list = new ArrayList();
		try {
			String hql = "select to_char(start_time,'yyyy-MM-dd hh:mm:ss'),in_or_out, customer_name,telno,content,path from  TELRECTELINFO,TELRECCOSTOMERINFO where TELRECTELINFO.customer_id=TELRECCOSTOMERINFO.id and user_id=? and to_char(start_time,'yyyy-MM-dd')=?";
			Query query = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(hql);
			query.setString(0, id);
			query.setString(1, time);

			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return list;
		}
	}
	@SuppressWarnings({ "rawtypes", "finally" })
	public List queryTelForDateForCompanyid(String companyid, String date) { 
		List list = new ArrayList();
		try {
			String hql = "         select "
					+ "         ACCOUNTNAME,"
					+ "         customer_name,"
					+ "         to_char(start_time,'yyyy-MM-dd hh:mm:ss'),"
					+ "    	   to_char(end_time,'yyyy-MM-dd hh:mm:ss'),"
					+ "         telno,"
					+ "         in_or_out,"
					+ "         content,"
					+ "         path"
					+ "         from "
					+ "         TELRECTELINFO,TELRECCOSTOMERINFO ,DTCACCOUNT"
					+ "         where"
					+ "         TELRECTELINFO.customer_id=TELRECCOSTOMERINFO.id "
					+ "         and "
					+ "         user_id in(select ACCOUNTID from DTCACCOUNT where COMPANYID=?)"
					+ "         and "
					+ "         to_char(start_time,'yyyy-MM-dd')=?"
					+ "         and user_id=ACCOUNTID"
					+ "          order by user_id,start_time";
			Query query = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(hql);
			query.setString(0, companyid);
			query.setString(1, date);

			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return list;
		}
	}
	@SuppressWarnings("rawtypes")
	public List queryTelForDateForCustomerId(String customerId, int rp,
			int pages) {
		// TODO Auto-generated method stub
		String hql = "from TelrecTelInfo where user_id=? order by start_time desc";
		Query query = this.getSessionFactory().openSession().createQuery(hql);

		query.setString(0, customerId);
		query.setMaxResults(rp);
		query.setFirstResult(pages - 1);
		return query.list();

	}

	public int toalForCustomerId(String customerId, int rp) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from TelrecTelInfo where user_id=?";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString(0, customerId);
		int cnt = ((Long) query.uniqueResult()).intValue();
		return cnt;
	}

	/**
	 * 查询通话记录
	 * 
	 * @param companyid
	 * @param rp
	 * @param pages
	 * @param customer_name
	 * @param user_name
	 * @param startdate
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public List queryTelrecInfoFroParam(String companyid, int rp, int pages,
			String customer_name, String user_name, Date startdate) { 
		StringBuffer hql = new StringBuffer(
				"from TelrecTelInfo where user_id in(select accountID from CAccount where companyID=:companyid)");
		if (customer_name != null && !customer_name.equals("null")
				&& !customer_name.equals("")) {
			hql.append(" and customer_name=:customer_name");
		}
		if (user_name != null && !user_name.equals("")
				&& !user_name.equals("null")) {
			hql.append(" and user_name=:user_name");
		}
		if (startdate != null && !startdate.equals("")
				&& !startdate.equals("null")) {
			hql.append(" and start_time between :startdate and :endtime");
		}
		Query query = this.getSessionFactory().openSession().createQuery(
				hql.toString());
		query.setString("companyid", companyid);
		if (customer_name != null && !customer_name.equals("null")
				&& !customer_name.equals("")) {
			query.setString("customer_name", customer_name);
		}
		if (user_name != null && !user_name.equals("")
				&& !user_name.equals("null")) {
			query.setString("user_name", user_name);
		}
		if (startdate != null && !startdate.equals("")
				&& !startdate.equals("null")) {
			query.setDate("startdate", startdate);
			query.setDate("endtime", new Date(startdate.getTime()
					+ this.ONEDAYCOUNT));
		}
		query.setMaxResults(rp);
		// System.out.println(pages*rp+1);
		query.setFirstResult((pages - 1) * rp );
		List list = query.list();
		return list;
	}

	/**
	 * 统计通话记录
	 * 
	 * @param companyid
	 * @param rp
	 * @param pages
	 * @param customer_name
	 * @param user_name
	 * @param startdate
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int toalForCustomerForTatol(String companyid, int rp, int pages,
			String customer_name, String user_name, Date startdate) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(
				"select count(*) from TelrecTelInfo where user_id in(select accountID from CAccount where companyID=:companyid)");
		if (customer_name != null && !customer_name.equals("")
				&& !customer_name.equals("null")) {
			hql.append("  and customer_name=:customer_name");
		}
		if (user_name != null && !user_name.equals("")
				&& !user_name.equals("null")) {
			hql.append("  and user_name=:user_name");
		}
		if (startdate != null && !startdate.equals("")
				&& !startdate.equals("null")) {
			hql.append(" and start_time between :startdate and :endtime");
		}
		Query query = this.getSessionFactory().openSession().createQuery(
				hql.toString());
		query.setString("companyid", companyid);
		if (customer_name != null && !customer_name.equals("")
				&& !customer_name.equals("null")) {
			query.setString("customer_name", customer_name);
		}
		if (user_name != null && !user_name.equals("")
				&& !user_name.equals("null")) {
			query.setString("user_name", user_name);
		}
		if (startdate != null && !startdate.equals("")
				&& !startdate.equals("null")) {
			query.setDate("startdate", startdate);
			query.setDate("endtime", new Date(startdate.getTime()
					+ this.ONEDAYCOUNT));
		}
		int cnt = ((Long) query.uniqueResult()).intValue();
		return cnt;
	}

	/**
	 * 根据companyid查询所有的客户
	 * 
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryTelForCustomer_nameAll(String companyid) {
		// TODO Auto-generated method stub
		String hql = "select distinct(tel.customer_name) from TelrecTelInfo as tel where user_id in(select accountID from CAccount where companyID=:companyid)";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString("companyid", companyid);
		List list = query.list();
		return list;
	}

	/**
	 * 根据companyid查询所有的用户
	 * 
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryTelForUser_nameAll(String companyid) {
		// TODO Auto-generated method stub
		String hql = "select distinct(tel.user_name) from TelrecTelInfo as tel where user_id in(select accountID from CAccount where companyID=:companyid)";
		Query query = this.getSessionFactory().openSession().createQuery(hql);
		query.setString("companyid", companyid);
		List list = query.list();
		return list;
	}
}
