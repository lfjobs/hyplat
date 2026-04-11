package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.FileManage;
import hy.ea.office.service.FileManagerService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zhuozhengsoft.pageoffice.FileSaver;
/**
 * 资料管理列表
 */
@Controller
@Scope("prototype")
public class FileManageAction {
	private static final Logger logger = LoggerFactory.getLogger(FileManageAction.class);
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private FileManagerService fileManagerService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private FileManage fileManage;
	@Resource
	private UpLoadFileService fileService;
	private String result;
	private String type;//1 代表个人 2代表公共

	
	/**
	 * 根据条件查询文件管理列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileManage);
		return getaFileManageList();
	}

	/**
	 * 文件管理列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getFAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(FileManage.class);
		
	    if(type.equals("1")){
	    	dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
	    	dc.add(Restrictions.eq("fileArincipalID", account.getStaffID()));
	    }
	    if(type.equals("2")){
	    	String hql = "from Company where companyID = ?";
	    	Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getCompanyID()});
	    	dc.add(Restrictions.or(
	    			Restrictions.or(
	    					Restrictions.and(Restrictions.eq("groupCompanySn",company.getGroupCompanySn()),Restrictions.eq("shares", "3")),
	    					Restrictions.and(Restrictions.eq("organizationID",organizationID),Restrictions.eq("shares", "1"))
	    					),
	    					Restrictions.and(Restrictions.eq("companyID",account.getCompanyID()),
	    							Restrictions.eq("shares", "2"))
	    							));

	    	
	    }
		dc.addOrder(Order.desc("fileManageDate"));
		if (search != null && search.equals("search")) {
			fileManage = (FileManage) session.get("tablesearch");
			if(null!=fileManage.getDocumentTitle()&&!"".equals(fileManage.getDocumentTitle())){
				dc.add(Restrictions.like("documentTitle", fileManage.getDocumentTitle().trim(), MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}

	/**
	 * 文件管理列表
	 * @return
	 */
	public String getaFileManageList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber),getFAList());
		return "fileManageList";	
	}
	
	/**
	 * 导出文件管理列表
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(FileManage.columnHeadings(), baseBeanService.getListByDC(getFAList()));
		CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出文件管理列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
    
	/**
	 * 保存文件管理列表
	 * @return
	 */
    public String saveFileManage(){
    	try{
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
//		if (fileManage.getPhoto() != null) {
//			String path = ServletActionContext.getRequest().getSession()
//					.getServletContext().getRealPath("/");
//			String photoPath = fileService.savePhoto(path, fileManage.getPhotoFileName(),
//					fileManage.getPhoto(), account.getCompanyID(), "office/filemanage/"
//							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
//			fileManage.setFileAccessories(photoPath);
//		}
		
		String fileAccessories = fileManage.getFileAccessories();
		if(fileAccessories!=null&&!fileAccessories.equals("")){
			fileManage.setFileAccessories(fileAccessories.substring(0,(fileAccessories.length()-1)));
		}
		if (fileManage.getFileManageID() == null
				|| "".equals(fileManage.getFileManageID())) {
			fileManage.setFileManageID(serverService.getServerID("fileManageID"));
			parameter = "添加文件管理列表(公文标题:"+fileManage.getDocumentTitle()+")";
		}
		else
		{
		 String hql2="from FileManage where companyID=?  and fileManageID=?";
		 FileManage aff=(FileManage) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),fileManage.getFileManageID() });
		 parameter = "修改文件管理列表(公文标题:"+aff.getDocumentTitle()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		fileManage.setCompanyID(account.getCompanyID());
		fileManage.setOrganizationID(organizationID);
		String hqlstaff = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{account.getStaffID()});
		fileManage.setFileArincipal(staff.getStaffName());
		fileManage.setFileArincipalID(account.getStaffID());
		String hqlcom = "from Company where companyID = ?";
		Company company  = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{account.getCompanyID()});
		fileManage.setPlanUnit(company.getCompanyName());
		fileManage.setFileManageDate(new Date());
		fileManage.setGroupCompanySn(company.getGroupCompanySn());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(fileManage);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	}catch(Exception e){
    		logger.error("操作异常", e);
    	}
		return "success";
    }

    /**
     * 删除文件管理列表
     * @return
     */
	 public String delFileManage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
	    Object[] params = {account.getCompanyID(),fileManage.getFileManageID()};
	    String hql1=" from FileManage where companyID=? and fileManageID=?" ;
		FileManage cf=(FileManage) baseBeanService.getBeanByHqlAndParams(hql1, params);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除文件管理列表(公文标题:"+cf.getDocumentTitle()+")", account);
    	String hql="delete from FileManage where  companyID=? and fileManageID=?";
    	List<BaseBean> beans=new ArrayList<BaseBean>();
    	beans.add(logbook);
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "success";
    }
	 
	 
	
   /**
    * 
    * 将pdf文件转换为swf
    * 
    */
	@SuppressWarnings("deprecation")
	public String txtToPdf() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String sourcePath = (String) request.getParameter("filePath");
		String fileName = sourcePath
				.substring(sourcePath.lastIndexOf('/') + 1);
		String realPath = ServletActionContext.getRequest().getRealPath(
				"/");
		//realPath = realPath.substring(0, realPath.lastIndexOf('/'));
		String fullPath = realPath+(sourcePath.replace(".txt", ".pdf").replace("/","\\"));
		Map<String, Object> map = new HashMap<String, Object>();
		File ff = new File(fullPath);
		 if(ff.exists()){
	 	    	map.put("docpath", sourcePath.replace(".txt", ".pdf").replace("\\","/"));
	 	    }else{
	 	    	String pdfPath = null;
	 			
	 			
	 			try {
	 				pdfPath = fileManagerService.makePDF(sourcePath, realPath,
	 						fileName);
	 				map.put("result", pdfPath);
	 			} catch (Exception e) {
	 			
	 				logger.error("操作异常", e);
	 			}
	 	    }
		
		
		
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 保存Office
	 * @return
	 */
	public String saveWord() {
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		HttpServletRequest request = ServletActionContext.getRequest();
		String docPath = request.getParameter("docPath");
		HttpServletResponse response = ServletActionContext.getResponse();
         FileSaver fs = new FileSaver(request, response);

		
		try {
			
			fs.saveToFile(path+docPath);
		} catch (Exception e) {
			logger.error("操作异常", e);
		} finally {
			try {
				fs.close();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
		return "none";
	}
	
	
	/**
	 * 
	 * 共享
	 * @return
	 */
	public String setShare(){
		
		String hql = "from FileManage where fileManageID = ?";
		FileManage fm = (FileManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{fileManage.getFileManageID()});
		fm.setShares(fileManage.getShares());
		fm.setShareDate(new Date());
		baseBeanService.update(fm);
		return "success";
		
		
	}

    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public FileManage getFileManage() {
		return fileManage;
	}

	public void setFileManage(FileManage fileManage) {
		this.fileManage = fileManage;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	
	
}
