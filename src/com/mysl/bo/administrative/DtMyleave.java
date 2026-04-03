package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * @author mz 请假申请单
 */

public class DtMyleave implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912517002220451206L;
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
	private String auditor;

	/*********************员工请假申请表**********************/  
	private String leavePostName;			//职位
	private String leaveDays;				//请假天数
	private String leaveHour;			    //请假小时
	private String leaveStartDate;			//起时间
	private String leaveEndDate;			//止时间
	private String leaveReceiver;			//工作接管人
	private String leaveReceiverid;//工作接管人id
	private String leaveType;				//请假类别
	private String leaveReason; 			//请假原因
	private String signdate;				//报到日期
	private String terminatedate;			//销假日期 
	private String leavecasual;				//事假累计
	private String leavesick;				//病假累计
	private String checkdiscount; 			//考勤折扣
	private String attachPath;//附件
	private String applyDate;//申请时间
	private String auditorId;//审核人Id
	private String auditTime;//审核时间

	@Override
	public String[] properties() {
		String[] properties = {companyname,organizationname,serialnumber,
				staffname, applyDate,  leavePostName, leaveDays,leaveHour,
			
				
				
				leaveStartDate,leaveEndDate,leaveReceiver,leaveType,leaveReason,
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿",auditor
				};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","申请日期","职位","请假天数","请假小时","起时间","止时间","接管人","请假类型","请假事由","单据状态","审核人"};
		return titles;
	}
	/** default constructor */
	public DtMyleave() {
	}

	/** full constructor */
  
	public DtMyleave(String key, String id, String status, String companyid,
			String companyname, String organizationid, String organizationname,
			String staffid, String staffname, Date addtime,
			String leavePostName, String leaveDays, String leaveHour,
			String leaveStartDate, String leaveEndDate, String leaveReceiver,
			String leaveType, String leaveReason, String signdate,
			String terminatedate, String leavecasual, String leavesick,
			String checkdiscount, String attachPath,String applyDate,String leaveReceiverid) {
		super();
		this.key = key;
		this.id = id;
		this.status = status;
		this.companyid = companyid;
		this.companyname = companyname;
		this.organizationid = organizationid;
		this.organizationname = organizationname;
		this.staffid = staffid;
		this.staffname = staffname;
		this.addtime = addtime;
		this.leavePostName = leavePostName;
		this.leaveDays = leaveDays;
		this.leaveHour = leaveHour;
		this.leaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.leaveReceiver = leaveReceiver;
		this.leaveType = leaveType;
		this.leaveReason = leaveReason;
		this.signdate = signdate;
		this.terminatedate = terminatedate;
		this.leavecasual = leavecasual;
		this.leavesick = leavesick;
		this.checkdiscount = checkdiscount;
		this.attachPath = attachPath;
		this.applyDate = applyDate;
		this.leaveReceiverid = leaveReceiverid;
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

	public String getLeavePostName() {
		return leavePostName;
	}

	public void setLeavePostName(String leavePostName) {
		this.leavePostName = leavePostName;
	}

	public String getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}

	public String getLeaveHour() {
		return leaveHour;
	}

	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getLeaveReceiver() {
		return leaveReceiver;
	}

	public void setLeaveReceiver(String leaveReceiver) {
		this.leaveReceiver = leaveReceiver;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	public String getTerminatedate() {
		return terminatedate;
	}

	public void setTerminatedate(String terminatedate) {
		this.terminatedate = terminatedate;
	}

	public String getLeavecasual() {
		return leavecasual;
	}

	public void setLeavecasual(String leavecasual) {
		this.leavecasual = leavecasual;
	}

	public String getLeavesick() {
		return leavesick;
	}

	public void setLeavesick(String leavesick) {
		this.leavesick = leavesick;
	}

	public String getCheckdiscount() {
		return checkdiscount;
	}

	public void setCheckdiscount(String checkdiscount) {
		this.checkdiscount = checkdiscount;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getLeaveReceiverid() {
		return leaveReceiverid;
	}

	public void setLeaveReceiverid(String leaveReceiverid) {
		this.leaveReceiverid = leaveReceiverid;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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