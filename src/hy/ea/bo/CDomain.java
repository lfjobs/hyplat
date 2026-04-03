package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 域名控制器
 * @author IT
 *
 */
public class CDomain implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String domainKey;
	private String domainID;
	private String domain;
	private String domainContent;
	private String domainTitle;
	/**
	 * 0:正常    9：停用
	 */
	private Integer state;
	
	public String getDomainKey() {
		return domainKey;
	}
	public void setDomainKey(String domainKey) {
		this.domainKey = domainKey;
	}
	public String getDomainID() {
		return domainID;
	}
	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomainContent() {
		return domainContent;
	}
	public void setDomainContent(String domainContent) {
		this.domainContent = domainContent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDomainTitle() {
		return domainTitle;
	}
	public void setDomainTitle(String domainTitle) {
		this.domainTitle = domainTitle;
	}
}
