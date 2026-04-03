package hy.ea.finance.dao.impl;

import hy.ea.finance.dao.CommissionDao;
import hy.ea.util.Converter;
import hy.plat.bo.BaseBean;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommissionDaoImpl
  implements CommissionDao
{
  @Resource
  private SessionFactory sessionFactory;

  public Map<String,Object> GetDesignPageList(String comId)
  {
    List list = new ArrayList();
    List list2 = new ArrayList();
    Map<String,Object> map=new HashMap<String, Object>();
    try {
      Connection ct = this.sessionFactory.getCurrentSession().connection();
      CallableStatement cs = ct.prepareCall("{call PRO_COMMISSION_DESIGN(?,?,?)}");
      cs.setString(1, comId);
      
      cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
      cs.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
      cs.execute();

      ResultSet rs = (ResultSet) cs.getObject(2);
      if (rs == null)return null;
      ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
      int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
      while (rs.next()) {
      	Object [] objr=new Object[columnCount];
          for (int i = 1; i <= columnCount; i++) {
          	objr[i-1]=rs.getString(i); 
          }
          list.add(objr);
      }
      
      
      ResultSet rs2 = (ResultSet) cs.getObject(3);
      if (rs2 == null)return null;
      ResultSetMetaData md2 = rs2.getMetaData();
      int columnCount2 = md2.getColumnCount();
      while (rs2.next()) {
        	Object [] objr=new Object[columnCount2];
            for (int i = 1; i <= columnCount2; i++) {
            	objr[i-1]=rs2.getString(i); 
            }
            list2.add(objr);
        }

      rs.close();
      rs2.close();
      cs.close();
      ct.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    map.put("level", list2);
    map.put("data", list);
    return map;
  }
}