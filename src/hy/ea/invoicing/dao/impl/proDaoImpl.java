package hy.ea.invoicing.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.invoicing.dao.proDao;
import hy.ea.util.Converter;
import hy.plat.bo.BaseBean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class proDaoImpl implements proDao {
	@Resource
	private SessionFactory sessionFactory;
	
	
	/**
	 * 月结存储过程调用
	 * @param groupsh 集团标示
	 * @param ym 月结年月
	 * @param com_id 公司id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void ProDMCO(String groupsh,String ym,String com_id) {
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call PRO_TOL_INVVOU_FORYM(?,?,?)}");
			cs.setString(1, groupsh); // 
			cs.setString(2, com_id); // 
			cs.setString(3, ym); // 
			cs.execute();		
			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}
	
	/**
	 * 年结存储过程调用
	 * @param groupsh 集团标示
	 * @param ym 年结年月
	 * @param com_id 公司id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String ProDYCO(String groupsh,String ym,String com_id) {
		String b="";
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call PRO_TOL_INVVOU_YY(?,?,?)}");
			cs.setString(1, groupsh); // 
			cs.setString(2, com_id); // 
			cs.setString(3, ym); // 
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
	 * 试算表存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目
	 *  7.部门id
	 *  8.报表类型【报表类型 A：总额式，B：余额式】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List<BaseBean> ProBalances(Object[] obj,Class clazz) {
		List<BaseBean> list=new ArrayList<BaseBean>();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call pro_try_balances(?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < obj.length; i++) {
				Object obj2=obj[i];
				if(i==4){
					obj2=(obj2==null||obj2.equals("")?"10000000":obj2);
				}else if(i==5){
					obj2=(obj2==null||obj2.equals("")?"99999999":obj2);
				}
				cs.setString(i+1, obj2==null||obj2.equals("")?"ALL":obj2.toString());
			}
			cs.registerOutParameter(9, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(9);
			Converter c = new Converter();
			
			Collection jihe=c.get(rs, clazz);
			for (Iterator it = jihe.iterator(); it.hasNext();) {
				BaseBean ltest = (BaseBean) it.next();
				list.add(ltest);
	           }
			
			// 4、关闭
			rs.close();
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.info("调试信息");  
			logger.error("操作异常", e);
		}
		return list;
	}
	
	/**
	 * 总额式现金银行流水存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目  
	 *  7.部门id
	 *  8.现金银行注记 【A.银行，B.现金】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<Object[]> ProZEDays(Object[] obj,Class clazz) {
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call pro_bc_days(?,?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < obj.length; i++) {
				Object obj2=obj[i];
				if(i==4){
					obj2=obj2==null||obj2.equals("")?"10000000":obj2;
				}else if(i==5){
					obj2=obj2==null||obj2.equals("")?"99999999":obj2;
				}
				cs.setString(i+1, obj2==null||obj2.equals("")?"ALL":obj2.toString());
			}
			cs.setString(9, "B");
			cs.registerOutParameter(10, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(10);
			if (rs == null)return Collections.EMPTY_LIST;
	        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
	        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
	        while (rs.next()) {
	        	Object [] objr=new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	            	objr[i-1]=rs.getString(i); 
	            }
	            list.add(objr);
	        }
			
			
			// 4、关闭
			rs.close();
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.info("调试信息");  
			logger.error("操作异常", e);
		}
		return list;
	}
	
	/**
	 * 余额式现金银行流水存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.作业id
	 *  2.公司ID
	 *  3.起始日期
	 *  4.终止日期
	 *  5.起始科目
	 *  6.终止科目  
	 *  7.部门id
	 *  8.现金银行注记 【A.银行，B.现金】
	 * @param clazz 返回实体类型
	 */
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<Object[]> ProYEDays(Object[] obj,Class clazz) {
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call pro_bc_days(?,?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < obj.length; i++) {
				Object obj2=obj[i];
				if(i==4){
					obj2=obj2==null||obj2.equals("")?"10000000":obj2;
				}else if(i==5){
					obj2=obj2==null||obj2.equals("")?"99999999":obj2;
				}
				cs.setString(i+1, obj2==null||obj2.equals("")?"ALL":obj2.toString());
			}
			cs.setString(9, "A");
			cs.registerOutParameter(10, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(10);
			if (rs == null)return Collections.EMPTY_LIST;
	        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
	        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
	        while (rs.next()) {
	        	Object [] objr=new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	            	objr[i-1]=rs.getString(i); 
	            }
	            list.add(objr);
	        }
			
			// 4、关闭
			rs.close();
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.info("调试信息");  
			logger.error("操作异常", e);
		}
		return list;
	}
	
	/**
	 * 资产负债表、损益表存储过程调用
	 * @param obj 参数集合
	 * 集合排序：
	 *  1.起始日期
	 *  2.终止日期
	 *  3.报表类别（A:资产负债表  B:损益表 C:资产负债表以及损益表 ）
	 *  4.单位注记：Y:千元    N:元
	 *  5.是否列印明细：Y:列印明细 N:不列印明细
	 *  6.作业id
	 *  7.公司ID
	 */
	@SuppressWarnings("deprecation")
	public void probcflexp(Object[] obj) {
		try {
			// 1.得到连接
			Connection ct = sessionFactory.getCurrentSession().connection();
			// 2.创建CallableStatement
			CallableStatement cs = ct.prepareCall("{call pro_bc_flex_p(?,?,?,?,?,?,?)}");
			for (int i = 0; i < obj.length; i++) {
				Object obj2=obj[i];
				cs.setString(i+1, obj2==null||obj2.equals("")?"ALL":obj2.toString());
			}
			cs.execute();
			// 4、关闭
			cs.close();
			ct.close();
		} catch (Exception e) {
			logger.info("调试信息");  
			logger.error("操作异常", e);
		}
	}
}
