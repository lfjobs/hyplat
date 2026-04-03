package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class PublicTelephone implements BaseBean ,ExcelBean,java.io.Serializable{
	private String telephoneID;
	private String telephoneKey;
    private String organizationID;
	private String companyID;
	private String linkmanName;					//联系人姓名
	private String job;							//职位
	private String handset;						//手机
	private String email;						//电子邮箱
	private String company;						//所在公司
	private String moreInformation;				//详细信息
	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司名称","往来关系","联系人姓名","联系人电话","电子邮箱" , "地址"};
		return titles;
	}

	public String[] properties() {
		String[] properties = {linkmanName,job,handset,email, company,moreInformation};
		return properties;
	}
	public String getTelephoneID() {
		return telephoneID;
	}
	public void setTelephoneID(String telephoneID) {
		this.telephoneID = telephoneID;
	}
	public String getTelephoneKey() {
		return telephoneKey;
	}
	public void setTelephoneKey(String telephoneKey) {
		this.telephoneKey = telephoneKey;
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
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMoreInformation() {
		return moreInformation;
	}
	public void setMoreInformation(String moreInformation) {
		this.moreInformation = moreInformation;
	}
	
	
}