package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;
/**
 * 
 * 微信活动报名表
 * @author 
 */

public class DtActivityEnroll implements BaseBean{
	
	private String EnrollKey;
	private String EnrollId;
	private String staffId;
	private String activityId;
	private String addressId;//活动地址id
	private String organizationID;//报名点（店铺id）
	public String getEnrollKey() {
		return EnrollKey;
	}
	public void setEnrollKey(String enrollKey) {
		EnrollKey = enrollKey;
	}
	public String getEnrollId() {
		return EnrollId;
	}
	public void setEnrollId(String enrollId) {
		EnrollId = enrollId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	
}