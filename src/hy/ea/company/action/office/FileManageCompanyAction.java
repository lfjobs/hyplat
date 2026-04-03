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
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 文件管理公司汇总列表
 */
@Controller
@Scope("prototype")
public class FileManageCompanyAction {
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
	 * 根据条件查询文件管理公司汇总列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileManage);
		return getaFileManageCompanyList();
	}

	/**
	 * 文件管理公司汇总列表
	 * @return
	 */
	public String getaFileManageCompanyList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getFAList());
		return "fileManageList";	
	}
	
	/**
	 * 文件管理公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getFAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(FileManage.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			fileManage = (FileManage) session.get("tablesearch");
			if(null!=fileManage.getDocumentTitle()&&!"".equals(fileManage.getDocumentTitle())){
				dc.add(Restrictions.like("documentTitle", fileManage.getDocumentTitle(), MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}
	
	/**
	 * 导出文件管理公司汇总列表
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(FileManage.columnHeadings(), baseBeanService.getListByDC(getFAList()));
		CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出文件管理公司汇总列表", account);
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