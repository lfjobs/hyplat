package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.ClientPblm;
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
 * cxf
 * 客户问题解决办
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ClientPblmAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private ClientPblm clientPblm;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	private List<BaseBean> beans;
	
	private InputStream excelStream;
	
	//保存客户问题解决办
	public String saveClientPblm(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if(null==clientPblm.getClientPblmID()||"".equals(clientPblm.getClientPblmID())){
			clientPblm.setClientPblmID(serverService.getServerID("clientPblm"));
			parameter = "添加客户问题解决办(编号:"+clientPblm.getClientCode()+")";
		}else{
			String hql = "from ClientPblm where companyID = ? and  clientPblmID = ? ";
			ClientPblm c = (ClientPblm) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),clientPblm.getClientPblmID()});
			parameter = "修改客户问题解决办(编号:"+c.getClientCode()+")";
		}
		clientPblm.setCompanyID(companyID);
		clientPblm.setOrganizationID(organizationID);
		beans.add(clientPblm);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除客户问题解决办
	 public String delClientPblm()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			CAccount account = (CAccount) session.get("account");
			beans = new ArrayList<BaseBean>();
		    Object[] params = {account.getCompanyID(),clientPblm.getClientPblmID()};
		    String hql2="from ClientPblm where companyID=?  and clientPblmID = ? ";
		    ClientPblm c=(ClientPblm) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除客户问题解决办(编号:"+c.getClientCode()+")", account);
		    beans.add(logBook);
	    	String hql="delete from ClientPblm where companyID=?  and clientPblmID=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params); 
			
		    return "success";
	    }
	 
	 //根据条件查询客户问题解决办
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", clientPblm);
			return getClientPblmList();
		}
	 //得到客户问题解决办
		public String getClientPblmList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "clientPblm";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(ClientPblm.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				clientPblm = (ClientPblm) session.get("tablesearch");
				if(null!=clientPblm.getClientCode()&&!"".equals(clientPblm.getClientCode()))
				{
					dc.add(Restrictions.like("serialNumber", clientPblm.getClientCode(), MatchMode.ANYWHERE));
				} 
				if(null!=clientPblm.getClientName()&&!"".equals(clientPblm.getClientName()))
				{
					dc.add(Restrictions.like("enName", clientPblm.getClientName(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
		
		// 导出客户问题解决办
		public String showClientPblmExcel() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			Map<String, String> map = new HashMap<String, String>();
			map.put("00", "请选择");
			map.put("01", "返修");
			map.put("02", "完全解决");
			map.put("03", "跟踪服务");
			map.put("04", "其他");
			ClientPblm.setOMap(map);
			excelStream = excelService.showExcel(ClientPblm.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出客户问题解决办", account);
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
		public ClientPblm getClientPblm() {
			return clientPblm;
		}
		public void setClientPblm(ClientPblm clientPblm) {
			this.clientPblm = clientPblm;
		}	
}
