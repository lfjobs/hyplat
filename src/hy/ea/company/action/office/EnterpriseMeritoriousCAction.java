package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseMeritorious;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
/**
 * 企业功臣列表 ----公司汇总
 * @author Mr.fan
 *
 */
public class EnterpriseMeritoriousCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseMeritorious enterpriseMeritorious;
	private PageForm pageForm;
	private File photo;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	
	//根据条件查询企业功臣
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseMeritorious);
		return getEnterpriseMeritoriousList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseMeritorious.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseMeritorious = (EnterpriseMeritorious) session.get("tablesearch");
			if(enterpriseMeritorious.getRewardName() != null
					&& !"".equals(enterpriseMeritorious.getRewardName().trim())){
				dc.add(Restrictions.like("rewardName", enterpriseMeritorious.getRewardName(), MatchMode.ANYWHERE));
				
			}if(enterpriseMeritorious.getRewardYear() != null
					&& !"".equals(enterpriseMeritorious.getRewardYear().trim())){
				dc.add(Restrictions.like("rewardYear", enterpriseMeritorious.getRewardYear(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 企业功臣列表
	public String getEnterpriseMeritoriousList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "meritoriouslist";	
	}
	
	//导出企业功臣列表
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID  = account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseMeritorious.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logbook=logBookService.saveCLogBook(companyID,"导出企业功臣公司汇总列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	public EnterpriseMeritorious getEnterpriseMeritorious() {
		return enterpriseMeritorious;
	}

	public void setEnterpriseMeritorious(EnterpriseMeritorious enterpriseMeritorious) {
		this.enterpriseMeritorious = enterpriseMeritorious;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}
	
	
}
