package hy.ea.invoicing.action;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.InvtFbCheck;
import hy.ea.dao.CCodeDao;
import hy.ea.finance.action.BenDis.hy_jinbiAction;
import hy.ea.invoicing.service.CheckInvService;
import hy.ea.service.CCodeService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bean.checkInv.CheckInvAddBean;
import mobile.tiantai.android.bean.checkInv.GoodListAddBean;
import mobile.tiantai.android.bean.payBudget.PayBudgetAddBean;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019-10-22.
 */
@Controller
@Scope("prototype")
public class CheckInvAction {
	private static final Logger logger = LoggerFactory.getLogger(CheckInvAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private CheckInvService checkinvService;
    @Resource
    private CCodeService codeService;
    private Logger logger = LoggerFactory.getLogger(CheckInvAction.class);
    private PageForm pageForm;//分页结果集
    private int pageNumber;//第几页
    private String search;//模糊查询条件
    private int searchType;//模糊查询条件类型
    private String result;//异步返回结果
    private String departmentID;//部门id
    private String departmentName = "-1";//部门名称
    private boolean showFlag = false;//false查看总列表true查看分列表
    private String billID;//单据编号
    private Staff staff;//人力资源
    private String companyid;//公司id手机端传过来的
    private String staffId;//staff表员工id手机端传过来的
    private String companyName;//公司名称
    private Map<String, Object> parmaInfor;//参数信息
    private GoodsManage goodsManage;//货物信息表
    private CheckInvAddBean addBean;//添加盘库单参数bean
    private Map<String, Object> scanningMap;//添加盘库单传入session中的checkinvAddBean数据
    private String mapKey;//sessiong中map的key值
    private String fbillid;//盘库单id
    private List<BaseBean> goodBeanslist;//货物集合
    private String delGoodsBillsIds;//修改页面删除已保存的货物id数组
    private int fbJumpType = 0;//发布跳转标识0：发布页；1：已发布页；2：未发布页
    private boolean fbJumpFlag = false;//标识发布页面是否显示发布选项卡
    private String orgid;
    private String staffname;//盘库人名
    private List<DepotManage> depots;
    private String depotID;
    private String warehouse;
    private String depotName;
    private List<CCode> typelist;
    private String childrenID;
    private List<BaseBean> children;
    private List<BaseBean> depotManagelist;
    private String barcode;//扫码枪的编码
    private Inventory inventory;//价格和库存集合
    private ProductPackaging productPackaging;//产品对象
    private List<BaseBean> goodsList;
    private String depotNum;
    private String depotId;
    private int showStyle;
    private double countPrice;
    private String goodsId;
    private String ppid;
    private String goodsID;
    private InvCheckGoods invCheckGoods;

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public InvCheckGoods getInvCheckGoods() {
        return invCheckGoods;
    }

    public void setInvCheckGoods(InvCheckGoods invCheckGoods) {
        this.invCheckGoods = invCheckGoods;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }


    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    private InvtFbCheck invtFbCheck;
    private String delcheckinvIds;//修改页面删除已保存的货物id数组

    /*列表展示*/
    public String getCheckInvList() {
        try {
            //保存信息
            //判断是否存在登录信息，如果没有则保存信息
//           staffId = "cstaff20110712KAX2RHUQZI0000025385";//测试用
            HttpSession session = ServletActionContext.getRequest().getSession();
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            //传过来的参数不为空
            if (StringHelper.isNotEmpty(companyid) && StringHelper.isNotEmpty(staffId)) {
                MapSesssionUtil.removeSession("paramMap");
                Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(companyid, staffId);
                MapSesssionUtil.saveSessionForMap("paramMap", map);
            } else {
                staffId = parmaInfor.get("staffId").toString();
                companyid = parmaInfor.get("companyId").toString();
            }
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
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
            //1.清除session中扫描bean信息
            MapSesssionUtil.removeSession("scanningMap");
            MapSesssionUtil.removeSession("backMap");
            //查询页面的字段
            pageForm = checkinvService.getCheckInvList(companyid, staffId, orgid, null != pageForm ? pageForm.getPageNumber() : 1, showFlag);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "checkInvList";
    }

    /**
     * 添加盘库单
     */
    public String toAddCheckInv() {
        //一级菜单
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            // 1.获取session数据
            parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            // 2.根据当前登录人公司id查询单据编号
            billID = serverService.getBillID(parmaInfor.get("companyId").toString());
            // 3.查询责任人信息
            String hqlForMan = "from Staff where staffID = ?";
            staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan, new Object[]{parmaInfor.get("staffId").toString()});
            // 4.根据手机端传过来的保存到session中的数据查询状态为正常的公司数据
            String sql = " from Company o where o.companyID = ? and o.companyStatus = '00'";
            Company company = (Company) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{parmaInfor.get("companyId").toString()});
            companyName = company != null ? company.getCompanyName() : "";
            scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
            if (scanningMap == null) {
                scanningMap = new HashMap<String, Object>();
                MapSesssionUtil.saveSessionForMap("scanningMap", scanningMap);
            } else {
                addBean = (CheckInvAddBean) scanningMap.get("invAddBean");
                //判断所选仓库是否为空
                if (depotName != null) {
                    addBean.setDepotName(depotName);
                    addBean.setWarehouse(depotId);
                    scanningMap.put("invAddBean", addBean);
                    scanningMap.put("depotName", depotName);
                }
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toAddCheckInv";
    }

    /**
     * 扫描获取产品信息
     *
     * @return
     */
    public String scanningInventoryInfo() {
        try {
            barcode = addBean.getBarcode();
            companyid = addBean.getCompanyId();
            goodsId = addBean.getGoodsId();
            ppid = addBean.getPpId();
            if (StringHelper.isNotEmpty(goodsId)) {
                productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID = ? and goodsID = ?", new Object[]{ppid, goodsId});
                inventory = new Inventory();
                inventory.setGoodsID(productPackaging.getGoodsID());
                inventory.setGoodsName(productPackaging.getGoodsName());//货物品名名称
                inventory.setUnitPrice(productPackaging.getPrice());
                inventory.setInvenQuantity(productPackaging.getQuantity());
            }
            if (StringHelper.isNotEmpty(barcode)) {
                warehouse = addBean.getWarehouse();
                String i = "select i.goodsid,g.goodsname,i.unitprice,i.invenquantity" +
                        " from  dt_inv_invt i,dtgoodsmanage g where i.goodsID = g.goodsID and g.goodsState=? and g.barCode = ?" +
                        " and i.warehouse = ? and i.companyID = ? and ROWNUM=1 ";
                Object inv = baseBeanService.getObjectBySqlAndParams(i, new Object[]{"00", barcode, warehouse, companyid});
                if (inv != null) {
                    Object[] invs = (Object[]) inv;
                    if (invs != null && invs.length > 0) {
                        inventory = new Inventory();
                        inventory.setGoodsID(invs[0].toString());//货物id
                        inventory.setGoodsName(invs[1].toString());//货物品名名称
                        inventory.setUnitPrice(invs[2].toString());//商品单价
                        inventory.setInvenQuantity(invs[3].toString());//商品库存数量
                    }
                }
            }
            if (inventory != null) {
                JSONObject obj = JSONObject.fromObject(inventory);
                result = obj.toString();
                checkinvService.splicingAddBean(inventory, addBean);
            } else {
                result = null;
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }


    /*盘库仓库列表*/
    public String depot() {
        scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
        CheckInvAddBean addBeanzz = (CheckInvAddBean) scanningMap.get("invAddBean");
        if (addBeanzz == null) {
            scanningMap.put("invAddBean", addBean);
        } else {
            addBeanzz.setDepartmentName(addBean.getDepartmentName());
            scanningMap.put("invAddBean", addBeanzz);
        }
        return "tree";
    }

    /**
     * 根据companyID和depotID查询其子节点
     */
    public String getListDepotmanageByPID() {
        // 调拨出库里面根据选择的公司来选择对应仓库
        parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        Object companyID = parmaInfor.get("companyId");
        if (companyID == null || "".equals(companyID)) {
            companyID = parmaInfor.get("companyid");
        }
        String hql = " from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
        Object[] params = {depotID, companyID};
        depotManagelist = baseBeanService.getListBeanByHqlAndParams(hql, params);
        return "deopt";
    }

    /**
     * 保存盘库单
     *
     * @return
     */
    public String saveCostSheet() {
        try {
            checkinvService.saveCostSheet(invtFbCheck, addBean);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return getCheckInvList();
    }


    /*
    * 跳转到详情
    * */
    public String toDetail() {
        try {
            //1.根据公司id和扫描的条形码号查询货物信息
            invtFbCheck = (InvtFbCheck) baseBeanService.getBeanByHqlAndParams("from InvtFbCheck o where o.fbillid = ? ", new Object[]{fbillid});
            if (invtFbCheck != null) {
                // 项目盘库单中的物品
                String hql = "  from InvCheckGoods where  fbillid =?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{fbillid});
                for (BaseBean baseBean : goodBeanslist) {
                    InvCheckGoods goods = (InvCheckGoods)baseBean;
                    goods.setError(Integer.parseInt(goods.getInvenQuantity()) - Integer.parseInt(goods.getRealQuantity()));
                    countPrice += Double.parseDouble(goods.getPrice()) * Integer.parseInt(goods.getRealQuantity());
                }
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toDetail";
    }

    /**
     * 异步根据部门id查询部门下在职员工信息
     *
     * @return
     */
    public String ajaxStaffForDep() {
        try {
            result = checkinvService.ajaxStaffForDep(departmentID);
        } catch (Exception e) {
            logger.error("操作异常", e);
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
            checkinvService.removeBeanForKey(mapKey);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    /**
     * 跳转修改盘库单
     *
     * @return
     */
    public String toUpCheckInv() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            //1.根据公司id和扫描的条形码号查询货物信息
            invtFbCheck = (InvtFbCheck) baseBeanService.getBeanByHqlAndParams("from InvtFbCheck o where o.fbillid = ? ", new Object[]{fbillid});
            if (invtFbCheck != null) {
                // 项目盘库单中的物品
                String hql = "  from InvCheckGoods where  fbillid =?";
                goodBeanslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{fbillid});
                if (goodBeanslist != null && goodBeanslist.size() != 0) {
                    for (BaseBean baseBean : goodBeanslist) {
                        InvCheckGoods goods = (InvCheckGoods) baseBean;
                        countPrice += Double.parseDouble(goods.getRealQuantity()) * Double.parseDouble(goods.getPrice());
                        goods.setError(Integer.parseInt(goods.getInvenQuantity()) - Integer.parseInt(goods.getRealQuantity()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toUpCheckInv";
    }

    /**
     * 修改盘库单信息
     *
     * @return
     */
    public String upCostSheet() {
        try {
            checkinvService.upCostSheet(invtFbCheck);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        getCheckInvList();
        return "checkInvList";
    }

    /**
     * 删除项目预算
     *
     * @return
     */
    public String delCostSheet() {
        try {
            checkinvService.removeBeanForKey(fbillid);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return getCheckInvList();
    }
    public String delGoods()
    {
        if ((this.goodsId != null) && (this.goodsId != ""))
        {
            Map<String, Object> map = MapSesssionUtil.getSessionForMap("scanningMap");
            CheckInvAddBean checkInvAddBean = (CheckInvAddBean)map.get("invAddBean");
            List<GoodListAddBean> goodList = checkInvAddBean.getGoodListAddBean();
            if ((goodList != null) && (goodList.size() != 0)) {
                for (int i = goodList.size() - 1; i >= 0; i--)
                {
                    GoodListAddBean goods = (GoodListAddBean)goodList.get(i);
                    if (goods != null)
                    {
                        String id = goods.getGoodsId();
                        if ((StringUtil.isNotEmpty(id)) &&
                                (id.equals(this.goodsId)))
                        {
                            Double goodsTotal = Double.valueOf(0.0D);
                            goodsTotal = Double.valueOf(checkInvAddBean.getTotal() - Double.parseDouble(goods.getUnitPrice()) * Integer.parseInt(goods.getRealQuantity() == null ? "0" : goods.getRealQuantity()));
                            checkInvAddBean.setTotal(goodsTotal.doubleValue());
                            goodList.remove(i);
                            checkInvAddBean.getDepotNum().remove(i);
                            break;
                        }
                    }
                }
            }
            JSONArray obj = JSONArray.fromObject(goodList);
            this.result = obj.toString();
        }
        return "success";
    }

    public String delUpdateGoods()
    {
        try
        {
            this.checkinvService.removeGoods(goodsID,fbillid);
        }
        catch (Exception e)
        {
            logger.error("操作异常", e);
        }
        return toUpCheckInv();
    }

    public InvtFbCheck getInvtFbCheck() {
        return invtFbCheck;
    }

    public void setInvtFbCheck(InvtFbCheck invtFbCheck) {
        this.invtFbCheck = invtFbCheck;
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

    public String getDepotNum() {
        return depotNum;
    }

    public void setDepotNum(String depotNum) {
        this.depotNum = depotNum;
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

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
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

    public CheckInvAddBean getAddBean() {
        return addBean;
    }

    public void setAddBean(CheckInvAddBean addBean) {
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

    public List<DepotManage> getDepots() {
        return depots;
    }

    public void setDepots(List<DepotManage> depots) {
        this.depots = depots;
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

    public String getDepotID() {
        return depotID;
    }

    public void setDepotID(String depotID) {
        this.depotID = depotID;
    }

    public List<CCode> getTypelist() {
        return typelist;
    }

    public void setTypelist(List<CCode> typelist) {
        this.typelist = typelist;
    }

    public String getChildrenID() {
        return childrenID;
    }

    public void setChildrenID(String childrenID) {
        this.childrenID = childrenID;
    }

    public List<BaseBean> getChildren() {
        return children;
    }

    public void setChildren(List<BaseBean> children) {
        this.children = children;
    }

    public List<BaseBean> getDepotManagelist() {
        return depotManagelist;
    }

    public void setDepotManagelist(List<BaseBean> depotManagelist) {
        this.depotManagelist = depotManagelist;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<BaseBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<BaseBean> goodsList) {
        this.goodsList = goodsList;
    }

    public String getFbillid() {
        return fbillid;
    }

    public void setFbillid(String fbillid) {
        this.fbillid = fbillid;
    }

    public String getDelcheckinvIds() {
        return delcheckinvIds;
    }

    public void setDelcheckinvIds(String delcheckinvIds) {
        this.delcheckinvIds = delcheckinvIds;
    }

    public double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }

    public int getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(int showStyle) {
        this.showStyle = showStyle;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }
}
