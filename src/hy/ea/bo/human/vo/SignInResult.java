package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到结果
 * @author
 *
 */
public class SignInResult implements BaseBean, Serializable {
	private static final long serialVersionUID = 2931370826924899372L;
	private String staffId;
	private String signInDate;
	private Date signInTime;
	private Date signOutTime;
	private Double lateMinutes;//迟到分钟数
	private Double leaveEarlyMinutes;//早退分钟数
	private Integer lateSeconds;//迟到秒数
	private Integer leaveEarlySeconds;//早退秒数
	private String staffName;

	public SignInResult() {
	}

	public SignInResult(String staffId, String signInDate, Date signInTime, Date signOutTime, Double lateMinutes, Double leaveEarlyMinutes, Integer lateSeconds, Integer leaveEarlySeconds) {
		this.staffId = staffId;
		this.signInDate = signInDate;
		this.signInTime = signInTime;
		this.signOutTime = signOutTime;
		this.lateMinutes = lateMinutes;
		this.leaveEarlyMinutes = leaveEarlyMinutes;
		this.lateSeconds = lateSeconds;
		this.leaveEarlySeconds = leaveEarlySeconds;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getSignInDate() {
		return signInDate;
	}

	public void setSignInDate(String signInDate) {
		this.signInDate = signInDate;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}

	public Double getLateMinutes() {
		return lateMinutes;
	}

	public void setLateMinutes(Double lateMinutes) {
		this.lateMinutes = lateMinutes;
	}

	public Double getLeaveEarlyMinutes() {
		return leaveEarlyMinutes;
	}

	public void setLeaveEarlyMinutes(Double leaveEarlyMinutes) {
		this.leaveEarlyMinutes = leaveEarlyMinutes;
	}

	public Integer getLateSeconds() {
		return lateSeconds;
	}

	public void setLateSeconds(Integer lateSeconds) {
		this.lateSeconds = lateSeconds;
	}

	public Integer getLeaveEarlySeconds() {
		return leaveEarlySeconds;
	}

	public void setLeaveEarlySeconds(Integer leaveEarlySeconds) {
		this.leaveEarlySeconds = leaveEarlySeconds;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}
