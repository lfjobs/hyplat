package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.CustomerAsk;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.TrackRelation;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

public class CompanyTrackAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private TrackRelation trackRelation;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private ContactCompany contactCompany;
	private List<BaseBean> beans;
	private List<BaseBean> customerAsk;
	private CustomerAsk cusAsk;
	private Staff cstaff;
	private String parameter;
	private String askID;
	private String sdate;
	private String edate;
	private String message;
	private String ccompanyID;
	private String result;
	private String contactConnectionsVal; //查询时客户类别传参'
	private String showType; //编辑类型  add添加  edit 编辑修改
	private String produce; //营销生产：company公司  group集团
	
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("Company", contactCompany);
		return getCompanyList();
	}
	public String searchAsk(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("Company", contactCompany);
		return getCustomerAskList();
	}
	public String getCompanyList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql_forStaff = "select cc.*,tr.contactconnections from dtcontactcompany cc left join dttrackrelation tr on tr.foreignkeyid=cc.ccompanyid  where tr.staffid = ?";
		List<Object> parms = new ArrayList<Object>();
		parms.add(account.getStaffID());
		hql_forStaff +=" and tr.status=?";
		parms.add("00");
		if (search != null && search.equals("search")) {
			contactCompany = (ContactCompany) session.get("Company");
			//单位名称
			if (null != contactCompany.getCompanyName()
					&& !"".equals(contactCompany.getCompanyName())) {
				hql_forStaff +=" and cc.companyName like ?";
				parms.add("%"+contactCompany.getCompanyName()+"%");
			}
			//单位地址
			if (null != contactCompany.getCompanyAddr()
					&& !"".equals(contactCompany.getCompanyAddr())) {
				hql_forStaff +=" and cc.companyAddr like ?";
				parms.add("%"+contactCompany.getCompanyAddr()+"%");
			}
			//信息录入人员姓名
			if(null !=contactCompany.getEntryPersonnel()
					&& !"".equals(contactCompany.getEntryPersonnel())){
				hql_forStaff +=" and cc.entryPersonnel like ?";
				parms.add("%"+contactCompany.getEntryPersonnel()+"%");
			}
			//负责人
			if (null != contactCompany.getCresponsible()
					&& !"".equals(contactCompany.getCresponsible())) {
				hql_forStaff +=" and cc.cresponsible like ?";
				parms.add("%"+contactCompany.getCresponsible()+"%");
			}
			//行业类别
			if (null != contactCompany.getIndustryType()
					&& !"".equals(contactCompany.getIndustryType())) {
				hql_forStaff +=" and cc.industryType like ?";
				parms.add("%"+contactCompany.getIndustryType()+"%");
			}
			//信息录入人员部门
			if (null != contactCompany.getEntryoName()
					&& !"".equals(contactCompany.getEntryoName())) {
				hql_forStaff +=" and cc.entryoName like ?";
				parms.add("%"+contactCompany.getEntryoName()+"%");
			}
			if(null != contactConnectionsVal && !"".equals(contactConnectionsVal)){
				hql_forStaff +=" and tr.contactconnections like ?";
				parms.add("%" + contactConnectionsVal + "%");
			}
				
		}
		hql_forStaff+=" order by tr.collectdate desc";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber),hql_forStaff,"select count(1) "
				+ hql_forStaff.substring(hql_forStaff.indexOf("from")), parms.toArray());
		return "list";
		
	}
	public String getCompanyListByID(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}if(showType.equals("edit")){
			contactCompany =(ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ? ", new Object[]{contactCompany.getCcompanyID()});
			return "updatelist";
		}
		return "list";
	}
	public String getCompanySearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("contactCompany", contactCompany);
		return getcompanyForCashier();
	}
	
	/**
	 * 支持往来单位的查询
	 * @return
	 */
	public String getcompanyForCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class);
		if (search != null && search.equals("search")) {
			contactCompany = (ContactCompany) session.get("contactCompany");
			if (contactCompany.getCompanyName() != null&& !"".equals(contactCompany.getCompanyName())) {
				dc.add(Restrictions.like("companyName", contactCompany.getCompanyName(),MatchMode.ANYWHERE));
			}
			if(contactCompany.getCompanyAddr()!=null&&!"".equals(contactCompany.getCompanyAddr())) {
				dc.add(Restrictions.like("companyAddr", contactCompany.getCompanyAddr(),MatchMode.ANYWHERE));
			}
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), dc);
		return "getcompanyForCashier";
	}
	
	public String getCompanybyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		int message=0;
		TrackRelation tRelation=(TrackRelation)baseBeanService.getBeanByHqlAndParams("from TrackRelation where staffID=? and companyID=? and foreignKeyID=?", new Object[]{account.getStaffID(),account.getCompanyID(),contactCompany.getCcompanyID()});
		if(tRelation!=null){
			message=1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("co", message);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 进入菜单树页面
	 * @return
	 */
	public String getFileCustomerAdvisory(){
		if(null !=produce && produce.equals("Company")){
			return "cListpage";
		}
		if(null !=produce && produce.equals("group")){
			return "gListpage";
		}
		return "listpage";
	}
	//客户咨询列表
	@SuppressWarnings("unchecked")
	public String getCustomerAskList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "select * from dtCustomerAsk where 1 = 1 ";
		List<Object> parms = new ArrayList<Object>();
		if (search != null && search.equals("search")) {
			//单位名称
			if (null != cusAsk.getCustomerName() && !"".equals(cusAsk.getCustomerName())) {
				sql +=" and customerName like ? ";
				parms.add("%"+cusAsk.getCustomerName()+"%");
			}
				
		}
		sql+=" order by askType,createTime desc";
		customerAsk=baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber),sql,"select count(1) " + sql.substring(sql.indexOf("from")),parms.toArray());
		return "aksList";
	}
	/**
	 * 保存
	 * 
	 * @return
	 */
	public String saveCompanyTrack() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		contactCompany =(ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ? ", new Object[]{ccompanyID});
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		int message=0;
		message=baseBeanService.getConutByByHqlAndParams("select count(*)  from TrackRelation where staffID=? and companyID=? and foreignKeyID=?", new Object[]{account.getStaffID(),account.getCompanyID(),contactCompany.getCcompanyID()});
		if(message<=0){	
			String organizationID = (String) session.get("organizationID");
			TrackRelation trackRelation=new TrackRelation();
			trackRelation.setTrackrelationID(serverService.getServerID("trackRelation"));
			trackRelation.setAddress(contactCompany.getAddress());
			trackRelation.setCollectdate(new Date());
			trackRelation.setForeignKeyID(contactCompany.getCcompanyID());
			trackRelation.setCompanyID(account.getCompanyID());
			trackRelation.setStatus("00");
			trackRelation.setOrganizationID(organizationID);
			trackRelation.setStaffID(account.getStaffID());
			parameter = "添加收集个人（人员姓名：:" + contactCompany.getCompanyName()+ ")";
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans = new ArrayList<BaseBean>();
			beans.add(logBook);
			beans.add(trackRelation);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("c", message);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 添加修改页
	 * @return
	 */
	public String toSaveJsp(){
		String hql = "from ContactCompany where ccompanyID = ?";
		Object[] param = {ccompanyID};
		contactCompany = (ContactCompany)baseBeanService.getBeanByHqlAndParams(hql,param );	
		if(!showType.equals("add")){
			String contactid = "";
			if(contactCompany.getCcompanyID().contains(",")){
				contactid = contactCompany.getCcompanyID().split(",")[0];
			}else{
				contactid = contactCompany.getCcompanyID();
			}
			trackRelation =(TrackRelation) baseBeanService.getBeanByHqlAndParams("from TrackRelation where foreignKeyID = ? ", new Object[]{contactid});
		}
		return "toSaveJsp";
	}
	//双击客户咨询
	public String checkAsk(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from CustomerAsk where askID = ?";
		Object[] param = {ccompanyID};
		cusAsk = (CustomerAsk)baseBeanService.getBeanByHqlAndParams(hql,param );	
		return "checkCustomerAsk";
	}
	//处理客户咨询
	public String updateCustomerAsk(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "update dtcustomerask set (asktype)=('1') where askID=?";
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{cusAsk.getAskID()});
		return "success";
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getContactConnectionsVal() {
		return contactConnectionsVal;
	}
	public void setContactConnectionsVal(String contactConnectionsVal) {
		this.contactConnectionsVal = contactConnectionsVal;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public TrackRelation getTrackRelation() {
		return trackRelation;
	}
	public void setTrackRelation(TrackRelation trackRelation) {
		this.trackRelation = trackRelation;
	}
	public Staff getCstaff() {
		return cstaff;
	}
	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public List<BaseBean> getCustomerAsk() {
		return customerAsk;
	}
	public void setCustomerAsk(List<BaseBean> customerAsk) {
		this.customerAsk = customerAsk;
	}
	public CustomerAsk getCusAsk() {
		return cusAsk;
	}
	public void setCusAsk(CustomerAsk cusAsk) {
		this.cusAsk = cusAsk;
	}
	public String getAskID() {
		return askID;
	}
	public void setAskID(String askID) {
		this.askID = askID;
	}
	
	
}
