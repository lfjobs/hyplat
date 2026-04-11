package hy.ea.finance.service.brokerage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.*;
import hy.ea.finance.service.brokerage.RetailService;
import hy.ea.finance.service.brokerage.VipPriceMobileService;
import hy.ea.finance.service.brokerage.VipService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class VipPriceMobileServiceImpl implements VipPriceMobileService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private VipService vipService;

    @Resource
    RetailService retailService;

    /**
     * vip产品[佣金...]查询
     *
     * @param map
     * @return 2018-11-26
     */
    @Override
    public PageForm selectVipPriceList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT(PP.PPID),PP.BARCODE,PP.GOODSNAME,PW.FACTORY AS CB,");
        sql.append(" PW.BROKERAGE AS YW,PW.VIP AS XTJ,PW.BROKERAGES AS DL,");
        sql.append(" NVL(PW.VIP*SU.TOTAL_PCT*0.01,0) AS HB,NVL(PW.VIP*(1+SU.TOTAL_PCT*0.01),PW.VIP) AS PRICE,");
        sql.append(" PP.SHOWWEIXIN ,PP.VIPSTATUS,PW.VIPID,PW.ADDTIMES,PW.STATE FROM DT_PRO_VIP PW");
        sql.append(" LEFT JOIN DT_PRODUCTPACKAGING PP ON PP.PPID=PW.PPID");
        sql.append(" LEFT JOIN DTCOMPANY CP ON CP.COMPANYID=PP.COMPANYID");
        sql.append(" LEFT JOIN DTGOODSMANAGE GM ON GM.GOODSID=PP.GOODSID");
        sql.append(" LEFT JOIN DT_CCOM_COM CCO ON CCO.COMPNAY_ID = CP.COMPANYID");
        sql.append(" LEFT JOIN DTCONTACTCOMPANY CC ON CC.CCOMPANYID = CCO.CCOMPANY_ID");
        sql.append(" LEFT JOIN DT_SET_SUBSIDIZE SU ON SU.GTID = CC.INDUSTRYTYPE AND SU.STUTAS = '01'");
        sql.append(" WHERE PP.VIPSTATUS='01' AND CP.COMPANYID = ? ");
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        if (search != null && !search.equals("")) {
            sql.append(" AND (PP.GOODSNAME LIKE ?");
            sql.append(" OR PP.BARCODE LIKE ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        sql.append(" ORDER BY PW.ADDTIMES DESC");
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }

    public Map<String, Object> getRetailPriceList(String psid) {
        Map<String, Object> result = new HashMap<>();

        // SQL语句作为常量定义
        final String SQL_MAIN_QUERY = "SELECT DW.VIPID,DW.VIP,DW.FACTORY,DW.BROKERAGE,DW.BROKERAGES, " +
                "DW.INVESTTYPE, P.GOODSNAME, P.BARCODE, S.STAFFNAME, C.COMPANYNAME,DW.VIPKEY,DW.STATE,DW.PPID " +
                "FROM DT_PRO_VIP DW " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON DW.PPID = P.PPID " +
                "LEFT JOIN DT_HR_STAFF S ON DW.PRINCIPAL = S.STAFFID " +
                "LEFT JOIN DTCOMPANY C ON DW.COMPANYID = C.COMPANYID " +
                "WHERE DW.VIPID = ?";

        final String SQL_SUB_QUERY = "SELECT VIPBROID,BROKERAGE,TYPEPPID, P.GOODSNAME " +
                "FROM DT_PRO_VIP_BROKERAGE PSS " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON PSS.TYPEPPID = P.PPID " +
                "WHERE PSS.VIPID = ? " +
                "ORDER BY PSS.VIPBROID ASC";
        try {
            Object[] mainResult = (Object[]) this.baseBeanService.getObjectBySqlAndParams(SQL_MAIN_QUERY, new Object[]{psid});
            List<BaseBean> subResult = this.baseBeanService.getListBeanBySqlAndParams(SQL_SUB_QUERY, new Object[]{psid});

            result.put("pRetail", mainResult);
            result.put("beanList", subResult);
        } catch (Exception e) {
            // 处理异常
            logger.error("操作异常", e);
            // 可以添加日志记录或其他错误处理机制
        }
        return result;
    }

    /**
     * 查询已设置佣金的产品
     * 2019-05-27
     *
     * @return
     */
    public String getPRetailByPpid(List<BaseBean> saveBean, PVip pVips) throws Exception {

        //修改佣金并将原数据存入历史表
        PVipHistory pVipHistory = new PVipHistory();
        pVipHistory.setVipId(pVips.getVipId());
        pVipHistory.setVip(pVips.getVip());
        pVipHistory.setFactory(pVips.getFactory());
        pVipHistory.setBrokerage(pVips.getBrokerage());
        pVipHistory.setBrokerages(pVips.getBrokerages());
        pVipHistory.setInvestType(pVips.getInvestType());
        pVipHistory.setAddTimes(pVips.getAddTimes());
        pVipHistory.setUpdateTimes(pVips.getUpdateTimes());
        pVipHistory.setPrincipal(pVips.getPrincipal());
        pVipHistory.setPpid(pVips.getPpid());
        pVipHistory.setCompanyId(pVips.getCompanyId());
        pVipHistory.setState(pVips.getState());
        saveBean.add(pVipHistory);
        //零售价代理商佣金
        List<BaseBean> brokerageList = vipService.brokerageListById(pVips.getVipId());
        if (brokerageList != null && brokerageList.size() > 0) {
            Object objects = null;
            for (int i = 0; i < brokerageList.size(); i++) {
                objects = brokerageList.get(i);
                Object[] objs = (Object[]) objects;
                if (objs != null) {
                    //批发价佣金修改
                    PVipBroHistory pVipBroHistory = new PVipBroHistory();
                    pVipBroHistory.setVipbroId(objs[0].toString());
                    pVipBroHistory.setBrokerage(Double.valueOf(objs[1].toString()));
                    pVipBroHistory.setPpid(pVipHistory.getPpid());
                    pVipBroHistory.setTypePpid(objs[2].toString());
                    pVipBroHistory.setCompanyId(pVipHistory.getCompanyId());
                    pVipBroHistory.setVipId(pVipHistory.getVipId());
                    saveBean.add(pVipBroHistory);
                }
            }
            return "201";
        } else {
            return "401";//数据异常
        }
    }

    @Override
    @Transactional
    public Map<String, String> savePrice(String state, CAccount cac, Map mapPro, Map mapdl) {
        Map<String, String> result = new HashMap<>();
        String flag = "200";
        List<BaseBean> saveBean = new ArrayList<BaseBean>();
        try {
            PVip pVips = this.vipService.getPVipById(mapPro.get("priceid").toString());
            //批发价添加
            String vipId = serverService.getServerID("vipId");
            logger.info("vipId:: {}", vipId);
            result.put("priceid", vipId);
            PVip vip = new PVip();
            vip.setVipId(vipId);
            vip.setCompanyId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            vip.setAddTimes(dateTime);
            vip.setPrincipal(cac.getStaffName());
            vip.setFactory(Double.valueOf(mapPro.get("cb").toString()));
            vip.setVip(Double.valueOf(mapPro.get("dj").toString()));
            vip.setBrokerage(Double.valueOf(mapPro.get("yw").toString()));
            vip.setInvestType(mapPro.get("tzt").toString());
            vip.setPpid(mapPro.get("ppid").toString());
            vip.setState(state);//默认给:00
            List<BaseBean> list = retailService.agencyTypeList();
            if (list != null && list.size() > 0) {
                Double f = 0d;
                for (int i = 0; i < list.size(); i++) {
                    ProductPackaging p = (ProductPackaging) list.get(i);
                    //批发价佣金添加
                    PVipBrokerage pVipBrokerage = new PVipBrokerage();
                    pVipBrokerage.setPpid(vip.getPpid());
                    pVipBrokerage.setCompanyId(cac.getCompanyID());
                    pVipBrokerage.setVipId(vipId);
                    String vipbroId = serverService.getServerID("vipbroId");
                    pVipBrokerage.setVipbroId(vipbroId);
                    pVipBrokerage.setBrokerage(Double.valueOf(mapdl.get(p.getPpID()).toString()));
                    f = pVipBrokerage.getBrokerage() + f;
                    pVipBrokerage.setTypePpid(p.getPpID());
                    saveBean.add(pVipBrokerage);
                }
                vip.setBrokerages(f);
            }
            List<String> sqlList = new ArrayList<>();
            List<Object[]> parm = new ArrayList<>();
            if (pVips != null) {
                vip.setUpdateTimes(new Date());
                logger.info("调试信息");
                flag = getPRetailByPpid(saveBean, pVips);
                if (!flag.equals("201")) {
                    throw new Exception("数据异常");
                }
                vip.setUpdateTimes(new Date());
                vip.setAddTimes(pVips.getAddTimes());
                String setup = "delete dt_pro_vip where vipId=?";
                String sub = "delete dt_pro_vip_brokerage where vipId=?";
                sqlList.add(setup);
                sqlList.add(sub);
                parm.add(new Object[]{pVips.getVipId()});
                parm.add(new Object[]{pVips.getVipId()});
            }
            saveBean.add(vip);
            sqlList.add("update dt_ProductPackaging set vipStatus ='01' where ppid = ? ");
            parm.add(new Object[]{mapPro.get("ppid")});
            String[] array = new String[sqlList.size()];
            for (int i = 0; i < sqlList.size(); i++) {
                array[i] = sqlList.get(i);
            }
            baseBeanService.executeSqlsByParmsList(saveBean, array, parm);
        } catch (Exception e) {
            logger.error("操作异常", e);
            flag = "500";
        }
        result.put("flag", flag);
        return result;
    }

    @Override
    public void updateState(String priceid) {
        String sql = "UPDATE DT_PRO_VIP SET STATE ='03' WHERE VIPID = ? ";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{priceid});
    }
}
