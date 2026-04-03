package hy.ea.bo.invoicing.voucher;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 *结账
 * 陈婷
 */
public class endAccount implements BaseBean{
	private String endaKey;        		//账号主键
	private String endaID;         		//账号业务主键
	private String companyID;				//公司ID
	private String companyName;				//公司名称
	private Date endTime;					//结账时间
	public String getEndaKey() {
		return endaKey;
	}
	public void setEndaKey(String endaKey) {
		this.endaKey = endaKey;
	}
	public String getEndaID() {
		return endaID;
	}
	public void setEndaID(String endaID) {
		this.endaID = endaID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
