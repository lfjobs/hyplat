package hy.ea.bo.office;
/**
 * QQ管理
 */
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;


public class Qq  implements BaseBean ,ExcelBean,java.io.Serializable {
	private String qqID;
	private String qqKey;
	private String companyID;
	private String organizationID;     
	private String qqNum;            	 //编号
	private String staffID;          	 //名字
	private String qqSequence;           //QQ号
	private String qqTel;            	 //手机号 
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","QQ编号","姓名","QQ号","手机号"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {qqNum,staffID,qqSequence,qqTel};
		return properties;
	} 
	public String getQqID() {
		return qqID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public void setQqID(String qqID) {
		this.qqID = qqID;
	}
	public String getQqKey() {
		return qqKey;
	}
	public void setQqKey(String qqKey) {
		this.qqKey = qqKey;
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
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getQqSequence() {
		return qqSequence;
	}
	public void setQqSequence(String qqSequence) {
		this.qqSequence = qqSequence;
	}
	public String getQqTel() {
		return qqTel;
	}
	public void setQqTel(String qqTel) {
		this.qqTel = qqTel;
	}
}
