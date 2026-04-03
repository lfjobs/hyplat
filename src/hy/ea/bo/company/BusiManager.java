package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 负责认领公司的业务员
 * @author 
 */
public class BusiManager implements BaseBean{
     private  String  bsmKey;
     private String bsmId;
     private String staffID;
     private Date createDate;
     private String type;   //00 认领公司
     private String  ccompanyID;//公司

	public String getBsmKey() {
		return bsmKey;
	}

	public void setBsmKey(String bsmKey) {
		this.bsmKey = bsmKey;
	}

	public String getBsmId() {
		return bsmId;
	}

	public void setBsmId(String bsmId) {
		this.bsmId = bsmId;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
}
