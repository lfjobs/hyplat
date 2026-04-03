package hy.ea.bo.office.vo;

import hy.plat.bo.BaseBean;

/**
 * 根据酒店名称查询类型视图
 * 
 * @author 李伟志
 * 
 */
public class RoomTypeVO implements BaseBean {

	private String starsName; //星级名称
	private String stars;//星级id
	private String codeValue;	// 房间类别
	private String roomType;	// 房间类别id

	public String getStarsName() {
		return starsName;
	}

	public void setStarsName(String starsName) {
		this.starsName = starsName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

}
