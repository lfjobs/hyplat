package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 员工会议单
 * @author Administrator
 *
 */
public class StaffMeeting implements BaseBean ,ExcelBean,java.io.Serializable{
	private String meetingID;
	private String meetingKey;
	private String companyID;//发起人所在公司
	private String organizationID;//发起人所在部门
	private String meetingNum;//会议编号
	private String meetingName;//会议名称
	private String meetingTheme;//会议主题
	private String meetingRequire;//会议要求
	private String principal;//会议发起人
	private String principalID;//联系人ID
	private String principaltel;//联系人电话；
	private String meetingType;//会议类型；初步考虑有：现场会议和视频会议  00  01
	private String meetingMinutes;//会议纪要
	private String meetingSummary;//会议总结
	private String meetingBrief;//会议简报 
	private String meetingRecord;//会议记录
	private String recorderID;//会议记录人
	private Date startDate;//规定会议开始时间
	private Date endDate;//规定会议结束时间
	private Date factStartDate;//会议时间开始时间
	private Date factEndDate;//会议实际结束时间
	private String meetingPlace;//会议原定地点
	private String factPlace;//会议实际地点
	private Date placeUpdate;//会议地点调整时间
	private String placeCause;//会议地点调整原因
	private Date dateUpdate;//会议开始时间调整时间
	private String dateCause;//会议时间调整原因
	private String smsNotice;//短信通知
	private String emailNotice;//邮件通知
	private String qqNotice;//QQ通知
	private String noticeContent;//通知内容
	private String noticeType;//通知状态00未通知：01:已通知; 
	private String status;//会议状态00：正常进行;01 会议取消; 09删除
    private String cmCause;//会议取消原因
    private String accessory;//附件文档类的
    private String mrecordAttach;//会议记录附件
    private String recordfile;//录音文件
    private String mroomoID;//会议室预约ID
    private Date updateTime;
    //视频会议新增
  	private String roomtype; //!--房间类型 1固定会议室 2预约会议室 4周例会议室-->
  	private String roomid;//第三方视频会议id
  	private String meetingpsw;
  	private String chairpsw;//主席密码
    
    
	public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称","部门名称", "凭证号","通知编号", "通知责任人","被通知责任人", "通知内容", "通知时间", "通知原因",
				"综合意见","公司经理","总部经理","主管","总部主管","审核","财务部审核","会计","总会计","出纳","总出纳"};
		return titles;
	}

	public String getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}

	public String getMeetingKey() {
		return meetingKey;
	}

	public void setMeetingKey(String meetingKey) {
		this.meetingKey = meetingKey;
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

	public String getMeetingNum() {
		return meetingNum;
	}

	public void setMeetingNum(String meetingNum) {
		this.meetingNum = meetingNum;
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

	public String getMeetingRequire() {
		return meetingRequire;
	}

	public void setMeetingRequire(String meetingRequire) {
		this.meetingRequire = meetingRequire;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getMeetingMinutes() {
		return meetingMinutes;
	}

	public void setMeetingMinutes(String meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}

	public String getMeetingSummary() {
		return meetingSummary;
	}

	public void setMeetingSummary(String meetingSummary) {
		this.meetingSummary = meetingSummary;
	}

	public String getMeetingBrief() {
		return meetingBrief;
	}

	public void setMeetingBrief(String meetingBrief) {
		this.meetingBrief = meetingBrief;
	}

	public String getMeetingRecord() {
		return meetingRecord;
	}

	public void setMeetingRecord(String meetingRecord) {
		this.meetingRecord = meetingRecord;
	}

	public String getRecorderID() {
		return recorderID;
	}

	public void setRecorderID(String recorderID) {
		this.recorderID = recorderID;
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

	public Date getFactStartDate() {
		return factStartDate;
	}

	public void setFactStartDate(Date factStartDate) {
		this.factStartDate = factStartDate;
	}

	public Date getFactEndDate() {
		return factEndDate;
	}

	public void setFactEndDate(Date factEndDate) {
		this.factEndDate = factEndDate;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getFactPlace() {
		return factPlace;
	}

	public void setFactPlace(String factPlace) {
		this.factPlace = factPlace;
	}

	public Date getPlaceUpdate() {
		return placeUpdate;
	}

	public void setPlaceUpdate(Date placeUpdate) {
		this.placeUpdate = placeUpdate;
	}

	public String getPlaceCause() {
		return placeCause;
	}

	public void setPlaceCause(String placeCause) {
		this.placeCause = placeCause;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getDateCause() {
		return dateCause;
	}

	public void setDateCause(String dateCause) {
		this.dateCause = dateCause;
	}


	public String getSmsNotice() {
		return smsNotice;
	}

	public void setSmsNotice(String smsNotice) {
		this.smsNotice = smsNotice;
	}

	public String getEmailNotice() {
		return emailNotice;
	}

	public void setEmailNotice(String emailNotice) {
		this.emailNotice = emailNotice;
	}

	public String getQqNotice() {
		return qqNotice;
	}

	public void setQqNotice(String qqNotice) {
		this.qqNotice = qqNotice;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getCmCause() {
		return cmCause;
	}

	public void setCmCause(String cmCause) {
		this.cmCause = cmCause;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getRecordfile() {
		return recordfile;
	}

	public void setRecordfile(String recordfile) {
		this.recordfile = recordfile;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrincipalID() {
		return principalID;
	}

	public void setPrincipalID(String principalID) {
		this.principalID = principalID;
	}

	public String getPrincipaltel() {
		return principaltel;
	}

	public void setPrincipaltel(String principaltel) {
		this.principaltel = principaltel;
	}

	public String getMrecordAttach() {
		return mrecordAttach;
	}

	public void setMrecordAttach(String mrecordAttach) {
		this.mrecordAttach = mrecordAttach;
	}

	public String getMroomoID() {
		return mroomoID;
	}

	public void setMroomoID(String mroomoID) {
		this.mroomoID = mroomoID;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getMeetingpsw() {
		return meetingpsw;
	}

	public void setMeetingpsw(String meetingpsw) {
		this.meetingpsw = meetingpsw;
	}

	public String getChairpsw() {
		return chairpsw;
	}

	public void setChairpsw(String chairpsw) {
		this.chairpsw = chairpsw;
	}

    
	
	
}
