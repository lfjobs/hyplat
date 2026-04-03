package hy.ea.bo.finance.InvestDeviceBind;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 投资设备绑定业务员表
 */
public class DeviceBindStaff implements BaseBean{
	private String dbsKey;			//逻辑主键
	private String dbsId;			//业务主键
	private String dbsDbId;			//设备绑定表外键(dt_DeviceBind)
	private String dbsStaffId;		//关联业务员人员外键(dt_hr_Staff)
	private String dbsSccId;		//关联业务员微分金账号外键(t_Eshop_Cuscom)
	private Date dbsDate ;			//添加时间
	private String dbsStatus	;	//停用启用状态1.启用，01.停用1，02.停用2(由于停用deviceBind表的某跳数据停用)
	
	public String getDbsKey() {
		return dbsKey;
	}
	public void setDbsKey(String dbsKey) {
		this.dbsKey = dbsKey;
	}
	public String getDbsId() {
		return dbsId;
	}
	public void setDbsId(String dbsId) {
		this.dbsId = dbsId;
	}
	public String getDbsDbId() {
		return dbsDbId;
	}
	public void setDbsDbId(String dbsDbId) {
		this.dbsDbId = dbsDbId;
	}
	public String getDbsStaffId() {
		return dbsStaffId;
	}
	public void setDbsStaffId(String dbsStaffId) {
		this.dbsStaffId = dbsStaffId;
	}
	public String getDbsSccId() {
		return dbsSccId;
	}
	public void setDbsSccId(String dbsSccId) {
		this.dbsSccId = dbsSccId;
	}
	
	public Date getDbsDate() {
		return dbsDate;
	}
	public void setDbsDate(Date dbsDate) {
		this.dbsDate = dbsDate;
	}
	public String getDbsStatus() {
		return dbsStatus;
	}
	public void setDbsStatus(String dbsStatus) {
		this.dbsStatus = dbsStatus;
	}
	
}
