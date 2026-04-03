package hy.ea.bo.BuildPlatform;

import hy.plat.bo.BaseBean;

/**
 * 人员管理 公司与staff关联
 */
public class TeamBuild implements BaseBean {
	private String teamBuildKey;
	private String teamBuildId;
	private String staffId;
	private String companyId;
	public String getTeamBuildKey() {
		return teamBuildKey;
	}
	public void setTeamBuildKey(String teamBuildKey) {
		this.teamBuildKey = teamBuildKey;
	}
	public String getTeamBuildId() {
		return teamBuildId;
	}
	public void setTeamBuildId(String teamBuildId) {
		this.teamBuildId = teamBuildId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
