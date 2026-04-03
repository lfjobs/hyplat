package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 公司银行帐号
 * @author zgzg
 *
 */
public class InstitutionsRegistration implements BaseBean,ExcelBean,java.io.Serializable{
		private String registrationID;
		private String registrationKey;
		
		private String companyID;  //登录公司ID
		
		private String  organizationID;//部门ID
		private String  organizationPID;//上级机构ID 
		
		private String bankRegistrationID;//银行开户登记证明号(审核号)
		private Date   registrationDate;//发证日期
		private String accountNature;//账户性质
		private String bankName;//银行名称
		private String bankAccount;//银行帐号
		private Date   openAccountDate; //开户时间
		private Date   cancellationDate;//注销时间
		
		//2010-1-17添加
		private String accountNum; //银行编号
		private String contact;//联系方式
		private String conPerson;//账户责任人 
		private String bankAddr;         //开户银行具体地址
		
		//2014-11-25添加
		private String papersType;//证件类型
		private String papersNumber;//证件号码
		private String birthday;//出生日期
		private Integer bankState;//是否开通网银
		private String bankCyber ;//网银注册行
		public static String[] columnHeadings() {
			String[] titles = {"序号" ,"帐号编号", "银行开户登记证明号(审核号)", "发证日期", "账户性质(使用类别)", "银行名称", "开户帐号", "开户时间", "注销时间","联系方式","账户责任人","开户银行地址"};
			return titles;
		}		
		@Override
		public String[] properties() {
			String[] properties = {accountNum, bankRegistrationID,String.format("%1$tF", registrationDate),
					accountNature, bankName, bankAccount, String.format("%1$tF", openAccountDate),String.format("%1$tF", cancellationDate),contact,conPerson,bankAddr};
			return properties;
		}
		
		
		
		public String getPapersType() {
			return papersType;
		}
		public void setPapersType(String papersType) {
			this.papersType = papersType;
		}
		public String getPapersNumber() {
			return papersNumber;
		}
		public void setPapersNumber(String papersNumber) {
			this.papersNumber = papersNumber;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public Integer getBankState() {
			return bankState;
		}
		public void setBankState(Integer bankState) {
			this.bankState = bankState;
		}
		public String getBankCyber() {
			return bankCyber;
		}
		public void setBankCyber(String bankCyber) {
			this.bankCyber = bankCyber;
		}
		public String getOrganizationID() {
			return organizationID;
		}
		public void setOrganizationID(String organizationID) {
			this.organizationID = organizationID;
		}
		public String getOrganizationPID() {
			return organizationPID;
		}
		public void setOrganizationPID(String organizationPID) {
			this.organizationPID = organizationPID;
		}
		public String getRegistrationID() {
			return registrationID;
		}



		public void setRegistrationID(String registrationID) {
			this.registrationID = registrationID;
		}



		public String getRegistrationKey() {
			return registrationKey;
		}



		public void setRegistrationKey(String registrationKey) {
			this.registrationKey = registrationKey;
		}



		public String getCompanyID() {
			return companyID;
		}



		public String getAccountNum() {
			return accountNum;
		}
		public void setAccountNum(String accountNum) {
			this.accountNum = accountNum;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getConPerson() {
			return conPerson;
		}
		public void setConPerson(String conPerson) {
			this.conPerson = conPerson;
		} 
		
		public String getBankAddr() {
			return bankAddr;
		}
		public void setBankAddr(String bankAddr) {
			this.bankAddr = bankAddr;
		}
		public void setCompanyID(String companyID) {
			this.companyID = companyID;
		}

		public String getBankRegistrationID() {
			return bankRegistrationID;
		}



		public void setBankRegistrationID(String bankRegistrationID) {
			this.bankRegistrationID = bankRegistrationID;
		}



		public Date getRegistrationDate() {
			return registrationDate;
		}



		public void setRegistrationDate(Date registrationDate) {
			this.registrationDate = registrationDate;
		}



		public String getAccountNature() {
			return accountNature;
		}



		public void setAccountNature(String accountNature) {
			this.accountNature = accountNature;
		}



		public String getBankName() {
			return bankName;
		}



		public void setBankName(String bankName) {
			this.bankName = bankName;
		}



		



		public String getBankAccount() {
			return bankAccount;
		}
		public void setBankAccount(String bankAccount) {
			this.bankAccount = bankAccount;
		}
		public Date getOpenAccountDate() {
			return openAccountDate;
		}



		public void setOpenAccountDate(Date openAccountDate) {
			this.openAccountDate = openAccountDate;
		}



		public Date getCancellationDate() {
			return cancellationDate;
		}



		public void setCancellationDate(Date cancellationDate) {
			this.cancellationDate = cancellationDate;
		}



}
