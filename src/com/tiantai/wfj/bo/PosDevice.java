package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 收银机或者社区终端设备
 */
public class PosDevice implements BaseBean,Serializable{

    private String posKey;
    private String posID;
    private String posNum;//pos唯一编号
	private String posName;//pos的名称；01:互视达销售终端机，02:红外线手持机，03：非红外线手持机，04：平板电脑。
	private String staffID;//负责人
	private String staffName;//负责人姓名
	private String organizationID;//部门或者负责的社区
	private String organizationName;
	private String comID;//负责配送的超市的ID
	private String comName;//负责配送的超市Name
	private Date  createDate;//添加日期
    private String inputID;//录入人员
	private String inputName;//录入人员姓名
	private String state;//0正常使用状态，1：已停用，2，已收回。
	private String accessCcomID;//哪个超市作为入口 会变动
	private String accessCcomName;//哪个超市作为入口 会变动
	private String fhform;//发货形式：0全部商家送货 1商家送货+现场拿货 2 仅现场拿货
	private String app;//空 不指定 01 考场练车 02 停车收费
	//停车收费的时候使用
	private String siteId;//绑定场地  
	private  String equipmentNumber;//设备号
    //智能货柜使用
	private String hgcode;//货柜编号

	public String getPosKey() {
		return posKey;
	}

	public void setPosKey(String posKey) {
		this.posKey = posKey;
	}

	public String getPosID() {
		return posID;
	}

	public void setPosID(String posID) {
		this.posID = posID;
	}

	public String getPosNum() {
		return posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getComID() {
		return comID;
	}

	public void setComID(String comID) {
		this.comID = comID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getInputID() {
		return inputID;
	}

	public void setInputID(String inputID) {
		this.inputID = inputID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getAccessCcomID() {
		return accessCcomID;
	}

	public void setAccessCcomID(String accessCcomID) {
		this.accessCcomID = accessCcomID;
	}

	public String getFhform() {
		return fhform;
	}

	public void setFhform(String fhform) {
		this.fhform = fhform;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}


	public String getAccessCcomName() {
		return accessCcomName;
	}

	public void setAccessCcomName(String accessCcomName) {
		this.accessCcomName = accessCcomName;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}


	public String getHgcode() {
		return hgcode;
	}

	public void setHgcode(String hgcode) {
		this.hgcode = hgcode;
	}
}
