package hy.ea.bo.company;

import hy.plat.bo.BaseBean;
/**
 * 	房间号
 * 
 * 
 * @author l_admin
 *
 */
public class RoomNumber implements BaseBean {

	private String roomNumKey;
	private String roomNumID;
	private String companyID;
	private String accommodID; //父表外键
	private String roomNum;//房间号
	private String starts ;//房间状态 入住：01  无人:00
	
	public String getRoomNumKey() {
		return roomNumKey;
	}
	public void setRoomNumKey(String roomNumKey) {
		this.roomNumKey = roomNumKey;
	}
	public String getRoomNumID() {
		return roomNumID;
	}
	public void setRoomNumID(String roomNumID) {
		this.roomNumID = roomNumID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAccommodID() {
		return accommodID;
	}
	public void setAccommodID(String accommodID) {
		this.accommodID = accommodID;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getStarts() {
		return starts;
	}
	public void setStarts(String starts) {
		this.starts = starts;
	}
	
}
