package hy.ea.driving.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.ea.driving.dao.AflovacationDao;
import hy.ea.util.elkc.DateUtilElkc;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * xgb
 */
@Service
@Transactional
public class AflovacationDaoImpl implements AflovacationDao {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public  synchronized void  updateOrderRecord(String trainer_id, String substitutename, String relay_trainer_id, Date begin, Date end){

		try {
			Connection ct = this.sessionFactory.getCurrentSession().connection();
			CallableStatement cs = ct.prepareCall("{call UPDATE_ORDER_RECORD(?,?,?,?,?)}");
			cs.setString(1,trainer_id);
		    cs.setString(2,substitutename);
			cs.setString(3,relay_trainer_id);
			cs.setString(4, DateUtilElkc.generateDateString(begin, "yyyy-MM-dd HH:mm:ss"));
			cs.setString(5, DateUtilElkc.generateDateString(end, "yyyy-MM-dd HH:mm:ss"));
			System.out.print(trainer_id);
			System.out.print(substitutename);
			System.out.print(relay_trainer_id);
			System.out.print(DateUtilElkc.generateDateString(begin, "yyyy-MM-dd HH:mm:ss"));
			System.out.print(DateUtilElkc.generateDateString(end, "yyyy-MM-dd HH:mm:ss"));


			cs.execute();
		    cs.close();
		    ct.close();
			
		} catch (SQLException e) {	
			logger.info("调试信息");
			logger.error("操作异常", e);
		}
	}
}
