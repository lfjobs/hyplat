package hy.plat.dao.impl;

import hy.ea.bo.invoicing.voucher.Subs;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

/**
 * 定时器调动类
 * @author lou
 *
 */
@Service
public class HistoryDao {
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanService baseBeanService;
	
	/**
	 * 每月初计算上月期末和本月期初值并计算
	 * 
	 * @param 
	 */
	@SuppressWarnings({ "unchecked", "deprecation"})
	public void filingHistory(int a){
		Date dd=new Date();
		String month=String.valueOf(dd.getMonth());
		String moString=String.valueOf(Integer.parseInt(month)+1);
		int year=dd.getYear()+1900;
		if(moString.length()==1){
			moString="0"+moString;
		}
		if(month.length()==1){
			moString="0"+(Integer.parseInt(month)+1);
			month="0"+month;
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",java.util.Locale.US);
		int day=Utilities.getDayMouth(year, Integer.parseInt(month));
		List<Object> lists=new ArrayList<Object>();
		String sql="select ins.subjectsid,ins.companyid from dt_inv_subs ins where ins.datess=? group by ins.subjectsid,ins.companyid";
		lists=baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd")});
		if(a>0){
			sql="select ins.subjectspid,ins.companyid from dt_inv_subs ins where ins.datess=? and ins.currentlevel=? group by ins.subjectspid,ins.companyid order by ins.currentlevel desc";
			lists=baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"),a});
		}
		if(lists.size()>0){
			for (int i = 0; i < lists.size(); i++) {
				Object[] objs=(Object[])lists.get(i);
				float bj=0f;
				float bd=0f;
				String hql="from Subs where subjectsID=? and companyID=? and datess=?";
				Query query = sessionFactory.getCurrentSession().createQuery(hql).setString(0, objs[0].toString()).setString(1, objs[1].toString()).setDate(2, Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"));
				
				query.setMaxResults(1);
				Subs subs=(Subs)query.uniqueResult();
				if(subs!=null){
					float monye=0;
					if(a>0){
						String hql2="from Subs where subjectsPID=? and companyID=? and datess=?";
						query = sessionFactory.getCurrentSession().createQuery(hql2).setString(0, objs[0].toString()).setString(1, objs[1].toString()).setDate(2, Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"));
						List<BaseBean> list2=query.list();
						for (int j = 0; j < list2.size(); j++) {
							Subs subs2=(Subs)list2.get(j);
							bj=bj+Float.valueOf(subs2.getCloan());
							bd=bd+Float.valueOf(subs2.getCforLoan());
							if(subs2.getEdirection().equals("贷")){
								monye=monye+Float.valueOf("-"+subs2.getEndCash());
							}else{
								monye=monye+Float.valueOf(subs2.getEndCash());
							}
						}
					}else{
						List<Object> listo=new ArrayList<Object>();
						if(subs.getSdirection().equals("贷")){
							monye=Float.valueOf("-"+subs.getStartCash());
						}else{
							monye=Float.valueOf(subs.getStartCash());
						}
						try {
							listo = baseBeanService.getListBeanBySqlAndParams("select ivs.* from dt_inv_vouchers ivs,dt_inv_voucher ivr where ivr.voucherid=ivs.voucherid and ivs.subjectsID=? and ivr.tallyDate between ? and ? and ivr.companyid=? and ivr.status=?",new Object[]{subs.getSubjectsNumbers(),sdFormat.parse(year+"-"+month+"-01 00:00:00"),sdFormat.parse(year+"-"+month+"-"+day+" 23:59:59"),subs.getCompanyID(),"10"});
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(listo.size()>0){
							for (int j = 0; j < listo.size(); j++) {
								Object[] objs2=(Object[])listo.get(j);
								if(objs2[8]!=null){
									monye=monye+Float.valueOf(objs2[8].toString());
									bj=bj+Float.valueOf(objs2[8].toString());
								}else if(objs2[9]!=null){
									monye=monye-Float.valueOf(objs2[9].toString());
									bd=bd+Float.valueOf(objs2[9].toString());
								}
							}
						}
					}
					subs.setCforLoan(String.valueOf(bd));
					subs.setCloan(String.valueOf(bj));
					String tos=String.valueOf(monye);
					if(subs.getStartCash().substring(0,1).equals("-")){
						subs.setStartCash(subs.getStartCash().substring(1));
					}
					if(tos.substring(0,1).equals("-")){
						subs.setEdirection("贷");
						subs.setEndCash(tos.substring(1));
						monye=Float.parseFloat(tos.substring(1));
					}else if(tos.equals("0.0")){
						subs.setEdirection("平");
						subs.setEndCash(String.valueOf(monye));
					}else{
						subs.setEdirection("借"); 
						subs.setEndCash(String.valueOf(monye));
					}
					sessionFactory.getCurrentSession().persist(subs);
					if(!month.equals("11")){
						Subs subs2=new Subs();
						if(a>0){
							query = sessionFactory.getCurrentSession().createQuery(hql).setString(0, objs[0].toString()).setString(1, objs[1].toString()).setDate(2, Utilities.getDateFromString(String.valueOf(year)+"-"+moString, "yyyy-MM"));
							query.setMaxResults(1);
							subs2=(Subs)query.uniqueResult();
						}else{
							subs2.setCompanyID(subs.getCompanyID());
							subs2.setSubearlyID(serverService.getServerID("subs"));
							subs2.setSubjectsPID(subs.getSubjectsPID());
							subs2.setCurrentLevel(subs.getCurrentLevel());
							subs2.setSubjectsID(subs.getSubjectsID());
							subs2.setSubjectsName(subs.getSubjectsName());
							subs2.setSubjectsNumbers(subs.getSubjectsNumbers());
							subs2.setDatess(Utilities.getDateFromString(String.valueOf(year)+"-"+moString, "yyyy-MM"));
						}
						subs2.setStartCash(String.valueOf(monye));
						subs2.setSdirection(subs.getEdirection());
						sessionFactory.getCurrentSession().persist(subs2);
					}
				}
			}
		}
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public int getbya(){
		Date dd=new Date();
		String month=String.valueOf(dd.getMonth());
		String moString=String.valueOf(Integer.parseInt(month)+1);
		int year=dd.getYear()+1900;
		if(moString.length()==1){
			moString="0"+moString;
		}
		if(month.length()==1){
			moString="0"+(Integer.parseInt(month)+1);
			month="0"+month;
		}
		List<String> lists=baseBeanService.getListBeanBySqlAndParams("select ins.currentlevel from dt_inv_subs ins where ins.datess=? group by ins.currentlevel,ins.subjectspid,ins.companyid order by ins.currentlevel desc",new Object[]{Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd")});
		return Integer.parseInt(lists.get(0));
	}
}
