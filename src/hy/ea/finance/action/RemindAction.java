package hy.ea.finance.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 	提醒功能
 */
public class RemindAction {
	private static final Logger logger = LoggerFactory.getLogger(RemindAction.class);
	@Resource
	private BaseBeanService baseBeanService;//基本的方法
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;//操作日志
	private String parameter;
	private Remind remind;//提醒实体类
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private List<BaseBean> beans;
	private String receiveDate;
	private String partnerID;//接收人id
	private String partnerName;//接收人name
	
	private String oid;//传过来的组织机构ID
	public String checkOrgID; //选中部门ID传参
	public String title1;
	private Staff searchCStaff;// 保存人员搜索信息
	private String[] args;
	/**
	 * 消息模块儿本公司在职人员的查询
	 * 
	 * @version xyz 2014-6-5
	 * @return
	 */
	public String getStaffForCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String currentOID = session.get("organizationID").toString();
		String hql = "";
		Object[] params = null;
		
		params =new Object[]{ account.getCompanyID(), "50",currentOID };
		//财务单据查询部门及子部门下所有人员
		if(title1!=null&&"title1".equals(title1)){
			hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ?)";
			params =new Object[]{ account.getCompanyID(), "50"};
		}
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID(),currentOID);
			return "staffForCashier";
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber), hql,
				params);
		return "staffForCashier";
	}

	/**
	 * 消息模块儿本公司在职人员条件查询
	 * 
	 * @version xyz 2014-6-5
	 * @return
	 */
	public String toSearchStaffForCashier() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffForCashier();
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID,String OID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql = "";
		String hql1 = "";
		String hql2 = " staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		
		if(title1!=null&&"title1".equals(title1)){
		    hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ?) and";
		}
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}
		Object[] params = { args[3], companyID, OID,
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		
		if(title1!=null&&"title1".equals(title1)){
			params = new Object[]{ companyID,args[3],
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber), hql,
				params);
	}
	
	
	//查询所有提醒信息
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", remind);
		return getListRemind();
	}
	public String getListRemind(){
		   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
			return "reminds";
	}
	//条件查询的方法
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Remind.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		dc.addOrder(Order.desc("addDate"));
		
		if (search != null && search.equals("search")) {
			remind = (Remind) session.get("tablesearch");
		
		if (!"".equals(remind.getCircularTitle())){
				dc.add(Restrictions.like("circularTitle",remind.getCircularTitle(),MatchMode.ANYWHERE));
		}
		if (!"".equals(remind.getStaffName())){
			dc.add(Restrictions.like("staffName",remind.getStaffName(),MatchMode.ANYWHERE));
		}
	}
		return dc;
}
	
	private DetachedCriteria serachHistoryaa() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Remind.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", account.getStaffID()));
		dc.add(Restrictions.or(Restrictions.eq("remindStatus","02"),Restrictions.eq("remindStatus", "03") ));
		dc.addOrder(Order.desc("addDate"));
		return dc;
}
	//查看当前责任人的历史信息
	public String serachHistory(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber),serachHistoryaa());
		return "historylist";
		
	}
	//提醒信息的添加和修改
	public String addremind() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (remind.getRemindID() == null
				|| "".equals(remind.getRemindID())) {
			parameter = "添加消息提醒(接收人:"+remind.getStaffName()+")";
		}
		else
		{
		 parameter = "修改消息提醒(接收人:"+remind.getStaffName()+")";
		}
		beans = new ArrayList<BaseBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(receiveDate);
		remind.setReceiveDate(date);
		remind.setCompanyID(account.getCompanyID());
		remind.setOrganizationID(organizationID);
		//后台字符串时间转换为date类型的
	    Date dt=new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tt=matter.format(dt);
        Date  ttt= matter.parse(tt);
	    remind.setAddDate(ttt);
	    if(partnerID!=""&&partnerName!=""){
	    	for(int i=0,j=0;i<partnerID.split(",").length&&j<partnerName.split(";").length;i++,j++){
	    		String id=partnerID.split(",")[i];
	    		String name=partnerName.split(";")[j];
	    		Remind remind1=new Remind();
	    		remind1.setRemindID(serverService.getServerID("remind"));
	    		remind1.setCompanyID(remind.getCompanyID());
	    		remind1.setOrganizationID(organizationID);
	    		remind1.setCircularText(remind.getCircularText());
	    		remind1.setCircularTitle(remind.getCircularTitle());
	    		remind1.setCircularType(remind.getCircularType());
	    		remind1.setUrlType(remind.getUrlType());
	    		remind1.setDetailedurl(remind.getDetailedurl());
	    		remind1.setStaffID(id);
	    		remind1.setStaffName(name);
	    		remind1.setReceiveDate(remind.getReceiveDate());
	    		remind1.setRemindType(remind.getRemindType());
	    		remind1.setRemindStatus(remind.getRemindStatus());
	    		remind1.setAddDate(remind.getAddDate());
	    		beans.add(remind1);
	    	}
	    	
	    }
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	//删除提醒信息
	public String delremind() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] params = {remind.getRemindID()};
		String hql2="from Remind where remindID=?";
		Remind re=(Remind) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除消息提醒(接收人:"+re.getStaffName()+")", account);
	    beans.add(logBook);
	    String hql = "delete from Remind where remindID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "success";
	}
	
	//修改状态
	 	public String upRemind(String remindID) {
	 		String hql="update dtremind r set r.remindStatus=? where r.remindID=?";
	 		String[] hqls = new String[] { hql };
	 		Object[] gparams = {"02",remindID};
	 		List<Object[]> parmsList = new ArrayList<Object[]>();
	 		parmsList.add(gparams);
	 		Boolean bl=null;
	 		try {
	 			baseBeanService.executeSqlsByParmsList(null, hqls, parmsList);
	 			bl=true;
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			//logger.error("操作异常", e);
	 			bl=false;
	 		}
	 		return "reminds";
	 	}
	public Remind getRemind() {
		return remind;
	}
	public void setRemind(Remind remind) {
		this.remind = remind;
	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCheckOrgID() {
		return checkOrgID;
	}

	public void setCheckOrgID(String checkOrgID) {
		this.checkOrgID = checkOrgID;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}
	public void setArgs(String[] args) {
		this.args = args[0].split("_");
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
}
