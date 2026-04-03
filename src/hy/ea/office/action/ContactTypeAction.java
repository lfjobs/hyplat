package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.ContactType;
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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 往来单位的联系方式 
 * @author :m
 */
@Controller
@Scope("prototype")
public class ContactTypeAction {

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
	
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private ContactType contactType;
	private List<CCode> conTypeList;   //联系方式
	private List<CCode> conSortList;   //联系类别
	
	private Map<String,ContactType> contactTypeMap;
	private InputStream excelStream;
	
	//保存联系方式信息
	public String saveContactType(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		List<BaseBean> bBean = new ArrayList<BaseBean>();
		contactType = new ContactType();
		for(ContactType c:contactTypeMap.values()){
			this.contactType.setContactTypeID(c.getContactTypeID());
			if(null==c.getContactTypeID()||"".equals(c.getContactTypeID())){
				c.setContactTypeID(serverService.getServerID("contacttype"));
				parameter = "添加往来单位联系方式(联系号码:"+c.getConNum()+")";
			}else{
				String hql = "from ContactType where companyID = ? and  contactTypeID = ? ";
				ContactType con = (ContactType) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),c.getContactTypeID()});
				parameter = "修改往来单位联系方式(联系号码:"+con.getConNum()+")";
			}
			c.setCompanyID(companyID);
			CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
			bBean.add(c);
			bBean.add(cLogBook);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bBean, null, null);
		return "success";
	}
	//删除联系方式
	 public String delContactType()
	    {
		 	CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
			String hql = "delete ContactType where companyID = ? and contactTypeID = ? ";
			Object [] params = {account.getCompanyID(),contactType.getContactTypeID()};
			String hql2="from ContactType where companyID=?  and contactTypeID = ?"; 
			ContactType con = (ContactType) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),contactType.getContactTypeID()});
			CLogBook cLogBook = logBookService.saveCLogBook(null, "删除往来单位联系方式(联系号码:"+con.getConNum()+")", account);
			//baseBeanService.executeHqlByParams(hql, params);
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(cLogBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
		    return "success";
	    }
	 
	 //根据条件查询联系方式列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", contactType);
			return getContactTypeList();
		}
	 //联系方式列表
		public String getContactTypeList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "contacttypelist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			DetachedCriteria dc = DetachedCriteria.forClass(ContactType.class);
			dc.add(Restrictions.eq("ccompanyID", contactType.getCcompanyID())); 
			conTypeList = codeService.getCCodeListByPID(account.getCompanyID(),"scode201004233ern4m24yx0000000262");
			Map<String,String> map = new HashMap<String,String>();
			for(CCode c:conTypeList){
				map.put(c.getCodeID(), c.getCodeValue());
			}
			ContactType.setOMap(map);
			
			if (search != null && search.equals("search")) {
				contactType = (ContactType) session.get("tablesearch");
				if(null!=contactType.getConNum()&&!"".equals(contactType.getConNum()))
				{
					dc.add(Restrictions.like("conNum", contactType.getConNum(), MatchMode.ANYWHERE));
				} 
			} 
			return dc;
		}
		
		// 导出联系方式列表
		public String showContactTypeExcel() {
			excelStream = excelService.showExcel(ContactType.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook cLogBook = logBookService.saveCLogBook(null,"导出联系方式列表", account);
			baseBeanService.update(cLogBook);
			return "showexcel";
		}	
		
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		} 
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
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
		public ContactType getContactType() {
			return contactType;
		}
		public void setContactType(ContactType contactType) {
			this.contactType = contactType;
		}
		public Map<String, ContactType> getContactTypeMap() {
			return contactTypeMap;
		}
		public void setContactTypeMap(Map<String, ContactType> contactTypeMap) {
			this.contactTypeMap = contactTypeMap;
		}
		public List<CCode> getConTypeList() {
			return conTypeList;
		}
		public void setConTypeList(List<CCode> conTypeList) {
			this.conTypeList = conTypeList;
		}
		public List<CCode> getConSortList() {
			return conSortList;
		}
		public void setConSortList(List<CCode> conSortList) {
			this.conSortList = conSortList;
		} 
}
