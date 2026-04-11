package hy.ea.st.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.st.service.EnrollsService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
@Transactional
@Service
public class EnrollsServiceImpl implements EnrollsService {

    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;

    public static String URL = "https://api.map.baidu.com/location/ip?";   //百度地图定位

    public static String AK = "TcVGuuIiIXftuZKGcSz3ns8DI8vhR02j";  //百度apiKey

    public List<BaseBean> getProductByType(String sql ,String ccompanyId){
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql,new Object[]{ccompanyId});
        return list;
    }

    public List<BaseBean> findPro(String sql,String ccompanyId){
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql,new Object[]{ccompanyId});
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveEnroll(Enroll enroll){
        beandao.save(enroll);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAccount(CAccount account){
        beandao.save(account);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addRelation(ContactRelation contactRelation){
        beandao.save(contactRelation);
    }
    /**
     *
     * 获取促销品
     * @param ppid
     * @param ptppid
     * @return
     */
    public List<Object> getPromotionList(String ppid,String ptppid){
        //促销品
        StringBuilder sql = new StringBuilder();
        sql.delete(0, sql.length());
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p,dt_promotion pt");
        sql.append(" where p.ppid = ps.ppid and ps.ppid=pt.ptppid and pt.ppid=? and ps.fcom_id is null and pt.ptppid in(");
        StringBuffer stemp = new StringBuffer();
        stemp.append("order by case pt.ptppid ");
        List<Object> sp = new ArrayList<Object>();
        List<Object> params = new ArrayList<Object>();
        params.add(ppid);
        List<Object> list = new ArrayList<Object>();
        if (ptppid != null && ptppid.length() > 0) {
            String[] temp = ptppid.split(",");
            for (int i = 0; i < temp.length; i++) {
                if (i == temp.length - 1) {
                    sql.append("?");
                } else {
                    sql.append("?,");
                }
                stemp.append("when ? then '" + i + "'");
                sp.add(temp[i]);
                params.add(temp[i]);
            }
            stemp.append(" else pt.ptppid end");
            sql.append(" )");
            sql.append(stemp.toString());
            params.addAll(sp);
            list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));

        }
        return list;
    }


    /**
     * 根据人员ID查询StaffID
     * @return
     */
    public TEshopCusCom getTEshopByStaffID(String staffID){
        TEshopCusCom tc = null;
        if(staffID!=null&&!staffID.equals("")) {

            String hql = "from TEshopCusCom where sccId = (select f.sccid from Staff f where staffID = ?)";
            tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffID});
        }
        return tc;

    }

    /**
     *
     * 生成5l5C账号
     * @param phone
     * @param companyID
     */
    public void generateAccount(String phone,String companyID,String coachStaffID){
        TEshopCusCom ts = null;
        if(coachStaffID!=null&&!coachStaffID.equals("")) {
              ts = getTEshopByStaffID(coachStaffID);
        }
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        TEshopCusCom tr = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?",new Object[]{phone,"01"});
       if(tr!=null) {

            if("TEshopCusCom20161010W9FXK9NJ450000011682".equals(tr.getSupperSccId())) {
                if (ts != null && Float.parseFloat(ts.getCusType()) <= 5) {
                    tr.setSuperioragent(ts.getAccount());
                    tr.setSupperSccId(ts.getSccId());
                    beanList.add(tr);
                    String hql = "from MarKeting where usersccid = ?";
                    MarKeting mk = (MarKeting) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tr.getSccId()});

                    mk.setMkuserID(ts.getAccount());
                    mk.setMkSccId(ts.getSccId());

                    beanList.add(mk);
                    String hqlfs1 = "from JoinFans where zsccId = ? and fsccId = ?";
                    String hqlfs2 = "from JoinFans where fsccId = ? and zsccId = ?";
                    JoinFans jf1 = (JoinFans) baseBeanService.getBeanByHqlAndParams(hqlfs1, new Object[]{tr.getSccId(),"TEshopCusCom20161010W9FXK9NJ450000011682"});
                    if (jf1 != null) {
                        jf1.setFaccount(ts.getAccount());
                        jf1.setFsccId(ts.getSccId());
                    }
                    beanList.add(jf1);
                    JoinFans jf2 = (JoinFans) baseBeanService.getBeanByHqlAndParams(hqlfs2, new Object[]{tr.getSccId(),"TEshopCusCom20161010W9FXK9NJ450000011682"});
                    if (jf2 != null) {
                        jf2.setZaccount(ts.getAccount());
                        jf2.setZsccId(ts.getSccId());
                    }
                    beanList.add(jf2);
                }
            }


           String hql = "from CAccount where staffID = ? and companyID = ?";
           CAccount account = (CAccount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tr.getStaffid(),companyID});
           if(account==null) {
            Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff  where staffID = ?",new Object[]{tr.getStaffid()});
            account = new CAccount();
            account.setAccountID(serverService.getServerID("account"));
           Company com = (Company) beandao.getObjectByHqlAndParams("from Company d where d.companyID=?", new String[]{companyID});
           String hql2 = "from CRole r where r.roleName like ?";
           CRole role = (CRole) beandao.getBeanByHqlAndParams(hql2, new String[]{"%客户%"});
           account.setCompanyID(com.getCompanyID());
           account.setCompanyName(com.getCompanyName());
           account.setRoleID(role.getRoleID());
           account.setCompany(com);
           account.setAccountPassword(Utilities.MD5("123456"));
           account.setAccountName(staff.getStaffName());
           account.setAccountEmail(phone);
           account.setStaffID(tr.getStaffid());
           account.setAccountStatus("00");
           account.setAccountOnLine("00");
            beanList.add(account);
           }

           ContactRelation contactRelation = new ContactRelation();
           contactRelation.setRelationID(serverService.getServerID("contactrelation"));
           contactRelation.setRelation("学员");
           contactRelation.setCompanyID(companyID);
           contactRelation.setStaffID(tr.getStaffid());
           beanList.add(contactRelation);

          baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList,null,null);

       }

    }

    /**
     *
     *
     * @param ppid
     * @param sccid
     * @return
     */
    public String validateEnroll(String sccid,String licenceType,String companyID){

        StringBuilder jxsql = new StringBuilder();
        jxsql.append("select count(*) from dt_order_bill_add a, dtCashierBills b, Dtgoodsbills c,dt_productpackaging p");
        jxsql.append(" where a.oa_bill_id = b.cashierbillsid");
        jxsql.append(" and b.cashierbillsid = c.cashierbillsid and p.ppid = c.ppid and b.fkstatus not in('01','13','18','15')");
        jxsql.append(" and c.typeid=? and a.oa_sccid = ? and p.categoryname=? and p.companyID = ? and b.status!=?");

        int count = baseBeanService.getConutByBySqlAndParams(jxsql.toString(), new Object[]{"学员报名",sccid,licenceType,companyID,"99"});
        logger.info("值：{}", count);
        String  tips  = "";
        if (count > 0) {
            tips = "同一车型不可重复报名";
        }

        return tips;

    }

    /**
     *
     * 根据账号查询SCCID
     * @param account
     * @return
     */
    public   String getSccIdByAccount(String account){
          String sccId = "";
          String hql = "from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?";
          TEshopCusCom tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account,"01"});
          if(tc!=null){
              sccId = tc.getSccId();
          }

        return sccId;
    }


    /**
     * 默认ak
     * 选择了ak，使用IP白名单校验：
     * 根据您选择的AK已为您生成调用代码
     * 检测到您当前的ak设置了IP白名单校验
     * 您的IP白名单中的IP非公网IP，请设置为公网IP，否则将请求失败
     * 请在IP地址为xxxxxxx的计算发起请求，否则将请求失败
     */
    public StringBuffer requestGetAK(Map<String, String> param) throws Exception {
        if (URL == null || URL.length() <= 0 || param == null || param.size() <= 0) {
            return null;
        }

        param.put("ak", AK);
        StringBuffer queryString = new StringBuffer();
        queryString.append(URL);
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            //第一种方式使用的 jdk 自带的转码方式   两种均可
            queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&");
            //第二种方式使用的 spring 的转码方法
            //queryString.append(UriUtils.encodeUri((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        URL url = new URL(queryString.toString());
        URLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(Utilities.decodeUnicode(line));
        }
        reader.close();
        isr.close();
        return buffer;
    }

    /**
     * 默认ak
     * 选择了ak，使用IP白名单校验：
     * 根据您选择的AK已为您生成调用代码
     * 检测到您当前的ak设置了IP白名单校验
     * 您的IP白名单中的IP非公网IP，请设置为公网IP，否则将请求失败
     * 请在IP地址为xxxxxxx的计算发起请求，否则将请求失败
     */
    public void requestGetAKjpg(Map<String, String> param) throws Exception {
        if (param == null || param.size() <= 0) {
            return ;
        }

        StringBuffer queryString = new StringBuffer();
        queryString.append("https://api.map.baidu.com/staticimage/v2");
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            //    第一种方式使用的 jdk 自带的转码方式  第二种方式使用的 spring 的转码方法 两种均可
             queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&");
            //queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        java.net.URL url = new URL(queryString.toString());
        logger.info("调试信息");
        URLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        isr.close();
        logger.info("调试信息");
    }
}
