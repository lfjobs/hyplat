package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/*
 * 车辆状态维护
 * */
public class CarStatus implements BaseBean, ExcelBean,java.io.Serializable{
	private String stKey;
	private String stID;
	private String companyID;//修改状态的人的公司
	private String companyName;
	private String organizationID;//修改状态的人的部门
	private String organizationName;
	private String staffID;//修改状态的人ID
	private String staffName;//修改状态的人姓名
	private String staffCode;//修改人的编号
	private Date operateDate; //修改时间
	private String carID;  //车辆ID
	private String carNum;
	private String status;//车辆状态00 未使用；01已使用 10 下线
	private String statusname;


	public static String[] columnHeadings() {
		String[] titles = { "序号","责任人公司","责任人部门","责任人","车牌号","操作时间","状态"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {companyName,organizationName,staffName,carNum,operateDate.toString(),statusname};
		return properties;
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
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getStKey() {
		return stKey;
	}
	public void setStKey(String stKey) {
		this.stKey = stKey;
	}
	public String getStID() {
		return stID;
	}
	public void setStID(String stID) {
		this.stID = stID;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusname() {
		return statusname;
	}
	
	public String getStatusname(String status) {
         if (status.equals("00")) {
        	 statusname="未使用";
		 }
		if (status.equals("01")) {
			 statusname="已使用";
		}
		if (status.equals("10")) {
			 statusname="下线";
		}
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	
}
