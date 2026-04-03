package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

/**
 * Dtofficeimagemanager entity. @author MyEclipse Persistence Tools
 */

public class Dtofficeimagemanager implements BaseBean {

	// Fields

	private String officeimagekey;
	private String officeimagemanagerid;
	private String companyid;
	private String organizationid;
	private String officedescription;
	private String imageshow;
	private String photolist;
	private String mark;
	private String photodescription;
	private String photoname;
	private String photodescrib;

	// Constructors

	/** default constructor */
	public Dtofficeimagemanager() {
	}

	/** full constructor */
	public Dtofficeimagemanager(String officeimagemanagerid, String companyid,
			String organizationid, String officedescription, String imageshow,
			String photolist, String mark, String photodescription,
			String photoname, String photodescrib) {
		this.officeimagemanagerid = officeimagemanagerid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.officedescription = officedescription;
		this.imageshow = imageshow;
		this.photolist = photolist;
		this.mark = mark;
		this.photodescription = photodescription;
		this.photoname = photoname;
		this.photodescrib = photodescrib;
	}

	// Property accessors

	public String getOfficeimagekey() {
		return this.officeimagekey;
	}

	public void setOfficeimagekey(String officeimagekey) {
		this.officeimagekey = officeimagekey;
	}

	public String getOfficeimagemanagerid() {
		return this.officeimagemanagerid;
	}

	public void setOfficeimagemanagerid(String officeimagemanagerid) {
		this.officeimagemanagerid = officeimagemanagerid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOfficedescription() {
		return this.officedescription;
	}

	public void setOfficedescription(String officedescription) {
		this.officedescription = officedescription;
	}

	public String getImageshow() {
		return this.imageshow;
	}

	public void setImageshow(String imageshow) {
		this.imageshow = imageshow;
	}

	public String getPhotolist() {
		return this.photolist;
	}

	public void setPhotolist(String photolist) {
		this.photolist = photolist;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getPhotodescription() {
		return this.photodescription;
	}

	public void setPhotodescription(String photodescription) {
		this.photodescription = photodescription;
	}

	public String getPhotoname() {
		return this.photoname;
	}

	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getPhotodescrib() {
		return this.photodescrib;
	}

	public void setPhotodescrib(String photodescrib) {
		this.photodescrib = photodescrib;
	}

}