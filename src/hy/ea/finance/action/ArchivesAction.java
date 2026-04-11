package hy.ea.finance.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.driving.DrivingDeal;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingPrincipalType;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.projectBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.history.HistoryCashierBills;
import hy.ea.bo.history.HistoryGoodsBills;
import hy.ea.bo.history.HistoryRelation;
import hy.ea.bo.history.vo.HistoryCashierBillVO;
import hy.ea.bo.history.vo.HistoryGoodsBillVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetDetail;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
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
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * ArchivesAction 单据归档
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class ArchivesAction {
	private static final Logger logger = LoggerFactory.getLogger(ArchivesAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private String result;
	private CashierBillsVO cashierBillsVO;
	private HistoryCashierBillVO historyVO;
	private HistoryCashierBills historyCashierBills;
	private HistoryRelation historyRelation;
	private Map<String, CashierBills> cashierBillsmap;
	private List<BaseBean> CashierBillslists;
	private PageForm pageForm;
	private int pageNumber;
	private String companyname;
	private String organizationname;
	private String search;
	private String parameter;
	private String tt;
	private Map<String, HistoryGoodsBills> goodsmap;
	private Map<String, HistoryCashierBillVO> cashierBillsmaps;
	private String nuDate; // 部门查询时间
	private String nuvDate; //公司查询时间
	
	/**
	 * 页面查询制单日期
	 */
	private String sdate;
	private String edate;
	private String sfdate;
	private String efdate;
	private String str;
	private String billid;
	private String comments;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
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
	private List<Object[]> objectny;
	/**
	 * 用于生成报表判断打印
	 * ct
	 */
	private String pan;
	private String chuanzhi;
	
	/**
	 * 单据类别
	 */
	private List<CCode> billsTypeList;
	
	
	/**
	 * 出纳记账列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierBillsList() {
		ActionContext.getContext().getSession().put("cashierBillsVO",
				cashierBillsVO);
		search();
		return "listc";
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
		List<Object> list = getListc();
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

	private List<Object> getListc() {
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
		sql += " and t.status = ? ";
		parms.add("07");
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("cashierBillsVO");
			if (cashierBillsVO != null) {
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
				if (null != cashierBillsVO.getDepartmentID().trim()
						&& !"".equals(cashierBillsVO.getDepartmentID().trim())) {
					sql += " and t.departmentID = ?";
					parms.add(cashierBillsVO.getDepartmentID().trim());
				}
				if (null != cashierBillsVO.getStaffID().trim()
						&& !"".equals(cashierBillsVO.getStaffID().trim())) {
					sql += " and t.staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
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
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * (报表)根据条件查询(调用)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public List<Object> getStatementsList(){
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> parms = new ArrayList<Object>();
		String hql = "from HistoryGoodsBillVO t where t.companyID=?";
		parms.add(account.getCompanyID());
		if(tt.equals("z")){
			hql += " and t.organizationID = ?";
			parms.add(organizationID);
		}
		//入账日期
		if (sfdate != null && efdate != null && !("").equals(sfdate)&& !("").equals(efdate)) {
			hql += " and t.endDate between ? and ? ";
			String ss=sfdate + " 00:00:00";
			String ee=efdate + " 23:59:59";
			parms.add(Utilities.getDateFromString(ss,"yyyy-MM-dd hh:mm:ss"));
			parms.add(Utilities.getDateFromString(ee,"yyyy-MM-dd hh:mm:ss"));
		}
		//单据类别
		if (null != historyVO.getBillsType()
				&& !"".equals(historyVO.getBillsType())) {
			hql += " and t.billsType = ?";
			parms.add(historyVO.getBillsType().trim());
		}
		//部门
		if (null != historyVO.getDepartmentID()
				&& !"".equals(historyVO.getDepartmentID())
				&& !account.getCompanyID().equals(
						historyVO.getDepartmentID())) {
			hql += " and t.departmentID = ?";
			parms.add(historyVO.getDepartmentID().trim());
		}
		//责任人
		if (null != historyVO.getStaffID()
				&& !"".equals(historyVO.getStaffID())) {
			hql += " and t.staffID = ?";
			parms.add(historyVO.getStaffID().trim());
		}
		//单位往来关系
		if (null != historyVO.getContactConnections()
				&& !"".equals(historyVO.getContactConnections())) {
			hql += " and t.contactConnections = ? ";
			parms.add(historyVO.getContactConnections().trim());
		}
		//个人往来关系
		if (null != historyVO.getPhone()
				&& !"".equals(historyVO.getPhone())) {
			hql += " and t.phone = ?";
			parms.add(historyVO.getPhone().trim());
		}
		//往来单位
		if (null != historyVO.getCcompanyname()
				&& !"".equals(historyVO.getCcompanyname())) {
			hql += " and t.ccompanyname like ? ";
			parms.add("%" + historyVO.getCcompanyname().trim() + "%");
		}
		//往来个人
		if (null != historyVO.getContactUserName()
				&& !"".equals(historyVO.getContactUserName())) {
			hql += " and t.contactUserName like ? ";
			parms.add("%" + historyVO.getContactUserName().trim() + "%");
		}
		chuanzhi="&historyVO.billsType="+historyVO.getBillsType().trim()+"&historyVO.departmentID="+historyVO.getDepartmentID().trim()+
				"&historyVO.ccompanyname="+historyVO.getCcompanyname().trim()+"&historyVO.contactUserName="+historyVO.getContactUserName().trim()+
				"&historyVO.staffID="+historyVO.getStaffID().trim()+"&historyVO.contactConnections="+historyVO.getContactConnections().trim()+
				"&historyVO.phone="+historyVO.getPhone().trim();
		hql+=" order by t.archivesDate,t.archivesNum";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 驳回保存审核信息
	 * @return
	 */ 
	public String SaveOrUpdateCheckbill(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		List<BaseBean> beans=new ArrayList<BaseBean>();
		if(str!=null&&!"".equals(str)){
			String []strs=str.split(",");
			for (int i = 0; i < strs.length; i++) {
				String cashid="";
				CashierBills bills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{strs[i]});
				CashierBills cBills=null;
				try {
					cBills = (CashierBills)bills.cloneCashierBills();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
				cBills.setCashierBillsID(serverService.getServerID("cashierTally"));
				cashid=cBills.getCashierBillsID();
				cBills.setCashierBillsKey(null);
				cBills.setStatus("11");
				beans.add(cBills);
				bills.setStatus("10");
				beans.add(bills);
				List<BaseBean> goodlist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object []{bills.getCashierBillsID() });
				for (int j = 0; j < goodlist.size(); j++) {
					GoodsBills goodsBills=(GoodsBills)goodlist.get(j);
					GoodsBills goodsBills2=null;
					try {
						goodsBills2=(GoodsBills)goodsBills.cloneGoodsBills();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						logger.error("操作异常", e);
					}
					goodsBills2.setCashierBillsID(cBills.getCashierBillsID());
					goodsBills2.setGoodsBillsID(serverService.getServerID("goodsbills"));
					goodsBills2.setGoodsBillsKey(null);
					beans.add(goodsBills2);
				}
				BillCheck billCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=? order by audittime desc", new Object[]{strs[i]});
				BillCheck billCheck2=null;
				if(billCheck!=null){
					try {
					billCheck2=(BillCheck)billCheck.cloneBillCheck();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						logger.error("操作异常", e);
					}
					billCheck2.setCheckid(serverService.getServerID("billcheck"));
					billCheck2.setCheckkey(null);
				}else{
					billCheck2=saveCheckbill(bills,cashid);
				}
				billCheck2.setAuditorstatus("10");
				billCheck2.setAuditorid(sta.getStaffID());
				billCheck2.setAuditorname(sta.getStaffName());
				billCheck2.setAudittime(new Date());
				billCheck2.setComments(comments);
				billCheck2.setBilltatus("归档管理阶段");
				BillCheck billCheck3=null;
				try {
					billCheck3=(BillCheck)billCheck2.cloneBillCheck();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
				billCheck3.setCheckid(serverService.getServerID("billcheck"));
				billCheck3.setCheckkey(null);
				billCheck3.setOldBillsID(bills.getCashierBillsID());
				billCheck2.setAuditorstatus("01");
				if(billCheck3.getBilltatus().substring(0, 4).equals("凭证管理")){
					billCheck3.setBilltatus(billCheck2.getBilltatus().substring(0, 4)+"主管审核阶段");
				}else{
					billCheck3.setBilltatus(billCheck2.getBilltatus().substring(0, 4)+"招标前审核阶段");
				}
				if(!cashid.equals("")){
					billCheck3.setNewBillsID(cashid);
				}
				beans.add(billCheck2);

				beans.add(billCheck3);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					null,null);
		}
		
		return "success";
	}
	
	/**
	 * 添加审核信息
	 * @param cashierBills
	 * @return
	 */
	public BillCheck saveCheckbill(CashierBills cashierBills,String cashid){
		BillCheck billCheck=new BillCheck();
		billCheck.setCheckid(serverService.getServerID("billcheck"));
		billCheck.setNewBillsID(cashid);
		billCheck.setOldBillsID(cashierBills.getCashierBillsID());
		billCheck.setFirstBillsID(cashierBills.getCashierBillsID());
		billCheck.setJournalNum(cashierBills.getJournalNum());
		billCheck.setAuditorcompanyid(cashierBills.getCompanyID());
		billCheck.setAuditorcompanyname(cashierBills.getCompanyName());
		billCheck.setAuditororgID(cashierBills.getDepartmentID());
		billCheck.setAuditororgName(cashierBills.getDataDepotName());
		if(cashierBills.getInputid()!=null){
			billCheck.setInputid(cashierBills.getInputid());
			Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cashierBills.getInputid()});
			billCheck.setInputname(staff.getStaffName());
		}
		billCheck.setStaffID(cashierBills.getStaffID());
		billCheck.setStaffName(cashierBills.getStaffName());
		billCheck.setBillsType(cashierBills.getBillsType());
		billCheck.setCashierDate(cashierBills.getCashierDate());
		return billCheck;
	}
	
	/**
	 * JSON查询审核信息
	 * @return
	 */
	public String AjxaGetCheckbill(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql="from BillCheck where newBillsID=?";
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{str.substring(0,40)});
		Map<String, Object> map=new HashMap<String, Object>();
		if(bCheck!=null){
			String hql2="from BillCheck where firstBillsID=? order by audittime desc";
			List<BaseBean> checkList=baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{bCheck.getFirstBillsID()});
			map.put("checkList", checkList);
		}else{
			map.put("checkList", null);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		result = obj.toString();
		return "success";
	}

	/**
	 * 单据归档列表查询
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getArchivesList() {
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
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
		//生成图表数据
		invprint();
		if(tt.equals("x")){
			return "list";
		}else if(tt.equals("z")){
			return "list1";
		}
		return null;
	}
	
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("historyVO",
				historyVO);
		ActionContext.getContext().getSession().put("historyRelation",
				historyRelation);
		return getArchivesList();
	}
	
	
	
	/**
	 * 根据条件查询(调用)
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		String organizationID =(String)ActionContext.getContext().getSession().get("organizationID");
		String hql = " from HistoryCashierBillVO t";
		// 用来保存参数
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		hql += " where t.companyID = ? ";
		parms.add(account.getCompanyID().trim());
		if(tt.equals("z")){
			hql += "  and t.organizationID = ?";
			parms.add(organizationID);
		}
		if (search != null && search.equals("search")) {
			historyVO = (HistoryCashierBillVO) session.get("historyVO");
			historyRelation = (HistoryRelation) session.get("historyRelation");
			if (null != historyVO.getStaffID()
					&& !"".equals(historyVO.getStaffID())) {
				hql += " and t.staffID = ?";
				parms.add(historyVO.getStaffID().trim());
			}
			if (null != historyVO.getDepartmentID()
					&& !"".equals(historyVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							historyVO.getDepartmentID())) {
				hql += " and t.departmentID = ?";
				parms.add(historyVO.getDepartmentID().trim());
			}
			if (null != historyVO.getBillsType()
					&& !"".equals(historyVO.getBillsType())) {
				hql += " and t.billsType = ?";
				parms.add(historyVO.getBillsType().trim());
			}
			if (null != historyVO.getJournalNum()
					&& !"".equals(historyVO.getJournalNum())) {
				hql += " and t.journalNum like ? ";
				parms.add("%" + historyVO.getJournalNum().trim() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				hql += " and t.cashierDate between ? and ? ";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != historyVO.getContactConnections()
					&& !"".equals(historyVO.getContactConnections())) {
				hql += " and t.contactConnections = ? ";
				parms.add(historyVO.getContactConnections().trim());
			}
			if (null != historyVO.getPhone()
					&& !"".equals(historyVO.getPhone())) {
				hql += " and t.phone = ?";
				parms.add(historyVO.getPhone().trim());
			}
			if (null != historyVO.getCcompanyname()
					&& !"".equals(historyVO.getCcompanyname())) {
				hql += " and t.ccompanyname like ? ";
				parms.add("%" + historyVO.getCcompanyname().trim() + "%");
			}
			if (null != historyVO.getContactUserName()
					&& !"".equals(historyVO.getContactUserName())) {
				hql += " and t.contactUserName like ? ";
				parms.add("%" + historyVO.getContactUserName().trim() + "%");
			}
			// 关联表
			if (null != historyRelation.getDeparchivesNum()
					&& !"".equals(historyRelation.getDeparchivesNum())) {
				hql += " and t.deparchivesNum like ? ";
				parms.add("%" + historyRelation.getDeparchivesNum().trim() + "%");
			}
			if (null != nuDate && !"".equals(nuDate)) {
				hql += " and t.deparchivesDate between ? and ? ";
				parms.add(Utilities.getDateFromString(nuDate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(nuDate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != historyRelation.getArchivesNum()
					&& !"".equals(historyRelation.getArchivesNum())) {
				hql += " and t.archivesNum like ? ";
				parms.add("%" + historyRelation.getArchivesNum().trim() + "%");
			}
			if (null != nuvDate && !"".equals(nuvDate)) {
				hql += " and t.archivesDate between ? and ? ";
				parms.add(Utilities.getDateFromString(nuvDate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(nuvDate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
		}
		hql += " order by t.cashierDate desc";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 单据归档生成报表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String toStatements(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID =(String)ActionContext.getContext().getSession().get("organizationID");
		List<Object> list = getStatementsList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		CashierBillslists=baseBeanService.getListBeanByHqlAndParams(sql, parms);
		//公司
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { account.getCompanyID() });
		if(tt.equals("z")){
			String orghql = "from COrganization where companyID=? and organizationID=?";
			COrganization cOrganization = (COrganization) baseBeanService
					.getBeanByHqlAndParams(orghql, new Object[] {account.getCompanyID(),organizationID});
			organizationname = cOrganization.getOrganizationName();
		}
		companyname = company.getCompanyName();
		return "tostatements";
	}
	/**
	 * 添加导出功能 
	 */
	@SuppressWarnings("unused")
	public String toshow(){
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getStatementsList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcels(HistoryGoodsBillVO.columnHeading(),
				baseBeanService.getListBeanByHqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳审核管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 
	 * 根据 HistoryCashierBills  处理    HistoryRelation           历史数据设置归档号
	 * 
	 * URL：http://localhost:8080/hyplat/ea/archivest/sajax_ea_updateHistoryRelation.jspa
	 */
	@SuppressWarnings("unchecked")
	public String updateHistoryRelation(){
		/**
		 * 
		 * 测试更新时间
		 */
		long startTime=System.currentTimeMillis();
		long endTime;
		logger.info("----------------开始-----------");	
		int count=0;
		/**
		 * 标识
		 */
		boolean result1=false;
		/**
		 * 删除归档信息
		 */
		String hqlDelete=" delete from HistoryRelation";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlDelete}, null);
		/**
		 * 日期，公司，部门排序
		 */
		String  sqlOrg="select  to_char(h.cashierdate,'yyyy-MM'),h.companyid,h.organizationid from dt_history_cashierbills h "+
					"group by to_char(h.cashierdate,'yyyy-MM'),h.companyid,h.organizationid  order by to_char(h.cashierdate,'yyyy-MM')  desc";
		List<BaseBean>  listOrg=baseBeanService.getListBeanBySqlAndParams(sqlOrg, null);
		List<BaseBean>  baseBeansList=new ArrayList<BaseBean>();
		Map<String, BaseBean>  beans=new HashMap<String, BaseBean>(); 
		if(listOrg!=null&&listOrg.size()>0){
			for (int i = 0; i < listOrg.size(); i++) {
				Object  obj=(Object)listOrg.get(i);
				Object[]  objs=(Object[])obj;
				String hql=" from HistoryCashierBills where  to_char(cashierdate,'yyyy-MM')=? and companyID=? and organizationID=? order by cashierdate asc ";
				List<BaseBean>  listOrg1=baseBeanService.getListBeanByHqlAndParams(hql, objs);
				if(listOrg1!=null&&listOrg1.size()>0){
					for (int j = 0; j < listOrg1.size(); j++) {
						HistoryCashierBills  historyCashierBills=(HistoryCashierBills)listOrg1.get(j);
						HistoryRelation historyRelation=new HistoryRelation();
						historyRelation.setID(serverService.getServerID("historyRelation"));
						historyRelation.setCashierBillsID(historyCashierBills.getCashierBillsID());
						historyRelation.setDeparchivesNum(String.valueOf(j+1));
						beans.put(historyCashierBills.getCashierBillsID(), historyRelation);
						count++;
						logger.info("调试信息");
					}	
				}
				
			}
		}
		logger.info("-------------------------------------------------------部门分组循环结束---------------------------------------------------");
		/**
		 * 日期，公司排序
		 */
		String  sqlCom="select  to_char(h.cashierdate,'yyyy-MM'),h.companyid from dt_history_cashierbills h "+
				"group by to_char(h.cashierdate,'yyyy-MM'),h.companyid  order by to_char(h.cashierdate,'yyyy-MM')  desc";
		List<BaseBean>  listCom=baseBeanService.getListBeanBySqlAndParams(sqlCom, null);
		if(listCom!=null&&listCom.size()>0){
			for (int i = 0; i < listCom.size(); i++) {
				Object  obj=(Object)listCom.get(i);
				Object[]  objs=(Object[])obj;
				String hql=" from HistoryCashierBills where  to_char(cashierdate,'yyyy-MM')=? and companyID=?  order by cashierdate asc ";
				List<BaseBean>  listCom1=baseBeanService.getListBeanByHqlAndParams(hql, objs);
				if(listCom1!=null&&listCom1.size()>0){
					for (int j = 0; j < listCom1.size(); j++) {
						HistoryCashierBills  historyCashierBills=(HistoryCashierBills)listCom1.get(j);
						HistoryRelation historyRelation=(HistoryRelation) beans.get(historyCashierBills.getCashierBillsID());
						historyRelation.setArchivesNum(String.valueOf(j+1));
						baseBeansList.add(historyRelation);
						count++;
						logger.info("调试信息");
					}	
				}
				
			}
			result1=true;
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		endTime=System.currentTimeMillis();
		logger.info("----------------结束-----------");	
		logger.info("调试信息");
		JSONObject  jsonArray=new JSONObject();
		jsonArray.accumulate("result", result1);
		result = jsonArray.toString();//ea/archivest/sajax_ea_updateHistoryRelation.jspa
		return "success";
	}
	
	
	/**
	 * 
	 * 根据 CashierBills，GoodsBillsVO  处理    DtDrivingAllInformation，DrivingDeal，DtDrivingPrincipal，DtDrivingPrincipalType           历史数据设置归档号
	 * 
	 * URL：http://localhost:8080/hyplat/ea/archivest/sajax_ea_updateHistoryDocumentAndStudent.jspa
	 */

	public String updateHistoryDocumentAndStudent(){
		/** 
		 * 处理的公司或者驾校 公司id  学员数量  公司名称
		 *  1	company201009046vxdyzy4wg0000000015	17385	资阳市胜威驾培有限公司
		    2	company201007133na5ggn2u30000000001	1	天太胜威集团总部
			3	company201009046vxdyzy4wg0000000140	10640	绵阳市胜威驾培有限公司
			4	company20101104xagk65jvi20000000034	284	四川省胜威特种作业培训学校
			5	company201009046vxdyzy4wg0000000055	1	孵龙国际教育集团成都公司
			6	company201009046vxdyzy4wg0000000025	10	北京天太世统科技有限责任公司
			7	company201009046vxdyzy4wg0000000130	8220	四川省胜威驾校有限公司
			8	company20120119IQIVNUWPNY0000001221	40	壹点红利餐饮资阳有限公司
		*/
		/**
		 * 
		 * 测试更新时间
		 */
		long startTime=System.currentTimeMillis();
		long endTime;
		logger.info("----------------开始-----------");	
		int count=0;
		int count1=0;
		/**
		 * 标识
		 */
		boolean result1=false;
		
		/******************* 新增教务 科一收集学员信息 ****************/ 
		List<BaseBean>  beans = new ArrayList<BaseBean>();
		
		//----开始--学员所缴金额总数------//
		StringBuffer  sql= new StringBuffer();
		sql.append(" select ");
		sql.append(" max(gc.companyID) companyID, ");
		sql.append(" gc.contactUserID contactUserID, ");
		sql.append(" max(decode(substr(gc.staffIdentityCard,17,1),'1','男','2','女') ) sex, ");
		sql.append(" max(gc.ctUserName) ctUserName, ");
		sql.append(" max(gc.reference) reference, ");
		sql.append(" max(gc.staffIdentityCard) staffIdentityCard, ");
		sql.append(" max(gc.staffID) staffID ");
		sql.append(" from dtcashierbills gc ");
		sql.append(" where ");
		sql.append(" gc.phone like '%学员%' and gc.contactUserID is not null and not exists (select 1 from DT_DRIVING_DEAL dd where dd.staffid=gc.contactUserID) ");
		sql.append(" and gc.companyID=? ");
		sql.append(" group by gc.contactUserID ");
		List  beansGoodsBills=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{"company201009046vxdyzy4wg0000000130"});//手动输入公司ID---------------------
		StringBuffer  sqlgoods= new StringBuffer();
		sqlgoods.append(" select ");
		sqlgoods.append(" gs.goodsid goodsid, ");
		sqlgoods.append(" gm.goodsname goodsname, ");
		sqlgoods.append(" gs.money money , ");
		sqlgoods.append(" gc.cashierdate cashierdate, ");
		sqlgoods.append(" gc.contactuserid contactuserid  ");
		sqlgoods.append(" from   dtgoodsbills gs ");
		sqlgoods.append(" left join dtcashierbills gc  on gs.cashierbillsid=gc.cashierbillsid ");
		sqlgoods.append(" left join dtgoodsmanage gm on gm.goodsid=gs.goodsid ");
		sqlgoods.append(" where ");
		sqlgoods.append(" gc.phone like '%学员%' ");
		sqlgoods.append(" and gc.companyID=? ");
		sqlgoods.append(" and gc.contactuserid is not null ");
		List   beansGoods=baseBeanService.getListBeanBySqlAndParams(sqlgoods.toString(), new Object[]{"company201009046vxdyzy4wg0000000015"});//手动输入公司ID---------------------
		HashMap<Object, ArrayList<Object[]>>  ws=new HashMap<Object, ArrayList<Object[]>>();
		for (Object object : beansGoods) {
			Object[]  objsGoods=(Object[])object;
			if(ws.containsKey(objsGoods[4])){
				ws.get(objsGoods[4]).add(new Object[]{objsGoods[0],objsGoods[1],objsGoods[2],objsGoods[3]});
			}else{
				ArrayList<Object[]> ws1=new ArrayList<Object[]>();
				ws1.add(new Object[]{objsGoods[0],objsGoods[1],objsGoods[2],objsGoods[3]});
				ws.put(objsGoods[4], ws1);
			}
		}
		//-------开始---------//
		if(beansGoodsBills!=null){
			for (Object obj : beansGoodsBills) {
				count++;
				logger.info("调试信息");
				Object[]  objs=(Object[])obj;
				double sumMoney = 0;// 初始化当前金额


				int count0=0;
				if(ws.containsKey(objs[1])&&ws.get(objs[1])!=null&&ws.get(objs[1]).size()>0){
					for (Object[] objsGoods : ws.get(objs[1])) {
						count0++;
						logger.info("调试信息");
						
						
						DtDrivingAllInformation dtDrivingAllInformation=new DtDrivingAllInformation();	
						dtDrivingAllInformation.setDrivingAllInformationID(serverService
							.getServerID("dtDrivAllInform"));
						dtDrivingAllInformation.setCompanyID(objs[0].toString());
						dtDrivingAllInformation.setStaffID(objs[1].toString());
						dtDrivingAllInformation.setCodeID(objsGoods[0]!=null?objsGoods[0].toString():"");
						dtDrivingAllInformation.setCodeValue(objsGoods[1]!=null?objsGoods[1].toString():"");
						dtDrivingAllInformation.setChargeName(objsGoods[1]!=null?objsGoods[1].toString():"");
						try {
							dtDrivingAllInformation.setChargeMoney(Double.parseDouble(objsGoods[2]!=null?objsGoods[2].toString():""));
						} catch (NumberFormatException e) {
							dtDrivingAllInformation.setChargeMoney(0);
						}
						try {
							dtDrivingAllInformation.setChargeTime(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", objsGoods[3]!=null?objsGoods[3].toString():""));
						} catch (RuntimeException e) {
							dtDrivingAllInformation.setChargeTime(new Date());
						}
						dtDrivingAllInformation.setDataTitle("05");
						sumMoney+=dtDrivingAllInformation.getChargeMoney();//累计当前金额
						beans.add(dtDrivingAllInformation);
					}
				}
				count1+=count0;
				logger.info("调试信息");
				//--开始--根据学员缴费金额判断 成交与预定客户类型  金额大于100小于2000为预定客户  大于等于2000为成交客户---//
				DrivingDeal  beanDrivingDeal=new DrivingDeal();
				beanDrivingDeal.setDrivingDealid(serverService
						.getServerID("DrivingDeal"));
				beanDrivingDeal.setCompanyid(objs[0].toString());
				beanDrivingDeal.setStaffID(objs[1].toString());
					
				if(sumMoney>0&&sumMoney<200){   //是否是预定还是成交
						beanDrivingDeal.setStates("00");
				}else if(sumMoney>=200){
						beanDrivingDeal.setStates("01");
				}
				beans.add(beanDrivingDeal);
				//--开始--根据是否为成交客户   同步生成学员培训信息表数据---//
				if("01".equals(beanDrivingDeal.getStates())){
					/**
					 * 收集-学员主表信息
					 */
					DtDrivingPrincipal dtDrivingPrincipal = new DtDrivingPrincipal();
					dtDrivingPrincipal.setDrivingprincipalid(serverService
							.getServerID("dtDrivingPrincipal"));
					dtDrivingPrincipal.setCompanyid(objs[0].toString());
					dtDrivingPrincipal.setCompanygroupid("ceshi");
					dtDrivingPrincipal.setResponsiblePersonsID(objs[6]!=null?objs[6].toString():"");
					
					dtDrivingPrincipal.setRegistrationdate(new Date());//报名时间
					dtDrivingPrincipal.setOperationdate(new Date());//操作时间
					dtDrivingPrincipal.setResgistrationmedicaldate(new Date());//体检时间
					
					dtDrivingPrincipal.setStudentid(objs[1].toString());
					dtDrivingPrincipal.setStudentname(objs[3]!=null?objs[3].toString():"");
					dtDrivingPrincipal.setStudentsex(objs[2]!=null?objs[2].toString():"");
					dtDrivingPrincipal.setStudentphone(objs[4]!=null?objs[4].toString():"");
					dtDrivingPrincipal.setStudentcard(objs[5]!=null?objs[5].toString():"");
					/**
					 * 
					 * 科目 一二三四  关系表
					 */
					DtDrivingPrincipalType dtDringPrialType = new DtDrivingPrincipalType();
					dtDringPrialType.setPrincipaltypeid(serverService
							.getServerID("principaltype"));
					dtDringPrialType.setDrivingprincipalid(dtDrivingPrincipal
							.getDrivingprincipalid());
					dtDringPrialType.setCompanyid(objs[0].toString());
					dtDringPrialType.setTestsum("0");
					dtDringPrialType.setDocstatus("01");
					dtDringPrialType.setStudentstatus("04");
					beans.add(dtDringPrialType);
					/**
					 * 保存beans
					 */
					beans.add(dtDrivingPrincipal);
				}
			}
			
			//--结束--根据根据是否为成交客户    同步生成学员培训信息表数据---//
		}
		//-------结束---------//
		logger.info("调试信息");
		logger.info("调试信息");
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		endTime=System.currentTimeMillis();
		logger.info("----------------结束-----------");	
		logger.info("调试信息");
		JSONObject  jsonArray=new JSONObject();
		jsonArray.accumulate("result", result1);
		result = jsonArray.toString();//ea/archivest/sajax_ea_updateHistoryRelation.jspa
		return "success";
	}
	 /**
	 *
	 * 查看
	 * 
	 * @return
	 */
	public String toCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		//单据类别
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
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
		//物品类别
		codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101014v5zed7cukk0000000002");
		if (historyCashierBills != null) {
			Object[] params1 = { companyID, historyCashierBills.getCashierBillsID() };
			String hql1 = "from HistoryGoodsBillVO where companyID = ? and cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from HistoryCashierBillVO where companyID = ?  and cashierBillsID=?";
			historyVO = (HistoryCashierBillVO) baseBeanService.getBeanByHqlAndParams(hql, params1);
			String hql2="from HistoryCashierBills where companyID = ?  and cashierBillsID=?";
			historyCashierBills=(HistoryCashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
		if(tt.equals("x")){
			return "edit";
		}else if(tt.equals("z")){
			return "edit1";
		}
		return null;
	}
	
	
	/**
	 * 封装保存的方法
	 * 
	 */
	public String saveArchivesBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		parameter = "修改归档单据（凭证号" + historyCashierBills.getJournalNum() + "）";
		HistoryCashierBills hisBills=(HistoryCashierBills)baseBeanService.getBeanByHqlAndParams("from HistoryCashierBills where companyID = ?  and cashierBillsID=?", new Object[]{account.getCompanyID(),historyCashierBills.getCashierBillsID()});
		historyCashierBills.setCompanyID(account.getCompanyID());
		historyCashierBills.setOrganizationID(organizationID);
		historyCashierBills.setBillsType(hisBills.getBillsType());
		historyCashierBills.setCashierDate(new Date());
		historyCashierBills.setResponsible(hisBills.getResponsible());
		historyCashierBills.setAccountant(hisBills.getAccountant());
		historyCashierBills.setCashier(hisBills.getCashier());
		historyCashierBills.setInput(hisBills.getInput());
		historyCashierBills.setCompetent(hisBills.getCompetent());
		historyCashierBills.setManager(hisBills.getManager());
		historyCashierBills.setFinancial(hisBills.getFinancial());
		historyCashierBills.setContactConnections(historyVO.getContactConnections());
		historyCashierBills.setPhone(historyVO.getPhone());
		historyCashierBills.setTaxDate(hisBills.getTaxDate());
		historyCashierBills.setStatus(hisBills.getStatus());
		historyCashierBills.setTaxstatus(hisBills.getTaxstatus());
		historyCashierBills.setTaxstatus2(hisBills.getTaxstatus2());
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		if (company.getCompanyPID().startsWith("pcompany")) {
			historyCashierBills.setPcompanyID(company.getCompanyID());
		} else {
			historyCashierBills.setPcompanyID(company.getCompanyPID());
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(historyCashierBills);
		if (goodsmap != null) {
			for (HistoryGoodsBills goods : goodsmap.values()) {
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
				goods.setCashierBillsID(historyCashierBills.getCashierBillsID());
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
		return "success";
	}
	
	@SuppressWarnings("unused")
	public String toSave(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "公司财务单据归档("+cashierBillsmaps.size()+"条)"
				+ account.getAccountEmail() + ")", account);
		if (cashierBillsmaps != null) {
			List<BaseBean> baseBeansList=new ArrayList<BaseBean>();
			List<Object> parms = new ArrayList<Object>();
			HistoryRelation historyRelation=null;
			String aa="";
			int a=1;
			
			for (HistoryCashierBillVO historyVO : cashierBillsmaps.values()){
				if(a==1){
					aa+=" where c.cashierBillsID =?";
					parms.add(historyVO.getCashierBillsID());
				}else{
					aa+=" or c.cashierBillsID =?";
					parms.add(historyVO.getCashierBillsID());
				}
				
				String hql = "from HistoryRelation where cashierBillsID = ?";
				historyRelation = (HistoryRelation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{historyVO.getCashierBillsID()});
				historyRelation.setArchivesNum(historyRelation.getDeparchivesNum());
				historyRelation.setArchivesDate(new Date());
				baseBeansList.add(historyRelation);
				a++;
			}
			baseBeansList.add(logBook);	
			baseBeanService.saveBeansListAndexecuteSqlsByParams(baseBeansList, null, null);
		}
		return "success";
	}
	/**
	 * 部门归档
	 * @return
	 */
	public String toSave1(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		String organizationID = (String)ActionContext.getContext().getSession()
		.get("organizationID");
		String hql = "from COrganization where organizationID =?";
		COrganization obj = (COrganization)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{organizationID});
		if(billid!=null&&!"".equals(billid)){
			String []strs=billid.split(",");
			List<BaseBean> baseBeansList=new ArrayList<BaseBean>();
			List<Object[]> parms = new ArrayList<Object[]>();
			List<String> sqls=new ArrayList<String>();
			CLogBook logBook = logBookService.saveCLogBook(null,obj.getOrganizationName()+"单据归档("+strs.length+"条)"
					+ account.getAccountEmail() + ")", account);
			baseBeansList.add(logBook);
			int denum=0;
			for (int i = 0; i < strs.length; i++) {
				if(i<=0){
					Object obj1=baseBeanService.getObjectBySqlAndParams("select max(deparchivesNum) from dt_history_relation where organizationID=? and companyID=?", new Object[]{organizationID,account.getCompanyID()});
					denum=(obj1==null?1:(Integer.parseInt(obj1.toString())+1));
				}else{
					denum=denum+1;
				}
				historyRelation=new HistoryRelation();
				historyRelation.setDeparchivesDate(new Date());
				historyRelation.setOrganizationID(organizationID);
				historyRelation.setCompanyID(account.getCompanyID());
				historyRelation.setDeparchivesNum(String.valueOf(denum));
				historyRelation.setCashierBillsID(strs[i]);
				historyRelation.setID(serverService.getServerID("historyRelation"));
				baseBeansList.add(historyRelation);
				String sql="update CashierBills set status=? where cashierBillsID=?";
				sqls.add(sql);
				parms.add(new Object[]{"08",strs[i]});
			}
			String [] usqls=new String[sqls.size()];
			for (int i = 0; i < sqls.size(); i++) {
				usqls[i]=sqls.get(i);
			}
			baseBeanService.executeHqlsByParamsList(baseBeansList, usqls, parms);
		}
		return "success";
	}
	
	/**
	 * 生成图表数据
	 */
	public void invprint() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		// 获取没有月总金额
		String sql1 = "select sum(hg.loan),sum(hg.forloan) from dt_hy_goodsvo hg where hg.enddate between ? and ? and hg.companyid=?";
		if(tt.equals("z")){
			sql1+=" and hg.organizationid=?";
		}
		String timeyear = Utilities.getDateString(new Date(), "yyyy-MM");
		String mm = "";
		if (timeyear.substring(5, 6).equals("0")) {
			mm = timeyear.substring(6, 7);
		} else {
			mm += timeyear.substring(5, 7);
		}
		objectny=new ArrayList<Object[]>();
		for (int i = 1; i <= Integer.parseInt(mm); i++) {
			int day = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
					0, 4)), i);
			String time = timeyear.substring(0, 4) + "-" + i;
			Date stime= Utilities.getDateFromString(Utilities.getStrDateStrs(time+ "-"+ "01")+ " 00:00:00","yyyy-MM-dd hh:mm:ss");
			Date etime= Utilities.getDateFromString(Utilities.getStrDateStrs(time+ "-"+ day)+ " 23:59:59","yyyy-MM-dd hh:mm:ss");
			List<Object> params=new ArrayList<Object>();
			params.add(stime);
			params.add(etime);
			params.add(account.getCompanyID());
			if(tt.equals("z")){
				params.add(organizationID);
			}
			Object list1 = baseBeanService.getObjectBySqlAndParams(
					sql1, params.toArray());
			Object[] obj = new Object[3];
			obj[0] = getMonth(i);
			if (list1 !=null) {
				Object[] o=(Object[])list1;
				if(o[0]!=null){
					obj[1]=String.format("%.2f", Float.parseFloat(o[0].toString())/10000);
				}else{
					obj[1]=0;
				}
				if(o[1]!=null){
					obj[2]=String.format("%.2f", Float.parseFloat(o[1].toString())/10000);
				}else{
					obj[2]=0;
				}
			} else {
				obj[1] = 0;
				obj[2] = 0;
			}
			objectny.add(obj);
		}
	}

	/**
	 * 获取月份
	 * 
	 * @param i
	 * @return
	 */
	private String getMonth(int i) {
		String mm = "";
		switch (i) {
		case 1:
			mm = "一月";
			break;
		case 2:
			mm = "二月";
			break;
		case 3:
			mm = "三月";
			break;
		case 4:
			mm = "四月";
			break;
		case 5:
			mm = "五月";
			break;
		case 6:
			mm = "六月";
			break;
		case 7:
			mm = "七月";
			break;
		case 8:
			mm = "八月";
			break;
		case 9:
			mm = "九月";
			break;
		case 10:
			mm = "十月";
			break;
		case 11:
			mm = "十一月";
			break;
		case 12:
			mm = "十二月";
			break;
		}
		return mm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Map<String, CashierBills> getCashierBillsmap() {
		return cashierBillsmap;
	}

	public void setCashierBillsmap(Map<String, CashierBills> cashierBillsmap) {
		this.cashierBillsmap = cashierBillsmap;
	}

	public HistoryCashierBills getHistoryCashierBills() {
		return historyCashierBills;
	}

	public void setHistoryCashierBills(HistoryCashierBills historyCashierBills) {
		this.historyCashierBills = historyCashierBills;
	}

	public HistoryRelation getHistoryRelation() {
		return historyRelation;
	}

	public void setHistoryRelation(HistoryRelation historyRelation) {
		this.historyRelation = historyRelation;
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

	public List<BaseBean> getCashierBillslists() {
		return CashierBillslists;
	}

	public void setCashierBillslists(List<BaseBean> cashierBillslists) {
		CashierBillslists = cashierBillslists;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public HistoryCashierBillVO getHistoryVO() {
		return historyVO;
	}

	public void setHistoryVO(HistoryCashierBillVO historyVO) {
		this.historyVO = historyVO;
	}

	public Map<String, HistoryGoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, HistoryGoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public List<CCode> getBillsTypeList() {
		return billTypes;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billTypes = billsTypeList;
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

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
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

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public Map<String, HistoryCashierBillVO> getCashierBillsmaps() {
		return cashierBillsmaps;
	}

	public void setCashierBillsmaps(Map<String, HistoryCashierBillVO> cashierBillsmaps) {
		this.cashierBillsmaps = cashierBillsmaps;
	}

	public String getNuDate() {
		return nuDate;
	}

	public void setNuDate(String nuDate) {
		this.nuDate = nuDate;
	}

	public String getNuvDate() {
		return nuvDate;
	}

	public void setNuvDate(String nuvDate) {
		this.nuvDate = nuvDate;
	}

	public String getSfdate() {
		return sfdate;
	}

	public void setSfdate(String sfdate) {
		this.sfdate = sfdate;
	}

	public String getEfdate() {
		return efdate;
	}

	public void setEfdate(String efdate) {
		this.efdate = efdate;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getChuanzhi() {
		return chuanzhi;
	}

	public void setChuanzhi(String chuanzhi) {
		this.chuanzhi = chuanzhi;
	}

	public List<Object[]> getObjectny() {
		return objectny;
	}

	public void setObjectny(List<Object[]> objectny) {
		this.objectny = objectny;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}
}
