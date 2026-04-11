package hy.ea.marketing.action.supermaket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.PresetKey;
import com.tiantai.wfj.bo.PresetPage;
import com.tiantai.wfj.bo.Scale;
import hy.ea.bo.CAccount;
import hy.ea.marketing.service.ScaleManageSerivce;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@Scope("prototype")
public class ScaleManageAction
{
	private static final Logger logger = LoggerFactory.getLogger(ScaleManageAction.class);
    @Resource
    private ServerService serverService;
    @Resource
    private ScaleManageSerivce scaleSerivce;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SuperSelfSerivce smSerivce;
    private String result;
    private Scale scale;
    private String search;
    private PageForm pageForm;
    private int pageNumber;
    private String companyID;
    private String companyIdentifier;
    private CAccount account;
    Map<String, PresetKey> presetKeymap;
    private PresetPage presetPage;
    private String parameter;  //查询参数
    private int lxType = 0;//查询类型，0：其他入口进入1：批发商城进入

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("scale", scale);
        return getScaleList();
    }

    private DetachedCriteria getList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(Scale.class);
        Scale scale = (Scale) session.get("scale");

        dc.add(Restrictions.eq("companyID", companyID));
        if (search != null && search.equals("search")) {
            if(scale.getAddress() != null && !"".equals(scale.getAddress().trim())){
                dc.add(Restrictions.eq("address",scale.getAddress()));

            }if(scale.getScaleNo()!=0&&!"".equals(scale.getScaleNo())){
                dc.add(Restrictions.eq("scaleNo",scale.getScaleNo()));

            }
        }
        return dc;
    }

    // 电子秤列表
    public String getScaleList()
    {
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
        return "scalelist";
    }
    /**
     *
     * 添加修改
     * @return
     */
    public String addOrUpdate(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(account==null){
            return "login";
        }
        scaleSerivce.addOrUpdate(scale,account.getCompanyID(),account.getStaffID());

     return "success";
    }

    /**
     *
     * 删除秤
     * @return
     */
    public String deleteScale(){
        if(scale!=null&&scale.getScKey()!=null&&!scale.getScKey().equals("")){
            scaleSerivce.delete(scale.getScKey());
        }

        return "success";
    }
///////////////////////////////////////////////////////////////////预置键/////////////////////////////////////////////////////////////////////////////////




    private DetachedCriteria getListPreset() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(PresetPage.class);
        PresetPage presetPage = (PresetPage) session.get("presetPage");

        dc.add(Restrictions.eq("companyID", companyID));

        return dc;
    }


    /**
     *
     * 预置键模板
     * @return
     */
    public String  getPreKeyList(){
          pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getListPreset());
      return "prekeylist";

    }

    public String savePreKey(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(account==null){
            return "login";
        }
        scaleSerivce.addPreOrUpdate(presetKeymap,presetPage,account.getCompanyID(),account.getStaffID());
        return "success";
    }

    /**
     *
     * 所有需要用电子秤打条码的
     * @return
     */
    public String ajaxGetScaleGoods(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        HttpServletRequest request = ServletActionContext.getRequest();
        String parameter = request.getParameter("parameter");
        String sql = "select c.plu,p.goodsName,p.goodsID,c.sgID,p.barCode,c.unitOfMeasureCode from dtScaleGoods c left join dt_ProductPackaging  p on c.goodsID = p.goodsID and p.isScale = ?  where c.companyID = ? and p.showweixin=?";

        List<Object> params = new ArrayList<Object>();
        params.add("0");
        params.add(account.getCompanyID());
        params.add("01");

        if(parameter!=null&&!parameter.equals("")){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(parameter);
            if( !isNum.matches() ){
                sql+=" and  p.goodsName like ?";
                params.add("%"+parameter+"%");
            }else{
                sql+=" and (c.plu  = ? or p.goodsName like ?)";
                params.add(parameter);
                params.add("%"+parameter+"%");
            }


        }
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber),sql,"select count(*) from ("+sql+")",params.toArray());

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();

        return "success";
    }


    /**
     *
     *  修改页面数据
     */
    public String  ajaxPrekeyListByPreID(){
        List<BaseBean> prekeylist = scaleSerivce.getPreKeyList(presetPage.getScpId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("prekeylist", prekeylist);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return  "success";

    }

    /**
     *
     * 删除预置键
     * @return
     */
    public String  deletePresetPage(){
        scaleSerivce.deletePreset(presetPage.getScpId());

      return "success";
    }
 ////////////////////////////////////////////////////与C#通讯///////////////////////////////////////////////////////////
    /**
     *
     *
     * @return
     */
    public String getAllScale(){
        HttpServletResponse response = ServletActionContext.getResponse();

        List<BaseBean> scalelist = baseBeanService.getListBeanByHqlAndParams("from Scale where companyID = ?",new Object[]{companyID});
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("scalelist", scalelist);

        try {
            JSONObject json = JSONObject.fromObject(map);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return  null;
    }

    /**
     *
     * 同步需要用电子秤打条码的商品
     * @return
     */
    public  String dataSyncGoods(){
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String,Object> map = scaleSerivce.getGoodsList(companyID);

        try {
            JSONObject json = JSONObject.fromObject(map);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return  null;

    }

    /*
 *  电子秤系统登陆
 */
    public String scaleLogin() {
        int result =  smSerivce.login(companyIdentifier,account);
        HttpServletResponse response = ServletActionContext.getResponse();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("account", account);
        map.put("result", result);


        try {
            JSONObject json = JSONObject.fromObject(map);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json.toString());

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

        return null;

    }
    /**
     * 同步预置键
     *
     * @return
     */
    public String dataSyncPreSet(){
        HttpServletResponse response = ServletActionContext.getResponse();

        List<BaseBean> prepagelist = baseBeanService.getListBeanByHqlAndParams("from PresetPage where companyID = ?",new Object[]{companyID});
        List<BaseBean> prekeylist = baseBeanService.getListBeanByHqlAndParams("from PresetKey where companyID = ? order by cast(keyNo as integer)",new Object[]{companyID});

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("prepagelist", prepagelist);
        map.put("prekeylist", prekeylist);

        try {
            JSONObject json = JSONObject.fromObject(map);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(json);

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return  null;
    }

    /**
     *
     * 查询热销称重商品
     * @return
     */
    public String findHotSaleGoods(){

        List<BaseBean> prolist = scaleSerivce.findHotSaleGoods(companyID);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("prolist",prolist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     *
     * 查询称重商品
     * @return
     */
    public String findGoods(){
        List<BaseBean> prolist = scaleSerivce.findGoods(companyID,parameter);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("prolist",prolist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }

    /***
     * 查询有产品的称重的产品分类
     * @return
     */
    public String findScaleGoodsCate(){
        List<BaseBean> catelist = scaleSerivce.findScaleGoodsCate(companyID);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("catelist",catelist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /***
     * 查询有产品的称重的二级分类
     * @return
     */
    public String findSecondCate(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String codePID = request.getParameter("codePID");
        List<BaseBean> twolist = scaleSerivce.findSecondCate(companyID,codePID);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("twolist",twolist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /***
     * 查询有产品的称重的产品
     * @return
     */
    public String findProductByCate(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String codeID = request.getParameter("codeID");
        pageForm = scaleSerivce.findProductByCate(companyID,codeID,parameter,(null != pageForm ? pageForm.getPageNumber() : 1), pageForm.getPageSize(),lxType);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageForm",pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /***
     * 查询有产品的称重的产品
     * @return
     */
    public String findProductPLU(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String plu = request.getParameter("plu");
        Object objs  = scaleSerivce.findProductPLU(companyID,plu);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("objs",objs);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /***
     * 查询有产品的称重的产品
     * @return
     */
    public String findProductByCatePhone(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String codeID = request.getParameter("codeID");
        String ppname = request.getParameter("ppname");
        String searchtype=request.getParameter("searchtype");
        logger.info("调试信息");
        logger.info("调试信息");
        pageForm = scaleSerivce.findProductByCatePhone(companyID,codeID,ppname,searchtype,parameter,1, 35);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageForm",pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }


    public Map<String, PresetKey> getPresetKeymap() {
        return presetKeymap;
    }

    public void setPresetKeymap(Map<String, PresetKey> presetKeymap) {
        this.presetKeymap = presetKeymap;
    }

    public PresetPage getPresetPage() {
        return presetPage;
    }

    public void setPresetPage(PresetPage presetPage) {
        this.presetPage = presetPage;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getLxType() {
        return lxType;
    }

    public void setLxType(int lxType) {
        this.lxType = lxType;
    }
}
