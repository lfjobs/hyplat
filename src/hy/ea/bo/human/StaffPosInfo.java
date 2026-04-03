package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

public class StaffPosInfo implements java.io.Serializable,BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2913140048996837049L;
	private String spiKey;
	private String spiID;
	private String staffID;
	private Date  posDate;        //最近一次定位时间
	private String ip;//最近一次登陆IP
	private BigDecimal longitude;//经度//最近一次登陆
	private BigDecimal latitude;//纬度//最近一次登陆
	private String source;//登陆来源 wfjapp;

	public String getSpiKey() {
		return spiKey;
	}

	public void setSpiKey(String spiKey) {
		this.spiKey = spiKey;
	}

	public String getSpiID() {
		return spiID;
	}

	public void setSpiID(String spiID) {
		this.spiID = spiID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Date getPosDate() {
		return posDate;
	}

	public void setPosDate(Date posDate) {
		this.posDate = posDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
