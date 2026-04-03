package com.tiantai.telrec.action;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class WebClearSession extends ActionSupport {
	/**
	 * 将Session的内容清空
	 */
	private static final long serialVersionUID = 1986760766355839467L;

	@SuppressWarnings("rawtypes")
	public String execuste() {
		HttpSession session = (HttpSession) ServletActionContext.getRequest()
				.getSession();
		Enumeration enm = session.getAttributeNames();
		while (enm.hasMoreElements()) {
			session.removeAttribute(enm.nextElement().toString());
		}
		return SUCCESS;
	}
}
