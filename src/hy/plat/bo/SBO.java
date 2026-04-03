
package hy.plat.bo;

import java.util.Date;
/**
 * System Business Object
 */
public class SBO implements BaseBean{
	private String boKey;
	private String boID;
	private String eaID;
	private String boName;
	private Date   boCreateDate;
	private Date   boUpdateDate;
	private String boTeam;
	/**
	 * 00:正常  01：升级  02：停用
	 */
	private String boStatus;
	private String boDesc;
	
	public String getBoKey() {
		return boKey;
	}
	public void setBoKey(String boKey) {
		this.boKey = boKey;
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
	public String getBoName() {
		return boName;
	}
	public void setBoName(String boName) {
		this.boName = boName;
	}
	public Date getBoCreateDate() {
		return boCreateDate;
	}
	public void setBoCreateDate(Date boCreateDate) {
		this.boCreateDate = boCreateDate;
	}
	public Date getBoUpdateDate() {
		return boUpdateDate;
	}
	public void setBoUpdateDate(Date boUpdateDate) {
		this.boUpdateDate = boUpdateDate;
	}
	public String getBoTeam() {
		return boTeam;
	}
	public void setBoTeam(String boTeam) {
		this.boTeam = boTeam;
	}
	public String getBoStatus() {
		return boStatus;
	}
	public void setBoStatus(String boStatus) {
		this.boStatus = boStatus;
	}
	public String getBoDesc() {
		return boDesc;
	}
	public void setBoDesc(String boDesc) {
		this.boDesc = boDesc;
	}
}
