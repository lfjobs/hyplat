package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CapitalConstruction;
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
 * 基建管理
 * */
@Controller
@Scope("prototype")
public class CapitalConstructionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private CapitalConstruction capital;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询基建管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", capital);
		return getCapitalConstructionList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CapitalConstruction.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			capital = (CapitalConstruction) session.get("tablesearch");
			if( !capital.getUnitName().equals(""))
			{
			dc.add(Restrictions.like("unitName", capital.getUnitName(),MatchMode.ANYWHERE));
			}
			if(!capital.getItem().equals(""))
			{
			dc.add(Restrictions.like("item", capital.getItem(),MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("capitalType", capital.getCapitalType()));
		} 
		return dc;
	}

	// 基建管理列表
	public String getCapitalConstructionList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "capitallist";	
	}
	// 导出基建管理列表

	public String showExcel() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "基建");
		map.put("01", "维修");
		CapitalConstruction.setOMap(map);
		excelStream = excelService.showExcel(CapitalConstruction.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出奖励单", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
  //基建管理保存
    
    public String saveCapitalConstruction()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (capital.getCapitalID()== null|| "".equals(capital.getCapitalID())) {
			capital.setCapitalID(serverService.getServerID("capital"));
			parameter = "添加基建管理(单位名称:"+capital.getUnitName()+")";
		}
		else
		{
		 String hql2="from CapitalConstruction where companyID=?  and capitalID=?";
		 CapitalConstruction cap=(CapitalConstruction) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),capital.getCapitalID()});
		 parameter = "修改基建管理(单位名称:"+cap.getUnitName()+")";
		
		}
		capital.setOrganizationID(organizationID);
		capital.setCompanyID(account.getCompanyID());
		//baseBeanService.update(capital);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(capital);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "succ";
    }
    //删除基建管理
	 public String delCapitalConstruction()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),capital.getCapitalID()};
		    String hql2="from CapitalConstruction where companyID=?  and capitalID=?";
			CapitalConstruction cap=(CapitalConstruction) baseBeanService.getBeanByHqlAndParams(hql2, params);
			CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除基建管理(单位名称:"+cap.getUnitName()+")", account);
	    	String hql="delete from CapitalConstruction where companyID=?  and capitalID=?";
	    	List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
	    	baseBeansList.add(cLogBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
			return "succ";
	    }


	public CapitalConstruction getCapital() {
		return capital;
	}

	public void setCapital(CapitalConstruction capital) {
		this.capital = capital;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
