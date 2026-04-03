package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 安全检查任务单据
 * @author zgzg
 *
 */
public class OASafeInspectInfo implements BaseBean {
	
	private String key;//主键
	private String id;//业务主键
	private String inspectNO;//任务单号
	private String safeTypeID;//FK:检查类别序号
	private String departmentID;//FK:单据所在部门ID（organizationID）
	private String companyID;//FK:公司ID
	private String personDepartmentID;//FK:检测人员部门ID（子部门）（organizationID）
	private String staffID;//FK:责任人ID
	private String staffNumber;//责任人编号
	private String position;//责任人岗位
	private String contactcompanyID;//FK:往来单位
	private String unitRelationShip;//往来单位关系
	private String contactcompanyBankNum;//FK:往来单位银行帐号
	private String contactUserID;//FK:往来个人
	private String contactUserBankNum;//FK：往来个人银行帐号
	private String personalRelationShip;//往来个人关系
	private Date makeDate;//制单日期
	private String attachment;//附件
	/**
	 * 主键
	 * @return
	 */
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 业务主键
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 任务单号
	 * @return
	 */
	public String getInspectNO() {
		return inspectNO;
	}
	public void setInspectNO(String inspectNO) {
		this.inspectNO = inspectNO;
	}
	/**
	 * FK:检查类别序号
	 * @return
	 */
	public String getSafeTypeID() {
		return safeTypeID;
	}
	public void setSafeTypeID(String safeTypeID) {
		this.safeTypeID = safeTypeID;
	}
	/**
	 * FK:公司ID
	 * @return
	 */
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	/**
	 * FK:单据所在部门ID（organizationID）
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	/**
	 * FK:责任人ID
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 责任人编号
	 * @return
	 */
	public String getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	/**
	 * 责任人岗位
	 * @return
	 */
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * FK:往来单位
	 * @return
	 */
	public String getContactcompanyID() {
		return contactcompanyID;
	}
	public void setContactcompanyID(String contactcompanyID) {
		this.contactcompanyID = contactcompanyID;
	}
	/**
	 * 往来单位关系
	 * @return
	 */
	public String getUnitRelationShip() {
		return unitRelationShip;
	}
	public void setUnitRelationShip(String unitRelationShip) {
		this.unitRelationShip = unitRelationShip;
	}
	/**
	 * FK:往来个人
	 * @return
	 */
	public String getContactUserID() {
		return contactUserID;
	}
	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}
	/**
	 * 往来个人关系
	 * @return
	 */
	public String getPersonalRelationShip() {
		return personalRelationShip;
	}
	public void setPersonalRelationShip(String personalRelationShip) {
		this.personalRelationShip = personalRelationShip;
	}
	/**
	 * 制单日期
	 * @return
	 */
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	/**
	 * 附件
	 * @return
	 */
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	/**
	 * 往来单位银行帐号
	 * @return
	 */
	public String getContactcompanyBankNum() {
		return contactcompanyBankNum;
	}
	public void setContactcompanyBankNum(String contactcompanyBankNum) {
		this.contactcompanyBankNum = contactcompanyBankNum;
	}
	/**
	 * 往来个人银行帐号
	 * @return
	 */
	public String getContactUserBankNum() {
		return contactUserBankNum;
	}
	public void setContactUserBankNum(String contactUserBankNum) {
		this.contactUserBankNum = contactUserBankNum;
	}
	/**
	 * FK:检测人员部门ID（子部门）（organizationID）
	 * @return
	 */
	public String getPersonDepartmentID() {
		return personDepartmentID;
	}
	public void setPersonDepartmentID(String personDepartmentID) {
		this.personDepartmentID = personDepartmentID;
	}
	
	

}
