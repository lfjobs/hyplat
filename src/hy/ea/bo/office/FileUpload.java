package hy.ea.bo.office;



import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

public class FileUpload implements BaseBean {

	private String key;
	private String fileUploadId;//文件id
	private String companyId;//所属公司Id
	private String companyName;//公司名称；
	private String fileUploadName;//文件柜名称
	private String fileUploadSize;//文件大小
	private String uploadPersonId;//文件柜创建人id
	private String uploadPersonName;//创建人姓名；
	private Date uploadTime;//创建文件柜时间
	private String uploadTimeString;//数据库没有，时间的String类型
	private String fileUploadPath;//上传文件所在路径
	private File[] sourcePath;
	private String[] sourcePathFileName;
	private String[] sourcePathContentType;
	private FileFolder fileFolder;
	private FileCabinet fileCabinet;
	private String typeFolder;//区分是文件夹还是文件
	private String saveDirectory;
	private String iconPath;
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public FileFolder getFileFolder() {
		return fileFolder;
	}
	public void setFileFolder(FileFolder fileFolder) {
		this.fileFolder = fileFolder;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFileUploadId() {
		return fileUploadId;
	}
	public void setFileUploadId(String fileUploadId) {
		this.fileUploadId = fileUploadId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFileUploadName() {
		return fileUploadName;
	}
	public void setFileUploadName(String fileUploadName) {
		this.fileUploadName = fileUploadName;
	}
	public String getFileUploadSize() {
		return fileUploadSize;
	}
	public void setFileUploadSize(String fileUploadSize) {
		this.fileUploadSize = fileUploadSize;
	}
	public String getUploadPersonId() {
		return uploadPersonId;
	}
	public void setUploadPersonId(String uploadPersonId) {
		this.uploadPersonId = uploadPersonId;
	}
	public String getUploadPersonName() {
		return uploadPersonName;
	}
	public void setUploadPersonName(String uploadPersonName) {
		this.uploadPersonName = uploadPersonName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getFileUploadPath() {
		return fileUploadPath;
	}
	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	public FileCabinet getFileCabinet() {
		return fileCabinet;
	}
	public void setFileCabinet(FileCabinet fileCabinet) {
		this.fileCabinet = fileCabinet;
	}
	public String getSaveDirectory() {
		return saveDirectory;
	}
	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}
	public File[] getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(File[] sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String[] getSourcePathFileName() {
		return sourcePathFileName;
	}
	public void setSourcePathFileName(String[] sourcePathFileName) {
		this.sourcePathFileName = sourcePathFileName;
	}
	public String[] getSourcePathContentType() {
		return sourcePathContentType;
	}
	public void setSourcePathContentType(String[] sourcePathContentType) {
		this.sourcePathContentType = sourcePathContentType;
	}
	public String getUploadTimeString() {
		return uploadTimeString;
	}
	public void setUploadTimeString(String uploadTimeString) {
		this.uploadTimeString = uploadTimeString;
	}
	public String getTypeFolder() {
		return typeFolder;
	}
	public void setTypeFolder(String typeFolder) {
		this.typeFolder = typeFolder;
	}

    
  

	

	

}
