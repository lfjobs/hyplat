package hy.ea.bo.production.drive;


import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 驾校通知信息
 * 
 * @author xgb
 *
 */
public class CarSchoolInform implements BaseBean {
	
	private String csiKey;
	private String csiId;
	private String ppId;//产品Id
	private String companyId;//公司Id
	private String staffid;//用户Id
	private String state;//状态"00"为未读,"01"为已读
	public String getCsiKey() {
		return csiKey;
	}
	public void setCsiKey(String csiKey) {
		this.csiKey = csiKey;
	}
	public String getCsiId() {
		return csiId;
	}
	public void setCsiId(String csiId) {
		this.csiId = csiId;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}