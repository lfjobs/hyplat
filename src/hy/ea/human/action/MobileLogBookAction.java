package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.LogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class MobileLogBookAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private int pageNumber;
	private String staffName;
	private LogBook logbook;
	private PageForm pageForm;
	private String sdate;
	private String edate;
	private List<BaseBean> logLocklist;
	private String status;
	private String scoreSort;

	private List<CCode> scoreSortlist;
	private Map<String, LogBook> logbookmap;

	/*
	 * 保存工作日志
	 */

	@SuppressWarnings("deprecation")
	public String saveLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		logbook = new LogBook();
		List<BaseBean> logBookList = new ArrayList<BaseBean>();
		if (logbookmap != null) {
			List<String> parermiters = new ArrayList<String>();
			for (LogBook logBooks : logbookmap.values()) {
				this.logbook.setStaffID(logBooks.getStaffID());
				if (logBooks.getPhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, logBooks
							.getPhotoContentType(), logBooks.getPhoto(),
							account.getCompanyID(), "/human/logbook/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					logBooks.setAttachment(photoPath);
				}

				if (null == logBooks.getLogBookID()
						|| "".equals(logBooks.getLogBookID())) {
					logBooks.setLogBookID(serverService.getServerID("logbook"));
					parameter = "添加工作日志(时间:"
							+ logBooks.getTodaydate().toLocaleString() + ")";
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String inputTime = format.format(new Date());
					logBooks.setInputDate(inputTime);
				} else {
					parameter = "修改工作日志(时间:"
							+ logBooks.getTodaydate().toLocaleString() + ")";
				}
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						"from Staff where staffID=?", new Object[] { logBooks
								.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")   (操作时间"
						+ new Date().toLocaleString() + ")";
				if (!Utilities.isNumeric(logBooks.getBisect())) {
					logBooks.setBisect("");
				}
				logBooks.setCompanyID(account.getCompanyID());
				logBooks.setOrganizationID(organizationID);
				logBooks.setStatus("00");
				logBookList.add(logBooks);
				parermiters.add(parameter);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(logBookList,
					null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(organizationID,
					parermiters, account);
		}
		return "success";
	}

	/*
	 * 删除工作日志
	 */

	public String delLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql3 = "select count(*) from LogBook where  companyID = ? and status = ? and logBookID= ?";
		Object[] params1 = { account.getCompanyID(), "01",
				logbook.getLogBookID() };
		if (baseBeanService.getConutByByHqlAndParams(hql3, params1) > 0) {
			return getListLogBook();
		}
		String hql = "delete LogBook where   companyID = ? and logBookID= ? ";
		Object[] params = { account.getCompanyID(), logbook.getLogBookID() };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { logbook.getStaffID() });
		logBookService.saveCLogBook(organizationID, "删除工作日志(人员名称："
				+ staff.getStaffName() + ")", account);
		// baseBeanService.executeHqlByParams(hql, params);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql }, params);
		return "success";
	}

	/*
	 * 得到工作日志列表
	 */

	public String getListLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : scoreSortlist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		LogBook.setOMap(map);
		logLocklist = baseBeanService.getListBeanByHqlAndParams(
				"from LogLock where companyID = ? and status= '00'",
				new Object[] { account.getCompanyID() });
		// 个人日志管理
		if (logbook == null) {
			logbook = new LogBook();
			logbook.setStaffID(account.getStaffID());
			String hqlstaff = "from CStaffCos where companyID = ?  and staffID = ?";
			Object[] paramsstaff = { account.getCompanyID(),
					logbook.getStaffID() };
			CStaffCos cosstaff = (CStaffCos) baseBeanService
					.getBeanByHqlAndParams(hqlstaff, paramsstaff);
			if (cosstaff == null) {
				return "false";
			}
			staffName = cosstaff.getStaffName();
			DetachedCriteria dc = DetachedCriteria.forClass(LogBook.class);
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("staffID", logbook.getStaffID()));
			if ((!("").equals(status)) && null != status) {
				dc.add(Restrictions.eq("status", status));
			}
			if ((!("").equals(scoreSort)) && null != scoreSort) {
				dc.add(Restrictions.eq("scoreSort", scoreSort));
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("todaydate", dateFormat
							.parse(sdate), dateFormat.parse(edate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dc.addOrder(Order.desc("todaydate"));
			dc.addOrder(Order.desc("logBookKey"));
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), dc);
			return "personlist";
		}
		String hqlstaff = "from CStaffCos where companyID = ?  and staffID = ?";
		Object[] paramsstaff = { account.getCompanyID(), logbook.getStaffID() };
		CStaffCos cosstaff = (CStaffCos) baseBeanService.getBeanByHqlAndParams(
				hqlstaff, paramsstaff);
		if (cosstaff == null) {
			return "false";
		}
		staffName = cosstaff.getStaffName();
		DetachedCriteria dc = DetachedCriteria.forClass(LogBook.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", logbook.getStaffID()));
		if ((!("").equals(status)) && null != status) {
			dc.add(Restrictions.eq("status", status));
		}
		if ((!("").equals(scoreSort)) && null != scoreSort) {
			dc.add(Restrictions.eq("scoreSort", scoreSort));
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dc.add(Restrictions.between("todaydate", dateFormat
						.parse(sdate), dateFormat.parse(edate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		dc.addOrder(Order.desc("todaydate"));
		dc.addOrder(Order.desc("logBookKey"));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber), dc);
		return "list";
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

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public LogBook getLogbook() {
		return logbook;
	}

	public void setLogbook(LogBook logbook) {
		this.logbook = logbook;
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

	public void setEdate(String edate) {
		this.edate = edate;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Map<String, LogBook> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, LogBook> logbookmap) {
		this.logbookmap = logbookmap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(String scoreSort) {
		this.scoreSort = scoreSort;
	}

	public List<BaseBean> getLogLocklist() {
		return logLocklist;
	}

	public void setLogLocklist(List<BaseBean> logLocklist) {
		this.logLocklist = logLocklist;
	}
}
