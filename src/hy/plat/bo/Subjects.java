package hy.plat.bo;
/**
 * 预设会计科目
 * @author Administrator
 *
 */
public class Subjects implements BaseBean{
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
	 * 状态
	 */
	private String subjectsStatus;
	
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
	
}
