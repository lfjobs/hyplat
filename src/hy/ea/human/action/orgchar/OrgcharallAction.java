package hy.ea.human.action.orgchar;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.orgchar.Orgcharall;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 机构图
 *
 */
@Controller
@Scope("prototype")
public class OrgcharallAction {
	@Resource
	private ShowExcelService excelService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private List<BaseBean> beans;
	private String result;
	private Orgcharall orgcharall;
	
	/**
	 *  获取路径
	 * @return
	 */
	public String getOrgcharAll(){

		String hql = " from Orgcharall o where o.companyID = ? and o.organizationID = ?";
		Object[] params = null;
		if(!orgcharall.getCompanyID().equals(orgcharall.getOrganizationID())){
			params = new Object[]{ orgcharall.getCompanyID(),orgcharall.getOrganizationID()};
		}else{
			params = new Object[]{ orgcharall.getCompanyID(),orgcharall.getCompanyID()};
		}
		beans = baseBeanService.getListBeanByHqlAndParams(hql, params);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(beans.size() > 0){
			map.put("orgcharall", beans.get(0));
		}else{
			map.put("orgcharall","isnull");
		}
		
		
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		
		orgcharall.setOrgcharallid(serverService.getServerID("orgcharall"));
		orgcharall.setCompanyID(account.getCompanyID());
		
		beans = new ArrayList<BaseBean>();
		beans.add(orgcharall);
		
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("savee", "savee");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		
		
		return "success";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deletee(){
		
		try {
			String hql = " from Orgcharall o where o.orgcharallid = ?";
			String hql0 = "delete from Orgcharall o where o.orgcharallid = ?";
			Object[] params = { orgcharall.getOrgcharallid() };
			beans = baseBeanService.getListBeanByHqlAndParams(hql, params);
			
			String path =  ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("\\");
			
			File f = new File(path+((Orgcharall)(beans.get(0))).getOrgcharUrl());
			f.delete();
			
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql0},params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deletee", "deletee");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		
		return "success";
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Orgcharall getOrgcharall() {
		return orgcharall;
	}

	public void setOrgcharall(Orgcharall orgcharall) {
		this.orgcharall = orgcharall;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	public ServerService getServerService() {
		return serverService;
	}
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
}
