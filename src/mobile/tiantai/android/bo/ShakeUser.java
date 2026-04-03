package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * 正在摇的人的信息
 * @author mz
 *
 */

public class ShakeUser implements BaseBean{
	
	private String key;
	private String id;
	
	private String staffID;
	private String staffName;
	private String city;
	private String lng;
	private String lat;
    private String distance;//与我的距离不需要存储只是到前台显示
	private Date shaketime;
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getShaketime() {
		return shaketime;
	}
	public void setShaketime(Date shaketime) {
		this.shaketime = shaketime;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
     
	
}