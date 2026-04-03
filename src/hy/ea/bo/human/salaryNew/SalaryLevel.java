package hy.ea.bo.human.salaryNew;


import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


/**
 * 薪资级别
 * @author mz
 *
 */
public class SalaryLevel implements BaseBean, Serializable {
	private String slkey;
	private String slID;
	private String num;//同一条级别相同的编号
	private String companyID;//会复制预设部分到公司
	private String stID;//项目点ID
	private String itemValue;//项目值

	private String seq;//报表排列顺序
	private Date createDate;//创建时间
	private String staffID;//创建人
	private String realName;//真实姓名必须认证过的才可以接触到工资
	private String isPreset;//是否是预设的，00预设  01 自定义

	private String parentslID;//父级别

	public String getIsPreset() {
		return isPreset;
	}

	public void setIsPreset(String isPreset) {
		this.isPreset = isPreset;
	}

	public String getSlkey() {
		return slkey;
	}

	public void setSlkey(String slkey) {
		this.slkey = slkey;
	}

	public String getSlID() {
		return slID;
	}

	public void setSlID(String slID) {
		this.slID = slID;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStID() {
		return stID;
	}

	public void setStID(String stID) {
		this.stID = stID;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
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

	public String getParentslID() {
		return parentslID;
	}

	public void setParentslID(String parentslID) {
		this.parentslID = parentslID;
	}
}
