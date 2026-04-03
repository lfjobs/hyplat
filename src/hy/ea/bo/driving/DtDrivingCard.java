package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;



public class DtDrivingCard  implements BaseBean{
	
	
	private String key;
	private String id;
	private String studentId;//学员ID
	private String companyID;//所在公司；
	private int printCount;//打印学员证次数
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public int getPrintCount() {
		return printCount;
	}
	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	

	
}