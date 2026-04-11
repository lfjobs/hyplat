package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.EnterpriseStamp;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SaveStampAction extends ActionSupport {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private String id;
	private String orgid;
	private String customerid;
	private String parameter;
	private EnterpriseStamp enterpriseStamp;

	public String execute() {
		String hql = "from CAccount where accountID=?";
		CAccount caccount = (CAccount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { this.customerid });
		HttpServletResponse response = ServletActionContext.getResponse();
		if (caccount != null) {
			if (enterpriseStamp.getEnterpriseStampID() == null
					|| "".equals(enterpriseStamp.getEnterpriseStampID())) {
				enterpriseStamp.setEnterpriseStampID(serverService
						.getServerID("enterpriseStamp"));
				parameter = "添加企业印章管理(印章名称:" + enterpriseStamp.getStampName()
						+ ")";
			}
			enterpriseStamp.setOrganizationID(orgid);
			enterpriseStamp.setCompanyID(caccount.getCompanyID());
			baseBeanService.update(enterpriseStamp);
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(enterpriseStamp.getEnterpriseStampID());
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return null;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnterpriseStamp getEnterpriseStamp() {
		return enterpriseStamp;
	}

	public void setEnterpriseStamp(EnterpriseStamp enterpriseStamp) {
		this.enterpriseStamp = enterpriseStamp;
	}
}
