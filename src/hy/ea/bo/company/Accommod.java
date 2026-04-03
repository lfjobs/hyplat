package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.List;

/**
 * 
 * 住宿管理
 * 
 * @author 李伟志
 * 
 */

public class Accommod implements BaseBean {

	private String accommodKey;
	private String accommodID;
	private String companyID;
	private String organizationID;
	private String hotelName; // 酒店名称
	private String stars; // 星级
	private String roomType; // 房间类别
	private String roomPrice;// 标价 RMB 101-00_102-01
	
	private String roomDisPrice ; //折扣价
	private String roomAgrPrice ; // 协议价
	private String floor; //楼层
	private String bedNum;//床位总数
	private String bedOccNum ;//住人床位数
	
	private String remarks;// 备注
	private String createName;// 创建人
	private String createDate;// 创建date
	private String updateName;// 最后修改人
	private String updateDate;// 最后修改date
	private String roomN;

	private List<String> roomNumList; // 房间数组集合

	
	public String getRoomN() {
		return roomN;
	}

	public void setRoomN(String roomN) {
		this.roomN = roomN;
	}

	public String getAccommodKey() {
		return accommodKey;
	}

	public void setAccommodKey(String accommodKey) {
		this.accommodKey = accommodKey;
	}

	public String getAccommodID() {
		return accommodID;
	}

	public void setAccommodID(String accommodID) {
		this.accommodID = accommodID;
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

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
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

	public List<String> getRoomNumList() {
		return roomNumList;
	}

	public void setRoomNumList(List<String> roomNumList) {
		this.roomNumList = roomNumList;
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

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getBedOccNum() {
		return bedOccNum;
	}

	public void setBedOccNum(String bedOccNum) {
		this.bedOccNum = bedOccNum;
	}

	
}
