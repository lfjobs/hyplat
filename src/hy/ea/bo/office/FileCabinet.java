package hy.ea.bo.office;



import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FileCabinet implements BaseBean ,java.io.Serializable{

	private String key;
	private String fileCabinetId;//文件柜id
	private String companyId;//所属公司Id
	private String companyName;//公司名称；
	private String fileCabinetName;//文件柜名称
	private String usedSpace;//占用空间
	private String fileFolderNumber;//包含的文件夹数量
	private String fileNumber;//包含的文件数量
	private String createrId;//文件柜创建人id
	private String createrName;//创建人姓名；
	private Date createTime;//创建文件柜时间
	private String descriptor;//对文件柜的描述
    private Set<FileFolder> fileFolders=new HashSet<FileFolder>();
    private Set<FileUpload> fileUploads=new HashSet<FileUpload>();
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFileCabinetId() {
		return fileCabinetId;
	}
	public void setFileCabinetId(String fileCabinetId) {
		this.fileCabinetId = fileCabinetId;
	}
	public String getFileCabinetName() {
		return fileCabinetName;
	}
	public void setFileCabinetName(String fileCabinetName) {
		this.fileCabinetName = fileCabinetName;
	}
	public String getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(String usedSpace) {
		this.usedSpace = usedSpace;
	}
	public String getFileFolderNumber() {
		return fileFolderNumber;
	}
	public void setFileFolderNumber(String fileFolderNumber) {
		this.fileFolderNumber = fileFolderNumber;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date rcreateTimeeateTime) {
		this.createTime = rcreateTimeeateTime;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Set<FileFolder> getFileFolders() {
		return fileFolders;
	}
	public void setFileFolders(Set<FileFolder> fileFolders) {
		this.fileFolders = fileFolders;
	}
	public Set<FileUpload> getFileUploads() {
		return fileUploads;
	}
	public void setFileUploads(Set<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}
	

	public static class FileSearchInfo {
		private String searchType;
		private String name;
		public String getSearchType() {
			return searchType;
		}
		public void setSearchType(String searchType) {
			this.searchType = searchType;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class FileUploadInfo implements java.io.Serializable{
		private String fileSize;
		private String fileCabinetId;
		private String fileFolderId;
		private String uploadMode;
		public String getFileSize() {
			return fileSize;
		}
		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}
		
		public String getFileCabinetId() {
			return fileCabinetId;
		}
		public void setFileCabinetId(String fileCabinetId) {
			this.fileCabinetId = fileCabinetId;
		}
		public String getFileFolderId() {
			return fileFolderId;
		}
		public void setFileFolderId(String fileFolderId) {
			this.fileFolderId = fileFolderId;
		}
		public String getUploadMode() {
			return uploadMode;
		}
		public void setUploadMode(String uploadMode) {
			this.uploadMode = uploadMode;
		}
		
		
	}
		
}
