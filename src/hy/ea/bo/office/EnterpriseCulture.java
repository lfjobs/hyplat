package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 企业文化
 *
 */
public class EnterpriseCulture implements BaseBean, ExcelBean ,java.io.Serializable {
	private String cultureID;
	private String cultureKey;
	private String companyID;
	private String organizationID;
	private String culture;//企业文化
	private String cultureContent;//内容
	private String cultureNote;//注释
	
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "企业文化", "文化内容","注释"};
		return titles;
	}	
	@Override
	public String[] properties() {
		String[] properties = {culture,cultureContent,cultureNote };
		return properties;
	}
	
	public String getCultureID() {
		return cultureID;
	}
	public void setCultureID(String cultureID) {
		this.cultureID = cultureID;
	}
	public String getCultureKey() {
		return cultureKey;
	}
	public void setCultureKey(String cultureKey) {
		this.cultureKey = cultureKey;
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
	public String getCulture() {
		return culture;
	}
	public void setCulture(String culture) {
		this.culture = culture;
	}
	public String getCultureContent() {
		return cultureContent;
	}
	public void setCultureContent(String cultureContent) {
		this.cultureContent = cultureContent;
	}
	public String getCultureNote() {
		return cultureNote;
	}
	public void setCultureNote(String cultureNote) {
		this.cultureNote = cultureNote;
	}
	
	

}
