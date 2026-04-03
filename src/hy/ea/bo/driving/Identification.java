package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

/**
 * Identification.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Identification  implements BaseBean{

	// Fields

	private String identificationkey;
	private String identificationid;
	private String companyid;
	private String staffID;
	private String type; // 00 教师管理

	// Constructors

	public String getIdentificationkey() {
		return identificationkey;
	}



	public void setIdentificationkey(String identificationkey) {
		this.identificationkey = identificationkey;
	}



	public String getIdentificationid() {
		return identificationid;
	}



	public void setIdentificationid(String identificationid) {
		this.identificationid = identificationid;
	}



	public String getStaffID() {
		return staffID;
	}



	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}



	/** default constructor */
	public Identification() {
	}



	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

}