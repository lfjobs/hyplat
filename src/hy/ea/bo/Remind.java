package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 提醒功能
 * @author lou
 *
 */
public class Remind implements BaseBean,Cloneable,java.io.Serializable {
	private String remindKey;
	private String remindID;
	private String companyID;		  //公司id
	private String organizationID;    //部门id
	private String circularType;      //通知类型  '01'待阅  '02'待办
	private String circularTitle;     //通知标题
	private String circularText;      //通知内容
	private String urlType;           //访问链接类型
	private String detailedurl;       //访问连接
	private String staffID;           //接收人ID
	private String staffName;         //接收人name
	private Date receiveDate;         //接收时间
	private String remindType;        //提醒方式   '01'页面弹框  '02'电脑客户端  '03'手机客户端提醒
	private String remindStatus;      //提醒状态   '01'未发送  '02'已发送  '03'发送失败
	private Date addDate;           //添加时间
	
	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneRemind() throws CloneNotSupportedException{
		
		return this.clone();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addDate == null) ? 0 : addDate.hashCode());
		result = prime * result
				+ ((circularText == null) ? 0 : circularText.hashCode());
		result = prime * result
				+ ((circularTitle == null) ? 0 : circularTitle.hashCode());
		result = prime * result
				+ ((circularType == null) ? 0 : circularType.hashCode());
		result = prime * result
				+ ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result
				+ ((detailedurl == null) ? 0 : detailedurl.hashCode());
		result = prime * result
				+ ((organizationID == null) ? 0 : organizationID.hashCode());
		result = prime * result
				+ ((receiveDate == null) ? 0 : receiveDate.hashCode());
		result = prime * result
				+ ((remindID == null) ? 0 : remindID.hashCode());
		result = prime * result
				+ ((remindKey == null) ? 0 : remindKey.hashCode());
		result = prime * result
				+ ((remindStatus == null) ? 0 : remindStatus.hashCode());
		result = prime * result
				+ ((remindType == null) ? 0 : remindType.hashCode());
		result = prime * result + ((staffID == null) ? 0 : staffID.hashCode());
		result = prime * result
				+ ((staffName == null) ? 0 : staffName.hashCode());
		result = prime * result + ((urlType == null) ? 0 : urlType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Remind other = (Remind) obj;
		if (addDate == null) {
			if (other.addDate != null)
				return false;
		} else if (!addDate.equals(other.addDate))
			return false;
		if (circularText == null) {
			if (other.circularText != null)
				return false;
		} else if (!circularText.equals(other.circularText))
			return false;
		if (circularTitle == null) {
			if (other.circularTitle != null)
				return false;
		} else if (!circularTitle.equals(other.circularTitle))
			return false;
		if (circularType == null) {
			if (other.circularType != null)
				return false;
		} else if (!circularType.equals(other.circularType))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (detailedurl == null) {
			if (other.detailedurl != null)
				return false;
		} else if (!detailedurl.equals(other.detailedurl))
			return false;
		if (organizationID == null) {
			if (other.organizationID != null)
				return false;
		} else if (!organizationID.equals(other.organizationID))
			return false;
		if (receiveDate == null) {
			if (other.receiveDate != null)
				return false;
		} else if (!receiveDate.equals(other.receiveDate))
			return false;
		if (remindID == null) {
			if (other.remindID != null)
				return false;
		} else if (!remindID.equals(other.remindID))
			return false;
		if (remindKey == null) {
			if (other.remindKey != null)
				return false;
		} else if (!remindKey.equals(other.remindKey))
			return false;
		if (remindStatus == null) {
			if (other.remindStatus != null)
				return false;
		} else if (!remindStatus.equals(other.remindStatus))
			return false;
		if (remindType == null) {
			if (other.remindType != null)
				return false;
		} else if (!remindType.equals(other.remindType))
			return false;
		if (staffID == null) {
			if (other.staffID != null)
				return false;
		} else if (!staffID.equals(other.staffID))
			return false;
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		if (urlType == null) {
			if (other.urlType != null)
				return false;
		} else if (!urlType.equals(other.urlType))
			return false;
		return true;
	}
	
	public String getRemindKey() {
		return remindKey;
	}
	public void setRemindKey(String remindKey) {
		this.remindKey = remindKey;
	}
	public String getRemindID() {
		return remindID;
	}
	public void setRemindID(String remindID) {
		this.remindID = remindID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCircularType() {
		return circularType;
	}
	public void setCircularType(String circularType) {
		this.circularType = circularType;
	}
	public String getCircularTitle() {
		return circularTitle;
	}
	public void setCircularTitle(String circularTitle) {
		this.circularTitle = circularTitle;
	}
	public String getCircularText() {
		return circularText;
	}
	public void setCircularText(String circularText) {
		this.circularText = circularText;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	public String getDetailedurl() {
		return detailedurl;
	}
	public void setDetailedurl(String detailedurl) {
		this.detailedurl = detailedurl;
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
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getRemindType() {
		return remindType;
	}
	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}
	public String getRemindStatus() {
		return remindStatus;
	}
	public void setRemindStatus(String remindStatus) {
		this.remindStatus = remindStatus;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
