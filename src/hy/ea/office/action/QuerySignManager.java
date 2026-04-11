package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.EnterpriseStamp;
import hy.ea.bo.office.SignManager;
import hy.ea.office.dao.impl.FlexiGridJSon;
import hy.ea.office.service.impl.SignManagerServiceImpl;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class QuerySignManager extends ActionSupport {

	private String signmanagerid;
	private String signstat;
	private String signid;
	private String relationtable;
	private Date starttime;
	private Date endtime;
	@Resource
	private SignManagerServiceImpl signManagerService;
	@Resource
	private BaseBeanService baseBeanService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		try {
			FlexiGridJSon bean = new FlexiGridJSon();
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			Map<String, Object> session = ActionContext.getContext().getSession();
			int pages = Integer.valueOf(request.getParameter("page"));
			int rp = Integer.valueOf(request.getParameter("rp"));
			String hql = " from EnterpriseStamp where companyid=?";
			String hql1 = " from CAccount where companyid=?";
			Object[] params = { ((CAccount) session.get("account")).getCompanyID() };
			CAccount account = (CAccount) ActionContext.getContext().getSession()
					.get("account");
			List Stamplist = baseBeanService.getListBeanByHqlAndParams(hql, params);
			List CAccountlist = baseBeanService.getListBeanByHqlAndParams(hql1, params);
			if(signmanagerid != null && !signmanagerid.equals("") && !signmanagerid.equals("null"))
			{
				for(int i=0;i<CAccountlist.size();i++)
				{
					CAccount caccount = (CAccount) CAccountlist.get(i);
					if(signmanagerid.equals(caccount.getAccountID()))
					{
						break;
					}
					else if(signmanagerid.equals(caccount.getAccountName()))
					{
						signmanagerid = caccount.getAccountID();
						break;
					}
				}
			}
			else
			{
				signmanagerid = account.getAccountID();
			}
			if(signid != null && !signid.equals("") && !signid.equals("null"))
			{
				for(int i=0;i<Stamplist.size();i++)
				{
					EnterpriseStamp aff = (EnterpriseStamp) Stamplist.get(i);
					if(signid.equals(aff.getStampName()))
					{
						signid = aff.getEnterpriseStampID();
						break;
					}
				}
			}
			if(signstat != null && !signstat.equals("") && !signstat.equals("null"))
			{
				if(signstat.equals("已审批"))
				{
					signstat = "true";
				}
				else if(signstat.equals("已报废"))
				{
					signstat = "false";
				}
			}
			else
			{
				signstat = "true";
			}
			List<SignManager> list = signManagerService.QuerySignManager(signmanagerid, rp, pages,
					signstat, signid, relationtable, starttime, endtime);
			int result = signManagerService.CountSignManager(signmanagerid, rp, pages,
					signstat, signid, relationtable, starttime, endtime);
			Object[] obj = list.toArray();
			List signlist = new ArrayList();
			if(signstat.equals("true") || signstat.equals("") || signstat.equals("null"))
			{
				for(int i=0;i<obj.length;i++)
				{
					SignManager s = (SignManager)obj[i];
					if(s.getSignstat().equals("true"))
					{
						s.setSignstat("已审批");
						s.setSignmanagerid(this.getAccountID(s, CAccountlist));
						s.setSignid(this.getStampName(s, Stamplist));
						signlist.add(s);
					}
				}
			}
			else if(signstat.equals("false"))
			{
				for(int i=0;i<obj.length;i++)
				{
					SignManager s = (SignManager)obj[i];
					if(s.getSignstat().equals("false"))
					{
						s.setSignstat("已报废");
						s.setSignmanagerid(this.getAccountID(s, CAccountlist));
						s.setSignid(this.getStampName(s, Stamplist));
						signlist.add(s);
					}
				}
				
			}
			bean.rows = signlist;
			bean.total =  result;
			bean.page = pages;
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonArray = JSONObject.fromObject(bean, jsonConfig);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			//logger.info("值：{}", outString);
			response.flushBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}

		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public String getAccountID(SignManager s, List CAccountlist)
	{
		for(int i=0;i<CAccountlist.size();i++)
		{	
			CAccount account = (CAccount)CAccountlist.get(i);
			if(s.getSignmanagerid().equals(account.getAccountID()))
			{
				s.setSignmanagerid(account.getAccountName());
				break;
			}
		}
		return s.getSignmanagerid();
	}
	
	@SuppressWarnings("rawtypes")
	public String getStampName(SignManager s, List Stamplist)
	{
		for(int i=0;i<Stamplist.size();i++)
		{	
			EnterpriseStamp aff = (EnterpriseStamp) Stamplist.get(i);
			if(s.getSignid().equals(aff.getEnterpriseStampID()))
			{
				s.setSignid(aff.getStampName());
				break;
			}
		}
		return s.getSignid();
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

	public String getSignmanagerid() {
		return signmanagerid;
	}

	public void setSignmanagerid(String signmanagerid) {
		this.signmanagerid = signmanagerid;
	}


}
