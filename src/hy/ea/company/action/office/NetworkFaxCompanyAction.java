package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetworkFax;
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
 * 网络传真管理公司汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class NetworkFaxCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService; 
	
	private NetworkFax networkFax;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	
	/**
	 * 根据条件查询网络传真管理公司汇总
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", networkFax);
		return getListNetworkFaxCompany();
	}
	
	/**
	 * 网络传真管理公司汇总列表 
	 * @return
	 */
	public String getListNetworkFaxCompany() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "networkfax";	
	}

	/**
	 * 网络传真管理公司汇总列表  、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetworkFax.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			networkFax = (NetworkFax) session.get("tablesearch");
			if (networkFax.getFaxNum()!=null && !"".equals(networkFax.getFaxNum().trim())) {
				dc.add(Restrictions.like("faxNum", networkFax.getFaxNum().trim(),MatchMode.ANYWHERE));
			}
			if (networkFax.getFaxCompanyID()!=null && !"".equals(networkFax.getFaxCompanyID().trim())) {
				dc.add(Restrictions.like("faxCompanyID", networkFax.getFaxCompanyID().trim(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	/**
	 * 导出网络传真管理公司汇总
	 * @return
	 */
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID=(String) session.get("organizationID");
		excelStream = excelService.showExcel(NetworkFax.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络传真公司汇总", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public NetworkFax getNetworkFax() {
		return networkFax;
	}

	public void setNetworkFax(NetworkFax networkFax) {
		this.networkFax = networkFax;
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
}