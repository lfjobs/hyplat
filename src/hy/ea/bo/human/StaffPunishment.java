package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;
/**
 * 处分情况
 * @author zg
 *
 */
public class StaffPunishment implements BaseBean ,ExcelBean{
	private String punishmentkey;
	private String punishmentID;
	private String companyID;
	private String staffID;
	private String punishmentType;//处分类别
	private String punishmentName;//处分名称
	private String punishmentReason;//处分原因
	private Date punishmentDate;//处分批准日期
	private String punishmentOrgan;//处分撤销日期
	private Date dischargeDate;//处分批准机关名称
	private String auditor;//审核人
	private String auditorNumber;//审核人人员编号
	private String auditorTime;//审核时间
	private String appendix;
	private String punishmentDesc;//备注
	private String photo;

	private File filePhoto;
	private String filePhotoFileName;
    private String filePhotoContentType;
	
	
	
	private static Map<String, String> oMap;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "处分类别", "处分名称", "处分原因", "处分批准日期", "处分批准机关名称",
				"处分撤销日期", "审核人", "审核人人员编号", "审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(punishmentType), punishmentName,
				punishmentReason, String.format("%1$tF", punishmentDate),
				punishmentOrgan, String.format("%1$tF", dischargeDate),
			    auditor, auditorNumber, auditorTime,
				punishmentDesc };
		return properties;
	}
	
	public File getFilePhoto() {
		return filePhoto;
	}



	public void setFilePhoto(File filePhoto) {
		this.filePhoto = filePhoto;
	}



	public String getFilePhotoContentType() {
		return filePhotoContentType;
	}



	public void setFilePhotoContentType(String filePhotoContentType) {
		this.filePhotoContentType = filePhotoContentType;
	}



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPunishmentkey() {
		return punishmentkey;
	}

	public void setPunishmentkey(String punishmentkey) {
		this.punishmentkey = punishmentkey;
	}

	public String getPunishmentID() {
		return punishmentID;
	}

	public void setPunishmentID(String punishmentID) {
		this.punishmentID = punishmentID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getPunishmentType() {
		return punishmentType;
	}

	public void setPunishmentType(String punishmentType) {
		this.punishmentType = punishmentType;
	}

	public String getPunishmentName() {
		return punishmentName;
	}

	public void setPunishmentName(String punishmentName) {
		this.punishmentName = punishmentName;
	}

	public String getPunishmentReason() {
		return punishmentReason;
	}

	public void setPunishmentReason(String punishmentReason) {
		this.punishmentReason = punishmentReason;
	}

	public String getPunishmentOrgan() {
		return punishmentOrgan;
	}

	public void setPunishmentOrgan(String punishmentOrgan) {
		this.punishmentOrgan = punishmentOrgan;
	}

	public Date getPunishmentDate() {
		return punishmentDate;
	}

	public void setPunishmentDate(Date punishmentDate) {
		this.punishmentDate = punishmentDate;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditorNumber() {
		return auditorNumber;
	}

	public void setAuditorNumber(String auditorNumber) {
		this.auditorNumber = auditorNumber;
	}

	public String getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(String auditorTime) {
		this.auditorTime = auditorTime;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

	public String getPunishmentDesc() {
		return punishmentDesc;
	}

	public void setPunishmentDesc(String punishmentDesc) {
		this.punishmentDesc = punishmentDesc;
	}

	public static Map<String, String> getOMap() {
		return oMap;
	}

	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getFilePhotoFileName() {
		return filePhotoFileName;
	}

	public void setFilePhotoFileName(String filePhotoFileName) {
		this.filePhotoFileName = filePhotoFileName;
	}
}
