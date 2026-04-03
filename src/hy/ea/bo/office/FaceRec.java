package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 人脸闸机设备
 * @author  mz
 *
 */
public class FaceRec implements BaseBean,java.io.Serializable{
	private String frkey;
	private String frId;
    private String sn;//序列号
	private String name;//设备名称，简单的描述来区分哪个设备
	private String staffID;
	private String staffName;
	private Date createDate;//创建时间
	private String state;//使用状态>0正常使用状态，1：已停用
	private String companyID;//公司ID
	private String onlineState;

	public String getFrkey() {
		return frkey;
	}

	public void setFrkey(String frkey) {
		this.frkey = frkey;
	}

	public String getFrId() {
		return frId;
	}

	public void setFrId(String frId) {
		this.frId = frId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}
}
