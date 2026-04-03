package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.MarketSurvey;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

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
/**
 * 	市场调查办
 */
public class MarketSurveyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private MarketSurvey marketSurvey;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private List<BaseBean> beans;
	
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", marketSurvey);
		return getListMarketSurvey();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(MarketSurvey.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			marketSurvey = (MarketSurvey) session.get("tablesearch");
		if (!"".equals(marketSurvey.getSurveyName())){
				dc.add(Restrictions.like("surveyName",marketSurvey.getSurveyName(),MatchMode.ANYWHERE));
		}
		if (!"".equals(marketSurvey.getSurveyRegional())){
			dc.add(Restrictions.like("surveyRegional",marketSurvey.getSurveyRegional(),MatchMode.ANYWHERE));
		}
	}
		return dc;
}

	public String getListMarketSurvey(){
	/*	Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);*/
		
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?15:pageNumber),getList());
		return "marketSurvey";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(MarketSurvey.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出市场调查办", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String addmarketSurvey() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (marketSurvey.getMarketSurveyID() == null
				|| "".equals(marketSurvey.getMarketSurveyID())) {
			marketSurvey.setMarketSurveyID(serverService.getServerID("marketSurvey"));
			parameter = "添加市场调查办(调查名称:"+marketSurvey.getSurveyName()+")";
		}
		else
		{
		 String hql2="from MarketSurvey where companyID=?  and marketSurveyID=?";
		 MarketSurvey aff=(MarketSurvey) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),marketSurvey.getMarketSurveyID() });
		 parameter = "修改市场调查办(调查名称:"+aff.getSurveyName()+")";
		
		}
		beans = new ArrayList<BaseBean>();
		marketSurvey.setOrganizationID(organizationID);
		marketSurvey.setCompanyID(account.getCompanyID());
		beans.add(marketSurvey);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String delmarketSurvey() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] params = { account.getCompanyID(), marketSurvey.getMarketSurveyID()};
	    String hql2="from MarketSurvey where companyID=?  and marketSurveyID=?";
	    MarketSurvey aff=(MarketSurvey) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除市场调查办(调查名称:"+aff.getSurveyName()+")", account);
	    beans.add(logBook);
	    String hql = "delete from MarketSurvey where companyID=?  and marketSurveyID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "success";
	}

	public MarketSurvey getMarketSurvey() {
		return marketSurvey;
	}

	public void setMarketSurvey(MarketSurvey marketSurvey) {
		this.marketSurvey = marketSurvey;
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

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
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
}
