package hy.ea.ccompany.action;
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
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CDepotManageAction {
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
	private String type;
	// 添加或修改库房管理
	public String saveDepotManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		if (null == depotManage.getDepotID()|| "".equals(depotManage.getDepotID())) {
			depotManage.setDepotID(serverService.getServerID("depotManage"));
			parameter = "添加库房管理(库房类型名称:"+depotManage.getDepotName()+")";
		}
		else
		{
			Object[] params = { companyID,depotManage.getDepotID()};
			String hql1="from DepotManage where companyID=? and depotID=? and depotState='00'";
			DepotManage bank=(DepotManage) baseBeanService.getBeanByHqlAndParams(hql1, params);
			parameter = "修改库房管理(库房类型名称:"+bank.getDepotName()+")";
			
		}
		depotManage.setCompanyID(companyID);
		depotManage.setDepotState("00");
		children = new ArrayList<BaseBean>();
		children.add(depotManage);
		CLogBook logBook = logBookService.saveCLogBook(companyID, parameter, account);
		children.add(logBook);
		baseBeanService.executeHqlsByParamsList(children, null, null);
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
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		depotManage.setCompanyID(companyID);
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
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		Object[] params = { companyID, depotManage.getDepotID()};
		String hql1 = "update DepotManage set depotState='01' where companyID = ?  and depotID = ?";
		String hql2="from DepotManage where companyID=? and depotID=? and depotState='00'";
		DepotManage bank=(DepotManage) baseBeanService.getBeanByHqlAndParams(hql2, params);
		CLogBook logBook = logBookService.saveCLogBook(companyID,"删除库房管理(库房类型名称："+ bank.getDepotName()+")", account);
		children = new ArrayList<BaseBean>();
		children.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(children, new String[]{hql1}, params);
		return "succ";
	}

	//库房管理列表
	public String getListDepotManage() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		Object[] params = {companyID};
		typelist = codeService.getCCodeListByPID(companyID,"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where companyID = ? and depotState='00'",
						params);
		return "tree";
	}
	
	
	public String getListDepotManageTree() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		Object[] params = {depotID ,companyID};
		typelist = codeService.getCCodeListByPID(companyID,"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where depotPID = ? and companyID = ? and depotState='00'",
						params);
		return "listDepotmanage";
	}
	/*
	 * xyz为验货选择仓库新加
	 */
	public String getListDepotManageTreenew() {
		// 调拨出库里面根据选择的公司来选择对应仓库
		HttpServletRequest re=ServletActionContext.getRequest();
		String companyID=re.getParameter("comIdOne");
		String deType=re.getParameter("deType");
		String fiveClear=re.getParameter("fiveClear");
		if(companyID==null||"".equals(companyID)){
			companyID=(String) ActionContext.getContext().getSession().get("companyID");
		}
		Object[] params = {depotID ,companyID};
		typelist = codeService.getCCodeListByPID(companyID,"scode20101014v5zed7cukk0000000004");
		String str="";
		if("screen".equals(deType)){
			str=" and depotName!='退货库' and depotName!='物流库' and depotName!='成品库' and depotName!='资金仓库'";
		}
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where depotPID = ? and companyID = ? and depotState='00' and depotType = '1' "+str,
						params);
		if(fiveClear!=null&&!"".equals(fiveClear)){
			String fiveClearName=re.getParameter("fiveClearName");
			List<BaseBean> list=pageForm.getList();
			DepotManage d=new DepotManage();
			d.setDepotID(fiveClear);
			d.setDepotName(fiveClearName+"库");
			list.add(d);
			pageForm.setList(list);
		}
		return "listDepotmanagenew";
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
		
		// 调拨出库里面根据选择的公司来选择对应仓库
		HttpServletRequest re=ServletActionContext.getRequest();
		String companyID=re.getParameter("comIdOne");
		if(companyID==null||"".equals(companyID)){
			companyID=account.getCompanyID();
		}
			String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
			Object[]  params={depotID,companyID};
			List<BaseBean>  depotManagelist= baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depotManagelist", depotManagelist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 根据companyID和depotID查询其子节点并排除不需要的仓库  
	 * 		zj
	 */
	public String getListDepotmanage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		
		// 调拨出库里面根据选择的公司来选择对应仓库
		HttpServletRequest re=ServletActionContext.getRequest();
		String companyID=re.getParameter("comIdOne");
		if(companyID==null||"".equals(companyID)){
			companyID=account.getCompanyID();
		}
		String sqls=" and depotName!='退货库' and depotName!='物流库' and depotName!='成品库' and depotName!='资金仓库'";
		String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' and depotType = '1' ";
		Object[]  params={depotID,companyID};
		hql+=(sqls+" order by depotNum");
		List<BaseBean>  depotManagelist= baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depotManagelist", depotManagelist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 根据companyID和depotID查询其子节点
	 *
	 */
	public String getSelectList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest request =ServletActionContext.getRequest();
		String companyID  = request.getParameter("companyID");
		String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' and depotType = ? ";
		Object[]  params={depotID,companyID,type};
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
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' order by depotNum ";
		Object[]  params={depotID,companyID};
		children= baseBeanService.getListBeanByHqlAndParams(hql, params); 
		return "sortchildren";
	}
	/**
	 * 排序子机构
	 */
	public String sortChildDepotManage() {
		String[] ids = childrenID.split("_");
		String hql = "update DepotManage set depotNum = ? where depotID = ?";
		int i = 0;
		Object[] params = new Object[ids.length+1];
		for (String id : ids) {
			params[i] = i;
			params[i+1]=id;
			i++;
			//baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
