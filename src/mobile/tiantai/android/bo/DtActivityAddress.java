package mobile.tiantai.android.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * 
 * 微信活动表
 * @author 
 */

public class DtActivityAddress implements BaseBean{
	
	private String addressKey;
	private String addressId;
	private String companyId;
	private String companyName;
	private String companyAddr;
	private String activityId;
	private String activityType;//活动类型：会议、活动、公告、收费会议
	private String actDate;//活动时间
	private String ensDate;//报名开始时间
	private String eneDate;//报名结束时间
	private String paDate;//一个时间有多个地址（时间和地址对应字段）
	
	public String getAddressKey() {
		return addressKey;
	}
	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActDate() {
		return actDate;
	}
	public void setActDate(String actDate) {
		this.actDate = actDate;
	}
	public String getEnsDate() {
		return ensDate;
	}
	public void setEnsDate(String ensDate) {
		this.ensDate = ensDate;
	}
	public String getEneDate() {
		return eneDate;
	}
	public void setEneDate(String eneDate) {
		this.eneDate = eneDate;
	}
	public String getPaDate() {
		return paDate;
	}
	public void setPaDate(String paDate) {
		this.paDate = paDate;
	}
	
}