package com.tiantai.telrec.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ClientTestConnection extends ActionSupport {
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.getWriter().write("true");
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
