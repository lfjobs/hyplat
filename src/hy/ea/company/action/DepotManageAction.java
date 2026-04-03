package hy.ea.company.action;
//库房管理
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.DepotManage;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepotManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private DepotManage depotManage;
	private PageForm pageForm;
	private String parameter;
	private List<CCode> typelist;
	private int pageNumber;
	private String childrenID;
	private String depotID;
	private String result;
	private List<BaseBean> children;
	// 添加或修改库房管理
	public String saveDepotManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (null == depotManage.getDepotID()|| "".equals(depotManage.getDepotID())) {
			depotManage.setDepotID(serverService.getServerID("depotManage"));
			parameter = "添加库房管理(库房类型名称:"+depotManage.getDepotName()+")";
		}
		else
		{
			Object[] params = { account.getCompanyID(),depotManage.getDepotID()};
			String hql1="from DepotManage where companyID=? and depotID=? and depotState='00'";
			DepotManage bank=(DepotManage) baseBeanService.getBeanByHqlAndParams(hql1, params);
			parameter = "修改库房管理(库房类型名称:"+bank.getDepotName()+")";
			
		}
		depotManage.setCompanyID(account.getCompanyID());
		depotManage.setDepotState("00");
		//baseBeanService.update(depotManage);
		CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(depotManage);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "succ";
	}
	//ajax添加库房
	public String saveDepotByAjax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		if (null == depotManage.getDepotID()|| "".equals(depotManage.getDepotID())) {
			depotManage.setDepotID(serverService.getServerID("depotManage"));
			parameter = "添加库房管理(库房类型名称:"+depotManage.getDepotName()+")";
		}
		depotManage.setCompanyID(account.getCompanyID());
		depotManage.setDepotState("00");
		baseBeanService.update(depotManage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depotManage", depotManage);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	// 删除某条库房管理
	public String delDepotManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { account.getCompanyID(), depotManage.getDepotID()};
		String hql1 = "update DepotManage set depotState='01' where companyID = ?  and depotID = ?";
		String hql2="from DepotManage where companyID=? and depotID=? and depotState='00'";
		DepotManage bank=(DepotManage) baseBeanService.getBeanByHqlAndParams(hql2, params);
		CLogBook cLogBook = logBookService.saveCLogBook(null,"删除库房管理(库房类型名称："+ bank.getDepotName()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql1}, params);
		return "succ";
	}

	//库房管理列表
	public String getListDepotManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = {account.getCompanyID()};
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where companyID = ? and depotState='00'",
						params);
		return "tree";
	}
	
	
	public String getListDepotManageTree() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = {depotID ,account.getCompanyID()};
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where depotPID = ? and companyID = ? and depotState='00'",
						params);
		return "listDepotmanage";
	}
	
	/**
	 * 根据companyID和depotID查询其子节点
	 */
	public String getListDepotmanageByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
			String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
			Object[]  params={depotID,account.getCompanyID()};
			List<BaseBean>  depotManagelist= baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depotManagelist", depotManagelist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 去排序子机构页面
	 */
	public String toSortChildDepotManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
		Object[]  params={depotID,account.getCompanyID()};
		children= baseBeanService.getListBeanByHqlAndParams(hql, params); 
		return "sortchildren";
	}
	/**
	 * 排序子机构
	 */
	public String sortChildDepotManage() {
		String[] ids = childrenID.split("_");
		int i = 0;
		List<Object[]> parms = new ArrayList<Object[]>();
		String[] hqls = new String[ids.length];
		String hql = "update DepotManage set depotNum = ? where depotID = ?";
		for (String id : ids) {
			hqls[i] = hql;
			Object[] params = { i++, id };
			parms.add(params);
		}
		baseBeanService.executeHqlsByParamsList(null, hqls, parms);
		return getListDepotManageTree();
	}

	public String getChildrenID() {
		return childrenID;
	}
	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}
	public List<BaseBean> getChildren() {
		return children;
	}
	public void setChildren(List<BaseBean> children) {
		this.children = children;
	}
	public String getDepotID() {
		return depotID;
	}
	public void setDepotID(String depotID) {
		this.depotID = depotID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public DepotManage getDepotManage() {
		return depotManage;
	}
	public void setDepotManage(DepotManage depotManage) {
		this.depotManage = depotManage;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


}
