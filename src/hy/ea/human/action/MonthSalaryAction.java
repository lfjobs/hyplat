package hy.ea.human.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.MonthSalary;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 月工资汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class MonthSalaryAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	
	private MonthSalary monthSalary;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	
	private List<Object> add;  //获取自定义加分项字段名
	private int addlength;     //获取自定义加分项字段名个数
	private List<Object> cut;  //获取自定义减分项字段名
	private int cutlength;     //获取自定义减分项字段名个数
	
	private List<BaseBean> wages;
	

	/**
	 * 月工资查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearchmon", monthSalary);
		return getMonthSalaryList();
	}
	
	/**
	 * 月工资汇总列表
	 * @return
	 */
	public String getMonthSalaryList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1),(pageNumber == 0 ? 10 : pageNumber),
				hql, params);
		if(pageForm != null){
			getAddCut(pageForm.getList());
		}
		return "list";
	}
	
	/**
	 * 月工资列表、查询、打印调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		String hql = "from MonthSalary where companyid = ? and status = ?";
		parms.add(account.getCompanyID());
		parms.add("01");
		if(search != null && search.equals("search")){
			monthSalary = (MonthSalary)session.get("tablesearchmon");
			if(monthSalary.getStaffname() != null 
					&& !"".equals(monthSalary.getStaffname().trim())){
				hql += " and staffname like ?";
				parms.add("%" + monthSalary.getStaffname().trim() + "%");
			}
			if(monthSalary.getMonths() != null 
					&& !"".equals(monthSalary.getMonths())){
				hql += " and months = ?";
				parms.add(monthSalary.getMonths());
			}
			if(monthSalary.getCategoryname() != null 
					&& !"".equals(monthSalary.getCategoryname())){
				hql += " and categoryname = ?";
				parms.add(monthSalary.getCategoryname());
			}
		}
		hql += " order by staffid";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 获取加分项、减分项
	 * @param list
	 */
	private void getAddCut(List<BaseBean> list){
		MonthSalary month = (MonthSalary)list.get(0);
		String adds = month.getWagefiledadd();
		if(adds != null){
			String[] wagadd = adds.substring(1, adds.length()-1).split(",");
			add = new ArrayList<Object>(); 
			for(int i=0;i<wagadd.length;i++){
				add.add(wagadd[i]); //加分项
			}
			addlength = add.size()-1;
		}
		String cuts = month.getWagefiledcut();
		if(cuts != null){
			String[] wagcut = cuts.substring(1, cuts.length()-1).split(",");
			cut = new ArrayList<Object>();
			for(int j=0;j<wagcut.length;j++){
				cut.add(wagcut[j]); //减分项
			}
			cutlength = cut.size()-1;
		}
	}
	
	/**
	 * 打印全部、单个打印月工资
	 * @return
	 */
	public String printManthSalary(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		String parmter = "";
		
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		if(request.getParameter("all") != null 
				&& request.getParameter("all").equals("all")){
			wages = baseBeanService.getListBeanByHqlAndParams(hql, params);
			parmter = "打印全部员工";
		}else{
			wages = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql, params).getList();
			parmter = "单个打印员工";
		}
		getAddCut(wages);
		
		String organizationID = (String) session.get("organizationID");
		monthSalary = new MonthSalary();
		monthSalary = (MonthSalary)wages.get(0);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parmter+monthSalary.getMonths().replace("-", "年")+"月工资", account);
		baseBeanService.update(logBook);
		
		if(request.getParameter("all") != null 
				&& request.getParameter("all").equals("all")){ 
			return "printall";
		}
		return "printevery";
	}
	
	
	public MonthSalary getMonthSalary() {
		return monthSalary;
	}
	public void setMonthSalary(MonthSalary monthSalary) {
		this.monthSalary = monthSalary;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public List<Object> getAdd() {
		return add;
	}
	public void setAdd(List<Object> add) {
		this.add = add;
	}
	public List<Object> getCut() {
		return cut;
	}
	public void setCut(List<Object> cut) {
		this.cut = cut;
	}
	public int getAddlength() {
		return addlength;
	}
	public void setAddlength(int addlength) {
		this.addlength = addlength;
	}
	public int getCutlength() {
		return cutlength;
	}
	public void setCutlength(int cutlength) {
		this.cutlength = cutlength;
	}

	public List<BaseBean> getWages() {
		return wages;
	}
	public void setWages(List<BaseBean> wages) {
		this.wages = wages;
	}
}