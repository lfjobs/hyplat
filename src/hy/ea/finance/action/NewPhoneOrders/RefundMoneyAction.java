package hy.ea.finance.action.NewPhoneOrders;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import hy.ea.util.MobileMessage;
import mobile.tiantai.android.util.JushMain;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.front.WfjEshopProductAction;

import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.PhoneBill;
import hy.ea.bo.finance.BenDis.RefundSheet;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.finance.service.transferService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class RefundMoneyAction {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private transferService trans;
    @Autowired
    private MobileMessage msage;

    private Logger logger = LoggerFactory.getLogger(RefundMoneyAction.class);
    private String cashId;//订单号
    private String type;//退款类型
    private String reason;//退款原因
    private String money;//退款金额
    private String account;//退款说明
    private String num;//数量
    private String expCode;//快递公司名标识
    private File[] photo;
    private String[] photoFileName;

    private RefundSheet refundSheet;
    private DtOrderBillAdd oa;
    private String result;    //ajax
    private String fkStatus;//微分金状态

    private List<BaseBean> list;

    private String staffid;
    private PageForm pageForm;
    private String tp;//00:仅退款   01：退款退货

    private int pageNumber;

    private String express;//快递公司
    private String expno;//快递运单号

    private Map<String, Object> mapg;
    private Map<String, Object> maps;
    private Map<String, Object> map;
    private String pl;


    //退款退货（仅退款）
    @SuppressWarnings("unchecked")
    public String ref() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = "from CashierBills where cashierBillsID = ?";
        CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});

        String Hql = "from GoodsBills where cashierBillsID = ?";
        GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(Hql, new Object[]{cashId});

        request.setAttribute("cashId", cashId);
        request.setAttribute("staffid", cash.getContactUserID());
        request.setAttribute("money", cash.getPriceSub());//总金额
        request.setAttribute("qu", gb.getQuantity());//数量
        request.setAttribute("price", gb.getPrice());//单价

        //仅退款 00   退款退货  01
        if ("00".equals(tp)) {
            return "rfmoney";
        } else {
            return "refund";
        }
    }

    //提交退款（退货退款）(cashID,staffid)
    @SuppressWarnings("unchecked")
    public String getReturnRefund() throws IllegalAccessException, InvocationTargetException {

        List<BaseBean> list = new ArrayList<BaseBean>();

        String hql = " from CashierBills where cashierBillsID = ? and status != ?";
        CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId, "99"});
        if (cashierBills.getFkStatus() != ""
                && (("04".equals(cashierBills.getStatusbill()) && "00".equals(cashierBills.getFkStatus()))
                || ("04".equals(cashierBills.getStatusbill()) && "02".equals(cashierBills.getFkStatus()))
                || ("04".equals(cashierBills.getStatusbill()) && "03".equals(cashierBills.getFkStatus())))) {

            StringBuilder sql = new StringBuilder();
            sql.append(" select cb.cashierbillsid ");
            sql.append(" from dtcashierbills cb ");
            sql.append(" where cb.cashierbillsid = ? ");
            sql.append(" union all ");
            sql.append(" select pa.ptcashierbillsid ");
            sql.append(" from dtcashierbills cb, dt_promotionassociation pa ");
            sql.append(" where cb.cashierbillsid = pa.cashierbillsid ");
            sql.append(" and cb.cashierbillsid = ? ");

            List<Object> obj = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{cashId, cashId});

            sql.delete(0, sql.length());
            sql.append("select cus from CashierBills cb,TEshopCusCom cus where cb.companyID = cus.companyId");
            sql.append(" and acquiesce = ? and cb.cashierBillsID = ?");

            TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(sql.toString(),
                    new Object[]{"01", cashId});

            for (int i = 0; i < obj.size(); i++) {
                Object lists = obj.get(i);
                boolean flag = app(lists.toString(), list, true);
                if (flag == false) {
                    list = null;
                    logger.error("" + "申请退货退款错误。");
                    break;
                }
            }
            baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
            pushMessage(cashierBills.getJournalNum(), cusCom);
            return details();
        } else {
            return details();
        }
    }

    /**
     * 推送、短信通知
     *
     * @param journalNum
     * @param cusCom
     */
    private void pushMessage(String journalNum, TEshopCusCom cusCom) {
        try {

            //商家信息
            StringBuffer consql = new StringBuffer();
            consql.append("select tc.contactway");
            consql.append(" from dt_hr_staff_contact tc,t_eshop_cuscom cus");
            consql.append(" where tc.contactway = cus.account");
            consql.append(" and tc.contactType = ? and tc.companyid = ?");
            consql.append(" union");
            consql.append(" select cus.account");
            consql.append(" from t_eshop_cuscom cus, dt_hr_staff rf");
            consql.append(" where rf.staffid = cus.staffid and cus.companyid = ?");

            List<Object> object = baseBeanService.getListBeanBySqlAndParams(
                    "select e.codeid from dtCCode e where e.codevalue = ? and e.companyid = ?"
                    , new Object[]{"短信提醒", "company201009046vxdygzy4w0000000025"});

            List<Object> conlist = baseBeanService.getListBeanBySqlAndParams(consql.toString()
                    , new Object[]{object != null && object.size() > 0 ? object.get(0) : ""
                            , cusCom.getCompanyId(), cusCom.getCompanyId()});

            //保存账号
            List<String> slist = new ArrayList<String>();
            for (Integer i = 0; i < conlist.size(); i++) {
                Object objects = conlist.get(i);
                slist.add(objects.toString());
            }
            //极光推送
            JushMain.sendjiguangMessage("买家已提交订单号：" + journalNum + "的退款申请，请及时处理！！！"
                    , ""
                    , "companyid=" + cusCom.getPpid() + "&sccId=" + cusCom.getSccId(), "refund", slist);
            if (conlist != null && conlist.size() > 0) {
                Object o = conlist.get(0);
                msage.setMobiles(o != null ? o.toString() : "");
                msage.setMessage("买家已提交订单号：" + journalNum + "的退款申请，请及时处理！！！");
                msage.sendMsg("【微分金平台】");
            }
        } catch (IOException e) {
            logger.error("推送，短信通知失败！");
        }
    }

    //申请退货退款单
    private boolean app(String cashid, List<BaseBean> list, boolean subtract) {

        try {

            String cbhql = " from CashierBills where cashierBillsID = ?  ";
            CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(cbhql, new Object[]{cashid});

            String gbhql = "from GoodsBills where cashierBillsID = ?";
            GoodsBills goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(gbhql, new Object[]{cashid});

            String tehql = "from TEshopCusCom where staffid = ?";
            TEshopCusCom tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(tehql, new Object[]{staffid});

            refundSheet = new RefundSheet();
            refundSheet.setRsid(serverService.getServerID("RefundSheet"));
            System.out.println("退货单id为：" + refundSheet.getRsid());
            refundSheet.setOrderCode(cashierBills.getjNumOrder());
            refundSheet.setCashierBillsID(cashid);
            refundSheet.setRefundCode(serverService.getBillID(cashierBills.getCompanyID()));
            refundSheet.setCompanyID(cashierBills.getCompanyID());
            refundSheet.setCompanyName(cashierBills.getCompanyName());
            refundSheet.setOrderDate(cashierBills.getCashierDate());
//			refundSheet.setUserName(tEshopCusCom.getAccount());
            refundSheet.setUserAccount(tEshopCusCom.getAccount());
            refundSheet.setPpid(goodsBills.getPpID());

            refundSheet.setRefundCause(reason);//退款原因
            refundSheet.setRefundDate(new Date());
            refundSheet.setRefundRemark(account);
            refundSheet.setRefundstate("00");//00:买家提交退货 申请

            //判断是否是主产品（cashId 主产品）
            if (cashid.equals(cashId)) {
                refundSheet.setRefundMoney(money);
                refundSheet.setRefundNum(num);
                cashierBills.setCkStatus(cashierBills.getFkStatus());
                cashierBills.setFkStatus("05");//申请退货退款
                list.add(cashierBills);
            } else {
                refundSheet.setRefundMoney(cashierBills.getPriceSub());
                refundSheet.setRefundNum(goodsBills.getQuantity());
            }


            // 02:已出库正在物流 (提交退货退款申请)
            if ("01".equals(tp)) {
                refundSheet.setRefundType(tp);
                if (photo != null && photo.length > 0) {
                    String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
                    String photopath = "";
                    for (int i = 0; i < photo.length; i++) {
                        photopath = fileService.savePhoto(
                                path,
                                photoFileName[i],
                                photo[i],
                                cashierBills.getCompanyID(),
                                "/photos/"
                                        + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                        if (i == 0) {
                            refundSheet.setVoucherfile1(photopath);
                        } else if (i == 1) {
                            refundSheet.setVoucherfile2(photopath);
                        } else {
                            refundSheet.setVoucherfile3(photopath);
                        }
                    }
                }
                list.add(refundSheet);

            } else {//仅退款

                refundSheet.setRefundType(tp);
                if (cashid.equals(cashId)) {
                    refundSheet.setRefundMoney(money);
                    cashierBills.setFkStatus("16");//申请退款审核中
                    cashierBills.setCkStatus(cashierBills.getFkStatus());
                    list.add(cashierBills);
                } else {
                    refundSheet.setRefundMoney(cashierBills.getPriceSub());
                }
                list.add(refundSheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            subtract = false;
        }
        return subtract;
    }

    //申请退款，审核中
    public String details() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = "from RefundSheet where cashierBillsID = ?";
        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});
        //仅退款 00   退款退货  01
        if ("00".equals(tp)) {
            return "details";
        } else {
            return "return";
        }

    }

    //获取卖家地址
    public String gainAddress() {

        String hql = "from RefundSheet where cashierBillsID = ?";
        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});

        return "gainAddress";
    }


    //退货申请成功，寄回商品             FkStatus 06:同意退货
    public String refundOfBack() throws IllegalAccessException, InvocationTargetException {


        HttpServletRequest request = ServletActionContext.getRequest();
        List<BaseBean> list = new ArrayList<BaseBean>();
        String cashId = request.getParameter("cashId");
        String staffid = request.getParameter("staffid");


        String hql = "from CashierBills where cashierBillsID = ?";
        CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});

        if (cb.getFkStatus() != null && "06".equals(cb.getFkStatus())) {

            StringBuilder sql = new StringBuilder();
            sql.append(" select cb.cashierbillsid ");
            sql.append(" from dtcashierbills cb ");
            sql.append(" where cb.cashierbillsid = ? ");
            sql.append(" union all ");
            sql.append(" select pa.ptcashierbillsid ");
            sql.append(" from dtcashierbills cb, dt_promotionassociation pa ");
            sql.append(" where cb.cashierbillsid = pa.cashierbillsid ");
            sql.append(" and cb.cashierbillsid = ? ");

            List<Object> obj = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{cashId, cashId});

            for (int i = 0; i < obj.size(); i++) {

                Object lists = obj.get(i);
                boolean flag = ruku(staffid, lists.toString(), list, true);
                if (flag == false) {
                    list = null;
                    logger.error("" + "入库保存错误。");
                    break;
                }
            }
            baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
        }

        return "refundOfBack";
    }

    //退货入库
    private boolean ruku(String staffid, String cashid, List<BaseBean> list, boolean subtract) {

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String Hql = "from RefundSheet where cashierBillsID = ?";
            refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(Hql, new Object[]{cashid});
            System.out.println("退货单id为：" + refundSheet.getRsid());
            refundSheet.setExpress(express);
            refundSheet.setWaybillno(expno);
            refundSheet.setExpCode(expCode);//快递公司名的标识
            refundSheet.setBackDate(new Date());//退货时间
            refundSheet.setRefundstate("03");//03:买家退货中
            list.add(refundSheet);

            String hqlt = "from TEshopCusCom where staffid = ?";
            TEshopCusCom tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{staffid});

            //地址
            String rHql = "from DtOrderBillAdd where oaBillId=?";
            oa = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(rHql, new Object[]{cashid});

            //获取库存名称为物流库的信息
            String depotHql = "from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
            DepotManage depot = (DepotManage) baseBeanService.getBeanByHqlAndParams(depotHql, new Object[]{"001", tEshopCusCom.getCompanyId(), "物流库", "00"});

            //物流入库
            String hqlgb = "from GoodsBills where cashierBillsID = ? and ppID = ?";
            GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hqlgb, new Object[]{refundSheet.getCashierBillsID(), refundSheet.getPpid()});

            //查询每种物品的库存
            String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=? and productId=?";
            Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{tEshopCusCom.getCompanyId(), gb.getGoodsID(), depot.getDepotID(), gb.getPpID()});

            //库存表
            if (inv == null) {
                inv = new Inventory();
                inv.setInventoryID(serverService.getServerID("Inventory"));
                System.out.println("新的库存表：" + inv.getInventoryID());
                inv.setCompanyID(tEshopCusCom.getCompanyId());
                inv.setWarehouse(depot.getDepotID());
                inv.setWarehouseName(depot.getDepotName());
                inv.setGoodsID(gb.getGoodsID());
                inv.setGoodsType(gb.getTypeID());
                inv.setStandard(gb.getStandard());
                inv.setGoodsCoding(gb.getGoodsNum());
                inv.setUnitPrice(gb.getPrice());
                inv.setProductId(gb.getPpID());
                inv.setInvenQuantity(refundSheet.getRefundNum());//退货数量
                inv.setSumPrice(refundSheet.getRefundMoney());//退款
                inv.setGoodstatus("00");
                list.add(inv);
            } else {
                System.out.println("已有的库存表：" + inv.getInventoryID());
                inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity()) + Integer.parseInt(gb.getQuantity()) + "");
                inv.setSumPrice(Double.parseDouble(inv.getSumPrice()) + Double.parseDouble(gb.getMoney()) + "");
                list.add(inv);
            }

            //库存盘点表
            stockInv sto = new stockInv();
            sto.setStockinvID(serverService.getServerID("stockInv"));
            System.out.println("存库盘点表id：" + sto.getStockinvID());
            sto.setCompanyID(tEshopCusCom.getCompanyId());
            sto.setGoodsBillsId(gb.getGoodsBillsID());
            sto.setGoodsID(gb.getGoodsID());
            sto.setGoodsType(gb.getTypeID());
            sto.setGoodsName(gb.getGoodsName());
            sto.setInvenQuantity(refundSheet.getRefundNum());//退货数量
            sto.setIntime(new Date());
            sto.setType("00");
            sto.setWarehouse(depot.getDepotID());
            sto.setWarehouseName(depot.getDepotName());
            list.add(sto);

            //物流入库单(单据表)
            CashierBills cbs = new CashierBills();
            cbs.setCashierBillsID(serverService.getServerID("CashierBills"));
            System.out.println("物流入库单（单据表）：" + cbs.getCashierBillsID());
            cbs.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
            cbs.setCompanyID(tEshopCusCom.getCompanyId());
            cbs.setCashierDate(new Date());
            cbs.setBillsType("物流入库单");
            cbs.setStaffName(oa.getSendname());
            cbs.setStatus("15");//物流已入库
            list.add(cbs);

            //物品单据
            GoodsBills newgb = new GoodsBills();
            newgb.setGoodsBillsID(serverService.getServerID("GoodsBills"));
            System.out.println("物流入库表（商品表）：" + newgb.getGoodsBillsID());
            newgb.setInventoryID(inv.getInventoryID());
            newgb.setStockinvID(sto.getStockinvID());
            newgb.setCashierBillsID(cbs.getCashierBillsID());
            newgb.setQuantity(refundSheet.getRefundNum());//退货数量
            newgb.setPrice(refundSheet.getRefundMoney());//退款金额
            newgb.setCostType("退货");
            newgb.setDepotID(depot.getDepotID());
            newgb.setDepotName(depot.getDepotName());
            newgb.setKcStatus("15");
            list.add(newgb);

            //进销存单据
            FinancialBill fin = new FinancialBill();
            fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
            System.out.println("进销存单据：" + fin.getFinancialbillID());
            fin.setCashierBillsID(cbs.getCashierBillsID());
            fin.setCompanyID(tEshopCusCom.getCompanyId());
            fin.setDepotID(depot.getDepotID());
            fin.setDepotName(depot.getDepotName());
            fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
            fin.setBillsDate(new Date());
            fin.setBillStaffID(tEshopCusCom.getStaffid());
            fin.setBillsType("物流入库单");
            list.add(fin);

            //更改原订单表fk状态
            String hql = "from CashierBills where cashierBillsID = ?";
            CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashid});

            if (cashid.equals(cashId)) {
                cb.setFkStatus("07");//07:退货中
                request.setAttribute("money", refundSheet.getRefundMoney());
                list.add(cb);
            }

        } catch (Exception e) {
            e.printStackTrace();
            subtract = false;
        }
        return subtract;

    }


    //查询退货单
    public String getRefundList() {
        return "list";
    }

    public String getAjax() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String pl = request.getParameter("pl");
        String staffid = request.getParameter("staffid");

        List<Object> para = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();//查询退货信息

        sql.append(" select r.cashierbillsid,r.ordercode,r.refundmoney,r.refundnum,r.refundstate, ");
        sql.append(" c.companyid,c.companyname,cc.logopath,r.refundtype,c.pricesub ");
        sql.append(" from dtrefundsheet r,dtcashierbills c,dtcontactcompany cc, dt_ccom_com ccom ");
        sql.append(" where r.cashierbillsid=c.cashierbillsid ");
        sql.append(" and ccom.ccompany_id = cc.ccompanyid ");
        sql.append(" and ccom.compnay_id = c.companyid ");
        sql.append(" and c.contactuserid=? ");

        para.add(staffid);

        if ("00".equals(pl)) {//退货中
            sql.append("  and r.refundstate !=? and r.refundstate!=?");
            para.add("02");
            para.add("05");
        } else {
            sql.append("  and (r.refundstate =? or r.refundstate =? ) ");
            para.add("02");
            para.add("05");
        }
        sql.append("  order by r.refunddate desc ");
        pageForm = baseBeanService.getPageFormBySQL(pageNumber, 6, sql.toString(), "select count(*) from (" + sql.toString() + ")", para.toArray());

        StringBuilder goods = new StringBuilder();//商品
        StringBuilder sales = new StringBuilder();//促销

        //查询商品
        goods.append("select g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,");
        goods.append("c.companyid,c.companyname,g.cashierbillsid,g.standard,c.jnumorder,p.tradecode g.pricetype  ");
        goods.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
        goods.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid in(");

        //查询促销品
        sales.append("select pa.cashierbillsid,pa.ppid,pa.ppname,pa.companyid,pa.price,pa.standard,p.image");
        sales.append(" from dt_promotionassociation pa,dtcashierbills cb,dt_productpackaging p");
        sales.append(" where pa.cashierbillsid=cb.cashierbillsid and p.ppid=pa.ppid and pa.cashierbillsid in( ");

        List<Object> obj = new ArrayList<Object>();

        Object[] objs = null;
        if (pageForm != null && pageForm.getList() != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object object = pageForm.getList().get(i);
                objs = (Object[]) object;
                goods.append("?,");
                sales.append("?,");
                obj.add(objs[0]);
            }
            String goodsSql = goods.toString();
            goodsSql = goods.substring(0, goods.length() - 1) + ")";

            String salesSql = sales.toString();
            salesSql = sales.substring(0, sales.length() - 1) + ")";


            List<Object[]> goodsList = baseBeanService.getListBeanBySqlAndParams(goodsSql, obj.toArray());
            List<Object[]> salesList = baseBeanService.getListBeanBySqlAndParams(salesSql, obj.toArray());

            mapg = new HashMap<String, Object>();//将商品填充到pagaForm
            maps = new HashMap<String, Object>();//促销品
            List<Object[]> glist = null;
            List<Object[]> slist = null;
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object object = pageForm.getList().get(i);
                Object[] objg = (Object[]) object;//商品
                Object[] objs1 = (Object[]) object;//促销品
                glist = new ArrayList<Object[]>();
                slist = new ArrayList<Object[]>();
                for (Object[] ob : goodsList) {
                    if (objg[0].equals(ob[10])) {
                        glist.add(ob);
                        mapg.put(objg[0] + "", glist);
                    }
                }
                for (Object[] ob : salesList) {
                    if (objs1[0].equals(ob[0])) {
                        slist.add(ob);
                        maps.put(objs1[0] + "", slist);
                    }
                }

            }
        }
        map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("mapg", mapg);
        map.put("maps", maps);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";

    }


    //查看是否退货(退款)
    public String check() {

        JSONObject obj = new JSONObject();
        String hql = "from RefundSheet where cashierBillsID = ?";
        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});

        //00  仅退款     01 退款退货
        if ("00".equals(refundSheet.getRefundType())) {
            if ("00".equals(refundSheet.getRefundstate())) {
                obj.put("type", 0);//提交申请
            } else if ("01".equals(refundSheet.getRefundstate())) {
                obj.put("type", 5);//卖家同意，请退货
            } else if ("05".equals(refundSheet.getRefundstate())) {
                obj.put("type", 6);//卖家打款给买家，退款结束
            } else if ("02".equals(refundSheet.getAccountstatus())) {
                obj.put("type", 4);//拒绝退款
            }

        } else if ("01".equals(refundSheet.getRefundType())) {
            if ("00".equals(refundSheet.getRefundstate())) {
                obj.put("type", 0);//提交申请
            } else if ("01".equals(refundSheet.getRefundstate())) {
                obj.put("type", 1);//卖家同意，请退货
            } else if ("03".equals(refundSheet.getRefundstate())) {
                obj.put("type", 2);//跳转到物流查询
            } else if ("05".equals(refundSheet.getRefundstate())) {
                obj.put("type", 3);//卖家打款给买家，退货结束
            } else if ("02".equals(refundSheet.getAccountstatus())) {
                obj.put("type", 4);//拒绝退货
            }
        }
        result = obj.toString();
        return "success";
    }


    public String view() {

        String hql = "from RefundSheet  where cashierBillsID = ? ";
        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});

        return "viewLogistics";
    }

    //调用物流信息
    public String wl() throws Exception {

        String hql = "from RefundSheet  where cashierBillsID = ? ";
        refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashId});
        String expCode = refundSheet.getExpCode();//快递公司编码
        String expNo = refundSheet.getWaybillno();
//        String res = trans.wuLiu(expCode, expNo);
        String res = trans.wuLiu( expNo);
        result = res;
        return "success";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    public File[] getPhoto() {
        return photo;
    }

    public void setPhoto(File[] photo) {
        this.photo = photo;
    }

    public String[] getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String[] photoFileName) {
        this.photoFileName = photoFileName;
    }


    public RefundSheet getRefundSheet() {
        return refundSheet;
    }

    public void setRefundSheet(RefundSheet refundSheet) {
        this.refundSheet = refundSheet;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFkStatus() {
        return fkStatus;
    }

    public void setFkStatus(String fkStatus) {
        this.fkStatus = fkStatus;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public DtOrderBillAdd getOa() {
        return oa;
    }

    public void setOa(DtOrderBillAdd oa) {
        this.oa = oa;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpno() {
        return expno;
    }

    public void setExpno(String expno) {
        this.expno = expno;
    }

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
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

    public Map<String, Object> getMapg() {
        return mapg;
    }

    public void setMapg(Map<String, Object> mapg) {
        this.mapg = mapg;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }


}
