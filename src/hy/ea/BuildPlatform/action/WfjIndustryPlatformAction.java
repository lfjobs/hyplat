package hy.ea.BuildPlatform.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.BuildPlatform.service.WfjIndustryPlatfromService;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("WfjIndustryPlatformAction")
@Scope("prototype")
public class WfjIndustryPlatformAction {
	private static final Logger logger = LoggerFactory.getLogger(WfjIndustryPlatformAction.class);
	@Resource
	private WfjIndustryPlatfromService wfjIndustryPlatfromService;
	
	private PageForm pageForm;
	private List<BaseBean> list;
	private String result; 
	private Company company;
	private CDetail cdl;
	private File pictureList; // 图片集合
	private String pictureListFileName; // 文件名
	private String ccmomtype;
	private String ppidUser;
	private String flag;
	
	private int pageNumber;
	
	
public String getIndustryList(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		String sccid = request.getParameter("sccid");
		String cumname = request.getParameter("cumname");
		Session(sccid);
		Map<String, Object> maps = wfjIndustryPlatfromService.getQuery(cumname);
		if("ajax".equals(ccmomtype)){
			
		Map<String, Object> map = new HashMap<String, Object>();
		Object object = maps.get("list");
		Object objects = maps.get("lists");
		if(object!=null||objects!=null)
			
				map.put("list", object);
				map.put("lists", objects);
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			
		}
		return "getIndustry";
	}
	
	
	private void Session(String sccid){
		HttpSession session =ServletActionContext.getRequest().getSession();
		session.setAttribute("sccid", sccid);
		
	}
	// 行业
	public final String industry() {

		return "industry";
	}

	public final String getAddPlatform() {

		return "AddPlatform";
	}

	public final String getQuery() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		String ppid = request.getParameter("ppid");
		String typeMon = wfjIndustryPlatfromService
				.getQueryPlatfrom(type, ppid);

		if ("01".equals(typeMon)) {// 微分金平台、、显示国家和国际p201612089W7PQNDBEM0000000013

			return "Query01";
		} else if ("02".equals(typeMon)) {// 国家平台、、显示中国，美国

			return "Query02";
		} else if ("03".equals(typeMon)) {// 省级，县级，行业平台

			return "Query03";

		} else if ("04".equals(typeMon)) {// 城市北京上海，，县级和村级的，，行业的

			return "Query04";

		}
		return "success";

	}

	public final String getAjax() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ppid = request.getParameter("ppid");
		String content = request.getParameter("content");
		String param = request.getParameter("param");
		pageForm = wfjIndustryPlatfromService.getQueryAjax(ppid, content,
				pageForm,param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 行业导航 一级行业
	 */
	public final String getIndustry() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String codePID = request.getParameter("codePID");
		List<BaseBean> industryList = wfjIndustryPlatfromService
				.getQueryIndustry(codePID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryList", industryList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public final String getAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		request.setAttribute("companyIdentifier", company.getCompanyName());
		int typeNumber = 0;
		wfjIndustryPlatfromService.getAdd(ccmomtype,company, cdl,
				typeNumber, path, pictureList, pictureListFileName,ppidUser);
		
		return "reginst";
	}
	
	
	public final String getVerification() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String ppid = request.getParameter("ppid");
		String voi = wfjIndustryPlatfromService.Verification(ppid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("voi", voi);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}
	
	public final String getVerificationCompany() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String company = request.getParameter("company");
		String voi = wfjIndustryPlatfromService.VerificationCompany(company);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("voi", voi);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}
	
	public final String getQueryCompany(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String ssid = (String) request.getSession().getAttribute("sccid");
		PageForm pageform = wfjIndustryPlatfromService.getQueryCompany(pageNumber,name,ssid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageform", pageform);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String findPlatForm(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");
		String platFromName = request.getParameter("platFromName");
		List<BaseBean> platform = wfjIndustryPlatfromService.findPlatForm(staffID,platFromName) ;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("platform",platform);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String addOrDelPlatForm(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");
		String ccompanyID = request.getParameter("ccompanyID");
		String platformID = request.getParameter("platformID");
		String flag = request.getParameter("flag");
		Boolean	b=true;
		try {
			wfjIndustryPlatfromService.addOrDelPlatForm(staffID,ccompanyID,platformID,flag);
		}catch (Exception e){
			b = false;
			logger.error("操作异常", e);
		}
		JSONObject json=new JSONObject();
		json.accumulate("b", b);
		result=json.toString();
		return "success";
	}

	public String platformByStaff(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");
		Map<String,Object> map = new HashMap<String,Object>();
		List<BaseBean> platform  = wfjIndustryPlatfromService.platformByStaff(staffID);
		map.put("platform",platform);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";

	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CDetail getCdl() {
		return cdl;
	}

	public void setCdl(CDetail cdl) {
		this.cdl = cdl;
	}

	public File getPictureList() {
		return pictureList;
	}

	public void setPictureList(File pictureList) {
		this.pictureList = pictureList;
	}

	public String getPictureListFileName() {
		return pictureListFileName;
	}

	public void setPictureListFileName(String pictureListFileName) {
		this.pictureListFileName = pictureListFileName;
	}

	public String getCcmomtype() {
		return ccmomtype;
	}

	public void setCcmomtype(String ccmomtype) {
		this.ccmomtype = ccmomtype;
	}

	public String getPpidUser() {
		return ppidUser;
	}

	public void setPpidUser(String ppidUser) {
		this.ppidUser = ppidUser;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
