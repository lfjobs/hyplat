package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

public class carQuasi implements BaseBean ,ExcelBean,java.io.Serializable{
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","车架号", "排号",
				"座号", "座位状态", "准载人数", "实载人数", "超载人数","乘坐方式","启程时间","抵达时间"
				, "车辆所属单位", "单位责任人", "单位联系方式", "驾驶员"
				, "手机号", "接送起地址", "接送止地址", "接送起时间"
				, "接送止时间", "单位", "乘坐人姓名", "手机号"
				, "承办单位", "承办单位责任人", "承办单位联系方式", "备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles ={carNum,carType,carFrameNum,numeral,seatNum,seatState,quasiLoad,realLoad,
				overLoad,byWay,departureTime.toString(),arriveTime.toString(),carUtil,utilPeople,utilTel,driver,driverTel,beganPlace,endPlace,beganTime.toString(),endTime.toString(),util,passenger,
				passengerTel,undertakeUtil,undertakePeo,undertakeTel,remark};
		return titles;
	}
	
	public static String[] columnHeadings2() {
		String[] titles = { "序号", "车牌号","车型","车架号", "排号",
				"座号", "座位状态", "准载人数", "实载人数", "超载人数",
				 "车辆所属单位", "单位责任人", "单位联系方式", "驾驶员"};
		return titles;
	}
	
	private String quasiKey;//主键
	private String quasiID;//业务主键
	private String carID;//车辆外键
	private String companyID;
	private String organizationID;
	private String carNum;
	private String carType;
	private String carFrameNum;
	private String numeral;//排号
	private String seatNum;//列号
	private String zuonum;//座号
	private String seatState;//座位状态
	private String quasiLoad;//准载
	private String realLoad;//实载
	private String overLoad;//超载
	private String carUtil;//车辆所属单位
	private String utilPeople;//单位责任人
	private String utilTel;//单位联系方式
	private String driver;//驾驶员
	private String driverTel;//手机号
	private String beganPlace;//接送起地点
	private String endPlace;//接送止地点
	private Date beganTime;//接送起时间
	private Date endTime;//接送止时间
	private String util;//单位
	private String  passenger;//乘坐人姓名
	private String  passengerTel;//手机号
	private String undertakeUtil;//承办单位
	private String undertakePeo;//承办单位责任人
	private String undertakeTel;//承办单位联系方式
	private String remark;//备注
	
	private String byWay;//乘坐方式
	private Date departureTime;//启程时间
	private Date arriveTime;//抵达时间
	
	@SuppressWarnings("unused")
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getQuasiKey() {
		return quasiKey;
	}
	public void setQuasiKey(String quasiKey) {
		this.quasiKey = quasiKey;
	}
	public String getQuasiID() {
		return quasiID;
	}
	public void setQuasiID(String quasiID) {
		this.quasiID = quasiID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
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
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarFrameNum() {
		return carFrameNum;
	}
	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}
	public String getNumeral() {
		return numeral;
	}
	public void setNumeral(String numeral) {
		this.numeral = numeral;
	}
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public String getSeatState() {
		return seatState;
	}
	public void setSeatState(String seatState) {
		this.seatState = seatState;
	}
	public String getQuasiLoad() {
		return quasiLoad;
	}
	public void setQuasiLoad(String quasiLoad) {
		this.quasiLoad = quasiLoad;
	}
	public String getRealLoad() {
		return realLoad;
	}
	public void setRealLoad(String realLoad) {
		this.realLoad = realLoad;
	}
	public String getOverLoad() {
		return overLoad;
	}
	public void setOverLoad(String overLoad) {
		this.overLoad = overLoad;
	}
	public String getCarUtil() {
		return carUtil;
	}
	public void setCarUtil(String carUtil) {
		this.carUtil = carUtil;
	}
	public String getUtilPeople() {
		return utilPeople;
	}
	public void setUtilPeople(String utilPeople) {
		this.utilPeople = utilPeople;
	}
	public String getUtilTel() {
		return utilTel;
	}
	public void setUtilTel(String utilTel) {
		this.utilTel = utilTel;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getBeganPlace() {
		return beganPlace;
	}
	public void setBeganPlace(String beganPlace) {
		this.beganPlace = beganPlace;
	}
	public String getEndPlace() {
		return endPlace;
	}
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}
	public Date getBeganTime() {
		return beganTime;
	}
	public void setBeganTime(Date beganTime) {
		this.beganTime = beganTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getUtil() {
		return util;
	}
	public void setUtil(String util) {
		this.util = util;
	}
	public String getPassenger() {
		return passenger;
	}
	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}
	public String getPassengerTel() {
		return passengerTel;
	}
	public void setPassengerTel(String passengerTel) {
		this.passengerTel = passengerTel;
	}
	public String getUndertakeUtil() {
		return undertakeUtil;
	}
	public void setUndertakeUtil(String undertakeUtil) {
		this.undertakeUtil = undertakeUtil;
	}
	public String getUndertakePeo() {
		return undertakePeo;
	}
	public void setUndertakePeo(String undertakePeo) {
		this.undertakePeo = undertakePeo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUndertakeTel() {
		return undertakeTel;
	}
	public void setUndertakeTel(String undertakeTel) {
		this.undertakeTel = undertakeTel;
	}
	public String getByWay() {
		return byWay;
	}
	public void setByWay(String byWay) {
		this.byWay = byWay;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getZuonum() {
		return zuonum;
	}
	public void setZuonum(String zuonum) {
		this.zuonum = zuonum;
	}
}
