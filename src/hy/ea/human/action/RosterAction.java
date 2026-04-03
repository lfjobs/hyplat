package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.DtRoster;
import hy.ea.service.CLogBookService;
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
import com.opensymphony.xwork2.ActionContext;

public class RosterAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private DtRoster roster;
	private List<BaseBean> beans;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private String parameter;
	//查询信息
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", roster);
		return getRosterAll();
	}
		private DetachedCriteria getList(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(DtRoster.class);
			dc.add(Restrictions.eq("companyid", companyID));
			if (search != null && search.equals("search")) {
				roster = (DtRoster) session.get("tablesearch");
				if(roster.getName() != null
						&& !"".equals(roster.getName().trim())){
					dc.add(Restrictions.like("name", roster.getName(), MatchMode.ANYWHERE));
					
				}if(roster.getPhone() != null
						&& !"".equals(roster.getPhone().trim())){
					dc.add(Restrictions.like("phone", roster.getPhone(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
	/**
	 * 获得所有花名册信息 
	 */
	public String getRosterAll(){
		 pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "rosterList";	
		
	}
	
	public String addroster(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String ComopanyID = account.getCompanyID();
		beans=new ArrayList<BaseBean>();
				if (roster.getRosterid() == null || "".equals(roster.getRosterid())) {
					roster.setRosterid(serverService.getServerID("DtRoster"));
					parameter = "添加花名册信息(名称:"+roster.getName()+")";
				} else {
					String hql = "from DtRoster where companyid = ? and  rosterid = ? ";
					DtRoster ROS = (DtRoster) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ComopanyID,roster.getRosterid()});
					parameter = "修改花名册信息(名称:"+ROS.getName()+")";
				}
				roster.setCompanyid(account.getCompanyID());
				CLogBook  logbook=logBookService.saveCLogBook(ComopanyID, parameter, account);
				beans.add(roster);
				beans.add(logbook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	public String del(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(), roster.getRosterid()};
		String hql = "delete from DtRoster where companyid=?  and rosterid=?";
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除花名册(名称："
				+ roster.getName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
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
	
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public DtRoster getRoster() {
		return roster;
	}
	public void setRoster(DtRoster roster) {
		this.roster = roster;
	}

	
	
}
