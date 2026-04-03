/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * cxf
 * 客户跟踪服务办
 * @author Administrator
 */
public class ClientTracking implements BaseBean,ExcelBean,java.io.Serializable{
	private String  clientTrackingKey;
	private String  clientTrackingID;
	private String  companyID;
	private String 	organizationID;
	private String  clientCode;             //编号
	private String 	clientName;             //客户名称
	private String 	clientBespeak;          //跟踪服务请求与预约
	private String 	clientAnnal;            //客户跟踪服务记录
	private String 	clientContent;          //客户跟踪服务内容
	private Date 	clientDate;             //客户跟踪服务日期
	private String 	clientResult;           //客户跟踪服务结果
	private String 	clientEvaluate;         //客户跟踪服务评估
	private String  clientFeedback; 	    //客户跟踪服务反馈
	private String 	remark;                 //备注
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "编号", "客户名称", "跟踪服务请求与预约", "客户跟踪服务记录", "客户跟踪服务内容", "客户跟踪服务日期",
				 "客户跟踪服务结果", "客户跟踪服务评估","客户跟踪服务反馈","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { clientCode,clientName,clientBespeak, 
				clientAnnal, clientContent, String.format("%1$tF", clientDate),clientResult,clientEvaluate,
				clientFeedback, remark};
		return properties;
	}
	public String getClientTrackingKey() {
		return clientTrackingKey;
	}
	public void setClientTrackingKey(String clientTrackingKey) {
		this.clientTrackingKey = clientTrackingKey;
	}
	public String getClientTrackingID() {
		return clientTrackingID;
	}
	public void setClientTrackingID(String clientTrackingID) {
		this.clientTrackingID = clientTrackingID;
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
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientBespeak() {
		return clientBespeak;
	}
	public void setClientBespeak(String clientBespeak) {
		this.clientBespeak = clientBespeak;
	}
	public String getClientAnnal() {
		return clientAnnal;
	}
	public void setClientAnnal(String clientAnnal) {
		this.clientAnnal = clientAnnal;
	}
	public String getClientContent() {
		return clientContent;
	}
	public void setClientContent(String clientContent) {
		this.clientContent = clientContent;
	}
	public String getClientResult() {
		return clientResult;
	}
	public void setClientResult(String clientResult) {
		this.clientResult = clientResult;
	}
	public String getClientEvaluate() {
		return clientEvaluate;
	}
	public void setClientEvaluate(String clientEvaluate) {
		this.clientEvaluate = clientEvaluate;
	}
	public String getClientFeedback() {
		return clientFeedback;
	}
	public void setClientFeedback(String clientFeedback) {
		this.clientFeedback = clientFeedback;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getClientDate() {
		return clientDate;
	}
	public void setClientDate(Date clientDate) {
		this.clientDate = clientDate;
	}

	
}
