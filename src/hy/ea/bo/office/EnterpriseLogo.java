package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 车辆信息
 * @author Administrator
 *
 */
public class EnterpriseLogo implements BaseBean ,ExcelBean,java.io.Serializable {
	private String logoID;
	private String logoKey;
	private String companyID;
	private String organizationID;
	private String logoType;//徽标类型
	private String logoPhoto;//徽标图片
	private String author;//作者
	private String logoNote;//注释
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "徽标类型", "作者", "注释"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {logoType,author,logoNote};
		return properties;
	}

	public String getLogoID() {
		return logoID;
	}

	public void setLogoID(String logoID) {
		this.logoID = logoID;
	}

	public String getLogoKey() {
		return logoKey;
	}

	public void setLogoKey(String logoKey) {
		this.logoKey = logoKey;
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

	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public String getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(String logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLogoNote() {
		return logoNote;
	}

	public void setLogoNote(String logoNote) {
		this.logoNote = logoNote;
	}

	
}
