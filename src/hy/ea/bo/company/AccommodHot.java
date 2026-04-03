package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 分配住宿
 * 
 * @author 李伟志
 * 
 */

public class AccommodHot implements BaseBean {

	private String accommodHotKey;
	private String accommodHotID;
	private String companyID;
	private String organizationID;
	private String accommodID; // 酒店外键
	private String staffID; // 住宿人员id
	private String roomDisPrice; // 折扣价
	private String roomAgrPrice; // 协议价
	private String roomNum; // 房间号id
	private String remarks;// 备注
	private String createName;// 创建人
	private String createDate;// 创建date
	private String updateName;// 最后修改人
	private String updateDate;// 最后修改date

	@SuppressWarnings("unused")
	private Date occDate; // 入住时间
	@SuppressWarnings("unused")
	private Date leaDate; // 离开时间

	
	public static String[] columnHeadings() {
		String[] titles = { "序号","酒店名称", "星级", "房间类别","入住人员", "楼层", "房间号", "标价(RMB)", "折扣价(RMB)", "协议(RMB)", "备注"};
		return titles;
	}
	
	
	
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getAccommodID() {
		return accommodID;
	}

	public void setAccommodID(String accommodID) {
		this.accommodID = accommodID;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	

	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}



	public String getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}



	public String getRoomDisPrice() {
		return roomDisPrice;
	}

	public void setRoomDisPrice(String roomDisPrice) {
		this.roomDisPrice = roomDisPrice;
	}

	public String getRoomAgrPrice() {
		return roomAgrPrice;
	}

	public void setRoomAgrPrice(String roomAgrPrice) {
		this.roomAgrPrice = roomAgrPrice;
	}

	public String getAccommodHotKey() {
		return accommodHotKey;
	}

	public void setAccommodHotKey(String accommodHotKey) {
		this.accommodHotKey = accommodHotKey;
	}

	public String getAccommodHotID() {
		return accommodHotID;
	}

	public void setAccommodHotID(String accommodHotID) {
		this.accommodHotID = accommodHotID;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

}
