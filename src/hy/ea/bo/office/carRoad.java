package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class carRoad implements BaseBean ,ExcelBean,java.io.Serializable{
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号","负责人","业户名称", "地址", "经营许可证号",
				"吨（座）位","车辆长宽高", "经营范围", "发证日期" };
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles ={baseName,roadAddress,carNum,permitNum,tonnage,carType,carLwh,businessRange,cardDate.toString()};
		return titles;
	}
	
	private String roadKey;//主键
	private String roadID;//业务主键
	private String carID;//车辆外键
	private String companyID;
	private String organizationID;
	private String baseName;//业务名称
	private String roadAddress;//地址
	private String carNum;//车辆号码
	private String permitNum;//经营许可证号
	private String tonnage;//吨位
	private String carType;//车辆类型
	private String carLwh;//车辆长宽高
	private String businessRange;//经营范围
	private Date cardDate;//发证日期
	
	private String engineNum;//发动机号
	private String carpeople;//车辆责任人
	public String getRoadKey() {
		return roadKey;
	}
	public void setRoadKey(String roadKey) {
		this.roadKey = roadKey;
	}
	
	public String getRoadID() {
		return roadID;
	}
	public void setRoadID(String roadID) {
		this.roadID = roadID;
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
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public String getRoadAddress() {
		return roadAddress;
	}
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getPermitNum() {
		return permitNum;
	}
	public void setPermitNum(String permitNum) {
		this.permitNum = permitNum;
	}
	public String getTonnage() {
		return tonnage;
	}
	public void setTonnage(String tonnage) {
		this.tonnage = tonnage;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarLwh() {
		return carLwh;
	}
	public void setCarLwh(String carLwh) {
		this.carLwh = carLwh;
	}
	public String getBusinessRange() {
		return businessRange;
	}
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}
	public Date getCardDate() {
		return cardDate;
	}
	public void setCardDate(Date cardDate) {
		this.cardDate = cardDate;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getCarpeople() {
		return carpeople;
	}
	public void setCarpeople(String carpeople) {
		this.carpeople = carpeople;
	}
}
