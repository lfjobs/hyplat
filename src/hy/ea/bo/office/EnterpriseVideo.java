package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
/**
 * 企业录像管理
 * @author Administrator
 *
 */
public class EnterpriseVideo implements BaseBean,ExcelBean,java.io.Serializable {


	private String enterpriseVideoKey;
	private String enterpriseVideoID;
	private String companyID;
	private String organizationID;
	
	private String enName;			//名称
	private String enSubject;		//录像主题描述
	private Date enDate;			//设置年度
	private String videoPath;		//录像
	private String mark;			//备注
	
	private File videoFile;
	private String videoFileFileName;
	private String videoFileContentType;
	
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","名称","录像主题描述","设置年度","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String titles[] = {enName,enSubject,String.format("%1$tF", enDate),mark};
		return titles;
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
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getMark() {
		return mark;
	} 
	public String getEnterpriseVideoKey() {
		return enterpriseVideoKey;
	}
	public void setEnterpriseVideoKey(String enterpriseVideoKey) {
		this.enterpriseVideoKey = enterpriseVideoKey;
	}
	public String getEnterpriseVideoID() {
		return enterpriseVideoID;
	}
	public void setEnterpriseVideoID(String enterpriseVideoID) {
		this.enterpriseVideoID = enterpriseVideoID;
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
	public void setMark(String mark) {
		this.mark = mark;
	}
	public File getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
	public String getVideoFileContentType() {
		return videoFileContentType;
	}
	public void setVideoFileContentType(String videoFileContentType) {
		this.videoFileContentType = videoFileContentType;
	}
	public String getVideoFileFileName() {
		return videoFileFileName;
	}
	public void setVideoFileFileName(String videoFileFileName) {
		this.videoFileFileName = videoFileFileName;
	}
}
