package hy.ea.bo.human.attence;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 考勤组
 */
public class AttenceGroup implements BaseBean, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String atgkey;
	private String atgID;
	private String attenceName;// 考勤组名称
	private String attenceType;// 00固定时间上下班 01：按排班时间上下班 02 不固定时间上下班
	private String ruleID;// 规则ID
	private String attenceDate;// 考勤时间
	private String attenceSite;// 考勤地点
	private Date createDate;// 创建时间
	private Date effectDate;// 生效日期
	private String companyID;// 公司ID
	private String orgID;// 部门ID ，如果全公司范围使用，部门ID为空
	private String staffID;// 创建人
	

	public String getAtgkey() {
		return atgkey;
	}

	public void setAtgkey(String atgkey) {
		this.atgkey = atgkey;
	}

	public String getAtgID() {
		return atgID;
	}

	public void setAtgID(String atgID) {
		this.atgID = atgID;
	}

	public String getAttenceName() {
		return attenceName;
	}

	public void setAttenceName(String attenceName) {
		this.attenceName = attenceName;
	}

	public String getAttenceType() {
		return attenceType;
	}

	public void setAttenceType(String attenceType) {
		this.attenceType = attenceType;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getAttenceDate() {
		return attenceDate;
	}

	public void setAttenceDate(String attenceDate) {
		this.attenceDate = attenceDate;
	}

	public String getAttenceSite() {
		return attenceSite;
	}

	public void setAttenceSite(String attenceSite) {
		this.attenceSite = attenceSite;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

}
