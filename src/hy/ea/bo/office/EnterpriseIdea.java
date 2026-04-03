package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 企业理念
 *
 */
public class EnterpriseIdea implements BaseBean ,ExcelBean ,java.io.Serializable {
	private String ideaID;
	private String ideaKey;
	private String companyID;
	private String organizationID;
	private String enterpriseName;//企业名称
	private String ideaContent;//理念内容
	private String ideaNote;//注释
	
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "企业名称", "理念内容","注释"};
		return titles;
	}	
	@Override
	public String[] properties() {
		String[] properties = {enterpriseName,ideaContent,ideaNote };
		return properties;
	}
	public String getIdeaID() {
		return ideaID;
	}
	public void setIdeaID(String ideaID) {
		this.ideaID = ideaID;
	}
	public String getIdeaKey() {
		return ideaKey;
	}
	public void setIdeaKey(String ideaKey) {
		this.ideaKey = ideaKey;
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
	public String getIdeaContent() {
		return ideaContent;
	}
	public void setIdeaContent(String ideaContent) {
		this.ideaContent = ideaContent;
	}
	public String getIdeaNote() {
		return ideaNote;
	}
	public void setIdeaNote(String ideaNote) {
		this.ideaNote = ideaNote;
	}
	
}
