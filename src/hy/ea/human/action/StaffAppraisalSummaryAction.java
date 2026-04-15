package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.vo.StaffAppraisalSummary;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/*
 * 综合考评汇总
 * */
@Controller
@Scope("prototype")
public class StaffAppraisalSummaryAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CLogBookService logBookService;
	private StaffAppraisalSummary staffappraisalsummary;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private List<BaseBean> organizationlist;
	private String startdate;
	private String enddate;
	private String result;
	private Company com;
    private List<BaseBean> comlist;
    private int pageNumber;
    
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffappraisalsummary);
		return getStaffAppraisalList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(StaffAppraisalSummary.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			staffappraisalsummary = (StaffAppraisalSummary) session.get("tablesearch");
			if(null!= staffappraisalsummary.getStaffName()&&!"".equals(staffappraisalsummary.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", staffappraisalsummary.getStaffName(), MatchMode.ANYWHERE));
				
			}
			if(!("").equals(startdate)&&startdate!=null&&enddate!=null&&!("").equals(enddate))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("appraisalDate",dateFormat.parse(startdate),dateFormat.parse(enddate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
		} 
		dc.addOrder(Order.desc("appraisalDate"));
		return dc;
	}
	// 考评汇总列表
	public String getStaffAppraisalList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "list";	
	}
	// 导出

	public String showExcel() {
		excelStream = excelService.showExcel(StaffAppraisalSummary.columnHeadings(), baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出考评汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
/////////////*********************公司综合考评汇总**************************/
	//公司综合考评汇总列表
	private DetachedCriteria companyAppraisalList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(StaffAppraisalSummary.class);
		ArrayList<String> cids=companyserverService.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		dc.add(Restrictions.in("companyID", cids));
		if (search != null && search.equals("search")) {
			
			staffappraisalsummary = (StaffAppraisalSummary) session
					.get("tablesearch");
			if(null!=staffappraisalsummary.getStaffCode()&&!"".equals(staffappraisalsummary.getStaffCode()))
			{
				dc.add(Restrictions.like("staffCode", staffappraisalsummary.getStaffCode(), MatchMode.ANYWHERE));
				
			}
			if(null!= staffappraisalsummary.getStaffName()&&!"".equals(staffappraisalsummary.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", staffappraisalsummary.getStaffName(), MatchMode.ANYWHERE));
				
			}
			if(staffappraisalsummary.getCompanyID()!=null && !"".equals(staffappraisalsummary.getCompanyID())){
			    dc.add(Restrictions.eq("companyID", staffappraisalsummary.getCompanyID()));
			}
			if(!("").equals(startdate)&&startdate!=null&&enddate!=null&&!("").equals(enddate))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("appraisalDate",dateFormat.parse(startdate),dateFormat.parse(enddate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
		} 
		dc.addOrder(Order.desc("appraisalDate"));
		return dc;
	}

	// 考评汇总列表
	public String getcompanyAppraisalList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), companyAppraisalList());
		return "clist";	
	}
	// 导出

	public String showcompanyExcel() {
		excelStream = excelService.showExcel(StaffAppraisalSummary.columnHeadings(), baseBeanService.getListByDC(companyAppraisalList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出公司考评汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	public String tocompanySearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffappraisalsummary);
		return getcompanyAppraisalList();
	}
	public StaffAppraisalSummary getStaffappraisalsummary() {
		return staffappraisalsummary;
	}

	public void setStaffappraisalsummary(StaffAppraisalSummary staffappraisalsummary) {
		this.staffappraisalsummary = staffappraisalsummary;
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

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getComlist() {
		return comlist;
	}

	public void setComlist(List<BaseBean> comlist) {
		this.comlist = comlist;
	}

	public Company getCom() {
		return com;
	}

	public void setCom(Company com) {
		this.com = com;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
