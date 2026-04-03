package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Honor;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffReward;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.bean.ExcelImport;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 荣誉名
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
public class HonorAction {
	@Resource
	private CompanyService companyService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private Honor honor;
	private ExcelImport excelImport;
	private PageForm pageForm;
	private int pageNumber;
	private String parameter;
	private List<BaseBean> beans;
	private String search; 
	private String result;

	
	public String toSearch(){
		
		ActionContext.getContext().getSession().put("tablesearch",
				honor);
		
		return getHonorList();
	}
	
	public String getHonorList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getlist(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), hql, params);
		
		return "list";
	}
	/**
	 * 获取 / results
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getlist(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = " from Honor h where h.companyID = ?";
		parms.add(account.getCompanyID());
		if(search != null && search.equals("search")) {
			honor = (Honor) session.get("tablesearch");
			if (honor.getHonorName() != null
					&& !"".equals(honor.getHonorName())) {
				hql += " and h.honorName like ?";
				parms.add("%" + honor.getHonorName() + "%");
			}
		}
		
		hql += " order by honorCode";
		results.add(hql);
		results.add(parms.toArray());
		return  results;
	}
	
	/**
	 * 保存草稿
	 * @return
	 */
	public String save(){
	
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		if(null==honor.getHonorID()||"".equals(honor.getHonorID())){
			honor.setHonorID(serverService.getServerID("honor"));
			honor.setCompanyID(account.getCompanyID());
			beans.add(honor);
			
		}else{
			beans.add(honor);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		System.err.println();
		return "success";
	}

	public String delHonor(){
		String hql = "delete from Honor h where h.honorID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{honor.getHonorID()});
		return "success";
	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Honor getHonor() {
		return honor;
	}

	public void setHonor(Honor honor) {
		this.honor = honor;
	}

	
	
}
