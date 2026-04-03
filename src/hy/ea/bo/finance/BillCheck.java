package hy.ea.bo.finance;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 财务单据审核记录
 * @author lou
 *
 */
public class BillCheck implements BaseBean, ExcelBean,Cloneable{
	private String checkkey; // 业务主键
	private String checkid; // 业务主键
	private String auditorcompanyid; // 单据公司id
	private String auditorcompanyname; // 单据公司名称
	private String auditororgID; // 单据部门ID
	private String auditororgName; // 单据部门名称
	private String newBillsID; // 新单据id
	private String oldBillsID; // 旧单据id
	private String firstBillsID; //最初单据id
	private String inputid; // 录入人员
	private String inputname; // 录入人员
	private String staffID; // 责任人ID
	private String staffName; // 责任人name
	private Date cashierDate; // 单据日期
	private String journalNum; // 凭证号
	private String billsType; // 单据类型
	private String projectName; // 项目名称
	private Date audittime; // 审核时间
	private String auditorid; // 审核人id
	private String auditorname; // 审核人姓名

	private String comments; // 审核意见 b
	private String auditorstatus; // 审核状态 '01'未审核 '02'已审核 '03' 驳回
	private String billtatus; // 单据阶段
	private String deptpost;//职务；
	private String remark;//备注

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public BillCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneBillCheck() throws CloneNotSupportedException{
		
		return this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((auditorcompanyid == null) ? 0 : auditorcompanyid.hashCode());
		result = prime
				* result
				+ ((auditorcompanyname == null) ? 0 : auditorcompanyname
						.hashCode());
		result = prime * result
				+ ((auditorid == null) ? 0 : auditorid.hashCode());
		result = prime * result
				+ ((auditorname == null) ? 0 : auditorname.hashCode());
		result = prime * result
				+ ((auditororgID == null) ? 0 : auditororgID.hashCode());
		result = prime * result
				+ ((auditororgName == null) ? 0 : auditororgName.hashCode());
		result = prime * result
				+ ((auditorstatus == null) ? 0 : auditorstatus.hashCode());
		result = prime * result
				+ ((audittime == null) ? 0 : audittime.hashCode());
		result = prime * result
				+ ((billsType == null) ? 0 : billsType.hashCode());
		result = prime * result
				+ ((billtatus == null) ? 0 : billtatus.hashCode());
		result = prime * result
				+ ((cashierDate == null) ? 0 : cashierDate.hashCode());
		result = prime * result + ((checkid == null) ? 0 : checkid.hashCode());
		result = prime * result
				+ ((checkkey == null) ? 0 : checkkey.hashCode());
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result
				+ ((firstBillsID == null) ? 0 : firstBillsID.hashCode());
		result = prime * result + ((inputid == null) ? 0 : inputid.hashCode());
		result = prime * result
				+ ((inputname == null) ? 0 : inputname.hashCode());
		result = prime * result
				+ ((journalNum == null) ? 0 : journalNum.hashCode());
		result = prime * result
				+ ((newBillsID == null) ? 0 : newBillsID.hashCode());
		result = prime * result
				+ ((oldBillsID == null) ? 0 : oldBillsID.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((staffID == null) ? 0 : staffID.hashCode());
		result = prime * result
				+ ((staffName == null) ? 0 : staffName.hashCode());
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
		BillCheck other = (BillCheck) obj;
		if (auditorcompanyid == null) {
			if (other.auditorcompanyid != null)
				return false;
		} else if (!auditorcompanyid.equals(other.auditorcompanyid))
			return false;
		if (auditorcompanyname == null) {
			if (other.auditorcompanyname != null)
				return false;
		} else if (!auditorcompanyname.equals(other.auditorcompanyname))
			return false;
		if (auditorid == null) {
			if (other.auditorid != null)
				return false;
		} else if (!auditorid.equals(other.auditorid))
			return false;
		if (auditorname == null) {
			if (other.auditorname != null)
				return false;
		} else if (!auditorname.equals(other.auditorname))
			return false;
		if (auditororgID == null) {
			if (other.auditororgID != null)
				return false;
		} else if (!auditororgID.equals(other.auditororgID))
			return false;
		if (auditororgName == null) {
			if (other.auditororgName != null)
				return false;
		} else if (!auditororgName.equals(other.auditororgName))
			return false;
		if (auditorstatus == null) {
			if (other.auditorstatus != null)
				return false;
		} else if (!auditorstatus.equals(other.auditorstatus))
			return false;
		if (audittime == null) {
			if (other.audittime != null)
				return false;
		} else if (!audittime.equals(other.audittime))
			return false;
		if (billsType == null) {
			if (other.billsType != null)
				return false;
		} else if (!billsType.equals(other.billsType))
			return false;
		if (billtatus == null) {
			if (other.billtatus != null)
				return false;
		} else if (!billtatus.equals(other.billtatus))
			return false;
		if (cashierDate == null) {
			if (other.cashierDate != null)
				return false;
		} else if (!cashierDate.equals(other.cashierDate))
			return false;
		if (checkid == null) {
			if (other.checkid != null)
				return false;
		} else if (!checkid.equals(other.checkid))
			return false;
		if (checkkey == null) {
			if (other.checkkey != null)
				return false;
		} else if (!checkkey.equals(other.checkkey))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (firstBillsID == null) {
			if (other.firstBillsID != null)
				return false;
		} else if (!firstBillsID.equals(other.firstBillsID))
			return false;
		if (inputid == null) {
			if (other.inputid != null)
				return false;
		} else if (!inputid.equals(other.inputid))
			return false;
		if (inputname == null) {
			if (other.inputname != null)
				return false;
		} else if (!inputname.equals(other.inputname))
			return false;
		if (journalNum == null) {
			if (other.journalNum != null)
				return false;
		} else if (!journalNum.equals(other.journalNum))
			return false;
		if (newBillsID == null) {
			if (other.newBillsID != null)
				return false;
		} else if (!newBillsID.equals(other.newBillsID))
			return false;
		if (oldBillsID == null) {
			if (other.oldBillsID != null)
				return false;
		} else if (!oldBillsID.equals(other.oldBillsID))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
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
		return true;
	}

	public BillCheck(String checkkey, String checkid, String auditorcompanyid,
			String auditorcompanyname, String auditororgID,
			String auditororgName, String newBillsID, String oldBillsID,
			String firstBillsID, String inputid, String inputname,
			String staffID, String staffName, Date cashierDate,
			String journalNum, String billsType, String projectName,
			Date audittime, String auditorid, String auditorname,
			String comments, String auditorstatus, String billtatus) {
		super();
		this.checkkey = checkkey;
		this.checkid = checkid;
		this.auditorcompanyid = auditorcompanyid;
		this.auditorcompanyname = auditorcompanyname;
		this.auditororgID = auditororgID;
		this.auditororgName = auditororgName;
		this.newBillsID = newBillsID;
		this.oldBillsID = oldBillsID;
		this.firstBillsID = firstBillsID;
		this.inputid = inputid;
		this.inputname = inputname;
		this.staffID = staffID;
		this.staffName = staffName;
		this.cashierDate = cashierDate;
		this.journalNum = journalNum;
		this.billsType = billsType;
		this.projectName = projectName;
		this.audittime = audittime;
		this.auditorid = auditorid;
		this.auditorname = auditorname;
		this.comments = comments;
		this.auditorstatus = auditorstatus;
		this.billtatus = billtatus;
	}

	public String getCheckkey() {
		return checkkey;
	}

	public void setCheckkey(String checkkey) {
		this.checkkey = checkkey;
	}

	public String getCheckid() {
		return checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	public String getNewBillsID() {
		return newBillsID;
	}

	public void setNewBillsID(String newBillsID) {
		this.newBillsID = newBillsID;
	}

	public String getOldBillsID() {
		return oldBillsID;
	}

	public void setOldBillsID(String oldBillsID) {
		this.oldBillsID = oldBillsID;
	}

	public String getInputid() {
		return inputid;
	}

	public void setInputid(String inputid) {
		this.inputid = inputid;
	}

	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
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

	public Date getCashierDate() {
		return cashierDate;
	}

	public void setCashierDate(Date cashierDate) {
		this.cashierDate = cashierDate;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getAuditorid() {
		return auditorid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}

	public String getAuditorname() {
		return auditorname;
	}

	public void setAuditorname(String auditorname) {
		this.auditorname = auditorname;
	}

	public String getAuditorcompanyid() {
		return auditorcompanyid;
	}

	public void setAuditorcompanyid(String auditorcompanyid) {
		this.auditorcompanyid = auditorcompanyid;
	}

	public String getAuditorcompanyname() {
		return auditorcompanyname;
	}

	public void setAuditorcompanyname(String auditorcompanyname) {
		this.auditorcompanyname = auditorcompanyname;
	}

	public String getAuditororgID() {
		return auditororgID;
	}

	public void setAuditororgID(String auditororgID) {
		this.auditororgID = auditororgID;
	}

	public String getAuditororgName() {
		return auditororgName;
	}

	public void setAuditororgName(String auditororgName) {
		this.auditororgName = auditororgName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
	}

	public String getBilltatus() {
		return billtatus;
	}

	public void setBilltatus(String billtatus) {
		this.billtatus = billtatus;
	}

	public String getFirstBillsID() {
		return firstBillsID;
	}

	public void setFirstBillsID(String firstBillsID) {
		this.firstBillsID = firstBillsID;
	}

	public String getDeptpost() {
		return deptpost;
	}

	public void setDeptpost(String deptpost) {
		this.deptpost = deptpost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
   
}
