package hy.ea.finance.service.brokerage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.ProSetupSubBackup;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProSetupBackup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.finance.service.brokerage.RetailPriceMobileService;
import hy.ea.finance.service.brokerage.RetailService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RetailPriceMobileServiceImpl implements RetailPriceMobileService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    ServerService serverService;
    @Resource
    RetailService retailService;

    /**
     * 零售产品[佣金...]查询
     *
     * @param map
     * @return 2019-1-19
     */
    @Override
    public PageForm selectRetailPriceList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数--商品名称或条码
        String companyId = (String) map.get("companyId");//公司id
        String showweixin = (String) map.get("showweixin");// 查询参数-物品状态 ‘’：全部  ‘01’：已发布 否则 未发布
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct(pp.ppid),pp.barcode,pp.goodsname,pw.ef_price as cb,");
        sql.append(" pw.BROKERAGE as yw,pw.re_price as xyj,pw.proxy_sum_price as dl,");
        sql.append(" nvl(pw.re_price*su.total_pct*0.01,0) as hb,nvl(pw.re_price*(1+su.total_pct*0.01),pw.re_price) as price,");
        sql.append(" pp.showweixin,pp.yjstatus,pw.suid,pw.sjdate,pw.state from DT_PRO_SETUP pw");
        sql.append(" left join dt_productpackaging pp on pp.ppid=pw.ppid");
        sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
        sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
        sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
        sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
        sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
        sql.append(" where pp.yjstatus='01'");
        List<Object> parms = new ArrayList<Object>();
        if (search != null && !search.equals("")) {
            sql.append(" and (pp.goodsname like ?");
            sql.append(" or pp.barcode like ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        sql.append(" and cp.companyid =?");
        parms.add(companyId);
        sql.append(" order by pw.sjdate desc");
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }

    @Override
    public Map<String, Object> getRetailPriceList(String psid) {
        Map<String, Object> result = new HashMap<>();

        // SQL语句作为常量定义
        final String SQL_MAIN_QUERY = "SELECT DW.SUID, DW.RE_PRICE, DW.EF_PRICE, DW.BROKERAGE, DW.PROXY_SUM_PRICE, " +
                "DW.TZ_TYPE, P.GOODSNAME, P.BARCODE, S.STAFFNAME, C.COMPANYNAME,DW.SUKEY, DW.STATE, DW.PPID " +
                "FROM DT_PRO_SETUP DW " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON DW.PPID = P.PPID " +
                "LEFT JOIN DT_HR_STAFF S ON DW.PRINCIPAL = S.STAFFID " +
                "LEFT JOIN DTCOMPANY C ON DW.COM_ID = C.COMPANYID " +
                "WHERE DW.SUID = ?";

        final String SQL_SUB_QUERY = "SELECT SUSID, AMOUNT, TYPE_PPID, P.GOODSNAME " +
                "FROM DT_PRO_SETUP_SUB PSS " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON PSS.TYPE_PPID = P.PPID " +
                "WHERE SUID = ? " +
                "ORDER BY SUSID ASC";

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
     * 查询已设置零售价佣金的产品
     * 2019-05-27
     *
     * @return
     */
    public String getPRetailByPpid(List<BaseBean> saveBean, ProSetup proSetups) throws Exception {

        //修改佣金并将原数据存入历史表
        ProSetupBackup proSetupBackup = new ProSetupBackup();
        proSetupBackup.setSubId(proSetups.getSuid());
        proSetupBackup.setSubKey(proSetups.getSukey());
        proSetupBackup.setRePrice(proSetups.getRePrice());
        proSetupBackup.setEfPrice(proSetups.getEfPrice());
        proSetupBackup.setBrokerage(proSetups.getBrokerage());
        proSetupBackup.setProxySumPrice(proSetups.getProxyprice());
        proSetupBackup.setTzType(proSetups.getTzType());
        proSetupBackup.setSjDate(proSetups.getSjdate());
        proSetupBackup.setEditDate(proSetups.getEditDate());
        proSetupBackup.setPrincipal(proSetups.getPrincipal());
        proSetupBackup.setPpid(proSetups.getPpid());
        proSetupBackup.setComId(proSetups.getComId());
        proSetupBackup.setState(proSetups.getState());
        saveBean.add(proSetupBackup);
        //零售价代理商佣金
        List<BaseBean> brokerageList = retailService.brokerageListById(proSetupBackup.getSubId());
        if (brokerageList != null && brokerageList.size() > 0) {
            Object objects = null;
            for (int i = 0; i < brokerageList.size(); i++) {
                objects = brokerageList.get(i);
                Object[] objs = (Object[]) objects;
                if (objs != null) {
                    ProSetupSubBackup proSetupSubBackup = new ProSetupSubBackup();
                    //生成(获取)零售价佣金id
                    ProSetupSub proSetupSub = retailService.getPRetBrokerageById(objs[0].toString());
                    proSetupSubBackup.setSusbId(objs[0].toString());
                    proSetupSubBackup.setAmount(objs[1].toString());
                    proSetupSubBackup.setPpid(objs[3].toString());
                    proSetupSubBackup.setTypePpid(objs[2].toString());
                    proSetupSubBackup.setSubId(proSetupBackup.getSubId());
                    proSetupSub.setAgentState(proSetupSub.getAgentState());
                    proSetupSub.setInvestComId(proSetupSub.getInvestComId());
                    proSetupSub.setAgentType(proSetupSub.getAgentType());
                    proSetupSub.setTextUrl(proSetupSub.getTextUrl());
                    proSetupSub.setState(proSetupSub.getState());
                    saveBean.add(proSetupSubBackup);
                }
            }
            return "201";
        } else {
            return "401";//数据异常
        }
    }

    /**
     * 保存
     * @param cac
     * @param mapPro
     * @param mapdl
     * @return
     */
    @Override
    @Transactional
    public Map<String, String> savePrice(String state, CAccount cac, Map mapPro, Map mapdl) {
        List<BaseBean> saveBean = new ArrayList<BaseBean>();
        Map<String, String> result = new HashMap<>();
        String flag = "201";
        try {
            String hql = "from ProSetup where ppid = ?";
            ProSetup proSetups = (ProSetup) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{mapPro.get("ppid").toString()});
            String retailId = serverService.getServerID("retailId");
            result.put("priceid", retailId);
            ProSetup proSetup = new ProSetup();
            proSetup.setSuid(retailId);
            proSetup.setComId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            proSetup.setSjdate(dateTime);
            proSetup.setPrincipal(cac.getStaffName());
            proSetup.setEfPrice(mapPro.get("cb").toString());
            proSetup.setRePrice(mapPro.get("dj").toString());
            proSetup.setBrokerage(mapPro.get("yw").toString());
            proSetup.setTzType(mapPro.get("tzt").toString());
            proSetup.setPpid(mapPro.get("ppid").toString());
            proSetup.setPpname(mapPro.get("pname").toString());
            proSetup.setState(state);//默认给:00

            List<BaseBean> list = retailService.agencyTypeList();
            if (list != null && list.size() > 0) {
                Float f = 0f;
                for (int i = 0; i < list.size(); i++) {
                    ProductPackaging p = (ProductPackaging) list.get(i);
                    ProSetupSub proSetupSub = new ProSetupSub();
                    proSetupSub.setPpid(proSetup.getPpid());
                    proSetupSub.setSuid(retailId);
                    String retailbroId = serverService.getServerID("retailbroId");
                    proSetupSub.setSusid(retailbroId);
                    proSetupSub.setAmount(mapdl.get(p.getPpID()).toString());
                    f = Float.parseFloat(proSetupSub.getAmount()) + f;
                    proSetupSub.setTypePpid(p.getPpID());
                    if (proSetups != null) {
                        ProSetupSub proSetupSubs = (ProSetupSub) baseBeanService.getBeanByHqlAndParams("from ProSetupSub where SUID = ? and typePpid=?", new Object[]{proSetups.getSuid(), p.getPpID()});
                        proSetupSub.setAgentState(proSetupSubs.getAgentState());
                        proSetupSub.setInvestComId(proSetupSubs.getInvestComId());
                        proSetupSub.setAgentType(proSetupSubs.getAgentType());
                        proSetupSub.setTextUrl(proSetupSubs.getTextUrl());
                        proSetupSub.setState(proSetupSubs.getState());
                    }
                    saveBean.add(proSetupSub);
                }
                proSetup.setProxyprice(f.toString());
            }
            List<String> sqlList = new ArrayList<>();
            List<Object[]> parm = new ArrayList<>();
            if (proSetups != null) {
                proSetup.setEditDate(new Date());
                flag = getPRetailByPpid(saveBean, proSetups);
                if (!flag.equals("201")) {
                    throw new Exception("数据异常");
                }
                proSetup.setSjdate(proSetups.getSjdate());
                proSetup.setEditDate(new Date());
                String setup = "DELETE DT_PRO_SETUP WHERE SUID=?";
                String sub = "DELETE DT_PRO_SETUP_SUB WHERE SUID=?";
                sqlList.add(setup);
                sqlList.add(sub);
                parm.add(new Object[]{proSetups.getSuid()});
                parm.add(new Object[]{proSetups.getSuid()});
            }
            saveBean.add(proSetup);

            sqlList.add("UPDATE DT_PRODUCTPACKAGING SET YJSTATUS ='01' WHERE PPID = ?");
            parm.add(new Object[]{mapPro.get("ppid")});
            String[] array = new String[sqlList.size()];
            for (int i = 0; i < sqlList.size(); i++) {
                array[i] = sqlList.get(i);
            }
            sqlList.add("UPDATE DT_INV_INVT I SET purPrice=?,sumPrice=invenQuantity*? WHERE I.PRODUCTID = ?");
            parm.add(new Object[]{mapPro.get("dj").toString(),mapPro.get("dj").toString(),mapPro.get("ppid")});
            baseBeanService.executeSqlsByParmsList(saveBean, array, parm);
        } catch (Exception e) {
            flag = "500";
            logger.error("操作异常", e);
        }
        result.put("flag", flag);
        return result;
    }

    @Override
    public void updateState(String priceid) {
        String sql = "update DT_PRO_SETUP set state ='03' where suid = ? ";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{priceid});
    }

}
