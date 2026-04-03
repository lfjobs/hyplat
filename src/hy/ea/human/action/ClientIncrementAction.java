package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.ClientIncrement;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
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

/**
 * cxf
 * 客户增值服务办
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ClientIncrementAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private ClientIncrement clientIncrement;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private InputStream excelStream;
	private List<BaseBean> beans;
	
	//保存客户增值服务办
	public String saveClientIncrement(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		beans = new ArrayList<BaseBean>();
		String organizationID = (String) map.get("organizationID");
		if(null==clientIncrement.getClientIncrementID()||"".equals(clientIncrement.getClientIncrementID())){
			clientIncrement.setClientIncrementID(serverService.getServerID("clientIncrement"));
			parameter = "添加客户增值服务办(编号:"+clientIncrement.getClientCode()+")";
		}else{
			String hql = "from ClientIncrement where companyID = ? and  clientIncrementID = ? ";
			ClientIncrement c = (ClientIncrement) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),clientIncrement.getClientIncrementID()});
			parameter = "修改客户增值服务办(编号:"+c.getClientCode()+")";
		}
		clientIncrement.setCompanyID(companyID);
		clientIncrement.setOrganizationID(organizationID);
		beans.add(clientIncrement);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除客户增值服务办
	 public String delClientIncrement()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			CAccount account = (CAccount) session.get("account");
			beans = new ArrayList<BaseBean>();
		    Object[] params = {account.getCompanyID(),clientIncrement.getClientIncrementID()};
		    String hql2="from ClientIncrement where companyID=?  and clientIncrementID = ? ";
		    ClientIncrement c=(ClientIncrement) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除客户增值服务办(编号:"+c.getClientCode()+")", account);
		    beans.add(logBook);
	    	String hql="delete from ClientIncrement where companyID=?  and clientIncrementID=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params); 
			
		    return "success";
	    }
	 
	 //根据条件查询客户增值服务办
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", clientIncrement);
			return getClientIncrementList();
		}
	 //得到客户增值服务办
		public String getClientIncrementList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "clientIncrement";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(ClientIncrement.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				clientIncrement = (ClientIncrement) session.get("tablesearch");
				if(null!=clientIncrement.getClientCode()&&!"".equals(clientIncrement.getClientCode()))
				{
					dc.add(Restrictions.like("serialNumber", clientIncrement.getClientCode(), MatchMode.ANYWHERE));
				} 
				if(null!=clientIncrement.getClientName()&&!"".equals(clientIncrement.getClientName()))
				{
					dc.add(Restrictions.like("enName", clientIncrement.getClientName(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
		
		// 导出客户增值服务办
		public String showClientIncrementExcel() {
			Map<String, Object> map = ActionContext.getContext().getSession();
			CAccount account = (CAccount) map.get("account");
			String organizationID = (String) map.get("organizationID");
			excelStream = excelService.showExcel(ClientIncrement.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出客户增值服务办", account);
			baseBeanService.update(logBook);
			return "showexcel";
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
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public ClientIncrement getClientIncrement() {
			return clientIncrement;
		}
		public void setClientIncrement(ClientIncrement clientIncrement) {
			this.clientIncrement = clientIncrement;
		}
}
