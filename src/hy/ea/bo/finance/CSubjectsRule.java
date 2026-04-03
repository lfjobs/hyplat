package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;
/**
 * 科目编码规则
 * @author lou
 *
 */
public class CSubjectsRule implements BaseBean {
	private String companyID;
	/**
	 * 主键
	 */
	private String ruleKey;
	/**
	 * 业务外键
	 */
	private String ruleID;
	/**
	 * 编码规则
	 */
	private String rules;
	/**
	 * 可用级次,此级次以内可设置新会计科目
	 */
	private String usableLevel;
	/**
	 * 已使用级次,此级次以上编码规则不能修改
	 */
	private String usedLevel;
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getRuleKey() {
		return ruleKey;
	}
	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}
	public String getRuleID() {
		return ruleID;
	}
	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	public String getUsableLevel() {
		return usableLevel;
	}
	public void setUsableLevel(String usableLevel) {
		this.usableLevel = usableLevel;
	}
	public String getUsedLevel() {
		return usedLevel;
	}
	public void setUsedLevel(String usedLevel) {
		this.usedLevel = usedLevel;
	}
	public String[] getRulesArray(){
		return rules.replace(" ", "").split(",");
	}
}
