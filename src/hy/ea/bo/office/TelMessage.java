package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.ea.util.MobileMessagenew;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 公司个人短信
 * @author Administrator
 *
 */
public class TelMessage  implements BaseBean ,ExcelBean,java.io.Serializable{

	private String key;//主键
	private String telMessageID;//业务主键
	
	private String companyID;
	private String companyName;
	private String ccompanyID;
	private String staffID;
	private String staffName;
	private String accountID;      //发件人账户名字
	private Date sendTime;			//发送时间
	private String content;			//发送内容
	
	private String telNum;             //消息对应的电话号码
	private String status;             //短消息的来源00表示在系统内，01表示系统外

	private Date sendDate;				//新加的字段，表示发送时间
	private String msgStatus;			//短消息状态在 MobileMessage.getStatusMSG()中取
	
	
	private String organizationID;//在哪个部门发的
	private String orgDetail;//是在部门下的办公室 还是营销模块；
	
	private String ralation;//接收短信人与发送人的关系；
	private String connection;//公司往来关系
	private String receiverCompanyID;//接收人的公司ID；
	private String receiverCompanyName;//接收人的公司名称;
	private String receiverID;//接收人ID；
	private String receiverName;//接收人姓名；
	
	

	public static String[] columnHeadings(){
		String[] titles = {"序号","往来单位","员工","内容","电话号码","发送时间","发送状态"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {companyName,staffName,content,telNum,String.format("%1$tF", sendDate),MobileMessagenew.getStatusMSG(msgStatus)};
		return properties;
	}
	
	public String getMsgStatusName(){
		return MobileMessagenew.getStatusMSG(msgStatus);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getTelMessageID() {
		return telMessageID;
	}

	public void setTelMessageID(String telMessageID) {
		this.telMessageID = telMessageID;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrgDetail() {
		return orgDetail;
	}
	public void setOrgDetail(String orgDetail) {
		this.orgDetail = orgDetail;
	}
	public String getRalation() {
		return ralation;
	}
	public void setRalation(String ralation) {
		this.ralation = ralation;
	}
	public String getReceiverCompanyID() {
		return receiverCompanyID;
	}
	public void setReceiverCompanyID(String receiverCompanyID) {
		this.receiverCompanyID = receiverCompanyID;
	}
	public String getReceiverCompanyName() {
		return receiverCompanyName;
	}
	public void setReceiverCompanyName(String receiverCompanyName) {
		this.receiverCompanyName = receiverCompanyName;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
}
