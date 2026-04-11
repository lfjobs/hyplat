package hy.ea.finance.service.brokerage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProCateRelate;
import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.ea.bo.finance.percentage.BPercentage;
import hy.ea.bo.finance.percentage.PPercentage;
import hy.ea.finance.service.brokerage.WholesaleService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-22.
 */
@Service
@Transactional
public class WholesaleServiceImpl implements WholesaleService {
    @Resource
    private BaseBeanService baseBeanService;

    /**
     * 批发产品[佣金...]查询
     *
     * @param map
     * @return 2018-11-26
     */
    @Override
    public PageForm selectWholesaleList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct(pp.ppid),cp.companyname,pp.productcode,pp.barcode,pp.goodsname, gm.variableID,");
        sql.append(" pw.factory,pw.wholesale,nvl(pw.wholesale*(1+su.total_pct*0.01),pw.wholesale) as price");
        sql.append(" ,pp.showweixin ,pp.wholesalestatus,pw.wholesaleid from dt_productpackaging pp");
        sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
        sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
        sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
        sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
        sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
        sql.append(" left join dt_pro_wholesale pw on pp.ppid=pw.ppid");
        sql.append("  where pp.wholesalestatus='01' and pw.state ='00' and cp.companyid =? ");
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        if (search != null && !search.equals("")) {
            sql.append(" and (pp.goodsname like ?");
            sql.append(" or pp.barcode like ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }

    /**
     * 去添加批发商品价格佣金页面
     * 2018-11-29
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
     * 获取没有设置批发价佣金的产品
     * 2018-11-30
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
        sql.append("select dp.ppid,dp.barcode,dp.goodsname from dt_productpackaging dp where dp.wholesalestatus ='00' and dp.companyid = ?");
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
     * 价格佣金百分比获取
     * 2018-12-5
     *
     * @param ppID
     * @return
     */
    @Override
    public Map<String, Object> getBrokeragePercent(String ppID) {
        HttpServletRequest request = ServletActionContext.getRequest();
        //获取后台用户登录信息
        HttpSession session = request.getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null) {
            //价格百分比获取
            PPercentage pPercentage = new PPercentage();
            String hql1 = "from PPercentage where companyId = ? and ppId = ?";
            pPercentage = (PPercentage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{cac.getCompanyID(), ppID});
            if (pPercentage == null) {
                //String sql1 = "select dpc.pcrid,dpc.ppid,dpc.codeid from dt_ProCateRelate dpc where dpc.ppid = ? and dpc.companyid =?";
                String hql2 = "from ProCateRelate where ppID = ? and companyID =?";
                ProCateRelate proCateRelate = (ProCateRelate) this.baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ppID, cac.getCompanyID()});
                String hql3 = "from PPercentage where companyId = ? and codeId = ?";
                if (proCateRelate != null && proCateRelate.getCodeID() != null && !proCateRelate.getCodeID().equals("")) {//参数校验
                    pPercentage = (PPercentage) this.baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{cac.getCompanyID(), proCateRelate.getCodeID()});
                }
                if (pPercentage == null) {
                    String hql4 = "from PPercentage where companyId = ? and codeId is null and ppId is null ";
                    pPercentage = (PPercentage) this.baseBeanService.getBeanByHqlAndParams(hql4, new Object[]{cac.getCompanyID()});
                }
            }
            //佣金百分比获取
            String bhql1 = "from BPercentage where companyId = ? and brokerageId = ?";
            String bhql2 = "from BPercentage where companyId = ? ";
            BPercentage bPercentage = new BPercentage();
            if (pPercentage != null) {
                bPercentage = (BPercentage) this.baseBeanService.getBeanByHqlAndParams(bhql1, new Object[]{cac.getCompanyID(), pPercentage.getBrokerageId()});
            } else {
                bPercentage = (BPercentage) this.baseBeanService.getBeanByHqlAndParams(bhql2, new Object[]{cac.getCompanyID()});

            }
            //产品展示价格百分比获取
            StringBuilder sql = new StringBuilder();
            sql.append("select cp.companyid,su.total_pct from  dtcompany cp");
            sql.append(" inner join dt_ccom_com cco on cco.compnay_id = cp.companyid");
            sql.append(" inner join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
            sql.append(" inner join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
            sql.append(" where cp.companyid = ?");
            List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{cac.getCompanyID()});
            map.put("pPercentage", pPercentage);
            map.put("bPercentage", bPercentage);
            map.put("beanList", beanList);
            map.put("code", "200");
        } else {
            //未登录
            map.put("code", "400");
        }
        return map;
    }

    /**
     * 修改产品表批发价状态
     *
     * @param ppid
     */
    @Override
    public void updatewholesaleStatus(String ppid) {
        String sql = "update dt_productpackaging dd set dd.wholesalestatus ='01'where dd.ppid = ?";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{ppid});
    }

    /**
     * 获取产品批发价
     *
     * @param ppid
     * @return
     */
    @Override
    public Object[] PWholesaleById(String ppid) {
        String sql = "select dw.wholesaleid,dw.wholesale,dw.factory," +
                "dw.brokerage,dw.brokerages,dw.investtype from dt_pro_wholesale dw  where dw.state ='00' and dw.ppid = ?";
        Object[] pWholesale = (Object[]) this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{ppid});
        return pWholesale;
    }

    /**
     * 获取产品批发价佣金
     *
     * @param wholesaleId
     * @return
     */
    @Override
    public List<BaseBean> brokerageListById(String wholesaleId) {
        String sql = "select whobroId,brokerage,typePpid from dt_pro_who_brokerage where wholesaleId =? order by whobroId asc";
        List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{wholesaleId});
        return beanList;
    }

    /**
     * 获取批发价修改回显数据
     *
     * @param wholesaleId
     * @return
     */
    @Override
    public PWholesale getPWholesaleById(String wholesaleId) {
        String hql = "from PWholesale where wholesaleId = ?";
        return (PWholesale) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{wholesaleId});
    }

    /**
     * /获取批发价佣金修改回显数据
     *
     * @param sbtzId
     * @return
     */
    @Override
    public PWhoBrokerage getPWhoBrokerageById(String sbtzId) {
        String hql = "from PWhoBrokerage where whobroId = ?";
        return (PWhoBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sbtzId});
    }

    /**
     * 删除当前批发产品及佣金[更改状态为:01]
     * 更改产品表相应产品批发佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    @Override
    public Map<String, Object> wholesaleDeleteById(PWholesale wholesale) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sqlps = "select suid,ppid from DT_PRO_SETUP  where ppid =? and state ='00'";
        List listBean = this.baseBeanService.getListBeanBySqlAndParams(sqlps, new Object[]{wholesale.getPpid()});
        if (listBean != null && listBean.size() > 0) {//零售价佣金已设置
            String sql = "update dt_pro_wholesale set state ='01' where wholesaleid = ? ";
            String sql1 = "update dt_ProductPackaging set wholesalestatus ='00' where ppid = ? ";
            try {
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{wholesale.getWholesaleId()});
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{wholesale.getPpid()});
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
}
