package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class NetWork implements BaseBean ,ExcelBean,java.io.Serializable{
	private String netWorkID;
	private String netWorkKey;
    private String organizationID;
	private String companyID;
	private String netWorkAddress;				//网络地址
	private String netWorkName;				    //网络名称
	private String netWorkCode;				    //网络编号
	private String netWorkPassword;				//网络密码
	private String amendPassword;				//修改密码
	private String admin;						//承办人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "网络地址","网络名称","网络编号","网络密码" , "修改密码", "承办人"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {netWorkAddress,netWorkName,netWorkCode, netWorkPassword,amendPassword,admin};
		return properties;
	}
	public String getNetWorkID() {
		return netWorkID;
	}
	public void setNetWorkID(String netWorkID) {
		this.netWorkID = netWorkID;
	}
	public String getNetWorkKey() {
		return netWorkKey;
	}
	public void setNetWorkKey(String netWorkKey) {
		this.netWorkKey = netWorkKey;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getNetWorkAddress() {
		return netWorkAddress;
	}
	public void setNetWorkAddress(String netWorkAddress) {
		this.netWorkAddress = netWorkAddress;
	}
	public String getNetWorkName() {
		return netWorkName;
	}
	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}
	public String getNetWorkPassword() {
		return netWorkPassword;
	}
	public void setNetWorkPassword(String netWorkPassword) {
		this.netWorkPassword = netWorkPassword;
	}
	public String getAmendPassword() {
		return amendPassword;
	}
	public void setAmendPassword(String amendPassword) {
		this.amendPassword = amendPassword;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getNetWorkCode() {
		return netWorkCode;
	}
	public void setNetWorkCode(String netWorkCode) {
		this.netWorkCode = netWorkCode;
	}
	
	
}
