package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 现场会议
 * @author Administrator
 *
 */
public class Meeting implements BaseBean,ExcelBean,java.io.Serializable{

	private String meetingKey;
	private String meetingID;
	private String companyID;
	private String organizationID;
	
	private String mtName;			 		//会议名称
	private String mtSubject;				//会议主题
	private String mtOrganizationID;		//发起部门 
	private Date mtDate;					//开会时间
	private String mtLeader;				//开会领导
	private String mtStaff;					//参会人员
	private String mtAddr;					//会议室地点
	private String mtPlace;					//会议室布置
	private String mtLog;					//会议记录
	private String mtStatus;				//会议是否完成（00表示未完成，01表示完成）
	private String mtNotice;				//会议通知
	private String mtFilePath;				//会议文件
	//会议文件
	private File mtFile;
	private String mtFileFileName;
	private String mtFileContentType;
	
	private static Map<String,String> oMap;
	public static String[] columnHeadings(){
		String[] titles = {"序号","会议名称","会议主题","发起部门","开会时间","开会领导","参会人员","会议室地点","会议室布置","会议记录","会议是否完成","会议通知"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles = {mtName,mtSubject,getMtOName(),String.format("%1$tF", mtDate),mtLeader,mtStaff,mtAddr,mtPlace,mtLog,oMap.get(mtStatus),mtNotice};
		return titles;
	}
	public String getMtOName(){
		String sName = "";
		if(null!=oMap){
			sName = oMap.get(mtOrganizationID);
		}
		return sName;
	}
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getMeetingKey() {
		return meetingKey;
	}
	public void setMeetingKey(String meetingKey) {
		this.meetingKey = meetingKey;
	}
	public String getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
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
	public String getMtName() {
		return mtName;
	}
	public void setMtName(String mtName) {
		this.mtName = mtName;
	}
	public String getMtSubject() {
		return mtSubject;
	}
	public void setMtSubject(String mtSubject) {
		this.mtSubject = mtSubject;
	}
	public String getMtOrganizationID() {
		return mtOrganizationID;
	}
	public void setMtOrganizationID(String mtOrganizationID) {
		this.mtOrganizationID = mtOrganizationID;
	}
	public Date getMtDate() {
		return mtDate;
	}
	public void setMtDate(Date mtDate) {
		this.mtDate = mtDate;
	}
	public String getMtFilePath() {
		return mtFilePath;
	}
	public void setMtFilePath(String mtFilePath) {
		this.mtFilePath = mtFilePath;
	}
	public String getMtFileContentType() {
		return mtFileContentType;
	}
	public void setMtFileContentType(String mtFileContentType) {
		this.mtFileContentType = mtFileContentType;
	}
	public void setMtFile(File mtFile) {
		this.mtFile = mtFile;
	}
	public String getMtLeader() {
		return mtLeader;
	}
	public void setMtLeader(String mtLeader) {
		this.mtLeader = mtLeader;
	}
	public String getMtStaff() {
		return mtStaff;
	}
	public void setMtStaff(String mtStaff) {
		this.mtStaff = mtStaff;
	}
	public String getMtAddr() {
		return mtAddr;
	}
	public void setMtAddr(String mtAddr) {
		this.mtAddr = mtAddr;
	}
	public String getMtPlace() {
		return mtPlace;
	}
	public void setMtPlace(String mtPlace) {
		this.mtPlace = mtPlace;
	}
	public String getMtLog() {
		return mtLog;
	}
	public void setMtLog(String mtLog) {
		this.mtLog = mtLog;
	}
	public String getMtStatus() {
		return mtStatus;
	}
	public void setMtStatus(String mtStatus) {
		this.mtStatus = mtStatus;
	}
	public String getMtNotice() {
		return mtNotice;
	}
	public void setMtNotice(String mtNotice) {
		this.mtNotice = mtNotice;
	}
	public File getMtFile() {
		return mtFile;
	}
	public String getMtFileFileName() {
		return mtFileFileName;
	}
	public void setMtFileFileName(String mtFileFileName) {
		this.mtFileFileName = mtFileFileName;
	} 
}
