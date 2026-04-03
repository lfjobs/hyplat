package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.InstitutionsRegistration;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBillSearch;
import hy.ea.bo.invoicing.ProjectByIndustry;
import hy.ea.bo.invoicing.ProjectMCar;
import hy.ea.bo.invoicing.ProjectManage;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.Document;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.InvoicingService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.tiantai.wfj.bo.OrderProduct;

/**
 * CostSheetAction 预算单据操作类
 * 
 * @author mz
 * 
 */
@Controller
@Scope("prototype")
public class CostSheetAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	private InputStream excelStream;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private InvoicingService invoicingService;
	@Resource
	private CompanyService companyserverService;
	protected HttpServletRequest request;
	private List<BaseBean> list;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private String groupCompanySn; // 集团表示
	private String companyID; // 公司ID
	private String xmtype;// 项目类型
	private String xmtypename;
	private String examineOpra;// 招标审核操作
	private BillCheck billCheck;
	private String comments;// 审核意见
	private CostSheetBillSearch csbSearch;
	private String zorg;
	private String pageSize;
	private String zhuangtai;// 01:人事生产>>新工资模块均传01选择公司部门下所有人限制
	private String zorgname;
	private String billID;
	private Document document;

	/**
	 * 单据类型 '00' 项目预算申请单 '08' 项目招标申请单
	 * 
	 */
	private String type;
	private String summary;// 汇总 gr gs jt

	/**
	 * 责任人
	 */
	private List<BaseBean> staffList;

	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;

	// ///开始

	private CashierBills cashierBills;
	private ProjectByIndustry pbyIndusty;
	private ProjectByIndustry recruiter;
	private ProjectMCar projectMcar;
	private String jumptype; // 查询类型
	private String result; // Ajax 返回值
	private Map<String, GoodsBills> logbookmap;
	private List<BaseBean> goodBillslist;
	private String vuvtype;// 用来区分修改，查看，打印等
	private Date startDate;// 用于查询传值
	private Date endDate;
	private String startTime;
	private String endTime;
	private ProjectManage projectManage;

	private String sub;// 手动防止表单提交
	// 添加设备使用

	private String codeID;
	private String goodsID;

	// 招标打印使用
	private String budgetAccount;// 投标总价(小写)： 48275.8 （支出）
	private String budgetUpper;// 投标总价(大写)：肆万捌仟贰百柒拾伍元点捌（支出）
	private Map<String, String> mapaccount;// 预算投标项目总价key:责任人-单据类别（凭证号）|value
											// 物品总金额
	private Map<String, List<BaseBean>> goodsBillsmaps;// 用于统计各个预算的物品列表
	private String billsType;// 单据类别
	private String zctype;// 支出类型 分为常规支出cg;和非常规类型fcg;//收入类型 预算，调整
	private Staff staff;
	private Date createDate;

	// 完成率
	private Map<String, Map<String, Map<Integer, Map<Long, String>>>> detailmap;
	private List<BaseBean> goodslist;
	private List<Object> goodslistobj;
	private List<Integer> yearlist;
	private String year;// 预算年份 公司
	// 收支余
	private Map<String, List<BaseBean>> mapszy;

	// 产品
	private ProductPackaging productPack;
	private String ppID; // 凭证号
	private Company company;

	private String fgtype;// 页面flexigrid选择，并且给预算单的悬着部门提供判断是否选择全部部门

	/**
	 * 确定提交项目分配
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toQpro() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		list = new ArrayList();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"确定提交项目分配", account);
		list.add(logBook);
		String hql = "update CashierBills c set c.paystatus = ? where c.cashierBillsID = ?";
		Object[] objs = new Object[] { "01", cashierBills.getCashierBillsID() };
		List ls = new ArrayList();
		ls.add(objs);
		baseBeanService.executeHqlsByParamsList(list, new String[] { hql }, ls);
		return "success";
	}

	/**
	 * 确认提交项目跟踪
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toWpro() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		list = new ArrayList();
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "",
				account);
		list.add(logBook);
		String hql = "update CashierBills c set c.paystatus = ? where c.cashierBillsID = ?";
		Object[] objs = new Object[] { "02", cashierBills.getCashierBillsID() };
		List ls = new ArrayList();
		ls.add(objs);
		baseBeanService.executeHqlsByParamsList(list, new String[] { hql }, ls);
		return "success";
	}

	/**
	 * 确认提交项目质量考评
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toEpro() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		list = new ArrayList();
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "",
				account);
		list.add(logBook);
		String hql = "update CashierBills c set c.paystatus = ? where c.cashierBillsID = ?";
		Object[] objs = new Object[] { "03", cashierBills.getCashierBillsID() };
		List ls = new ArrayList();
		ls.add(objs);
		baseBeanService.executeHqlsByParamsList(list, new String[] { hql }, ls);
		return "success";
	}

	/**
	 * 微分金订单查询
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getlisorer() {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");

		// String sql =
		// "select do.key,do.orderID,do.orderNumber,do.userID,do.supplierID,"
		// +
		// "do.orderType,do.orderMoney,do.paymentType ,do.paymentNumber,do.paymentTime,"
		// +
		// "do.receiveAddress,do.createTime,do.updateTime,do.updateUser,do.wlCompany,do.wlOrderNumber,"
		// + "do.sendProductTime,do.sendProductID,do.freight from "
		// +
		// "dtOrder do , dtOrderProduct dop ,dt_ProductPackaging pk where  dop.orderID=do.orderID and pk.companyID=dop.compID and  dop.compID=?";

		String sql = "select do.key,do.orderID,do.orderNumber,do.userID,do.supplierID,do.supplier,"
				+ "do.orderType,do.orderMoney,do.paymentType ,do.paymentNumber,do.paymentTime,"
				+ "do.receiveAddress,do.createTime,do.updateTime,do.updateUser,dop.wlCompany,dop.wlOrderNumber,"
				+ "dop.sendProductTime,do.sendProductID,do.freight from "
				+ "dtOrder do  left join  dtOrderProduct dop on dop.orderID=do.orderID left join dt_ProductPackaging pk on pk.companyID=dop.compID and  pk.companyID=?";

		// if(ppID!=null&& "".equals(ppID)){
		// sql+="and do.orderID=？ ";
		// }
		// if(year!=null&& "".equals(year)){
		// sql+="and do.createTime=？ ";
		// }
		String sqlcount = "select count(*) from "
				+ " dtOrder do  left join  dtOrderProduct dop on dop.orderID=do.orderID left join dt_ProductPackaging pk on pk.companyID=dop.compID and  pk.companyID=?";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql, sqlcount,
				new Object[] { account.getCompanyID() });
		// ,(ppID==null?null:ppID),(year==null?null:year),account.getCompanyID()

		return "getlistorer";
	}

	/**
	 * 条件查询
	 * 
	 * @return
	 */
	public String getlistbyParam() {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");

		String ppID = this.ppID;
		String year = this.year;

		String sql = "select do.key,do.orderID,do.orderNumber,do.userID,do.supplierID,"
				+ "do.orderType,do.orderMoney,do.paymentType ,do.paymentNumber,do.paymentTime,"
				+ "do.receiveAddress,do.createTime,do.updateTime,do.updateUser,do.wlCompany,do.wlOrderNumber,"
				+ "do.sendProductTime,do.sendProductID,do.freight from "
				+ "dtOrder do , dtOrderProduct dop ,dt_ProductPackaging pk where  dop.orderID=do.orderID and pk.companyID=dop.compID and  dop.compID=?";

		// String sql =
		// "select do.key,do.orderID,do.orderNumber,do.userID,do.supplierID,"
		// +
		// "do.orderType,do.orderMoney,do.paymentType ,do.paymentNumber,do.paymentTime,"
		// +
		// "do.receiveAddress,do.createTime,do.updateTime,do.updateUser,do.wlCompany,do.wlOrderNumber,"
		// + "do.sendProductTime,do.sendProductID,do.freight from "
		// +
		// "dtOrder do  left join  dtOrderProduct dop on dop.orderID=do.orderID left join dt_ProductPackaging pk on pk.companyID=dop.compID and  pk.companyID=?";
		//
		if (ppID != null && "".equals(ppID)) {
			sql += "and do.orderID=？ ";
		}
		if (year != null && "".equals(year)) {
			sql += "and do.createTime=？ ";
		}

		String sqlcount = "select count(*) from "
				+ " dtOrder do  left join  dtOrderProduct dop on dop.orderID=do.orderID left join dt_ProductPackaging pk on pk.companyID=dop.compID and  pk.companyID=?";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql, sqlcount,
				new Object[] { account.getCompanyID(),
						(ppID == null ? null : ppID),
						(year == null ? null : year), account.getCompanyID() });

		return "getlistorer";
	}

	public String datedd() {
		String orderID = ppID;

		String sql1 = "delete dtOrder d where d.key=? ";
		String sql2 = "delete dtOrderProduct d where d.orderID=?";
		String hql4 = "from OrderProduct  where orderID=?";
		String sql3 = "dalete dt_ProductPackaging d where d.ppID=?";
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null,
				new String[] { sql1 }, new Object[] { orderID });
		list = baseBeanService.getListBeanByHqlAndParams(hql4,
				new String[] { orderID });
		for (BaseBean op1 : list) {
			OrderProduct op = (OrderProduct) op1;
			if (op != null) {
				baseBeanService.saveBeansListAndexecuteSqlsByParams(null,
						new String[] { sql3 },
						new Object[] { op.getProductID() });
				baseBeanService
						.saveBeansListAndexecuteSqlsByParams(null,
								new String[] { sql2 },
								new Object[] { op.getOrderID() });
			}
		}
		return "success";

	}

	// /////////////////////////////////////项目预算列表功能开始//////////////////////////////////////////////////////
	/**
	 * 预算订单——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("cashierBills", cashierBills);
		return getCostSheetList();
	}

	/**
	 * 
	 * 
	 * 项目预算列表|未审核项目预算列表|已审核项目预算列表 jumptype：fxlb|zbqsh|zbqysh
	 * 
	 * @return
	 */
	public String getCostSheetList() {

		if (zhuangtai != null && zhuangtai.equals("01")) {
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), finddcBy());
		} else {
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), getDc());
		}
		return jumptype;

	}

	// 人事生产工资模块预算单据查询
	private DetachedCriteria finddcBy() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String companyID = session.get("companyID").toString();
		DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);
		dc.add(Restrictions.eq("billsType", "项目支出预算单"));

		if (null != fgtype) {
			dc.add(Restrictions.between("paystatus", "00", "03"));
		}
		// 项目预算列表
		if ("fxlb".equals(jumptype)) {
			dc.add(Restrictions.eq("companyID", companyID));
		}

		if (search != null && search.equals("search")) {
			cashierBills = (CashierBills) session.get("cashierBills");
			if (cashierBills.getProjectName() != null
					&& !cashierBills.getProjectName().equals("")) {
				dc.add(Restrictions.like("projectName", cashierBills
						.getProjectName().trim(), MatchMode.ANYWHERE));

			}
			if (cashierBills.getXmtype() != null
					&& !cashierBills.getXmtype().equals("")) {
				dc.add(Restrictions.like("xmtype", cashierBills.getXmtype(),
						MatchMode.ANYWHERE));
			}

			if (cashierBills.getJournalNum() != null
					&& !cashierBills.getJournalNum().equals("")) {
				dc.add(Restrictions.like("journalNum",
						cashierBills.getJournalNum(), MatchMode.ANYWHERE));
			}

			if (cashierBills.getStaffName() != null
					&& !cashierBills.getStaffName().equals("")) {
				dc.add(Restrictions.or(
						Restrictions.like("staffCode",
								cashierBills.getStaffCode(), MatchMode.ANYWHERE),
						Restrictions.like("staffName",
								cashierBills.getStaffName(), MatchMode.ANYWHERE)));
			}

			if (cashierBills.getInputName() != null
					&& !cashierBills.getInputName().equals("")) {
				dc.add(Restrictions.like("inputName",
						cashierBills.getInputName(), MatchMode.ANYWHERE));
			}

			if (startTime != null && !startTime.equals("")) {
				startDate = Utilities.getDateFromString(startTime,
						"yyyy-MM-dd HH:mm:ss");
				if (endTime == null || endTime.equals("")) {
					endDate = new Date();
				} else {
					endDate = Utilities.getDateFromString(endTime,
							"yyyy-MM-dd HH:mm:ss");
				}
				dc.add(Restrictions.between("cashierDate", startDate, endDate));
			}

			if (cashierBills.getBillsType() != null
					&& !cashierBills.getBillsType().equals("")) {
				dc.add(Restrictions.eq("billsType", cashierBills.getBillsType()));
			}

			if (cashierBills.getStatus() != null
					&& !cashierBills.getStatus().equals("")) {
				dc.add(Restrictions.eq("status", cashierBills.getStatus()));
			}

		}
		dc.addOrder(Order.asc("paystatus"));
		return dc;
	}

	/**
	 * 
	 * 项目预算列表离线查询
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DetachedCriteria getDc() {

		System.out.println(billsType);

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);
		/*
		 * dc.add(Restrictions.or(Restrictions.eq("billsType", "项目收入预算单"),
		 * Restrictions.eq("billsType", "项目支出预算单")));
		 */
		// 招标前未审核
		if ("zbqsh".equals(jumptype)) {
			dc.add(Restrictions.eq("status", "01"));
			dc.add(Restrictions.eq("examineID", account.getStaffID()));
			dc.add(Restrictions.eq("examineorgID", organizationID));
			dc.add(Restrictions.eq("examineComID", account.getCompanyID()));
		}
		// 招标前已审核
		if ("zbqysh".equals(jumptype)) {

			try {
				String sqlcheck = "select newBillsID from dtbillcheck where auditorid = ? and auditororgID = ? and auditorcompanyid = ? and (auditorstatus = ? or auditorstatus = ?)";

				List<String> billlist = baseBeanService
						.getListBeanBySqlAndParams(sqlcheck,
								new Object[] { account.getStaffID(),
										organizationID, account.getCompanyID(),
										"02", "03" });
				dc.add(Restrictions.in("cashierBillsID", billlist));
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		// 项目预算列表
		if ("fxlb".equals(jumptype)) {
			dc.add(Restrictions.or(Restrictions.or(Restrictions.or(
					Restrictions.eq("status", "00"),
					Restrictions.eq("status", "01")), Restrictions.or(
					Restrictions.eq("status", "02"),
					Restrictions.eq("status", "03"))),Restrictions.or(
					Restrictions.eq("status", "04"),
					Restrictions.eq("status", "40"))));

			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("organizationID", organizationID));
			dc.add(Restrictions
					.eq("billsType", billsType));
			dc.addOrder(Order.desc("cashierDate"));
			if (zctype != null && !zctype.equals("")) {
				if (zctype.equals("cg") || zctype.equals("fcg")) {
					dc.add(Restrictions.eq("zctype", zctype));
				}

				if (zctype.equals("t")) {
					dc.add(Restrictions.not(Restrictions.eq("status", "00")));
				}
			}
		}

		// 比价列表
		if ("bjlb".equals(jumptype)) {
			dc.add(Restrictions.eq("status", "03"));

			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("organizationID", organizationID));
		}

		// 部门预算列表
		if ("fxlbhumen".equals(jumptype)) {
			dc.add(Restrictions.or(Restrictions.or(Restrictions.or(
					Restrictions.eq("status", "00"),
					Restrictions.eq("status", "01")), Restrictions.or(
					Restrictions.eq("status", "02"),
					Restrictions.eq("status", "03"))),Restrictions.or(
					Restrictions.eq("status", "04"),
					Restrictions.eq("status", "40"))));
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions
					.like("billsType", billsType, MatchMode.ANYWHERE));
			if (zctype != null && !zctype.equals("")) {
				if (zctype.equals("cg") || zctype.equals("fcg")) {
					dc.add(Restrictions.eq("zctype", zctype));
				}

				if (zctype.equals("t")) {
					dc.add(Restrictions.not(Restrictions.eq("status", "00")));
				}
			}
		}

		if (search != null && search.equals("search")) {
			cashierBills = (CashierBills) session.get("cashierBills");
			if (cashierBills.getProjectName() != null
					&& !cashierBills.getProjectName().equals("")) {
				dc.add(Restrictions.like("projectName", cashierBills
						.getProjectName().trim(), MatchMode.ANYWHERE));

			}
			if (cashierBills.getXmtype() != null
					&& !cashierBills.getXmtype().equals("")) {
				dc.add(Restrictions.like("xmtype", cashierBills.getXmtype(),
						MatchMode.ANYWHERE));
			}

			if (cashierBills.getJournalNum() != null
					&& !cashierBills.getJournalNum().equals("")) {
				dc.add(Restrictions.like("journalNum",
						cashierBills.getJournalNum(), MatchMode.ANYWHERE));
			}

			if (cashierBills.getStaffName() != null
					&& !cashierBills.getStaffName().equals("")) {
				dc.add(Restrictions.or(
						Restrictions.like("staffCode",
								cashierBills.getStaffCode(), MatchMode.ANYWHERE),
						Restrictions.like("staffName",
								cashierBills.getStaffName(), MatchMode.ANYWHERE)));
			}

			if (cashierBills.getInputName() != null
					&& !cashierBills.getInputName().equals("")) {
				dc.add(Restrictions.like("inputName",
						cashierBills.getInputName(), MatchMode.ANYWHERE));
			}

			if (startTime != null && !startTime.equals("")) {
				startDate = Utilities.getDateFromString(startTime,
						"yyyy-MM-dd HH:mm:ss");
				if (endTime == null || endTime.equals("")) {
					endDate = new Date();
				} else {
					endDate = Utilities.getDateFromString(endTime,
							"yyyy-MM-dd HH:mm:ss");
				}
				dc.add(Restrictions.between("cashierDate", startDate, endDate));
			}

			if (cashierBills.getBillsType() != null
					&& !cashierBills.getBillsType().equals("")) {
				dc.add(Restrictions.eq("billsType", cashierBills.getBillsType()));
			}

			if (cashierBills.getStatus() != null
					&& !cashierBills.getStatus().equals("")) {
				dc.add(Restrictions.eq("status", cashierBills.getStatus()));
			}

		}
		return dc;
	}

	/**
	 * 
	 * 招标前提交审核：做的事情：查找是否存在BillCheck,如果有， 修改生成一个BillCheck,有审核人信息，提交时间
	 * 
	 * @return
	 */
	public String zbqSubmitExamine() {
		String hqlc = "from CashierBills where cashierBillsID = ?";
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hqlc,
						new Object[] { cashierBills.getCashierBillsID() });

		cash.setStatus("01");// 招标前审核中
		cash.setExamineID(cashierBills.getExamineID());
		cash.setExamineName(cashierBills.getExamineName());
		cash.setExamineComID(cashierBills.getExamineComID());
		cash.setExamineComName(cashierBills.getExamineComName());
		cash.setExamineorgID(cashierBills.getExamineorgID());
		cash.setExamineorgName(cashierBills.getExamineorgName());

		List<BaseBean> beans = new ArrayList<BaseBean>();

		String hqlcheck = "from BillCheck where newBillsID = ? and audittime is null";
		BillCheck billCheck = (BillCheck) baseBeanService
				.getBeanByHqlAndParams(hqlcheck,
						new Object[] { cash.getCashierBillsID() });

		// 如果billcheck不等于空说明驳回过，驳回后会增加一个billcheck 修改这个billcheck的审核信息
		if (billCheck != null) {

			billCheck.setAuditorstatus("01");
			billCheck.setBilltatus("未审核-招标前");
			// 审核人信息用input录入人员信息传值
			billCheck.setAuditorcompanyid(cashierBills.getExamineComID());
			billCheck.setAuditorcompanyname(cashierBills.getExamineComName());
			billCheck.setAuditororgID(cashierBills.getExamineorgID());
			billCheck.setAuditororgName(cashierBills.getExamineorgName());
			billCheck.setAuditorid(cashierBills.getExamineID());
			billCheck.setAuditorname(cashierBills.getExamineName());
		} else {
			// 第一次生成一个billcheck
			billCheck = saveCheckbill(cash, null);

		}

		// 提交审核后设置审核状态
		billCheck.setAuditorstatus("01");
		billCheck.setBilltatus("未审核-招标前");
		beans.add(billCheck);
		beans.add(cash);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		if ("update".equals(examineOpra)) {
			vuvtype = "edit";
			return toEditCostSheet();
		}

		return "success";
	}

	/**
	 * @param cashierBills
	 * @return
	 */
	public BillCheck saveCheckbill(CashierBills cash, BillCheck bc) {

		BillCheck billCheck = new BillCheck();
		billCheck.setCheckid(serverService.getServerID("billcheck"));
		if (bc != null) {
			billCheck.setOldBillsID(bc.getNewBillsID());
		} else {
			billCheck.setOldBillsID(cash.getCashierBillsID());
		}
		billCheck.setNewBillsID(cash.getCashierBillsID());
		billCheck.setFirstBillsID(cash.getCashierBillsID());

		billCheck.setJournalNum(cash.getJournalNum());
		billCheck.setAuditorcompanyid(cash.getExamineComID());
		billCheck.setAuditorcompanyname(cash.getExamineComName());
		billCheck.setAuditororgID(cash.getExamineorgID());
		billCheck.setAuditororgName(cash.getExamineorgName());
		billCheck.setAuditorid(cash.getExamineID());
		billCheck.setAuditorname(cash.getExamineName());

		billCheck.setInputid(cash.getInputid());
		billCheck.setInputname(cash.getInputName());

		billCheck.setStaffID(cash.getStaffID());
		billCheck.setStaffName(cash.getStaffName());
		billCheck.setBillsType(cash.getBillsType());
		billCheck.setCashierDate(cash.getCashierDate());
		billCheck.setProjectName(cash.getProjectName());
		billCheck.setAuditorstatus("01");

		return billCheck;
	}

	/**
	 * 
	 * 
	 * 确定预算
	 * 
	 * @return
	 */
	public String confCostSheet() {

		String hql = "from CashierBills where cashierBillsID = ?";
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hql,
						new Object[] { cashierBills.getCashierBillsID() });
		if ("项目收入预算单".equals(cash.getBillsType())) {
			cash.setStatus("40");// 确定招标
		} else {
			cash.setStatus("03");// 确定招标
		}

		baseBeanService.update(cash);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 删除项目预算
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String deteleCostSheet() {
		String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
		String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
		List parmList = new ArrayList();
		parmList.add(new Object[] { cashierBills.getCashierBillsID() });
		parmList.add(new Object[] { cashierBills.getCashierBillsID() });
		baseBeanService.executeHqlsByParamsList(null,
				new String[] { hql, hql1 }, parmList);

		if ("update".equals(examineOpra)) {

			return toSaveCostSheet();
		}

		return "success";
	}

	/**
	 * 
	 * 
	 * 导出Excel
	 * 
	 * @return
	 */
	public String exportExcel() {

		List<BaseBean> list = baseBeanService.getListByDC(getDc());
		excelStream = excelService.showExcel(cashierBills.columnHeadings(),
				list);

		return "showexcel";
	}

	/**
	 * 
	 * 
	 * 打印预算
	 * 
	 * @return
	 */
	public String printCostSheet() {

		toEditCostSheet();

		return "printcsb";

	}

	// /////////////////////////////////////项目预算列表功能结束//////////////////////////////////////////////////////

	// /////////////////////////////////////招标前未审核和已审核模块开始//////////////////////////////////////////////////////

	/**
	 * 
	 * 招标前审核中进行的操作包括：审核通过并结束审核01 |审核通过并转交他人审核02| 驳回03； examineOpra:01|02|03
	 * 
	 * @return
	 */
	public String zbqExamining() {

		String fhql = "from CashierBills where cashierBillsID = ?";
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(fhql,
						new Object[] { cashierBills.getCashierBillsID() });
		try {
			// 查询之前的BillCheck 理论上一定会有
			String hqlcheck = "from BillCheck where newBillsID = ? and audittime is null";
			BillCheck bc = (BillCheck) baseBeanService
					.getBeanByHqlAndParams(hqlcheck,
							new Object[] { cashierBills.getCashierBillsID() });

			List<BaseBean> beans = new ArrayList<BaseBean>();
			if (bc != null) {
				bc.setAudittime(new Date());
				bc.setComments(comments);
			}
			// 审核通过并结束
			if (examineOpra.equals("01")) {
				bc.setAuditorstatus("02");// 已审核
				bc.setBilltatus("审核通过-招标前");
				cash.setStatus("02");// 单子状态改变为招标前审核通过
				// 生成新的Billcheck用来记录招标驳回，或者是招标通过

				BillCheck newBillCheck = saveCheckbill(cash, bc);
				newBillCheck.setBilltatus("未审核-招标");
				beans.add(newBillCheck);

			}

			// 审核通过转他人审批
			if (examineOpra.equals("02")) {

				bc.setAuditorstatus("02");// 已审核
				bc.setBilltatus("审核通过-招标前");

				// 修改单子当前的审核人
				cash.setExamineID(cashierBills.getExamineID());
				cash.setExamineName(cashierBills.getExamineName());
				cash.setExamineComID(cashierBills.getExamineComID());
				cash.setExamineComName(cashierBills.getExamineComName());
				cash.setExamineorgID(cashierBills.getExamineorgID());
				cash.setExamineorgName(cashierBills.getExamineorgName());

				// 生成新的Billcheck用来记录下一个审批人的审批情况
				BillCheck newBillCheck = saveCheckbill(cash, bc);
				beans.add(newBillCheck);

			}

			// 驳回: 1）需要复制预算单子一份修改状态为驳回（10），
			// 2)改变原来的Billcheck审核状态为驳回（03）并添加驳回时间，
			// 3)复制原来billcheck生成新的BillCheck记录新复制的预算单子，billcheck审核状态为驳回待修改11，审核时间设为null；
			if (examineOpra.equals("03")) {

				beans = rejectCheckbill(cash, beans, "zbq", bc);
				bc.setAuditorstatus("03");// 已驳回
				bc.setBilltatus("驳回-招标前");

			}
			beans.add(cash);
			beans.add(bc);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";

	}

	/**
	 * 
	 * 
	 * 招标比价中的驳回
	 * 
	 * @return
	 */
	public String viaCostSheet() {
		String fhql = "from CashierBills where cashierBillsID = ?";
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(fhql,
						new Object[] { cashierBills.getCashierBillsID() });
		// 查询之前的BillCheck 理论上一定会有
		String hqlcheck = "from BillCheck where newBillsID = ? and audittime is null";
		BillCheck bc = (BillCheck) baseBeanService.getBeanByHqlAndParams(
				hqlcheck, new Object[] { cashierBills.getCashierBillsID() });

		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (bc != null) {
			bc.setAudittime(new Date());
			bc.setComments(comments);
			bc.setAuditorstatus("03");// 已驳回
			bc.setBilltatus("驳回-招标");
			beans.add(bc);
		}
		beans = rejectCheckbill(cash, beans, "zbh", bc);

		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 招标前驳回|招标比价驳回
	 * 
	 * @param bills
	 * @param beans
	 * @param rejecttype
	 *            :zbq|zbh
	 * @param ybills
	 * @return
	 */
	public List<BaseBean> rejectCheckbill(CashierBills cash,
			List<BaseBean> beans, String rejecttype, BillCheck ybills) {
		if (ybills == null) {
			ybills = saveCheckbill(cash, null);
		}

		try {
			// 将原来单子状态改为10驳回状态
			cash.setStatus("10");
			beans.add(cash);

			// 克隆一份被驳回的单子
			CashierBills newCash = (CashierBills) cash.cloneCashierBills();
			newCash.setCashierBillsKey(null);
			newCash.setCashierBillsID(serverService.getServerID("cashierTally"));
			newCash.setStatus("11");// 驳回待修改状态
			beans.add(newCash);

			// 克隆单子里的物品
			String hqlgoods = "from GoodsBills where cashierBillsID = ?";
			List<BaseBean> goodBilllist = baseBeanService
					.getListBeanByHqlAndParams(hqlgoods,
							new Object[] { cash.getCashierBillsID() });

			for (int k = 0; k < goodBilllist.size(); k++) {
				GoodsBills goodBill = (GoodsBills) goodBilllist.get(k);

				GoodsBills newGoodbill = (GoodsBills) goodBill
						.cloneGoodsBills();

				newGoodbill.setGoodsBillsKey(null);
				newGoodbill.setGoodsBillsID(serverService
						.getServerID("goodsbills"));
				newGoodbill.setCashierBillsID(newCash.getCashierBillsID());
				beans.add(newGoodbill);

			}

			// 新生成个审核记录BillCheck复制原来的
			BillCheck newBillCheck = (BillCheck) ybills.cloneBillCheck();

			newBillCheck.setCheckid(serverService.getServerID("billcheck"));
			newBillCheck.setAudittime(null);
			newBillCheck.setCheckkey(null);
			newBillCheck.setAuditorstatus("01");// 未审核
			newBillCheck.setComments("");
			if (rejecttype.equals("zbq")) {
				newBillCheck.setBilltatus("驳回待修改-招标前");
			}

			if (rejecttype.equals("zbh")) {
				newBillCheck.setBilltatus("驳回待修改-招标");
			}

			newBillCheck.setNewBillsID(newCash.getCashierBillsID());
			newBillCheck.setOldBillsID(ybills.getNewBillsID());
			beans.add(newBillCheck);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return beans;
	}

	/**
	 * 
	 * 
	 * 审核记录
	 * 
	 * @return
	 */
	public String AjxaGetCheckbill() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from BillCheck c where c.newBillsID=?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cashierBills.getCashierBillsID() });
		if (list.size() != 0) {
			String hql2 = "from BillCheck t where t.firstBillsID= ? order by t.audittime";
			List<BaseBean> checkList = baseBeanService
					.getListBeanByHqlAndParams(hql2,
							new Object[] { ((BillCheck) list.get(0))
									.getFirstBillsID() });
			map.put("checkList", checkList);
		} else {
			map.put("checkList", null);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map, jsonConfig);
		result = obj.toString();
		return "success";
	}

	// /////////////////////////////////////招标前未审核和已审核模块结束//////////////////////////////////////////////////////

	// /////////////////////////////////////项目预算添加和修改页面功能//////////////////////////////////////////////////////

	/**
	 * 
	 * 根据项目分类查询项目列表pageForm
	 * 
	 * 
	 */
	public String getProjectList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			String yj = request.getParameter("yj");
			DetachedCriteria dc = DetachedCriteria
					.forClass(ProductPackaging.class);
			if (yj != null && yj.equals("01")) {
				dc.add(Restrictions.eq("yjstatus", "00"));
			}
			if (xmtype != null && !xmtype.equals("")) {
				dc.add(Restrictions.or(Restrictions.eq("xmtype", xmtype),
						Restrictions.eq("xmtype", xmtype)));

			}
			if (parameter == null) {
				parameter = "";
			}
			dc.add(Restrictions
					.like("goodsName", parameter, MatchMode.ANYWHERE));
			/*dc.add(Restrictions.eq("organizationID",
					(String) session.get("organizationID")));*/
			dc.add(Restrictions.eq("companyID",
					(String) session.get("companyID")));
			dc.add(Restrictions.isNull("parentId"));
			// dc.addOrder(Order.asc("projectCode"));

			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 20 : pageNumber), dc);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject obj = JSONObject.fromObject(map, jsonConfig);
			this.result = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 
	 * 
	 * 获取当前部门下的（顶级部门）的银行账号
	 * 
	 * @return
	 */
	public String getBankAccountList() {

		Map<String, Object> session = ActionContext.getContext().getSession();

		DetachedCriteria dc = DetachedCriteria
				.forClass(InstitutionsRegistration.class);
		// bankAccount银行账号 bankName银行名称 conPerson账户责任人用几个查询
		dc.add(Restrictions.or(Restrictions.like("bankAccount", parameter,
				MatchMode.ANYWHERE), Restrictions.or(
				Restrictions.like("bankName", parameter, MatchMode.ANYWHERE),
				Restrictions.like("conPerson", parameter, MatchMode.ANYWHERE))));
		dc.add(Restrictions.eq("organizationID",
				(String) session.get("organizationID")));
		dc.add(Restrictions.eq("companyID", (String) session.get("companyID")));

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject obj = JSONObject.fromObject(map, jsonConfig);
		this.result = obj.toString();

		return "success";

	}

	/** 判断公司下项目是否为人事项目 */
	private boolean compareBy(String comId, String code) {
		boolean flag = false;
		String sql = "select count(t.codeid) from dtccode t where t.companyid =? and t.codesn like ? and t.codesn=?";
		List<Object> parm = new ArrayList<Object>();
		parm.add(comId);
		parm.add("01%");// 人事项目codesn
		parm.add(code);
		int ret = baseBeanService.getConutByBySqlAndParams(sql, parm.toArray());
		if (ret > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 预算订单——保存
	 */
	public String saveCostSheet() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (sub != null && !sub.equals("")) {
			if (sub != null && sub.equals(session.get("sbcf"))) {
				session.remove("sbcf");
			} else {
				vuvtype = "edit";
				cashierBills.setCashierBillsID((String) session
						.get("cashierBillsID"));
				return toEditCostSheet();
			}
		}

		String organizationID = (String) session.get("organizationID");

		if (cashierBills.getCashierBillsID() == null
				|| cashierBills.getCashierBillsID().equals("")) {
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";
			cashierBills.setStatus("00");// 未审核
			cashierBills.setZctype(zctype);
			if (compareBy(account.getCompanyID(), cashierBills.getXmtype())) {
				cashierBills.setPaystatus("00");// 工资状态
			}
		} else {
			parameter = "修改预算单据（凭证号：" + cashierBills.getJournalNum() + "）";
			cashierBills.setCashierDate(new Date());
			if (!"t".equals(zctype)) {
				// 删除原GoodsBills
				String hql = "delete from GoodsBills  where cashierBillsID = ?";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
						new String[] { hql },
						new Object[] { cashierBills.getCashierBillsID() });
			}

		}
		cashierBills.setGroupCompanySn((String) session.get("groupCompanySn"));
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setCompanyName(account.getCompanyName());
		cashierBills.setCashierDate(new Date());

		cashierBills.setOrganizationID(organizationID);
		// 制单人信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		cashierBills.setInputName(sta.getStaffName());
		cashierBills.setInputid(account.getStaffID());
		cashierBills.setInputOrganizationID(organizationID);
		String hqlForOrg = "from COrganization where organizationID = ?";
		COrganization org = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hqlForOrg,
						new Object[] { organizationID });
		cashierBills.setInputOrganizationName(org.getOrganizationName());

		cashierBills.setInputCompanyid(account.getCompanyID());
		cashierBills.setInputCompanyName(account.getCompany().getCompanyName());

		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cashierBills);

		List<Object[]> paramlist = new ArrayList<Object[]>();
		int i = 0;
		if (logbookmap != null) {
			String[] hqls = new String[logbookmap.size()];
			for (GoodsBills goodsBills : logbookmap.values()) {
				if ("t".equals(zctype)) {
					String hql = "update GoodsBills set tiaoQuantity = ?,tiaoPrice= ?,tiaoMoney = ? where goodsBillsID = ?";
					hqls[i] = hql;
					Object[] param = new Object[] {
							goodsBills.getTiaoQuantity(),
							goodsBills.getTiaoPrice(),
							goodsBills.getTiaoMoney(),
							goodsBills.getGoodsBillsID() };
					paramlist.add(param);
					i++;
				} else {
					goodsBills.setCashierBillsID(cashierBills
							.getCashierBillsID());
					goodsBills.setGoodsBillsID(serverService
							.getServerID("goodsbills"));
					goodsBills.setCompanyID(account.getCompanyID());
					goodsBills.setEndDate(new Date());// 入账时间
					goodsBills.setTiaoQuantity(goodsBills.getQuantity());// 初始时将调整信息设置为预算信息
					goodsBills.setTiaoPrice(goodsBills.getPrice());
					goodsBills.setTiaoMoney(goodsBills.getMoney());
					baseBeanList.add(goodsBills);
				}
			}

			if ("t".equals(zctype)) {
				baseBeanService.executeHqlsByParamsList(null, hqls, paramlist);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		session.put("cashierBillsID", cashierBills.getCashierBillsID());
		vuvtype = "edit";

		return toEditCostSheet();
	}

	/**
	 * 预算订单——添加
	 * 
	 */
	public String toSaveCostSheet() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		// 单价管理
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");

		billID = serverService.getBillID(account.getCompanyID());
		// 责任人name
		String hqlForMan = "from Staff where staffID = ?";
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("sbcf", Math.random() + "");
		return "add";
	}

	/**
	 * 单据修改
	 * 
	 * @return
	 */
	public String toEditCostSheet() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
        HttpServletRequest re=ServletActionContext.getRequest();
		// 项目预算单
		String hqlys = "from CashierBills where cashierBillsID = ?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hqlys, new Object[] { cashierBills.getCashierBillsID() });
		zctype = cashierBills.getZctype();
		billsType = cashierBills.getBillsType();
		if (cashierBills != null) {
			// 项目预算单中的物品
			String hqlg = "from GoodsBills where cashierBillsID = ?";
			goodBillslist = baseBeanService.getListBeanByHqlAndParams(hqlg,
					new Object[] { cashierBills.getCashierBillsID() });

			// 单价管理
			priceManageList = codeService
					.getCCodeListByPID(account.getCompanyID(),
							"scode20101101dfs3uhdprp0000000032");
			// 处理附表
			String hqldustry = "from ProjectByIndustry where projectID = ?";
			String hqlcar = "from ProjectMCar where projectID = ?";
			// 处理车辆
			if (cashierBills.getXmtype() != null
					&& cashierBills.getXmtype().startsWith("0240511")) {

				projectMcar = (ProjectMCar) baseBeanService
						.getBeanByHqlAndParams(hqldustry,
								new Object[] { cashierBills.getProID() });

			}

			// 处理行业
			if ("0521".equals(cashierBills.getXmtype())) {

				pbyIndusty = (ProjectByIndustry) baseBeanService
						.getBeanByHqlAndParams(hqldustry,
								new Object[] { cashierBills.getProID() });

			}

			// 处理招生员

			if ("0542".equals(cashierBills.getXmtype())) {

				recruiter = (ProjectByIndustry) baseBeanService
						.getBeanByHqlAndParams(hqlcar,
								new Object[] { cashierBills.getProID() });

			}

			if ("printcsb".equals(vuvtype)) {
                goodBillslist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?",new Object[]{cashierBills.getCashierBillsID()});
                if (goodBillslist!=null){
                    for (int i = 0; i <goodBillslist.size() ; i++) {
                        GoodsBills goodsBills=(GoodsBills)goodBillslist.get(i);
                        List<BaseBean> bCheckList = baseBeanService
                                .getListBeanByHqlAndParams(
                                        "from BillCheck where newBillsID=? order by audittime",
                                        new Object[] { goodsBills.getGoodsBillsID()});

                        if (bCheckList.size() > 0) {
                            Map<String, String> bcheckMap = new HashMap<String, String>();
                            for (int j = 0; j < bCheckList.size(); j++) {
                                BillCheck billCheck = (BillCheck) bCheckList.get(j);
                                if (billCheck.getDeptpost() != null) {
                                    bcheckMap.put(billCheck.getDeptpost(),
                                            billCheck.getAuditorname());
                                }
                            }
                            re.setAttribute("bcheckMap",bcheckMap);
                        }
                    }
                }


			}

		}
		return vuvtype;
	}

	// ///////////////////////////////////项目预算添加页面功能结束//////////////////////////////////////////////////////

	// /////////////////////////////////////项目明细功能开始
	// /////////////////////////////////////////////////

	/**
	 * 
	 * 
	 * 获取项目明细列表
	 * 
	 * @return
	 */
	public String getProjectDetailList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");

			String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
			staffList = baseBeanService.getListBeanByHqlAndParams(staffhql,
					new Object[] { account.getCompanyID(), "20",
							(String) session.get("organizationID") });

			List<Object> params = new ArrayList<Object>();
			params.add((String) session.get("organizationID"));
			params.add(account.getCompanyID());
			String sql = "select t.companyName,t.departmentName,t.staffName,"
					+ "t.projectCode,t.projectName,t.xmtypename,t.journalNum,d.goodsNum,d.goodsName,"
					+ "d.standard,d.goodsVariableID,d.quantity,d.price,d.money,t.billsType,"
					+ "d.ccompanyName,d.connectName,t.cashierBillsID,d.goodsBillsID "
					+ "from dtCashierBills t right join dtGoodsBills d on t.cashierBillsID = d.cashierBillsID where t.organizationID = ? and t.companyID = ?";

			if (type.equals("clmx")) {
				sql += " and t.carID is not null";
			}
			if (type.equals("srmx")) {
				sql += " and t.billsType = ?";
				params.add("项目收入预算单");
			}
			if (type.equals("zcmx")) {
				sql += " and t.billsType = ?";
				params.add("项目支出预算单");
			}
			if (type.equals("xmmx")) {
				sql += " and (t.billsType = ? or t.billsType = ?)";
				params.add("项目收入预算单");
				params.add("项目支出预算单");
			}

			if (search != null && search.equals("search")) {
				pageForm = csbSearch(sql, params);
			} else {

				sql += " order by t.cashierDate desc";
				pageForm = baseBeanService.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 15 : pageNumber),
						sql,
						"select count(1) "
								+ (sql.substring(sql.indexOf("from"))),
						params.toArray());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pdetailist";
	}

	public String toSearchByDetail() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("csbSearch", csbSearch);
		return getProjectDetailList();
	}

	/**
	 * 
	 * 项目明细查询
	 * 
	 * @return
	 */
	public PageForm csbSearch(String sql, List<Object> params) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		csbSearch = (CostSheetBillSearch) session.get("csbSearch");

		if (csbSearch != null) {
			if (csbSearch.getZprojectname() != null
					&& !csbSearch.getZprojectname().equals("")) {
				sql += " and t.xmtype like ?";
				params.add("%" + csbSearch.getZprojectname().trim() + "%");

			}
			if (csbSearch.getSprojectname() != null
					&& !csbSearch.getSprojectname().equals("")) {
				sql += " and t.projectname like ?";
				params.add("%" + csbSearch.getSprojectname().trim() + "%");

			}

			if (csbSearch.getGoodsnum() != null
					&& !csbSearch.getGoodsnum().equals("")) {
				sql += " and d.goodsNum like ?";
				params.add("%" + csbSearch.getGoodsnum().trim() + "%");

			}

			if (csbSearch.getCompanyname() != null
					&& !csbSearch.getCompanyname().equals("")) {
				sql += " and t.companyname like ?";
				params.add("%" + csbSearch.getCompanyname().trim() + "%");

			}

			if (csbSearch.getOrgname() != null
					&& !csbSearch.getOrgname().equals("")) {
				sql += " and t.departmentName like ?";
				params.add("%" + csbSearch.getOrgname().trim() + "%");

			}

			if (csbSearch.getStaffname() != null
					&& !csbSearch.getStaffname().equals("")) {
				sql += " and t.staffName like ?";
				params.add("%" + csbSearch.getStaffname().trim() + "%");

			}

			if (csbSearch.getCcompanyname() != null
					&& !csbSearch.getCcompanyname().equals("")) {
				sql += " and d.ccompanyName like ?";
				params.add("%" + csbSearch.getCcompanyname().trim() + "%");

			}

			if (csbSearch.getCstaffname() != null
					&& !csbSearch.getCstaffname().equals("")) {
				sql += " and d.connectName like ?";
				params.add("%" + csbSearch.getCstaffname().trim() + "%");

			}

			if (csbSearch.getStart() != null && csbSearch.getStart() != null
					&& !csbSearch.getStart().equals("")) {
				sql += " and t.cashierDate between ? and ? ";
				params.add(Utilities.getDateFromString(csbSearch.getStart(),
						"yyyy-MM-dd"));

				Date enddate = null;
				if (csbSearch.getEnd() == null || csbSearch.getEnd().equals("")) {
					enddate = new Date();
				} else {
					enddate = Utilities.getDateFromString(csbSearch.getEnd(),
							"yyyy-MM-dd");
				}
				params.add(enddate);
			}
		}

		sql += " order by t.cashierDate desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), sql, "select count(1) "
						+ (sql.substring(sql.indexOf("from"))),
				params.toArray());

		return pageForm;
	}

	/**
	 * 项目明细导出
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		List<Object> params = new ArrayList<Object>();
		params.add((String) session.get("organizationID"));
		params.add(account.getCompanyID());
		String sql = "select t.companyName,t.departmentName,t.staffName,"
				+ "t.projectCode,t.projectName,t.xmtypename,t.journalNum,d.goodsNum,d.goodsName,"
				+ "d.standard,d.goodsVariableID,d.quantity,d.price,d.money,t.billsType,case when t.billsType='项目收入预算单' then d.money else '' end,case when t.billsType='项目支出预算单' then d.money else '' end,d.money,"
				+ "d.ccompanyName,d.connectName "
				+ "from dtCashierBills t left join dtGoodsBills d on t.cashierBillsID = d.cashierBillsID where t.organizationID = ? and t.companyID = ?";

		if (type.equals("clmx")) {
			sql += " and t.carID is not null";
		}
		if (type.equals("srmx")) {
			sql += " and t.billsType = ?";
			params.add("项目收入预算单");
		}
		if (type.equals("zcmx")) {
			sql += " and t.billsType = ?";
			params.add("项目支出预算单");
		}
		if (type.equals("xmmx")) {
			sql += " and (t.billsType = ? or t.billsType = ?)";
			params.add("项目收入预算单");
			params.add("项目支出预算单");
		}

		if (search != null && search.equals("search")) {
			csbSearch = (CostSheetBillSearch) session.get("csbSearch");
			if (csbSearch != null && !csbSearch.getZprojectname().equals("")) {
				sql += " and p.projectname like ?";
				params.add("%" + csbSearch.getZprojectname().trim() + "%");

			}
			if (csbSearch != null && !csbSearch.getSprojectname().equals("")) {
				sql += " and t.projectname like ?";
				params.add("%" + csbSearch.getSprojectname().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getGoodsnum().equals("")) {
				sql += " and d.goodsNum like ?";
				params.add("%" + csbSearch.getGoodsnum().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getCompanyname().equals("")) {
				sql += " and t.companyname like ?";
				params.add("%" + csbSearch.getCompanyname().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getOrgname().equals("")) {
				sql += " and t.departmentName like ?";
				params.add("%" + csbSearch.getOrgname().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getStaffname().equals("")) {
				sql += " and t.staffName like ?";
				params.add("%" + csbSearch.getStaffname().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getCcompanyname().equals("")) {
				sql += " and d.ccompanyName like ?";
				params.add("%" + csbSearch.getCcompanyname().trim() + "%");

			}

			if (csbSearch != null && !csbSearch.getCstaffname().equals("")) {
				sql += " and d.connectName like ?";
				params.add("%" + csbSearch.getCstaffname().trim() + "%");

			}

			if (csbSearch != null && csbSearch.getStart() != null
					&& !csbSearch.getStart().equals("")) {
				sql += " and t.billsDate between ? and ? ";
				params.add(Utilities.getDateFromString(csbSearch.getStart(),
						"yyyy-MM-dd"));

				Date enddate = null;
				if (csbSearch.getEnd() == null || csbSearch.getEnd().equals("")) {
					enddate = new Date();
				} else {
					enddate = Utilities.getDateFromString(csbSearch.getEnd(),
							"yyyy-MM-dd");
				}
				params.add(enddate);
			}


		}
		sql += " order by t.cashierDate desc";
		String[] titles = { "序号", "公司", "部门", "责任人", "子项目编号", "子项目名称", "主项目名称",
				"单据凭证号", "物品编号", "物品名称", "型号/规格", "单位", "数量", "单价", "金额",
				"单据类型", "预算收入", "预算支出", "预算余额", "往来单位", "往来个人" };
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,
				params.toArray());
		excelStream = excelService.showExcel(titles, list);

		return "showexcel";
	}

	// /////////////////////////////////////项目明细功能结束
	// /////////////////////////////////////////////////

	// ////////////////////////////////////招标打印开始
	// /////////////////////////////////////////////////

	// 项目招标打印
	public String projectPrint() {

		return "projectprint";
	}

	/**
	 * 
	 * 获取封面内容
	 * 
	 * @return
	 */
	public String getCoverContent() {

		try {

			String hqlp = "from ProductPackaging where ppID = ?";

			productPack = (ProductPackaging) baseBeanService
					.getBeanByHqlAndParams(hqlp, new Object[] { ppID });

			company = (Company) baseBeanService.getBeanByHqlAndParams(
					"from Company where companyID = ?",
					new Object[] { productPack.getCompanyID() });
			staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID = ?",
					new Object[] { productPack.getStaffID() });
			// 获取当前项目所有的项目预算（包括收入和支出）
			String hql = "from CashierBills where proID = ? and (billsType = ? or billsType = ?)";
			list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
					ppID, "项目收入预算单", "项目支出预算单" });

			String hqls = "from CashierBills where proID = ? and billsType = ?";
			// 获取当前项目所有收入预算
			List<BaseBean> cashlists = baseBeanService
					.getListBeanByHqlAndParams(hqls, new Object[] { ppID,
							"项目收入预算单" });
			// 获取当前项目所有支出预算
			List<BaseBean> cashlistz = baseBeanService
					.getListBeanByHqlAndParams(hqls, new Object[] { ppID,
							"项目支出预算单" });
			String hqlgood = "from GoodsBills where cashierBillsID = ?";
			List<BaseBean> goodlist = null;

			// 计算预算投标总价（支出）
			if ("printtprice".equals(type)) {
				float budgetAccountf = 0;
				for (BaseBean b : cashlistz) {
					CashierBills cash = (CashierBills) b;
					goodlist = baseBeanService.getListBeanByHqlAndParams(
							hqlgood, new Object[] { cash.getCashierBillsID() });
					for (BaseBean g : goodlist) {
						GoodsBills good = (GoodsBills) g;
						budgetAccountf += Float.parseFloat(good.getMoney());
					}
				}
				// 预算投标总价小写(支出)
				budgetAccount = budgetAccountf + "";
				// 预算总价转成大写(支出)
				budgetUpper = invoicingService.cnUpperCaser(budgetAccount);
			}
			mapaccount = new HashMap<String, String>();
			// 预算投标项目总价
			if ("printpprice".equals(type)) {

				for (BaseBean b : list) {
					float num = 0;
					CashierBills cash = (CashierBills) b;
					goodlist = baseBeanService.getListBeanByHqlAndParams(
							hqlgood, new Object[] { cash.getCashierBillsID() });
					for (BaseBean g : goodlist) {
						GoodsBills good = (GoodsBills) g;
						num += Float.parseFloat(good.getMoney());

					}

					mapaccount.put(
							cash.getStaffName() + "——" + cash.getBillsType()
									+ "(" + cash.getJournalNum() + ")", num
									+ "");
				}

			}
			// 各预算总价
			if ("printsprice".equals(type) || "printdprice".equals(type)) {
				// 获取每一个预算的物品列表放在一个Map<String,BaseBean>
				goodsBillsmaps = new HashMap<String, List<BaseBean>>();
				for (BaseBean b : list) {
					CashierBills cash = (CashierBills) b;
					goodlist = baseBeanService.getListBeanByHqlAndParams(
							hqlgood, new Object[] { cash.getCashierBillsID() });
					goodsBillsmaps.put(cash.getCashierBillsID(), goodlist);
					float i = 0;
					for (BaseBean g : goodlist) {
						GoodsBills good = (GoodsBills) g;
						i += Float.parseFloat(good.getMoney());
					}
					mapaccount.put(cash.getCashierBillsID(), i + "");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	/**
	 * 
	 * 
	 * 获取超级父项目凭证号
	 * 
	 * @return
	 */
	public String getBillIDs() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String BillID = serverService.getBillID(account.getCompanyID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("billID", BillID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// ////////////////////////////////////招标打印结束
	// /////////////////////////////////////////////////

	// ///////////////////////////////////////////新增mz///////////////////////////////////////////////////////////

	/**
	 * 
	 * 键入实时查询物品
	 */
	public String getGoodsByQuery() {
		try {
			CAccount account = (CAccount) ActionContext.getContext()
					.getSession().get("account");
			DetachedCriteria dc = DetachedCriteria.forClass(GoodsManage.class);
			// 判断是否有子公司
			Company com = companyserverService.getCompanyByCompanyID(account
					.getCompanyID());
			ArrayList<String> cids = new ArrayList<String>();
			if (com.getCompanyPID().startsWith("pcompany")) {
				cids = companyserverService.getCompanyAndItsChildrenIDs(account
						.getCompanyID());
			} else {
				cids = companyserverService.getCompanyAndItsChildrenIDs(com
						.getCompanyPID());
			}
			dc.add(Restrictions.eq("goodsState", "00"));
			dc.add(Restrictions.in("companyID", cids));
			if (null != parameter && !"".equals(parameter)) {
				dc.add(Restrictions.or(Restrictions.like("goodsCoding",
						parameter, MatchMode.ANYWHERE), Restrictions.like(
						"goodsName", parameter, MatchMode.ANYWHERE)));
			}
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 30 : pageNumber), dc);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 
	 * 获取往来单位
	 * 
	 * @return
	 */
	public String getListContactCompany() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, Object> map = new HashMap<String, Object>();

		DetachedCriteria dc = DetachedCriteria
				.forClass(ContactCompanyView.class);
		try {
			dc.add(Restrictions.like("companyName",
					URLDecoder.decode(parameter, "UTF-8"), MatchMode.ANYWHERE));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));

		dc.addOrder(Order.desc("companyName"));

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 30 : pageNumber), dc);

		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据往来个人名称模糊查询列表
	 * 
	 * @return
	 */
	public String getListContactUser() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ContactUser.class);
		try {
			dc.add(Restrictions.like("staffName",
					URLDecoder.decode(parameter, "UTF-8"), MatchMode.ANYWHERE));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));

		dc.addOrder(Order.desc("staffName"));

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 30 : pageNumber), dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 获取该集团下所有公司
	 * 
	 * @return
	 */
	public String seachgroupsync() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String groupCompanySn = (String) session.get("groupCompanySn");
		String hql = "from Company where groupCompanySn = ?";
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> company = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { groupCompanySn });
		if (companyID != null) {
			String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
			List<BaseBean> departmentList = baseBeanService
					.getListBeanByHqlAndParams(hql1, new Object[] { companyID,
							companyID });
			map.put("departmentList", departmentList);
		}
		map.put("company", company);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 预算调整完成率报表
	 * 
	 * @return
	 */
	public String budgetComplete() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date starty = null;
		Date starte = null;
		if (year == null || year.equals("")) {
			year = cal.get(Calendar.YEAR) + "";// 获取当前年份

		}
		starty = Utilities.getDateFromString(year + "-01-01", "yyyy-MM-dd");
		starte = Utilities.getDateFromString(year + "-12-31", "yyyy-MM-dd");

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hqlgoods = "from GoodsBills g where exists(select c.cashierBillsID from CashierBills  c where g.cashierBillsID = c.cashierBillsID and  billsType = ? and organizationID = ?) and g.targetStart between  ? and ?  and targetSalerID is not null order by g.targetSalerID desc";
		List<BaseBean> glist = baseBeanService.getListBeanByHqlAndParams(
				hqlgoods, new Object[] { "项目收入预算单", organizationID, starty,
						starte });

		goodslist = new ArrayList<BaseBean>();
		String hqlg = "from GoodsBills g1 where g1.companyID = ? and exists(select c.cashierBillsID from CashierBills  c where g1.cashierBillsID = c.cashierBillsID and  c.billsType = ? and c.organizationID = ? and c.targetSalerID = g1.targetSalerID) and g1.targetSalerID is not null and g1.targetStart between  ? and ? and g1.planGoodsBillsID is not null";

		// 查询实际销售金额
		List<BaseBean> factlist = null;
		factlist = baseBeanService.getListBeanByHqlAndParams(hqlg,
				new Object[] { account.getCompanyID(), "收款单", organizationID,
						starty, starte });

		for (BaseBean b : glist) {
			GoodsBills good1 = (GoodsBills) b;

			if (goodslist.size() == 0) {
				goodslist.add(good1);
				continue;
			}
			boolean bool = true;

			for (BaseBean c : goodslist) {
				GoodsBills good2 = (GoodsBills) c;
				if (good2.getTargetDeptID() != null
						&& good2.getTargetDeptID().equals(
								good1.getTargetDeptID())) {
					if (good2.getTargetSalerID() != null
							&& good2.getTargetSalerID().equals(
									good1.getTargetSalerID())) {
						if (good2.getGoodsID() != null
								&& good2.getGoodsID()
										.equals(good1.getGoodsID())) {
							bool = false;
							break;

						}
					}
				}
			}
			if (bool) {
				goodslist.add(good1);
			}

		}

		detailmap = new HashMap<String, Map<String, Map<Integer, Map<Long, String>>>>();// ID，年，月，1,2,4
		for (BaseBean g : goodslist) {
			GoodsBills good = (GoodsBills) g;

			Map<String, Map<Integer, Map<Long, String>>> map1 = new HashMap<String, Map<Integer, Map<Long, String>>>();
			Map<Integer, Map<Long, String>> map2 = new HashMap<Integer, Map<Long, String>>();
			for (BaseBean c : glist) {

				Map<Long, String> map3 = new HashMap<Long, String>();
				GoodsBills good2 = (GoodsBills) c;
				if (good2.getTargetDeptID() != null
						&& good2.getTargetDeptID().equals(
								good.getTargetDeptID())) {
					if (good2.getTargetSalerID() != null
							&& good2.getTargetSalerID().equals(
									good.getTargetSalerID())) {
						if (good2.getGoodsID() != null
								&& good2.getGoodsID().equals(good.getGoodsID())) {
							if (good2.getMoney() != null
									&& !good2.getMoney().equals("")) {
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(good2.getTargetStart());
								int month = cal2.get(Calendar.MONTH) + 1;// 获取月份
								String years = cal2.get(Calendar.YEAR) + "";// 获取年份
								// System.out.println(years);

								String numys = "";
								String numts = "";
								String numfs = "";

								// System.out.println(map1.get(years)+"开始");
								if (map1.get(years) != null) {
									if (map1.get(years).get(month) != null) {
										numys = map1.get(years).get(month)
												.get(1l);

									}

									if (map1.get(years).get(month) != null) {
										numts = map1.get(years).get(month)
												.get(2l);

									}

									if (map1.get(years).get(month) != null) {
										numfs = map1.get(years).get(month)
												.get(3l);

									}

								}

								if (numys != null && !numys.equals("")) {
									map3.put((long) 1,
											String.valueOf(Float
													.parseFloat(numys)
													+ Float.parseFloat(good2
															.getMoney())));
								} else {
									map3.put((long) 1, good2.getMoney());
								}

								if (numts != null && !numts.equals("")) {
									map3.put((long) 2, String.valueOf(Float
											.parseFloat(numts)
											+ Float.parseFloat(good2
													.getTiaoMoney())));
								} else {
									map3.put((long) 2, good2.getTiaoMoney());
								}

								// 查询实际销售金额
								// factlist =
								// baseBeanService.getListBeanByHqlAndParams(hqlg,new
								// Object[]{good2.getGoodsBillsID(),account.getCompanyID(),"收款单",organizationID,good2.getTargetSalerID(),good2.getTargetSalerID()});
								//
								float factf = 0;
								for (BaseBean f : factlist) {
									GoodsBills fact = (GoodsBills) f;
									if (fact.getPlanGoodsBillsID().equals(
											good2.getGoodsBillsID())
											&& fact.getTargetSalerID().equals(
													good2.getTargetSalerID())) {
										if (fact.getRealMoney() != null) {
											factf += Float.parseFloat(fact
													.getRealMoney());
										}
									}

								}

								if (numfs != null && !numfs.equals("")) {
									map3.put(
											(long) 3,
											String.valueOf(Float
													.parseFloat(numfs) + factf));
								} else {
									map3.put((long) 3, factf + "");
								}

								map2.put(new Integer(month), map3);

								map1.put(years, map2);
								// System.out.println(map1.get(years)+"111111111");

							}

						}
					}
				}

			}
			if (good != null) {
				detailmap.put(good.getGoodsBillsID(), map1);

			}

		}

		return "budget_complete";
	}

	/**
	 * 
	 * 
	 * 库存，添加到设备
	 * 
	 * @return
	 */
	public String addDevice() {

		try {
			// 在项目树里生成 如果是车辆的话再后勤车辆管理中添加车
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");

			Map<String, Object> map = new HashMap<String, Object>();

			String hql = "from CCode where goodsID = ? and companyID= ?";
			List<BaseBean> listc = baseBeanService.getListBeanByHqlAndParams(
					hql, new Object[] { goodsID, account.getCompanyID() });

			if (listc.size() != 0) {
				map.put("result", "fail");
			} else {

				hql = "from CCode where codeID = ? and companyID = ?";

				CCode ccode = (CCode) baseBeanService.getBeanByHqlAndParams(
						hql, new Object[] { codeID, account.getCompanyID() });

				hql = "from CCode where codePID = ? and companyID = ? and codeStatus != ? order by codeNumber desc";
				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql, new Object[] { codeID,
								account.getCompanyID(), "98" });
				CCode mixcode = null;
				if (list.size() != 0) {
					mixcode = (CCode) list.get(0);
				}

				// 查询物品信息，将物品生成到ccode表中
				hql = "from GoodsManage where goodsID = ?";
				GoodsManage good = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goodsID });
				CCode newcode = new CCode();
				newcode.setCodeID(serverService.getServerID("ccode"));
				newcode.setCodeValue(good.getGoodsName());
				newcode.setCodeDesc(good.getGoodsName());
				newcode.setCompanyID(account.getCompanyID());
				newcode.setCodePID(codeID);
				newcode.setCodeStatus("01");
				newcode.setGroupSn(ccode.getGroupSn());
				newcode.setGoodsID(good.getGoodsID());
				String codeSnp = ccode.getCodeSn();
				// 计算编号
				if (mixcode == null) {
					newcode.setCodeNumber(1);
					newcode.setCodeSn(codeSnp + "0001");
				} else {
					String halfcodeSn = mixcode.getCodeSn().replace(
							ccode.getCodeSn(), "");

					String halfncodeSn = String.valueOf((int) Integer
							.parseInt(halfcodeSn) + 1);
					int c = halfcodeSn.length() - halfncodeSn.length();
					String ling = "";
					for (int i = 0; i < c; i++) {
						ling += "0";
					}
					newcode.setCodeSn(ccode.getCodeSn() + ling + halfncodeSn);

					newcode.setCodeNumber(mixcode.getCodeNumber() + 1);
				}

				baseBeanService.save(newcode);

				// 保存车辆信息
				// 根据物品ID查询车辆
				if (good.getTypeID() != null && good.getTypeID().equals("车辆")) {

					String hql2 = "from CarInformation where goodsID=?";

					CarInformation carinfo = (CarInformation) baseBeanService
							.getBeanByHqlAndParams(hql2,
									new Object[] { good.getGoodsID() });
					carinfo.setStaffID(account.getStaffID());
					carinfo.setStaffName(account.getStaffName());
					carinfo.setState1("00");
					carinfo.setState2("01");
					carinfo.setState3("00");

					carinfo.setOrganizationID(organizationID);
					carinfo.setCompanyID(account.getCompanyID());

					baseBeanService.update(carinfo);

				}

				map.put("result", "success");
			}

			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 
	 * 
	 * 获取项目收支余或盈亏
	 * 
	 * @return
	 */
	public String getProjectSzy() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {
			return "no_login";
		}

		String hql = "from ProductPackaging where organizationID = ? and companyID = ?";

		list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
				organizationID, account.getCompanyID() });
		String hqld = "from CashierBills where proID = ? and (billsType = ? or billsType = ?)";
		String hqlg = "from GoodsBills where cashierBillsID = ?";
		mapszy = new HashMap<String, List<BaseBean>>();

		for (BaseBean b : list) {
			ProductPackaging project = (ProductPackaging) b;
			List<BaseBean> listdz = baseBeanService.getListBeanByHqlAndParams(
					hqld, new Object[] { project.getPpID(), "项目收入预算单",
							"项目支出预算单" });

			for (BaseBean d : listdz) {
				CashierBills cashier = (CashierBills) d;
				List<BaseBean> goodlist = baseBeanService
						.getListBeanByHqlAndParams(hqlg,
								new Object[] { cashier.getCashierBillsID() });
				float money = 0;

				for (BaseBean g : goodlist) {
					GoodsBills good = (GoodsBills) g;
					money += Float.parseFloat(good.getMoney() == null ? "0"
							: good.getMoney());
				}
				cashier.setAccountNum(money + "");// 该字段仅仅用于传值；
			}

			mapszy.put(project.getPpID(), listdz);
		}

		return "pszy";

	}

	/**
	 * 
	 * 
	 * 获取当前的单据的状态
	 * 
	 * @return
	 */
	public String getCurrentStatus() {

		String hql = "from CashierBills where cashierBillsID = ?";
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hql,
						new Object[] { cashierBills.getCashierBillsID() });

		Map<String, Object> map = new HashMap<String, Object>();
		if (cash != null) {
			map.put("result", cash.getStatus());
		} else {
			map.put("result", "noexist");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	/**
	 * 
	 * 根据产品parentId获取子产品，也就是子项目
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getProjectByParent() {
		String sql = "select ppID,parentId,goodsName  from dt_ProductPackaging t connect by nocycle "
				+ "prior t.ppID = t.parentId start with t.ppID = ? order by sorting";
		try {
			List<BaseBean> productlist = baseBeanService
					.getListBeanBySqlAndParams(sql,
							new Object[] { productPack.getPpID() });
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productlist", productlist);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 
	 * 获取父部门信息
	 * 
	 * @return
	 */
	public String getParentOrg() {
		String hqlorg = "from COrganization t where t.organizationID = ?";
		COrganization org1 = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hqlorg,
						new Object[] { cashierBills.getDepartmentID() });
		Company company = null;
		COrganization org = null;
		String parentid = "";
		String parentname = "";
		if (org1.getOrganizationPID().startsWith("company")) {
			String hqlcom = "from Company where companyID = ?";
			company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom,
					new Object[] { org1.getOrganizationPID() });
			parentid = company.getCompanyID();
			parentname = company.getCompanyName();
		} else {
			org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlorg,
					new Object[] { org1.getOrganizationPID() });
			parentid = org.getOrganizationID();
			parentname = org.getOrganizationName();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", parentid);
		map.put("parentname", parentname);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 
	 * 根据往来个人ID获取StaffID
	 * 
	 * @return
	 */
	public String getStaffIdByConenetID() {

		String hql = "from ContactUser where relationID = ?";
		ContactUser user = (ContactUser) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { staff.getStaffID() });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffID", user.getStaffID());

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public Map<String, GoodsBills> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, GoodsBills> logbookmap) {
		this.logbookmap = logbookmap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getJumptype() {
		return jumptype;
	}

	public void setJumptype(String jumptype) {
		this.jumptype = jumptype;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<CCode> getPriceManageList() {
		return priceManageList;
	}

	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}

	public Map<String, String> getMapaccount() {
		return mapaccount;
	}

	public void setMapaccount(Map<String, String> mapaccount) {
		this.mapaccount = mapaccount;
	}

	public Map<String, List<BaseBean>> getGoodsBillsmaps() {
		return goodsBillsmaps;
	}

	public void setGoodsBillsmaps(Map<String, List<BaseBean>> goodsBillsmaps) {
		this.goodsBillsmaps = goodsBillsmaps;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public String getExamineOpra() {
		return examineOpra;
	}

	public void setExamineOpra(String examineOpra) {
		this.examineOpra = examineOpra;
	}

	public BillCheck getBillCheck() {
		return billCheck;
	}

	public void setBillCheck(BillCheck billCheck) {
		this.billCheck = billCheck;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getGroupCompanySn() {
		return groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getXmtypename() {
		return xmtypename;
	}

	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}

	public CostSheetBillSearch getCsbSearch() {
		return csbSearch;
	}

	public void setCsbSearch(CostSheetBillSearch csbSearch) {
		this.csbSearch = csbSearch;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getZorg() {
		return zorg;
	}

	public void setZorg(String zorg) {
		this.zorg = zorg;
	}

	public String getZorgname() {
		return zorgname;
	}

	public void setZorgname(String zorgname) {
		this.zorgname = zorgname;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public ProjectByIndustry getPbyIndusty() {
		return pbyIndusty;
	}

	public void setPbyIndusty(ProjectByIndustry pbyIndusty) {
		this.pbyIndusty = pbyIndusty;
	}

	public ProjectByIndustry getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(ProjectByIndustry recruiter) {
		this.recruiter = recruiter;
	}

	public ProjectMCar getProjectMcar() {
		return projectMcar;
	}

	public void setProjectMcar(ProjectMCar projectMcar) {
		this.projectMcar = projectMcar;
	}

	public List<BaseBean> getGoodBillslist() {
		return goodBillslist;
	}

	public void setGoodBillslist(List<BaseBean> goodBillslist) {
		this.goodBillslist = goodBillslist;
	}

	public String getVuvtype() {
		return vuvtype;
	}

	public void setVuvtype(String vuvtype) {
		this.vuvtype = vuvtype;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ProjectManage getProjectManage() {
		return projectManage;
	}

	public void setProjectManage(ProjectManage projectManage) {
		this.projectManage = projectManage;
	}

	public String getBudgetAccount() {
		return budgetAccount;
	}

	public void setBudgetAccount(String budgetAccount) {
		this.budgetAccount = budgetAccount;
	}

	public String getBudgetUpper() {
		return budgetUpper;
	}

	public void setBudgetUpper(String budgetUpper) {
		this.budgetUpper = budgetUpper;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getZctype() {
		return zctype;
	}

	public void setZctype(String zctype) {
		this.zctype = zctype;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Map<String, Map<String, Map<Integer, Map<Long, String>>>> getDetailmap() {
		return detailmap;
	}

	public void setDetailmap(
			Map<String, Map<String, Map<Integer, Map<Long, String>>>> detailmap) {
		this.detailmap = detailmap;
	}

	public List<BaseBean> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<BaseBean> goodslist) {
		this.goodslist = goodslist;
	}

	public List<Object> getGoodslistobj() {
		return goodslistobj;
	}

	public void setGoodslistobj(List<Object> goodslistobj) {
		this.goodslistobj = goodslistobj;
	}

	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public List<Integer> getYearlist() {
		return yearlist;
	}

	public void setYearlist(List<Integer> yearlist) {
		this.yearlist = yearlist;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public Map<String, List<BaseBean>> getMapszy() {
		return mapszy;
	}

	public void setMapszy(Map<String, List<BaseBean>> mapszy) {
		this.mapszy = mapszy;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public ProductPackaging getProductPack() {
		return productPack;
	}

	public void setProductPack(ProductPackaging productPack) {
		this.productPack = productPack;
	}

	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getFgtype() {
		return fgtype;
	}

	public void setFgtype(String fgtype) {
		this.fgtype = fgtype;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
