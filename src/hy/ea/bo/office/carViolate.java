package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 违章表
 * @author ct
 *
 */
public class carViolate implements BaseBean ,ExcelBean,java.io.Serializable{
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号", "责任人",  "违章日期",
				"违章原因", "处理情况", "备注" };
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles ={carType,violatePeople,carNum,violateDate.toString(),violateReason,treatment,remark};
		return titles;
	}

	private String carviolateKey;//业务主键
	private String carviolateID;//主键
	private String companyID;
	private String organizationID;
	private String carID;//汽车外键
	private String carType;//车牌类型
	private String  carNum;//车牌号
	private String violatePeople;//负责人
	private Date violateDate;//违章日期
	private String violateReason;//违章原因
	private String treatment;//处理情况
	private String remark;//备注
	
	private String engineNum;//发动机号
	public String getCarviolateKey() {
		return carviolateKey;
	}
	public void setCarviolateKey(String carviolateKey) {
		this.carviolateKey = carviolateKey;
	}
	public String getCarviolateID() {
		return carviolateID;
	}
	public void setCarviolateID(String carviolateID) {
		this.carviolateID = carviolateID;
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
	public Date getViolateDate() {
		return violateDate;
	}
	public void setViolateDate(Date violateDate) {
		this.violateDate = violateDate;
	}
	public String getViolateReason() {
		return violateReason;
	}
	public void setViolateReason(String violateReason) {
		this.violateReason = violateReason;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getViolatePeople() {
		return violatePeople;
	}
	public void setViolatePeople(String violatePeople) {
		this.violatePeople = violatePeople;
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
}
