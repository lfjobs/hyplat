package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 值班排班
 * @author
 *
 */
public class DutyScheduling implements BaseBean, Serializable {
	private static final long serialVersionUID = -4678761006946195737L;
	private String dutySchedulingId;
	private String companyId;
	private String address;
	private String dutyStaffId;
	private String dutyStartDate;
	private String dutyEndDate;
	private String dutyType;
	private String tel;
	private String dutyContent;
	private String schedulingDate;//排班时间
	private String dutyStaffName;

	public DutyScheduling() {
	}

	public DutyScheduling(String dutySchedulingId, String companyId, String dutyStaffId, String dutyStartDate, String dutyEndDate, String dutyType) {
		this.dutySchedulingId = dutySchedulingId;
		this.companyId = companyId;
		this.dutyStaffId = dutyStaffId;
		this.dutyStartDate = dutyStartDate;
		this.dutyEndDate = dutyEndDate;
		this.dutyType = dutyType;
	}

	public String getDutySchedulingId() {
		return dutySchedulingId;
	}

	public void setDutySchedulingId(String dutySchedulingId) {
		this.dutySchedulingId = dutySchedulingId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDutyStaffId() {
		return dutyStaffId;
	}

	public void setDutyStaffId(String dutyStaffId) {
		this.dutyStaffId = dutyStaffId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDutyContent() {
		return dutyContent;
	}

	public void setDutyContent(String dutyContent) {
		this.dutyContent = dutyContent;
	}

	public String getSchedulingDate() {
		return schedulingDate;
	}

	public void setSchedulingDate(String schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	public String getDutyStaffName() {
		return dutyStaffName;
	}

	public void setDutyStaffName(String dutyStaffName) {
		this.dutyStaffName = dutyStaffName;
	}

	public String getDutyStartDate() {
		return dutyStartDate;
	}

	public void setDutyStartDate(String dutyStartDate) {
		this.dutyStartDate = dutyStartDate;
	}

	public String getDutyEndDate() {
		return dutyEndDate;
	}

	public void setDutyEndDate(String dutyEndDate) {
		this.dutyEndDate = dutyEndDate;
	}

	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}
}
