package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 企业纪实管理
 *
 */
public class EnterpriseReport implements BaseBean,ExcelBean ,java.io.Serializable {
	private String reportID;
	private String reportKey;
	private String companyID;
	private String organizationID;
	private Date reportDate;//时间
	private String companyName;//单位
	private String reportName;//纪实名称
	private String reportContent;//纪实内容
	private String reportStatus;//纪实状态
	private String remark;//备注
	public static String[] columnHeadings(){
		String[] titles = {"序号" , "时间", "单位","纪实名称","纪实主题内容","纪实状况","纪实备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[] properties = {String.format("%1$tF", reportDate),companyName,reportName,reportContent,reportStatus,remark};
		return properties;
	}
	
	public String getReportID() {
		return reportID;
	}
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	public String getReportKey() {
		return reportKey;
	}
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
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
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
