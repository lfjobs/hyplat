package hy.ea.company.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 往来单位
 *@author 陈小丰
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ContactConnectionAction {
	private static final Logger logger = LoggerFactory.getLogger(ContactConnectionAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private ContactCompanyView cview;
	private List<CCode> typelist;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private List<CCode> connectionlist;
	
	private String type;
	private String title;
	public InputStream excelStream;
	private String typemes;//短信的查询的分类；挺恶心的一个东西╮(╯▽╰)╭
	 //根据条件查询往来单位 
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String industryType ="";
		String contactConnections="";
		try {
			if(cview!=null&&cview.getIndustryType()!=null&&!cview.getIndustryType().equals("")){
			   industryType = java.net.URLDecoder.decode(cview.getIndustryType(),"UTF-8");
			 }
			if(cview!=null&&cview.getContactConnections()!=null&&!cview.getContactConnections().equals("")){
	          contactConnections = java.net.URLDecoder.decode(cview.getContactConnections(),"UTF-8");
			}
			cview.setIndustryType(industryType);
			cview.setContactConnections(contactConnections);
			session.put("tablesearch", cview);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return getListContactConnection();
	}
	// 删除某条往来单位
	public String delContactConnection() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String[]  contactConnectionID=cview.getContactConnectionID().split(",");
		List<Object[]> parms = new ArrayList<Object[]>();
		String[] hqls = new String[contactConnectionID.length];
		for (int i = 0; i < contactConnectionID.length; i++) {
			hqls[i]="delete from  ContactConnection  where contactConnectionID = ? and companyID = ?";
			Object[] parm = {contactConnectionID[i],account.getCompanyID()};
			parms.add(parm);
		}
		baseBeanService.executeHqlsByParamsList(null, hqls, parms);	
		return "success";
	}
	
	public DetachedCriteria getListBYDC()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(ContactCompanyView.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		
		if(cview!=null&&cview.getCompanyID()!=null&&!cview.getCompanyID().equals("")&&typemes!=null&&typemes.equals("tree")){
			dc.add(Restrictions.eq("companyID", cview.getCompanyID()));
		}else if(typemes!=null&&typemes.equals("waicha")){
			 
			 
		 }else{
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		}
		if (search != null && search.equals("search")) {
			this.cview = (ContactCompanyView) session.get("tablesearch");
			if(null !=cview.getCompanyName() && !"".equals(cview.getCompanyName())){
				dc.add(Restrictions.like("companyName", cview.getCompanyName(),MatchMode.ANYWHERE));
			}
			if(null != cview.getCresponsible() && !"".equals(cview.getCresponsible()))
			{
				dc.add(Restrictions.like("cresponsible", cview.getCresponsible(),MatchMode.ANYWHERE));
			}
			if(null != cview.getCompanyAddr()&& !"".equals(cview.getCompanyAddr())){
				dc.add(Restrictions.like("companyAddr", cview.getCompanyAddr(),MatchMode.ANYWHERE));
			}
			if(null!=cview.getIndustryType()&&!"".equals(cview.getIndustryType()))
			{
				try {
					String industryType = java.net.URLDecoder.decode(cview.getIndustryType(),"UTF-8");
					dc.add(Restrictions.eq("industryType", industryType));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
			}
			if(null!=cview.getContactConnections()&&!"".equals(cview.getContactConnections()))
			{
				dc.add(Restrictions.like("contactConnections", cview.getContactConnections(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	//往来单位列表
	public String getListContactConnection() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20110106hfjes5ucxp0000000003");
		connectionlist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		if(pageForm!=null)
		{
			session.put("RecordCount", pageForm.getRecordCount());
		}else{
			session.put("RecordCount", 0);
		}
		return "list";
	}
	
	//往来单位
	public String getContactConnection() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20110106hfjes5ucxp0000000003");
		connectionlist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getListBYDC());
		return "relation";
	}
	
	/**
	 * 导出接待单位列表
	 * @param : account organizationID
	 * @return : showexcel
	 */
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(ContactCompanyView.columnHeadings(), baseBeanService.getListByDC(getListBYDC()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出单位列表", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
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
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public ContactCompanyView getCview() {
		return cview;
	}
	public void setCview(ContactCompanyView cview) {
		this.cview = cview;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
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
	public String getTypemes() {
		return typemes;
	}
	public void setTypemes(String typemes) {
		this.typemes = typemes;
	}
	
	
}
