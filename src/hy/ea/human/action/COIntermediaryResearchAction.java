package hy.ea.human.action;
//中介调查
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COIntermediaryResearch;
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

@Controller
@Scope("prototype")
public class COIntermediaryResearchAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private String result;
	private InputStream excelStream;
	private String intermediaryResearchID;
	private COIntermediaryResearch intermediaryresearch;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> beans;
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(COIntermediaryResearch.class);
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.eq("companyID",account.getCompanyID()));
		if (search != null && search.equals("search")) {
			intermediaryresearch = (COIntermediaryResearch) session.get("tablesearch");
			if(null!=intermediaryresearch.getCompanyName()&&!"".equals(intermediaryresearch.getCompanyName()))
			{
				dc.add(Restrictions.like("companyName",intermediaryresearch.getCompanyName(),
						MatchMode.ANYWHERE));
			}
			if(null!=intermediaryresearch.getInvitePost()&&!"".equals(intermediaryresearch.getInvitePost()))
			{
				dc.add(Restrictions.like("invitePost",intermediaryresearch.getInvitePost(),
						MatchMode.ANYWHERE));
			}
			if(null!=intermediaryresearch.getCompanyAddress()&&!"".equals(intermediaryresearch.getCompanyAddress()))
			{
				dc.add(Restrictions.like("companyAddress",intermediaryresearch.getCompanyAddress(),
						MatchMode.ANYWHERE));
			}
			
			
		}
		return dc;
	}

	// 中介调查列表
	public String getIntermediaryResearchList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "intermediaryresearchlist";
	}

	// 保存中介调查
	public String saveIntermediaryResearch() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if (intermediaryresearch.getIntermediaryResearchID() == null
				|| "".equals(intermediaryresearch.getIntermediaryResearchID())) {
			intermediaryresearch.setIntermediaryResearchID(serverService
					.getServerID("intermediaryresearch"));
			parameter = "添加中介调查(调查单位名称:"+intermediaryresearch.getCompanyName()+")";
		}
		else
		{
			Object[] params = { account.getCompanyID(), organizationID,intermediaryresearch.getIntermediaryResearchID() };
			String hql1="from COIntermediaryResearch where companyID=? and organizationID=? and intermediaryResearchID=?";
			COIntermediaryResearch interme=(COIntermediaryResearch) baseBeanService.getBeanByHqlAndParams(hql1, params);
			parameter = "修改中介调查(调查单位名称:"+interme.getCompanyName()+")";
		}
		intermediaryresearch.setCompanyID(account.getCompanyID());
		intermediaryresearch.setOrganizationID(organizationID);
		beans.add(intermediaryresearch);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				intermediaryresearch);
		return getIntermediaryResearchList();
	}

	// 删除中介调查
	public String delIntermediaryResearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID,intermediaryresearch.getIntermediaryResearchID() };
		String hql1="from COIntermediaryResearch where companyID=? and organizationID=? and intermediaryResearchID=?";
		intermediaryresearch=(COIntermediaryResearch) baseBeanService.getBeanByHqlAndParams(hql1, params);
		beans = new ArrayList<BaseBean>();
	    CLogBook logBook = logBookService.saveCLogBook(organizationID,"删除中介调查(调查单位名称："+intermediaryresearch.getCompanyName()+")", account);
	    beans.add(logBook);
	    String hql = "delete from COIntermediaryResearch where companyID=? and organizationID=? and intermediaryResearchID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql}, params);
		return "success";
	}

	// 导出
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(COIntermediaryResearch
				.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出中介调查", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getIntermediaryResearchID() {
		return intermediaryResearchID;
	}

	public void setIntermediaryResearchID(String intermediaryResearchID) {
		this.intermediaryResearchID = intermediaryResearchID;
	}

	public COIntermediaryResearch getIntermediaryresearch() {
		return intermediaryresearch;
	}

	public void setIntermediaryresearch(
			COIntermediaryResearch intermediaryresearch) {
		this.intermediaryresearch = intermediaryresearch;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
