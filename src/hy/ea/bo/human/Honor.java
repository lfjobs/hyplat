package hy.ea.bo.human;

import hy.plat.bo.BaseBean;


public class Honor implements BaseBean ,java.io.Serializable{


	private String honorKey;
	private String honorID;
	private String companyID;	
	private String honorCode;
	private String honorName;
	private String honorType;
	private String honorMoney;
	
	
	
	public String getHonorKey() {
		return honorKey;
	}
	public void setHonorKey(String honorKey) {
		this.honorKey = honorKey;
	}
	public String getHonorID() {
		return honorID;
	}
	public void setHonorID(String honorID) {
		this.honorID = honorID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getHonorCode() {
		return honorCode;
	}
	public void setHonorCode(String honorCode) {
		this.honorCode = honorCode;
	}
	public String getHonorName() {
		return honorName;
	}
	public void setHonorName(String honorName) {
		this.honorName = honorName;
	}
	public String getHonorType() {
		return honorType;
	}
	public void setHonorType(String honorType) {
		this.honorType = honorType;
	}
	public String getHonorMoney() {
		return honorMoney;
	}
	public void setHonorMoney(String honorMoney) {
		this.honorMoney = honorMoney;
	}
	
	
}