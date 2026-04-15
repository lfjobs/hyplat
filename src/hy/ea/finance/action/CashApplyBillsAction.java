package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashApplyBills;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.CashierBillsAllot;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.history.HistoryGoodsBills;
import hy.ea.bo.human.InstitutionsRegistration;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffBankAccount;
import hy.ea.bo.office.Registration;
import hy.ea.service.CCodeService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;

/**
 * 公司出纳单据统计管理：CashierBillsSummaryCompanyAction
 * 
 * @author yjg
 * 
 */
public class CashApplyBillsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	private String result;
	private PageForm pageForm;
	private int pageNumber;
	private List<CashApplyBills> cash;
	List<BaseBean> CashApplyList;
	private String search;
	private String status;
	private String cashApply;
	private int Money;

	private CashierBillsVO cashierBillsVO;
	private CashierBills cashierBills;
	private CashApplyBills cashApplyBills;
	private CashierBillsAllot cashierBillsAllot;
	private List<BaseBean> gooList;

	private String sdate;

	private String edate;

	private String bokuan;   //拨款：bokuan    暂不拨款：zanbubokuan

	private String print;

	private String cother;

	private String weibokuan;

	private String level;// 区分总部 allCompany 公司 company 部门 organization
	private String cancel;// 区分是否作废
	private String allot;// 确定调拨
	
	private String srandom;
	
	private Map<String, String> bcheckMap;//审核信息
	
	private String numID;//银行查询信息
	private String numtype;
	private String cuid;
	private String commStr; //用于传递审核已经字符串
	/**
	 * 多选框参数传递
	 * 
	 * @return
	 */
	private String str;
	
	/**
	 * 调拨多选框参数传递
	 * 
	 * @return
	 */
	private String strc;

	/**
	 * 
	 * 现金请购审批明细列表查询
	 * 
	 */

	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("cashApplyBills", cashierBills);
		return toCashList();
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	public String toCash() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		cashierBills =(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{str});
		
		
		if (print != null && "print".equals("print")) {
			if (cother != null && cother.equals("cother")) {
				return "cashPrintother";
			} else {
				return "cashPrint";
			}
		}
		List<BaseBean> bCheckList =baseBeanService.getListBeanByHqlAndParams("from BillCheck where newBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		if(bCheckList.size()>0){
			bcheckMap=new HashMap<String, String>();
			commStr="";
			for (int i = 0; i < bCheckList.size(); i++) {
				BillCheck billCheck=(BillCheck)bCheckList.get(i);
				if(billCheck.getDeptpost()!=null){
					commStr=commStr+billCheck.getAuditorname()+":"+billCheck.getComments()+";";
					bcheckMap.put(billCheck.getDeptpost(), billCheck.getAuditorname());
					bcheckMap.put(billCheck.getDeptpost()+"zt", billCheck.getAuditorstatus());
				}
			}
		}
		session.put("session_value", Math.random() + "");
		
		return "cash";
	}
	
	
	/**
	 * 单据列表
	 * 
	 * @return
	 */
	public String toCashList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams("from Company where (companyPID=? or companyID=?) and companyStatus=?", new Object[]{account.getCompanyID(),account.getCompanyID(),"00"});
		
		List<Object> arrays = new ArrayList<Object>();
		
		String sql2="select c1.CASHIERBILLSID,c1.companyName,c1.departmentName,c1.projectName,c1.xmtypename,c1.JournalNum,c1.staffname,c1.inputName,c1.CASHIERDATE,c1.status" +
				" from dtcashierbills c1  where c1.BILLSTYPE =?";
		arrays.add("拨款单");
		/*arrays.add("项目支出预算单");*/
		
		if (level != null && "allCompany".equals(level.trim())) {
			if(list.size()>0){
				sql2+=" and c1.companyID in (";
				for (int i = 0; i < list.size(); i++) {
					Company company=(Company)list.get(i);
					arrays.add(company.getCompanyID());
					if(i>0){
						sql2+=",";
					}
					sql2+="?";
				}
				sql2+=" )";
			}
		}
		if (level != null && "company".equals(level.trim())) {
			sql2 += " AND c1.companyid =? ";
			arrays.add(account.getCompanyID());
		}
		if (level != null && "organization".equals(level.trim())) {
			sql2 += " AND c1.organizationID =? ";
			arrays.add(organizationID);
			sql2 += " AND c1.COMPANYID = ?";
			arrays.add(account.getCompanyID());
		}
		
		if(search != null && "search".equals(search)){
			cashierBills = (CashierBills) session.get("cashApplyBills");
			if (cashierBills != null && !"".equals(cashierBills)) {
				/*if (cashierBills.getCompanyID() != null
						&& !"".equals(cashierBills.getCompanyID().trim())) {
					sql2 += " AND c1.companyID = ?";
					arrays.add(cashierBills.getCompanyID());
				}else{
					sql2+=" and c1.companyID in (";
					for (int i = 0; i < list.size(); i++) {
						Company company=(Company)list.get(i);
						arrays.add(company.getCompanyID());
						if(i>0){
							sql2+=",";
						}
						sql2+="?";
					}
					sql2+=" )";
				}*/
				if(null!=cashierBills.getProjectName().trim()
						&&!"".equals(cashierBills.getProjectName().trim())){
					sql2 += " and c1.projectName like ?";
					arrays.add("%"+cashierBills.getProjectName().trim()+"%");
				}
				if (cashierBills.getJournalNum() != null
						&& !"".equals(cashierBills.getJournalNum().trim())) {
					sql2 += " AND c1.journalNum like ?";
					arrays.add("%" + cashierBills.getJournalNum().trim()+ "%");
				}
				if (cashierBills.getStaffName() != null
						&& !"".equals(cashierBills.getStaffName().trim())) {
					sql2 += " AND c1.staffname like ?";
					arrays.add("%" + cashierBills.getStaffName().trim()+ "%");
				}
				/*if (cashierBills.getInputName().trim() != null
						&& !"".equals(cashierBills.getInputName().trim())) {
					sql2 += " AND c1.inputName like ?";
					arrays.add("%" +cashierBills.getInputName().trim()+ "%");
				}*/
				if(cashierBills.getStatus()!=null
						&&!cashierBills.getStatus().equals("")){
					sql2 += " AND c1.status =?";
					arrays.add(cashierBills.getStatus());
				}else{
					// 区分是不是未拨款
					if (weibokuan != null && "weibokuan".equals(weibokuan)) {
						sql2 += " AND (c1.status =? or c1.status =? or c1.status =?)";
						arrays.add("41");
						arrays.add("42");
						arrays.add("44");
					} else {
						sql2 += " AND  c1.status =?";
						arrays.add("43");
					}
				}
				if (sdate != null && !"".equals(sdate) && edate != null
						&& !"".equals(edate)) {
					sql2 += " AND c1.cashierDate between ?  and ? ";
					arrays.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					arrays.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}else{
					sql2+=" AND trunc(c1.cashierDate,'mm') between ? and ?";
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, -3);
					arrays.add(calendar.getTime());
					arrays.add(new Date());
				}
			}
		}else{
			
			
			if (weibokuan != null && "weibokuan".equals(weibokuan)) {
				sql2 += " AND (c1.status =? or c1.status =? or c1.status =?)";
				arrays.add("41");
				arrays.add("42");
				arrays.add("44");
			} else {
				sql2 += " AND c1.status =?";
				arrays.add("43");
			}
			
			sql2+=" AND trunc(c1.cashierDate,'mm') between ? and ?";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			arrays.add(calendar.getTime());
			arrays.add(new Date());
		}
				
		// 未拨款区分数据显示权限
				
		
		
		sql2 += " order by c1.cashierDate desc nulls last";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql2, "select count(*) "+ sql2.substring(sql2.indexOf("from")),
				arrays.toArray());
		if (print != null && "print".equals("print")) {
			if (cother != null && cother.equals("cother")) {
				return "cashPrintother";
			} else {
				return "cashPrint";
			}
		}
		return "cashList";
	}

	/**
	 * 调拨现金申请审批明细单
	 * 
	 * @return
	 */
	public String ChangeCashApplyBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tableallot", cashierBillsVO);
		if (allot != null && "allot".equals(allot)) {
			cashierBillsVO = (CashierBillsVO) session.get("tableallot");
			if (cashierBillsVO != null && !"".equals(cashierBillsVO)) {
				if (strc != null && !"".equals(strc.trim())) {
					String[] strs = strc.substring(0, strc.lastIndexOf(","))
							.split(",");
					if (strs.length > 0) {
						List<BaseBean> allotList = new ArrayList<BaseBean>();// 声明实体集合
						for (int i = 0; i < strs.length; i++) {
							cashierBillsAllot = new CashierBillsAllot();
							cashierBillsAllot.setCashierBillsID(strs[i]);
							cashierBillsAllot.setCompanyID(cashierBillsVO
									.getCompanyID());
							cashierBillsAllot.setCompanyName(cashierBillsVO
									.getCompanyname());
							cashierBillsAllot.setDepartmentID(cashierBillsVO
									.getDepartmentID());
							cashierBillsAllot.setDepartmentName(cashierBillsVO
									.getDepartmentname());
							cashierBillsAllot.setStaffID(cashierBillsVO
									.getStaffID());
							cashierBillsAllot.setStaffName(cashierBillsVO
									.getStaffname());
							allotList.add(cashierBillsAllot);
						}
						baseBeanService.executeSqlsByParmsList(allotList, null,
								null);
					}
				}

			}
		}
		return toCash();
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	public String SaveCashApplyBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		if(srandom!=null&&!srandom.equals("")){
			if(srandom!=null&&srandom.equals(session.get("session_value"))){
				session.remove("session_value");
			}else{
				str=cashierBills.getCashierBillsID();
			   return toCash();
			}
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CashierBills cBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		cBills.setAppstyle(cashierBills.getAppstyle());
		cBills.setStatus("42");
		beans.add(cBills);
		for (CashApplyBills entity : cash) {
			if (entity != null) {
				GoodsBills goodsBills = (GoodsBills) baseBeanService
						.getBeanByHqlAndParams(
								"from GoodsBills where goodsBillsID=?",
								new Object[] { entity.getGoodsBillsID() });					
				goodsBills.setStartDate(entity.getAppropriationDate());
				beans.add(goodsBills);
				CashApplyBills cashApplyBills=new CashApplyBills();
				cashApplyBills.setCashApplyBillsID(serverService.getServerID("cashApply"));
				cashApplyBills.setCashierBillsID(cashierBills.getCashierBillsID());
				cashApplyBills.setGoodsBillsID(goodsBills.getGoodsBillsID());
				cashApplyBills.setCsbID(cashierBills.getProID());
				cashApplyBills.setAppropriationDate(entity.getAppropriationDate() == null
						|| "".equals(entity.getAppropriationDate()) ? new Date()
						: entity.getAppropriationDate());
				cashApplyBills.setAppropriateStatus("01");
				cashApplyBills.setAppropriationMoney(entity.getAppropriationMoney());
				/*cashApplyBills.setAppropriationNote(entity.getAppropriationNum());*/
				cashApplyBills.setAppropriationNum(entity.getAppropriationNum());
				cashApplyBills.setAppropriationcompanyID(entity.getAppropriationcompanyID());
				cashApplyBills.setAppropriationcompanyName(entity.getAppropriationcompanyName());
				cashApplyBills.setReceivablesID(entity.getReceivablesID());
				cashApplyBills.setReceivablesName(entity.getReceivablesName());
				cashApplyBills.setRecropriationNum(entity.getRecropriationNum());
				cashApplyBills.setAppstyle(cashierBills.getAppstyle());
				/*cashApplyBills.setAppropriationNote(entity.getAppropriationNote());*/
				beans.add(cashApplyBills);
			}
		}
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	
	/**
	 * 暂不拨款 修改物品状态
	 * 
	 * @return
	 */
	public String SaveCashApplyNoBills() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> JouStr = new ArrayList<String>();
		for (CashApplyBills entity : cash) {
			if (entity != null) {
				GoodsBills goodsBills = (GoodsBills) baseBeanService
						.getBeanByHqlAndParams(
								"from GoodsBills where goodsBillsID=?",
								new Object[] { entity.getGoodsBillsID() });					
				goodsBills.setStartDate(entity.getAppropriationDate());
				beans.add(goodsBills);
				CashApplyBills cashApplyBills=new CashApplyBills();
				cashApplyBills.setCashApplyBillsID(serverService.getServerID("cashApply"));
				cashApplyBills.setCashierBillsID(cashierBills.getCashierBillsID());
				cashApplyBills.setGoodsBillsID(goodsBills.getGoodsBillsID());
				cashApplyBills.setCsbID(cashierBills.getProID());
				cashApplyBills.setAppropriationDate(entity.getAppropriationDate() == null
						|| "".equals(entity.getAppropriationDate()) ? new Date()
						: entity.getAppropriationDate());
				cashApplyBills.setAppropriateStatus("01");
				cashApplyBills.setAppropriationMoney(entity.getAppropriationMoney());
				cashApplyBills.setAppropriationNum(entity.getAppropriationNum());
				cashApplyBills.setAppropriationcompanyID(entity.getAppropriationcompanyID());
				cashApplyBills.setAppropriationcompanyName(entity.getAppropriationcompanyName());
				cashApplyBills.setReceivablesID(entity.getReceivablesID());
				cashApplyBills.setReceivablesName(entity.getReceivablesName());
				cashApplyBills.setRecropriationNum(entity.getRecropriationNum());
				cashApplyBills.setAppstyle(cashierBills.getAppstyle());
				beans.add(entity);
			}
		}

		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}

	/**
	 * 自动生成预入款单据
	 * 
	 * @param entity
	 * @param cashid
	 * @param beans
	 * @return
	 */
	public String savecash() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CashierBills cBills = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(
						"from CashierBills where cashierBillsID=?",
						new Object[] { cashierBills.getCashierBillsID() });
		List<BaseBean> beans=new ArrayList<BaseBean>();
		List<BaseBean> gList=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		try {
			// 生成预入款单
			CashierBills cashierBills2 = new CashierBills();
			cashierBills2 = (CashierBills) cBills.cloneCashierBills();
			cashierBills2.setBillsType("预入款单");
			cashierBills2.setStatusbill("00");
			cashierBills2.setCashierBillsID(serverService.getServerID("cashierTally"));
			cashierBills2.setCashierBillsKey(null);
			cashierBills2.setJournalNum(serverService.getBillID(cBills.getCompanyID()));
			cashierBills2.setCashierDate(new Date());
			cashierBills2.setStatus("09");
			beans.add(cashierBills2);
			
			if(gList.size()>0){
				for (int i = 0; i < gList.size(); i++) {
					GoodsBills gBills=(GoodsBills)gList.get(i);
					GoodsBills goodsBills=(GoodsBills)gBills.cloneGoodsBills();
					goodsBills.setGoodsBillsID(serverService.getServerID("goodsbills"));
					goodsBills.setPlanGoodsBillsID(gBills.getGoodsBillsID());
					goodsBills.setGoodsBillsKey(null);
					goodsBills.setCashierBillsID(cashierBills2.getCashierBillsID());
					beans.add(goodsBills);
					
					cashApplyBills=(CashApplyBills)baseBeanService.getBeanByHqlAndParams("from CashApplyBills where goodsBillsID=?", new Object[]{gBills.getGoodsBillsID()});
					CashApplyBills cBills2=(CashApplyBills)cashApplyBills.cloneCashApplyBills();
					cBills2.setCashApplyBillsID(serverService.getServerID("cashApply"));
					cBills2.setCashApplyBillsKey(null);
					cBills2.setGoodsBillsID(goodsBills.getGoodsBillsID());
					cBills2.setCashierBillsID(cashierBills2.getCashierBillsID());
					beans.add(cBills2);
				}
			}
			
			// 保存预算单据关联单据表信息
			RelatedBill relatedBill = new RelatedBill();
			relatedBill.setRbID(serverService.getServerID("relatedbill"));
			relatedBill.setCashid(cBills.getCashierBillsID());
			relatedBill.setCashfid(cashierBills2.getCashierBillsID());
			beans.add(relatedBill);
			
			cBills.setStatus("43");
			beans.add(cBills);			
			
			baseBeanService.executeHqlsByParamsList(beans, null, null);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 自动生成欠款单据
	 * 
	 * @param entity
	 * @param cashid
	 * @param beans
	 * @return
	 */
	public String saveNoCash() {
		CashierBills cBills = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(
						"from CashierBills where cashierBillsID=?",
						new Object[] { cashierBills.getCashierBillsID() });
		List<BaseBean> beans=new ArrayList<BaseBean>();
		List<BaseBean> gList=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		try {
			// 生成欠款单
			CashierBills cashierBills2 = new CashierBills();
			cashierBills2 = (CashierBills) cBills.cloneCashierBills();		
			cashierBills2.setBillsType("欠款单");
			cashierBills2.setStatusbill("00");
			cashierBills2.setCashierBillsID(serverService.getServerID("cashierTally"));
			cashierBills2.setCashierBillsKey(null);
			cashierBills2.setJournalNum(serverService.getBillID(cBills.getCompanyID()));
			cashierBills2.setCashierDate(new Date());
			cashierBills2.setStatus("46");
			beans.add(cashierBills2);
			if(gList.size()>0){
				for (int i = 0; i < gList.size(); i++) {
					GoodsBills gBills=(GoodsBills)gList.get(i);
					GoodsBills goodsBills=(GoodsBills)gBills.cloneGoodsBills();
					cashApplyBills=(CashApplyBills)baseBeanService.getBeanByHqlAndParams("from CashApplyBills where goodsBillsID=?", new Object[]{gBills.getGoodsBillsID()});
					goodsBills.setGoodsBillsID(serverService.getServerID("goodsbills"));
					goodsBills.setPlanGoodsBillsID(gBills.getGoodsBillsID());
					goodsBills.setGoodsBillsKey(null);
					goodsBills.setCashierBillsID(cashierBills2.getCashierBillsID());
					goodsBills.setRealMoney(cashApplyBills.getAppropriationMoney());
					beans.add(goodsBills);
				}
			}
			
			// 保存预算单据关联单据表信息
			RelatedBill relatedBill = new RelatedBill();
			relatedBill.setRbID(serverService.getServerID("relatedbill"));
			relatedBill.setCashid(cBills.getCashierBillsID());
			relatedBill.setCashfid(cashierBills2.getCashierBillsID());
			beans.add(relatedBill);
			
			cBills.setStatus("44");
			beans.add(cBills);	
			
			baseBeanService.executeHqlsByParamsList(beans, null, null);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	public String ajax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String[] goid = str.split(",");
		if (goid.length > 0) {
			for (int i = 0; i < goid.length; i++) {
				GoodsBills gooBills = (GoodsBills) baseBeanService
						.getBeanByHqlAndParams(
								"from GoodsBills where goodsBillsID=?",
								new Object[] { goid[i] });
				CashierBills cashierBills = (CashierBills) baseBeanService
						.getBeanByHqlAndParams(
								"from CashierBills where cashierBillsID=?",
								new Object[] { gooBills.getCashierBillsID() });
				cashierBills.setCcompanyName(account.getCompanyName());
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						"from Staff where staffID=?",
						new Object[] { account.getStaffID() });
				cashierBills.setCtUserName(staff.getStaffName());
				gooList.add(gooBills);
			}
		}
		return "success";
	}
	
	public String AjaxgetCompanyAndStaff(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> StaffList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<CCode> ComanyList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("StaffList", StaffList);
		map.put("ComanyList", ComanyList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 
	 * 
	 * 获取当前部门下的（顶级部门）的银行账号
	 * @return
	 */
	public String getBankAccountList(){
		DetachedCriteria dc=null; 
		if(numtype.equals("users")){
			dc = DetachedCriteria.forClass(StaffBankAccount.class);
	         //bankAccount银行账号 bankName银行名称 conPerson账户责任人用几个查询
			if(numID!=null&&!numID.equals("")){
				dc.add(Restrictions.or(Restrictions.like("bankAccount",numID, MatchMode.ANYWHERE),Restrictions.like("bankName",numID, MatchMode.ANYWHERE)));
			}
			dc.add(Restrictions.eq("staffID",cuid));
		}else{
			dc = DetachedCriteria.forClass(Registration.class);
	         //bankAccount银行账号 bankName银行名称 conPerson账户责任人用几个查询
			if(numID!=null&&!numID.equals("")){
				dc.add(Restrictions.or(Restrictions.like("bankAccount",numID, MatchMode.ANYWHERE),Restrictions.like("bankName",numID, MatchMode.ANYWHERE)));
			}
			dc.add(Restrictions.or(Restrictions.eq("ccompanyID",cuid),Restrictions.eq("companyID", cuid)));
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20: pageNumber), dc);
		if(numtype.equals("com")&&pageForm==null){
			dc = DetachedCriteria.forClass(InstitutionsRegistration.class);
	         //bankAccount银行账号 bankName银行名称 conPerson账户责任人用几个查询
			if(numID!=null&&!numID.equals("")){
				dc.add(Restrictions.or(Restrictions.like("bankAccount",numID, MatchMode.ANYWHERE),Restrictions.like("bankName",numID, MatchMode.ANYWHERE)));
			}
			dc.add(Restrictions.eq("companyID",cuid));
			pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 20: pageNumber), dc);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		this.result = obj.toString();
		
		return "success";
		
		
	}

	/**
	 * 获取session中的cashApplyBillsID查询现金申请单
	 * 
	 * @return
	 */
	public String QueryCashApplyBills() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		String cashApplyBillsID = (String) session.get("cashApplyBillsID");
		String hql = "from CashApplyBills where cashApplyBillsID = ?";
		CashApplyList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cashApplyBillsID });
		for (int i = 0; i < CashApplyList.size(); i++) {
			CashApplyBills cas = (CashApplyBills) CashApplyList.get(i);
			if (cas.getMoney() != null && !cas.getMoney().equals("")) {
				Money += Integer.parseInt(cas.getMoney());
			}
		}
		return "edit";
	}

	/**
	 * 
	 * 作废现金申请单
	 * 
	 * @return
	 */
	public String CancelCashApplyBills() {
		try {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			List<BaseBean> list = new ArrayList<BaseBean>();
			if (strs.length > 0) {
				String hql = "from HistoryGoodsBills where goodsBillsID = ?";
				HistoryGoodsBills hgb = null;
				for (int i = 0; i < strs.length; i++) {
					hgb = (HistoryGoodsBills) baseBeanService
							.getBeanByHqlAndParams(hql,
									new Object[] { strs[i] });
					hgb.setCanstatus("cancel");
					list.add(hgb);
				}

			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Map<String,Object> map = new HashMap<String, Object>();
		// map.put("result", "success");
		// JSONObject jo = JSONObject.fromObject(map);
		// this.result = jo.toString();

		return "success";
	}

	@SuppressWarnings("unchecked")
	public String upgoodsmanage() {
		List<String> grouplist = baseBeanService.getListBeanBySqlAndParams(
				"select distinct co.groupcompanysn from dtcompany co", null);
		List<String> typelist = baseBeanService.getListBeanBySqlAndParams(
				"select distinct g.typeid from dtgoodsmanage g", null);
		String gruouid = "";
		System.out.println("---------------------开始---------------------");

		if (grouplist.size() > 0) {
			List<BaseBean> goolist = new ArrayList<BaseBean>();
			for (int i = 0; i < grouplist.size(); i++) {
				System.out.println(grouplist.get(i) + "开始");
				gruouid = grouplist.get(i);
				String typeid = "";
				for (int j = 0; j < typelist.size(); j++) {
					System.out.println(typelist.get(j) + "开始");
					typeid = typelist.get(j);
					String sql = "select g from GoodsManage g , Company c where c.companyID=g.companyID and c.groupCompanySn=? and g.typeID=?";
					List<BaseBean> list = baseBeanService
							.getListBeanByHqlAndParams(sql, new Object[] {
									grouplist.get(i), typelist.get(j) });

					for (int k = 0; k < list.size(); k++) {
						GoodsManage goodsManage = new GoodsManage();
						goodsManage = (GoodsManage) list.get(k);
						String coding = ToChineseFirstLetter.getFirstLetter(
								goodsManage.getTypeID()).toUpperCase();
						DecimalFormat form = new DecimalFormat("000000");
						goodsManage.setGoodsCoding(coding + "_"
								+ form.format(k + 1));
						System.out.println(goodsManage.getGoodsCoding());
						goolist.add(goodsManage);
					}

					System.out.println(typeid + "结束");
				}
				System.out.println(gruouid + "结束");
			}
			baseBeanService.executeSqlsByParmsList(goolist, null, null);
		}

		System.out.println("---------------------结束---------------------");
		return "";
	}
	
	
	public String getProductAndGoodsAjax(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		/*List<BaseBean> productList = baseBeanService
				.getListBeanByHqlAndParams("from ProductPackaging where parentId=?", new Object[] {cuid });*/
		String sql = "select ppID,parentId,goodsName  from dt_ProductPackaging t connect by nocycle " +
				"prior t.ppID = t.parentId start with t.ppID = ?";
		List<BaseBean> productList = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{cuid});
		//List<BaseBean> goodsList =baseBeanService.getListBeanByHqlAndParams("from GoodsBills where ppID=?", new Object[] { cuid });
		List<Object> arrays=new ArrayList<Object>();
		String sql1 = " SELECT g.goodsbillsid gbid,g.GOODSNUM,g.GOODSNAME,to_char(g.STARTDATE,'yyyy-mm-dd'),"
				+ " g.GOODSVARIABLEID,g.QUANTITY,g.PRICEMANAGE,g.PRICE,g.MONEY,dp.appropriationMoney,"
				+ " dp.appropriationcompanyID,dp.appropriationcompanyName,dp.appropriationNum,dp.appropriateStatus,"
				+ " dp.ReceivablesID,dp.ReceivablesName,dp.RecropriationNum,to_char(g.TARGETSTART,'yyyy-mm-dd'),"
				+ " to_char(g.TARGETEND,'yyyy-mm-dd'),g.TARGETDEPTID,g.TARGETDEPTNAME,g.TARGETSALERID,g.TARGETSALERNAME,"
				+ " g.studentCode,g.studentPeriods,g.schoolopendate,g.CCOMPANYID,g.CCOMPANYNAME,g.CONNECTID,g.CONNECTNAME,g.ppID"
				+ " from dtgoodsbills g "
				+ " left join dtCashApplyBills dp on g.goodsbillsid=dp.goodsbillsid"
				+ " WHERE g.cashierBillsID=?";
		arrays.add(str);
		
		sql += " order  by dp.cashierDate desc nulls last";
		List<Object[]> goodsList =baseBeanService.getListBeanBySqlAndParams(sql1, arrays.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("productList", productList);
		map.put("goodsList", goodsList);
		try {
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	

	/**
	 * 
	 * 获取已作废单据列表
	 * 
	 * @return
	 */
	public String getCancelCashList() {
		return null;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getCashApply() {
		return cashApply;
	}

	public void setCashApply(String cashApply) {
		this.cashApply = cashApply;
	}

	public List<CashApplyBills> getCash() {
		return cash;
	}

	public void setCash(List<CashApplyBills> cash) {
		this.cash = cash;
	}

	public List<BaseBean> getCashApplyList() {
		return CashApplyList;
	}

	public void setCashApplList(List<BaseBean> cashApplyList) {
		CashApplyList = cashApplyList;
	}

	public int getMoney() {
		return Money;
	}

	public void setMoney(int money) {
		Money = money;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	public CashierBillsAllot getCashierBillsAllot() {
		return cashierBillsAllot;
	}

	public void setCashierBillsAllot(CashierBillsAllot cashierBillsAllot) {
		this.cashierBillsAllot = cashierBillsAllot;
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

	public String getBokuan() {
		return bokuan;
	}

	public void setBokuan(String bokuan) {
		this.bokuan = bokuan;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getCother() {
		return cother;
	}

	public void setCother(String cother) {
		this.cother = cother;
	}

	public String getWeibokuan() {
		return weibokuan;
	}

	public void setWeibokuan(String weibokuan) {
		this.weibokuan = weibokuan;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getStrc() {
		return strc;
	}

	public void setStrc(String strc) {
		this.strc = strc;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getAllot() {
		return allot;
	}

	public void setAllot(String allot) {
		this.allot = allot;
	}

	public CashApplyBills getCashApplyBills() {
		return cashApplyBills;
	}

	public void setCashApplyBills(CashApplyBills cashApplyBills) {
		this.cashApplyBills = cashApplyBills;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public Map<String, String> getBcheckMap() {
		return bcheckMap;
	}

	public void setBcheckMap(Map<String, String> bcheckMap) {
		this.bcheckMap = bcheckMap;
	}

	public String getNumID() {
		return numID;
	}

	public void setNumID(String numID) {
		this.numID = numID;
	}

	public String getNumtype() {
		return numtype;
	}

	public void setNumtype(String numtype) {
		this.numtype = numtype;
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

	public String getCommStr() {
		return commStr;
	}

	public void setCommStr(String commStr) {
		this.commStr = commStr;
	}
}
