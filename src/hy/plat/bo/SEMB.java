
package hy.plat.bo;

/**
 * System EA Menu BO Object
 */
public class SEMB implements BaseBean{
	private String embKey;
	private String embID;
	private String eaID;
	private String menuID;
	private String boID;
	public String getEmbKey() {
		return embKey;
	}
	public void setEmbKey(String embKey) {
		this.embKey = embKey;
	}
	public String getEmbID() {
		return embID;
	}
	public void setEmbID(String embID) {
		this.embID = embID;
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
	public String getBoID() {
		return boID;
	}
	public void setBoID(String boID) {
		this.boID = boID;
	}
}
