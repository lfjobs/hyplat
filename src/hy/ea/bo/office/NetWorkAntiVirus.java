package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

public class NetWorkAntiVirus implements BaseBean ,ExcelBean,java.io.Serializable {
	private String antiVirusID;
	private String antiVirusKey;
    private String organizationID;
	private String companyID;
	private String networkAddress;			    //网络地址
	private String netWorkName;				    //网络名称
	private String netWorkCode;				    //网络编号
	private String netWorkPassword;				//网络密码
	private String antiVirusSoftware ;		    //杀毒软件
	private Date antiVirusDate;				    //杀毒日期
	private String antiVirusStatus;             //杀毒状态     01已杀毒  02 未杀毒    03正在杀毒
	private String admin;                       //承办人
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "网络地址","网络名称","网络编号","网络密码" , "杀毒软件", "杀毒日期", "杀毒状态", "承办人"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {networkAddress,netWorkName,netWorkCode,netWorkPassword,antiVirusSoftware,String.format("%1$tF", antiVirusDate),oMap.get(antiVirusStatus), admin};
		return properties;
	}
	
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getAntiVirusID() {
		return antiVirusID;
	}
	public void setAntiVirusID(String antiVirusID) {
		this.antiVirusID = antiVirusID;
	}
	public String getAntiVirusKey() {
		return antiVirusKey;
	}
	public void setAntiVirusKey(String antiVirusKey) {
		this.antiVirusKey = antiVirusKey;
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
	public String getNetworkAddress() {
		return networkAddress;
	}
	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	public String getNetWorkName() {
		return netWorkName;
	}
	public void setNetWorkName(String netWorkName) {
		this.netWorkName = netWorkName;
	}
	public String getNetWorkCode() {
		return netWorkCode;
	}
	public void setNetWorkCode(String netWorkCode) {
		this.netWorkCode = netWorkCode;
	}
	public String getNetWorkPassword() {
		return netWorkPassword;
	}
	public void setNetWorkPassword(String netWorkPassword) {
		this.netWorkPassword = netWorkPassword;
	}
	public String getAntiVirusSoftware() {
		return antiVirusSoftware;
	}
	public void setAntiVirusSoftware(String antiVirusSoftware) {
		this.antiVirusSoftware = antiVirusSoftware;
	}
	public Date getAntiVirusDate() {
		return antiVirusDate;
	}
	public void setAntiVirusDate(Date antiVirusDate) {
		this.antiVirusDate = antiVirusDate;
	}
	public String getAntiVirusStatus() {
		return antiVirusStatus;
	}
	public void setAntiVirusStatus(String antiVirusStatus) {
		this.antiVirusStatus = antiVirusStatus;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
}
