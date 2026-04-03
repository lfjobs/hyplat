/**
 * Contact
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 银行帐号
 * @author Administrator
 *
 */
public class StaffBankAccount implements BaseBean,ExcelBean{
	private String  bankAccountKey;
	private String  bankAccountID;
	private String 	staffID;
	private String  bankCode;//银行编号
	private String  approvalNumber;//证明号（核准号）
	private String  bankName;//银行名称
	private String  branchName; //支行名称
	private Date 	theDate; //发证日期
	private String 	accountNature;//账户性质（使用类别）
	private String 	bankAccount;//银行帐号
	private Date    openDate;//开户时间
	private String  bankContact;//银行联系方式     
	private String  province;  		//所属省份
	private String 	bankAddress;// 所属城市 
	private Date 	cancellationDate ;//注销时间
	private String  type;     // 00：公司   01：个人
	private String 	ccompanyId;//往来单位id 
	private String cardholder;		//持卡人姓名
	private String isdefault;//默认收款银行卡1是0否
	private String cardType;// 00:对私 01:对公
	private Date addTime;//新增时间
	
	/* 银行卡信息  持卡人所对应的信息    */	
	private String idcard;//身份证号	
	private String tel;//银行预留电话
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "银行编号", "证明号", "银行名称","发证日期", "账户性质", "银行帐号", "开户时间", "银行联系方式", "银行地址","注销时间"};
		return titles;
	}
	public String[] properties() {
		String[] properties = {bankCode,approvalNumber,bankName, String.format("%1$tF", theDate),accountNature,bankAccount,String.format("%1$tF", openDate),bankContact,bankAddress,String.format("%1$tF", cancellationDate)};
		return properties;
	}
	public String getBankAccountKey() {
		return bankAccountKey;
	}
	public void setBankAccountKey(String bankAccountKey) {
		this.bankAccountKey = bankAccountKey;
	}
	public String getBankAccountID() {
		return bankAccountID;
	}
	public void setBankAccountID(String bankAccountID) {
		this.bankAccountID = bankAccountID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	public Date getTheDate() {
		return theDate;
	}
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}
	public String getAccountNature() {
		return accountNature;
	}
	public void setAccountNature(String accountNature) {
		this.accountNature = accountNature;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getBankContact() {
		return bankContact;
	}
	public void setBankContact(String bankContact) {
		this.bankContact = bankContact;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public Date getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
