package hy.ea.bo.production.recruit;



import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class RelateStaff implements BaseBean,java.io.Serializable {

	private String rskey;
	private String rsId;
	private String staffID;
	private String companyID;
	private Date  createDate;


	public String getRskey() {
		return rskey;
	}

	public void setRskey(String rskey) {
		this.rskey = rskey;
	}

	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}