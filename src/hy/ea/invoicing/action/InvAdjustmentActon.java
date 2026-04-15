package hy.ea.invoicing.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.InvAdjustment;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class InvAdjustmentActon
{
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    private PageForm pageForm;
    private int pageNumber;
    private String result;
    private InvAdjustment invAdjustment;

    public String toSearch()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String barcode = request.getParameter("barcode");
        String sdate = request.getParameter("sdate");
        String edate = request.getParameter("edate");
        StringBuilder sql = new StringBuilder("SELECT IA.IAID,IA.BARCODE,P.GOODSNAME,'销售库',IA.ADDDATE,IA.INVNUM,IA.ADJNUM,S.STAFFNAME");
        sql.append(" FROM DT_INV_ADJUSTMENT IA");
        sql.append(" LEFT JOIN DT_HR_STAFF S ON IA.STAFFID = S.STAFFID");
        sql.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON IA.PPID = P.PPID");
        sql.append(" WHERE 1=1");
        List<Object> paremList = new ArrayList();
        if ((barcode != null) && (!barcode.equals("")))
        {
            sql.append(" AND IA.BARCODE=?");
            paremList.add(barcode);
        }
        if ((sdate != null) && (!sdate.equals("")) && (edate != null) && (!edate.equals("")))
        {
            sql.append(" AND IA.ADDDATE BETWEEN ? AND ?");
            paremList.add(Utilities.getDateFromString(sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
            paremList.add(Utilities.getDateFromString(edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
        }
        sql.append(" ORDER BY IA.ADDDATE DESC");

        this.pageForm = this.baseBeanService.getPageFormBySQL(null != this.pageForm ? this.pageForm
                .getPageNumber() : 1, this.pageNumber == 0 ? 10 : this.pageNumber, sql
                .toString(), "select count(*) from (" + sql
                .toString() + ")", paremList.toArray());
        return "toSearch";
    }

    public String ajxaGetProduct()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String comid = session.get("companyID").toString();
        String barcode = request.getParameter("barcode");
        StringBuffer sql = new StringBuffer("SELECT I.INVENTORYID,P.PPID,P.BARCODE,P.GOODSNAME,P.VARIABLEID,I.INVENQUANTITY");
        sql.append(" FROM DT_INV_INVT I");
        sql.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON I.PRODUCTID = P.PPID");
        sql.append(" WHERE P.BARCODE =? AND I.WAREHOUSENAME=? AND P.COMPANYID=?");
        List<Object> list = this.baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[] { barcode, "销售库", comid });
        Map<String, Object> map = new HashMap();
        map.put("list", list);
        JSONObject js = JSONObject.fromObject(map);
        this.result = js.toString();
        return "success";
    }

    public String UpdateInv()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String comid = session.get("companyID").toString();
        CAccount account = (CAccount)session.get("account");
        List<BaseBean> beansList = new ArrayList();
        try
        {
            if (this.invAdjustment != null)
            {
                String hql = "from Inventory where inventoryID=?";
                Inventory i = (Inventory)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[] { this.invAdjustment.getInvid() });
                if (i != null)
                {
                    if (Float.parseFloat(this.invAdjustment.getAdjnum()) > 0.0F)
                    {
                        i.setInvenQuantity(this.invAdjustment.getAdjnum());
                        beansList.add(i);
                        this.invAdjustment.setIaid(this.serverService.getServerID("invadju"));
                        this.invAdjustment.setAdddate(new Date());
                        this.invAdjustment.setComid(comid);
                        this.invAdjustment.setStaffid(account.getStaffID());
                        beansList.add(this.invAdjustment);
                        this.baseBeanService.executeHqlsByParamsList(beansList, null, null);
                    }
                    else
                    {
                        request.setAttribute("message", "10");
                    }
                }
                else {
                    request.setAttribute("message", "10");
                }
            }
            else
            {
                request.setAttribute("message", "10");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("message", "10");
        }
        return "success";
    }

    public String getcode()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String comid = session.get("companyID").toString();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODEID,CODEPID,CODEVALUE FROM");
        sql.append(" (SELECT CC.CODEID,CC.CODEVALUE,CC.CODEPID FROM DTCCODE CC WHERE CC.COMPANYID =?)");
        sql.append(" START WITH CODEID =? CONNECT BY PRIOR CODEID = CODEPID");

        List<BaseBean> list = this.baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[] { comid, "scode20190415raqvqk3uvs0000000762" });
        Map<String, Object> map = new HashMap();
        map.put("codeList", list);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    public String GetProduct()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String comid = session.get("companyID").toString();

        String parameter = request.getParameter("parameter");
        List<Object> params = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT I.INVENTORYID,P.PPID,P.GOODSNAME,P.VARIABLEID,P.GOODSID,S.UNITOFMEASURECODE,P.BARCODE,I.INVENQUANTITY");
        sql.append(" FROM DT_INV_INVT I LEFT JOIN DT_PRODUCTPACKAGING P ON I.PRODUCTID = P.PPID");
        sql.append(" LEFT JOIN DT_PROCATERELATE CC ON CC.PPID=P.PPID");
        sql.append(" INNER JOIN DTSCALEGOODS S ON S.GOODSID = P.GOODSID");
        sql.append(" WHERE P.ISSCALE = ? AND I.WAREHOUSENAME=?");
        params.add("0");
        params.add("销售库");
        if ((parameter != null) && (!parameter.equals("")))
        {
            sql.append(" AND P.GOODSNAME like ?");
            params.add("%" + parameter + "%");
        }
        sql.append(" AND P.COMPANYID =?");
        params.add(comid);

        this.pageForm = this.baseBeanService.getPageFormBySQL(null != this.pageForm ? this.pageForm.getPageNumber() : 1, this.pageNumber == 0 ? 20 : this.pageNumber, sql
                .toString(), "select count(*) from (" + sql + ")", params.toArray());
        Map<String, Object> map = new HashMap();
        map.put("pageForm", this.pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    public InvAdjustment getInvAdjustment()
    {
        return this.invAdjustment;
    }

    public void setInvAdjustment(InvAdjustment invAdjustment)
    {
        this.invAdjustment = invAdjustment;
    }

    public String getResult()
    {
        return this.result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public PageForm getPageForm()
    {
        return this.pageForm;
    }

    public void setPageForm(PageForm pageForm)
    {
        this.pageForm = pageForm;
    }

    public int getPageNumber()
    {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }
}
