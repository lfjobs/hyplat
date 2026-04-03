package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 电子秤信息
 * @author Administrator
 *
 */
public class Scale implements BaseBean {

	private String scKey;
	private String scId;
	private String deviceID;//设备号，唯一标识，当MTScaleKey中设置以秤号为索引时，设备号等于秤号；当设置成以IP为索引时，设备号等于IP地址。
	private int scaleNo;//秤号。
	/*
	     秤类型：
8442：8442类型的秤
BlackEagle：BlackEagle类型的秤
bCom：bCom类型的秤
bPro：bPro类型的秤
Apollo：Apollo秤，同bPro类型
bTwin：bTwin类型的秤
bHigh：bHigh类型的秤
i15：i15类型的秤
RL00：RL00/RL00+类型的秤
bMobile：bMobile类型的秤
bDrive：bDrive类型的秤
Skipper 5000:Skipper 5000类型，同bMobile。
Skipper 7000: Skipper 7000类型，同bDrive
bPlus：bPlus类型的秤
FreshBase：FreshBase类型的秤


	 */
	private String scaleType;//bCom,RL00
	private String connectType;// 通讯类型：Network：局域网 Internet：广域网;目前只能用局域网
	private String address;//IP地址
	private String port;//端口号默认：局域网默认：3001
	private String decimalDigits;//金额精度默认为2
	private String staffID;//责任ID
	private String staffname;//责任人姓名
	private String  companyID;//公司ID
	private Date cdate;//日期
	private String keycount;//键个数


	public String getScKey() {
		return scKey;
	}

	public void setScKey(String scKey) {
		this.scKey = scKey;
	}

	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getScaleNo() {
		return scaleNo;
	}

	public void setScaleNo(int scaleNo) {
		this.scaleNo = scaleNo;
	}

	public String getScaleType() {
		return scaleType;
	}

	public void setScaleType(String scaleType) {
		this.scaleType = scaleType;
	}

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getKeycount() {
		return keycount;
	}

	public void setKeycount(String keycount) {
		this.keycount = keycount;
	}
}
