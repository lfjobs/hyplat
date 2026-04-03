package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 文件管理
 * @author Administrator
 *
 */
public class FileManage implements BaseBean ,ExcelBean,java.io.Serializable {
	private String fileManageID;
	private String fileManageKey;
    private String organizationID;
	private String companyID;
	private Date   fileManageDate;				//日期
	private String documentTitle;				//标题
	private String planUnit;				    //拟搞单位
	private String frontUnit;				    //主办单位
	private String blastMainUnit;				//主送单位
	private String copyUnit;					//抄送单位
	private String fileRescriptumAttitude;      //公文批复意见	
	private String fileType;					//公文文件类型
	private String fileAccessories;             //公文附件文件
	private String fileArincipalID;//责任人ID；
	private String fileArincipal;               //公文负责人
	private String remark;                      //备注
	
	private File    photo;
	private String photoFileName;
	private String photoContentType;
	
	private String shares;//share null 自己的； 1 部门的 2.公司的 3.集团的
	private String groupCompanySn;//集团标志
	private Date shareDate;//共享时间
	

	public static String[] columnHeadings() {
		String[] titles = { "序号", "上传日期","文件标题","上传单位","文件类型",
				"上传人","描述"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {String.format("%1$tF", fileManageDate),documentTitle, planUnit,
				fileType,
				fileArincipal,remark};
		return properties;
	}
	public String getFileManageID() {
		return fileManageID;
	}
	public void setFileManageID(String fileManageID) {
		this.fileManageID = fileManageID;
	}
	public String getFileManageKey() {
		return fileManageKey;
	}
	public void setFileManageKey(String fileManageKey) {
		this.fileManageKey = fileManageKey;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Date getFileManageDate() {
		return fileManageDate;
	}
	public void setFileManageDate(Date fileManageDate) {
		this.fileManageDate = fileManageDate;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getPlanUnit() {
		return planUnit;
	}
	public void setPlanUnit(String planUnit) {
		this.planUnit = planUnit;
	}
	public String getFrontUnit() {
		return frontUnit;
	}
	public void setFrontUnit(String frontUnit) {
		this.frontUnit = frontUnit;
	}
	public String getBlastMainUnit() {
		return blastMainUnit;
	}
	public void setBlastMainUnit(String blastMainUnit) {
		this.blastMainUnit = blastMainUnit;
	}
	public String getCopyUnit() {
		return copyUnit;
	}
	public void setCopyUnit(String copyUnit) {
		this.copyUnit = copyUnit;
	}
	public String getFileRescriptumAttitude() {
		return fileRescriptumAttitude;
	}
	public void setFileRescriptumAttitude(String fileRescriptumAttitude) {
		this.fileRescriptumAttitude = fileRescriptumAttitude;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileAccessories() {
		return fileAccessories;
	}
	public void setFileAccessories(String fileAccessories) {
		this.fileAccessories = fileAccessories;
	}
	public String getFileArincipal() {
		return fileArincipal;
	}
	public void setFileArincipal(String fileArincipal) {
		this.fileArincipal = fileArincipal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getFileArincipalID() {
		return fileArincipalID;
	}
	public void setFileArincipalID(String fileArincipalID) {
		this.fileArincipalID = fileArincipalID;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public Date getShareDate() {
		return shareDate;
	}
	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}
	
	
}
