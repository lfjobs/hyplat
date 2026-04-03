package com.tiantai.telrec.action;

import com.opensymphony.xwork2.ActionSupport;

public class ClientQueryUserParaAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3969661735130797797L;
	private String orgsimple;
	private String username;

	public String getOrgsimple() {
		return orgsimple;
	}

	public void setOrgsimple(String orgsimple) {
		this.orgsimple = orgsimple;
	}

	public String execute() throws Exception {
		/*
		 * HttpServletResponse response = ServletActionContext.getResponse();
		 * HttpServletResponse response = ServletActionContext.getRequest() List<Map>
		 * list = userDao.queryUserForUseridAndCompanyId(orgsimple, username);
		 * String outString; if (list.size() > 0) { JSONObject jsonArray =
		 * JSONObject.fromObject(list.get(0)); outString = jsonArray.toString(); }
		 * else { outString = JSONObject.fromObject(new Object()).toString(); }
		 * response.getWriter().write(outString); response.flushBuffer();
		 */
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
