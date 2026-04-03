package hy.ea.production.action.cprocedure;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.PlanAmount;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

/**
 * 生产量计划
 */
@Controller
@Scope("prototype")
public class AmountPlanAction {
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

	private PlanAmount plan;

	private GoodFunction goodFunction;

	private List<BaseBean> list;
	private String fiveClear;
	

	/***
	 * 
	 * 
	 * 生产量列表
	 * 
	 * @return
	 */
	public String getAmountPlanList() {

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());

		return "list";
	}

	private DetachedCriteria getLists() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(PlanAmount.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.addOrder(Order.desc("createDate"));

		if (search != null && "search".equals(search)) {
			plan = (PlanAmount) session.get("tablesearch");
			if (plan.getProductCode() != null
					&& !plan.getProductCode().equals("")) {
				dc.add(Restrictions.like("productCode", plan.getProductCode(),
						MatchMode.ANYWHERE));
			}
			if (plan.getProductName() != null
					&& !plan.getProductName().equals("")) {
				dc.add(Restrictions.like("productName", plan.getProductName(),
						MatchMode.ANYWHERE));
			}

		}

		return dc;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", plan);
		return getAmountPlanList();
	}

	/**
	 * 
	 * 
	 * 获取添加修改页面
	 * 
	 * @return
	 */
	public String getAddPage() {
		if (plan != null && plan.getPaid() != null
				&& !plan.getPaid().equals("")) {
			String hql = "from PlanAmount where paid = ?";

			plan = (PlanAmount) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { plan.getPaid() });
		}

		return "addpage";
	}

	/**
	 * 
	 * 
	 * 保存和修改计划
	 * 
	 * @return
	 */
	public String savePlan() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (plan.getPaid() == null || plan.getPaid().equals("")) {
			plan.setPaid(serverService.getServerID("paid"));
		}

		plan.setCompanyID(account.getCompanyID());

		baseBeanService.update(plan);

		return Action.SUCCESS;
	}

	/**
	 * 
	 * 删除计划
	 * 
	 * @return
	 */
	public String deletePlans() {
		String hql = "from PlanAmount where paid = ?";
		PlanAmount plans = (PlanAmount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { plan.getPaid() });

		baseBeanService.deleteBeanByKey(PlanAmount.class, plans.getPakey());

		return Action.SUCCESS;
	}

	/**
	 * 
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		excelStream = excelService.showExcel(PlanAmount.columnHeadings(),
				baseBeanService.getListByDC(getLists()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService.saveCLogBook(null, "导出生产量计划",
				account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	/**
	 * 
	 * 打印按照查询结构打印
	 * 
	 * @return
	 */
	public String printPrev() {
		list = baseBeanService.getListByDC(getLists());

		return "printprev";
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

	public GoodFunction getGoodFunction() {
		return goodFunction;
	}

	public void setGoodFunction(GoodFunction goodFunction) {
		this.goodFunction = goodFunction;
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

	public PlanAmount getPlan() {
		return plan;
	}

	public void setPlan(PlanAmount plan) {
		this.plan = plan;
	}
	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
}
