package hy.ea.bo.driving;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DrivingDealCheGuan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DrivingDealCheGuan  implements BaseBean,ExcelBean{

	// Fields

	private String drivingDealCheGuankey;
	private String drivingDealCheGuanid;
	private String companyid;
	private String staffID;// 学员ID
	private String staffName;//学员名称
	private Date   submitCheGuanDate; //报车管时间
	private Date   inputDate;//录入时间
	private String theCheGuanStates;//00 未报车管 01 已报车管
	private String operationResponsibleID;//操作责任人ID
	private String operationResponsibleName;//操作责任人名称
	private String checkCheGuanStates;// 00 不合格  01 合格 车管审核资料是否合格
	private String checkCheGuanStatesReason;//不合格原因
	public DrivingDealCheGuan() {
		super();
	}
	
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "开始居住日期", "结束居住日期", "地址类别", "详细地址", "备注" };
		return titles;
	}




	/**
	 * 录入时间 系统自动生成
	 * @return
	 */
	public Date getInputDate() {
		return inputDate;
	}


	/**
	 * 录入时间 系统自动生成
	 * @return
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}


	/**
	 * 数据库主键
	 * @return
	 */
	public String getDrivingDealCheGuankey() {
		return drivingDealCheGuankey;
	}
	/**
	 * 数据库主键
	 * @return
	 */
	public void setDrivingDealCheGuankey(String drivingDealCheGuankey) {
		this.drivingDealCheGuankey = drivingDealCheGuankey;
	}
	/**
	 * 业务主键
	 * @return
	 */
	public String getDrivingDealCheGuanid() {
		return drivingDealCheGuanid;
	}
	/**
	 * 业务主键
	 * @return
	 */
	public void setDrivingDealCheGuanid(String drivingDealCheGuanid) {
		this.drivingDealCheGuanid = drivingDealCheGuanid;
	}

	/**
	 * 报车管时间
	 * @return
	 */
	public Date getSubmitCheGuanDate() {
		return submitCheGuanDate;
	}
	/**
	 * 报车管时间
	 * @return
	 */
	public void setSubmitCheGuanDate(Date submitCheGuanDate) {
		this.submitCheGuanDate = submitCheGuanDate;
	}
	/**
	 * 公司ID
	 * @return
	 */
	public String getCompanyid() {
		return companyid;
	}
	/**
	 * 公司ID
	 * @return
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	/**
	 * 学员ID
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * 学员ID
	 * @return
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 报车管状态
	 * @return
	 */
	public String getTheCheGuanStates() {
		return theCheGuanStates;
	}
	/**
	 * 报车管状态
	 * @return
	 */
	public void setTheCheGuanStates(String theCheGuanStates) {
		this.theCheGuanStates = theCheGuanStates;
	}

	/**
	 * 学员名称
	 * @return
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * 学员名称
	 * @return
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	/**
	 * 操作责任人ID
	 * @return
	 */
	public String getOperationResponsibleID() {
		return operationResponsibleID;
	}
	/**
	 * 操作责任人ID
	 * @return
	 */
	public void setOperationResponsibleID(String operationResponsibleID) {
		this.operationResponsibleID = operationResponsibleID;
	}


	/**
	 * 操作责任人名称
	 * @return
	 */
	public String getOperationResponsibleName() {
		return operationResponsibleName;
	}
	/**
	 * 操作责任人名称
	 * @return
	 */
	public void setOperationResponsibleName(String operationResponsibleName) {
		this.operationResponsibleName = operationResponsibleName;
	}
	/**
	 * 报车管资料是否合格
	 * @return
	 */
	public String getCheckCheGuanStates() {
		return checkCheGuanStates;
	}
	/**
	 * 报车管资料是否合格
	 * @return
	 */
	public void setCheckCheGuanStates(String checkCheGuanStates) {
		this.checkCheGuanStates = checkCheGuanStates;
	}
	/**
	 * 车管资料不合格原因
	 * @return
	 */
	public String getCheckCheGuanStatesReason() {
		return checkCheGuanStatesReason;
	}
	/**
	 * 车管资料不合格原因
	 * @return
	 */
	public void setCheckCheGuanStatesReason(String checkCheGuanStatesReason) {
		this.checkCheGuanStatesReason = checkCheGuanStatesReason;
	}
	
}