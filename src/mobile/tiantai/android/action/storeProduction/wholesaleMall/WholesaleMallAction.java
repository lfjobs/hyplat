package mobile.tiantai.android.action.storeProduction.wholesaleMall;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.human.StaffAddress;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.bo.scMobile.PfscShoppingCart;
import mobile.tiantai.android.service.storeProduction.wholesaleMall.WholesaleMallService;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.apache.struts2.ServletActionContext;
import org.hibernate.util.StringHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 批发商城action
 * Created by Administrator on 2019-11-22.
 */
@Controller
@Scope("prototype")//作用域
public class WholesaleMallAction {

    @Resource
    private BaseBeanService baseBeanService;
    //批发商城service
    @Resource
    private WholesaleMallService wholesaleMallService;
    @Resource
    private SuperSelfSerivce smSerivce;

    private List<BaseBean> goodsClassifyList;//商品分类
    private String ccompanyID;//公司id
    private String codePID;//物类id[该超市所有商品分类的父id]
    private String result;//异步返回结果
    private String industryType = "超级市场";//行业类别//行业类别id
    private String goodsId;//货物id
    private PfscShoppingCart pfscShoppingCart;//购物车bean
    private Map<String, Object> mapObj;//购物车用map集合
    private String shoppingCartParmStr;//购物车添加数据
    private String delPscIdStr;//购物车删除id拼接字符串
    private StaffAddress staffAddress;//收货地址
    private String checkCompanyIds;//购物车选中公司ids
    private String search;//模糊查询内容
    private String longitude;//经度
    private String latitude;//纬度
    private PageForm pageForm;
    private String companyName;//公司名称
    private String posNum;//大屏端编号
    private int lxType = 0;//搜索页面跳转类型0小屏跳转，1大屏跳转
    //登录信息
    private String companyid;//公司id手机端传过来的
    private String staffId;//staff表员工id手机端传过来的
    private String pfscReturnFlag;//返回标识，0：返回预算单页面，1：返回进入页，2：跳转链接页
    private String ccomIDPlatform;//平台

    /**
     * 保存更新另一个用户信息到session
     * @param staffId
     */
    private void getTEshopCusForSession(String staffId) throws  Exception{
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        if (cus == null) {
            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid=?", new Object[]{staffId});
            if (customer != null) {
                sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
                TEshopCusCom shopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?", new Object[]{customer.getAccount(), "01"});
                if (shopCusCom == null) {
                    List<BaseBean>  cusComBean = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{customer.getAccount()});
                    if (cusComBean.size() > 0) { //这个地方只能用size来判断，如果判断是否为null的话会报错的，因为list集合本身是不会为null的
                        shopCusCom = (TEshopCusCom) cusComBean.get(0);
                    }
                    shopCusCom.setAcquiesce("01");
                    baseBeanService.update(shopCusCom);
                }
                sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, shopCusCom);
            }
        }
    }

    /**
     * 跳转批发商城页
     *
     * @return
     */
    public String toWholesaleMall() {
    	
    	  if(staffId==null){
    		  HttpSession session = ServletActionContext.getRequest().getSession();
    	        SessionWrap sw = SessionWrap.getInstance();
    	        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
    	                SessionWrap.KEY_CUSTOMER);
    	        staffId  = cus.getStaffid();
    	  }
    	  
    	  
    	  
    	    HttpServletRequest request = ServletActionContext.getRequest();
    	    String phl = request.getParameter("phl");
    	    String ccomIDPlatform = request.getParameter("ccomIDPlatform");
    	    String ccompanyID = request.getParameter("ccompanyID");
    	    Map<String,Object> mp = wholesaleMallService.getAdvertPic(ccompanyID,ccomIDPlatform);
    	
    	           request.setAttribute("ppid", mp.get("ppid"));
    	           request.setAttribute("image", mp.get("image"));
    	   
    	    
    	    
    	    if("phl".equals(phl)){
    	    	 codePID = "scode20190415raqvqk3uvs0000000762";//物类id[该超市所有商品分类的父id]
    	    	
    	    	 return "toWholesaleMall";
    	    	
    	    }
    	
    	
    	
        try {
            codePID = "scode20190415raqvqk3uvs0000000762";//物类id[该超市所有商品分类的父id]
            //保存更新另一个用户信息到session
            getTEshopCusForSession(staffId);
            //1.从别的入口进入批发商城，根据手机端传过来的参数判断是否已经放入session中
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            if (parmaInfor == null) {//未存入session，则将数据存入session
                Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(companyid, staffId);
                map.put("pfscReturnFlag",StringHelper.isEmpty(pfscReturnFlag)  ? "0":pfscReturnFlag);//返回标识为空则默认填入0，否则填入传过来的数据
                parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
            } else {
                //传过来的参数不为空
                if (StringHelper.isNotEmpty(companyid) && StringHelper.isNotEmpty(staffId)) {
                    //判断传过来的参数与session中的参数不一致
                    if (!parmaInfor.get("companyId").toString().equals(companyid) || !parmaInfor.get("staffId").toString().equals(staffId)) {
                        MapSesssionUtil.removeSession("paramMap");
                        Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(companyid, staffId);
                        parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                    }
                }
            }
            //2.根据传过来的companyID查询往来单位获取ccompanyid
          
            String hql = "from CcomCom o where o.comanyId = ?";
            CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{parmaInfor.get("companyId").toString()});
            request.setAttribute("companyId", cc.getComanyId());
            //3.查询用，将信息保存到session中
            Map<String, Object> backParmaInfor = MapSesssionUtil.getSessionForMap("backMap");
            if (backParmaInfor == null) {//未存入session，则将数据存入session
                //ccompanyID = cc.getCcompanyId();
               //全部商家查询太慢暂时不用
                //ccompanyID = "all";
                //companyName = "全部商家";
                //每页显示最大数[默认显示10条]
                List<Object> parms = new ArrayList<Object>();
                StringBuilder sb = new StringBuilder("select ccompanyid,companyname,companyaddr,logopath,industrytype from dtcontactcompany where 1=1 ");
                //参数非空校验
                if (StringHelper.isNotEmpty(industryType)) {//行业类型id校验
                    sb.append(" and industryid = ?");
                    parms.add("scode20190415raqvqk3uvs0000000762");
                }
                List<BaseBean> beanList = baseBeanService.getListBeanBySqlAndParams(sb.toString(), parms.toArray());
                //随机获取商家信息
                Random rand = new Random();
                Object o1 = (Object)beanList.get(rand.nextInt(beanList.size()));
                Object[] objs = (Object[]) o1;
                ccompanyID = objs[0].toString();
                companyName =objs[1].toString();
                backParmaInfor = addBackMapInfor(ccompanyID, companyName, cc);
            } else {
                //传过来的参数不为空
                if (StringHelper.isNotEmpty(ccompanyID) && StringHelper.isNotEmpty(companyName)) {
                    //判断传过来的参数与session中的参数不一致
                    if (!backParmaInfor.get("ccompanyId").toString().equals(ccompanyID) || !backParmaInfor.get("companyName").toString().equals(companyName)) {
                        MapSesssionUtil.removeSession("backMap");
                        backParmaInfor = addBackMapInfor(ccompanyID, companyName, cc);
                    }
                }
                ccompanyID = backParmaInfor.get("ccompanyId").toString();
                companyName = backParmaInfor.get("companyName").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toWholesaleMall";
    }

    /**
     * 将参数信息保存到回退map session中，session用于更换商家
     *
     * @param ccompanyID  选中往来单位id
     * @param companyName 公司名称
     * @param cc          往来单位
     */
    private Map<String, Object> addBackMapInfor(String ccompanyID, String companyName, CcomCom cc) {
        Map<String, Object> map = MapSesssionUtil.savePfsc_back_SessionForMap(ccompanyID, companyName);
        map.put("companyId", cc.getComanyId());//公司id
        map.put("qbsjCompanyID", cc.getCcompanyId());//全部商家点击时用
        return MapSesssionUtil.saveSessionForMap("backMap", map);
    }

    /**
     * 异步获取一级商品分类
     *
     * @return
     */
    public String ajaxGetOneGoodsClassify() {

        try {
       	 HttpServletRequest request = ServletActionContext.getRequest();
       	 String ccomIDPlatform = request.getParameter("ccomIDPlatform");
       	 if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
       	     result = wholesaleMallService.ajaxGetOneGoodsClassifyPlat(ccomIDPlatform, codePID);
       	 }else{
            result = wholesaleMallService.ajaxGetOneGoodsClassify(ccompanyID, codePID);
       	 }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步获取二级商品分类
     *
     * @return
     */
    public String ajaxGetTwoGoodsClassify() {
        try {
            result = wholesaleMallService.ajaxGetTwoGoodsClassify(ccompanyID, codePID,ccomIDPlatform);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 功能:查询超市有关商品
     *
     * @return
     */
    public String ajaxGetSupermarketGoodsList() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                   SessionWrap.KEY_SHOPCUSCOM);
            if(cus==null){
            	 Map<String, Object> map = new HashMap<String,Object>();
            	 map.put("nologin", "nologin");
            	 JSONObject obj = JSONObject.fromObject(map);
                 result = obj.toString();
                 return "success";
            	
            }
            //1.获取商品信息
            Map<String, Object> map = wholesaleMallService.ajaxGetSupermarketGoodsList(ccompanyID, codePID, search, industryType,ccomIDPlatform,cus.getStaffid(),cus.getCompanyId());

            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步获取规格分类
     *
     * @return
     */
    public String ajaxGetGGFlList() {
        try {
            result = wholesaleMallService.ajaxGetGGFlList(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步将商品添加到购物车
     *
     * @return
     */
    public String ajaxAddShoppingCart() {
        try {
        	
        	 HttpSession session = ServletActionContext.getRequest().getSession();
             SessionWrap sw = SessionWrap.getInstance();
             TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
             if(cus==null){
                 HttpServletRequest request = ServletActionContext.getRequest();
                 String url = request.getHeader("Referer");
                 session.setAttribute("url", url);
             	 Map<String, Object> map = new HashMap<String,Object>();
             	 map.put("nologin", "nologin");
             	 JSONObject obj = JSONObject.fromObject(map);
                  result = obj.toString();
                  return "success";
             	
             }
            //1.异步保存或更新前台传过来的添加信息
        	if(pfscShoppingCart!=null&&(pfscShoppingCart.getCompanyid()==null||pfscShoppingCart.getCompanyid().equals(""))){
        		
        		pfscShoppingCart = wholesaleMallService.getProCartInfo(pfscShoppingCart);
        	}
        	
            String pscId = wholesaleMallService.ajaxAddShoppingCart(pfscShoppingCart,cus.getCompanyId(),cus.getStaffid());
            //2.查询保存的购物车中的数量、金额、商品种类
            result = wholesaleMallService.getShopCartInfor(new HashMap<String, Object>(), pscId,cus.getStaffid(),cus.getCompanyId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步移除到购物车商品
     *
     * @return
     */
    public String ajaxRemoveShoppingCart() {
        try {
        	 HttpSession session = ServletActionContext.getRequest().getSession();
             SessionWrap sw = SessionWrap.getInstance();
             TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
             if(cus==null){
             	 Map<String, Object> map = new HashMap<String,Object>();
             	 map.put("nologin", "nologin");
             	 JSONObject obj = JSONObject.fromObject(map);
                  result = obj.toString();
                  return "success";
             	
             }
            //1.购物车数量减一
            wholesaleMallService.jianShoppingCart(pfscShoppingCart);
            //2.购物车数量为0删除购物车货物
            wholesaleMallService.delShoppingCart(pfscShoppingCart);
            //3.查询保存的购物车中的数量、金额、商品种类
            result = wholesaleMallService.getShopCartInfor(new HashMap<String, Object>(), null,cus.getStaffid(),cus.getCompanyId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 跳转购物车列表
     *
     * @return
     */
    public String shoppingCartList() {
    	
         HttpSession session = ServletActionContext.getRequest().getSession();
         SessionWrap sw = SessionWrap.getInstance();
         TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
    	 Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");

         Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(cus.getCompanyId(), cus.getStaffid());
         parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
   
        return "shoppingCartList";
    }

    /**
     * 异步获取购物车列表信息
     *
     * @return
     */
    public String ajaxShoppingCartList() {
        try {
            mapObj = wholesaleMallService.shoppingCartList();
            JSONObject obj = JSONObject.fromObject(mapObj);
            result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 异步修改购物车数量
     *
     * @return
     */
    public String ajaxChangeCartNum() {
        try {
            //1.修改购物车数量
            wholesaleMallService.ajaxChangeCartNum(shoppingCartParmStr);
            //2.删除购物车数据
            if (StringHelper.isNotEmpty(delPscIdStr)) {
                //2.1.获取session数据
                Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
                //2.2.根据手机端传过来的staffid查询状态为在职的account表数据
                String companyId = parmaInfor.get("companyId").toString();
                String staffId = parmaInfor.get("staffId").toString();
                //查询数据
                for (String pscId : delPscIdStr.split("@")) {
                    wholesaleMallService.delShoppingCarts(pscId, companyId, staffId);
                }
            }
            result = "succ";
        } catch (Exception e) {
            e.printStackTrace();
            result = "false";
        }
        return "success";
    }

    /**
     * 购物车结算地址
     *
     * @return
     */
    public String toSettlement() {
        try {
            mapObj = wholesaleMallService.toSettlement(staffAddress, shoppingCartParmStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "orderdetailss";
    }

    /**
     * 跳转商品查询页面
     *
     * @return
     */
    public String toGoodsSearch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        //request.setAttribute("companyId", request.getParameter("companyId"));
        request.setAttribute("companyId", request.getParameter("ccompanyID"));
        //查询往来单位
        ccompanyID = request.getParameter("ccompanyID");
        String hql = "from CcomCom where ccompanyId = ?";
        CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyID});
        if (cc != null) {
            request.setAttribute("companyId", cc.getComanyId());
        }
        return lxType == 0 ? "toGoodsSearch" : "toDpGoodsSearch";
    }

    /**
     * 跳转商家列表页
     *
     * @return
     */
    public String toBussList() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Map<String, Object> backParmaInfor = MapSesssionUtil.getSessionForMap("backMap");
            if (backParmaInfor != null) {
                ccompanyID = backParmaInfor.get("ccompanyId").toString();//选中的当前往来单位id
                companyName = backParmaInfor.get("companyName").toString();//选中的当前公司名称
                request.setAttribute("companyId", backParmaInfor.get("companyId").toString());//当前登录人id
                request.setAttribute("qbsjCompanyID", backParmaInfor.get("qbsjCompanyID").toString());//当前登录人companyid对应的往来单位ccompanyid
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "toBussList";
    }

    /**
     * 异步获取商家列表页
     *
     * @return
     */
    public String ajaxBussList() {
        try {
            //当前页
            int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            List<Object> parms = new ArrayList<Object>();
            Map<String, Object> map = wholesaleMallService.toBussList(parms, longitude, latitude, industryType, search);
            pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, 10, map.get("sql").toString(), "select count(*) from (" + map.get("sql").toString() + ")", parms.toArray());
            map.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 进入大屏购物车列表
     *
     * @return
     */
    @Deprecated
    public String shoppingCartForDpList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Cookie[] cookies = request.getCookies();
        String comID = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("comID")) {
                comID = cookie.getValue();
                break;
            }
        }
        if (ccompanyID != null && !ccompanyID.equals("")) {
            Company cc = (Company) smSerivce.getCompanyByCComID(ccompanyID);
            comID = cc.getCompanyID();
            request.setAttribute("companyName", cc.getCompanyName());
        }
        String fh = smSerivce.findFhForm(posNum, ccompanyID);
        request.setAttribute("companyID", comID);
        request.setAttribute("fh", fh);
        return "shoppingCartForDpList";
    }

    /**
     * 异步获取大屏购物车数据
     *
     * @return
     */
    @Deprecated
    public String ajaxDpShoppingCartList() {
        try {
            List<BaseBean> shoppingCartList = wholesaleMallService.dpShoppingCartList();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goodlist", shoppingCartList);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * GET AND SET METHOD
     */
    public List<BaseBean> getGoodsClassifyList() {
        return goodsClassifyList;
    }

    public void setGoodsClassifyList(List<BaseBean> goodsClassifyList) {
        this.goodsClassifyList = goodsClassifyList;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getCodePID() {
        return codePID;
    }

    public void setCodePID(String codePID) {
        this.codePID = codePID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public PfscShoppingCart getPfscShoppingCart() {
        return pfscShoppingCart;
    }

    public void setPfscShoppingCart(PfscShoppingCart pfscShoppingCart) {
        this.pfscShoppingCart = pfscShoppingCart;
    }


    public String getShoppingCartParmStr() {
        return shoppingCartParmStr;
    }

    public void setShoppingCartParmStr(String shoppingCartParmStr) {
        this.shoppingCartParmStr = shoppingCartParmStr;
    }

    public String getDelPscIdStr() {
        return delPscIdStr;
    }

    public void setDelPscIdStr(String delPscIdStr) {
        this.delPscIdStr = delPscIdStr;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
    }

    public String getCheckCompanyIds() {
        return checkCompanyIds;
    }

    public void setCheckCompanyIds(String checkCompanyIds) {
        this.checkCompanyIds = checkCompanyIds;
    }

    public Map<String, Object> getMapObj() {
        return mapObj;
    }

    public void setMapObj(Map<String, Object> mapObj) {
        this.mapObj = mapObj;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public int getLxType() {
        return lxType;
    }

    public void setLxType(int lxType) {
        this.lxType = lxType;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPfscReturnFlag() {
        return pfscReturnFlag;
    }

    public void setPfscReturnFlag(String pfscReturnFlag) {
        this.pfscReturnFlag = pfscReturnFlag;
    }

	public String getCcomIDPlatform() {
		return ccomIDPlatform;
	}

	public void setCcomIDPlatform(String ccomIDPlatform) {
		this.ccomIDPlatform = ccomIDPlatform;
	}
    
    
}
