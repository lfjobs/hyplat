package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
/**
 * 企业文化艺术作品管理
 * @author Administrator
 *
 */
public class EnterpriseArt implements BaseBean,ExcelBean,java.io.Serializable {

	private String enterpriseArtKey;
	private String enterpriseArtID;
	private String companyID;
	private String organizationID;
	
	private String enPerson;				//作者
	private String enName;					//作品名称
	private String enSubject;				//作品描述 
	private String enType;					//作品类别(00表示国内，01表示国际)		
	private String artFilePath;				//附件
	private String enDiscuss;				//鉴赏评论
	private String mark;					//备注
	
	private File artFile;
	private String artFileFileName;
	private String artFileContentType;
	
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","作者","作品名称","作品描述","作品类别","鉴赏评论","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {enPerson,enName,enSubject,enType,enDiscuss,mark};
		return properties;
	}
	public String getEnterpriseArtKey() {
		return enterpriseArtKey;
	}
	public void setEnterpriseArtKey(String enterpriseArtKey) {
		this.enterpriseArtKey = enterpriseArtKey;
	}
	public String getEnterpriseArtID() {
		return enterpriseArtID;
	}
	public void setEnterpriseArtID(String enterpriseArtID) {
		this.enterpriseArtID = enterpriseArtID;
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
	public String getEnPerson() {
		return enPerson;
	}
	public void setEnPerson(String enPerson) {
		this.enPerson = enPerson;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getEnSubject() {
		return enSubject;
	}
	public void setEnSubject(String enSubject) {
		this.enSubject = enSubject;
	}
	public String getEnType() {
		return enType;
	}
	public void setEnType(String enType) {
		this.enType = enType;
	}
	public String getArtFilePath() {
		return artFilePath;
	}
	public void setArtFilePath(String artFilePath) {
		this.artFilePath = artFilePath;
	}
	public String getEnDiscuss() {
		return enDiscuss;
	}
	public void setEnDiscuss(String enDiscuss) {
		this.enDiscuss = enDiscuss;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public File getArtFile() {
		return artFile;
	}
	public void setArtFile(File artFile) {
		this.artFile = artFile;
	}
	public String getArtFileContentType() {
		return artFileContentType;
	}
	public void setArtFileContentType(String artFileContentType) {
		this.artFileContentType = artFileContentType;
	}
	public String getArtFileFileName() {
		return artFileFileName;
	}
	public void setArtFileFileName(String artFileFileName) {
		this.artFileFileName = artFileFileName;
	}
}
