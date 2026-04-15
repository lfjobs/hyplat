package hy.ea.company.action;

/**
 * 往来个人
 *@author 陈小丰
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@Controller
@Scope("prototype")
public class MobileContactUserAction {
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
	 //根据条件查询往来个人 
	public String toSearch() {
		try {
			String Relation = java.net.URLDecoder.decode(contactUser.getRelation(),"UTF-8");
			Map<String, Object> session = ActionContext.getContext().getSession();
			contactUser.setRelation(Relation);
			session.put("tablesearch", contactUser);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * 根据往来个人查询银行账户
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankList", bankList);
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
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			this.contactUser = (ContactUser) session.get("tablesearch");
			//System.out.println(contactUser);
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
				try {
					String Relation = "";
					Relation = java.net.URLDecoder.decode(contactUser.getRelation(),"UTF-8");
					dc.add(Restrictions.eq("relation",Relation));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dc;
	}
	
	
	//往来个人列表
	public String getListContactUser() {
		if(pageNumber==0)
		{
			pageForm = baseBeanService.getPageFormByDC((pageNumber), (1),getListBYDC());
			return "list";
		}
		else
		{
			pageForm = baseBeanService.getPageFormByDC((pageNumber), (1),getListBYDC());
			HttpServletResponse response = ServletActionContext.getResponse();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(outString);
				//System.out.println(outString);
				response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;	
		}
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
		String hql = " from ContactUser where staffName like ? and relation like ?  and companyID = ? ";
		if(statusFor!=null&&statusFor.equals("relation")){
			hql += " and staffID in (select t.contactUserID from CashierBills t where t.depStatue='01' and companyID = '" + account.getCompanyID() + "') ";
		}
		Object[] parms = {"%"+contactUser.getStaffName()+"%","%"+contactUser.getRelation()+"%",account.getCompanyID()} ;
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql,parms);
		 codeRelationList = codeService.getCCodeListByPID(account
					.getCompanyID(), "scode20110106hfjes5ucxp0000000017");Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("codeRelationList", codeRelationList);
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
	
}
