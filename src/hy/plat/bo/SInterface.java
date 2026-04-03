
package hy.plat.bo;

import java.util.Date;
/**
 * System Interface
 */
public class SInterface implements BaseBean{
	private String interfaceKey;
	private String interfaceID;
	private String boID;
	private String interfaceName;
	private String interfaceUrl;
	private Date   interfaceCreateDate;
	private String interfaceTeam;
	/**
	 * 00:启用 01:禁用  02:入口  03:机构接口
	 */
	private String interfaceStatus;
	private String interfaceDesc;
	public String getInterfaceKey() {
		return interfaceKey;
	}
	public void setInterfaceKey(String interfaceKey) {
		this.interfaceKey = interfaceKey;
	}
	public String getInterfaceID() {
		return interfaceID;
	}
	public void setInterfaceID(String interfaceID) {
		this.interfaceID = interfaceID;
	}
	public String getBoID() {
		return boID;
	}
	public void setBoID(String boID) {
		this.boID = boID;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public Date getInterfaceCreateDate() {
		return interfaceCreateDate;
	}
	public void setInterfaceCreateDate(Date interfaceCreateDate) {
		this.interfaceCreateDate = interfaceCreateDate;
	}
	public String getInterfaceTeam() {
		return interfaceTeam;
	}
	public void setInterfaceTeam(String interfaceTeam) {
		this.interfaceTeam = interfaceTeam;
	}
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	public String getInterfaceDesc() {
		return interfaceDesc;
	}
	public void setInterfaceDesc(String interfaceDesc) {
		this.interfaceDesc = interfaceDesc;
	}
}
