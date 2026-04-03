package mobile.tiantai.android.action.qyrz;


import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.ea.util.StringUtil;
import hy.ea.util.bean.POI;
import hy.ea.util.bean.ValidationUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.service.QyrzService;

import mobile.tiantai.android.util.DouBaoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 企业认证（周边商城）；
 * Created by Administrator on 2019-10-18.
 */
@Controller
@Scope("prototype")
public class QyrzAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private QyrzService qyrzService;
    @Resource
    private DouBaoService douBaoService;
    @Resource
    private BaseBeanDao beandao;

    @Resource
    private EarthIndexService earthIndexService;
    private List<BaseBean> productList;// 产品列
    private Object result;
    private  int pageNumber;
    private  int pageSize;
    private String date;
    private String parameter;
    private String gdcate;
    private String gdcate2;
    private String staffID;
    private  PageForm pageForm;
    private String sccId;
    HttpServletRequest request = ServletActionContext.getRequest();
    private int page;
    private Object results;
    private String companyName;
    /**
     * 跳转周边页
     *
     * @return
     */
    public String toPeriphery() {
        String sccid = request.getParameter("sccid");
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        if(sccid!=null&&!sccid.equals("")){

            TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});


            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{cus.getAccount()});

            sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);

        }


        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if(tc!=null) {

            earthIndexService.addBrowseHistory(tc.getSccId(),"周边","");
        }


        return "toPeriphery";
    }

    /**
     *
     * 获取新闻
     * @return
     */
    public String  getNewList(){
        String x = request.getParameter("dwLnglatX");
        String y = request.getParameter("dwLnglatY");
        List<BaseBean> newslist = qyrzService.getNewsList(x,y);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newslist", newslist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     *
     * 查看详情
     * @return
     */
    public String toDetail(){
        HttpServletResponse response = ServletActionContext.getResponse();
        String gdID = request.getParameter("gdID");
        ContactCompany cc = (ContactCompany)baseBeanService.getBeanByHqlAndParams("from ContactCompany where gdID = ?",new Object[]{gdID});
        Map<String, Object> map = new HashMap<String, Object>();

        if(cc!=null){
            map.put("result", cc.getCcompanyID());
        }else{
            map.put("result", "0");
        }


        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     * 公司会员
     */
    @SuppressWarnings("unchecked")
    public String getpk() {
        productList = qyrzService.getpk();
        return "MyjiaruCompany";
    }

    /**
     * 跳转认证页面
     *
     * @return
     */
    @Deprecated
    public String toRz() {


        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();

        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if (tc != null) {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            String url = request.getHeader("Referer");
//            session.setAttribute("url", url);
//                return "login";
            Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff f where f.staffID = ?",new Object[]{tc.getStaffid()});
            request.setAttribute("staffname",staff.getStaffName());
            request.setAttribute("telphone",staff.getReference());
            request.setAttribute("user",tc.getAccount());
        }




        String realpath = ServletActionContext.getServletContext()
                .getRealPath("\\");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");

        String content = qyrzService.getProDetail(request.getParameter("goodsid"),realpath);
        request.setAttribute("content",content);


        return "rl";
    }





    /**
     * 行业导航
     * 一级行业
     */
    @Deprecated
    public String getIndustry() {

        String hql = "";
        String codePID = request.getParameter("codePID");
        List<BaseBean> industryList = null;
        if (codePID == null || codePID.equals("")) {

            hql = "from BusinessType a where a.typeLevel = ? AND a.status=?  order by a.sortNum asc";
            industryList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{2,"1"});
        }else{
            hql = "from BusinessType a where a.typeLevel!=? and  a.typePID = ? AND a.status=?  order by a.sortNum asc";

            industryList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{2,codePID,"1"});
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("industryList", industryList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     *
     * 验证是否有资格认领
     * @return
     */
    public String validateCom(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        String gdID = request.getParameter("gdID");

       String busiManagerID = request.getParameter("busiManagerID");
        String wx = request.getParameter("wx");
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if(tc==null&&wx=="1"){
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            map.put("login", "login");
        }
        //没注册公司的可以认领
        ContactCompany contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where gdID = ?",new Object[]{gdID});
        if(contactCompany!=null){
            map.put("result", "1");;//已经被认领
        }else {

            if (tc != null&&(busiManagerID==null||busiManagerID.equals(""))) {
                String state = tc.getState();
                map.put("result", state);
                if ("1".equals(state)) {
                    map.put("result", "0"); //没有被认领

                }

            } else {

                map.put("result", "0"); //没有被认领
            }
        }


        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * @Description: 周边商家已认领、未认领查询
     * @author: zy
     * @Date: 2024/06/24 18:00
     * @param
     * @return List<String> 已认领或未认领的高德id集合
     */
    public String validateComBatch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        //HttpSession session = request.getSession();
        //SessionWrap sw = SessionWrap.getInstance();
        String[] j = request.getParameterValues("gdIds[]");
        //String[] j = gdIDs.split(",");
        List<Object> gdList = Arrays.asList(j);
        String claim = request.getParameter("claim");
        //TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        List<Object> claimIds = new ArrayList<>();//已经被认领
        List<Object> notClaimIds = new ArrayList<>();//没有被认领
        //没注册公司的可以认领
        //List<ContactCompany> contactCompanyList = (List<ContactCompany>) baseBeanService.getBeanByHqlAndParams("from ContactCompany where gdID in ?", new Object[]{gdList});
        /*String hqlCRole = "select gdID from ContactCompany where gdID in "++" ";
        Query query = sessionFactory.getCurrentSession().createQuery(hqlCRole);
        query.setParameter(0, gdList);
        List<ContactCompany> contactCompanyList = query.list();*/

        List<Object> list=new ArrayList<>();
        String sql = "select gdID from dtContactCompany where gdID in(";
        for(int i=0;i<gdList.size();i++){
            sql+= gdList.size()==i+1?"?":"?,";
            list.add(gdList.get(i));
        }
        sql+=") ";
        List<Object> contactCompanyList = beandao.getListObjectBySqlAndParams(sql, list.toArray());
        if (contactCompanyList != null && contactCompanyList.size() > 0) {
            for (Object company : contactCompanyList) {
                claimIds.add(company);
            }
            //List<String> ids = contactCompanyList.stream().map(ContactCompany::getGdID).collect(Collectors.toList());
            //notClaimIds = gdList.stream().filter(gd -> !contactCompanyList.contains(gd)).collect(Collectors.toList());
            for(Object obj : gdList){
                if(!contactCompanyList.contains(obj)){
                    notClaimIds.add(obj);
                }
            }
        } else {
            notClaimIds = gdList;
        }
        if ("claim".equals(claim)) {//已认领
            JSONArray obj = JSONArray.fromObject(claimIds);
            result = obj;
        } else {//未认领
            JSONArray obj = JSONArray.fromObject(notClaimIds);
            result = obj;
        }
        return "success";
    }

    /**
     *
     *   验证账号是否已经有公司了
     * @return
     */
    public String checkAccount(){
       String tel  = request.getParameter("tel");
       List<BaseBean> tclist = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and state = ?",new Object[]{tel,"2"});
        Map<String, Object> map = new HashMap<String, Object>();
       if(tclist.size()>0){
           map.put("result","1");
       }else{
           map.put("result","0");
       }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     *
     * 获取登陆用户已认领的公司
     * @return
     */
    public String getCompleteBus(){
        String ajax = request.getParameter("ajax");

        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = null;
        TEshopCustomer  customer = null;
        if(!"ajax".equals(ajax)) {

            if (sccId != null && !sccId.equals("")) {
                tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            } else {
                tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);

            }
            if (tc == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                return "login";
            }
            customer = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{tc.getAccount()});

            sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
            request.setAttribute("busiManagerID",tc.getStaffid());
        }else{
            tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);

        }
          pageForm =  qyrzService.getCompeleteBusi((null != pageForm ? pageForm.getPageNumber() : 1),pageSize==0?10:pageSize,date,parameter,gdcate,tc.getStaffid(),gdcate2);

          if("ajax".equals(ajax)){
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("pageForm",pageForm);
              JSONObject obj = JSONObject.fromObject(map);
              result = obj.toString();
              return "success";
          }


          return "zbstatis";
    }

    /**
     *
     *  未认领
     * @return
     */
     public String getSendcompany(){
         HttpSession session = request.getSession();
         SessionWrap sw = SessionWrap.getInstance();

         String jd = request.getParameter("jd");
         String wd = request.getParameter("wd");
         String yw = request.getParameter("yw");

         pageForm =  qyrzService.getCompanyZ((null != pageForm ? pageForm.getPageNumber() : 1),pageSize==0?20:pageSize,parameter,gdcate,jd,wd,yw);
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("pageForm",pageForm);
         JSONObject obj = JSONObject.fromObject(map);
         result = obj.toString();
         return "success";
    }

    private Object isNull(Object tep){
        if(tep==null||"null".equals(tep)){
            return "";
        }else{
            return tep;
        }
    }
    /**
     *
     * 未认领拨号
     * @return
     */
    public String callCompany(){
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();

        String jd = request.getParameter("jd");
        String wd = request.getParameter("wd");

        pageForm =  qyrzService.getCompanyZ(page,20,parameter,gdcate,jd,wd,"w");
        JSONObject jsonObjList = new JSONObject();
        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                POI poi = (POI)pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                if(poi.getTel()!=null&&!poi.getTel().equals("")){
                    jsonObj.accumulate("name", isNull(poi.getName()));
                     String[] tels = poi.getTel().split(";");
                     for (int j = 0;j<tels.length;j++){
                          if(ValidationUtil.isMobileNO(tels[j])){
                              jsonObj.discard("tel");
                              jsonObj.accumulate("tel", isNull(tels[j]));
                              break;
                          }else{
                              jsonObj.discard("tel");
                              jsonObj.accumulate("tel", isNull(tels[j]));
                          }
                     }
                      String[] photos =   poi.getPhotos();
                     String photo = "";
                     if(photos!=null&&photos.length>0){
                         photo = photos[0];
                     }
                    jsonObj.accumulate("logo", photo);
                     if(poi.getType()!=null&&!poi.getType().equals("")){
                         String[] types = poi.getType().split(";");
                         jsonObj.accumulate("gdcate", types[0]);
                     }



                    lists.add(jsonObj);
                }


            }
        }

        jsonObjList.accumulate("comlist", lists);
        jsonObjList.accumulate("pageNumber",pageForm.getPageNumber());
        results = jsonObjList;
        return "success";
    }

    /**
     *
     *   豆包ai
     * @return
     */
    public String getCompanyInfoFromDoubao(){
        String companyName = request.getParameter("companyName");
        if (StringUtil.isEmpty(companyName)){
            return "";
        }
        String s = douBaoService.companyTips(companyName);
        JSONObject obj = JSONObject.fromObject(s);
        result = obj.toString();
        return "success";
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<BaseBean> getProductList() {
        return productList;
    }

    public void setProductList(List<BaseBean> productList) {
        this.productList = productList;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getGdcate() {
        return gdcate;
    }

    public void setGdcate(String gdcate) {
        this.gdcate = gdcate;
    }

    public String getGdcate2() {
        return gdcate2;
    }

    public void setGdcate2(String gdcate2) {
        this.gdcate2 = gdcate2;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
