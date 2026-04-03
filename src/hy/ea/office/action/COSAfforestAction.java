package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.COSAfforest;
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
public class COSAfforestAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private COSAfforest afforest;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", afforest);
		return getListForPage();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSAfforest.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			afforest = (COSAfforest) session.get("tablesearch");
			if (!"".equals(afforest.getClasses()))
				dc.add(Restrictions.like("classes", afforest.getClasses(),
						MatchMode.ANYWHERE));
			dc.add(Restrictions.like("principal", afforest.getPrincipal(),
					MatchMode.ANYWHERE));
		}
		return dc;
	}

	public String getListForPage() {
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount) (ActionContext.getContext()
				.getSession().get("account"))).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "list";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(COSAfforest.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出绿化管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (afforest.getAfforestID() == null
				|| "".equals(afforest.getAfforestID())) {
			afforest.setAfforestID(serverService.getServerID("afforest"));
			parameter = "添加绿化管理(绿化地点:"+afforest.getPlace()+")";
		}
		else
		{
		 String hql2="from COSAfforest where companyID=?  and afforestID=?";
		 COSAfforest aff=(COSAfforest) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(), afforest.getAfforestID() });
		 parameter = "修改绿化管理(绿化地点:"+aff.getPlace()+")";
		
		}
		afforest.setOrganizationID(organizationID);
		afforest.setCompanyID(account.getCompanyID());
		//baseBeanService.update(afforest);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(afforest);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}

	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), afforest.getAfforestID() };
	    String hql2="from COSAfforest where companyID=?  and afforestID=?";
	    COSAfforest aff=(COSAfforest) baseBeanService.getBeanByHqlAndParams(hql2, params);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除绿化管理(绿化地点:"+aff.getPlace()+")", account);
		String hql = "delete from COSAfforest where companyID=?  and afforestID=?";
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
		return "success";
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

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public COSAfforest getAfforest() {
		return afforest;
	}

	public void setAfforest(COSAfforest afforest) {
		this.afforest = afforest;
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
