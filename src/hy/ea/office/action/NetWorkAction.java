package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWork;
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
 * 网络加密管理
 */
@Controller
@Scope("prototype")
public class NetWorkAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private NetWork network;
	
	

	//根据条件查询网络加密管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", network);
		return getNetWork();
	}

	private DetachedCriteria getNList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(NetWork.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			network = (NetWork) session.get("tablesearch");
			if(null!= network.getNetWorkAddress()&&!"".equals(network.getNetWorkAddress()))
			{
			dc.add(Restrictions.like("netWorkAddress", network.getNetWorkAddress(), MatchMode.ANYWHERE));
			}
			if(null!= network.getNetWorkName()&&!"".equals( network.getNetWorkName()))
			{
			dc.add(Restrictions.like("netWorkName", network.getNetWorkName(),MatchMode.ANYWHERE));
			}
			if(null!=network.getNetWorkCode()&&!"".equals(network.getNetWorkCode()))
			{
			dc.add(Restrictions.like("netWorkCode", network.getNetWorkCode(),MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}

	/***********************************************/

	//网络加密管理
	public String getNetWork() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getNList());
		return "networkList";	
	}
	
	// 导出网络加密管理

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(NetWork.columnHeadings(), baseBeanService.getListByDC(getNList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络加密管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //保存网络加密管理
    
    public String saveNetWork(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (network.getNetWorkID() == null
				|| "".equals(network.getNetWorkID())) {
			network.setNetWorkID(serverService.getServerID("netWorkID"));
			parameter = "添加网络加密管理(网络名称:"+network.getNetWorkName()+")";
		}
		else
		{
		 String hql2="from NetWork where companyID=?  and netWorkID=?";
		 NetWork net=(NetWork) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),network.getNetWorkID() });
		 parameter = "修改网络加密管理(网络名称:"+net.getNetWorkName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		network.setCompanyID(account.getCompanyID());
		network.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(network);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
    }

    //删除网络加密管理
	 public String delNetWork(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),network.getNetWorkID()};
		    String hql1=" from NetWork where companyID=? and netWorkID=?" ;
			NetWork cf=(NetWork) baseBeanService.getBeanByHqlAndParams(hql1, params);
			CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除网络加密管理(网络名称:"+cf.getNetWorkName()+")", account);
	    	String[] hql={"delete from NetWork where companyID=? and netWorkID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			return "success";
	    }

	    public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
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

	public NetWork getNetwork() {
		return network;
	}

	public void setNetwork(NetWork network) {
		this.network = network;
	}

}
