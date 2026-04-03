package hy.ea.bo.finance.InvestDeviceBind;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 投资设备绑定表
 */
public class DeviceBind implements BaseBean{
	private String dbKey;			//逻辑主键
	private String dbId;			//业务主键
	private String dbcarNumber;		//车牌号
	private String dbGoodsId;		//货物外键(dtgoodsmanage)
	private String dbCarId;			//车辆外键(dt_Car_CarInformation)
	private String dbStaffId;		//投资人员外键(dt_hr_Staff)
	private String dbSccId;			//投资人微分金账号外键(t_Eshop_Cuscom)
	private Date dbDate ;			//添加时间
	private Date dbModifyDate;		//修改时间
	private String dbStatus	;		//停用启用状态1.启用，0.停用
	private String dbtzType;		//投资类型 01.教练车，02.单车
	
	public String getDbKey() {
		return dbKey;
	}
	public void setDbKey(String dbKey) {
		this.dbKey = dbKey;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getDbcarNumber() {
		return dbcarNumber;
	}
	public void setDbcarNumber(String dbcarNumber) {
		this.dbcarNumber = dbcarNumber;
	}
	public String getDbGoodsId() {
		return dbGoodsId;
	}
	public void setDbGoodsId(String dbGoodsId) {
		this.dbGoodsId = dbGoodsId;
	}
	public String getDbCarId() {
		return dbCarId;
	}
	public void setDbCarId(String dbCarId) {
		this.dbCarId = dbCarId;
	}
	public String getDbStaffId() {
		return dbStaffId;
	}
	public void setDbStaffId(String dbStaffId) {
		this.dbStaffId = dbStaffId;
	}
	public String getDbSccId() {
		return dbSccId;
	}
	public void setDbSccId(String dbSccId) {
		this.dbSccId = dbSccId;
	}
	public Date getDbDate() {
		return dbDate;
	}
	public void setDbDate(Date dbDate) {
		this.dbDate = dbDate;
	}
	public Date getDbModifyDate() {
		return dbModifyDate;
	}
	public void setDbModifyDate(Date dbModifyDate) {
		this.dbModifyDate = dbModifyDate;
	}
	public String getDbStatus() {
		return dbStatus;
	}
	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
	}
	public String getDbtzType() {
		return dbtzType;
	}
	public void setDbtzType(String dbtzType) {
		this.dbtzType = dbtzType;
	}
}
