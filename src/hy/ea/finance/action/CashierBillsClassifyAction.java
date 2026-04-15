package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.projectBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
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

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;



@Controller
@Scope("prototype")
public class CashierBillsClassifyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService upLoadFileService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private UpLoadFileService fileService;
	public InputStream excelStream;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private Map<String, GoodsBills> goodsmap;
	/**
	 * 单据类别
	 */
	private String billsType;
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	/**
	 * 付款方式--GoodsBills
	 */
	private List<CCode> payTypeList;
	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;
	/**
	 * 物流方式--GoodsBills
	 */
	private List<CCode> logisticsList;
	// private List<CCode> contactRegardList;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;
	/**
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	
	private String typeID;
	private String search;
	private String sdate;
	private String edate;
	private String departmentID;
	private String differenceStyle;
	private String BType;

	private String projectDecPath;
	private String projectDecPath1;
	private String level;//区分总部 allCompany  公司 company  部门 organization
	
	
	/**
	 * 用于招标预算
	 * ct
	 */
	private String typeType;//用于接传值
	private projectBills bills;//用于保存预算招标项目名称中间表

	/** **************************************************** */
	/**
	 * 预算审核——获取单据类别(供调用)
	 * 
	 * @param status
	 *            单据类别编号
	 * @return
	 */
	private String getBillsTypeMap(String status) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("00", "预算前调查单");
		result.put("01", "预算管理单");
		result.put("02", "确定预算单");
		result.put("03", "预算市场调查单");
		result.put("04", "采购招标管理单");
		result.put("05", "采购比价管理单");
		result.put("06", "采购物品审批管理单");
		result.put("07", "采购前验货单");
		result.put("08", "采购管理单");
		result.put("09", "采购收货验货单");
		result.put("10", "现金申请明细审批单");
		result.put("20", "项目预算招标申请单");
		/*result.put("10", "半成品入库单");
		result.put("11", "半成品出库单");
		result.put("12", "半成品库存单");
		result.put("13", "半成品质检单");
		result.put("14", "生成入库单");
		result.put("15", "生产出库单");
		result.put("16", "生产库存单");
		result.put("17", "生产质检单");
		result.put("18", "成品入库单");
		result.put("19", "成品出库单");
		result.put("20", "成品库存单");
		result.put("21", "成品质检单");
		result.put("22", "预算前调查资金管理单");
		result.put("23", "预算资金单");
		result.put("24", "确定预算单");
		result.put("25", "市场调查资金管理单");
		result.put("26", "采购招标资金管理单");
		result.put("27", "采购比价资金管理单");
		result.put("28", "采购资金审批管理单");
		result.put("29", "采购前审批拨现金对账单");
		result.put("30", "采购资金管理单");
		result.put("31", "收货资金验收单");
		result.put("32", "半成品入库资金管理单");
		result.put("33", "半成品出库资金管理单");
		result.put("34", "半成品库存资金管理单");
		result.put("35", "半成品质检资金管理单");
		result.put("36", "生产入库资金管理单");
		result.put("37", "生产出库资金管理单");
		result.put("38", "生产库存资金管理单");
		result.put("39", "生产质检资金管理单");
		result.put("40", "成品入库资金管理单");
		result.put("41", "成品出库资金管理单");
		result.put("42", "成品库存资金管理单");
		result.put("43", "成品质检管理单");*/
		result.put("43", "成品质检管理单");
		return result.get(status);
	}

	/**
	 * 出纳记账列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierBillsList() {
		search();
		return "list";
	}

	/**
	 * 出纳记账保存
	 */
	public String saveCashierBills() {
		save();
		return "success";
	}

	/**
	 * 去出纳记账添加、修改页面
	 * 
	 */
	public String toSaveCashierBills() {
		toPage();
		return "edit";
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getCashierBillsList();
	}

	/**
	 * 封装查询类表的方法
	 */
	private void search() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50", organizationID });
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		billsType=getBillsTypeMap(BType);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
	}

	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBills 查询条件
	 * @return ：beanlist
	 */

	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		String sql = "from CashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		sql += " where 1=1 ";
		if(level!=null&&"allCompany".equals(level.trim())){
			sql+=" and (t.pcompanyID =? or t.companyID =?)";
			parms.add(account.getCompanyID());
			parms.add(account.getCompanyID());
		}
		if(level!=null&&"company".equals(level.trim())){
			sql += " and t.companyID = ? ";
			parms.add(account.getCompanyID());
		}
		if(level!=null&&"organization".equals(level.trim())){
			sql += " and t.companyID = ? ";
			parms.add(account.getCompanyID());
			sql += " and t.organizationID = ? ";
			parms.add(organizationID);
		}
		if(BType.equals("10")){
			sql += " and (t.billsType = ? or t.billsType = ? or t.billsType = ? or t.billsType = ?)";
			parms.add("现金申请单");
			parms.add("现金申请明细审批单");
			parms.add("现金申请明细单");
			parms.add("现金请购明细审批单");
		}else if(BType.equals("20")){
			sql += " and (t.billsType = ? or t.billsType = ? or t.billsType = ? or t.billsType = ? or t.billsType = ?)";
			parms.add("现金申请单");
			parms.add("现金申请明细审批单");
			parms.add("现金申请明细单");
			parms.add("现金请购明细审批单");
			parms.add("项目预算招标申请单");
		}else{
			sql += " and t.billsType = ?";
			parms.add(getBillsTypeMap(BType));
		}
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if (null != cashierBillsVO.getStaffID().trim()
						&& !"".equals(cashierBillsVO.getStaffID().trim())) {
					sql += " and t.staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
				}
				if (null != cashierBillsVO.getDepartmentID().trim()
						&& !"".equals(cashierBillsVO.getDepartmentID().trim())) {
					sql += " and t.departmentID = ?"; 
					parms.add(cashierBillsVO.getDepartmentID().trim());
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					sql += " and t.journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim()
									+ "%");
				}
				if (null != cashierBillsVO.getStatus().trim()
						&& !"".equals(cashierBillsVO.getStatus().trim())) {
					sql += " and t.status = ?";
					parms.add(cashierBillsVO.getStatus().trim());
				} else {
					sql += " and t.status between ? and ?";
					parms.add("04");
					parms.add("10");
				}
				if (null != cashierBillsVO.getContactConnections().trim()
						&& !"".equals(cashierBillsVO.getContactConnections()
								.trim())) {
					sql += " and t.contactConnections = ? ";
					parms.add(cashierBillsVO.getContactConnections());
				}
				if (null != cashierBillsVO.getPhone().trim()
						&& !"".equals(cashierBillsVO.getPhone().trim())) {
					sql += " and t.phone = ? ";
					parms.add(cashierBillsVO.getPhone().trim());
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					sql += " and t.cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBillsVO.getCcompanyname().trim()
						&& !"".equals(cashierBillsVO.getCcompanyname().trim())) {
					sql += " and t.companyname like ? ";
					parms.add("%" + cashierBillsVO.getCcompanyname().trim()
							+ "%");
				}
				if (null != cashierBillsVO.getContactUserName().trim()
						&& !"".equals(cashierBillsVO.getContactUserName()
								.trim())) {
					sql += " and t.staffname like ? ";
					parms.add("%" + cashierBillsVO.getContactUserName().trim()
							+ "%");
				}
				if (null != cashierBillsVO.getDepStatue()
						&& !"".equals(cashierBillsVO.getDepStatue())) {
					if ("00".equals(cashierBillsVO.getDepStatue())) {
						sql += " and (t.depStatue = ? or t.depStatue is null)";
						parms.add("00");
					} else {
						sql += " and t.depStatue = ? ";
						parms.add(cashierBillsVO.getDepStatue().trim());
					}
				}
			}
		} else {
			sql += " and t.status between ? and ? ";
			parms.add("04");
			parms.add("07");
		}
		sql+=" and t.cashierDate > ? ";
		parms.add(DateUtil.parseDate("yyyy-MM-dd", "2013-01-01"));
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出出纳记账
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		//sql = "select *" + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 封装保存的方法
	 * 
	 * @param :
	 *            cashierTally
	 * @return : toSaveCashierTally() 继续添加
	 * @return : getCashierTallyList() 返回列表
	 */
	private void save() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (null == cashierBills.getCashierBillsID()
				|| "".equals(cashierBills.getCashierBillsID())) {
			cashierBills.setJournalNum(serverService.getBillID(account
					.getCompanyID()));
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			parameter = "添加出纳记账（凭证号" + cashierBills.getJournalNum() + "）";
		} else {
			parameter = "修改出纳记账（凭证号" + cashierBills.getJournalNum() + "）";

		}
		if (cashierBills.getStatus() != null) {

			if (cashierBills.getStatus() == "04"
					|| cashierBills.getStatus().equals("04")) {
				parameter += "并把档案归档！";
			}
		}
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setCompanyName(account.getCompanyName());
		cashierBills.setOrganizationID(organizationID);
		cashierBills.setCashierDate(new Date());
		if (cashierBillsVO != null) {
			cashierBills.setBillsType(getBillsTypeMap(BType));
			cashierBills.setContactConnections(cashierBillsVO
					.getContactConnections());
			cashierBills.setPhone(cashierBillsVO.getPhone());
		}
		cashierBills.setPbillsTypeID("scode2013032523n6id3d3p0000000002");
		
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		if (!company.getCompanyPID().startsWith("pcompany")) {
			company = companyserverService.getCompanyByCompanyID(company.getCompanyPID());
		}
		cashierBills.setPcompanyID(company.getCompanyID());
		cashierBills.setPcompanyName(company.getCompanyName());
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cashierBills);
		if (goodsmap != null) {
			for (GoodsBills goods : goodsmap.values()) {
				if (null == goods.getGoodsBillsID()
						|| "".equals(goods.getGoodsBillsID())) {
					goods.setGoodsBillsID(serverService
							.getServerID("goodsbills"));
					goods.setEndDate(new Date());
				}
				if (goods.getPhoto() != null && !"".equals(goods.getPhoto())) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, goods.getPhotoFileName(), goods
									.getPhoto(), account.getCompanyID(),
									"finance/cashierbills/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					goods.setAttachmentPath(photoPath);
				}
				if (goods.getBillDate() != null
						&& !goods.getBillDate().equals("")) {
					goods.setEndDate(Utilities.getDateFromString(goods
							.getBillDate(), "yyyy-MM-dd hh:mm:ss"));
				}
				if (goods.getSdate() != null && !goods.getSdate().equals("")) {
					goods.setStartDate(Utilities.getDateFromString(goods
							.getSdate(), "yyyy-MM-dd hh:mm:ss"));
				}
				if (goods.getEdate() != null && !goods.getEdate().equals("")) {
					goods.setAccompanyDate(Utilities.getDateFromString(goods
							.getEdate(), "yyyy-MM-dd hh:mm:ss"));
				}
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				baseBeanList.add(goods);
			}
		}
		
		
		/*  用于保存项目预算招标项目字段（中间表）
		  ct*/
		if(BType.equals("20")){
			String hql="from CostSheetBill where companyID=? and groupCompanySn=? and journalNum=?";
			CostSheetBill costbill=(CostSheetBill) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { account.getCompanyID(),ActionContext.getContext().getSession().get("groupCompanySn").toString(), typeType });
			projectBills bills=new projectBills();
			bills.setProjectID(serverService.getServerID("projectBills"));
			bills.setCompanyID(account.getCompanyID());
			bills.setGroupCompanySn(ActionContext.getContext().getSession().get("groupCompanySn").toString());
			bills.setCashierBillsID(cashierBills.getCashierBillsID());
			bills.setCsbID(costbill.getCsbID());
			bills.setProjectName(costbill.getProjectName());
			bills.setStartTime(costbill.getStartTime());
			bills.setEndTime(costbill.getEndTime());
			bills.setCostdescription(costbill.getCostdescription());
			baseBeanList.add(bills);
		}
		
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * JSON取得物品列表
	 */
	public String getGoodsManageByTypeID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
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
		if (null != typeID && !"".equals(typeID)) {
			dc.add(Restrictions.eq("typeID", typeID));
		}
		if (null != parameter && !"".equals(parameter)) {
			dc.add(Restrictions.or(Restrictions.like("goodsCoding", parameter,
					MatchMode.ANYWHERE), Restrictions.like("goodsName",
					parameter, MatchMode.ANYWHERE)));
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * JSON取得出纳记账单凭证号
	 */
	public String getBillID() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String BillID = serverService.getBillID(account.getCompanyID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BillID", BillID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * JSON修改出纳记账单状态
	 */
	public String updateCashierBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String organizationID = (String) session.get("organizationID");
		String hql = "update CashierBills set status = ? where companyID = ?  and cashierBillsID = ? ";
		Object[] params = { "04", account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		String hql1 = "from  CashierBills  where companyID = ?  and cashierBillsID = ? ";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { account.getCompanyID(),
						cashierBills.getCashierBillsID() });
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"修改出纳记账单状态:(凭证号：" + cb.getJournalNum() + ")", account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
				new String[] { hql }, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cb", cb);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * JSON修改出纳记账单状态
	 */
	public String updateCashierBillsInvalid() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String organizationID = (String) session.get("organizationID");
		String hql = "update CashierBills set status = ? where companyID = ?  and cashierBillsID = ? ";
		// status '30' 未审核作废
		Object[] params = { "30", account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		String hql1 = "from  CashierBills  where companyID = ?  and cashierBillsID = ? ";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { account.getCompanyID(),
						cashierBills.getCashierBillsID() });
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"修改出纳记账单状态:(凭证号：" + cb.getJournalNum() + ")", account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
				new String[] { hql }, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cb", cb);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 出纳记账删除
	 * 
	 * @param cashierTally.getCashierID()
	 * @return getCashierTallyList()
	 */
	public String delCashierBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
		String hql1 = " delete from CashierBills where companyID=?   and  cashierBillsID= ? ";
		String hql2 = " delete from GoodsBills where companyID=?   and  cashierBillsID= ? ";
		String hql3 = " delete from UpLoadFile where companyID=?   and  parmeterID = ? ";
		String hql4 = "from CashierBills where companyID= ?   and cashierBillsID = ? ";
		String hql5 = "from UpLoadFile where companyID= ?   and parmeterID = ? ";
		List<BaseBean> beanlist = baseBeanService.getListBeanByHqlAndParams(
				hql5, params);
		ArrayList<String> paths = new ArrayList<String>();
		for (BaseBean bean : beanlist) {
			UpLoadFile load = (UpLoadFile) bean;
			paths.add(path + load.getFilepath());
		}
		upLoadFileService.deletePhotos(paths);
		String[] hqls = { hql3, hql2, hql1 };
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hql4, params);
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除出纳记账(凭证号:" + cash.getJournalNum() + ")", account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, hqls,
				params);

		return "success";
	}

	/**
	 * 物品单删除
	 * 
	 */
	public String delGoodsBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { account.getCompanyID(), typeID };
		String hql = " delete from GoodsBills where companyID=?   and  goodsBillsID=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql }, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeID", typeID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 封装跳转添加或编辑页面的方法
	 * 
	 * @param :companyID,
	 *            organizationID 添加
	 * @param :companyID,
	 *            organizationID ，cashierTally.getCashierID()修改
	 * @return : edit
	 */
	private void toPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		// Object[] params = { companyID, organizationID };
		// String staffhql = "from Staff where staffID in ( select staffID from
		// COS where companyID=? and organizationID=? and cosStatus='50' )";
		// stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
		// params);

		// 单据类别
		billsType=getBillsTypeMap(BType);
		// 费用类别
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		// 付款方式
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		// 物流方式
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		// 单价管理
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		// contactRegardList =
		// codeService.getCCodeListByPID(companyID,"scode20110106hfjes5ucxp0000000017");
		// 仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		// 单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		// 个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");

		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1); 
			String hql2 = " from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills = (CashierBills) baseBeanService
					.getBeanByHqlAndParams(hql2, params1); 
		}
		// else {
		// cashierBillsVO = new CashierBillsVO();
		// cashierBillsVO.setJournalNum(serverService.getBillID(companyID));
		// }
	}
	
	/**
	 * 查询所有已审核通过的项目（用于招标预算生成票据）
	 * ct
	 * @return
	 */
	public List<Object> getBillList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String groupCompanySn=ActionContext.getContext().getSession().get("groupCompanySn").toString();
		List<Object> result = new ArrayList<Object>();
		String sql="select zi.goodsID, zi.goodsNum, zi.subjectsID, zi.subjectsName, zi.reasonThing,zi.goodsName, " +
				" zi.costType,zi.typeID,zi.mnemonicCode,zi.model,zi.standard,zi.unit,zi.quantity,zi.weight," +
				" zi.boxStandard,zi.priceManage,zi.unitPrice,zi.amount,zi.depotType,zi.loan,zi.forLoan,zi.direction," +
				" zi.balance,fu.csbid,zi.batchNumber from dt_inv_csthbd zi left join dt_inv_csthb fu on zi.csbid = fu.csbid " ;
		List<Object> parms = new ArrayList<Object>();
		sql+=" where fu.companyID=?";
		parms.add(account.getCompanyID().trim());	
		sql+=" and fu.groupCompanySn=?";
		parms.add(groupCompanySn);	
		sql+=" and fu.billStatus=?";
		parms.add("02");
		if (null != typeType && !"".equals(typeType)) {
			sql += " and fu.journalnum =?";
			parms.add( typeType.trim());
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	public String getbill(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<Object> list = getBillList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")),parms);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
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

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public List<CCode> getCostTypeList() {
		return costTypeList;
	}

	public void setCostTypeList(List<CCode> costTypeList) {
		this.costTypeList = costTypeList;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getPriceManageList() {
		return priceManageList;
	}

	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getDifferenceStyle() {
		return differenceStyle;
	}

	public void setDifferenceStyle(String differenceStyle) {
		this.differenceStyle = differenceStyle;
	}

	public String getBType() {
		return BType;
	}

	public void setBType(String type) {
		BType = type;
	}

	public String getProjectDecPath() {
		return projectDecPath;
	}

	public void setProjectDecPath(String projectDecPath) {
		this.projectDecPath = projectDecPath;
	}

	public String getProjectDecPath1() {
		return projectDecPath1;
	}

	public void setProjectDecPath1(String projectDecPath1) {
		this.projectDecPath1 = projectDecPath1;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTypeType() {
		return typeType;
	}

	public void setTypeType(String typeType) {
		this.typeType = typeType;
	}

	public projectBills getBills() {
		return bills;
	}

	public void setBills(projectBills bills) {
		this.bills = bills;
	}
}
