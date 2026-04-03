package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/*
 * 车牌号维护
 * */
public class CarNumber implements BaseBean, ExcelBean,java.io.Serializable{
	private String cnKey;
	private String cnID;
	private String companyID;
	private String organizationID;
	private String staffID;
	private String staffName;
	private Date brandDate; //上牌时间
	private String carNum;  //车牌号
	private String carID;  //车辆ID
	private String Unit; //上牌车管单位
	private String address;		//地址
	private Date fzDate;		//上牌废止时间
	private String fzOrgizationID;  //上牌废止部门
	private String fzStaffID;  //上牌废止责任人
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","部门名称","责任人","上牌时间","车牌号","上牌车管单位", "车管单位地址", "车牌废止时间","车牌废止部门","车牌废止责任人"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {organizationID,staffName,brandDate.toString(),carNum,Unit,address,fzDate.toString(),fzOrgizationID,fzStaffID};
		return properties;
	}
	public String getCnKey() {
		return cnKey;
	}
	public void setCnKey(String cnKey) {
		this.cnKey = cnKey;
	}
	public String getCnID() {
		return cnID;
	}
	public void setCnID(String cnID) {
		this.cnID = cnID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBrandDate() {
		return brandDate;
	}
	public void setBrandDate(Date brandDate) {
		this.brandDate = brandDate;
	}
	public Date getFzDate() {
		return fzDate;
	}
	public void setFzDate(Date fzDate) {
		this.fzDate = fzDate;
	}
	public String getFzOrgizationID() {
		return fzOrgizationID;
	}
	public void setFzOrgizationID(String fzOrgizationID) {
		this.fzOrgizationID = fzOrgizationID;
	}
	public String getFzStaffID() {
		return fzStaffID;
	}
	public void setFzStaffID(String fzStaffID) {
		this.fzStaffID = fzStaffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	
}
