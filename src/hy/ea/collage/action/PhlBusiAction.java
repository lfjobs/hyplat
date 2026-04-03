package hy.ea.collage.action;

import hy.ea.collage.service.PhlBusiSerivce;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;

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

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

/**
 *
 * 拼货拉商家
 */
@Controller
@Scope("prototype")
public class PhlBusiAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private PhlBusiSerivce phlBusiSerivce;

	@Resource
	private SDistrictService regionService;
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
	private String companyName;
	private String companyMID;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);


	/**
	 *
	 * 获取省份
	 * @return
	 */
	public String getCDistricts() {

	    Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> distinctlist = phlBusiSerivce.getCDistricts();
		map.put("distinctlist",distinctlist);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 *
	 * 获取城市
	 * @return
	 */
	public String getCityDistricts(){

		Map<String, Object> map = new HashMap<String, Object>();
		List<SDistrict> distinctlist = regionService.getDistrictListByPID(districtPID);
		map.put("distinctlist",distinctlist);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}


	/**
	 *
	 * 商家
	 * @return
	 */
	public  String  findBusiCompany(){
		String staffID = "";
        if(tc!=null){
        	staffID = tc.getStaffid();
		}
		pageForm =  phlBusiSerivce.getPageFormBusi((null != pageForm ? pageForm.getPageNumber() : 1), 10,Constant.NY_ID,companyMID,placeCrit,cateCrit,disCrit,saleCrit,companyName,staffID);
		
		Map<String,List<BaseBean>> pmap = phlBusiSerivce.getShowProduct(pageForm.getList());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		map.put("pmap",pmap);
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyMID() {
		return companyMID;
	}

	public void setCompanyMID(String companyMID) {
		this.companyMID = companyMID;
	}
}