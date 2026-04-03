package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * ip地址管理
 * @author Administrator
 *
 */
public class AddressIP implements BaseBean,ExcelBean,Serializable {
	private String ipID;
	private String ipKey;
	private String companyID;
	private String organizationID;
	private String ipCode;//编号
	private String userName;//姓名
	private String computerModel;//电脑型号
	private String ipAddress;//IP地址
	
	
	
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","编号","姓名","电脑型号","IP地址"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {ipCode,userName,computerModel,ipAddress };
		return properties;
	}
			
	public String getIpID() {
		return ipID;
	}
	public void setIpID(String ipID) {
		this.ipID = ipID;
	}
	public String getIpKey() {
		return ipKey;
	}
	public void setIpKey(String ipKey) {
		this.ipKey = ipKey;
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
	public String getIpCode() {
		return ipCode;
	}
	public void setIpCode(String ipCode) {
		this.ipCode = ipCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComputerModel() {
		return computerModel;
	}
	public void setComputerModel(String computerModel) {
		this.computerModel = computerModel;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
