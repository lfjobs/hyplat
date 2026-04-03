package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.CustomerManage;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 客户管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class CustomerManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	
	private CustomerManage customerManage;
	private Staff staff;
	
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	
	private String staffid;
	private String goodsid;
	private String status1;
	private String interest;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	
	public List<CCode> customersList;
	private Map<String, CustomerManage> customermap;
	private String type;
	
	/**************************************咨询客户汇总管理********************************************/
	/**
	 * 查询潜在客户
	 * @return
	 */
	public String toSearchPotentialSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("staff", staff);
		return getPotentialListSummary();
	}
	
	/**
	 * 潜在客户列表
	 * @return
	 */
	public String getPotentialListSummary(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getPotentialListSummary(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params);
		return "potentiallistSummary";
	}
	
	/**
	 * 潜在客户列表、查询调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getPotentialListSummary(Map<String, Object> session,CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = "from Staff s where exists (select c.staffID from CustomerManage c "
				+ " where c.staffID = s.staffID and c.companyID = ? and codeID = ?)";
		/**
		 * @param interest = ? 为客户兴趣  为固定类别列表区别于动态的 code集合中codeID
		 */
		if(!status1.contains("code")){
			 hql = "from Staff s where exists (select c.staffID from CustomerManage c "
					+ " where c.staffID = s.staffID and c.companyID = ? and interest = ?)";
		}
		/**
		 * @param interest = ? 为客户兴趣  为固定类别列表区别于动态的 code集合中codeID
		 */
		parms.add(account.getCompanyID());
		parms.add(status1);
		
		if (search != null && search.equals("search")){
			Staff sta = (Staff)session.get("staff");
			if(sta.getStaffName() != "" 
					&& !"".equals(sta.getStaffName())){
				hql += " and s.staffName like ?";
				parms.add("%" + sta.getStaffName() + "%");
			}
			if(sta.getStaffIdentityCard() != "" 
					&& !"".equals(sta.getStaffIdentityCard())){
				hql += " and s.staffIdentityCard like ?";
				parms.add("%" + sta.getStaffIdentityCard() + "%");
			}
		}
		hql += " order by s.staffID";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	public String showExcelPotentialListSummary(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getPotentialListSummary(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		excelStream = excelService.showExcel(Staff.columnHeadings2(),
				 baseBeanService.getListBeanByHqlAndParams(hql, params));
		return "showexcel";
	}
	/**************************************个人客户管理********************************************/
	/**
	 * 查询客户管理
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("customerManage", customerManage);
		return getCustomerList();
	}

	/**
	 * 客户管理列表
	 * @return
	 */
	public String getCustomerList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params);
		if(!status1.equals("03")){
			if(status1.equals("00")){ //客户分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306059vd8t6qypi0000000002");
			}else if(status1.equals("01")){ //潜在客户分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306069vd8t6qypi0000000029");
			}else{ //02:客户来源分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306069vd8t6qypi0000000040");
			}
		}
		return "list";
	}
	
	/**
	 * 客户管理列表调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = "from CustomerManage where companyID = ? and staffID = ? and status = ?";
		parms.add(account.getCompanyID());
		parms.add(staffid);
		parms.add(status1);
		
		if (search != null && search.equals("search")){
			CustomerManage customer = (CustomerManage)session.get("customerManage");
			if(customer.getGoodsName() != "" 
					&& !"".equals(customer.getGoodsName().trim())){
				hql += " and goodsName like ?";
				parms.add("%" + customer.getGoodsName().trim() + "%");
			}
			if(status1.equals("03")){
				if(customer.getInterest() != "" 
						&& !"".equals(customer.getInterest())){
					hql += " and interest = ?";
					parms.add(customer.getInterest());
				}
			}else{
				if(customer.getCodeID() != "" 
						&& !"".equals(customer.getCodeID())){
					hql += " and codeID = ?";
					parms.add(customer.getCodeID());
				}
			}
		}
		hql += " order by customerManageID";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 保存/修改
	 * 
	 * @return
	 */
	public String saveCustomer() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String parmers = new String();
		
		if(customermap != null){
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			for(CustomerManage customer : customermap.values()){
				customer.setCompanyID(account.getCompanyID());
				if(customer.getCustomerManageID() == null){
					customer.setCustomerManageID(serverService.getServerID("customerManage"));
					if(status1.equals("00")){
						parmers = "添加客户分类管理：" + customer.getCodeName();
					}else if(status1.equals("01")){
						parmers = "添加潜在客户需求：" + customer.getCodeName();
					}else if(status1.equals("02")){
						parmers = "添加客户来源管理：" + customer.getCodeName();
					}else{
						parmers = "添加对产品  " + customer.getGoodsName() + " 的兴趣";
					}
				}else{
					if(status1.equals("00")){
						parmers = "修改客户分类管理：" + customer.getCodeName();
					}else if(status1.equals("01")){
						parmers = "修改潜在客户需求：" + customer.getCodeName();
					}else if(status1.equals("02")){
						parmers = "修改客户来源管理：" + customer.getCodeName();
					}else{
						parmers = "修改对产品  " + customer.getGoodsName() + " 的兴趣";
					}
				}
				CLogBook logBook = logBookService.saveCLogBook(
						organizationID, parmers , account);
				baseBeanList.add(customer);
				baseBeanList.add(logBook);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,null, null);
		}
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delCustomer() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> baseBean = new ArrayList<BaseBean>();
		List<Object[]> parmsList = new ArrayList<Object[]>();
		String[] hqls = { "delete CustomerManage where customerManageID = ?" };
		Object[] parms = { customerManage.getCustomerManageID() };
		parmsList.add(parms);
		String parmers = new String();
		if(status1.equals("00")){
			parmers = "删除客户分类管理";
		}else if(status1.equals("01")){
			parmers = "删除潜在客户需求";
		}else if(status1.equals("02")){
			parmers = "删除客户来源管理";
		}else{
			parmers = "删除客户产品兴趣";
		}
		CLogBook logBook = logBookService.saveCLogBook(
				organizationID, parmers , account);
		baseBean.add(logBook);
		baseBeanService.executeHqlsByParamsList(baseBean, hqls, parmsList);
		return "success";
	}
	
	/**************************************潜在客户管理********************************************/
	/**
	 * 查询潜在客户
	 * @return
	 */
	public String toSearchPotential() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("staff", staff);
		return getPotentialList();
	}
	
	/**
	 * 潜在客户列表
	 * @return
	 */
	public String getPotentialList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getPotentialList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params);
		return "potentiallist";
	}
	
	/**
	 * 潜在客户列表、查询调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getPotentialList(Map<String, Object> session,CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = "from Staff s where exists (select c.staffID from CustomerManage c "
				+ " where c.staffID = s.staffID and c.companyID = ? and c.goodsID = ? and codeID = ?)";
		parms.add(account.getCompanyID());
		parms.add(goodsid);
		parms.add(status1);
		
		if (search != null && search.equals("search")){
			Staff sta = (Staff)session.get("staff");
			if(sta.getStaffName() != "" 
					&& !"".equals(sta.getStaffName())){
				hql += " and s.staffName like ?";
				parms.add("%" + sta.getStaffName() + "%");
			}
			if(sta.getStaffIdentityCard() != "" 
					&& !"".equals(sta.getStaffIdentityCard())){
				hql += " and s.staffIdentityCard like ?";
				parms.add("%" + sta.getStaffIdentityCard() + "%");
			}
		}
		hql += " order by s.staffID";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	/**************************************客户分类管理********************************************/
	/**
	 * 查询客户分类
	 * @return
	 */
	public String toSearchSort() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("staff", staff);
		return getSortList();
	}
	
	/**
	 * 客户分类列表
	 * @return
	 */
	public String getSortList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getSortList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		String sqlcount = " select count(*) " + sql.substring(sql.indexOf("from"));
		
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount, params);
		if(!status1.equals("03")){
			if(status1.equals("00")){ //客户分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306059vd8t6qypi0000000002");
			}else if(status1.equals("01")){ //潜在客户分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306069vd8t6qypi0000000029");
			}else{ //02:客户来源分类
				customersList = codeService.getCCodeListByPID(account
						.getCompanyID(), "scode201306069vd8t6qypi0000000040");
			}
		}
		return "sortlist";
	}
	
	/**
	 * 客户分类列表、查询调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getSortList(Map<String, Object> session,CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		
		String sql = "select c.codeName,c.interest,s.staffCode,s.recordCode,s.staffName,s.usedNmae,s.sex,"
				+ " s.birthday,s.nativePlace,s.nationality,s.nation,s.staffIdentityCard,s.reference,s.passportNum"
				+ " from dt_hr_staff s left join dt_customermanage c on c.staffID = s.staffID"
				+ " where c.companyID = ? and c.goodsID = ? and c.status = ?";
		parms.add(account.getCompanyID());
		parms.add(goodsid);
		parms.add(status1);
		
		if (search != null && search.equals("search")){
			Staff sta = (Staff)session.get("staff");
			if(sta.getStaffDesc() != ""
					&& !"".equals(sta.getStaffDesc())){
				if(status1.equals("03")){
					sql += " and c.interest = ?";
				}else{
					sql += " and c.codeID = ?";
				}
				parms.add(sta.getStaffDesc());
			}
			if(sta.getStaffName() != "" 
					&& !"".equals(sta.getStaffName().trim())){
				sql += " and s.staffName like ?";
				parms.add("%" + sta.getStaffName().trim() + "%");
			}
			if(sta.getStaffIdentityCard() != "" 
					&& !"".equals(sta.getStaffIdentityCard().trim())){
				sql += " and s.staffIdentityCard like ?";
				parms.add("%" + sta.getStaffIdentityCard().trim() + "%");
			}
		}
		sql += " order by c.codeID,c.interest";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	
	/**************************************客户信息管理********************************************/
	/**
	 * 查询客户信息
	 * @return
	 */
	public String toSearchMessage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("staff", staff);
		return getMessageList();
	}
	
	/**
	 * 客户信息列表
	 * @return
	 */
	public String getMessageList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getMessageList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[])list.get(1);
		String sqlcount = " select count(*) " + sql.substring(sql.indexOf("from"));
		
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount, params);
		return "messagelist";
	}
	
	/**
	 * 客户信息列表、查询调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getMessageList(Map<String, Object> session,CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String sql = "";
		String sql1 = "";
		
		if(status1.equals("con")){ //联系方式
			sql = "select t.staffName,s.contactWay,s.contactName,s.contactDate,"
				+ " sc.codevalue,s.assessor,s.assessorCode,s.assessorDate,s.contactDesc"
				+ " from dt_hr_staff_contact s"
				+ " left join dtscode sc on sc.codeid = s.contactType";
			
		}else if(status1.equals("com")){ //所属单位
			sql = "select t.staffName,co.companyName,co.companyAddr,co.companyTel,"
				+ " co.cresponsible,co.responsibleTel,co.industryType,co.remark"
				+ " from dtCS s left join dtContactCompany co on co.ccompanyid = s.companyid";
			
		}else if(status1.equals("tra")){ //客户跟踪
			sql = "select t.staffName,s.inputDate,s.TrackStartdate,s.TarckEnddate,"
				+ " s.workAddr,s.serviceMode,s.trackContent,s.trackReason,s.handleStatus"
				+ " from dttrack s";
			
		}else if(status1.equals("tel")){ //呼叫信息
			sql = "select t.staffName,s.telNumber,s.customName,s.recordContent,case when s.isDeal = '0'"
				+ " then '否' else '是' end,s1.companyname,s.dealUser,s.dealDate,s.dealContent"
				+ " from dt_tel_telinrecord s";
			
		}else{ //个人证件
			sql = "select t.staffName,s.invalidateStart,s.invalidateEnd,s.credentialsName,s.credentialsType,"
				+ " s.credentialsNo,s.recordsNumber,s.address,s.organ,s.credentialsrecordNo,s.appendixNumber,"
				+ " s.auditor,s.auditorNumber,s.invalidate,s.credentialsDesc,s.photo" 
				+ " from dt_hr_staff_Certificate s";
		}
		if(status1.equals("tel")){
			sql1 = " left join dt_tel_telinrecorddeal s1 on s1.telinrecordid = s.id"
			    + " left join dt_hr_staff t on t.staffid = s.customId"
				+ " where exists (select DISTINCT c.staffID from dt_CustomerManage c"
				+ " where c.staffID = s.customId and c.companyID = ? and c.goodsID = ?)";
		}else{
			sql1 = " left join dt_hr_staff t on t.staffid = s.staffid"
				+ " where exists (select DISTINCT c.staffID from dt_CustomerManage c"
				+ " where c.staffID = s.staffID and c.companyID = ? and c.goodsID = ?)";
		}
		parms.add(account.getCompanyID());
		parms.add(goodsid);
		
		if (search != null && search.equals("search")){
			Staff sta = (Staff)session.get("staff");
			if(sta.getStaffName() != "" 
					&& !"".equals(sta.getStaffName().trim())){
				sql1 += " and t.staffName like ?";
				parms.add("%" + sta.getStaffName().trim() + "%");
			}
		}
		sql1 += " order by t.staffID";
		results.add(sql+sql1);
		results.add(parms.toArray());
		return results;
	}
	
	public CustomerManage getCustomerManage() {
		return customerManage;
	}

	public void setCustomerManage(CustomerManage customerManage) {
		this.customerManage = customerManage;
	}
	
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
	
	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public Map<String, CustomerManage> getCustomermap() {
		return customermap;
	}

	public void setCustomermap(Map<String, CustomerManage> customermap) {
		this.customermap = customermap;
	}

	public List<CCode> getCustomersList() {
		return customersList;
	}

	public void setCustomersList(List<CCode> customersList) {
		this.customersList = customersList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}