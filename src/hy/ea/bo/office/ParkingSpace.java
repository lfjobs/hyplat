package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;


/*
 * 停车位
 * */
public class ParkingSpace implements BaseBean,java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4928553702036088605L;
	private String parkskey;
	private String parksId;//主键
	private String siteId;//场地ID
	private String companyId;//所属公司主键
	private String parkingCode;//停车位编号 自动生成
	private String parkingName;//停车位人工命名
	private Date createDate;//创建时间
	private String staffId;//创建人
	private String staffName;//创建人姓名
	private String status;//00 没有占用  01 已占用  02:弃用状态
	private String carType;//试用车型
	private String parkingLength;
	private String parkingWidth;
	public String getParkskey() {
		return parkskey;
	}
	public void setParkskey(String parkskey) {
		this.parkskey = parkskey;
	}
	public String getParksId() {
		return parksId;
	}
	public void setParksId(String parksId) {
		this.parksId = parksId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getParkingCode() {
		return parkingCode;
	}
	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getParkingLength() {
		return parkingLength;
	}
	public void setParkingLength(String parkingLength) {
		this.parkingLength = parkingLength;
	}
	public String getParkingWidth() {
		return parkingWidth;
	}
	public void setParkingWidth(String parkingWidth) {
		this.parkingWidth = parkingWidth;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
    
    
}
