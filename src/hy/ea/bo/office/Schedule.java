package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Map;

/**
 * 公共日程安排
 * @author Administrator
 *
 */
public class Schedule implements BaseBean,ExcelBean,java.io.Serializable {

	private String scheduleKey;
	private String scheduleID;
	private String companyID;
	private String organizationID;
	
	private String corganizationID;							//所在部门				
	private String staffID;									//员工姓名
	private String workContent;								//工作内容
	private String workSchedule;							//日程安排
	private String workStatus;								//完成状态
	private String appraise;								//完成评价
	
	private static Map<String,String> oMap; 
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getCorganizationName(){
		String coName = "";
		if(null!=oMap){
			coName = oMap.get(corganizationID);
		}
		return coName;
	}
	public String getStaffName(){
		String stfName = "";
		if(null!=oMap){
			stfName = oMap.get(staffID);
		}
		return stfName;
	}
	public String getScheduleKey() {
		return scheduleKey;
	}
	public void setScheduleKey(String scheduleKey) {
		this.scheduleKey = scheduleKey;
	}
	public String getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
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
	public String getCorganizationID() {
		return corganizationID;
	}
	public void setCorganizationID(String corganizationID) {
		this.corganizationID = corganizationID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	public String getWorkSchedule() {
		return workSchedule;
	}
	public void setWorkSchedule(String workSchedule) {
		this.workSchedule = workSchedule;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getAppraise() {
		return appraise;
	}
	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}
	public static String[] columnHeadings(){
		String[] titles = {"序号","所在部门","员工姓名","工作内容","日程安排","完成状态","完成评价"};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[] titiles = {oMap.get(corganizationID),oMap.get(staffID),workContent,workSchedule,workStatus,appraise};
		return titiles;
	}
}
