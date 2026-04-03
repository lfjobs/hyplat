package hy.ea.bo.production.scmanage;
import java.util.Date;

import hy.plat.bo.BaseBean;


/**
 * 用户观看视频情况
 *
 */
public class UserReadVideo implements BaseBean{
	private String urvKey;  		//主键
	private String urvID;  		//业务主键
	private String vedioID;//视频ID
    private String staffID;//观看视频的用户ID
    private Date readDate;//阅读时间
    
	public String getUrvKey() {
		return urvKey;
	}
	public void setUrvKey(String urvKey) {
		this.urvKey = urvKey;
	}
	public String getUrvID() {
		return urvID;
	}
	public void setUrvID(String urvID) {
		this.urvID = urvID;
	}
	public String getVedioID() {
		return vedioID;
	}
	public void setVedioID(String vedioID) {
		this.vedioID = vedioID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	

}