
package hy.plat.bo;

/**
 * System Role EA Menu Interface
 */
public class SREMI implements BaseBean{
	private String remiKey;
	private String remiID;
	private String roleID;
	private String eaID;
	private String menuID;
	private String interfaceID;
	private String interfaceUrl;
	private String interfaceStatus;
	public String getRemiKey() {
		return remiKey;
	}
	public void setRemiKey(String remiKey) {
		this.remiKey = remiKey;
	}
	public String getRemiID() {
		return remiID;
	}
	public void setRemiID(String remiID) {
		this.remiID = remiID;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getEaID() {
		return eaID;
	}
	public void setEaID(String eaID) {
		this.eaID = eaID;
	}
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
	public String getInterfaceID() {
		return interfaceID;
	}
	public void setInterfaceID(String interfaceID) {
		this.interfaceID = interfaceID;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
}
