package hy.ea.bo.human.salaryNew;


import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


/**
 * 薪资结构单元
 * @author mz
 *
 */
public class SalaryUnits implements BaseBean, Serializable {
	private String sukey;
	private String suID;
	private String companyID;//会复制预设部分到公司
	private String unitName;//单元名称
	private String status;//单元状态 00 正常 01 作废  99 删除

	private String seq;//报表排列顺序
	private Date createDate;//创建时间
	private Date updateDate;//修改时间
	private String updateID;//修改人
	private String staffID;//创建人
	private String realName;//真实姓名必须认证过的才可以接触到工资
	private String isPreset;//是否是预设的，00预设  01 自定义
	private String parentslID;//父级别
	private String sorterID;//排序人
	private Date sortDate;//排序时间
	private String delerID;//删除人ID
	private Date delDate;//删除时间
	public String getSukey() {
		return sukey;
	}

	public void setSukey(String sukey) {
		this.sukey = sukey;
	}

	public String getSuID() {
		return suID;
	}

	public void setSuID(String suID) {
		this.suID = suID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIsPreset() {
		return isPreset;
	}

	public void setIsPreset(String isPreset) {
		this.isPreset = isPreset;
	}

	public String getParentslID() {
		return parentslID;
	}

	public void setParentslID(String parentslID) {
		this.parentslID = parentslID;
	}

	public Date getSortDate() {
		return sortDate;
	}

	public void setSortDate(Date sortDate) {
		this.sortDate = sortDate;
	}

	public String getSorterID() {
		return sorterID;
	}

	public void setSorterID(String sorterID) {
		this.sorterID = sorterID;
	}

	public String getDelerID() {
		return delerID;
	}

	public void setDelerID(String delerID) {
		this.delerID = delerID;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getUpdateID() {
		return updateID;
	}

	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
