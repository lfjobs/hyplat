package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CMB;
import hy.ea.service.CEAService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CREMIService;
import hy.ea.service.CRoleService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SEA;
import hy.plat.service.BaseBeanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller @Scope("prototype")
public class CRoleAllotAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CEAService ceaService;
	@Resource
	private CREMIService cremiService;
	@Resource
	private CRoleService roleService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private String roleID;
	private String result;
	private List<SEA> seaList; 
	private List<BaseBean>  beans;
	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getParameter() {
		return parameter;
	}

	public List<SEA> getSeaList() {
		return seaList;
	}

	public void setSeaList(List<SEA> seaList) {
		this.seaList = seaList;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	private  String title;//区分权限跳转页面 title=title  为权限快速分配通道
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 给角色分配权限时列出所有的SEA
	 * @return
	 */
	public String getListSEAForAllot(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
	    seaList = ceaService.getListSea(account.getCompanyID());
	    String hql = " from CRole where companyID = ? order by organizationName";
	    beans=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
	    if(title!=null&&"title".equals(title)){
	    	return "success_extend";
	    }
		return "success";
	}
	
	/**
	 * 给角色分配权限时列出某个SEA下所有的菜单项及给这个菜单分配的BO的Interface
	 * @return
	 */
	public String getListCMIForAllot(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String []er = parameter.split("-");
		String eaID = er[0];
		String roleID = er[1];
		List<CMB> cmiList =null; 
		Map<String, Object> maps=new HashMap<String, Object>();
		if(title!=null&&"title".equals(title)){
			maps.put("title", "title");
		}
		cmiList = cremiService.getCMIListByEaIDAndRoleID(account.getCompanyID(),eaID, roleID,maps);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cmilist", cmiList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 保存分配的权限信息
	 * @return
	 */
	public String saveCREMI(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String []remi = parameter.split("-");
	    roleID = remi[0];
		String eaID = remi[1];
		String []mis = (3 == remi.length)?remi[2].split(","):null;
		CLogBook logBook = logBookService.saveCLogBook(null,"角色分配权限(角色名称："+ roleService.getRoleByID(account.getCompanyID(), roleID).getRoleName()
				+")", account);
		cremiService.saveCREMI(account.getCompanyID(),roleID, eaID, mis,logBook);
		if(account.getRoleID().equals(roleID)){
			// 将cir放入session中
			HashMap<String, String> irMap = cremiService.getCIRMapByRoleID(
					account.getCompanyID(), account.getRoleID());
			ActionContext.getContext().getSession().put("cir", irMap);
		}
		
		return getListSEAForAllot();
	}
	
	/**
	 * 保存多个角色分配的权限信息
	 * @return
	 */
	public String saveMoreCREMI(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String []remi = parameter.split("-");
	    roleID = remi[0];
		String eaID = remi[1];
		String[] roleIDS = remi[2]!=null?remi[2].trim().substring(0, remi[2].length()-1).split(","):null;//角色数组
		String[] mis     = remi[3]!=null&&!"".equals(remi[3].trim())?remi[3].trim().substring(0, remi[3].trim().length()-1).split(","):null;//选中interface数组
		String[] misno   = remi[4]!=null&&!"".equals(remi[4].trim())?remi[4].trim().substring(0, remi[4].trim().length()-1).split(","):null;//未选中interface数组
		CLogBook[]  logBooks=new CLogBook[roleIDS.length];
		if(roleIDS!=null){
			for (int i = 0; i < roleIDS.length; i++) {
				CLogBook logBook = logBookService.saveCLogBook(null,"角色分配权限(角色名称："+ roleService.getRoleByID(account.getCompanyID(), roleIDS[i]).getRoleName()
						+")", account);
				logBooks[i]=logBook;
			}
			cremiService.saveCREMI(account.getCompanyID(),roleIDS, eaID, mis,misno,logBooks);//重载的service方法 保存多个角色权限
		}
		if(account.getRoleID().equals(roleID)){
			// 将cir放入session中
			HashMap<String, String> irMap = cremiService.getCIRMapByRoleID(
					account.getCompanyID(), account.getRoleID());
			ActionContext.getContext().getSession().put("cir", irMap);
		}
		
		return getListSEAForAllot();
	}
}

