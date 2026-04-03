package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.InOutPropagate;
import hy.ea.human.service.COrganizationService;
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
 *  户内户外宣传管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class InOutPropagateAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	@Resource
	private COrganizationService organizationService;
	private List<COrganization> orgnizationList;
	private InOutPropagate propagate;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private InputStream excelStream;
	
	//保存户内户外宣传信息
	public String saveInOutPropagate(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		if(null==propagate.getPropagateID()||"".equals(propagate.getPropagateID())){
			propagate.setPropagateID(serverService.getServerID("propagate"));
			parameter = "添加户内户外宣传信息(编号:"+propagate.getSerialNumber()+")";
		}else{
			String hql = "from InOutPropagate where companyID = ? and  propagateID = ? ";
			InOutPropagate propagate0 = (InOutPropagate) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),propagate.getPropagateID()});
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
	//删除户内户外宣传信息
	 public String delInOutPropagate()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),propagate.getPropagateID()};
		    String hql2="from InOutPropagate where companyID=?  and propagateID = ? ";
		    InOutPropagate propagate0=(InOutPropagate) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除户内户外宣传(编号:"+propagate0.getSerialNumber()+")", account);
	    	String hql="delete from InOutPropagate where companyID=?  and propagateID=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		    return "success";
	    }
	 
	 //根据条件查询户内户外宣传列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", propagate);
			return getInOutPropagateList();
		}
	 // 户内户外宣传列表
		public String getInOutPropagateList() { 
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "inoutpropagatelist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			Map<String,String> ccmap = new HashMap<String,String>();
			//部门列表 
			orgnizationList =	organizationService.getOrganizationList(organizationID,companyID);
			if(null!=orgnizationList){
				for(COrganization o:orgnizationList){  
					ccmap.put(o.getOrganizationID(), o.getOrganizationName());
				}
			}
			InOutPropagate.setOMap(ccmap);
			DetachedCriteria dc = DetachedCriteria.forClass(InOutPropagate.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				propagate = (InOutPropagate) session.get("tablesearch");
				if(null!=propagate.getSerialNumber()&&!"".equals(propagate.getSerialNumber()))
				{
					dc.add(Restrictions.like("serialNumber", propagate.getSerialNumber(), MatchMode.ANYWHERE));
				}
				if(null!=propagate.getCorganizationID()&&!"".equals(propagate.getCorganizationID())){
					dc.add(Restrictions.eq("corganizationID", propagate.getCorganizationID()));
				}
				if(null!=propagate.getExecutePerson()&&!"".equals(propagate.getExecutePerson()))
				{
					dc.add(Restrictions.like("executePerson", propagate.getExecutePerson(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}
		
		// 导出户内户外宣传
		public String showInOutPropagateExcel() {
			excelStream = excelService.showExcel(InOutPropagate.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出户内户外宣传列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}
		public InOutPropagate getPropagate() {
			return propagate;
		}
		public void setPropagate(InOutPropagate propagate) {
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
		public List<COrganization> getOrgnizationList() {
			return orgnizationList;
		}
		public void setOrgnizationList(List<COrganization> orgnizationList) {
			this.orgnizationList = orgnizationList;
		}
}
