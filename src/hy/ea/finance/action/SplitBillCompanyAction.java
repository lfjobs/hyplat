package hy.ea.finance.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.finance.BalanceChange;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.proBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.bo.finance.vo.GoodsNumVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetDetail;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;
public class SplitBillCompanyAction {
	private static final Logger logger = LoggerFactory.getLogger(SplitBillCompanyAction.class);
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
	private CompanyService companyserverService;
	@Resource
	private UpLoadFileService fileService;
	public InputStream excelStream;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String subjectsID;
	private String zz;
	private String sztype;// 用于区分是收入预算还是支出预算
	private String dtype;// 用于区分哪个明细
	private String gncheck;
	private List<String> gngid;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private GoodsCashierBillsVO goodsCashierBillsVO;
	private Map<String, GoodsBills> goodsmap;
	/**
	 * 单据类别
	 */
	private List<CCode> billsType;
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
	/**
	 * 物品类别
	 */
	private List<CCode> codeList;
	private String typeID;
	private String search;
	private String sdate;
	private String edate;
	private String departmentID;
	private String differenceStyle;
	private String staffname;
	private String BType;
	private String caid;// 单据id

	private String projectDecPath;
	private String projectDecPath1;
	private BalanceChange balanceChange;
	private String level;// 区分总部 allCompany 公司 company 部门 organization 个人staff
	private String billsTypes;

	// 项目单据
	public CostSheetBill costSheetbill;

	private String checkOutUrl;// 列表页面路径

	// private CostSheetDetail costSheetDetail; // 进销存预算子类

	/** **************************************************** */

	public void getbilltype(String company, CashierBills cashierBills) {
		String tt = null;
		String tt1 = null;
		String tt2 = null;
		String tt3 = null;
		String tt4 = null;
		if (zz.equals("00")) {
			tt = "scode20130104uyj3s8t4b50000000002"; // 现金收入类
		} else if (zz.equals("01")) {
			tt = "scode20130104uyj3s8t4b50000000003"; // 现金支出类
		} else if (zz.equals("02")) {
			tt = "scode20130104uyj3s8t4b50000000006"; // 银行收入类
		} else if (zz.equals("03")) {
			tt = "scode20130104uyj3s8t4b50000000007"; // 银行支出类
		} else if (zz.equals("04")) {
			tt = "scode20130104uyj3s8t4b50000000004"; // 应收账款类
		} else if (zz.equals("05")) {
			tt = "scode20130104uyj3s8t4b50000000005"; // 应付账款类
		} else if (zz.equals("09")) {
			tt = "scode20140217dgj38ivank0000000010"; // 应付工资类
		} else if (zz.equals("10")) {
			tt = "scode20140217dgj38ivank0000000012"; // 已付工资类
		} else if (zz.equals("11")) {
			tt = "scode20140217dgj38ivank0000000015"; // 固定资产
		} else if (zz.equals("12")) {
			tt = "scode20140217dgj38ivank0000000017"; // 资产增加
		} else if (zz.equals("13")) {
			tt = "scode20140217dgj38ivank0000000019"; // 资产减少
		} else if (zz.equals("14")) {
			tt = "scode20140217dgj38ivank0000000021"; // 预收款单
		} else if (zz.equals("15")) {
			tt = "scode20140217dgj38ivank0000000023"; // 现收款单
		} else if (zz.equals("16")) {
			tt = "scode20140217dgj38ivank0000000025"; // 销售订货
		} else if (zz.equals("17")) {
			tt = "scode20140217dgj38ivank0000000027"; // 销售发货
		} else if (zz.equals("18")) {
			tt = "scode20140217dgj38ivank0000000029"; // 销售退货
		} else if (zz.equals("19")) {
			tt = "scode20140217dgj38ivank0000000031"; // 销售调拨
		} else if (zz.equals("20")) {
			tt = "scode20140217dgj38ivank0000000036"; // 资产报损
		} else if (zz.equals("06")) {
			tt = "scode20130104uyj3s8t4b50000000002"; // 现金日记账
			tt1 = "scode20130104uyj3s8t4b50000000003";
			tt2 = "scode20130104uyj3s8t4b50000000004";
			tt3 = "scode20130104uyj3s8t4b50000000005";
		} else if (zz.equals("07")) {
			tt = "scode20130104uyj3s8t4b50000000002"; // 资金收入
			tt1 = "scode20130104uyj3s8t4b50000000006";
			tt2 = "scode20131212k63xn9ysqp0000000002";
		} else if (zz.equals("08")) {
			tt = "scode20130104uyj3s8t4b50000000003"; // 资金支出
			tt1 = "scode20130104uyj3s8t4b50000000007";
			tt2 = "scode20131212k63xn9ysqp0000000002";
		} else if (zz.equals("21")) {
			tt = "scode20130104uyj3s8t4b50000000003"; // 资金收支
			tt1 = "scode20130104uyj3s8t4b50000000007";
			tt2 = "scode20131212k63xn9ysqp0000000002";
			tt3 = "scode20130104uyj3s8t4b50000000002";
			tt4 = "scode20130104uyj3s8t4b50000000006";
		}
		if (cashierBills != null) {
			if (cashierBills.getBillsType().equals("取款单")) {
				cashierBills
						.setPbillsTypeID("scode201303255bfk6jsacr0000000002");
			} else if (cashierBills.getBillsType().equals("存款单")) {
				cashierBills
						.setPbillsTypeID("scode201303255bfk6jsacr0000000003");
			} else {
				cashierBills.setPbillsTypeID(tt);
			}
		}
		if (zz.equals("06")) {
			billsType = codeService.getCCodeListByPIDs(company, new String[] {
					tt, tt1, tt2, tt3 });
		} else if (zz.equals("07") || zz.equals("08")) {
			billsType = codeService.getCCodeListByPIDs(company, new String[] {
					tt, tt1, tt2 });
		} else if (zz.equals("21") || zz.equals("08")) {
			billsType = codeService.getCCodeListByPIDs(company, new String[] {
					tt, tt1, tt2, tt3, tt4 });
		} else {
			billsType = codeService.getCCodeListByPID(company, tt);
		}
		CCode cCode = new CCode();
		List<CCode> cCodes = codeService.getCCodeListByPID(company,
				"scode201303255bfk6jsacr0000000002");
		if (cCodes.size() > 0) {
			if (zz.equals("00") || zz.equals("03")) {
				cCode.setCodeValue("取款单");
				billsType.add(cCode);
			}
			if (zz.equals("01") || zz.equals("02")) {
				cCode.setCodeValue("存款单");
				billsType.add(cCode);
			}
		}
	}

	/**
	 * 出纳记账列表
	 * 
	 * @param : account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierBillsList() {
		search();
		return "list";
	}

	/**
	 * 去出纳记账添加、修改页面
	 * 
	 */
	public String toSaveCashierBills() {
		toPage();
		String ret = "";
		if (cashierBillsVO != null
				&& cashierBillsVO.getStatusbill().equals("00")) {
			ret = "edit2";
		} else {
			ret = "edit";
		}
		return ret;
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", cashierBillsVO);
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
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
	}

	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param : CashierBills 查询条件
	 * @return ：beanlist
	 */

	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String Hql = " from CashierBillsVO ";
		Hql += " where 1=1 ";
		Hql += " and companyid = ? ";
		parms.add(account.getCompanyID());
		getbilltype(account.getCompanyID(), null);
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if (null != cashierBillsVO.getBillsType()
						&& !"".equals(cashierBillsVO.getBillsType())) {
					Hql += " and billstype=?";
					parms.add(cashierBillsVO.getBillsType());
				} else {
					if (billsType.size() > 1) {
						Hql += " and (";
						for (int i = 0; i < billsType.size(); i++) {
							CCode obj = billsType.get(i);
							// Object[] objs = (Object[]) obj;
							if (i > 0) {
								Hql += " or";
							}
							Hql += " billstype=?";
							parms.add(obj.getCodeValue());
						}
						Hql += ")";
					} else if (billsType.size() == 1) {
						CCode obj = billsType.get(0);
						// Object[] objs = (Object[]) obj;
						Hql += " and billstype=?";
						parms.add(obj.getCodeValue());
					} else {
						Hql += " and billstype is null";
					}
				}
				if (null != cashierBillsVO.getStaffID().trim()
						&& !"".equals(cashierBillsVO.getStaffID().trim())) {
					Hql += " and staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
				}
				if (null != cashierBillsVO.getDepartmentID()
						&& !"".equals(cashierBillsVO.getDepartmentID())) {
					Hql += " and (organizationID = ? or departmentID = ? ) ";
					parms.add(cashierBillsVO.getDepartmentID());
					parms.add(cashierBillsVO.getDepartmentID());
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					Hql += " and journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim() + "%");
				}
				if (null != cashierBillsVO.getStatus().trim()
						&& !"".equals(cashierBillsVO.getStatus().trim())) {
					Hql += " and status = ?";
					parms.add(cashierBillsVO.getStatus().trim());
				} else {
					Hql += " and status between ? and ?";
					parms.add("04");
					parms.add("10");
				}
				if (null != cashierBillsVO.getContactConnections().trim()
						&& !"".equals(cashierBillsVO.getContactConnections()
								.trim())) {
					Hql += " and contactConnections = ? ";
					parms.add(cashierBillsVO.getContactConnections());
				}
				if (null != cashierBillsVO.getPhone().trim()
						&& !"".equals(cashierBillsVO.getPhone().trim())) {
					Hql += " and phone = ? ";
					parms.add(cashierBillsVO.getPhone().trim());
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					Hql += " and cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBillsVO.getCcompanyname().trim()
						&& !"".equals(cashierBillsVO.getCcompanyname().trim())) {
					Hql += " and companyname like ? ";
					parms.add("%" + cashierBillsVO.getCcompanyname().trim()
							+ "%");
				}
				if (null != cashierBillsVO.getContactUserName().trim()
						&& !"".equals(cashierBillsVO.getContactUserName()
								.trim())) {
					Hql += " and staffname like ? ";
					parms.add("%" + cashierBillsVO.getContactUserName().trim()
							+ "%");
				}
				if (null != cashierBillsVO.getDepStatue()
						&& !"".equals(cashierBillsVO.getDepStatue())) {
					if ("00".equals(cashierBillsVO.getDepStatue())) {
						Hql += " and (depStatue = ? or depStatue is null)";
						parms.add("00");
					} else {
						Hql += " and depStatue = ? ";
						parms.add(cashierBillsVO.getDepStatue().trim());
					}
				}
			}
		} else {
			Hql += " and (status between ? and ? or status=? or status=?or status = ?)";
			parms.add("04");
			parms.add("07");
			parms.add("11");
			parms.add("09");
			parms.add("40");
			if (billsType.size() > 1) {
				Hql += " and (";
				for (int i = 0; i < billsType.size(); i++) {
					CCode obj = billsType.get(i);
					// Object[] objs = (Object[]) obj;
					if (i > 0) {
						Hql += " or";
					}
					Hql += " billstype=?";
					parms.add(obj.getCodeValue());
				}
				Hql += ")";
			} else if (billsType.size() == 1) {
				CCode obj = billsType.get(0);
				// Object[] objs = (Object[]) obj;
				Hql += " and billstype=?";
				parms.add(obj.getCodeValue());
			} else {
				Hql += " and billstype is null";
			}
		}
		Hql += " order by cashierDate desc";
		result.add(Hql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearchGood() {
		ActionContext.getContext().getSession()
				.put("goodssearch", goodsCashierBillsVO);
		return searchGood();
	}

	/**
	 * 封装查询类表的方法
	 */
	public String searchGood() {
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
		List<Object> list = getProList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, "select count(*) "
						+ hql.substring(hql.indexOf("from")), parms);
		return "listGood";
	}

	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param : GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql += " and t.organizationID=?";
		parms.add(organizationID);
		getbilltype(account.getCompanyID(), null);
		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session
					.get("goodssearch");
			if (null != goodsCashierBillsVO.getStaffID()
					&& !"".equals(goodsCashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(goodsCashierBillsVO.getStaffID().trim());
			}
			if (null != goodsCashierBillsVO.getDepartmentID()
					&& !"".equals(goodsCashierBillsVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							goodsCashierBillsVO.getDepartmentID())) {
				sql += " and t.departmentID=?";
				parms.add(goodsCashierBillsVO.getDepartmentID().trim());
			}
			if (null != goodsCashierBillsVO.getBillsType()
					&& !"".equals(goodsCashierBillsVO.getBillsType())) {
				sql += " and t.billsType = ?";
				parms.add(goodsCashierBillsVO.getBillsType().trim());
			}
			if (null != goodsCashierBillsVO.getJournalNum()
					&& !"".equals(goodsCashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim()
						+ "%");
			}
			if (null != goodsCashierBillsVO.getGoodsName()
					&& !"".equals(goodsCashierBillsVO.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsName().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsCoding()
					&& !"".equals(goodsCashierBillsVO.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsCoding().trim()
						+ "%");
			}
			if (null != goodsCashierBillsVO.getCcompanyname()
					&& !"".equals(goodsCashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + goodsCashierBillsVO.getCcompanyname().trim()
						+ "%");
			}
			if (null != goodsCashierBillsVO.getContactUserID()
					&& !"".equals(goodsCashierBillsVO.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + goodsCashierBillsVO.getContactUserName().trim()
						+ "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00:000",
						"yyyy-MM-dd HH:mm:ss:sss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59:999",
						"yyyy-MM-dd HH:mm:ss:sss"));
			}
		} else {
			if (billsType.size() > 1) {
				sql += " and (";
				for (int i = 0; i < billsType.size(); i++) {
					CCode obj = billsType.get(i);
					// Object[] objs = (Object[]) obj;
					if (i > 0) {
						sql += " or";
					}
					sql += " t.billsType=?";
					parms.add(obj.getCodeValue());
				}
				sql += ")";
			} else if (billsType.size() == 1) {
				CCode obj = billsType.get(0);
				// Object[] objs = (Object[]) obj;
				sql += " and t.billsType=?";
				parms.add(obj.getCodeValue());
			} else {
				sql += " and t.billsType is null";
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出出纳记账
	 * 
	 * @param : account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 导出支出报表
	 * 
	 * @param : account organizationID
	 * @return : showexcel
	 */

	public String showExcelzc() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String hql = "from GoodsNumVO where companyid=? and organizationid=? and (status=? or status=? or status=? or status=?) and endDate between ? and ? order by endDate asc";
		String hql2 = "from GoodsNumVO where companyid=? and organizationid=? and (status=? or status=? or status=? or status=?) and endDate < ? order by endDate asc";
		List<BaseBean> listbb2 = baseBeanService.getListBeanByHqlAndParams(
				hql2,
				new Object[] { account.getCompanyID(), organizationID, "01",
						"02", "03", "04",
						Utilities.getDateFromString(sdate, "yyyy-MM-dd") });
		float xjnum = 0f;
		float yhnum = 0f;
		float xjye = 0f;
		float xjzye = 0f;
		if (listbb2.size() > 0) {
			for (int i = 0; i < listbb2.size(); i++) {
				GoodsNumVO gNumVO = new GoodsNumVO();
				gNumVO = (GoodsNumVO) listbb2.get(i);
				String money = fnum.format(Float.parseFloat(gNumVO
						.getQuantity() == null ? "0" : gNumVO.getQuantity())
						* Float.parseFloat(gNumVO.getPrice() == null ? "0"
								: gNumVO.getPrice()));
				if (gNumVO.getCostType().equals("预入金")) {
					gNumVO.setPreDeposit(money);
					gNumVO.setCryh(money);
				} else if (gNumVO.getCostType().equals("应收款")) {
					gNumVO.setReceivables(money);
					gNumVO.setQcxj(money);
				} else if (gNumVO.getCostType().equals("应付款")) {
					gNumVO.setPayables(money);
					gNumVO.setCrxj(money);
				} else if (gNumVO.getCostType().equals("报销冲账")) {
					gNumVO.setBxcz(money);
					gNumVO.setCrxj(money);
				} else if (gNumVO.getCostType().equals("取现")) {
					gNumVO.setBxf(money);
					gNumVO.setCrxj(money);
					gNumVO.setQcyh(money);
				} else {
					gNumVO.setBxf(money);
					gNumVO.setQcxj(money);
				}
				xjnum = Float.parseFloat(fnum.format(xjnum
						+ Float.parseFloat(gNumVO.getCrxj() == null ? "0"
								: gNumVO.getCrxj())
						- Float.parseFloat(gNumVO.getQcxj() == null ? "0"
								: gNumVO.getQcxj())));
				yhnum = Float.parseFloat(fnum.format(yhnum
						+ Float.parseFloat(gNumVO.getCryh() == null ? "0"
								: gNumVO.getCryh())
						- Float.parseFloat(gNumVO.getQcyh() == null ? "0"
								: gNumVO.getQcyh())));
				xjye = Float.parseFloat(fnum.format(Float.parseFloat(gNumVO
						.getKcxj() == null ? "0" : gNumVO.getKcxj())
						+ Float.parseFloat(gNumVO.getYhxj() == null ? "0"
								: gNumVO.getYhxj())));
				xjzye = Float
						.parseFloat(fnum.format(Float.parseFloat(gNumVO
								.getKcxj() == null ? "0" : gNumVO.getKcxj())
								+ Float.parseFloat(gNumVO.getYhxj() == null ? "0"
										: gNumVO.getYhxj())
								+ Float.parseFloat(gNumVO.getReceivables() == null ? "0"
										: gNumVO.getReceivables())));
				gNumVO.setKcxj(String.valueOf(xjnum));
				gNumVO.setYhxj(String.valueOf(yhnum));
				gNumVO.setXjye(String.valueOf(xjye));
				gNumVO.setXjzye(String.valueOf(xjzye));
			}
		}
		GoodsNumVO goodsNumVO = new GoodsNumVO();
		goodsNumVO.setCompanyname("上月余额");
		goodsNumVO.setKcxj(String.valueOf(xjnum));
		goodsNumVO.setYhxj(String.valueOf(yhnum));
		goodsNumVO.setXjye(String.valueOf(xjye));
		goodsNumVO.setXjzye(String.valueOf(xjzye));
		List<BaseBean> listbb = baseBeanService.getListBeanByHqlAndParams(
				hql,
				new Object[] { account.getCompanyID(), organizationID, "01",
						"02", "03", "04",
						Utilities.getDateFromString(sdate, "yyyy-MM-dd"),
						Utilities.getDateFromString(edate, "yyyy-MM-dd") });
		listbb.add(0, goodsNumVO);
		String orgname = "";
		if (listbb.size() > 0) {
			for (int i = 0; i < listbb.size(); i++) {
				if (i > 0) {
					GoodsNumVO gNumVO = new GoodsNumVO();
					gNumVO = (GoodsNumVO) listbb.get(i);
					orgname = gNumVO.getDepartmentname();
					String money = fnum
							.format(Float
									.parseFloat(gNumVO.getQuantity() == null ? "0"
											: gNumVO.getQuantity())
									* Float.parseFloat(gNumVO.getPrice() == null ? "0"
											: gNumVO.getPrice()));
					if (gNumVO.getCostType().equals("预入金")) {
						gNumVO.setPreDeposit(money);
						gNumVO.setCryh(money);
					} else if (gNumVO.getCostType().equals("应收款")) {
						gNumVO.setReceivables(money);
						gNumVO.setQcxj(money);
					} else if (gNumVO.getCostType().equals("应付款")) {
						gNumVO.setPayables(money);
						gNumVO.setCrxj(money);
					} else if (gNumVO.getCostType().equals("报销冲账")) {
						gNumVO.setBxcz(money);
						gNumVO.setCrxj(money);
					} else if (gNumVO.getCostType().equals("取现")) {
						gNumVO.setBxf(money);
						gNumVO.setCrxj(money);
						gNumVO.setQcyh(money);
					} else {
						gNumVO.setBxf(money);
						gNumVO.setQcxj(money);
					}
					gNumVO.setKcxj(fnum.format(xjnum
							+ Float.parseFloat(gNumVO.getCrxj() == null ? "0"
									: gNumVO.getCrxj())
							- Float.parseFloat(gNumVO.getQcxj() == null ? "0"
									: gNumVO.getQcxj())));
					gNumVO.setYhxj(fnum.format(yhnum
							+ Float.parseFloat(gNumVO.getCryh() == null ? "0"
									: gNumVO.getCryh())
							- Float.parseFloat(gNumVO.getQcyh() == null ? "0"
									: gNumVO.getQcyh())));
					gNumVO.setXjye(fnum.format(Float.parseFloat(gNumVO
							.getKcxj() == null ? "0" : gNumVO.getKcxj())
							+ Float.parseFloat(gNumVO.getYhxj() == null ? "0"
									: gNumVO.getYhxj())));
					gNumVO.setXjzye(fnum.format(Float.parseFloat(gNumVO
							.getKcxj() == null ? "0" : gNumVO.getKcxj())
							+ Float.parseFloat(gNumVO.getYhxj() == null ? "0"
									: gNumVO.getYhxj())
							+ Float.parseFloat(gNumVO.getReceivables() == null ? "0"
									: gNumVO.getReceivables())));
					xjnum = Float.parseFloat(gNumVO.getKcxj());
					yhnum = Float.parseFloat(gNumVO.getYhxj());
				}
			}
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		String strTitle = cd.get(Calendar.YEAR) + "年" + cd.get(Calendar.MONTH)
				+ 1 + "月" + cd.get(Calendar.DAY_OF_MONTH) + "日到";
		cd.setTime(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		strTitle = strTitle + cd.get(Calendar.YEAR) + "年"
				+ cd.get(Calendar.MONTH) + 1 + "月"
				+ cd.get(Calendar.DAY_OF_MONTH) + "日"
				+ account.getCompanyName() + orgname + "费用流水报表";
		excelStream = excelService.showExcelByWater(
				GoodsNumVO.columnHeadings(), listbb, strTitle);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账", account);
		baseBeanService.update(logBook);
		return "showexcel";
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
	 * 封装跳转添加或编辑页面的方法
	 * 
	 * @param :companyID, organizationID 添加
	 * @param :companyID, organizationID ，cashierTally.getCashierID()修改
	 * @return : edit
	 */
	private void toPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();

		// 单据类别
		getbilltype(account.getCompanyID(), null);
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
		// 仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		// 单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		// 个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		// 物品类别
		codeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101014v5zed7cukk0000000002");

		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
			pageForm = baseBeanService.getPageForm(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
			String hql2 = "from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills = (CashierBills) baseBeanService
					.getBeanByHqlAndParams(hql2, params1);
		}
	}

	// 添加项目单据
	public String getAjaxCashList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { account.getCompanyID(), parameter };
		String hql = " from CostSheetBill where companyID=? and journalNum=?";
		CostSheetBill costSheetbill = new CostSheetBill();
		costSheetbill = (CostSheetBill) baseBeanService.getBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("costSheetbill", costSheetbill);
		JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
		result = jsonArray.toString();
		return "success";
	}

	// 判断是否有单据
	public String getbills() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from dtProBills where companyID=? and csbID=? ";
		int count = baseBeanService.getConutByBySqlAndParams(hql, new Object[] {
				account.getCompanyID(), costSheetbill.getCsbID() });
		String counts = String.valueOf(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 将项目单据生成出纳单（可进行三审） ct
	 * 
	 * @return
	 */
	public String cashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		String hql = "from CostSheetBill where groupCompanySn=? and companyID=? and csbID=? ";
		CostSheetBill costSheetBill1 = (CostSheetBill) baseBeanService
				.getBeanByHqlAndParams(
						hql,
						new Object[] {
								ActionContext.getContext().getSession()
										.get("groupCompanySn").toString(),
								account.getCompanyID(),
								costSheetbill.getCsbID() });
		CashierBills cashierBills = new CashierBills();
		cashierBills.setJournalNum(serverService.getBillID(account
				.getCompanyID()));
		cashierBills.setCashierBillsID(serverService
				.getServerID("cashierTally"));
		cashierBills.setPcompanyID(company.getCompanyID());
		cashierBills.setPcompanyName(company.getCompanyName());
		cashierBills.setStatus("01");
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setCompanyName(account.getCompanyName());
		cashierBills.setOrganizationID(organizationID);
		cashierBills.setPbillsTypeID("scode2013032523n6id3d3p0000000002");
		cashierBills.setCashierDate(new Date());
		cashierBills.setDataDepotID(costSheetBill1.getDepartmentID());
		String hql1 = "from COrganization where companyID=? and organizationID=?";
		COrganization orc = (COrganization) baseBeanService
				.getBeanByHqlAndParams(
						hql1,
						new Object[] { account.getCompanyID(),
								costSheetBill1.getDepartmentID() });
		cashierBills.setDataDepotName(orc.getOrganizationName());
		cashierBills.setCompanyBankNum(costSheetBill1.getCompanyBankNum());
		cashierBills.setStaffID(costSheetBill1.getStaffID());
		cashierBills.setStaffName(costSheetBill1.getStaffName());
		cashierBills.setStaffCode(costSheetBill1.getStaffCode());
		cashierBills.setDepartmentID(costSheetBill1.getDepartmentID());
		cashierBills.setDepartmentName(costSheetBill1.getDepartmentName());
		cashierBills.setBillsType(cashierBillsVO.getBillsType());
		getbilltype(account.getCompanyID(), cashierBills);
		// 往来单位
		cashierBills.setContactConnections(costSheetBill1
				.getCcompanyRelationship());
		cashierBills.setCcompanyID(costSheetBill1.getCcompanyID());
		cashierBills.setCcompanyName(costSheetBill1.getCcompanyName());
		cashierBills.setAccountNum(costSheetBill1.getAccountNum());
		cashierBills.setCompanyAddr(costSheetBill1.getCompanyAddr());
		cashierBills.setCompanyTel(costSheetBill1.getCompanyTel());
		cashierBills.setCresponsible(costSheetBill1.getCresponsible());
		cashierBills.setResponsibleTel(costSheetBill1.getResponsibleTel());
		cashierBills.setIndustryType(costSheetBill1.getIndustryType());

		// 往来个人
		cashierBills.setPhone(costSheetBill1.getCstaffRelationship());
		cashierBills.setUserAccountNum(costSheetBill1.getUserAccountNum());
		cashierBills.setContactUserID(costSheetBill1.getCstaffID());
		cashierBills.setCtUserName(costSheetBill1.getCstaffName());
		cashierBills.setReference(costSheetBill1.getReference());
		cashierBills
				.setStaffIdentityCard(costSheetBill1.getStaffIdentityCard());
		cashierBills.setReferenceCode(costSheetBill1.getReferenceCode());
		cashierBills.setReferenceOrganization(costSheetBill1
				.getReferenceOrganization());
		cashierBills.setStaffAddress(costSheetBill1.getStaffAddress());

		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cashierBills);

		// 查询产品保存物品单据表中
		String hql2 = "from CostSheetDetail where csbID=?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql2,
				new Object[] { costSheetbill.getCsbID() });
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				GoodsBills goods = new GoodsBills();
				CostSheetDetail detail = (CostSheetDetail) list.get(i);
				goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
				goods.setEndDate(new Date());
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				goods.setGoodsNomber(String.valueOf((i + 1)));
				goods.setGoodsID(detail.getGoodsID());
				goods.setBatchNumber(detail.getBatchNumber());
				goods.setStartDate(detail.getStartDate());
				goods.setPeriodDate(detail.getPeriodDate());
				goods.setSubjectsID(detail.getSubjectsID());
				goods.setSubjectsName(detail.getSubjectsName());
				goods.setReasonThing(detail.getReasonThing());
				goods.setCostType(detail.getCostType());
				goods.setWeight(detail.getWeight());
				goods.setBoxStandard(detail.getBoxStandard());
				goods.setPriceManage(detail.getPriceManage());
				goods.setDepotType(detail.getDepotType());
				goods.setDepotID(detail.getDepotID());
				goods.setDepotName(detail.getDepotName());
				goods.setLoan(detail.getLoan());
				goods.setForLoan(detail.getForLoan());
				goods.setDirection(detail.getDirection());
				goods.setBalance(detail.getBalance());
				goods.setGoodsVariableID(detail.getUnit());
				goods.setPrice(detail.getUnitPrice());
				goods.setQuantity(detail.getQuantity());
				goods.setMoney(detail.getAmount());
				baseBeanList.add(goods);
			}
		}
		// 用于保存项目预算招标项目字段（中间表）
		String hql3 = "from CostSheetBill where companyID=? and groupCompanySn=? and csbID=?";
		CostSheetBill costbill = (CostSheetBill) baseBeanService
				.getBeanByHqlAndParams(
						hql3,
						new Object[] {
								account.getCompanyID(),
								ActionContext.getContext().getSession()
										.get("groupCompanySn").toString(),
								costSheetbill.getCsbID() });
		proBills bills = new proBills();
		bills.setProjectID(serverService.getServerID("projectBills"));
		bills.setCompanyID(account.getCompanyID());
		bills.setCashierBillsID(cashierBills.getCashierBillsID());
		bills.setCsbID(costbill.getCsbID());
		bills.setProjectName(costbill.getProjectName());
		bills.setStartTime(costbill.getStartTime());
		bills.setEndTime(costbill.getEndTime());
		bills.setCostdescription(costbill.getCostdescription());
		baseBeanList.add(bills);

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
		return "success";
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toPage2() {
		return "toPage";
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toPageNum() {
		String a[] = gncheck.split(",");
		if (a.length >= 1) {
			gngid = new ArrayList<String>();
			for (int i = 0; i < a.length; i++) {
				List<BaseBean> listgn = baseBeanService
						.getListBeanByHqlAndParams(
								"from GoodsNum where goodsBillsID=?",
								new Object[] { a[i] });
				if (listgn.size() > 0) {
					for (int j = 0; j < listgn.size(); j++) {
						GoodsNum goodsNum = (GoodsNum) listgn.get(j);
						gngid.add(goodsNum.getGoodsCoding()
								+ goodsNum.getGoodsnumber());
					}
				}
			}
		}
		return "toPagenum";
	}

	/**
	 * @author lou
	 * @param name
	 *            :确任收款
	 * @return
	 */
	public String confirmReceivables(String ss) {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String sql = "update dtcashierBills set status=? where companyID=? and cashierBillsID=? and status=?";
		String[] sqls = { sql };
		List<Object[]> beams = new ArrayList<Object[]>();
		String[] bb = { "04", account.getCompanyID(),
				cashierBills.getCashierBillsID(), "09" };
		beams.add(bb);
		baseBeanService.executeSqlsByParmsList(null, sqls, beams);
		return "success";
	}

	/**
	 * @param 项目树
	 * @author lou
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAjaxCsb() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String organizationID = (String) ActionContext.getContext()
				.getSession().get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { account.getCompanyID(), organizationID };
		String hql = "select distinct cp.journalnum,cp.projectname" +
				" from dtcashapplybills cp" +
				" left join dtgoodsbills g on cp.goodsbillsid = g.goodsbillsid" +
				" left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid" +
				" where ca.companyid=? and ca.organizationid=?";
		List<Object[]> costList = baseBeanService.getListBeanBySqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("costList", costList);
		JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
		result = jsonArray.toString();
		return "success";
	}

	/**
	 * JSON取得物品列表
	 */
	public String getCostSheetDetailByTypeID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		JsonConfig cfg = new JsonConfig();  
		 cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"}); 
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "select G.GOODSBILLSID,ca.companynames "
				+ "G.QUANTITY, G.PRICE, G.MONEY, to_char(ca.CASHIERDATE,'yyyy-MM-dd'), "
				+ "cp.appropriationmoney,cp.appropriationnote, to_char(cp.appropriationdate,'yyyy-MM-dd'),cp.appropriatestatus" +
				" from dtcashapplybills cp" +
				" left join dtgoodsbills g on cp.goodsbillsid = g.goodsbillsid" +
				" left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid" +
				" where cp.journalnum=?";
		String sqlcount = " SELECT COUNT(1) from dtcashapplybills cp" +
				" left join dtgoodsbills g on cp.goodsbillsid = g.goodsbillsid" +
				" left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid" +
				" where cp.journalnum=?";
		List<Object> arrays = new ArrayList<Object>();
		arrays.add(typeID);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,
				arrays.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("pageForm", pageForm);
		try {
			JSONObject oj = JSONObject.fromObject(map, jsonConfig);
			result = oj.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		
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

	public List<CCode> getBillsType() {
		return billsType;
	}

	public void setBillsType(List<CCode> billsType) {
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

	public String getSubjectsID() {
		return subjectsID;
	}

	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public String getZz() {
		return zz;
	}

	public void setZz(String zz) {
		this.zz = zz;
	}

	public CostSheetBill getCostSheetbill() {
		return costSheetbill;
	}

	public void setCostSheetbill(CostSheetBill costSheetbill) {
		this.costSheetbill = costSheetbill;
	}

	public BalanceChange getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(BalanceChange balanceChange) {
		this.balanceChange = balanceChange;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSztype() {
		return sztype;
	}

	public void setSztype(String sztype) {
		this.sztype = sztype;
	}

	public GoodsCashierBillsVO getGoodsCashierBillsVO() {
		return goodsCashierBillsVO;
	}

	public void setGoodsCashierBillsVO(GoodsCashierBillsVO goodsCashierBillsVO) {
		this.goodsCashierBillsVO = goodsCashierBillsVO;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getBillsTypes() {
		return billsTypes;
	}

	public void setBillsTypes(String billsTypes) {
		this.billsTypes = billsTypes;
	}

	public String getCaid() {
		return caid;
	}

	public void setCaid(String caid) {
		this.caid = caid;
	}

	public String getGncheck() {
		return gncheck;
	}

	public void setGncheck(String gncheck) {
		this.gncheck = gncheck;
	}

	public List<String> getGngid() {
		return gngid;
	}

	public void setGngid(List<String> gngid) {
		this.gngid = gngid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getCheckOutUrl() {
		return checkOutUrl;
	}

	public void setCheckOutUrl(String checkOutUrl) {
		this.checkOutUrl = checkOutUrl;
	}
}
