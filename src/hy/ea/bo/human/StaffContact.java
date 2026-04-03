/**
 * Contact
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

/**
 * 
 * 联系方式
 * @author YJG
 *
 */
public class StaffContact implements BaseBean,ExcelBean{
	private String  contactKey;
	private String 	companyID;
	private String 	staffID;
	private String  contactID;
	private String  contactName;//联系人姓名
	private Date 	contactDate;
	private String 	contactWay;//联系方式
	private String 	contactType;//联系类型
	private String assessor;//审核人
	private String assessorCode;//审核人编码
	private Date assessorDate;//审核时间
	private String 	contactDesc;
	
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "联系号码", "联系姓名", "登记日期", "联系类型", "审核人", "审核人编号", "审核时间", "备注"};
		return titles;
	}
	public String[] properties() {
		String[] properties = {contactWay,contactName, String.format("%1$tF", contactDate),oMap.get(contactType),assessor,assessorCode,
				String.format("%1$tF", assessorDate),contactDesc};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getContactKey() {
		return contactKey;
	}
	public void setContactKey(String contactKey) {
		this.contactKey = contactKey;
	}


	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getContactID() {
		return contactID;
	}
	public void setContactID(String contactID) {
		this.contactID = contactID;
	}
	public Date getContactDate() {
		return contactDate;
	}
	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getContactDesc() {
		return contactDesc;
	}
	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getAssessor() {
		return assessor;
	}
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}
	public String getAssessorCode() {
		return assessorCode;
	}
	public void setAssessorCode(String assessorCode) {
		this.assessorCode = assessorCode;
	}
	public Date getAssessorDate() {
		return assessorDate;
	}
	public void setAssessorDate(Date assessorDate) {
		this.assessorDate = assessorDate;
	}
	
	public String getStaffName()
	{
		String staffName="";
		if(oMap!=null)
		{
			staffName = oMap.get(staffID);
		}
		return staffName;
	}
}
