package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 车辆保险信息
 * 
 * @author Administrator
 * 
 */
public class CarInsurance implements BaseBean, ExcelBean,java.io.Serializable {
	private String insuranceKey;// 数据库主键
	private String insuranceID;// 业务主键
	private String companyID;
	private String organizationID;
	private String carID;// 外键
	private String insuranceCode;// 保险单号
	private String insuranceComName;// 保险公司名称
	private String insuranceName;// 险种名称
	private String purchaseCode;// 被保险人身份证号码/组织机构代码
	private String useNature;// 使用性质
	private String brandModel;// 厂牌型号
	private String passengerNum;// 核定载客
	private Date startTime;// 起始时间
	private Date endTime;// 终止时间
	private String insuranceAmount;// 保险金额
	private String insurancePhone;// 保险单位电话
	private String sellInsurer;// 卖保险人
	private String sellInsurerPhone;// 卖保险人电话
	private String remarks; // 备注
	
	private String carType;//车牌类型
	private String  carNum;//车牌号
	private String engineNum;//发动机号
	private String carPeople;//负责人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号","负责人","保险单号", "保险公司名称", "险种名称", "被保险人身份证号码/组织机构代码",
				"使用性质", "厂牌型号", "核定载客", "起始时间", "终止时间", "保险金额", "保险单位电话",
				"卖保险人", "卖保险人电话", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { insuranceCode, insuranceComName, insuranceName,
				purchaseCode, useNature, brandModel, passengerNum,
				String.format("%1$tF", startTime),
				String.format("%1$tF", endTime), insuranceAmount,
				insurancePhone, sellInsurer, sellInsurerPhone, remarks };
		return properties;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInsuranceKey() {
		return insuranceKey;
	}

	public void setInsuranceKey(String insuranceKey) {
		this.insuranceKey = insuranceKey;
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
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

	public String getInsuranceCode() {
		return insuranceCode;
	}

	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	public String getInsuranceComName() {
		return insuranceComName;
	}

	public void setInsuranceComName(String insuranceComName) {
		this.insuranceComName = insuranceComName;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public String getUseNature() {
		return useNature;
	}

	public void setUseNature(String useNature) {
		this.useNature = useNature;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getPassengerNum() {
		return passengerNum;
	}

	public void setPassengerNum(String passengerNum) {
		this.passengerNum = passengerNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getInsurancePhone() {
		return insurancePhone;
	}

	public void setInsurancePhone(String insurancePhone) {
		this.insurancePhone = insurancePhone;
	}

	public String getSellInsurer() {
		return sellInsurer;
	}

	public void setSellInsurer(String sellInsurer) {
		this.sellInsurer = sellInsurer;
	}

	public String getSellInsurerPhone() {
		return sellInsurerPhone;
	}

	public void setSellInsurerPhone(String sellInsurerPhone) {
		this.sellInsurerPhone = sellInsurerPhone;
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
