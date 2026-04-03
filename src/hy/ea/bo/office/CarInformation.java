package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 车辆基本信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CarInformation implements java.io.Serializable,BaseBean ,ExcelBean{
	private String carID;
	private String carKey;
	private String companyID;
	private String organizationID;
	private String carNum;//车牌号
	private String engineNum;//发动机号   
	private String carType;//车辆类型
	private String carPrice;//购买价格
	private Date  buyDate;//购买日期
	private String conditions;//当前状况
	private String photo;//图片
	private String companyName;//购买单位
	private String driver;//司机
	private String carPlace;//地点
	private String carModels1;//车辆用途外键
	private String carModels2;//车辆种类外键
	private String carModels3;//车辆品牌外键
	private String carFrameNum;//车架号
	private String brandModel;//车辆厂牌型号
	private String engineType;//发动机型号
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
	private String registrationDate;//注册登记日期
	private String cstaffID; //责任人id
	private String goodsID;//物品编号外键
	private String goodsCoding;//车辆编号也是物品编号

	private String loadVolume;//载重体积
	private Date joinDate;//加入平台日期
	private String drivRelation;//关联行驶证编号

	public String getDrivRelation() {
		return drivRelation;
	}

	public void setDrivRelation(String drivRelation) {
		this.drivRelation = drivRelation;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getLoadVolume() {
		return loadVolume;
	}

	public void setLoadVolume(String loadVolume) {
		this.loadVolume = loadVolume;
	}

	public String getCstaffID() {
		return cstaffID;
	}

	public void setCstaffID(String cstaffID) {
		this.cstaffID = cstaffID;
	}

	public String getCresponsible() {
		return cresponsible;
	}

	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}

	private String cresponsible;//责任人
	private String staffID;
	private String staffName;//责任人
	private String state1;//加盟车：00；本校：01
	private String state2;//新增车（正常状态）：01；下线（报废状态）：00
	private String state3;//未使用：00；使用：01 下线（报废状态）：00

	
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司","部门","责任人","车型","车牌号", "发动机号", "车架号", "注册登记日期", 
				"当前状态","加盟状态"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { carNum,engineNum,carType,carPrice,companyName,staffName};
		return properties;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getCarKey() {
		return carKey;
	}

	public void setCarKey(String carKey) {
		this.carKey = carKey;
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

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getCarPlace() {
		return carPlace;
	}

	public void setCarPlace(String carPlace) {
		this.carPlace = carPlace;
	}

	public String getCarModels1() {
		return carModels1;
	}

	public void setCarModels1(String carModels1) {
		this.carModels1 = carModels1;
	}

	public String getCarModels2() {
		return carModels2;
	}

	public void setCarModels2(String carModels2) {
		this.carModels2 = carModels2;
	}

	public String getCarModels3() {
		return carModels3;
	}

	public void setCarModels3(String carModels3) {
		this.carModels3 = carModels3;
	}

	public String getCarFrameNum() {
		return carFrameNum;
	}

	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public String getState3() {
		return state3;
	}

	public void setState3(String state3) {
		this.state3 = state3;
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

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsCoding() {
		return goodsCoding;
	}

	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}
	
}
