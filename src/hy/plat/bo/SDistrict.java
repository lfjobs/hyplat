
package hy.plat.bo;

/**
 * System District
 */
public class SDistrict implements BaseBean{
	private String  districtKey;
	private String  districtID;
	private String 	districtPID;
	private String 	districtCode;
	private String 	districtName;
	private String  districtStatus;
	public SDistrict() {
		super();
	}
	public SDistrict(String districtKey, String districtID, String districtPID,
			String districtCode, String districtName, String districtStatus) {
		super();
		this.districtKey = districtKey;
		this.districtID = districtID;
		this.districtPID = districtPID;
		this.districtCode = districtCode;
		this.districtName = districtName;
		this.districtStatus = districtStatus;
	}
	public String getDistrictKey() {
		return districtKey;
	}
	public void setDistrictKey(String districtKey) {
		this.districtKey = districtKey;
	}
	public String getDistrictID() {
		return districtID;
	}
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	public String getDistrictPID() {
		return districtPID;
	}
	public void setDistrictPID(String districtPID) {
		this.districtPID = districtPID;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDistrictStatus() {
		return districtStatus;
	}
	public void setDistrictStatus(String districtStatus) {
		this.districtStatus = districtStatus;
	}
}
