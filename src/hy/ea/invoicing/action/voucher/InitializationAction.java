package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.invoicing.voucher.startTime;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
/**
 * 财务初始化
 * @author lou
 *
 */
public class InitializationAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String result;
	private String stime;
	private String parameter;
	
	/**
	 * 跳转页面
	 * @return
	 */
	public String topage(){
		return "topage";
	}
	
	/**
	 * 财务初始化保存开启时间 
	 * @return 
	 */
	public String getFunction(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		List<BaseBean> beans=new ArrayList<BaseBean>();
		Company company=(Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{account.getCompanyID()});
		startTime startTime=new startTime();
		startTime.setStartID(serverService.getServerID("start"));
		startTime.setCompanyID(account.getCompanyID());
		startTime.setCompanyName(company.getCompanyName());
		startTime.setStartTime(Utilities.getDateFromString(stime, "yyyy-MM"));
		beans.add(startTime);
		parameter = "财务初始化开启时间为"+stime;
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "topage";
	}
	
	/**
	 * 判断是否财务初始化
	 * @return
	 */
	public String ajaxFunction(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql="select count(*) from startTime where companyID=?";
		int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}
}
