package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseMeritorious;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
/**
 * 企业功臣集团汇总
 * @author Mr.fan
 *
 */
@Controller
@Scope("prototype")
public class EnterpriseMeritoriousGAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private  EnterpriseMeritorious enterpriseMeritorious;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private File photo;
	//根据条件查询企业功臣
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseMeritorious);
		return getEnterpriseMeritoriousList();
	}


	// 企业功臣列表
	
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from EnterpriseMeritorious t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			enterpriseMeritorious = (EnterpriseMeritorious) session.get("tablesearch");
			if (null != enterpriseMeritorious.getRewardName()
					&& !"".equals(enterpriseMeritorious.getRewardName().trim())) {
				hql += " and t.rewardName like ?";
				parms.add("%" + enterpriseMeritorious.getRewardName().trim() + "%");
			}
			if (null != enterpriseMeritorious.getRewardYear()
					&& !"".equals(enterpriseMeritorious.getRewardYear().trim())) {
				hql += " and t.rewardYear like ?";
				parms.add("%" + enterpriseMeritorious.getRewardYear().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	public String getEnterpriseMeritoriousList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "meritoriouslist";	
	}
	
	//导出企业功臣列表
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(EnterpriseMeritorious.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出企业功臣集团汇总", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
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


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public EnterpriseMeritorious getEnterpriseMeritorious() {
		return enterpriseMeritorious;
	}


	public void setEnterpriseMeritorious(EnterpriseMeritorious enterpriseMeritorious) {
		this.enterpriseMeritorious = enterpriseMeritorious;
	}


	public File getPhoto() {
		return photo;
	}


	public void setPhoto(File photo) {
		this.photo = photo;
	}
	
}
