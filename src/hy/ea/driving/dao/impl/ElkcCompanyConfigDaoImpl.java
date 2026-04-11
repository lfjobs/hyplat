package hy.ea.driving.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.ea.driving.dao.ElkcCompanyConfigDao;
import hy.ea.util.DateUtil;
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
 * 生成教练初始时间段
 */
@Service
@Transactional
public class ElkcCompanyConfigDaoImpl implements ElkcCompanyConfigDao {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public  synchronized void  createTeacherTime(String companyId,String staffID,Date date){

		try {
			Connection ct = this.sessionFactory.getCurrentSession().connection();
			CallableStatement cs = ct.prepareCall("{call createTeacherOrderTime(?,?,?)}");
			cs.setString(1,companyId);
		    cs.setString(2,staffID);
			cs.setString(3, DateUtilElkc.generateDateString(date, "yyyy-MM-dd"));
			System.out.print(companyId);
			System.out.print(staffID);
			System.out.print(DateUtilElkc.generateDateString(date, "yyyy-MM-dd"));


			cs.execute();
		    cs.close();
		    ct.close();
			
		} catch (SQLException e) {	
			logger.info("调试信息");
			logger.error("操作异常", e);
		}
	}
	@Override
	public  synchronized void  updateTeacherLeave(String companyId,Date date){
		try {
			Connection ct = this.sessionFactory.getCurrentSession().connection();
			CallableStatement cs = ct.prepareCall("{call update_detail_time_by_leave(?,?)}");
			cs.setString(1,DateUtilElkc.generateDateString(date, "yyyy-MM-dd"));
			cs.setString(2,companyId);

			cs.execute();
			cs.close();
			ct.close();

		} catch (SQLException e) {
			logger.info("调试信息");
			logger.error("操作异常", e);
		}
	}

}
