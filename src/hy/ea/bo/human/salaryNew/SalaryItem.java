package hy.ea.bo.human.salaryNew;


import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


/**
 * 薪资结构单元下面的项目小点
 * @author mz
 *
 */
public class SalaryItem implements BaseBean, Serializable {
	private String stkey;
	private String stID;
	private String suID;//属于哪个工资单元
	private String companyID;//会复制预设部分到公司
	private String itemName;//项目点名称
	private String status;//项目点状态 00 正常 01 作废  99 删除
	private String seq;//报表排列顺序
	private Date createDate;//创建时间
	private String staffID;//创建人
	private String realName;//真实姓名必须认证过的才可以接触到工资
	private Date updateDate;//修改时间
	private String updateID;//修改人
	private String isPreset;//是否是预设的，00预设  01 自定义
	private String statisRules;//统计规则  + 还是 -  0 或者只是显示如序号和级别名称
	private String sorterID;//排序人
	private Date sortDate;//排序时间
	private String delerID;//删除人ID
	private Date delDate;//删除时间
	public String getStkey() {
		return stkey;
	}

	public void setStkey(String stkey) {
		this.stkey = stkey;
	}

	public String getStID() {
		return stID;
	}

	public void setStID(String stID) {
		this.stID = stID;
	}

	public String getSuID() {
		return suID;
	}

	public void setSuID(String suID) {
		this.suID = suID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public String getStatisRules() {
		return statisRules;
	}

	public void setStatisRules(String statisRules) {
		this.statisRules = statisRules;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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

	public String getSorterID() {
		return sorterID;
	}

	public void setSorterID(String sorterID) {
		this.sorterID = sorterID;
	}

	public Date getSortDate() {
		return sortDate;
	}

	public void setSortDate(Date sortDate) {
		this.sortDate = sortDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateID() {
		return updateID;
	}

	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}
}
