package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class carMotorcar implements BaseBean ,ExcelBean,java.io.Serializable{
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车辆类型","发动机号码","负责人","号牌号码", "所有人",  "住址",
				"使用性质", "车辆识别代码", "品牌型号",  "注册日期","发证日期","机动车行驶证有效期" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] titles = { flapperType,allPeople,carType,address,useProp,recognitionCode,brandModel,engineNum,registrationDate.toString(),cardDate.toString(),validityDate.toString() };
		return titles;
	}
	
	private String motorcarKey;//主键
	private String motorcarID;//业务主键
	private String companyID;
	private String organizationID;
	private String carID;//车辆外键
	private String flapperType;//号牌号码
	private String allPeople;//所有人
	private String carType;//车辆类型
	private String address;//住址
	private String useProp;//使用性质
	private String recognitionCode;//车辆识别代码
	private String brandModel;//品牌型号
	private String engineNum;//发动机号码
	private Date registrationDate;//注册日期
	private Date cardDate;//发证日期
	private Date validityDate;//机动车行驶证有效期
	private String carNum;//车牌号
	private String carpeople;//责任人
	public String getMotorcarKey() {
		return motorcarKey;
	}
	public void setMotorcarKey(String motorcarKey) {
		this.motorcarKey = motorcarKey;
	}
	public String getMotorcarID() {
		return motorcarID;
	}
	public void setMotorcarID(String motorcarID) {
		this.motorcarID = motorcarID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getFlapperType() {
		return flapperType;
	}
	public void setFlapperType(String flapperType) {
		this.flapperType = flapperType;
	}
	public String getAllPeople() {
		return allPeople;
	}
	public void setAllPeople(String allPeople) {
		this.allPeople = allPeople;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUseProp() {
		return useProp;
	}
	public void setUseProp(String useProp) {
		this.useProp = useProp;
	}
	public String getRecognitionCode() {
		return recognitionCode;
	}
	public void setRecognitionCode(String recognitionCode) {
		this.recognitionCode = recognitionCode;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getCardDate() {
		return cardDate;
	}
	public void setCardDate(Date cardDate) {
		this.cardDate = cardDate;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
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

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarpeople() {
		return carpeople;
	}

	public void setCarpeople(String carpeople) {
		this.carpeople = carpeople;
	}
}
