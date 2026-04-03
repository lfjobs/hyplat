package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetWorkAntiVirus;
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
 * 网络杀毒管理
 */
@Controller
@Scope("prototype")
public class NetWorkAntiVirusAction {
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
	private NetWorkAntiVirus netWorkAntiVirus;
	private List<BaseBean> netlist;
	
	

	//根据条件查询网络杀毒管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", netWorkAntiVirus);
		return getNetWorkAntiVirusList();
	}

	private DetachedCriteria getNAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetWorkAntiVirus.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			netWorkAntiVirus = (NetWorkAntiVirus) session.get("tablesearch");
			if(null!= netWorkAntiVirus.getNetworkAddress()&&!"".equals( netWorkAntiVirus.getNetworkAddress()))
			{
				dc.add(Restrictions.like("networkAddress", netWorkAntiVirus.getNetworkAddress(),MatchMode.ANYWHERE));
			}
			if(null!= netWorkAntiVirus.getNetWorkName()&&!"".equals( netWorkAntiVirus.getNetWorkName()))
			{
				dc.add(Restrictions.like("netWorkName", netWorkAntiVirus.getNetWorkName(), MatchMode.ANYWHERE));
			}
			if(null!= netWorkAntiVirus.getNetWorkCode()&&!"".equals(netWorkAntiVirus.getNetWorkCode()))
			{
				dc.add(Restrictions.like("netWorkCode",netWorkAntiVirus.getNetWorkCode(),MatchMode.ANYWHERE));
			}
		}
		return  dc;
	}

	/***********************************************/

	//网络杀毒管理
	public String getNetWorkAntiVirusList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getNAList());
		return "networkantivirusList";	
	}
	
	// 导出网络杀毒管理

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "请选择");
		map.put("01", "已杀毒");
		map.put("02", "未杀毒");
		map.put("03", "正在杀毒");
		NetWorkAntiVirus.setOMap(map);
		excelStream = excelService.showExcel(NetWorkAntiVirus.columnHeadings(), baseBeanService.getListByDC(getNAList()));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络杀毒管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //保存网络杀毒管理
    
    public String saveNetWorkAntiVirus(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (netWorkAntiVirus.getAntiVirusID() == null
				|| "".equals(netWorkAntiVirus.getAntiVirusID())) {
			netWorkAntiVirus.setAntiVirusID(serverService.getServerID("afficheID"));
			parameter = "添加网络杀毒管理(网络名称:"+netWorkAntiVirus.getNetWorkName()+")";
		}
		else
		{
		 String hql2="from NetWorkAntiVirus where companyID=?  and antiVirusID=?";
		 NetWorkAntiVirus aff=(NetWorkAntiVirus) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),netWorkAntiVirus.getAntiVirusID() });
		 parameter = "修改网络杀毒管理(网络名称:"+aff.getNetWorkName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		netWorkAntiVirus.setCompanyID(account.getCompanyID());
		netWorkAntiVirus.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(netWorkAntiVirus);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
    }

    //删除网络杀毒管理
	 public String delNetWorkAntiVirus(){
		 
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),netWorkAntiVirus.getAntiVirusID()};
		    String hql1=" from NetWorkAntiVirus where companyID=? and antiVirusID=?" ;
		    NetWorkAntiVirus cf=(NetWorkAntiVirus) baseBeanService.getBeanByHqlAndParams(hql1, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除网络杀毒管理(网络名称:"+cf.getNetWorkName()+")", account);
	    	String[] hql={"delete from NetWorkAntiVirus where  companyID=? and antiVirusID=?"};
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


	public NetWorkAntiVirus getNetWorkAntiVirus() {
		return netWorkAntiVirus;
	}

	public void setNetWorkAntiVirus(NetWorkAntiVirus netWorkAntiVirus) {
		this.netWorkAntiVirus = netWorkAntiVirus;
	}

	public List<BaseBean> getNetlist() {
		return netlist;
	}

	public void setNetlist(List<BaseBean> netlist) {
		this.netlist = netlist;
	}
}
