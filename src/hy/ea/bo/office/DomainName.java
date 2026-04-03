package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;


/**
 * 域名管理
 * @author Administrator
 *
 */
public class DomainName implements BaseBean,ExcelBean ,java.io.Serializable {
	private String domainID;
	private String domainKey; 
	
	private String companyID;
	private String organizationID;
	
	private String domainCode;//编号
	private String companyName;//单位名称
	private String companyUrl;//单位网址
	private String domain;//域名
	
	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","编号","单位名称","单位网址","域名"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {domainCode,companyName,companyUrl,domain };
		return properties;
	}
	
	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getDomainID() {
		return domainID;
	}

	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}

	public String getDomainKey() {
		return domainKey;
	}

	public void setDomainKey(String domainKey) {
		this.domainKey = domainKey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
			

	
}
