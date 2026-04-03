package hy.ea.bo.human;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 往来个人或往来单位咨询跟踪信息
 * @author lf
 *
 */
public class Track implements BaseBean, ExcelBean {
	private String trackKey;
	private String trackID;	
	private String trackrelationID; //收集对象 外键
	private String companyID;//公司ID
	private Date inputDate; //录入时间
	private Date trackStartdate; //咨询起时间
	private Date tarckEnddate; //咨询止时间
	private String workAddr; //工作地点
	private String serviceMode; //服务方式
	private String trackContent; //咨询跟踪内容
	private String trackReason; //跟踪原因
	private String handleStatus; //处理状态
	
	
	public static String[] columnHeadings1() {
		String[] titles = { "序号", "公司名称","部门名称", "责任人","个人往来关系",
				"往来个人","电话", "地址","录入时间", "咨询起时间", "咨询止时间","工作地点" ,"服务方式","咨询跟踪内容","跟踪原因","处理状态"};
		return titles;
	}
	
	public static String[] columnHeadings2() {
		String[] titles = { "序号", "公司名称","部门名称", "责任人","单位往来关系",
				"往来单位","电话", "地址","录入时间", "咨询起时间", "咨询止时间","工作地点" ,"服务方式","咨询跟踪内容","跟踪原因","处理状态"};
		return titles;
	}
	
	public static String[] columnHeadings3() {
		String[] titles = { "序号", "录入时间", "咨询起时间", "咨询止时间","工作地点" ,"服务方式","咨询跟踪内容","跟踪原因","处理状态"};
		return titles;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTrackKey() {
		return trackKey;
	}

	public void setTrackKey(String trackKey) {
		this.trackKey = trackKey;
	}

	public String getTrackID() {
		return trackID;
	}

	public void setTrackID(String trackID) {
		this.trackID = trackID;
	}



	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getTrackStartdate() {
		return trackStartdate;
	}

	public void setTrackStartdate(Date trackStartdate) {
		this.trackStartdate = trackStartdate;
	}

	public Date getTarckEnddate() {
		return tarckEnddate;
	}

	public void setTarckEnddate(Date tarckEnddate) {
		this.tarckEnddate = tarckEnddate;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(String serviceMode) {
		this.serviceMode = serviceMode;
	}

	public String getTrackContent() {
		return trackContent;
	}

	public void setTrackContent(String trackContent) {
		this.trackContent = trackContent;
	}

	public String getTrackReason() {
		return trackReason;
	}

	public void setTrackReason(String trackReason) {
		this.trackReason = trackReason;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getTrackrelationID() {
		return trackrelationID;
	}

	public void setTrackrelationID(String trackrelationID) {
		this.trackrelationID = trackrelationID;
	}
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
}
