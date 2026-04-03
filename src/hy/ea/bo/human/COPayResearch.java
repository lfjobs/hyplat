package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 人事-薪水调查
 * @author Administrator
 *
 */

public class COPayResearch implements BaseBean ,ExcelBean,java.io.Serializable{
	private String payResearchKey;
	private String payResearchID; 
	private String organizationID;
	private String companyID;
	private String industryCategory;//行业类别
	private String post;//岗位
	private String salaryRange ;//工薪范围
	private String workingTenure ;//工作年限
	private String jobRequirement;// 工作要求
	private String remarks;//备注
	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","行业类别","岗位","工薪范围","工作年限","工作要求","备注"};
		return titles;
	}
	/**
	 * 所需导入的字段
	 * @version zg 2011-5-9
	 * @return
	 */
	public static String[] excelImportPro() {
		String[] properties = {"industryCategory", "post","salaryRange","workingTenure" ,"jobRequirement","remarks"};
		return properties;
	}
	public String[] properties() {
		String[] properties = {industryCategory, post,salaryRange,workingTenure ,jobRequirement,remarks};
		return properties;
	}
	
	public String getPayResearchKey() {
		return payResearchKey;
	}
	public void setPayResearchKey(String payResearchKey) {
		this.payResearchKey = payResearchKey;
	}
	public String getPayResearchID() {
		return payResearchID;
	}
	public void setPayResearchID(String payResearchID) {
		this.payResearchID = payResearchID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getIndustryCategory() {
		return industryCategory;
	}
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getSalaryRange() {
		return salaryRange;
	}
	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}
	public String getWorkingTenure() {
		return workingTenure;
	}
	public void setWorkingTenure(String workingTenure) {
		this.workingTenure = workingTenure;
	}
	public String getJobRequirement() {
		return jobRequirement;
	}
	public void setJobRequirement(String jobRequirement) {
		this.jobRequirement = jobRequirement;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
