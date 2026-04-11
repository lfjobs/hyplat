package hy.ea.finance.service.brokerage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.ProSetupSubBackup;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProSetupBackup;
import hy.ea.finance.service.brokerage.RetailService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2019-1-19.
 */
@Service
@Transactional
public class RetailServiceImpl implements RetailService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    ServerService serverService;
    @Resource
    private ShowExcelService excelService;
    private InputStream excelStream;
    /**
     * 零售产品[佣金...]查询
     *
     * @param map
     * @return 2019-1-19
     */
    @Override
    public PageForm selectRetailList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数--商品名称或条码
        String companyId = (String) map.get("companyId");//公司id
        String showweixin=(String)map.get("showweixin");// 查询参数-物品状态 ‘’：全部  ‘01’：已发布 否则 未发布
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct(pp.ppid),cp.companyname,pp.productcode,pp.barcode,pp.goodsname, gm.variableID,");
        sql.append(" pw.ef_price,pw.re_price,nvl(pw.re_price*(1+su.total_pct*0.01),pw.re_price) as price");
        sql.append(" ,pp.showweixin ,pp.yjstatus ,pw.suid from dt_productpackaging pp");
        sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
        sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
        sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
        sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
        sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
        sql.append(" left join DT_PRO_SETUP pw on pp.ppid=pw.ppid");
        sql.append("  where pp.yjstatus='01' and pw.state ='00'");
        List<Object> parms = new ArrayList<Object>();
        if (search != null && !search.equals("")) {
            sql.append(" and (pp.goodsname like ?");
            sql.append(" or pp.barcode like ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        if (showweixin!=null&&!showweixin.equals("")){
            if (showweixin.equals("01")){
                sql.append(" and pp.showweixin = ?");
            }else {
                sql.append(" and pp.showweixin != ?");
            }
            parms.add("01");
        }
        sql.append(" and cp.companyid =?");
        parms.add(companyId);
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }

    /**
     * 去添加零售商品价格佣金页面
     * 2019-1-19
     *
     * @return
     */
    @Override
    public List<BaseBean> agencyTypeList() {
        //代理类别
        List<BaseBean> agencyTypeList =
                baseBeanService.getListBeanByHqlAndParams("from ProductPackaging where type=? and goodsname<>? order by sorting", new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
        return agencyTypeList;
    }

    /**
     * 获取没有设置零售价佣金的产品
     * 2019-1-19
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
        sql.append("select dp.ppid,dp.barcode,dp.goodsname from dt_productpackaging dp where dp.yjstatus ='00' and dp.companyid = ?");
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
     * 修改产品表零售价状态
     * 2019-1-19
     *
     * @param ppid
     */
    @Override
    public void updateretailStatus(String ppid) {
        String sql = "update dt_productpackaging dd set dd.yjstatus ='01'where dd.ppid = ?";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{ppid});
    }


    /**
     * 获取产品零售价
     * 2019-1-19
     *
     * @param ppid
     * @return
     */
    @Override
    public Object[] PRetailById(String ppid) {
        String sql = "select dw.suid,dw.re_price,dw.ef_price," +
                "dw.brokerage,dw.proxy_sum_price,dw.tz_type from DT_PRO_SETUP dw  where dw.state='00' and dw.ppid = ?";
        Object[] pRetail = (Object[]) this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{ppid});
        return pRetail;
    }

    /**
     * 获取产品零售价佣金
     * 2019-1-19
     *
     * @param retailId
     * @return
     */
    @Override
    public List<BaseBean> brokerageListById(String retailId) {
        String sql = "select SUSID,AMOUNT,TYPE_PPID,PPID,SUID from DT_PRO_SETUP_SUB where SUID =? order by SUSID asc";
        List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{retailId});
        return beanList;
    }

    /**
     * 获取零售价修改回显数据
     * 2019-1-19
     *
     * @param retailId
     * @return
     */
    @Override
    public ProSetup getPRetailById(String retailId) {
        String hql = "from ProSetup where suid = ?";
        return (ProSetup) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{retailId});
    }

    /**
     * /获取零售价佣金修改回显数据
     * 2019-1-22
     *
     * @param sbtzId
     * @return
     */
    @Override
    public ProSetupSub getPRetBrokerageById(String sbtzId) {
        String hql = "from ProSetupSub where susid = ?";
        return (ProSetupSub) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sbtzId});
    }


    /**
     * 删除当前批发产品及佣金[更改状态为:01]
     * 更改产品表相应产品批发佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    @Override
    public Map<String, Object> retailDeleteById(ProSetup proSetup) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sqlps = "select wholesaleid,ppid from dt_pro_wholesale  where ppid =? and state ='00'";
        List listBean = this.baseBeanService.getListBeanBySqlAndParams(sqlps, new Object[]{proSetup.getPpid()});
        if (listBean != null && listBean.size() > 0) {//批发价佣金已设置
            String sql = "update DT_PRO_SETUP set state ='01' where suid = ? ";
            String sql1 = "update dt_ProductPackaging set yjstatus ='00' where ppid = ? ";
            try {
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{proSetup.getSuid()});
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{proSetup.getPpid()});
                map.put("code", "200");
            } catch (Exception e) {
                logger.error("操作异常", e);
                map.put("code", "500");
            }
        } else {
            map.put("code", "201");
        }

        return map;
    }

    /**
     * 查询已设置零售价佣金的产品
     * 2019-05-27
     *
     * @return
     */
    @Override
    public String getPRetailByPpid(ProSetup proSetup, Map<String, String> mapPro) {
        String hql = "from ProSetup where ppid = ?";
        ProSetup proSetups = (ProSetup) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{proSetup.getPpid()});
        if (proSetups == null) {//该产品没有设置零售价佣金>>去添加佣金
            return "200";
        } else {//修改佣金并将原数据存入历史表
            ProSetupBackup proSetupBackup = new ProSetupBackup();
            proSetupBackup.setSubId(proSetups.getSuid());
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

            //生成(获取)零售价id
            String retailId = serverService.getServerID("retailId");
            proSetups.setSuid(retailId);
            proSetups.setRePrice(proSetup.getRePrice());
            proSetups.setEfPrice(proSetup.getEfPrice());
            proSetups.setBrokerage(proSetup.getBrokerage());
            proSetups.setProxyprice(proSetup.getProxyprice());
            proSetups.setTzType(proSetup.getTzType());
            proSetups.setPrincipal(proSetup.getPrincipal());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            proSetups.setEditDate(dateTime);
            proSetups.setState("00");
            //零售价代理商佣金
            List<BaseBean> brokerageList = this.brokerageListById(proSetupBackup.getSubId());
            if (brokerageList != null && brokerageList.size() > 0) {
                this.baseBeanService.save(proSetupBackup);//保存零售价到历史记录表
                this.baseBeanService.update(proSetups); //零售价修改
                Object objects = null;
                for (int i = 0; i < brokerageList.size(); i++) {
                    objects = brokerageList.get(i);
                    Object[] objs = (Object[]) objects;
                    if (objs != null) {
                        ProSetupSub ProSetupSub = this.getPRetBrokerageById(objs[0].toString());
                        ProSetupSubBackup proSetupSubBackup = new ProSetupSubBackup();
                        //生成(获取)零售价佣金id
                        String retbroId1 = this.serverService.getServerID("retbroId");
                        proSetupSubBackup.setSusbId(retbroId1);
                        proSetupSubBackup.setAmount(objs[1].toString());
                        proSetupSubBackup.setPpid(objs[3].toString());
                        proSetupSubBackup.setTypePpid(objs[2].toString());
                        proSetupSubBackup.setSubId(objs[4].toString());
                        this.baseBeanService.save(proSetupSubBackup);//保存零售价佣金到历史记录表
                        if ("p20170605KY3VAANZJG0000000003".equals(objs[2])) {//设备投资
                            ProSetupSub.setAmount(mapPro.get("sbtz"));
                        }
                        if ("p20170220ZVZR76B88M0000000016".equals(objs[2])) {//贴牌
                            ProSetupSub.setAmount(mapPro.get("tp"));
                        }
                        if ("p20170220ZVZR76B88M0000000017".equals(objs[2])) {//设备安装
                            ProSetupSub.setAmount(mapPro.get("sbaz"));
                        }
                        if ("p20170220ZVZR76B88M0000000018".equals(objs[2])) {//省级代理
                            ProSetupSub.setAmount(mapPro.get("sjdl"));
                        }
                        if ("p20170220ZVZR76B88M0000000019".equals(objs[2])) {//县级代理
                            ProSetupSub.setAmount(mapPro.get("xjdl"));
                        }
                        if ("p20170220ZVZR76B88M0000000020".equals(objs[2])) {//村级代理
                            ProSetupSub.setAmount(mapPro.get("cjdl"));
                        }
                        if ("p20170220ZVZR76B88M0000000022".equals(objs[2])) {//客户积分
                            ProSetupSub.setAmount(mapPro.get("khjf"));
                        }
                        ProSetupSub.setSuid(retailId);
                        this.baseBeanService.update(ProSetupSub);//修改零售价佣金
                    }
                }
                //更改产品表佣金状态 yjstatus为01
                this.updateretailStatus(proSetup.getPpid());
                return "201";
            } else {
                return "401";//数据异常
            }
        }
    }

    /**
     * 产品零售价Excel导出
     * 2019-1-19
     *
     * @return
     */
    @Override
    public InputStream RetailShowExcel(String search,String showweixin,String comid) {

            StringBuilder sql = new StringBuilder();
            sql.append("select cp.companyname ,pp.productcode ,pp.barcode ,pp.goodsname ,gm.variableID ,");
            sql.append(" ROUND(nvl(pw.re_price * (1 + (su.total_pct * 0.01)), pw.re_price), 2),");
            sql.append(" ROUND(pw.re_price,2),ROUND(nvl(pw.re_price * (su.total_pct * 0.01), 0),2),pw.ef_price,pw.brokerage,");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" sum(decode(pss.type_ppid,?,pss.amount)),");
            sql.append(" case when pp.showweixin= ? then ? else ? end from dt_productpackaging pp");
            sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
            sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
            sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
            sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
            sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = ?");
            sql.append(" left join DT_PRO_SETUP pw on pp.ppid=pw.ppid");
            sql.append(" left join dt_pro_setup_sub pss on pw.suid = pss.suid");
            sql.append("  where pw.state =?");
            List<Object> parms = new ArrayList<Object>();
            parms.add("p20170220ZVZR76B88M0000000017");
            parms.add("p20170220ZVZR76B88M0000000016");
            parms.add("p20170220ZVZR76B88M0000000020");
            parms.add("p20170220ZVZR76B88M0000000018");
            parms.add("p20170220ZVZR76B88M0000000019");
            parms.add("p20170220ZVZR76B88M0000000022");
            parms.add("p20170605KY3VAANZJG0000000003");
            parms.add("01");
            parms.add("已发布");
            parms.add("未发布");
            parms.add("01");
            parms.add("00");
            if (showweixin != null && !showweixin.equals("")) {
                if (showweixin.equals("01")) {
                    sql.append(" and pp.showweixin = ?");
                } else {
                    sql.append(" and pp.showweixin != ?");
                }
                parms.add("01");
            }
            if (search != null && !search.equals("")) {
                sql.append(" and (pp.goodsname like ?");
                sql.append(" or pp.barcode like ?)");
                parms.add("%" + search.trim() + "%");
                parms.add("%" + search.trim() + "%");
            }
            sql.append(" and cp.companyid = ? ");
            parms.add(comid);

            sql.append("  group by cp.companyname,pp.productcode,pp.barcode,pp.goodsname, ");
            sql.append("  gm.variableID,pw.re_price,su.total_pct,pw.ef_price,pw.brokerage,pp.showweixin,pp.yjstatus");


            List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
            InputStream excelStream = excelService.showExcel(new String[]{"序号", "公司名称", "产品编号", "条码",
                    "产品名称", "产品单位", "展示零售价", "平台零售价", "消费红包", "成本价", "业务佣金",
                    "设备安装", "贴牌", "村级代理", "省级代理", "县级代理", "客户积分", "设备投资", "产品状态"}, list);
            return excelStream;
    }

}
