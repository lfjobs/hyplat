package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.office.service.impl.SignManagerServiceImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class CountSignManager extends ActionSupport {

	private int rp;
	private int pages;
	private String signstat;
	private String signid;
	private String relationtable;
	private String relationid;
	private Date starttime;
	private Date endtime;
	private SignManagerServiceImpl signManagerService;

	@SuppressWarnings("rawtypes")
	public String execute() {
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			CAccount account = (CAccount) ActionContext.getContext()
					.getSession().get("account");
			String companyid = account.getCompanyID();
			List list = this.signManagerService.QueryRelationTable(companyid, rp, pages,relationid, signstat, signid, starttime, endtime);
			@SuppressWarnings("unused")
			int resule = this.signManagerService.CountRelationTable(companyid, relationid, signstat, signid, starttime, endtime);
			String outString = JSONSerializer.toJSON(list).toString();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			response.flushBuffer();
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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

	public void setSignid(String signid) {
		this.signid = signid;
	}

	public String getRelationtable() {
		return relationtable;
	}

	public void setRelationtable(String relationtable) {
		this.relationtable = relationtable;
	}

	public String getRelationid() {
		return relationid;
	}

	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public SignManagerServiceImpl getSignManagerService() {
		return signManagerService;
	}

	public void setSignManagerService(SignManagerServiceImpl signManagerService) {
		this.signManagerService = signManagerService;
	}

}
