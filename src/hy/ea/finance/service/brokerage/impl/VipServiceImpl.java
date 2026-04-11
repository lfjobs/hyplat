package hy.ea.finance.service.brokerage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.brokerage.PVip;
import hy.ea.bo.finance.brokerage.PVipBrokerage;
import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.ea.bo.finance.percentage.BPercentage;
import hy.ea.bo.finance.percentage.PPercentage;
import hy.ea.finance.service.brokerage.VipService;
import hy.ea.finance.service.brokerage.WholesaleService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
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
public class VipServiceImpl implements VipService {
    @Resource
    private BaseBeanService baseBeanService;

    /**
     * vip产品[佣金...]查询
     *
     * @param map
     * @return 2018-11-26
     */
    @Override
    public PageForm selectVipList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct(pp.ppid),cp.companyname,pp.productcode,pp.barcode,pp.goodsname, gm.variableID,");
        sql.append(" pw.factory,pw.vip,nvl(pw.vip*(1+su.total_pct*0.01),pw.vip) as price");
        sql.append(" ,pp.showweixin ,pp.vipstatus ,pw.vipid from dt_productpackaging pp");
        sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
        sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
        sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
        sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
        sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
        sql.append(" left join dt_pro_vip pw on pp.ppid=pw.ppid");
        sql.append(" where pp.vipstatus='01' and pw.state ='00' and cp.companyid = ? ");
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
     * 获取没有设置VIP价佣金并且已设置零售价佣金的产品
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
        sql.append("select dp.ppid,dp.barcode,dp.goodsname from dt_productpackaging dp  ");
        sql.append("where dp.vipStatus ='00' and dp.yjstatus = '01' and dp.companyid = ?");
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
     * 修改产品表VIP价状态
     *
     * @param ppid
     */
    @Override
    public void updateVipStatus(String ppid) {
        String sql = "update dt_productpackaging dd set dd.vipstatus ='01'where dd.ppid = ?";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{ppid});
    }

    /**
     * 获取产品VIP价
     *
     * @param ppid
     * @return
     */
    @Override
    public Object[] pVipById(String ppid) {
        String sql = "select vipid,vip,factory,brokerage,brokerages,investtype from dt_pro_vip where state ='00' and  ppid = ?";
        Object[] pVip = (Object[]) this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{ppid});
        return pVip;
    }

    /**
     * 获取产品VIP价佣金
     *
     * @param vipId
     * @return
     */
    @Override
    public List<BaseBean> brokerageListById(String vipId) {
        String sql = "select vipbroId,brokerage,typePpid from dt_pro_vip_brokerage where vipId =? order by vipbroId asc";
        List<BaseBean> beanList = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{vipId});
        return beanList;
    }


    /**
     * 获取VIP价修改回显数据
     *
     * @param vipId
     * @return
     */
    @Override
    public PVip getPVipById(String vipId) {
        String hql = "from PVip where vipId = ?";
        return (PVip) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{vipId});
    }

    /**
     * /获取VIP价佣金修改回显数据
     *
     * @param sbtzId
     * @return
     */
    @Override
    public PVipBrokerage getPVipBrokerageById(String sbtzId) {
        String hql = "from PVipBrokerage where vipbroId = ?";
        return (PVipBrokerage) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sbtzId});
    }

    /**
     * 删除当前VIP产品及佣金[更改状态为:01]
     * 更改产品表相应产品vip佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    @Override
    public Map<String, Object> vipDeleteById(PVip vip) {

        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "update dt_pro_vip set state ='01' where vipId = ? ";
        String sql1 = "update dt_ProductPackaging set vipstatus ='00' where ppid = ? ";
        try {
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{vip.getVipId()});
            this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{vip.getPpid()});
            map.put("code", "200");
        } catch (Exception e) {
            logger.error("操作异常", e);
            map.put("code", "500");
        }
        return map;


    }
}
