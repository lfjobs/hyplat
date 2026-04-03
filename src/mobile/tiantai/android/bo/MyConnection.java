package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;
/**
 * 
 * 我的人脉表
 * @author mz
 *
 */

public class MyConnection implements BaseBean{
	
	private String key;
	private String id;
	private String staffID;//这个人脉是哪个用户ID
	private String staffName;//这个人脉是哪个用户名字
	private String relationID;//这个人脉是那个用户的人脉
	private String city;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getRelationID() {
		return relationID;
	}
	public void setRelationID(String relationID) {
		this.relationID = relationID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

	
}