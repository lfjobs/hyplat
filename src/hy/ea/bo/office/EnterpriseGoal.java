package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 企业目标
 *
 */
public class EnterpriseGoal implements BaseBean,ExcelBean,java.io.Serializable {
	private String goalID;
	private String goalKey;
	private String companyID;
	private String organizationID;
	private String enterpriseName;//企业名称
	private String goalContent;//内容
	private String goalNote;//注释
	public static String[] columnHeadings() {
		String[] titles = { "序号", "企业名称", "目标内容", "注释"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {enterpriseName,goalContent,goalNote};
		return properties;
	}
	public String getGoalID() {
		return goalID;
	}
	public void setGoalID(String goalID) {
		this.goalID = goalID;
	}
	public String getGoalKey() {
		return goalKey;
	}
	public void setGoalKey(String goalKey) {
		this.goalKey = goalKey;
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
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getGoalContent() {
		return goalContent;
	}
	public void setGoalContent(String goalContent) {
		this.goalContent = goalContent;
	}
	public String getGoalNote() {
		return goalNote;
	}
	public void setGoalNote(String goalNote) {
		this.goalNote = goalNote;
	}
	
	

}
