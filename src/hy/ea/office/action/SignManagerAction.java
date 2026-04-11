package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.EnterpriseStamp;
import hy.ea.bo.office.SignManager;
import hy.ea.office.service.impl.SignManagerServiceImpl;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class SignManagerAction extends ActionSupport {

	private String signid;
	private String signstat;
	private String relationid;
	private String position;
	private String relationtable;
	private String signmanagerkey;
	private Date datetime;
	private SignManager signManager;
	@Resource
	private SignManagerServiceImpl signManagerService;
	@Resource
	private BaseBeanService baseBeanService;

	public SignManagerServiceImpl getSignManagerService() {
		return signManagerService;
	}

	public void setSignManagerService(SignManagerServiceImpl signManagerService) {
		this.signManagerService = signManagerService;
	}

	public String InsertSignManager() {

		try {

			SignManager signmanager = this.GetSignManager();
			signManagerService.InsertSignManager(signmanager);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("ok");
			response.flushBuffer();
		} catch (Exception e) { // TODO Auto-generated catch block
			logger.error("操作异常", e);
		}

		return null;
	}

	public String UpdatePosition() {

		try {
			@SuppressWarnings("unused")
			String stampid = this.getStampid(signid);
			boolean bool = signManagerService.UpdatePosition(position,
					signmanagerkey);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			if (bool == true) {
				response.getWriter().write("true");
			} else {
				response.getWriter().write("false");
			}
			response.flushBuffer();
		} catch (Exception e) { // TODO Auto-generated catch block
			logger.error("操作异常", e);
		}

		return null;
	}

	public String UpdateSignManager() {

		try {
			// SignManager signManager = this.GetSignManager();
			// boolean bool = signManagerService.SignManager(signManager,
			// signstat, signid,
			// relationid);
			@SuppressWarnings("unused")
			SignManager signmanager = this.GetSignManager();
			HttpServletResponse response = ServletActionContext.getResponse();
			@SuppressWarnings("unused")
			String stampid = this.getStampid(signid);
			String stampstat = this.getStampstat(signstat);
			boolean bool = signManagerService.UpdateSignstat(stampstat,
					signmanagerkey);
			response.setCharacterEncoding("UTF-8");
			if (bool == true) {
				response.getWriter().write("true");
			} else {
				response.getWriter().write("false");
			}
			response.flushBuffer();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}

		return null;
	}

	// 返回图片路径与位置
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String StampManager() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Object[] params = { ((CAccount) session.get("account"))
					.getCompanyID() };
			String hql = " from EnterpriseStamp where companyid=?";
			List StampList = baseBeanService.getListBeanByHqlAndParams(hql,
					params);
			if (signmanagerkey == null || signmanagerkey.equals("")
					|| signmanagerkey.equals("null")) {
				return null;
			} else {
				List<SignManager> list = signManagerService
						.getSignManager(signmanagerkey);
				SignManager Stamp = list.get(0);
				String path = "";
				for (int i = 0; i < StampList.size(); i++) {
					EnterpriseStamp aff = (EnterpriseStamp) StampList.get(i);
					if (aff.getEnterpriseStampID().equals(Stamp.getSignid())) {
						path = aff.getScanningAccessories();
						break;
					}
				}
				response.setCharacterEncoding("UTF-8");
				String StampPosition = Stamp.getPosition();
				String[] str = StampPosition.split(",");
				String top = str[0];
				String left = str[1];
				JSONObject object = new JSONObject();
				object.put("path", path);
				object.put("top", top);
				object.put("left", left);
				String outString = object.toString();
				response.getWriter().write(outString);
				//logger.info("值：{}", outString);
				response.flushBuffer();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return null;
	}

	// 将接到signid,转换与数据库对应
	@SuppressWarnings("rawtypes")
	public String getStampid(String signid) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Object[] params = { ((CAccount) session.get("account")).getCompanyID() };
		String hql = " from EnterpriseStamp where companyid=?";
		List list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		String stampid = "";
		if (signid != null) {
			for (int i = 0; i < list.size(); i++) {
				EnterpriseStamp aff = (EnterpriseStamp) list.get(i);
				if (signid.equals(aff.getStampName())) {
					stampid = aff.getEnterpriseStampID();
					break;
				}
			}
		}
		return stampid;
	}

	// 将接到的signstat,转换与数据库对应
	public String getStampstat(String signstat) {
		String stampstat = "";
		if (signstat.equals("已审批")) {
			stampstat = "true";
		} else if (signstat.equals("已报废")) {
			stampstat = "false";
		}
		return stampstat;
	}

	// 封装SignManager对象
	public SignManager GetSignManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) request.getSession().getAttribute(
				"account");
		String accountid = account.getAccountID();
		SignManager signmanager = new SignManager();
		signmanager.setSignmanagerid(accountid);
		signmanager.setPosition(position);
		signmanager.setRelationid(relationid);
		signmanager.setRelationtable(relationtable);
		signmanager.setSignid(signid);
		signmanager.setSignstat(signstat);
		signmanager.setDatetime(new Date());
		return signmanager;
	}

	public String getSignstat() {
		return signstat;
	}

	public void setSignstat(String signstat) {
		this.signstat = signstat;
	}

	public String getSignid() {
		return signid;
	}

	public String getSignmanagerkey() {
		return signmanagerkey;
	}

	public void setSignmanagerkey(String signmanagerkey) {
		this.signmanagerkey = signmanagerkey;
	}

	public void setSignid(String signid) {
		this.signid = signid;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRelationid() {
		return relationid;
	}

	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}

	public SignManager getSignManager() {
		return signManager;
	}

	public void setSignManager(SignManager signManager) {
		this.signManager = signManager;
	}

	public String getRelationtable() {
		return relationtable;
	}

	public void setRelationtable(String relationtable) {
		this.relationtable = relationtable;
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
}
