package hy.ea.production.action.adesign;





import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.production.BudgetPlan;
import hy.ea.bo.production.PlanAmount;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.Date;
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
 * 预算计划
 */
@Controller
@Scope("prototype")
public class BudgetPlanAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	
	
	
	private InputStream excelStream;

	private PageForm pageForm;
	
	private String result;

	private int pageNumber;
	private String search;
	
	private BudgetPlan plan;

	
	private List<BaseBean> list;

	
	private String aeptype;//添加a，编辑e，预览p 
	
	private  String type;//类型：01产品生产量预算  、生产量日周月季年计划

	
	private String fiveClear;
	private String category;
	
	
	/***
	 * 
	 * 
	 * 预算计划列表
	 * @return
	 */
	public String getBudgetPlanList(){
		
		
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());
		
		
		return "list";
	}
	
	private DetachedCriteria getLists(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		DetachedCriteria dc = DetachedCriteria.forClass(BudgetPlan.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("type", type));
		dc.add(Restrictions.eq("category", category));
		
		if (search != null && "search".equals(search)) {
			plan = (BudgetPlan) session
					.get("tablesearch");
			   if(plan.getProductCode()!=null&&!plan.getProductCode().equals("")){
	        	   dc.add(Restrictions.like("productCode", plan.getProductCode(), MatchMode.ANYWHERE));
	           }
	           if(plan.getProductName()!=null&&!plan.getProductName().equals("")){
	        	   dc.add(Restrictions.like("productName", plan.getProductName(), MatchMode.ANYWHERE));
	           }
	           if(plan.getYear()!=null&&!plan.getYear().equals("")){
	        	   dc.add(Restrictions.eq("year", plan.getYear()));
	           }
		  
		  
		}
		
		return dc;
	}
	
	
	
	
	
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", plan);
		return getBudgetPlanList();
	}
	
	
	
	/**
	 * 
	 * 
	 * 获取添加修改页面
	 * @return
	 */
	public String getAddPage(){
		
		if(plan!=null&&plan.getBpid()!=null&&!plan.getBpid().equals("")){
			String hql = "from BudgetPlan where bpid = ?";
			
			plan = (BudgetPlan) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{plan.getBpid()});
			}
			
		
		
		return "addpage";
	}
	
	

	

	
	
	/**
	 * 
	 * 
	 * 保存和修改物品
	 * @return
	 */
	public String saveBudgetPlan(){
	CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		
		if(plan.getBpid()==null||plan.getBpid().equals("")){
			plan.setBpid(serverService.getServerID("bpid"));
		}
		
		plan.setCompanyID(account.getCompanyID());
		plan.setCreateDate(new Date());
		plan.setCategory(category);
		baseBeanService.update(plan);
		
		
		return "success";
	}
	
	

	
	
	
	
	/**
	 * 
	 * 删除计划预算
	 * @return
	 */
	public String deleteBudgetPlan(){
		String hql = "from BudgetPlan where bpid = ?";
		BudgetPlan p = (BudgetPlan) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{plan.getBpid()});
		
	    baseBeanService.deleteBeanByKey(BudgetPlan.class, p.getBpkey());
		
		return "success";
	}
	
	
	
	/**
	 * 
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		excelStream = excelService.showExcel(BudgetPlan.columnHeadings(),
				baseBeanService.getListByDC(getLists()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService
				.saveCLogBook(null, "导出预算计划管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
	
	
	
	/**
	 * 
	 * 打印按照查询结构打印
	 * @return
	 */
	public String printPrev(){
		list = baseBeanService.getListByDC(getLists());
		
		
		return "printprev";
	}
	

	public BudgetPlan getPlan() {
		return plan;
	}

	public void setPlan(BudgetPlan plan) {
		this.plan = plan;
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
	
	public String getAeptype() {
		return aeptype;
	}

	public void setAeptype(String aeptype) {
		this.aeptype = aeptype;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}



	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public List<BaseBean> getList() {
		return list;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
   
}
