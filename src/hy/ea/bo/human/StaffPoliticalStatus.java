package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 政治面貌Political Affiliation
 * @author zg
 *
 */
public class StaffPoliticalStatus implements BaseBean ,ExcelBean,java.io.Serializable{
	private String politicalkey;
	private String companyID;
	private String politicalID;
	private String staffID;
	private String politicalStatus;
	private Date joinDate;
	private String unit;
	private String introducer;
	private Date probatePassDate;
	private String partyStand;
	private String auditor;
	private String auditorNumber;
	private String auditorTime;
	private String appendix;
	private String politicalDesc;
    private String photo;
    
    private File filephoto;//附件
    private String filephotoFileName;
	private String filephotoContentType;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "政治面貌", "参加组织日期", "参加党派时所在单位", "介绍人", "转正时间",
				"党（团）龄", "审核人", "审核人人员编号", "审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { oMap.get(politicalStatus),
				String.format("%1$tF", joinDate), unit, introducer,
				String.format("%1$tF", probatePassDate), partyStand,
				auditor, auditorNumber,
				 auditorTime, politicalDesc };
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public Date getProbatePassDate() {
		return probatePassDate;
	}

	public void setProbatePassDate(Date probatePassDate) {
		this.probatePassDate = probatePassDate;
	}

	public String getPartyStand() {
		return partyStand;
	}

	public void setPartyStand(String partyStand) {
		this.partyStand = partyStand;
	}

	public String getAuditor() {
		return auditor;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getPoliticalkey() {
		return politicalkey;
	}

	public void setPoliticalkey(String politicalkey) {
		this.politicalkey = politicalkey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getPoliticalID() {
		return politicalID;
	}

	public void setPoliticalID(String politicalID) {
		this.politicalID = politicalID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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

	public String getPoliticalDesc() {
		return politicalDesc;
	}

	public void setPoliticalDesc(String politicalDesc) {
		this.politicalDesc = politicalDesc;
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
