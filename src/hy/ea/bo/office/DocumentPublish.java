package hy.ea.bo.office;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class DocumentPublish implements BaseBean {

	private String key;
	private String pubId;
	private String docId;
	private String companyId;
	private String title;
    private String publisher;
    private String publisherID;
    private Date pubTime;
    private String wordUri;
    private String web;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPubId() {
		return pubId;
	}
	public String getPublisherID() {
		return publisherID;
	}
	public void setPublisherID(String publisherID) {
		this.publisherID = publisherID;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public String getWordUri() {
		return wordUri;
	}
	public void setWordUri(String wordUri) {
		this.wordUri = wordUri;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
