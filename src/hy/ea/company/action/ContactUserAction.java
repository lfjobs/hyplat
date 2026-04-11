package hy.ea.company.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 往来个人
 *@author 陈小丰
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ContactUserAction {
	private static final Logger logger = LoggerFactory.getLogger(ContactUserAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private ContactUser contactUser;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private List<CCode> codeRelationList;
	public InputStream excelStream;
	public String statusFor;//教务处传值状态
	private CashierBills cashierBills;
	private String type;
	private String title;
	private String companyName;
	private String typemes;//短信的查询的分类；挺恶心的一个东西╮(╯▽╰)╭
	/**
	 * 学员报开学 证件是否合格标识
	 */
	private String dataIsComplete;


	//根据条件查询往来个人 
	public String toSearch() {
		String Relation="";
		try {
			if(contactUser.getRelation()!=null&&!contactUser.getRelation().equals("")){
			  Relation = java.net.URLDecoder.decode(contactUser.getRelation(),"UTF-8");
			}
			Map<String, Object> session = ActionContext.getContext().getSession();
			contactUser.setRelation(Relation);
			
			session.put("tablesearch", contactUser);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return getListContactUser();
	}
	// 删除某条往来个人
	public String delContactUser() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String [] relationID=contactUser.getRelationID().split(",");
		List<Object[]> parms = new ArrayList<Object[]>();
		String[] hqls = new String[relationID.length];
		for (int i = 0; i < relationID.length; i++) {
			hqls[i] = "delete from  ContactRelation  where relationID = ? and companyID = ?";
			Object[] parm = {relationID[i],account.getCompanyID()};
			parms.add(parm);
		}
		baseBeanService.executeHqlsByParamsList(null, hqls, parms);
		return "success";
	}
	/**
	 * 根据往来个人查询银行账户,qq,邮箱，联系方式
	 * @return
	 */
	public String getListRegistrationUser() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { contactUser.getStaffID()};
		 //查询银行账户
		 String hql="from StaffBankAccount where staffID= ?";
		 List<BaseBean> bankList=baseBeanService.getListBeanByHqlAndParams(hql, params);
		 //查询qq
		 String hql1="from StaffContact where  staffID=? and contactType=?";
		 List<BaseBean> qqList=baseBeanService.getListBeanByHqlAndParams(hql1,new Object[]{contactUser.getStaffID(),"scode20130507gcjqakbweq0000000006"});
		 //查询邮箱
		 String hql2="from StaffContact where  staffID=? and contactType=?";
		 List<BaseBean> emailList=baseBeanService.getListBeanByHqlAndParams(hql2,new Object[]{contactUser.getStaffID(),"scode20100426c8rdqacjae0000000002"});
		 //查询地址
		 String hql3="from StaffAddress where staffID= ?";
		 List<BaseBean> aList=baseBeanService.getListBeanByHqlAndParams(hql3, params);
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<String> list=new ArrayList<String>();
			if(aList.size()>0){
			for(int i=0;i<aList.size();i++){
				StaffAddress staffAddress=(StaffAddress)aList.get(i);
				list.add(staffAddress.getAddressDetailed());
			}
				map.put("Arraylist", list);
			}

		map.put("bankList", bankList);
		map.put("qqList", qqList);
		map.put("emailList", emailList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	public DetachedCriteria getListBYDC()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(ContactUser.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		 codeRelationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110106hfjes5ucxp0000000017");

		if(contactUser!=null&&contactUser.getCompanyID()!=null&&!contactUser.getCompanyID().equals("")&&typemes!=null&&typemes.equals("tree")){
			dc.add(Restrictions.eq("companyID", contactUser.getCompanyID()));
			
		 }else if(typemes!=null&&typemes.equals("waicha")){
			 
			 
		 }else{
			 
				dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		 }
		if (search != null && search.equals("search")) {
			this.contactUser = (ContactUser) session.get("tablesearch");
			//logger.info("值：{}", contactUser);
			if(contactUser.getStaffName()!=null&&!"".equals(contactUser.getStaffName()))
			{
				dc.add(Restrictions.like("staffName", contactUser.getStaffName(),MatchMode.ANYWHERE));
			}
			if(contactUser.getStaffIdentityCard()!=null&&!"".equals(contactUser.getStaffIdentityCard()))
			{
				dc.add(Restrictions.like("staffIdentityCard", contactUser.getStaffIdentityCard(),MatchMode.ANYWHERE));
			}
			if(contactUser.getStaffAddress()!=null&&!"".equals(contactUser.getStaffAddress()))
			{
				dc.add(Restrictions.like("staffAddress", contactUser.getStaffAddress(),MatchMode.ANYWHERE));
			}
			if(contactUser.getReference()!=null&&!"".equals(contactUser.getReference()))
			{
				dc.add(Restrictions.like("reference", contactUser.getReference(),MatchMode.ANYWHERE));
			}
			if(contactUser.getStaffCode()!=null&&!"".equals(contactUser.getStaffCode()))
			{
				dc.add(Restrictions.like("staffCode", contactUser.getStaffCode(),MatchMode.ANYWHERE));
			}
			if(contactUser.getRelation()!=null&&!"".equals(contactUser.getRelation()))
			{
				dc.add(Restrictions.like("relation", contactUser.getRelation(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	
	//往来个人列表
	public String getListContactUser() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		if(pageForm!=null)
		{
			session.put("RecordCount", pageForm.getRecordCount());
		}else{
			session.put("RecordCount", 0);
		}
		return "list";
	}
	
	//往来关系
	public String getRelationList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		return "relation";
	}
	
	/**
	 * 根据往来个人名称模糊查询列表
	 * @return
	 */
	public String getListContactUserBycontactUserName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from ContactUser c where c.staffName like ? and c.relation like ?  and c.companyID = ? ";
		if(statusFor!=null&&statusFor.equals("relation")){
			hql += " and c.staffID in (select t.contactUserID from CashierBills t where t.depStatue='01' and t.companyID = '" + account.getCompanyID() + "') ";
		}
		/*start 学员证件是否齐全**/
		if("00".equals(dataIsComplete)){
			hql += " and exists  ( select dp.studentid from DtDrivingPrincipal dp where c.staffID=dp.studentid and dp.dataIsComplete='00' and dp.studentCode is null )";
		}
		/*end 学员证件是否齐全**/
		Object[] parms = {"%"+contactUser.getStaffName()+"%","%"+contactUser.getRelation()+"%",account.getCompanyID()} ;
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql,parms);
		 codeRelationList = codeService.getCCodeListByPID(account
					.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("codeRelationList", codeRelationList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 根据单据编号查询往来个人-学员
	 * @return
	 */
	public String getOneContactUserByJournalNum() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from ContactUser where companyID = ? ";
			   hql += " and staffID in (select t.contactUserID from CashierBills t where t.depStatue='01' " +
			   		"and companyID = '" + account.getCompanyID() + "' and t.journalNum='"+ cashierBills.getJournalNum() +"') ";		
		Object[] parms = {account.getCompanyID()} ;
		contactUser=(ContactUser) baseBeanService.getBeanByHqlAndParams(hql, parms);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contactUser", contactUser);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 导出往来个人
	 * @param : account organizationID
	 * @return : showexcel
	 */
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(ContactUser.columnHeadings(), baseBeanService.getListByDC(getListBYDC()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出往来个人", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
	
	public ContactUser getContactUser() {
		return contactUser;
	}
	public void setContactUser(ContactUser ContactUser) {
		this.contactUser = ContactUser;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}
	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getStatusFor() {
		return statusFor;
	}
	public void setStatusFor(String statusFor) {
		this.statusFor = statusFor;
	}
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}
	 public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getTypemes() {
		return typemes;
	}
	public void setTypemes(String typemes) {
		this.typemes = typemes;
	}
	public String getDataIsComplete() {
		return dataIsComplete;
	}
	public void setDataIsComplete(String dataIsComplete) {
		this.dataIsComplete = dataIsComplete;
	}
	
}
