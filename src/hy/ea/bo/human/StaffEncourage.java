package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Map;

/**
 * 奖励情况
 * @author zg
 *
 */
public class StaffEncourage implements BaseBean,ExcelBean {

	private String encouragekey;
	private String encourageID;
	private String companyID;
	private String staffID;
	private String encourageType;//奖励类别
	private String encourageName;//奖励名称
	private String encourageReason;//奖励原因
	private String encourageDate;//奖励日期
	private String encourageOrgan;//奖励批准机关名称
	private String honoraryTitle;//荣誉称号名称
	private String encourageSanction;//奖励批准日期
	private String auditor;//审核人
	private String auditorNumber;//审核人人员编号
	private String auditorTime;
	private String appendix;//这个字段应该没有用. 如查证,删除之
	private String encourageDesc;//备注
	private String photo;
	
	private File filephoto;//附件
	private String filephotoFileName;
	private String filephotoContentType;
	
	
	private static Map<String, String> oMap;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "奖励类别", "奖励名称", "奖励原因", "奖励日期", "奖励批准机关名称",
				"荣誉称号名称", "奖励批准日期", "审核人", "审核人人员编号", "审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(encourageType), encourageName, encourageReason,
				encourageDate, encourageOrgan, honoraryTitle,
				encourageSanction,auditor, auditorNumber,
				auditorTime, encourageDesc };
		return properties;
	}

	public String getEncourageType() {
		return encourageType;
	}

	public void setEncourageType(String encourageType) {
		this.encourageType = encourageType;
	}

	public String getEncourageName() {
		return encourageName;
	}

	public void setEncourageName(String encourageName) {
		this.encourageName = encourageName;
	}

	public String getEncourageReason() {
		return encourageReason;
	}

	public void setEncourageReason(String encourageReason) {
		this.encourageReason = encourageReason;
	}

	public String getEncourageOrgan() {
		return encourageOrgan;
	}

	public void setEncourageOrgan(String encourageOrgan) {
		this.encourageOrgan = encourageOrgan;
	}

	public String getHonoraryTitle() {
		return honoraryTitle;
	}

	public void setHonoraryTitle(String honoraryTitle) {
		this.honoraryTitle = honoraryTitle;
	}

	public String getEncourageSanction() {
		return encourageSanction;
	}

	public void setEncourageSanction(String encourageSanction) {
		this.encourageSanction = encourageSanction;
	}

	public String getEncouragekey() {
		return encouragekey;
	}

	public void setEncouragekey(String encouragekey) {
		this.encouragekey = encouragekey;
	}

	public String getEncourageID() {
		return encourageID;
	}

	public void setEncourageID(String encourageID) {
		this.encourageID = encourageID;
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

	public String getEncourageDate() {
		return encourageDate;
	}

	public void setEncourageDate(String encourageDate) {
		this.encourageDate = encourageDate;
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

	public String getEncourageDesc() {
		return encourageDesc;
	}

	public void setEncourageDesc(String encourageDesc) {
		this.encourageDesc = encourageDesc;
	}

	public static Map<String, String> getOMap() {
		return oMap;
	}

	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public File getFilephoto() {
		return filephoto;
	}

	public void setFilephoto(File filephoto) {
		this.filephoto = filephoto;
	}

	public String getFilephotoContentType() {
		return filephotoContentType;
	}

	public void setFilephotoContentType(String filephotoContentType) {
		this.filephotoContentType = filephotoContentType;
	}

	public String getFilephotoFileName() {
		return filephotoFileName;
	}

	public void setFilephotoFileName(String filephotoFileName) {
		this.filephotoFileName = filephotoFileName;
	}

}
