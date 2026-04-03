package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.FileManage;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 文件管理集团汇总列表
 */
@Controller
@Scope("prototype")
public class FileManageGroupAction {
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;

	private FileManage fileManage;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	
	/**
	 * 根据条件查询文件管理集团汇总列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileManage);
		return getaFileManageGroupList();
	}

	/**
	 * 文件管理集团汇总列表
	 * @return
	 */
	public String getaFileManageGroupList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getFileList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		
		return "fileManageList";	
	}
	
	/**
	 * 文件管理集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getFileList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from FileManage t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		
		if (search != null && search.equals("search")){
			fileManage = (FileManage) session.get("tablesearch");
			if(fileManage.getDocumentTitle() != null 
					&& !"".equals(fileManage.getDocumentTitle().trim())){
				hql += " and t.documentTitle like ?";
				parms.add("%" +fileManage.getDocumentTitle().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 导出文件管理集团汇总列表
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getFileList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		
		excelStream = excelService.showExcel(FileManage
				.columnHeadings(), baseBeanService
				.getListBeanByHqlAndParams(hql, parms));
		CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出文件管理集团汇总列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
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
}