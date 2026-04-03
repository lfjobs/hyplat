package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Qq;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
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
 * QQ管理公司汇总
 * @author :m
 */
@Controller
@Scope("prototype")
public class QqCompanyAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private Qq qq;
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	private InputStream excelStream;
	
	/**
	 * 根据条件查询QQ公司汇总列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", qq);
		return getQqCompanyList();
	}

	/**
	 * QQ公司汇总列表
	 * @return
	 */
	public String getQqCompanyList() {
	    pageForm = baseBeanService.getPageFormByDC((
	    		null != pageForm ? pageForm.getPageNumber() : 1), 
	    		(pageNumber==0?10:pageNumber), getList());
		return "qqlist";	
	}
	
	/**
	 * QQ公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Qq.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			qq = (Qq) session.get("tablesearch");
			if(null!=qq.getQqSequence()&&!"".equals(qq.getQqSequence().trim()))
			{
				dc.add(Restrictions.like("qqSequence", qq.getQqSequence().trim(), MatchMode.ANYWHERE));
			} 
		} 
		return dc;
	}
		
	/**
	 * 导出QQ公司汇总
	 * @return
	 */
	public String showQqCompanyExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(Qq.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出QQ管理公司汇总表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}	
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public Qq getQq() {
		return qq;
	}
	public void setQq(Qq qq) {
		this.qq = qq;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}