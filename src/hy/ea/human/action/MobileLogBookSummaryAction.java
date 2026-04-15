package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.human.vo.LogBookSummary;
import hy.ea.bo.human.vo.LokBookPrintVO;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.LogBookService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/*
 * 工作日志汇总 LogBookSummaryAction
 */
@Controller
@Scope("prototype")
public class MobileLogBookSummaryAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	@Resource
	private LogBookService showlogBookService;

	private LogBookSummary logbooksummary;

	private COrganization corganization;
	private PageForm pageForm;
	private String sdate;
	private String edate;
	private String search;
	private String staffName;
	private String result;
	private String companyID;
	private String status;
	private List<CCode> scoreSortlist;
	private List<BaseBean> organizationlist;
	private int pageNumber;
	private List<BaseBean> comlist;

	private LokBookPrintVO logBookPrintVo;
	private List<BaseBean> listLogBookSummary;
	private List<LokBookPrintVO> logBookPrintVoList;

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", logbooksummary);
		return getListLogBook();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(LogBookSummary.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			this.logbooksummary = (LogBookSummary) session.get("tablesearch");
			dc.add(Restrictions.like("staffName",
					logbooksummary.getStaffName(), MatchMode.ANYWHERE));
			dc.add(Restrictions.like("scoreSort",
					logbooksummary.getScoreSort(), MatchMode.ANYWHERE));
			if (!("").equals(logbooksummary.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", logbooksummary
						.getStaffCode(), MatchMode.ANYWHERE));
			}
			if (!companyID.equals(logbooksummary.getOrganizationID())) {
				//System.out.println(logbooksummary.getOrganizationID());
				dc.add(Restrictions.like("organizationID", logbooksummary
						.getOrganizationID(), MatchMode.ANYWHERE));
			}
			if (!("").equals(sdate) && sdate != null && edate != null
					&& !("").equals(edate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("todaydate", dateFormat
							.parse(sdate), dateFormat.parse(edate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (null != logbooksummary.getStatus()
					&& !("").equalsIgnoreCase(logbooksummary.getStatus())) {
				dc.add(Restrictions.eq("status", logbooksummary.getStatus()));
			}

		}
		dc.addOrder(Order.asc("staffID"));
		dc.addOrder(Order.desc("todaydate"));
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		return dc;
	}

	// 工作日志汇总列表
	public String getListLogBook() {
		pageForm = baseBeanService.getPageFormByDC((pageNumber), (1), getList());
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			//System.out.println(outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// return "list";
	}

	//JSON取得部门列表
	 public String getoList()
	 {
		    CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			if(account == null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nologin", 1);
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			}
			String companyID = account.getCompanyID();
			Object[] params1 = { companyID };
		    String ohql = "from COrganization where companyID=? and Status = '00'";
		    organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		    pageForm = baseBeanService.getPageFormByDC((pageNumber), (1), getList());
		    List<BaseBean> compareOList = getCutAcctOrg();
		    Map<String, Object> map = new HashMap<String, Object>();
	        map.put("organizationlist", organizationlist);
	        map.put("compareOList", compareOList);
	        JSONObject oj = JSONObject.fromObject(map);
	        result = oj.toString();
	        organizationlist=null;
	        compareOList=null;
	        return "list";
	 }
	 
	 /**
	  * 获取责任人为当前账户的所有部门
	  * @return
	  */
	 private List<BaseBean> getCutAcctOrg(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount acct = (CAccount) session.get("account");
		 String hql = " from COrganization org where org.companyID = ? " +
		 		"and org.organizationID in " +
		 		"(select ag.organizationID from Agencies ag where ag.staffID =?)";
		 return baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{acct.getCompanyID(),acct.getStaffID()});
	 }

	// 导出
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(LogBookSummary.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出工作日志", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/*
	 * 积分汇总（员工工资管理）
	 * 
	 */
	public String getListLogBookIntegral() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String hql = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName), "
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end ) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ) "
					+ "from LogBookSummary j where ";
			String hql1 = " j.companyID=? and todaydate >=? and todaydate<=? and j.bisect is not null group by j.staffName,j.staffID order by j.staffName";
			String hql2 = "";
			String hql3 = "";
			Object[] params = { account.getCompanyID(),
					dateFormat.parse(sdate), dateFormat.parse(edate) };
			if (staffName != null && !"".equals(staffName)) {
				hql2 = hql + " j.staffName like '%" + staffName + "%' and "
						+ hql1;
				hql3 = "select count(*) from LogBookSummary j where j.logBookKey in ( select max(j.logBookKey) from LogBookSummary j where j.companyID=? and j.staffName like  '%"
						+ staffName
						+ "%' and todaydate >=? and todaydate<=? and j.bisect is not null group by j.staffID)";
			} else {
				hql2 = hql + hql1;
				hql3 = "select count(*) from LogBookSummary j where j.logBookKey in ( select max(j.logBookKey) from LogBookSummary j where j.companyID=?  and todaydate >=? and todaydate<=? and j.bisect is not null group by j.staffID)";
			}
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql2, hql3, params);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "LogBookIntegral";
	}

	public List<BaseBean> salaryIntegralList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String hql = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName), "
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end ) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
					+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ) "
					+ "from LogBookSummary j where ";
			String hql1 = " j.companyID=? and todaydate >=? and todaydate<=? and j.bisect is not null group by j.staffName,j.staffID order by j.staffName";
			Object[] params = { account.getCompanyID(),
					dateFormat.parse(sdate), dateFormat.parse(edate) };
			String hql2 = "";
			if (staffName != null && !"".equals(staffName)) {
				hql2 = hql + " j.staffName like '%" + staffName + "%' and "
						+ hql1;
			} else {
				hql2 = hql + hql1;
			}
			return baseBeanService.getListBeanByHqlAndParams(hql2, params);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 员工工资导出
	 */
	public String showIntegralExcel() {
		excelStream = showlogBookService.showExcel(SalaryIntegral
				.columnHeadings(), salaryIntegralList());
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出员工工资", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// ///////////////*****************公司工作日志汇总********************/
	public String tocompanySearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", logbooksummary);
		return getListcompanyLogBook();
	}

	private DetachedCriteria getcompanyList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(LogBookSummary.class);
		dc.add(Restrictions
				.in("companyID", companyserverService
						.getCompanyAndItsChildrenIDs((String) session
								.get("companyID"))));
		if (search != null && search.equals("search")) {
			this.logbooksummary = (LogBookSummary) session.get("tablesearch");
			dc.add(Restrictions.like("staffName",
					logbooksummary.getStaffName(), MatchMode.ANYWHERE));
			dc.add(Restrictions.like("scoreSort",
					logbooksummary.getScoreSort(), MatchMode.ANYWHERE));
			if (!("").equals(logbooksummary.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", logbooksummary
						.getStaffCode(), MatchMode.ANYWHERE));
			}
			if (logbooksummary.getCompanyID() != null
					&& !"".equals(logbooksummary.getCompanyID()))
				dc.add(Restrictions.eq("companyID", logbooksummary
						.getCompanyID()));
			if (!("").equals(sdate) && sdate != null && edate != null
					&& !("").equals(edate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("todaydate", dateFormat
							.parse(sdate), dateFormat.parse(edate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.asc("staffID"));
		dc.addOrder(Order.desc("todaydate"));
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		return dc;
	}

	// 工作日志汇总列表
	public String getListcompanyLogBook() {

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getcompanyList());
		return "clist";
	}

	// 导出

	public String showCExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		excelStream = excelService.showExcel(LogBookSummary.columnHeadings(),
				baseBeanService.getListByDC(getcompanyList()));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作日志", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/** *********************************公司工资汇总******************************************* */
	/*
	 * 积分汇总（员工工资管理）
	 * 
	 */
	public String getcompanyIntegral() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		if (!"".equals(companyID) && companyID != null) {
			cids = new ArrayList<String>();
			cids.add(companyID);
		}
		String hql00 = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName), "
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end ) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ) "
				+ "from LogBookSummary j where ";
		String hq11 = "select count(*) from LogBookSummary j where j.logBookKey in (select  max(j.logBookKey) from LogBookSummary j where ";
		String hql01 = "  todaydate >=to_Date('"
				+ sdate
				+ "','yyyy-mm-dd') and todaydate<= to_Date('"
				+ edate
				+ "','yyyy-mm-dd') and j.bisect is not null and ( j.companyID = ";
		String hql02 = "  todaydate >=to_Date('"
				+ sdate
				+ "','yyyy-mm-dd') and todaydate<= to_Date('"
				+ edate
				+ "','yyyy-mm-dd') and j.bisect is not null and ( j.companyID =  ";
		String hql = "";
		if (staffName != null && !"".equals(staffName)) {
			hql = hql00 + " j.staffName like '%" + staffName + "%' and "
					+ hql01;
			hq11 = hq11 + " j.staffName like '%" + staffName + "%' and "
					+ hql02;
		} else {
			hql = hql00 + hql01;
			hq11 = hq11 + hql02;
		}
		String hql2 = "') group by j.companyID,j.staffID,j.staffName  order by j.companyID,j.staffName";
		String hql3 = "')  group by j.companyID,j.staffID";
		for (int i = 0; i < cids.size(); i++) {
			if (i == cids.size() - 1) {
				hql = hql + "'" + cids.get(i) + hql2;
				hq11 = hq11 + "'" + cids.get(i) + hql3 + ")";
				break;
			}
			hql = hql + "'" + cids.get(i) + "'  or j.companyID = ";
			hq11 = hq11 + "'" + cids.get(i) + "'  or j.companyID = ";
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, hq11, null);
		return "cIntegral";
	}

	/*
	 * 公司员工工资导出
	 */
	public String showcompanyExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		if (!"".equals(companyID) && companyID != null) {
			cids = new ArrayList<String>();
			cids.add(companyID);
		}
		String hql00 = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName), "
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end ) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
				+ "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ) "
				+ "from LogBookSummary j where ";
		String hql01 = "  todaydate >=to_Date('"
				+ sdate
				+ "','yyyy-mm-dd') and todaydate<= to_Date('"
				+ edate
				+ "','yyyy-mm-dd') and j.bisect is not null and ( j.companyID = ";
		String hql = "";
		if (staffName != null && !"".equals(staffName)) {
			hql = hql00 + " j.staffName like '%" + staffName + "%' and "
					+ hql01;
		} else {
			hql = hql00 + hql01;
		}
		String hql2 = "') group by j.companyID,j.staffID,j.staffName  order by j.companyID,j.staffID";
		for (int i = 0; i < cids.size(); i++) {
			if (i == cids.size() - 1) {
				hql = hql + "'" + cids.get(i) + hql2;
				break;
			}
			hql = hql + "'" + cids.get(i) + "'  or j.companyID = ";
		}
		List<BaseBean> BaseList = baseBeanService.getListBeanByHqlAndParams(
				hql, null);
		excelStream = showlogBookService.showExcel(SalaryIntegral
				.columnHeadings(), BaseList);

		CLogBook logBook = logBookService.saveCLogBook(null, "导出公司员工工资",
				account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 归档加锁
	 * 
	 * @return lock = on 加锁 lock = off 为未加锁
	 */
	public String toLock() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (!("").equals(sdate) && sdate != null && edate != null
				&& !("").equals(edate)) {
			CLogBook logBook = new CLogBook();
			String lock = ServletActionContext.getRequest()
					.getParameter("lock");
			String hql = null;
			if (lock.equalsIgnoreCase("on")) {
				hql = "update LogBook set status = '01' where todaydate >= ? and todaydate <= ? and companyID = ?";
				logBook = logBookService.saveCLogBook(null, "加锁工作日志汇总(起时间:"
						+ sdate + "结束时间：" + edate + ")", account);

			}
			if (lock.equalsIgnoreCase("off")) {
				hql = "update LogBook set status = '00' where todaydate >= ? and todaydate <= ? and companyID = ?";
				logBook = logBookService.saveCLogBook(null, "解锁工作日志汇总(起时间:"
						+ sdate + "结束时间：" + edate + ")", account);
			}
			beans.add(logBook);
			Object[] params = {
					Utilities.getDateFromString(sdate, "yyyy-MM-dd"),
					Utilities.getDateFromString(edate, "yyyy-MM-dd"),
					account.getCompanyID() };
			if (lock.equalsIgnoreCase("on") || lock.equalsIgnoreCase("off"))
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
						new String[] { hql }, params);
		}
		return getListLogBook();
	}

	// 打印某天的工作日志
	public String toDaYin() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc1 = DetachedCriteria.forClass(CStaffCos.class);
		dc1.add(Restrictions.eq("companyID", companyID));
		staffName = logbooksummary.getStaffName();
		if (null != staffName && !("").equals(staffName)) {
			dc1.add(Restrictions.like("staffName", staffName,
					MatchMode.ANYWHERE));
		}
		List<BaseBean> CStaffCosList = baseBeanService.getListByDC(dc1);
		List<LokBookPrintVO> bookPrintVOAddList = new ArrayList<LokBookPrintVO>();
		if (CStaffCosList.size() > 0) {
			for (BaseBean baseBean : CStaffCosList) {
				CStaffCos cos = (CStaffCos) baseBean;
				String hql = "from LogBookSummary  where todaydate = ? and staffID = ? and companyID = ?";
				Object[] params = { logbooksummary.getTodaydate(),
						cos.getStaffID(), account.getCompanyID() };
				List<BaseBean> logbooklist = baseBeanService
						.getListBeanByHqlAndParams(hql, params);
				if (logbooklist.size() > 0) {
					LokBookPrintVO bookPrintVO = new LokBookPrintVO();
					bookPrintVO.setLokBookPrintVOID(serverService
							.getBillID(companyID + "logbook"));
					bookPrintVO.setCompanyID(cos.getCompanyID());
					bookPrintVO.setCompanyName(cos.getCompanyName());
					bookPrintVO.setStaffID(cos.getStaffID());
					bookPrintVO.setStaffName(cos.getStaffName());
					bookPrintVO.setTodaydate(Utilities.getDateString(
							logbooksummary.getTodaydate(), "yyyy-MM-dd"));
					bookPrintVO.setLogbookList(logbooklist);
					float bisects = 0;
					for (BaseBean bean : logbooklist) {
						LogBookSummary summary = (LogBookSummary) bean;
						if (summary.getBisect() != null) {
							if (summary.getScoreSort().equals(
									"scode20100812ikdv89y6kt0000000036")
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000035")
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000034")
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000033")
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000032")) {
								bisects -= Float
										.parseFloat(summary.getBisect());
							} else {
								bisects += Float
										.parseFloat(summary.getBisect());
							}

						}
					}
					bookPrintVO.setBisects(Float.toString(Math
							.round(bisects * 100) / 100f));
					bookPrintVO.setMomey(Float.toString(Math
							.round(bisects * 100 * 20) / 100f));
					bookPrintVOAddList.add(bookPrintVO);
				}
			}
			logBookPrintVoList = bookPrintVOAddList;
		}
		return "logBookPing";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public LogBookSummary getLogbooksummary() {
		return logbooksummary;
	}

	public void setLogbooksummary(LogBookSummary logbooksummary) {
		this.logbooksummary = logbooksummary;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<CCode> getScoreSortlist() {
		return scoreSortlist;
	}

	public void setScoreSortlist(List<CCode> scoreSortlist) {
		this.scoreSortlist = scoreSortlist;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CompanyService getCompanyserverService() {
		return companyserverService;
	}

	public void setCompanyserverService(CompanyService companyserverService) {
		this.companyserverService = companyserverService;
	}

	public List<BaseBean> getComlist() {
		return comlist;
	}

	public void setComlist(List<BaseBean> comlist) {
		this.comlist = comlist;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public COrganization getCorganization() {
		return corganization;
	}

	public void setCorganization(COrganization corganization) {
		this.corganization = corganization;
	}

	public LokBookPrintVO getLogBookPrintVo() {
		return logBookPrintVo;
	}

	public void setLogBookPrintVo(LokBookPrintVO logBookPrintVo) {
		this.logBookPrintVo = logBookPrintVo;
	}

	public List<BaseBean> getListLogBookSummary() {
		return listLogBookSummary;
	}

	public void setListLogBookSummary(List<BaseBean> listLogBookSummary) {
		this.listLogBookSummary = listLogBookSummary;
	}

	public List<LokBookPrintVO> getLogBookPrintVoList() {
		return logBookPrintVoList;
	}

	public void setLogBookPrintVoList(List<LokBookPrintVO> logBookPrintVoList) {
		this.logBookPrintVoList = logBookPrintVoList;
	}

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

}
