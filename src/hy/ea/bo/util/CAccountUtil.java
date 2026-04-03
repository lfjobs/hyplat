package hy.ea.bo.util;

import java.io.Serializable;

public class CAccountUtil implements Serializable{

	private String orgName; //部门
	private String opoName;	//岗位
	private String depName; //职务 
	private String cacName; //帐号名称
	private String cacstauts; //状态
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOpoName() {
		return opoName;
	}
	public void setOpoName(String opoName) {
		this.opoName = opoName;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getCacName() {
		return cacName;
	}
	public void setCacName(String cacName) {
		this.cacName = cacName;
	}
	public String getCacstauts() {
		return cacstauts;
	}
	public void setCacstauts(String cacstauts) {
		this.cacstauts = cacstauts;
	}
	
}
