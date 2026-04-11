package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.human.Staff;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ClientSaveStaff extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648225640048934791L;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private Staff cstaff;
	private String userid;

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public String execute() {
		//logger.info("调试信息");
		//logger.info("调试信息");
		String hql = "select count(*) from Staff where  staffIdentityCard = ? ";
		Object[] params = { cstaff.getStaffIdentityCard() };
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (cstaff.getStaffID() == null || "".equals(cstaff.getStaffID())) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			cstaff.setStaffCode("NO" + pcount);
			cstaff.setRecordCode("NO" + pcount);
			cstaff.setStaffID(serverService.getServerID("cstaff"));

		} else {
			String hql1 = "from Staff where staffID = ? ";
			Object[] params1 = { cstaff.getStaffID() };
			Staff s = (Staff) baseBeanService.getBeanByHqlAndParams(hql1,
					params1);
			if(s.getStaffIdentityCard()==null){
				s.setStaffIdentityCard("");
			}
			if ( s.getStaffIdentityCard().equals(cstaff.getStaffIdentityCard())) {
				count = 0;
			}
		}
		if (count > 0) {
			try {
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print("error");
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
		cstaff.setStaffStatus("00");
		baseBeanService.update(cstaff);
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(cstaff.getStaffID());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}

	/**
	 * 社会人力资源模糊查询
	 * 
	 * @return
	 */

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
