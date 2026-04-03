package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 工作态度管理
 *
 */
public class EnterpriseManner implements BaseBean ,ExcelBean,java.io.Serializable {
	private String mannerID;
	private String mannerKey;
	private String companyID;
	private String organizationID;
	private String enterpriseName;//企业名称
	private String mannerContent;//态度
	private String mannerNote;//注释
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "企业名称", "管理态度", "注释"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {enterpriseName,mannerContent,mannerNote};
		return properties;
	}
	
	public String getMannerID() {
		return mannerID;
	}
	public void setMannerID(String mannerID) {
		this.mannerID = mannerID;
	}
	public String getMannerKey() {
		return mannerKey;
	}
	public void setMannerKey(String mannerKey) {
		this.mannerKey = mannerKey;
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
	public String getMannerContent() {
		return mannerContent;
	}
	public void setMannerContent(String mannerContent) {
		this.mannerContent = mannerContent;
	}
	public String getMannerNote() {
		return mannerNote;
	}
	public void setMannerNote(String mannerNote) {
		this.mannerNote = mannerNote;
	}
	

}
