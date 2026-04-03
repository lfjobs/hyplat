package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.driving.DtCarutilitybill;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.opensymphony.xwork2.ActionContext;

/**
 * 出纳记账单管理：CashierBillsAction
 * 
 * @author yjg
 * 
 */
public class CashierBillsAction {
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
	private DtCarutilitybill carutilitybill;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String Showtype;
	private String staffZerenren;
	private String period;
	private String fiveClear;
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
	private List<CCode> billsTypeList;
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
	private String staffIds;
	private String staffIDvalues;
	private String projectDecPath;
	private String projectDecPath1;

	/** **************************************************** */
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
	 * @param
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
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
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
		sql += " and t.companyID = ? ";
		parms.add(account.getCompanyID());
		sql += " and t.organizationID = ? ";
		parms.add(organizationID);
		if(period!=null&&period.equals("01")){
			sql += " and t.cashierDate between ? and ? ";
			Calendar   c   =   Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -30);
			parms.add(c.getTime());
			parms.add(new Date());
		}
		// sql += " and t.staffID = ? ";
		// parms.add(account.getStaffID());
		if (BType != null && !BType.equals("")) {
			sql += " and t.billsType = ?";
			parms.add(BType.trim());
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
				if (null != cashierBillsVO.getBillsType()
						&& !"".equals(cashierBillsVO.getBillsType())) {
					sql += " and t.billsType = ?";
					parms.add(cashierBillsVO.getBillsType().trim());
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					sql += " and t.journalNum like ? ";
					parms
							.add("%" + cashierBillsVO.getJournalNum().trim()
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
					sql += " and t.contactUserName like ? ";
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
		//sql = "select " + sql.substring(sql.indexOf(",") + 1);
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
		//String hqlForMan = "from Staff where staffID = ?";
		//Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				//new Object[] { account.getStaffID() });
		
		//cashierBills.setStaffID(sta.getStaffID());
		//cashierBills.setStaffName(sta.getStaffName());
		//cashierBills.setStaffCode(sta.getStaffCode());
		
		if (cashierBillsVO != null) {
			cashierBills.setBillsType(BType);
			cashierBills.setContactConnections(cashierBillsVO
					.getContactConnections());
			cashierBills.setPhone(cashierBillsVO.getPhone());
		}
		CCode cCode=(CCode)baseBeanService.getBeanByHqlAndParams("from CCode cc where cc.codeValue=?",new Object[]{cashierBills.getBillsType()});
		if(cCode!=null){
			cashierBills.setPbillsTypeID(cCode.getCodePID());
		}
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		if (!company.getCompanyPID().startsWith("pcompany")) {
			company = companyserverService.getCompanyByCompanyID(company.getCompanyPID());
		}
		cashierBills.setPcompanyID(company.getCompanyID());
		cashierBills.setPcompanyName(company.getCompanyName());
		cashierBills.setInputid(account.getStaffID());
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (null == cashierBills.getCashierBillsID()
				|| "".equals(cashierBills.getCashierBillsID())) {
			cashierBills.setJournalNum(serverService.getBillID(account
					.getCompanyID()));
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			parameter = "添加出纳记账（凭证号" + cashierBills.getJournalNum() + "）";
			if (cashierBills.getStatus() != null&&cashierBills.getStatus().equals("04")) {
				baseBeanList.add(saveCheckbill(cashierBills));
			}
		} else {
			parameter = "修改出纳记账（凭证号" + cashierBills.getJournalNum() + "）";

		}
		
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
				if (BType.equals("现金申请明细审批单")) {
					goods.setGoodsStatus("00");
				}
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		
		/*** 根据财务收款营销人员信息保存至教务学员表信息--开始**/
		if(cashierBills.getReferrerID()!=null&&!"".equals(cashierBills.getReferrerID().trim())&&cashierBills.getContactUserID()!=null&&!"".equals(cashierBills.getContactUserID())){
			baseBeanList.add(addDtDrivingAllInformationOfCashierBills(cashierBills));
		}
		/*** 根据财务收款营销人员信息保存至教务学员表信息--结束**/
		
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public BillCheck saveCheckbill(CashierBills cashierBills){
		BillCheck billCheck=new BillCheck();
		billCheck.setCheckid(serverService.getServerID("billcheck"));
		billCheck.setNewBillsID(cashierBills.getCashierBillsID());
		billCheck.setOldBillsID(cashierBills.getCashierBillsID());
		billCheck.setFirstBillsID(cashierBills.getCashierBillsID());
		billCheck.setJournalNum(cashierBills.getJournalNum());
		billCheck.setAuditorcompanyid(cashierBills.getCompanyID());
		billCheck.setAuditorcompanyname(cashierBills.getCompanyName());
		billCheck.setAuditororgID(cashierBills.getDepartmentID());
		billCheck.setAuditororgName(cashierBills.getDataDepotName());
		billCheck.setInputid(cashierBills.getInputid());
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cashierBills.getInputid()});
		billCheck.setInputname(staff.getStaffName());
		billCheck.setStaffID(cashierBills.getStaffID());
		billCheck.setStaffName(cashierBills.getStaffName());
		billCheck.setBillsType(cashierBills.getBillsType());
		billCheck.setAuditorstatus("00");
		billCheck.setAudittime(cashierBills.getCashierDate());
		return billCheck;
	}
	
	/**
	 * 根据财务收款营销人员信息保存至教务学员表信息方法
	 * @param
	 */
	private DtDrivingAllInformation addDtDrivingAllInformationOfCashierBills(CashierBills cb){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DtDrivingAllInformation ddaif=(DtDrivingAllInformation)baseBeanService.getBeanByHqlAndParams(" from DtDrivingAllInformation where  staffID=? and companyID=? and dataTitle=? ", new Object[]{cb.getContactUserID(),account.getCompanyID(),"04"});
		if(ddaif==null){
			ddaif=new DtDrivingAllInformation();
			ddaif.setDrivingAllInformationID(serverService.getServerID("DtDrivingAllInfor"));
			ddaif.setCompanyID(account.getCompanyID());
			ddaif.setAcceptPeopleID(account.getStaffID());
			ddaif.setAcceptPeople(account.getAccountName());
			ddaif.setStaffID(cb.getContactUserID());
			ddaif.setAddTime(new Date());
		}
		ddaif.setOrganizationID(cb.getAssignsID());
		ddaif.setOrganizationName(cb.getAssignsName());
		ddaif.setRegistrationPhone(cb.getReference());
		ddaif.setSignUpDate(cb.getSignUpDate());
		ddaif.setReferrer(cb.getReferrerName());
		ddaif.setReferrerID(cb.getReferrerID());
		ddaif.setReferrerPhone(cb.getReferrerPhone());
		ddaif.setReferrerIdentityCard(cb.getReferrerIdentityCard());
		ddaif.setDataTitle("04");
		return ddaif;
		
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
		if(null != fiveClear && !"".equals(fiveClear)){
		    dc.add(Restrictions.eq("fiveClear", fiveClear));
		}
		if (null != typeID && !"".equals(typeID)) {
			dc.add(Restrictions.eq("typeID", typeID));
		}
		if (null != parameter && !"".equals(parameter)) {
			dc.add(
					Restrictions.or(Restrictions.or(Restrictions.like("goodsCoding", parameter, MatchMode.ANYWHERE),Restrictions.like("goodsName", parameter, MatchMode.ANYWHERE)),
							Restrictions.like("barCode", parameter, MatchMode.ANYWHERE)));

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
	 * JSON取得物品列表
	 */
	public String getGoodsManageForBarcode() {
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
		dc.add(Restrictions.eq("goodsName", parameter));
	    List<BaseBean> list = baseBeanService.getListByDC(dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodlist", list);	
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * JSON取得已拨款的物品列表
	 * @return
	 */
	public String getGoodsManageForBarcodes() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql="select bill.goodsID, goods.goodsCoding, goods.goodsName, goods.defaultStorage, goods.typeID, " +
				" goods.mnemonicCode, goods.variableID, goods.variable1ID, goods.variable2ID, goods.variable3ID, goods.variable4ID, " +
				" goods.acquiesceStandard, goods.model, goods.subjectsName, goods.subjectsID,bill.price,bill.quantity,bo.appropriationmoney,bo.cashapplybillsid " +
				" from dtCashApplyBills bo   left join dtgoodsbills bill on bo.goodsbillsid = bill.goodsbillsid " +
				" left join dtgoodsmanage goods on bill.goodsid = goods.goodsid " +
				" where bill.companyid=? and bo.appropriatestatus=? ";
		 List<Object> parms=new ArrayList<Object>();
		 parms.add(account.getCompanyID());
		 parms.add("01");
		 if (null != parameter && !"".equals(parameter)) {
				sql+=" and goods.goodsCoding like ? ";
				 parms.add("%" + parameter+"%");
			} 
		 pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber),sql,"select count(*) "+ sql.substring(sql.indexOf("from")),parms.toArray());
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

		String hql = "from GoodsManage where defaultStorage = ?";

		if (parameter == null) {
			parameter = "";
		}
		parameter = parameter.trim();
		GoodsManage goodinfo = null;
		try {
		
			goodinfo = (GoodsManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{parameter.trim()});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodinfo", goodinfo);
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
		//status '30' 未审核作废
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
	 * @param
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
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("canStaffName", sta.getStaffName());
		session.put("canStaffId", sta.getStaffID());
		session.put("canStaffCode", sta.getStaffCode());
		// String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		// Object[] params = { companyID, organizationID };
		// String staffhql = "from Staff where staffID in ( select staffID from
		// COS where companyID=? and organizationID=? and cosStatus='50' )";
		// stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
		// params);
		
		//单据类别
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		//费用类别
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		//付款方式
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		//物流方式
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		//单价管理
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		// contactRegardList =
		// codeService.getCCodeListByPID(companyID,"scode20110106hfjes5ucxp0000000017");
		//仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		//单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//个人往来关系
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
			String hql2="from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
		// else {
		// cashierBillsVO = new CashierBillsVO();
		// cashierBillsVO.setJournalNum(serverService.getBillID(companyID));
		// }
	}
	
	//处理报车管报销单信息
	public String toSaveCarCashierBills(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		stafflist = new ArrayList<BaseBean>();
		int  batchnumbet=baseBeanService.getConutByByHqlAndParams("select count(*) from DtCarutilitybill where companyid=? group by batchnumber ",new Object[]{companyID} );
		String[] staffIDs = staffIDvalues.split(",");
		for (int i = 0; i < staffIDs.length; i++) {
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID=? ", new Object[] { staffIDs[i] });
			DtCarutilitybill carutilitybill = new DtCarutilitybill();
			carutilitybill.setCarutilitybillid(serverService
					.getServerID("carutilitybill"));
			carutilitybill.setBillsid(cashierBills.getCashierBillsID());
			carutilitybill.setStaffid(staff.getStaffID());
			carutilitybill.setStaffids(staffZerenren);
			carutilitybill.setProposerdate(new Date());
			carutilitybill.setCompanyid(account.getCompanyID());
			carutilitybill.setBatchnumber("BCG" +  batchnumbet);
			stafflist.add(carutilitybill);
			
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(stafflist, null,
				null);
		//单据类别
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		//费用类别
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		//付款方式
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		//物流方式
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		//单价管理
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		// contactRegardList =
		// codeService.getCCodeListByPID(companyID,"scode20110106hfjes5ucxp0000000017");
		//仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		//单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//个人往来关系
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
			String hql2="from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
		return "edit";
	}
/*	
	@SuppressWarnings("unused")
	private void toSaveCarCashierBill() {
		
		
	}*/
	
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

	public List<CCode> getBillsTypeList() {
		return billsTypeList;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billsTypeList = billsTypeList;
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

	public String getStaffZerenren() {
		return staffZerenren;
	}

	public void setStaffZerenren(String staffZerenren) {
		this.staffZerenren = staffZerenren;
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

	public String getShowtype() {
		return Showtype;
	}

	public void setShowtype(String showtype) {
		Showtype = showtype;
	}



	public DtCarutilitybill getCarutilitybill() {
		return carutilitybill;
	}

	public void setCarutilitybill(DtCarutilitybill carutilitybill) {
		this.carutilitybill = carutilitybill;
	}

	public String getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getStaffIDvalues() {
		return staffIDvalues;
	}

	public void setStaffIDvalues(String staffIDvalues) {
		this.staffIDvalues = staffIDvalues;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	
}