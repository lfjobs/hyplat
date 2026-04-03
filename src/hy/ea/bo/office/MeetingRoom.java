package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

/**
 * 会议室
 * @author Administrator
 *
 */
public class MeetingRoom implements BaseBean,java.io.Serializable{
	private String mroomKey;
	private String mroomID;
    private String companyID;//所属公司
    private String zone;//所在位置
    private String roomName;//会议室名
	
	private String remark;//说明
	private String capacity;//容纳人数
	private String adminID;//管理员ID
	private String adminName;//管理员姓名
	private String adminTel;//管理员联系方式
	public String getMroomKey() {
		return mroomKey;
	}
	public void setMroomKey(String mroomKey) {
		this.mroomKey = mroomKey;
	}
	public String getMroomID() {
		return mroomID;
	}
	public void setMroomID(String mroomID) {
		this.mroomID = mroomID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getAdminID() {
		return adminID;
	}
	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminTel() {
		return adminTel;
	}
	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
   

	
    
	
	
}
