package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocumentAuditing implements BaseBean,java.io.Serializable{
    private String key;
	private Long round;//操作（驳回 轮数 ，一个流程算一回）次数
    private String companyIDofSubscriber;//审批人所属公司
    private String deptIDofSubscriber;//审批人所属部门
    private String subscriberID;//审批人ID
    private String subscriberComment;//审批人评语
    private String preSubscriberKey;//上一审批记录的key
    private Date subscribTime;  //驳回、不批准、批准或最近一次转他人审批时间
    private String result;   //审批结果 reject,noapprove, approve, transfer
    private Document document;//公文
    //数据库没有的字段
    private String comNameofSub;
    private String deptNameofSub;
    private String subscriberName;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCompanyIDofSubscriber() {
		return companyIDofSubscriber;
	}
	public void setCompanyIDofSubscriber(String companyIDofSubscriber) {
		this.companyIDofSubscriber = companyIDofSubscriber;
	}
	public String getDeptIDofSubscriber() {
		return deptIDofSubscriber;
	}
	public void setDeptIDofSubscriber(String deptIDofSubscriber) {
		this.deptIDofSubscriber = deptIDofSubscriber;
	}
	public String getSubscriberID() {
		return subscriberID;
	}
	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}
	public String getSubscriberComment() {
		return subscriberComment;
	}
	public void setSubscriberComment(String subscriberComment) {
		this.subscriberComment = subscriberComment;
	}
	public String getPreSubscriberKey() {
		return preSubscriberKey;
	}
	public void setPreSubscriberKey(String preSubscriberKey) {
		this.preSubscriberKey = preSubscriberKey;
	}
	public Date getSubscribTime() {
		return subscribTime;
	}
	public void setSubscribTime(Date subscribTime) {
		this.subscribTime = subscribTime;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getComNameofSub() {
		return comNameofSub;
	}
	public void setComNameofSub(String comNameofSub) {
		this.comNameofSub = comNameofSub;
	}
	public String getDeptNameofSub() {
		return deptNameofSub;
	}
	public void setDeptNameofSub(String deptNameofSub) {
		this.deptNameofSub = deptNameofSub;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public Long getRound() {
		return round;
	}
	public void setRound(Long round) {
		this.round = round;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
