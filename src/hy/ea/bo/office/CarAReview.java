package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 车辆年审信息
 * 
 * @author Administrator
 * 
 */
public class CarAReview implements BaseBean, ExcelBean ,java.io.Serializable{
	private String areviewKey;// 数据库主键
	private String areviewID;// 业务主键
	private String companyID;
	private String organizationID;
	private String carID;// 外键
	private Date registrationDate;// 登记日期
	private Date areviewValid;// 年审有效期
	private Date areviewDate;// 年审时间
	private String policyCode;// 二保单
	private String areviewCode;// 年审档案盒
	private String remarks; // 备注
	
	private String carType;//车牌类型
	private String  carNum;//车牌号
	private String engineNum;//发动机号
	private String carPeople;//负责人
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号","负责人","登记日期", "年审有效期", "年审时间", "二保单", "年审档案盒", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { String.format("%1$tF",registrationDate),String.format("%1$tF",areviewValid)
				, String.format("%1$tF",areviewDate), policyCode,
				areviewCode, remarks };
		return properties;
	}

	public String getAreviewKey() {
		return areviewKey;
	}

	public void setAreviewKey(String areviewKey) {
		this.areviewKey = areviewKey;
	}

	public String getAreviewID() {
		return areviewID;
	}

	public void setAreviewID(String areviewID) {
		this.areviewID = areviewID;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getAreviewValid() {
		return areviewValid;
	}

	public void setAreviewValid(Date areviewValid) {
		this.areviewValid = areviewValid;
	}

	public Date getAreviewDate() {
		return areviewDate;
	}

	public void setAreviewDate(Date areviewDate) {
		this.areviewDate = areviewDate;
	}

	public String getPolicyCode() {
		return policyCode;
	}

	public void setPolicyCode(String policyCode) {
		this.policyCode = policyCode;
	}

	public String getAreviewCode() {
		return areviewCode;
	}

	public void setAreviewCode(String areviewCode) {
		this.areviewCode = areviewCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
