package hy.ea.bo.driving;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DtDrivingAppointmentRecord entity. @author MyEclipse Persistence Tools
 */

public class DtDrivingAppointmentRecord implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	private String drivingappointmentrecordkey;
	private String drivingappointmentrecordid;
	private String drivingprincipalid;
	private Date appointmentdate;
	private String startdate;
	private String enddate;
	private BigDecimal timelength;
	private String coach;
	private String coachcode;
	private String carcode;
	private String coachphone;
	private String trainingaddress;
	private String whether;
	private String startshuttledate;
	private String endshuttledate;
	private String shuttleaddress;
	private String shuttlestaff;
	private String shuttlestaffid;
	private String shuttlestaffphone;
	private String shuttlecarnumber;
	private String shuttlecarxinhao;
	private String sumtimelength;
	private BigDecimal haveschooltime;
	private BigDecimal noschooltime;
	private String issendmessage;
	private String isappointmentstatus;
	private String companyid;
	private String staffid;
	private String staffname;
	private String staffphone;
	private String staffsex;
	private String staffcard;
	private String docstatus;
	/**
	 * @param
	 * @describe 培训项目  引用外键
	 * @time 2014-09-19
	 */
	private String ddsrsyllabusid;//ID
	private String ddsrsyllabusprogram; //名称
	public static String[] columnHeadings() {
		String[] titles = { "序号","预约日期","起时间","止时间","时长","教练","教练编号","车牌号","培训地点"
				,"是否接送","接时间","送时间","接送地点","接送责任人","接送责任人电话","接送车牌号","接送车型号","总学时","已预约学时"
				,"未预约学时","短信通知","预约状态"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		 String[] properties={String.format("%1$tF", appointmentdate),startdate,enddate,timelength.toString(),
				 coach,coachcode,carcode,trainingaddress,whether.equals("01")?"是":"否",startshuttledate,endshuttledate,shuttleaddress,
				 shuttlestaff,shuttlestaffphone,shuttlecarnumber,shuttlecarxinhao,sumtimelength,
				 haveschooltime.toString(),noschooltime.toString(),issendmessage,isappointmentstatus.equals("01")?"成功":"预约"};
		return properties;
	}
	// Constructors

	/** default constructor */
	public DtDrivingAppointmentRecord() {
	}

	/** minimal constructor */
	public DtDrivingAppointmentRecord(String drivingappointmentrecordid) {
		this.drivingappointmentrecordid = drivingappointmentrecordid;
	}

	/** full constructor */
	

	// Property accessors

	public String getDrivingappointmentrecordkey() {
		return this.drivingappointmentrecordkey;
	}

	public DtDrivingAppointmentRecord(String drivingappointmentrecordkey,
			String drivingappointmentrecordid, String drivingprincipalid,
			Date appointmentdate, String startdate, String enddate,
			BigDecimal timelength, String coach, String coachcode,
			String carcode, String trainingaddress, String whether,
			String startshuttledate, String endshuttledate,
			String shuttleaddress, String shuttlestaff, String shuttlestaffid,
			String shuttlestaffphone, String shuttlecarnumber,
			String shuttlecarxinhao, String sumtimelength,
			BigDecimal haveschooltime, BigDecimal noschooltime,
			String issendmessage, String isappointmentstatus, String companyid,
			String staffid, String staffname, String staffphone, String staffsex) {
		super();
		this.drivingappointmentrecordkey = drivingappointmentrecordkey;
		this.drivingappointmentrecordid = drivingappointmentrecordid;
		this.drivingprincipalid = drivingprincipalid;
		this.appointmentdate = appointmentdate;
		this.startdate = startdate;
		this.enddate = enddate;
		this.timelength = timelength;
		this.coach = coach;
		this.coachcode = coachcode;
		this.carcode = carcode;
		this.trainingaddress = trainingaddress;
		this.whether = whether;
		this.startshuttledate = startshuttledate;
		this.endshuttledate = endshuttledate;
		this.shuttleaddress = shuttleaddress;
		this.shuttlestaff = shuttlestaff;
		this.shuttlestaffid = shuttlestaffid;
		this.shuttlestaffphone = shuttlestaffphone;
		this.shuttlecarnumber = shuttlecarnumber;
		this.shuttlecarxinhao = shuttlecarxinhao;
		this.sumtimelength = sumtimelength;
		this.haveschooltime = haveschooltime;
		this.noschooltime = noschooltime;
		this.issendmessage = issendmessage;
		this.isappointmentstatus = isappointmentstatus;
		this.companyid = companyid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.staffphone = staffphone;
		this.staffsex = staffsex;
	}

	public void setDrivingappointmentrecordkey(
			String drivingappointmentrecordkey) {
		this.drivingappointmentrecordkey = drivingappointmentrecordkey;
	}

	public String getDrivingappointmentrecordid() {
		return this.drivingappointmentrecordid;
	}

	public void setDrivingappointmentrecordid(String drivingappointmentrecordid) {
		this.drivingappointmentrecordid = drivingappointmentrecordid;
	}

	public String getDrivingprincipalid() {
		return this.drivingprincipalid;
	}

	public void setDrivingprincipalid(String drivingprincipalid) {
		this.drivingprincipalid = drivingprincipalid;
	}

	public Date getAppointmentdate() {
		return this.appointmentdate;
	}

	public void setAppointmentdate(Date appointmentdate) {
		this.appointmentdate = appointmentdate;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getTimelength() {
		return this.timelength;
	}

	public void setTimelength(BigDecimal timelength) {
		this.timelength = timelength;
	}

	public String getCoach() {
		return this.coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getCoachcode() {
		return this.coachcode;
	}

	public void setCoachcode(String coachcode) {
		this.coachcode = coachcode;
	}

	public String getCarcode() {
		return this.carcode;
	}

	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}

	public String getTrainingaddress() {
		return this.trainingaddress;
	}

	public void setTrainingaddress(String trainingaddress) {
		this.trainingaddress = trainingaddress;
	}

	public String getWhether() {
		return this.whether;
	}

	public void setWhether(String whether) {
		this.whether = whether;
	}

	public String getStartshuttledate() {
		return this.startshuttledate;
	}

	public void setStartshuttledate(String startshuttledate) {
		this.startshuttledate = startshuttledate;
	}

	public String getEndshuttledate() {
		return this.endshuttledate;
	}

	public void setEndshuttledate(String endshuttledate) {
		this.endshuttledate = endshuttledate;
	}

	public String getShuttleaddress() {
		return this.shuttleaddress;
	}

	public void setShuttleaddress(String shuttleaddress) {
		this.shuttleaddress = shuttleaddress;
	}

	public String getShuttlestaff() {
		return this.shuttlestaff;
	}

	public void setShuttlestaff(String shuttlestaff) {
		this.shuttlestaff = shuttlestaff;
	}

	public String getShuttlestaffid() {
		return this.shuttlestaffid;
	}

	public void setShuttlestaffid(String shuttlestaffid) {
		this.shuttlestaffid = shuttlestaffid;
	}

	public String getShuttlestaffphone() {
		return this.shuttlestaffphone;
	}

	public void setShuttlestaffphone(String shuttlestaffphone) {
		this.shuttlestaffphone = shuttlestaffphone;
	}

	public String getShuttlecarnumber() {
		return this.shuttlecarnumber;
	}

	public void setShuttlecarnumber(String shuttlecarnumber) {
		this.shuttlecarnumber = shuttlecarnumber;
	}

	public String getShuttlecarxinhao() {
		return this.shuttlecarxinhao;
	}

	public void setShuttlecarxinhao(String shuttlecarxinhao) {
		this.shuttlecarxinhao = shuttlecarxinhao;
	}

	public String getSumtimelength() {
		return this.sumtimelength;
	}

	public void setSumtimelength(String sumtimelength) {
		this.sumtimelength = sumtimelength;
	}

	public BigDecimal getHaveschooltime() {
		return this.haveschooltime;
	}

	public void setHaveschooltime(BigDecimal haveschooltime) {
		this.haveschooltime = haveschooltime;
	}

	public BigDecimal getNoschooltime() {
		return this.noschooltime;
	}

	public void setNoschooltime(BigDecimal noschooltime) {
		this.noschooltime = noschooltime;
	}

	public String getIssendmessage() {
		return this.issendmessage;
	}

	public void setIssendmessage(String issendmessage) {
		this.issendmessage = issendmessage;
	}

	public String getIsappointmentstatus() {
		return this.isappointmentstatus;
	}

	public void setIsappointmentstatus(String isappointmentstatus) {
		this.isappointmentstatus = isappointmentstatus;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getStaffphone() {
		return staffphone;
	}

	public void setStaffphone(String staffphone) {
		this.staffphone = staffphone;
	}

	public String getStaffsex() {
		return staffsex;
	}

	public void setStaffsex(String staffsex) {
		this.staffsex = staffsex;
	}

	public String getStaffcard() {
		return staffcard;
	}

	public void setStaffcard(String staffcard) {
		this.staffcard = staffcard;
	}

	public String getCoachphone() {
		return coachphone;
	}

	public void setCoachphone(String coachphone) {
		this.coachphone = coachphone;
	}

	public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getDdsrsyllabusid() {
		return ddsrsyllabusid;
	}

	public void setDdsrsyllabusid(String ddsrsyllabusid) {
		this.ddsrsyllabusid = ddsrsyllabusid;
	}

	public String getDdsrsyllabusprogram() {
		return ddsrsyllabusprogram;
	}

	public void setDdsrsyllabusprogram(String ddsrsyllabusprogram) {
		this.ddsrsyllabusprogram = ddsrsyllabusprogram;
	}
	
	

}