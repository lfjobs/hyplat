package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.service.CCodeService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ClientQueryIndustryType extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7295610883705264437L;
	@Resource
	private CCodeService codeService;
	private List<CCode> typelist;

	public String execute() throws Exception {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONArray array = JSONArray.fromObject(typelist);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array.toString());
		return null;

	}
}
