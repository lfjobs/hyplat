package hy.ea.bo.office;



import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FileFolder implements BaseBean {

	private String key;
	private String fileFolderId;//文件柜id
	private String companyId;//所属公司Id
	private String companyName;//公司名称；
	private String fileFolderName;//文件夹名称
	private String usedSpace;//占用空间
	private String fileNumber;//包含的文件数量
	private String createrId;//文件夹创建人id
	private String createrName;//创建人姓名；
	private Date createTime;//创建文件夹时间
	private String createTimeString;//数据库没有，时间的String类型
	private String descriptor;//对文件夹的描述
	private FileCabinet fileCabinet;//所属文件柜 
	private String saveCabinet;
	private String typeFolder;//区分是文件夹还是文件
	private Set<FileUpload> fileUploads = new HashSet<FileUpload>();
	public Set<FileUpload> getFileUploads() {
		return fileUploads;
	}
	public void setFileUploads(Set<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFileFolderId() {
		return fileFolderId;
	}
	public void setFileFolderId(String fileFolderId) {
		this.fileFolderId = fileFolderId;
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
	public String getFileFolderName() {
		return fileFolderName;
	}
	public void setFileFolderName(String fileFolderName) {
		this.fileFolderName = fileFolderName;
	}
	public String getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(String usedSpace) {
		this.usedSpace = usedSpace;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public FileCabinet getFileCabinet() {
		return fileCabinet;
	}
	public void setFileCabinet(FileCabinet fileCabinet) {
		this.fileCabinet = fileCabinet;
	}
	public String getCreateTimeString() {
		return createTimeString;
	}
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
	public String getTypeFolder() {
		return typeFolder;
	}
	public void setTypeFolder(String typeFolder) {
		this.typeFolder = typeFolder;
	}
	public String getSaveCabinet() {
		return saveCabinet;
	}
	public void setSaveCabinet(String saveCabinet) {
		this.saveCabinet = saveCabinet;
	}
}
