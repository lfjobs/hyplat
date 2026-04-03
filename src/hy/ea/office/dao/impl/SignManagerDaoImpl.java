package hy.ea.office.dao.impl;

import hy.ea.bo.office.SignManager;
import hy.ea.office.dao.SignManagerDao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignManagerDaoImpl implements SignManagerDao {

	@Resource
	private SessionFactory sessionFactory;
	private static final long ONEDAYCOUNT = 1000 * 60 * 60 * 24;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean DeleteSignManager(String signmanagerkey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void InsertSignManager(SignManager signmanager) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(signmanager);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getSignManager(String signmanagerkey) {
		// TODO Auto-generated method stub
		String hql = "from SignManager where signmanagerkey=:signmanagerkey";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("signmanagerkey", signmanagerkey);
		List list = query.list();
		return list;
	}
	
	/**
	 * 修改印章位置
	 * @param position 印章位置
	 * @param signmanagerkey
	 */
	@Override
	public boolean UpdatePosition(String position, String signmanagerkey) {
		// TODO Auto-generated method stub
		String hql = "update SignManager set position=:position where signmanagerkey=:signmanagerkey";
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("signmanagerkey", signmanagerkey);
		query.setString("position", position);
		return query.executeUpdate() > 0 ? true : false;
	}

	/**
	 * 修改印章状态
	 * @param signstat 印章状态
	 * @param signmanagerkey
	 */
	public boolean UpdateSignstat(String signstat, String signmanagerkey) {
		// TODO Auto-generated method stub
		String hql = "update SignManager set signstat=:signstat where signmanagerkey=:signmanagerkey";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("signmanagerkey", signmanagerkey);
		query.setString("signstat", signstat);
		return query.executeUpdate() > 0 ? true : false;
	}

	public boolean UpdateRelationtable(String signstat, String relationid) {
		// TODO Auto-generated method stub
		String hql = "update Relation r set r.signstat=:signstat where r.signid=:signid and r.id=:relationid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("signstat", signstat);
		query.setString("relationid", relationid);
		return query.executeUpdate() > 0 ? true : false;
	}

	/**
	 * 查询印章
	 * @param accountid
	 * @param rp
	 * @param pages
	 * @param signstat 印章状态
	 * @param signid 印章ID		
	 * @param relationtable	关联的表
	 * @param starttime	盖章时间
	 * @param endtime
	 * @return
	 */
	@SuppressWarnings({"static-access", "rawtypes" })
	public List QuerySignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime)
	{
		StringBuffer hql = new StringBuffer("from SignManager where signmanagerid=:accountid ");
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			hql.append(" and signstat=:signstat ");
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			hql.append(" and signid=:signid ");
		}
		if(relationtable!=null && !relationtable.equals("null") && !relationtable.equals(""))
		{
			hql.append(" and relationtable=:relationtable ");
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			hql.append(" and datetime between :starttime and :endtime ");
		}
		hql.append(" order by datetime desc ");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("accountid", accountid);
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			query.setString("signstat", signstat);
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			query.setString("signid", signid);
		}
		if(relationtable!=null && !relationtable.equals("null") && !relationtable.equals(""))
		{
			query.setString("relationtable", relationtable);
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals("") && endtime!=null && !endtime.equals("null") && !endtime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(endtime.getTime()+this.ONEDAYCOUNT));
		}
		else if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(starttime.getTime()+this.ONEDAYCOUNT));
		}
		query.setMaxResults(rp);
		query.setFirstResult((pages - 1) * rp );
		List list = query.list();
		return list; 
	}

	/**
	 * 统计印章
	 * @param accountid
	 * @param rp
	 * @param pages
	 * @param signstat 印章状态
	 * @param signid 印章ID
	 * @param relationtable 关联的表
	 * @param starttime 盖章时间
	 * @param endtime
	 * @return
	 */
	@SuppressWarnings({"static-access" })
	public int CountSignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime)
	{
		StringBuffer hql = new StringBuffer("select count(*) from SignManager where signmanagerid=:accountid ");
		
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			hql.append(" and signstat=:signstat ");
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			hql.append(" and signid=:signid ");
		}
		if(relationtable!=null && !relationtable.equals("null") && !relationtable.equals(""))
		{
			hql.append(" and relationtable=:relationtable ");
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			hql.append(" and datetime between :starttime and :endtime ");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("accountid", accountid);
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			query.setString("signstat", signstat);
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			query.setString("signid", signid);
		}
		if(relationtable!=null && !relationtable.equals("null") && !relationtable.equals(""))
		{
			query.setString("relationtable", relationtable);
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals("") && endtime!=null && !endtime.equals("null") && !endtime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(endtime.getTime()+this.ONEDAYCOUNT));
		}
		else if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(starttime.getTime()+this.ONEDAYCOUNT));
		}
		int cnt = ((Long) query.uniqueResult()).intValue();
		return cnt; 
	}

	@SuppressWarnings({"static-access", "rawtypes" })
	public List QueryRelationTable(String accountid, int rp, int pages, String relationid, String signstat, String signid, Date starttime, Date endtime)
	{
		StringBuffer hql = new StringBuffer("from Relation r where r.accountid=:accountid ");
		if(relationid!=null && !relationid.equals("null") && !relationid.equals(""))
		{
			hql.append(" and r.id=:relationid ");
		}
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			hql.append(" and r.signstat=:signstat ");
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			hql.append(" and r.signid=:signid ");
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			hql.append(" and r.datetime between :starttime and :endtime ");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("accountid", accountid);
		if(relationid!=null && !relationid.equals("null") && !relationid.equals(""))
		{
			query.setString("relationid", relationid);
		}
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			query.setString("signstat", signstat);
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			query.setString("signid", signid);
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals("") && endtime!=null && !endtime.equals("null") && !endtime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(endtime.getTime()+this.ONEDAYCOUNT));
		}
		else if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(starttime.getTime()+this.ONEDAYCOUNT));
		}
		query.setMaxResults(rp);
		query.setFirstResult((pages - 1) * rp );
		List list = query.list();
		return list; 
	}
	
	@SuppressWarnings({  "static-access" })
	public int CountRelationTable(String accountid, String relationid, String signstat, String signid, Date starttime, Date endtime)
	{
		StringBuffer hql = new StringBuffer("select count(*) from Relation r where r.accountid=:accountid) ");
		if(relationid!=null && !relationid.equals("null") && !relationid.equals(""))
		{
			hql.append(" and r.id=:relationid ");
		}
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			hql.append(" and r.signstat=:signstat ");
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			hql.append(" and r.signid=:signid ");
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			hql.append(" and r.datetime between :starttime and :endtime ");
		}
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setString("accountid", accountid);
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			query.setString("relationid", relationid);
		}
		if(signstat!=null && !signstat.equals("null") && !signstat.equals(""))
		{
			query.setString("signstat", signstat);
		}
		if(signid!=null && !signid.equals("null") && !signid.equals(""))
		{
			query.setString("signid", signid);
		}
		if(starttime!=null && !starttime.equals("null") && !starttime.equals("") && endtime!=null && !endtime.equals("null") && !endtime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(endtime.getTime()+this.ONEDAYCOUNT));
		}
		else if(starttime!=null && !starttime.equals("null") && !starttime.equals(""))
		{
			query.setDate("starttime", starttime);
			query.setDate("endtime", new Date(starttime.getTime()+this.ONEDAYCOUNT));
		}
		int cnt = ((Long) query.uniqueResult()).intValue();
		return cnt; 
	}
	
}


