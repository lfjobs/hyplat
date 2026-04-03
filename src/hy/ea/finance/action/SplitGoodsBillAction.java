package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class SplitGoodsBillAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CompanyService companyserverService;
	public InputStream excelStream;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private GoodsCashierBillsVO goodsCashierBillsVO;
	private String search;
	private String sdate;
	private String edate;
	private String zz;
	private String sztype;//用于区分是收入预算还是支出预算
	private String level;// 区分总部 allCompany 公司 company 部门 organization 个人staff
	
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 单据类别
	 */
	private List<CCode> billsType;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	/**
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	
	
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
		}else if (zz.equals("09")) {
			tt = "scode20130104uyj3s8t4b50000000002"; // 资金收入支出
			tt1 = "scode20130104uyj3s8t4b50000000006";
			tt2 = "scode20130104uyj3s8t4b50000000003"; 
			tt3 = "scode20130104uyj3s8t4b50000000007";
			tt4 = "scode20131212k63xn9ysqp0000000002";
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
		} else if (zz.equals("09")) {
			billsType = codeService.getCCodeListByPIDs(company, new String[] {
					tt, tt1, tt2, tt3, tt4 });
		}else {
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
	 * 导出物品明细管理列表
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list=new ArrayList<Object>();
		if(level.equals("organization")){
			list = getOProList();
		}else if(level.equals("company")){
			list = getCProList();
		}else if(level.equals("allCompany")){
			list = getAProList();
		}
		String sql = (String) list.get(0);
		//sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcel(GoodsCashierBillsVO
				.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(
				sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出物品明细", account);
		baseBeanService.update(logBook);
		return "showexcel";

	}
	
	/**************************************************************个人************************************************************/
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSSearchGood() {
		ActionContext.getContext().getSession()
				.put("goodssearch", goodsCashierBillsVO);
		return SsearchGood();
	}
	
	/**
	 * 封装查询类表的方法
	 */
	public String SsearchGood() {
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
		List<Object> list = getSProList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, "select count(*) "
						+ hql.substring(hql.indexOf("from")), parms);
		return "listGoods";
	}
	
	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getSProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql += " and t.staffID = ?";
		parms.add(account.getStaffID());
		getbilltype(account.getCompanyID(),null);
		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session.get("goodssearch");
			if (null != goodsCashierBillsVO.getBillsType()
					&& !"".equals(goodsCashierBillsVO.getBillsType())) {
				sql += " and t.billsType = ?";
				parms.add(goodsCashierBillsVO.getBillsType().trim());
			}
			if (null != goodsCashierBillsVO.getJournalNum()
					&& !"".equals(goodsCashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsName()
					&& !"".equals(goodsCashierBillsVO.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsName().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsCoding()
					&& !"".equals(goodsCashierBillsVO.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsCoding().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getCcompanyname()
					&& !"".equals(goodsCashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + goodsCashierBillsVO.getCcompanyname().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getContactUserID()
					&& !"".equals(goodsCashierBillsVO.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + goodsCashierBillsVO.getContactUserName().trim() + "%");
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
	
	/**************************************************************部门************************************************************/
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toOSearchGood() {
		ActionContext.getContext().getSession()
				.put("goodssearch", goodsCashierBillsVO);
		return OsearchGood();
	}
	
	/**
	 * 封装查询类表的方法
	 */
	public String OsearchGood() {
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
		List<Object> list = getOProList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, "select count(*) "
						+ hql.substring(hql.indexOf("from")), parms);
		return "listGoodo";
	}
	
	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getOProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql+=" and t.organizationID=?";
		parms.add(organizationID);
		getbilltype(account.getCompanyID(),null);
		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session.get("goodssearch");
			if (null != goodsCashierBillsVO.getStaffID()
					&& !"".equals(goodsCashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(goodsCashierBillsVO.getStaffID().trim());
			}
			if (null != goodsCashierBillsVO.getBillsType()
					&& !"".equals(goodsCashierBillsVO.getBillsType())) {
				sql += " and t.billsType = ?";
				parms.add(goodsCashierBillsVO.getBillsType().trim());
			}
			if (null != goodsCashierBillsVO.getJournalNum()
					&& !"".equals(goodsCashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsName()
					&& !"".equals(goodsCashierBillsVO.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsName().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsCoding()
					&& !"".equals(goodsCashierBillsVO.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsCoding().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getCcompanyname()
					&& !"".equals(goodsCashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + goodsCashierBillsVO.getCcompanyname().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getContactUserID()
					&& !"".equals(goodsCashierBillsVO.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + goodsCashierBillsVO.getContactUserName().trim() + "%");
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
	
	/**************************************************************公司************************************************************/
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toCSearchGood() {
		ActionContext.getContext().getSession()
				.put("goodssearch", goodsCashierBillsVO);
		return CsearchGood();
	}
	
	/**
	 * 封装查询类表的方法
	 */
	public String CsearchGood() {
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
		List<Object> list = getCProList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, "select count(*) "
						+ hql.substring(hql.indexOf("from")), parms);
		return "listGoodc";
	}
	
	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getCProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		getbilltype(account.getCompanyID(),null);
		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session.get("goodssearch");
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
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsName()
					&& !"".equals(goodsCashierBillsVO.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsName().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsCoding()
					&& !"".equals(goodsCashierBillsVO.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsCoding().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getCcompanyname()
					&& !"".equals(goodsCashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + goodsCashierBillsVO.getCcompanyname().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getContactUserID()
					&& !"".equals(goodsCashierBillsVO.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + goodsCashierBillsVO.getContactUserName().trim() + "%");
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
	
	/**************************************************************集团************************************************************/
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toASearchGood() {
		ActionContext.getContext().getSession()
				.put("goodssearch", goodsCashierBillsVO);
		return AsearchGood();
	}
	
	/**
	 * 封装查询类表的方法
	 */
	public String AsearchGood() {
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
		List<Object> list = getAProList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, "select count(*) "
						+ hql.substring(hql.indexOf("from")), parms);
		return "listGooda";
	}
	
	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getAProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql+=" and t.organizationID=?";
		parms.add(organizationID);
		getbilltype(account.getCompanyID(),null);
		if (search != null && search.equals("search")) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session.get("goodssearch");
			if (null != goodsCashierBillsVO.getCompanyID()
					&& !"".equals(goodsCashierBillsVO.getCompanyID())) {
				sql += " and t.companyID = ? ";
				parms.add(goodsCashierBillsVO.getCompanyID());
			} else {
				if (cids.size() == 1) {
					sql += " and t.companyID = ? ";
					parms.add(cids.get(0));
				} else {
					sql += " and (t.pcompanyID = ? or t.companyID = ? )";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
			}
			if (null != goodsCashierBillsVO.getStaffID()
					&& !"".equals(goodsCashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(goodsCashierBillsVO.getStaffID().trim());
			}
			if (null != goodsCashierBillsVO.getDepartmentID()
					&& !"".equals(goodsCashierBillsVO.getDepartmentID())) {
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
				parms.add("%" + goodsCashierBillsVO.getJournalNum().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsName()
					&& !"".equals(goodsCashierBillsVO.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsName().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getGoodsCoding()
					&& !"".equals(goodsCashierBillsVO.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + goodsCashierBillsVO.getGoodsCoding().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getCcompanyname()
					&& !"".equals(goodsCashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + goodsCashierBillsVO.getCcompanyname().trim() + "%");
			}
			if (null != goodsCashierBillsVO.getContactUserID()
					&& !"".equals(goodsCashierBillsVO.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + goodsCashierBillsVO.getContactUserName().trim() + "%");
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
			if (cids.size() == 1) {
				sql += " and t.companyID = ? ";
				parms.add(cids.get(0));
			} else {
				sql += " and (t.pcompanyID = ? or t.companyID = ? )";
				parms.add(account.getCompanyID());
				parms.add(account.getCompanyID());
			}
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

	public GoodsCashierBillsVO getGoodsCashierBillsVO() {
		return goodsCashierBillsVO;
	}

	public void setGoodsCashierBillsVO(GoodsCashierBillsVO goodsCashierBillsVO) {
		this.goodsCashierBillsVO = goodsCashierBillsVO;
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

	public String getZz() {
		return zz;
	}

	public void setZz(String zz) {
		this.zz = zz;
	}

	public String getSztype() {
		return sztype;
	}

	public void setSztype(String sztype) {
		this.sztype = sztype;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public List<CCode> getBillsType() {
		return billsType;
	}

	public void setBillsType(List<CCode> billsType) {
		this.billsType = billsType;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
