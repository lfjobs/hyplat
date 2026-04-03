package hy.ea.bo;

import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * 帐号管理
 * @author IT
 *
 */
public class CAccount implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String accountKey;
	private String accountID;
	private String staffID;
	private String companyID;
	private String accountName;
	private String accountEmail;          //用户名
	private String accountPassword;       //密码
	private String roleID;
    private String roleName;
	/**
	 * 00:正常    02：停用
	 */
	private String accountStatus;
	/**
	 * 01:在线　　00:不在线 
	 */
	private String accountOnLine;
	//非数据库字段
	private String staffName;
	//非数据库字段
	private String afterStaffID;
	
	private static Map<String, String> oRmap;
	
	private String companyName;
	private Staff staff;
	private Company company;//所属公司


	private String loginGuid;//用于超市判断是否登陆

	private String relationship; //往来关系；

	private String start; //口试1  笔试2结果3

	private String status;

	private String auditionDirection;//应聘方向

	private String auditionPost; //应聘职位

	private String experience;//工作经验

	public CAccount(Company company, Staff staff) {
		super();
		this.company=company;
		this.staff=staff;
		//this.accountID=accountID;
	}
	public CAccount(){}
	public String getCompanyName() {
		if(StringUtils.isBlank(companyName)){
			if(oRmap!=null){
				companyName=oRmap.get(companyID);
			}
		}
		return companyName;
	}
	public static void setoRmap(Map<String, String> oRmap) {
		CAccount.oRmap = oRmap;
	}
	public String getAccountKey() {
		return accountKey;
	}
	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	/**
	 * 00:正常    02：停用
	 * @return
	 */
	public String getAccountStatus() {
		return accountStatus;
	}
	/**
	 * 00:正常    02：停用
	 * @param accountStatus
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * 01:在线　　00:不在线 
	 * @return
	 */
	public String getAccountOnLine() {
		return accountOnLine;
	}
	/**
	 * 01:在线　　00:不在线 
	 * @param accountOnLine
	 */
	public void setAccountOnLine(String accountOnLine) {
		this.accountOnLine = accountOnLine;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getAfterStaffID() {
		return afterStaffID;
	}
	public void setAfterStaffID(String afterStaffID) {
		this.afterStaffID = afterStaffID;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getLoginGuid() {
		return loginGuid;
	}

	public void setLoginGuid(String loginGuid) {
		this.loginGuid = loginGuid;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getAuditionDirection() {
		return auditionDirection;
	}

	public void setAuditionDirection(String auditionDirection) {
		this.auditionDirection = auditionDirection;
	}

	public String getAuditionPost() {
		return auditionPost;
	}

	public void setAuditionPost(String auditionPost) {
		this.auditionPost = auditionPost;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
