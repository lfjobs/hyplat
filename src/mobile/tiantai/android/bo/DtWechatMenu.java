package mobile.tiantai.android.bo;

import java.util.Date;

import javax.management.loading.PrivateClassLoader;

import hy.plat.bo.BaseBean;
/**
 * 
 * 微信活动表
 * @author 
 */

public class DtWechatMenu implements BaseBean{
	
	 private String menuKey;
	 private String menuId;  //菜单id
	 private String menuName; //菜单名称
	 private String menuPid;  //父菜单
	 private String content;  //菜单内容
	 private Date createDate; //创建日期
	 private String companyId;//公司id
	 private String staffId; //添加人员
	 private String image;   //菜单图标
	 private String topmenu; //头部大菜单
	public String getMenuKey() {
		return menuKey;
	}
	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuPid() {
		return menuPid;
	}
	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTopmenu() {
		return topmenu;
	}
	public void setTopmenu(String topmenu) {
		this.topmenu = topmenu;
	}
	
}