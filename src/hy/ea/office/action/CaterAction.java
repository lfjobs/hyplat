package hy.ea.office.action;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.office.Cater;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 餐桌管理
 *
 * @author LG
 */
@Controller
@Scope("prototype")
public class CaterAction {
    @Resource
    private BaseBeanService baseBeanService;

    private PageForm pageForm;

    @Resource
    private CLogBookService logBookService;


    private String typeType;
    private int pageNumber;
    private String result;
    private String deptID;
    private Cater  cater;
    private String search;
    private String parameter;
    private Cater caterSearch;
    private ProductPackaging productPack;
    private String caterID;

    public String getCaterList() {
        pageForm = baseBeanService.getPageFormByDC((    null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
        return "caterlist";
    }

    //根据条件查询信息
    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", caterSearch);
        return getCaterList();
    }

    public String deleteCater(){
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID=(String) session.get("organizationID");
        Object[] params = {account.getCompanyID(),caterID};
        CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "", account);
        String hql="delete from Cater where  companyID=? and ID=?";
        List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
        baseBeansList.add(cLogBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
        return "success";
    }

    public String updateCater(){
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID=(String) session.get("organizationID");
        @SuppressWarnings("unused")
        String obj = (String) session.get("session_value");
        String hql2="from Cater where companyID=?  and ID=?";
        Cater caterinfo=(Cater) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompany().getCompanyID(),cater.getID()});
        caterinfo.setPersonID(cater.getPersonID());
        caterinfo.setPersonName(cater.getPersonName());
        caterinfo.setStatus(cater.getStatus());
        CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "", account);
        List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
        baseBeansList.add(caterinfo);
        baseBeansList.add(cLogBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
        return "success";
    }

    //添加
    public String saveCater()
    {
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID=(String) session.get("organizationID");
        @SuppressWarnings("unused")
        String obj = (String) session.get("session_value");
        String hql2="from Cater";
        Cater caterinfo = new Cater();
        String uuid = UUID.randomUUID().toString();
        String ID =uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
        caterinfo.setID(ID);
        DecimalFormat format3 = new DecimalFormat("000");
        String phql = "select max(substr(boardNo,3)) from Cater where companyID = ?";
        Object objs =  baseBeanService.getObjectByHqlAndParams(phql, new Object[]{account.getCompany().getCompanyID()});
        int count = Integer.parseInt(objs==null?"0":objs.toString()) +1;;
        String boardNO = format3.format(count);
        caterinfo.setBoardNo("NO"+boardNO);
        caterinfo.setPpID(cater.getPpID());
        caterinfo.setCompanyName(cater.getCompanyName());
        caterinfo.setPersonID(cater.getPersonID());
        caterinfo.setPersonName(cater.getPersonName());
        caterinfo.setDeptID(organizationID);
        caterinfo.setDeptName(cater.getDeptName());
        caterinfo.setName(cater.getName());
        caterinfo.setStatus(cater.getStatus());
        caterinfo.setCompanyID(account.getCompanyID());
        CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
        List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
        baseBeansList.add(caterinfo);
        baseBeansList.add(cLogBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
        return "success";
    }

    public  String  getProduct(){
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }

        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("session_value", Math.random() + "");
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getProList());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    private DetachedCriteria getProList(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(ProductPackaging.class);
        dc.add(Restrictions.eq("companyID", companyID));
        dc.add(Restrictions.eq("type", "餐桌"));
        if (null != parameter && !"".equals(parameter)) {
            dc.add(Restrictions.like("goodsName", parameter,
                    MatchMode.ANYWHERE));
        }
        return dc;
    }


    private DetachedCriteria getList() {
        ActionContext context=ActionContext.getContext();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String organizationID = (String) session.get("organizationID");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(Cater.class);
        dc.add(Restrictions.eq("companyID", companyID));

        String phqls = "select sccId from TEshopCusCom  where companyId = ?";
        Object sccid =  baseBeanService.getObjectByHqlAndParams(phqls, new Object[]{account.getCompany().getCompanyID()});
        session.put("sccid", sccid);
        if (search != null && search.equals("search")) {
            caterSearch = (Cater) session.get("tablesearch");
            if(caterSearch.getBoardNo()!=null&&!caterSearch.getBoardNo().equals("")){
                dc.add(Restrictions.like("boardNo", caterSearch.getBoardNo().trim(), MatchMode.ANYWHERE));
            }
            if(caterSearch.getPersonName()!=null&&!caterSearch.getPersonName().equals("")){
                dc.add(Restrictions.like("personName", caterSearch.getPersonName().trim(), MatchMode.ANYWHERE));
            }

            if(caterSearch.getStatus()!=null&&!caterSearch.getStatus().equals("")){
                dc.add(Restrictions.like("status", caterSearch.getStatus().trim(), MatchMode.ANYWHERE));
            }

        }
        return dc;
    }

    public Cater getCaterSearch() {
        return caterSearch;
    }

    public void setCaterSearch(Cater caterSearch) {
        this.caterSearch = caterSearch;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getTypeType() {
        return typeType;
    }

    public void setTypeType(String typeType) {
        this.typeType = typeType;
    }

    public Cater getCater() {
        return cater;
    }

    public void setCater(Cater cater) {
        this.cater = cater;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCaterID() {
        return caterID;
    }

    public void setCaterID(String caterID) {
        this.caterID = caterID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ProductPackaging getProductPack() {
        return productPack;
    }

    public void setProductPack(ProductPackaging productPack) {
        this.productPack = productPack;
    }
}
