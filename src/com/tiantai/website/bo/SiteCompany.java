package com.tiantai.website.bo;

import hy.plat.bo.BaseBean;

/**
 * SiteCompany entity. @author MyEclipse Persistence Tools
 */

public class SiteCompany implements BaseBean, java.io.Serializable {

	// Fields

	private String id;
	private String companyName;
	private Integer regionId;
	private String tel;
	private String fax;
	private String intro;
	private Float longitude;
	private String address;
	private Float latitude;

	// Constructors

	/** default constructor */
	public SiteCompany() {
	}

	/** minimal constructor */
	public SiteCompany(String id, String companyName, String address) {
		this.id = id;
		this.companyName = companyName;
		this.address = address;
	}

	/** full constructor */
	public SiteCompany(String id, String companyName, Integer regionId,
			String tel, String fax, String intro, Float longitude,
			String address, Float latitude) {
		this.id = id;
		this.companyName = companyName;
		this.regionId = regionId;
		this.tel = tel;
		this.fax = fax;
		this.intro = intro;
		this.longitude = longitude;
		this.address = address;
		this.latitude = latitude;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

}