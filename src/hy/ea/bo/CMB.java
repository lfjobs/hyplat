
package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.util.List;
/**
 * Company Menu Interface
 * 用于取得给菜单分配的BO的Interface
 */
public class CMB implements BaseBean{
	private String menuID;
	private String eaID;
	private String menuName;
	private List<CBI> CbiList;
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
	public List<CBI> getCbiList() {
		return CbiList;
	}
	public void setCbiList(List<CBI> cbiList) {
		CbiList = cbiList;
	}
	
}
