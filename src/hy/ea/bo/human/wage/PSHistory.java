package hy.ea.bo.human.wage;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 员工工资级别变更记录表
 * @author Administrator
 *
 */
public class PSHistory implements BaseBean{
	
	private String pshKey;			//主键
	private String pshID;			//逻辑主键
	private String payScaleID;		//级别外键
	private String companyID;       //公司ID
	private String staffID;			//员工ID
	private Date applyDate;		//录入时间(自动生成)
	private Date effectiveDate;	//级别生效日期
	private String status;			//状态	'01'正在使用  '02'原级别历史记录  '03'待生效级别
	
	public PSHistory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 主键
	 */
	public String getPshKey() {
		return pshKey;
	}
	/**
	 * 主键
	 */
	public void setPshKey(String pshKey) {
		this.pshKey = pshKey;
	}
	
	/**
	 * 逻辑主键
	 */
	public String getPshID() {
		return pshID;
	}
	
	/**
	 * 逻辑主键
	 */
	public void setPshID(String pshID) {
		this.pshID = pshID;
	}
	
	/**
	 * 级别外键
	 */
	public String getPayScaleID() {
		return payScaleID;
	}
	/**
	 * 级别外键
	 */
	public void setPayScaleID(String payScaleID) {
		this.payScaleID = payScaleID;
	}
	
	/**
	 * 公司ID
	 */
	public String getCompanyID() {
		return companyID;
	}
	
	/**
	 * 公司ID
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
	 * 员工ID	
	 */
	public String getStaffID() {
		return staffID;
	}
	
	/**
	 * 员工ID	
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	

	/**
	 * 级别生效日期
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	/**
	 * 级别生效日期
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	/**
	 * 录入时间
	 */
	public Date getApplyDate() {
		return applyDate;
	}
	
	/**
	 * 录入时间
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 状态	'01'正在使用  '02'原级别历史记录
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 状态	'01'正在使用  '02'原级别历史记录
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
