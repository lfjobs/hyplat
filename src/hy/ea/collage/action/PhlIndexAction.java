package hy.ea.collage.action;

import com.tiantai.wfj.service.EarthIndexService;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.collage.service.PhlIndexSerivce;
import hy.ea.collage.service.PhlJoinService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

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
 * 拼货拉首页
 */
@Controller
@Scope("prototype")
public class PhlIndexAction {
	@Resource
	private PhlIndexSerivce phlIndexSerivce;
	@Resource
	private PhlJoinService phlJoinSerivce;
	private PageForm pageForm;

	@Resource
	private EarthIndexService earthIndexService;
	private String result;

	private int pageNumber;

	private String search;
	private String ccompanyID;
	private String companyID;
	private String staffID;
	private String codePID;
	private CarInformation carInformation;
	private Staff staff;
	private Map<String,String> advertmap1;
	private Map<String,String> advertmap2;
	private String codeID;
	private String codeValue;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);



	/**
	 * 拼货拉首页
	 * @return
	 */
	public String getPhlIndex(){



		if(tc!=null) {

			earthIndexService.addBrowseHistory(tc.getSccId(),"市场","");
		}

		if(ccompanyID==null||ccompanyID.equals("")){
    	ccompanyID = Constant.PHL_ID;
	}
     //获取分类
	List<BaseBean> catelist = phlIndexSerivce.getPhlProCate(Constant.PHL_ID);
	
	if(catelist.size()==0){
		ccompanyID = "contactCompany20101230UB4U5884S30000000176";
		catelist = phlIndexSerivce.getPhlProCate(ccompanyID);
	}
	request.setAttribute("catelist",catelist);
	request.setAttribute("ccomIDPlatform",ccompanyID);
	Object cc = phlIndexSerivce.getContactCom(ccompanyID);
	Object[] objs = (Object[])cc;
	request.setAttribute("companyName",objs[0]);
	companyID = objs[1].toString();
	if(Constant.PHL_ID.equals(ccompanyID)){
		request.setAttribute("scale","0");
	}else{
		request.setAttribute("scale","1");

	}
	   advertmap1 = phlIndexSerivce.getAdvert(companyID);
	   advertmap2 = phlIndexSerivce.getAdvert(companyID);

		return "phlindex";
	}

	/**
	 * 最近浏览
	 * @return
	 */
	public String getRecentViewList(){

		if(tc!=null){
			staffID = tc.getStaffid();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(ccompanyID==null||ccompanyID.equals("")){
			ccompanyID = Constant.PHL_ID;
		}
		List<BaseBean> viewlist =  phlIndexSerivce.getRecentViewList(staffID,ccompanyID);
		map.put("viewlist",viewlist);
		JSONObject jo = JSONObject.fromObject(map);

		result = jo.toString();

		return "success";
	}
	
	/**
	 * 记录最近访问的分类
	 * @return
	 */
	public String addRecentView(){
		
		if(tc!=null){
			staffID = tc.getStaffid();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(ccompanyID==null||ccompanyID.equals("")){
			ccompanyID = Constant.PHL_ID;
		}
		 phlIndexSerivce.addRecentView(staffID, ccompanyID, codeID, codeValue);
	
		return "success";
	}


	/**
	 * 获取展馆分类
	 *
	 * @return
	 */
	public String  getExhiProduct(){
		
		
		Map<String,Object> mp = phlIndexSerivce.getExhiProduct(companyID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ts", mp);
		JSONObject jo = JSONObject.fromObject(map);

		result = jo.toString();

       return "success";
	}


	/**
	 * 最新资讯
	 *
	 * @return
	 */
	public String getRecentNews(){
		if(ccompanyID==null||ccompanyID.equals("")){
			ccompanyID = Constant.PHL_ID;
		}
		List<BaseBean> newslist = phlIndexSerivce.getRecentNews(ccompanyID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newslist",newslist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";

	}

	/**
	 *
	 * 首页有货拉物流
	 */
	public String getIndexLogisList(){
		  List<BaseBean>  list = phlJoinSerivce.getListFromCar(Constant.NMSC_ID,ccompanyID);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("carlist",list);
	        JSONObject jo = JSONObject.fromObject(map);
	        result = jo.toString();
	        return "success";


	}



	/**
	 * 获取首页商家
	 * @return
	 */
	public String getIndexBusiComList(){
		List<BaseBean> comlist =  phlIndexSerivce.getIndexBusiComList(ccompanyID);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("comlist",comlist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";


	}


	/**
	 *
	 * 获取首页市场
	 * @return
	 */
	public String getIndexMarketList(){
		if(tc!=null){
			staffID = tc.getStaffid();
		}
		List<BaseBean>  marketlist =  phlIndexSerivce.getIndexMarketList(Constant.NMSC_ID,staffID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("marketlist",marketlist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";


	}

	/**
	 *
	 * 所有农贸市场
	 * @return
	 */
	public String getAllNmMarket(){
		if(tc!=null){
			staffID = tc.getStaffid();
		}
		pageForm = phlIndexSerivce.getPageFormMarket((null != pageForm ? pageForm.getPageNumber() : 1), 10,Constant.NMSC_ID,staffID);
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";

	}


	/**
	 *
	 * 获取首页采购
	 */
	public String getIndexPurchaseList(){

		return "success";

	}

	/**
	 *
	 * 获取首页第一个分类商品以及分类
	 */
	public String getIndexProOrCate(){
		if(ccompanyID==null||ccompanyID.equals("")){
			ccompanyID = Constant.PHL_ID;
		}
		Map<String,Object> map = phlIndexSerivce.getIndexProOrCate(ccompanyID);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";

	}

	/**
	 *
	 * 首页分类商品
  */
	public String getIndexProduct(){
		pageForm =  phlIndexSerivce.getIndexProduct((null != pageForm ? pageForm.getPageNumber() : 1), 20,codePID,Constant.NY_ID,companyID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";


	}


   /////////////////////////////////////////////分类///////////////////////////////////////////////////////////////
    //产品分类
	public String getProCate(){
	   	if(ccompanyID==null||ccompanyID.equals("")){
			ccompanyID = Constant.PHL_ID;
		}
		 List<BaseBean> list = phlIndexSerivce.getProCate(ccompanyID,codePID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("catelist",list);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
            return "success";
	}

	/**
	 *
	 * @return
	 */
	public String carJoin(){


		return null;
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

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCodePID() {
		return codePID;
	}

	public void setCodePID(String codePID) {
		this.codePID = codePID;
	}

	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Map<String, String> getAdvertmap1() {
		return advertmap1;
	}

	public void setAdvertmap1(Map<String, String> advertmap1) {
		this.advertmap1 = advertmap1;
	}

	public Map<String, String> getAdvertmap2() {
		return advertmap2;
	}

	public void setAdvertmap2(Map<String, String> advertmap2) {
		this.advertmap2 = advertmap2;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	
	
	
}