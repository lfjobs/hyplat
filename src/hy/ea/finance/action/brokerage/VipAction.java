package hy.ea.finance.action.brokerage;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.*;
import hy.ea.finance.service.brokerage.VipService;
import hy.ea.finance.service.brokerage.WholesaleService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-12-11.
 */
@Controller
@Scope("prototype")
public class VipAction {
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
    @Resource
    VipService vipService;
    @Resource
    WholesaleService wholesaleService;
    @Resource
    private ShowExcelService excelService;

    private PageForm pageForm;
    //ajax响应结果
    private String result;
    private String search;//模糊查询参数
    private String flag;//查看数据标识 flag=01[数据设置为只读]
    private int pageNumber;//每页显示最大数[pageSize]
    private Map<String, String> mapPro;//代理佣金
    private Map<String, String> mapId;//代理类别id
    private PVip vip;
    private ProductPackaging productPackaging;
    private InputStream excelStream;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(VipAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    CAccount cac = (CAccount) session.getAttribute("account");

    //////商品VIP价模块//////

    /**
     * VIP产品[佣金...]查询
     * 2018-12-12
     *
     * @return
     */
    public String selectVipList() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示30条]
            int pageSize = (pageNumber == 0 ? 30 : pageNumber);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageNumber", pageNumber1);
            map.put("pageSize", pageSize);
            map.put("search", search);
            map.put("companyId", cac.getCompanyID());
            pageForm = this.vipService.selectVipList(map);
            return "vip_list";
        }
    }

    /**
     * 获取没有设置VIP价佣金的产品
     * 2018-12-13
     *
     * @return
     */
    public String getProductByStatus() {
        Map<String, Object> map2 = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            int pageSize = (pageNumber == 0 ? 10 : pageNumber);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("pageNumber", pageNumber1);
            map1.put("pageSize", pageSize);
            map1.put("search", search);
            map1.put("companyId", cac.getCompanyID());
            pageForm = this.vipService.getProductByStatus(map1);
            map2.put("pageForm", pageForm);
            map2.put("search", search);
            map2.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map2.put("code", "400");
        }

        JSONObject jsonObject = JSONObject.fromObject(map2);
        this.result = jsonObject.toString();
        return "success";
    }


    /**
     * 去添加VIP商品价格佣金页面
     * 2018-12-13
     *
     * @return
     */
    public String toVipAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计VIP商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "vip_add";
        }
    }

    /**
     * 添加VIP商品价格佣金
     * 2018-12-13
     *
     * @return
     */
    public String addVipBrokerage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //VIP价添加
            String vipId = serverService.getServerID("vipId");
            vip.setVipId(vipId);
            vip.setCompanyId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            vip.setAddTimes(dateTime);
            vip.setPrincipal(cac.getStaffName());
            vip.setState("00");//默认给:00
            this.baseBeanService.save(vip);
            //将mapId中的对象添加到mapPro中
            mapPro.putAll(mapId);
            //VIP价佣金添加
            PVipBrokerage pVipBrokerage = new PVipBrokerage();
            pVipBrokerage.setPpid(vip.getPpid());
            pVipBrokerage.setCompanyId(cac.getCompanyID());
            pVipBrokerage.setVipId(vipId);
            String vipbroId = serverService.getServerID("vipbroId");
            pVipBrokerage.setVipbroId(vipbroId);
            pVipBrokerage.setBrokerage(Double.valueOf(mapPro.get("sbtz")));
            pVipBrokerage.setTypePpid(mapPro.get("sbtzId"));
            this.baseBeanService.save(pVipBrokerage);

            PVipBrokerage pVipBrokerage1 = new PVipBrokerage();
            pVipBrokerage1.setPpid(vip.getPpid());
            pVipBrokerage1.setCompanyId(cac.getCompanyID());
            pVipBrokerage1.setVipId(vipId);
            String vipbroId1 = serverService.getServerID("vipbroId");
            pVipBrokerage1.setVipbroId(vipbroId1);
            pVipBrokerage1.setBrokerage(Double.valueOf(mapPro.get("tp")));
            pVipBrokerage1.setTypePpid(mapPro.get("tpId"));
            this.baseBeanService.save(pVipBrokerage1);

            PVipBrokerage pVipBrokerage2 = new PVipBrokerage();
            pVipBrokerage2.setPpid(vip.getPpid());
            pVipBrokerage2.setCompanyId(cac.getCompanyID());
            pVipBrokerage2.setVipId(vipId);
            String vipbroId2 = serverService.getServerID("vipbroId");
            pVipBrokerage2.setVipbroId(vipbroId2);
            pVipBrokerage2.setBrokerage(Double.valueOf(mapPro.get("sbaz")));
            pVipBrokerage2.setTypePpid(mapPro.get("sbazId"));
            this.baseBeanService.save(pVipBrokerage2);

            PVipBrokerage pVipBrokerage3 = new PVipBrokerage();
            pVipBrokerage3.setPpid(vip.getPpid());
            pVipBrokerage3.setCompanyId(cac.getCompanyID());
            pVipBrokerage3.setVipId(vipId);
            String vipbroId3 = serverService.getServerID("vipbroId");
            pVipBrokerage3.setVipbroId(vipbroId3);
            pVipBrokerage3.setBrokerage(Double.valueOf(mapPro.get("sjdl")));
            pVipBrokerage3.setTypePpid(mapPro.get("sjdlId"));
            this.baseBeanService.save(pVipBrokerage3);

            PVipBrokerage pVipBrokerage4 = new PVipBrokerage();
            pVipBrokerage4.setPpid(vip.getPpid());
            pVipBrokerage4.setCompanyId(cac.getCompanyID());
            pVipBrokerage4.setVipId(vipId);
            String vipbroId4 = serverService.getServerID("vipbroId");
            pVipBrokerage4.setVipbroId(vipbroId4);
            pVipBrokerage4.setBrokerage(Double.valueOf(mapPro.get("xjdl")));
            pVipBrokerage4.setTypePpid(mapPro.get("xjdlId"));
            this.baseBeanService.save(pVipBrokerage4);

            PVipBrokerage pVipBrokerage5 = new PVipBrokerage();
            pVipBrokerage5.setPpid(vip.getPpid());
            pVipBrokerage5.setCompanyId(cac.getCompanyID());
            pVipBrokerage5.setVipId(vipId);
            String vipbroId5 = serverService.getServerID("vipbroId");
            pVipBrokerage5.setVipbroId(vipbroId5);
            pVipBrokerage5.setBrokerage(Double.valueOf(mapPro.get("cjdl")));
            pVipBrokerage5.setTypePpid(mapPro.get("cjdlId"));
            this.baseBeanService.save(pVipBrokerage5);

            PVipBrokerage pVipBrokerage6 = new PVipBrokerage();
            pVipBrokerage6.setPpid(vip.getPpid());
            pVipBrokerage6.setCompanyId(cac.getCompanyID());
            pVipBrokerage6.setVipId(vipId);
            String vipbroId6 = serverService.getServerID("vipbroId");
            pVipBrokerage6.setVipbroId(vipbroId6);
            pVipBrokerage6.setBrokerage(Double.valueOf(mapPro.get("khjf")));
            pVipBrokerage6.setTypePpid(mapPro.get("khjfId"));
            this.baseBeanService.save(pVipBrokerage6);
            //更改产品表佣金状态 vipStatus为01
            this.vipService.updateVipStatus(vip.getPpid());
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去修改VIP商品价格佣金页面
     * 2018-12-14
     *
     * @return
     */
    public String toVipUpdate() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计VIP商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            //VIP价
            Object[] vip = this.vipService.pVipById(productPackaging.getPpID());
            //VIP价佣金
            List<BaseBean> brokerageList = this.vipService.brokerageListById((String) vip[0]);
            request.setAttribute("agencyTypeList", agencyList);
            request.setAttribute("pVips", vip);
            request.setAttribute("brokerageList", brokerageList);
            request.setAttribute("product", productPackaging);
            logger.info(flag + "查看数据标识");
            request.setAttribute("flag", flag);
            return "vip_update";
        }
    }

    /**
     * 修改VIP商品价格佣金
     * 2018-12-14
     *
     * @return
     */
    public String updateVipBrokerage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            //获取VIP价修改回显数据
            PVip pVips = this.vipService.getPVipById(vip.getVipId());

            PVipHistory pVipHistory = new PVipHistory();
            pVipHistory.setVipId(vip.getVipId());
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
            this.baseBeanService.save(pVipHistory);//保存VIP价到历史记录表
            //生成(获取)VIP价id
            String vipId = this.serverService.getServerID("vipId");
            pVips.setVipId(vipId);
            pVips.setVip(vip.getVip());
            pVips.setFactory(vip.getFactory());
            pVips.setBrokerage(vip.getBrokerage());
            pVips.setBrokerages(vip.getBrokerages());
            pVips.setInvestType(vip.getInvestType());
            pVips.setPrincipal(cac.getStaffName());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            pVips.setUpdateTimes(dateTime);
            this.baseBeanService.update(pVips); //VIP价修改
            //集合合并
            mapPro.putAll(mapId);

            //VIP价佣金修改
            String sbtzId = mapPro.get("sbtzId");
            PVipBrokerage pVipBrokerage1 = this.vipService.getPVipBrokerageById(sbtzId);
            PVipBroHistory pVipBroHistory1 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId1 = this.serverService.getServerID("vipbroId");
            pVipBroHistory1.setVipbroId(vipbroId1);
            pVipBroHistory1.setBrokerage(pVipBrokerage1.getBrokerage());
            pVipBroHistory1.setPpid(pVipBrokerage1.getPpid());
            pVipBroHistory1.setTypePpid(pVipBrokerage1.getTypePpid());
            pVipBroHistory1.setCompanyId(pVipBrokerage1.getCompanyId());
            pVipBroHistory1.setVipId(pVipBrokerage1.getVipId());
            this.baseBeanService.save(pVipBroHistory1);//保存VIP价佣金到历史记录表
            pVipBrokerage1.setBrokerage(Double.valueOf(mapPro.get("sbtz")));
            pVipBrokerage1.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage1);//修改VIP价佣金

            String tpId = mapPro.get("tpId");
            PVipBrokerage pVipBrokerage2 = this.vipService.getPVipBrokerageById(tpId);
            PVipBroHistory pVipBroHistory2 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId2 = this.serverService.getServerID("vipbroId");
            pVipBroHistory2.setVipbroId(vipbroId2);
            pVipBroHistory2.setBrokerage(pVipBrokerage2.getBrokerage());
            pVipBroHistory2.setPpid(pVipBrokerage2.getPpid());
            pVipBroHistory2.setTypePpid(pVipBrokerage2.getTypePpid());
            pVipBroHistory2.setCompanyId(pVipBrokerage2.getCompanyId());
            pVipBroHistory2.setVipId(pVipBrokerage2.getVipId());
            this.baseBeanService.save(pVipBroHistory2);//保存VIP价佣金到历史记录表
            pVipBrokerage2.setBrokerage(Double.valueOf(mapPro.get("tp")));
            pVipBrokerage2.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage2);//修改VIP价佣金

            String sbazId = mapPro.get("sbazId");
            PVipBrokerage pVipBrokerage3 = this.vipService.getPVipBrokerageById(sbazId);
            PVipBroHistory pVipBroHistory3 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId3 = this.serverService.getServerID("vipbroId");
            pVipBroHistory3.setVipbroId(vipbroId3);
            pVipBroHistory3.setBrokerage(pVipBrokerage3.getBrokerage());
            pVipBroHistory3.setPpid(pVipBrokerage3.getPpid());
            pVipBroHistory3.setTypePpid(pVipBrokerage3.getTypePpid());
            pVipBroHistory3.setCompanyId(pVipBrokerage3.getCompanyId());
            pVipBroHistory3.setVipId(pVipBrokerage3.getVipId());
            this.baseBeanService.save(pVipBroHistory3);//保存VIP价佣金到历史记录表
            pVipBrokerage3.setBrokerage(Double.valueOf(mapPro.get("sbaz")));
            pVipBrokerage3.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage3);//修改VIP价佣金

            String sjdlId = mapPro.get("sjdlId");
            PVipBrokerage pVipBrokerage4 = this.vipService.getPVipBrokerageById(sjdlId);
            PVipBroHistory pVipBroHistory4 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId4 = this.serverService.getServerID("vipbroId");
            pVipBroHistory4.setVipbroId(vipbroId4);
            pVipBroHistory4.setBrokerage(pVipBrokerage4.getBrokerage());
            pVipBroHistory4.setPpid(pVipBrokerage4.getPpid());
            pVipBroHistory4.setTypePpid(pVipBrokerage4.getTypePpid());
            pVipBroHistory4.setCompanyId(pVipBrokerage4.getCompanyId());
            pVipBroHistory4.setVipId(pVipBrokerage4.getVipId());
            this.baseBeanService.save(pVipBroHistory4);//保存VIP价佣金到历史记录表
            pVipBrokerage4.setBrokerage(Double.valueOf(mapPro.get("sjdl")));
            pVipBrokerage4.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage4);//修改VIP价佣金

            String xjdlId = mapPro.get("xjdlId");
            PVipBrokerage pVipBrokerage5 = this.vipService.getPVipBrokerageById(xjdlId);
            PVipBroHistory pVipBroHistory5 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId5 = this.serverService.getServerID("vipbroId");
            pVipBroHistory5.setVipbroId(vipbroId5);
            pVipBroHistory5.setBrokerage(pVipBrokerage5.getBrokerage());
            pVipBroHistory5.setPpid(pVipBrokerage5.getPpid());
            pVipBroHistory5.setTypePpid(pVipBrokerage5.getTypePpid());
            pVipBroHistory5.setCompanyId(pVipBrokerage5.getCompanyId());
            pVipBroHistory5.setVipId(pVipBrokerage5.getVipId());
            this.baseBeanService.save(pVipBroHistory5);//保存VIP价佣金到历史记录表
            pVipBrokerage5.setBrokerage(Double.valueOf(mapPro.get("xjdl")));
            pVipBrokerage5.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage5);//修改VIP价佣金

            String cjdlId = mapPro.get("cjdlId");
            PVipBrokerage pVipBrokerage6 = this.vipService.getPVipBrokerageById(cjdlId);
            PVipBroHistory pVipBroHistory6 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId6 = this.serverService.getServerID("vipbroId");
            pVipBroHistory6.setVipbroId(vipbroId6);
            pVipBroHistory6.setBrokerage(pVipBrokerage6.getBrokerage());
            pVipBroHistory6.setPpid(pVipBrokerage6.getPpid());
            pVipBroHistory6.setTypePpid(pVipBrokerage6.getTypePpid());
            pVipBroHistory6.setCompanyId(pVipBrokerage6.getCompanyId());
            pVipBroHistory6.setVipId(pVipBrokerage6.getVipId());
            this.baseBeanService.save(pVipBroHistory6);//保存VIP价佣金到历史记录表
            pVipBrokerage6.setBrokerage(Double.valueOf(mapPro.get("cjdl")));
            pVipBrokerage6.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage6);//修改VIP价佣金

            String khjfId = mapPro.get("khjfId");
            PVipBrokerage pVipBrokerage7 = this.vipService.getPVipBrokerageById(khjfId);
            PVipBroHistory pVipBroHistory7 = new PVipBroHistory();
            //生成(获取)VIP价佣金id
            String vipbroId7 = this.serverService.getServerID("vipbroId");
            pVipBroHistory7.setVipbroId(vipbroId7);
            pVipBroHistory7.setBrokerage(pVipBrokerage7.getBrokerage());
            pVipBroHistory7.setPpid(pVipBrokerage7.getPpid());
            pVipBroHistory7.setTypePpid(pVipBrokerage7.getTypePpid());
            pVipBroHistory7.setCompanyId(pVipBrokerage7.getCompanyId());
            pVipBroHistory7.setVipId(pVipBrokerage7.getVipId());
            this.baseBeanService.save(pVipBroHistory7);//保存VIP价佣金到历史记录表
            pVipBrokerage7.setBrokerage(Double.valueOf(mapPro.get("khjf")));
            pVipBrokerage7.setVipId(vipId);
            this.baseBeanService.update(pVipBrokerage7);//修改VIP价佣金

            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 产品VIP价Excel导出
     * 2018-12-14
     *
     * @return
     */
    public String VipShowExcel() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            StringBuilder sql = new StringBuilder();
            sql.append("select cp.companyname,pp.productcode,pp.barcode,pp.goodsname, gm.variableID,");
            sql.append(" pw.factory,pw.vip,nvl(pw.vip*(1+su.total_pct*0.01),pw.vip) as price");
            sql.append(" ,pp.showweixin ,pp.vipstatus from dt_productpackaging pp");
            sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
            sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
            sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
            sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
            sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
            sql.append(" left join dt_pro_vip pw on pp.ppid=pw.ppid and pw.state ='00' ");
            sql.append("  where pp.vipstatus='01' and pw.state ='00' and cp.companyid = ? ");
            List<Object> parms = new ArrayList<Object>();
            parms.add(cac.getCompanyID());
            List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
            excelStream = excelService.showExcel(new String[]{"序号", "公司名称", "产品编号", "条码",
                    "产品名称", "产品单位", "成本价", "系统VIP价", "展示VIP价", "发布状态", "佣金状态"}, list);
            return "showexcel";

        } else {
            //未登录
            return "failed";
        }

    }
    /**
     * 删除当前VIP产品及佣金[更改状态为:01]
     * 更改产品表相应产品vip佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    public String vipDeleteById() {
        Map<String, Object> map = this.vipService.vipDeleteById(vip);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }


    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, String> getMapPro() {
        return mapPro;
    }

    public void setMapPro(Map<String, String> mapPro) {
        this.mapPro = mapPro;
    }

    public Map<String, String> getMapId() {
        return mapId;
    }

    public void setMapId(Map<String, String> mapId) {
        this.mapId = mapId;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public PVip getVip() {
        return vip;
    }

    public void setVip(PVip vip) {
        this.vip = vip;
    }
}
