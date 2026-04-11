package hy.ea.finance.service.brokerage.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.brokerage.PActPrice;
import hy.ea.bo.finance.brokerage.PActivity;
import hy.ea.bo.finance.brokerage.PActivityBrokerage;
import hy.ea.finance.action.brokerage.ActivityAction;
import hy.ea.finance.service.brokerage.ActivityService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2018-12-21.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    ServerService serverService;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(ActivityAction.class);

    /**
     * 相关活动查询
     *
     * @param map
     * @return 2018-12-20
     */
    @Override
    public PageForm selectActivityList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String activityType = (String) map.get("activityType");//活动类型
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        List<Object> parms = new ArrayList<Object>();
        sql.append("select dpa.activityid,dpa.activityname,dpa.activitydescribe,");
        sql.append("to_char(dpa.starttime,'yyyy/MM/dd HH24:MI:SS'),to_char(dpa.endtime,'yyyy/MM/dd HH24:MI:SS')");
        sql.append(",dpa.principal,to_char(dpa.addtimes,'yyyy/MM/dd HH24:MI:SS') ,dpa.state,dpa.type ,");
        sql.append("to_char(dpa.updateTimes,'yyyy/MM/dd HH24:MI:SS') from dt_pro_activity dpa ");
        sql.append("where dpa.state <> '04' and dpa.companyid = ? ");
        parms.add(companyId);
        if(!"all".equals(activityType)){
            sql.append("and dpa.type = ?");
            parms.add(activityType);
        }

        //参数非空校验
        if (search != null && !search.equals("")) {
            sql.append(" and dpa.activityname like ?");
            parms.add("%" + search.trim() + "%");
        }
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }


    /**
     * 获取没有设置活动价佣金并且已设置零售价佣金的产品
     * 2018-12-20
     *
     * @return
     */
    @Override
    public PageForm getProductByStatus(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        sql.append("select dp.ppid,dp.barcode,dp.goodsname from dt_productpackaging dp  ");
        sql.append("where dp.activityStatus ='00' and dp.yjstatus = '01' and dp.companyid = ?");
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        if (search != null && !search.equals("")) {
            sql.append(" and (dp.goodsname like ?");
            sql.append(" or dp.barcode like ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }


    /**
     * 获取相关活动
     *
     * @param activityId
     * @return
     */
    @Override
    public Object[] pActivityById(String activityId) {
        String sql = "select dpa.activityid,dpa.activityname,dpa.activitydescribe,dpa.activitypicture," +
                "to_char(dpa.starttime,'yyyy/MM/dd HH24:MI:SS'),to_char(dpa.endtime,'yyyy/MM/dd HH24:MI:SS')," +
                "dpa.principal,to_char(dpa.addtimes,'yyyy/MM/dd HH24:MI:SS') ,dpa.state,dpa.type from " +
                "dt_pro_activity dpa where dpa.activityid = ?";
        Object[] params = (Object[]) this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{activityId});
        return params;
    }


    /**
     * 获取活动修改回显数据
     *
     * @param activityId
     * @return
     */
    @Override
    public PActivity getPActivityById(String activityId) {
        String hql = "from PActivity where activityId = ?";
        return (PActivity) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{activityId});
    }

    /**
     * 添加活动佣金
     * 2019-1-5
     *
     * @param pActPriceList
     * @param pActPrice
     * @return
     */
    @Override
    public Map<String, Object> addActivityBrokerage(List<PActPrice> pActPriceList, PActPrice pActPrice) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (pActPriceList != null && pActPriceList.size() > 0 && pActPrice != null) {//非空校验
            HttpServletRequest request = ServletActionContext.getRequest();
            //获取后台用户登录信息
            HttpSession session = request.getSession();
            CAccount cac = (CAccount) session.getAttribute("account");
            //非空校验
            if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
                int size = pActPriceList.size();
                for (int i = 0; i < size; i++) {
                    //活动价添加
                    PActPrice pActPrice2 = pActPriceList.get(i);
                    String actPriceId = serverService.getServerID("actPriceId");
                    PActPrice pActPrice1 = new PActPrice();
                    pActPrice1.setActPriceId(actPriceId);
                    pActPrice1.setActPrice(pActPrice2.getActPrice());
                    pActPrice1.setFactory(pActPrice2.getFactory());
                    pActPrice1.setBrokerage(pActPrice2.getBrokerage());
                    pActPrice1.setBrokerages(pActPrice2.getBrokerages());
                    pActPrice1.setInvestType(pActPrice2.getInvestType());
                    pActPrice1.setPpid(pActPrice2.getPpid());
                    pActPrice1.setCompanyId(cac.getCompanyID());
                    pActPrice1.setActivityId(pActPrice.getActivityId());
                    pActPrice1.setState("00");
                    this.baseBeanService.save(pActPrice1);

                    //活动价佣金添加
                    PActivityBrokerage pActivityBro1 = new PActivityBrokerage();
                    String activitybroId1 = serverService.getServerID("activitybroId");
                    pActivityBro1.setActivitybroId(activitybroId1);
                    pActivityBro1.setBrokerage(pActPrice2.getSbtz());
                    pActivityBro1.setPpid(pActPrice2.getPpid());
                    pActivityBro1.setTypePpid(pActPrice.getSbtzId());
                    pActivityBro1.setCompanyId(cac.getCompanyID());
                    pActivityBro1.setActPriceId(actPriceId);
                    pActivityBro1.setState("00");
                    this.baseBeanService.save(pActivityBro1);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro2 = new PActivityBrokerage();
                    String activitybroId2 = serverService.getServerID("activitybroId");
                    pActivityBro2.setActivitybroId(activitybroId2);
                    pActivityBro2.setBrokerage(pActPrice2.getTp());
                    pActivityBro2.setPpid(pActPrice2.getPpid());
                    pActivityBro2.setTypePpid(pActPrice.getTpId());
                    pActivityBro2.setCompanyId(cac.getCompanyID());
                    pActivityBro2.setActPriceId(actPriceId);
                    pActivityBro2.setState("00");
                    this.baseBeanService.save(pActivityBro2);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro3 = new PActivityBrokerage();
                    String activitybroId3 = serverService.getServerID("activitybroId");
                    pActivityBro3.setActivitybroId(activitybroId3);
                    pActivityBro3.setBrokerage(pActPrice2.getSbaz());
                    pActivityBro3.setPpid(pActPrice2.getPpid());
                    pActivityBro3.setTypePpid(pActPrice.getSbazId());
                    pActivityBro3.setCompanyId(cac.getCompanyID());
                    pActivityBro3.setActPriceId(actPriceId);
                    pActivityBro3.setState("00");
                    this.baseBeanService.save(pActivityBro3);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro4 = new PActivityBrokerage();
                    String activitybroId4 = serverService.getServerID("activitybroId");
                    pActivityBro4.setActivitybroId(activitybroId4);
                    pActivityBro4.setBrokerage(pActPrice2.getSjdl());
                    pActivityBro4.setPpid(pActPrice2.getPpid());
                    pActivityBro4.setTypePpid(pActPrice.getSjdlId());
                    pActivityBro4.setCompanyId(cac.getCompanyID());
                    pActivityBro4.setActPriceId(actPriceId);
                    pActivityBro4.setState("00");
                    this.baseBeanService.save(pActivityBro4);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro5 = new PActivityBrokerage();
                    String activitybroId5 = serverService.getServerID("activitybroId");
                    pActivityBro5.setActivitybroId(activitybroId5);
                    pActivityBro5.setBrokerage(pActPrice2.getXjdl());
                    pActivityBro5.setPpid(pActPrice2.getPpid());
                    pActivityBro5.setTypePpid(pActPrice.getXjdlId());
                    pActivityBro5.setCompanyId(cac.getCompanyID());
                    pActivityBro5.setActPriceId(actPriceId);
                    pActivityBro5.setState("00");
                    this.baseBeanService.save(pActivityBro5);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro6 = new PActivityBrokerage();
                    String activitybroId6 = serverService.getServerID("activitybroId");
                    pActivityBro6.setActivitybroId(activitybroId6);
                    pActivityBro6.setBrokerage(pActPrice2.getCjdl());
                    pActivityBro6.setPpid(pActPrice2.getPpid());
                    pActivityBro6.setTypePpid(pActPrice.getCjdlId());
                    pActivityBro6.setCompanyId(cac.getCompanyID());
                    pActivityBro6.setActPriceId(actPriceId);
                    pActivityBro6.setState("00");
                    this.baseBeanService.save(pActivityBro6);
                    //活动价佣金添加
                    PActivityBrokerage pActivityBro7 = new PActivityBrokerage();
                    String activitybroId7 = serverService.getServerID("activitybroId");
                    pActivityBro7.setActivitybroId(activitybroId7);
                    pActivityBro7.setBrokerage(pActPrice2.getKhjf());
                    pActivityBro7.setPpid(pActPrice2.getPpid());
                    pActivityBro7.setTypePpid(pActPrice.getKhjfId());
                    pActivityBro7.setCompanyId(cac.getCompanyID());
                    pActivityBro7.setActPriceId(actPriceId);
                    pActivityBro7.setState("00");
                    this.baseBeanService.save(pActivityBro7);
                    //更改产品表佣金状态 activityStatus为01
                    String sql = "update dt_productpackaging set activityStatus ='01' where ppid = ?";
                    this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{pActPrice2.getPpid()});
                }
                map.put("code", "200");
            } else {
                logger.error("登录异常");
                map.put("code", "400");//登录异常
            }
        } else {
            logger.error("参数异常");
            map.put("code", "401");//参数异常
        }
        return map;
    }

    /**
     * 活动添加
     * 2019-1-8
     *
     * @param pActivity
     */
    @Override
    public void addActivity(PActivity pActivity) {
        this.baseBeanService.save(pActivity);
    }

    /**
     * 活动修改
     * 2019-1-9
     *
     * @param pActivity
     */
    @Override
    public void updateActivity(PActivity pActivity) {
        this.baseBeanService.update(pActivity);

    }

    /**
     * 活动删除[更改状态为04:已删除]
     * 2019-1-10
     *
     * @return
     */
    @Override
    public void activityDeleteById(String activityId) {
        String sql = "update dt_pro_activity set state ='04' where activityId = ? ";
        try {
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{activityId});
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
    }

    /**
     * 活动开启[更改状态为01:已开启]
     * 2019-1-30
     *
     * @return
     */
    @Override
    public void activityOpenById(String activityId) {
        String sql = "update dt_pro_activity set state ='01' where activityId = ? ";
        try {
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{activityId});
        } catch (Exception e) {
            logger.error("操作异常", e);
        }

    }

    /**
     * 活动暂停[更改状态为02:暂停]
     * 2019-1-11
     *
     * @return
     */
    @Override
    public void activitySuspendById(String activityId) {
        String sql = "update dt_pro_activity set state ='02' where activityId = ? ";
        try {
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{activityId});
        } catch (Exception e) {
            logger.error("操作异常", e);
        }

    }

    /**
     * 获取当前活动所有产品的活动价及相关佣金
     * 2019-1-12
     *
     * @return
     */
    @Override
    public Map<String, Object> getActivityPrice(String activityId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select pa.actPriceId,pa.actprice,pa.factory,pa.brokerage,pa.brokerages,pa.investtype," +
                "pa.ppid,pa.activityid,pp.barcode,pp.goodsname from dt_pro_activity_price pa," +
                "dt_ProductPackaging pp where pa.ppid=pp.ppid and pa.state ='00' and pa.activityid = ? ";
        List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{activityId});
        map.put("actPriceList", beanList);
        return map;
    }

    /**
     * 获取当前活动产品的相关代理佣金
     * 2019-1-12
     *
     * @return
     */
    @Override
    public Map<String, Object> getActivityBrokerage(String actPriceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "select activitybroid,brokerage,typeppid from dt_pro_activity_brokerage where state = '00'and actPriceId = ? ";
        List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{actPriceId});
        map.put("actBrokerageList", beanList);
        return map;
    }

    /**
     * 修改活动佣金
     * 2019-1-14
     *
     * @param pActPriceList
     * @param pActPrice
     * @return
     */
    @Override
    public Map<String, Object> updateActivityBrokerage(List<PActPrice> pActPriceList, PActPrice pActPrice) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (pActPriceList != null && pActPriceList.size() > 0 && pActPrice != null) {//非空校验
            HttpServletRequest request = ServletActionContext.getRequest();
            //获取后台用户登录信息
            HttpSession session = request.getSession();
            CAccount cac = (CAccount) session.getAttribute("account");
            //非空校验
            if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
                int size = pActPriceList.size();
                for (int i = 0; i < size; i++) {
                    //活动价修改
                    PActPrice pActPrice2 = pActPriceList.get(i);
                    String hql = "from PActPrice where actPriceId = ?";
                    PActPrice pActPrice1 = (PActPrice) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{pActPrice2.getActPriceId()});
                    pActPrice1.setActPrice(pActPrice2.getActPrice());
                    pActPrice1.setFactory(pActPrice2.getFactory());
                    pActPrice1.setBrokerage(pActPrice2.getBrokerage());
                    pActPrice1.setBrokerages(pActPrice2.getBrokerages());
                    pActPrice1.setInvestType(pActPrice2.getInvestType());
                    pActPrice1.setPpid(pActPrice2.getPpid());
                    pActPrice1.setCompanyId(cac.getCompanyID());
                    pActPrice1.setActivityId(pActPrice.getActivityId());
                    this.baseBeanService.update(pActPrice1);

                    String hql1 = "from PActivityBrokerage where activitybroId = ?";
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro1 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getSbtzId()});//获取活动价佣金修改回显数据
                    pActivityBro1.setBrokerage(pActPrice2.getSbtz());
                    pActivityBro1.setPpid(pActPrice2.getPpid());
                    pActivityBro1.setTypePpid(pActPrice.getSbtzId());
                    pActivityBro1.setCompanyId(cac.getCompanyID());
                    pActivityBro1.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro1);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro2 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getTpId()});
                    pActivityBro2.setBrokerage(pActPrice2.getTp());
                    pActivityBro2.setPpid(pActPrice2.getPpid());
                    pActivityBro2.setTypePpid(pActPrice.getTpId());
                    pActivityBro2.setCompanyId(cac.getCompanyID());
                    pActivityBro2.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro2);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro3 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getSbazId()});
                    pActivityBro3.setBrokerage(pActPrice2.getSbaz());
                    pActivityBro3.setPpid(pActPrice2.getPpid());
                    pActivityBro3.setTypePpid(pActPrice.getSbazId());
                    pActivityBro3.setCompanyId(cac.getCompanyID());
                    pActivityBro3.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro3);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro4 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getSjdlId()});
                    pActivityBro4.setBrokerage(pActPrice2.getSjdl());
                    pActivityBro4.setPpid(pActPrice2.getPpid());
                    pActivityBro4.setTypePpid(pActPrice.getSjdlId());
                    pActivityBro4.setCompanyId(cac.getCompanyID());
                    pActivityBro4.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro4);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro5 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getXjdlId()});
                    pActivityBro5.setBrokerage(pActPrice2.getXjdl());
                    pActivityBro5.setPpid(pActPrice2.getPpid());
                    pActivityBro5.setTypePpid(pActPrice.getXjdlId());
                    pActivityBro5.setCompanyId(cac.getCompanyID());
                    pActivityBro5.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro5);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro6 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getCjdlId()});
                    pActivityBro6.setBrokerage(pActPrice2.getCjdl());
                    pActivityBro6.setPpid(pActPrice2.getPpid());
                    pActivityBro6.setTypePpid(pActPrice.getCjdlId());
                    pActivityBro6.setCompanyId(cac.getCompanyID());
                    pActivityBro6.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro6);
                    //活动价佣金修改
                    PActivityBrokerage pActivityBro7 = (PActivityBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{pActPrice2.getKhjfId()});
                    pActivityBro7.setBrokerage(pActPrice2.getKhjf());
                    pActivityBro7.setPpid(pActPrice2.getPpid());
                    pActivityBro7.setTypePpid(pActPrice.getKhjfId());
                    pActivityBro7.setCompanyId(cac.getCompanyID());
                    pActivityBro7.setActPriceId(pActPrice1.getActPriceId());
                    this.baseBeanService.update(pActivityBro7);
                }
                map.put("code", "200");
            } else {
                logger.error("登录异常,重新登录");
                map.put("code", "400");//登录异常
            }
        } else {
            logger.error("参数异常");
            map.put("code", "401");//参数异常
        }
        return map;
    }

    /**
     * 删除当前活动产品及佣金[更改状态为:01]
     * 更改产品表相应产品活动佣金状态为 00
     * 2019-05-24
     *
     * @param actPrice
     * @return
     */
    @Override
    public Map<String, Object> delActivityBrokerageById(PActPrice actPrice) {

        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "update dt_pro_activity_price set state ='01' where actPriceId = ? ";
        String sql1 = "update dt_pro_activity_brokerage set state ='01' where actPriceId = ? ";
        String psql = "update dt_ProductPackaging set activitystatus ='00' where ppid = ? ";
        try {
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{actPrice.getActPriceId()});
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{actPrice.getActPriceId()});
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{psql}, new Object[]{actPrice.getPpid()});
            map.put("code", "200");
        } catch (Exception e) {
            logger.error("操作异常", e);
            map.put("code", "201");
        }
        return map;

    }


}
