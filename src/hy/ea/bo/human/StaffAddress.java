/**
 * Address
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
/**
 * 地址管理
 * @author YJG
 */
public class StaffAddress implements BaseBean,ExcelBean,Serializable{
	private String  addressKey;
	private String 	companyID;
	private String  addressID;
	private String 	staffID;
	private String 	addressType;//地址类别ID
	private Date 	livestartDate;//开始居住日期
	private Date 	liveendDate;//结束居住日期
	private String 	addressDesc;//备注
	private String 	addressDetailed;//详细地址
	private String  isDefault;//标识是否是默认地址  是  不是
	
	 
	/**
	 * 收货地址管理 ljc
	 * */
	private String area; //收货人所在地区            zj: 个人名片的地区也存这里
	private String consignee; //收货人
	private String phone; //收货人电话
	private String postCode; //收货人邮政编码
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "开始居住日期", "结束居住日期", "地址类别", "详细地址", "备注" };
		return titles;
	}
	public String[] properties() {
		String[] properties = { String.format("%1$tF", livestartDate), String.format("%1$tF", liveendDate), oMap.get(addressType),addressDetailed, addressDesc};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getAddressKey() {
		return addressKey;
	}
	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
	}

	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public Date getLivestartDate() {
		return livestartDate;
	}
	public void setLivestartDate(Date livestartDate) {
		this.livestartDate = livestartDate;
	}
	public Date getLiveendDate() {
		return liveendDate;
	}
	public void setLiveendDate(Date liveendDate) {
		this.liveendDate = liveendDate;
	}
	public String getAddressDesc() {
		return addressDesc;
	}
	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}
	public String getAddressDetailed() {
		return addressDetailed;
	}
	public void setAddressDetailed(String addressDetailed) {
		this.addressDetailed = addressDetailed;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * 获取地址类别名称
	 * @return
	 */
	public String getAddressTypeName() {
		if(oMap!=null&&addressType!=null) {
			return oMap.get(addressType);
		}else{
			return null;
		}
	}
    
}
