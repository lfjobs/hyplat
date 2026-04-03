package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * @author mz 加班申请单
 */

public class DtMyovertime implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	/**
	 * 
	 */

	private String key;
	private String id;
	private String status;
	private String serialnumber;
	private String companyid;
	private String companyname;
	private String organizationid;
	private String organizationname;
	private String staffid;
	private String staffname;
	private Date addtime;
	//加班申请单
	private String overTimePostName;		//岗位
	private String overTimeDays;			//加班天数
	private String overTimeHour;			//加班小时
	private String overTimeSort;			//加班类别
	private String overTimeStartDate;		//起时间
	private String overTimeEndDate;			//止时间
	private String overTimeReason;          //加班事由
	private String overTimeContent;			//加班内容
	private String overtimeWages;			//加班工资评分
	private String attachPath;//附件
	private String auditor;//审核人记录

	private String applyDate;//申请日期
	private String auditorId;//审核人Id
	private String auditTime;//审核时间
	

	@Override
	public String[] properties() {
		String[] titles = {companyname,organizationname,serialnumber,
				staffname, String.format("%1$tF", addtime), overTimePostName, overTimeSort,overTimeStartDate,overTimeEndDate,overTimeDays,overTimeHour,overtimeWages,overTimeReason,overTimeContent,
				"01".endsWith(status)?"待审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿",auditor
				};
		return titles;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","申请日期","岗位","加班类别","起时间","止时间","加班天数","加班小时","加班工资评分","加班事由","加班内容","单据状态","审核人"};
		return titles;
	}
	/** default constructor */
	public DtMyovertime() {
	}

	/** full constructor */
	
	public DtMyovertime(String key, String id, String status,
			String serialnumber, String companyid, String companyname,
			String organizationid, String organizationname, String staffid,
			String staffname, Date addtime, String overTimePostName,
			String overTimeDays, String overTimeHour, String overTimeSort,
			String overTimeStartDate, String overTimeEndDate,
			String overTimeReason, String overTimeContent,
			String overtimeWages, String attachPath) {
		super();
		this.key = key;
		this.id = id;
		this.status = status;
		this.serialnumber = serialnumber;
		this.companyid = companyid;
		this.companyname = companyname;
		this.organizationid = organizationid;
		this.organizationname = organizationname;
		this.staffid = staffid;
		this.staffname = staffname;
		this.addtime = addtime;
		this.overTimePostName = overTimePostName;
		this.overTimeDays = overTimeDays;
		this.overTimeHour = overTimeHour;
		this.overTimeSort = overTimeSort;
		this.overTimeStartDate = overTimeStartDate;
		this.overTimeEndDate = overTimeEndDate;
		this.overTimeReason = overTimeReason;
		this.overTimeContent = overTimeContent;
		this.overtimeWages = overtimeWages;
		this.attachPath = attachPath;
	}

	// Property accessors

	public String getKey() {
		return this.key;
	}

	

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	
	

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationname() {
		return this.organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getOverTimePostName() {
		return overTimePostName;
	}

	public void setOverTimePostName(String overTimePostName) {
		this.overTimePostName = overTimePostName;
	}

	public String getOverTimeDays() {
		return overTimeDays;
	}

	public void setOverTimeDays(String overTimeDays) {
		this.overTimeDays = overTimeDays;
	}

	public String getOverTimeHour() {
		return overTimeHour;
	}

	public void setOverTimeHour(String overTimeHour) {
		this.overTimeHour = overTimeHour;
	}

	public String getOverTimeSort() {
		return overTimeSort;
	}

	public void setOverTimeSort(String overTimeSort) {
		this.overTimeSort = overTimeSort;
	}

	public String getOverTimeStartDate() {
		return overTimeStartDate;
	}

	public void setOverTimeStartDate(String overTimeStartDate) {
		this.overTimeStartDate = overTimeStartDate;
	}

	public String getOverTimeEndDate() {
		return overTimeEndDate;
	}

	public void setOverTimeEndDate(String overTimeEndDate) {
		this.overTimeEndDate = overTimeEndDate;
	}

	public String getOverTimeReason() {
		return overTimeReason;
	}

	public void setOverTimeReason(String overTimeReason) {
		this.overTimeReason = overTimeReason;
	}

	public String getOverTimeContent() {
		return overTimeContent;
	}

	public void setOverTimeContent(String overTimeContent) {
		this.overTimeContent = overTimeContent;
	}

	public String getOvertimeWages() {
		return overtimeWages;
	}

	public void setOvertimeWages(String overtimeWages) {
		this.overtimeWages = overtimeWages;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
}