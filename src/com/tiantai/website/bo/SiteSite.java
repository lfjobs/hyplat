package com.tiantai.website.bo;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;

/**
 * SiteSite entity. @author MyEclipse Persistence Tools
 */

public class SiteSite implements BaseBean, java.io.Serializable {

	// Fields

	private String id;
	private String domain;
	private String name;
	private String shortName;
	private String metaTitle;
	private Boolean closed;
	private String metaKey;
	private String metaDec;
	private Boolean vip;
	private String weibo;
	private String logo;
	private Timestamp regTime;
	private Boolean recommon;
	private Boolean openShopping;
	private String companyId;
	// Constructors

	/** default constructor */
	public SiteSite() {
	}

	/** minimal constructor */
	public SiteSite(String id, Timestamp regTime) {
		this.id = id;
		this.regTime = regTime;
	}

	/** full constructor */
	public SiteSite(String id, String domain, String name, String shortName,
			String metaTitle, Boolean closed, String metaKey, String metaDec,
			Boolean vip, String weibo, String logo, Timestamp regTime,
			Boolean recommon) {
		this.id = id;
		this.domain = domain;
		this.name = name;
		this.shortName = shortName;
		this.metaTitle = metaTitle;
		this.closed = closed;
		this.metaKey = metaKey;
		this.metaDec = metaDec;
		this.vip = vip;
		this.weibo = weibo;
		this.logo = logo;
		this.regTime = regTime;
		this.recommon = recommon;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMetaTitle() {
		return this.metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public Boolean getClosed() {
		return this.closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getMetaKey() {
		return this.metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public String getMetaDec() {
		return this.metaDec;
	}

	public void setMetaDec(String metaDec) {
		this.metaDec = metaDec;
	}

	public Boolean getVip() {
		return this.vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public String getWeibo() {
		return this.weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Timestamp getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	public Boolean getRecommon() {
		return this.recommon;
	}

	public void setRecommon(Boolean recommon) {
		this.recommon = recommon;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Boolean getOpenShopping() {
		return openShopping;
	}

	public void setOpenShopping(Boolean openShopping) {
		this.openShopping = openShopping;
	}

}