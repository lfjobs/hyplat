package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bean.FlexiGridJSonBean;

@SuppressWarnings("serial")
public class WebQueryTelForCustomer extends ActionSupport {

	private com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService;

	@SuppressWarnings({ "unused", "rawtypes" })
	public String execute() throws Exception {
		FlexiGridJSonBean bean = new FlexiGridJSonBean();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyid = account.getCompanyID();
		try {
			List list = telInfoService.queryTelForCustomer_nameAll(companyid);
			// JSONObject jsonArray = JSONObject.fromObject(list);
			// String outString = jsonArray.toString();
			String outString = JSONSerializer.toJSON(list).toString();
			outString = "{\"earTag\":" + outString + "}";
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public com.tiantai.telrec.service.ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(
			com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}
}
