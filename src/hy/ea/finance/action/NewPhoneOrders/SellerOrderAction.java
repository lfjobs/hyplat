package hy.ea.finance.action.NewPhoneOrders;

import com.wechat.utils.HTTPV3;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.Comments;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.PhoneBill;
import hy.ea.bo.finance.BenDis.RefundSheet;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.finance.service.AlipayRefundService;
import hy.ea.finance.service.transferService;
import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.service.WchatPay;

/**
 * @author zzl
 */
@Controller("SellerOrderAction")
@Scope("prototype")
public class SellerOrderAction {
	private static final Logger logger = LoggerFactory.getLogger(SellerOrderAction.class);

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private transferService transferService;
    @Resource
    private AlipayRefundService alipayRefund;
    @Resource
    private ServerService serverService;

    private Logger log = LoggerFactory.getLogger(SellerOrderAction.class);

    private Map<String, Object> map1;
    private int pageNumber;

    private PageForm pageForm;
    private String result;
    private String companyid;
    private String ljly;
    private String pl;
    private String user;
    private String sta;// 收货单的标识
    private String cashierBillsID;
    private String oaBillId;
    private String parameter;// 前台页面传参判断使用
    private List<String> state;
    private String staffId;
    private String waybillNumber;// 运单号
    private String EBusinessID;
    private String status;// 退货单的标识
    private String status2;

    private String baseUrl;// 路径前缀
    private String ddid;
    private String journalNum;
    private String morre;

    private TEshopCusCom tEshopCusCom;


    private Map<String, Object> map;

    private String fkStatus;


    /**
     * 小系统客户管理（客户订单）
     */
    public String cusOrder() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cAccount = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        String companyid = cAccount.getCompanyID();
        String staffid = cAccount.getStaffID();
        session.setAttribute("companyid", companyid);
        session.setAttribute("staffid", staffid);
        return "selllist";
    }

    /**
     * 小系统客户管理（客户）
     */
    public String getList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cAccount = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        session.setAttribute("companyid", cAccount.getCompanyID());
        session.setAttribute("staffid", cAccount.getStaffID());
        return "customer";
    }

    public String getListAjax() {

        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String companyid = request.getParameter("companyid");
        String cusName = request.getParameter("cusName");
        DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class);//离线查询
        dc.add(Restrictions.eq("companyid", companyid));
        dc.addOrder(Order.desc("cashierdate"));//排序
        try {
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 7, dc);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" select ff.photo,ff.staffname,ff.reference ");
        sql.append(" from dt_hr_staff ff ");
        sql.append(" where ff.staffid in( ");

        List<Object> object = new ArrayList<Object>();
        Object[] objs = null;
        if (pageForm != null && pageForm.getList() != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
                if (!"01".equals(pb.getFkStatus())) {
                    sql.append("?,");
                    object.add(pb.getContactUserID());//找出staffid

                }
            }

            String listSql = sql.toString();
            listSql = sql.substring(0, sql.length() - 1) + ")";
            ;

            //用户模糊查询
            if (!"".equals(cusName) && cusName != null) {
                cusName = cusName.trim();//去空格
                listSql += " and (ff.staffname like ? or ff.reference like ? ) ";
                object.add("%" + cusName + "%");
                object.add("%" + cusName + "%");
            }

            PageForm pageForms = baseBeanService.getPageFormBySQL(pageNumber, 8, listSql,
                    "select count(*) from (" + listSql + ")", object.toArray());
            map.put("pageForm", pageForms);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }


    /**
     * 查看物流
     */
    public final String logisticsQuery() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String exCode = request.getParameter("exCode");
        String waybillNumber = request.getParameter("waybillNumber");

        try {
            if ("02".equals(type)) {

                return "LogisticsQuery";
            }
//            result = transferService.wuLiu(exCode, waybillNumber);
            result = transferService.wuLiu( waybillNumber);
        } catch (Exception e) {
            log.error("发货失败");
        }

        return "success";
    }

    /**
     * 单号识别
     */
    public final String numberRecognition() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String number = request.getParameter("number");
        try {
            result = transferService.wuLiu(number);
        } catch (Exception e) {
            log.error("单号识别错误");
        }
        return "success";
    }

    /**
     * 发货
     */

    public final String deliverGoods() {
        HttpServletRequest request = ServletActionContext.getRequest();
        state = transferService.onkeyfh(companyid, staffId, cashierBillsID);
        if ("00".equals(state.get(0))) {
            String Waybillno = request.getParameter("Waybillno");
            if (Waybillno != null) {
                return getDelivery();
            }
            return "liveryOk";
        } else {

            return "liveryNo";
        }


    }

    /**
     * 物流发货
     */
    private String getDelivery() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String Waybillno = request.getParameter("Waybillno");
        String ExCode = request.getParameter("ExCode");
        String hql = " from DtOrderBillAdd where oaBillId=?";
        DtOrderBillAdd dto = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql,
                new Object[]{cashierBillsID});

        dto.setWaybillno(Waybillno);
        dto.setExCode(ExCode);
        baseBeanService.update(dto);

        return "livery";
    }

    /**
     * 我的订单页面
     */
    public final String getcomporder() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sccid = request.getParameter("sccId");
        if (!"".equals(sccid)) {
            StringBuilder sql = new StringBuilder();
            sql.append("select dt.staffname, s.account,s.sccid from t_eshop_cuscom s,dt_hr_Staff dt where s.sccid=? and dt.staffid=s.staffid ");
            Object obj = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{sccid});
            request.setAttribute("obj", obj);
        }
        return "list";
    }

    /**
     * 上拉下拉加载
     */
    public String getAjax() {
        //pageForm = getList(companyid, pl);
        Map<String, Object> map = new HashMap<String, Object>();
        map = getList(companyid, pl,null,null);
        //map.put("pageForm", pageForm);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }
    
    /**
     * 新版上拉下拉加载
     */
    public String getAjaxNew() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String source=request.getParameter("source");
        String parameter=request.getParameter("parameter");
        //pageForm = getList(companyid, pl);
        Map<String, Object> map  = getList(companyid, pl,source,parameter);
        //map.put("pageForm", pageForm);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    /**
     * 单据列表
     *
     * @param staid 公司id
     * @param pl    单据跟踪状态
     * @return
     */
    @SuppressWarnings("unchecked")
    public  Map<String, Object> getList(String staid,String pl,String source,String parameter) {
        DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class);
        dc.add(Restrictions.eq("companyid", staid));
        if(parameter!=null&&!parameter.equals("")){
            dc.add(Restrictions.or(Restrictions.like("journalNum",parameter),
                    Restrictions.like("cgscomname",parameter)));
        }
        if(source!=null&&source.equals("office")){
        	dc.add(Restrictions.ne("paystatus", "01"));
        	if (pl != null && !pl.equals("")) {
                dc.add(Restrictions.eq("supplierstatus", pl));
            }
        }else{
        	if (pl != null && !pl.equals("")) {
                dc.add(Restrictions.eq("fkStatus", pl));
            } else {
                dc.add(Restrictions.ne("fkStatus", "01"));
            }
        }
        dc.addOrder(Order.desc("cashierdate"));
        try {
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 7, dc);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        List<Object[]> goodlist = null;
        StringBuffer sql = new StringBuffer();
        StringBuffer ptsql = new StringBuffer();
        String goodhql = "";
        String psql = "";

        sql.append("select g.goodsbillsid,g.remark,");
        sql.append(" g.quantity,g.price,g.goodsnum,");
        sql.append(" g.goodsname,p.image,g.ppid,");
        sql.append(" g.goodsID,g.cashierbillsid,g.standard,");
        sql.append(" c.priceSub,c.status,c.fkStatus,");
        sql.append(" to_char(c.cashierDate,'YYYY-MM-DD HH24:MI:SS')");
        String flag ="200";//[200:正常，300:sql多追加2个字段]
        if ("02".equals(pl) || "".equals(pl)) {
            sql.append(",ord.waybillno,ord .exCode");
            flag = "300";
        }
        sql.append(",c.journalNum,c.trade_no,c.wfStatus4,c.wfStatus1,g.pricetype ");
        sql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
        if ("02".equals(pl) || "".equals(pl)) {
            sql.append(",DT_ORDER_BILL_ADD ord");
        }
        sql.append(" where g.ppid=p.ppid ");
        if ("02".equals(pl) || "".equals(pl)) {
            sql.append(" and ord.OA_BILL_ID=c.cashierbillsid");
        }
        sql.append(" and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid in(");


        ptsql.append(" select pa.price,");
        ptsql.append(" pa.ppname,p.image,pa.ppid,pa.companyid,");
        ptsql.append(" pa.cashierbillsid,pa.standard,pa.cashierbillsid,");
        ptsql.append(" c.priceSub as ptcashid,c.fkStatus,c.status");
        ptsql.append(" from dt_productpackaging p,");
        ptsql.append("  dtcashierbills c,dt_promotionassociation pa");
        ptsql.append(" where pa.ppid = p.ppid");
        ptsql.append(" and c.cashierbillsid = pa.ptcashierbillsid ");
        ptsql.append(" and pa.cashierbillsid in(");

        List<Object> parmsforbillId = new ArrayList<Object>();

        //买家信息
        StringBuffer consql = new StringBuffer();
        List<Object> paramc = new ArrayList<Object>();
        List<Object[]> conlist = new ArrayList<Object[]>();
        consql.append("select t.sccid,t.account,sf.staffname from t_eshop_cuscom t ,dt_hr_staff sf where t.staffid= sf.staffid and t.sccid in (");

        List<Object[]> ptlist = null;
        if (pageForm != null && pageForm.getList() != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
                sql.append("?,");
                ptsql.append("?,");
                consql.append("?,");
                parmsforbillId.add(pb.getCashierBillsID());
                paramc.add(pb.getSccid());
            }
            goodhql = sql.toString();
            goodhql = sql.substring(0, sql.length() - 1) + ")";

            psql = ptsql.toString();
            psql = ptsql.substring(0, ptsql.toString().length() - 1) + ")";

            String con_sql = consql.substring(0, consql.toString().length() - 1) + ")";

            conlist = baseBeanService.getListBeanBySqlAndParams(con_sql, paramc.toArray());
            if(source==null||!source.equals("office")){
	            goodlist = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), parmsforbillId.toArray());
	            //促销品
	            ptlist = baseBeanService.getListBeanBySqlAndParams(psql, parmsforbillId.toArray());
            }
            // 物品填到pageform
            List<Object[]> glist = null;
            List<Object[]> plist = null;
            List<Object[]> clist = null;
            for (int i = 0; i < pageForm.getList().size(); i++) {
            	 PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
            	if(source==null||!source.equals("office")){
	            	//产品信息
	                glist = new ArrayList<Object[]>();
	                for (Object[] obj : goodlist) {
	                    if (pb.getCashierBillsID().equals(obj[9].toString())) {
	                        glist.add(obj);
	                    }
	                }
	                pb.setGoodsList(glist);
	                
	                //促销产品信息
	                plist = new ArrayList<Object[]>();
	                for (Object[] obj : ptlist) {
	                    if (pb.getCashierBillsID().equals(obj[5].toString())) {
	                        plist.add(obj);
	                    }
	                }
	                pb.setPtgoodsList(plist);
            	}
                //买家信息
                clist = new ArrayList<Object[]>();
                for (Object[] obj : conlist) {
                    if (pb.getSccid().equals(obj[0].toString())) {
                        clist.add(obj);
                    }
                }
                pb.setContactInfo(clist);
                pageForm.getList().set(i, pb);
            }
        }
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("flag",flag);
        map.put("pageForm", pageForm);
        return map;
    }

    /**
     * 单据详情 //只查看单据详情的 《BILL》带有地址 ADDRESS单独只查看地址的 我的订单Myorder我的订单详情页面
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public final String getCashBill() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String partem = request.getParameter("partem");
        if ("BILL".equals(parameter)) {

            StringBuffer goodhql = new StringBuffer();
            PhoneBill pb = (PhoneBill) baseBeanService.getBeanByHqlAndParams("from PhoneBill where cashierBillsID=?",
                    new Object[]{cashierBillsID});
            /***
             * 0:id 1:key 2:数量 3:单价 4:产品编号 5:产品名称 6:产品图片 7:产品id 8:供应商id 9:供货商名称
             * 10:物品id
             ***/
            goodhql.append("select g.goodsbillsid,g.goodsbillskey,");
            goodhql.append("g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.ccompanyid,c.ccompanyname,");
            goodhql.append("g.goodsID,p.companyId,g.standard,c.priceSub,g.remark,c.fkStatus,nvl(g.fhnum,g.quantity)");
            goodhql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
            goodhql.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid=?");
            List<Object[]> goodlist = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(),
                    new Object[]{pb.getCashierBillsID()});
            pb.setGoodsList(goodlist);
            request.setAttribute("pb", pb);
            goodhql.delete(0, goodhql.length());
            // 促销主产品
            goodhql.append("select DISTINCT g.goodsbillsid,g.goodsbillskey,");
            goodhql.append(" g.quantity,g.price,g.goodsnum,");
            goodhql.append(" g.goodsname,p.image,g.ppid,g.goodsid,");
            goodhql.append(" g.companyid,g.cashierbillsid,g.standard,");
            goodhql.append(" pa.cashierbillsid as ptcashid,c.fkStatus");
            goodhql.append(" from dtgoodsbills g, dt_productpackaging p, dtcashierbills c,dt_promotionassociation pa");
            goodhql.append(" where g.ppid = p.ppid");
            goodhql.append(" and c.cashierbillsid = g.cashierbillsid");
            goodhql.append(" and c.cashierbillsid = pa.ptcashierbillsid");
            goodhql.append(" and pa.cashierbillsid =?");
            List<Object[]> list = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(),
                    new Object[]{pb.getCashierBillsID()});
            pb.setPtgoodsList(list);
            request.setAttribute("pt", pb);

            goodhql.delete(0, goodhql.length());
            goodhql.append(" select pp.ppid,pp.companyid,pp.goodsname,pp.quantity,pp.price,pp.image,pp.stocksize,pp.monthsales,");
            goodhql.append("pp.standard,pp.shangjiastatus,pp.logo,pp.photo,pp.tradecode,pp.tradename,pp.tradeid,pp.brand,pp.goodsID");
            goodhql.append(" from dt_productpackaging pp where pp.goodsid in  ");
            goodhql.append("(select d.goodsid from dt_inv_invt d where  d.warehouse in (select d.depotid from ");
            goodhql.append("dtDepotManage d where d.companyid=? and d.depotname = '销售库')) and ");
            goodhql.append("pp.yjstatus='01' and pp.showweixin='01' and pp.companyid= ? and rownum<7");

            List<BaseBean> cplist = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(),
                    new Object[]{pb.getCompanyid(), pb.getCompanyid()});
            request.setAttribute("cplist", cplist);

            CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(
                    "from CashierBills where cashierBillsID = ?", new Object[]{pb.getCashierBillsID()});
            request.setAttribute("cash", cash);
            String hql = " from DtOrderBillAdd where oaBillJounum=?";
            DtOrderBillAdd orderBill = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql,
                    new Object[]{oaBillId});
            request.setAttribute("orderBill", orderBill);

            // 退货表
            String hqls = " from RefundSheet where cashierBillsID=?";
            RefundSheet refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hqls,
                    new Object[]{cashierBillsID});
            request.setAttribute("refundSheet", refundSheet);
        } else if ("ADDRESS".equals(parameter)) {
            String hql = " from DtOrderBillAdd where oaBillJounum=?";
            DtOrderBillAdd orderBill = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql,
                    new Object[]{oaBillId});
            request.setAttribute("orderBill", orderBill);
            return "updADDRESS";
        }

        if ("Myorder".equals(partem)) {/** 我的订单详情页面 ***/
            return "Myorder";
        } else if ("Receipt".equals(partem)) {/** 收货单页面的详情页面带有地址 */
            if ("11".equals(sta)) {/*** 查询待评价的产品 ***/
                String hql = " from Comments where cashierBillsID=?";
                Comments coments = (Comments) baseBeanService.getBeanByHqlAndParams(hql,
                        new Object[]{cashierBillsID});
                request.setAttribute("com", coments);
            }
            return "ReceiptOrder";
        } else if ("myReturnOrder".equals(partem)) {/*** 退货单页面的详情页面带有地址* */
            return "myReturnOrder";
        } else if ("OrderDetails".equals(partem)) {/** 退货单订单详情 **/
            return "OrderDetails";
        }
        // 单个促销品查看
        return "CashBill";
    }

    /**
     * 修改用户收货地址
     */
    public final String updateAddress() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String oaKey = request.getParameter("oaKey");
        String staffid = request.getParameter("staffid");
        String sort = request.getParameter("sort");
        String hql = " from DtOrderBillAdd s where s.oaKey=? ";
        DtOrderBillAdd dtOrderBillAdd = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql,
                new Object[]{oaKey});
        String receivename = request.getParameter("receivename");
        String receivetel = request.getParameter("receivetel");
        String receiveaddress = request.getParameter("receiveaddress");
        dtOrderBillAdd.setReceivename(receivename);
        dtOrderBillAdd.setReceivetel(receivetel);
        dtOrderBillAdd.setReceiveaddress(receiveaddress);
        baseBeanService.update(dtOrderBillAdd);
        if(companyid!=null&&!companyid.equals("")){
            HttpServletResponse response = ServletActionContext.getResponse();
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String url = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?companyid="+companyid+"&staffid="+staffid+"&sort="+sort;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                logger.error("操作异常", e);
            }
            return null;
        }else{
            parameter = "BILL";
            return getCashBill();
        }
    }

    /**
     * 收货单
     */

    public final String getReceipt() {

        return "Receipt";
    }

    /**
     * 退货单
     */

    public final String getReturnOrder() {

        return "ReturnOrder";
    }

    /**
     * 收货单sta==00待评价sta==11交易成功
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public final String AjaxReceiptReturnOrder() {
        HttpSession sessions = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer customer = (TEshopCustomer) sw.getObject(sessions, SessionWrap.KEY_CUSTOMER);

        String stype = request.getParameter("type");// 退货单和收货单、标识
        String stypedate = request.getParameter("stypedate");// 退货中和退货结束的标识
        StringBuffer sql = new StringBuffer();
        List<Object> objsp = new ArrayList<Object>();
        if ("Receipt".equals(stype)) {// 收货单
            sql.append("select d.cashierBillsID,d.useraccount,d.companyName,");
            sql.append(" t.journalNum,d.state,");
            sql.append(" d.csid,to_char(d.orderDate,'YYYY-MM-DD HH24:MI:SS'),");
            sql.append(" d.consigneeName,d.consigneeAddress,");
            sql.append(" to_char(d.consignneDate,'YYYY-MM-DD HH24:MI:SS'),to_char(d.sendDate,'YYYY-MM-DD HH24:MI:SS'),t.priceSub,d.cskey,t.companyID,t.wlyf,t.standard");
            sql.append(" from DTCONSIGNEESHEET d,DTCASHIERBILLS t");
            sql.append(" where d.companyID=? and d.state=? ");
            sql.append(" and t.cashierBillsID = d.cashierBillsID order by d.orderDate desc");
            objsp.add(companyid);
            objsp.add(sta);
        } else {// 退货单
            sql.append(" select dt.cashierBillsID, ");
            sql.append(" dt.refundCode,dt.companyID,dt.companyName,");
            sql.append(" to_char(dt.orderDate,'YYYY-MM-DD HH24:MI:SS'),dt.refundType,t.journalNum");
            sql.append(",t.trade_no,t.wfStatus4,dt.refundstate,t.wfStatus1");
            sql.append(" from dtRefundSheet dt,DTCASHIERBILLS t");
            sql.append(" where dt.cashierBillsID = t.cashierBillsID");
            sql.append(" and dt.companyID=? and ");
            objsp.add(companyid);// 00
            if ("return".equals(stypedate)) {
                sql.append(" dt.refundstate!=? and dt.refundstate!=? ");
                sql.append(" order by dt.orderDate desc");
                objsp.add(status);// 00
                objsp.add(status2);// 03
            }

            if ("returnend".equals(stypedate)) {
                sql.append(" (dt.refundstate=? or dt.refundstate=?  )");
                sql.append(" order by dt.orderDate desc ");
                objsp.add(status);// 01
                objsp.add(status2);// 05
            }

        }


        pageForm = baseBeanService.getPageFormBySQL(pageNumber, 4, sql.toString(),
                "select count(*) from (" + sql.toString() + ")", objsp.toArray());

        StringBuffer sqlo = new StringBuffer();
        sqlo.append("select d.image,g.goodsName,g.price,g.quantity,g.price,g.goodsNum,g.billsNumbers,");
        sqlo.append("d.ppid,g.standard ,g.pricetype from dt_ProductPackaging d,dtGoodsBills g");
        sqlo.append(" where g.cashierBillsID = ? and d.ppID=g.ppID");

        map1 = new HashMap<String, Object>();
        List<BaseBean> goodlist = null;
        if (pageForm != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object ff = (Object) pageForm.getList().get(i);
                Object[] obj = (Object[]) ff;
                goodlist = baseBeanService.getListBeanBySqlAndParams(sqlo.toString(),
                        new Object[]{obj[0].toString()});
                map1.put(obj[0] + "", goodlist);
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("mapp", map1);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 同意退货/
     */
    public final String agreetoReturn() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String oaBillId = request.getParameter("oaBillId");
        String types = request.getParameter("types");
        String hql = " from DtOrderBillAdd where oaBillId = ?";
        DtOrderBillAdd dtOrderBillAdd = (DtOrderBillAdd)
                baseBeanService.getBeanByHqlAndParams(hql,
                        new Object[]{oaBillId});

        String hqls = " from RefundSheet where cashierBillsID=?";
        String hqlp = " from CashierBills where cashierBillsID=?";
        CashierBills cashierBills = (CashierBills)
                baseBeanService.getBeanByHqlAndParams(hqlp,
                        new Object[]{oaBillId});

        RefundSheet refundSheet = (RefundSheet)
                baseBeanService.getBeanByHqlAndParams(hqls,
                        new Object[]{dtOrderBillAdd.getOaBillId()});
        String status = cashierBills.getCkStatus();
        String str = null;
        if ("01".equals(types)) {
            cashierBills.setFkStatus("06");
            refundSheet.setRefundstate("01");
            refundSheet.setRefundAddress(dtOrderBillAdd.getSendaddress());// 地址
            refundSheet.setPostcode(dtOrderBillAdd.getSendcode());// 邮编
            refundSheet.setReceiverTel(dtOrderBillAdd.getSendtel());// 电话
            refundSheet.setReceiverName(dtOrderBillAdd.getSendname());// 姓名
            if (fkStatus != null) {
                if ("00".equals(fkStatus) || "01".equals(fkStatus) || "02".equals(fkStatus)) {
                    str = getReturn();
                } else {
                    str = "01";
                }
            }
        } else if ("02".equals(types)) {

            cashierBills.setFkStatus(status);
            refundSheet.setRefundstate("02");//拒绝退货
            refundSheet.setAccountstatus("02");//改单子关闭（作废）
            str = "02";
        }

        baseBeanService.update(refundSheet);

        request.setAttribute("dtOrderBillAdd", dtOrderBillAdd);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("map", str);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 拒绝,同意/退款
     */
    public final String refusetoReturn() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String oaBillId = request.getParameter("oaBillId");
        String types = request.getParameter("types");
        String hqls = " from RefundSheet where cashierBillsID=?";
        String hqlp = " from CashierBills where cashierBillsID=?";

        CashierBills cashierBills = (CashierBills)
                baseBeanService.getBeanByHqlAndParams(hqlp,
                        new Object[]{oaBillId});
        RefundSheet refundSheet = (RefundSheet)
                baseBeanService.getBeanByHqlAndParams(hqls, new Object[]{oaBillId});

        String status = cashierBills.getCkStatus();
        List<BaseBean> list = new ArrayList<BaseBean>();
        String str = null;
        // 01//成功02失败
        if ("01".equals(types)) {
            if (fkStatus != null && "16".equals(fkStatus)) {
                str = getReturn();
                if ("01".equals(str)) {

                    //退款流程结束
                    cashierBills.setFkStatus("18");

                    //卖家已银行打款
                    refundSheet.setRefundstate("05");
                } else {
                    //失败状态改回初始状态
                    cashierBills.setFkStatus("16");
                    refundSheet.setRefundstate("00");
                    str = "02";
                }
            }
        } else if ("02".equals(types)) {
            cashierBills.setFkStatus(status);
            refundSheet.setRefundstate("02");//拒绝退货
            refundSheet.setAccountstatus("02");
            str = "02";
        }
        list.add(cashierBills);
        list.add(refundSheet);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("map", str);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

    @SuppressWarnings("unused")
	public String getReturn() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String str = null;
        //退钱
        String typel = request.getParameter("typel");//区分什么支付购买的
        //typel01支付宝02微信03银联
        if ("01".equals(typel)) {//支付宝
            /**
             * out_trade_no<字符长度64>订单支付时传入的商户订单号,不能和 trade_no同时为空。
             * trade_no<字符长度64>支付宝交易号，和商户订单号不能同时为空
             * refund_amount<字符长度9>需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
             * refund_reason<字符长度256>退款的原因说明
             * out_request_no<字符长度64>标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
             * */
            String out_trade_no = request.getParameter("out_trade_no");
            String trade_no = request.getParameter("trade_no");
            String refund_amount = request.getParameter("refund_amount");
            String refund_reason = "正常退款";
            log.error("订单号:" + out_trade_no + "&支付宝交易号:" + trade_no + "&退款金额:" + refund_amount + "&退款原因:正常退款");
            String af = alipayRefund.refund(out_trade_no, trade_no, refund_amount, refund_reason);
            if (af == "成功") {
                str = "01";
            } else {
                str = "02";
            }

        } else if ("00".equals(typel)) {//微信

            WxPayDto tpWxPayDto = new WxPayDto();
            String refundno = request.getParameter("refundCode");
            String orderId = request.getParameter("out_trade_no");
            String totalFee = request.getParameter("refund_amount");
            String refundfee = request.getParameter("refund_amount");
            String wfStatus1 = request.getParameter("wfStatus1");

            tpWxPayDto.setRefundno(refundno);// 退款单号
            tpWxPayDto.setOrderId(orderId);// 商户订单号  测试数据：
            tpWxPayDto.setTotalFee(totalFee);// 总金额 0.1
            tpWxPayDto.setRefundfee(refundfee);// 退款金额0.1

            //判断微信支付方式 00微信公众号支付 01微信app支付
         if ("00".equals(wfStatus1)) {
               tpWxPayDto.setWechatbz("wxa1b3f84c027804c3");
            } else if ("01".equals(wfStatus1)) {
               tpWxPayDto.setWechatbz("apppay");
           }


            log.error("退款单号:" + refundno + "&订单号:" + orderId + "&退款总金额:" + totalFee + "&退款金额:" + refundfee + "&微信支付方式:" + wfStatus1);

            List<WxPayDto> wxPayDtoList = transferService.getRefundInfo(tpWxPayDto);

              if(wxPayDtoList==null){
                  WxPayDto payDto = WchatPay.wechatRefund(tpWxPayDto);
                  if ("SUCCESS".equals(payDto.getReturn_code()) && "OK".equals(payDto.getReturn_msg())) {
                      str = "01";
                  } else {
                      str = "02";
                  }
              }else{
                  String refund_id = HTTPV3.refunds(wxPayDtoList);

                  if (refund_id!=null&&!refund_id.equals("")) {
                      str = "01";
                  } else {
                      str = "02";
                  }

              }

        } else if ("02".equals(typel)) {//银联
            /*String morre = "1.0";
            String ddid = request.getParameter("ddid");
            ;
            String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            *//*morre=new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
            if(morre.contains(".")){
				morre=morre.substring(0, morre.lastIndexOf("."));
			}*//*
            String url = "test.impf2010.com";
            unionpayMeth.consumeUndo(url
                    + "/ea/seller/ea_syncPay.jspa?journalNum=" + ddid, url
                    + "/ea/seller/ea_asyncPay.jspa?journalNum=" + ddid, "0", ddid, "1.0");*/
        }
        return str;
    }

    public final String getConfirmReceipt() {

        return "Receipt";

    }


    public String gotoSave() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String rsid = request.getParameter("rsid");
        String hql = "from RefundSheet where rsid = ?";
        RefundSheet rs = (RefundSheet) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{rsid});
        rs.setRemitDate(new Date());
        generatelogpushsheet(rs.getCashierBillsID(), rs.getPpid(), rs);
        baseBeanService.update(rs);


        return "";
    }

    /*
     *
     * 生成物流出库单
     */
    @SuppressWarnings("unused")
    public void generatelogpushsheet(String cashierBillsID, String ppID,
                                     RefundSheet rs) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<BaseBean> list = new ArrayList<BaseBean>();

        String hql1 = " from TEshopCusCom where staffid = ?";
        tEshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{staffId});
        /**
         * 生成单据
         */
        // 单据表
        CashierBills ca = new CashierBills();
        ca.setCashierBillsID(serverService.getServerID("cashierTally"));
        ca.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        ca.setCompanyID(tEshopCusCom.getCompanyId());
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
        fin.setCompanyID(tEshopCusCom.getCompanyId());
        fin.setOrganizationID(tEshopCusCom.getOrganizationID());
        fin.setDepotID(depot.getDepotID());
        fin.setDepotName(depot.getDepotName());
        fin.setStaffsID(rs.getReceiverID()); // 接收人Id
        fin.setStaffsName(rs.getReceiverName()); // 接收人Name
        fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
        fin.setBillsDate(new Date());
        fin.setBillStaffID(tEshopCusCom.getStaffid());
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

            logger.error("操作异常", e);
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
     * 保存拣货出库单
     * @return
     */
	public String saveSorting(){
    	String flag="操作成功";
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String cashid=request.getParameter("cashid");
    	try {
    		transferService.saveSorting(cashid);
		} catch (Exception e) {
			logger.error("操作异常", e);
			flag="操作失败";
		}
    	
    	Map<String, String> map=new HashMap();
    	map.put("flag", flag);
    	JSONObject oj = JSONObject.fromObject(map);
    	result=oj.toString();
    	return "success";
    }

    /**
     * 拣货出库单列表
     * @param comid
     * @return
     */
    public Map<String, Object> getDeliveryList(String comid,String statusType,String parameter){
        List<Object> parms=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("SELECT T.ORDERID,T.JOURNALNUM,T.DELIVERYNUM,to_char(S.XDDATE,'YYYY-MM-DD HH24:MI:SS'),to_char(T.ADDDATE,'YYYY-MM-DD HH24:MI:SS'),");
		sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
		sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,T.PURCHASERNAME,T.pichingname,T.STATUS");
		sql.append(" FROM DT_DELIVERY T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S");
		sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID=S.CASHIERBILLSID");
		if(parameter!=null&&!parameter.equals("")){
            sql.append(" AND t.PURCHASERNAME like ?");
            sql.append(" AND t.JOURNALNUM like ?");
            parms.add(parameter);
            parms.add(parameter);
        }
		sql.append(" AND t.status=?");
		sql.append(" AND T.COMPANYID = ?");
        sql.append(" order by t.adddate desc");
		parms.add(statusType);
		parms.add(comid);
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql.toString(),"select count(1) from ("
						+ sql.toString() +" )", parms.toArray());
		Map<String,Object> map =new HashMap<String,Object>();
        //map.put("flag",flag);
        map.put("pageForm", pageForm);
        return map;
	}

    /**
     * 拣货出库单ajax调用
     * @return
     */
    public String getDeliveryAjax() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String statusType=request.getParameter("statusType");
        String parameter=request.getParameter("parameter");
        Map<String, Object> map =  getDeliveryList(companyid,statusType,parameter);
        JsonConfig j = new JsonConfig();
        j.setIgnoreDefaultExcludes(false);
        j.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject obj = JSONObject.fromObject(map, j);
        result = obj.toString();
        return "success";
    }

    /**
     * 拣货出库单详情
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getDeliveryDetails(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String comid=request.getParameter("comid");
    	String orderid=request.getParameter("ordid");
    	StringBuffer sql=new StringBuffer("SELECT T.ORDERID,T.JOURNALNUM,T.DELIVERYNUM,S.XDDATE,T.ADDDATE,");
		sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
		sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,");
		sql.append("T.PURCHASERNAME,T.pichingname,t.sellermessage,T.STATUS,C.PRICESUB");
		sql.append(" FROM DT_DELIVERY T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S,DTCASHIERBILLS C");
		sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID=S.CASHIERBILLSID");
        sql.append(" AND T.CASHIERBILLSID=C.CASHIERBILLSID");
		sql.append(" AND T.ORDERID=?");
		//sql.append(" AND T.COMPANYID = ?");
		Object ordObj=baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{orderid});
		
		request.setAttribute("ordobj", ordObj);
        sql = new StringBuffer("select dg.deliveryid,dg.goodname,p.variableid,dg.ordernum,dg.quantity,dg.error,dg.unitprice,");
        sql.append("dg.totalprices,p.ppid,p.isscale,p.barcode from dt_deliver_goods dg ");
        sql.append(" left join dt_productpackaging p on dg.ppid = p.ppid");
        sql.append(" where dg.orderid=?");
		List<BaseBean> goodsList=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{orderid});
    	request.setAttribute("gl", goodsList);
		return "deliveryDetails";
    }
    
	/**
	 * 拣货出库逻辑处理
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String DeliveryLogicalProcessing(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String content=request.getParameter("jsonval");
    	String orderid=request.getParameter("orderid");
        String staffid=request.getParameter("staffid");
    	try {
    		String flag=transferService.DeliveryLogicalProcessing(content, orderid,staffid);
        	Map<String, String> map=new HashMap();
        	map.put("flag", flag);
        	JSONObject oj = JSONObject.fromObject(map);
        	result=oj.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
    	return "success";
    }
    
    /**
     * 发货单ajax调用
     * @return
     */
    public String getSendAjax() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String statusType=request.getParameter("statusType");
        String parameter=request.getParameter("parameter");
        Map<String, Object> map = new HashMap<String, Object>();
        map = getSendList(companyid,statusType,parameter);
        JsonConfig j = new JsonConfig();
        j.setIgnoreDefaultExcludes(false);
        j.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject obj = JSONObject.fromObject(map, j);
        result = obj.toString();
        return "success";
    }
    
    /**
     * 发货单列表
     * @param comid
     * @return
     */
    public Map<String, Object> getSendList(String comid,String statusType,String parameter){
        List<Object> parms=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("SELECT T.SENDID,T.JOURNALNUM,T.SENDNUM,");
		sql.append("TO_CHAR(S.XDDATE, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(T.ADDDATE, 'YYYY-MM-DD HH24:MI:SS'),");
		sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
		sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,");
		sql.append("T.PURCHASERNAME,T.pichingname,T.STATUS");
		sql.append(" FROM DT_SEND_BILL T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S");
		sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID = S.CASHIERBILLSID");
        if(parameter!=null&&!parameter.equals("")){
            sql.append(" AND t.PURCHASERNAME like ?");
            sql.append(" AND t.JOURNALNUM like ?");
            parms.add(parameter);
            parms.add(parameter);
        }
        sql.append(" AND T.STATUS = ? AND T.COMPANYID =?");
        sql.append(" order by t.adddate desc");
		parms.add(statusType);
		parms.add(comid);
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql.toString(),"select count(1) from ("
						+ sql.toString() +" )", parms.toArray());
		Map<String,Object> map =new HashMap<String,Object>();
        //map.put("flag",flag);
        map.put("pageForm", pageForm);
        return map;
	}

    /**
     * 发货单详情
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getSendGoods (){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String comid=request.getParameter("comid");
    	String sendid=request.getParameter("sendid");
        StringBuffer sql=new StringBuffer("SELECT T.SENDID,T.JOURNALNUM,T.SENDNUM,S.XDDATE,T.ADDDATE,");
        sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
        sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,T.PURCHASERNAME,T.pichingname,T.STATUS,");
        sql.append("O.RECEIVENAME,O.RECEIVETEL,O.RECEIVEADDRESS,C.PRICESUB");
        sql.append(" FROM DT_SEND_BILL T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S,DT_ORDER_BILL_ADD O,DTCASHIERBILLS C");
        sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
        sql.append(" AND T.CASHIERBILLSID=S.CASHIERBILLSID");
        sql.append(" AND T.CASHIERBILLSID=O.OA_BILL_ID");
        sql.append(" AND T.CASHIERBILLSID=C.CASHIERBILLSID");
        sql.append(" AND T.SENDID=?");
		Object ordObj=baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{sendid});
		
		request.setAttribute("ordobj", ordObj);
        sql = new StringBuffer("select dg.sendgoodid,dg.goodname,p.variableid,dg.ordernum,dg.quantity,dg.error,dg.unitprice,");
        sql.append("dg.totalprices,p.ppid,p.isscale,p.barcode from dt_send_goods dg");
        sql.append(" left join dt_productpackaging p on dg.ppid = p.ppid");
        sql.append(" where dg.SENDID=?");
		List<BaseBean> goodsList=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{sendid});
    	request.setAttribute("gl", goodsList);
		return "SendGoods";
    }

    /**
	 * 发货逻辑处理
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String SendLogicalProcessing(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String sendid=request.getParameter("sendid");
        String staffid=request.getParameter("staffid");
    	try {
    		String flag=transferService.SendLogicalProcessing(sendid,staffid);
        	Map<String, String> map=new HashMap();
        	map.put("flag", flag);
        	JSONObject oj = JSONObject.fromObject(map);
        	result=oj.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
    	return "success";
    }
    
    /**
     * 送货单ajax调用
     * @return
     */
    public String getTransportAjax() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String statusType=request.getParameter("statusType");
        String parameter=request.getParameter("parameter");
        Map<String, Object> map = new HashMap<String, Object>();
        map = getTransportList(companyid,statusType,parameter);
        JsonConfig j = new JsonConfig();
        j.setIgnoreDefaultExcludes(false);
        j.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject obj = JSONObject.fromObject(map, j);
        result = obj.toString();
        return "success";
    }
    
    /**
     * 送货单列表
     * @param comid
     * @return
     */
    public Map<String, Object> getTransportList(String comid,String statusType,String parameter){
        List<Object> parms=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("SELECT T.TRANSPORTID,T.JOURNALNUM,T.TRANSPORTNUM,");
		sql.append("TO_CHAR(S.XDDATE, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(T.ADDDATE, 'YYYY-MM-DD HH24:MI:SS'),");
		sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
		sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,");
		sql.append("T.PURCHASERNAME,T.T.pichingname,STATUS");
		sql.append(" FROM DT_TRANSPORT_BILL T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S");
		sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID = S.CASHIERBILLSID" );
        if(parameter!=null&&!parameter.equals("")){
            sql.append(" AND t.PURCHASERNAME like ?");
            sql.append(" AND t.JOURNALNUM like ?");
            parms.add(parameter);
            parms.add(parameter);
        }
        sql.append(" AND T.STATUS = ? AND T.COMPANYID =?");
        sql.append(" order by t.adddate desc");
		parms.add(statusType);
		parms.add(comid);
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql.toString(),"select count(1) from ("
						+ sql.toString() +" )", parms.toArray());
		Map<String,Object> map =new HashMap<String,Object>();
        //map.put("flag",flag);
        map.put("pageForm", pageForm);
        return map;
	}

    /**
     * 送货单详情
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getTransportGoods(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String comid=request.getParameter("comid");
    	String reansportid=request.getParameter("reansportid");
    	StringBuffer sql=new StringBuffer("SELECT T.TRANSPORTID,T.JOURNALNUM,T.TRANSPORTNUM,S.XDDATE,T.ADDDATE,");
		sql.append("SUBSTR(CBAT.MAPVAL,INSTR(CBAT.MAPVAL, '-', 1, 1) + 1,");
		sql.append("INSTR(CBAT.MAPVAL, '-', 1, 2) - INSTR(CBAT.MAPVAL, '-', 1, 1) - 1) CGS_COMNAME,T.PURCHASERNAME,T.pichingname,T.STATUS,");
		sql.append("O.RECEIVENAME,O.RECEIVETEL,O.RECEIVEADDRESS,C.PRICESUB");
		sql.append(" FROM DT_TRANSPORT_BILL T, DT_CB_A_TRANSFERPAY CBAT, DT_STATUS S,DT_ORDER_BILL_ADD O,DTCASHIERBILLS C");
		sql.append(" WHERE T.CASHIERBILLSID = CBAT.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID=S.CASHIERBILLSID");
		sql.append(" AND T.CASHIERBILLSID=O.OA_BILL_ID");
        sql.append(" AND T.CASHIERBILLSID=C.CASHIERBILLSID");
		sql.append(" AND T.TRANSPORTID=?");
		Object ordObj=baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{reansportid});
		
		request.setAttribute("ordobj", ordObj);
        sql = new StringBuffer("select dg.tgoodid,dg.goodname,p.variableid,dg.ordernum,dg.quantity,dg.error,dg.unitprice,");
        sql.append("dg.totalprices,p.ppid,p.isscale,p.barcode from dt_transport_goods dg");
        sql.append(" left join dt_productpackaging p on dg.ppid = p.ppid");
        sql.append(" where dg.transportid=?");
		List<BaseBean> goodsList=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{reansportid});
    	request.setAttribute("gl", goodsList);
		return "transporGoods";
    }

    /**
	 * 送货逻辑处理
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String TransportLogicalProcessing(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String transportid=request.getParameter("transportid");
    	String Waybillno = request.getParameter("Waybillno");
        String ExCode = request.getParameter("ExCode");
        String staffid=request.getParameter("staffid");
        String flag="00";
    	try {
    		flag=transferService.TransportLogicalProcessing(transportid,Waybillno,ExCode,staffid);
		} catch (Exception e) {
			flag="01";
			logger.error("操作异常", e);
		}
    	Map<String, String> map=new HashMap();
    	map.put("flag", flag);
    	JSONObject oj = JSONObject.fromObject(map);
    	result=oj.toString();
    	return "success";
    }

    public String getBuyerOrder(){
	    return "BuyerOrder";
    }
    /**
     * 新版上拉下拉加载
     */
    public String getBuyerAjax() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String source=request.getParameter("source");
        Map<String, Object> map = new HashMap<String, Object>();
        map = getBuyerList(companyid, pl,source);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    /**
     * 单据列表
     *
     * @param staid 公司id
     * @param pl    单据跟踪状态
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getBuyerList(String staid,String pl,String source) {
        DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class);
        dc.add(Restrictions.eq("cgscomid", staid));
        if(parameter!=null&&!parameter.equals("")){
            dc.add(Restrictions.or(Restrictions.like("journalNum",parameter),
                    Restrictions.like("companyName",parameter)));
        }
    	if (pl != null && !pl.equals("")) {
    	    if(pl.equals("01")){
                dc.add(Restrictions.or(Restrictions.eq("paystatus", "01"),Restrictions.eq("paystatus","02")));
            }else{
                dc.add(Restrictions.eq("paystatus", pl));
            }
        }
        dc.addOrder(Order.desc("cashierdate"));
        try {
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 4, dc);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("pageForm", pageForm);
        return map;
    }

    /**
     * 保存欠款单保存地址
     * @return
     */
    public String getOverdraftAjax(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashid=request.getParameter("cashid");
        String raddressId=request.getParameter("raddressId");
        String falg="00";//结果种类  00 成功  01生成失败 02欠款单重复生成
        try {
            falg=transferService.addOverdraft(cashid,raddressId,falg);
        }catch (Exception e){
            falg="01";
            logger.error("操作异常", e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("falg",falg);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    /**
     * 保存地址
     * @return
     */
    public String addAddress(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashid=request.getParameter("cashid");
        String raddressId=request.getParameter("raddressId");
        String falg="00";//结果种类  00 成功  01添加失败
        try {
            transferService.addAddress(cashid,raddressId);
        }catch (Exception e){
            falg="01";
            logger.error("操作异常", e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("falg",falg);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    public String getGoodsnameAjax(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashid=request.getParameter("cashid");
        Object goodname="";
        try {
            String sql="select wm_concat(g.goodsname) from dtgoodsbills g where g.cashierbillsid=?";
            goodname=baseBeanService.getObjectBySqlAndParams(sql,new Object[]{cashid});
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodname",goodname);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getLjly() {
        return ljly;
    }

    public void setLjly(String ljly) {
        this.ljly = ljly;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public Map<String, Object> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, Object> map1) {
        this.map1 = map1;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSta() {
        return sta;
    }

    public void setSta(String sta) {
        this.sta = sta;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getOaBillId() {
        return oaBillId;
    }

    public void setOaBillId(String oaBillId) {
        this.oaBillId = oaBillId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String eBusinessID) {
        EBusinessID = eBusinessID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getMorre() {
        return morre;
    }

    public void setMorre(String morre) {
        this.morre = morre;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public TEshopCusCom gettEshopCusCom() {
        return tEshopCusCom;
    }

    public void settEshopCusCom(TEshopCusCom tEshopCusCom) {
        this.tEshopCusCom = tEshopCusCom;
    }

    public String getFkStatus() {
        return fkStatus;
    }

    public void setFkStatus(String fkStatus) {
        this.fkStatus = fkStatus;
    }


}
