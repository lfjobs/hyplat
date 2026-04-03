package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.vo.LogBookSummary;
import hy.ea.service.CCodeService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
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
 * 
 * 集团工作--日志汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class LogBookCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CCodeService codeService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private LogBookSummary logbooksummary;
	private List<CCode> scoreSortlist;
	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<Object> parms = new ArrayList<Object>();
		String sql = "select l.companyName,l.staffcode,l.staffname,l.todaydate,l.startdate,l.enddate,l.joblocation," +
				" l.jobcontent,l.scoresortname,l.bisect," +
				" case when l.attachment is null then '无'"+ 
				" else l.attachment end," +
				" l.supervisor,l.staffingManage,l.manager" +
				" from view_LogBookSummary l where exists(select g.companyid from dtcompany" +
				" g where l.companyid =  g.companyid and (g.companyID = ? or g.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			logbooksummary = (LogBookSummary) session.get("tablesearch");
			//人员姓名
			if (logbooksummary.getStaffName()!= null
					&& !logbooksummary.getStaffName().trim().equals("")) {
				sql += " and l.staffName like ? ";
				parms.add("%" + logbooksummary.getStaffName().trim() + "%");
			}
			//公司
			if (logbooksummary.getCompanyID()!= null
					&& !logbooksummary.getCompanyID().equals("")) {
				sql += " and l.companyid = ? ";
				parms.add(logbooksummary.getCompanyID());
			}
			//人员编号
			if (logbooksummary.getStaffCode()!= null
					&& !logbooksummary.getStaffCode().trim().equals("")) {
				sql += " and l.staffCode like ? ";
				parms.add("%" + logbooksummary.getStaffCode().trim() + "%");
			}
			//得分
			if (logbooksummary.getScoreSort()!= null
					&& !logbooksummary.getScoreSort().equals("")) {
				sql += " and l.scoreSort = ? ";
				parms.add(logbooksummary.getScoreSort());
			}
		}
		sql += " order by l.companyName,l.staffCode,l.todaydate";
		
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,parms.toArray());
		excelStream = excelService.showExcel(LogBookSummary.columnHeadings(),list);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", logbooksummary);
		return getLogBookList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getLogBookList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		return "getLogBookList";
	}

	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from LogBookSummary t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			logbooksummary = (LogBookSummary) session.get("tablesearch");
			//人员姓名
			if (logbooksummary.getStaffName()!= null
					&& !logbooksummary.getStaffName().trim().equals("")) {
				hql += " and t.staffName like ? ";
				parms.add("%" + logbooksummary.getStaffName().trim() + "%");
			}
			//公司
			if (logbooksummary.getCompanyID()!= null
					&& !logbooksummary.getCompanyID().equals("")) {
				hql += " and t.companyID = ? ";
				parms.add(logbooksummary.getCompanyID());
			}
			//人员编号
			if (logbooksummary.getStaffCode()!= null
					&& !logbooksummary.getStaffCode().trim().equals("")) {
				hql += " and t.staffCode like ? ";
				parms.add("%" + logbooksummary.getStaffCode().trim() + "%");
			}
			//得分
			if (logbooksummary.getScoreSort()!= null
					&& !logbooksummary.getScoreSort().equals("")) {
				hql += " and t.scoreSort = ? ";
				parms.add(logbooksummary.getScoreSort());
			}
		}
		hql += " order by t.companyName,t.staffCode,t.todaydate";
		results.add(hql);
		results.add(parms.toArray());
		return results;
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

	

	public CompanyService getCompanyserverService() {
		return companyserverService;
	}

	public void setCompanyserverService(CompanyService companyserverService) {
		this.companyserverService = companyserverService;
	}

	public LogBookSummary getLogbooksummary() {
		return logbooksummary;
	}

	public void setLogbooksummary(LogBookSummary logbooksummary) {
		this.logbooksummary = logbooksummary;
	}

	public List<CCode> getScoreSortlist() {
		return scoreSortlist;
	}

	public void setScoreSortlist(List<CCode> scoreSortlist) {
		this.scoreSortlist = scoreSortlist;
	}

}
