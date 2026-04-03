package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Logistics;
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
/*
 * 物流管理
 * */
@Controller
@Scope("prototype")
public class LogisticsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
    private Logistics logistics;
	private PageForm pageForm;
	private InputStream excelStream;
	private String staffID;
	private String result;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询物流管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", logistics);
		return getLogisticsList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Logistics.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			logistics = (Logistics) session.get("tablesearch");
			
			dc.add(Restrictions.like("organizationID", logistics.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 物流管理列表
	public String getLogisticsList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "logisticslist";	
	}
	// 导出物流管理列表

	public String showExcel() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "是");
		map.put("01", "否");
		Logistics.setOMap(map);
		excelStream = excelService.showExcel(Logistics.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出物流管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //物流管理保存
    
    public String saveLogistics()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (logistics.getLogisticsID()== null
				|| "".equals(logistics.getLogisticsID())) {
			logistics.setLogisticsID(serverService.getServerID("logistics"));
			parameter = "添加物流管理(物流公司:"+logistics.getPenskelogistics()+")";
		}
		else
		{
			 String hql2="from Logistics where companyID=?  and logisticsID=?";
			 Logistics jeom=(Logistics) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),logistics.getLogisticsID()});
		     parameter = "修改物流管理(物流公司:"+jeom.getPenskelogistics()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		logistics.setOrganizationID(organizationID);
		logistics.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		beans.add(logistics);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除物流管理
	 public String delLogistics()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),logistics.getLogisticsID()};
		    String hql2="from Logistics where companyID=?  and logisticsID=?";
			 Logistics jeom=(Logistics) baseBeanService.getBeanByHqlAndParams(hql2, params);
			 CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除物流管理(物流公司:"+jeom.getPenskelogistics()+")", account);
	    	String[] hql={"delete from Logistics where companyID=?  and logisticsID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			return "succ";
	    }

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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

}
