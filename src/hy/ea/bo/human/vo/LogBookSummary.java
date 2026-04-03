/**
 * LogBook
 */
package hy.ea.bo.human.vo;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
/**
 * YJG工作日志汇总
 * @author Administrator
 */
public class LogBookSummary implements BaseBean,ExcelBean,Serializable {
	private String  logBookKey;
	private String  logBookID;
	
	private String  companyID;
	private String  companyName;
	
	private String 	organizationID;
	private String categoryname;
	private String staffcategoryid;
	
	private String 	staffID;
	private String staffName;//员工姓名
	/********对应code表**********/
	private String 	scoreSort;//得分类别ID
	private String 	scoreSortName;//得分类别name
	/******************/
	private String  startdate;//起时间
	private String  jobLocation;//工作地点
	private String 	enddate;//止时间
	private String 	jobContent;//完成工作内容
	
	private String 	bisect;//得分
	private String 	attachment;//附件类别及名称
	private String 	supervisor;//主管签字
	private String 	staffingManage;//人事主管管理
	private String 	manager;//公司经理
	private Date 	todaydate;//当天日期
	private String  status;//正常时00,锁定01；
	private String staffCode;//员工编号
	private String logoStatus;
	private String postname;
	
	public static String[] columnHeadings() {
		String[] titles = {"序号" ,"公司名称", "员工编号","员工姓名", "当天日期","起时间","止时间", "工作地点","完成工作内容", "得分类别", "得分", "附件类别及名称",
				 "主管签字", "人事主管管理", "公司经理"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { companyName,staffCode,staffName,String.format("%1$tF", todaydate),startdate,
				enddate, jobLocation,jobContent, scoreSortName,bisect,attachment, supervisor,
				staffingManage, manager};
		return properties;
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
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	/**
	 * 得分类别
	 * @return
	 */
	public String getScoreSort() {
		return scoreSort;
	}
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
	public void setTodaydate(Date todaydate) {
		this.todaydate = todaydate;
	}
	/**
	 * 员工姓名
	 * @return
	 */
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * 员工编号
	 * @return
	 */
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 得分类别
	 * @return
	 */
	public String getScoreSortName() {
		return scoreSortName;
	}
	public void setScoreSortName(String scoreSortName) {
		this.scoreSortName = scoreSortName;
	}
	/**
	 * 正常时00,锁定01；
	 * @return
	 */
	public String getStatus() {
		return status;
	}
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
	public String getLogoStatus() {
		return logoStatus;
	}
	public void setLogoStatus(String logoStatus) {
		this.logoStatus = logoStatus;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getStaffcategoryid() {
		return staffcategoryid;
	}
	public void setStaffcategoryid(String staffcategoryid) {
		this.staffcategoryid = staffcategoryid;
	}
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
	}
}
