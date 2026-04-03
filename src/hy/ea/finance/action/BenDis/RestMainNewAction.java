package hy.ea.finance.action.BenDis;

import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.RestaurantService;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 餐饮新主页
 */
@Controller
@Scope("prototype")
public class RestMainNewAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private ServerService serverService;

    private String companyId;
    private String ccompanyId;
    private String result;
    private int pageNumber;
    private int pageSize;
    private String categoryId;
    private String  posNum;



    /**
     *
     * 新版餐饮首页
     * @return
     */
    public String indexMain(){


        return   "index";
    }

    /**
     *
     * 分类显示菜单
      * @return
     */
    public  String cateMenu(){



        return  "cateMenu";

    }

    /**
     * 获取分类
     * @return
     */
    public String getCateByComID(){
        List<BaseBean> list =  restaurantService.getALLCate(companyId);
        Map<String, Object> map = new HashMap<String, Object>();
        if(list.size()>0){
        SchProCategory sc = (SchProCategory)list.get(0);
        if(sc!=null){
            categoryId = sc.getCategoryId();
            String staffid = "";
            if(posNum==null||posNum.equals("")){
                HttpSession session = ServletActionContext.getRequest().getSession();
                SessionWrap sw = SessionWrap.getInstance();
                TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
                if(cus!=null) {
                    staffid = cus.getStaffid();
                }
            }
            PageForm pageForm =  restaurantService.getProductByCate(companyId,staffid,posNum,categoryId,pageNumber,pageSize);
           
            map.put("pageForm", pageForm);
          }
        }
     
        map.put("list", list);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";

    }

    /**
     *
     * 获取产品
     * @return
     */
    public String getProductByCate(){
        String staffid = "";
        if(posNum==null||posNum.equals("")){
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
            if(cus!=null) {
                staffid = cus.getStaffid();
            }
        }
        PageForm pageForm =  restaurantService.getProductByCate(companyId,staffid,posNum,categoryId,pageNumber,pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";

    }

    /**
     * 查询购物车商品数目
     */

    public String getCompanyCartNum() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request  = ServletActionContext.getRequest();
        String posNum = request.getParameter("posNum");
        String companyId = request.getParameter("companyId");
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, sw.KEY_CUSTOMER);
        String staffId = null;
        if(cus!=null){
            staffId = cus.getStaffid();
        }
        int cartNum = restaurantService.getCompanyCartNum(posNum,staffId,companyId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cartNum",cartNum);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 生成订单号
     * @return
     */
    public String getJum(){

        String jum=serverService.getBillID("company201009046vxdyzy4wg0000000025");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jum", jum);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();

        return "success";
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }
}
