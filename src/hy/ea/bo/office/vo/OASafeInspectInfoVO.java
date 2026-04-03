package hy.ea.bo.office.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 安全检查任务单据视图
 * @author zgzg
 *
 */
public class OASafeInspectInfoVO implements BaseBean,java.io.Serializable {
	
	private String inspectID;//FK：安全检测单据ID
	private String departmentID;//FK:单据所在部门ID
	private String tsicompanyID;//FK:检测公司ID
	private String tsiDeptID;//FK:检测部门ID
	private String tsiPersonID;//FK:检测人ID
	private String exunitID;//FK:被检测单位
	private String expersonID;//FK:被检测个人ID
	private String inspectTypeID;//FK:安全检测类别ID
	
	private String inspectKey;//主键
	private String inspectNO;//凭证号
	private String typeName;//安全类别
	private Date makeDate;//制单日期
	private String attachment;//附件
	
	
	private String tsiCompany;//检测公司
	private String tsiDept;//检测部门
	private String tsiPerson;//检测人
	private String tsiPersonCode;//检测人档案号
	private String tsiPersonPost;//检测人岗位
	
	
	private String exUnitName;//往来单位
	private String exUnitTel;//往来单位电话
	private String exUnitPerson;//单位责任人
	private String exUnitPersonTel;//单位人电话
	private String exUnitAddr;//单位地址
	private String exUnitIndustry;//单位行业类别
	private String exUnitBanknum;//单位银行帐号
	private String exUnitRelation;//单位关系
	
	private String exPerson;//往来个人
	private String exPersonTel;//往来个人电话
	private String exPersonIdcard;//个人身份证
	private String exPersonQQ;//个人qq
	private String exPersonaddr;//个人地址
	private String exPersonBanknum;//个人银行帐号
	private String exPersonRelation;//个人往来关系
	
	
	/**
	 * FK：安全检测单据ID
	 * @return
	 */
	public String getInspectID() {
		return inspectID;
	}
	public void setInspectID(String inspectID) {
		this.inspectID = inspectID;
	}
	/**
	 * FK:检测公司ID
	 * @return
	 */
	public String getTsicompanyID() {
		return tsicompanyID;
	}
	public void setTsicompanyID(String tsicompanyID) {
		this.tsicompanyID = tsicompanyID;
	}
	/**
	 * FK:检测部门ID
	 * @return
	 */
	public String getTsiDeptID() {
		return tsiDeptID;
	}
	public void setTsiDeptID(String tsiDeptID) {
		this.tsiDeptID = tsiDeptID;
	}
	/**
	 * FK:检测人ID
	 * @return
	 */
	public String getTsiPersonID() {
		return tsiPersonID;
	}
	public void setTsiPersonID(String tsiPersonID) {
		this.tsiPersonID = tsiPersonID;
	}
	/**
	 * FK:被检测单位
	 * @return
	 */
	public String getExunitID() {
		return exunitID;
	}
	public void setExunitID(String exunitID) {
		this.exunitID = exunitID;
	}
	/**
	 * FK:被检测个人ID
	 * @return
	 */
	public String getExpersonID() {
		return expersonID;
	}
	public void setExpersonID(String expersonID) {
		this.expersonID = expersonID;
	}
	/**
	 * FK:安全检测类别ID
	 * @return
	 */
	public String getInspectTypeID() {
		return inspectTypeID;
	}
	public void setInspectTypeID(String inspectTypeID) {
		this.inspectTypeID = inspectTypeID;
	}
	/**
	 * 主键
	 * @return
	 */
	public String getInspectKey() {
		return inspectKey;
	}
	public void setInspectKey(String inspectKey) {
		this.inspectKey = inspectKey;
	}
	/**
	 * 凭证号
	 * @return
	 */
	public String getInspectNO() {
		return inspectNO;
	}
	public void setInspectNO(String inspectNO) {
		this.inspectNO = inspectNO;
	}
	/**
	 * 安全类别
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * 检测公司
	 * @return
	 */
	public String getTsiCompany() {
		return tsiCompany;
	}
	public void setTsiCompany(String tsiCompany) {
		this.tsiCompany = tsiCompany;
	}
	/**
	 * 检测部门
	 * @return
	 */
	public String getTsiDept() {
		return tsiDept;
	}
	public void setTsiDept(String tsiDept) {
		this.tsiDept = tsiDept;
	}
	/**
	 * 检测人
	 * @return
	 */
	public String getTsiPerson() {
		return tsiPerson;
	}
	public void setTsiPerson(String tsiPerson) {
		this.tsiPerson = tsiPerson;
	}
	/**
	 * 检测人档案号
	 * @return
	 */
	public String getTsiPersonCode() {
		return tsiPersonCode;
	}
	public void setTsiPersonCode(String tsiPersonCode) {
		this.tsiPersonCode = tsiPersonCode;
	}
	/**
	 * 检测人岗位
	 * @return
	 */
	public String getTsiPersonPost() {
		return tsiPersonPost;
	}
	public void setTsiPersonPost(String tsiPersonPost) {
		this.tsiPersonPost = tsiPersonPost;
	}
	/**
	 * 往来单位
	 * @return
	 */
	public String getExUnitName() {
		return exUnitName;
	}
	public void setExUnitName(String exUnitName) {
		this.exUnitName = exUnitName;
	}
	/**
	 * 往来单位电话
	 * @return
	 */
	public String getExUnitTel() {
		return exUnitTel;
	}
	public void setExUnitTel(String exUnitTel) {
		this.exUnitTel = exUnitTel;
	}
	/**
	 * 单位责任人
	 * @return
	 */
	public String getExUnitPerson() {
		return exUnitPerson;
	}
	public void setExUnitPerson(String exUnitPerson) {
		this.exUnitPerson = exUnitPerson;
	}
	/**
	 * 单位人电话
	 * @return
	 */
	public String getExUnitPersonTel() {
		return exUnitPersonTel;
	}
	public void setExUnitPersonTel(String exUnitPersonTel) {
		this.exUnitPersonTel = exUnitPersonTel;
	}
	/**
	 * 单位地址
	 * @return
	 */
	public String getExUnitAddr() {
		return exUnitAddr;
	}
	public void setExUnitAddr(String exUnitAddr) {
		this.exUnitAddr = exUnitAddr;
	}
	/**
	 * 单位行业类别
	 * @return
	 */
	public String getExUnitIndustry() {
		return exUnitIndustry;
	}
	public void setExUnitIndustry(String exUnitIndustry) {
		this.exUnitIndustry = exUnitIndustry;
	}
	/**
	 * 单位银行帐号
	 * @return
	 */
	public String getExUnitBanknum() {
		return exUnitBanknum;
	}
	public void setExUnitBanknum(String exUnitBanknum) {
		this.exUnitBanknum = exUnitBanknum;
	}
	/**
	 * 单位关系
	 * @return
	 */
	public String getExUnitRelation() {
		return exUnitRelation;
	}
	public void setExUnitRelation(String exUnitRelation) {
		this.exUnitRelation = exUnitRelation;
	}
	/**
	 * 往来个人
	 * @return
	 */
	public String getExPerson() {
		return exPerson;
	}
	public void setExPerson(String exPerson) {
		this.exPerson = exPerson;
	}
	/**
	 * 往来个人电话
	 * @return
	 */
	public String getExPersonTel() {
		return exPersonTel;
	}
	public void setExPersonTel(String exPersonTel) {
		this.exPersonTel = exPersonTel;
	}
	/**
	 * 个人身份证
	 * @return
	 */
	public String getExPersonIdcard() {
		return exPersonIdcard;
	}
	public void setExPersonIdcard(String exPersonIdcard) {
		this.exPersonIdcard = exPersonIdcard;
	}
	/**
	 * 个人qq
	 * @return
	 */
	public String getExPersonQQ() {
		return exPersonQQ;
	}
	public void setExPersonQQ(String exPersonQQ) {
		this.exPersonQQ = exPersonQQ;
	}
	/**
	 * 个人地址
	 * @return
	 */
	public String getExPersonaddr() {
		return exPersonaddr;
	}
	public void setExPersonaddr(String exPersonaddr) {
		this.exPersonaddr = exPersonaddr;
	}
	/**
	 * 个人银行帐号
	 * @return
	 */
	public String getExPersonBanknum() {
		return exPersonBanknum;
	}
	public void setExPersonBanknum(String exPersonBanknum) {
		this.exPersonBanknum = exPersonBanknum;
	}
	/**
	 * 个人往来关系
	 * @return
	 */
	public String getExPersonRelation() {
		return exPersonRelation;
	}
	public void setExPersonRelation(String exPersonRelation) {
		this.exPersonRelation = exPersonRelation;
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
	 * FK:单据所在部门ID
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	
}
