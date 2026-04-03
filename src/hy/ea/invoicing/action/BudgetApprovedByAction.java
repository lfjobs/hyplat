package hy.ea.invoicing.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetDetail;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * BudgetApprovedByAction 预算单据审核
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class BudgetApprovedByAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	private CostSheetBill costSheetBill; // 进销存预算单据表
	private CostSheetDetail costSheetDetail; // 进销存预算子类
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private Map<String, CostSheetDetail> logbookmap;
	private String sdate;
	private String edate;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;		//单位往来关系
	private List<CCode> codeRelationList;		//个人往来关系
	private String companyname;		//公司名称
	private String organizationname;		//部门名称
	private Staff contactUser;		//往来个人
	private ContactCompany contactCompanyView;		//往来单位
	private String result;
	private String toSee;
	private List<BaseBean> costSheetDetailList;
	/**
	 * 单据状态
	 * '00' 待审核
	 * '01' 确定
	 * '10' 驳回作废
	 */
	private String status;
	/**
	 * 单据类型 
	 * '00' 采购物品 
	 * '01' 日常支出 
	 * '02' 出差支出 
	 * '03' 租用支出 
	 * '04' 投入支出 
	 * '05' 应收款 
	 * '06' 应付款
	 * '07' 收入
	 */
	private String type;
	/**
	 * 责任人
	 */
	private List<BaseBean> staffList;
	
	
	/**
	 * 预算审核——获取单据类别(供调用)
	 * 
	 * @param status
	 *            单据类别编号
	 * @return
	 */
	private String utilType(String status) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("00", "采购物品预算单");
		result.put("01", "日常支出预算单");
		result.put("02", "出差支出预算单");
		result.put("03", "租用支出预算单");
		result.put("04", "投入支出预算单");
		result.put("05", "应收款预算单");
		result.put("06", "应付款预算单");
		result.put("07", "收入预算单");
		return result.get(status);
	}
	
	
	/**
	 * 预算审核——供查看、打印预览调用
	 */
	private void toSee(){
		if (costSheetBill != null) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String groupCompanySn = session.get("groupCompanySn").toString();
			//进销存预算单据
			if(toSee!=null&&toSee.equals("history")){
				
				String hql = " from CostSheetBill where csbID = ? ";
				costSheetBill = (CostSheetBill) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[]{costSheetBill.getCsbID()});
			}else{
			
			String hql = " from CostSheetBill where groupCompanySn=? and companyid=? and organizationID=? and csbID = ? ";
			costSheetBill = (CostSheetBill) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,costSheetBill.getCsbID()});
			}
			
			//进销存预算子类
			String hql1 = "from CostSheetDetail where  csbID=?";
			costSheetDetailList = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{costSheetBill.getCsbID()});
//			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
//					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
//					hql1, new Object[]{costSheetBill.getCsbID()});
			//往来个人
			String hql2 = " from Staff where staffID=? ";
			contactUser=(Staff)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{costSheetBill.getCstaffID()});
			//往来单位
			String hql3 = " from ContactCompany where ccompanyID=? ";
			contactCompanyView=(ContactCompany)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{costSheetBill.getCcompanyID()});
			//公司
			String comhql = "from Company where companyID=?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					comhql, new Object[] { account.getCompanyID() });
			companyname = company.getCompanyName();
			//部门
			String orghql = "from COrganization where companyID=? and organizationID=?";
			COrganization cOrganization = (COrganization) baseBeanService
					.getBeanByHqlAndParams(orghql, new Object[] {
							account.getCompanyID(),organizationID});
			organizationname = cOrganization.getOrganizationName();
		}
	}
	
	/**
	 * 预算审核——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("costSheetBill",
				costSheetBill);
		return getApprovedByList();
	}

	/**
	 * 预算审核——列表
	 * 
	 * @return
	 */
	public String getApprovedByList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> parmsList = new ArrayList<Object>();
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = "select b.csbid, c.companyname, b.journalNum, b.billsType, cr.organizationname,"
				+ " b.StaffName, b.billStaffName, b.billsDate, b.companyBankNum, b.ccompanyName,"
				+ " b.cstaffName,b.csreviewedname,"
				+ " case when b.billStatus='00' then '待审核'"
				+ " when b.billStatus='01' then '同意'"
				+ " when b.billStatus='10' then '驳回'"
				+ " else '' end"
				+ " from dt_inv_csthb b"
				+ " left join dtcompany c on  c.companyid = b.companyID"
				+ " left join dtcorganization cr on  cr.organizationid = b.departmentID";
		sql += " where b.groupCompanySn = ?";
		parmsList.add(groupCompanySn);
		sql += " and b.companyid = ? ";
		parmsList.add(account.getCompanyID());
		sql += " and b.organizationID=?";
		parmsList.add(organizationID);
		sql += " and b.billsType=?";
		String billtype = utilType(type);
		parmsList.add(billtype);
		if (search != null && search.equals("search")) {
			CostSheetBill cSheetBill = (CostSheetBill) session
					.get("costSheetBill");
			if (cSheetBill.getJournalNum() != null
					&& !cSheetBill.getJournalNum().trim().equals("")) {
				sql += " and b.journalNum like ? ";
				parmsList.add("%" + cSheetBill.getJournalNum().trim() + "%");
			}
			if(cSheetBill.getBillStaffName()!=null&&!cSheetBill.getBillStaffName().equals("")){
				sql+="and b.billStaffName like ?";
				parmsList.add("%"+cSheetBill.getBillStaffName().trim()+"%");
			}
			if (cSheetBill.getStaffID() != null
					&& !cSheetBill.getStaffID().equals("")) {
				sql += " and b.staffID = ? ";
				parmsList.add(cSheetBill.getStaffID());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and b.billsDate between ? and ? ";
				parmsList.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				parmsList.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (cSheetBill.getBillStatus() != null
					&& !cSheetBill.getBillStatus().equals("")) {
				sql += " and b.billStatus = ? ";
				parmsList.add(cSheetBill.getBillStatus());
			}
		}
		if (search == null || search.equals("")) {
			sql += " and b.billStatus=?";
				parmsList.add("00");
		}
		sql += " order by b.billsDate desc";
		Object[] parms = parmsList.toArray();
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
		staffList = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(),"50", organizationID });
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "list";
	}
	
	/**
	 * 预算审核——JSON修改待经理审核单状态
	 */
	public String updateResponsible(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		String hql = "update CostSheetBill set billStatus = ?,csreviewedName= ?,csreviewedID=? where groupCompanySn=? and companyID = ? and organizationID=? and csbID = ? ";
		Object[] params = { costSheetBill.getBillStatus(), sta.getStaffName(),sta.getStaffID(),
				 groupCompanySn,account.getCompanyID(),organizationID,costSheetBill.getCsbID()};
		String hql1 = "from  CostSheetBill where groupCompanySn=? and companyid=? and organizationID=? and csbID = ? ";
		CostSheetBill cb = (CostSheetBill) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { groupCompanySn,account.getCompanyID(),organizationID,costSheetBill.getCsbID()});
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = null;
		if (costSheetBill.getBillStatus().equals("10")) {
			logbook = logBookService.saveCLogBook(organizationID, "预算单据审核：驳回作废(凭证号:"
					+ cb.getJournalNum() + ")", account);
		} else {
			logbook = logBookService.saveCLogBook(organizationID,
					"预算单据审核：同意:(凭证号：" + cb.getJournalNum() + ")", account);
		}
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}
	
	/**
	 * 预算审核——查看
	 * 
	 * @return
	 */
	public String toApprovedBy(){
		toSee();
		return "edit";
	}
	
	/**
	 * 预算审核——打印单据
	 * @return
	 */
	public String toprintcsb() {
		toSee();
		return "printcsb";
	}
	
	public CostSheetBill getCostSheetBill() {
		return costSheetBill;
	}
	public void setCostSheetBill(CostSheetBill costSheetBill) {
		this.costSheetBill = costSheetBill;
	}
	public CostSheetDetail getCostSheetDetail() {
		return costSheetDetail;
	}
	public void setCostSheetDetail(CostSheetDetail costSheetDetail) {
		this.costSheetDetail = costSheetDetail;
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
	public Map<String, CostSheetDetail> getLogbookmap() {
		return logbookmap;
	}
	public void setLogbookmap(Map<String, CostSheetDetail> logbookmap) {
		this.logbookmap = logbookmap;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public Staff getContactUser() {
		return contactUser;
	}

	public void setContactUser(Staff contactUser) {
		this.contactUser = contactUser;
	}

	public ContactCompany getContactCompanyView() {
		return contactCompanyView;
	}

	public void setContactCompanyView(ContactCompany contactCompanyView) {
		this.contactCompanyView = contactCompanyView;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public String getToSee() {
		return toSee;
	}


	public void setToSee(String toSee) {
		this.toSee = toSee;
	}


	public List<BaseBean> getCostSheetDetailList() {
		return costSheetDetailList;
	}


	public void setCostSheetDetailList(List<BaseBean> costSheetDetailList) {
		this.costSheetDetailList = costSheetDetailList;
	}
	
}
