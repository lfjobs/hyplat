package hy.ea.marketing.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.TCuscomProduct;
import hy.ea.bo.finance.BenDis.TEshopCuscomSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.marketing.bo.InvestApply;
import hy.ea.marketing.bo.ProProxy;
import hy.ea.marketing.service.ProductAgentService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.RandomDatas;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ProductAgentServiceImpl implements ProductAgentService {
    @Resource
    private BaseBeanService bbservice;
    @Resource
    private ServerService serverService;
    @Resource
    private UploadContentToFileService contentToFileService;

    private Logger logger = LoggerFactory.getLogger(ProductAgentServiceImpl.class);

    /**
     * 导航
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Object> getNavigation() {

        //查询导航
        StringBuffer sql = new StringBuffer();
        sql.delete(0, sql.length());
        sql.append("select ppid,goodsname from dt_ProductPackaging where type = ?");
        sql.append(" and goodsname <> ? and goodsname <> ? and goodsname <> ? and goodsname <> ? order by sorting");

        List<Object> plist = bbservice.getListBeanBySqlAndParams(
                sql.toString(),
                new Object[]{"佣金分配代理类别", "佣金分配代理类别", "全国代理", "客户积分", "设备投资"});

        return plist;
    }

    /**
     * 查询招商产品
     *
     * @param pageNumber 当前页数
     * @param pageSize   显示记录数
     * @param flag       判断标识tpye_ppid
     * @param search     搜索关键词
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PageForm getAgentProductsPageForm(Integer pageNumber, Integer pageSize, String flag, String search) {
        PageForm pageForm = new PageForm();

        StringBuffer sql = new StringBuffer();
        sql.append("select p.ppid,pp.pid,p.companyid,pp.agenttype,p.image,p.goodsname");
        sql.append(" from dt_productpackaging p,dt_pro_proxy pp");
        sql.append(" where p.ppid = pp.ppid and showweixin = ? and pp.agentstate = ?");
        sql.append(" and pp.type_ppid <> ? and pp.type_ppid <> ? and pp.type_ppid <> ? ");
        List<Object> params = new ArrayList<Object>();
        params.add("01");
        params.add("00");
        params.add("p20170220ZVZR76B88M0000000014");
        params.add("p20170220ZVZR76B88M0000000022");
        params.add("p20170605KY3VAANZJG0000000003");

        if (search != null && search.length() > 0) {
            sql.append(" and p.goodsname like ?");
            params.add("%" + search + "%");
        }
        if (flag != null && flag.length() > 0) {
            sql.append(" and pp.type_ppid = ?");
            params.add(flag);
        }

        //已发布的招商产品
        pageForm = bbservice.getPageFormBySQL(
                pageNumber != null ? pageNumber : 1,
                pageSize,
                sql.toString(),
                "select count(*) from (" + sql.toString() + ")",
                params.toArray());

        return pageForm;
    }

    /**
     * 查询招商产品根据公司id
     *
     * @param pageNumber 当前页数
     * @param pageSize   显示记录数
     * @param companyId  公司id
     * @param flag       00招商中，01招商结束
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getAgentProPageFormByCom(Integer pageNumber, Integer pageSize, String companyId, String flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        //产品pageForm
        PageForm pageForm = new PageForm();
        StringBuffer hql = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        hql.append("from ProductPackaging p where p.showweixin = ? and p.companyID = ? and ");
        if (flag==null||flag.length()<=0){
            hql.append(" not ");
        }
        hql.append(" exists ( select 1 from ProProxy pp");
        hql.append(" where pp.ppid = p.ppID");
        List<Object> obj = new ArrayList<Object>();
        obj.add("01");
        obj.add(companyId);

        if (flag!= null && flag.length() > 0)
        {
            hql.append(" and pp.agentstate = ?");
            obj.add(flag);
        }
        hql.append(" )");
        pageForm = bbservice.getPageForm(
                pageNumber != null ? pageNumber : 1,
                pageSize,
                hql.toString(),
                obj.toArray());
        map.put("pageForm",pageForm);


        //代理信息
        sql.append("from ProProxy where ppid in (");

        List<Object> params = new ArrayList<Object>();
        List<Object> params_pss = new ArrayList<Object>();
        if (pageForm != null) {
            for (int i = 0; i < pageForm.getList().size();i++) {
                ProductPackaging pp = (ProductPackaging) pageForm.getList().get(i);
                sql.append("?,");
                params.add(pp.getPpID());
            }

            String temp_sql = sql.toString().substring(0,sql.toString().length() - 1) + ")";

            List<BaseBean> psslist = bbservice.getListBeanByHqlAndParams(temp_sql,params.toArray());
            List<BaseBean> sslist = null;
            //改变prosetup属性
            for (int i = 0; i < pageForm.getList().size(); i++) {
                ProductPackaging pp = (ProductPackaging) pageForm.getList().get(i);
                sslist=new ArrayList<BaseBean>();
                for (int y = 0; y < psslist.size(); y++) {
                    ProProxy pProxy = (ProProxy) psslist.get(y);
                    if (pp.getPpID().equals(pProxy.getPpid())) {
                        sslist.add(pProxy);
                    }
                }
                pp.setPmlist(sslist);
            }
        }

        //公司信息
        hql.delete(0, hql.length());
        hql.append("select cc.companyname,ccom.compnay_id,cc.industrytype,cc.logopath");
        hql.append(" from dtcontactcompany cc,dt_ccom_com ccom where ccom.ccompany_id = cc.ccompanyid and ccom.compnay_id = ?");
        List<Object> comList = bbservice.getListBeanBySqlAndParams(hql.toString(), new Object[]{companyId});
        map.put("comlist", comList);
        return map;
    }

    /**
     * 招商详情
     *
     * @param ppId 产品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getAgentDetail(String ppId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select p.goodsname,p.ppid,p.image,p.companyid");
        sql.append(" from dt_productpackaging p where p.ppid = ? ");

        List<Object> plist = bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppId});
        map.put("plist", plist);

        //查询代理信息
        sql.delete(0, sql.length());
        List<BaseBean> psslist = new ArrayList<BaseBean>();
        if (plist.size() > 0) {
            Object[] obj = (Object[]) plist.get(0);
            sql.append("from ProProxy where ppid = ? and agenttype != ? and agenttype != ? and agenttype != ? order by agenttype");
            psslist = bbservice.getListBeanByHqlAndParams(sql.toString(), new Object[]{obj[1].toString(), "p20170220ZVZR76B88M0000000014", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003"});
            map.put("psslist", psslist);

            //查询申请记录信息
            if (psslist.size() > 0) {
                sql.delete(0, sql.length());
                sql.append("select ia.susid,ia.inapId,sf.staffname,sf.headimage,ia.agree,ia.areaName,ia.areappid from dt_InvestApply ia,t_eshop_cuscom cus,dt_hr_staff sf ");
                sql.append(" where ia.sccId = cus.sccId and cus.staffid = sf.staffid and susid in (");
                List<Object> params = new ArrayList<Object>();
                for (int i = 0; i < psslist.size(); i++) {
                    ProProxy pProxy = (ProProxy) psslist.get(i);
                    sql.append("?,");
                    params.add(pProxy.getPid());
                }
                String temp_sql = sql.toString().substring(0, sql.toString().length() - 1) + ")";
                List<Object> aplist = bbservice.getListBeanBySqlAndParams(temp_sql, params.toArray());
                map.put("aplist", aplist);
            }
        }
        return map;
    }

    /**
     * 发布招商
     *
     * @param ppIds
     * @param html  招商要求文本
     * @return
     */
    @Override
    public String saveProAgent(String ppIds, String html) {
        String flag = "1";
        try {
            StringBuffer sql = new StringBuffer();
            List<BaseBean> beans = new ArrayList<BaseBean>();

            sql.append("from ProductPackaging where type=? and goodsname<>? order by sorting");

            List<BaseBean> prolist = bbservice.getListBeanByHqlAndParams(sql.toString(), new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
            String url = "";
            logger.info("值：{}", prolist);
            //招商要求
            if (html != null && html.length() > 0) {
                url = saveContentToFile(html.trim());
            }
            logger.info("值：{}", ppIds);
            String [] ppid=ppIds.split(",");
            if(ppid.length>0){
                for (int j = 0; j < ppid.length; j++){
                    sql = new StringBuffer();
                    sql.append("from ProProxy where ppid=?");
                    List<BaseBean> Proxylist = bbservice.getListBeanByHqlAndParams(sql.toString(), new Object[]{ppid[j]});
                    if(Proxylist!=null&&!Proxylist.isEmpty()){
                        for (int i = 0; i < Proxylist.size(); i++) {
                            ProProxy proxy=(ProProxy)Proxylist.get(i);
                            proxy.setSjdate(new Date());
                            proxy.setTexturl(url);
                            beans.add(proxy);
                        }
                    }else{
                        for (int i = 0; i < prolist.size(); i++) {
                            ProProxy proxy=new ProProxy();
                            proxy.setPid(serverService.getServerID("proxy"));

                            ProductPackaging p = (ProductPackaging) prolist.get(i);
                            proxy.setAgentstate("00");
                            proxy.setState("00");
                            proxy.setPpid(ppid[j]);
                            proxy.setTypePpid(p.getPpID());
                            //贴牌
                            if ("p20170220ZVZR76B88M0000000016".equals(p.getPpID())) {
                                proxy.setAgenttype("00");
                            }

                            //设备安装
                            if ("p20170220ZVZR76B88M0000000017".equals(p.getPpID())) {
                                proxy.setAgenttype("01");
                            }

                            //省级代理
                            if ("p20170220ZVZR76B88M0000000018".equals(p.getPpID())) {
                                proxy.setAgenttype("02");
                            }

                            //县级代理
                            if ("p20170220ZVZR76B88M0000000019".equals(p.getPpID())) {
                                proxy.setAgenttype("03");
                            }

                            //村级代理
                            if ("p20170220ZVZR76B88M0000000020".equals(p.getPpID())) {
                                proxy.setAgenttype("04");
                            }
                            proxy.setSjdate(new Date());
                            proxy.setTexturl(url);
                            beans.add(proxy);
                        }
                    }

                }
            }
            logger.info("值：{}", beans);
            bbservice.executeHqlsByParamsList(beans, null, null);
        } catch (Exception e) {
            flag = "0";
        }
        return flag;
    }

    /**
     * 产品信息
     *
     * @param ppId 产品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<BaseBean> proInfo(String ppId) {
        StringBuffer sql = new StringBuffer();
        StringBuffer sql_pss = new StringBuffer();
        List<Object> obj = new ArrayList<Object>();

        sql.append("from ProductPackaging p where p.ppID in (");
        if (ppId.length() > 0) {
            String[] ppid = ppId.split(",");
            for (int i = 0; i < ppid.length; i++) {
                sql.append("?,");
                obj.add(ppid[i]);
            }
        }
        String temp_sql = sql.toString().substring(0,sql.toString().length() - 1) + ")";


        List<BaseBean> proInfo = bbservice.getListBeanByHqlAndParams(temp_sql, obj.toArray());



        if (proInfo != null&&proInfo.size()>0) {
            sql.delete(0, sql.length());
            sql.append("from ProductPackaging where type=? and goodsname<>? order by sorting");
            List<BaseBean> psslist = bbservice.getListBeanByHqlAndParams(sql.toString(),new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
            for (int i = 0; i < proInfo.size(); i++) {
                ProductPackaging pp = (ProductPackaging) proInfo.get(i);
                pp.setPmlist(psslist);
            }
        }
        return proInfo;
    }

    /**
     * 招商产品详情
     *
     * @param ppId 产品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> proAgentDetail(String ppId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();

        sql.append("select p.ppid,pp.pid,p.goodsname,p.companyid,p.image,");
        sql.append("pp.agenttype,pp.texturl,pp.type_ppid,pp.state,to_char(pp.sjdate,'YYYY-MM-DD'),p.goodsid");
        sql.append(" from dt_productpackaging p,dt_pro_proxy pp");
        sql.append(" where p.ppid = pp.ppid and p.ppid = ?");
        sql.append(" and pp.type_ppid <> ? and pp.type_ppid <> ? and pp.type_ppid <> ?");
        sql.append(" order by pp.agenttype");
        List<Object> list = bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppId, "p20170220ZVZR76B88M0000000014", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003"});
        map.put("list", list);

        //剩余招商数
        sql.delete(0, sql.length());
        sql.append("select count(*) from dt_pro_proxy pp where pp.ppid = ? and pp.state = ? and pp.type_ppid <> ? and pp.type_ppid <> ? and pp.type_ppid <> ?");
        Integer count = bbservice.getConutByBySqlAndParams(sql.toString(), new Object[]{ppId, "00", "p20170220ZVZR76B88M0000000014", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003"});
        map.put("count", count);

        //公司信息及产品描述
        sql.delete(0, sql.length());
        sql.append("select cc.logopath,cc.companyname,cc.industrytype,gf.url,ccom.compnay_id,cc.companyTel");
        sql.append(" from dtcontactcompany cc ,dt_ccom_com ccom,dt_productpackaging p left join dtgoodfunction gf on p.goodsid = gf.goodsid");
        sql.append(" where ccom.compnay_id = p.companyid and ccom.ccompany_id = cc.ccompanyid and p.ppid = ?");
        List<Object> comList = bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppId});
        map.put("comlist", comList);
        if (comList.size() > 0) {
            if (comList.get(0) != null) {
                Object[] obj = (Object[]) comList.get(0);
                if (obj[3] != null && obj[3].toString().length() > 0) {
                    String s = getContentFromFile(obj[3].toString());
                    String[] func = s.split("#z");
                    map.put("func", func);
                }

            }
        }
        return map;
    }

    /**
     * 查看招商要求文本
     *
     * @param pid 佣金id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> demandsDetail(String pid) {
        Map<String, Object> map = new HashMap<String, Object>();
        ProProxy proxy = (ProProxy) bbservice.getBeanByHqlAndParams(
                "from ProProxy where pid = ?", new Object[]{pid});
        if (proxy != null && proxy.getTexturl() != null) {
            String s = getContentFromFile(proxy.getTexturl());
            String[] func = s.split("#z");
            map.put("func", func);
        }
        return map;
    }

    /**
     * 结束招商
     *
     * @param ppid 产品id
     * @param flag 00招商，01招商结束
     */
    @Override
    public void endInvest(String ppid, String flag) {
        String hql = "update ProProxy set agentstate = ? where ppid = ?";
        List<Object[]> params = new ArrayList<Object[]>();
        Object[] obj = new Object[]{flag, ppid};
        params.add(obj);
        bbservice.executeHqlsByParamsList(null,
                new String[]{hql},
                params);
    }

    /**
     * 同意申请招商
     *
     * @param inapId 申请记录id
     */
    @Override
    public synchronized void agreeToInvest(String inapId) {
        //代理商和商品绑定表
        InvestApply apply = (InvestApply) bbservice.getBeanByHqlAndParams(
                "from InvestApply where inapId = ?", new Object[]{inapId});
        if (apply.getAgree() == null || apply.getAgree().equals("")) {
            String hql = "update InvestApply set agree = ? where inapId = ?";
            List<Object[]> params = new ArrayList<Object[]>();
            List<BaseBean> beans = new ArrayList<BaseBean>();
            List<String> hqls = new ArrayList<String>();

            hqls.add(hql);
            Object[] obj = new Object[]{"01", inapId};
            params.add(obj);

            ProProxy pProxy = (ProProxy) bbservice.getBeanByHqlAndParams(
                    "select pp from ProProxy pp,InvestApply ia where pp.pid = ia.susid and inapId = ?",
                    new Object[]{inapId});

            //if(!setupSub.getAgentType().equalsIgnoreCase("02")&&!setupSub.getAgentType().equalsIgnoreCase("03")&&!setupSub.getAgentType().equalsIgnoreCase("04")){
            //改变代理商佣金已被抢购状态
            pProxy.setState("01");
            beans.add(pProxy);
            //}


            //若此招商产品代理全部被抢完，招商自动结束
            Integer count = bbservice.getConutByByHqlAndParams("select count(*) from ProProxy where state = ? and ppid = ?",
                    new Object[]{"00", pProxy.getPpid()});
            if (count == 0) {
                hqls.add("update ProProxy set agentstate = ? where ppid = ?");
                params.add(new Object[]{"01", pProxy.getPpid()});
            }

            TEshopCuscomSub cuscomSub = (TEshopCuscomSub) bbservice.getBeanByHqlAndParams(
                    "from TEshopCuscomSub where sccid = ? and tyepPpid =? "
                    , new Object[]{apply.getSccId(), pProxy.getTypePpid()});

            TCuscomProduct product = new TCuscomProduct();
            product.setCpid(serverService.getServerID("cpid"));
            product.setPpid(pProxy.getPpid());
            product.setSccsid(cuscomSub.getSccsid());
            product.setSjdate(new Date());
            beans.add(product);

            bbservice.executeHqlsByParamsList(beans,
                    hqls.toArray(new String[]{}),
                    params);
        }
    }

    /**
     * 查找区域是否审核通过
     *
     * @param ppId     产品id
     * @param areappid 区域产品id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public int getSnapCount(String ppId, String areappid) {
        //同一产品同一区域代理只能抢购一次
        StringBuilder sb = new StringBuilder("select count(*) from t_cuscom_product tcp,t_eshop_cuscom_sub tecs ");
        sb.append("where tcp.sccsid=tecs.sccsid and tcp.ppid=? and tecs.areappid = ?");
        int count = bbservice.getConutByBySqlAndParams(sb.toString(), new Object[]{ppId, areappid});
        return count;
    }

    /**
     * 立即抢购
     *
     * @param pid 代理关系id
     * @return
     */
    @Override
    public String snapUp(String pid) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        String areappid = null;
        String areaName = null;
        TEshopCusCom cusCom = (TEshopCusCom) sessionWrap.getObject(session, sessionWrap.KEY_SHOPCUSCOM);
        List<BaseBean> beans = new ArrayList<BaseBean>();

        List<BaseBean> list = bbservice.getListBeanByHqlAndParams(
                "from TEshopCuscomSub where sccid = ?",
                new Object[]{cusCom != null ? cusCom.getSccId() : ""}
        );
        ProProxy proxy = (ProProxy) bbservice.getBeanByHqlAndParams(
                "from ProProxy where pid = ?"
                , new Object[]{pid}
        );
        Integer times = 0;
        //若产品是贴牌00或设备安装01时
        if (("00".equalsIgnoreCase(proxy.getAgenttype())) || ("01".equalsIgnoreCase(proxy.getAgenttype()))) {
            //同一产品同一代理只能抢购一次
            InvestApply apply = (InvestApply) this.bbservice.getBeanByHqlAndParams(
                    "from InvestApply where susid = ? and sccId = ?",
                    new Object[]{pid, cusCom.getSccId()});
            if (apply != null) {
                return "re";
            }
            int custype = Integer.parseInt(cusCom.getCusType());
            if (custype < 6) {
                times++;
                //符合以下判断条件就给T_Eshop_cuscum_sub添加一条数据
                List<BaseBean> list1 = bbservice.getListBeanByHqlAndParams(
                        "from TEshopCuscomSub where sccid = ? and tyepPpid = ?",
                        new Object[]{cusCom != null ? cusCom.getSccId() : "", proxy.getTypePpid()}
                );
                if (list1 == null || list1.size() == 0) {
                    TEshopCuscomSub cuscomSub = new TEshopCuscomSub();
                    cuscomSub.setAccount(cusCom.getAccount());
                    cuscomSub.setSccid(cusCom.getSccId());
                    cuscomSub.setSccsid(serverService.getServerID("tecss"));
                    cuscomSub.setSjdate(new Date());
                    cuscomSub.setTyepPpid(proxy.getTypePpid());
                    beans.add(cuscomSub);
                }
            } else {
                return "gere";
            }
        } else {
            //判断当前用户是否有抢购资格
            if (list == null || list.size() == 0) {
                return "no";
            }

            //同一产品同一代理只能抢购一次
            InvestApply apply = (InvestApply) bbservice.getBeanByHqlAndParams(
                    "from InvestApply where susid = ? and sccId = ?"
                    , new Object[]{pid, cusCom.getSccId()}
            );
            if (apply != null) {
                return "re";
            }

            //是否同等资格判断
            for (int i = 0; i < list.size(); i++) {
                TEshopCuscomSub cuscomSub = (TEshopCuscomSub) list.get(i);
                if (cuscomSub.getTyepPpid().equals(proxy.getTypePpid())) {
                    times++;
                }
            }

            if (times == 0) {
                return "noeq";
            }

            if (("02".equalsIgnoreCase(proxy.getAgenttype())) || ("03".equalsIgnoreCase(proxy.getAgenttype())) || ("04".equalsIgnoreCase(proxy.getAgenttype()))) {
                StringBuilder ga = new StringBuilder("select p.ppid,p.goodsname from t_eshop_cuscom_sub t,dt_productpackaging p");
                ga.append(" where t.AREAPPID=p.ppid and t.sccid = ? and t.tyepPpid=? ");
                Object[] o = (Object[]) bbservice.getObjectBySqlAndParams(ga.toString(), new Object[]{cusCom.getSccId(), proxy.getTypePpid()});

                areappid = o[0].toString();
                areaName = o[1].toString();
                //同一产品同一区域代理只能抢购一次
                StringBuilder sb = new StringBuilder("select count(*) from t_cuscom_product tcp,t_eshop_cuscom_sub tecs ");
                sb.append("where tcp.sccsid=tecs.sccsid and tcp.ppid=? and tecs.areappid = ?");
                int count = bbservice.getConutByBySqlAndParams(sb.toString(), new Object[]{proxy.getPpid(), areappid});
                if (count > 0) {
                    return "are";
                }
            }
        }

        //申请记录添加
        InvestApply investApply = new InvestApply();
        investApply.setInapId(serverService.getServerID("inap"));
        investApply.setSccId(cusCom.getSccId());
        investApply.setSusid(pid);
        investApply.setInapDate(new Date());
        investApply.setAreappid(areappid);
        investApply.setAreaName(areaName);
        beans.add(investApply);

        //推送给公司account
        /*ProSetup setup = (ProSetup) bbservice.getBeanByHqlAndParams(
                "from ProSetup where suid = ?", new Object[]{setupSub.getSuid()}
        );

        //修改佣金时间
        setup.setEditDate(new Date());
        beans.add(setup);*/

        bbservice.executeHqlsByParamsList(beans, null, null);

        /*List<BaseBean> telist = bbservice.getListBeanByHqlAndParams(
                "select cus from TEshopCusCom cus,ProductPackaging p where p.companyID = cus.companyId and p.ppID = ?"
                , new Object[]{proxy.getPpid()}
        );

        //发送推送号码集合
        List<String> slist = new ArrayList<String>();

        for (int i = 0; i < telist.size(); i++) {
            TEshopCusCom com = (TEshopCusCom) telist.get(0);
            slist.add(com.getAccount());
        }

        logger.error("推送号码：" + slist.toString());

        JushMain.sendjiguangMessage("有用户已抢购" + proxy.getPpname() + "代理商品，请查看！！！"
                , "", "ppId=" + proxy.getPpid() + "&companyId=" + proxy.getInvestcomid(), "AgentPro", slist);*/

        return "yes";
    }

    /**
     * 申请详情
     *
     * @param ppId   产品id
     * @param inapId 申请记录id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> applyDetail(String ppId, String inapId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select pp.pid,ia.inapid,pp.agenttype,sf.headimage,sf.staffname,ia.agree,ia.areaName,ia.areappid");
        sql.append(" from dt_pro_proxy pp,dt_investApply ia,dt_hr_staff sf,T_ESHOP_CUSCOM cus");
        sql.append(" where pp.pid = ia.susid and ia.sccId = cus.sccid and sf.staffid = cus.staffid");
        sql.append(" and ia.inapId = ?");

        List<Object> list = bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{inapId});
        map.put("list", list);

        //产品信息
        List<Object> plist = bbservice.getListBeanBySqlAndParams(
                "select p.image,p.ppid,p.goodsname from dt_ProductPackaging p where p.ppId = ?"
                , new Object[]{ppId});
        map.put("pp", plist);
        return map;
    }

    /**
     * 保存文本编辑器内容
     *
     * @param content
     * @return
     */
    private String saveContentToFile(String content) {
        String id = RandomDatas.getUUID();
        String path = ServletActionContext.getServletContext().getRealPath("")
                + "/upload_files/goodDetail/";
        try {
            contentToFileService.saveContent(id, content, path);
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return "/upload_files/goodDetail/" + id
                + UploadContentToFileService.suffix;
    }

    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\") + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            logger.error("操作异常", e);
            return "";
        }
    }

    @Override
    public void zsdl(String comid, String sccid, String ppid) {
        StringBuffer sql = new StringBuffer();
        List<Object> obj = new ArrayList<Object>();
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String areappid = null;
        String areaName = null;
        String sccsid = null;
        try {
            sql.append("select pss from ProSetup ps,ProSetupSub pss where ps.suid = pss.suid and ps.comId = ? and pss.typePpid=? and pss.agentState is null");

            List<BaseBean> prolist = bbservice.getListBeanByHqlAndParams(sql.toString(), new Object[]{comid, ppid});

            TEshopCusCom cusCom = (TEshopCusCom) bbservice.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});

            if (("p20170220ZVZR76B88M0000000016".equalsIgnoreCase(ppid)) || ("p20170220ZVZR76B88M0000000017".equalsIgnoreCase(ppid))) {

                int custype = Integer.parseInt(cusCom.getCusType());
                if (custype < 6) {

                    TEshopCuscomSub cuscomSub = new TEshopCuscomSub();
                    cuscomSub.setAccount(cusCom.getAccount());
                    cuscomSub.setSccid(cusCom.getSccId());
                    cuscomSub.setSccsid(serverService.getServerID("tecss"));
                    cuscomSub.setSjdate(new Date());
                    cuscomSub.setTyepPpid(ppid);
                    beans.add(cuscomSub);

                    sccsid = cuscomSub.getSccsid();
                }
            } else if (("p20170220ZVZR76B88M0000000018".equalsIgnoreCase(ppid)) || ("p20170220ZVZR76B88M0000000019".equalsIgnoreCase(ppid)) || ("p20170220ZVZR76B88M0000000020".equalsIgnoreCase(ppid))) {
                StringBuilder ga = new StringBuilder("select p.ppid,p.goodsname,t.sccsid from t_eshop_cuscom_sub t,dt_productpackaging p");
                ga.append(" where t.AREAPPID=p.ppid and t.sccid = ? and t.tyepPpid=? ");
                Object[] o = (Object[]) bbservice.getObjectBySqlAndParams(ga.toString(), new Object[]{cusCom.getSccId(), ppid});
                if (o != null) {
                    areappid = o[0].toString();
                    areaName = o[1].toString();
                    sccsid = o[2].toString();
                }
            }
            for (int i = 0; i < prolist.size(); i++) {
                ProSetupSub proSetupSub = (ProSetupSub) prolist.get(i);
                proSetupSub.setAgentState("00");
                proSetupSub.setState("01");
                //贴牌
                if ("p20170220ZVZR76B88M0000000016".equals(proSetupSub.getTypePpid())) {
                    proSetupSub.setAgentType("00");
                }

                //设备安装
                if ("p20170220ZVZR76B88M0000000017".equals(proSetupSub.getTypePpid())) {
                    proSetupSub.setAgentType("01");
                }

                //省级代理
                if ("p20170220ZVZR76B88M0000000018".equals(proSetupSub.getTypePpid())) {
                    proSetupSub.setAgentType("02");
                }

                //县级代理
                if ("p20170220ZVZR76B88M0000000019".equals(proSetupSub.getTypePpid())) {
                    proSetupSub.setAgentType("03");
                }

                //村级代理
                if ("p20170220ZVZR76B88M0000000020".equals(proSetupSub.getTypePpid())) {
                    proSetupSub.setAgentType("04");
                }
                proSetupSub.setSjdate(new Date());
                proSetupSub.setTextUrl(saveContentToFile("客户至上"));
                beans.add(proSetupSub);

                //修改佣金时间
                if (prolist.size() > 0) {
                    ProSetupSub setupSub = (ProSetupSub) prolist.get(0);
                    ProSetup setup = (ProSetup) bbservice.getBeanByHqlAndParams(
                            "from ProSetup where suid = ?",
                            new Object[]{setupSub.getSuid()}
                    );
                    setup.setEditDate(new Date());
                    beans.add(setup);
                }

                InvestApply investApply = new InvestApply();
                investApply.setInapId(serverService.getServerID("inap"));
                investApply.setSccId(cusCom.getSccId());
                investApply.setSusid(proSetupSub.getSusid());
                investApply.setInapDate(new Date());
                investApply.setAreappid(areappid);
                investApply.setAreaName(areaName);
                beans.add(investApply);

                TCuscomProduct product = new TCuscomProduct();
                product.setCpid(serverService.getServerID("cpid"));
                product.setPpid(proSetupSub.getPpid());
                product.setSccsid(sccsid);
                product.setSjdate(new Date());
                beans.add(product);
            }
            bbservice.executeHqlsByParamsList(beans, null, null);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
    }

}
