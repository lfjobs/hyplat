package hy.ea.collage.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.collage.service.PhlBusiSerivce;
import hy.ea.collage.service.PhlProductSerivce;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 拼货拉批发商城产品
 */
@Controller
@Scope("prototype")
public class PhlProductAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private PhlProductSerivce phlProductSerivce;

	private PageForm pageForm;

	private String result;

	private int pageNumber;

	private String search;

	private String  districtPID;
     //查询条件
	private  String disCrit;
	private  String saleCrit;
	private  String placeCrit;
	private  String cateCrit;
	private String priceCrit;
	private String goodsName;
	private String ccompanyID;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);





	/**
	 *
	 * 商家
	 * @return
	 */
	public  String  findSmallProduct(){
		String staffID = "";
        if(tc!=null){
        	staffID = tc.getStaffid();
		}
		pageForm =  phlProductSerivce.getPageFormPro((null != pageForm ? pageForm.getPageNumber() : 1), 20,Constant.NY_ID,placeCrit,cateCrit,disCrit,saleCrit,priceCrit,goodsName,staffID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 *
	 * 跳转搜索页面
	 * @return
	 */
	public String jumpSearch(){
		List<BaseBean> list = phlProductSerivce.hotCateSearch(ccompanyID);
        request.setAttribute("hotlist",list);
		return "jsearch";
	}

	
	
	 /**
     * 查询批发购物车商品数目
     */

    public String shopPFCartGoodNum() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
    			SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        int goodNum = 0;

        try {
            String sql = "select nvl(sum(c.tjNum),0) from DT_PFSCSHOPPINGCART c,dt_ProductPackaging  p where p.ppID = c.ppid and c.staffId = ?  and  c.staffComId = ? and  p.showweixin = ?";
            goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{cus.getStaffid(),cus.getCompanyId(), "01"});

       
            map.put("goodNum", goodNum);
        } catch (Exception e) {
            map.put("goodNum", goodNum);
            //  e.printStackTrace();
        }

        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getDistrictPID() {
		return districtPID;
	}

	public void setDistrictPID(String districtPID) {
		this.districtPID = districtPID;
	}

	public String getDisCrit() {
		return disCrit;
	}

	public void setDisCrit(String disCrit) {
		this.disCrit = disCrit;
	}

	public String getSaleCrit() {
		return saleCrit;
	}

	public void setSaleCrit(String saleCrit) {
		this.saleCrit = saleCrit;
	}

	public String getPlaceCrit() {
		return placeCrit;
	}

	public void setPlaceCrit(String placeCrit) {
		this.placeCrit = placeCrit;
	}

	public String getCateCrit() {
		return cateCrit;
	}

	public void setCateCrit(String cateCrit) {
		this.cateCrit = cateCrit;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getPriceCrit() {
		return priceCrit;
	}

	public void setPriceCrit(String priceCrit) {
		this.priceCrit = priceCrit;
	}
}