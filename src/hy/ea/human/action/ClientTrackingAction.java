package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.ClientTracking;
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
 * 客户跟踪服务办
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ClientTrackingAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private ClientTracking clientTracking;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	private List<BaseBean> beans;
	private String produce;
	private InputStream excelStream;
	
	//保存客户跟踪服务办
	public String saveClientTracking(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if(null==clientTracking.getClientTrackingID()||"".equals(clientTracking.getClientTrackingID())){
			clientTracking.setClientTrackingID(serverService.getServerID("clientTracking"));
			parameter = "添加客户跟踪服务办(编号:"+clientTracking.getClientCode()+")";
		}else{
			String hql = "from ClientTracking where companyID = ? and  clientTrackingID = ? ";
			ClientTracking c = (ClientTracking) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),clientTracking.getClientTrackingID()});
			parameter = "修改客户跟踪服务办(编号:"+c.getClientCode()+")";
		}
		clientTracking.setCompanyID(companyID);
		clientTracking.setOrganizationID(organizationID);
		beans.add(clientTracking);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除客户跟踪服务办
	 public String delClientTracking()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),clientTracking.getClientTrackingID()};
		    String hql2="from ClientTracking where companyID=?  and clientTrackingID = ? ";
		    ClientTracking clienttracking=(ClientTracking) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    beans = new ArrayList<BaseBean>();
		    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除客户跟踪服务办(编号:"+clienttracking.getClientCode()+")", account);
		    beans.add(logBook);
		    String hql="delete from ClientTracking where companyID=?  and clientTrackingID=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params); 
			
		    return "success";
	    }
	 
	 //根据条件查询客户跟踪服务办
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", clientTracking);
			return getClientTrackingList();
		}
	 //得到客户跟踪服务办
		public String getClientTrackingList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "clientTrack";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(ClientTracking.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				clientTracking = (ClientTracking) session.get("tablesearch");
				if(null!=clientTracking.getClientCode()&&!"".equals(clientTracking.getClientCode()))
				{
					dc.add(Restrictions.like("serialNumber", clientTracking.getClientCode(), MatchMode.ANYWHERE));
				} 
				if(null!=clientTracking.getClientName()&&!"".equals(clientTracking.getClientName()))
				{
					dc.add(Restrictions.like("enName", clientTracking.getClientName(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
		
		// 导出客户跟踪服务办
		public String showClientTrackingExcel() {
			Map<String, Object> map = ActionContext.getContext().getSession();
			CAccount account = (CAccount) map.get("account");
			String organizationID = (String) map.get("organizationID");
			excelStream = excelService.showExcel(ClientTracking.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出客户跟踪服务办", account);
			baseBeanService.update(logBook);
			return "showexcel";
		}
		/**
		 * 返回跟踪产品客户服务菜单树页面
		 * @return
		 */
		public String getFileTrackService(){
			if(null != produce && produce.equals("Company")){
				return "cFirstpage";
			}
			if(null != produce && produce.equals("group")){
				return "gFirstpage";
			}
			return "firstpage";
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
		public ClientTracking getClientTracking() {
			return clientTracking;
		}
		public void setClientTracking(ClientTracking clientTracking) {
			this.clientTracking = clientTracking;
		}
		public String getProduce() {
			return produce;
		}
		public void setProduce(String produce) {
			this.produce = produce;
		}	
		
}
