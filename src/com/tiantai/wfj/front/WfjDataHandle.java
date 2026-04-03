package com.tiantai.wfj.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;


import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.CStaffCos;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

@Controller
@Scope("prototype")
public class WfjDataHandle {
	
	@Resource
	private BaseBeanService beanService;
	@Resource
	private ServerService serverService;
	
	private String result;
	
	public String ComAccountAdd(){
		TEshopCusCom cusCom=new TEshopCusCom();
		cusCom.setSccId(serverService.getServerID("sccid"));
		cusCom.setCusType("2");
		cusCom.setState("2");
		TEshopCustomer customer=new TEshopCustomer();
		customer.setCustid(serverService.getServerID(""));
		return "";
	}
	
	public String getCusCom(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		TEshopCusCom cusCom=(TEshopCusCom)beanService.getBeanByHqlAndParams("from TEshopCusCom c where companyId=? and state=?", new Object[]{account.getCompanyID(),"2"});
		request.setAttribute("cusCom", cusCom);
		if(cusCom!=null){
			Staff staff=(Staff)beanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cusCom.getStaffid()});
			request.setAttribute("staff", staff);
			String type="list";
			request.setAttribute("type", type);
		}
		
		
		return "selete";
	}
	
	/**
	 * 根据参数获取人员staffID
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public String getSearchStaff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String parameter=request.getParameter("parameter");
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "select * from view_sc s where s.companyid=? and s.staffname like ? and s.staffid not in (select e.staffid from t_eshop_cuscom e)";
		Object[] params = { account.getCompanyID(), "%" + parameter + "%" };
		List<BaseBean> list = beanService.getListBeanBySqlAndParams(sql,params);
		List<String[]> Arraylist = new ArrayList<String[]>();
		for (BaseBean baseBean : list) {
			CStaffCos staffCos = (CStaffCos) baseBean;
			Arraylist.add(new String[] { staffCos.getStaffName(),
					staffCos.getStaffID() });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Arraylist", Arraylist);
		map.put("stafflist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
}
