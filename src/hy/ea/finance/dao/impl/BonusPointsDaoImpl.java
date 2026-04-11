package hy.ea.finance.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hy.ea.finance.dao.BonusPointsDao;

@Service @Transactional
public class BonusPointsDaoImpl implements BonusPointsDao{

	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	@Override
	public synchronized void buyBonusPoints(String comid, String jum, String staffid, 
			String sccid, String money, String appstyle,String trade_no) {		
		try {
			Connection ct = this.sessionFactory.getCurrentSession().connection();
			CallableStatement cs = ct.prepareCall("{call PRO_BUYJIFEN(?,?,?,?,?,?,?)}");
			cs.setString(1,comid);
		    cs.setString(2,jum);
		    cs.setString(3,staffid);
		    cs.setString(4,sccid);
		    cs.setString(5,money);
		    cs.setString(6,appstyle);
			cs.setString(7,trade_no);
			cs.execute();
		    cs.close();
		    ct.close();
			
		} catch (SQLException e) {	
			logger.info("调试信息");
			logger.error("操作异常", e);
		}
	}

}
