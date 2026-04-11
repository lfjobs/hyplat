package hy.ea.finance.dao.impl;

import hy.ea.finance.dao.transferDao;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service @Transactional
public class transferDaoImpf implements transferDao{
	@Resource
	private SessionFactory sessionFactory;

    private Logger logger = LoggerFactory.getLogger(transferDaoImpf.class);
	
	/**
	 * 发货   一键发货
	 *@param com_id 供应商id
	 * @param cashi_id 根据ID单条发货      如果批量发货  则为null
	 * @param fh_staff_id 发货人id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<String> onkeyfh(String com_id,String fh_staff_id,String cashi_id) {
		long a= System.currentTimeMillis();//获取当前系统时间(毫秒)
        logger.error(fh_staff_id+"-"+com_id+"-"+cashi_id+"发货存储过程执行开始");
		List<String> list=new ArrayList<String>();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call on_Key_fh(?,?,?,?,?,?)}");
			cs.setString(1, com_id);
			
			cs.setString(2, fh_staff_id);
			
			cs.setString(3,cashi_id==null?"%%":cashi_id);
		    cs.registerOutParameter(4, java.sql.Types.VARCHAR);
		    cs.registerOutParameter(5, java.sql.Types.VARCHAR);
		    cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.execute();		
			
			list.add((String) cs.getObject(4));
			list.add((String) cs.getObject(5));
			list.add((String) cs.getObject(6));

			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
        logger.error(cashi_id+"发货存储过程执行时间为："+(System.currentTimeMillis()-a));
		return list;
	}
	
	/**
	 * 分配金币
	 * @param com_id 供应商公司id
	 * @param cashid 订单id
	 * @param staff_id 操作人id
	 * @param money 供应商成本(金币)
	 * @return
	 */
	@Override
	public String Distribution(String com_id,String cashid,String staff_id,String money) throws Exception{
		String b="";
		String cashnum="";
        // 1.得到连接
        Connection ct = sessionFactory.getCurrentSession().connection();
        // 2.创建CallableStatement
        CallableStatement cs = ct.prepareCall("{call GET_ASSIGN_COST(?,?,?,?)}");
        cs.setString(1, cashid);
        cs.setString(2, staff_id);
        cs.setString(3, money);
        cs.registerOutParameter(4, java.sql.Types.VARCHAR);
        cs.execute();
        cashnum=(String) cs.getObject(4);
        // 4、关闭
        cs.close();
        ct.close();

		return cashnum;
	}
	
	/**
	 * 分配给供应商成本
	 * @param com_id 公司id
	 * @param cashid 订单id
	 * @param staff_id 操作人id
	 * @param money 供应商成本金币个数
	 */
	@SuppressWarnings("deprecation")
	public String cost(String com_id,String cashid,String staff_id,String money){
		String b="";
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call GET_cost(?,?,?,?)}");
			cs.setString(1, com_id);
			cs.setString(2, cashid);
			cs.setString(3, staff_id);
			cs.setString(4, money);
			cs.execute();
			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			String a=e.getMessage().toString().substring(3);
			b=a.substring(a.indexOf(":")+1,a.indexOf("ORA"));
			//logger.error("操作异常", e);
		}
		return b;
	}
	
	/**
	 * 修改订单状态
	 * @param cashid 订单id
	 * @param status 要改成的状态
	 */
	@SuppressWarnings("deprecation")
	public String getCoasUpdate(String cashid,String status){
		String b="";
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call get_cost_update(?,?)}");
			cs.setString(1, cashid);
			cs.setString(2, status);
			cs.execute();		
			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			String a=e.getMessage().toString().substring(3);
			b=a.substring(a.indexOf(":")+1,a.indexOf("ORA"));
			//logger.error("操作异常", e);
		}
		return b;
	}

	/**
	 * 虚拟发货自动收货
	 * @param cashid 订单id
	 * @param staffid 操作人id
	 */
	@SuppressWarnings("deprecation")
	public void virtual(String cashid ,String staffid){
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call pro_xuni_fahuo(?,?)}");
			cs.setString(1, cashid);
			cs.setString(2, staffid);
			cs.execute();		
			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}
	
	/**
	 * 用户确认收货
	 * @param cashierBillsID 订单id
	 */
	@SuppressWarnings("deprecation")
	@Override
	public synchronized void recognitionHarvest(String cashierBillsID) {
		// TODO Auto-generated method stub
		try {
		    Connection ct = this.sessionFactory.getCurrentSession().connection();
		    CallableStatement cs = ct.prepareCall("{call on_Key_sh(?)}");
		    cs.setString(1,cashierBillsID);
		    cs.execute();
		    cs.close();
		    ct.close();
		  } catch (Exception e) {
		    logger.info("调试信息");
		    logger.error("操作异常", e);
		  }
	}
	
	/**
	 * 金币充值
	 * @param comid 公司id
	 * @param jum 订单编号
	 * @param staffid 付款人id
	 * @param money 购买金币金额
	 * @param appstyle 支付方式
	 * @param trade_no 第三方交易号
	 */
	@SuppressWarnings("deprecation")
	@Override
	public synchronized void buyJinbi(String comid,String jum,String staffid,String sccid,String money,String appstyle,String trade_no) {
		// TODO Auto-generated method stub
		try {
		    Connection ct = this.sessionFactory.getCurrentSession().connection();
		    CallableStatement cs = ct.prepareCall("{call PRO_BUYJINBI(?,?,?,?,?,?,?)}");
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
		  } catch (Exception e) {
		    logger.info("调试信息");
		    logger.error("操作异常", e);
		  }
	}
}
