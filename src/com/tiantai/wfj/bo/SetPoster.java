package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 *  海报设置
 */
public class SetPoster implements BaseBean,Serializable {
    private String spKey;
    private String spID;
    private String posterName;//海报名称简介
    private String isPublish;//是否推送到设备了  00 未推送 01推送了
	private Date publishDate; //发布时间
	private Date offlineDate;//下线时间
	private String  offlineID;//下线人
	private String offlineName;//下线人姓名
	private String publishID;//发布人
	private String publishName;//发布人姓名
	private String posterPath;//图片路径； 800*1280
	private String companyID;//维护公司
	private String inputID;//录入人员
	private String inputName;//录入人员姓名
	private Date createDate;  //录入时间
	private String deviceType;  //00微信 01 支付宝
	private int sorts;//排序 1，2，3，4，5
	private Date sortDate;//下线时间
	private String  sortID;//下线人
	private String  sortName;//下线人姓名

	public String getSpKey() {
		return spKey;
	}

	public void setSpKey(String spKey) {
		this.spKey = spKey;
	}

	public String getSpID() {
		return spID;
	}

	public void setSpID(String spID) {
		this.spID = spID;
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getInputID() {
		return inputID;
	}

	public void setInputID(String inputID) {
		this.inputID = inputID;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getSorts() {
		return sorts;
	}

	public void setSorts(int sorts) {
		this.sorts = sorts;
	}

	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishID() {
		return publishID;
	}

	public void setPublishID(String publishID) {
		this.publishID = publishID;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public String getOfflineID() {
		return offlineID;
	}

	public void setOfflineID(String offlineID) {
		this.offlineID = offlineID;
	}

	public String getOfflineName() {
		return offlineName;
	}

	public void setOfflineName(String offlineName) {
		this.offlineName = offlineName;
	}

	public Date getSortDate() {
		return sortDate;
	}

	public void setSortDate(Date sortDate) {
		this.sortDate = sortDate;
	}

	public String getSortID() {
		return sortID;
	}

	public void setSortID(String sortID) {
		this.sortID = sortID;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
}
