package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			logger.error("操作异常", e);
		}
		return null;
	}
}
