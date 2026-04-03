package hy.ea.production.action.cprocedure.drive;



import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

/**
 * 驾校
 */
@Controller
@Scope("prototype")
public class DriveManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private String search;
	private String parameter;
	private String companyID;
	private String staffID;
	private Staff staff;
	private File photo;
	private String photoFileName;

	
	/**
	 * 
	 * 登录移动办公判断是学员，还是教练，还是普通员工
	 * @return
	 */
	public String afterLoginIndex(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=? ",
				new Object[] { staffID});
		Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{companyID});
		sw.setObject(session, SessionWrap.KEY_COMPANY, company);
		sw.setObject(session, SessionWrap.KEY_STAFF,staff);
		
		String sql = "select relation from dtContactRelation where staffID = ? and companyID = ?";
		List<String> contactlist = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{staffID,companyID});
		if(contactlist.contains("学员")){
			return "stuindex";
			
		}else if(contactlist.contains("教练")){
			return "teaindex";
			
		}
		
		
		return "stuindex";
		
	}
	
	




	/**
	 * 
	 * 
	 * 获取添加修改页面
	 * 
	 * @return
	 */
	public String getStuBaseInfo() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
	
		
		
		return "stubase";
	}
	
	/**
	 * 
	 * 学员基本信息保存
	 * @return
	 */
	public String saveStuBaseInfo(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
		if(staff.getStaffID()!=null&&staff.getStaffName().equals("")){
			
			if (photo != null) {
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path,photoFileName, photo, company.getCompanyID(),"/android/namecard"
								+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
				staff.setHeadimage(photoPath);
			}
			
			baseBeanService.update(staff);
			
		}
		
		 return "";
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


	public String getStaffID() {
		return staffID;
	}


	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public String getCompanyID() {
		return companyID;
	}


	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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
	
	
}
