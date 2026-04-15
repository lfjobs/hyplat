package hy.ea.invoicing.action;

import com.tiantai.telrec.tool.JsonDateValueProcessor;
import hy.ea.bo.Company;
import hy.ea.bo.finance.BenDis.ConsigneeSheet;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.invoicing.Rukubill;
import hy.ea.bo.invoicing.TransportBill;
import hy.ea.bo.invoicing.TransportGoods;
import hy.ea.invoicing.service.RukuBillService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021-03-18.
 */
public class RukuBillAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private RukuBillService rukuBillService;
    private String companyid;
    private String sccid;
    private ProductPackaging p;
    private String result;

    private String parameter;// 前台页面传参判断使用

    private PageForm pageForm;

    /**
     * 新版上拉下拉加载
     */
    public String getRukuAjax() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("compayid");
        String staffid = request.getParameter("staffid");
        String type=request.getParameter("type");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if(type!=null){
                if (type.equals("Ruku")){
                    map = rukuBillService.getInitializeList(companyid, parameter, pageForm);
                }else if (type.equals("Bill")){
                    map = rukuBillService.getBillList(staffid, parameter, pageForm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
        JSONObject obj = JSONObject.fromObject(map, jsonConfig);
        result = obj.toString();
        return "success";
    }

    /**
     * 扫码查询产品
     */
    public String getProductByComid() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String barcode = request.getParameter("barcode");
        String ppid = request.getParameter("ppid");
        Object depotid = baseBeanService.getObjectBySqlAndParams(
                "select depotID from dtdepotmanage where depotname=? and companyID = ? and depotState=? ", new Object[]{"销售库", companyid, "00"});
        request.setAttribute("depotid", depotid);
        if (ppid != null && ppid != "") {
            String sql = "from ProductPackaging where ppid=?";
            p = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{ppid});
        } else {
            String sql = "from ProductPackaging where barCode=? and companyID=?";
            p = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{barcode, companyid});
        }
        if (p == null) {
            String sql = "from ProductPackaging where barCode=? and rownum=1";
            p = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{barcode});
            if (p != null) {
                return "pro";
            } else {
                return "toProducts";
            }
        } else {
            return "pro";
        }
    }

    /**
     * 扫码查询单据
     */
    public String getBill() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String barcode = request.getParameter("barcode");
        String sql = "from CashierBills where journalNum=?";
        CashierBills ca = (CashierBills) baseBeanService.getBeanByHqlAndParams(sql, new Object[]{barcode});
        List<Object[]> ol = new ArrayList<Object[]>();
        if (ca != null) {
            String gsql = "from GoodsBills where cashierBillsID=?";
            List<BaseBean> gb = baseBeanService.getListBeanByHqlAndParams(gsql, new Object[]{ca.getCashierBillsID()});
            if (gb != null && gb.size() > 0) {
                for (int i = 0; i < gb.size(); i++) {
                    GoodsBills g = (GoodsBills) gb.get(i);
                    ol.add(new Object[]{g.getGoodsID(), g.getPpID(), g.getGoodsName(), g.getQuantity()});
                }
            }
            request.setAttribute("ghsid", ca.getCompanyID());
            request.setAttribute("ghsname", ca.getCompanyName());
            request.setAttribute("journalNum", ca.getJournalNum());
            request.setAttribute("cid",ca.getCashierBillsID());
            request.setAttribute("ol", ol);
        } else {
            String csql = "from CONSIGNEESHEET where consigneCode=?";
            ConsigneeSheet cs = (ConsigneeSheet) baseBeanService.getBeanByHqlAndParams(csql, new Object[]{barcode});
            if (cs != null) {
                String gsql = "from GoodsBills where cashierBillsID=?";
                List<BaseBean> gb = baseBeanService.getListBeanByHqlAndParams(gsql, new Object[]{cs.getCashierBillsID()});
                if (gb != null && gb.size() > 0) {
                    for (int i = 0; i < gb.size(); i++) {
                        GoodsBills g = (GoodsBills) gb.get(i);
                        ol.add(new Object[]{g.getGoodsID(), g.getPpID(), g.getGoodsName(), g.getQuantity()});
                    }
                }
                request.setAttribute("ghsid", cs.getCompanyID());
                request.setAttribute("ghsname", cs.getCompanyName());
                request.setAttribute("journalNum", cs.getOrderCode());
                request.setAttribute("csid",cs.getCsid());
                request.setAttribute("ol", ol);
            }else {
                String tsql = "from TransportBill where journalnum=?";
                TransportBill tb = (TransportBill) baseBeanService.getBeanByHqlAndParams(tsql, new Object[]{barcode});
                if (tb != null) {
                    String tgsql = "from TransportGoods where transportid=?";
                    List<BaseBean> tgb = baseBeanService.getListBeanByHqlAndParams(tgsql, new Object[]{tb.getTransportid()});
                    if (tgb != null && tgb.size() > 0) {
                        for (int i = 0; i < tgb.size(); i++) {
                            TransportGoods t = (TransportGoods) tgb.get(i);
                            ol.add(new Object[]{t.getGoodsid(), t.getPpid(), t.getGoodname(), t.getQuantity()});
                        }
                    }
                    request.setAttribute("ghsid", tb.getPurchaserid());
                    request.setAttribute("ghsname", tb.getPurchasername());
                    request.setAttribute("journalNum", tb.getTransportnum());
                    request.setAttribute("tid",tb.getTransportid());
                    request.setAttribute("ol", ol);
                }else{
                    request.setAttribute("flag","没有数据");
                }
            }
        }
        return "add";
    }

    public String getrukuBill() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String rkid = request.getParameter("rkid");
        Rukubill bill =(Rukubill) baseBeanService.getBeanByHqlAndParams("from Rukubill where rkid=?",new Object[]{rkid});
        if(bill!=null){
            Company c =(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?",new Object[]{bill.getCompanyid()});
            String gsql = "from RukuGoods where rkid=?";
            List<BaseBean> gb = baseBeanService.getListBeanByHqlAndParams("from RukuGoods where rkid=?", new Object[]{rkid});
            request.setAttribute("bill",bill);
            request.setAttribute("ghsid", bill.getCompanyid());
            request.setAttribute("ghsname", c.getCompanyName());
            request.setAttribute("jum", bill.getJournalnum());
            request.setAttribute("gb",gb);
        }
        return "add";
    }

    public String getQualityGuaranteePeriod() {
        List<BaseBean> scode = baseBeanService.getListBeanByHqlAndParams("from SCode where codePID=? order by codeNumber", new Object[]{"scode20210426t45k5q4em30000000003"});
        JSONObject json = new JSONObject();
        json.accumulate("scode", scode);
        result = json.toString();
        return "success";
    }

    /**
     * 获取供货商
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getCom() {
        HttpServletRequest request = ServletActionContext.getRequest();
        //String valString = request.getParameter("valString");
        StringBuilder sqlString = new StringBuilder("select c.companyid,c.companyname from dtcompany c");
        /*sqlString.append(" where c.ccompanyid = cc.ccompany_id and cc.compnay_id=t.companyid and c.companyname like ?");
        sqlString.append(" and t.account is not null and cc.compnay_id is not null and c.companyname is not null");*/
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(sqlString.toString(), null);
        JSONObject obj = new JSONObject();
        obj.accumulate("objvalue", list);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取采购商员工
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getStaff() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String valString = request.getParameter("valString");
        String comid = request.getParameter("comid");
        String hql = "select staffID,staffName from view_sc where companyID = ?";
        Object[] params = {comid};
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(hql, params);
        JSONObject obj = new JSONObject();
        obj.accumulate("objvalue", list);
        result = obj.toString();
        return "success";
    }

    /**
     * 入库
     * @return
     */
    public String ajaxSaveRuku() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String formjum = request.getParameter("billform");
        String serializeJson = request.getParameter("serializeJson");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            rukuBillService.SaveRuku(formjum, serializeJson);
            map.put("falg", "完成");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("falg", "失败");
        }
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public ProductPackaging getP() {
        return p;
    }

    public void setP(ProductPackaging p) {
        this.p = p;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }
}
