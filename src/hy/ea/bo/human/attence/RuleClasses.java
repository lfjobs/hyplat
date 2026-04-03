package hy.ea.bo.human.attence;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 规则对应的时间班次
 */
public class RuleClasses implements BaseBean, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classkey;
	private String classID;
	private String signNum;// 每天打卡次数  2,4,6
	
	private Date startWork1;
	private Date endWork1;
	
	private Date startWork2;
	private Date endWork2;
	
	private Date startWork3;
	private Date endWork3;

	private String  className;//班次名称
	private String attenceDate;// 考勤时间
	private Date createDate;// 创建时间
	private String companyID;// 公司ID
	private String staffID;// 创建人
	public String getClasskey() {
		return classkey;
	}
	public void setClasskey(String classkey) {
		this.classkey = classkey;
	}
	public String getClassID() {
		return classID;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	public String getSignNum() {
		return signNum;
	}
	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}
	public Date getStartWork1() {
		return startWork1;
	}
	public void setStartWork1(Date startWork1) {
		this.startWork1 = startWork1;
	}
	public Date getEndWork1() {
		return endWork1;
	}
	public void setEndWork1(Date endWork1) {
		this.endWork1 = endWork1;
	}
	public Date getStartWork2() {
		return startWork2;
	}
	public void setStartWork2(Date startWork2) {
		this.startWork2 = startWork2;
	}
	public Date getEndWork2() {
		return endWork2;
	}
	public void setEndWork2(Date endWork2) {
		this.endWork2 = endWork2;
	}
	public Date getStartWork3() {
		return startWork3;
	}
	public void setStartWork3(Date startWork3) {
		this.startWork3 = startWork3;
	}
	public Date getEndWork3() {
		return endWork3;
	}
	public void setEndWork3(Date endWork3) {
		this.endWork3 = endWork3;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAttenceDate() {
		return attenceDate;
	}
	public void setAttenceDate(String attenceDate) {
		this.attenceDate = attenceDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	


	
}
