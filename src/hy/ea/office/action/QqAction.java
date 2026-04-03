package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Qq;
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
 * QQ管理
 * @author :m
 */
@Controller
@Scope("prototype")
public class QqAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private Qq qq;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private List<BaseBean> staffList; 
	private InputStream excelStream;
	
	//保存QQ信息
	public String saveQq(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		if(null==qq.getQqID()||"".equals(qq.getQqID())){
			qq.setQqID(serverService.getServerID("qq"));
			parameter = "添加QQ信息(QQ号:"+qq.getQqSequence()+")";
		}else{
			String hql = "from Qq where companyID = ? and  qqID = ? ";
			Qq qq0 = (Qq) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),qq.getQqID()});
			parameter = "修改QQ信息(QQ号:"+qq0.getQqSequence()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		qq.setCompanyID(companyID);
		qq.setOrganizationID(organizationID);
		beans.add(qq);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除QQ信息
	 public String delQq()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),qq.getQqID()};
		    String hql2="from Qq where companyID=?  and qqID = ? ";
		    Qq qq0=(Qq) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除QQ信息(QQ号:"+qq0.getQqSequence()+")", account);
	    	String[] hql={"delete from Qq where companyID=?  and qqID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		    return "success";
	    }
	 
	 //根据条件查询QQ列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", qq);
			return getQqList();
		}
	 // QQ列表
		public String getQqList() {
			Map<String,Object> session = ActionContext.getContext().getSession();
			String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and organizationID = ? and cosStatus = '50' ) ";
			Object[] params = { ((CAccount) (session.get("account"))).getCompanyID(), session.get("organizationID")};
			staffList= baseBeanService.getListBeanByHqlAndParams(hql, params);
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "qqlist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(Qq.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				qq = (Qq) session.get("tablesearch");
				if(null!=qq.getQqSequence()&&!"".equals(qq.getQqSequence()))
				{
					dc.add(Restrictions.like("qqSequence", qq.getQqSequence(), MatchMode.ANYWHERE));
				} 
			} 
			return dc;
		}
		
		// 导出QQ 
		public String showQqExcel() {
			excelStream = excelService.showExcel(Qq.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出QQ管理表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}	
		
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public Qq getQq() {
			return qq;
		}
		public void setQq(Qq qq) {
			this.qq = qq;
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
		public List<BaseBean> getStaffList() {
			return staffList;
		}
		public void setStaffList(List<BaseBean> staffList) {
			this.staffList = staffList;
		} 
}
