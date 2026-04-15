package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.finance.BalanceChange;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashApplyBills;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.finance.vo.GoodsBillsVO;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.bo.finance.vo.GoodsNumVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.math.BigDecimal;
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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * SplitBillAction 收支单据操作
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class SplitBillAction {
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
	private String commStr; //用于传递审核已经字符串
	private String billtname;//用于传递生成单据类别
	
	private String srandom;
	
	private List<String> gngid;
	private Map<String, String> bcheckMap;
	private List<Object[]> report;
	
	private List<BaseBean> BaseBeanlist; //传递实体类
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private GoodsCashierBillsVO goodsCashierBillsVO;
	private Map<String, GoodsBills> goodsmap;
	private GoodsBillsVO goodsBillsVO;
	private CashApplyBills cashApplyBills;
	private List<CashApplyBills> cash;
	private String xmtype;
	private String searchValue;
	/**
	 * 单据类别
	 */
	private List<CCode> billsType;
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	
	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;
	
	private String typeID;
	private String search;
	private String sdate;
	private String edate;
	private String departmentID;
	private String differenceStyle;
	private String staffname;
	private String caid;// 单据id
	private String cuid;//项目id
	private BigDecimal jy;
	private String status;
	private String projectDecPath;
	private String projectDecPath1;
	private BalanceChange balanceChange;
	private String level;// 区分总部 allCompany 公司 company 部门 organization 个人staff
	private String billsTypes;
	private String comments;//审核意见
	private String deptpost;//审核人职务

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
	 * 
	 * 微分金支出单跳转页面
	 * 
	 */
	public String getwfjjsp()
	
	{
		return "protre";
		
		
		
	}

	/**
	 * 出纳记账保存
	 */
	public String saveCashierBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(srandom!=null&&!srandom.equals("")){
			if(srandom!=null&&srandom.equals(session.get("session_value"))){
				session.remove("session_value");
			}else{
			   return toSaveCashierBills();
			}
		}
		save1();
		return "success";
	}

	/**
	 * 去出纳记账添加、修改页面
	 * 
	 */
	public String toSaveCashierBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");
		toPage();
		String ret = "";
		if (cashierBills.getBillsType().equals("收款单")||cashierBills.getBillsType().equals("待收款单")) {
			ret = "update";
		}else if(cashierBills.getBillsType().equals("预入款单")||cashierBills.getBillsType().equals("欠款单")){
			ret = "appdeit";
		}else {
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
		String organizationID = (String) session.get("organizationID");
		String Hql = " from CashierBillsVO ";
		Hql += " where 1=1 ";
		Hql += " and companyid = ? ";
	parms.add(account.getCompanyID());
		Hql += " and organizationid = ? ";
		parms.add(organizationID);
		//getbilltype(account.getCompanyID(), null);
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if(null!=cashierBillsVO.getProjectName().trim()
						&&!"".equals(cashierBillsVO.getProjectName().trim())){
					Hql += " and projectName like ?";
					parms.add("%"+cashierBillsVO.getProjectName().trim()+"%");
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					Hql += " and journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim() + "%");
				}
				if (null != cashierBillsVO.getBillsType()
						&& !"".equals(cashierBillsVO.getBillsType())) {
					Hql += " and billstype=?";
					parms.add(cashierBillsVO.getBillsType());
				} else {
					Hql += " and billstype in (?,?,?,?,?,?,?)";
					parms.add("收款单");
					parms.add("待收款单");
					parms.add("预入款单");
					parms.add("支款单");
					parms.add("销售明细单");
					parms.add("项目收入预算单");
					parms.add("欠款单");
				}
				if (null != cashierBillsVO.getStaffname().trim()
						&& !"".equals(cashierBillsVO.getStaffname().trim())) {
					Hql += " and staffname like ?";
					parms.add("%"+cashierBillsVO.getStaffname().trim()+"%");
				}
				/*if (null != cashierBillsVO.getDepartmentID().trim()
						&& !"".equals(cashierBillsVO.getDepartmentID().trim())) {
					Hql += " and departmentid = ?";
					parms.add(cashierBillsVO.getDepartmentID().trim());
				}*/
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					Hql += " and cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBillsVO.getStatus().trim()
						&& !"".equals(cashierBillsVO.getStatus().trim())) {
					Hql += " and status = ?";
					parms.add(cashierBillsVO.getStatus().trim());
				} else {
					Hql += " and (status between ? and ? or status=? or status=?or status = ? or status = ? or status = ?)";
					parms.add("04");
					parms.add("07");
					parms.add("11");
					parms.add("09");
					parms.add("40");
					parms.add("45");
					parms.add("46");
				}
			}
		} else {
			Hql += " and (status between ? and ? or status=? or status=?or status = ? or status = ? or status = ?)";
			parms.add("04");
			parms.add("07");
			parms.add("11");
			parms.add("09");
			parms.add("40");
			parms.add("45");
			parms.add("46");
			Hql += " and billstype in (?,?,?,?,?,?,?)";
			parms.add("收款单");
			parms.add("预入款单");
			parms.add("支款单");
			parms.add("销售明细单");
			parms.add("项目收入预算单");
			parms.add("欠款单");
			parms.add("费用报销单");
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
	 * 生成收款/代收款单
	 * 
	 * @param : cashierTally
	 * @return : getCashierTallyList() 返回列表
	 */
	public String generateNewBill() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(srandom!=null&&!srandom.equals("")){
			if(srandom!=null&&srandom.equals(session.get("session_value"))){
				session.remove("session_value");
			}else{
			   return toSaveCashierBills();
			}
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String[] billnames=billtname.split(",");
		for (int i = 0; i < billnames.length; i++) {
			if(!billnames.equals("")&&billnames!=null){
				savebill(baseBeanList,billnames[i]);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,null, null);
		//cashierBills=cBills;
		return "success";
	}
	
	/**
	 * 生成单据(调用)
	 * 
	 * @param : cashierTally
	 * @return : getCashierTallyList() 返回列表
	 */
	private List<BaseBean> savebill(List<BaseBean> baseBeanList,String bn){
		CashierBills cBills=new CashierBills();
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			
			//生成单据信息
			CashierBills caBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(
					"from CashierBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
			cBills=(CashierBills)caBills.cloneCashierBills();
			cBills.setCashierBillsID(serverService.getServerID("cashierTally"));
			cBills.setJournalNum(serverService.getBillID(account.getCompanyID()));
			cBills.setCashierBillsKey(null);
			cBills.setBillsType(bn);
			cBills.setStatus("40");
			cBills.setCashierDate(new Date());
			cBills.setStatusbill("01");
			
			//制单人信息
			cBills.setInputName("系统生成");
			cBills.setInputid(null);  //制单人员id
			cBills.setInputCompanyid(null); //制单人公司id
			cBills.setInputCompanyName(null); //制单人公司名称
			cBills.setInputOrganizationID(null); //制单人部门id
			cBills.setInputOrganizationName(null); //制单人部门名称
			
			
			//修改预算单据状态
			/*caBills.setStatus("50");*/
			caBills.setFkStatus("00");//收入预算单改为已付款状态
			baseBeanList.add(caBills);
			//生成物品单据
			String[] goosbiid=caid.split(",");
			for(int i = 0; i < goosbiid.length; i++ ){
				GoodsBills gBills=(GoodsBills)baseBeanService.getBeanByHqlAndParams("from GoodsBills where goodsBillsID=?", new Object[]{goosbiid[i]});
				GoodsBills gBills1=(GoodsBills)gBills.cloneGoodsBills();
				gBills1.setGoodsBillsID(serverService.getServerID("goodsbills"));
				gBills1.setGoodsBillsKey(null);
				gBills1.setStartDate(new Date());
				gBills1.setCashierBillsID(cBills.getCashierBillsID());
				gBills1.setPlanGoodsBillsID(gBills.getGoodsBillsID());
				
				List<BaseBean> goodlist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where planGoodsBillsID=? ", new Object[]{gBills.getGoodsBillsID()});
				BigDecimal bg1=new BigDecimal("0");
				if(goodlist.size()>0){
					for (int j = 0; j < goodlist.size(); j++) {
						GoodsBills g=(GoodsBills)goodlist.get(j);
						BigDecimal bg=new BigDecimal((g.getRealMoney()==null||g.getRealMoney().equals("")?"0":g.getRealMoney()));
						bg1=bg1.add(bg);
					}
				}
				gBills1.setAlreadyMoney(String.valueOf(bg1));
				
				BigDecimal bgm=new BigDecimal(gBills.getMoney());
				
				gBills1.setRealMoney(String.valueOf(bgm.subtract(bg1)));
				
				BigDecimal bgr=new BigDecimal(gBills1.getRealMoney());
				
				gBills1.setFutureMoney(String.valueOf(bgm.subtract(bgr).subtract(bg1)));
				
				if(gBills1.getTargetStart()!=null){
					if(i<=0){
						cBills.setTargetStart(gBills1.getTargetStart());
						cBills.setTargetEnd(gBills1.getTargetEnd());
					}else{
						if(cBills.getTargetStart().getTime()<gBills1.getTargetStart().getTime()){
							cBills.setTargetStart(gBills1.getTargetStart());
						}
						if(cBills.getTargetEnd().getTime()<gBills1 .getTargetEnd().getTime()){
							cBills.setTargetEnd(gBills1.getTargetEnd());
						}
					}
					cBills.setTargetDeptID(gBills1.getTargetDeptID());//目标部门ID
					cBills.setTargetDeptName(gBills1.getTargetDeptName());//目标部门name
					cBills.setTargetSalerID(gBills1.getTargetSalerID());//目标业务员ID
					cBills.setTargetSalerName(gBills1.getTargetSalerName());//目标业务员Name	
				}
				baseBeanList.add(gBills1);
			}
			baseBeanList.add(cBills);
			// 保存与预算单据关联单据单据表信息
			RelatedBill relatedBill = new RelatedBill() ;
			relatedBill.setRbID(serverService.getServerID("relatedbill"));
			relatedBill.setCashid(caBills.getCashierBillsID());
			relatedBill.setCashfid(cBills.getCashierBillsID());
			baseBeanList.add(relatedBill);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return baseBeanList;
	}

	/**
	 * 封装保存的方法
	 * 
	 * @param : cashierTally
	 * @return : toSaveCashierTally() 继续添加
	 * @return : getCashierTallyList() 返回列表
	 */
	private void save() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		if (!company.getCompanyPID().startsWith("pcompany")) {
			company = companyserverService.getCompanyByCompanyID(company
					.getCompanyPID());
		}
		cashierBills.setStatus("04");
		cashierBills.setPcompanyID(company.getCompanyID());
		cashierBills.setPcompanyName(company.getCompanyName());
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		parameter = "提交审核收款单（凭证号" + cashierBills.getJournalNum() + "）";
		baseBeanList.add(cashierBills);
		if (goodsmap != null) {
			for (GoodsBills goods : goodsmap.values()) {
				GoodsBills gooBills=null;
				if(goods.getGoodsName()==null||goods.getGoodsName().equals("")){
					continue;
				}
				if(goods.getGoodsBillsID()!=null&&!goods.getGoodsBillsID().equals("")){
					gooBills=(GoodsBills)baseBeanService.getBeanByHqlAndParams("from GoodsBills where goodsBillsID=?", new Object[]{goods.getGoodsBillsID()});
					gooBills.setReasonThing(gooBills.getReasonThing());
					gooBills.setStandard(goods.getStatus());
					gooBills.setQuantity(goods.getQuantity());
					gooBills.setPriceManage(goods.getPriceManage());
					gooBills.setPrice(goods.getPrice());
					gooBills.setMoney(goods.getMoney());
					gooBills.setCcompanyID(goods.getCcompanyID());
					gooBills.setCcompanyName(goods.getCcompanyName());
					gooBills.setConnectID(goods.getConnectID());
					gooBills.setConnectName(goods.getConnectName());
				}else{
					goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
					goods.setEndDate(new Date());
					goods.setCompanyID(account.getCompanyID());
					goods.setCashierBillsID(cashierBills.getCashierBillsID());
				}
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
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
	 * 封装保存的方法
	 * 
	 * @param : cashierTally
	 * @return : toSaveCashierTally() 继续添加
	 * @return : getCashierTallyList() 返回列表
	 */
	private void save1() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		String[] hqls=new String[goodsmap.size()+1];
		List<Object[]> paramList=new ArrayList<Object[]>();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		
		String chql = "update CashierBills set status =?,appstyle=? where cashierBillsID = ? ";
		hqls[0]=chql;
		paramList.add(new Object[]{"04",cashierBills.getAppstyle(),cashierBills.getCashierBillsID()});		
		parameter = "提交审核收款单（凭证号" + cashierBills.getJournalNum() + "）";
		
		if(cashApplyBills!=null){
			cashierBills =(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
			cashApplyBills.setCashApplyBillsID(serverService.getServerID("cashApply"));
			cashApplyBills.setCashierBillsID(cashierBills.getCashierBillsID());
			cashApplyBills.setCsbID(cashierBills.getProID());
			cashApplyBills.setAppstyle(cashierBills.getAppstyle());
			cashApplyBills.setProjectName(cashierBills.getProjectName());
			cashApplyBills.setXmtypename(cashierBills.getXmtypename());
			cashApplyBills.setJournalNum(cashierBills.getJournalNum());
			baseBeanList.add(cashApplyBills);
		}
		
		if (goodsmap != null) {
			int i=1;
			for (GoodsBills goods : goodsmap.values()) {
				BigDecimal bgm=new BigDecimal(goods.getMoney()); //预算
				BigDecimal bgr=new BigDecimal(goods.getRealMoney());  //本次收款
				BigDecimal bga=new BigDecimal(goods.getAlreadyMoney());  //已收款		
				String hql = "update GoodsBills set rebate =?,rebatePrice=?,realMoney=?,futureMoney=?,startDate=?,price=?,quantity=? where goodsBillsID = ? ";
				Object[] params = { goods.getRebate(), goods.getRebatePrice(),goods.getRealMoney(),String.valueOf(bgm.subtract(bgr).subtract(bga)),goods.getStartDate(),goods.getPrice(),goods.getQuantity(),goods.getGoodsBillsID() };
				hqls[i]=hql;
				paramList.add(params);
				i++;
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		try {
			baseBeanService.executeHqlsByParamsList(baseBeanList,
					hqls, paramList);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//审核
	public String verifyBills(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		//JsonConfig cfg = new JsonConfig();  
		//cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"}); 
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		cashierBills =(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		BillCheck bCheck= saveCheckbill(cashierBills);
		baseBeanService.save(bCheck);
		Map<String, Object> map = new HashMap<String, Object>();
		/*JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));*/
		map.put("staffName", bCheck.getAuditorname());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 添加审核信息
	 * 
	 * @param cashierBills
	 * @return
	 */
	public BillCheck saveCheckbill(CashierBills cashierBills) {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		BillCheck billCheck = new BillCheck();
		billCheck.setCheckid(serverService.getServerID("billcheck"));
		billCheck.setNewBillsID(cashierBills.getCashierBillsID());
		billCheck.setOldBillsID(cashierBills.getCashierBillsID());
		billCheck.setFirstBillsID(cashierBills.getCashierBillsID());
		billCheck.setJournalNum(cashierBills.getJournalNum());
		billCheck.setAuditorcompanyid(cashierBills.getCompanyID());
		billCheck.setAuditorcompanyname(cashierBills.getCompanyName());
		billCheck.setAuditororgID(cashierBills.getDepartmentID());
		billCheck.setAuditororgName(cashierBills.getDataDepotName());
		if(cashierBills.getInputName()!=null&&cashierBills.getInputName().equals("系统生成")){
			billCheck.setInputid(null);
			billCheck.setInputname("系统生成");
		}else{
			billCheck.setInputid(cashierBills.getInputid());
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID=?",
					new Object[] { cashierBills.getInputid() });
			billCheck.setInputname(staff.getStaffName());
		}
		billCheck.setStaffID(cashierBills.getStaffID());
		billCheck.setStaffName(cashierBills.getStaffName());
		billCheck.setBillsType(cashierBills.getBillsType());
		billCheck.setAuditorstatus("02");
		billCheck.setCashierDate(cashierBills.getCashierDate());
		billCheck.setBilltatus("凭证管理主管审核阶段");
		billCheck.setAuditorid(account.getStaffID());
		billCheck.setAuditorname(account.getStaffName());
		billCheck.setAudittime(new Date());
		billCheck.setComments(comments);
		billCheck.setDeptpost(deptpost);
		return billCheck;
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
				hql1,
				new Object[] { account.getCompanyID(),
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

		if (cashierBills != null) {
			String hql2 = "from CashierBills where companyID = ?  and cashierBillsID=?";
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
			if(cashierBills.getBillsType().equals("预入款单")){
				String sql = " SELECT g.goodsbillsid gbid,g.GOODSNUM,g.GOODSNAME,g.STARTDATE,"
						+ " g.GOODSVARIABLEID,g.QUANTITY,g.PRICEMANAGE,g.PRICE,g.MONEY,dp.appropriationMoney,"
						+ " dp.appropriationcompanyID,dp.appropriationcompanyName,dp.appropriationNum,dp.appropriateStatus,"
						+ " dp.ReceivablesID,dp.ReceivablesName,dp.RecropriationNum,g.TARGETSTART,"
						+ " g.TARGETEND,g.TARGETDEPTID,g.TARGETDEPTNAME,g.TARGETSALERID,g.TARGETSALERNAME,"
						+ " g.studentCode,g.studentPeriods,g.schoolopendate,g.CCOMPANYID,g.CCOMPANYNAME,g.CONNECTID,g.CONNECTNAME"
						+ " from dtgoodsbills g "
						+ " left join dtCashApplyBills dp on g.goodsbillsid=dp.goodsbillsid"
						+ " WHERE g.cashierBillsID=?";
				sql += " order  by dp.cashierDate desc nulls last";
				BaseBeanlist = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{cashierBills.getCashierBillsID()});
			}else{
				String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
				BaseBeanlist = baseBeanService.getListBeanByHqlAndParams(hql1, params1);
				
			}
			
			List<BaseBean> bCheckList =baseBeanService.getListBeanByHqlAndParams("from BillCheck where newBillsID=? order by audittime", new Object[]{cashierBills.getCashierBillsID()});
			if(bCheckList.size()>0){
				bcheckMap=new HashMap<String, String>();
				commStr="";
				for (int i = 0; i < bCheckList.size(); i++) {
					BillCheck billCheck=(BillCheck)bCheckList.get(i);
					if(billCheck.getDeptpost()!=null){
						commStr=commStr+billCheck.getAuditorname()+":"+billCheck.getComments()+";";
						bcheckMap.put(billCheck.getDeptpost(), billCheck.getAuditorname());
					}
				}
			}
			
			cashApplyBills=(CashApplyBills)baseBeanService.getBeanByHqlAndParams("from CashApplyBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
			
		}
	}
	
	/**
	 * 封装跳转编辑页面的项目和物品
	 * 
	 * @return : edit
	 */
	@SuppressWarnings("unchecked")
	public String getProductAndGoodsAjax(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "select ppID,parentId,goodsName  from dt_ProductPackaging t connect by nocycle " +
				"prior t.ppID = t.parentId start with t.ppID = ?";
		List<BaseBean> productList = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{cuid});
		List<Object> arrays=new ArrayList<Object>();
		String sql1 = " from GoodsBills g  WHERE g.cashierBillsID=?";
		arrays.add(caid);
		List<BaseBean> goodsList =baseBeanService.getListBeanByHqlAndParams(sql1, arrays.toArray());
		for (int i = 0; i < goodsList.size(); i++) {
			GoodsBills g=(GoodsBills)goodsList.get(i);
			List<BaseBean> goodlist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where planGoodsBillsID=? ", new Object[]{g.getGoodsBillsID()});
			BigDecimal bg1=new BigDecimal("0");
			if(goodlist.size()>0){
				for (int j = 0; j < goodlist.size(); j++) {
					GoodsBills g2=(GoodsBills)goodlist.get(j);
					BigDecimal bg=new BigDecimal((g2.getRealMoney()==null||g2.getRealMoney().equals("")?"0":g2.getRealMoney()));
					bg1=bg1.add(bg);
				}
				((GoodsBills)goodsList.get(i)).setAlreadyMoney(String.valueOf(bg1));
				BigDecimal bgm=new BigDecimal(g.getMoney());
				BigDecimal bgr=new BigDecimal((g.getRealMoney()==null||g.getRealMoney().equals("")?"0":g.getRealMoney()));
				((GoodsBills)goodsList.get(i)).setFutureMoney(String.valueOf(bgm.subtract(bgr).subtract(bg1)));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("productList", productList);
		map.put("goodsList", goodsList);
		try {
			JSONObject oj = JSONObject.fromObject(map, jsonConfig);
			result = oj.toString();
		} catch (Exception e) {
			e.printStackTrace();
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
	public String confirmReceivables() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String sql = "update dtcashierBills set status=? where companyID=? and cashierBillsID=? and status=?";
		String[] sqls = { sql };
		List<Object[]> beams = new ArrayList<Object[]>();
		String[] bb = { "45", account.getCompanyID(),
				cashierBills.getCashierBillsID(), "09" };
		beams.add(bb);
		/*List<BaseBean> beans=new ArrayList<BaseBean>();
		for (CashApplyBills entity : cash) {
			if (entity != null) {
				GoodsBills goodsBills = (GoodsBills) baseBeanService
						.getBeanByHqlAndParams(
								"from GoodsBills where goodsBillsID=?",
								new Object[] { entity.getGoodsBillsID() });					
				goodsBills.setMoney(entity.getAppropriationMoney());
				beans.add(goodsBills);
			}
		}*/
		baseBeanService.executeSqlsByParmsList(null, sqls, beams);
		return "success";
	}
	
	/**
	 * 报表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String reportform(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String organizationID = (String) ActionContext.getContext().getSession().get("organizationID");
		String rsql="";
		String rsql1="";
		List<Object> parms=new ArrayList<Object>();
		if(searchValue!=null&&!searchValue.equals("")){
			rsql+="and "+searchValue;
			
		}
		if (search != null && search.equals("search")) {
			if (goodsCashierBillsVO != null) {
				if(null!=goodsCashierBillsVO.getCompanyID()
						&&!"".equals(goodsCashierBillsVO.getCompanyID())){
					rsql1 += " and c1.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
					rsql += " and g1.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
				}else{
					rsql1 += " and c1.companyid = ?";
					parms.add(account.getCompanyID());
					rsql += " and g1.companyid = ?";
					parms.add(account.getCompanyID());
				}
				if(null!=goodsCashierBillsVO.getOrganizationID()
						&&!"".equals(goodsCashierBillsVO.getOrganizationID())){
					rsql += " and c.organizationid = ?";
					parms.add(goodsCashierBillsVO.getOrganizationID());
				}else{
					rsql += " and c.organizationid = ?";
					parms.add(organizationID);
				}
				if (null != goodsCashierBillsVO.getGoodsID()
						&& !"".equals(goodsCashierBillsVO.getGoodsID())) {
					rsql += " and g.goodsid = ? ";
					parms.add(goodsCashierBillsVO.getGoodsID());
				}
				if (null != goodsCashierBillsVO.getProid()
						&& !"".equals(goodsCashierBillsVO.getProid())) {
					rsql += " and c.proid = ?";
					parms.add(goodsCashierBillsVO.getProid());
				}
				if (sdate != null&& !("").equals(sdate)) {
					rsql += " and g1.startdate < ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));
				}else{
					rsql += " and g1.startdate < ? ";

					parms.add(new Date());
				}
			}
		}else{
			rsql1 += " and c1.companyid = ?";
			parms.add(account.getCompanyID());
			rsql += " and g1.companyid = ?";
			parms.add(account.getCompanyID());
				
				rsql += " and c1.organizationid = ?";
				parms.add(organizationID);
				
				rsql += " and g1.startdate < ? ";

				parms.add(Utilities.getDateFromString(Utilities.getDateString(new Date(), "yyyy-MM-dd") + " 00:00:00:000",
						"yyyy-MM-dd HH:mm:ss:sss"));
		}
		String ssql = "select sum(g1.money)"
				+ " from dtgoodsbills g1"
				+ " left join dtcashierbills c1 on c1.cashierbillsid = g1.cashierbillsid"
				+ rsql1
				+ " where c1.billstype in ('支款单', '费用报销单', '费用出/入库报销单', '退费/货报销单', '采购单')"
				//+ " and c.staffid = ?"
				+ rsql;
		
		List<Object> srMoney=baseBeanService.getListBeanBySqlAndParams(ssql, parms.toArray());
		BigDecimal srjy=new BigDecimal(srMoney.get(0).toString());
		
		String zsql = "select sum(g1.money)"
				+ " from dtgoodsbills g1"
				+ " left join dtcashierbills c1 on c1.cashierbillsid = g1.cashierbillsid"
				+ rsql1
				+ " where c1.billstype in ('收款单', '预入款单')"
				+ rsql;
		
		List<Object> zcMoney=baseBeanService.getListBeanBySqlAndParams(zsql, parms.toArray());
		BigDecimal zcjy=new BigDecimal(zcMoney.get(0).toString());
		BigDecimal szjy=srjy.subtract(zcjy);
		jy=szjy;
		parms=new ArrayList<Object>();
		String rsql2="";
		String rsql3="";
		String rsql4="";
		if (search != null && search.equals("search")) {
			if (goodsCashierBillsVO != null) {
				if(null!=goodsCashierBillsVO.getCompanyID()
						&&!"".equals(goodsCashierBillsVO.getCompanyID())){
					rsql2 += " and c1.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
					rsql3 += " and c1.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
					rsql4 += " and c.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
					rsql4 += " where g.companyid = ?";
					parms.add(goodsCashierBillsVO.getCompanyID());
				}else{
					rsql2 += " and c1.companyid = ?";
					parms.add(account.getCompanyID());
					rsql3 += " and c1.companyid = ?";
					parms.add(account.getCompanyID());
					rsql4 += " and c.companyid = ?";
					parms.add(account.getCompanyID());
					rsql4 += " where g.companyid = ?";
					parms.add(account.getCompanyID());
				}
				if(null!=goodsCashierBillsVO.getOrganizationID()
						&&!"".equals(goodsCashierBillsVO.getOrganizationID())){
					rsql4 += " and c.organizationid = ?";
					parms.add(goodsCashierBillsVO.getOrganizationID());
				}else{
					rsql += " and c.organizationid = ?";
					parms.add(organizationID);
				}
				if (null != goodsCashierBillsVO.getGoodsID()
						&& !"".equals(goodsCashierBillsVO.getGoodsID())) {
					rsql4 += " and g.goodsid = ? ";
					parms.add(goodsCashierBillsVO.getGoodsID());
				}
				if (null != goodsCashierBillsVO.getProid()
						&& !"".equals(goodsCashierBillsVO.getProid())) {
					rsql4 += " and c.proid = ?";
					parms.add(goodsCashierBillsVO.getProid());
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					rsql4 += " and g.startdate between ? and ?";
					parms.add(Utilities.getDateFromString(sdate + " 00:00:00:000",
							"yyyy-MM-dd HH:mm:ss:sss"));
					parms.add(Utilities.getDateFromString(edate + " 23:59:59:999",
							"yyyy-MM-dd HH:mm:ss:sss"));
				}else{
					rsql4 += " and g.startdate between ? and ?";

					parms.add(Utilities.getDateFromString(Utilities.getDateString(new Date(), "yyyy-MM-dd") + " 00:00:00:000",
							"yyyy-MM-dd HH:mm:ss:sss"));
					parms.add(Utilities.getDateFromString(Utilities.getDateString(new Date(), "yyyy-MM-dd") + " 23:59:59:999",
							"yyyy-MM-dd HH:mm:ss:sss"));
				}
			}
		}else{
			rsql2 += " and c1.companyid = ?";
			parms.add(account.getCompanyID());
			rsql3 += " and c1.companyid = ?";
			parms.add(account.getCompanyID());
			rsql4 += " and c.companyid = ?";
			parms.add(account.getCompanyID());
			rsql4 += " where g.companyid = ?";
			parms.add(account.getCompanyID());
			
			rsql4 += " and c.organizationid = ?";
			parms.add(organizationID);
			
			rsql4 += " and g.startdate between ? and ?";

			parms.add(Utilities.getDateFromString(/*Utilities.getDateString(new Date(), "yyyy-MM-dd") +*/ "2015-02-26 00:00:00:000",
					"yyyy-MM-dd HH:mm:ss:sss"));
			parms.add(Utilities.getDateFromString(/*Utilities.getDateString(new Date(), "yyyy-MM-dd") +*/ "2015-03-26 23:59:59:999",
					"yyyy-MM-dd HH:mm:ss:sss"));
	}
		rsql4+=" and c.billstype in";
		if(zz.equals("sy")){
			rsql4+="(?,?)";
			parms.add("收款单");
			parms.add("预入款单");
		}else if(zz.equals("zc")){
			rsql4+="(?,?,?,?,?,?)";
			parms.add("支款单");
			parms.add("费用报销单");
			parms.add("费用出/入库报销单");
			parms.add("退费/货报销单");
			parms.add("采购单");
			parms.add("拨款单");
		}else if(zz.equals("sz")){
			rsql4+="(?,?,?,?,?,?,?,?)";
			parms.add("收款单");
			parms.add("预入款单");
			parms.add("支款单");
			parms.add("费用报销单");
			parms.add("费用出/入库报销单");
			parms.add("退费/货报销单");
			parms.add("采购单");
			parms.add("拨款单");
		}
		
		String sql = "select g.goodsBillsID,c.companyname,c.departmentname,c.projectname,c.xmtypename,c.journalNum,"
				+ " c.staffname,g.Goodsnum,g.goodsname,g.typeid,g.quantity,g.price,g.money,g.ccompanyName,g.connectName,"
				+ " CASE"
				+ " WHEN c.billstype in ('收款单', '预入款单') THEN"
				+ " '借'"
				+ " WHEN c.billstype in ('拨款单', '支款单', '费用报销单', '费用出/入库报销单', '退费/货报销单', '采购单') THEN"
				+ " '贷'"
				+ " end f,"
				+ " (select g1.money"
				+ "     from dtgoodsbills g1"
				+ "     left join dtcashierbills c1 on c1.cashierbillsid = g1.cashierbillsid"
				+ rsql2
				+ " 	where g1.goodsbillsid = g.goodsbillsid"
				+ "  	and c1.billstype in ('收款单', '预入款单')) m1,"
				+ " (select g1.money"
				+ " 	from dtgoodsbills g1"
				+ " 	left join dtcashierbills c1 on c1.cashierbillsid = g1.cashierbillsid"
				+ rsql3
				+ " 	where g1.goodsbillsid = g.goodsbillsid"
				+ " 	and c1.billstype in"
				+ " 	('拨款单','支款单', '费用报销单', '费用出/入库报销单', '退费/货报销单', '采购单')) m2,g.realMoney"
				+ " from dtgoodsbills g"
				+ " left join dtcashierbills c on c.cashierbillsid = g.cashierbillsid"
				+ rsql4;
				if(searchValue!=null&&!searchValue.equals("")){
					sql+="and "+searchValue;
					
				}
		sql+= " order by c.cashierdate";
		
		report=baseBeanService.getListBeanBySqlAndParams(sql,parms.toArray());
		if(report!=null){
			for (Object[] objects : report) {
				int objlen=objects.length;
				BigDecimal sy=new BigDecimal(((objects[16]==null||objects[16].equals(""))?"0":objects[16]).toString());
				BigDecimal zc=new BigDecimal(((objects[17]==null||objects[17].equals(""))?"0":objects[17]).toString());
				szjy=szjy.add(sy).subtract(zc);
				objects[objlen-1]=szjy.toString();
			}
		}
		return "report";
	}
	
	/**
	 * 根据项目分类查询项目列表pageForm
	 */
	@SuppressWarnings("unchecked")
	public String getProjectList(){
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			/*if(type){
				
			}*/			
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			if(account == null){
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("nologin", 1);
				JSONObject obj = JSONObject.fromObject(map1);
				result = obj.toString();
				return "success";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(CCode.class);
			
			dc.addOrder(Order.asc("codeSn"));
			dc.add(Restrictions.eq("groupSn", "scode201410284shpd9x4fa0000000005"));
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			if(parameter!=null){
				dc.add(Restrictions.or(Restrictions.like("codeSn",parameter.trim(),MatchMode.ANYWHERE),Restrictions.like("codeValue",parameter.trim(),MatchMode.ANYWHERE)));
			}		  
			List<BaseBean>  xmlist = baseBeanService.getListByDC(dc);
			
			map.put("xmlist",xmlist);
			
			String sql = "select distinct c.projectname"
					+ " from  dtcashierbills c where c.companyid = ?";
		
			gngid=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
			map.put("gngid",gngid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject obj = JSONObject.fromObject(map,jsonConfig);
			this.result = obj.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String getgoods(){
		try{
			Map<String,Object> map = new HashMap<String, Object>();
				
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			if(account == null){
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("nologin", 1);
				JSONObject obj = JSONObject.fromObject(map1);
				result = obj.toString();
				return "success";
			}
			
			String sql = "select distinct c.goodsNum,c.goodsName"
					+ " from  dtgoodsBills c where c.companyid = ? and c.startDate between ? and ?";
			List<Object> pamers=new ArrayList<Object>();
			pamers.add(account.getCompanyID());
			pamers.add(Utilities.getDateFromString("2015-02-26 00:00:00:000","yyyy-MM-dd HH:mm:ss:sss"));
			pamers.add(Utilities.getDateFromString("2015-03-26 23:59:59:999","yyyy-MM-dd HH:mm:ss:sss"));
			if(parameter!=null&&!parameter.equals("")){
				sql+="and (goodsName like ? or goodsNum like ?)";
				pamers.add("%"+parameter.trim()+"%");
				pamers.add("%"+parameter.trim()+"%");
			}
			gngid=baseBeanService.getListBeanBySqlAndParams(sql,pamers.toArray());
			
			map.put("gngid",gngid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject obj = JSONObject.fromObject(map,jsonConfig);
			this.result = obj.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "success";
	}
	
	

	// /////////////////////////////////明细//////////////////////////////////////////////
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toDetailSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", goodsCashierBillsVO);
		return getBillsDetailList();
	}

	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param : GoodsCashierBillsVO 查询条件
	 * @return ：
	 */

	public List<Object> getDetailList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		String sql = "from GoodsCashierBillsVO t where 1=1";
		List<Object> parms = new ArrayList<Object>();
		if(level.equals("organization")){
			sql+=" and t.companyID = ? and t.organizationID = ?";
			parms.add(account.getCompanyID());
			parms.add((String) session.get("organizationID"));
		}else if(level.equals("company")){
			sql+=" and t.companyID = ?";
			parms.add(account.getCompanyID());
		}

		if (dtype.equals("asset")) {
			// 资产明细//需要将固定资产，资产增加，资产减少3单据类型显示，查询时按类型查询
			sql += " and (t.billsType like ? or billsType like ? and billsType like ?)";
			parms.add("%固定资产单%");
			parms.add("%资产增加单%");
			parms.add("%资产减少单%");
		}
		if (dtype.equals("ysd")) {
			// 应收明细//
			sql += " and t.billsType like ? ";
			parms.add("%应收款单%");

		}
		if (dtype.equals("yfd")) {
			// 应付明细//
			sql += " and t.billsType like ? ";
			parms.add("%应付款单%");

		}
		if (dtype.equals("salary")) {
			// 工资明细//应付，已付
			sql += " and (t.billsType like ? or t.billsType like ?)";
			parms.add("%应付工资单%");
			parms.add("%已付工资单%");

		}

		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session
					.get("tablesearch");
			if (null != goodsCashierBillsVO.getBillsType()
					&& !"".equals(goodsCashierBillsVO.getBillsType())) {
				sql += " and t.billsType like ?";
				parms.add("%" + goodsCashierBillsVO.getBillsType().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getJournalNum()
					&& !"".equals(goodsCashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim()
						+ "%");
			}
			if(level.equals("organization")){
				if (null != goodsCashierBillsVO.getDepartmentID()
						&& !"".equals(goodsCashierBillsVO.getDepartmentID())
						&& !account.getCompanyID().equals(
								goodsCashierBillsVO.getDepartmentID())) {
					sql += " and t.departmentID=?";
					parms.add(goodsCashierBillsVO.getDepartmentID().trim());
				}
			}else if(level.equals("company")){
				if (null != goodsCashierBillsVO.getDepartmentID()
						&& !"".equals(goodsCashierBillsVO.getDepartmentID())) {
					sql += " and (organizationID = ? or departmentID = ? ) ";
					parms.add(goodsCashierBillsVO.getDepartmentID());
					parms.add(goodsCashierBillsVO.getDepartmentID());
				}
			}else if(level.equals("allCompany")){
				if (null != goodsCashierBillsVO.getCompanyID()
						&& !"".equals(goodsCashierBillsVO.getCompanyID())) {
					sql += " and companyID = ? ";
						parms.add(goodsCashierBillsVO.getCompanyID());
				} else {
					if (cids.size() == 1) {
						sql += " and companyID = ? ";
						parms.add(cids.get(0));
					} else {
						sql += " and (pcompanyID = ? or companyID = ? )";
						parms.add(account.getCompanyID());
						parms.add(account.getCompanyID());
					}

				}
				if (null != goodsCashierBillsVO.getDepartmentID()
						&& !"".equals(goodsCashierBillsVO.getDepartmentID())
						&& !goodsCashierBillsVO.getCompanyID().equals(
								goodsCashierBillsVO.getDepartmentID())) {
					sql += " and (organizationID = ? or departmentID = ? ) ";
					parms.add(goodsCashierBillsVO.getDepartmentID());
					parms.add(goodsCashierBillsVO.getDepartmentID());
				}
			}
			if (null != goodsCashierBillsVO.getStaffID()
					&& !"".equals(goodsCashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(goodsCashierBillsVO.getStaffID().trim());
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
			if (null != goodsCashierBillsVO.getContactUserName()
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

		}else{
			if(level.equals("allCompany")){
				if (cids.size() == 1) {
					sql += " and companyID = ? ";
					parms.add(account.getCompanyID());
				} else {
					sql += " and (pcompanyID = ? or companyID= ? ) ";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出物品明细管理列表
	 * 
	 * @param : account organizationID
	 * @return : showexcel
	 */

	public String showDetailExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = this.getDetailList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcel(
				GoodsCashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(sql, parms));
		String decription = "";
		if (dtype.equals("asset")) {
			// 资产明细//需要将固定资产，资产增加，资产减少3单据类型显示，查询时按类型查询
			decription = "资产明细";

		}
		if (dtype.equals("ysd")) {
			// 应收明细//

			decription = "应收账款明细";
		}
		if (dtype.equals("yfd")) {
			decription = "应付账款明细";

		}
		if (dtype.equals("salary")) {
			decription = "工资明细";

		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				decription, account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 
	 * 明细 根据 goodsManage goodsBill CashierBills
	 */
	public String getBillsDetailList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		
		List<Object> list = getDetailList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "detail";
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
		String hql = "select distinct ca.journalnum,ca.projectname" +
				" from dtcashapplybills cp" +
				" left join dtgoodsbills g on cp.goodsbillsid = g.goodsbillsid" +
				" left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid" +
				" where ca.companyid=? and ca.organizationid=? and cp.appropriateStatus='01'";
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
		String sql = "select G.GOODSBILLSID,ca.staffName,g.goodsCoding,g.goodsName,g.typeID,"
				+ " G.QUANTITY, G.PRICE,"
				+ " cp.appropriationmoney,to_char(cp.appropriationdate,'yyyy-MM-dd'),g.goodsID,"
				+ " g.mnemonicCode,g.model,g.standard,g.variableID,g.variable1ID,g.variable2ID,"
				+ " g.variable3ID,g.variable4ID,ca.ctUserName"
				+ " from dtcashapplybills cp"
				+ " left join dtGoodsBillsVO g on cp.goodsbillsid = g.goodsbillsid"
				+ " left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid"
				+ " where ca.journalnum=? and cp.appropriateStatus='01'";
		String sqlcount = " SELECT COUNT(1) from dtcashapplybills cp"
				+ " left join dtgoodsbills g on cp.goodsbillsid = g.goodsbillsid"
				+ " left join dtcashierbills ca on ca.cashierbillsid = g.cashierbillsid"
				+ " where ca.journalnum=? and cp.appropriateStatus='01'"; 
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
			e.printStackTrace();
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

	public List<BaseBean> getBaseBeanlist() {
		return BaseBeanlist;
	}

	public void setBaseBeanlist(List<BaseBean> baseBeanlist) {
		BaseBeanlist = baseBeanlist;
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

	public GoodsBillsVO getGoodsBillsVO() {
		return goodsBillsVO;
	}

	public void setGoodsBillsVO(GoodsBillsVO goodsBillsVO) {
		this.goodsBillsVO = goodsBillsVO;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDeptpost() {
		return deptpost;
	}

	public void setDeptpost(String deptpost) {
		this.deptpost = deptpost;
	}

	public Map<String, String> getBcheckMap() {
		return bcheckMap;
	}

	public void setBcheckMap(Map<String, String> bcheckMap) {
		this.bcheckMap = bcheckMap;
	}

	public CashApplyBills getCashApplyBills() {
		return cashApplyBills;
	}

	public void setCashApplyBills(CashApplyBills cashApplyBills) {
		this.cashApplyBills = cashApplyBills;
	}

	public List<CashApplyBills> getCash() {
		return cash;
	}

	public void setCash(List<CashApplyBills> cash) {
		this.cash = cash;
	}

	public String getCommStr() {
		return commStr;
	}

	public void setCommStr(String commStr) {
		this.commStr = commStr;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getSrandom() {
		return srandom;
	}

	public void setSrandom(String srandom) {
		this.srandom = srandom;
	}

	public List<Object[]> getReport() {
		return report;
	}

	public void setReport(List<Object[]> report) {
		this.report = report;
	}

	public BigDecimal getJy() {
		return jy;
	}

	public void setJy(BigDecimal jy) {
		this.jy = jy;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}
	
	public String getBilltname() {
		return billtname;
	}

	public void setBilltname(String billtname) {
		this.billtname = billtname;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
