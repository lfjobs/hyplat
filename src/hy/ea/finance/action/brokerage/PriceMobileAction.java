package hy.ea.finance.action.brokerage;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.finance.service.brokerage.*;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 零售价格操作类
 * 该类负责处理移动端零售价格相关的操作。
 */
@Controller
@Scope("prototype")
public class PriceMobileAction {
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
    @Resource
    RetailService retailService;
    @Resource
    WholesaleService wholesaleService;
    @Resource
    VipService vipService;
    @Resource
    RetailPriceMobileService retailPriceMobileService;
    @Resource
    WholesalePriceMobileService wholesalePriceMobileService;
    @Resource
    VipPriceMobileService vipPriceMobileService;
    @Resource
    SetSubsidizeService setSubsidizeService;
    @Resource
    private ShowExcelService excelService;

    private PageForm pageForm;
    //ajax响应结果
    private String result;
    private String search;//模糊查询参数-商品名称或条码
    private String showweixin; //查询参数-物品状态 ‘’：全部  ‘01’：已发布 否则 未发布
    private String flag;//查看数据标识 flag=01[数据设置为只读]
    private int pageNumber;//当前页码
    private int pageSize;//每页显示最大数
    private String companyID;//当前公司id
    private String yjtype;//价格种类
    private Map<String, String> mapPro;//代理佣金
    private Map<String, String> mapdl;//代理类别id //or代理商佣金susid
    /*private ProSetup proSetup;
    private ProductPackaging productPackaging;
    private InputStream excelStream;*/
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(PriceMobileAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    private CAccount cac;

    /**
     * 产品售价[佣金...]查询
     * 2019-1-19
     *
     * @return
     */
    public String ajaxselectRetailList() {
        //非空校验
        cac =getCac();
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            companyID=getCompanyId();
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("pageNumber", pageNumber);
            parameterMap.put("pageSize", pageSize);
            parameterMap.put("search", search);
            parameterMap.put("companyId", companyID);
            if (yjtype.equals("wholesale-li")) {
                pageForm = wholesalePriceMobileService.selectWholesalePriceList(parameterMap);
            } else if (yjtype.equals("vip-li")) {
                pageForm = vipPriceMobileService.selectVipPriceList(parameterMap);
            } else {
                pageForm = retailPriceMobileService.selectRetailPriceList(parameterMap);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jsonObject = JSONObject.fromObject(map);
            this.result = jsonObject.toString();
            return "success";
        }
    }

    /**
     * 获取没有设置零售价佣金的产品
     * 2019-1-19
     *
     * @return
     */
    public String getProductByStatus() {
        Map<String, Object> map2 = new HashMap<String, Object>();
        companyID=getCompanyId();//当前公司id
        cac=getCac();
        //非空校验
        if (cac != null && companyID != null && !"".equals(companyID)) {
            //当前页
            //int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示20条]
            //int pageSize = (pageNumber == 0 ? 20 : pageNumber);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("pageNumber", pageNumber);
            map1.put("pageSize", pageSize);
            map1.put("search", search);
            map1.put("companyId", companyID);
            if (yjtype == "wholesale-li") {
                pageForm = this.wholesaleService.getProductByStatus(map1);
            } else if (yjtype == "vip-li") {
                pageForm = this.vipService.getProductByStatus(map1);
            } else {
                pageForm = this.retailService.getProductByStatus(map1);
            }
            map2.put("pageForm", pageForm);
            map2.put("search", search);
            map2.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map2.put("code", "400");
        }

        JSONObject jsonObject = JSONObject.fromObject(map2);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 跳转页面
     * priceid：数据id
     * priceType：返回页面 01：添加  02：修改  03：查看  04：审核
     * @return
     */
    public String getPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        String returnStr="toSavePage";
        companyID=getCompanyId();//当前公司id
        String priceid=request.getParameter("priceid");
        String priceType=request.getParameter("priceType");
        if (priceType!=null&&priceType.equals("04")){
            returnStr="getexamine";
        }
        if (yjtype.equals("wholesale-li")) {
            map=wholesalePriceMobileService.getRetailPriceList(priceid);
        } else if (yjtype.equals("vip-li")) {
            map=vipPriceMobileService.getRetailPriceList(priceid);
        } else {
            map=retailPriceMobileService.getRetailPriceList(priceid);
        }
        SetSubsidize setSubsidize = (SetSubsidize) setSubsidizeService.setSubsidizeByTypeGetcid(companyID);
        Double totalPct = 0.0;
        if (setSubsidize == null) {
            totalPct = 0.0;
        } else {
            totalPct = Double.valueOf(setSubsidize.getTotalPct());

        }
        request.setAttribute("totalPct", totalPct);
        request.setAttribute("yjtype",yjtype);
        if (priceType!=null&&(priceType.equals("03")||priceType.equals("02"))){
            List<Object> beanList=(List<Object>) map.get("beanList");
            if (beanList!=null&&beanList.size()>0){
                mapdl=new HashMap<String, String>();
                for (Object bean:beanList){
                    if (bean instanceof Object[]){
                        Object[] beanStr=(Object[])bean;
                        mapdl.put(beanStr[2].toString(),beanStr[1].toString());
                    }
                }
            }
        }
        request.setAttribute("pRetail",map.get("pRetail"));
        request.setAttribute("beanList",map.get("beanList"));
        return returnStr;
    }

    /**
     * 保存/修改
     * @return
     */
    public String save() {
        Map<String, String> map = new HashMap<String, String>();
        String state=request.getParameter("state");//00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回
        cac=getCac();
        if (yjtype.equals("wholesale-li")) {
            map =wholesalePriceMobileService.savePrice(state,cac, mapPro, mapdl);
        } else if (yjtype.equals("vip-li")) {
            map =vipPriceMobileService.savePrice(state,cac, mapPro, mapdl);
        } else {
            map =retailPriceMobileService.savePrice(state,cac, mapPro, mapdl);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 修改数据状态为审核中
     * @return
     */
    public String updateState(){
        String priceid=request.getParameter("priceid");
        if (yjtype.equals("wholesale-li")) {
            wholesalePriceMobileService.updateState(priceid);
        } else if (yjtype.equals("vip-li")) {
            vipPriceMobileService.updateState(priceid);
        } else {
            retailPriceMobileService.updateState(priceid);
        }
        return "success";
    }

    public ServerService getServerService() {
        return serverService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getYjtype() {
        return yjtype;
    }

    public void setYjtype(String yjtype) {
        this.yjtype = yjtype;
    }

    public Map<String, String> getMapPro() {
        return mapPro;
    }

    public void setMapPro(Map<String, String> mapPro) {
        this.mapPro = mapPro;
    }

    public Map<String, String> getMapdl() {
        return mapdl;
    }

    public void setMapdl(Map<String, String> mapdl) {
        this.mapdl = mapdl;
    }


    public CAccount getCac() {
        if (cac == null) setCac(null);
        return cac;
    }

    public void setCac(CAccount cac) {
        if (cac == null) {
            this.cac = (CAccount) ActionContext.getContext().getSession().get("account");
        } else {
            this.cac = cac;
        }
    }

    public String getCompanyId() {
        if (companyID == null) setCompanyId(null);
        return companyID;
    }

    public void setCompanyId(String companyId) {
        if (companyId == null || companyId.equals("")) {
            cac = getCac();
            if (cac != null) {
                if (cac.getCompanyID() != null && !cac.getCompanyID().equals("")) {
                    this.companyID = cac.getCompanyID();
                }
            } else {
                this.companyID = companyID;
            }
        }
    }
}
