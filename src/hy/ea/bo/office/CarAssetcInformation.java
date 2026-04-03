package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/*
 * 车辆资产信息
 * */
public class CarAssetcInformation implements BaseBean, ExcelBean,java.io.Serializable {

	
	private String CarAssetcKey;//主键
	private String assetcID;  //编号ID
	private String carID; //业务外键
	private String companyID;
	private String organizationID;
	private String propertyname; //资产名称
	private String standard;  //规格
	private String count;  // 数量
	private String price;  // 单价
	private String totleCount;  //金额
	private Date connectTime;  //交接时间
	private String connectPeople;  //交接责任人
	private String certificatenum;  //凭证号
	private String trackNumber;  //移动单号
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[] properties = {propertyname,standard,count,price,totleCount,String.format("%1$tF", connectTime),connectPeople,certificatenum,trackNumber}; 
		return properties;
	}
	public static String[] columnHeadings() {
		String[] titles = {"编号","资产名称","规格","数量","单价","金额","交接时间","交接责任人","凭证号","移动单号"};
		return titles;
	}
	
	public String getCarAssetcKey() {
		return CarAssetcKey;
	}

	public void setCarAssetcKey(String carAssetcKey) {
		CarAssetcKey = carAssetcKey;
	}

	public String getAssetcID() {
		return assetcID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public void setAssetcID(String assetcID) {
		this.assetcID = assetcID;
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

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}


	public Date getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}

	public String getConnectPeople() {
		return connectPeople;
	}

	public void setConnectPeople(String connectPeople) {
		this.connectPeople = connectPeople;
	}

	public String getCertificatenum() {
		return certificatenum;
	}

	public void setCertificatenum(String certificatenum) {
		this.certificatenum = certificatenum;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotleCount() {
		return totleCount;
	}
	public void setTotleCount(String totleCount) {
		this.totleCount = totleCount;
	}

	

}
