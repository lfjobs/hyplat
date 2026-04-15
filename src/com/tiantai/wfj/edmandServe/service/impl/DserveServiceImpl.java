package com.tiantai.wfj.edmandServe.service.impl;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.edmandServe.bo.*;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.human.StaffCertificate;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import com.tiantai.wfj.edmandServe.service.DserveService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mobile.tiantai.android.util.JushMain;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lf on 2017-07-13.
 * 发布需求抢单实现类
 */
@Service
public class DserveServiceImpl implements DserveService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService bbservice;


    private final static Logger logger = LoggerFactory.getLogger(DserveServiceImpl.class);

    private String xhy = "scode20170714cnjcrn5jm20000000067"; //scode表中新行业id

    /**
     * 发布需求详情保存（发布需求）
     *
     * @param demandDetail
     * @return
     */
    @Transactional
    public String saveDetail(DemandDetail demandDetail) {
        String flag = "操作成功";

        //获取companyid
        /*StringBuilder cushql = new StringBuilder("SELECT t.companyid,t.staffid FROM T_ESHOP_CUSCOM t where t.companyid is not null");
        cushql.append(" and rownum= ? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid");
        Object p = this.beandao.getObjectBySqlAndParams(cushql.toString(), new Object[]{1, demandDetail.getDdsccid()});
        Object[] ps = (Object[]) p;
        if (ps != null && ps[0] != null) {*/
            demandDetail.setDdid(serverService.getServerID("ddetail"));
            demandDetail.setDdstatus("0");
            demandDetail.setDdBool('Y');
            demandDetail.setDdadddate(new Date());
            demandDetail.setDscount(0);
            try {
                //beandao.save(demandDetail);
                List<BaseBean> b = new ArrayList<>();
                b.add(demandDetail);
                b.add(saveBusinessTypeStaff(demandDetail.getDdstaffid(), demandDetail.getDdscodeid()));
                beandao.saveBeansListAndexecuteHqlsByParams(b, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                flag = "保存失败！";
            }
        /*} else {
            flag = "发布产品帐号数据错误！";
        }*/
        logger.error(flag);
        return flag;
    }

    /**
     * 根据发布帐号查询需求列表
     *
     * @param ddcid 发布关联id
     //* @param tab   0:(我的发布)发布过的需求但还未确认抢单对象  1:(发布记录)发布者已经选了抢单用户了（状态）以及 超时结束的
     * @param tab 0:已抢单 1:未抢单 2:已结算 3:全部
     * @param otype 1:个人 2:企业
     * @return
     */
    public List<BaseBean> detailListBySccid(String ddcid, String tab,String otype) {
        StringBuilder sql = new StringBuilder("from DemandDetail where ddcid=? and ddtype=?");
        List<Object> parme = new ArrayList<Object>();
        parme.add(ddcid);
        parme.add(otype);
        if (tab.equals("0")) {
            sql.append(" and (ddstatus =? or ddstatus=?) and dscount>0");
            parme.add("1");
            parme.add("4");
        } else if (tab.equals("1")){
            sql.append(" and ddstatus =? and dscount<=0");
            parme.add("0");
        } else if (tab.equals("2")){
            sql.append(" and ddstatus =?");
            parme.add("4");
        } else {
            sql.append(" and (ddstatus =? or ddstatus =? or ddstatus =? or ddstatus =?)");
            parme.add("0");
            parme.add("1");
            parme.add("2");
            parme.add("4");
        }
        sql.append(" order by ddadddate desc");
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(sql.toString(), parme.toArray());
        return list;
    }

    /**
     * 根据id查询需求详情
     *
     * @param ddid 需求id
     * @return
     */
    public DemandDetail detailByDdid(String ddid) {
        StringBuilder sql = new StringBuilder("from DemandDetail where ddid=?");
        List<Object> parme = new ArrayList<Object>();
        parme.add(ddid);
        DemandDetail ddetail = (DemandDetail) beandao.getBeanByHqlAndParams(sql.toString(), parme.toArray());
        return ddetail;
    }

    /**
     * 根据需求id查询已抢单责任人列表
     *
     * @param ddid 需求id
     * @return
     */
    public List<Object> serveListByDdid(String ddid) {
        StringBuilder sql = new StringBuilder("select c.companyname, t.account, hs.staffname,s.dsid");
        sql.append(" from dt_demand_serve s, t_eshop_cuscom t, dtcompany c, dt_hr_staff hs");
        sql.append(" where s.dscomid = c.companyid and s.dssccid = t.sccid and");
        sql.append(" s.dsstaffid = hs.staffid and s.dsddid=? and (s.dsstatus= ? or s.dsstatus= ?) order by s.dsdate desc");
        List<Object> parme = new ArrayList<Object>();
        parme.add(ddid);
        parme.add("0");
        parme.add("1");
        List<Object> objList = beandao.getListObjectBySqlAndParams(sql.toString(), parme.toArray());
        return objList;
    }

    /**
     * 根据地点查询需求列表
     *
     * @param address 地点
     * @return
     */
    public List<BaseBean> detailListByAddress(String address) {
        StringBuilder sql = new StringBuilder("from DemandDetail where ddsccid like ?");
        List<Object> parme = new ArrayList<Object>();
        parme.add("%" + address + "%");
        sql.append(" order by ddadddate desc");
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(sql.toString(), parme.toArray());
        return list;
    }

    /**
     * 根据时间查询需求列表
     *
     * @param pageSize 条数
     * @return
     */
    public List<BaseBean> detailListByDate(String pageSize, String wts,String wtype) throws Exception{
        List<Object> parme = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select new DemandDetail(ddid ,ddsccid ,ddtitle,ddremark,ddexpectdate,ddexpectprice,");
        sql.append("ddcontactname,ddcontactphone,ddaddress,coordinate ,ddworktype)");
        sql.append(" from DemandDetail where DDbool= ? and ddstatus=? ");
        //and rownum <= ?
        /*StringBuilder sql = new StringBuilder("from DemandDetail where DDbool= ? and ddstatus=?");*/
        parme.add("Y");
        parme.add("0");
        /*parme.add(Long.parseLong(pageSize));*/
        if (wtype == null||wtype.isEmpty()||wtype.equals("0")) {
            if (wts != null && !wts.equals("")) {
                String[] wt = wts.split(",");
                for (int i = 0; i < wt.length; i++) {
                    if (i == 0) {
                        sql.append(" and (");
                    } else {
                        sql.append(" or");
                    }
                    sql.append(" ddscodeid=?");
                    parme.add(wt[i]);
                    if (i == (wt.length - 1)) {
                        sql.append(" )");
                    }
                }
                sql.append(" or");
            } else {
                sql.append(" and");
            /*sql.append("and ddscodeid=?");
            parme.add("");*/
            }
            sql.append(" ddworktype=?");
            parme.add("服务平台");
        }

        sql.append(" order by ddadddate desc");
        return bbservice.getListBeanByHqlAndParams(sql.toString(), parme.toArray());
    }

    /**
     * 分页查询需求列表
     *
     * @param pageNumber 当前页数
     * @param wts        工种id集合
     * @return
     */
    public PageForm detailListBywts(int pageNumber, String wts,String wtype) {
        PageForm pageForm = null;
        try {
            List<Object> parme = new ArrayList<Object>();
            StringBuilder pfsql = new StringBuilder("from DemandDetail where DDbool= ? and ddstatus=? ");
            parme.add("Y");
            parme.add("0");
            if (wtype == null||wtype.isEmpty()||wtype.equals("0")) {
                if (wts != null && !wts.equals("")) {
                    String[] wt = wts.split(",");
                    for (int i = 0; i < wt.length; i++) {
                        if (i == 0) {
                            pfsql.append(" and ((");
                        } else {
                            pfsql.append(" or");
                        }
                        pfsql.append(" ddscodeid=?");
                        parme.add(wt[i]);
                        if (i == (wt.length - 1)) {
                            pfsql.append(" )");
                        }
                    }
                    pfsql.append(" or");
                } else {
                    pfsql.append(" and (");
                /*pfsql.append("and (ddscodeid=?");
                parme.add("");*/
                }
                pfsql.append(" ddworktype=?)");
                parme.add("服务平台");
            }
            pfsql.append(" order by ddadddate desc");
            pageForm = bbservice.getPageForm(pageNumber, 10, pfsql+"", parme.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForm;
    }

    /**
     * 根据抢单人查询需求列表
     *
     * @param cid
     * @param pageNumber 当前页数
     * @return
     */
    public PageForm detailListBydssccid(String cid, int pageNumber) {
        PageForm pageForm = null;
        try {
            List<Object> parme = new ArrayList<Object>();
            StringBuilder pfsql = new StringBuilder("SELECT D.DDID, D.DDCONTACTPHONE, to_char(D.ddadddate,'yyyy-mm-dd'), D.DDTITLE, ddworktype FROM DT_DEMAND_DETAIL D, DT_DEMAND_SERVE S ");
            pfsql.append("WHERE D.DDID = S.DSDDID AND (S.DSSTATUS=? OR S.DSSTATUS=?) AND dscid=? AND D.DDSTATUS = ?");
            StringBuilder pfcsql = new
                    StringBuilder("select count(*) FROM DT_DEMAND_DETAIL d,DT_DEMAND_SERVE s ");
            pfcsql.append("WHERE D.DDID = S.DSDDID AND (S.DSSTATUS=? OR S.DSSTATUS=?) AND dscid=? AND D.DDSTATUS = ?");
            parme.add('1');
            parme.add('4');
            parme.add(cid);
            parme.add('1');
            pfsql.append(" ORDER BY S.DSDATE DESC");
            pageForm = bbservice.getPageFormBySQL(pageNumber, 6, pfsql.toString(), pfcsql.toString(), parme.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForm;
    }

    /**
     * 根据抢单人查询需求列表
     *
     * @param cid 抢单人id
     * @return
     */
    public List<Object> detailListBydssccid(String cid) {
        List<Object> objects = null;
        try {
            List<Object> parme = new ArrayList<Object>();
            StringBuilder pfsql = new StringBuilder("select d.ddid,d.ddcontactphone,d.ddadddate,d.ddtitle,d.ddworktype from DT_DEMAND_DETAIL d,DT_DEMAND_SERVE s ");
            pfsql.append("where d.ddid= s.dsddid and dsstatus=? and dscid=? and ddstatus = ?");
            parme.add('0');
            parme.add(cid);
            parme.add('0');
            pfsql.append(" order by s.dsdate desc");
            objects = beandao.getListObjectBySqlAndParams(pfsql.toString(), parme.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * 查询1-2级行业
     *
     * @return
     */
    public List<BaseBean> industryList(String level, String pid) {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        List<Object> pamar = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select s.codeid,s.codepid,s.codenumber,s.codevalue,s.codesn from dtscode s where ");
        sql.append(" (s.codenumber = ? or s.codenumber = ?)");
        if (level != null && level.equals("3")) {
            pamar.add("2");
            pamar.add("3");
            pamar.add(xhy);
        } else {
            pamar.add("4");
            pamar.add("5");
            pamar.add(pid);
        }
        sql.append(" start with s.codepid= ? connect by prior s.codeid=s.codepid order by s.codesn");
        beanList = this.beandao.getListBeanBySqlAndParams(sql.toString(), pamar.toArray());
        return beanList;
    }

    /**
     * 查询新行业
     *
     * @return
     */
    public List<BaseBean> industryListNew(String level, String pid) {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        List<Object> pamar = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select s.TYPE_ID,s.TYPE_PID,s.TYPE_LEVEL,s.TYPE_NAME,s.TYPE_NUM from DT_BUSINESS_TYPE s where ");
        sql.append(" (s.TYPE_LEVEL = ? or s.TYPE_LEVEL = ?) and s.status='1'");
        if (level != null && level.equals("3")) {
            pamar.add("2");
            pamar.add("3");
            sql.append(" start with s.TYPE_PID is null");
            //pamar.add( xhy );
        } else {
            pamar.add("4");
            pamar.add("5");
            sql.append(" start with s.TYPE_PID= ?");
            pamar.add(pid);
        }
        sql.append(" connect by prior s.TYPE_ID=s.TYPE_PID order by s.TYPE_LEVEL,s.TYPE_NUM");
        beanList = this.beandao.getListBeanBySqlAndParams(sql.toString(), pamar.toArray());
        return beanList;
    }

    /**
     * 查询新行业
     *
     * @return
     */
    public List<BaseBean> industryListNew(String typeName) {
        List<Object> pamar = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select s.TYPE_ID,s.TYPE_PID,s.TYPE_LEVEL,s.TYPE_NAME,s.TYPE_NUM from DT_BUSINESS_TYPE s where ");
        sql.append(" s.TYPE_LEVEL = ? and s.status='1' and s.TYPE_NAME like ?");
        pamar.add("5");
        pamar.add("%" + typeName + "%");
        List<BaseBean> beanList = this.beandao.getListBeanBySqlAndParams(sql.toString(), pamar.toArray());
        return beanList;
    }

    /**
     * 保存抢单数据
     *
     * @param ddid  需求id
     * @param sccid 抢单帐号id
     * @param cid 关联id (来源不同关联表不同,来源1:关联staff 2:关联company)
     * @param otype 来源 1:个人 2:企业
     * @param dsaddress 抢单人地址
     * @param coordinate 抢单人经纬度
     * @return
     */
    @Transactional
    public synchronized String saveServe(String ddid, String sccid,String cid,String otype,String dsaddress,String coordinate) {
        String flag = "操作成功";
        String hql = "from DemandDetail where ddid=?";
        try {
            boolean isflag = false;
            DemandDetail demandDetail = (DemandDetail) this.beandao.getBeanByHqlAndParams(hql, new Object[]{ddid});
            if (demandDetail != null) {
                String ddsccid = demandDetail.getDdsccid();
                if (demandDetail.getDdstatus() != null && demandDetail.getDdstatus().equals("0")) {
                    if (demandDetail.getDdBool() != null && demandDetail.getDdBool().equals('Y')) {
                        if (ddsccid != null && !demandDetail.getDdBool().equals("")) {
                            if (!ddsccid.equals(sccid)) {
                                isflag = true;
                            } else {
                                flag = "不可以抢自己发布的需求！";
                            }
                        } else {
                            flag = "单据数据错误！";
                        }
                    } else {
                        flag = "抢单的人太多了，明天再来吧！";
                    }
                } else {
                    if (demandDetail.getDdstatus().equals("1")) {
                        flag = "该需求已经被他人抢走！";
                    } else if (demandDetail.getDdstatus().equals("2")) {
                        flag = "该需求已经被移除！";
                    }
                }
            } else {
                flag = "该需求不存在！";
            }
            if (isflag) {
                String type = demandDetail.getDdtitle();
                Integer count = dsCount(ddid, null);
                if ((count < 5 && !"新注册用户".equals(type)) || ("新注册用户".equals(type))) {
                    if ("新注册用户".equals(type)) {
                        if (otype .equals("2")) {
                            flag = "企业用户，不能抢";
                            return flag;
                        }
                        TEshopCusCom cc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{sccid});
                        int ccType = Integer.parseInt(cc.getCusType());
                        if (ccType < 2) {
                            flag = "您的级别太高，不能抢";
                            return flag;
                        }
                        if (ccType > 5) {
                            flag = "您的级别太低，不能抢";
                            return flag;
                        }
                    }
                    Integer countIs = dsCount(ddid, sccid);
                    if (countIs <= 0) {
                        //获取companyid
                        StringBuilder cushql = new StringBuilder("SELECT t.companyid FROM T_ESHOP_CUSCOM t where t.companyid is not null");
                        cushql.append(" and rownum= ? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid");
                        Object comid = this.beandao.getObjectBySqlAndParams(cushql.toString(), new Object[]{1, demandDetail.getDdsccid()});
                        if (comid != null || !comid.equals("")) {
                            List<BaseBean> beanList = new ArrayList<BaseBean>();

                            //Object staffid = beandao.getObjectBySqlAndParams("select staffid from t_eshop_cuscom where sccid=?", new Object[]{sccid});

                            DemandServe demandServe = new DemandServe();
                            demandServe.setDsid(serverService.getServerID("dserve"));
                            demandServe.setDsddid(ddid);
                            demandServe.setDssccid(sccid);
                            demandServe.setDscid(cid);
                            demandServe.setDstype(otype);
                            demandServe.setDsstatus("0");
                            demandServe.setDscomid(comid.toString());
                            demandServe.setDsdate(new Date());
                            demandServe.setDsaddress(dsaddress);
                            demandServe.setCoordinate(coordinate);
                            beanList.add(demandServe);
                            if (count == 4 && !"新注册用户".equals(type)) {
                                demandDetail.setDdBool('N');
                            }
                            demandDetail.setDscount(count + 1);
                            beanList.add(demandDetail);
                            beandao.executeHqlsByParmsList(beanList, null, null);
                            //推送服务
                            List<String> list = new ArrayList<String>();
                            TEshopCusCom ccDl = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{demandDetail.getDdsccid()});
                            list.add(ccDl.getAccount());
                            JushMain.sendjiguangMessage("有用户抢你的单了", null, null, "someoneDq", list);

                        } else {
                            flag = "您的帐号数据错误！";
                        }
                    } else {
                        flag = "这个单你已经抢过了！";
                    }
                } else {
                    flag = "抢单的人太多了，明天再来吧！";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = "抢单失败!";
        }
        return flag;
    }

    /**
     * 确认接单人员
     *
     * @param ddid 需求id
     * @param dsid 抢单人表id
     * @return
     */
    @Transactional
    public synchronized String updateDetail(String ddid, String dsid) {
        String flag = "操作成功";
        String[] hqls = new String[3];
        List<Object[]> pames = new ArrayList<Object[]>();
        Integer count = beandao.getConutByByHqlAndParams("select count(*) from DemandServe where dsddid=? and dsid=?", new Object[]{ddid, dsid});
        if (count == 1) {
            try {
                hqls[0] = "update DemandServe set dsstatus=? where dsddid=? and dsid=?";
                pames.add(new Object[]{"1", ddid, dsid});
                hqls[1] = "update DemandDetail set ddstatus=?,dscount=? where ddid=?";
                pames.add(new Object[]{"1", 0, dsid});
                hqls[2] = "delete DemandServe where dsddid=? and dsid<> ? ";
                pames.add(new Object[]{ddid, dsid});
                beandao.executeHqlsByParmsList(null, hqls, pames);
            } catch (Exception e) {
                e.printStackTrace();
                flag = "操作失败！";
            }
        } else {
            flag = "抢单人信息错误！";
        }
        return flag;
    }

    /**
     * 查询工种列表
     *
     * @param cwcid 关联id
     * @param user  微分金帐号
     * @param type 类别 1:个人 2:企业
     * @return
     */
    public List<BaseBean> wtypeListBySccid(String cwcid, String user,String type) {
        List<String> barem=new ArrayList<>();
        StringBuilder hql=new StringBuilder("from TCtomerWorktype ");
        hql.append("where cwstatus=?");
        /*hql.append(" and cwcid=? and cwtype=?");*/
        barem.add("0");
        /*barem.add(cwcid);
        barem.add(type);*/
        if (user == null||user.isEmpty()) {
            hql.append(" and cwstaffid=?");
            barem.add(cwcid);
        }else {
            Object cusid = cusidByUser(user);
            hql.append(" and cwcustid=?");
            barem.add(cusid.toString());
        }
        hql.append(" order by cwdate");
        List<BaseBean> beanList = beandao.getListBeanByHqlAndParams(hql.toString(), barem.toArray());

        return beanList;
    }

    /**
     * 判断是否有工种
     *
     * @param sccid 微分金帐号id
     * @param user  微分金帐号
     * @return
     */
    public JSONObject wtypeCountBySccid(String sccid, String user) {
        JSONObject a = new JSONObject();
        boolean bool = false;
        List<Object[]> pames = new ArrayList<Object[]>();
        Object cusid = cusidByUser(user);
        if (cusid != null && !cusid.equals("")) {
            Integer count = cwtCountByUser(cusid.toString());
            if (count > 0) {
                List<Object> tws = beandao.getListObjectBySqlAndParams("select cw.cwscodeid from t_ctomer_worktype cw where cw.cwcustid= ? and cw.cwstatus= ? ", new Object[]{cusid, "0"});
                a.accumulate("twslist", tws);
                bool = true;
            }
        } else {
            a.accumulate("flag", "抢单人信息错误！");
        }
        a.accumulate("bool", bool);
        return a;
    }

    /**
     * 保存工种
     *
     * @param user    帐号
     * @param tcw 工种信息
     * @return
     */
    @Transactional
    public Map<String, Object> saveWtype(String user, TCtomerWorktype tcw) {
        Map<String, Object> map = new HashMap<String, Object>();
        String flag = "操作成功";
        try {
            Object cusid = cusidByUser(user);
            if (cusid != null && !cusid.equals("")) {
                Integer count = cwtCountByUser(cusid.toString());
                if (count < 5) {
                    String cwid = serverService.getServerID("cworktype");
                    /*TCtomerWorktype tctomerWorktype = new TCtomerWorktype();
                    BeanUtils.copyProperties(tcw,tctomerWorktype);*/
                    tcw.setCwcustid(cusid.toString());
                    tcw.setCwdate(new Date());
                    tcw.setCwid(cwid);
                    tcw.setCwstatus("0");
                    List<BaseBean> b = new ArrayList<>();
                    b.add(tcw);
                    b.add(saveBusinessTypeStaff(tcw.getCwstaffid(), tcw.getCwscodeid()));
                    beandao.saveBeansListAndexecuteHqlsByParams(b,null,null);

                    map.put("cwid", cwid);
                } else {
                    flag = "添加工种以达到上线！";
                }
            } else {
                flag = "帐号信息错误！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = "操作失败！";
        }
        map.put("flag", flag);
        return map;
    }

    /**
     * 删除工种
     *
     * @param cwtid 工种id
     * @return
     */
    @Transactional
    public String delWtype(String cwtid) {
        String flag = "操作成功";
        try {
            StringBuilder delsql = new StringBuilder("update TCtomerWorktype set cwstatus = ?  where cwkey = ? and cwstatus = ? ");
            beandao.saveBeansListAndexecuteHqlsByParams(null, new String[]{delsql.toString()}, new Object[]{"1", cwtid, "0"});
        } catch (Exception e) {
            e.printStackTrace();
            flag = "操作失败！";
        }
        return flag;
    }

    /**
     * 查看
     * @param cwkey
     * @return
     */
    @Override
    public TCtomerWorktype getWtypeByKey(String cwkey) {
        TCtomerWorktype sc=(TCtomerWorktype)beandao.getBeanByKey(TCtomerWorktype.class,cwkey);
        return sc;
    }

    /**
     * 根据需求id查询抢单条数
     *
     * @param ddid 需求id
     * @return
     */
    private Integer dsCount(String ddid, String sccid) {
        List<Object> pames = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select count(*) from DemandServe where dsddid=? and dsstatus=?");
        pames.add(ddid);
        pames.add("0");
        if (sccid != null && !sccid.equals("")) {
            sql.append(" and dssccid= ? ");
            pames.add(sccid);
        }
        return beandao.getConutByByHqlAndParams(sql.toString(), pames.toArray());
    }

    /**
     * 根据帐号查询帐号密码表id
     *
     * @param user 帐号
     * @return
     */
    private Object cusidByUser(String user) {
        Object cusid = beandao.getObjectBySqlAndParams("select t.custid from t_eshop_customer t where t.account= ? and logOff=0 ", new Object[]{user});
        return cusid;
    }

    /**
     * 根据帐号查询工种条数
     *
     * @param cusid 帐号密码表id
     * @return
     */
    private Integer cwtCountByUser(String cusid) {
        return beandao.getConutByByHqlAndParams("select count(*) from TCtomerWorktype where cwcustid=? and cwstatus=?", new Object[]{cusid, "0"});
    }

    /**
     * 可以被抢单的列表，注册用户无推荐人
     *
     * @return
     */
    public PageForm zhuceqdList(int pageNumber) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select dd.ddtitle,dd.ddadddate,dd.ddcontactphone,dd.ddcontactname,dd.ddid from dt_demand_detail dd");
        sql.append(" where dd.ddworktype = ? and dd.ddstatus = ? order by dd.ddadddate desc");
        String title = "服务平台";
        String status = "0";
        PageForm pageForm = this.bbservice.getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", new Object[]{title, status});
        //List<BaseBean> list=this.bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {title, status });
        //System.out.println(list);
        return pageForm;
    }


    /**
     * 用户已抢 抢单服务（服务平台）集合
     * @param cid 用户id
     * @return
     */
    public List detailhyListBydssccid(String cid) {
        List<BaseBean> list = null;
        try {
            List<Object> parme = new ArrayList<Object>();
            StringBuilder pfsql = new StringBuilder("SELECT D.DDID, D.DDCONTACTPHONE, to_char(D.ddadddate,'yyyy-mm-dd'), D.DDTITLE, ddworktype FROM DT_DEMAND_DETAIL D, DT_DEMAND_SERVE S ");
            pfsql.append("WHERE D.DDID = S.DSDDID AND D.ddworktype=? AND (S.DSSTATUS=? OR S.DSSTATUS=?) AND dscid=? AND D.DDSTATUS = ?");
            parme.add("服务平台");
            parme.add('1');
            parme.add('4');
            parme.add(cid);
            parme.add('1');
            pfsql.append(" ORDER BY S.DSDATE DESC");
            list= bbservice.getListBeanBySqlAndParams(pfsql.toString(), parme.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 收单服务,查询用户已经抢到的单
     */
    public PageForm shouDan(String sccid, int pageNumber, String phone, String title) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select dd.ddid,dd.ddcontactphone,to_char(dd.ddadddate,'yyyy-mm-dd hh:mm:ss'),dd.ddtitle,dd.ddaddress");
        sql.append(" from dt_demand_detail dd,dt_demand_serve ds ");
        sql.append(" where dd.ddid = ds.dsddid and ds.dsstatus = ? and dd.ddstatus = ?  and ds.dssccid = ?");
        params.add("1");//dsstatus =1代表通过，ddstatus = 1 代表已通过
        params.add("1");
        params.add(sccid);
        if (phone != null && !phone.equals("")) {
            sql.append(" and dd.ddcontactphone like ?");
            params.add("%" + phone + "%");
        }
        if (title != null && !title.equals("")) {
            sql.append(" and dd.ddtitle like ?");
            params.add("%" + title + "%");
        }
        sql.append(" order by dd.ddadddate desc ");
        PageForm pageForm = this.bbservice
                .getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", params.toArray());
        return pageForm;
    }

    /**
     * 抢单人详细信息
     */
    public Object selQdPerDetail(String dsid) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select hs.headimage,hs.staffname,t.account,hs.reference,c.companyname,hs.industryType,hs.position,s.dsid,t.sccid");
        sql.append(" from dt_demand_serve s, t_eshop_cuscom t, dtcompany c, dt_hr_staff hs");
        sql.append(" where s.dscomid = c.companyid and s.dssccid = t.sccid and s.dsstaffid = hs.staffid");
        sql.append(" and t.acquiesce = ? and s.dsid = ?");
        params.add("01");
        params.add(dsid);
        Object obj = bbservice.getObjectBySqlAndParams(sql.toString(), params.toArray());
        return obj;
    }
    /**
     * 确认抢单人
     * @param beans
     * @param dsid
     * @throws Exception
     */
    private void isOKDemand(List<BaseBean> beans,String dsid) throws Exception{
        //修改dt_demand_serve表中的状态dsstatus 为1代表通过
        DemandServe ds = (DemandServe) bbservice.getBeanByHqlAndParams("from DemandServe where dsid = ?", new Object[]{dsid});
        if (ds != null) {
            ds.setDsstatus("1");
            beans.add(ds);
        }
        //修改dt_demand_detail表的状态ddstatus = 1 已确认订单
        DemandDetail dd = (DemandDetail) bbservice.getBeanByHqlAndParams("from DemandDetail where ddid = ?", new Object[]{ds.getDsddid()});
        if (dd != null) {
            dd.setDdstatus("1");
            beans.add(dd);
        }
    }

    /**
     * 保存确认抢单人信息
     * @param dsid
     * @return
     */
    @Transactional
    public String[] isOKDemand(String dsid){
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String[] personInfor = new String[2];
        try {
            isOKDemand(beans,dsid);
            bbservice.saveBeansListAndexecuteHqlsByParams(beans, null, null);
            personInfor[0] = "";
            personInfor[1] = "";
        }catch (Exception e){
            e.printStackTrace();
        }
        return personInfor;
    }

    /**
     * 新注册用户确认抢单成功人
     *
     * @param sccid   抢单人sccid
     * @param account 抢单人account
     * @param dsid 单据id
     * @param cc
     * @return
     */
    @Transactional
    public String[] insureQdPerson(String sccid, String account, String dsid, TEshopCusCom cc) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String[] personInfor = new String[2];
        try {
            //给本人添加上级
            cc.setSupperSccId(sccid);
            cc.setSuperioragent(account);
            beans.add(cc);
            TEshopCusCom tec = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{sccid});
            //和推荐人互粉
            JoinFans jf1 = (JoinFans) bbservice.getBeanByHqlAndParams("from JoinFans where zsccId=?", new Object[]{cc.getSccId()});
            jf1.setFaccount(account);
            jf1.setFansDate(new Date());
            jf1.setFsccId(sccid);
            jf1.setStaffid(tec.getStaffid());
            beans.add(jf1);
            JoinFans jf2 = (JoinFans) bbservice.getBeanByHqlAndParams("from JoinFans where fsccId=?", new Object[]{cc.getSccId()});
            jf2.setFansDate(new Date());
            jf2.setZaccount(account);
            jf2.setZsccId(sccid);
            beans.add(jf2);
            //邀请人修改
            MarKeting mk = (MarKeting) bbservice.getBeanByHqlAndParams("from MarKeting where userSccId=?", new Object[]{cc.getSccId()});
            mk.setMkdate(new Date());
            mk.setMkSccId(sccid);
            mk.setMkuserID(account);
            beans.add(mk);

            isOKDemand(beans,dsid);
            bbservice.saveBeansListAndexecuteHqlsByParams(beans, null, null);
            Object[] obj = (Object[]) bbservice.
                    getObjectBySqlAndParams("select s.staffname,e.custype,e.account from t_eshop_cuscom e,dt_hr_staff s where e.staffid = s.staffid and e.sccid = ?"
                            , new Object[]{sccid});
            String insurePerson = obj[0].toString();
            switch (obj[1].toString()) {
                case "2":
                    insurePerson = insurePerson + "公司";
                    break;
                case "3":
                    insurePerson = insurePerson + "合伙人商城业主会员";
                    break;
                case "4":
                    insurePerson = insurePerson + "微分金商城业主会员";
                    break;
                case "5":
                    insurePerson = insurePerson + "代理商商城业主会员";
                    break;
            }
            personInfor[0] = insurePerson;
            personInfor[1] = obj[2].toString();
            //推送服务
            List<String> list = new ArrayList<String>();
            list.add(personInfor[1]);
            JushMain.sendjiguangMessage("恭喜您已成功抢到新注册用户", null, cc.getAccount(), "lowerlevelsucceed", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personInfor;
    }

    /**
     * 判断登录用户的级别以及推介人是否还是默认的
     * 比较抢单用户的级别和登录用户的级别
     */
    public String isPanduan(String sccid, String dlSccid) {
        String flag = "正常";
        TEshopCusCom ccDl = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{dlSccid});
        //判断登录用户的级别以及推介人是否还是默认的
        //默认推荐人TEshopCusCom20161010W9FXK9NJ450000011682
        if (!"TEshopCusCom20161010W9FXK9NJ450000011682".equals(ccDl.getSupperSccId())) {
            flag = "已经有推荐人了";
            return flag;
        }
        int custypeDl = Integer.parseInt(ccDl.getCusType());
        TEshopCusCom ccQd = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{sccid});
        int custypeQd = Integer.parseInt(ccQd.getCusType());
        if (custypeQd < 2 || custypeQd > 5) {
            flag = "推荐人级别有误";
            return flag;
        }
        if (custypeDl <= custypeQd) {
            flag = "推荐人级别太低";
        }
        return flag;
    }

    /**
     * 根据人员id获取点击量最多的行业
     * @param staffid 人员id
     * @return
     */
    public List<BaseBean> industryListNewBybtid(String staffid) {
        List<Object> pamar = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT S.TYPE_ID, S.TYPE_PID, S.TYPE_LEVEL, S.TYPE_NAME, S.TYPE_NUM ");
        sql.append(" FROM DT_BUSINESS_TYPE S LEFT JOIN DT_BUSINESSTYPE_STAFF B ON B.BTYPEID=S.TYPE_ID ");
        sql.append(" WHERE S.STATUS = '1' AND B.BSSTAFFID=?");
        sql.append(" ORDER BY B.CLICKNUM DESC");
        pamar.add(staffid);
        List<BaseBean> beanList = this.beandao.getListBeanBySqlAndParams(sql.toString(), pamar.toArray());
        return beanList;
    }

    /**
     * 处理人员关联行业数据
     *
     * @param bssid 人员 id
     * @param btid  行业 id
     * @return
     */
    private businessTypeStaff saveBusinessTypeStaff(String bssid, String btid) {
        String hql = "from businessTypeStaff where bsStaffid=? and btypeId=?";
        businessTypeStaff b = (businessTypeStaff) beandao.getBeanByHqlAndParams(hql, new Object[]{bssid, btid});
        int num = 0;
        if (b == null) {
            b=new businessTypeStaff();
            b.setBsid(serverService.getServerID("btstaff"));
            b.setBsStaffid(bssid);
            b.setBtypeId(btid);
        } else {
            num = b.getClickNum();
        }
        b.setClickNum(num + 1);
        return b;
    }


}
