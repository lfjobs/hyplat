package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bean.FlexiGridJSonBean;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

public class WebQueryTelByCustomerId extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4743228214492292443L;
	private com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService;

	@SuppressWarnings({ "unused", "unchecked" })
	public String execute() throws Exception {
		FlexiGridJSonBean bean = new FlexiGridJSonBean();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyid = account.getCompanyID();
		String user_name = request.getParameter("user_name");

		String customer_name = request.getParameter("customer_name");
		user_name = java.net.URLDecoder.decode(user_name, "UTF-8");
		customer_name = java.net.URLDecoder.decode(customer_name, "UTF-8");
		String startdate = request.getParameter("startdate");
		int pages = Integer.valueOf(request.getParameter("page"));

		int rp = Integer.valueOf(request.getParameter("rp"));
		String sortname = request.getParameter("sortname");
		String sortorder = request.getParameter("sortorder");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date start_time = null;
		
		// rows = dao.findByIpDate(sortname, sortorder, (pages - 1) * rp,
		// rp);
		try {
			if (startdate != null && !startdate.equals("")
					&& !startdate.equals("null")) {
				start_time = df.parse(startdate);
			}
			bean.rows = telInfoService.queryTelrecInfoForParams(companyid, rp,
					pages, customer_name, user_name, start_time);
			bean.total = telInfoService.queryTelrecInfoForParamsTotal(
					companyid, rp, pages, customer_name, user_name, start_time);
			bean.page = pages;

			JsonConfig jsonConfig = new JsonConfig();

			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

			/*
			 * jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			 * 
			 * public boolean apply(Object arg0, String arg1, Object arg2) { if
			 * ((!arg1.equals("start_time")) || (!arg1.equals("end_time"))) {
			 * return false; } else { return true; } } });
			 */
			JSONObject jsonArray = JSONObject.fromObject(bean, jsonConfig);
			String outString = jsonArray.toString();
			//logger.info("值：{}", outString);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			// logger.info("值：{}", outString);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("操作异常", e);
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
