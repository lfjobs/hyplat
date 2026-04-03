package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 企业奖状奖牌
 * @author Administrator
 *
 */
public class EnterpriseCitation implements BaseBean,ExcelBean,java.io.Serializable {

	private String enterpriseCitationKey;
	private String enterpriseCitationID;
	private String companyID;
	private String organizationID;
	
	private String enName;					//名称
	private String enSubject;				//主题内容
	private Date enDate;					//授予年度
	private String citationFilePath;		//扫描附件
	private String mark;					//备注
	
	private File citationFile;
	private String citationFileFileName;
	private String citationFileContentType;
	
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","名称","主题内容","授予年度","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {enName,enSubject,String.format("%1$tF", enDate),mark};
		return properties;
	}
	public String getEnterpriseCitationKey() {
		return enterpriseCitationKey;
	}
	public void setEnterpriseCitationKey(String enterpriseCitationKey) {
		this.enterpriseCitationKey = enterpriseCitationKey;
	}
	public String getEnterpriseCitationID() {
		return enterpriseCitationID;
	}
	public void setEnterpriseCitationID(String enterpriseCitationID) {
		this.enterpriseCitationID = enterpriseCitationID;
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
	public Date getEnDate() {
		return enDate;
	}
	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}
	public String getCitationFilePath() {
		return citationFilePath;
	}
	public void setCitationFilePath(String citationFilePath) {
		this.citationFilePath = citationFilePath;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public File getCitationFile() {
		return citationFile;
	}
	public void setCitationFile(File citationFile) {
		this.citationFile = citationFile;
	}
	public String getCitationFileContentType() {
		return citationFileContentType;
	}
	public void setCitationFileContentType(String citationFileContentType) {
		this.citationFileContentType = citationFileContentType;
	}
	public String getCitationFileFileName() {
		return citationFileFileName;
	}
	public void setCitationFileFileName(String citationFileFileName) {
		this.citationFileFileName = citationFileFileName;
	}
}
