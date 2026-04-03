package hy.ea.finance.action.brokerage;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.PWhoBroHistory;
import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWhoHistory;
import hy.ea.bo.finance.brokerage.PWholesale;
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
 * Created by Administrator on 2018-11-7.
 */
@Controller
@Scope("prototype")
public class WholesaleAction {
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
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
    private PWholesale wholesale;
    private ProductPackaging productPackaging;
    private InputStream excelStream;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(WholesaleAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    CAccount cac = (CAccount) session.getAttribute("account");

    //////商品批发价模块//////

    /**
     * 批发产品[佣金...]查询
     * 2018-11-26
     *
     * @return
     */
    public String selectWholesaleList() {
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
            pageForm = this.wholesaleService.selectWholesaleList(map);
            return "wholesale_list";
        }
    }

    /**
     * 获取没有设置批发价佣金的产品
     * 2018-11-30
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
            pageForm = this.wholesaleService.getProductByStatus(map1);
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
     * 获取各种商品佣金百分比
     * 2018-12-03
     *
     * @return
     */
    public String getBrokeragePercent() {
        Map<String, Object> map = this.wholesaleService.getBrokeragePercent(productPackaging.getPpID());
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去添加批发商品价格佣金页面
     * 2018-11-23
     *
     * @return
     */
    public String toWholesaleAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计批发商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "wholesale_add";
        }
    }

    /**
     * 添加批发商品价格佣金
     * 2018-12-04
     *
     * @return
     */
    public String addBrokeragePercent() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //批发价添加
            String wholesaleId = serverService.getServerID("wholesaleId");
            wholesale.setWholesaleId(wholesaleId);
            wholesale.setCompanyId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            wholesale.setAddTimes(dateTime);
            wholesale.setPrincipal(cac.getStaffName());
            wholesale.setState("00");//默认给:00
            this.baseBeanService.save(wholesale);
            //将mapId中的对象添加到mapPro中
            mapPro.putAll(mapId);
            //批发价佣金添加
            PWhoBrokerage pWhoBrokerage = new PWhoBrokerage();
            pWhoBrokerage.setPpid(wholesale.getPpid());
            pWhoBrokerage.setCompanyId(cac.getCompanyID());
            pWhoBrokerage.setWholesaleId(wholesaleId);
            String whobroId = serverService.getServerID("whobroId");
            pWhoBrokerage.setWhobroId(whobroId);
            pWhoBrokerage.setBrokerage(Double.valueOf(mapPro.get("sbtz")));
            pWhoBrokerage.setTypePpid(mapPro.get("sbtzId"));
            this.baseBeanService.save(pWhoBrokerage);

            PWhoBrokerage pWhoBrokerage1 = new PWhoBrokerage();
            pWhoBrokerage1.setPpid(wholesale.getPpid());
            pWhoBrokerage1.setCompanyId(cac.getCompanyID());
            pWhoBrokerage1.setWholesaleId(wholesaleId);
            String whobroId1 = serverService.getServerID("whobroId");
            pWhoBrokerage1.setWhobroId(whobroId1);
            pWhoBrokerage1.setBrokerage(Double.valueOf(mapPro.get("tp")));
            pWhoBrokerage1.setTypePpid(mapPro.get("tpId"));
            this.baseBeanService.save(pWhoBrokerage1);

            PWhoBrokerage pWhoBrokerage2 = new PWhoBrokerage();
            pWhoBrokerage2.setPpid(wholesale.getPpid());
            pWhoBrokerage2.setCompanyId(cac.getCompanyID());
            pWhoBrokerage2.setWholesaleId(wholesaleId);
            String whobroId2 = serverService.getServerID("whobroId");
            pWhoBrokerage2.setWhobroId(whobroId2);
            pWhoBrokerage2.setBrokerage(Double.valueOf(mapPro.get("sbaz")));
            pWhoBrokerage2.setTypePpid(mapPro.get("sbazId"));
            this.baseBeanService.save(pWhoBrokerage2);

            PWhoBrokerage pWhoBrokerage3 = new PWhoBrokerage();
            pWhoBrokerage3.setPpid(wholesale.getPpid());
            pWhoBrokerage3.setCompanyId(cac.getCompanyID());
            pWhoBrokerage3.setWholesaleId(wholesaleId);
            String whobroId3 = serverService.getServerID("whobroId");
            pWhoBrokerage3.setWhobroId(whobroId3);
            pWhoBrokerage3.setBrokerage(Double.valueOf(mapPro.get("sjdl")));
            pWhoBrokerage3.setTypePpid(mapPro.get("sjdlId"));
            this.baseBeanService.save(pWhoBrokerage3);

            PWhoBrokerage pWhoBrokerage4 = new PWhoBrokerage();
            pWhoBrokerage4.setPpid(wholesale.getPpid());
            pWhoBrokerage4.setCompanyId(cac.getCompanyID());
            pWhoBrokerage4.setWholesaleId(wholesaleId);
            String whobroId4 = serverService.getServerID("whobroId");
            pWhoBrokerage4.setWhobroId(whobroId4);
            pWhoBrokerage4.setBrokerage(Double.valueOf(mapPro.get("xjdl")));
            pWhoBrokerage4.setTypePpid(mapPro.get("xjdlId"));
            this.baseBeanService.save(pWhoBrokerage4);

            PWhoBrokerage pWhoBrokerage5 = new PWhoBrokerage();
            pWhoBrokerage5.setPpid(wholesale.getPpid());
            pWhoBrokerage5.setCompanyId(cac.getCompanyID());
            pWhoBrokerage5.setWholesaleId(wholesaleId);
            String whobroId5 = serverService.getServerID("whobroId");
            pWhoBrokerage5.setWhobroId(whobroId5);
            pWhoBrokerage5.setBrokerage(Double.valueOf(mapPro.get("cjdl")));
            pWhoBrokerage5.setTypePpid(mapPro.get("cjdlId"));
            this.baseBeanService.save(pWhoBrokerage5);

            PWhoBrokerage pWhoBrokerage6 = new PWhoBrokerage();
            pWhoBrokerage6.setPpid(wholesale.getPpid());
            pWhoBrokerage6.setCompanyId(cac.getCompanyID());
            pWhoBrokerage6.setWholesaleId(wholesaleId);
            String whobroId6 = serverService.getServerID("whobroId");
            pWhoBrokerage6.setWhobroId(whobroId6);
            pWhoBrokerage6.setBrokerage(Double.valueOf(mapPro.get("khjf")));
            pWhoBrokerage6.setTypePpid(mapPro.get("khjfId"));
            this.baseBeanService.save(pWhoBrokerage6);
            //更改产品表佣金状态 wholesaleStatus为01
            this.wholesaleService.updatewholesaleStatus(wholesale.getPpid());
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
     * 去修改批发商品价格佣金页面
     * 2018-12-07
     *
     * @return
     */
    public String toWholesaleUpdate() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计批发商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            //批发价
            Object[] pWholesales = this.wholesaleService.PWholesaleById(productPackaging.getPpID());
            //批发价佣金
            List<BaseBean> brokerageList = this.wholesaleService.brokerageListById((String) pWholesales[0]);
            request.setAttribute("agencyTypeList", agencyList);
            request.setAttribute("pWholesales", pWholesales);
            request.setAttribute("brokerageList", brokerageList);
            request.setAttribute("product", productPackaging);
            request.setAttribute("flag", flag);
            return "wholesale_update";
        }
    }

    /**
     * 修改批发商品价格佣金
     * 2018-12-08
     *
     * @return
     */
    public String updateBrokeragePercent() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            logger.info("wholesale:" + wholesale);
            //获取批发价修改回显数据
            PWholesale pWholesales = this.wholesaleService.getPWholesaleById(wholesale.getWholesaleId());

            PWhoHistory pWhoHistory = new PWhoHistory();
            pWhoHistory.setWholesaleId(wholesale.getWholesaleId());
            pWhoHistory.setWholesale(pWholesales.getWholesale());
            pWhoHistory.setFactory(pWholesales.getFactory());
            pWhoHistory.setBrokerage(pWholesales.getBrokerage());
            pWhoHistory.setBrokerages(pWholesales.getBrokerages());
            pWhoHistory.setInvestType(pWholesales.getInvestType());
            pWhoHistory.setAddTimes(pWholesales.getAddTimes());
            pWhoHistory.setUpdateTimes(pWholesales.getUpdateTimes());
            pWhoHistory.setPrincipal(pWholesales.getPrincipal());
            pWhoHistory.setPpid(pWholesales.getPpid());
            pWhoHistory.setCompanyId(pWholesales.getCompanyId());
            pWhoHistory.setState(pWholesales.getState());
            this.baseBeanService.save(pWhoHistory);//保存批发价到历史记录表
            //生成(获取)批发价id
            String wholesaleId = this.serverService.getServerID("wholesaleId");
            pWholesales.setWholesaleId(wholesaleId);
            pWholesales.setWholesale(wholesale.getWholesale());
            pWholesales.setFactory(wholesale.getFactory());
            pWholesales.setBrokerage(wholesale.getBrokerage());
            pWholesales.setBrokerages(wholesale.getBrokerages());
            pWholesales.setInvestType(wholesale.getInvestType());
            pWholesales.setPrincipal(cac.getStaffName());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            pWholesales.setUpdateTimes(dateTime);
            this.baseBeanService.update(pWholesales); //批发价修改
            //将mapId中的对象添加到mapPro中
            mapPro.putAll(mapId);

            //批发价佣金修改
            String sbtzId = mapPro.get("sbtzId");
            PWhoBrokerage pWhoBrokerage1 = this.wholesaleService.getPWhoBrokerageById(sbtzId);
            PWhoBroHistory pWhoBroHistory1 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId1 = this.serverService.getServerID("whobroId");
            pWhoBroHistory1.setWhobroId(whobroId1);
            pWhoBroHistory1.setBrokerage(pWhoBrokerage1.getBrokerage());
            pWhoBroHistory1.setPpid(pWhoBrokerage1.getPpid());
            pWhoBroHistory1.setTypePpid(pWhoBrokerage1.getTypePpid());
            pWhoBroHistory1.setCompanyId(pWhoBrokerage1.getCompanyId());
            pWhoBroHistory1.setWholesaleId(pWhoBrokerage1.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory1);//保存批发价佣金到历史记录表
            pWhoBrokerage1.setBrokerage(Double.valueOf(mapPro.get("sbtz")));
            pWhoBrokerage1.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage1);//修改批发价佣金

            String tpId = mapPro.get("tpId");
            PWhoBrokerage pWhoBrokerage2 = this.wholesaleService.getPWhoBrokerageById(tpId);
            PWhoBroHistory pWhoBroHistory2 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId2 = this.serverService.getServerID("whobroId");
            pWhoBroHistory2.setWhobroId(whobroId2);
            pWhoBroHistory2.setBrokerage(pWhoBrokerage2.getBrokerage());
            pWhoBroHistory2.setPpid(pWhoBrokerage2.getPpid());
            pWhoBroHistory2.setTypePpid(pWhoBrokerage2.getTypePpid());
            pWhoBroHistory2.setCompanyId(pWhoBrokerage2.getCompanyId());
            pWhoBroHistory2.setWholesaleId(pWhoBrokerage2.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory2);//保存批发价佣金到历史记录表
            pWhoBrokerage2.setBrokerage(Double.valueOf(mapPro.get("tp")));
            pWhoBrokerage2.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage2);//修改批发价佣金

            String sbazId = mapPro.get("sbazId");
            PWhoBrokerage pWhoBrokerage3 = this.wholesaleService.getPWhoBrokerageById(sbazId);
            PWhoBroHistory pWhoBroHistory3 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId3 = this.serverService.getServerID("whobroId");
            pWhoBroHistory3.setWhobroId(whobroId3);
            pWhoBroHistory3.setBrokerage(pWhoBrokerage3.getBrokerage());
            pWhoBroHistory3.setPpid(pWhoBrokerage3.getPpid());
            pWhoBroHistory3.setTypePpid(pWhoBrokerage3.getTypePpid());
            pWhoBroHistory3.setCompanyId(pWhoBrokerage3.getCompanyId());
            pWhoBroHistory3.setWholesaleId(pWhoBrokerage3.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory3);//保存批发价佣金到历史记录表
            pWhoBrokerage3.setBrokerage(Double.valueOf(mapPro.get("sbaz")));
            pWhoBrokerage3.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage3);//修改批发价佣金

            String sjdlId = mapPro.get("sjdlId");
            PWhoBrokerage pWhoBrokerage4 = this.wholesaleService.getPWhoBrokerageById(sjdlId);
            PWhoBroHistory pWhoBroHistory4 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId4 = this.serverService.getServerID("whobroId");
            pWhoBroHistory4.setWhobroId(whobroId4);
            pWhoBroHistory4.setBrokerage(pWhoBrokerage4.getBrokerage());
            pWhoBroHistory4.setPpid(pWhoBrokerage4.getPpid());
            pWhoBroHistory4.setTypePpid(pWhoBrokerage4.getTypePpid());
            pWhoBroHistory4.setCompanyId(pWhoBrokerage4.getCompanyId());
            pWhoBroHistory4.setWholesaleId(pWhoBrokerage4.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory4);//保存批发价佣金到历史记录表
            pWhoBrokerage4.setBrokerage(Double.valueOf(mapPro.get("sjdl")));
            pWhoBrokerage4.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage4);//修改批发价佣金

            String xjdlId = mapPro.get("xjdlId");
            PWhoBrokerage pWhoBrokerage5 = this.wholesaleService.getPWhoBrokerageById(xjdlId);
            PWhoBroHistory pWhoBroHistory5 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId5 = this.serverService.getServerID("whobroId");
            pWhoBroHistory5.setWhobroId(whobroId5);
            pWhoBroHistory5.setBrokerage(pWhoBrokerage5.getBrokerage());
            pWhoBroHistory5.setPpid(pWhoBrokerage5.getPpid());
            pWhoBroHistory5.setTypePpid(pWhoBrokerage5.getTypePpid());
            pWhoBroHistory5.setCompanyId(pWhoBrokerage5.getCompanyId());
            pWhoBroHistory5.setWholesaleId(pWhoBrokerage5.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory5);//保存批发价佣金到历史记录表
            pWhoBrokerage5.setBrokerage(Double.valueOf(mapPro.get("xjdl")));
            pWhoBrokerage5.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage5);//修改批发价佣金

            String cjdlId = mapPro.get("cjdlId");
            PWhoBrokerage pWhoBrokerage6 = this.wholesaleService.getPWhoBrokerageById(cjdlId);
            PWhoBroHistory pWhoBroHistory6 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId6 = this.serverService.getServerID("whobroId");
            pWhoBroHistory6.setWhobroId(whobroId6);
            pWhoBroHistory6.setBrokerage(pWhoBrokerage6.getBrokerage());
            pWhoBroHistory6.setPpid(pWhoBrokerage6.getPpid());
            pWhoBroHistory6.setTypePpid(pWhoBrokerage6.getTypePpid());
            pWhoBroHistory6.setCompanyId(pWhoBrokerage6.getCompanyId());
            pWhoBroHistory6.setWholesaleId(pWhoBrokerage6.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory6);//保存批发价佣金到历史记录表
            pWhoBrokerage6.setBrokerage(Double.valueOf(mapPro.get("cjdl")));
            pWhoBrokerage6.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage6);//修改批发价佣金

            String khjfId = mapPro.get("khjfId");
            PWhoBrokerage pWhoBrokerage7 = this.wholesaleService.getPWhoBrokerageById(khjfId);
            PWhoBroHistory pWhoBroHistory7 = new PWhoBroHistory();
            //生成(获取)批发价佣金id
            String whobroId7 = this.serverService.getServerID("whobroId");
            pWhoBroHistory7.setWhobroId(whobroId7);
            pWhoBroHistory7.setBrokerage(pWhoBrokerage7.getBrokerage());
            pWhoBroHistory7.setPpid(pWhoBrokerage7.getPpid());
            pWhoBroHistory7.setTypePpid(pWhoBrokerage7.getTypePpid());
            pWhoBroHistory7.setCompanyId(pWhoBrokerage7.getCompanyId());
            pWhoBroHistory7.setWholesaleId(pWhoBrokerage7.getWholesaleId());
            this.baseBeanService.save(pWhoBroHistory7);//保存批发价佣金到历史记录表
            pWhoBrokerage7.setBrokerage(Double.valueOf(mapPro.get("khjf")));
            pWhoBrokerage7.setWholesaleId(wholesaleId);
            this.baseBeanService.update(pWhoBrokerage7);//修改批发价佣金

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
     * 产品批发价Excel导出
     * 2018-12-10
     *
     * @return
     */
    public String WholesaleShowExcel() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            StringBuilder sql = new StringBuilder();
            sql.append("select cp.companyname,pp.productcode,pp.barcode,pp.goodsname, gm.variableID,");
            sql.append(" pw.factory,pw.wholesale,nvl(pw.wholesale*(1+su.total_pct*0.01),pw.wholesale) as price");
            sql.append(" ,pp.showweixin ,pp.wholesalestatus from dt_productpackaging pp");
            sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
            sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid");
            sql.append(" left join dt_ccom_com cco on cco.compnay_id = cp.companyid");
            sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
            sql.append(" left join dt_set_subsidize su on su.gtid = cc.industrytype and su.stutas = '01'");
            sql.append(" left join dt_pro_wholesale pw on pp.ppid=pw.ppid");
            sql.append("  where pp.wholesalestatus='01' and pw.state ='00' and cp.companyid = ? ");

            List<Object> parms = new ArrayList<Object>();
            parms.add(cac.getCompanyID());
            List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
            excelStream = excelService.showExcel(new String[]{"序号", "公司名称", "产品编号", "条码",
                    "产品名称", "产品单位", "成本价", "系统批发价", "展示批发价", "发布状态", "佣金状态"}, list);
            return "showexcel";

        } else {
            //未登录
            return "failed";
        }

    }

    /**
     * 删除当前批发产品及佣金[更改状态为:01]
     * 更改产品表相应产品批发佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    public String wholesaleDeleteById() {
        Map<String, Object> map = this.wholesaleService.wholesaleDeleteById(wholesale);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }


    public WholesaleService getWholesaleService() {
        return wholesaleService;
    }

    public void setWholesaleService(WholesaleService wholesaleService) {
        this.wholesaleService = wholesaleService;
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

    public PWholesale getWholesale() {
        return wholesale;
    }

    public void setWholesale(PWholesale wholesale) {
        this.wholesale = wholesale;
    }

}
