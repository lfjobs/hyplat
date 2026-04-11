package hy.ea.finance.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
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
import hy.ea.util.VOtool;
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
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 单据报税管理：DutiableAction
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class DutiableAction {
	private static final Logger logger = LoggerFactory.getLogger(DutiableAction.class);
	@Resource
	private BaseBeanService baseBeanService;
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
	private ServerService serverService;

	public InputStream excelStream;
	private CashierBillsVO cashierBillsVO;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> stafflist;
	private List<CCode> codeRelationList;
	private List<CCode> connectionlist;
	private CashierBills cashierBills;
	private String search;
	private String BType;
	private Map<String, GoodsBills> goodsmap;
	/**
	 * 存储页面部门Id
	 */
	private String deptID;
	/**
	 * 页面查询制单日期
	 */
	private String sdate;
	private String edate;
	/**
	 * 页面查询报税日期
	 */
	private String tsdate;
	private String tedate;
	/**
	 * 储存复选框选中ID 用在添加已有单据里
	 */
	private List<String> checkradio;

	/**
	 * 单据类别
	 */
	private List<CCode> billTypes;
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
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;
	/**
	 * 客户咨询单查询打印
	 */
	private List<BaseBean> cashierBillsVoList;
	List<BaseBean> goodsList;
	private String staffNameID; // 打印查询传值
	/**
	 * 物品类别
	 */
	private List<CCode> codeList;
	
	
	// JSON取得部门列表
	public String getoList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		billTypes=codeService.getCCodeListByPID(account.getCompanyID(),
		"scode20101101dfs3uhdprp0000000029");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("billTypes", billTypes);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		billTypes=null;
		return "success";
	}
	
	
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getDutiableList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getDutiableList();
	}

	/**
	 * 单据报税管理列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getDutiableList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50" });
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "taxlist";
	}

	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBills 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		// VOtool.getCashierBillVO()是用来生成sql语句的
		String sql = VOtool.getCashierBillVO(2);
		// 用来保存参数
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		sql += " where t.companyid = ? ";
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");

			if (null != cashierBillsVO.getStaffID()
					&& !"".equals(cashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(cashierBillsVO.getStaffID());
			}
			if (null != cashierBillsVO.getDepartmentID()
					&& !"".equals(cashierBillsVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							cashierBillsVO.getDepartmentID())) {
				sql += " and t.departmentID = ?";
				parms.add(cashierBillsVO.getDepartmentID());
			}
			if (null != cashierBillsVO.getBillsType()
					&& !"".equals(cashierBillsVO.getBillsType())) {
				sql += " and t.billsType = ?";
				parms.add(cashierBillsVO.getBillsType());
			}
			if (null != cashierBillsVO.getJournalNum()
					&& !"".equals(cashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ? ";
				parms.add("%" + cashierBillsVO.getJournalNum() + "%");
			}
			if (null != cashierBillsVO.getTaxstatus()
					&& !"".equals(cashierBillsVO.getTaxstatus())) {
				sql += " and t.taxstatus = ? ";
				parms.add(cashierBillsVO.getTaxstatus());
			} else {
				sql += " and( t.taxstatus between ? and ? or t.taxstatus=? or t.taxstatus=?) ";
				parms.add("01");
				parms.add("04");
				parms.add("10");
				parms.add("00");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ? ";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (tsdate != null && tedate != null && !("").equals(tsdate)
					&& !("").equals(tedate)) {
				sql += " and t.taxDate between ? and ? ";
				parms.add(Utilities.getDateFromString(tsdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(tedate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != cashierBillsVO.getContactConnections()
					&& !"".equals(cashierBillsVO.getContactConnections())) {
				sql += " and t.contactConnections = ? ";
				parms.add(cashierBillsVO.getContactConnections());
			}
			if (null != cashierBillsVO.getPhone()
					&& !"".equals(cashierBillsVO.getPhone())) {
				sql += " and t.phone = ?";
				parms.add(cashierBillsVO.getPhone());
			}
			if (null != cashierBillsVO.getCcompanyname()
					&& !"".equals(cashierBillsVO.getCcompanyname())) {
				sql += " and cc.companyname like ? ";
				parms.add("%" + cashierBillsVO.getCcompanyname() + "%");
			}
			if (null != cashierBillsVO.getContactUserName()
					&& !"".equals(cashierBillsVO.getContactUserName())) {
				sql += " and cu.staffname like ? ";
				parms.add("%" + cashierBillsVO.getContactUserName() + "%");
			}
		}
		if (search == null || search.equals("")) {
			sql += " and t.taxstatus = ? ";
			parms.add("01");
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出单据报税管理
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeading(),
				baseBeanService.getListBeanBySqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出单据报税管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	public String toCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String tax;
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ? and cashierBillsID=?";
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
		tax = cashierBillsVO.getTaxstatus();
		if (tax.equals("00")) {
			BType = cashierBillsVO.getBillsType();
			toPage();
			return "add";
		} else {
			return "edit";
		}
	}

	/**
	 * 根据税务单据编号模糊查询列表
	 * 
	 * @return
	 */
	public String getAjaxDutiableList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params1 = { cashierBillsVO.getJournalNum() };
		String hql = " from CashierBillsVO where  journalNum=?";
		cashierBillsVO = (CashierBillsVO) baseBeanService
				.getBeanByHqlAndParams(hql, params1);
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("cashierBillsVO", cashierBillsVO);
		JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
		result = jsonArray.toString();
		return "success";
	}

	/**
	 * 税务单据保存
	 */
	public String saveCashierBills() {
		save();
		return "success";
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
			parameter = "添加税务单据（凭证号" + cashierBills.getJournalNum() + "）";
		} else {
			parameter = "修改税务单据（凭证号" + cashierBills.getJournalNum() + "）";
		}
		if (cashierBills.getTaxstatus() != null) {

			if (cashierBills.getTaxstatus() == "01"
					|| cashierBills.getTaxstatus().equals("01")) {
				parameter += "并把档案归档！";
			}
		}
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setCompanyName(account.getCompanyName());
		cashierBills.setOrganizationID(organizationID);
		cashierBills.setCashierDate(new Date());
		cashierBills.setBillsType(BType);
		cashierBills.setResponsible(sta.getStaffName());
		cashierBills.setAccountant(sta.getStaffName());
		cashierBills.setCashier(sta.getStaffName());
		cashierBills.setInputid(sta.getStaffName());
		
		if (cashierBills.getStaffName() != null
		&& !cashierBills.getStaffName().equals("")) {
	     String staffname = cashierBills.getStaffName().substring(0,
	    		 cashierBills.getStaffName().indexOf("-"));
	     cashierBills.setStaffName(staffname);
        }
		
	
		if (cashierBillsVO != null) {
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
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}

	}

	/**
	 * 去税务单据添加、修改页面
	 * 
	 */
	public String toSaveCashierBills() {
		toPage();
		return "add";
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
		session.put("manStaffName", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("manStaffId", sta.getStaffID());
		session.put("manStaffCode", sta.getStaffCode());
		// String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		// Object[] params = { companyID, organizationID };
		// String staffhql = "from Staff where staffID in ( select staffID from
		// COS where companyID=? and organizationID=? and cosStatus='50' )";
		// stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
		// params);
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		// contactRegardList =
		// codeService.getCCodeListByPID(companyID,"scode20110106hfjes5ucxp0000000017");
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		//物品类别
		codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101014v5zed7cukk0000000002");
		
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		// else {
		// cashierBillsVO = new CashierBillsVO();
		// cashierBillsVO.setJournalNum(serverService.getBillID(companyID));
		// }
	}

	/**
	 * 把普通单据改为报税单据
	 * 
	 * @return
	 */
	public String taxUpdateZero() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		CAccount account = (CAccount) session.get("account");
		if (checkradio != null && checkradio.size() > 0) {
			Date date = cashierBills.getTaxDate();
			for (int i = 0; i < checkradio.size(); i++) {
				cashierBills = (CashierBills) baseBeanService
						.getBeanByHqlAndParams(
								"from CashierBills where journalNum=?",
								new Object[] { checkradio.get(i) });
				cashierBills.setTaxDate(date);
				cashierBills.setStatus("20");
				cashierBills.setTaxstatus("01");
				CLogBook logBook = logBookService.saveCLogBook(organizationID,
						"添加已有单据(凭证号:" + cashierBills.getJournalNum() + ")",
						account);
				baseBeanService.update(cashierBills);
				baseBeanService.update(logBook);
			}
		}
		return "success";
	}

	/**
	 * 税务单据删除
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
				"删除税务单据(凭证号:" + cash.getJournalNum() + ")", account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, hqls,
				params);

		return "success";
	}

	/**
	 * 打印全部单据(除了草稿单据)
	 * 
	 * @return
	 */
	public String toprinttax() {
		if (cashierBillsVO != null) {
			Object[] params1 = { cashierBillsVO.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where  cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where  cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
		}
		return "printtax";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
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

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public String getBType() {
		return BType;
	}

	public void setBType(String type) {
		BType = type;
	}

	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<String> getCheckradio() {
		return checkradio;
	}

	public void setCheckradio(List<String> checkradio) {
		this.checkradio = checkradio;
	}

	public List<CCode> getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
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

	public String getTsdate() {
		return tsdate;
	}

	public void setTsdate(String tsdate) {
		this.tsdate = tsdate;
	}

	public String getTedate() {
		return tedate;
	}

	public void setTedate(String tedate) {
		this.tedate = tedate;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public List<BaseBean> getCashierBillsVoList() {
		return cashierBillsVoList;
	}

	public void setCashierBillsVoList(List<BaseBean> cashierBillsVoList) {
		this.cashierBillsVoList = cashierBillsVoList;
	}

	public String getStaffNameID() {
		return staffNameID;
	}

	public void setStaffNameID(String staffNameID) {
		this.staffNameID = staffNameID;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}
 
}
