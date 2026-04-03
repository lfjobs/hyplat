package hy.ea.bo.production.drive;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 驾校学员扩展信息
 * 
 * @author mz
 *
 */
public class StudentInfo implements BaseBean {

	private String siKey;
	private String siId;
	private String oriEnroll;// 报名来源
	private Date dateEnroll;// 报名时间
	private String oriDriveLic;// 现有驾照
	private String ispartyMem;// 是否党员
	private String islocal;// 是否本地
	private String isDriveLic;// 已领驾驶证
	private String iscerComple;// 已领结业证
    private String staffID;//人员ID

	public String getSiKey() {
		return siKey;
	}

	public void setSiKey(String siKey) {
		this.siKey = siKey;
	}

	public String getSiId() {
		return siId;
	}

	public void setSiId(String siId) {
		this.siId = siId;
	}

	public String getOriEnroll() {
		return oriEnroll;
	}

	public void setOriEnroll(String oriEnroll) {
		this.oriEnroll = oriEnroll;
	}

	public Date getDateEnroll() {
		return dateEnroll;
	}

	public void setDateEnroll(Date dateEnroll) {
		this.dateEnroll = dateEnroll;
	}

	public String getOriDriveLic() {
		return oriDriveLic;
	}

	public void setOriDriveLic(String oriDriveLic) {
		this.oriDriveLic = oriDriveLic;
	}

	public String getIspartyMem() {
		return ispartyMem;
	}

	public void setIspartyMem(String ispartyMem) {
		this.ispartyMem = ispartyMem;
	}

	public String getIslocal() {
		return islocal;
	}

	public void setIslocal(String islocal) {
		this.islocal = islocal;
	}

	public String getIsDriveLic() {
		return isDriveLic;
	}

	public void setIsDriveLic(String isDriveLic) {
		this.isDriveLic = isDriveLic;
	}

	public String getIscerComple() {
		return iscerComple;
	}

	public void setIscerComple(String iscerComple) {
		this.iscerComple = iscerComple;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
    
}