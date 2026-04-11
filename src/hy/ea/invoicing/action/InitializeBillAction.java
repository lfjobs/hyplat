package hy.ea.invoicing.action;

import com.tiantai.telrec.tool.JsonDateValueProcessor;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.*;
import hy.ea.invoicing.service.InitializeService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 初始库存
 */
public class InitializeBillAction {
	private static final Logger logger = LoggerFactory.getLogger(InitializeBillAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private InitializeService initializeService;
    private Logger log = LoggerFactory.getLogger(InitializeBillAction.class);
    private String parameter;// 前台页面传参判断使用
    private int pageNumber;

    private PageForm pageForm;
    private String result;

    /**
     * 新版上拉下拉加载
     */
    public String getInitializeAjax() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("compayid");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = initializeService.getInitializeList(companyid, parameter, pageForm);
        } catch (Exception e) {
            logger.error("操作异常", e);
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
     * 查询责任人
     *
     * @return
     */
    public String getStaff() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String staffid = request.getParameter("staffid");
        String companyid = request.getParameter("compayid");
        StringBuilder sqlStr = new StringBuilder("from Staff where staffid=?");
        Staff s = (Staff) baseBeanService.getBeanByHqlAndParams(sqlStr.toString(), new Object[]{staffid});
        String jum = serverService.getBillID(companyid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("staffname", s.getStaffName());
        map.put("jum", jum);
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    /**
     * 查询产品
     *
     * @return
     */
    public String getProduct() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String companyid = request.getParameter("compayid");
            String barcode = request.getParameter("barcode");
            String ppid = request.getParameter("ppid");
            List<String> params = new ArrayList<String>();
            StringBuilder sqlStr = new StringBuilder("select p.ppid,p.goodsid,p.goodsname, i.invenquantity,p.variableid," +
                    "p.brand,cc.codeid,ccc.codeValue,p.yjstatus,p.wholesaleStatus");
            sqlStr.append(" from dt_productpackaging p");
            sqlStr.append(" left join dt_ProCateRelate cc on cc.ppid = p.ppid");
            sqlStr.append(" left join dtccode ccc on ccc.codeID = cc.codeID");
            sqlStr.append(" left join dt_inv_invt i on p.ppid = i.productid where ");
            if (barcode != null && !barcode.equals("")) {
                sqlStr.append(" p.barcode = ? and ");
                params.add(barcode);
            } else if (ppid != null && !ppid.equals("")) {
                sqlStr.append(" p.ppid = ? and ");
                params.add(ppid);
            } else {
                map.put("error", "没有数据，请先录入数据");
                return "success";
            }
            sqlStr.append(" p.delStatus = ? ");
            params.add("00");
            sqlStr.append(" and p.companyid = ?");
            params.add(companyid);
            List<Object> objList = baseBeanService.getListBeanBySqlAndParams(sqlStr.toString(), params.toArray());
            Object depotid = baseBeanService.getObjectBySqlAndParams(
                    "select depotID from dtdepotmanage where depotname=? and companyID = ? and depotState=? ", new Object[]{"销售库", companyid, "00"});
            map.put("销售库", depotid);
            map.put("objList", objList);
        } catch (Exception e) {
            map.put("error", "数据错误");
            logger.error("操作异常", e);
        }
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }

    /**
     * 盘库仓库列表
     *
     * @return
     */
    public String depot() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("companyid", request.getParameter("compayid"));
        return "tree";
    }

    public String ajaxSaveInitialize() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String formjum = request.getParameter("formjum");
        String serializeJson = request.getParameter("serializeJson");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            initializeService.SaveInitialize(formjum, serializeJson);
            map.put("falg", "完成");
        } catch (Exception e) {
            logger.error("操作异常", e);
            map.put("falg", "失败");
        }
        JSONObject jsonArray = JSONObject.fromObject(map);
        result = jsonArray.toString();
        return "success";
    }


    /**
     * 新版上拉下拉加载
     */
    public String getGoodsAjax() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("compayid");
        String sdate = request.getParameter("sdate");
        String edate = request.getParameter("edate");
        String pemer = request.getParameter("pemer");
        String title = request.getParameter("title");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if ((sdate != null && !sdate.equals("") && edate != null && !edate.equals(""))
                    || (pemer != null && !pemer.equals("") && title != null && !title.equals(""))) {
                map = initializeService.getConditionQuery(companyid, sdate, edate, pemer, title, "P", pageForm, pageNumber);
            }

            /*JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                    new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
            JSONObject obj = JSONObject.fromObject(map,jsonConfig);
            result = jsonArray.toString();*/

            JSONObject jsonArray = JSONObject.fromObject(map);
            result = jsonArray.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    /**
     * 打印查询
     *
     * @return
     */
    public String getGoods() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("compayid");
        String sdate = request.getParameter("sdate");
        String edate = request.getParameter("edate");
        String pemer = request.getParameter("pemer");
        String title = request.getParameter("title");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if ((sdate != null && !sdate.equals("") && edate != null && !edate.equals(""))
                    || (pemer != null && !pemer.equals("") && title != null && !title.equals(""))) {
                map = initializeService.getConditionQuery(companyid, sdate, edate, pemer, title, "L", pageForm, pageNumber);
            }
            JSONObject jsonArray = JSONObject.fromObject(map);
            result = jsonArray.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    public String getListDepotmanageByPID() {
        // 调拨出库里面根据选择的公司来选择对应仓库
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("compayid");
        String depotID = request.getParameter("depotID");
        request.setAttribute("sort", request.getParameter("sort"));
        String hql = " from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
        Object[] params = {depotID, companyid};
        List<BaseBean> depotManagelist = baseBeanService.getListBeanByHqlAndParams(hql, params);
        request.setAttribute("depotManagelist", baseBeanService.getListBeanByHqlAndParams(hql, params));
        return "deopt";
    }

    public String getInitialize() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("companyid");
        String initializeid = request.getParameter("initializeid");
        InitializeBill ib = (InitializeBill) baseBeanService.getBeanByHqlAndParams
                ("from InitializeBill where initializeid=?", new Object[]{initializeid});
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams
                ("from InitializeGoods where initializeid=?", new Object[]{initializeid});
        request.setAttribute("bill", ib);
        request.setAttribute("goodslist", list);
        return "initialize";
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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
}
