package hy.ea.collage.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.collage.service.SbqIndexSerivce;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 商帮圈首页
 */
@Controller
@Scope("prototype")
public class SbqIndexAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private SbqIndexSerivce sbqIndexSerivce;

	private PageForm pageForm;

	private String result;

	private int pageNumber;
	private int pageSize;


	private String search;

	private String ccomIDPlatform;//行业平台对应的往来单位ID；

	private ProductPackaging productPackaging;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);

	/**
	 *
	 * 根据平台获取发布的动态等
	 * @return
	 */
	public String  getAllInfo(){

		String nav = request.getParameter("nav");
		if(nav!=null&&!nav.equals("")) {
               return "index";
		}else{
			Map<String, Object> map = sbqIndexSerivce.getPageFormInfo((null != pageForm ? pageForm.getPageNumber() : 1), 30, ccomIDPlatform,tc!=null?tc.getStaffid():"");
			JSONObject jo = JSONObject.fromObject(map);
			result = jo.toString();
			return "success";
		}

	}

	/**
	 *
	 * 点赞
	 * @return
	 */
	public String dzOpr(){


		Map<String,Object> map = new HashMap<String,Object>();

		if(tc==null){
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		 }else{
		    String result = sbqIndexSerivce.dzopr(productPackaging.getPpID(),productPackaging.getGoodsID(),tc.getStaffid());
			map.put("result", result);
		}

		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";
	}

	/**
	 *
	 * 附近的人
	 * @return
	 */
	public String getFjPeo(){
		  Map<String,Object> map = new HashMap<String,Object>();
		   if(tc==null){
		    	 String url = request.getHeader("Referer");
			     session.setAttribute("url", url);
			      map.put("login", "login");
	     	}else{
			   pageForm = sbqIndexSerivce.getFjPeo((null != pageForm ? pageForm.getPageNumber() : 1), 30, tc.getStaffid());

			   map.put("pageForm", pageForm);
		   }

			JSONObject jo = JSONObject.fromObject(map);
			result = jo.toString();
			return "success";


	}

	/**
	 * 获取行业平台
	 * @return
	 */
	public String allPlatForm(){
		Map<String,Object> map = new HashMap<String,Object>();
		String staffID = "";
		if(tc!=null){
			staffID = tc.getStaffid();
		}
		pageForm = sbqIndexSerivce.getPlatForm((null != pageForm ? pageForm.getPageNumber() : 1), 30, staffID);
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 *
	 * 关注行业平台
	 * @return
	 */
	public String  followPlatForm(){
		Map<String,Object> map = new HashMap<String,Object>();

		if(tc==null){
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		}else {
			String result = sbqIndexSerivce.followPlatForm(tc, ccomIDPlatform);
			map.put("result", result);
		}

		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 发动态
	 * @return
	 */
	public String sendDynamic(){
		Map<String,Object> map = new HashMap<String,Object>();

		if(tc==null){
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		}else{
			map.put("sccid",tc.getSccId());
		}

		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
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

	public String getCcomIDPlatform() {
		return ccomIDPlatform;
	}

	public void setCcomIDPlatform(String ccomIDPlatform) {
		this.ccomIDPlatform = ccomIDPlatform;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}
}