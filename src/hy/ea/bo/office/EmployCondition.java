package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class EmployCondition implements BaseBean,ExcelBean,java.io.Serializable{

	private String conditionkey;
	private String conditionID;
	private String carID;
	private String companyID ;
	private String organizationID;
	private String currentsituation;//车辆当前情况
	private Date conectTime;//买卖时间
	private float assignmentMoney;//成交金额
	private String buyName;//买方姓名
	private String buytelphone;//买方电话
	private String certificatenum;//凭证号	
	private String trackNumber;//移动单号
	private String remark;//备注
	
	private String carNum;//车牌号
	private String carType;//车辆；类型
	private String carPeople;//车辆负责人
	private String engineNum;//发动机号
	private String frameNum;//车辆识别号
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public static String[] columnHeadings() {
		String[] titles = { "序号","车牌号","车型","车架号","发动机号","负责人", "车辆当前情况", "买卖时间", "成交金额",
				"买方姓名", "买方电话","凭证号","移动单号","备注" };
		return titles;
	}
	
	public String getConditionkey() {
		return conditionkey;
	}


	public void setConditionkey(String conditionkey) {
		this.conditionkey = conditionkey;
	}


	public String getConditionID() {
		return conditionID;
	}


	public void setConditionID(String conditionID) {
		this.conditionID = conditionID;
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

	public String getCurrentsituation() {
		return currentsituation;
	}


	public void setCurrentsituation(String currentsituation) {
		this.currentsituation = currentsituation;
	}


	public Date getConectTime() {
		return conectTime;
	}


	public void setConectTime(Date conectTime) {
		this.conectTime = conectTime;
	}


	public float getAssignmentMoney() {
		return assignmentMoney;
	}


	public void setAssignmentMoney(float assignmentMoney) {
		this.assignmentMoney = assignmentMoney;
	}


	public String getBuyName() {
		return buyName;
	}


	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}


	public String getBuytelphone() {
		return buytelphone;
	}


	public void setBuytelphone(String buytelphone) {
		this.buytelphone = buytelphone;
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
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


	public String getCarPeople() {
		return carPeople;
	}


	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
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



}
