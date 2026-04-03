package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.KeyManage;
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
/*
 * 钥匙管理
 * */
@Controller
@Scope("prototype")
public class KeyManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private KeyManage keyManage;
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

	//根据条件查询钥匙管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", keyManage);
		return getKeyManageList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(KeyManage.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			keyManage = (KeyManage) session.get("tablesearch");
			if( !keyManage.getDeptName().equals(""))
			{
			dc.add(Restrictions.like("deptName", keyManage.getDeptName(),MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 钥匙管理列表
	public String getKeyManageList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "keyManagelist";	
	}
	// 导出钥匙管理列表

	public String showExcel() {
		excelStream = excelService.showExcel(KeyManage.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出钥匙管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //钥匙管理保存
    
    public String saveKeyManage()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (keyManage.getKeyManageID()== null|| "".equals(keyManage.getKeyManageID())) {
			keyManage.setKeyManageID(serverService.getServerID("keyManage"));
			parameter = "添加钥匙管理(房间号:"+keyManage.getRoomNum()+")";
		}
		else
		{
			 String hql2="from KeyManage where companyID=?  and keyManageID=?";
			 KeyManage jeom=(KeyManage) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),keyManage.getKeyManageID()});
		     parameter = "修改钥匙管理(房间号:"+jeom.getRoomNum()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		keyManage.setOrganizationID(organizationID);
		keyManage.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(keyManage);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除钥匙管理
	 public String delKeyManage()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),keyManage.getKeyManageID()};
		    String hql2="from KeyManage where companyID=?  and keyManageID=?";
			 KeyManage jeom=(KeyManage) baseBeanService.getBeanByHqlAndParams(hql2,params);
			 CLogBook  logbook= logBookService.saveCLogBook(organizationID, "删除钥匙管理(房间号:"+jeom.getRoomNum()+")", account);
	    	String hql="delete from KeyManage where companyID=?  and keyManageID=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "succ";
	    }

	public KeyManage getKeyManage() {
		return keyManage;
	}

	public void setKeyManage(KeyManage keyManage) {
		this.keyManage = keyManage;
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
