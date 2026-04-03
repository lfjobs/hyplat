/*
 *北京天太世统科技有限公司 010-64164005 
 *author：zg。email：longsky_03@sina.com
 */

package hy.ea.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alipay.util.httpClient.HttpRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Cmodal;
import hy.ea.bo.Company;
import hy.ea.bo.OrgModal;
import hy.ea.bo.human.COrganization;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SInterface;
import hy.plat.bo.Smodal;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SInterfaceService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

/**
 * 模块自定义功能
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class CmodalAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CompanyService companyService;
	@Resource
	private COrganizationService organizationService;
	@Resource
	private SInterfaceService sinterfaceService;
	@Resource
	private ServerService idserver;
	private COrganization organization;
	private String organizationID;
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private Cmodal entity;
	private String result;
	private Company company;
	private List<SInterface> SInterfaceList;
	/**
	 * 预期作为：修改模块名称，启用，禁用的功能。
	 */
	public String createCmodal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "update Cmodal c set c.modalname=? ,c.status = ? where c.modalid = ? ";
		Object[] parms = new Object[] { entity.getModalname(),
				entity.getStatus(), entity.getModalid() };
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "修改模块内容", account);
		beans.add(account);
		beans.add(logBook);
		this.baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, parms);
		return getListCmodal();
	}

	/**
	 * 列表查询功能
	 */
	public String getListCmodal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Map<String, Object> map = new HashMap<String, Object>();
		//所有展示的数据
		String hql ="select new Cmodal(t.modalid,t.modalname,t.pmodalid) from Cmodal t where t.companyid=? and t.status=?";
		List<BaseBean> lst=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"01"});
		//当前公司已配置的数据
		String chql = "select t.modal_id from dt_org_modal t where t.company_id=? and t.org_or_com_id=?";
		List<Object> clst=baseBeanService.getListBeanBySqlAndParams(chql, new Object[]{account.getCompanyID(),organizationID});
		String temp="";
		for (int i = 0; i < clst.size(); i++) {
			Object o=clst.get(i);
			temp+=o.toString();
		}
		map.put("modalList", lst);
		map.put("checkList", temp);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据父模块查询子功能
	 */
	public String getCmodalByPid() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list = baseBeanService
				.getListBeanByHqlAndParams(
						"from Smodal where companyid= ? and pmodalid = ? order by sortsn",
						new Object[] { account.getCompanyID(),
								entity.getPmodalid() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modalList", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 进入模块树管理
	 * 
	 * @return
	 */
	public String modalManage() {
		ActionContext.getContext().getSession()
				.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		company = companyService.getCompanyByCompanyID(account.getCompanyID());
		return "modal_manager";
	}


	/**
	 * 
	 */
	public String saveMenu() {
		
		String menuids=ServletActionContext.getRequest().getParameter("menuids");
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> beans=new ArrayList<BaseBean>();
		String[] ids= menuids.split(",");
		OrgModal om=null;
		for (int i = 0; i < ids.length; i++) {
			om=new OrgModal();
			om.setCompanyId(account.getCompanyID());
			om.setModalId(idserver.getServerID("OrgModal"));
			om.setOrgnizationId(organizationID);
			om.setModalId(ids[i]);
			beans.add(om);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{"delete OrgModal where companyId=? and orgnizationId=?"}
				,new Object[]{account.getCompanyID(),organizationID});
		return Action.SUCCESS;
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

	public Cmodal getEntity() {
		return entity;
	}

	public void setEntity(Cmodal entity) {
		this.entity = entity;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public COrganization getOrganization() {
		return organization;
	}

	public void setOrganization(COrganization organization) {
		this.organization = organization;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public List<SInterface> getSInterfaceList() {
		return SInterfaceList;
	}

	public void setSInterfaceList(List<SInterface> sInterfaceList) {
		SInterfaceList = sInterfaceList;
	}
	
	
}
