package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 请假数据
 * @author
 *
 */
public class LeaveData implements BaseBean, Serializable {
	private static final long serialVersionUID = -5605720462097229303L;
	private String staffId;
	private String leaveDate;
	private Double leaveHours;
	private Integer leaveMinutes;
	private Integer leaveSeconds;
	private String leaveType;

	public LeaveData() {
	}

	public LeaveData(String staffId, String leaveDate, Double leaveHours, Integer leaveMinutes, Integer leaveSeconds, String leaveType) {
		this.staffId = staffId;
		this.leaveDate = leaveDate;
		this.leaveHours = leaveHours;
		this.leaveMinutes = leaveMinutes;
		this.leaveSeconds = leaveSeconds;
		this.leaveType = leaveType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Double getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(Double leaveHours) {
		this.leaveHours = leaveHours;
	}

	public Integer getLeaveMinutes() {
		return leaveMinutes;
	}

	public void setLeaveMinutes(Integer leaveMinutes) {
		this.leaveMinutes = leaveMinutes;
	}

	public Integer getLeaveSeconds() {
		return leaveSeconds;
	}

	public void setLeaveSeconds(Integer leaveSeconds) {
		this.leaveSeconds = leaveSeconds;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
}
