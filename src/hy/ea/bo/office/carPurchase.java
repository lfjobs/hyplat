package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class carPurchase implements BaseBean ,ExcelBean,java.io.Serializable{
	public static String[] columnHeadings() {
		String[] titles = { "序号","车牌号","车型","车架号","发动机号","负责人", "编号", "纳税人", "厂牌型号", "征收机关名称", "经办人签章" };
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles ={numbers,taxpayer,brandModel,engineNum,frameNum,collectionNum,agentSignature};
		return titles;
	}
	
	private String purchaseKey;//主键
	private String purchaseID;//业务Id
	private String companyID;
	private String organizationID;
	private String carID;//车辆外键
	private String numbers;//编号
	private String taxpayer;//纳税人
	private String brandModel;//厂牌型号
	private String engineNum;//发动机号
	private String frameNum;//车辆识别号
	private String collectionNum;//征收机关名称
	private String agentSignature;//经办人签章
	
	private String carNum;//车牌号
	private String carType;//车辆；类型
	private String carPeople;//车辆负责人
	public String getPurchaseKey() {
		return purchaseKey;
	}
	public void setPurchaseKey(String purchaseKey) {
		this.purchaseKey = purchaseKey;
	}
	public String getPurchaseID() {
		return purchaseID;
	}
	public void setPurchaseID(String purchaseID) {
		this.purchaseID = purchaseID;
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
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getTaxpayer() {
		return taxpayer;
	}
	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
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
	public String getFrameNum() {
		return frameNum;
	}
	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}
	public String getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(String collectionNum) {
		this.collectionNum = collectionNum;
	}
	public String getAgentSignature() {
		return agentSignature;
	}
	public void setAgentSignature(String agentSignature) {
		this.agentSignature = agentSignature;
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
}
