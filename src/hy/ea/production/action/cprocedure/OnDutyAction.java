package hy.ea.production.action.cprocedure;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.production.DtDutyBranch;
import hy.ea.bo.production.DtOnDuty;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 分配值班
 * @author lf
 *
 */
public class OnDutyAction {
	
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
	private DtOnDuty onDuty;
	private Map<String, DtDutyBranch> dutyb;
	private List<BaseBean> dblist;
	private String fiveClear;
	private String type;
	private String category;
	public String getSearchs(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("duty", onDuty);
		return getPutyList();
	}

	public String getPutyList() {
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());

		return "list";
	}

	private DetachedCriteria getLists() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(DtOnDuty.class);
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		dc.addOrder(Order.desc("allotdate"));
		dc.add(Restrictions.eq("type",type));
		dc.add(Restrictions.eq("category",category));
		if(fiveClear!=null&&!"".equals(fiveClear))
			dc.add(Restrictions.eq("fiveClear",fiveClear));
		if (search != null && "search".equals(search)) {
			onDuty = (DtOnDuty) session.get("duty");
			if (onDuty.getProductcode() != null
					&& !onDuty.getProductcode().equals("")) {
				dc.add(Restrictions.like("productcode", onDuty.getProductcode(),
						MatchMode.ANYWHERE));
			}
			if (onDuty.getProductname() != null
					&& !onDuty.getProductname().equals("")) {
				dc.add(Restrictions.like("productname", onDuty.getProductname(),
						MatchMode.ANYWHERE));
			}

		}

		return dc;
	}
	
	public String getAddPage(){
		return "addpage";
	}
	
	public String getedit(){
		onDuty=(DtOnDuty)baseBeanService.getBeanByHqlAndParams("from DtOnDuty where dutyid=?", new Object[]{onDuty.getDutyid()});
		dblist=baseBeanService.getListBeanByHqlAndParams("from DtDutyBranch where dutyid=? order by sdate", new Object[]{onDuty.getDutyid()});
		return "addpage";
	}
	
	public String savePuty(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Company company=(Company)session.get("currentcompany");
		String orgid=session.get("organizationID").toString();
		String orgname=session.get("organizationName").toString();
		List<BaseBean> baselist=new ArrayList<BaseBean>();	
		String str="修改";
		if(onDuty.getDutyid()==null||onDuty.getDutyid().equals("")){
			str="保存";
			onDuty.setDutyid(serverService.getServerID("onduty"));
		}
		onDuty.setCompanyid(company.getCompanyID());
		onDuty.setCompanyname(company.getCompanyName());
		onDuty.setOrgid(orgid);
		onDuty.setOrgname(orgname);
		onDuty.setStaffid(account.getStaffID());
		onDuty.setStaffname(account.getStaffName());
		onDuty.setFiveClear(fiveClear);
		
		baselist.add(onDuty);
		for (DtDutyBranch dutyBranch : dutyb.values()) {
			if(dutyBranch.getBranchid()==null||dutyBranch.getBranchid().equals("")){
				dutyBranch.setBranchid(serverService.getServerID("dutybranch"));
				dutyBranch.setDutyType(onDuty.getDutyType());
				dutyBranch.setDutyid(onDuty.getDutyid());
			}
			dutyBranch.setSdate(Utilities.getDateFromString(dutyBranch.getStrdate(), "HH:mm:dd"));
			dutyBranch.setEdate(Utilities.getDateFromString(dutyBranch.getEnddate(), "HH:mm:dd"));
			baselist.add(dutyBranch);
		}
		CLogBook cLogBook = logBookService
				.saveCLogBook(null, str+onDuty.getProductname()+"分配班值", account);
		baselist.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baselist, null,null);
		return "success";
	}
	
	/**
	 * 
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		excelStream = excelService.showExcel(DtOnDuty.columnHeadings(),
				baseBeanService.getListByDC(getLists()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService
				.saveCLogBook(null, "导出分配班值", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
	
	/**
	 * 
	 * 删除计划
	 * @return
	 */
	public String deletePlans(){
		String hql = "delete DtOnDuty where dutyid = ?";
		String hqls = "delete DtDutyBranch where dutyid = ?";
		
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql,hqls}, new Object[]{onDuty.getDutyid()});
	    
		
	    return Action.SUCCESS;
	}
	
	public String getprint(){
		dblist=baseBeanService.getListByDC(getLists());
		return "print";
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

	public DtOnDuty getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(DtOnDuty onDuty) {
		this.onDuty = onDuty;
	}

	public Map<String, DtDutyBranch> getDutyb() {
		return dutyb;
	}

	public void setDutyb(Map<String, DtDutyBranch> dutyb) {
		this.dutyb = dutyb;
	}

	public List<BaseBean> getDblist() {
		return dblist;
	}

	public void setDblist(List<BaseBean> dblist) {
		this.dblist = dblist;
	}

	/**
	 * @return the excelStream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * @param excelStream the excelStream to set
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
