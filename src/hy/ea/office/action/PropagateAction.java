package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Propagate;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
 *  网络宣传管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class PropagateAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private Propagate propagate;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private InputStream excelStream;
	
	//保存网络宣传信息
	public String savePropagate(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		if(null==propagate.getPropagateID()||"".equals(propagate.getPropagateID())){
			propagate.setPropagateID(serverService.getServerID("propagate"));
			parameter = "添加网络宣传信息(编号:"+propagate.getSerialNumber()+")";
		}else{
			String hql = "from Propagate where companyID = ? and  propagateID = ? ";
			Propagate propagate0 = (Propagate) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),propagate.getPropagateID()});
			parameter = "修改网络宣传信息(编号:"+propagate0.getSerialNumber()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		propagate.setCompanyID(companyID);
		propagate.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(propagate);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除网络宣传信息
	 public String delPropagate()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),propagate.getPropagateID()};
		    String hql2="from Propagate where companyID=?  and propagateID = ? ";
		    Propagate propagate0=(Propagate) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    List<BaseBean> beans=new ArrayList<BaseBean>();
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除网络宣传(编号:"+propagate0.getSerialNumber()+")", account);
	    	String[] hql={"delete from Propagate where companyID=?  and propagateID=?"};
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			
		    return "success";
	    }
	 
	 //根据条件查询网络宣传列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", propagate);
			return getPropagateList();
		}
	 // 网络宣传列表
		public String getPropagateList() { 
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "propagatelist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(Propagate.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				propagate = (Propagate) session.get("tablesearch");
				if(null!=propagate.getSerialNumber()&&!"".equals(propagate.getSerialNumber()))
				{
					dc.add(Restrictions.like("serialNumber", propagate.getSerialNumber(), MatchMode.ANYWHERE));
				} 
				Date sdate = propagate.getStartDate();
				Date edate = propagate.getEndDate();
				if(!("").equals(sdate)&&sdate!=null&&edate!=null&&!("").equals(edate))
				{ 
						dc.add(Restrictions.between("startDate",sdate,edate)); 
				}
			} 
			return dc;
		}
		
		// 导出propagate 
		public String showPropagateExcel() {
			excelStream = excelService.showExcel(Propagate.columnHeadings(),baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出网络宣传列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}
		public Propagate getPropagate() {
			return propagate;
		}
		public void setPropagate(Propagate propagate) {
			this.propagate = propagate;
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
	
}
