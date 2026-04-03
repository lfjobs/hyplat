package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 公文和第三方模块关联表
 *
 */
public class DocRelateOther implements BaseBean {

	private String key;
	private String id;
    private String status;//初始状态00 ，盖章后章台01
	private Date updateDate;//盖章日期
	private Date createDate;//创建日期
	private String tableName;//表名
	private String idName;//ID名
	private String idValue;//ID 值
	private String stateName;//要修改的状态名
	private String stateValue;//通过的值
	private String refundValue;//驳回的值

	private String docId;//公文ID
	private String source;//来源
	private String htmlUrl;//html链接
	private String title;//公文名称
	private String staffID;
	private String companyID;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(String refundValue) {
		this.refundValue = refundValue;
	}
}
