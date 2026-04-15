package mobile.tiantai.android.action.storeProduction.budgetSheet;


import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.ProductsMmanagService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.office.DocumentPass;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.layercache.IndustryCache;
import hy.ea.util.layercache.Record;
import hy.plat.bo.BaseBean;
import hy.plat.bo.BusinessType;
import hy.plat.bo.BusinessTypeRecent;
import hy.plat.bo.PageForm;
import hy.plat.dao.BusinessTypeDao;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bean.payBudget.CostBudgetAddBean;
import mobile.tiantai.android.bean.payBudget.CostBudgetBaseBean;
import mobile.tiantai.android.bean.payBudget.PayBudgetAddBean;
import mobile.tiantai.android.service.DouService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.PayBudgetService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.ReviewCirculateService;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 卖场生产管理部（办公室）——》企业战略规划管理——》项目预算计划action
 * Created by Administrator on 2019-10-18.
 */
@Controller
@Scope("prototype")//作用域
public class PayBudgetSheetAction {
    @Resource
    private BaseBeanService baseBeanService;//基础beanservice
    @Resource
    private ReviewCirculateService reviewCirculateService;
    @Resource
    private ServerService serverService;
    @Resource
    private PayBudgetService payBudgetService;//预支付预算单service

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private ProductsMmanagService pmService;

    @Resource
    private BusinessTypeDao businessTypeDao;

    @Resource
    private ContractService contractService;

    @Resource
    private DocCommonService docCommonService;

    @Resource
    private CLogBookService logBookService;

    private CashierBills cashierBills;//收支单管理
    private PageForm pageForm;//分页结果集
    private int pageNumber;//第几页
    private String search;//模糊查询条件
    private int searchType;//模糊查询条件类型
    private String result;//异步返回结果
    private String departmentID;//部门id
    private String departmentName = "-1";//部门名称
    private boolean showFlag = false;//false查看总列表true查看分列表
    private String billID;//凭证号
    private Staff staff;//人力资源
    private String companyid;//公司id手机端传过来的
    private String staffId;//staff表员工id手机端传过来的
    private String companyName;//公司名称
    private Map<String, Object> parmaInfor;//参数信息
    private GoodsManage goodsManage;//货物信息表
    private PayBudgetAddBean addBean;//添加预算单参数bean
    private Map<String, Object> scanningMap;//添加预算单传入session中的PayBudgetAddBean数据
    private String mapKey;//sessiong中map的key值
    private String cashierBillsId;//预算单id
    private List<BaseBean> goodBeanslist;//货物集合
    private String delGoodsBillsIds;//修改页面删除已保存的货物id数组
    private int fbJumpType = 0;//发布跳转标识0：发布页；1：已发布页；2：未发布页
    private boolean fbJumpFlag = false;//标识发布页面是否显示发布选项卡
    private File file;
    private String companyID;
    private CostBudgetAddBean costAddBean;//招标投标单参数bean
    private CostBudgetBaseBean costBaseBean;//招标投标单存储单据等数据bean(用于存储部门、类型、日期、单据状态等信息)
    private String type;//招标投标查询类型
    private Map<String, Object> scanGoodsMap;//添单招标投标传入session中的PayBudgetAddBean数据
    private Map<String, Object> cacheGoodsMap;//添单招标投标确定提交的数据
    private String[] functionList;
    private String menuId;
    private File[] pic;// 产品描述图片
    private String[] picFileName;
    private GoodsBills goodsBills;
    private String keyNum;
    private String editFlag;
    private File fileLogo;// 品牌图片
    private List<BaseBean> orgList;//登陆人部门数据
    private Map<String, String> purpose;//物品分类（物品用途）
    private String tenantFlag;//租户标识：personal个人；other其他
    private String menuName;

    private String showType;

    private String approvalResult;//审核结果

    private String billsType = "初始项目单";//订单类型

    private List<BaseBean> invList;//仓库数据

    List<String> cashierBillsIDs = null; // 用于入库/出库单审核后进行库存增减操作(传递单据id)

    private String approvedStatus;//订单审核状态

    private String billsTypeName;
    private String head;

    private BusinessTypeRecent businessTypeRecent;

    private GoodsBillsItemRecent goodsRecent;

    @Resource
    private DouService douService;

    private String imageURL;

    /**
     * 保存更新另一个用户信息到session
     *
     * @param staffId
     */
    private void getTEshopCusForSession(String staffId) throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        if (cus == null) {
            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid=?", new Object[]{staffId});
            if (customer != null) {
                sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
                TEshopCusCom shopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?", new Object[]{customer.getAccount(), "01"});
                if (shopCusCom == null) {
                    goodBeanslist = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{customer.getAccount()});
                    if (goodBeanslist.size() > 0) { //这个地方只能用size来判断，如果判断是否为null的话会报错的，因为list集合本身是不会为null的
                        shopCusCom = (TEshopCusCom) goodBeanslist.get(0);
                    }
                    shopCusCom.setAcquiesce("01");
                    baseBeanService.update(shopCusCom);
                }
                sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, shopCusCom);
            }
        }
    }

    /**
     * 常规支出项目预算
     *
     * @return
     */
    public String toPayBudgetList() {
        try {
            //保存信息
            //判断是否存在登录信息，如果没有则保存信息
            //staffId = "cstaff20110712KAX2RHUQZI0000025385";//测试用
            //保存更新另一个用户信息到session
            getTEshopCusForSession(staffId);
            //1.清除session中扫描bean信息
            MapSesssionUtil.removeSession("scanningMap");
            MapSesssionUtil.removeSession("backMap");
            //2.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getDc(staffId, companyid, departmentID, showFlag, search, searchType);
            //3.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "payBudgetList";
    }


    /**
     * ajax获取支出预算单列表
     *
     * @return
     */
    public String ajaxPayBudgetList() {
        try {
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getDc(staffId, companyid, departmentID, showFlag, search, searchType);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 添加支出预算单
     *
     * @return
     */
    public String toAddPayBudget() {
        try {
            System.out.println("跳转添加支出预算单");
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            // 2.根据当前登录人公司id查询凭证号
            billID = serverService.getBillID(parmaInfor.get("companyId").toString());
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{parmaInfor.get("staffId").toString()});
            // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
            String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
            Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{parmaInfor.get("companyId").toString()});
            companyName = company != null ? company.getCompanyName() : "";
            // 5.判断是否是提交扫描数据.
            payBudgetService.toAddPayBudget(scanningMap, addBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toAddPayBudget";
    }

    /**
     * 异步根据部门id查询部门下在职员工信息
     *
     * @return
     */
    public String ajaxStaffForDep() {
        try {
            result = payBudgetService.ajaxStaffForDep(departmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 根据项目分类查询项目列表pageForm
     */
    public String ajaxProjectList() {
        try {
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getProDc(search);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("proList", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject obj = JSONObject.fromObject(map, jsonConfig);
            this.result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 扫描获取产品信息
     *
     * @return
     */
    public String scanningInventoryInfo() {
        try {
            System.out.println("通过扫描枪获取数据" + addBean.getBarcode());
            //addBean.setCompanyId("company20170324CUPPV7YJDK0000000088");//TODO 测试用
            //1.根据公司id和扫描的条形码号查询货物信息
            goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{addBean.getCompanyId(), addBean.getBarcode()});
            //2.判断商品是否存在拼接扫描参数信息到bean中
            payBudgetService.splicingAddBean(goodsManage, addBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "scanningInventoryInfo";
    }

    /**
     * 异步获取往来公司（商家）信息
     *
     * @return
     */
    public String ajaxWlGsList() {
        try {
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getWlGsDc(search);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 根据往来个人名称模糊查询列表
     *
     * @return
     */
    public String ajaxWlGrList() {
        try {
            //1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            String hql = " from ContactUser c where (c.staffName like ? or c.relation like ?) and c.companyID = ? ";
            Object[] parms = {"%" + search + "%", "%" + search + "%", parmaInfor.get("companyId").toString()};
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql, parms);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步根据key值删除session中的map的值
     *
     * @return
     */
    public String ajaxDelMapForKey() {
        try {
            payBudgetService.removeBeanForKey(mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 保存未支付预算单
     *
     * @return
     */
    public String saveCostSheet() {
        try {
            payBudgetService.saveCostSheet(cashierBills);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPayBudgetList();
    }

    /**
     * 入库/出库
     */
    public String ajaxUpdateWareManagement() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String selectedData = request.getParameter("selectedItems");
            if (selectedData != null && !selectedData.isEmpty()) {
                // 解析JSON数组字符串
                JSONArray jsonArray = JSONArray.fromObject(selectedData);
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    ids.add(jsonArray.getString(i));
                }
                for (String cashierBillsID : ids) {
                    updateWareManagement(cashierBillsID);
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "OK");
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    private void updateWareManagement(String cashierBillsID) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        //当前登录人员信息
        String hqlForMan = "from Staff where staffID = ?";
        Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{account.getStaffID()});
        String hqlcash = "from CashierBills where cashierBillsID = ?";
        CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash, new Object[]{cashierBillsID});
        if (Objects.isNull(cbills)) {
            return;
        }
        String status = cbills.getBillsType().equals("入库单") ? "15" : cbills.getBillsType().equals("出库单") ? "16" : "";
        cbills.setStatus(status);
        baseBeanList.add(cbills);
        //改变物品的状态
        String hqlg = "from GoodsBills where cashierBillsID = ?";
        Object[] par = {cashierBillsID};
        List<BaseBean> billsgoodlist = baseBeanService.getListBeanByHqlAndParams(hqlg, par);
        Map<String, GoodsBills> goodsIdMap = new HashMap<String, GoodsBills>();
        for (int i = 0; i < billsgoodlist.size(); i++) {
            GoodsBills finbg = new GoodsBills();
            GoodsBills goodsBills = new GoodsBills();
            finbg = (GoodsBills) billsgoodlist.get(i);
            goodsBills = (GoodsBills) billsgoodlist.get(i);
            if (StringUtils.isNotEmpty(finbg.getGoodsID())) {
                if (goodsIdMap.get(finbg.getGoodsID()) != null) {
                    BigDecimal quantity = BigDecimal.valueOf(Double.valueOf(StringUtils.isNotEmpty(goodsBills.getTiaoQuantity()) ? goodsBills.getTiaoQuantity() : "0"));
                    goodsBills.setQuantity(quantity.add(BigDecimal.valueOf(Double.valueOf(goodsIdMap.get(finbg.getGoodsID()).getQuantity()))).setScale(2, RoundingMode.HALF_UP).toString());
                } else {
                    goodsBills.setQuantity(finbg.getTiaoQuantity());
                }
                goodsIdMap.put(finbg.getGoodsID(), goodsBills);
                if (finbg.getGoodsBillsID() != null && !"".equals(finbg.getGoodsBillsID())) {
                    //更改单据出库入库物品的状态
                    finbg.setKcStatus(status);//已入/出库
                    //判断库存表中有没有要入库的物品
                    baseBeanList.add(finbg);
                    String billType = cbills.getBillsType();
                    String parameter = "员工：" + account.getAccountName() + "审核" + billType + "（库房物品 ID：" + finbg.getGoodsID() + "）";
                    CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
                    baseBeanList.add(cLogBook);
                }
            }
        }
        int ratio = cbills.getBillsType().equals("入库单") ? 1 : cbills.getBillsType().equals("出库单") ? -1 : 1;
        for (Map.Entry<String, GoodsBills> entry : goodsIdMap.entrySet()) {
            String countPerPackage = StringUtils.isNotEmpty(entry.getValue().getGuigeTypeValue()) ? entry.getValue().getGuigeTypeValue() : null;
            double countPerPkg = 1;
            if (Objects.nonNull(countPerPackage)) {
                String temp = countPerPackage.replace("个", "").replace(org.apache.commons.lang3.StringUtils.SPACE, "");
                if (temp.length() > 0 && StringUtils.isNumeric(temp)) {
                    countPerPkg = Double.parseDouble(temp);
                    if (countPerPkg == 0) {
                        countPerPkg = 1;
                    }
                }
            }
            double quantity = StringUtils.isNotEmpty(entry.getValue().getQuantity()) ? Double.parseDouble(entry.getValue().getQuantity()) : 0;
            double unitPrice = StringUtils.isNotEmpty(entry.getValue().getUnitPrice()) ? Double.parseDouble(entry.getValue().getUnitPrice()) : 0;
            //将数量转成最小单位个
            quantity = BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(countPerPkg)).doubleValue();
            unitPrice = BigDecimal.valueOf(unitPrice).divide(BigDecimal.valueOf(countPerPkg), 8, RoundingMode.HALF_UP).doubleValue();

            String invenQuantity = null;//存入库存表后库存
            String hql1 = " from Inventory where goodsID= ? and warehouse= ? and area IS NULL and frame IS NULL and position IS NULL";
            String hql2 = "from ProductPackaging where goodsID = ?";
            ProductPackaging productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{entry.getValue().getGoodsID()});
            List<BaseBean> inventoryList = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{entry.getValue().getGoodsID(), cbills.getDataDepotID()});
            if (Objects.nonNull(inventoryList) && inventoryList.size() > 0) {
                Inventory inventory = (Inventory) inventoryList.get(0);
                double invenQuant = Double.parseDouble(StringUtils.isNotEmpty(inventory.getInvenQuantity()) ? inventory.getInvenQuantity() : "0");//库存商品数量
                double amount = unitPrice * quantity;//收入商品总金额
                double sumPrice = Double.parseDouble(StringUtils.isNotEmpty(inventory.getSumPrice()) ? inventory.getSumPrice() : "0");//库存商品总金额
                double sum = invenQuant + (quantity * ratio);//入库后库存商品总数量
                double averagePrice = BigDecimal.valueOf(sumPrice + amount).divide(BigDecimal.valueOf(sum), 8, RoundingMode.HALF_UP).doubleValue();//移动加权平均单价
                entry.getValue().setInventoryID(inventory.getInventoryID());
                inventory.setSumPrice(BigDecimal.valueOf(averagePrice).multiply(BigDecimal.valueOf(sum)).setScale(2, RoundingMode.HALF_UP).toString());
                inventory.setUnitPrice(BigDecimal.valueOf(averagePrice).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                inventory.setInvenQuantity(BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).toString());
                baseBeanList.add(inventory);
                invenQuantity = inventory.getInvenQuantity();//库存
            } else {
                if (cbills.getBillsType().equals("入库单")) {
                    Inventory inventory = new Inventory();
                    double sum = quantity * ratio;//入库后库存商品总数量
                    double sumPrice = unitPrice * sum;//入库后库存商品总金额
                    inventory.setInventoryID(serverService.getServerID("Inventory"));
                    inventory.setCompanyID(cbills.getCompanyID());
                    inventory.setStaffID(cbills.getStaffID());
                    inventory.setStaffName(cbills.getStaffName());
                    inventory.setWarehouse(cbills.getDataDepotID());
                    inventory.setWarehouseName(cbills.getDataDepotName());
                    inventory.setGoodsID(entry.getValue().getGoodsID());
                    inventory.setGoodsName(entry.getValue().getGoodsName());
                    inventory.setBarcode(entry.getValue().getBarCode());
                    inventory.setGoodstatus("00");
                    inventory.setSumPrice(BigDecimal.valueOf(sumPrice).setScale(2, RoundingMode.HALF_UP).toString());
                    inventory.setUnitPrice(BigDecimal.valueOf(unitPrice).setScale(2, RoundingMode.HALF_UP).toString());
                    inventory.setInvenQuantity(BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).toString());
                    baseBeanList.add(inventory);
                    entry.getValue().setInventoryID(inventory.getInventoryID());
                    invenQuantity = inventory.getInvenQuantity();//库存
                }
            }

            stockInv stockinvs = new stockInv();
            stockinvs.setStockinvID(serverService.getServerID("stockInv"));
            stockinvs.setCompanyID(cbills.getCompanyID());
            stockinvs.setGoodsID(entry.getValue().getGoodsID());
            stockinvs.setGoodsType(entry.getValue().getTypeID());
            stockinvs.setGoodsName(entry.getValue().getGoodsName());
            stockinvs.setInvenQuantity(invenQuantity);
            stockinvs.setWarehouse(cbills.getDataDepotID());
            stockinvs.setWarehouseName(cbills.getDataDepotName());
            stockinvs.setStaffID(sta.getStaffID());//使用人id
            stockinvs.setStaffName(sta.getStaffName());//使用人name
            if (entry.getValue().getUnitPrice() == null) {
                entry.getValue().setPrice("0");
            }
            if (invenQuantity == null) {
                invenQuantity = "0";
            }
            stockinvs.setSummoney(BigDecimal.valueOf(Double.valueOf(invenQuantity)).multiply(BigDecimal.valueOf(unitPrice)).setScale(2, RoundingMode.HALF_UP).toString());//库存商品总金额
            stockinvs.setIntime(new Date());
            stockinvs.setGoodsBillsId(entry.getValue().getGoodsBillsID());
            String stockType = cbills.getBillsType().equals("入库单") ? "00" : cbills.getBillsType().equals("出库单") ? "01" : "";
            stockinvs.setType(stockType);//00入库 01出库
            baseBeanList.add(stockinvs);
        }
        String parameter = cbills.getBillsType() + "审核入库（凭证号" + cbills.getJournalNum() + "）";
        CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
        baseBeanList.add(logBook);
        baseBeanService.executeSqlsByParmsList(baseBeanList, null, null);
        ActionContext.getContext().getSession().put("cashierBills", cbills);
    }

    /**
     * 跳转预算单详情页
     *
     * @return
     */
    public String toDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 项目预算单中的物品
                String hql = " from GoodsBills gb ,GoodsManage gm where gb.goodsID = gm.goodsID and gb.cashierBillsID = ?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            // 2.判断是否是提交扫描数据.
            //payBudgetService.toAddPayBudget(scanningMap, addBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toDetail";
    }

    /**
     * 跳转修改预算单
     *
     * @return
     */
    public String toUpPayBudget() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
                String hql = " from GoodsBills gb ,GoodsManage gm where gb.goodsID = gm.goodsID and gb.cashierBillsID = ?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            // 3.判断是否是提交扫描数据.
            payBudgetService.toAddPayBudget(scanningMap, addBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toUpPayBudget";
    }

    /**
     * 修改预算单信息
     *
     * @return
     */
    public String upCostSheet() {
        try {
            // 删除以保存的goodsbills表
            if (StringHelper.isNotEmpty(delGoodsBillsIds)) {
                for (String goodsBillsId : delGoodsBillsIds.split(",")) {
                    // 删除原GoodsBills
                    String hql = "delete from GoodsBills o where o.goodsBillsID = ?";
                    List<Object[]> params = new ArrayList<Object[]>();
                    params.add(new Object[]{goodsBillsId});
                    baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, params);
                }
            }
            //修改预算单信息
            payBudgetService.upCostSheet(cashierBills);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPayBudgetList();
    }

    /**
     * 删除项目预算
     *
     * @return
     */
    public String delCostSheet() {
        try {
            String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
            String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
            List parmList = new ArrayList();
            parmList.add(new Object[]{cashierBillsId});
            parmList.add(new Object[]{cashierBillsId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parmList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPayBudgetList();
    }

    /**
     * 跳转发布页
     *
     * @return
     */
    public String toRelease() {
        try {
            System.out.println("跳转发布页" + cashierBillsId);
            //1.根据公司id和扫描的条形码号查询货物信息
            String cashHql = "from CashierBills o where o.cashierBillsID = ? and (o.status = '00' or o.status = '01')";
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(cashHql, new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 项目预算单中的物品
                String hql = " from GoodsBills gb ,GoodsManage gm where gb.goodsID = gm.goodsID and gb.cashierBillsID = ?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toRelease";
    }

    /**
     * 跳转发布预算单列表
     *
     * @return
     */
    public String toBudgetReleaseList() {
        try {
            System.out.println("跳转跳转发布预算单列表");
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getReleaseDc(fbJumpType);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toBudgetReleaseList";
    }

    /**
     * ajax获取已发布/未发布支出预算单列表
     *
     * @return
     */
    public String ajaxBudgetReleaseList() {
        try {
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getReleaseDc(fbJumpType);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 确定发布
     *
     * @return
     */
    public String confCostSheet() {
        try {
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID = ?", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                cashierBills.setStatus("03");// 确定招标
                baseBeanService.update(cashierBills);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPayBudgetList();
    }

    /**
     * 跳转盘库详细页面
     *
     * @return
     */
    public String toBarCodeInfo() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb ,GoodsManage gm where gb.goodsID = gm.goodsID and gb.goodsBillsID = ?";
            goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{search});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toBarCodeInfo";
    }


    /**
     * 上传图片/视频
     *
     * @return
     */
    public String uplodFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String name = request.getParameter("name");
        String chunk = request.getParameter("chunk");
        String chunks = request.getParameter("chunks");
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("\\");
        try {
            map = payBudgetService.upLoadFile(chunk, chunks, name, file, path, companyID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

//    =================初始项目功能================

    /**
     * 跳转招标投标预填页面
     *
     * @return
     */
    public String toCostBudgetBillList() {
        try {
            //1.清除session中扫描物品bean信息
            System.out.println("进入初始项目列表===========");
            System.out.printf("参数打印：menuId：%s，tenantFlag：%s，menuName：%s，staffId：%s，companyid：%s\n", menuId, tenantFlag, menuName, staffId, companyid);
            MapSesssionUtil.removeSession("scanGoodsMap");
            MapSesssionUtil.removeSession("cacheGoodsMap");
            //每次跳转新增页面，先清除基本信息的缓存
            MapSesssionUtil.removeSession("costBaseBean");
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

            if (StringUtils.isNotBlank(tenantFlag)) {
                MapSesssionUtil.saveSessionForObject("tenantFlag", tenantFlag);
            } else {
                tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            }
            if (StringUtils.isNotBlank(menuName)) {
                MapSesssionUtil.saveSessionForObject("menuName", menuName);
            }
            System.out.printf("从session中获取参数打印：menuId：%s，tenantFlag：%s，menuName：%s，staffId：%s，companyid：%s\n", menuId, tenantFlag, menuName, staffId, companyid);
            //个人账户登录
            if ("personal".equals(tenantFlag)) {
                System.out.println("初始项目个人模块================");
                SessionWrap sw = SessionWrap.getInstance();
                Map<String, Object> session = ActionContext.getContext().getSession();
                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
                if (tc != null) {
                    staffId = tc.getStaffid();
                }
            }
//            else{
//                //保存更新另一个用户信息到session
//                getTEshopCusForSession(staffId);
//            }

//            MapSesssionUtil.removeSession("backMap");
            //2.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getBudgetBillDc(staffId, companyid, type, showFlag, search, searchType, tenantFlag, billsType, null);
            //3.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            Map<String, Object> paramMap = MapSesssionUtil.getSessionForMap("paramMap");
            if (Objects.nonNull(paramMap)) {
                paramMap.put("billsType", billsType);
            } else {
                paramMap = new HashMap<>();
                paramMap.put("billsType", billsType);
                MapSesssionUtil.saveSessionForMap("paramMap", paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("ng".equals(menuId)) {
            return "costBudgetBillList";
        } else {
            return "costBudgetBillCirculationList";
        }

    }

    public String toProjectBillList() {
        try {
            String sccId = "";
            //1.清除session中扫描物品bean信息
            System.out.println("进入初始项目列表===========");
            System.out.printf("参数打印：menuId：%s，tenantFlag：%s，menuName：%s，staffId：%s，companyid：%s\n", menuId, tenantFlag, menuName, staffId, companyid);
            MapSesssionUtil.removeSession("scanGoodsMap");
            MapSesssionUtil.removeSession("cacheGoodsMap");
            //每次跳转新增页面，先清除基本信息的缓存
            MapSesssionUtil.removeSession("costBaseBean");
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

            if (StringUtils.isNotBlank(tenantFlag)) {
                MapSesssionUtil.saveSessionForObject("tenantFlag", tenantFlag);
            } else {
                tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            }
            if (StringUtils.isNotBlank(menuName)) {
                MapSesssionUtil.saveSessionForObject("menuName", menuName);
            }
            System.out.printf("从session中获取参数打印：menuId：%s，tenantFlag：%s，menuName：%s，staffId：%s，companyid：%s\n", menuId, tenantFlag, menuName, staffId, companyid);
            //个人账户登录
            if ("personal".equals(tenantFlag)) {
                System.out.println("初始项目个人模块================");
                SessionWrap sw = SessionWrap.getInstance();
                Map<String, Object> session = ActionContext.getContext().getSession();
                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
                if (tc != null) {
                    staffId = tc.getStaffid();
                    sccId = tc.getSccId();
                }
            }

            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            MapSesssionUtil.getSessionForObject("personalId");
            //非个人用户
            if (StringUtils.isNotBlank(tenantFlag)) {
                if (!"personal".equals(tenantFlag)) {
                    if (parmaInfor == null) {//未存入session且公司不为空，则将数据存入session
                        Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyid, staffId);
                        parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                    } else {
                        //传过来的参数不为空
                        if (StringHelper.isNotEmpty(companyid) && StringHelper.isNotEmpty(staffId)) {
                            //判断传过来的参数与session中的参数不一致
                            if (!parmaInfor.get("companyId").toString().equals(companyid) || !parmaInfor.get("staffId").toString().equals(staffId)) {
                                MapSesssionUtil.removeSession("paramMap");
                                Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyid, staffId);
                                parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                            }
                        }
                    }
                } else {
                    if (StringUtils.isNotBlank(staffId)) {
                        MapSesssionUtil.saveSessionForObject("personalId", staffId);
                    }
                }
            }

            Map<String, Object> paramMap = MapSesssionUtil.getSessionForMap("paramMap");
            if (Objects.nonNull(paramMap)) {
                paramMap.put("billsType", billsType);
                paramMap.put("sccId", sccId);
                if (Objects.isNull(paramMap.get("companyId"))) {
                    paramMap.put("companyId", companyid);
                }
            } else {
                paramMap = new HashMap<>();
                paramMap.put("billsType", billsType);
                paramMap.put("sccId", sccId);
                paramMap.put("companyId", companyid);
                MapSesssionUtil.saveSessionForMap("paramMap", paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectBillList";
    }

    /**
     * 跳转到收件页面
     *
     * @return
     */
    public String toCostBudgetBillReceiveList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //保存更新另一个用户信息到session
//            getTEshopCusForSession(staffID);
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

//            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillReceive(staffID, companyId, search, searchType);
//            //3.获取数据信息
//            pageForm = baseBeanService.getPageFormByDC(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), dc);
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillReceive(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
            Map<String, Object> paramMap = MapSesssionUtil.getSessionForMap("paramMap");
            if (Objects.nonNull(paramMap)) {
                paramMap.put("billsType", billsType);
            } else {
                paramMap = new HashMap<>();
                paramMap.put("billsType", billsType);
                MapSesssionUtil.saveSessionForMap("paramMap", paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetBillReceiveList";
    }

    private void getBudgetBillReceive(String staffId, String companyId, String search, int searchType, StringBuilder sql, List<Object> params, String billsType) throws Exception {
        sql.append(" select o from CashierBills o,CashierBillsExt b where o.cashierBillsID=b.cashierBillsID ");
        //查询传阅——接收数据状态
        if ("初始项目单".equals(billsType) || "收入".equals(billsType) || "支出".equals(billsType)) {
            sql.append(" and o.billsType in ('收入','支出') and o.zctype='cg' ");
        } else {
            sql.append(" and o.billsType in ('" + billsType + "') and o.zctype='cg' ");
        }

        sql.append(" and b.receiverID = ? ");
        params.add(staffId);

        if (StringUtils.isNotEmpty(companyId)) {
            sql.append(" and b.receiverCompanyID = ? ");
            params.add(companyId);
        } else {
            //往来个人没有公司
            sql.append(" and b.receiverCompanyID is null ");
        }
        //拼接模糊查询条件
        searchSwith(sql, params, search, searchType);

        sql.append(" order by cashierDate desc ");

//        pageForm = baseBeanService.getPageFormBySQL(
//                (null != pageForm ? pageForm.getPageNumber() : 1),
//                (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(1)"
//                        + sql.substring(sql.indexOf("from")), params.toArray());
    }

    public String toCostBudgetBillApprovalList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

//            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillReceive(staffID, companyId, search, searchType);
//            //3.获取数据信息
//            pageForm = baseBeanService.getPageFormByDC(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), dc);
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillApproval(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetBillApprovalList";
    }

    private void getBudgetBillApproval(String staffId, String companyId, String search, int searchType, StringBuilder sql, List<Object> params, String billsType) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        sql.append(" select o from CashierBills o,CashierBillsExt b where o.cashierBillsID=b.cashierBillsID ");
        //查询传阅——接收数据状态
        if ("初始项目单".equals(billsType) || "收入".equals(billsType) || "支出".equals(billsType)) {
            sql.append(" and o.status='07' and o.billsType in ('收入','支出') and o.zctype='cg' ");
        } else {
            sql.append(" and o.status='07' and o.billsType in ('" + billsType + "') and o.zctype='cg' ");
        }
//        sql.append(" and o.responsible = ? ");
        sql.append(" and b.receiverID = ? ");
//        params.add(account.getStaffID());
        params.add(staffId);
        if (StringUtils.isNotEmpty(companyId)) {
            sql.append(" and b.receiverCompanyID = ? ");
            params.add(companyId);
        } else {
            //往来个人没有公司
            sql.append(" and b.receiverCompanyID is null ");
        }
        //拼接模糊查询条件
        searchSwith(sql, params, search, searchType);
        sql.append(" order by cashierDate desc ");
    }

    private void searchSwith(StringBuilder sql, List<Object> params, String search, int searchType) {
        //拼接模糊查询条件
        if (StringHelper.isNotEmpty(search)) {
            //模糊查询
            switch (searchType) {
                case 2://项目名称
                    sql.append(" and o.projectName like ? ");
                    params.add("'%" + search + "%'");
                    break;
                case 3://凭证号
                    sql.append(" and o.journalNum like ? ");
                    params.add("'%" + search + "%'");
                    break;
                case 4://责任人
                    sql.append(" and o.staffName like ? ");
                    params.add("'%" + search + "%'");
                    break;
                case 11://制单人
                    sql.append(" and o.inputName like ? ");
                    params.add("'%" + search + "%'");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 跳转到已发送页面
     *
     * @return
     */
    public String toCostBudgetBillSentList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            //保存更新另一个用户信息到session
//            getTEshopCusForSession(staffID);
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillSent(staffID, companyId, search, searchType);
            //3.获取数据信息
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillSent(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetBillSentList";
    }

    private void getBudgetBillSent(String staffId, String companyId, String search, int searchType, StringBuilder sql, List<Object> params, String billsType) throws Exception {
        sql.append(" select o from CashierBills o,CashierBillsExt b where o.cashierBillsID=b.cashierBillsID ");
        //查询传阅——接收数据状态
        if ("初始项目单".equals(billsType) || "收入".equals(billsType) || "支出".equals(billsType)) {
            sql.append(" and o.billsType in ('收入','支出') and o.zctype='cg' ");
        } else {
            sql.append(" and o.billsType in ('" + billsType + "') and o.zctype='cg' ");
        }

        sql.append(" and b.fromMember = ? ");
        params.add(staffId);

        if (StringUtils.isNotEmpty(companyId)) {
            sql.append(" and companyID =?");
            params.add(companyId);
        } else {
            //个人没有公司
            sql.append(" and companyID=' ' ");
        }

        //拼接模糊查询条件
        searchSwith(sql, params, search, searchType);

        sql.append(" order by cashierDate desc ");
    }

    /**
     * ajax获取支出预算单发送列表
     *
     * @return
     */
    public String ajaxCostBudgetBillSentList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //1.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillSent(staffID, companyId, search, searchType);
//            //2.获取数据信息
//            pageForm = baseBeanService.getPageFormByDC(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), dc);

            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillSent(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * ajax获取预算单接收列表
     *
     * @return
     */
    public String ajaxCostBudgetBillReceiveList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

//            //1.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillReceive(staffID, companyId, search, searchType);
//            //2.获取数据信息
//            pageForm = baseBeanService.getPageFormByDC(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), dc);

            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillReceive(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());

            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String ajaxCostBudgetBillApprovalList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getBudgetBillApproval(staffID, companyId, search, searchType, sql, params, billsType);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());

            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    public String ajaxCostBudgetBillList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffId = (String) parmaInfor.get("staffId");
                companyid = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffId = (String) personalId;
            }
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getBudgetBillDc(staffId, companyid, type, showFlag, search, searchType, tenantFlag, billsType, approvedStatus);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageForm.getPageSize() == 0 ? 10 : pageForm.getPageSize()), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 统计条数
     *
     * @return
     */
    public String countBudgetBillList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            String staffID = null;
            String companyId = null;

            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getBudgetBillDc(staffID, companyId, type, showFlag, search, searchType, tenantFlag, billsType, null);
//            DetachedCriteria dcSent = payBudgetService.getBudgetBillSent(staffID, companyId, type, searchType);
//            DetachedCriteria dcReceive = payBudgetService.getBudgetBillReceive(staffID, companyId, type, searchType);

            StringBuilder sentSql = new StringBuilder();
            List<Object> sentParams = new ArrayList<>();
            getBudgetBillSent(staffID, companyId, search, searchType, sentSql, sentParams, billsType);

            StringBuilder receiveSql = new StringBuilder();
            List<Object> receiveParams = new ArrayList<>();
            getBudgetBillReceive(staffID, companyId, search, searchType, receiveSql, receiveParams, billsType);

            StringBuilder approvalSql = new StringBuilder();
            List<Object> approvalParams = new ArrayList<>();
            getBudgetBillApproval(staffID, companyId, search, searchType, approvalSql, approvalParams, billsType);

            //3.获取拟稿总条数
            int ngCount = baseBeanDao.getConutByDC(dc);
            int sentCount = baseBeanDao.getConutByByHqlAndParams("select count(*) " + sentSql.substring(sentSql.indexOf("from")), sentParams.toArray());
            int receiveCount = baseBeanDao.getConutByByHqlAndParams("select count(*) " + receiveSql.substring(receiveSql.indexOf("from")), receiveParams.toArray());
            int approvalCount = baseBeanDao.getConutByByHqlAndParams("select count(*) " + approvalSql.substring(approvalSql.indexOf("from")), approvalParams.toArray());
            HashMap<String, Integer> map = new HashMap<>();
            map.put("ng", ngCount);
            map.put("sent", sentCount);
            map.put("receive", receiveCount);
            map.put("approval", approvalCount);
            JSONObject jo = JSONObject.fromObject(map);
            result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 跳转添加招标投标单中间页面(新增按钮的功能)
     *
     * @return
     */
    public String toAddCostBudgetItem() {
        try {
            System.out.println("跳转添加初始项目单中间页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            String commitFlag = request.getParameter("commitFlag");
            System.out.println(cashierBillsId);

            if (costBaseBean != null) {
                MapSesssionUtil.saveSessionForObject("costBaseBean", costBaseBean);
            } else {
                costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            }
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            if ("back".equals(commitFlag) && StringUtils.isNotEmpty(costAddBean.getGoodsPurpose())) {
                purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
                purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
                purpose.put("billsType", costAddBean.getBillsType());
            } else {
                purpose.put("goodsPurposeId", costBaseBean.getTradeId());
                purpose.put("goodsPurpose", costBaseBean.getTradeName());
                purpose.put("billsType", costBaseBean.getBillsType());
            }

            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 将扫描的数据放入缓存中
            //scanGoodsMap的数据，每次在中间页面点击提交操作之后都要把scanGoodsMap数据清除
            // TODO: 20241022：提交的同时保存最近联系人账户信息、账号信息数据入账号信息管理表
            payBudgetService.toAddCostBudgetItem(scanGoodsMap, costAddBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addCostBudgetItem";
    }

    public String toAddProjectItem() {
        try {
            System.out.println("跳转添加初始项目单中间页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            String commitFlag = request.getParameter("commitFlag");
            System.out.println(cashierBillsId);

            if (costBaseBean != null) {
                MapSesssionUtil.saveSessionForObject("costBaseBean", costBaseBean);
            } else {
                costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            }
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            if ("back".equals(commitFlag) && StringUtils.isNotEmpty(costAddBean.getGoodsPurpose())) {
                purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
                purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
                purpose.put("billsType", costAddBean.getBillsType());
            } else {
                purpose.put("goodsPurposeId", costBaseBean.getTradeId());
                purpose.put("goodsPurpose", costBaseBean.getTradeName());
                purpose.put("billsType", costBaseBean.getBillsType());
            }

            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 将扫描的数据放入缓存中
            //scanGoodsMap的数据，每次在中间页面点击提交操作之后都要把scanGoodsMap数据清除
            // TODO: 20241022：提交的同时保存最近联系人账户信息、账号信息数据入账号信息管理表
            payBudgetService.toAddCostBudgetItem(scanGoodsMap, costAddBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addProjectItem";
    }


    /**
     * 跳转到添加招标投标页面(扫描、新增操作的提交都会走这个接口)
     *
     * @return8
     */
    public String toAddCostBudgetBill() {
        try {
            System.out.println("跳转添加初始项目单页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            String industryType = null;
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
                String rawIndustryType = company.getIndustryType();
//                rawIndustryType = "餐饮业";
                if (rawIndustryType != null && !rawIndustryType.isEmpty()) {
                    String[] parts = rawIndustryType.split("/");
                    if (parts.length > 0) {
                        for (int i = parts.length - 1; i >= 0; i--) {
                            if (parts[i] != null && !parts[i].trim().isEmpty()) {
                                industryType = parts[i].trim();
                                break;
                            }
                        }
                    }
                    if (industryType == null) {
                        industryType = rawIndustryType;
                    }
                } else {
                    industryType = rawIndustryType;
                }
            } else {
                billID = serverService.getBillID(staffID);
            }

            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            String hqlBusinessType = " FROM BusinessType WHERE typeName = ?";
            BusinessType businessType = (BusinessType) baseBeanService.getBeanByHqlAndParams(hqlBusinessType, new Object[]{industryType});
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            if (costBaseBean == null) {
                costBaseBean = new CostBudgetBaseBean();
            }
            if (businessType != null) {
                //行业分类
                if (StringUtils.isBlank(costBaseBean.getTradeName())) {
                    costBaseBean.setTradeName(businessType.getTypeName());
                }
                if (StringUtils.isBlank(costBaseBean.getTradeId())) {
                    costBaseBean.setTradeId(businessType.getTypeId());
                }
                //项目分类
                if (StringUtils.isBlank(costBaseBean.getXmTypeName())) {
                    costBaseBean.setXmTypeName(businessType.getTypeName());
                }
                if (StringUtils.isBlank(costBaseBean.getXmType())) {
                    costBaseBean.setXmType(businessType.getTypeId());
                }

            }

            // 将中间页面的缓存数据保存到主页面的缓存中，然后将scanGoodsMap的数据清除
            String commitFlag = request.getParameter("commitFlag");
            if (!"no".equals(commitFlag)) {
                payBudgetService.toAddCostBudgetBill(cacheGoodsMap, scanGoodsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addCostBudgetBill";
    }

    public String toAddProjectBill() {
        try {
            System.out.println("跳转添加初始项目单页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            // 将中间页面的缓存数据保存到主页面的缓存中，然后将scanGoodsMap的数据清除
            String commitFlag = request.getParameter("commitFlag");
            if (!"no".equals(commitFlag)) {
                payBudgetService.toAddCostBudgetBill(cacheGoodsMap, scanGoodsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addProjectBill";
    }


    /**
     * 扫描获取产品信息
     *
     * @return
     */
    public String scanningCostBudgetInfo() {
        try {
            System.out.println("初始项目通过扫描枪获取数据" + costAddBean.getBarCode());
            //1.根据扫描的条形码号查询货物信息
//            goodsManage = (GoodsManage-) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.barCode = ?", new Object[]{costAddBean.getBarCode()});
            if (StringUtils.isNotBlank(costAddBean.getCompanyId())) {
                goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{costAddBean.getCompanyId(), costAddBean.getBarCode()});
            }

            //2.判断商品是否存在拼接扫描参数信息到bean中
            payBudgetService.splicingAddBudgetBean(goodsManage, costAddBean);
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
            purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
            purpose.put("billsType", costAddBean.getBillsType());

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                }
            }
            //暂时：扫码进入的单位为空，因为物品表数据的单位是老的
            costAddBean.setVariableId("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(costAddBean.getGoodsName()) && StringUtils.isEmpty(costAddBean.getTradeName())) {
            return "noData";
        }
        return "scanningCostBudgetInfo";
    }

    public String scanningProjectInfo() {
        try {
            System.out.println("初始项目通过扫描枪获取数据" + costAddBean.getBarCode());
            if (Objects.nonNull(costAddBean.getBarCode())) {
                //1.根据扫描的条形码号查询货物信息
//            goodsManage = (GoodsManage-) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.barCode = ?", new Object[]{costAddBean.getBarCode()});
                if (StringUtils.isNotBlank(costAddBean.getCompanyId())) {
                    goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{costAddBean.getCompanyId(), costAddBean.getBarCode()});
                }

                //2.判断商品是否存在拼接扫描参数信息到bean中
                payBudgetService.splicingAddBudgetBean(goodsManage, costAddBean);
            }
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
            purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
            purpose.put("billsType", costAddBean.getBillsType());

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                }
            }
            //暂时：扫码进入的单位为空，因为物品表数据的单位是老的
            costAddBean.setVariableId("");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (StringUtils.isEmpty(costAddBean.getGoodsName()) && StringUtils.isEmpty(costAddBean.getTradeName())) {
//            return "noData";
//        }
        return "scanningProjectInfo";
    }


    /**
     * 非扫码方式查询数据
     * 根据物品名称或者条码进行查询
     *
     * @return
     */
    public String getCostBudgetInfo() {
        try {

            System.out.println("查询条件" + search);
            search = search.trim();
            //1.根据查询条件查询货物信息
//            goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.barCode = ?", new Object[]{costAddBean.getBarCode()});
            if (StringUtils.isNotBlank(costAddBean.getCompanyId())) {
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and (o.barCode like ? or goodsName like ?)", new Object[]{costAddBean.getCompanyId(), "%" + search + "%", "%" + search + "%"});
            }

            //如果本公司没有，那么查全表
            if (CollectionUtils.isEmpty(goodBeanslist)) {
                StringBuffer sql = new StringBuffer();
                List<Object> parame = new ArrayList<>();
                sql.append("select distinct gm.barcode,gm.goodsname ");
                sql.append(" from dtGoodsManage gm ");
                sql.append(" where 1=1 ");

                sql.append(" and (gm.barcode like ? or gm.goodsname like ?)");
                parame.add("%" + search + "%");
                parame.add("%" + search + "%");
//                sql.append(" order by gm.createdate ");
                //查询出基本信息
                List<Object[]> prolist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parame.toArray());
                if (CollectionUtil.isNotEmpty(prolist)) {
                    goodBeanslist = new ArrayList<BaseBean>();
                    GoodsManage bean = null;
                    for (Object[] obj : prolist) {
                        if (obj[0] != null) {
                            bean = new GoodsManage();
                            bean.setBarCode(String.valueOf(obj[0]));
                            bean.setGoodsName(String.valueOf(obj[1]));
                            goodBeanslist.add(bean);
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goods", goodBeanslist);
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "success";
    }

    public String getBusinessType() {
        try {
            List<BaseBean> businessTypeList = baseBeanService.getListBeanByHqlAndParams("from BusinessType B where B.typeName like ? " + "and B.status = ? and B.typeLevel = ? order by B.sortNum ", new Object[]{"%" + search + "%", "1", 5});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("businessTypeList", businessTypeList);
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String findBusinessTypeRecent() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            HttpServletRequest request = ServletActionContext.getRequest();
            CAccount account = (CAccount) session.get("account");
            if (Objects.isNull(account)) {
                return "success";
            }
            List<BaseBean> businessTypeRecentList = baseBeanService.getListBeanByHqlAndParams("from BusinessTypeRecent B where B.staffId = ? " + "and B.flag = ? order by B.createDate desc", new Object[]{account.getStaffID(), request.getParameter("flag")});
            Map<String, Object> map = new HashMap<String, Object>();
            if (Objects.nonNull(businessTypeRecentList) && businessTypeRecentList.size() > 0) {
                if (businessTypeRecentList.size() >= 20) {
                    map.put("businessTypeRecentList", businessTypeRecentList.subList(0, 20));
                } else {
                    map.put("businessTypeRecentList", businessTypeRecentList.subList(0, businessTypeRecentList.size()));
                }
            } else {
                map.put("businessTypeRecentList", businessTypeRecentList);
            }
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String addBusinessTypeRecent() {
        payBudgetService.addBusinessTypeRecent(businessTypeRecent);
        return "success";
    }

    public String getGoods() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (Objects.isNull(account)) {
                return "success";
            }
            List<BaseBean> goodsList = baseBeanService.getListBeanByHqlAndParams("from GoodsManage G where G.companyID = ? and (G.barCode like ? or G.goodsName like ?) " + "order by G.goodsName", new Object[]{account.getCompanyID(), "%" + search + "%", "%" + search + "%"});
            if (Objects.isNull(goodsList) || goodsList.size() == 0) {
                goodsList = baseBeanService.getListBeanByHqlAndParams("from GoodsManage G where G.barCode like ? or G.goodsName like ? " + "order by G.goodsName", new Object[]{"%" + search + "%", "%" + search + "%"});
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goodsList", goodsList);
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String findGoodsRecent() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            HttpServletRequest request = ServletActionContext.getRequest();
            CAccount account = (CAccount) session.get("account");
            if (Objects.isNull(account)) {
                return "success";
            }
            List<BaseBean> goodsRecentList = baseBeanService.getListBeanByHqlAndParams("from GoodsBillsItemRecent G where G.staffId = ? " + "and G.flag = ? order by G.createDate desc", new Object[]{account.getStaffID(), request.getParameter("flag")});
            Map<String, Object> map = new HashMap<String, Object>();
            if (Objects.nonNull(goodsRecentList) && goodsRecentList.size() > 0) {
                if (goodsRecentList.size() >= 20) {
                    map.put("goodsRecentList", goodsRecentList.subList(0, 20));
                } else {
                    map.put("goodsRecentList", goodsRecentList.subList(0, goodsRecentList.size()));
                }
            } else {
                map.put("goodsRecentList", goodsRecentList);
            }
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String addGoodsRecent() {
        payBudgetService.addGoodsRecent(goodsRecent);
        return "success";
    }

    public String getOrganizationByStaff() {
        try {
            result = payBudgetService.getOrganizationByStaff(companyid, staffId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public String getDepot() {
        try {
            result = payBudgetService.getDepotByCompanyId(companyid, "001");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 保存初始项目
     *
     * @return
     */
    public String saveCostBudgetSheet() {
        try {
            payBudgetService.saveCostBudgetSheet(cashierBills);
            billsType = cashierBills.getBillsType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toCostBudgetBillList();
    }

    public String saveProjectSheet() {
        try {
            payBudgetService.saveCostBudgetSheet(cashierBills);
            billsType = cashierBills.getBillsType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toProjectBillList();
    }

    public String importCostBudgetSheet() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String cashierBillsData = request.getParameter("cashierBillsData");
            String data = request.getParameter("goodsBillsData");
            //json字符串转实体
            ObjectMapper objectMapper = new ObjectMapper();
            List<GoodsBillsData> goodsBillsDataList = objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, GoodsBillsData.class));
            String resultData = payBudgetService.saveCostBudgetBill("采购单", cashierBillsData, goodsBillsDataList);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", resultData);
            JSONObject jo = JSONObject.fromObject(map);
            result = jo.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Action.SUCCESS;
    }

    public String importCostBudgetSheetByImage() {
        try {
            String robotMessage = douService.handleMessage("提取文字", imageURL);
            List<String> robotData = douService.generateRobotDataForOrder(robotMessage);
            String cashierBillsData = ",,,,,酒、饮料及茶叶零售,";
            List<GoodsBillsData> goodsBillsDataList = new ArrayList<>();
            for (String line : robotData) {
                GoodsBillsData goodsBillsData = new GoodsBillsData();
                String temp = line.replace("|", "#");
                String[] cols = temp.split("#");
                goodsBillsData.setLineNo(cols[1]);
                goodsBillsData.setBarCode(cols[2]);
                goodsBillsData.setGoodsName(cols[3]);
                goodsBillsData.setCount(cols[6]);
                goodsBillsData.setPrice(cols[7]);
                goodsBillsDataList.add(goodsBillsData);
            }
            if (billsType.equals("初始项目单")) {
                billsType = "支出";
            }
            String resultData = payBudgetService.saveCostBudgetBill(billsType, null, goodsBillsDataList);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", resultData);
            JSONObject jo = JSONObject.fromObject(map);
            result = jo.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Action.SUCCESS;
    }

    /**
     * 修改招标投标数据
     *
     * @return
     */
    public String updateCostBudgetSheet() {
        try {
            menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            payBudgetService.updateCostBudgetSheet(cashierBills, menuId);
            billsType = cashierBills.getBillsType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("receive".equals(menuId)) {
            return toCostBudgetBillReceiveList();
        }
        return toCostBudgetBillList();
    }

    public String updateProjectSheet() {
        try {
            menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            payBudgetService.updateCostBudgetSheet(cashierBills, menuId);
            billsType = cashierBills.getBillsType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("receive".equals(menuId)) {
            return toProjectBillReceiveList();
        }
        return toProjectBillList();
    }

    /**
     * 删除招标投标单
     *
     * @return
     */
    public String delCostBudgetSheet() {
        try {
            //TODO:校验状态是否能删除
            String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
            String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
            List parmList = new ArrayList();
            parmList.add(new Object[]{cashierBillsId});
            parmList.add(new Object[]{cashierBillsId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parmList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("receive".equals(menuId)) {
            return toCostBudgetBillReceiveList();
        }
        return toCostBudgetBillList();
    }

    public String delProjectCostBudgetSheet() {
        try {
            String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
            String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
            List parmList = new ArrayList();
            parmList.add(new Object[]{cashierBillsId});
            parmList.add(new Object[]{cashierBillsId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parmList);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("result", "OK");
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 跳转招标投标单详情页
     *
     * @return
     */
    public String toCostBudgetDetail() {
        String pageType = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            pageType = request.getParameter("pageType");
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 项目预算单中的物品
//                String hql = " from GoodsBills gb where gb.cashierBillsID = ? ";
//                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});

                if (CollectionUtil.isNotEmpty(list)) {
                    goodBeanslist = new ArrayList<>();
                    CostBudgetAddBean info = null;
                    GoodsBills bill = null;
                    GoodsBillsExt ext = null;
                    for (int i = 0; i < list.size(); i++) {
                        JSONArray arr = JSONArray.fromObject(list.get(i));
                        bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                        ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                        info = new CostBudgetAddBean();
                        payBudgetService.getBudgetItemInfo(bill, ext, info);
                        goodBeanslist.add(info);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("printPage".equals(pageType)) {
            return "costBudgetBillPrint";
        }
        return "costBudgetDetail";
    }

    public String toProjectBudgetDetail() {
        String pageType = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            pageType = request.getParameter("pageType");
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});

                if (CollectionUtil.isNotEmpty(list)) {
                    goodBeanslist = new ArrayList<>();
                    CostBudgetAddBean info = null;
                    GoodsBills bill = null;
                    GoodsBillsExt ext = null;
                    for (int i = 0; i < list.size(); i++) {
                        JSONArray arr = JSONArray.fromObject(list.get(i));
                        bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                        ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                        info = new CostBudgetAddBean();
                        payBudgetService.getBudgetItemInfo(bill, ext, info);
                        goodBeanslist.add(info);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("printPage".equals(pageType)) {
            return "projectBillPrint";
        }
        return "projectBudgetDetail";
    }

    /**
     * 跳转修改招标投标页面
     *
     * @return
     */
    public String toUpdateCostBudgetBill() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 3.判断是否是提交扫描数据
            payBudgetService.getUpdateCostBudgetItem(cacheGoodsMap, goodBeanslist, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetUpdate";
    }

    public String toUpdateProjectBill() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 3.判断是否是提交扫描数据
            payBudgetService.getUpdateCostBudgetItem(cacheGoodsMap, goodBeanslist, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "updateProjectBill";
    }


    /**
     * 中间页面的修改提交功能
     *
     * @return
     */
    public String toUpdateCostBudgetItem() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
//                String hql = " from GoodsBills gb where gb.cashierBillsID = ?";
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 3.判断是否是提交扫描数据
            payBudgetService.getUpdateCostBudgetItem(cacheGoodsMap, goodBeanslist, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetUpdate";
    }

    /**
     * 新增招标投标页面删除功能
     *
     * @return
     */
    public String removeGoodsBillOfAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
//            costAddBean = new CostBudgetAddBean();
//            costAddBean.setBillsType(cashierBills.getBillsType());
//            costAddBean.setDepartmentName(cashierBills.getDepartmentName());
//            costAddBean.setDepartmentID(cashierBills.getDepartmentID());
//            costAddBean.setItemName(cashierBills.getProjectName());
//            costAddBean.setItemType(cashierBills.getXmtypename());
//            costAddBean.setItemId(cashierBills.getProID());
//            costAddBean.setXmType(cashierBills.getXmtype());
//            costAddBean.setItemCode(cashierBills.getProjectCode());
//            costAddBean.setAddress(cashierBills.getAddress());
//            costAddBean.setCoordinate(cashierBills.getCoordinate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addCostBudgetBill";
    }

    public String removeProjectGoodsBillOfAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addProjectBill";
    }

    /**
     * 新增招标投标页面删除功能（中间页面）
     *
     * @return
     */
    public String removeGoodsBillOfToAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }

            Set<String> keySet = scanGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addCostBudgetItem";
    }

    public String removeProjectGoodsBillOfToAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
                invList = payBudgetService.getDepot(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }

            Set<String> keySet = scanGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addProjectItem";
    }

    /**
     * 修改招标投标页面删除功能
     *
     * @return
     */
    public String removeGoodsBillOfUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetUpdate";
    }

    public String removeProjectGoodsBillOfUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "updateProjectBill";
    }

    /**
     * 修改费用物品页面明细查询
     *
     * @return
     */
    public String toCostBudgetItemUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
//            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
//            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
//            costAddBean = new CostBudgetAddBean();
//            payBudgetService.getBudgetItemInfo(goodsBills, costAddBean);

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) cacheGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetItemUpdate";
    }

    public String toProjectItemUpdate() {
        try {
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) cacheGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectItemUpdate";
    }

    /**
     * 修改费用物品页面明细查询(中间页面)
     *
     * @return
     */
    public String toCostBudgetItemToUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
//            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
//            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
//            costAddBean = new CostBudgetAddBean();
//            payBudgetService.getBudgetItemInfo(goodsBills, costAddBean);

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) scanGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetItemUpdate";
    }

    public String toProjectItemToUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
//            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
//            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
//            costAddBean = new CostBudgetAddBean();
//            payBudgetService.getBudgetItemInfo(goodsBills, costAddBean);

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) scanGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectItemUpdate";
    }

    /**
     * 修改费用物品明细明细查询
     *
     * @return
     */
    public String toCostBudgetItemDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            hql = " from GoodsBillsExt ext where ext.goodsBillsID = ?";
            GoodsBillsExt goodsBillsExt = (GoodsBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            costAddBean = new CostBudgetAddBean();
            payBudgetService.getBudgetItemInfo(goodsBills, goodsBillsExt, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "costBudgetItemDetail";
    }

    public String toProjectItemDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            hql = " from GoodsBillsExt ext where ext.goodsBillsID = ?";
            GoodsBillsExt goodsBillsExt = (GoodsBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            costAddBean = new CostBudgetAddBean();
            payBudgetService.getBudgetItemInfo(goodsBills, goodsBillsExt, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectItemDetail";
    }

    public String getBusinessTypeByPID() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String codeID = request.getParameter("codeID");
//        List<CCode> codeList = codeService.getCCodeListByPID(companyID, codeID);
        List<BusinessType> codeList = businessTypeDao.getBusinessTypeByPID(codeID);
        map.put("codeList", codeList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    @Resource
    private IndustryCache industryCache;

    public String getBusinessTypeRoot() {

        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> industryRootList = industryCache.getIndustryRootList();
        map.put("rootList", industryRootList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String getBusinessTypeLikeName() {

        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();

        String keyword = request.getParameter("keyword");
        List<Record> records = industryCache.searchByName(keyword);
        map.put("codeList", records);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String getBusinessTypesById() {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();

        String Id = request.getParameter("id");
        List<Record> records = industryCache.searchByCodeId(Id);
        map.put("codeList", records);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 传阅初始项目单
     */
    public String circularizeBudgetBill() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String keyId = request.getParameter("keyId");
        String receiverID = request.getParameter("receiverID");//传阅接收人id
        String receiverDeptID = request.getParameter("receiverDeptID");//传阅接收人部门
        String receiverCompanyID = request.getParameter("receiverCompanyID");//传阅接收人公司
        String source = request.getParameter("source");//04：初始项目——集团内部最近联系人；05：初始项目——往来单位最近联系人；06：初始项目——往来个人最近联系人
        JSONObject jsonObjList = new JSONObject();
        try {

            String hqlstaff = "from Staff where staffID = ?";
            CashierBillsExt billSub = null;
            CashierBills bills = null;


            // 列表传阅
            if (StringUtils.isNotEmpty(keyId)) {
                String hql = "from CashierBillsExt where cashierBillsID = ?";
                billSub = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});

                hql = "from CashierBills where cashierBillsID = ?";
                bills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});
            }

            CAccount account = (CAccount) session.getAttribute("account");

            String staffID = null;
            String checkComID = null;
            if (account != null) {
                staffID = account.getStaffID();
                checkComID = account.getCompanyID();

            }
            String checkOrgID = null;
            if (checkComID != null && !checkComID.equals("")) {

                checkOrgID = contractService.getCheckOrg(staffID, checkComID);
            }
            if (billSub == null) {
                billSub = new CashierBillsExt();
                billSub.setCashierBillsID(bills.getCashierBillsID());
            }
            billSub.setUpdateTime(new Date());
            billSub.setFromMember(account.getStaffID());

            billSub.setReceiverID(receiverID);
            billSub.setReceiverDeptID(receiverDeptID);
            billSub.setReceiverCompanyID(receiverCompanyID);
            if ("00".equals(bills.getStatus())) {
                bills.setStatus("50");
            }

            baseBeanService.update(billSub);
            baseBeanService.update(bills);

            // 增加已传阅记录
            DocumentPass dp = new DocumentPass();
            dp.setPassId(serverService.getServerID("passId"));
            dp.setDocId(keyId);
            dp.setPassTime(new Date());
            dp.setSubscriber2(account.getStaffID());
            dp.setDeptOfsub2(checkOrgID);
            dp.setCompanyIDOfsub2(account.getCompanyID());
            dp.setToSubscriber2(receiverID);
            dp.setDeptOftoSub2(receiverDeptID);
            dp.setCompanyIDOftosub2(receiverCompanyID);
            baseBeanService.save(dp);
            Staff receiver = null;
            COrganization org = null;
            Company company = null;
            String hqlorg = "from COrganization where organizationID = ?";
            String hqlcompany = "from Company where companyID = ?";
            receiver = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{receiverID});

            org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlorg, new Object[]{receiverDeptID});

            company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcompany, new Object[]{receiverCompanyID});
            String orgName = "";
            if (org != null) {
                orgName = org.getOrganizationName();
            }
            String comName = "";
            if (company != null) {
                comName = company.getCompanyName();
            }
            // 添加对公文的操作记录
            docCommonService.addTrackRecord(bills.getCashierBillsID(), account.getStaffID(), checkOrgID, account.getCompanyID(), "传阅初始项目单至" + receiver.getStaffName() + "(" + receiver.getStaffCode() + ")[" + orgName + "," + comName + "]");

            String content = "[收件箱]下,请进行查阅处理";
            Map<String, Object> session1 = ActionContext.getContext().getSession();
//            docCommonService.sendPhoneMessage(document.getReceiverID(), document
//                            .getReceiverDeptID(), document.getReceiverCompanyID(), account.getStaffID(), account.getCompanyID(), content,
//                    doc.getTitle(), (String) session1.get("module"), "pass");


            contractService.addRecentContact(account.getStaffID(), account.getCompanyID(), receiverID, receiverDeptID, receiverCompanyID, source);
            jsonObjList.accumulate("result", "suc");
        } catch (Exception e) {
            jsonObjList.accumulate("result", "fail");
            e.printStackTrace();
        }
        result = jsonObjList.toString();
        return "success";
    }

    /**
     * 获取最近填写账户
     *
     * @return
     */
    public String getGoodsBillsContactRecent() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = (CAccount) session.get("account");
        List<GoodsBillsContactRecent> list = payBudgetService.getGoodsBillsContactRecent(account.getStaffID(), request.getParameter("accountFlag"));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";


    }

    /**
     * 获取最近填写物品
     *
     * @return
     */
    public String getGoodsBillsItemRecent() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = (CAccount) session.get("account");
        List<GoodsBillsItemRecent> list = payBudgetService.getGoodsBillsItemRecent(account.getStaffID(), request.getParameter("flag"));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 根据最近填写物品快速填写明细项
     *
     * @return
     */
    public String getCostBudgetItem() {
        try {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            String goodsBillsId = request.getParameter("goodsBillsId");
            costAddBean = payBudgetService.getCostBudgetItemById(keyNum);
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                    costAddBean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(bean.getBalance())).toString());
                }
            }

            costAddBean.setGoodsBillsID("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "scanningCostBudgetInfo";
    }

    public String getProjectItem() {
        try {
            costAddBean = payBudgetService.getCostBudgetItemById(keyNum);
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                    costAddBean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(bean.getBalance())).toString());
                }
            }

            costAddBean.setGoodsBillsID("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "scanningProjectInfo";
    }

    public String getUnitData() {
        List<BaseBean> list = payBudgetService.getUnitData();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public String getSpecsByParent() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<HashMap<String, String>> list = payBudgetService.getSpecsByParent(request.getParameter("parentId"));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public String getSpecsInfo() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String parentId = request.getParameter("parentId");
        String code = request.getParameter("code");
        List<BaseBean> list = payBudgetService.getSpecsInfo(code, parentId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


//     =================================项目计划功能============================

    /**
     * 跳转项目计划预填页面
     *
     * @return
     */
    public String toPlanCostBudgetBillList() {
        try {
            //1.清除session中扫描物品bean信息
            MapSesssionUtil.removeSession("scanGoodsMap");
            MapSesssionUtil.removeSession("cacheGoodsMap");
            //每次跳转新增页面，先清除基本信息的缓存
            MapSesssionUtil.removeSession("costBaseBean");
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

            if (StringUtils.isNotBlank(tenantFlag)) {
                MapSesssionUtil.saveSessionForObject("tenantFlag", tenantFlag);
            } else {
                tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            }
            //个人账户登录
            if ("personal".equals(tenantFlag)) {
                SessionWrap sw = SessionWrap.getInstance();
                Map<String, Object> session = ActionContext.getContext().getSession();
                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
                if (tc != null) {
                    staffId = tc.getStaffid();
                }
            }
            if (StringUtils.isNotBlank(menuName)) {
                MapSesssionUtil.saveSessionForObject("menuName", menuName);
            }
//            else{
//                //保存更新另一个用户信息到session
//                getTEshopCusForSession(staffId);
//            }

//            MapSesssionUtil.removeSession("backMap");
            //2.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getPlanBudgetBillDc(staffId, companyid, type, showFlag, search, searchType, tenantFlag);
            //3.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("ng".equals(menuId)) {
            return "planCostBudgetBillList";
        } else {
            return "planCostBudgetBillCirculationList";
        }

    }

    /**
     * 跳转到项目计划收件页面
     *
     * @return
     */
    public String toPlanCostBudgetBillReceiveList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //保存更新另一个用户信息到session
//            getTEshopCusForSession(staffID);
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

//            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillReceive(staffID, companyId, search, searchType);
//            //3.获取数据信息
//            pageForm = baseBeanService.getPageFormByDC(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), dc);
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getPlanBudgetBillReceive(staffID, companyId, search, searchType, sql, params);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetBillReceiveList";
    }

    private void getPlanBudgetBillReceive(String staffId, String companyId, String search, int searchType, StringBuilder sql, List<Object> params) throws Exception {
        sql.append(" select o from CashierBills o,CashierBillsExt b where o.cashierBillsID=b.cashierBillsID ");
        //查询传阅——接收数据状态
        sql.append(" and o.status='50' and o.billsType in ('计划') and o.zctype='cg' ");

        sql.append(" and b.receiverID = ? ");
        params.add(staffId);

        if (StringUtils.isNotEmpty(companyId)) {
            sql.append(" and b.receiverCompanyID = ? ");
            params.add(companyId);
        } else {
            //往来个人没有公司
            sql.append(" and b.receiverCompanyID is null ");
        }
        //拼接模糊查询条件
        searchSwith(sql, params, search, searchType);

        sql.append(" order by cashierDate desc ");

//        pageForm = baseBeanService.getPageFormBySQL(
//                (null != pageForm ? pageForm.getPageNumber() : 1),
//                (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(1)"
//                        + sql.substring(sql.indexOf("from")), params.toArray());
    }


    /**
     * 跳转到项目计划已发送页面
     *
     * @return
     */
    public String toPlanCostBudgetBillSentList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            //保存更新另一个用户信息到session
//            getTEshopCusForSession(staffID);
            //将menuId保存在session中，以便后续页面进行取出
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }

            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillSent(staffID, companyId, search, searchType);
            //3.获取数据信息
            StringBuilder sql = new StringBuilder();
            List<Object> params = new ArrayList<>();
            getPlanBudgetBillSent(staffID, companyId, search, searchType, sql, params);
            pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.substring(sql.indexOf("from")), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetBillSentList";
    }


    /**
     * ajax获取项目计划单发送列表
     *
     * @return
     */
    public String ajaxPlanCostBudgetBillSentList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getPlanBudgetBillSent(staffID, companyId, search, searchType);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * ajax获取项目计划单接收列表
     *
     * @return
     */
    public String ajaxPlanCostBudgetBillReceiveList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }

            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getPlanBudgetBillReceive(staffID, companyId, search, searchType);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    public String ajaxPlanCostBudgetBillList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffId = (String) parmaInfor.get("staffId");
                companyid = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffId = (String) personalId;
            }
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getPlanBudgetBillDc(staffId, companyid, type, showFlag, search, searchType, tenantFlag);
            //2.获取数据信息
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
            //3.将结果放入map返回
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 统计条数
     *
     * @return
     */
    public String countPlanBudgetBillList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            String staffID = null;
            String companyId = null;

            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            //1.将列表信息拼接成DetachedCriteria类
            DetachedCriteria dc = payBudgetService.getPlanBudgetBillDc(staffID, companyId, type, showFlag, search, searchType, tenantFlag);
//            DetachedCriteria dcSent = payBudgetService.getBudgetBillSent(staffID, companyId, type, searchType);
//            DetachedCriteria dcReceive = payBudgetService.getBudgetBillReceive(staffID, companyId, type, searchType);

            StringBuilder sentSql = new StringBuilder();
            List<Object> sentParams = new ArrayList<>();
            getPlanBudgetBillSent(staffID, companyId, search, searchType, sentSql, sentParams);

            StringBuilder receiveSql = new StringBuilder();
            List<Object> receiveParams = new ArrayList<>();
            getPlanBudgetBillReceive(staffID, companyId, search, searchType, receiveSql, receiveParams);
            //3.获取拟稿总条数
            int ngCount = baseBeanDao.getConutByDC(dc);
            int sentCount = baseBeanDao.getConutByByHqlAndParams("select count(*) " + sentSql.substring(sentSql.indexOf("from")), sentParams.toArray());
            int receiveCount = baseBeanDao.getConutByByHqlAndParams("select count(*) " + receiveSql.substring(receiveSql.indexOf("from")), receiveParams.toArray());
            HashMap<String, Integer> map = new HashMap<>();
            map.put("ng", ngCount);
            map.put("sent", sentCount);
            map.put("receive", receiveCount);
            JSONObject jo = JSONObject.fromObject(map);
            result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    private void getPlanBudgetBillSent(String staffId, String companyId, String search, int searchType, StringBuilder sql, List<Object> params) throws Exception {
        sql.append(" select o from CashierBills o,CashierBillsExt b where o.cashierBillsID=b.cashierBillsID ");
        //查询传阅——接收数据状态
        sql.append(" and o.status='50' and o.billsType in ('计划') and o.zctype='cg' ");

        sql.append(" and b.fromMember = ? ");
        params.add(staffId);

        if (StringUtils.isNotEmpty(companyId)) {
            sql.append(" and companyID =?");
            params.add(companyId);
        } else {
            //个人没有公司
            sql.append(" and companyID=' ' ");
        }

        //拼接模糊查询条件
        searchSwith(sql, params, search, searchType);

        sql.append(" order by cashierDate desc ");
    }

    /**
     * 跳转添加项目计划单中间页面(新增按钮的功能)
     *
     * @return
     */
    public String toAddPlanCostBudgetItem() {
        try {
            System.out.println("跳转添加项目计划单中间页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            String commitFlag = request.getParameter("commitFlag");
            System.out.println(cashierBillsId);

            if (costBaseBean != null) {
                MapSesssionUtil.saveSessionForObject("costBaseBean", costBaseBean);
            } else {
                costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            }
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            if ("back".equals(commitFlag) && StringUtils.isNotEmpty(costAddBean.getGoodsPurpose())) {
                purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
                purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
                purpose.put("billsType", costAddBean.getBillsType());
            } else {
                purpose.put("goodsPurposeId", costBaseBean.getTradeId());
                purpose.put("goodsPurpose", costBaseBean.getTradeName());
                purpose.put("billsType", costBaseBean.getBillsType());
            }

            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 将扫描的数据放入缓存中
            //scanGoodsMap的数据，每次在中间页面点击提交操作之后都要把scanGoodsMap数据清除
            // TODO: 20241022：提交的同时保存最近联系人账户信息、账号信息数据入账号信息管理表
            payBudgetService.toAddPlanCostBudgetItem(scanGoodsMap, costAddBean, mapKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addPlanCostBudgetItem";
    }


    /**
     * 跳转到添加项目计划页面(扫描、新增操作的提交都会走这个接口)
     *
     * @return8
     */
    public String toAddPlanCostBudgetBill() {
        try {
            System.out.println("跳转添加项目计划单页面");
            HttpServletRequest request = ServletActionContext.getRequest();
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
            // 将中间页面的缓存数据保存到主页面的缓存中，然后将scanGoodsMap的数据清除
            String commitFlag = request.getParameter("commitFlag");
            if (!"no".equals(commitFlag)) {
                payBudgetService.toAddCostBudgetBill(cacheGoodsMap, scanGoodsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addPlanCostBudgetBill";
    }


    /**
     * 项目计划扫描获取产品信息
     *
     * @return
     */
    public String scanningPlanCostBudgetInfo() {
        try {
            System.out.println("项目计划通过扫描枪获取数据" + costAddBean.getBarCode());
            //1.根据扫描的条形码号查询货物信息
//            goodsManage = (GoodsManage-) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.barCode = ?", new Object[]{costAddBean.getBarCode()});
            if (StringUtils.isNotBlank(costAddBean.getCompanyId())) {
                goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{costAddBean.getCompanyId(), costAddBean.getBarCode()});
            }

            //2.判断商品是否存在拼接扫描参数信息到bean中
            payBudgetService.splicingAddBudgetBean(goodsManage, costAddBean);
            if (purpose == null) {
                purpose = new HashMap<>();
            }
            purpose.put("goodsPurposeId", costAddBean.getGoodsPurposeId());
            purpose.put("goodsPurpose", costAddBean.getGoodsPurpose());
            purpose.put("billsType", costAddBean.getBillsType());

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                }
            }
            //暂时：扫码进入的单位为空，因为物品表数据的单位是老的
            costAddBean.setVariableId("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(costAddBean.getGoodsName()) && StringUtils.isEmpty(costAddBean.getTradeName())) {
            return "planNoData";
        }
        return "scanningPlanCostBudgetInfo";
    }


    /**
     * 保存项目计划
     *
     * @return
     */
    public String savePlanCostBudgetSheet() {
        try {
            payBudgetService.savePlanCostBudgetSheet(cashierBills);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPlanCostBudgetBillList();
    }

    /**
     * 修改项目计划数据
     *
     * @return
     */
    public String updatePlanCostBudgetSheet() {
        try {
            menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            payBudgetService.updatePlanCostBudgetSheet(cashierBills, menuId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("receive".equals(menuId)) {
            return toPlanCostBudgetBillReceiveList();
        }
        return toPlanCostBudgetBillList();
    }

    /**
     * 删除项目计划单
     *
     * @return
     */
    public String delPlanCostBudgetSheet() {
        try {
            //TODO:校验状态是否能删除
            String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
            String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
            List parmList = new ArrayList();
            parmList.add(new Object[]{cashierBillsId});
            parmList.add(new Object[]{cashierBillsId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parmList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toPlanCostBudgetBillList();
    }

    /**
     * 跳转项目计划单详情页
     *
     * @return
     */
    public String toPlanCostBudgetDetail() {
        String pageType = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            pageType = request.getParameter("pageType");
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 项目预算单中的物品
//                String hql = " from GoodsBills gb where gb.cashierBillsID = ? ";
//                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});

                if (CollectionUtil.isNotEmpty(list)) {
                    goodBeanslist = new ArrayList<>();
                    CostBudgetAddBean info = null;
                    GoodsBills bill = null;
                    GoodsBillsExt ext = null;
                    for (int i = 0; i < list.size(); i++) {
                        JSONArray arr = JSONArray.fromObject(list.get(i));
                        bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                        ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                        info = new CostBudgetAddBean();
                        payBudgetService.getBudgetItemInfo(bill, ext, info);
                        goodBeanslist.add(info);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("printPage".equals(pageType)) {
            return "costBudgetBillPrint";
        }
        return "planCostBudgetDetail";
    }

    /**
     * 跳转修改项目计划页面
     *
     * @return
     */
    public String toUpdatePlanCostBudgetBill() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ? order by ext.orderNum ";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 3.判断是否是提交扫描数据
            payBudgetService.getUpdateCostBudgetItem(cacheGoodsMap, goodBeanslist, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetUpdate";
    }


    /**
     * 中间页面的修改提交功能
     *
     * @return
     */
    public String toUpdatePlanCostBudgetItem() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});
            if (cashierBills != null) {
                // 2.项目预算单中的物品
//                String hql = " from GoodsBills gb where gb.cashierBillsID = ?";
                String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.cashierBillsID = ?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
            }
            if (pic != null) {
                costAddBean.setPic(pic);
                costAddBean.setPicFileName(picFileName);
            }
            if (fileLogo != null) {
                costAddBean.setFileLogo(fileLogo);
            }
            // 3.判断是否是提交扫描数据
            payBudgetService.getUpdateCostBudgetItem(cacheGoodsMap, goodBeanslist, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetUpdate";
    }

    /**
     * 新增招标投标页面删除功能
     *
     * @return
     */
    public String removePlanGoodsBillOfAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
            costBaseBean = (CostBudgetBaseBean) MapSesssionUtil.getSessionForObject("costBaseBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addPlanCostBudgetBill";
    }

    /**
     * 新增项目计划页面删除功能（中间页面）
     *
     * @return
     */
    public String removePlanGoodsBillOfToAdd() {
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{staffID});
            // 2.根据当前登录人公司id查询凭证号
            if (StringUtils.isNotBlank(companyId)) {
                billID = serverService.getBillID(companyId);
                // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
                String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
                Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{companyId});
                companyName = company != null ? company.getCompanyName() : "";
                orgList = payBudgetService.getOrganization(companyId, staffID);
            } else {
                billID = serverService.getBillID(staffID);
            }

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                scanGoodsMap.remove(keyNum);
            }

            Set<String> keySet = scanGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "addPlanCostBudgetItem";
    }

    /**
     * 修改项目计划页面删除功能
     *
     * @return
     */
    public String removePlanGoodsBillOfUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ? ", new Object[]{cashierBillsId});

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                cacheGoodsMap.remove(keyNum);
            }
            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetUpdate";
    }

    /**
     * 修改项目计划页面明细查询
     *
     * @return
     */
    public String toPlanCostBudgetItemUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
//            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
//            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
//            costAddBean = new CostBudgetAddBean();
//            payBudgetService.getBudgetItemInfo(goodsBills, costAddBean);

            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) cacheGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetItemUpdate";
    }

    /**
     * 修改项目计划页面明细查询(中间页面)
     *
     * @return
     */
    public String toPlanCostBudgetItemToUpdate() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
//            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
//            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
//            costAddBean = new CostBudgetAddBean();
//            payBudgetService.getBudgetItemInfo(goodsBills, costAddBean);

            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            if (CollectionUtil.isNotEmpty(scanGoodsMap)) {//未存入session，则将数据存入session
                costAddBean = (CostBudgetAddBean) scanGoodsMap.get(keyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetItemUpdate";
    }

    /**
     * 修改项目计划明细明细查询
     *
     * @return
     */
    public String toPlanCostBudgetItemDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            String hql = " from GoodsBills gb where gb.goodsBillsID = ?";
            goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            hql = " from GoodsBillsExt ext where ext.goodsBillsID = ?";
            GoodsBillsExt goodsBillsExt = (GoodsBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{search});
            costAddBean = new CostBudgetAddBean();
            payBudgetService.getBudgetItemInfo(goodsBills, goodsBillsExt, costAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "planCostBudgetItemDetail";
    }


    /**
     * 传阅项目计划单
     */
    public String circularizePlanBudgetBill() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String keyId = request.getParameter("keyId");
        String receiverID = request.getParameter("receiverID");//传阅接收人id
        String receiverDeptID = request.getParameter("receiverDeptID");//传阅接收人部门
        String receiverCompanyID = request.getParameter("receiverCompanyID");//传阅接收人公司
        String source = request.getParameter("source");//07：初始项目——集团内部最近联系人；08：初始项目——往来单位最近联系人；09：初始项目——往来个人最近联系人
        JSONObject jsonObjList = new JSONObject();
        try {

            String hqlstaff = "from Staff where staffID = ?";
            CashierBillsExt billSub = null;
            CashierBills bills = null;


            // 列表传阅
            if (StringUtils.isNotEmpty(keyId)) {
                String hql = "from CashierBillsExt where cashierBillsID = ?";
                billSub = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});

                hql = "from CashierBills where cashierBillsID = ?";
                bills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{keyId});
            }

            CAccount account = (CAccount) session.getAttribute("account");

            String staffID = null;
            String checkComID = null;
            if (account != null) {
                staffID = account.getStaffID();
                checkComID = account.getCompanyID();

            }
            String checkOrgID = null;
            if (checkComID != null && !checkComID.equals("")) {

                checkOrgID = contractService.getCheckOrg(staffID, checkComID);
            }
            if (billSub == null) {
                billSub = new CashierBillsExt();
                billSub.setCashierBillsID(bills.getCashierBillsID());
            }
            billSub.setUpdateTime(new Date());
            billSub.setFromMember(account.getStaffID());

            billSub.setReceiverID(receiverID);
            billSub.setReceiverDeptID(receiverDeptID);
            billSub.setReceiverCompanyID(receiverCompanyID);
            if ("00".equals(bills.getStatus())) {
                bills.setStatus("50");
            }

            baseBeanService.update(billSub);
            baseBeanService.update(bills);

            // 增加已传阅记录
            DocumentPass dp = new DocumentPass();
            dp.setPassId(serverService.getServerID("passId"));
            dp.setDocId(keyId);
            dp.setPassTime(new Date());
            dp.setSubscriber2(account.getStaffID());
            dp.setDeptOfsub2(checkOrgID);
            dp.setCompanyIDOfsub2(account.getCompanyID());
            dp.setToSubscriber2(receiverID);
            dp.setDeptOftoSub2(receiverDeptID);
            dp.setCompanyIDOftosub2(receiverCompanyID);
            baseBeanService.save(dp);
            Staff receiver = null;
            COrganization org = null;
            Company company = null;
            String hqlorg = "from COrganization where organizationID = ?";
            String hqlcompany = "from Company where companyID = ?";
            receiver = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{receiverID});

            org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlorg, new Object[]{receiverDeptID});

            company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcompany, new Object[]{receiverCompanyID});
            String orgName = "";
            if (org != null) {
                orgName = org.getOrganizationName();
            }
            String comName = "";
            if (company != null) {
                comName = company.getCompanyName();
            }
            // 添加对公文的操作记录
            docCommonService.addTrackRecord(bills.getCashierBillsID(), account.getStaffID(), checkOrgID, account.getCompanyID(), "传阅项目计划单至" + receiver.getStaffName() + "(" + receiver.getStaffCode() + ")[" + orgName + "," + comName + "]");

            String content = "[收件箱]下,请进行查阅处理";
            Map<String, Object> session1 = ActionContext.getContext().getSession();
//            docCommonService.sendPhoneMessage(document.getReceiverID(), document
//                            .getReceiverDeptID(), document.getReceiverCompanyID(), account.getStaffID(), account.getCompanyID(), content,
//                    doc.getTitle(), (String) session1.get("module"), "pass");


            contractService.addRecentContact(account.getStaffID(), account.getCompanyID(), receiverID, receiverDeptID, receiverCompanyID, source);
            jsonObjList.accumulate("result", "suc");
        } catch (Exception e) {
            jsonObjList.accumulate("result", "fail");
            e.printStackTrace();
        }
        result = jsonObjList.toString();
        return "success";
    }

    /**
     * 根据最近填写物品快速填写明细项
     *
     * @return
     */
    public String getPlanCostBudgetItem() {
        try {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            String goodsBillsId = request.getParameter("goodsBillsId");
            costAddBean = payBudgetService.getCostBudgetItemById(keyNum);
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            if (CollectionUtil.isEmpty(cacheGoodsMap) && CollectionUtil.isEmpty(scanGoodsMap)) {
                costAddBean.setInitialBalance("0");
            } else {
                if (CollectionUtil.isNotEmpty(scanGoodsMap)) {
                    Set<String> keySet = scanGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                } else {
                    Set<String> keySet = cacheGoodsMap.keySet();
                    CostBudgetAddBean bean = null;
                    for (String s : keySet) {
                        bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                    }
                    costAddBean.setInitialBalance(bean.getBalance());
                    costAddBean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(bean.getBalance())).toString());
                }
            }

            costAddBean.setGoodsBillsID("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "scanningPlanCostBudgetInfo";
    }

    /**
     * 审核权限简单控制，如果当前单子没有审核人或者有审核人同时审核人是自己则可以审核
     *
     * @return
     */
    public String approval() {
        try {
            String result = payBudgetService.approval(cashierBillsId, approvalResult);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("result", result);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String changeBillsType() {
        try {
            String result = payBudgetService.changeBillsType(cashierBillsId, billsTypeName);
            Map<String, Object> map = new HashMap<String, Object>(1);
            map.put("result", result);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String projectOrderManage() {
        return "projectOrderManage";
    }

    public String toProjectBillReceiveList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }
//            StringBuilder sql = new StringBuilder();
//            List<Object> params = new ArrayList<>();
//            getBudgetBillReceive(staffID, companyId, search, searchType, sql, params, billsType);
//            pageForm = baseBeanService.getPageForm(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) "
//                            + sql.substring(sql.indexOf("from")), params.toArray());
            Map<String, Object> paramMap = MapSesssionUtil.getSessionForMap("paramMap");
            if (Objects.nonNull(paramMap)) {
                paramMap.put("billsType", billsType);
            } else {
                paramMap = new HashMap<>();
                paramMap.put("billsType", billsType);
                MapSesssionUtil.saveSessionForMap("paramMap", paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectBillReceiveList";
    }


    public String toProjectBillSentList() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffID = null;
            String companyId = null;
            tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffID = (String) parmaInfor.get("staffId");
                companyId = (String) parmaInfor.get("companyId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffID = (String) personalId;
            }
            if (StringUtils.isNotEmpty(menuId)) {
                MapSesssionUtil.saveSessionForObject("menuId", menuId);
            } else {
                menuId = (String) MapSesssionUtil.getSessionForObject("menuId");
            }
            //2.将列表信息拼接成DetachedCriteria类
//            DetachedCriteria dc = payBudgetService.getBudgetBillSent(staffID, companyId, search, searchType);

            //3.获取数据信息
//            StringBuilder sql = new StringBuilder();
//            List<Object> params = new ArrayList<>();
//            getBudgetBillSent(staffID, companyId, search, searchType, sql, params, billsType);
//            pageForm = baseBeanService.getPageForm(
//                    (null != pageForm ? pageForm.getPageNumber() : 1),
//                    (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) "
//                            + sql.substring(sql.indexOf("from")), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "projectBillSentList";
    }

    public String toBusinessTypeSearch() {
        return "businessTypeSearch";
    }

    public String toGoodsSearch() {
        return "goodsSearch";
    }

    /**
     * GET AND SET METHOD
     */
    public CashierBills getCashierBills() {
        return cashierBills;
    }

    public void setCashierBills(CashierBills cashierBills) {
        this.cashierBills = cashierBills;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public boolean isShowFlag() {
        return showFlag;
    }

    public void setShowFlag(boolean showFlag) {
        this.showFlag = showFlag;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Map<String, Object> getParmaInfor() {
        return parmaInfor;
    }

    public void setParmaInfor(Map<String, Object> parmaInfor) {
        this.parmaInfor = parmaInfor;
    }

    public GoodsManage getGoodsManage() {
        return goodsManage;
    }

    public void setGoodsManage(GoodsManage goodsManage) {
        this.goodsManage = goodsManage;
    }

    public PayBudgetAddBean getAddBean() {
        return addBean;
    }

    public void setAddBean(PayBudgetAddBean addBean) {
        this.addBean = addBean;
    }

    public Map<String, Object> getScanningMap() {
        return scanningMap;
    }

    public void setScanningMap(Map<String, Object> scanningMap) {
        this.scanningMap = scanningMap;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getCashierBillsId() {
        return cashierBillsId;
    }

    public void setCashierBillsId(String cashierBillsId) {
        this.cashierBillsId = cashierBillsId;
    }

    public List<BaseBean> getGoodBeanslist() {
        return goodBeanslist;
    }

    public void setGoodBeanslist(List<BaseBean> goodBeanslist) {
        this.goodBeanslist = goodBeanslist;
    }

    public String getDelGoodsBillsIds() {
        return delGoodsBillsIds;
    }

    public void setDelGoodsBillsIds(String delGoodsBillsIds) {
        this.delGoodsBillsIds = delGoodsBillsIds;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getFbJumpType() {
        return fbJumpType;
    }

    public void setFbJumpType(int fbJumpType) {
        this.fbJumpType = fbJumpType;
    }

    public boolean isFbJumpFlag() {
        return fbJumpFlag;
    }

    public void setFbJumpFlag(boolean fbJumpFlag) {
        this.fbJumpFlag = fbJumpFlag;
    }

    public CostBudgetAddBean getCostAddBean() {
        return costAddBean;
    }

    public void setCostAddBean(CostBudgetAddBean costAddBean) {
        this.costAddBean = costAddBean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public Map<String, Object> getScanGoodsMap() {
        return scanGoodsMap;
    }

    public void setScanGoodsMap(Map<String, Object> scanGoodsMap) {
        this.scanGoodsMap = scanGoodsMap;
    }

    public String[] getFunctionList() {
        return functionList;
    }

    public void setFunctionList(String[] functionList) {
        this.functionList = functionList;
    }


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public File[] getPic() {
        return pic;
    }

    public void setPic(File[] pic) {
        this.pic = pic;
    }

    public String[] getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String[] picFileName) {
        this.picFileName = picFileName;
    }

    public GoodsBills getGoodsBills() {
        return goodsBills;
    }

    public void setGoodsBills(GoodsBills goodsBills) {
        this.goodsBills = goodsBills;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public File getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(File fileLogo) {
        this.fileLogo = fileLogo;
    }

    public List<BaseBean> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<BaseBean> orgList) {
        this.orgList = orgList;
    }

    public Map<String, Object> getCacheGoodsMap() {
        return cacheGoodsMap;
    }

    public void setCacheGoodsMap(Map<String, Object> cacheGoodsMap) {
        this.cacheGoodsMap = cacheGoodsMap;
    }

    public CostBudgetBaseBean getCostBaseBean() {
        return costBaseBean;
    }

    public void setCostBaseBean(CostBudgetBaseBean costBaseBean) {
        this.costBaseBean = costBaseBean;
    }

    public Map<String, String> getPurpose() {
        return purpose;
    }

    public void setPurpose(Map<String, String> purpose) {
        this.purpose = purpose;
    }

    public String getTenantFlag() {
        return tenantFlag;
    }

    public void setTenantFlag(String tenantFlag) {
        this.tenantFlag = tenantFlag;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(String approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getBillsTypeName() {
        return billsTypeName;
    }

    public void setBillsTypeName(String billsTypeName) {
        this.billsTypeName = billsTypeName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<BaseBean> getInvList() {
        return invList;
    }

    public void setInvList(List<BaseBean> invList) {
        this.invList = invList;
    }

    public BusinessTypeRecent getBusinessTypeRecent() {
        return businessTypeRecent;
    }

    public void setBusinessTypeRecent(BusinessTypeRecent businessTypeRecent) {
        this.businessTypeRecent = businessTypeRecent;
    }

    public GoodsBillsItemRecent getGoodsRecent() {
        return goodsRecent;
    }

    public void setGoodsRecent(GoodsBillsItemRecent goodsRecent) {
        this.goodsRecent = goodsRecent;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
