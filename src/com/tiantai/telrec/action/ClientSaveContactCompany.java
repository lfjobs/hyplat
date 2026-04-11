package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.company.ContactCompany;

import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ClientSaveContactCompany extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3157489920110412162L;
	private ContactCompany contactCompany;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
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

	public String execute() {

		if (null == contactCompany.getCcompanyID()
				|| "".equals(contactCompany.getCcompanyID())) {
			contactCompany.setCcompanyID(serverService
					.getServerID("contactCompany"));
		}
		baseBeanService.update(contactCompany);
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(contactCompany.getCcompanyID());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}
}
