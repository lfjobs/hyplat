package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;


/*
 * 停车位
 * */
public class ParkingSpaceEmpty implements BaseBean,java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4928553702036088605L;
	private String emptyKey;
	private String emptyId;//主键
	private String siteId;//场地ID
	private String parksId;//车位id
	private String staffId;//创建人
	private String staffName;//创建人姓名
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String address;//详细地址
	private float longitude;//经度
	private float latitude;//维度
	private String liaisonName;//联系人
	private String liaisonPhon;//联系人电话
	private String parkingCode;
	private String startTimeTransfer;
	private String endTimeTransfer;

	public String getEmptyKey() {
		return emptyKey;
	}

	public void setEmptyKey(String emptyKey) {
		this.emptyKey = emptyKey;
	}

	public String getEmptyId() {
		return emptyId;
	}

	public void setEmptyId(String emptyId) {
		this.emptyId = emptyId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getParksId() {
		return parksId;
	}

	public void setParksId(String parksId) {
		this.parksId = parksId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getLiaisonName() {
		return liaisonName;
	}

	public void setLiaisonName(String liaisonName) {
		this.liaisonName = liaisonName;
	}

	public String getLiaisonPhon() {
		return liaisonPhon;
	}

	public void setLiaisonPhon(String liaisonPhon) {
		this.liaisonPhon = liaisonPhon;
	}

	public String getParkingCode() {
		return parkingCode;
	}

	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}

	public String getStartTimeTransfer() {
		return startTimeTransfer;
	}

	public void setStartTimeTransfer(String startTimeTransfer) {
		this.startTimeTransfer = startTimeTransfer;
	}

	public String getEndTimeTransfer() {
		return endTimeTransfer;
	}

	public void setEndTimeTransfer(String endTimeTransfer) {
		this.endTimeTransfer = endTimeTransfer;
	}

	@Override
	public String toString() {
		return "ParkingSpaceEmpty{" +
				"emptyKey='" + emptyKey + '\'' +
				", emptyId='" + emptyId + '\'' +
				", siteId='" + siteId + '\'' +
				", parksId='" + parksId + '\'' +
				", staffId='" + staffId + '\'' +
				", staffName='" + staffName + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", address='" + address + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", liaisonName='" + liaisonName + '\'' +
				", liaisonPhon='" + liaisonPhon + '\'' +
				", parkingCode='" + parkingCode + '\'' +
				'}';
	}
}
