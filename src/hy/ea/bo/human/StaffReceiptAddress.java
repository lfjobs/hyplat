package hy.ea.bo.human;

import hy.plat.bo.BaseBean;
/**
 * 	个人商城名片收货地址管理
 * @author zj
 *
 */
public class StaffReceiptAddress implements BaseBean{

	private String addressKey;
	private String addressID;
	private String staffID;						//所属人员主键
	private String consignee;				// 收货人
	private String phoneNumber;		// 手机号码
	private String province;					// 省
	private String city;								// 市
	private String area;							// 区
	private String address;						// 详细地址
	private String postcode;					// 邮政编码
	private String defaults;					// 是否为默认收货地址  00：是  01： 不是  默认地址只能存在一个
	public String getAddressKey() {
		return addressKey;
	}
	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getDefaults() {
		return defaults;
	}
	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	
}
