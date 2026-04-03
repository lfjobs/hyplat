package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

/**
 * 项目中按行业信息
 * @author mz
 *
 */
public class ProjectByIndustry implements BaseBean,java.io.Serializable{
	
	private String reKey;			        //主键
	private String reID;         			//业务主键
	
	/****行业信息*****/
	private String industryID;//行业ID codeID
	private String industryName;//行业Name codeValue
	private String salesman;//业务员；
	private String salesmanID;//业务员；
	private String salesmanCode;//业务员；
	/****行业信息*****/
	
	
	/****招生信息*****/
	private String recruiterID; //对应Staff表中的staffID;
	private String recruiterName;//姓名
	private String recruiterCode;//编号
	private String identityCard;//身份证
	private String email;//招生员邮箱
	private String phone;//招生员联系电话
	private String address;//招生员地址
	private String applyPlaceID;//分校/报名点ID
	private String applyPlaceName;//分校/报名点名称
	/****招生信息*****/
	
	private String projectID;//项目ID
	
	
	
	
	public String getReKey() {
		return reKey;
	}
	public void setReKey(String reKey) {
		this.reKey = reKey;
	}
	public String getReID() {
		return reID;
	}
	public void setReID(String reID) {
		this.reID = reID;
	}
	public String getRecruiterID() {
		return recruiterID;
	}
	public void setRecruiterID(String recruiterID) {
		this.recruiterID = recruiterID;
	}
	public String getRecruiterName() {
		return recruiterName;
	}
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	public String getRecruiterCode() {
		return recruiterCode;
	}
	public void setRecruiterCode(String recruiterCode) {
		this.recruiterCode = recruiterCode;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getApplyPlaceID() {
		return applyPlaceID;
	}
	public void setApplyPlaceID(String applyPlaceID) {
		this.applyPlaceID = applyPlaceID;
	}
	public String getApplyPlaceName() {
		return applyPlaceName;
	}
	public void setApplyPlaceName(String applyPlaceName) {
		this.applyPlaceName = applyPlaceName;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getIndustryID() {
		return industryID;
	}
	public void setIndustryID(String industryID) {
		this.industryID = industryID;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getSalesmanID() {
		return salesmanID;
	}
	public void setSalesmanID(String salesmanID) {
		this.salesmanID = salesmanID;
	}
	public String getSalesmanCode() {
		return salesmanCode;
	}
	public void setSalesmanCode(String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}
	
	
   
	
	
}
