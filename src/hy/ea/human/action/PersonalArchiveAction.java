package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPABill;
import hy.ea.bo.human.StaffPersonalFile;
import hy.ea.bo.invoicing.PersonalArchiveBill;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 人事档案
 * 
 * @author mz
 * 
 */
@Controller
@Scope("prototype")
public class PersonalArchiveAction {
	private static final Logger logger = LoggerFactory.getLogger(PersonalArchiveAction.class);
	@SuppressWarnings("unused")
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String category;
	private String search;
	private Staff searchCStaff;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private PersonalArchiveBill paBill;
	private String parameter;
	private Map<String, Staff> goodsmap;
	private Staff staff;
	private String type;
	private StaffPersonalFile personalRecord;
	private String managerID;
	private CashierBillsVO cashierBillsVO1;
	private CashierBillsVO cashierBillsVO2;

	/**
	 * 人事档案——添加、修改页面
	 * 
	 */
	public String toSavePersonalArchive() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		String hqlstaff = "from Staff where staffID = ?";
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlcom = "from Company where companyID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
				new Object[] { account.getStaffID() });
		COrganization org = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hqlorg, new Object[] { (String) session
						.get("organizationID") });
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				hqlcom, new Object[] { companyID });
		paBill = new PersonalArchiveBill();
		paBill.setStaffName(staff.getStaffName());
		paBill.setOrganizationName(org.getOrganizationName());
		paBill.setStaffID(account.getStaffID());
		paBill.setOrganizationID((String) session.get("organizationID"));
		paBill.setCompanyID(company.getCompanyID());
		paBill.setCompanyName(company.getCompanyName());

		return "add";
	}

	/**
	 * JSON取得物品列表
	 */
	public String getGoodsManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql1 = "";
		if (type.equals("in")) {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ?) and not exists(select staffID from StaffPABill p where s.staffID = p.staffID)  and ";
		} else {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ? ) and exists(select staffID from StaffPABill p where s.staffID = p.staffID) and ";
		}
		String hql2 = " (recordCode = ? or staffIdentityCard = ?) order by staffID desc ";
		String hql = "";
		hql = hql1 + hql2;
		if (parameter == null) {
			parameter = "";
		}
		parameter = parameter.trim();
		try {
			Object[] params = { "50", account.getCompanyID(),
					 parameter,parameter};
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";

	}

	/**
	 * 
	 * 根据芯片查档案
	 * 
	 * @return
	 */
	public String QueryArchiveInfo() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql1 = "";
		if (type.equals("in")) {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ?) and not exists(select staffID from StaffPABill p where s.staffID = p.staffID) and ";
		} else {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ? ) and exists(select staffID from StaffPABill p where s.staffID = p.staffID) and ";
		}
		String hql2 = " s.chipid = ?";
		String hql = "";
		hql = hql1 + hql2;
		if (parameter == null) {
			parameter = "";
		}
		parameter = parameter.trim();
		Staff staffinfo = null;
		try {
			Object[] params = { "50", account.getCompanyID(),parameter};
			staffinfo = (Staff) baseBeanService.getBeanByHqlAndParams(hql,params);
			
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffinfo", staffinfo);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";

	}

	/**
	 * 人事档案——保存
	 */
	public String savePArchive() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String groupCompanySn = session.get("groupCompanySn").toString();
			if (groupCompanySn == null) {
				return "login";
			}
			String pabillID = serverService.getServerID("parchive");
			paBill.setPabillID(pabillID);
			parameter = "添加人事档案单据（凭证号" + paBill.getJournalNum() + "）";
			paBill.setOrganizationID(organizationID);
			paBill.setCompanyID(account.getCompanyID());
			paBill.setGroupCompanySn(groupCompanySn);
			paBill.setBillsType("异动单");
			paBill.setBillsDate(Utilities.getDateString(new Date(),
					"yyyy-MM-dd"));
			paBill.setBillStaffName(paBill.getStaffName());
			paBill.setBillStaffID(account.getStaffID());
			paBill.setBillStatus("00");
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			baseBeanList.add(paBill);
			String[] staffs = staff.getStaffID().split(",");
			String[] prices = staff.getPrice().split(",");
			String hql = "from Staff where staffID = ?";
			Staff oldstaff = null;
			Staff manager = null;
			StaffPABill spa = null;
			if (staffs.length != 0) {
				for (int i = 0; i < staffs.length - 1; i++) {
					oldstaff = (Staff) baseBeanService.getBeanByHqlAndParams(
							hql, new Object[] { staffs[i].trim() });
					oldstaff.setPrice(prices[i].trim());
					baseBeanService.update(oldstaff);
					// 物品和单子绑定
					spa = new StaffPABill();
					spa.setSpaId(serverService.getServerID("spa"));
					spa.setPaBillID(pabillID);
					spa.setStaffID(staffs[i].trim());
					baseBeanService.save(spa);

					// 修改人事档案
					personalRecord = new StaffPersonalFile();
					personalRecord.setRecordID(serverService
							.getServerID("personalRecord"));
					personalRecord.setCompanyID(account.getCompanyID());
					personalRecord.setControlStartDate(new Date());
					
					if (managerID != null && !managerID.equals("")) {
						manager = (Staff) baseBeanService.getBeanByHqlAndParams(
								hql, new Object[] { managerID });
						if(manager!=null){
						   personalRecord.setDutyOfficer(manager.getStaffName());
						}
					} else {
						personalRecord.setDutyOfficer(account.getStaffID());
					}
					personalRecord.setStaffID(staffs[i].trim());
					baseBeanService.save(personalRecord);

				}

			}

			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			baseBeanList.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	public String toViewSheet() {
		toView();
		return "edit";
	}

	public void toView() {
		if (paBill != null) {
			String hql1 = "from Staff s where exists(select staffID from StaffPABill p where paBillID = ? and s.staffID = p.staffID)";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, new Object[] { paBill.getPabillID() });
			String hql = " from PersonalArchiveBill where  pabillID=?";
			paBill = (PersonalArchiveBill) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { paBill
							.getPabillID() });

			String hqlorg = "from COrganization  where organizationID = ?";
			String hqlcom = "from Company where companyID = ?";
			COrganization org = (COrganization) baseBeanService
					.getBeanByHqlAndParams(hqlorg, new Object[] { paBill
							.getOrganizationID() });
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { paBill.getCompanyID() });
			if (org != null) {
				paBill.setOrganizationName(org.getOrganizationName());

			}
			if (company != null) {
				paBill.setCompanyName(company.getCompanyName());
			}

			String hqlc = "from CashierBillsVO where ccompanyID = ?";
			String hqls = "from CashierBillsVO where contactUserID = ?";
			cashierBillsVO1 = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hqlc, new Object[] { paBill
							.getCcompanyID() });
			cashierBillsVO2 = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hqls, new Object[] { paBill
							.getCstaffID() });
		}
	}

	/**
	 * 
	 * 入库出库打印异动单
	 * 
	 * @return
	 */
	public String toprintSheet() {
		toView();
		return "printpa";
	}

	// 进入入库出库模块以及库存显示人员信息列表
	public String getListPersonalInfo() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID(), type);
			return "staffinfo";
		}
		String hql = "";
		if (type.equals("in")) {
			hql = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.companyID = ? and c.cosStatus= ?) and not exists(select staffID from StaffPABill p where s.staffID = p.staffID)";
		} else {
			hql = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.companyID = ? and c.cosStatus= ?) and exists(select staffID from StaffPABill p where s.staffID = p.staffID)";
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, new Object[] { account.getCompanyID(), "50" });

		request.setAttribute("type", type);
		return "staffinfo";
	}

	/**
	 * 
	 * 
	 * 获得正式员工列表
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getListPersonalInfo();
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID, String type) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "";
		if (type.equals("in")) {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ?) and not exists(select staffID from StaffPABill p where s.staffID = p.staffID)  and ";
		} else {
			hql1 = "from Staff s where exists(select staffID from COS c where s.staffID = c.staffID  and c.cosStatus= ? and c.companyID = ? ) and exists(select staffID from StaffPABill p where s.staffID = p.staffID) and ";
		}
		String hql2 = " staffCode like ? and staffName like ? and staffIdentityCard like ? and  order by staffID desc ";
		String hql = "";
		hql = hql1 + hql2;
		try {
			Object[] params = { "50", companyID,
					"%" + searchCStaff.getStaffCode() + "%",
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}

	/**
	 * 
	 * 单据查询
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", paBill);
		return getPArchiveSheetList();
	}

	// 出库单据
	public String getPArchiveSheetList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		DetachedCriteria dc = DetachedCriteria
				.forClass(PersonalArchiveBill.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			paBill = (PersonalArchiveBill) session.get("tablesearch");
			if (paBill.getBillStaffName() != null
					&& !paBill.getBillStaffName().equals("")) {
				dc.add(Restrictions.like("billStaffName", paBill
						.getBillStaffName(), MatchMode.ANYWHERE));
			}
		}

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		session.put("tablesearch", paBill);

		return "list";
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public PersonalArchiveBill getPaBill() {
		return paBill;
	}

	public void setPaBill(PersonalArchiveBill paBill) {
		this.paBill = paBill;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Map<String, Staff> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, Staff> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public StaffPersonalFile getPersonalRecord() {
		return personalRecord;
	}

	public void setPersonalRecord(StaffPersonalFile personalRecord) {
		this.personalRecord = personalRecord;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public CashierBillsVO getCashierBillsVO1() {
		return cashierBillsVO1;
	}

	public void setCashierBillsVO1(CashierBillsVO cashierBillsVO1) {
		this.cashierBillsVO1 = cashierBillsVO1;
	}

	public CashierBillsVO getCashierBillsVO2() {
		return cashierBillsVO2;
	}

	public void setCashierBillsVO2(CashierBillsVO cashierBillsVO2) {
		this.cashierBillsVO2 = cashierBillsVO2;
	}

}