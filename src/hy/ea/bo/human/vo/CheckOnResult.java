package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 考勤结果
 * @author
 *
 */
public class CheckOnResult implements BaseBean, Serializable {
	private static final long serialVersionUID = 6633981789610842191L;
	private String staffId;
	private String staffName;
	private String checkOnType;
	private String checkOnDate;
	private String signInTime;
	private String signOutTime;
	private Double minutes;//迟到，早退，请假，旷工，加班分钟数
	private Integer seconds;
	private Double hours;
	private Double days;

	public CheckOnResult() {
	}

	public CheckOnResult(String staffId, String staffName, String checkOnType, String checkOnDate, Double minutes, Integer seconds, Double hours, Double days) {
		this.staffId = staffId;
		this.staffName = staffName;
		this.checkOnType = checkOnType;
		this.checkOnDate = checkOnDate;
		this.minutes = minutes;
		this.seconds = seconds;
		this.hours = hours;
		this.days = days;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCheckOnType() {
		return checkOnType;
	}

	public void setCheckOnType(String checkOnType) {
		this.checkOnType = checkOnType;
	}

	public String getCheckOnDate() {
		return checkOnDate;
	}

	public void setCheckOnDate(String checkOnDate) {
		this.checkOnDate = checkOnDate;
	}

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}

	public Double getMinutes() {
		return minutes;
	}

	public void setMinutes(Double minutes) {
		this.minutes = minutes;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Double getDays() {
		return days;
	}

	public void setDays(Double days) {
		this.days = days;
	}
}
