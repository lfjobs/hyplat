package hy.ea.finance.action.BenDis;

import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.RefundAddress;
import hy.ea.bo.finance.BenDis.RefundSheet;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alipay.util.AlipayNotify;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * 退款单管理
 */
@Controller
@Scope("prototype")
public class RefundSheetAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private ServerService serverService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private UpLoadFileService fileService;

    private InputStream excelStream;

    private PageForm pageForm;

    private String result;

    private int pageNumber;
    private String search;

    private RefundSheet refundSheet;

    private String id;

    private List<BaseBean> list;
    private String voptype;// 打印or查看

    private String state;

    private CashierBills cashierBills;

    private String refund;
    private File file; // 文件
    private String fileFileName; // 文件名
    private String filePath; // 文件路径

    private String fiveClear;

    private RefundSheet rs;

    private String stype;// 汇总类型

    private String type;

    private String photo;// 产品图片

    private ProductPackaging productPackaging; // 产品

    private StaffAddress staffAddress;// 退货地址

    private String params;

    private RefundAddress refundAddress;

    private String companyName;


    private String companyId;
    private Company company;

    private String param3;//收货人
    private String param1;//电话号码
    private String param2;//所在地区
    private String param4;//街道
    private String key;
    private String user;
    private String role;//买家 卖家
    private List<BaseBean> entityList;
    private TEshopCusCom tEshopCusCom;
    private String status;
    private String ppid;
    private String staid;

    /***
     * 收货单列表
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public String getRefundSheetList() {
        List<String> para = new ArrayList<String>();
        if (type != null && type != "" && type.equals("mobile")) {
            String hql1 = " from TEshopCusCom where account = ? and logOff=0";
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{user});
            String sql = "select c.cashierbillsid, p.productCode, c.refundMoney, p.standard,p.image,c.refundstate,c.rsid,c.refundNum"
                    + " from  DTREFUNDSHEET c "
                    + " join DT_PRODUCTPACKAGING p on p.ppid=c.ppid where";
            if (role.equals("buyer")) {
                //卖家
                sql += " c.companyID = ?";
                para.add(tc.getCompanyId());
            } else {
                //买家
                sql += " c.userAccount = ?";
                para.add(tc.getAccount());
            }
            if (params != null && params != "") {
                params = params.trim();
                sql += "  and (c.refundCode like ? or p.goodsname like ? or c.refundDate like ?)";
                para.add("%" + params + "%");
                para.add("%" + params + "%");
                para.add("%" + params + "%");

            }

            list = baseBeanService.getListBeanBySqlAndParams(sql, para.toArray());

            return "mobileRefundList";
        } else {
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 10 : pageNumber), getLists());

            return "list";
        }

    }

    private DetachedCriteria getLists() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        DetachedCriteria dc = DetachedCriteria.forClass(RefundSheet.class, "p");
        dc.addOrder(Order.desc("refundDate"));

        if (stype.equals("01")) {
            // 供应商汇总
            dc.add(Restrictions.eq("companyID", account.getCompanyID()));

        } else if (stype.equals("02")) {
            // 全部汇总
        }

        if (search != null && "search".equals(search)) {
            refundSheet = (RefundSheet) session.get("tablesearch");
            if (refundSheet.getOrderCode() != null
                    && !refundSheet.getOrderCode().equals("")) {
                dc.add(Restrictions.eq("orderCode", refundSheet.getOrderCode()));
            }
            if (refundSheet.getUserAccount() != null
                    && !refundSheet.getUserAccount().equals("")) {
                dc.add(Restrictions.like("userAccount",
                        refundSheet.getUserAccount(), MatchMode.ANYWHERE));
            }

        }
        return dc;
    }

    /**
     * 搜索
     * @return
     * @throws ParseException
     */
    public String toSearch() throws ParseException {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", refundSheet);
        return getRefundSheetList();
    }

    /**
     * 获取查看退货单详情以及打印页面
     * @return
     */
    public String getEditOrPrintPage() {

        if (type.equals("mobile") && type != null) {
            HttpServletRequest request = ServletActionContext.getRequest();
            String hql = " from CashierBills  where cashierBillsID = ?";
            cashierBills = (CashierBills) baseBeanService
                    .getBeanByHqlAndParams(hql, new Object[]{id});
            String hql2 = " from StaffAddress where addressID= ?";
            StaffAddress staffAddress = (StaffAddress) baseBeanService
                    .getBeanByHqlAndParams(hql2,
                            new Object[]{cashierBills.getStaffAddress()});
            String hql1 = " from RefundSheet where cashierBillsID = ?";
            request.setAttribute("staffAddress", staffAddress);
            refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                    hql1, new Object[]{id});

            return "mobileright";

        }

        if (refundSheet != null && refundSheet.getRsid() != null
                && !refundSheet.getRsid().equals("")) {
            String hql = "from RefundSheet where rsid= ?";

            refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                    hql, new Object[]{refundSheet.getRsid()});

            String hqlproduct = "from GoodsBills where cashierBillsID = ? ";
            list = baseBeanService.getListBeanByHqlAndParams(hqlproduct,
                    new Object[]{refundSheet.getCashierBillsID()});
        }

        if (voptype != null && voptype.equals("e")) {
            return "seepage";

        } else {
            return "printprev";
        }

    }

    /**
     * 上传凭证
     * @return
     */
    public String uploadFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyID = request.getParameter("companyID");
        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        try {
            filePath = fileService
                    .savePhoto(
                            path,
                            fileFileName,
                            file,
                            companyID,
                            "gooddesign/"
                                    + Utilities.getDateString(new Date(),
                                    "yyyy-MM-dd"));
        } catch (Exception e) {
            System.out.println("保存上传图片失败");
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("filePath", filePath);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 导出
     * @return
     */
    public String showExcel() {
        excelStream = excelService.showExcel(RefundSheet.columnHeadings(),
                baseBeanService.getListByDC(getLists()));
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        CLogBook cLogBook = logBookService.saveCLogBook(null, "导出收货单", account);
        baseBeanService.update(cLogBook);
        return "showexcel";
    }

    /**
     * 同意或者拒绝买家退款并退货
     * @return
     * @throws ParseException
     */
    public String approveOrReject() throws ParseException {
        List<BaseBean> list = new ArrayList<BaseBean>();
        String hql = "from RefundSheet where rsid = ?";
        String Hql = " from CashierBills where cashierBillsID = ?";
        RefundSheet rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{refundSheet.getRsid()});
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
                Hql, new Object[]{rs.getCashierBillsID()});
        if (refundSheet.getRefundstate().equals("01")) {
            // 同意买家退款并退货 需要保存提供给买家的退货地址等信息
            rs.setRefundAddress(refundSheet.getRefundAddress());
            rs.setPostcode(refundSheet.getPostcode());
            rs.setReceiverID(refundSheet.getReceiverID());
            rs.setReceiverName(refundSheet.getReceiverName());
            rs.setReceiverTel(refundSheet.getReceiverTel());
            rs.setWarehouseCode(refundSheet.getWarehouseCode());// 选择仓库，不需要向用户展示
            cashierBills.setFkStatus("06");
            list.add(cashierBills);

        }
        rs.setRemark(refundSheet.getRemark());
        rs.setDealDate(new Date());// 同意时间
        rs.setRefundstate(refundSheet.getRefundstate());
        list.add(rs);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
        return "success";

    }

    /**
     * 手机端同意或者拒绝买家退款并退货
     * @return
     * @throws ParseException
     */
    public String mobileApproveOrReject() throws ParseException {
        List<BaseBean> list = new ArrayList<BaseBean>();
        String hql = "from RefundSheet where rsid = ?";
        String Hql = " from CashierBills where cashierBillsID = ?";
        RefundSheet rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{refundSheet.getRsid()});
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
                Hql, new Object[]{rs.getCashierBillsID()});
        if (refundSheet.getRefundstate().equals("01")) {
            // 同意买家退款并退货 需要保存提供给买家的退货地址等信息
            rs.setReceiverName(param3);
            rs.setReceiverTel(param1);
            rs.setRefundAddress(param2);
            rs.setRefundstate("03");
            cashierBills.setFkStatus("06");
            list.add(cashierBills);
        }
        if (refundSheet.getRefundstate().equals("07")) {
            // 同意买家售后， 需要保存提供给买家的退货地址等信息
            rs.setReceiverName(param3);
            rs.setReceiverTel(param1);
            rs.setRefundAddress(param2);
            rs.setRefundstate("08");
            cashierBills.setFkStatus("11");
            list.add(cashierBills);
        }
        rs.setDealDate(new Date());// 同意时间

        list.add(rs);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
        return "success";

    }

    public String refundDefault() {
        return "refundDefault";
    }

    /**
     * 卖家退货申请单
     */
    public String mobileRight() {
        String hql = "from RefundSheet where cashierBillsID = ?";

        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql,
                new Object[]{id});
        return "mobileright";
    }

    /**
     * 同意或者退款页面
     * @return
     */
    public String approveOrejectPage() {

        String hql = "from RefundSheet where rsid = ?";

        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql,
                new Object[]{refundSheet.getRsid()});
        id = refundSheet.getCashierBillsID();
        String sql = "select p.manualurl from DTGOODSBILLS t join DT_PRODUCTPACKAGING p on t.ppid=p.ppid where t.cashierbillsid= ?";
        productPackaging = (ProductPackaging) baseBeanService
                .getObjectBySqlAndParams(sql,
                        new Object[]{refundSheet.getCashierBillsID()});
        if (type.equals("mobile") && type != "") {
            if (!state.equals("11")) {
                String hql1 = " from RefundAddress where companyId = ? and status='00' and rtype='0'";
                refundAddress = (RefundAddress) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{refundSheet.getCompanyID()});
            }

            if (state.equals("00")) {

                refundSheet.setRefundstate("01");
            } else {
                refundSheet.setRefundstate("07");
            }
            return "mobileAgree";
        }

        if (state.equals("01")) {

            return "approvepage";
        } else {
            return "rejectpage";
        }

    }

    /**
     * 卖家修改保存退货地址
     * @return
     */
    public String refundAddress() {


        //修改退后地址状态为00默认的所有
        String sql = "Update DTREFUNDADDRESS t set t.status='01' where t.status='00' and t.rtype='0'";

        baseBeanService.executeSqlsByParmsList(null, new String[]{sql}, null);
        //保存默认退货地址
        String Sql = "UPDATE DTREFUNDADDRESS t set t.status='00' WHERE t.raddressKey=? and t.rtype='0'";
        List<Object> params = new ArrayList<Object>();
        params.add(key);
        baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{Sql}, params.toArray());
        return "success";
    }

    /**
     * 卖家查询退货地址
     * @return
     */
    public String refundAddressList() {
        //默认地址
        String hql = " from RefundAddress where companyId = ? and status='00' and rtype='0'";
        refundAddress = (RefundAddress) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId});
        //普通地址
        String hql1 = " from RefundAddress where companyId = ? and status='01' and rtype='0'";
        list = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{companyId});

        return "mobileAddress";
    }

    /**
     * 卖家生成退货地址
     * @return
     */
    public String addAddress() {

        return "addAddress";
    }

    public String saveAddress() {
        if (refundAddress.getRaddressId() == null) {
            refundAddress.setRaddressId(serverService.getServerID("rAddress"));

            refundAddress.setCompanyId(companyId);
            refundAddress.setStatus("01");  //新添地址为其他地址
            refundAddress.setRtype(0);

        }
        baseBeanService.save(refundAddress);
        return "success";

    }

    /**
	 * 添加或修改收货地址
	 */
    public String RefundAddressAddOrEdit(){
        String raddressId=refundAddress.getRaddressId();
        RefundAddress add=(RefundAddress)baseBeanService.getBeanByHqlAndParams("from RefundAddress where raddressId=?",new Object[]{raddressId});
        if(add==null){
            add=new RefundAddress();
            add.setRaddressId(serverService.getServerID("StaffAddress"));
            add.setCompanyId(refundAddress.getCompanyId());

            List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams("from RefundAddress where companyId=?", new Object[]{refundAddress.getCompanyId()});
            add.setStatus(list.size()>0?"00":"01");
        }
        add.setRtype(refundAddress.getRtype());
        add.setRstreet(refundAddress.getRstreet());
        add.setRarea(refundAddress.getRarea());
        add.setRname(refundAddress.getRname());
        add.setRphone(refundAddress.getRphone());
        baseBeanService.saveOrUpdate(add);
        return "success";
    }

    /**
     * 申请售后页面
     * @return
     */
    public String afterSale() {
        String hql = " from ProductPackaging where ppID = ?";

        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{ppid});
        return "afterSale";

    }

    /**
     * 退货详情
     * @return
     */
    public String AfterRefundDetail() {

        String hql = " from CashierBills where cashierBillsID = ?";
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
        String hql1 = " from ProductPackaging where ppID=?";
        productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{ppid});


        return "afterRefundDetail";
    }

    /**
     * 售后详情
     * @return
     */
    public String AfterSaleDetail() {
        String hql = " ";
        return "afterSaleDetail";
    }

    /**
     * 申请售后
     *
     * @return
     */
    public String applyAfterSale() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<BaseBean> list = new ArrayList<BaseBean>();

        //String groupCompanySn=session.get("groupCompanySn").toString();
        String hql = " from CashierBills where cashierBillsID = ?";
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
        String hql1 = " from TEshopCusCom where staffid = ?";
        tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{staid});
        status = cashierBills.getFkStatus();
        if (cashierBills.getFkStatus() != "" && cashierBills.getFkStatus().equals("03")) {
            refundSheet.setRefundCode(serverService.getBillID(cashierBills.getCompanyID()));
            refundSheet.setRsid(serverService.getServerID("rsid"));
            refundSheet.setCashierBillsID(id);
            refundSheet.setPpid(ppid);
            refundSheet.setUserAccount(tEshopCusCom.getAccount());
            refundSheet.setOrderCode(cashierBills.getjNumOrder());
            refundSheet.setOrderDate(cashierBills.getCashierDate());
            refundSheet.setCompanyID(tEshopCusCom.getCompanyId());
            refundSheet.setCompanyName(tEshopCusCom.getPseudoCompanyName());
            refundSheet.setRefundDate(new Date());
            refundSheet.setRefundstate("06");
            refundSheet.setUserName(cashierBills.getStaffName());
            //refundSheet.setVipType(cashierBills.getWfthytpe());
            cashierBills.setFkStatus("10");//申请换货维修
            list.add(refundSheet);
        }
        //添加运单信息
        if (cashierBills.getFkStatus() != "" && cashierBills.getFkStatus().equals("11")) {
            String hql2 = " from RefundSheet where cashierBillsID = ?";
            rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{id});
            rs.setExpress(refundSheet.getExpress());
            rs.setWaybillno(refundSheet.getWaybillno());
            cashierBills.setFkStatus("12");
            rs.setRefundstate("08");
            list.add(rs);
         /*
          * 入库单据
		  */
            //单据表
            CashierBills ca = new CashierBills();
            ca.setCashierBillsID(serverService.getServerID("cashierTally"));
            ca.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
            //ca.setGroupCompanySn((session.get("groupCompanySn").toString()));
            ca.setCompanyID(tEshopCusCom.getCompanyId());
            //ca.setCompanyName(((Company)session.get("currentcompany")).getCompanyName());

            //ca.setOrganizationID(session.get("organizationID").toString());

            ca.setCashierDate(new Date());
            ca.setBillsType("物流入库单");

            ca.setStaffID(rs.getReceiverID());        // 接收人Id
            ca.setStaffName(rs.getReceiverName());    //接收人Name
            ca.setStaffCode("");        //接收人编号
            ca.setInputid(tEshopCusCom.getStaffid());
            //ca.setInputName(tEshopCusCom.getStaff);
            ca.setStatus("15");
            list.add(ca);

            //获取库存名称为物流库的信息
            String depotHql = "from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
            DepotManage depot = (DepotManage) baseBeanService.getBeanByHqlAndParams(depotHql, new Object[]{"001", tEshopCusCom.getCompanyId(), "物流库", "00"});
            //进销存单据
            FinancialBill fin = new FinancialBill();
            fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
            fin.setCashierBillsID(ca.getCashierBillsID());
            //fin.setGroupCompanySn(session.get("groupCompanySn").toString());
            fin.setCompanyID(tEshopCusCom.getCompanyId());
            //fin.setOrganizationID(session.get("organizationID").toString());
            fin.setDepotID(depot.getDepotID());
            fin.setDepotName(depot.getDepotName());
            fin.setStaffsID(rs.getReceiverID());            //接收人Id
            fin.setStaffsName(rs.getReceiverName());      //接收人Name
            fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
            fin.setBillsDate(new Date());
            fin.setBillStaffID(tEshopCusCom.getStaffid());
            //fin.setBillStaffName(account.getStaffName());
            fin.setBillsType("物流入库单");
            list.add(fin);
			
		 
		 /*
		  * 入库动作
		  */

            //物流入库

            String hqlgb = "from GoodsBills where cashierBillsID = ? and ppID = ?";
            GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hqlgb, new Object[]{rs.getCashierBillsID(), rs.getPpid()});


            //查询每种物品的库存
            String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=? and productId=?";
            Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{tEshopCusCom.getCompanyId(), gb.getGoodsID(), depot.getDepotID(), gb.getPpID()});


            //库存表
            if (inv == null) {
                inv = new Inventory();
                inv.setInventoryID(serverService.getBillID("inventoryID"));
                inv.setCompanyID(tEshopCusCom.getCompanyId());
                //inv.setGroupCompanySn(groupCompanySn);
                inv.setWarehouse(depot.getDepotID());
                inv.setWarehouseName(depot.getDepotName());
                inv.setGoodsID(gb.getGoodsID());
                inv.setGoodsName(gb.getGoodsName());
                inv.setGoodsType(gb.getTypeID());
                inv.setStandard(gb.getStandard());
                inv.setGoodsCoding(gb.getGoodsNum());
                inv.setUnitPrice(gb.getPrice());
                inv.setProductId(gb.getPpID());//物品单价
                inv.setInvenQuantity(gb.getQuantity());    //物品数量
                inv.setSumPrice(gb.getMoney());            //总价
                inv.setGoodstatus("00");
                list.add(inv);
            } else {
                inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity()) + Integer.parseInt(gb.getQuantity()) + "");
                inv.setSumPrice(Double.parseDouble(inv.getSumPrice()) + Double.parseDouble(gb.getMoney()) + "");
                list.add(inv);
            }


            //库存盘点表
            stockInv sto = new stockInv();
            sto.setStockinvID(serverService.getServerID("stockInv"));
            sto.setCompanyID(tEshopCusCom.getCompanyId());
            //sto.setGroupCompanySn(groupCompanySn);
            sto.setGoodsBillsId(gb.getGoodsBillsID());
            sto.setGoodsID(gb.getGoodsID());
            sto.setGoodsType(gb.getTypeID());
            sto.setGoodsName(gb.getGoodsName());
            sto.setInvenQuantity(gb.getQuantity());            //物品数量
            sto.setSummoney(gb.getMoney());                //总价
            sto.setIntime(new Date());
            sto.setType("00");
            sto.setWarehouse(depot.getDepotID());
            sto.setWarehouseName(depot.getDepotName());
            list.add(sto);

            //物品单据
            GoodsBills newgoodsBill = null;
            try {
                newgoodsBill = (GoodsBills) gb.cloneGoodsBills();
            } catch (Exception e) {

                e.printStackTrace();
            }
            newgoodsBill.setGoodsBillsID(serverService.getServerID("goodsBillsID"));
            newgoodsBill.setGoodsBillsKey(null);
            newgoodsBill.setInventoryID(inv.getInventoryID());//库存ID
            newgoodsBill.setStockinvID(sto.getStockinvID());
            newgoodsBill.setCashierBillsID(ca.getCashierBillsID());
            newgoodsBill.setCostType("退货");
            newgoodsBill.setDepotID(depot.getDepotID());
            newgoodsBill.setDepotName(depot.getDepotName());
            newgoodsBill.setKcStatus("15");

            list.add(newgoodsBill);
        }


        list.add(cashierBills);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
        return "success";
    }

    /**
     * 卖家确认收货 生成入库单
     *
     * @return
     */
    public String confirmRefund() {
        List<BaseBean> list = new ArrayList<BaseBean>();
        String hql = " from RefundSheet where rsid = ?";
        RefundSheet rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{refundSheet.getRsid()});
        rs.setConfirmConsigneeDate(new Date());
        String hql1 = " from CashierBills c where c.cashierBillsID=? ";
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{rs.getCashierBillsID()});
        if (status.equals("03")) {
            rs.setRefundstate("04");
            cashierBills.setFkStatus("08");
        } else {
            rs.setRefundstate("09");
            cashierBills.setFkStatus("13");
        }
        list.add(rs);
        list.add(cashierBills);
        // 入库操作
        pushWareHouse(rs.getCashierBillsID(), rs.getWarehouseCode());
        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);


        return "success";

    }

    /**
     * 入库
     *
     * @return
     */
    public String pushWareHouse(String cashierBillsID, String wareHouseCode) {
        String hql = "from RefundSheet where rsid = ?";
        RefundSheet rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{refundSheet.getRsid()});
        rs.setRemitDate(new Date());
        //generatepushsheet(rs.getCashierBillsID(), rs.getPpid(), rs);
        generatelogpushsheet(rs.getCashierBillsID(), rs.getPpid(), rs);
        baseBeanService.update(rs);

        return null;
    }

    /*
     *
     * 生成退货入库单
     */
    public void generatepushsheet(String CashierBillsID, String ppID,
                                  RefundSheet rs) {

        Map<String, Object> session = ActionContext.getContext().getSession();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<BaseBean> list = new ArrayList<BaseBean>();
        String hql1 = " from TEshopCusCom where account = ? and logOff=0";
        tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{user});
        //String groupCompanySn = session.get("groupCompanySn").toString();

        /**
         * 生成单据
         */
        // 单据表
        CashierBills ca = new CashierBills();
        ca.setCashierBillsID(serverService.getServerID("cashierTally"));
        ca.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        //ca.setGroupCompanySn((session.get("groupCompanySn").toString()));
        ca.setCompanyID(tEshopCusCom.getCompanyId());
        //ca.setCompanyName(((Company) session.get("currentcompany"))
        //.getCompanyName());

        ca.setOrganizationID(tEshopCusCom.getOrganizationID());

        ca.setCashierDate(new Date());
        ca.setBillsType("销售退货入库单");

        ca.setStaffID(rs.getReceiverID()); // 接收人Id
        ca.setStaffName(rs.getReceiverName()); // 接收人Name
        ca.setStaffCode(""); // 接收人编号
        ca.setInputid(tEshopCusCom.getStaffid());
        //ca.setInputName(account.getStaffName());
        ca.setStatus("15");
        list.add(ca);

        // 单据关联表
        RelatedBill re = new RelatedBill();
        re.setRbID(serverService.getServerID("RelatedBill"));
        re.setCashid(ca.getCashierBillsID());
        re.setCashfid(CashierBillsID);// 订单ID
        re.setBilltype("销售退货入库单");
        list.add(re);

        // 获取库存名称为退货库的信息
        String depotHql = "from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
        DepotManage depot = (DepotManage) baseBeanService
                .getBeanByHqlAndParams(depotHql,
                        new Object[]{"001", tEshopCusCom.getCompanyId(), "退货库",
                                "00"});
        // 进销存单据
        FinancialBill fin = new FinancialBill();
        fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
        fin.setCashierBillsID(ca.getCashierBillsID());
        //fin.setGroupCompanySn(session.get("groupCompanySn").toString());
        fin.setCompanyID(tEshopCusCom.getCompanyId());
        fin.setOrganizationID(tEshopCusCom.getOrganizationID());
        fin.setDepotID(depot.getDepotID());
        fin.setDepotName(depot.getDepotName());
        fin.setStaffsID(rs.getReceiverID()); // 接收人Id
        fin.setStaffsName(rs.getReceiverName()); // 接收人Name
        fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        fin.setBillsDate(new Date());
        fin.setBillStaffID(tEshopCusCom.getStaffid());
        //fin.setBillStaffName(account.getStaffName());
        fin.setBillsType("销售退货入库单");
        list.add(fin);

        String hqlgb = "from GoodsBills where cashierBillsID = ? and ppID = ?";
        GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(
                hqlgb, new Object[]{CashierBillsID, ppID});

        String quantity = gb.getQuantity();

        // 获取该物品类型的最高序号
        String numHql = "from GoodsNum where companyID=? and goodsID=? order by goodsnumber";
        List<BaseBean> numList = baseBeanService.getListBeanByHqlAndParams(
                numHql,
                new Object[]{tEshopCusCom.getCompanyId(), gb.getGoodsID()});
        String number = "";
        if (numList.size() != 0) {
            number = Integer
                    .parseInt(((GoodsNum) numList.get(numList.size() - 1))
                            .getGoodsnumber())
                    + "";
        }

        // 查询每种物品的库存
        String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=? and productId=?";
        Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(
                invHql, new Object[]{tEshopCusCom.getCompanyId(), gb.getGoodsID(),
                        depot.getDepotID(), gb.getPpID()});

        // 每一种物品处理下
        // 库存盘点表
        stockInv sto = new stockInv();
        sto.setStockinvID(serverService.getServerID("stockInv"));
        sto.setCompanyID(tEshopCusCom.getCompanyId());
        //sto.setGroupCompanySn(groupCompanySn);
        sto.setGoodsBillsId(gb.getGoodsBillsID());
        sto.setGoodsID(gb.getGoodsID());
        sto.setGoodsType(gb.getTypeID());
        sto.setGoodsName(gb.getGoodsName());
        sto.setInvenQuantity(gb.getQuantity()); // 物品数量
        sto.setSummoney(gb.getMoney()); // 总价
        sto.setIntime(new Date());
        sto.setType("00");
        sto.setWarehouse(depot.getDepotID());
        sto.setWarehouseName(depot.getDepotName());
        list.add(sto);

        // 库存表
        if (inv == null) {
            inv = new Inventory();
            inv.setInventoryID(serverService.getBillID("inventoryID"));
            inv.setCompanyID(tEshopCusCom.getCompanyId());
            //inv.setGroupCompanySn(groupCompanySn);
            inv.setWarehouse(depot.getDepotID());
            inv.setWarehouseName(depot.getDepotName());
            inv.setGoodsID(gb.getGoodsID());
            inv.setGoodsName(gb.getGoodsName());
            inv.setGoodsType(gb.getTypeID());
            inv.setStandard(gb.getStandard());
            inv.setGoodsCoding(gb.getGoodsNum());
            inv.setProductId(gb.getPpID());
            inv.setUnitPrice(gb.getPrice()); // 物品单价
            inv.setInvenQuantity(gb.getQuantity()); // 物品数量
            inv.setSumPrice(gb.getMoney()); // 总价
            inv.setGoodstatus("00");
            list.add(inv);
        } else {
            inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())
                    + Integer.parseInt(gb.getQuantity()) + "");
            inv.setSumPrice(Double.parseDouble(inv.getSumPrice())
                    + Double.parseDouble(gb.getMoney()) + "");
            list.add(inv);
        }

        GoodsBills newgoodsBill = null;
        try {
            newgoodsBill = (GoodsBills) gb.cloneGoodsBills();
        } catch (Exception e) {

            e.printStackTrace();
        }
        newgoodsBill.setGoodsBillsID(serverService.getServerID("goodsBillsID"));
        newgoodsBill.setGoodsBillsKey(null);
        newgoodsBill.setInventoryID(inv.getInventoryID());// 库存ID
        newgoodsBill.setStockinvID(sto.getStockinvID());
        newgoodsBill.setCashierBillsID(ca.getCashierBillsID());
        newgoodsBill.setCostType("退货");
        newgoodsBill.setDepotID(depot.getDepotID());
        newgoodsBill.setDepotName(depot.getDepotName());
        newgoodsBill.setKcStatus("15");

        list.add(newgoodsBill);

        for (int i = 0; i < Integer.parseInt(quantity); i++) {

            // 物品序号保存
            GoodsNum num = new GoodsNum();
            num.setGnID(serverService.getServerID("GoodsNum"));
            num.setStatus("00");
            num.setGoodsID(gb.getGoodsID());
            num.setCompanyID(tEshopCusCom.getCompanyId());
            if ("".equals(number)) {
                num.setGoodsnumber("0000" + (i + 1));
            } else {
                String n = Integer.parseInt(number) + (i + 1) + "";
                if (n.length() < 5) {
                    for (int r = 0; r < 5 - n.length(); r++) {
                        n = "0" + n;
                    }
                }
                num.setGoodsnumber(n);
            }
            num.setGoodsCoding(gb.getGoodsNum());
            num.setGoodsBillsID(newgoodsBill.getGoodsBillsID());
            num.setCashierBillsID(inv.getInventoryID());
            list.add(num);
        }

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

    }

    /*
     *
     * 生成物流出库单
     */
    public void generatelogpushsheet(String cashierBillsID, String ppID,
                                     RefundSheet rs) {

        Map<String, Object> session = ActionContext.getContext().getSession();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<BaseBean> list = new ArrayList<BaseBean>();

        //String groupCompanySn = session.get("groupCompanySn").toString();

        String hql1 = " from TEshopCusCom where account = ? and logOff=0";
        tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{user});
        /**
         * 生成单据
         */
        // 单据表
        CashierBills ca = new CashierBills();
        ca.setCashierBillsID(serverService.getServerID("cashierTally"));
        ca.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        //ca.setGroupCompanySn((session.get("groupCompanySn").toString()));
        ca.setCompanyID(tEshopCusCom.getCompanyId());
		/*ca.setCompanyName(((Company) session.get("currentcompany"))
				.getCompanyName());*/

        ca.setOrganizationID(tEshopCusCom.getOrganizationID());

        ca.setCashierDate(new Date());
        ca.setBillsType("物流出库单");

        ca.setStaffID(rs.getReceiverID()); // 接收人Id
        ca.setStaffName(rs.getReceiverName()); // 接收人Name
        ca.setStaffCode(""); // 接收人编号
        ca.setInputid(tEshopCusCom.getStaffid());
        //ca.setInputName(account.getStaffName());
        ca.setStatus("16");
        list.add(ca);

        // 单据关联表
        RelatedBill re = new RelatedBill();
        re.setRbID(serverService.getServerID("RelatedBill"));
        re.setCashid(ca.getCashierBillsID());
        re.setCashfid(cashierBillsID);// 订单ID
        re.setBilltype("物流出库单");
        list.add(re);

        // 获取库存名称为物流库的信息
        String depotHql = "from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
        DepotManage depot = (DepotManage) baseBeanService
                .getBeanByHqlAndParams(depotHql,
                        new Object[]{"001", tEshopCusCom.getCompanyId(), "物流库",
                                "00"});
        // 进销存单据
        FinancialBill fin = new FinancialBill();
        fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
        fin.setCashierBillsID(ca.getCashierBillsID());
        //fin.setGroupCompanySn(session.get("groupCompanySn").toString());
        fin.setCompanyID(tEshopCusCom.getCompanyId());
        fin.setOrganizationID(tEshopCusCom.getOrganizationID());
        fin.setDepotID(depot.getDepotID());
        fin.setDepotName(depot.getDepotName());
        fin.setStaffsID(rs.getReceiverID()); // 接收人Id
        fin.setStaffsName(rs.getReceiverName()); // 接收人Name
        fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        fin.setBillsDate(new Date());
        fin.setBillStaffID(tEshopCusCom.getStaffid());
        //fin.setBillStaffName(account.getStaffName());
        fin.setBillsType("物流出库单");
        list.add(fin);

        String hqlgb = "from GoodsBills where cashierBillsID = ? and ppID = ?";
        GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(
                hqlgb, new Object[]{cashierBillsID, ppID});

        // 查询每种物品的库存
        String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=? and productId=?";
        Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(
                invHql, new Object[]{tEshopCusCom.getCompanyId(), gb.getGoodsID(),
                        depot.getDepotID(), gb.getPpID()});

        // 每一种物品处理下
        // 库存盘点表
        stockInv sto = new stockInv();
        sto.setStockinvID(serverService.getServerID("stockInv"));
        sto.setCompanyID(tEshopCusCom.getCompanyId());
        //sto.setGroupCompanySn(groupCompanySn);
        sto.setGoodsBillsId(gb.getGoodsBillsID());
        sto.setGoodsID(gb.getGoodsID());
        sto.setGoodsType(gb.getTypeID());
        sto.setGoodsName(gb.getGoodsName());
        sto.setInvenQuantity(gb.getQuantity()); // 物品数量
        sto.setSummoney(gb.getMoney()); // 总价
        sto.setIntime(new Date());
        sto.setType("01");
        sto.setWarehouse(depot.getDepotID());
        sto.setWarehouseName(depot.getDepotName());
        list.add(sto);

        // 库存表
        inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())
                - Integer.parseInt(gb.getQuantity()) + "");
        inv.setSumPrice(Double.parseDouble(inv.getSumPrice())
                - Double.parseDouble(gb.getMoney()) + "");
        list.add(inv);

        GoodsBills newgoodsBill = null;
        try {
            newgoodsBill = (GoodsBills) gb.cloneGoodsBills();
        } catch (Exception e) {

            e.printStackTrace();
        }
        newgoodsBill.setGoodsBillsID(serverService.getServerID("goodsBillsID"));
        newgoodsBill.setGoodsBillsKey(null);
        newgoodsBill.setInventoryID(inv.getInventoryID());// 库存ID
        newgoodsBill.setStockinvID(sto.getStockinvID());
        newgoodsBill.setCashierBillsID(ca.getCashierBillsID());
        newgoodsBill.setCostType("退货");
        newgoodsBill.setDepotID(depot.getDepotID());
        newgoodsBill.setDepotName(depot.getDepotName());
        newgoodsBill.setKcStatus("15");

        list.add(newgoodsBill);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

    }

    /**
     * 订单的产品 从“物流仓库” 中出库生成出库单
     * <p>
     * 仓库==物流
     *
     * @return
     */
    public String pullHouse() {
        return "";
    }

    /**
     * 物流出库单
     *
     * @param cashierBillsID
     * @param rs
     */
    public void generatepullsheet(String cashierBillsID, RefundSheet rs) {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<BaseBean> list = new ArrayList<BaseBean>();

        String groupCompanySn = session.get("groupCompanySn").toString();

        /**
         * 生成单据
         */
        // 单据表
        CashierBills ca = new CashierBills();
        ca.setCashierBillsID(serverService.getServerID("cashierTally"));
        ca.setJournalNum(serverService.getBillID(account.getCompanyID()));
        ca.setGroupCompanySn((session.get("groupCompanySn").toString()));
        ca.setCompanyID(account.getCompanyID());
        ca.setCompanyName(((Company) session.get("currentcompany"))
                .getCompanyName());

        ca.setOrganizationID(session.get("organizationID").toString());

        ca.setCashierDate(new Date());
        ca.setBillsType("物流出库单");

        ca.setStaffID(rs.getReceiverID()); // 接收人Id
        ca.setStaffName(rs.getReceiverName()); // 接收人Name
        ca.setStaffCode(""); // 接收人编号
        ca.setInputid(account.getStaffID());
        ca.setInputName(account.getStaffName());
        ca.setStatus("16");
        list.add(ca);

        // 单据关联表
        RelatedBill re = new RelatedBill();
        re.setRbID(serverService.getServerID("RelatedBill"));
        re.setCashid(ca.getCashierBillsID());
        re.setCashfid(cashierBillsID);// 订单ID
        re.setBilltype("物流出库单");
        list.add(re);

        // 获取库存名称为退货库的信息
        String depotHql = "from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
        DepotManage depot = (DepotManage) baseBeanService
                .getBeanByHqlAndParams(depotHql,
                        new Object[]{"001", account.getCompanyID(), "物流库",
                                "00"});
        // 进销存单据
        FinancialBill fin = new FinancialBill();
        fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
        fin.setCashierBillsID(ca.getCashierBillsID());
        //fin.setGroupCompanySn(session.get("groupCompanySn").toString());
        fin.setCompanyID(account.getCompanyID());
        fin.setOrganizationID(session.get("organizationID").toString());
        fin.setDepotID(depot.getDepotID());
        fin.setDepotName(depot.getDepotName());
        fin.setStaffsID(rs.getReceiverID()); // 接收人Id
        fin.setStaffsName(rs.getReceiverName()); // 接收人Name
        fin.setJournalNum(serverService.getBillID(account.getCompanyID()));
        fin.setBillsDate(new Date());
        fin.setBillStaffID(account.getStaffID());
        fin.setBillStaffName(account.getStaffName());
        fin.setBillsType("物流出库单");
        list.add(fin);

        String hqlgb = "from GoodsBills where cashierBillsID = ?";
        GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(
                hqlgb, new Object[]{cashierBillsID});

        // 查询每种物品的库存
        String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=?";
        Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(
                invHql, new Object[]{account.getCompanyID(), gb.getGoodsID(),
                        depot.getDepotID()});

        // 每一种物品处理下
        // 库存盘点表
        stockInv sto = new stockInv();
        sto.setStockinvID(serverService.getServerID("stockInv"));
        sto.setCompanyID(account.getCompanyID());
        sto.setGroupCompanySn(groupCompanySn);
        sto.setGoodsBillsId(gb.getGoodsBillsID());
        sto.setGoodsID(gb.getGoodsID());
        sto.setGoodsType(gb.getTypeID());
        sto.setGoodsName(gb.getGoodsName());
        sto.setInvenQuantity(gb.getQuantity()); // 物品数量
        sto.setSummoney(gb.getMoney()); // 总价
        sto.setIntime(new Date());
        sto.setType("01");
        sto.setWarehouse(depot.getDepotID());
        sto.setWarehouseName(depot.getDepotName());
        list.add(sto);

        // 库存表
        if (inv != null) {
            inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())
                    - Integer.parseInt(gb.getQuantity()) + "");
            inv.setSumPrice(Double.parseDouble(inv.getSumPrice())
                    - Double.parseDouble(gb.getMoney()) + "");
            list.add(inv);
        }

        GoodsBills newgoodsBill = null;
        try {
            newgoodsBill = (GoodsBills) gb.cloneGoodsBills();
        } catch (Exception e) {

            e.printStackTrace();
        }
        newgoodsBill.setGoodsBillsID(serverService.getServerID("goodsBillsID"));
        newgoodsBill.setInventoryID(inv.getInventoryID());// 库存ID
        newgoodsBill.setStockinvID(sto.getStockinvID());
        newgoodsBill.setCashierBillsID(ca.getCashierBillsID());
        newgoodsBill.setCostType("退货");
        newgoodsBill.setDepotID(depot.getDepotID());
        newgoodsBill.setDepotName(depot.getDepotName());
        newgoodsBill.setKcStatus("15");

        list.add(newgoodsBill);
        String hqln = "from GoodsNum where  goodsBillsID = ? ";
        GoodsNum goodnum = (GoodsNum) baseBeanService.getBeanByHqlAndParams(
                hqln, new Object[]{gb.getGoodsBillsID()});

        goodnum.setStatus("04");
        goodnum.setGoodsBillsID(newgoodsBill.getGoodsBillsID());
        list.add(goodnum);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

    }

    /**
     * 打款给买方
     *
     * @return
     */
    public String backMoneytoBuyer() {
        // HttpServletRequest request = ServletActionContext.getRequest();
        //
        // String hql = "from RefundSheet where rsid = ?";
        // RefundSheet rs = (RefundSheet)
        // baseBeanService.getBeanByHqlAndParams(hql, new
        // Object[]{refundSheet.getRsid()});
        //
        // String hqlorder = "from CashierBills where cashierBillsID = ?";
        // CashierBills cash = (CashierBills) baseBeanService
        // .getBeanByHqlAndParams(hqlorder,
        // new Object[] { rs.getCashierBillsID() });
        // String s = "";
        // if (cash.getFkStatus().equals("00")) {// 已付款
        // if (cash.getWfStatus1().equals("00")
        // || (cash.getWfStatus1().equals("01") && cash.getWfStatus2()
        // .equals("00"))) {// 在线支付
        // if (cash.getWfStatus4().equals("00")) {
        // // 微信
        //
        // WxPayDto tpWxPay = new WxPayDto();
        // tpWxPay.setOrderId(cash.getJournalNum());//商户订单号
        // tpWxPay.setRefundno(rs.getRefundCode());//退款编号
        // tpWxPay.setRefundfee(rs.getRefundMoney());//退款金额
        // tpWxPay.setTotalFee(cash.getPriceSub());//支付总金额
        // tpWxPay.setWechatbz("wxff4c5683480d6664");
        // s = WchatPay.wechatRefund(tpWxPay);
        // Map m= GetWxOrderno.parseJson(s);
        //
        // String hqlre = "from RefundSheet where journalNum = ?";
        // RefundSheet re = (RefundSheet)
        // baseBeanService.getBeanByHqlAndParams(hqlre, new
        // Object[]{m.get("out_trade_no").toString()});
        //
        // if(!re.getAccountstatus().equals("01")){
        //
        // generatePaySheet(m.get("out_trade_no").toString(),m.get("transaction_id").toString(),"10");
        // re.setAccountstatus("01");
        // re.setRemitDate(new Date());
        // baseBeanService.update(re);
        //
        // }
        // String page = "";
        // String res="";
        // if(s.indexOf("SUCCESS")!=-1){
        // res = "退款成功";
        //
        //
        // }else{
        // res = "退款失败";
        // }
        // page="<tr><td width=\"30%\" align=\"right\">退款结果</td><td>"+res+"</td></tr>";
        //
        // request.setAttribute("result", page.toString());
        //
        // return "noticebefore";
        //
        //
        // } else if (cash.getWfStatus4().equals("01")) {
        // // 支付宝
        // RefundParam rparam = new RefundParam();
        // rparam.setNotify_url("http://www.impf2010.com");
        // try {
        // rparam.setSeller_email(new
        // String((AlipayConfig.seller_email).getBytes("ISO-8859-1"),"UTF-8"));
        // String curestr = Utilities.getDateString(new Date(),
        // "yyyy-MM-dd HH:mm:ss");
        // rparam.setRefund_date(new
        // String(curestr.getBytes("ISO-8859-1"),"UTF-8"));//退款日期
        // String batch = Utilities.getDateString(new Date(),
        // "yyyyMMdd")+RandomDatas.getRandomNumber(7);
        // rparam.setBatch_no(new
        // String(batch.getBytes("ISO-8859-1"),"UTF-8"));//批次号
        // rparam.setBatch_num(new
        // String("1".getBytes("ISO-8859-1"),"UTF-8"));//退款次数
        // String cause = "你好";
        // String detail =
        // ("2015090100001000870061348099"+"^"+"0.01"+"^ss").trim();
        // System.out.println("========================"+detail);
        // rparam.setDetail_data(detail);//退款详情
        // } catch (UnsupportedEncodingException e1) {
        //
        // e1.printStackTrace();
        // }
        //
        //
        // HttpServletResponse response = ServletActionContext.getResponse();
        //
        // s = AlipayRefund.refund(rparam);
        // try {
        // response.getWriter().print(s);
        // } catch (IOException e) {
        //
        // e.printStackTrace();
        // }
        // //System.out.println(s);
        //
        // } else {
        // 银联
		/*Map<String, String> consumeUndo=unionpayMeth.consumeUndo(
						"http://localhost:8080/hyplat/ea/refund/ea_noticeUrlByYlbefore.jspa",
						"http://localhost:8080/hyplat/ea/refund/ea_noticeURLbyYL.jspa","0",
						"201511231628516433648", "3","07");*/
        // }
        //
        // }
        //
        // }

        return null;

    }

    /**
     * 支付宝通知到账
     *
     * @return
     */
    public String noticeURLbyAli() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 批次号

        try {
            // String batch_no = new String(request.getParameter("batch_no")
            // .getBytes("ISO-8859-1"), "UTF-8");

            // 批量退款数据中转账成功的笔数

            // String success_num = new
            // String(request.getParameter("success_num")
            // .getBytes("ISO-8859-1"), "UTF-8");

            // 批量退款数据中的详细信息
            // String result_details = new String(request.getParameter(
            // "result_details").getBytes("ISO-8859-1"), "UTF-8");

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            String trade_no = request.getParameter("trade_no"); // 支付宝交易号
            String order_no = request.getParameter("out_trade_no"); // 获取订单号
            // String total_fee = request.getParameter("total_fee"); // 获取总金额

            if (AlipayNotify.verify(params)) {// 验证成功
                // ////////////////////////////////////////////////////////////////////////////////////////
                // 请在这里加上商户的业务逻辑程序代码

                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                // 判断是否在商户网站中已经做过了这次通知返回的处理
                // 如果没有做过处理，那么执行商户的业务程序
                // 如果有做过处理，那么不执行商户的业务程序
                String hqlre = "from RefundSheet where journalNum = ?";
                RefundSheet re = (RefundSheet) baseBeanService
                        .getBeanByHqlAndParams(hqlre, new Object[]{order_no});
                System.out.println(1111111);
                if (!re.getAccountstatus().equals("01")) {
                    System.out.println(222222);
                    generatePaySheet(order_no, trade_no, "10");
                    re.setAccountstatus("01");
                    re.setRemitDate(new Date());
                    baseBeanService.update(re);

                }

                response.getWriter().println("success"); // 请不要修改或删除

                // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                // ////////////////////////////////////////////////////////////////////////////////////////
            } else {// 验证失败
                response.getWriter().println("fail");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }

    /**
     * 银联后台异步通知
     *
     * @return
     */
    public String noticeURLbyYL() {

        HttpServletRequest request = ServletActionContext.getRequest();

        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

        try {
            request.setCharacterEncoding("ISO-8859-1");
        } catch (UnsupportedEncodingException e2) {

            e2.printStackTrace();
        }
        String encoding = request.getParameter(SDKConstants.param_encoding);
        // 获取请求参数中所有的信息
        Map<String, String> reqParam = getAllRequestParam(request);
        // 打印请求报文
        LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                try {
                    value = new String(value.getBytes("ISO-8859-1"), encoding);
                } catch (UnsupportedEncodingException e1) {

                    e1.printStackTrace();
                }
                valideData.put(key, value);
            }
        }

        // 验证签名
        if (!SDKUtil.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            System.out.println(valideData.get("orderId")); // 其他字段也可用类似方式获取
            LogUtil.writeLog("验证签名结果[成功].");

            String hqlre = "from RefundSheet where journalNum = ?";
            RefundSheet re = (RefundSheet) baseBeanService
                    .getBeanByHqlAndParams(hqlre,
                            new Object[]{valideData.get("orderId")});
            if (!re.getAccountstatus().equals("01")) {
                generatePaySheet(valideData.get("orderId"),
                        valideData.get("origQryId"), "08");
                re.setAccountstatus("01");
                re.setRemitDate(new Date());
                baseBeanService.update(re);

            }

        }

        LogUtil.writeLog("BackRcvResponse接收后台通知结束");

        return null;

    }

    /**
     * 银联退款前台同步通知
     *
     * @return
     */
    public String noticeUrlByYlbefore() {
        HttpServletRequest request = ServletActionContext.getRequest();
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

        try {
            request.setCharacterEncoding("ISO-8859-1");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        String encoding = request.getParameter(SDKConstants.param_encoding);
        LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");

        Map<String, String> respParam = getAllRequestParam(request);

        // 打印请求报文
        LogUtil.printRequestLog(respParam);

        Map<String, String> valideData = null;
        StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                try {
                    value = new String(value.getBytes("ISO-8859-1"), encoding);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                page.append("<tr><td width=\"30%\" align=\"right\">" + key
                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!SDKUtil.validate(valideData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            LogUtil.writeLog("验证签名结果[成功].");
            System.out.println(valideData.get("orderId")); // 其他字段也可用类似方式获取

        }
        request.setAttribute("result", page.toString());

        LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");

        return "noticebefore";
    }

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                // System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 生成退款支出单
     *
     * @param journalNum 商户订单交易号
     * @param tradeNo    第三方退款交易号
     * @param appstyle   付款方式
     */
    public void generatePaySheet(String journalNum, String tradeNo,
                                 String appstyle) {

        System.out.println(33333333);
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        // 用户购买时订单
        String hql = "from CashierBills where journalNum = ?";
        CashierBills cash = (CashierBills) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{journalNum});

        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        // 退款支出单
        CashierBills cb = new CashierBills();
        cb.setCashierBillsID(serverService.getServerID("cashierTally"));
        cb.setGroupCompanySn((String) session.get("groupCompanySn")); // 集团标识
        cb.setCompanyID(account.getCompanyID());
        cb.setCompanyName(((Company) session.get("currentcompany"))
                .getCompanyName());// 当前公司name
        cb.setCashierDate(new Date());// 单据日期 下单日期
        cb.setjNumOrder(cash.getJournalNum());

        cb.setAppstyle(appstyle); // 拨款方式 08:银联退款 09：微信退款 10：支付宝退款
        cb.setStatusbill("12"); // 单据状态判断单据来源 04：用户下单
        cb.setPriceSub(cash.getPriceSub());// /退款金额/

        cb.setBillsType("退款单");// 单据类型

        cb.setJournalNum(serverService.getBillID(account.getCompanyID()));// 凭证号
        cb.setTrade_no(tradeNo);// 第三方支付交易号 //退款交易
        cb.setStaffID(cash.getStaffID());// 责任人ID
        cb.setStaffName(cash.getStaffName());// 责任人name
        cb.setStaffCode(cash.getStaffCode());// 责任人编号

        /************************** 制单人信息 ************************/
        cb.setInputid(account.getStaffID()); // 制单人员id
        cb.setInputName(account.getStaffName());// 制单人名称
        cb.setInputCompanyid(account.getCompanyID()); // 制单人公司id
        cb.setInputCompanyName(((Company) session.get("currentcompany"))
                .getCompanyName()); // 制单人公司名称

        /************** 往来个人 ****************/

        cb.setContactUserID(cash.getContactUserID());
        cb.setCtUserName(cash.getCtUserName());
        cb.setReference(cash.getReference());
        cb.setStaffIdentityCard(cash.getStaffIdentityCard());
        cb.setStaffAddress(cash.getStaffAddress());
        cb.setReferenceCode(cash.getReferenceCode());
        baseBeanList.add(cb);

        String hqlgoods = "from GoodsBills where cashierBillsID = ?";
        List<BaseBean> goodslist = baseBeanService.getListBeanByHqlAndParams(
                hqlgoods, new Object[]{cash.getCashierBillsID()});
        GoodsBills newgoods = new GoodsBills();
        for (BaseBean b : goodslist) {
            GoodsBills goods = (GoodsBills) b;
            try {
                newgoods = (GoodsBills) goods.cloneGoodsBills();
                newgoods.setGoodsBillsID(serverService
                        .getServerID("goodsBillsID"));
                newgoods.setCashierBillsID(cash.getCashierBillsID());
                baseBeanList.add(newgoods);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                null);

    }

    public String toRefundCashSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("ttablesearch", cashierBills);
        return findRefundCashList();
    }

    /**
     * 获取退款单据
     *
     * @return
     */
    public String findRefundCashList() {
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), getCashList());

        return "tocash";
    }

    private DetachedCriteria getCashList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        DetachedCriteria dc = DetachedCriteria
                .forClass(CashierBills.class, "p");
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("billsType", "退款单"));
        dc.add(Restrictions.eq("statusbill", "12"));
        if (stype.equals("01")) {
            // 供应商汇总
            DetachedCriteria cri = DetachedCriteria.forClass(
                    DtOrderBillAdd.class, "c");
            cri.add(Property.forName("c.oaBillId").eqProperty(
                    "p.cashierBillsID"));
            cri.add(Restrictions.eq("oaGysId", account.getCompanyID()));
        } else if (stype.equals("02")) {
            // 全部汇总

        }

        if (search != null && "search".equals(search)) {
            cashierBills = (CashierBills) session.get("ttablesearch");

            if (cashierBills.getJournalNum() != null
                    && !cashierBills.getJournalNum().equals("")) {
                dc.add(Restrictions.like("journalNum",
                        cashierBills.getJournalNum(), MatchMode.ANYWHERE));
            }

            if (cashierBills.getjNumOrder() != null
                    && !cashierBills.getjNumOrder().equals("")) {
                dc.add(Restrictions.like("jNumOrder",
                        cashierBills.getjNumOrder(), MatchMode.ANYWHERE));
            }

        }

        return dc;

    }

    /**
     * 单据打印预览
     *
     * @return
     */
    public String getViewCashPage() {

        String hql = "from CashierBills where cashierBillsID = ?";
        cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{cashierBills.getCashierBillsID()});
        hql = "from GoodsBills where cashierBillsID = ?";
        list = baseBeanService.getListBeanByHqlAndParams(hql,
                new Object[]{cashierBills.getCashierBillsID()});

        return "cashsee";

    }

    /**
     * 验证卖家是否绑定银行卡,是否有退货地址和发货地址
     *
     * @param
     * @return
     */
    public String isAllow() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (companyId != null && companyId.length() > 1) {
            String hql = "select ra from RefundAddress ra where ra.companyId=? and ra.rtype=?";
            //发货地址
            BaseBean bean = baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId, 1});
            if (bean != null) {
                bean = null;
                //退货地址
                bean = baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId, 0});
                if (bean != null) {
                    //银行卡信息做好了之后开启
				/*	 bean=null;
					hql="select bk from StaffBankAccount bk,CcomCom cc where cc.ccompanyId=bk.ccompanyId and cc.comanyId=? and bk.type=? ";
					bean=baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId,"00"});
					if(bean==null){
						result="请先完善银行卡信息";
					}*/

                } else {
                    result = "请先完善退货地址";
                    return Action.SUCCESS;
                }
            } else {
                result = "请先完善发货地址";
                return Action.SUCCESS;
            }
            HttpServletRequest request = ServletActionContext.getRequest();
            String ppids = request.getParameter("ppids");
            String[] ppidArr = ppids.split(",");

            hql = "select nvl(INVENQUANTITY,0),p.ppid,p.goodsname,d.inventoryid from dt_ProductPackaging p  left join dt_inv_invt d on d.productid=p.ppid " +
                    "and d.WAREHOUSENAME=? where   p.ppid in (";
            for (int i = 0; i < ppidArr.length; i++) {
                hql += "?,";
            }
            hql = hql.substring(0, hql.length() - 1);
            hql += ")";

            String[] params = new String[ppidArr.length + 1];
            params[0] = "销售库";
            for (int i = 0; i < ppidArr.length; i++) {
                params[i + 1] = ppidArr[i];
            }
            List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql, params);
            String allowId = "";
            String errorName = "";
            for (int i = 0; i < list.size(); i++) {
                Object obj = (Object) list.get(i);
                Object[] os = (Object[]) obj;
                if ("0".equals(os[0].toString())) {
                    errorName += os[2] + ";\n";
                } else {
                    allowId += os[1] + ",";
                }
            }
            if (!"".equals(errorName)) {
                if ("".equals(allowId)) {
                    result = "所选产品：\n" + errorName + "尚未入库!";
                } else {
                    result = "产品：\n" + errorName + "尚未入库，是否将其余产品发布?";
                }
                session.put("allowId", allowId);
            }
				/*Inventory inv=(Inventory)baseBeanService.getObjectBySqlAndParams(hql,params);
				Inventory inv=new Inventory();
				for (String p: ppidArr){
					hql="select nvl(INVENQUANTITY,0),d.productid,d.goodsname from dt_inv_invt d where d.WAREHOUSENAME = ? and  d.productid = ?";
					 Object obj=baseBeanService.getObjectBySqlAndParams(hql,new Object[]{"销售库",p});
					if (obj==null){
						hql="select goodsname from dt_productpackaging where ppid= ?";
						String errorName=(String) baseBeanService.getObjectBySqlAndParams(hql,new Object[]{p});
						errorList.add(errorName);
					}
				}*/
			/*if (errorList.size()>0){
					String errorName="";
				for (String s : errorList){
					errorName+=s+";\n";
				}
			}*/

        }
        return Action.SUCCESS;
    }

    /**
     * 退货地址,发货地址
     *
     * @return
     */
    public String sealAddress() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String flag = request.getParameter("flag");
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        if (user == null || user.equals("")) {
            TEshopCusCom scc = null;
            CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            if(account!=null&&"2".equals(flag)){
                String companyID = account.getCompanyID();
                scc = (TEshopCusCom) baseBeanService
                        .getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",
                                new Object[]{companyID});
            }else{
                scc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            }

            user = scc.getSccId();
        }else{
            TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{user});

            TEshopCustomer customer = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{cus.getAccount()});
            sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);

        }
        ActionContext.getContext().getValueStack().set("user", user);
        ActionContext.getContext().getValueStack().set("type", type);
        String hql = "select ra from TEshopCusCom tec,RefundAddress ra where ra.companyId=tec.companyId and ra.rtype=? and tec.sccId=? order by ra.status,raddressId desc";
        if (type != null && type.equals("1")) {
            entityList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{1, user});
        } else {
            entityList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{0, user});
        }
        return "sealAddress";
    }

    /**
     * 收货地址
     *
     * @return
     */
    public String sealAddress2() {

        HttpServletRequest request = ServletActionContext.getRequest();
        ActionContext.getContext().getValueStack().set("type", type);
        String hql = "from RefundAddress ra where ra.companyId=? and ra.rtype=?";
        entityList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyId,2});
        return "sealAddress";
    }

    //添加跳转
    public String toAdd() {
        return "toadd";
    }
    //个人名片添加退货地址.发货地址

    //保存地址
    public String addPersonAddr() {
        TEshopCusCom teshopcuscom = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams("from TEshopCusCom cc where cc.sccId=?",
                        new Object[]{user});
        HttpServletRequest request = ServletActionContext.getRequest();
        if ((companyId==null||companyId.equals(""))&&teshopcuscom != null && teshopcuscom.getCompanyId() != null
                && !teshopcuscom.getCompanyId().equals("")) {
            companyId=teshopcuscom.getCompanyId();
        }

        int ret = 0;
        Object[] parm = null;
        if (type != null && type.equals("1")) {
            parm = new Object[]{1, companyId};
            refundAddress.setRtype(1);
        }else if (type != null && type.equals("2")) {
            parm = new Object[]{2, companyId};
            refundAddress.setRtype(2);
        } else {
            parm = new Object[]{0, companyId};
            refundAddress.setRtype(0);
        }

        ret = baseBeanService
                .getConutByByHqlAndParams(
                        "select count(s) from RefundAddress s where s.rtype=? and s.companyId = ?",
                        parm);

        refundAddress.setRaddressId(serverService
                .getServerID("RefundAddress"));
        refundAddress.setCompanyId(companyId);
        if (ret == 0) {
            refundAddress.setStatus("00");
        }
        baseBeanService.save(refundAddress);
        if (type != null && type.equals("2")) {
            return sealAddress2();
        }else{
            return sealAddress();
        }

    }

    //设置默认地址
    public String changeDefault() {
        if (refundAddress != null && refundAddress.getRaddressId() != null && refundAddress.getRaddressId().length() > 1) {
            String hql = "update RefundAddress u set u.status='01' where u.companyId=? and u.status= '00'";
            String hql1 = "update RefundAddress u set u.status='00' where u.raddressId=?";
            if (type != null && type.equals("1")) {
                hql += " and u.rtype=1";
            } else if (type != null && type.equals("2")){
                hql += " and u.rtype=2";
            }else {
                hql += " and u.rtype=0";
            }
            List<Object[]> parm = new ArrayList<Object[]>();
            parm.add(new Object[]{refundAddress.getCompanyId()});
            parm.add(new Object[]{refundAddress.getRaddressId()});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parm);
        }
        return Action.SUCCESS;
    }

    //详情
    public String toDetail() {
        if (refundAddress != null && refundAddress.getRaddressId() != null) {
            String hql = "from RefundAddress where raddressId=?";
            refundAddress = (RefundAddress) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{refundAddress.getRaddressId()});
            ActionContext.getContext().getValueStack().push(refundAddress);
        }
        return "detail";
    }

    //删除地址
    public String delAddr() {
        if (refundAddress != null && refundAddress.getRaddressId() != null) {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"delete from RefundAddress where raddressId=?"}, new Object[]{refundAddress.getRaddressId()});
            if(type!=null&&type.equals("2")){
                return sealAddress2();
            }
            return  Action.SUCCESS;
        } else {
            return "detail";
        }
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

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public String getVoptype() {
        return voptype;
    }

    public void setVoptype(String voptype) {
        this.voptype = voptype;
    }

    public RefundSheet getRefundSheet() {
        return refundSheet;
    }

    public void setRefundSheet(RefundSheet refundSheet) {
        this.refundSheet = refundSheet;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFiveClear() {
        return fiveClear;
    }

    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }

    public RefundSheet getRs() {
        return rs;
    }

    public void setRs(RefundSheet rs) {
        this.rs = rs;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CashierBills getCashierBills() {
        return cashierBills;
    }

    public void setCashierBills(CashierBills cashierBills) {
        this.cashierBills = cashierBills;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public RefundAddress getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(RefundAddress refundAddress) {
        this.refundAddress = refundAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public List<BaseBean> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<BaseBean> entityList) {
        this.entityList = entityList;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getStaid() {
        return staid;
    }

    public void setStaid(String staid) {
        this.staid = staid;
    }

}
