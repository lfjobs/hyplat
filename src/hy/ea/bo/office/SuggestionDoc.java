package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

public class SuggestionDoc implements BaseBean {

	private String key;
	private String sugId;
	private String docId;//公文ID
	private String suggestion;//意见
	private String sender;//发起人
    private String receiver;//接收人
    private String senderName;//发起人姓名
    private String receiverName;//接收人姓名
    private String sugTime;//发意见的时间
    private String sugTimestr;
	public String getSugTimestr() {
		return sugTimestr;
	}
	public void setSugTimestr(String sugTimestr) {
		this.sugTimestr = sugTimestr;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSugId() {
		return sugId;
	}
	public void setSugId(String sugId) {
		this.sugId = sugId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSugTime() {
		return sugTime;
	}
	public void setSugTime(String sugTime) {
		this.sugTime = sugTime;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}
