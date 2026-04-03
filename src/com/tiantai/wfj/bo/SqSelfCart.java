package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 社区订单购物车
 * @author mz
 *
 */
public class SqSelfCart implements BaseBean{

	private String sqKey;
	private String sqId;
	private String comID;//POS机器维护的公司
	private String posNum;// 收银机编号//机器代码
	private String pid;// 商品ID
	private String companyId;// 商品对应的公司ID
	private String itemNum;// 商品的数量
	private String sendNum;//需要配送的数量
	private String showNum;//显示数量
	private String barCode;//条码
	private String organazitionID;//社区管辖部门也就是哪个社区
	private String stardard;//规格
	private String sgrId;//扫码ID
	private String relateID;//关联ID
	private String hgCode;//货柜编号
	private Date addDate;//加入时间
	private String deptId;//库房ID
	private String deptName;//库房名称
	public String getSqKey() {
		return sqKey;
	}

	public void setSqKey(String sqKey) {
		this.sqKey = sqKey;
	}

	public String getSqId() {
		return sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	public String getComID() {
		return comID;
	}

	public void setComID(String comID) {
		this.comID = comID;
	}

	public String getPosNum() {
		return posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}


	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getOrganazitionID() {
		return organazitionID;
	}

	public void setOrganazitionID(String organazitionID) {
		this.organazitionID = organazitionID;
	}

	public String getStardard() {
		return stardard;
	}

	public void setStardard(String stardard) {
		this.stardard = stardard;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public String getSgrId() {
		return sgrId;
	}

	public void setSgrId(String sgrId) {
		this.sgrId = sgrId;
	}

	public String getRelateID() {
		return relateID;
	}

	public void setRelateID(String relateID) {
		this.relateID = relateID;
	}

	public String getHgCode() {
		return hgCode;
	}

	public void setHgCode(String hgCode) {
		this.hgCode = hgCode;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
