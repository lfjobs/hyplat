/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;
/**
 * 日志
 * YJG
 * @author Administrator
 */
public class LogBook implements BaseBean,ExcelBean{
	private String  logBookKey;
	private String  logBookID;
	private String  companyID;
	private String 	organizationID;
	private String 	staffID;
	private String  inputDate; //录入时间
	private String  startdate;//起时间
	private String  jobLocation;//工作地点 
	private String 	enddate;//止时间
	private String 	jobContent;//完成工作内容
	private String 	scoreSort;//得分类别ID
	private String 	bisect;//得分
	private String 	attachment;//附件类别及名称
	private String 	supervisor;//主管签字
	private String 	staffingManage;//人事主管管理
	private String 	manager;//公司经理
	private Date 	todaydate;//当天日期
	private String  status;//正常时00,锁定01；
	private File    photo;
	private String photoFileName;
	private String photoContentType;
	/**
	 * 转正之前：00 转正之后01
	 */
	private String logoStatus;
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "当天日期","录入时间" ,"起时间","止时间","工作地点","完成工作内容", "得分类别", "得分",
				 "主管签字", "人事主管管理", "公司经理"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { String.format("%1$tF",todaydate),inputDate,startdate, 
				enddate,jobLocation, jobContent, oMap.get(scoreSort),bisect,supervisor,
				staffingManage, manager};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
    public String getScoreSortName()
    {
    	String scoresortname="";
    	if(oMap!=null)
    	{
    		scoresortname=oMap.get(scoreSort);
    	}
    	return scoresortname;
    }
	public String getLogBookKey() {
		return logBookKey;
	}
	public void setLogBookKey(String logBookKey) {
		this.logBookKey = logBookKey;
	}
	public String getLogBookID() {
		return logBookID;
	}
	public void setLogBookID(String logBookID) {
		this.logBookID = logBookID;
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
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 起时间
	 * @return
	 */
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	/**
	 * 止时间
	 * @return
	 */
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	/**
	 * 完成工作内容
	 * @return
	 */
	public String getJobContent() {
		return jobContent;
	}
	/**
	 * 完成工作内容
	 * @param jobContent
	 */
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	/**
	 * 得分类别ID
	 * @return
	 */
	public String getScoreSort() {
		return scoreSort;
	}
	/**
	 * 得分类别ID
	 * @param scoreSort
	 */
	public void setScoreSort(String scoreSort) {
		this.scoreSort = scoreSort;
	}
	/**
	 * 得分
	 * @return
	 */
	public String getBisect() {
		return bisect;
	}
	/**
	 * 得分
	 * @param bisect
	 */
	public void setBisect(String bisect) {
		this.bisect = bisect;
	}
	/**
	 * 附件类别及名称
	 * @return
	 */
	public String getAttachment() {
		return attachment;
	}
	/**
	 * 附件类别及名称
	 * @param attachment
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	/**
	 * 主管签字
	 * @return
	 */
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	/**
	 * 人事主管管理
	 * @return
	 */
	public String getStaffingManage() {
		return staffingManage;
	}
	public void setStaffingManage(String staffingManage) {
		this.staffingManage = staffingManage;
	}
	/**
	 * 公司经理
	 * @return
	 */
	public String getManager() {
		return manager;
	}
	/**
	 * 公司经理
	 * @param manager
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}
	/**
	 * 当天日期
	 * @return
	 */
	public Date getTodaydate() {
		return todaydate;
	}
	/**
	 * 当天日期
	 * @param todaydate
	 */
	public void setTodaydate(Date todaydate) {
		this.todaydate = todaydate;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	/**
	 * 正常时00,锁定01；
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 正常时00,锁定01；
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 工作地点 
	 * @return
	 */
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	/**
	 * 录入时间
	 * @return
	 */
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getLogoStatus() {
		return logoStatus;
	}
	public void setLogoStatus(String logoStatus) {
		this.logoStatus = logoStatus;
	}

	
}
