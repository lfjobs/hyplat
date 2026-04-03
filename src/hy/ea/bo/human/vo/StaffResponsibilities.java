/**
 * Responsibilities
 */
package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * YJG
 * 
 * @author Administrator 岗位职责管理
 */

public class StaffResponsibilities implements BaseBean, ExcelBean,java.io.Serializable{
  private String responsibilitiesKey;
  private String responsibilitiesID;
  private String companyID;
  private String staffID;
  private String organizationID;//外键
  private String departmentID;//部门名称
  private String organizationPID;//直接行政上级
  private String organizationCID;//直接行政下级
  private String responsibilitiesNum;
  private String recordNum;//档案编号
  private Date startDate;//开始时间
  private Date endDate;//结束时间
  private String postName;//岗位名称
  private String dutyName;//职责名称
  private String postmanage;//岗位情况管理
  private String companyName;//工作单位名称
  private String managesCope;//管理范围
  private String jobContent1;//工作内容1
  private String jobContent2;//工作内容2
  private String jobContent3;//工作内容3
  private String jobContent4;//工作内容4
  private String jobContent5;//工作内容5
  private String staffName;// 人员名
  private String fileNum;// 文件号
  private String staffCode;// 人员编号
  private String photo;// 照片路径
  
  //加入四个字段 对应上面id对应的名称lwt
  private String companyIDName;//
  private String departmentIDName;
  private String organizationPIDName;
  private String organizationCIDName;
  	//我现在想到了,以后别用这个map了. lwt
//	private static Map<String, String> oMap;// 有用, 导Excel的时候. 不大好.以后想办法

	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司名称", "部门名称","员工编号", "员工姓名", "岗位职责编号", "岗位职责档案编号",
				"岗位起始时间", "岗位截止时间", "岗位名称", "职务名称", "岗位情况管理", "工作单位名称",
				"直接行政上级", "直接行政下级", "管理范围", "工作内容1", "工作内容2","工作内容3", "工作内容4", "工作内容5"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { companyIDName,departmentIDName,staffCode, staffName, responsibilitiesNum,
				recordNum, String.format("%1$tF", startDate),
				String.format("%1$tF", endDate), postName, dutyName,
				postmanage, companyName, 
				organizationPIDName, organizationCIDName,
				managesCope, jobContent1,jobContent2,jobContent3,jobContent4 ,jobContent5};
		return properties;
	}

	public String getResponsibilitiesKey() {
		return responsibilitiesKey;
	}

	public String getJobContent5() {
		return jobContent5;
	}

	public void setJobContent5(String jobContent5) {
		this.jobContent5 = jobContent5;
	}

	public void setResponsibilitiesKey(String responsibilitiesKey) {
		this.responsibilitiesKey = responsibilitiesKey;
	}

	public String getResponsibilitiesID() {
		return responsibilitiesID;
	}

	public void setResponsibilitiesID(String responsibilitiesID) {
		this.responsibilitiesID = responsibilitiesID;
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

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getOrganizationPID() {
		return organizationPID;
	}

	public void setOrganizationPID(String organizationPID) {
		this.organizationPID = organizationPID;
	}

	public String getOrganizationCID() {
		return organizationCID;
	}

	public void setOrganizationCID(String organizationCID) {
		this.organizationCID = organizationCID;
	}

	public String getResponsibilitiesNum() {
		return responsibilitiesNum;
	}

	public void setResponsibilitiesNum(String responsibilitiesNum) {
		this.responsibilitiesNum = responsibilitiesNum;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
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

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getPostmanage() {
		return postmanage;
	}

	public void setPostmanage(String postmanage) {
		this.postmanage = postmanage;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getManagesCope() {
		return managesCope;
	}

	public void setManagesCope(String managesCope) {
		this.managesCope = managesCope;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
//	@Deprecated
//	public static Map<String, String> getOMap() {
//		return oMap;
//	}
//	@Deprecated
//	public static void setOMap(Map<String, String> map) {
//		oMap = map;
//	}

	public String getJobContent1() {
		return jobContent1;
	}

	public void setJobContent1(String jobContent1) {
		this.jobContent1 = jobContent1;
	}

	public String getJobContent2() {
		return jobContent2;
	}

	public void setJobContent2(String jobContent2) {
		this.jobContent2 = jobContent2;
	}

	public String getJobContent3() {
		return jobContent3;
	}

	public void setJobContent3(String jobContent3) {
		this.jobContent3 = jobContent3;
	}

	public String getJobContent4() {
		return jobContent4;
	}

	public void setJobContent4(String jobContent4) {
		this.jobContent4 = jobContent4;
	}

	public String getCompanyIDName() {
		return companyIDName;
	}

	public void setCompanyIDName(String companyIDName) {
		this.companyIDName = companyIDName;
	}

	public String getDepartmentIDName() {
		return departmentIDName;
	}

	public void setDepartmentIDName(String departmentIDName) {
		this.departmentIDName = departmentIDName;
	}

	public String getOrganizationPIDName() {
		return organizationPIDName;
	}

	public void setOrganizationPIDName(String organizationPIDName) {
		this.organizationPIDName = organizationPIDName;
	}

	public String getOrganizationCIDName() {
		return organizationCIDName;
	}

	public void setOrganizationCIDName(String organizationCIDName) {
		this.organizationCIDName = organizationCIDName;
	}
	
}
	