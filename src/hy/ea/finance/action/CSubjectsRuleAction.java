package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CSubjectsRule;
import hy.ea.finance.service.CSubjectsRuleService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CSubjectsRuleAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CSubjectsRuleService subjectsRuleService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private CSubjectsRule subRule;
	private String result;
	private List<BaseBean> beans;
	private String currentLevel;
	/**
	 * 保存编码规则
	 * @return
	 */
	@SuppressWarnings("null")
	public String saveCSubjectsRule(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		String hql = "from CSubjectsRule where companyID = ? ";
		CSubjectsRule subRule2 =(CSubjectsRule) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		if(subRule2==null){
			subRule2.setCompanyID(account.getCompanyID());
			subRule2.setRuleID(serverService.getServerID("subRule"));
		}
		subRule2.setUsableLevel(subRule.getRulesArray().length+"");
		subRule2.setUsedLevel("".equals(subRule.getUsedLevel())?"2":subRule.getUsedLevel());
		subRule2.setRules(subRule.getRules());
		beans=new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null,"保存编码规则", account);
		beans.add(logBook);
		beans.add(subRule2);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return checkCSubjectsRule();
	}
	/**
	 * 查看科目编码规则
	 * @return
	 */
	public String checkCSubjectsRule(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from CSubjectsRule where companyID = ? ";
		Object [] params = {account.getCompanyID()};
		subRule = (CSubjectsRule) baseBeanService.getBeanByHqlAndParams(hql, params);
		return "checkCSubjectsRule";
	}
	
	public String getCSubjectsRule(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from CSubjectsRule where companyID = ? ";
		Object[] params = {account.getCompanyID()};
		CSubjectsRule subRule =  (CSubjectsRule) baseBeanService.getBeanByHqlAndParams(hql, params);
		if(subRule==null)
		{
			subRule=new CSubjectsRule();
			subRule.setCompanyID(account.getCompanyID());
			subRule.setRuleID(serverService.getServerID("subRule"));
			subRule.setUsableLevel("2");
			subRule.setUsedLevel("2");
			subRule.setRules("2,3,,,,,,");
			subjectsRuleService.save(subRule);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subRule", subRule);
		JSONObject json=JSONObject.fromObject(map);
		this.result=json.toString();
		return "success";
	}
	
	/**
	 * 根据级别查询科目
	 * @return
	 */
	public String ajaxCsubejstsList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from CSubjects where companyID=? and currentLevel=? and (subjectsStatus = ? or subjectsStatus = ? )";
		Object [] params={account.getCompanyID(),currentLevel,"00","01"};
		List<BaseBean> csubjectsList=baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		int i=0;
		if(csubjectsList.size()>0){
			i=1;
		}
		map.put("as", i);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	public CSubjectsRule getSubRule() {
		return subRule;
	}
	public void setSubRule(CSubjectsRule subRule) {
		this.subRule = subRule;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	
	
}
