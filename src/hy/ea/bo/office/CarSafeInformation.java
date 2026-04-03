package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;


//车辆安全信息
public class CarSafeInformation implements BaseBean,ExcelBean,java.io.Serializable{
	private String carSafeKey; //车辆安全表的主键
	private String carSafeID;  //车辆安全的编号
	private String companyID;
	private String organizationID;
	private String carID;   //业务外键
	
	private  Date accidentTime;//事故时间
	private String accidentpass;//事故经过  
	private double directlossmoney;//直接损失金额
	private double indirectlossmoney;//间接损失金额
	private String  disposeidea;//处理意见
	private String accidentdutypeople;//事故责任人
	private String disposepeople;//处理责任人
	private Date disposeTime;//处理时间
	private String accidentbox;//事故档案盒
	private String certificatenum;//凭证号
	private String accidentPicture;//事故图片
	
	private String carType;//车牌类型
	private String  carNum;//车牌号
	private String engineNum;//发动机号
	private String carPeople;//负责人
	public String getCarSafeKey() {
		return carSafeKey;
	}
	public void setCarSafeKey(String carSafeKey) {
		this.carSafeKey = carSafeKey;
	}
	public String getCarSafeID() {
		return carSafeID;
	}
	public void setCarSafeID(String carSafeID) {
		this.carSafeID = carSafeID;
	}
	public String getAccidentrecordpeopleID() {
		return accidentrecordpeopleID;
	}
	public void setAccidentrecordpeopleID(String accidentrecordpeopleID) {
		this.accidentrecordpeopleID = accidentrecordpeopleID;
	}

	private String accidentrecordpeople;//事故档案责任人
	private String accidentrecordpeopleID;//事故档案责任人ID
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号","负责人","事故时间",
				"事故经过","事故责任人","直接损失金额","间接损失金额","处理时间","处理责任人","处理意见","事故档案盒","凭证号"};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[] properties = {String.format("%1$tF", accidentTime),"accidentpass","directlossmoney","indirectlossmoney","disposeidea","accidentdutypeople","disposepeople",String.format("%1$tF", disposeTime)}; 
		return properties;
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

	public Date getAccidentTime() {
		return accidentTime;
	}

	public void setAccidentTime(Date accidentTime) {
		this.accidentTime = accidentTime;
	}

	public String getAccidentpass() {
		return accidentpass;
	}

	public void setAccidentpass(String accidentpass) {
		this.accidentpass = accidentpass;
	}

	public double getDirectlossmoney() {
		return directlossmoney;
	}

	public void setDirectlossmoney(double directlossmoney) {
		this.directlossmoney = directlossmoney;
	}
	public double getIndirectlossmoney() {
		return indirectlossmoney;
	}
	public void setIndirectlossmoney(double indirectlossmoney) {
		this.indirectlossmoney = indirectlossmoney;
	}
	public String getDisposeidea() {
		return disposeidea;
	}
	public void setDisposeidea(String disposeidea) {
		this.disposeidea = disposeidea;
	}
	public String getAccidentdutypeople() {
		return accidentdutypeople;
	}
	public void setAccidentdutypeople(String accidentdutypeople) {
		this.accidentdutypeople = accidentdutypeople;
	}
	public String getDisposepeople() {
		return disposepeople;
	}
	public void setDisposepeople(String disposepeople) {
		this.disposepeople = disposepeople;
	}
	public Date getDisposeTime() {
		return disposeTime;
	}
	public void setDisposeTime(Date disposeTime) {
		this.disposeTime = disposeTime;
	}
	public String getAccidentrecordpeople() {
		return accidentrecordpeople;
	}
	public void setAccidentrecordpeople(String accidentrecordpeople) {
		this.accidentrecordpeople = accidentrecordpeople;
	}
	public String getAccidentbox() {
		return accidentbox;
	}
	public void setAccidentbox(String accidentbox) {
		this.accidentbox = accidentbox;
	}
	public String getCertificatenum() {
		return certificatenum;
	}
	public void setCertificatenum(String certificatenum) {
		this.certificatenum = certificatenum;
	}
	public String getAccidentPicture() {
		return accidentPicture;
	}
	public void setAccidentPicture(String accidentPicture) {
		this.accidentPicture = accidentPicture;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
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
	public String getCarPeople() {
		return carPeople;
	}
	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
	}
	
}
