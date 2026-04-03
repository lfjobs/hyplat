package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 印章使用日志管理
 * @author Administrator
 *
 */
public class ModifyStampPswLog implements BaseBean{
	private String key;
	private String pswLogId;
	private Date modifyTime;
	private String modifyUser;
	private String modifyUserName;
	private String enterpriseStampID;//印章ID
	private String newPsw;
	public String getNewPsw() {
		return newPsw;
	}
	public void setNewPsw(String newPsw) {
		this.newPsw = newPsw;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPswLogId() {
		return pswLogId;
	}
	public void setPswLogId(String pswLogId) {
		this.pswLogId = pswLogId;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getEnterpriseStampID() {
		return enterpriseStampID;
	}
	public void setEnterpriseStampID(String enterpriseStampID) {
		this.enterpriseStampID = enterpriseStampID;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	
	
	
}
