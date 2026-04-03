package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * @author mz 出差申请单
 */

public class DtMytravel implements java.io.Serializable,BaseBean,ExcelBean {

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
	

	/***********出差申请单***********/
	private String travelcause;		//出差原因
	private Date startDate;			//出差开始日期
	private Date endDate;			//出差止日期
	private String travelDays;			//出差天数 系统生成	=出差止日期-出差起日期
	private String travelHours;
	private String applyDate;//申请日期
	private String postName;
	private String auditorId;//审核人Id
	private String auditTime;//审核时间
	private String travelStartDate;
	private String travelEndDate;

	@Override
	public String[] properties() {
		String[] properties = {companyname,organizationname,serialnumber,
				staffname, String.format("%1$tF", addtime),String.format("%1$tF", startDate), String.format("%1$tF", endDate),travelDays,travelcause,
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿",auditor
				};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","申请日期","出差起时间","出差止时间","出差天数","出差原因","单据状态","审核人"};
		return titles;
	}
	/** default constructor */
	public DtMytravel() {
	}

	/** full constructor */
	public DtMytravel(String id, String status,
			String serialnumber, String proposerid, String proposername,
			String companyid, String companyname, String organizationid,
			String organizationname, String staffid, String staffname,
			Date addtime, String department, String score, String note) {
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

	public String getTravelcause() {
		return travelcause;
	}

	public void setTravelcause(String travelcause) {
		this.travelcause = travelcause;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTravelDays() {
		return travelDays;
	}

	public void setTravelDays(String travelDays) {
		this.travelDays = travelDays;
	}

	public String getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(String travelHours) {
		this.travelHours = travelHours;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
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

	public String getTravelStartDate() {
		return travelStartDate;
	}

	public void setTravelStartDate(String travelStartDate) {
		this.travelStartDate = travelStartDate;
	}

	public String getTravelEndDate() {
		return travelEndDate;
	}

	public void setTravelEndDate(String travelEndDate) {
		this.travelEndDate = travelEndDate;
	}
}