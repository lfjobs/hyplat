package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
/**
 * 网络硬盘
 * @author Administrator
 *
 */

public class NetDisk  implements BaseBean ,ExcelBean,java.io.Serializable {
	private String netDiskID;
	private String netDiskKey;
	private String companyID;
	private String organizationID;     
	private String netDiskNum;            	 //文件编号
	private String netDiskName;              //文件名
	private String netDiskType;            	 //文件类型 
	private String netDiskSize;            	 //文件大小
	private Date netDiskDate;            	 //修改时间
	
	private String netDiskPath;              //文件上传保存的路径
	
	private File netDiskFile;                //上传的文件
	private String netDiskFileFileName;        //上传文件的名称
	private String netDiskFileContentType;   //上传文件的类型
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","文件编号","文件名","文件类型","文件大小","修改时间"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {netDiskNum,netDiskName,netDiskType,netDiskSize,String.format("%1$tF", netDiskDate)};
		return properties;
	}
	public String getNetDiskID() {
		return netDiskID;
	}
	public void setNetDiskID(String netDiskID) {
		this.netDiskID = netDiskID;
	}
	public String getNetDiskKey() {
		return netDiskKey;
	}
	public void setNetDiskKey(String netDiskKey) {
		this.netDiskKey = netDiskKey;
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
	public String getNetDiskNum() {
		return netDiskNum;
	}
	public void setNetDiskNum(String netDiskNum) {
		this.netDiskNum = netDiskNum;
	}
	public String getNetDiskName() {
		return netDiskName;
	}
	public void setNetDiskName(String netDiskName) {
		this.netDiskName = netDiskName;
	}
	public String getNetDiskType() {
		return netDiskType;
	}
	public void setNetDiskType(String netDiskType) {
		this.netDiskType = netDiskType;
	}
	public String getNetDiskSize() {
		return netDiskSize;
	}
	public void setNetDiskSize(String netDiskSize) {
		this.netDiskSize = netDiskSize;
	}
	public Date getNetDiskDate() {
		return netDiskDate;
	}
	public void setNetDiskDate(Date netDiskDate) {
		this.netDiskDate = netDiskDate;
	}
	public File getNetDiskFile() {
		return netDiskFile;
	}
	public void setNetDiskFile(File netDiskFile) {
		this.netDiskFile = netDiskFile;
	}
	public String getNetDiskFileContentType() {
		return netDiskFileContentType;
	}
	public void setNetDiskFileContentType(String netDiskFileContentType) {
		this.netDiskFileContentType = netDiskFileContentType;
	}
	public String getNetDiskPath() {
		return netDiskPath;
	}
	public void setNetDiskPath(String netDiskPath) {
		this.netDiskPath = netDiskPath;
	}
	public String getNetDiskFileFileName() {
		return netDiskFileFileName;
	}
	public void setNetDiskFileFileName(String netDiskFileFileName) {
		this.netDiskFileFileName = netDiskFileFileName;
	} 
}
