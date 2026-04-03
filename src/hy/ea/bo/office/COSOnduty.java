package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class COSOnduty implements BaseBean, ExcelBean ,java.io.Serializable{
	private String ondutyKey;
	private String ondutyID;
	private String companyID;
	private String organizationID;
	private String departmentName;// 部门名称
	private String headOfDepartment;// 部门负责人
	private String staffName;//员工姓名
	private String dutyRoom;//值班室
	private Date attendedTime;// 值班时间
	private String scope;//负责范围
	private String jobRecord;// 工作记录
	private String remark;// 备注

	public static String[] columnHeadings() {
		String[] titles = { "序号", "部门名称", "部门负责人", "员工姓名", "值班室","值班时间","负责范围","工作记录","备注"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { departmentName,headOfDepartment,staffName,dutyRoom,
				String.format("%1$tF", attendedTime) ,scope, jobRecord,remark };
		return properties;
	}

	public String getOndutyKey() {
		return ondutyKey;
	}

	public void setOndutyKey(String ondutyKey) {
		this.ondutyKey = ondutyKey;
	}

	public String getOndutyID() {
		return ondutyID;
	}

	public void setOndutyID(String ondutyID) {
		this.ondutyID = ondutyID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHeadOfDepartment() {
		return headOfDepartment;
	}

	public void setHeadOfDepartment(String headOfDepartment) {
		this.headOfDepartment = headOfDepartment;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDutyRoom() {
		return dutyRoom;
	}

	public void setDutyRoom(String dutyRoom) {
		this.dutyRoom = dutyRoom;
	}

	public Date getAttendedTime() {
		return attendedTime;
	}

	public void setAttendedTime(Date attendedTime) {
		this.attendedTime = attendedTime;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getJobRecord() {
		return jobRecord;
	}

	public void setJobRecord(String jobRecord) {
		this.jobRecord = jobRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
