package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;

/**
 * 人事档案单据
 *
 */
public class StudentArchiveBill implements BaseBean,ExcelBean,java.io.Serializable{
	private String pabillKey;        //主键
	private String pabillID;         //业务主键
	private String groupCompanySn;     		//集团公司的标识  外键
	private String companyID;          		//公司  外键
	private String companyName;//数据库没有
	private String organizationID;     		//单据所在部门  外键
	private String organizationName;//所在部门数据库没有；
	private String departmentID;       		//子部门ID(限定部门单据) 外键
	
	private String companyBankNum;     		//公司银行账号
	private String billStatus;         		//单据状态  00：未收货  01：已收货 02：已入库 03：已出库 04：市场调查 05：盘库 06：移库 07:销售出库
	
	private String cstaffID;            	//往来个人ID  外键
	private String cstaffName;          	//往来个人名称
	private String cstaffRelationship;      //个人往来关系
	private String userAccountNum;			//往来个人银行帐号
	
	private String ccompanyID;         		//往来单位ID  外键
	private String ccompanyName;       		//往来单位名称
	private String ccompanyRelationship;    //往来单位关系
	private String ccompanyTel;        		//往来单位电话
	private String accountNum;				//往来公司银行账号	
	
	private String staffID;                 //限定部门内的责任人ID  外键
	private String staffName;               //责任人名称
	
	private String staffsID;           		//限定部门内的接收人ID  外键
	private String staffsName;				//接收人姓名
	
	private String billsType;                //单据类型
	private String journalNum;               //凭证号
	private String billsDate;                //制单日期
	
	private String billStaffID;              //制单人ID  外键
	private String billStaffName;            //制单人名称
	
	private String accessoriesUrl;			//附件(路径)
	private File photo;						//附件文件
	private String PhotoFileName;			//附件名称
	
	@Override
	public String[] properties() {
		return null;
	}
	


	public String getPabillKey() {
		return pabillKey;
	}



	public void setPabillKey(String pabillKey) {
		this.pabillKey = pabillKey;
	}



	public String getPabillID() {
		return pabillID;
	}



	public void setPabillID(String pabillID) {
		this.pabillID = pabillID;
	}



	/**
	 * 集团公司的标识  外键
	 * @return
	 */
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	/**
	 * 集团公司的标识  外键
	 * @param groupCompanySn
	 */
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	
	/**
	 * 公司  外键
	 * @return
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * 公司  外键
	 * @param companyID
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
	 * 单据所在部门  外键
	 * @return
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * 单据所在部门  外键
	 * @param organizationID
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	
	/**
	 * 子部门ID(限定部门单据) 外键
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}
	/**
	 * 子部门ID(限定部门单据) 外键
	 * @param departmentID
	 */
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	
	/**
	 * 公司银行账号
	 * @return
	 */
	public String getCompanyBankNum() {
		return companyBankNum;
	}
	/**
	 * 公司银行账号
	 * @param companyBankNum
	 */
	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}
	
	/**
	 * 单据状态  00：未收货  01：已收货 02：已入库 03：已出库 04：市场调查 05:盘库单
	 * @return
	 */
	public String getBillStatus() {
		return billStatus;
	}
	/**
	 * 单据状态  00：未收货  01：已收货 02：已入库 03：已出库 04：市场调查 05:盘库单
	 * @param billStatus
	 */
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	
	/**
	 * 往来个人ID  外键
	 * @return
	 */
	public String getCstaffID() {
		return cstaffID;
	}
	/**
	 * 往来个人ID  外键
	 * @param cstaffID
	 */
	public void setCstaffID(String cstaffID) {
		this.cstaffID = cstaffID;
	}
	
	/**
	 * 往来个人名称
	 * @return
	 */
	public String getCstaffName() {
		return cstaffName;
	}
	/**
	 * 往来个人名称
	 * @param cstaffName
	 */
	public void setCstaffName(String cstaffName) {
		this.cstaffName = cstaffName;
	}
	
	/**
	 * 个人往来关系
	 * @return
	 */
	public String getCstaffRelationship() {
		return cstaffRelationship;
	}
	/**
	 * 个人往来关系
	 * @param cstaffRelationship
	 */
	public void setCstaffRelationship(String cstaffRelationship) {
		this.cstaffRelationship = cstaffRelationship;
	}
	
	/**
	 * 往来单位ID  外键
	 * @return
	 */
	public String getCcompanyID() {
		return ccompanyID;
	}
	/**
	 * 往来单位ID  外键
	 * @param ccompanyID
	 */
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	
	/**
	 * 往来单位名称
	 * @return
	 */
	public String getCcompanyName() {
		return ccompanyName;
	}
	/**
	 * 往来单位名称
	 * @param ccompanyName
	 */
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	
	/**
	 * 往来单位关系
	 * @return
	 */
	public String getCcompanyRelationship() {
		return ccompanyRelationship;
	}
	/**
	 * 往来单位关系
	 * @param ccompanyRelationship
	 */
	public void setCcompanyRelationship(String ccompanyRelationship) {
		this.ccompanyRelationship = ccompanyRelationship;
	}
	
	/**
	 * 往来单位电话
	 * @return
	 */
	public String getCcompanyTel() {
		return ccompanyTel;
	}
	/**
	 * 往来单位电话
	 * @param ccompanyTel
	 */
	public void setCcompanyTel(String ccompanyTel) {
		this.ccompanyTel = ccompanyTel;
	}
	
	/**
	 * 限定部门内德责任人ID  外键
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * 限定部门内德责任人ID  外键
	 * @param staffID
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	/**
	 * 单据类型
	 * @return
	 */
	public String getBillsType() {
		return billsType;
	}
	/**
	 * 单据类型
	 * @param billsType
	 */
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	
	/**
	 * 凭证号
	 * @return
	 */
	public String getJournalNum() {
		return journalNum;
	}
	/**
	 * 凭证号
	 * @param journalNum
	 */
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	
	/**
	 * 制单日期
	 * @return
	 */
	public String getBillsDate() {
		return billsDate;
	}
	/**
	 * 制单日期
	 * @param billsDate
	 */
	public void setBillsDate(String billsDate) {
		this.billsDate = billsDate;
	}
	
	/**
	 * 制单人ID  外键
	 * @return
	 */
	public String getBillStaffID() {
		return billStaffID;
	}
	/**
	 * 制单人ID  外键
	 * @param billStaffID
	 */
	public void setBillStaffID(String billStaffID) {
		this.billStaffID = billStaffID;
	}
	
	/**
	 * 制单人名称
	 * @return
	 */
	public String getBillStaffName() {
		return billStaffName;
	}
	/**
	 * 制单人名称
	 * @param billStaffName
	 */
	public void setBillStaffName(String billStaffName) {
		this.billStaffName = billStaffName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
	}
	/**
	 * 责任人名称
	 * @return
	 */

	public String getStaffName() {
		return staffName;
	}
	/**
	 * 责任人名称
	 * @return
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * 附件(路径)
	 * @return
	 */
	public String getAccessoriesUrl() {
		return accessoriesUrl;
	}
	/**
	 * 附件(路径)
	 * @param accessoriesUrl
	 */
	public void setAccessoriesUrl(String accessoriesUrl) {
		this.accessoriesUrl = accessoriesUrl;
	}

	/**
	 * 附件文件
	 * @return
	 */
	public File getPhoto() {
		return photo;
	}
	/**
	 * 附件文件
	 * @param photo
	 */
	public void setPhoto(File photo) {
		this.photo = photo;
	}

	/**
	 * 附件名称
	 * @return
	 */
	public String getPhotoFileName() {
		return PhotoFileName;
	}
	/**
	 * 附件名称
	 * @param photoFileName
	 */
	public void setPhotoFileName(String photoFileName) {
		PhotoFileName = photoFileName;
	}

	public String getStaffsID() {
		return staffsID;
	}

	public void setStaffsID(String staffsID) {
		this.staffsID = staffsID;
	}

	public String getStaffsName() {
		return staffsName;
	}

	public void setStaffsName(String staffsName) {
		this.staffsName = staffsName;
	}



	public String getOrganizationName() {
		return organizationName;
	}



	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
