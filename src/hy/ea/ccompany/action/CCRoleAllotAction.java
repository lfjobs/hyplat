package hy.ea.ccompany.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CMB;
import hy.ea.service.CEAService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CREMIService;
import hy.ea.service.CRoleService;
import hy.plat.bo.SEA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller @Scope("prototype")
public class CCRoleAllotAction {
	
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
	private String compID;
	

	public String getCompID() {
		return compID;
	}

	public void setCompID(String compID) {
		this.compID = compID;
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
	
	/**
	 * 给角色分配权限时列出所有的SEA
	 * @return
	 */
	public String getListSEAForAllot(){
		seaList = ceaService.getListSea(compID);
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
		List<CMB> cmiList = cremiService.getCMIListByEaIDAndRoleID(compID, eaID, roleID, null);
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
		CLogBook logBook = logBookService.saveCLogBook(compID,"角色分配权限(角色名称："+ roleService.getRoleByID(compID, roleID).getRoleName()
				+")", account);
		cremiService.saveCREMI(compID,roleID, eaID, mis,logBook);
		/**
		 * 当前公司与所操作公司为同一公司且为同一账号则执行以下代码
		 */
		if(account.getCompanyID().equals(compID)&&account.getRoleID().equals(roleID)){
			HashMap<String, String> irMap = cremiService.getCIRMapByRoleID(
					compID, account.getRoleID());
			ActionContext.getContext().getSession().put("cir", irMap);
		}
		return getListSEAForAllot();
	}
}

