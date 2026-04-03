package hy.ea.BuildPlatform.action;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.util.SessionWrap;

import hy.ea.BuildPlatform.service.PeopleManageMenService;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;

@Controller("PeopleManageMenAction")
@Scope("prototype")
public class PeopleManageMenAction {
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private PeopleManageMenService pmmservice;
	private String result;
	private PageForm pageForm;
	private Staff staff;	
	private File photo;
	private String photoFileName;
	private String ppId;
	private ProductPackaging productPackaging;
	private String content;
	private String search;
	public String teamShow(){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		if(ca==null){
			return "login";
		}
		return "teamshow";
	}
	public String manualFriendsAdd(){
		if(staff!=null&&staff.getStaffID().length()>0){
			staff=pmmservice.toGetStaff(staff.getStaffID());
		}
		return "manualFriendsAdd";
	}
	public String personalDetail(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Map<String,Object> map=pmmservice.getPeopleInfo(staff.getStaffID());
		request.setAttribute("map", map);
		return "personalDetail";
	}
	public String getPersonalBrief(){
		Map<String,Object> map=pmmservice.getPersonalBrief(ppId);
		JSONObject json=JSONObject.fromObject(map);
		result=json.toString();
		return "success";
	}
	public String teamAdd(){
		return "teamAdd";
	}
	public String friendsAdd(){
		return "friendsAdd";
	}
	public String companyAdd(){
		return "companyAdd";
	}
	public String ajaxCompanyAdd(){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		pageForm=pmmservice.getStaffForCompany(pageForm, ca!=null?ca.getCompanyID():"",search);
		JSONObject json =new JSONObject();
		json.accumulate("pageForm", pageForm);
		result=json.toString();
		return "success";
	}
	public String ajaxPageForm(){  
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		pageForm=pmmservice.getPageFormBySql(ca, pageForm);
		JSONObject json =new JSONObject();
		json.accumulate("pageForm", pageForm);
		result=json.toString();
		return "success";
	}
	public String ajaxAdd(){
		Boolean b=true;
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		if(photo!=null){
			String photopath="";
			String path = ServletActionContext.getRequest()
					.getSession().getServletContext().getRealPath("/");
			photopath=fileService.savePhoto(path, photoFileName, photo, ca.getStaffID(), "/staff/"
					+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
    		ImageCut.scale(path+photopath, path+jjPath, 100, 120);
			staff.setHeadimage(jjPath);
		}
		try {
			pmmservice.savaOrUpdate(staff,ca,ppId);				
		} catch (Exception e) {
			b=false;
		}
		JSONObject json=new JSONObject();
		json.accumulate("b", b);
		result=json.toString();
		return "success";
	}
	public String ajaxPersonalBrief(){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		ppId=pmmservice.savePersonalBrief(productPackaging,content,ca!=null?ca.getCompanyID():"");
		JSONObject json =new JSONObject();
		json.accumulate("ppId", ppId);
		result=json.toString();
		return "success";
	}
	public String ajaxFansFriends(){
		pageForm=pmmservice.fansFriends(pageForm,search);
		JSONObject json =new JSONObject();
		json.accumulate("pageForm", pageForm);
		result=json.toString();
		return "success";
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
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}
	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
}
