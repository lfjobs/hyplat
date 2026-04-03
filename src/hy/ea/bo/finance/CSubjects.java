package hy.ea.bo.finance;
import hy.plat.bo.BaseBean;
/**
 * 公司会计科目
 * @author Administrator
 *
 */
public class CSubjects implements BaseBean{
	private String companyID;
	/**
	 * 主键
	 */
	private String subjectsKey;
	/**
	 * 业务主键
	 */
	private String subjectsID;
	/**
	 * 业务外键
	 */
	private String subjectsPID;
	/**
	 * 编号
	 */
	private String subjectsNumbers;
	/**
	 * 科目名称
	 */
	private String subjectsName;
	/**
	 * 状态 00系统预设 01 用户预设 02 作废
	 */
	private String subjectsStatus;
	/**
	 * 科目类别区分：A.银行，B.现金，C.固定资产，D.其他
	 */
	private String subjectsCategory;
	/**
	 * 借贷方向（D / C ，D：表示借，C：表示贷）
	 */
	private String subjectsDirection;
	/**
	 * 主账户注记（Y/N , Y:主账户，N：虚账户） 
	 */
	private String subjectsAccounts;
	/**
	 * 当前级别
	 */
	private String currentLevel;
	
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getSubjectsKey() {
		return subjectsKey;
	}
	public void setSubjectsKey(String subjectsKey) {
		this.subjectsKey = subjectsKey;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getSubjectsNumbers() {
		return subjectsNumbers;
	}
	public void setSubjectsNumbers(String subjectsNumbers) {
		this.subjectsNumbers = subjectsNumbers;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getSubjectsStatus() {
		return subjectsStatus;
	}
	public void setSubjectsStatus(String subjectsStatus) {
		this.subjectsStatus = subjectsStatus;
	}
	public String getSubjectsPID() {
		return subjectsPID;
	}
	public void setSubjectsPID(String subjectsPID) {
		this.subjectsPID = subjectsPID;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getSubjectsCategory() {
		return subjectsCategory;
	}
	public void setSubjectsCategory(String subjectsCategory) {
		this.subjectsCategory = subjectsCategory;
	}
	public String getSubjectsDirection() {
		return subjectsDirection;
	}
	public void setSubjectsDirection(String subjectsDirection) {
		this.subjectsDirection = subjectsDirection;
	}
	public String getSubjectsAccounts() {
		return subjectsAccounts;
	}
	public void setSubjectsAccounts(String subjectsAccounts) {
		this.subjectsAccounts = subjectsAccounts;
	}
	
}
