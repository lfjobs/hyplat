package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.plat.service.BaseBeanService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
/**
 * 企业印章管理
 */
public class QuerySignListByAccount extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private BaseBeanService baseBeanService;

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	
	@SuppressWarnings("rawtypes")
	public String execute() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from EnterpriseStamp where companyid=?";
		Object[] params = { ((CAccount) session.get("account")).getCompanyID() };

		@SuppressWarnings("unused")
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		HttpServletResponse response = ServletActionContext.getResponse();
		List list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		try {
			String outString = JSONSerializer.toJSON(list).toString();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			response.flushBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
