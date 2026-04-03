package hy.ea.invoicing.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import hy.ea.invoicing.dao.EstablishDao;

public class EstablishDaoImpl implements EstablishDao{
	 @Resource
	  private SessionFactory sessionFactory;
	@SuppressWarnings("deprecation")
	@Override
	public void establishGoodsNumber(Object[] obj) {
		// TODO Auto-generated method stub
		  try {
			     Connection ct = this.sessionFactory.getCurrentSession().connection();
			     CallableStatement cs = ct.prepareCall("{call establish_goods_number(?,?,?,?,?,?,?,?)}");
			     for(int i=0;i<obj.length;i++){
			    	 if(i==6){
			    		 cs.setInt(i+1,(Integer)obj[i]);
			    	 }
			    	 cs.setString(i+1,obj[i].toString());
			     }
			     cs.execute();
			     cs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
