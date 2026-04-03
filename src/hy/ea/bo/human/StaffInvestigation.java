/**
 * Investigation
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 
 * 调查情况
 * @author YJG
 * 
 */
public class StaffInvestigation implements BaseBean ,ExcelBean{
	private String investigationKey;
	private String companyID;
	private String investigationID;
	private String staffID;
	private Date investigationDate;
	private String investigationItem;
	private String investigationPeroration;
	private String investigationPrincipal;
	private String conductIdea;
	private String conductPrincipal;
	private Date conductDate;
	private String assessor;// 审核人
	private String assessorCode;// 审核人编码
	private Date assessorDate;// 审核时间
	private String investigationDesc;
	private String quadratList;
    private String photo;
    private File    photos;
    private String  photosFileName;
	private String  photosContentType;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "调查时间", "调查项目", "调查结论", "调查责任人", "处理意见",
				"处理责任人", "处理时间", "审核人", "审核人人员编号", "审核时间", "备注" };
		return titles;
	}
	public String[] properties() {
		String[] properties = { String.format("%1$tF", investigationDate),
				investigationItem, investigationPeroration,
				investigationPrincipal, conductIdea, conductPrincipal,
				String.format("%1$tF", conductDate), assessor,
				assessorCode, String.format("%1$tF", assessorDate),
				investigationDesc };
		return properties;
	}

	public String getInvestigationKey() {
		return investigationKey;
	}

	public void setInvestigationKey(String investigationKey) {
		this.investigationKey = investigationKey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getInvestigationID() {
		return investigationID;
	}

	public void setInvestigationID(String investigationID) {
		this.investigationID = investigationID;
	}

	public String getStaffID() {
		return staffID;
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Date getInvestigationDate() {
		return investigationDate;
	}

	public void setInvestigationDate(Date investigationDate) {
		this.investigationDate = investigationDate;
	}

	public String getInvestigationItem() {
		return investigationItem;
	}

	public void setInvestigationItem(String investigationItem) {
		this.investigationItem = investigationItem;
	}

	public String getInvestigationPeroration() {
		return investigationPeroration;
	}

	public void setInvestigationPeroration(String investigationPeroration) {
		this.investigationPeroration = investigationPeroration;
	}

	public String getInvestigationPrincipal() {
		return investigationPrincipal;
	}

	public void setInvestigationPrincipal(String investigationPrincipal) {
		this.investigationPrincipal = investigationPrincipal;
	}

	public String getConductIdea() {
		return conductIdea;
	}

	public void setConductIdea(String conductIdea) {
		this.conductIdea = conductIdea;
	}

	public String getConductPrincipal() {
		return conductPrincipal;
	}

	public void setConductPrincipal(String conductPrincipal) {
		this.conductPrincipal = conductPrincipal;
	}

	public Date getConductDate() {
		return conductDate;
	}

	public void setConductDate(Date conductDate) {
		this.conductDate = conductDate;
	}

	public String getQuadratList() {
		return quadratList;
	}

	public void setQuadratList(String quadratList) {
		this.quadratList = quadratList;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public String getAssessorCode() {
		return assessorCode;
	}

	public void setAssessorCode(String assessorCode) {
		this.assessorCode = assessorCode;
	}

	public Date getAssessorDate() {
		return assessorDate;
	}

	public void setAssessorDate(Date assessorDate) {
		this.assessorDate = assessorDate;
	}

	public String getInvestigationDesc() {
		return investigationDesc;
	}

	public void setInvestigationDesc(String investigationDesc) {
		this.investigationDesc = investigationDesc;
	}
	public File getPhotos() {
		return photos;
	}
	public void setPhotos(File photos) {
		this.photos = photos;
	}
	public String getPhotosContentType() {
		return photosContentType;
	}
	public void setPhotosContentType(String photosContentType) {
		this.photosContentType = photosContentType;
	}
	public String getPhotosFileName() {
		return photosFileName;
	}
	public void setPhotosFileName(String photosFileName) {
		this.photosFileName = photosFileName;
	}

}
