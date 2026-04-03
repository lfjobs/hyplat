package com.tiantai.telrec.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.tiantai.telrec.bo.TelrecTelInInfo;
import com.tiantai.telrec.dao.TelInInfoDao;

public class TelInInfoDaoImpl implements TelInInfoDao {
	private SessionFactory sessionFactory;

	// 显示这个公司这个时间段下全部来电
	@SuppressWarnings("rawtypes")
	public List getTelInListForDate(String companyid, String date1, String date2) {

		String hql = "from TelrecTelInInfo where rela_companyid=? and (in_time between ? and ? )";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				hql);
		query.setString(0, companyid);
		query.setString(1, date1);
		query.setString(2, date2);

		return query.list();
	}

	// 显示员工这个时间段下全部来电
	@SuppressWarnings("rawtypes")
	public List getTelInListForUserid(String date1, String date2, String userid) {

		String hql = "from TelrecTelInInfo where userid=? and in_time between ? and ?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				hql);
		query.setString(0, date1);
		query.setString(1, date2);
		query.setString(2, userid);

		return query.list();
	}

	// 保存电话打入信息
	public void insertTelIn(TelrecTelInInfo info) {
		Session session = this.getSessionFactory().getCurrentSession();

		session.persist(info);

	}

	// 这个公司某时间段未接电话
	@SuppressWarnings("rawtypes")
	public List getMissedCallForAll(String date1, String date2, String companyid) {
		String hql = "from TelrecTelInInfo as in  where companyid=? and (in.in_time  between ? and ?) and in.telno not in(select info.telno from TelrecTelInfo as info  where info.start_time between ? and ?)";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(
				hql);
		query.setString(0, companyid);
		query.setString(1, date1);
		query.setString(2, date2);
		query.setString(3, date1);
		query.setString(4, date2);

		return query.list();
	}

	// 这个公司某人某时间段未接电话
	@SuppressWarnings("rawtypes")
	public List getMissedCallForUserId(String date1, String date2, String userid) {
		String hql = "  select  username,telno,to_char(in_time,'yyyy-MM-dd hh:mm:ss')    from      TelrecTelInInfo   where userid=? and (in_time  between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd')) ";
		Query query = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(hql);
		query.setString(0, userid);
		query.setString(1, date1);
		query.setString(2, date2);
		return query.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void deleteForIdForId(String telinId) {
		// TODO Auto-generated method stub
		String hql = "delete TelrecTelInInfo where id = ?  ";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(hql);
		query.setString(0, telinId);
		query.executeUpdate();
	}
}
