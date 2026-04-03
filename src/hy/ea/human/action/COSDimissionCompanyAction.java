package hy.ea.human.action;

/**
 * 公司离职员工
 *@author 陈小丰
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class COSDimissionCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private COSDimissionStaffVO codi;
	private List<CCode> typelist;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private List<CCode> connectionlist;
	@Resource
	private CompanyService companyserverService;
	
	public InputStream excelStream;
	 //根据条件查询公司离职员工汇总 
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", codi);
		return getListCOSDimissionCompany();
	}
	
	public DetachedCriteria getListBYDC()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(COSDimissionStaffVO.class);
		ArrayList<String> cidi=companyserverService.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		dc.add(Restrictions.in("companyID", cidi));
		if (search != null && search.equals("search")) {
			this.codi = (COSDimissionStaffVO) session.get("tablesearch");
			if(null != codi.getCompanyID() && !"".equals(codi.getCompanyID()))
	        {
	        	 dc.add(Restrictions.eq("companyID", codi.getCompanyID()));
	        }
			if(null != codi.getStaffCode() && !"".equals(codi.getStaffCode()))
			{
				dc.add(Restrictions.like("staffCode", codi.getStaffCode(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffName() && !"".equals(codi.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", codi.getStaffName(),MatchMode.ANYWHERE));
			}
			if(null != codi.getStaffIdentityCard() && !"".equals(codi.getStaffIdentityCard()))
			{
				dc.add(Restrictions.like("staffIdentityCard", codi.getStaffIdentityCard(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	
	//得到公司离职员工汇总列表
	public String getListCOSDimissionCompany() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		return "list";
	}
	/**
	 * 导出公司离职员工汇总列表
	 * @param : account organizationID
	 * @return : showexcel
	 */
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(COSDimissionStaffVO.columnHeadings(), baseBeanService.getListByDC(getListBYDC()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出离职员工列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public COSDimissionStaffVO getCodi() {
		return codi;
	}
	public void setCodi(COSDimissionStaffVO codi) {
		this.codi = codi;
	}
	public CCodeService getCodeService() {
		return codeService;
	}
	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public CompanyService getCompanyserverService() {
		return companyserverService;
	}

	public void setCompanyserverService(CompanyService companyserverService) {
		this.companyserverService = companyserverService;
	}
	
}
