package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 会议室预约
 * @author Administrator
 *
 */
public class MeetingRoomOrder implements BaseBean{
	private String mroomoKey;
	private String 	mroomoID;
	private String meetingName;//会议名称
	private String meetingTheme;//会议主题

	
	private Date  startDate;//开始日期
	private Date endDate;//结束日期
	private String startDates;
	private String endDates;
	private String dates;//属于哪一天的  格式 "yyyy-MM-dd"
	private String startTime;//开始时间
	private String endTime;//结束时间
	
	
	private Date startDay;//完整的开始时间格式 "yyyy-MM-dd hh:mm:ss"
	private Date endDay;//完整的结束时间格式 "yyyy-MM-dd hh:mm:ss"
	private String startDays;
	private String endDays;
    
	private String companyID;//预约人公司
    private String staffID;//预约人ID
    private String staffName;//预约人姓名
	private String orderTel;//预定电话
	
    private String organizationID;//预约部门id
    private String organizationName;//预约部门name;
    
    private String mroomID;//会议室ID  目前一次预约只能预约一个会议室，
    private String cancel;// 取消 01；
    private String quote;//是否被引用 null没引用 01 引用了
	public String getMroomoKey() {
		return mroomoKey;
	}

	public void setMroomoKey(String mroomoKey) {
		this.mroomoKey = mroomoKey;
	}

	public String getMroomoID() {
		return mroomoID;
	}

	public void setMroomoID(String mroomoID) {
		this.mroomoID = mroomoID;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getMeetingTheme() {
		return meetingTheme;
	}

	public void setMeetingTheme(String meetingTheme) {
		this.meetingTheme = meetingTheme;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrderTel() {
		return orderTel;
	}

	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getMroomID() {
		return mroomID;
	}

	public void setMroomID(String mroomID) {
		this.mroomID = mroomID;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getStartDays() {
		return startDays;
	}

	public void setStartDays(String startDays) {
		this.startDays = startDays;
	}

	public String getEndDays() {
		return endDays;
	}

	public void setEndDays(String endDays) {
		this.endDays = endDays;
	}

	public String getStartDates() {
		return startDates;
	}

	public void setStartDates(String startDates) {
		this.startDates = startDates;
	}

	public String getEndDates() {
		return endDates;
	}

	public void setEndDates(String endDates) {
		this.endDates = endDates;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
    
    
    
	
    
	
	
    
	
	
}
