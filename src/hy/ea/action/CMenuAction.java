package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CMenu;
import hy.ea.service.CEAService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CMenuService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SEA;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CMenuAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CMenuService menuService;
	@Resource
	private CEAService ceaservice;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private CMenu cmenu;
	private List<SEA> cealist;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> beans;
	

	public String saveCMenu() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		if(null == cmenu.getMenuID() || "".equals(cmenu.getMenuID())){
			cmenu.setMenuID(serverService.getServerID("cmenu"));
			parameter = "添加菜单";
			parameter += "(菜单名称:"+cmenu.getMenuName()+")";
		}else{
			parameter = "修改菜单";
			parameter += "(菜单名称:"+menuService.getMenuByID(account.getCompanyID(), cmenu.getMenuID()).getMenuName()+")";
		}
		cmenu.setCompanyID(account.getCompanyID());
		//baseBeanService.update(cmenu);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans = new ArrayList<BaseBean>();
		beans.add(cmenu);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getListCMenu();
	}

	public String getListCMenu() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		cealist = ceaservice.getListSea(account.getCompanyID());
		String hql = " from CMenu where companyID = ? order by menuNumber ";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, new String[]{account.getCompanyID()});
		return "list";
	}

	public String editCMenu() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		cealist = ceaservice.getListSea(account.getCompanyID());
		if(null == cmenu){
			return "to_edit";
		}
		cmenu = menuService.getMenuByID(account.getCompanyID(),cmenu.getMenuID());
		return "to_edit";
	}

	public String delCMenu() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"删除菜单(菜单名称："+ menuService.getMenuByID(account.getCompanyID(),cmenu.getMenuID()).getMenuName()
				+")", account);
		menuService.deleteMenuByID(account.getCompanyID(),logBook,cmenu.getMenuID());
		return getListCMenu();
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<SEA> getCealist() {
		return cealist;
	}

	public void setCealist(List<SEA> cealist) {
		this.cealist = cealist;
	}

	public CMenu getCmenu() {
		return cmenu;
	}

	public void setCmenu(CMenu cmenu) {
		this.cmenu = cmenu;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

}
