package mobile.tiantai.android.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class AuditRecord implements BaseBean{

	
	/**
	 * 任务消息审核表
	 */
	private String aukey;
	private String auid;
	private String batchNum;  //提交时候生成的一组，比如一组10个人审
	private String auditID;//审核人ID
	private String auditName;//审核人姓名
	private String auditOrgID;//审核人部门ID
	private String auditOrgName;//审核人部门Name
	private String auditComID;//审核人公司ID
	private String auditComName;//审核人公司Name
	private String position;//审批人职位
	private String startID;//发起人ID
	private String startName;//发起人姓名
	
	private Date submitDate;//提交审核时间
	
	private Date commitDate;//审核过程时间
	private String sorts;//审核顺序
	
	private String viewUrl;//查看详情链接
	private String module;//模块
	private String thirdId;//第三方ID，比如：隶属于某个产品的审批流程
	
	private String state;//00；尚未收到，01：审核中；02：审核通过，03：已驳回，04：已转交，05：发起人已撤销
	//转交要新生成一条审核中的记录至转交人后，转交人状态已转交

	private String auditComment;//审核意见
	 

	public String getAukey() {
		return aukey;
	}
	public void setAukey(String aukey) {
		this.aukey = aukey;
	}
	public String getAuid() {
		return auid;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public String getAuditID() {
		return auditID;
	}
	public void setAuditID(String auditID) {
		this.auditID = auditID;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditOrgID() {
		return auditOrgID;
	}
	public void setAuditOrgID(String auditOrgID) {
		this.auditOrgID = auditOrgID;
	}
	public String getAuditOrgName() {
		return auditOrgName;
	}
	public void setAuditOrgName(String auditOrgName) {
		this.auditOrgName = auditOrgName;
	}
	public String getAuditComID() {
		return auditComID;
	}
	public void setAuditComID(String auditComID) {
		this.auditComID = auditComID;
	}
	public String getAuditComName() {
		return auditComName;
	}
	public void setAuditComName(String auditComName) {
		this.auditComName = auditComName;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}

	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getStartID() {
		return startID;
	}
	public void setStartID(String startID) {
		this.startID = startID;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}

	public String getSorts() {
		return sorts;
	}
	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	 
    
	public String getAuditComment() {
		return auditComment;
	}
	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
}
