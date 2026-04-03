
package hy.ea.bo;

import hy.plat.bo.BaseBean;
import hy.plat.bo.SInterface;

import java.util.List;
/**
 * Company Menu Interface
 * 用于取得给菜单分配的BO的Interface
 */
public class CBI implements BaseBean{
	private String boID;
	private String eaID;
	private String boName;
	private List<SInterface> sinterfaceList;
	
	
	public String getBoName() {
		return boName;
	}
	public void setBoName(String boName) {
		this.boName = boName;
	}
	public String getBoID() {
		return boID;
	}
	public void setBoID(String boID) {
		this.boID = boID;
	}
	public String getEaID() {
		return eaID;
	}
	public void setEaID(String eaID) {
		this.eaID = eaID;
	}
	public List<SInterface> getSinterfaceList() {
		return sinterfaceList;
	}
	public void setSinterfaceList(List<SInterface> sinterfaceList) {
		this.sinterfaceList = sinterfaceList;
	}
}
