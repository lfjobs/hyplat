package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.bo.office.DocumentTemplate;
import hy.ea.office.service.ZOfficeService;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 卓正中间件管理
 */
public class ZOfficeAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ZOfficeService zOfficeService;
	private String result;

	/**
	 * 保存office 包括 word,excel
	 * 
	 * @return
	 */
	public String saveOffice() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String storepath = request.getParameter("docPath");
		zOfficeService.saveOffice(request, response, realpath, storepath);
		return "none";
	}

	/**
	 * 创建一个新的空白word,或者excel fileType:W,E temp：用于区分创建的是否是模板
	 * 
	 * @throws IOException
	 * 
	 * 
	 */

	public String createBlankOffice() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileType = request.getParameter("fileType");
		String temp = request.getParameter("temp");
		String storepath = "";// 文件创建后存储位置
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		// 模板和非模板存储位置区别
		if (temp != null && temp.equals("temp")) {
			storepath = "document/template/" + account.getCompanyID();
		} else {
			storepath = "document/" + account.getCompanyID();
		}
		String blankOfficePath = zOfficeService.createOffice(realpath,
				fileType, null, storepath);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", blankOfficePath);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();

		return "success";

	}

	/*
	 * 
	 * 根据模板创建office,包括Word,Excel
	 */
	public String createOfficeByTemp() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileType = request.getParameter("fileType");
		String templateId = request.getParameter("templateId");
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String storepath = "document/" + account.getCompanyID();
		String officePath = "";
		if (templateId != "") {
			String hql = "from DocumentTemplate where templateId = ?";
			DocumentTemplate dt = (DocumentTemplate) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { templateId });

			officePath = zOfficeService.createOffice(realpath, fileType, dt
					.getTemplatePath(), storepath);
		} else {
			officePath = zOfficeService.createOffice(realpath, fileType, null,
					storepath);

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", officePath);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();

		return "success";

	}

	/**
	 * 
	 * 删除Office文件
	 * 
	 * @return
	 */

	public String deleteOffice() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String docPath = request.getParameter("docPath");
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String filePath = realpath + docPath;
		zOfficeService.deleteOffice(filePath);
		return "success";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}