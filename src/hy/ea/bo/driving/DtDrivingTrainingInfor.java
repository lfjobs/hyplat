package hy.ea.bo.driving;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * DtDrivingTrainingInfor entity. @author MyEclipse Persistence Tools
 */

public class DtDrivingTrainingInfor implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	private String drivingtraininginforkey;
	private DtDrivingAppointmentRecord dtDrivingAppointmentRecord;
	private String drivingtraininginforid;
	private String istruestatus;

	// Constructors
	public static String[] columnHeadings() {
		String[] titles = { "序号","培训状态","培训日期","起时间","止时间","时长","教练","教练编号","车牌号","培训地点"
				,"是否接送","接时间","送时间","接送地点","接送责任人","接送责任人电话","接送车牌号","接送车型号","总学时","已预约学时"
				,"未预约学时","短信通知"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		 String[] properties={istruestatus.equals("01")?"已培训":"未培训",String.format("%1$tF", dtDrivingAppointmentRecord.getAppointmentdate()),dtDrivingAppointmentRecord.getStartdate()
				 ,dtDrivingAppointmentRecord.getEnddate(),dtDrivingAppointmentRecord.getTimelength().toString(),
				 dtDrivingAppointmentRecord.getCoach(),dtDrivingAppointmentRecord.getCoachcode(),dtDrivingAppointmentRecord.getCarcode(),dtDrivingAppointmentRecord.getTrainingaddress()
				 ,dtDrivingAppointmentRecord.getWhether().equals("01")?"是":"否",dtDrivingAppointmentRecord.getStartshuttledate(),
				dtDrivingAppointmentRecord.getEndshuttledate(),dtDrivingAppointmentRecord.getShuttleaddress(),
				dtDrivingAppointmentRecord.getShuttlestaff(),dtDrivingAppointmentRecord.getShuttlestaffphone(),dtDrivingAppointmentRecord.getShuttlecarnumber(),
				dtDrivingAppointmentRecord.getShuttlecarxinhao(),dtDrivingAppointmentRecord.getSumtimelength(),
				dtDrivingAppointmentRecord.getHaveschooltime().toString(),dtDrivingAppointmentRecord.getNoschooltime().toString()
				,dtDrivingAppointmentRecord.getIssendmessage()};
		return properties;
	}
	/** default constructor */
	public DtDrivingTrainingInfor() {
	}

	/** minimal constructor */
	public DtDrivingTrainingInfor(
			DtDrivingAppointmentRecord dtDrivingAppointmentRecord,
			String drivingtraininginforid) {
		this.dtDrivingAppointmentRecord = dtDrivingAppointmentRecord;
		this.drivingtraininginforid = drivingtraininginforid;
	}

	/** full constructor */
	public DtDrivingTrainingInfor(
			DtDrivingAppointmentRecord dtDrivingAppointmentRecord,
			String drivingtraininginforid, String istruestatus) {
		this.dtDrivingAppointmentRecord = dtDrivingAppointmentRecord;
		this.drivingtraininginforid = drivingtraininginforid;
		this.istruestatus = istruestatus;
	}

	// Property accessors

	public String getDrivingtraininginforkey() {
		return this.drivingtraininginforkey;
	}

	public void setDrivingtraininginforkey(String drivingtraininginforkey) {
		this.drivingtraininginforkey = drivingtraininginforkey;
	}

	public DtDrivingAppointmentRecord getDtDrivingAppointmentRecord() {
		return this.dtDrivingAppointmentRecord;
	}

	public void setDtDrivingAppointmentRecord(
			DtDrivingAppointmentRecord dtDrivingAppointmentRecord) {
		this.dtDrivingAppointmentRecord = dtDrivingAppointmentRecord;
	}

	public String getDrivingtraininginforid() {
		return this.drivingtraininginforid;
	}

	public void setDrivingtraininginforid(String drivingtraininginforid) {
		this.drivingtraininginforid = drivingtraininginforid;
	}

	public String getIstruestatus() {
		return this.istruestatus;
	}

	public void setIstruestatus(String istruestatus) {
		this.istruestatus = istruestatus;
	}

}