package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

public class Email implements BaseBean ,ExcelBean{
	private String emailID;
	private String emailKey;
	private String companyID;
	private String addresserID;           //发件人
	private String addresseeID;           //收件人
	private String title;                 //主题
	private String content;               //内容
	private Date   emailDate;             //日期
	private String addresserStatus;       //发件人状态  01已发 02草搞箱 03删除 '04'彻底删除
	private String addresseeStatus;       //收件人状态  11未读 12已读   03删除 '04'彻底删除 '10' 为对方还未发送保存在对方草稿箱
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
    public String getAddresserName()
    {
    	String addresserName="";
    	if(oMap!=null)
    	{
    		addresserName=oMap.get(addresserID);
    	}
    	return addresserName;
    }
    public String getAddresseeName()
    {
    	String addresseeName="";
    	if(oMap!=null)
    	{
    		addresseeName=oMap.get(addresseeID);
    	}
    	return addresseeName;
    }

	public String getEmailID() {
		return emailID;
	}
	
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getEmailKey() {
		return emailKey;
	}
	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAddresserID() {
		return addresserID;
	}
	public void setAddresserID(String addresserID) {
		this.addresserID = addresserID;
	}
	public String getAddresseeID() {
		return addresseeID;
	}
	public void setAddresseeID(String addresseeID) {
		this.addresseeID = addresseeID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getEmailDate() {
		return emailDate;
	}
	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}
	public String getAddresserStatus() {
		return addresserStatus;
	}
	public void setAddresserStatus(String addresserStatus) {
		this.addresserStatus = addresserStatus;
	}
	public String getAddresseeStatus() {
		return addresseeStatus;
	}
	public void setAddresseeStatus(String addresseeStatus) {
		this.addresseeStatus = addresseeStatus;
	}
	@Override
	public String[] properties() {
		return null;
	}
	
}
