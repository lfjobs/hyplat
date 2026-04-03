package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 企业精神
 * @author Administrator
 *
 */
public class EnterpriseSpirit implements BaseBean, ExcelBean ,java.io.Serializable {
	private String spiritID;
	private String spiritKey;
	private String companyID;
	private String organizationID;
	private String enterpriseName;//企业名称
	private String spiritContent;//内容
	private String spiritNote;//注释
	
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "企业名称", "精神内容","注释"};
		return titles;
	}	
	@Override
	public String[] properties() {
		String[] properties = {enterpriseName,spiritContent,spiritNote };
		return properties;
	}
	public String getSpiritID() {
		return spiritID;
	}
	public void setSpiritID(String spiritID) {
		this.spiritID = spiritID;
	}
	public String getSpiritKey() {
		return spiritKey;
	}
	public void setSpiritKey(String spiritKey) {
		this.spiritKey = spiritKey;
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
	public String getSpiritContent() {
		return spiritContent;
	}
	public void setSpiritContent(String spiritContent) {
		this.spiritContent = spiritContent;
	}
	public String getSpiritNote() {
		return spiritNote;
	}
	public void setSpiritNote(String spiritNote) {
		this.spiritNote = spiritNote;
	}


}
