package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;


/*
 * 设备信息
 * */
public class EquipmentInformation implements BaseBean{
	
	private String equipmentkey;
	private String equipmentId;//主键
	private String siteId;//所属场地id
	private String deviceName;//设备名称
	private String equipmentNumber;//设备编号
	private String unitType;//设备型号
	private String manufacturer;//生产厂商
	private Date equipmentDate;//创建时间
	private Date installationTime;//安装日期

	private String status;//00:已使用(正常),01:未使用,02:已使用(损坏)
	private String channel;//出口 0 入口 1
	public String getEquipmentkey() {
		return equipmentkey;
	}
	public void setEquipmentkey(String equipmentkey) {
		this.equipmentkey = equipmentkey;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public Date getEquipmentDate() {
		return equipmentDate;
	}
	public void setEquipmentDate(Date equipmentDate) {
		this.equipmentDate = equipmentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getInstallationTime() {
		return installationTime;
	}
	public void setInstallationTime(Date installationTime) {
		this.installationTime = installationTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
