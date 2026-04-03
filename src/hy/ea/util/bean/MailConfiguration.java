package hy.ea.util.bean;

public class MailConfiguration {
	private String protocol;
	private String host;
	private String port;
	private String auth;
	private String mailFrom;
	private String mailTo;
	private String copytTo;
	private String msgSubject;
	private String msgText;
	private String authUser;
	private String authPassword;
	private String replyMail;
	private String replyPhone;
	private String petSystemPassword;
	
	public String getReplyMail() {
		return replyMail;
	}
	public void setReplyMail(String replyMail) {
		this.replyMail = replyMail;
	}
	public String getReplyPhone() {
		return replyPhone;
	}
	public void setReplyPhone(String replyPhone) {
		this.replyPhone = replyPhone;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getCopytTo() {
		return copytTo;
	}
	public void setCopytTo(String copytTo) {
		this.copytTo = copytTo;
	}
	public String getMsgSubject() {
		return msgSubject;
	}
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	
	
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String result = "";
	    
	    result = "MailConfiguration ( "
	        + super.toString() + TAB
	        + "protocol = " + this.protocol + TAB
	        + "host = " 	+ this.host + TAB
	        + "port = " 	+ this.port + TAB
	        + "auth = "     + this.auth + TAB
	        + "mailFrom = " + this.mailFrom + TAB
	        + "mailTo = " 	+ this.mailTo + TAB
	        + "copytTo = " 	+ this.copytTo + TAB
	        + "msgSubject = " + this.msgSubject + TAB
	        + "msgText = " 	  + this.msgText + TAB
	        + "authUser = " 	  + this.authUser + TAB
	        + "authPassword = "   + this.authPassword + TAB
	        + "replyMail = "  + this.replyMail + TAB
	        + "replyPhone = " + this.replyPhone + TAB
	        + "petSystemPassword = " + this.petSystemPassword + TAB
	        + " )";
	
	    return result;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getAuthUser() {
		return authUser;
	}
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public String getPetSystemPassword() {
		return petSystemPassword;
	}
	public void setPetSystemPassword(String petSystemPassword) {
		this.petSystemPassword = petSystemPassword;
	}
}
