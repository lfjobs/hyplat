/**
 * Responsibilities
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * ZC
 * @author  MarketSurvey  市场调查办
 */
public class MarketSurvey implements BaseBean,ExcelBean,java.io.Serializable{
  private String marketSurveyKey;
  private String marketSurveyID;
  
  private String companyID;
  private String organizationID;
  
  private String surveyName;//调查名称
  private String surveyForms;//调查形式 
  private String surveyContent ;//调查内容
  private String surveyTarget;//调查目标
  private String surveyRegional;//调查区域
  private String surveyResult;//调查结果
  private String surveyEvaluation;//调查评价
  private String surveyBudget;//调查预算
  private String note;//备注
  
  public static String[] columnHeadings() {
		String[] titles = { "序号", "调查名称", "调查形式 ", "调查内容", "调查目标", "调查区域", "调查结果",
				"调查评价", "调查预算" ,"备注"};
		return titles;
	}
	public String[] properties() {
		String[] properties = { surveyName, surveyForms, surveyContent,surveyTarget, surveyRegional,
				surveyResult, surveyEvaluation,surveyBudget, note };
		return properties ;
	}
	public String getMarketSurveyKey() {
		return marketSurveyKey;
	}
	public void setMarketSurveyKey(String marketSurveyKey) {
		this.marketSurveyKey = marketSurveyKey;
	}
	public String getMarketSurveyID() {
		return marketSurveyID;
	}
	public void setMarketSurveyID(String marketSurveyID) {
		this.marketSurveyID = marketSurveyID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	public String getSurveyForms() {
		return surveyForms;
	}
	public void setSurveyForms(String surveyForms) {
		this.surveyForms = surveyForms;
	}
	public String getSurveyContent() {
		return surveyContent;
	}
	public void setSurveyContent(String surveyContent) {
		this.surveyContent = surveyContent;
	}
	public String getSurveyTarget() {
		return surveyTarget;
	}
	public void setSurveyTarget(String surveyTarget) {
		this.surveyTarget = surveyTarget;
	}
	public String getSurveyRegional() {
		return surveyRegional;
	}
	public void setSurveyRegional(String surveyRegional) {
		this.surveyRegional = surveyRegional;
	}
	public String getSurveyResult() {
		return surveyResult;
	}
	public void setSurveyResult(String surveyResult) {
		this.surveyResult = surveyResult;
	}
	public String getSurveyEvaluation() {
		return surveyEvaluation;
	}
	public void setSurveyEvaluation(String surveyEvaluation) {
		this.surveyEvaluation = surveyEvaluation;
	}
	public String getSurveyBudget() {
		return surveyBudget;
	}
	public void setSurveyBudget(String surveyBudget) {
		this.surveyBudget = surveyBudget;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
