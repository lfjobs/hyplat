/**
 * Responsibilities
 */
package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * YJG
 * @author  Loglock  日志加锁
 */
public class LogLock implements BaseBean{
  private String logLockkey;
  private String logLockID;
  private String companyID;
  private String staffID;   //人员id
  private String staffName; //人员姓名
  
  private Date startDate;//开始时间
  private Date endDate;//结束时间
  private Date lockDate;//操作时间
  private String status;//状态   00:加锁    01:解锁
  
	public String getLogLockkey() {
		return logLockkey;
	}
	public void setLogLockkey(String logLockkey) {
		this.logLockkey = logLockkey;
	}
	public String getLogLockID() {
		return logLockID;
	}
	public void setLogLockID(String logLockID) {
		this.logLockID = logLockID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getLockDate() {
		return lockDate;
	}
	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}