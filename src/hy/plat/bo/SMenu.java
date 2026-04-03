
package hy.plat.bo;

/**
 * System Menu
 */
public class SMenu implements BaseBean{
	private String menuKey;
	private String menuID;
	private String eaID;
	private String menuName;
	private String menuNumber;
	private String menuDesc;
	public String getMenuKey() {
		return menuKey;
	}
	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}
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
	public String getMenuNumber() {
		return menuNumber;
	}
	public void setMenuNumber(String menuNumber) {
		this.menuNumber = menuNumber;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
}
