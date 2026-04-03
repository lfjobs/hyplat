package hy.ea.production.action.cprocedure;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.production.MemberAllot;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

/**
 * 
 * 人员分配管理
 * 
 * @author zzl
 * 
 */
@Controller
@Scope("prototype")
public class MemberAllotlAction {
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
	private MemberAllot memberAllot;// 实体类
	private String fiveClear;
	private List<BaseBean> list;
	
	private String type;						//类别
	private String category; 				//类型
	

	public String getMemberList() {
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());

		return "list";
	}

	private DetachedCriteria getLists() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(MemberAllot.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("type", type));
		dc.add(Restrictions.eq("category", category));
		if(fiveClear!=null&&!"".equals(fiveClear))
			dc.add(Restrictions.eq("fiveClear", fiveClear));
		if (search != null && "search".equals(search)) {
			memberAllot = (MemberAllot) session.get("tablesearch");
			if (memberAllot.getProductCode() != null
					&& !memberAllot.getProductCode().equals("")) {
				dc.add(Restrictions.like("productCode",
						memberAllot.getProductCode(), MatchMode.ANYWHERE));
			}
			if (memberAllot.getProductName() != null
					&& !memberAllot.getProductName().equals("")) {
				dc.add(Restrictions.like("productName",
						memberAllot.getProductName(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", memberAllot);
		return getMemberList();
	}

	public String getAdd() {
		if (memberAllot != null && !memberAllot.getMakey().equals("")) {
			String hql = "from MemberAllot where makey = ?";

			memberAllot = (MemberAllot) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { memberAllot.getMakey() });
		}

		return "add";
	}

	public String savePlan() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (memberAllot.getMakey() == null || memberAllot.getMakey().equals("")) {
			memberAllot.setMaid(serverService.getServerID("maid"));
		}
		memberAllot.setCompanyID(account.getCompanyID());
		baseBeanService.update(memberAllot);

		return Action.SUCCESS;
	}

	public String deletePlans() {
		baseBeanService.deleteBeanByKey(MemberAllot.class,
				memberAllot.getMakey());

		return Action.SUCCESS;
	}

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		excelStream = excelService.showExcel(MemberAllot.columnHeadings(),
				baseBeanService.getListByDC(getLists()));
		CLogBook cLogBook = logBookService.saveCLogBook(null, "人员分配", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String print() {
		list = baseBeanService.getListByDC(getLists());
		return "print";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public MemberAllot getMemberAllot() {
		return memberAllot;
	}

	public void setMemberAllot(MemberAllot memberAllot) {
		this.memberAllot = memberAllot;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	
	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
