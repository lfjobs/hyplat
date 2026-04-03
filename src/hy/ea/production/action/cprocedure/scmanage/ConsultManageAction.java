package hy.ea.production.action.cprocedure.scmanage;

import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.ConsultingRegistration;

import hy.ea.production.service.ConsultManageService;

import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.*;

/**
 * 咨询
 */
@Controller
@Scope("prototype")
public class ConsultManageAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ConsultManageService consultService;
    @Resource
    private ShowExcelService excelService;
    public InputStream excelStream;
    private ConsultingRegistration consult;
    private PageForm pageForm;
    private String result;
    private int pageNumber;
    private int pageSize;
    private String search;

    /**
     * 咨询保存
     */
    public String  saveConsult(){

        consultService.saveConsult(consult);

        return "success";

    }

    /**
     * 回访记录
     * @return
     */
    public String saveReturnVisit(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(account==null){
            return "not_login";
        }
        consult.setVisitStaffID(account.getStaffID());
        consultService.saveReturnVisit(consult);
        HttpServletRequest request = ServletActionContext.getRequest();

         request.setAttribute("message","11");

        return "success";
    }

    /**
     * 验证是否咨询过该项目
     */
    public  String checkIn(){
        String results = consultService.checkIn(consult.getConsultantPhone(),consult.getPpid());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result",results);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";

    }


    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("consult", consult);
        return getConsultList();
    }



    private DetachedCriteria getList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(ConsultingRegistration.class);
        ConsultingRegistration consult = (ConsultingRegistration) session.get("consult");

        dc.add(Restrictions.eq("companyId", companyID));
        dc.addOrder(Order.desc("consultingDate"));
        if (search != null && search.equals("search")) {
            if (consult.getConsultantName() != null && !"".equals(consult.getConsultantName())) {
                dc.add(Restrictions.like("consultantName",consult.getConsultantName(), MatchMode.ANYWHERE));

            }
            if (consult.getConsultantPhone() != null && !"".equals(consult.getConsultantPhone())) {
                dc.add(Restrictions.eq("consultantPhone",consult.getConsultantPhone()));

            }
            if (consult.getReturnVisit()!= null && !"".equals(consult.getReturnVisit())) {
                dc.add(Restrictions.eq("returnVisit",consult.getReturnVisit()));

            }
        }
        return dc;
    }


    /**
     * 咨询列表
     * @return
     */
    public String getConsultList() {
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), getList());
        return "consultlist";
    }




    //////////////////////////新版////////////////////////////////////////////

    /**
     *
     * 获取咨询列表
     * @return
     */
    public String getConsultslist(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();


        CAccount account = (CAccount) session.getAttribute("account");

        if(account==null){
           return "login";
        }
        String parameter = request.getParameter("parameter");
        String returnVisit = request.getParameter("returnVisit");
        String ajax = request.getParameter("ajax");
        pageForm = consultService.getConsultslist(pageNumber,(pageSize == 0 ? 20 : pageSize),account.getStaffID(),account.getCompanyID(),parameter,returnVisit);
        if(ajax!=null&&ajax.equals("ajax")){
           Map<String,Object> map = new HashMap<String,Object>();
           map.put("pageForm",pageForm);
           JSONObject jo = JSONObject.fromObject(map);
           result = jo.toString();
           return "success";
       }
         return "newconsult";
    }



    /**
     *
     * 获取咨询列表
     * @return
     */
    public String getConsultsNum(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();


        CAccount account = (CAccount) session.getAttribute("account");

        if(account==null){
            return "login";
        }
        String parameter = request.getParameter("parameter");

        pageForm = consultService.getConsultslist(1,(pageSize == 0 ? 20 : pageSize),account.getStaffID(),account.getCompanyID(),parameter,"00");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("num",pageForm==null?0:pageForm.getRecordCount());
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
            return "success";


    }
    /**
     *
     *
     * 咨询报名人
     * @return
     */
    public String getConBmlist(){

        HttpServletRequest request = ServletActionContext.getRequest();
        String ppid = request.getParameter("ppid");
        List<Object> list = consultService.getConBmlist(ppid);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",list);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     *
     * 查看详情
     * @return
     */
    public String viewDetail(){
        consult = consultService.viewDetail(consult.getCrId());

        return "viewdetail";
    }


    public String viewRecord(){
        consult = consultService.viewDetail(consult.getCrId());

        return "viewrecord";

    }

    /**
     *
     * 导出
     * @return
     */
    public String showExcel() {
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = ServletActionContext.getRequest().getSession();
        String parameter = request.getParameter("parameter");
        String returnVisit = request.getParameter("returnVisit");

        CAccount account = (CAccount) session.getAttribute("account");
        if(account==null){
            return "login";
        }
        excelStream = excelService.showExcel(ConsultingRegistration.columnHeadings(),
                consultService.getConsultForList(account.getStaffID(),account.getCompanyID(),parameter,returnVisit));

        return "showexcel";
    }

    /**
     *
     * 打印的数据
     * @return
     */
    public String printData(){

        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = ServletActionContext.getRequest().getSession();
        String parameter = request.getParameter("parameter");
        String returnVisit = request.getParameter("returnVisit");

        CAccount account = (CAccount) session.getAttribute("account");
        if(account==null){
            return "login";
        }
        List<BaseBean> list = consultService.getConsultForList(account.getStaffID(),account.getCompanyID(),parameter,returnVisit);
         request.setAttribute("list",list);

        return "toprint";

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

    public ConsultingRegistration getConsult() {
        return consult;
    }

    public void setConsult(ConsultingRegistration consult) {
        this.consult = consult;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }
}
