package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class carBottle implements BaseBean ,ExcelBean,java.io.Serializable{
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车辆号码", "车型","车架号","发动机号","责任人","登证记号", "区域", "单位","厂牌型号",
				 "车主",  "地址","电话","安装单位","安装数量","安装日期","气瓶编号","型号","制造单位","出厂年月","容积","下次检验日期" };
		return titles;
	}
	@Override
	public String[] properties() {																									
		String[] titles={boardingNum,area,carNum,unit,owners,bottleAddress,telephone,installationUnit,installationNum,installationDate.toString(),cylindersNum,typeNum, makeUnit,factoryDate.toString(),volume,inspectionDate.toString()};
		return titles;
	}
	
	private String bottleKey;//主键
	private String bottleID;//业务主键
	private String carID;//车辆外键
	private String companyID;
	private String organizationID;
	private String boardingNum;//登证记号
	private String area;//区域
	private String carNum;//车牌号码
	private String unit;//单位
	private String brandType;//厂牌型号
	private String owners;//车主
	private String frameNum;//车架号
	private String engineNum;//发动机号
	private String bottleAddress;//地址
	private String telephone;//电话
	private String installationUnit;//安装单位
	private String installationNum;//安装数量
	private Date installationDate;//安装日期
	private String cylindersNum;//气瓶编号
	private String typeNum;//型号
	private String makeUnit;//制造单位
	private Date factoryDate;//出厂年月
	private String volume;//容积
	private  Date inspectionDate;//下次体检日期
	
	private String carType;//车型
	private String carPeople;//负责人
	public String getBottleKey() {
		return bottleKey;
	}
	public void setBottleKey(String bottleKey) {
		this.bottleKey = bottleKey;
	}
	public String getBottleID() {
		return bottleID;
	}
	public void setBottleID(String bottleID) {
		this.bottleID = bottleID;
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
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getBoardingNum() {
		return boardingNum;
	}
	public void setBoardingNum(String boardingNum) {
		this.boardingNum = boardingNum;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBrandType() {
		return brandType;
	}
	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}
	public String getOwners() {
		return owners;
	}
	public void setOwners(String owners) {
		this.owners = owners;
	}
	public String getFrameNum() {
		return frameNum;
	}
	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getBottleAddress() {
		return bottleAddress;
	}
	public void setBottleAddress(String bottleAddress) {
		this.bottleAddress = bottleAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getInstallationUnit() {
		return installationUnit;
	}
	public void setInstallationUnit(String installationUnit) {
		this.installationUnit = installationUnit;
	}
	public String getInstallationNum() {
		return installationNum;
	}
	public void setInstallationNum(String installationNum) {
		this.installationNum = installationNum;
	}
	public Date getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}
	public String getCylindersNum() {
		return cylindersNum;
	}
	public void setCylindersNum(String cylindersNum) {
		this.cylindersNum = cylindersNum;
	}
	public String getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public String getMakeUnit() {
		return makeUnit;
	}
	public void setMakeUnit(String makeUnit) {
		this.makeUnit = makeUnit;
	}
	public Date getFactoryDate() {
		return factoryDate;
	}
	public void setFactoryDate(Date factoryDate) {
		this.factoryDate = factoryDate;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarPeople() {
		return carPeople;
	}
	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
	}
}
