package hy.ea.bo.office;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class carFoundation implements BaseBean ,ExcelBean{
	
	private String foundationKey;//主键
	private String foundationId;//业务id
	private String carID;//车辆外键
	private String brandModel;//车辆厂牌型号
	private String engineType;//发动机型号
	private String engineNum;//发动机号
	private String frameNum;//车辆识别代码/车架号
	private String containerInSize;//货箱内部尺寸
	private String outerSize;//外廓尺寸
	private String driveType;//驱动形式
	private String power;//排量/功率
	private String fuelType;//燃油类别
	private String colorPaintNum;//外观颜色及漆号
	private String vehicleBrand;//车辆品牌
	private String factoryName;//制造厂名称
	private String tractionTotal;//准牵引总质量
	private String wheelTead;//轮距
	private String ratifyPeople;//核定载客
	private String ratifyQuality;//核定载质量
	private String domestic;//国产进口
	private String bridgePeople;//驾驶室载客
	private String springNum;//钢板弹簧片数
	private String vehicleGet;//车辆获得方式
	private String useProp;//使用性质
	private Date releaseDate;//出厂日期
	private Date operationDate;//运行日期
	private String wheelbase;//轴距
	private String kmFuel;//百公里耗油
	private String shaftNum;//轴数
	private String tireNum;//轮胎数
	private String tireSpecifications;//轮胎规格
	private String serviceQuality;//整备质量
	private String steeringType;//转向形式
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getFoundationKey() {
		return foundationKey;
	}
	public void setFoundationKey(String foundationKey) {
		this.foundationKey = foundationKey;
	}
	public String getFoundationId() {
		return foundationId;
	}
	public void setFoundationId(String foundationId) {
		this.foundationId = foundationId;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getFrameNum() {
		return frameNum;
	}
	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}
	public String getContainerInSize() {
		return containerInSize;
	}
	public void setContainerInSize(String containerInSize) {
		this.containerInSize = containerInSize;
	}
	public String getOuterSize() {
		return outerSize;
	}
	public void setOuterSize(String outerSize) {
		this.outerSize = outerSize;
	}
	public String getDriveType() {
		return driveType;
	}
	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getColorPaintNum() {
		return colorPaintNum;
	}
	public void setColorPaintNum(String colorPaintNum) {
		this.colorPaintNum = colorPaintNum;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getTractionTotal() {
		return tractionTotal;
	}
	public void setTractionTotal(String tractionTotal) {
		this.tractionTotal = tractionTotal;
	}
	public String getWheelTead() {
		return wheelTead;
	}
	public void setWheelTead(String wheelTead) {
		this.wheelTead = wheelTead;
	}
	public String getRatifyPeople() {
		return ratifyPeople;
	}
	public void setRatifyPeople(String ratifyPeople) {
		this.ratifyPeople = ratifyPeople;
	}
	public String getRatifyQuality() {
		return ratifyQuality;
	}
	public void setRatifyQuality(String ratifyQuality) {
		this.ratifyQuality = ratifyQuality;
	}
	public String getDomestic() {
		return domestic;
	}
	public void setDomestic(String domestic) {
		this.domestic = domestic;
	}
	public String getBridgePeople() {
		return bridgePeople;
	}
	public void setBridgePeople(String bridgePeople) {
		this.bridgePeople = bridgePeople;
	}
	public String getSpringNum() {
		return springNum;
	}
	public void setSpringNum(String springNum) {
		this.springNum = springNum;
	}
	public String getVehicleGet() {
		return vehicleGet;
	}
	public void setVehicleGet(String vehicleGet) {
		this.vehicleGet = vehicleGet;
	}
	public String getUseProp() {
		return useProp;
	}
	public void setUseProp(String useProp) {
		this.useProp = useProp;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getWheelbase() {
		return wheelbase;
	}
	public void setWheelbase(String wheelbase) {
		this.wheelbase = wheelbase;
	}
	public String getKmFuel() {
		return kmFuel;
	}
	public void setKmFuel(String kmFuel) {
		this.kmFuel = kmFuel;
	}
	public String getShaftNum() {
		return shaftNum;
	}
	public void setShaftNum(String shaftNum) {
		this.shaftNum = shaftNum;
	}
	public String getTireNum() {
		return tireNum;
	}
	public void setTireNum(String tireNum) {
		this.tireNum = tireNum;
	}
	public String getTireSpecifications() {
		return tireSpecifications;
	}
	public void setTireSpecifications(String tireSpecifications) {
		this.tireSpecifications = tireSpecifications;
	}
	public String getServiceQuality() {
		return serviceQuality;
	}
	public void setServiceQuality(String serviceQuality) {
		this.serviceQuality = serviceQuality;
	}
	public String getSteeringType() {
		return steeringType;
	}
	public void setSteeringType(String steeringType) {
		this.steeringType = steeringType;
	}
}
