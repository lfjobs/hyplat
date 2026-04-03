package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 *钥匙管理
 * @author Administrator
 *
 */
public class KeyManage implements BaseBean ,ExcelBean,java.io.Serializable{
	private String keyManageID;
	private String keyManageKey;
	private String companyID;
	private String organizationID;
	private String deptName;//部门名称
	private String roomNum;//房间号
	private String keyCount;//钥匙数量
	private String custodian;//保管人
	private Date borrowDate;//借出时间
	private Date returnDate;//归还时间
	private String memorandum;//备忘录
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "部门名称", "房间号", "钥匙数量", "保管人","借出时间", "归还时间","备忘录"};
		return titles;
	}

	public String[] properties() {
		String[] properties = { deptName,roomNum,keyCount,custodian,String.format("%1$tF", borrowDate),String.format("%1$tF", returnDate),
				memorandum};
		return properties;
	}

	public String getKeyManageID() {
		return keyManageID;
	}

	public void setKeyManageID(String keyManageID) {
		this.keyManageID = keyManageID;
	}

	public String getKeyManageKey() {
		return keyManageKey;
	}

	public void setKeyManageKey(String keyManageKey) {
		this.keyManageKey = keyManageKey;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}


	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getMemorandum() {
		return memorandum;
	}

	public void setMemorandum(String memorandum) {
		this.memorandum = memorandum;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

}
