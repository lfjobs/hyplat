package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

/**
 * 会议室
 * @author mz
 *
 */
@SuppressWarnings("serial")
public class VideoRoom implements BaseBean,java.io.Serializable{
	private String roomId;
    private String roomName;//房间名称-->
    private String roomType;//<!--房间类型 1固定会议室 2预约会议室 4周例会议室-->
    private String verifyMode;//><!--登录校验模型 1、用户密码验证；2、会议室密码验证；3、匿名登录-->
	
	private String curUserCount;//<!--房间当前用户数-->
	private String hopeEndTime;//<!--预定结束时间-->
	private String hopeStartTime;//<!--预定开始时间-->
	private String maxUserCount;//<!--房间最大访问人数-->
	private String password;//密码
	private String enableChairPwd;//是否启用主席密码:0，不启用 , 1 启用
	private String chairPassword;//主席密码


	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getVerifyMode() {
		return verifyMode;
	}

	public void setVerifyMode(String verifyMode) {
		this.verifyMode = verifyMode;
	}

	public String getCurUserCount() {
		return curUserCount;
	}

	public void setCurUserCount(String curUserCount) {
		this.curUserCount = curUserCount;
	}

	public String getHopeEndTime() {
		return hopeEndTime;
	}

	public void setHopeEndTime(String hopeEndTime) {
		this.hopeEndTime = hopeEndTime;
	}

	public String getHopeStartTime() {
		return hopeStartTime;
	}

	public void setHopeStartTime(String hopeStartTime) {
		this.hopeStartTime = hopeStartTime;
	}

	public String getMaxUserCount() {
		return maxUserCount;
	}

	public void setMaxUserCount(String maxUserCount) {
		this.maxUserCount = maxUserCount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnableChairPwd() {
		return enableChairPwd;
	}

	public void setEnableChairPwd(String enableChairPwd) {
		this.enableChairPwd = enableChairPwd;
	}

	public String getChairPassword() {
		return chairPassword;
	}

	public void setChairPassword(String chairPassword) {
		this.chairPassword = chairPassword;
	}
}
