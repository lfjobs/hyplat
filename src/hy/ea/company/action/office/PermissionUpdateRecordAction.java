package hy.ea.company.action.office;

import hy.ea.bo.company.Dtpermissionupdaterecord;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

public class PermissionUpdateRecordAction {
	@Resource
	private BaseBeanService baseBeanService;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private Dtpermissionupdaterecord permissionRecord;
	
	
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				permissionRecord);
		return getListPermissionupdateRecord();
	}
	
	/**
	 * 获得系统权限修改的所有记录
	 * @return list
	 */
	
	public String getListPermissionupdateRecord(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?20:pageNumber), getList());
		return"list";	
	}
	
	
	/**
	 * 根据conpanyID查询所有记录修改信息
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(Dtpermissionupdaterecord.class);
		if (search != null && search.equals("search")) {
			permissionRecord = (Dtpermissionupdaterecord) session.get("tablesearch");
			if(permissionRecord.getCrolechangelogodate()!=null&&!"".equals(permissionRecord.getCrolechangelogodate()))
			{
				dc.add(Restrictions.like("crolechangelogodate",permissionRecord.getCrolechangelogodate()));
			}
			
			
			if(permissionRecord.getCroledetails()!=null&&!"".equals(permissionRecord.getCroledetails()))
			{
				dc.add(Restrictions.like("croledetails",permissionRecord.getCroledetails(),
						MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.desc("crolechangelogodate"));
		return dc;
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


	public Dtpermissionupdaterecord getPermissionRecord() {
		return permissionRecord;
	}


	public void setPermissionRecord(Dtpermissionupdaterecord permissionRecord) {
		this.permissionRecord = permissionRecord;
	}
	
}
