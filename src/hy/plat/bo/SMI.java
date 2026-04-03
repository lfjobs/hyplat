
package hy.plat.bo;

import java.util.List;
/**
 * System Menu Interface
 * 用于取得给菜单分配的BO的Interface
 */
public class SMI implements BaseBean{
	private String menuID;
	private String eaID;
	private String menuName;
	private List<SInterface> sinterfaceList;
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
	public String getEaID() {
		return eaID;
	}
	public void setEaID(String eaID) {
		this.eaID = eaID;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public List<SInterface> getSinterfaceList() {
		return sinterfaceList;
	}
	public void setSinterfaceList(List<SInterface> sinterfaceList) {
		this.sinterfaceList = sinterfaceList;
	}
}
