package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 请假数据
 * @author
 *
 */
public class OvertimeData implements BaseBean, Serializable {
	private static final long serialVersionUID = 4435251039254585860L;
	private String staffId;
	private String overtimeDate;
	private Double overtimeHours;
	private Integer overtimeMinutes;
	private Integer overtimeSeconds;
	private String overtimeType;

	public OvertimeData() {
	}

	public OvertimeData(String staffId, String overtimeDate, Double overtimeHours, Integer overtimeMinutes, Integer overtimeSeconds, String overtimeType) {
		this.staffId = staffId;
		this.overtimeDate = overtimeDate;
		this.overtimeHours = overtimeHours;
		this.overtimeMinutes = overtimeMinutes;
		this.overtimeSeconds = overtimeSeconds;
		this.overtimeType = overtimeType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getOvertimeDate() {
		return overtimeDate;
	}

	public void setOvertimeDate(String overtimeDate) {
		this.overtimeDate = overtimeDate;
	}

	public Double getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public Integer getOvertimeMinutes() {
		return overtimeMinutes;
	}

	public void setOvertimeMinutes(Integer overtimeMinutes) {
		this.overtimeMinutes = overtimeMinutes;
	}

	public Integer getOvertimeSeconds() {
		return overtimeSeconds;
	}

	public void setOvertimeSeconds(Integer overtimeSeconds) {
		this.overtimeSeconds = overtimeSeconds;
	}

	public String getOvertimeType() {
		return overtimeType;
	}

	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
}
