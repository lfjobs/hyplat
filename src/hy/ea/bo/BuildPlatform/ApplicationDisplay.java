package hy.ea.bo.BuildPlatform;

import hy.plat.bo.BaseBean;
/**
 *快捷应用显示权限 
 */
public class ApplicationDisplay implements BaseBean {
	private String appDisKey;
	private String appDisId;
	private String staffId;
	private String ppId;
	public String getAppDisKey() {
		return appDisKey;
	}
	public void setAppDisKey(String appDisKey) {
		this.appDisKey = appDisKey;
	}
	public String getAppDisId() {
		return appDisId;
	}
	public void setAppDisId(String appDisId) {
		this.appDisId = appDisId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
}
