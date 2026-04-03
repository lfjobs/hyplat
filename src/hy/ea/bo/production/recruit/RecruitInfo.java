package hy.ea.bo.production.recruit;



import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class RecruitInfo implements BaseBean, ExcelBean,java.io.Serializable {

	private String rikey; // 主键
	private String riId;
	private String workCity;//工作地点(默认：招聘公司所在城市)
	private String workPlace;//工作详细地址(默认：招聘公司详细地址)
	private String workYears;//工作年限要求
	private String education;//学历要求
	private String partorfull;//全职还是兼职  00 全职： 01 兼职
	private String salary;//薪资条件 ，具体金额 或者 范围 或者 面议
	private String status;//发布状态00未发(草稿)  01：已发布 09:已删除
	private String staffID;//责任人，发布人name
	private String staffName;//发布人name
	private Date publishDate;//发布时间
	private String codeID;//职位ID，即：产品ppID;
	private String positionName;//职位类别
	private String ccompanyID;//往来单位ID
	private String companyID;//公司ID
	private String companyName;//公司name
	private String personNumber;//职位人数 
	private String remark;//备注
	private String industry;//行业
	private Date createDate;//创建时间

	private Date  offlineDate;//下线时间
	private String offlineID;//下线操作人
	private String offlineName;//下线操作人姓名

	private String jobRequire;//任职要求



	private String jobTitle;//职位名称
	private String positionCode;//职位类别code





	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司", "职位名称", "薪金要求", "工作年限要求","学历要求","兼职/全职","发布人",
				"发布时间","任职要求","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { companyName,positionName,salary,workYears,education,partorfull,staffName,
				String.format("%1$tF",publishDate),jobRequire,remark};
		return properties;
	}
	public String getRikey() {
		return rikey;
	}
	public void setRikey(String rikey) {
		this.rikey = rikey;
	}
	public String getRiId() {
		return riId;
	}
	public void setRiId(String riId) {
		this.riId = riId;
	}
	public String getWorkCity() {
		return workCity;
	}
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getWorkYears() {
		return workYears;
	}
	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getPartorfull() {
		return partorfull;
	}
	public void setPartorfull(String partorfull) {
		this.partorfull = partorfull;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getJobRequire() {
		return jobRequire;
	}
	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public String getOfflineID() {
		return offlineID;
	}

	public void setOfflineID(String offlineID) {
		this.offlineID = offlineID;
	}

	public String getOfflineName() {
		return offlineName;
	}

	public void setOfflineName(String offlineName) {
		this.offlineName = offlineName;
	}
}