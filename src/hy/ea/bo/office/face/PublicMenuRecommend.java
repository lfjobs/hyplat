package hy.ea.bo.office.face;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 一级菜单管理
 * @author IT
 *
 */
public class PublicMenuRecommend implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String recommendKey;
	private String recommendId;
	private String menuId;
	private String staffId;
	private Date updateTime;

	public String getRecommendKey() {
		return recommendKey;
	}

	public void setRecommendKey(String recommendKey) {
		this.recommendKey = recommendKey;
	}

	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
