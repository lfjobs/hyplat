package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 管理理念
 *
 */
public class EnterpriseManage implements BaseBean, ExcelBean,java.io.Serializable  {
	private String manageID;
	private String manageKey;
	private String companyID;
	private String organizationID;
	private String manageIdea;//管理理念
	private String manageContent;//内容
	private String manageNote;//注释
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "管理理念", "理念内容", "注释"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {manageIdea,manageContent,manageNote};
		return properties;
	}
	public String getManageID() {
		return manageID;
	}
	public void setManageID(String manageID) {
		this.manageID = manageID;
	}
	
	public String getManageKey() {
		return manageKey;
	}
	public void setManageKey(String manageKey) {
		this.manageKey = manageKey;
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
	public String getManageIdea() {
		return manageIdea;
	}
	public void setManageIdea(String manageIdea) {
		this.manageIdea = manageIdea;
	}
	public String getManageContent() {
		return manageContent;
	}
	public void setManageContent(String manageContent) {
		this.manageContent = manageContent;
	}
	public String getManageNote() {
		return manageNote;
	}
	public void setManageNote(String manageNote) {
		this.manageNote = manageNote;
	}
	
	

}
