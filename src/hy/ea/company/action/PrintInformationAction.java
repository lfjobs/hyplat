package hy.ea.company.action;

/**
 * 印信息
 *@author 
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.PrintInformation;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class PrintInformationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PrintInformation printInfo;
	private List<BaseBean> baseBeanList;
	private String staffIDS;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	public InputStream excelStream;

	public String savePrintInformation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		baseBeanList = new ArrayList<BaseBean>();
		String[] staffID = staffIDS.split(",");
		for (int i = 0; i < staffID.length; i++) {
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID=? ", new Object[] { staffID[i] });
			PrintInformation printInfos = new PrintInformation();
			printInfos.setCredentialsName(printInfo.getCredentialsName());
			printInfos.setCredentialsType(printInfo.getCredentialsType());
			printInfos.setCredentialsDate(printInfo.getCredentialsDate());
			printInfos.setCredentialsDate2(printInfo.getCredentialsDate2());
			printInfos.setAddress(printInfo.getAddress());
			printInfos.setPrintInfoID(serverService
					.getServerID("printinformation"));
			printInfos.setAccountID(account.getAccountID());
			printInfos.setCompanyID(account.getCompanyID());
			printInfos.setCreateDate(new Date());
			printInfos.setStaffCode(staff.getStaffCode());
			printInfos.setStaffName(staff.getStaffName());
			printInfos.setCredentialsCode(printInfo.getCredentialsCode());
			printInfos.setCredentialsTitle(printInfo.getCredentialsTitle());
			printInfos.setStaffIdentityCard(staff.getStaffIdentityCard());
			printInfos.setStaffID(staff.getStaffID());
			printInfos.setPhoto(staff.getPhoto());
			baseBeanList.add(printInfos);
		}
		ActionContext.getContext().put("size", printInfo.getSize());

		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "printinfo";
	}

	/*
	 * 查询打印信息
	 */
	/** *************************************************************** */
	// 根据条件查询打印信息
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", printInfo);
		return getPrintInList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(PrintInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			printInfo = (PrintInformation) session.get("tablesearch");
			if (null != printInfo.getStaffName()
					&& !"".equals(printInfo.getStaffName())) {
				dc.add(Restrictions.like("staffName", printInfo.getStaffName(),
						MatchMode.ANYWHERE));
			}
			if (null != printInfo.getStaffCode()
					&& !"".equals(printInfo.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", printInfo.getStaffCode(),
						MatchMode.ANYWHERE));
			}
			if (null != printInfo.getStaffIdentityCard()
					&& !"".equals(printInfo.getStaffIdentityCard())) {
				dc.add(Restrictions.like("staffIdentityCard", printInfo
						.getStaffIdentityCard(), MatchMode.ANYWHERE));
			}
			if (null != printInfo.getCredentialsName()
					&& !"".equals(printInfo.getCredentialsName())) {
				dc.add(Restrictions.like("credentialsName", printInfo
						.getCredentialsName(), MatchMode.ANYWHERE));
			}
			if (null != printInfo.getCredentialsCode()
					&& !"".equals(printInfo.getCredentialsCode())) {
				dc.add(Restrictions.like("credentialsCode", printInfo
						.getCredentialsCode(), MatchMode.ANYWHERE));
			}

			if (null != printInfo.getCredentialsType()
					&& !"".equals(printInfo.getCredentialsType())) {
				dc.add(Restrictions.like("credentialsType", printInfo
						.getCredentialsType(), MatchMode.ANYWHERE));
			}
			if (null != printInfo.getCredentialsDate()
					&& !"".equals(printInfo.getCredentialsDate())) {
				dc.add(Restrictions.like("credentialsDate", printInfo
						.getCredentialsDate(), MatchMode.ANYWHERE));
			}
			if (null != printInfo.getCreateDate()
					&& !"".equals(printInfo.getCreateDate())) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss:sss");
				try {
					dc.add(Restrictions.between("createDate",
							dateFormat.parse(printInfo.getCreateDate()
									+ " 00:00:00:000"), dateFormat
									.parse(printInfo.getCreateDate()
											+ " 23:59:59:999")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (null != printInfo.getAddress()
					&& !"".equals(printInfo.getAddress())) {
				dc.add(Restrictions.like("address", printInfo.getAddress(),
						MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 得到打印信息列表
	public String getPrintInList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList());

		return "printInlist";
	}

	// 导出打印信息列表

	public String showExcel() {
		excelStream = excelService.showExcel(PrintInformation.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "导出打印信息", account);
		baseBeanService.update(cLogBook);
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public PrintInformation getPrintInfo() {
		return printInfo;
	}

	public void setPrintInfo(PrintInformation printInfo) {
		this.printInfo = printInfo;
	}

	public String getStaffIDS() {
		return staffIDS;
	}

	public void setStaffIDS(String staffIDS) {
		this.staffIDS = staffIDS;
	}

	public List<BaseBean> getBaseBeanList() {
		return baseBeanList;
	}

	public void setBaseBeanList(List<BaseBean> baseBeanList) {
		this.baseBeanList = baseBeanList;
	}

}
