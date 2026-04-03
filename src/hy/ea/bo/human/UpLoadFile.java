package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 多附件上传实体
 * @author zgzg
 *
 */
public class UpLoadFile implements BaseBean {
	private String fileKey;
	private String fileID; 
	private String companyID;
	private String parmeterID;
	private String filename; 
	private String filepath; 
	private String filedesc;
	private String fileType;
	private String fileAttr;


	
	public UpLoadFile() {
		super();
	}
	public UpLoadFile(String filepath) {
		super();
		this.filepath=filepath;
	}
	public UpLoadFile(String fileKey, String fileID, String companyID,
			String parmeterID, String filename, String filepath, String filedesc) {
		super();
		this.fileKey = fileKey;
		this.fileID = fileID;
		this.companyID = companyID;
		this.parmeterID = parmeterID;
		this.filename = filename;
		this.filepath = filepath;
		this.filedesc = filedesc;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getParmeterID() {
		return parmeterID;
	}
	public void setParmeterID(String parmeterID) {
		this.parmeterID = parmeterID;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFiledesc() {
		return filedesc;
	}
	public void setFiledesc(String filedesc) {
		this.filedesc = filedesc;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileAttr() {
		return fileAttr;
	}

	public void setFileAttr(String fileAttr) {
		this.fileAttr = fileAttr;
	}
}
